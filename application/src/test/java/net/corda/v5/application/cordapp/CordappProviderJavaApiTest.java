package net.corda.v5.application.cordapp;

import net.corda.v5.application.services.configuration.CordappConfig;
import net.corda.v5.application.services.configuration.CordappConfigurationProvider;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CordappProviderJavaApiTest {

    private final CordappConfigurationProvider cordappProvider = mock(CordappConfigurationProvider.class);
    private final CordappConfig cordappConfig = mock(CordappConfig.class);

    @Test
    public void getAppConfig() {
        when(cordappProvider.getAppConfig()).thenReturn(cordappConfig);

        CordappConfig result = cordappProvider.getAppConfig();

        Assertions.assertThat(result).isNotNull();
        Assertions.assertThat(result).isEqualTo(cordappConfig);
    }
}
