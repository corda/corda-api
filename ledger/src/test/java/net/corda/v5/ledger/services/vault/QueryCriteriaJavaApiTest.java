package net.corda.v5.ledger.services.vault;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.security.PublicKey;
import java.time.Instant;
import java.util.List;

import static org.mockito.Mockito.mock;

public class QueryCriteriaJavaApiTest {

    private final AttachmentsQueryCriteriaParser attachmentsQueryCriteriaParser = mock(AttachmentsQueryCriteriaParser.class);
    private final PublicKey publicKey = mock(PublicKey.class);
    private final List<PublicKey> publicKeyList = List.of(publicKey);
    private final ColumnPredicate.EqualityComparison<String> predicateString =
            new ColumnPredicate.EqualityComparison<>(EqualityComparisonOperator.EQUAL, "rightLiteral");
    private final ColumnPredicate.EqualityComparison<Instant> predicateInstant =
            new ColumnPredicate.EqualityComparison<>(EqualityComparisonOperator.EQUAL, Instant.MAX);
    private final ColumnPredicate.EqualityComparison<Integer> predicateInt =
            new ColumnPredicate.EqualityComparison<>(EqualityComparisonOperator.EQUAL, 1);
    private final ColumnPredicate.EqualityComparison<List<PublicKey>> predicatePublicKeyList =
            new ColumnPredicate.EqualityComparison<>(EqualityComparisonOperator.EQUAL, publicKeyList);
    private final ColumnPredicate.EqualityComparison<Boolean> predicateBoolean =
            new ColumnPredicate.EqualityComparison<>(EqualityComparisonOperator.EQUAL, true);
    private final ColumnPredicate.EqualityComparison<List<String>> predicateContract =
            new ColumnPredicate.EqualityComparison<>(EqualityComparisonOperator.EQUAL, List.of("test"));
    private final ColumnPredicate.EqualityComparison<String> predicateString2 =
            new ColumnPredicate.EqualityComparison<>(EqualityComparisonOperator.NOT_EQUAL, "rightLiteral");
    private final ColumnPredicate.EqualityComparison<Instant> predicateInstant2 =
            new ColumnPredicate.EqualityComparison<>(EqualityComparisonOperator.EQUAL, Instant.MIN);
    private final AttachmentQueryCriteria.AttachmentsQueryCriteria attachmentsQueryCriteria = new AttachmentQueryCriteria.AttachmentsQueryCriteria(
            predicateString,
            predicateString,
            predicateInstant,
            predicateContract,
            predicatePublicKeyList,
            predicateBoolean,
            predicateInt
    );

    @Nested
    public class AttachmentQueryCriteriaJavaApiTest {

        @Nested
        public class AttachmentQueryCriteriaClassJavaApiTest {

            @Test
            public void getUploaderCondition() {
                var result = attachmentsQueryCriteria.getUploaderCondition();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(predicateString);
            }

            @Test
            public void getFilenameCondition() {
                var result = attachmentsQueryCriteria.getFilenameCondition();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(predicateString);
            }

            @Test
            public void getUploadDateCondition() {
                var result = attachmentsQueryCriteria.getUploadDateCondition();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(predicateInstant);
            }

            @Test
            public void getContractClassNamesCondition() {
                var result = attachmentsQueryCriteria.getContractClassNamesCondition();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(predicateContract);
            }

            @Test
            public void getSignersCondition() {
                var result = attachmentsQueryCriteria.getSignersCondition();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(predicatePublicKeyList);
            }

            @Test
            public void isSignedCondition() {
                var result = attachmentsQueryCriteria.isSignedCondition();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(predicateBoolean);
            }

            @Test
            public void getVersionCondition() {
                var result = attachmentsQueryCriteria.getVersionCondition();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(predicateInt);
            }

            @Test
            public void visit() {
                var result = attachmentsQueryCriteria.visit(attachmentsQueryCriteriaParser);

                Assertions.assertThat(result).isNotNull();
            }

            @Test
            public void copy() {
                var result = attachmentsQueryCriteria.copy(
                        predicateString2,
                        predicateString2,
                        predicateInstant2
                );

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.getContractClassNamesCondition()).isEqualTo(predicateContract);
                Assertions.assertThat(result.getFilenameCondition()).isEqualTo(predicateString2);
                Assertions.assertThat(result.getSignersCondition()).isEqualTo(predicatePublicKeyList);
                Assertions.assertThat(result.getUploadDateCondition()).isEqualTo(predicateInstant2);
                Assertions.assertThat(result.getVersionCondition()).isEqualTo(predicateInt);
                Assertions.assertThat(result.getUploaderCondition()).isEqualTo(predicateString2);
                Assertions.assertThat(result.isSignedCondition()).isEqualTo(predicateBoolean);
            }

            @Test
            public void withUploader() {
                var result = attachmentsQueryCriteria.withUploader(predicateString);

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.getUploaderCondition()).isEqualTo(predicateString);
            }

            @Test
            public void withFilename() {
                var result = attachmentsQueryCriteria.withFilename(predicateString);

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.getFilenameCondition()).isEqualTo(predicateString);
            }

            @Test
            public void withUploadDate() {
                var result = attachmentsQueryCriteria.withUploadDate(predicateInstant);

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.getUploadDateCondition()).isEqualTo(predicateInstant);
            }

            @Test
            public void withContractClassNames() {
                var result = attachmentsQueryCriteria.withContractClassNames(predicateContract);

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.getContractClassNamesCondition()).isEqualTo(predicateContract);
            }

            @Test
            public void withSigners() {
                var result = attachmentsQueryCriteria.withSigners(predicatePublicKeyList);

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.getSignersCondition()).isEqualTo(predicatePublicKeyList);
            }

            @Test
            public void isSigned() {
                var result = attachmentsQueryCriteria.isSigned(predicateBoolean);

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.isSignedCondition()).isEqualTo(predicateBoolean);
            }

            @Test
            public void withVersion() {
                var result = attachmentsQueryCriteria.withVersion(predicateInt);

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result.getVersionCondition()).isEqualTo(predicateInt);
            }
        }

        @Nested
        public class AndCompositionJavaApiTest {

            private final AttachmentQueryCriteria.AndComposition andComposition = new AttachmentQueryCriteria.AndComposition(attachmentsQueryCriteria, attachmentsQueryCriteria);

            @Test
            public void getA() {
                var result = andComposition.getA();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(attachmentsQueryCriteria);
            }

            @Test
            public void getB() {
                var result = andComposition.getB();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(attachmentsQueryCriteria);
            }
        }

        @Nested
        public class OrCompositionJavaApiTest {

            private final AttachmentQueryCriteria.OrComposition orComposition = new AttachmentQueryCriteria.OrComposition(attachmentsQueryCriteria, attachmentsQueryCriteria);

            @Test
            public void getA() {
                var result = orComposition.getA();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(attachmentsQueryCriteria);
            }

            @Test
            public void getB() {
                var result = orComposition.getB();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(attachmentsQueryCriteria);
            }
        }

        @Test
        public void and() {
            var result = attachmentsQueryCriteria.and(attachmentsQueryCriteria);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(AttachmentQueryCriteria.AndComposition.class);
        }

        @Test
        public void or() {
            var result = attachmentsQueryCriteria.or(attachmentsQueryCriteria);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(AttachmentQueryCriteria.OrComposition.class);
        }
    }

    @Nested
    public class AttachmentsQueryCriteriaParserJavaApiTest {

        @Test
        public void parseCriteria() {
            var result = attachmentsQueryCriteriaParser.parseCriteria(attachmentsQueryCriteria);

            Assertions.assertThat(result).isNotNull();
        }
    }
}
