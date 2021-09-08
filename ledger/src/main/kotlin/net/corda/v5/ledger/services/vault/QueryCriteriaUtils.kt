@file:JvmName("QueryCriteriaUtils")

package net.corda.v5.ledger.services.vault

import java.lang.reflect.Field
import kotlin.jvm.internal.CallableReference
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.jvm.javaGetter
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.util.uncheckedCast
import net.corda.v5.ledger.schemas.StatePersistable

@CordaSerializable
@DoNotImplement
interface Operator

enum class BinaryLogicalOperator : Operator {
    AND,
    OR
}

enum class EqualityComparisonOperator : Operator {
    EQUAL,
    NOT_EQUAL,
    EQUAL_IGNORE_CASE,
    NOT_EQUAL_IGNORE_CASE
}

enum class BinaryComparisonOperator : Operator {
    LESS_THAN,
    LESS_THAN_OR_EQUAL,
    GREATER_THAN,
    GREATER_THAN_OR_EQUAL,
}

enum class NullOperator : Operator {
    IS_NULL,
    NOT_NULL
}

enum class LikenessOperator : Operator {
    LIKE,
    NOT_LIKE,
    LIKE_IGNORE_CASE,
    NOT_LIKE_IGNORE_CASE
}

enum class CollectionOperator : Operator {
    IN,
    NOT_IN,
    IN_IGNORE_CASE,
    NOT_IN_IGNORE_CASE
}

@CordaSerializable
enum class AggregateFunctionType {
    COUNT,
    AVG,
    MIN,
    MAX,
    SUM,
}

@CordaSerializable
sealed class CriteriaExpression<O, out T> {
    data class BinaryLogical<O>(val left: CriteriaExpression<O, Boolean>, val right: CriteriaExpression<O, Boolean>, val operator: BinaryLogicalOperator) : CriteriaExpression<O, Boolean>()
    data class Not<O>(val expression: CriteriaExpression<O, Boolean>) : CriteriaExpression<O, Boolean>()
    data class ColumnPredicateExpression<O, C>(val column: Column<O, C>, val predicate: ColumnPredicate<C>) : CriteriaExpression<O, Boolean>()
    data class AggregateFunctionExpression<O, C>(val column: Column<O, C>, val predicate: ColumnPredicate<C>,
                                                 val groupByColumns: List<Column<O, C>>?,
                                                 val orderBy: Sort.Direction?) : CriteriaExpression<O, Boolean>()
}

@CordaSerializable
class Column<O, out C>(val name: String, val declaringClass: Class<*>) {
    constructor(field: FieldInfo) : this(field.name, field.entityClass)
    constructor(property: KProperty1<O, C?>) : this(property.name, declaringClass(property))

    private companion object {
        fun <O, C> declaringClass(property: KProperty1<O, C?>): Class<*> {
            return when (property) {
                // This is to ensure that, for a JPA Entity, a field declared in a MappedSuperclass will not cause Hibernate to reject a query referencing it.
                // TODO remove the cast and access the owner properly after it will be exposed as Kotlin's public API (https://youtrack.jetbrains.com/issue/KT-24170).
                is CallableReference -> ((property as CallableReference).owner as KClass<*>).javaObjectType
                else -> property.javaGetter!!.declaringClass
            }
        }
    }
}

@CordaSerializable
sealed class ColumnPredicate<C> {
    data class EqualityComparison<C>(val operator: EqualityComparisonOperator, val rightLiteral: C) : ColumnPredicate<C>()
    data class BinaryComparison<C : Comparable<C>>(val operator: BinaryComparisonOperator, val rightLiteral: C) : ColumnPredicate<C>()
    data class Likeness(val operator: LikenessOperator, val rightLiteral: String) : ColumnPredicate<String>()
    data class CollectionExpression<C>(val operator: CollectionOperator, val rightLiteral: Collection<C>) : ColumnPredicate<C>()
    data class Between<C : Comparable<C>>(val rightFromLiteral: C, val rightToLiteral: C) : ColumnPredicate<C>()
    data class NullExpression<C>(val operator: NullOperator) : ColumnPredicate<C>()
    data class AggregateFunction<C>(val type: AggregateFunctionType) : ColumnPredicate<C>()
}

fun <O, R> resolveEnclosingObjectFromExpression(expression: CriteriaExpression<O, R>): Class<O> {
    return when (expression) {
        is CriteriaExpression.BinaryLogical -> resolveEnclosingObjectFromExpression(expression.left)
        is CriteriaExpression.Not -> resolveEnclosingObjectFromExpression(expression.expression)
        is CriteriaExpression.ColumnPredicateExpression<O, *> -> resolveEnclosingObjectFromColumn(expression.column)
        is CriteriaExpression.AggregateFunctionExpression<O, *> -> resolveEnclosingObjectFromColumn(expression.column)
    }
}

fun <O, C> resolveEnclosingObjectFromColumn(column: Column<O, C>): Class<O> = uncheckedCast(column.declaringClass)
fun <O, C> getColumnName(column: Column<O, C>): String = column.name

/**
 *  Pagination and Ordering
 *
 *  Provide simple ability to specify an offset within a result set and the number of results to
 *  return from that offset (eg. page size) together with (optional) sorting criteria at column level.
 *
 *  Note: it is the responsibility of the calling client to manage page windows.
 *
 *  For advanced pagination it is recommended you utilise standard JPA query frameworks such as
 *  Spring Data's JPARepository which extends the [PagingAndSortingRepository] interface to provide
 *  paging and sorting capability:
 *  https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
 */
const val DEFAULT_PAGE_NUM = 1
const val DEFAULT_PAGE_SIZE = 200

/**
 * Note: use [PageSpecification] to correctly handle a number of bounded pages of a pre-configured page size.
 */
// Here we subtract 1 to allow the Vault to figure out whether there are more results and pages by querying for `pageSize + 1`.
const val MAX_PAGE_SIZE = Int.MAX_VALUE - 1

/**
 * [PageSpecification] allows specification of a page number (starting from [DEFAULT_PAGE_NUM]) and page size
 * (defaulting to [DEFAULT_PAGE_SIZE] with a maximum page size of [MAX_PAGE_SIZE])
 * Note: we default the page number to [DEFAULT_PAGE_SIZE] to enable queries without requiring a page specification
 * but enabling detection of large results sets that fall out of the [DEFAULT_PAGE_SIZE] requirement.
 * [MAX_PAGE_SIZE] should be used with extreme caution as results may exceed your JVM memory footprint.
 */
@CordaSerializable
data class PageSpecification(val pageNumber: Int = -1, val pageSize: Int = DEFAULT_PAGE_SIZE) {
    val isDefault = (pageSize == DEFAULT_PAGE_SIZE && pageNumber == -1)
}

abstract class BaseSort

/**
 * Sort allows specification of a set of entity attribute names and their associated directionality
 * and null handling, to be applied upon processing a query specification.
 */
@CordaSerializable
data class Sort(val columns: Collection<SortColumn>) : BaseSort() {
    @CordaSerializable
    enum class Direction {
        ASC,
        DESC
    }

    @CordaSerializable
    @DoNotImplement
    interface Attribute

    @CordaSerializable
    data class SortColumn(
            val sortAttribute: SortAttribute,
            val direction: Direction = Direction.ASC)
}

@CordaSerializable
data class AttachmentSort(val columns: Collection<AttachmentSortColumn>) : BaseSort() {

    enum class AttachmentSortAttribute(val columnName: String) {
        INSERTION_DATE("insertionDate"),
        UPLOADER("uploader"),
        FILENAME("filename"),
        VERSION ("version")
    }

    @CordaSerializable
    data class AttachmentSortColumn(
            val sortAttribute: AttachmentSortAttribute,
            val direction: Sort.Direction = Sort.Direction.ASC)
}

@CordaSerializable
sealed class SortAttribute {
    /**
     * [sortAttribute] refers to common table attributes defined in the node schemas:
     * VaultState, VaultLinearStates, VaultFungibleStates
     */
    data class Standard(val attribute: Sort.Attribute) : SortAttribute()

    /**
     * [entityStateClass] should reference a persistent state entity
     * [entityStateColumnName] should reference an entity attribute name as defined by the associated mapped schema
     * (for example, [CashSchemaV1.PersistentCashState::currency.name])
     */
    data class Custom(val entityStateClass: Class<out StatePersistable>,
                      val entityStateColumnName: String) : SortAttribute()
}

@CordaSerializable
@Suppress("TooManyFunctions")
object Builder {

    fun <R : Comparable<R>> compare(operator: BinaryComparisonOperator, value: R) = ColumnPredicate.BinaryComparison(operator, value)
    fun <O, R> KProperty1<O, R?>.predicate(predicate: ColumnPredicate<R>) = CriteriaExpression.ColumnPredicateExpression(Column(this), predicate)

    fun <R> FieldInfo.predicate(predicate: ColumnPredicate<R>) = CriteriaExpression.ColumnPredicateExpression(Column<Any, R>(this), predicate)

    fun <O, R> KProperty1<O, R?>.functionPredicate(predicate: ColumnPredicate<R>, groupByColumns: List<Column<O, R>>? = null, orderBy: Sort.Direction? = null)
            = CriteriaExpression.AggregateFunctionExpression(Column(this), predicate, groupByColumns, orderBy)

    fun <R> FieldInfo.functionPredicate(predicate: ColumnPredicate<R>, groupByColumns: List<Column<Any, R>>? = null, orderBy: Sort.Direction? = null)
            = CriteriaExpression.AggregateFunctionExpression(Column(this), predicate, groupByColumns, orderBy)

    fun <O, R : Comparable<R>> KProperty1<O, R?>.comparePredicate(operator: BinaryComparisonOperator, value: R) = predicate(compare(operator, value))
    fun <R : Comparable<R>> FieldInfo.comparePredicate(operator: BinaryComparisonOperator, value: R) = predicate(compare(operator, value))

    @JvmOverloads
    fun <O, R> KProperty1<O, R?>.equal(value: R, exactMatch: Boolean = true) = predicate(Builder.equal(value, exactMatch))

    @JvmOverloads
    fun <O, R> KProperty1<O, R?>.notEqual(value: R, exactMatch: Boolean = true) = predicate(Builder.notEqual(value, exactMatch))

    fun <O, R : Comparable<R>> KProperty1<O, R?>.lessThan(value: R) = comparePredicate(BinaryComparisonOperator.LESS_THAN, value)

    fun <O, R : Comparable<R>> KProperty1<O, R?>.lessThanOrEqual(value: R) = comparePredicate(BinaryComparisonOperator.LESS_THAN_OR_EQUAL, value)

    fun <O, R : Comparable<R>> KProperty1<O, R?>.greaterThan(value: R) = comparePredicate(BinaryComparisonOperator.GREATER_THAN, value)

    fun <O, R : Comparable<R>> KProperty1<O, R?>.greaterThanOrEqual(value: R) = comparePredicate(BinaryComparisonOperator.GREATER_THAN_OR_EQUAL, value)

    fun <O, R : Comparable<R>> KProperty1<O, R?>.between(from: R, to: R) = predicate(ColumnPredicate.Between(from, to))

    @JvmOverloads
    fun <O, R : Comparable<R>> KProperty1<O, R?>.`in`(collection: Collection<R>, exactMatch: Boolean = true) = predicate(Builder.`in`(collection, exactMatch))

    @JvmOverloads
    fun <O, R : Comparable<R>> KProperty1<O, R?>.notIn(collection: Collection<R>, exactMatch: Boolean = true) = predicate(Builder.notIn(collection, exactMatch))

    @JvmStatic
    @JvmOverloads
    fun <R> FieldInfo.equal(value: R, exactMatch: Boolean = true) = predicate(Builder.equal(value, exactMatch))

    @JvmStatic
    @JvmOverloads
    fun <R> FieldInfo.notEqual(value: R, exactMatch: Boolean = true) = predicate(Builder.notEqual(value, exactMatch))

    @JvmStatic
    fun <R : Comparable<R>> FieldInfo.lessThan(value: R) = comparePredicate(BinaryComparisonOperator.LESS_THAN, value)

    @JvmStatic
    fun <R : Comparable<R>> FieldInfo.lessThanOrEqual(value: R) = comparePredicate(BinaryComparisonOperator.LESS_THAN_OR_EQUAL, value)

    @JvmStatic
    fun <R : Comparable<R>> FieldInfo.greaterThan(value: R) = comparePredicate(BinaryComparisonOperator.GREATER_THAN, value)

    @JvmStatic
    fun <R : Comparable<R>> FieldInfo.greaterThanOrEqual(value: R) = comparePredicate(BinaryComparisonOperator.GREATER_THAN_OR_EQUAL, value)

    @JvmStatic
    fun <R : Comparable<R>> FieldInfo.between(from: R, to: R) = predicate(ColumnPredicate.Between(from, to))

    @JvmStatic
    @JvmOverloads
    fun <R : Comparable<R>> FieldInfo.`in`(collection: Collection<R>, exactMatch: Boolean = true) = predicate(Builder.`in`(collection, exactMatch))

    @JvmStatic
    @JvmOverloads
    fun <R : Comparable<R>> FieldInfo.notIn(collection: Collection<R>, exactMatch: Boolean = true) = predicate(Builder.notIn(collection, exactMatch))

    @JvmOverloads
    fun <R> equal(value: R, exactMatch: Boolean = true) = ColumnPredicate.EqualityComparison(
        if (exactMatch) EqualityComparisonOperator.EQUAL else EqualityComparisonOperator.EQUAL_IGNORE_CASE,
        value
    )

    @JvmOverloads
    fun <R> notEqual(value: R, exactMatch: Boolean = true) = ColumnPredicate.EqualityComparison(
        if (exactMatch) EqualityComparisonOperator.NOT_EQUAL else EqualityComparisonOperator.NOT_EQUAL_IGNORE_CASE,
        value
    )

    fun <R : Comparable<R>> lessThan(value: R) = compare(BinaryComparisonOperator.LESS_THAN, value)

    fun <R : Comparable<R>> lessThanOrEqual(value: R) = compare(BinaryComparisonOperator.LESS_THAN_OR_EQUAL, value)

    fun <R : Comparable<R>> greaterThan(value: R) = compare(BinaryComparisonOperator.GREATER_THAN, value)

    fun <R : Comparable<R>> greaterThanOrEqual(value: R) = compare(BinaryComparisonOperator.GREATER_THAN_OR_EQUAL, value)

    fun <R : Comparable<R>> between(from: R, to: R) = ColumnPredicate.Between(from, to)

    @JvmOverloads
    fun <R : Comparable<R>> `in`(collection: Collection<R>, exactMatch: Boolean = true) =
        ColumnPredicate.CollectionExpression(if (exactMatch) CollectionOperator.IN else CollectionOperator.IN_IGNORE_CASE, collection)

    @JvmOverloads
    fun <R : Comparable<R>> notIn(collection: Collection<R>, exactMatch: Boolean = true) = ColumnPredicate.CollectionExpression(
        if (exactMatch) CollectionOperator.NOT_IN else CollectionOperator.NOT_IN_IGNORE_CASE,
        collection
    )

    @JvmOverloads
    fun like(string: String, exactMatch: Boolean = true) =
        ColumnPredicate.Likeness(if (exactMatch) LikenessOperator.LIKE else LikenessOperator.LIKE_IGNORE_CASE, string)

    @JvmOverloads
    fun notLike(string: String, exactMatch: Boolean = true) =
        ColumnPredicate.Likeness(if (exactMatch) LikenessOperator.NOT_LIKE else LikenessOperator.NOT_LIKE_IGNORE_CASE, string)

    fun <R> isNull() = ColumnPredicate.NullExpression<R>(NullOperator.IS_NULL)
    fun <R> isNotNull() = ColumnPredicate.NullExpression<R>(NullOperator.NOT_NULL)

    @JvmOverloads
    fun <O> KProperty1<O, String?>.like(string: String, exactMatch: Boolean = true) = predicate(Builder.like(string, exactMatch))

    @JvmStatic
    @JvmOverloads
    fun FieldInfo.like(string: String, exactMatch: Boolean = true) = predicate(Builder.like(string, exactMatch))

    @JvmOverloads
    fun <O> KProperty1<O, String?>.notLike(string: String, exactMatch: Boolean = true) = predicate(Builder.notLike(string, exactMatch))

    @JvmStatic
    @JvmOverloads
    fun FieldInfo.notLike(string: String, exactMatch: Boolean = true) = predicate(Builder.notLike(string, exactMatch))

    fun <O, R> KProperty1<O, R?>.isNull() = predicate(ColumnPredicate.NullExpression(NullOperator.IS_NULL))
    @JvmStatic
    fun FieldInfo.isNull() = predicate(ColumnPredicate.NullExpression<Any>(NullOperator.IS_NULL))

    fun <O, R> KProperty1<O, R?>.notNull() = predicate(ColumnPredicate.NullExpression(NullOperator.NOT_NULL))
    @JvmStatic
    fun FieldInfo.notNull() = predicate(ColumnPredicate.NullExpression<Any>(NullOperator.NOT_NULL))

    /** aggregate functions */
    fun <O, R> KProperty1<O, R?>.sum(groupByColumns: List<KProperty1<O, R>>? = null, orderBy: Sort.Direction? = null) =
        functionPredicate(ColumnPredicate.AggregateFunction(AggregateFunctionType.SUM), groupByColumns?.map { Column(it) }, orderBy)

    @JvmStatic
    @JvmOverloads
    fun <R> FieldInfo.sum(groupByColumns: List<FieldInfo>? = null, orderBy: Sort.Direction? = null) =
        functionPredicate(ColumnPredicate.AggregateFunction(AggregateFunctionType.SUM), groupByColumns?.map { Column<Any, R>(it) }, orderBy)

    fun <O, R> KProperty1<O, R?>.count() = functionPredicate(ColumnPredicate.AggregateFunction(AggregateFunctionType.COUNT))
    @JvmStatic
    fun FieldInfo.count() = functionPredicate(ColumnPredicate.AggregateFunction<Any>(AggregateFunctionType.COUNT))

    fun <O, R> KProperty1<O, R?>.avg(groupByColumns: List<KProperty1<O, R>>? = null, orderBy: Sort.Direction? = null) =
        functionPredicate(ColumnPredicate.AggregateFunction(AggregateFunctionType.AVG), groupByColumns?.map { Column(it) }, orderBy)

    @JvmStatic
    @JvmOverloads
    fun <R> FieldInfo.avg(groupByColumns: List<FieldInfo>? = null, orderBy: Sort.Direction? = null) =
        functionPredicate(ColumnPredicate.AggregateFunction(AggregateFunctionType.AVG), groupByColumns?.map { Column<Any, R>(it) }, orderBy)

    fun <O, R> KProperty1<O, R?>.min(groupByColumns: List<KProperty1<O, R>>? = null, orderBy: Sort.Direction? = null) =
        functionPredicate(ColumnPredicate.AggregateFunction(AggregateFunctionType.MIN), groupByColumns?.map { Column(it) }, orderBy)

    @JvmStatic
    @JvmOverloads
    fun <R> FieldInfo.min(groupByColumns: List<FieldInfo>? = null, orderBy: Sort.Direction? = null) =
        functionPredicate(ColumnPredicate.AggregateFunction(AggregateFunctionType.MIN), groupByColumns?.map { Column<Any, R>(it) }, orderBy)

    fun <O, R> KProperty1<O, R?>.max(groupByColumns: List<KProperty1<O, R>>? = null, orderBy: Sort.Direction? = null) =
        functionPredicate(ColumnPredicate.AggregateFunction(AggregateFunctionType.MAX), groupByColumns?.map { Column(it) }, orderBy)

    @JvmStatic
    @JvmOverloads
    fun <R> FieldInfo.max(groupByColumns: List<FieldInfo>? = null, orderBy: Sort.Direction? = null) =
        functionPredicate(ColumnPredicate.AggregateFunction(AggregateFunctionType.MAX), groupByColumns?.map { Column<Any, R>(it) }, orderBy)

    private fun Field.info(): FieldInfo = FieldInfo(name, declaringClass)
}

inline fun <A> builder(block: Builder.() -> A) = block(Builder)

/**
 * Contains information about a field from an entity class.
 * Used as part of query criteria construction through [Builder], produced by function [getFieldInfo].
 * The constructor should not be invoked manually.
 *
 * @param name field name
 * @param entityClass JPA entity class for the query
 */
class FieldInfo internal constructor(val name: String, val entityClass: Class<*>)

/**
 * Returns a [FieldInfo] for field with name [fieldName] in [entityClass].
 *
 * @param fieldName name of the field
 * @param entityClass JPA entity class containing the field
 * @throws NoSuchFieldException if no field with name [fieldName] is found in the class hierarchy of [entityClass]
 */
@Throws(NoSuchFieldException::class)
fun getFieldInfo(fieldName: String, entityClass: Class<*>): FieldInfo {
    val field = getField(fieldName, entityClass)
    return FieldInfo(field.name, entityClass)
}

/**
 * Returns a [Field] for field with name [fieldName] in [entityClass].
 *
 * @param fieldName name of the field
 * @param entityClass JPA entity class containing the field
 * @throws NoSuchFieldException if no field with name [fieldName] is found in the class hierarchy of [entityClass]
 */
@Throws(NoSuchFieldException::class)
fun getField(fieldName: String, entityClass: Class<*>?): Field {
    if (entityClass == null) {
        throw NoSuchFieldException(fieldName)
    }

    return try {
        entityClass.getDeclaredField(fieldName)
    } catch (e: NoSuchFieldException) {
        getField(fieldName, entityClass.superclass)
    }
}