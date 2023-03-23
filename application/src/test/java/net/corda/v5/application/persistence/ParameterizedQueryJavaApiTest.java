package net.corda.v5.application.persistence;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.mockito.Mockito.*;

public class ParameterizedQueryJavaApiTest {

    static class TestObject {
        public int foo;
    }

    final private ParameterizedQuery<TestObject> query = mock(ParameterizedQuery.class);

    @Test
    public void setParameterChain() {
        when(query.setParameter(anyString(), any())).thenReturn(query);
        PagedQuery.ResultSet<TestObject> result = query
                .setParameter("foo", "bar")
                .setParameter("fred", 789)
                .execute();
        verify(query, times(1)).setParameter("foo", "bar");
        verify(query, times(1)).setParameter("fred", 789);
        verify(query, times(1)).execute();
    }

    @Test
    public void setParameters() {
        Map<String, Object> params = Map.of("foo", "bar", "fred", 789);
        when(query.setParameters(params)).thenReturn(query);
        PagedQuery.ResultSet<TestObject> result = query
                .setParameters(params)
                .execute();
        verify(query, times(1)).setParameters(params);
        verify(query, times(1)).execute();
    }
}
