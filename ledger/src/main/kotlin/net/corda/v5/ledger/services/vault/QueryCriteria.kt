@file:JvmName("QueryCriteria")

package net.corda.v5.ledger.services.vault

import java.security.PublicKey
import java.time.Instant
import javax.persistence.criteria.Predicate
import net.corda.v5.base.annotations.CordaSerializable
import net.corda.v5.ledger.contracts.ContractClassName

interface GenericQueryCriteria<Q : GenericQueryCriteria<Q, *>, in P : BaseQueryCriteriaParser<Q, *, *>> {
    fun visit(parser: P): Collection<Predicate>

    interface ChainableQueryCriteria<Q : GenericQueryCriteria<Q, P>, in P : BaseQueryCriteriaParser<Q, P, *>> {

        interface AndVisitor<Q : GenericQueryCriteria<Q, P>, in P : BaseQueryCriteriaParser<Q, P, S>, in S : BaseSort> : GenericQueryCriteria<Q, P> {
            val a:Q
            val b:Q
            override fun visit(parser: P): Collection<Predicate> {
                return parser.parseAnd(this.a, this.b)
            }
        }

        interface OrVisitor<Q : GenericQueryCriteria<Q, P>, in P : BaseQueryCriteriaParser<Q, P, S>, in S : BaseSort> : GenericQueryCriteria<Q, P> {
            val a:Q
            val b:Q
            override fun visit(parser: P): Collection<Predicate> {
                return parser.parseOr(this.a, this.b)
            }
        }

        infix fun and(criteria: Q): Q
        infix fun or(criteria: Q): Q
    }
}

@CordaSerializable
sealed class AttachmentQueryCriteria : GenericQueryCriteria<AttachmentQueryCriteria, AttachmentsQueryCriteriaParser>, GenericQueryCriteria.ChainableQueryCriteria<AttachmentQueryCriteria, AttachmentsQueryCriteriaParser> {
    /**
     * AttachmentsQueryCriteria:
     */
    @Suppress("MagicNumber") // need to list deprecation versions explicitly
    data class AttachmentsQueryCriteria(val uploaderCondition: ColumnPredicate<String>? = null,
                                        val filenameCondition: ColumnPredicate<String>? = null,
                                        val uploadDateCondition: ColumnPredicate<Instant>? = null,
                                        val contractClassNamesCondition: ColumnPredicate<List<ContractClassName>>? = null,
                                        val signersCondition: ColumnPredicate<List<PublicKey>>? = null,
                                        val isSignedCondition: ColumnPredicate<Boolean>? = null,
                                        val versionCondition: ColumnPredicate<Int>? = null) : AttachmentQueryCriteria() {

        constructor(uploaderCondition: ColumnPredicate<String>? = null,
                    filenameCondition: ColumnPredicate<String>? = null,
                    uploadDateCondition: ColumnPredicate<Instant>? = null) : this(uploaderCondition, filenameCondition, uploadDateCondition, null)

        constructor(uploaderCondition: ColumnPredicate<String>?) : this(uploaderCondition, null)
        constructor(uploaderCondition: ColumnPredicate<String>?, filenameCondition: ColumnPredicate<String>?) : this(uploaderCondition, filenameCondition, null)

        override fun visit(parser: AttachmentsQueryCriteriaParser): Collection<Predicate> {
            return parser.parseCriteria(this)
        }

        fun copy(
                uploaderCondition: ColumnPredicate<String>? = this.uploaderCondition,
                filenameCondition: ColumnPredicate<String>? = this.filenameCondition,
                uploadDateCondition: ColumnPredicate<Instant>? = this.uploadDateCondition
        ): AttachmentsQueryCriteria {
            return AttachmentsQueryCriteria(
                    uploaderCondition,
                    filenameCondition,
                    uploadDateCondition,
                    contractClassNamesCondition,
                    signersCondition,
                    isSignedCondition,
                    versionCondition
            )
        }

        fun withUploader(uploaderPredicate: ColumnPredicate<String>): AttachmentsQueryCriteria = copy(uploaderCondition = uploaderPredicate)
        fun withFilename(filenamePredicate: ColumnPredicate<String>): AttachmentsQueryCriteria = copy(filenameCondition = filenamePredicate)
        fun withUploadDate(uploadDatePredicate: ColumnPredicate<Instant>): AttachmentsQueryCriteria = copy(uploadDateCondition = uploadDatePredicate)
        fun withContractClassNames(contractClassNamesPredicate: ColumnPredicate<List<ContractClassName>>): AttachmentsQueryCriteria = copy(contractClassNamesCondition = contractClassNamesPredicate)
        fun withSigners(signersPredicate: ColumnPredicate<List<PublicKey>>): AttachmentsQueryCriteria = copy(signersCondition = signersPredicate)
        fun isSigned(isSignedPredicate: ColumnPredicate<Boolean>): AttachmentsQueryCriteria = copy(isSignedCondition = isSignedPredicate)
        fun withVersion(versionPredicate: ColumnPredicate<Int>): AttachmentsQueryCriteria = copy(versionCondition = versionPredicate)
    }

    class AndComposition(override val a: AttachmentQueryCriteria, override val b: AttachmentQueryCriteria): AttachmentQueryCriteria(), GenericQueryCriteria.ChainableQueryCriteria.AndVisitor<AttachmentQueryCriteria, AttachmentsQueryCriteriaParser, AttachmentSort>
    class OrComposition(override val a: AttachmentQueryCriteria, override val b: AttachmentQueryCriteria): AttachmentQueryCriteria(), GenericQueryCriteria.ChainableQueryCriteria.OrVisitor<AttachmentQueryCriteria, AttachmentsQueryCriteriaParser, AttachmentSort>

    override fun and(criteria: AttachmentQueryCriteria): AttachmentQueryCriteria = AndComposition(this, criteria)
    override fun or(criteria: AttachmentQueryCriteria): AttachmentQueryCriteria = OrComposition(this, criteria)
}

interface BaseQueryCriteriaParser<Q: GenericQueryCriteria<Q, P>, in P: BaseQueryCriteriaParser<Q, P, S>, in S : BaseSort> {
    fun parseOr(left: Q, right: Q): Collection<Predicate>
    fun parseAnd(left: Q, right: Q): Collection<Predicate>
    fun parse(criteria: Q, sorting: S? = null): Collection<Predicate>
}

interface AttachmentsQueryCriteriaParser : BaseQueryCriteriaParser<AttachmentQueryCriteria, AttachmentsQueryCriteriaParser, AttachmentSort> {
    fun parseCriteria(criteria: AttachmentQueryCriteria.AttachmentsQueryCriteria): Collection<Predicate>
}
