package net.corda.v5.application.marshalling;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

public class JsonSerializerJavaApiTest {

    private static class TestSerializer implements JsonSerializer<String> {
        @Override
        public void serialize(String item, @NotNull JsonWriter jsonWriter) {
            jsonWriter.writeRaw("test");
        }
    }

    @Test
    void instantiateSubclassAndCall() {
        TestSerializer ts = new TestSerializer();
        JsonWriter jw = mock(JsonWriter.class);
        ts.serialize("test", jw);
    }
}
