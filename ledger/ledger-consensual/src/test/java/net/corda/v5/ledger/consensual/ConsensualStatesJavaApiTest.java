package net.corda.v5.ledger.consensual;

import net.corda.v5.ledger.consensual.transaction.ConsensualLedgerTransaction;
import org.assertj.core.api.Assertions;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ConsensualStatesJavaApiTest {
    private final Party Party = mock(Party.class);
    private final List<Party> participants = List.of(Party);

    @Test
    public void participants() {

        final ConsensualState ConsensualState = mock(ConsensualState.class);
        when(ConsensualState.getParticipants()).thenReturn(participants);

        final List<Party> participants1 = ConsensualState.getParticipants();

        Assertions.assertThat(participants).isEqualTo(participants1);
    }

    @Test
    public void customConsensualStateClass() {
        final CustomConsensualState customConsensualState = new CustomConsensualState(participants);

        Assertions.assertThat(customConsensualState).isNotNull();
        Assertions.assertThat(customConsensualState.getParticipants()).isEqualTo(participants);
    }

    class CustomConsensualState implements ConsensualState {
        private final List<Party> participants;

        public CustomConsensualState(List<Party> participants) {
            this.participants = participants;
        }

        @NotNull
        @Override
        public List<Party> getParticipants() {
            return participants;
        }

        @Override
        public boolean verify(@NotNull ConsensualLedgerTransaction consensualLedgerTransaction) {
            return true;
        }
    }
}
