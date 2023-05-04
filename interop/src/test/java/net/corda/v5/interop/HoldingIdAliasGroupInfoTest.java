package net.corda.v5.interop;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HoldingIdAliasGroupInfoTest {

    private final HoldingIdAliasGroupInfo holdingIdAliasGroupInfo = mock(HoldingIdAliasGroupInfo.class);

    @Test
    void getShortHash() {
        String testHortHash = "ABCD1234CH";
        when(holdingIdAliasGroupInfo.getShortHash()).thenReturn(testHortHash);

        String result = holdingIdAliasGroupInfo.getShortHash();

        assertEquals(testHortHash, result);
        verify(holdingIdAliasGroupInfo, times(1)).getShortHash();
    }

    @Test
    void getGroups() {
        InteropGroupInfo interopGroupInfo1 = mock(InteropGroupInfo.class);
        InteropGroupInfo interopGroupInfo2 = mock(InteropGroupInfo.class);
        List<InteropGroupInfo> test = List.of(interopGroupInfo1, interopGroupInfo2);
        when(holdingIdAliasGroupInfo.getGroups()).thenReturn(test);

        List<InteropGroupInfo> result = holdingIdAliasGroupInfo.getGroups();

        assertEquals(test, result);
        verify(holdingIdAliasGroupInfo, times(1)).getGroups();
    }
}