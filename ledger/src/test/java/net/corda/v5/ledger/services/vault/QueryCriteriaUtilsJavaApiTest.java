package net.corda.v5.ledger.services.vault;

import net.corda.v5.ledger.schemas.StatePersistable;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.mock;

public class QueryCriteriaUtilsJavaApiTest {

    private final CriteriaExpression<Object, Boolean> criteriaExpression = mock(CriteriaExpression.class);
    private final Column<Object, Object> column = new Column<>("column", String.class);
    private final ColumnPredicate.EqualityComparison<Object> predicate = new ColumnPredicate.EqualityComparison<>(EqualityComparisonOperator.EQUAL, true);
    private final CriteriaExpression.ColumnPredicateExpression<Object, Object> columnPredicateExpression =
            new CriteriaExpression.ColumnPredicateExpression<>(column, predicate);
    private final Sort.Attribute attribute = mock(Sort.Attribute.class);
    private final SortAttribute.Standard standard = new SortAttribute.Standard(attribute);
    private final String value = "value";
    private final FieldInfo fieldInfo = new FieldInfo(value, String.class);

    @Nested
    public class CriteriaExpressionJavaApiTest {

        @Nested
        public class BinaryLogicalJavaApiTest {
            private final CriteriaExpression.BinaryLogical<Object> binaryLogical =
                    new CriteriaExpression.BinaryLogical<>(criteriaExpression, criteriaExpression, BinaryLogicalOperator.AND);

            @Test
            public void getLeft() {
                CriteriaExpression<Object, Boolean> result = binaryLogical.getLeft();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(criteriaExpression);
            }

            @Test
            public void getRight() {
                CriteriaExpression<Object, Boolean> result = binaryLogical.getRight();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(criteriaExpression);
            }

            @Test
            public void getOperator() {
                BinaryLogicalOperator result = binaryLogical.getOperator();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(BinaryLogicalOperator.AND);
            }
        }

        @Nested
        public class NotJavaApiTest {
            private final CriteriaExpression.Not<Object> not =
                    new CriteriaExpression.Not<>(criteriaExpression);

            @Test
            public void getExpression() {
                CriteriaExpression<Object, Boolean> result = not.getExpression();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(criteriaExpression);
            }
        }

        @Nested
        public class ColumnPredicateExpressionJavaApiTest {

            @Test
            public void getColumn() {
                Column<Object, Object> result = columnPredicateExpression.getColumn();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(column);
            }

            @Test
            public void getPredicate() {
                ColumnPredicate<Object> result = columnPredicateExpression.getPredicate();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(predicate);
            }
        }

        @Nested
        public class AggregateFunctionExpressionJavaApiTest {
            private final CriteriaExpression.AggregateFunctionExpression<Object, Object> aggregateFunctionExpression =
                    new CriteriaExpression.AggregateFunctionExpression<>(column, predicate, List.of(column), Sort.Direction.ASC);

            @Test
            public void getColumn() {
                Column<Object, Object> result = aggregateFunctionExpression.getColumn();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(column);
            }

            @Test
            public void getPredicate() {
                ColumnPredicate<Object> result = aggregateFunctionExpression.getPredicate();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(predicate);
            }

            @Test
            public void getGroupByColumns() {
                List<Column<Object, Object>> result = aggregateFunctionExpression.getGroupByColumns();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(List.of(column));
            }

            @Test
            public void getOrderBy() {
                Sort.Direction result = aggregateFunctionExpression.getOrderBy();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(Sort.Direction.ASC);
            }
        }
    }

    @Nested
    public class ColumnJavaApiTest {

        private final Column<Object, Object> column = new Column<>("column", String.class);

        @Test
        public void getName() {
            String result = column.getName();

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isEqualTo("column");
        }

        @Test
        public void getDeclaringClass() {
            Class<?> result = column.getDeclaringClass();

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isEqualTo(String.class);
        }
    }

    @Nested
    public class ColumnPredicateJavaApiTest {

        @Nested
        public class EqualityComparisonJavaApiTest {
            private final ColumnPredicate.EqualityComparison<String> equalityComparison =
                    new ColumnPredicate.EqualityComparison<>(EqualityComparisonOperator.EQUAL, "rightLiteral");

            @Test
            public void getOperator() {
                EqualityComparisonOperator result = equalityComparison.getOperator();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(EqualityComparisonOperator.EQUAL);
            }

            @Test
            public void getRightLiteral() {
                String result = equalityComparison.getRightLiteral();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo("rightLiteral");
            }
        }

        @Nested
        public class BinaryComparisonJavaApiTest {
            private final ColumnPredicate.BinaryComparison<String> binaryComparison =
                    new ColumnPredicate.BinaryComparison<>(BinaryComparisonOperator.LESS_THAN, "rightLiteral");

            @Test
            public void getOperator() {
                BinaryComparisonOperator result = binaryComparison.getOperator();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(BinaryComparisonOperator.LESS_THAN);
            }

            @Test
            public void getRightLiteral() {
                String result = binaryComparison.getRightLiteral();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo("rightLiteral");
            }
        }

        @Nested
        public class LikenessJavaApiTest {
            private final ColumnPredicate.Likeness likeness =
                    new ColumnPredicate.Likeness(LikenessOperator.NOT_LIKE, "rightLiteral");

            @Test
            public void getOperator() {
                LikenessOperator result = likeness.getOperator();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(LikenessOperator.NOT_LIKE);
            }

            @Test
            public void getRightLiteral() {
                String result = likeness.getRightLiteral();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo("rightLiteral");
            }
        }

        @Nested
        public class CollectionExpressionJavaApiTest {
            private final ColumnPredicate.CollectionExpression<String> collectionExpression =
                    new ColumnPredicate.CollectionExpression<>(CollectionOperator.IN, Collections.singletonList("rightLiteral"));

            @Test
            public void getOperator() {
                CollectionOperator result = collectionExpression.getOperator();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(CollectionOperator.IN);
            }

            @Test
            public void getRightLiteral() {
                Collection<String> result = collectionExpression.getRightLiteral();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(Collections.singletonList("rightLiteral"));
            }
        }

        @Nested
        public class BetweenJavaApiTest {
            private final ColumnPredicate.Between<String> between =
                    new ColumnPredicate.Between<>("rightFromLiteral", "rightToLiteral");

            @Test
            public void getRightFromLiteral() {
                String result = between.getRightFromLiteral();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo("rightFromLiteral");
            }

            @Test
            public void getRightToLiteral() {
                String result = between.getRightToLiteral();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo("rightToLiteral");
            }
        }

        @Nested
        public class NullExpressionJavaApiTest {
            private final ColumnPredicate.NullExpression<String> nullExpression =
                    new ColumnPredicate.NullExpression<>(NullOperator.IS_NULL);

            @Test
            public void getOperator() {
                NullOperator result = nullExpression.getOperator();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(NullOperator.IS_NULL);
            }
        }

        @Nested
        public class AggregateFunctionJavaApiTest {
            private final ColumnPredicate.AggregateFunction<String> aggregateFunction =
                    new ColumnPredicate.AggregateFunction<>(AggregateFunctionType.MAX);

            @Test
            public void getType() {
                AggregateFunctionType result = aggregateFunction.getType();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(AggregateFunctionType.MAX);
            }
        }
    }

    @Test
    public void resolveEnclosingObjectFromExpression() {
        Class<Object> result = QueryCriteriaUtils.resolveEnclosingObjectFromExpression(columnPredicateExpression);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void resolveEnclosingObjectFromColumn() {
        Class<Object> result = QueryCriteriaUtils.resolveEnclosingObjectFromColumn(column);

        Assertions.assertThat(result).isNotNull();
    }

    @Test
    public void getColumnName() {
        String result = QueryCriteriaUtils.getColumnName(column);

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo("column");
    }

    @Nested
    public class PageSpecificationJavaApiTest {

        private final PageSpecification pageSpecification = new PageSpecification(1, 1);

        @Test
        public void getPageNumber() {
            int result = pageSpecification.getPageNumber();

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isEqualTo(1);
        }

        @Test
        public void getPageSize() {
            int result = pageSpecification.getPageSize();

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isEqualTo(1);
        }

        @Test
        public void isDefault() {
            Boolean result = pageSpecification.isDefault();

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isEqualTo(false);
        }
    }

    @Nested
    public class SortJavaApiTest {
        private final Sort.SortColumn sortColumn = new Sort.SortColumn(standard, Sort.Direction.ASC);
        private final Sort sort = new Sort(Collections.singletonList(sortColumn));

        @Test
        public void getColumns() {
            Collection<Sort.SortColumn> result = sort.getColumns();

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isEqualTo(Collections.singletonList(sortColumn));
        }

        @Nested
        public class SortColumnJavaApiTest {

            @Test
            public void getSortAttribute() {
                SortAttribute result = sortColumn.getSortAttribute();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(standard);
            }

            @Test
            public void getDirection() {
                Sort.Direction result = sortColumn.getDirection();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(Sort.Direction.ASC);
            }
        }
    }

    @Nested
    public class AttachmentSortJavaApiTest {
        private final AttachmentSort.AttachmentSortColumn attachmentSortColumn =
                new AttachmentSort.AttachmentSortColumn(AttachmentSort.AttachmentSortAttribute.VERSION, Sort.Direction.ASC);
        private final AttachmentSort attachmentSort = new AttachmentSort(Collections.singletonList(attachmentSortColumn));

        @Test
        public void getColumns() {
            var result = attachmentSort.getColumns();

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isEqualTo(Collections.singletonList(attachmentSortColumn));
        }

        @Nested
        public class AttachmentSortColumnJavaApiTest {

            @Test
            public void getSortAttribute() {
                var result = attachmentSortColumn.getSortAttribute();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(AttachmentSort.AttachmentSortAttribute.VERSION);
            }

            @Test
            public void getColumns() {
                var result = attachmentSortColumn.getDirection();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(Sort.Direction.ASC);
            }
        }
    }

    @Nested
    public class SortAttributeJavaApiTest {

        @Nested
        public class StandardJavaApiTest {

            @Test
            public void getAttribute() {
                var result = standard.getAttribute();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(attribute);
            }
        }

        @Nested
        public class CustomJavaApiTest {

            private final SortAttribute.Custom custom = new SortAttribute.Custom(StatePersistable.class, "entityStateColumnName");

            @Test
            public void getEntityStateClass() {
                var result = custom.getEntityStateClass();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo(StatePersistable.class);
            }

            @Test
            public void getEntityStateColumnName() {
                var result = custom.getEntityStateColumnName();

                Assertions.assertThat(result).isNotNull();
                Assertions.assertThat(result).isEqualTo("entityStateColumnName");
            }
        }
    }

    @Nested
    public class BuilderJavaApiTest {

        @Test
        public void equal() {
            var result = Builder.equal(fieldInfo, value, true);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void notEqual() {
            var result = Builder.notEqual(fieldInfo, value, true);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void lessThan() {
            var result = Builder.lessThan(fieldInfo, value);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void lessThanOrEqual() {
            var result = Builder.lessThanOrEqual(fieldInfo, value);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void greaterThan() {
            var result = Builder.greaterThan(fieldInfo, value);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void greaterThanOrEqual() {
            var result = Builder.greaterThanOrEqual(fieldInfo, value);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void between() {
            String valueFrom = "fromTest";
            String valueTo = "fromTest";
            FieldInfo fieldInfoMock = new FieldInfo(valueFrom, String.class);
            var result = Builder.between(fieldInfoMock, valueFrom, valueTo);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void in() {
            var result = Builder.in(fieldInfo, Collections.singletonList(value), true);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void notIn() {
            var result = Builder.notIn(fieldInfo, Collections.singletonList(value), true);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void like() {
            var result = Builder.like(fieldInfo, value, true);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void notLike() {
            var result = Builder.notLike(fieldInfo, value, true);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void isNull() {
            var result = Builder.isNull(fieldInfo);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void notNull() {
            var result = Builder.notNull(fieldInfo);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void sum() {
            var result = Builder.sum(fieldInfo, List.of(fieldInfo), Sort.Direction.ASC);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void count() {
            var result = Builder.count(fieldInfo);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void avg() {
            var result = Builder.avg(fieldInfo, List.of(fieldInfo), Sort.Direction.ASC);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void min() {
            var result = Builder.min(fieldInfo, List.of(fieldInfo), Sort.Direction.ASC);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }

        @Test
        public void max() {
            var result = Builder.max(fieldInfo, List.of(fieldInfo), Sort.Direction.ASC);

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isInstanceOf(CriteriaExpression.class);
        }
    }

    @Test
    public void builder() {
        var result = QueryCriteriaUtils.builder(Builder::isNotNull);

        Assertions.assertThat(result).isNotNull();
    }

    @Nested
    public class FieldInfoJavaApiTest {

        @Test
        public void getName() {
            var result = fieldInfo.getName();

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isEqualTo(value);
        }

        @Test
        public void getEntityClass() {
            var result = fieldInfo.getEntityClass();

            Assertions.assertThat(result).isNotNull();
            Assertions.assertThat(result).isEqualTo(String.class);
        }
    }

    @Test
    public void getField() throws NoSuchFieldException {
        var result = QueryCriteriaUtils.getField(value, String.class);

        Assertions.assertThat(result).isNotNull();
    }
}
