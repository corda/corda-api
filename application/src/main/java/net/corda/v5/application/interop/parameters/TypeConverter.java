package net.corda.v5.application.interop.parameters;

import net.corda.v5.application.marshalling.JsonMarshallingService;
import org.jetbrains.annotations.NotNull;

public interface TypeConverter {
    @NotNull
    JsonMarshallingService getJsonMarshaller();

    Object convertFacadeToJvm(@NotNull ParameterType<?> parameterType, @NotNull Object parameterValue, @NotNull Class<?> jvmType);

    Object convertJvmToFacade(Object value, ParameterType<?> expectedType);
}
