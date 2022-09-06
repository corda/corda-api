package net.corda.v5.application.marshalling;

import net.corda.v5.application.flows.RPCStartableFlow;
import net.corda.v5.base.annotations.DoNotImplement;

/**
 * {@link JsonMarshallingService} marshalls to and from JSON using the registered JSON mapper.
 *
 * The platform will provide an instance of {@link JsonMarshallingService} to flows via property injection.
 *
 * Example usage:
 * @see RPCStartableFlow
 */
@DoNotImplement
public interface JsonMarshallingService extends MarshallingService {}
