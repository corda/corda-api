package net.corda.v5.application.flows;

import net.corda.v5.application.marshalling.JsonMarshallingService;
import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

/**
 * {@link RPCStartableFlow} is a {@link Flow} that is started via RPC.
 *
 * {@link RPCStartableFlow#call} takes in a {@link RPCRequestData}, containing the body of the RPC request that started the flow.
 *
 * The string return type is treated by the platform as a JSON encoded string to return to the RPC
 * service, and will appear in the RPC flow status when the flow completes. To assist in returning valid JSON, the
 * {@link JsonMarshallingService} can be used.
 *
 * Flows implementing this interface must have a no-arg constructor. The flow invocation will fail if this constructor
 * does not exist.
 *
 * Example usage:
 * <ul>
 * <li>Kotlin:
 * <pre>
 * class MyFlow : RPCStartableFlow {
 *
 *     &#64;CordaInject
 *     lateinit var jsonMarshallingService: JsonMarshallingService
 *
 *     &#64;Suspendable
 *     override fun call(requestBody: RPCRequestData): String {
 *         val parameters = requestBody.getRequestBodyAs<MyFlowParameters>(jsonMarshallingService)
 *         ...
 *         return jsonMarshallingService.format(parameters)
 *     }
 * }
 * </pre></li>
 *
 * <li>Java:
 * <pre>
 * public class MyFlow implements RPCStartableFlow {
 *
 *     &#64;CordaInject
 *     public JsonMarshallingService jsonMarshallingService;
 *
 *     &#64;Suspendable
 *     &#64;Override
 *     public String call(RPCRequestData requestBody) {
 *         MyFlowParameters parameters = requestBody.getRequestBodyAs(jsonMarshallingService, MyFlowParameters.class);
 *         ...
 *         return jsonMarshallingService.format(parameters);
 *     }
 * }
 * </pre></li>
 */
public interface RPCStartableFlow extends Flow {

    /**
     * The business logic for this flow should be written here.
     *
     * This is equivalent to the normal flow call method, where the output is fixed to being a JSON encoded string.
     * Additionally, the call method is invoked with the body of the RPC request.
     *
     * @param requestBody The body of the RPC request that started this flow.
     *
     * @return A JSON encoded string to be supplied to the flow status on flow completion as the result.
     */
    @Suspendable
    @NotNull
    String call(@NotNull RPCRequestData requestBody);
}
