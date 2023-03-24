package net.corda.v5.application.flows.interop.dispatch

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import net.corda.v5.application.flows.interop.binding.FacadeInterfaceBinding
import net.corda.v5.application.flows.interop.binding.FacadeMethodBinding
import net.corda.v5.application.flows.interop.binding.FacadeOutParameterBindings
import net.corda.v5.application.flows.interop.binding.api.BindsFacade
import net.corda.v5.application.flows.interop.binding.api.FacadeVersions
import net.corda.v5.application.flows.interop.binding.api.InteropAction
import net.corda.v5.application.flows.interop.binding.creation.FacadeInterfaceBindings
import net.corda.v5.application.flows.interop.proxies.FacadeMethodDispatchException
import net.corda.v5.application.flows.interop.proxies.JacksonJsonMarshaller
import net.corda.v5.application.interop.facade.Facade
import net.corda.v5.application.interop.facade.FacadeRequest
import net.corda.v5.application.interop.facade.FacadeResponse
import net.corda.v5.application.interop.parameters.TypeConverter
import net.corda.v5.application.interop.parameters.TypedParameter
import net.corda.v5.application.interop.parameters.TypedParameterValue

object FacadeServerDispatchers {

    @JvmStatic
    fun <T : Any> buildDispatcher(
        facade: Facade,
        targetInterface: Class<T>,
        target: T,
        typeConverter: TypeConverter
    ) : FacadeServerDispatcher {
        val binding = FacadeInterfaceBindings.bind(facade, targetInterface)
        return FacadeServerDispatcher(typeConverter, binding, target)
    }
}

fun Any.buildDispatcher(facade: Facade, typeConverter: TypeConverter): FacadeServerDispatcher {
    val targetInterface = this.javaClass.interfaces.find {
        it.isAnnotationPresent(BindsFacade::class.java) &&
                it.getAnnotation(BindsFacade::class.java).value == facade.facadeId.unversionedName &&
                (!it.isAnnotationPresent(FacadeVersions::class.java) ||
                        facade.facadeId.version in it.getAnnotation(FacadeVersions::class.java).value.toSet())
    } ?: throw FacadeMethodDispatchException("Object $this implements no interface binding ${facade.facadeId}")

    return FacadeServerDispatchers.buildDispatcher(facade, targetInterface as Class<Any>, this, typeConverter)
}

fun Any.buildDispatcher(facade: Facade): FacadeServerDispatcher =
    buildDispatcher(facade, TypeConverter(JacksonJsonMarshaller(ObjectMapper().registerKotlinModule())))

class FacadeServerDispatcher(
    val typeConverter: TypeConverter,
    val binding: FacadeInterfaceBinding,
    val target: Any
) : (FacadeRequest) -> FacadeResponse {
    @SuppressWarnings("SpreadOperator")
    override fun invoke(request: FacadeRequest): FacadeResponse {
        val boundMethod = binding.bindingFor(request.methodName) ?: throw FacadeMethodDispatchException(
            "No method on ${target.javaClass} is bound to request method ${request.methodName}"
        )

        val args = buildMethodArguments(boundMethod, request)
        val result = (boundMethod.interfaceMethod.invoke(target, listOf(args)) as InteropAction<Any>).result
        val outParameterValues = getOutParameterValues(result, boundMethod.outParameterBindings)

        return binding.facade.response(boundMethod.facadeMethod.name, *outParameterValues.toTypedArray())
    }

    private fun getOutParameterValues(
        result: Any,
        outParameterBindings: FacadeOutParameterBindings
    ): List<TypedParameterValue<*>> = when (outParameterBindings) {
        FacadeOutParameterBindings.NoOutParameters -> emptyList()

        is FacadeOutParameterBindings.SingletonOutParameterBinding -> {
            val parameter = outParameterBindings.outParameter as TypedParameter<Any>
            val value = typeConverter.convertJvmToFacade(result, parameter.type)

            listOf(parameter of value)
        }

        is FacadeOutParameterBindings.DataClassOutParameterBindings -> {
            outParameterBindings.bindings.map { binding ->
                val propertyValue = binding.readMethod.invoke(result)
                (binding.facadeOutParameter as TypedParameter<Any>) of propertyValue
            }
        }
    }

    private fun buildMethodArguments(
        boundMethod: FacadeMethodBinding,
        request: FacadeRequest
    ): Array<Any?> {
        val args = Array<Any?>(boundMethod.inParameterBindings.size) { null }

        request.inParameters.forEach { parameterValue ->
            val parameterBinding =
                boundMethod.bindingForInParameter(parameterValue.parameter.name) ?: throw FacadeMethodDispatchException(
                    "Method ${boundMethod.facadeMethod.qualifiedName} does not have a parameter " +
                            parameterValue.parameter.name
                )

            args[parameterBinding.boundParameter.index] = typeConverter.convertFacadeToJvm(
                parameterValue.parameter.type,
                parameterValue.value,
                parameterBinding.boundParameter.type
            )
        }
        return args
    }
}