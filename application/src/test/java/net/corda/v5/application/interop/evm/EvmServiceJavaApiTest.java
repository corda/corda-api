package net.corda.v5.application.interop.evm;

import net.corda.v5.application.interop.evm.options.CallOptions;
import net.corda.v5.application.interop.evm.options.EvmOptions;
import net.corda.v5.application.interop.evm.options.TransactionOptions;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static net.corda.v5.application.interop.evm.Type.STRING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class EvmServiceJavaApiTest {

    private final EvmService service = mock(EvmService.class);

    @Test
    public void callWithMultipleParameters() {
        String test = "test";
        Parameter<String> one = Parameter.of("one", "value");
        Parameter<BigInteger> two = Parameter.of("one", BigInteger.TWO);
        when(service.call(anyString(), anyString(), any(), eq(STRING), eq(one), eq(two))).thenReturn(test);

        String result = service.call("", "", mock(CallOptions.class), STRING, one, two);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(test);
    }

    @Test
    public void callWithMultipleParametersInList() {
        String test = "test";
        Parameter<String> one = Parameter.of("one", "value");
        Parameter<BigInteger> two = Parameter.of("one", BigInteger.TWO);
        List<Parameter<?>> list =Arrays.asList(one, two);

        when(service.call(anyString(), anyString(), any(), eq(STRING), eq(list))).thenReturn(test);

        String result = service.call("", "", mock(CallOptions.class), STRING, list);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(test);
    }

    @Test
    public void callIncludingAnArrayInList() {
        String test = "test";
        Parameter<String> one = Parameter.of("one", "value");
        Parameter<String[]> two = Parameter.of("one", "one", "two");
        List<Parameter<?>> list = Arrays.asList(one, two);

        when(service.call(anyString(), anyString(), any(), eq(STRING), eq(list))).thenReturn(test);

        String result = service.call("", "", mock(CallOptions.class), STRING, list);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(test);
    }

    @Test
    public void transactionWithMultipleParameters() {
        String test = "test";
        Parameter<String> one = Parameter.of("one", STRING, "value");
        Parameter<BigInteger> two = Parameter.of("one", Type.INT256, BigInteger.TWO);
        when(service.transaction(anyString(), anyString(), any(), eq(one), eq(two))).thenReturn(test);

        String result = service.transaction("", "", mock(TransactionOptions.class), one, two);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(test);
    }

    @Test
    public void transactionWithMultipleParametersInList() {
        String test = "test";
        Parameter<String> one = Parameter.of("one", "value");
        Parameter<BigInteger> two = Parameter.of("one", BigInteger.TWO);
        List<Parameter<?>> list = Arrays.asList(one, two);

        when(service.transaction(anyString(), anyString(), any(), eq(list))).thenReturn(test);

        String result = service.transaction("", "", mock(TransactionOptions.class), list);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(test);
    }

    @Test
    public void transactionIncludingAnArrayInList() {
        String test = "test";
        Parameter<String> one = Parameter.of("one", "value");
        Parameter<String[]> two = Parameter.of("one", "one", "two");
        List<Parameter<?>> list = Arrays.asList(one, two);

        when(service.transaction(anyString(), anyString(), any(), eq(list))).thenReturn(test);

        String result = service.transaction("", "", mock(TransactionOptions.class), list);

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(test);
    }

    @Test
    public void getTransactionReceipt() {
        List<String> topics = List.of("topic");
        List<Log> logs = List.of(new Log("address",
                topics,
                "data",
                BigInteger.ONE,
                "transactionalHash",
                BigInteger.ONE,
                "blockHash",
                1,
                false));
        TransactionReceipt test = new TransactionReceipt(
                "blockHash",
                BigInteger.ONE,
                "contractAddress",
                BigInteger.ONE,
                BigInteger.ONE,
                "from",
                BigInteger.ONE,
                logs,
                "logsBloom",
                true,
                "to",
                "transactionHash",
                BigInteger.ONE,
                "type");

        when(service.getTransactionReceipt(anyString(), any(EvmOptions.class))).thenReturn(test);

        TransactionReceipt result = service.getTransactionReceipt("", mock(EvmOptions.class));

        assertThat(result).isNotNull();
        assertThat(result).isEqualTo(test);
    }

    @Test
    public void getBlockByNumber() {
        Block test = new Block(
                BigInteger.ONE,
                "hash",
                "parentHash",
                BigInteger.ONE,
                "sha3Uncles",
                "logsBloom",
                "transactionsRoot",
                "stateRoot",
                "receiptsRoot",
                "miner",
                "mixHash",
                BigInteger.ONE,
                BigInteger.ONE,
                "extraData",
                BigInteger.ONE,
                BigInteger.ONE,
                BigInteger.ONE,
                BigInteger.ONE,
                List.of("transactions"),
                List.of("uncles"),
                BigInteger.ONE,
                BigInteger.ONE,
                BigInteger.ONE);

        when(service.getBlockByNumber(any(), anyBoolean(), any(EvmOptions.class))).thenReturn(test);

        Block resultTrue = service.getBlockByNumber(BigInteger.ONE, true, mock(EvmOptions.class));

        assertThat(resultTrue).isNotNull();
        assertThat(resultTrue).isEqualTo(test);

        Block resultFalse = service.getBlockByNumber(BigInteger.ONE, false, mock(EvmOptions.class));

        assertThat(resultFalse).isNotNull();
        assertThat(resultFalse).isEqualTo(test);
    }

    @Test
    public void getBlockByHash() {
        Block test = new Block(
                BigInteger.ONE,
                "hash",
                "parentHash",
                BigInteger.ONE,
                "sha3Uncles",
                "logsBloom",
                "transactionsRoot",
                "stateRoot",
                "receiptsRoot",
                "miner",
                "mixHash",
                BigInteger.ONE,
                BigInteger.ONE,
                "extraData",
                BigInteger.ONE,
                BigInteger.ONE,
                BigInteger.ONE,
                BigInteger.ONE,
                List.of("transactions"),
                List.of("uncles"),
                BigInteger.ONE,
                BigInteger.ONE,
                BigInteger.ONE);

        when(service.getBlockByHash(any(), anyBoolean(), any(EvmOptions.class))).thenReturn(test);

        Block resultTrue = service.getBlockByHash("", true, mock(EvmOptions.class));

        assertThat(resultTrue).isNotNull();
        assertThat(resultTrue).isEqualTo(test);

        Block resultFalse = service.getBlockByHash("", false, mock(EvmOptions.class));

        assertThat(resultFalse).isNotNull();
        assertThat(resultFalse).isEqualTo(test);
    }
}