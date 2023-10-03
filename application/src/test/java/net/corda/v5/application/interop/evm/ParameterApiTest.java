package net.corda.v5.application.interop.evm;

import net.corda.v5.base.exceptions.CordaRuntimeException;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static net.corda.v5.application.interop.evm.Type.BOOLEAN;
import static net.corda.v5.application.interop.evm.Type.CHARACTER;
import static net.corda.v5.application.interop.evm.Type.CHARACTER_ARRAY;
import static net.corda.v5.application.interop.evm.Type.CHARACTER_LIST;
import static net.corda.v5.application.interop.evm.Type.INT256;
import static net.corda.v5.application.interop.evm.Type.INT32;
import static net.corda.v5.application.interop.evm.Type.INT64;
import static net.corda.v5.application.interop.evm.Type.STRING;
import static net.corda.v5.application.interop.evm.Type.STRING_ARRAY;
import static net.corda.v5.application.interop.evm.Type.STRING_LIST;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;

class ParameterApiTest {

    @Test
    public void parameterCorrectlyDeterminesType() {
        Parameter<Character> characterTest = Parameter.of("test", 's');
        assertThat(characterTest.getName()).isEqualTo("test");
        assertThat(characterTest.getType()).isEqualTo(CHARACTER);
        assertThat(characterTest.getValue()).isEqualTo('s');

        Parameter<String> stringTest = Parameter.of("test", "string");
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(stringTest.getType()).isEqualTo(STRING);
        assertThat(stringTest.getValue()).isEqualTo("string");

        Parameter<Boolean> boolTest = Parameter.of("test", true);
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(boolTest.getType()).isEqualTo(BOOLEAN);
        assertThat(boolTest.getValue()).isEqualTo(true);

        Parameter<Integer> intTest = Parameter.of("test", 1);
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(intTest.getType()).isEqualTo(INT32);
        assertThat(intTest.getValue()).isEqualTo(1);

        Parameter<Long> longTest = Parameter.of("test", 1L);
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(longTest.getType()).isEqualTo(INT64);
        assertThat(longTest.getValue()).isEqualTo(1L);

        Parameter<BigInteger> bigIntTest = Parameter.of("test", BigInteger.ONE);
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(bigIntTest.getType()).isEqualTo(INT256);
        assertThat(bigIntTest.getValue()).isEqualTo(BigInteger.ONE);
    }

    @Test
    public void arrayTypesAreCorrectlyDetermined() {
        Parameter<Character[]> characterTest = Parameter.of("test", 's', 't');
        assertThat(characterTest.getName()).isEqualTo("test");
        assertThat(characterTest.getType()).isEqualTo(CHARACTER_ARRAY);
        assertThat(characterTest.getValue()).isEqualTo(new Character[]{'s', 't'});

        Parameter<String[]> stringTest = Parameter.of("test", "string", "test");
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(stringTest.getType()).isEqualTo(STRING_ARRAY);
        assertThat(stringTest.getValue()).isEqualTo(new String[]{"string", "test"});

        Parameter<Boolean[]> boolTest = Parameter.of("test", true, false);
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(boolTest.getType()).isEqualTo(Type.BOOLEAN_ARRAY);
        assertThat(boolTest.getValue()).isEqualTo(new Boolean[]{true, false});

        Parameter<Integer[]> intTest = Parameter.of("test", 1, 2);
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(intTest.getType()).isEqualTo(Type.INT32_ARRAY);
        assertThat(intTest.getValue()).isEqualTo(new Integer[]{1, 2});

        Parameter<Long[]> longTest = Parameter.of("test", 1L, 2L);
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(longTest.getType()).isEqualTo(Type.INT64_ARRAY);
        assertThat(longTest.getValue()).isEqualTo(new Long[]{1L, 2L});

        Parameter<BigInteger[]> bigIntTest = Parameter.of("test", BigInteger.ONE, BigInteger.TWO);
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(bigIntTest.getType()).isEqualTo(Type.INT256_ARRAY);
        assertThat(bigIntTest.getValue()).isEqualTo(new BigInteger[]{BigInteger.ONE, BigInteger.TWO});
    }


    @Test
    public void listTypesAreCorrectlyDetermined() {
        Parameter<List<Character>> characterTest = Parameter.of("test", Arrays.asList('s', 't'));
        assertThat(characterTest.getName()).isEqualTo("test");
        assertThat(characterTest.getType()).isEqualTo(CHARACTER_LIST);
        assertThat(characterTest.getValue()).isEqualTo(Arrays.asList('s', 't'));

        Parameter<List<String>> stringTest = Parameter.of("test", Arrays.asList("string", "test"));
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(stringTest.getType()).isEqualTo(STRING_LIST);
        assertThat(stringTest.getValue()).isEqualTo(Arrays.asList("string", "test"));

        Parameter<List<Boolean>> boolTest = Parameter.of("test", Arrays.asList(true, false));
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(boolTest.getType()).isEqualTo(Type.BOOLEAN_LIST);
        assertThat(boolTest.getValue()).isEqualTo(Arrays.asList(true, false));

        Parameter<List<Integer>> intTest = Parameter.of("test", Arrays.asList(1, 2));
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(intTest.getType()).isEqualTo(Type.INT32_LIST);
        assertThat(intTest.getValue()).isEqualTo(Arrays.asList(1, 2));

        Parameter<List<Long>> longTest = Parameter.of("test", Arrays.asList(1L, 2L));
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(longTest.getType()).isEqualTo(Type.INT64_LIST);
        assertThat(longTest.getValue()).isEqualTo(Arrays.asList(1L, 2L));

        Parameter<List<BigInteger>> bigIntTest = Parameter.of("test", Arrays.asList(BigInteger.ONE, BigInteger.TWO));
        assertThat(stringTest.getName()).isEqualTo("test");
        assertThat(bigIntTest.getType()).isEqualTo(Type.INT256_LIST);
        assertThat(bigIntTest.getValue()).isEqualTo(Arrays.asList(BigInteger.ONE, BigInteger.TWO));
    }

    @Test
    public void parameterCorrectlyThrowsForBadTypes() {
        assertThatExceptionOfType(CordaRuntimeException.class).isThrownBy(() ->
                Parameter.of("test", 0.1)
        );

        assertThatExceptionOfType(CordaRuntimeException.class).isThrownBy(() ->
                Parameter.of("test", 0.1d)
        );
    }

}