package net.corda.v5.application.interop.facade;

import net.corda.v5.application.interop.parameters.ParameterType;
import net.corda.v5.application.interop.parameters.ParameterTypeLabel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Collections.emptyList;

//TODO add implementation for this interface in corda-runtime-os
public interface JacksonFacadeReader {

    @NotNull
    Facade read(@NotNull Reader reader);

    @NotNull
    private FacadeMethod parseFacadeMethod(@NotNull FacadeId facadeId, @NotNull String id, @NotNull FacadeMethodType methodType, @NotNull Map<String, ParameterType<Object>> aliases, @Nullable FacadeMethodDefinition methodJson) {
//        assert methodJson.getIn() != null;

//        if (methodJson != null) {
//            if(methodJson.getIn() != null){
//                Map<String, String> inParams = methodJson.getIn();
//                List list = new ArrayList(inParams.size());
//                Iterator iterator = inParams.entrySet().iterator();
//
//                while(iterator.hasNext()) {
//                    Map.Entry mapEntry = (Map.Entry)iterator.next();
//                    String name = (String)mapEntry.getKey();
//                    name = (String)mapEntry.getValue();
//                    var21 = new ParameterTypeLabel(name, ParameterType.of(name, aliases));
//                    list.add(var21);
//                }
//            }
//            }
//        }

//        List<ParameterType<Object>> inParams = methodJson.getIn();
        Map<String, String> map = methodJson.getIn();
            map = (name, type) -> ParameterTypeLabel(name, ParameterType.of(type, aliases))
            ?: emptyList();



        Map<Integer, Animal> map = list.stream()
                .collect(Collectors.toMap(Animal::getId, Function.identity()));
        return map;


        List<ParameterType<Object>> outParams = methodJson.getOut()
                ?.map { (name, type) -> ParameterTypeLabel(name, ParameterType.of(type, aliases)) }
            ?: emptyList();

        return new FacadeMethod(facadeId, id, methodType, inParams, outParams);
    }


    Map var10000;
    String name;
    TypedParameter var21;
    List var27;
    label39: {
        if (methodJson != null) {
            var10000 = methodJson.getIn();
            if (var10000 != null) {
                Map $this$map$iv = var10000;
                int $i$f$map = false;
                Collection destination$iv$iv = (Collection)(new ArrayList($this$map$iv.size()));
                int $i$f$mapTo = false;
                Iterator var12 = $this$map$iv.entrySet().iterator();

                while(var12.hasNext()) {
                    Map.Entry item$iv$iv = (Map.Entry)var12.next();
                    int var15 = false;
                    String name = (String)item$iv$iv.getKey();
                    name = (String)item$iv$iv.getValue();
                    var21 = new TypedParameter(name, ParameterType.Companion.of(name, aliases));
                    destination$iv$iv.add(var21);
                }

                var27 = (List)destination$iv$iv;
                break label39;
            }
        }

        var27 = CollectionsKt.emptyList();
    }

    List inParams;
    label28: {
        inParams = var27;
        if (methodJson != null) {
            var10000 = methodJson.getOut();
            if (var10000 != null) {
                Map $this$map$iv = var10000;
                int $i$f$map = false;
                Collection destination$iv$iv = (Collection)(new ArrayList($this$map$iv.size()));
                int $i$f$mapTo = false;
                Iterator var26 = $this$map$iv.entrySet().iterator();

                while(var26.hasNext()) {
                    Map.Entry item$iv$iv = (Map.Entry)var26.next();
                    int var16 = false;
                    name = (String)item$iv$iv.getKey();
                    String type = (String)item$iv$iv.getValue();
                    var21 = new TypedParameter(name, ParameterType.Companion.of(type, aliases));
                    destination$iv$iv.add(var21);
                }

                var27 = (List)destination$iv$iv;
                break label28;
            }
        }

        var27 = CollectionsKt.emptyList();
    }

    List outParams = var27;
      return new FacadeMethod(facadeId, id, methodType, inParams, outParams);
}
