package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.ParameterType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.Reader;
import java.util.Map;

import static java.util.Collections.emptyList;

//TODO add implementation for this interface in corda-runtime-os
public interface JacksonFacadeReader {

    @NotNull
    Facade read(@NotNull Reader reader);

    @NotNull
    private FacadeMethod parseFacadeMethod(@NotNull FacadeId facadeId, @NotNull String id, @NotNull FacadeMethodType methodType, @NotNull Map<String, ParameterType<Object>> aliases, @Nullable FacadeMethodDefinition methodJson) {
        assert methodJson.getIn() != null;
        List<TypedParameter<Object>> inParams = methodJson.getIn()
            .map { (name, type) -> TypedParameter(name, ParameterType.of(type, aliases)) }
            ?: emptyList();

        List<TypedParameter<Object>> outParams = methodJson.getOut()
                ?.map { (name, type) -> TypedParameter(name, ParameterType.of(type, aliases)) }
            ?: emptyList();

        return FacadeMethod(facadeId, id, methodType, inParams, outParams);
    }
}
