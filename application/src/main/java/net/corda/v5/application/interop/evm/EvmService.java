package net.corda.v5.application.interop.evm;

import net.corda.v5.application.interop.evm.options.CallOptions;
import net.corda.v5.application.interop.evm.options.EvmOptions;
import net.corda.v5.application.interop.evm.options.TransactionOptions;
import net.corda.v5.base.annotations.DoNotImplement;
import net.corda.v5.base.annotations.Suspendable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * Responsible for interactions with an Ethereum service
 */
@DoNotImplement
public interface EvmService {

    /**
     * Sends a <a href="https://www.quicknode.com/docs/ethereum/eth_call">eth_call</a> query to the EVM.
     *
     * @param functionName the function name on the EVM smart contract to call
     * @param to           the address of the smart contract
     * @param parameters   any parameters (in order listed by the call) for the contract
     * @return the returned value of the contract function.
     */
    @Suspendable
    @NotNull
    <T> T call(@NotNull String functionName,
               @NotNull String to,
               CallOptions options,
               Class<T> returnType,
               @NotNull Parameter<?>... parameters
    );

    /**
     * Sends a <a href="https://www.quicknode.com/docs/ethereum/eth_call">eth_call</a> query to the EVM.
     *
     * @param functionName the function name on the EVM smart contract to call
     * @param to           the address of the smart contract
     * @param parameters   any parameters (mapped by parameter name to value) for the contract
     * @return the returned value of the contract function.
     */
    @Suspendable
    @NotNull
    <T> T call(@NotNull String functionName,
               @NotNull String to,
               CallOptions options,
               Class<T> returnType,
               @NotNull List<Parameter<?>> parameters
    );

    /**
     * Sends a <a href="https://www.quicknode.com/docs/ethereum/eth_sendRawTransaction">eth_sendRawTransaction</a> query to the EVM.
     * <p>
     * Returns back immediately with the transaction hash.  In order to determine when the transaction has
     * been successfully committed to the chain use {@link EvmService#getTransactionReceipt(String, EvmOptions)}
     *
     * @param functionName the function name on the EVM smart contract to call
     * @param to           the address of the smart contract. null when it's a contract creation
     * @param options      any optional values for the transaction.  see {@link TransactionOptions}
     * @param parameters   any parameters for the contract. see {@link Parameter}
     * @return the transaction hash
     */
    @Suspendable
    @NotNull
    String transaction(@NotNull String functionName,
                       String to,
                       TransactionOptions options,
                       @NotNull Parameter<?>... parameters
    );

    /**
     * Sends a <a href="https://www.quicknode.com/docs/ethereum/eth_sendRawTransaction">eth_sendRawTransaction</a> query to the EVM.
     * <p>
     * Returns back immediately with the transaction hash.  In order to determine when the transaction has
     * been successfully committed to the chain use {@link EvmService#getTransactionReceipt(String, EvmOptions)}
     *
     * @param functionName the function name on the EVM smart contract to call
     * @param to           the address of the smart contract. null when it's a contract creation
     * @param options      any optional values for the transaction.  see {@link TransactionOptions}
     * @param parameters   any parameters for the contract. see {@link Parameter}
     * @return the transaction hash
     */
    @Suspendable
    @NotNull
    String transaction(@NotNull String functionName,
                       String to,
                       TransactionOptions options,
                       @NotNull List<Parameter<?>> parameters
    );

    /**
     * Sends a <a href="https://www.quicknode.com/docs/ethereum/eth_getTransactionReceipt">eth_getTransactionReceipt</a> query to the EVM.
     * <p>
     * Retrieves a receipt of the given transaction, including status.  See {@link TransactionReceipt}.
     *
     * @param transactionHash the transaction for which to query
     * @return an [EvmTransactionReceipt] with the results of the transaction
     */
    @NotNull
    TransactionReceipt getTransactionReceipt(@NotNull String transactionHash,
                                             EvmOptions options);
}
