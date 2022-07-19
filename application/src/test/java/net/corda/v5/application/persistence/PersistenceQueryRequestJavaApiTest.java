package net.corda.v5.application.persistence;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.mock;

public class PersistenceQueryRequestJavaApiTest {
    final private int offset = 123;
    final private int limit = 456;
    final private PersistenceQueryRequest persistenceQueryRequest =
            new PersistenceQueryRequest("test", Map.of("key", "value"), offset, limit);
    final private PersistenceQueryRequest persistenceQueryRequestDefault =
            new PersistenceQueryRequest("test", Map.of("key", "value"));

    @Test
    public void getQueryName() {
        String result = persistenceQueryRequest.getQueryName();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo("test");
    }

    @Test
    public void getNamedParameters() {
        Map<String, Object> result = persistenceQueryRequest.getNamedParameters();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(Map.of("key", "value"));
    }

    @Test
    public void getOffset() {
        int result = persistenceQueryRequest.getOffset();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(offset);
    }

    @Test
    public void getDefaultOffset() {
        int result = persistenceQueryRequestDefault.getOffset();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(0);
    }

    @Test
    public void getLimit() {
        int result = persistenceQueryRequest.getLimit();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(limit);
    }

    @Test
    public void getDefaultLimit() {
        int result = persistenceQueryRequestDefault.getLimit();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    public void build() {
        PersistenceQueryRequest result = new PersistenceQueryRequest.Builder("test", Map.of()).build();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getQueryName()).isEqualTo("test");
        Assertions.assertThat(result.getNamedParameters()).isEqualTo(Map.of());
        Assertions.assertThat(result.getOffset()).isEqualTo(0);
        Assertions.assertThat(result.getLimit()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    public void withOffset() {
        PersistenceQueryRequest result =
                new PersistenceQueryRequest.Builder("test", Map.of()).withOffset(offset).build();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getQueryName()).isEqualTo("test");
        Assertions.assertThat(result.getNamedParameters()).isEqualTo(Map.of());
        Assertions.assertThat(result.getOffset()).isEqualTo(offset);
        Assertions.assertThat(result.getLimit()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    public void withLimit() {
        PersistenceQueryRequest result = new PersistenceQueryRequest.Builder("test", Map.of()).withLimit(limit).build();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result.getQueryName()).isEqualTo("test");
        Assertions.assertThat(result.getNamedParameters()).isEqualTo(Map.of());
        Assertions.assertThat(result.getOffset()).isEqualTo(0);
        Assertions.assertThat(result.getLimit()).isEqualTo(limit);
    }
}
