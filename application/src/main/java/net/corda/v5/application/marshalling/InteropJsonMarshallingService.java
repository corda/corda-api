package net.corda.v5.application.marshalling;

import net.corda.v5.application.flows.ClientStartableFlow;
import net.corda.v5.base.annotations.DoNotImplement;

/**
 * {@link InteropJsonMarshallingService} marshalls to and from JSON using the registered JSON mapper.
 * <p>
 * The platform will provide an instance of {@link InteropJsonMarshallingService} to flows via property injection.
 * <p>
 * Example usage:
 * @see ClientStartableFlow
 */
@DoNotImplement
public interface InteropJsonMarshallingService extends MarshallingService {
}
