@file:JvmName("PersistenceUtils")
package net.corda.v5.application.persistence

import net.corda.v5.base.annotations.DoNotImplement
import net.corda.v5.base.annotations.Suspendable

/**
 * The [PersistenceService] API allows a flow to insert, find, update and delete custom entities in the persistent
 * store provided by the platform.
 */
@DoNotImplement
@Suppress("LongParameterList", "TooManyFunctions")
interface PersistenceService {
    /**
     * Persist a single [entity] to the store.
     *
     * @param entity the entity to persist.
     * @throws CordaPersistenceException if an error happens during persist operation
     */
    @Suspendable
    fun persist(entity: Any)

    /**
     * Persist multiple [entities] in the persistence context in a single transaction.
     *
     * @param entities list of entities to be persisted.
     * @throws CordaPersistenceException if an error happens during persist operation
     */
    @Suspendable
    fun persist(entities: List<Any>)

    /**
     * Merge a single [entity] in the persistence context in a transaction.
     *
     * @param entity the entity to merge.
     * @return the merged entity.
     * @throws CordaPersistenceException if an error happens during merge operation
     */
    @Suspendable
    fun <T : Any> merge(entity: T): T?

    /**
     * Merge multiple [entities] in the persistence context in a single transaction.
     *
     * @param entities list of entities to be merged.
     * @return the list of merged entities.
     * @throws CordaPersistenceException if an error happens during merge operation
     */
    @Suspendable
    fun <T : Any> merge(entities: List<T>): List<T>

    /**
     * Remove a single [entity] from the persistence context in a transaction.
     *
     * @param entity the entity to remove.
     * @throws CordaPersistenceException if an error happens during remove operation
     */
    @Suspendable
    fun remove(entity: Any)

    /**
     * Remove multiple [entities] from the persistence context in a single transaction.
     *
     * @param entities list of entities to be remove.
     * @throws CordaPersistenceException if an error happens during remove operation
     */
    @Suspendable
    fun remove(entities: List<Any>)

    /**
     * Find a single entity in the persistence context given the [entityClass] and [primaryKey] of the entity.
     *
     * @param entityClass the type of entity to find.
     * @param primaryKey the primary key of the entity to find.
     * @return the found entity, null if it could not be found in the persistence context.
     * @throws CordaPersistenceException if an error happens during find operation
     */
    @Suspendable
    fun <T : Any> find(entityClass: Class<T>, primaryKey: Any): T?

    /**
     * Find multiple entities of the same type with different primary keys from the persistence context in a single
     * transaction.
     *
     * @param entityClass the type of the entities to find.
     * @param primaryKeys list of primary keys to find with the given [entityClass] type.
     * @return list of entities found. Empty list if none were found.
     * @throws CordaPersistenceException if an error happens during find operation
     */
    @Suspendable
    fun <T : Any> find(entityClass: Class<T>, primaryKeys: List<Any>): List<T>

    /**
     * Create a [PagedQuery] to find all entities of the same type from the persistence context in a single transaction.
     *
     * @param entityClass the type of the entities to find.
     * @return a [PagedQuery] that returns the list of entities found.
     * @throws CordaPersistenceException if an error happens during find operation
     */
    @Suspendable
    fun <T : Any> findAll(entityClass: Class<T>): PagedQuery<T>

    /**
     * Create a [ParameterisedQuery] to support a named query to return a list of entities of the given type in a single
     * transaction. Casts result set to the specified type [T].
     * Example usage:
     *
     * - Kotlin:
     *
     * ```kotlin
     * class MyFlow : RPCStartableFlow {
     *     // For JPA Entity:
     *     @CordaSerializable
     *     @Entity
     *     @Table(name = "DOGS")
     *     @NamedQuery(name = "find_by_name_and_age", query = "SELECT d FROM Dog d WHERE d.name = :name AND d.age <= :maxAge")
     *     class Dog {
     *         @Id
     *         private val id: UUID? = null
     *
     *         @Column(name = "DOG_NAME", length = 50, nullable = false, unique = false)
     *         private val name: String? = null
     *
     *         @Column(name = "DOG_AGE")
     *         private val age: Int? = null // getters and setters
     *         // ...
     *     }
     *
     *     @CordaInject
     *     lateinit var persistenceService: PersistenceService
     *
     *     override fun call(requestBody: RPCRequestData): String {
     *         // create a named query setting parameters one-by-one, that returns the second page of up to 100 records
     *         val pagedQuery = persistenceService
     *             .query("find_by_name_and_age", Dog::class.java)
     *             .setParameter("name", "Felix")
     *             .setParameter("maxAge", 5)
     *             .setLimit(100)
     *             .setOffset(200)
     *
     *         // execute the query and return the results as a List
     *         val result1 = pagedQuery.execute()
     *
     *         // create a named query setting parameters as Map, that returns the second page of up to 100 records
     *         val paramQuery = persistenceService
     *             .query("find_by_name_and_age", Dog::class.java)
     *             .setParameters(Map.of("name", "Felix", "maxAge", 5))
     *             .setLimit(100)
     *             .setOffset(200)
     *
     *         // execute the query and return the results as a List
     *         val result2 = pagedQuery.execute()
     *     }
     * }
     * ```
     *
     * - Java:
     *
     * ```java
     * public class MyFlow implements RPCStartableFlow {
     *
     *     // For JPA Entity:
     *     @CordaSerializable
     *     @Entity
     *     @Table(name = "DOGS")
     *     @NamedQuery(name = "find_by_name_and_age", query = "SELECT d FROM Dog d WHERE d.name = :name AND d.age <= :maxAge")
     *     class Dog {
     *         @Id
     *         private UUID id;
     *         @Column(name = "DOG_NAME", length = 50, nullable = false, unique = false)
     *         private String name;
     *         @Column(name = "DOG_AGE")
     *         private Integer age;
     *
     *         // getters and setters
     *          ...
     *     }
     *
     *     @CordaInject
     *     public PersistenceService persistenceService;
     *
     *     @Override
     *     public String call(RPCRequestData requestBody) {
     *         // create a named query setting parameters one-by-one, that returns the second page of up to 100 records
     *         ParameterisedQuery<Dog> pagedQuery = persistenceService
     *                 .query("find_by_name_and_age", Dog.class)
     *                 .setParameter("name", "Felix")
     *                 .setParameter("maxAge", 5)
     *                 .setLimit(100)
     *                 .setOffset(200);
     *
     *         // execute the query and return the results as a List
     *         List<Dog> result1 = pagedQuery.execute();
     *
     *         // create a named query setting parameters as Map, that returns the second page of up to 100 records
     *         ParameterisedQuery<Dog> paramQuery = persistenceService
     *                 .query("find_by_name_and_age", Dog.class)
     *                 .setParameters(Map.of("name", "Felix", "maxAge", 5))
     *                 .setLimit(100)
     *                 .setOffset(200);
     *
     *         // execute the query and return the results as a List
     *         List<Dog> result2 = pagedQuery.execute();
     *
     *         .....
     *     }
     * }
     * ```
     * @param queryName the name of the named query registered in the persistence context.
     * @param entityClass the type of the entities to find.
     * @param T the type of the results.
     * @return a [ParameterisedQuery] that returns the list of entities found. Empty list if none were found.
     * @throws CordaPersistenceException if an error happens during query operation
     */
    @Suspendable
    fun <T : Any> query(
        queryName: String,
        entityClass: Class<T>
    ): ParameterisedQuery<T>
}

/**
 * Find a single entity in the persistence context given the entity type [T] and [primaryKey] of the entity.
 *
 * @param primaryKey the primary key of the entity to find.
 * @param T the type of entity to find.
 * @return the found entity, null if it could not be found in the persistence context.
 */
inline fun <reified T : Any> PersistenceService.find(primaryKey: Any): T? = find(T::class.java, primaryKey)

/**
 * Find multiple entities of the same type with different primary keys from the persistence context in a single
 * transaction.
 *
 * @param primaryKeys list of primary keys to find with the given entity type [T].
 * @param T the type of the entities to find.
 * @return list of entities found. Empty list if none were found.
 */
inline fun <reified T : Any> PersistenceService.find(primaryKeys: List<Any>): List<T> = find(T::class.java, primaryKeys)

/**
 * Find all entities of the same type in a single transaction.
 *
 * @param T the type of the entities to find.
 * @return a [PagedQuery] that returns the list of entities found. Empty list if none were found.
 */
inline fun <reified T : Any> PersistenceService.findAll(): PagedQuery<T> = findAll(T::class.java)
