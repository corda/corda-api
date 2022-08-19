package net.corda.v5.ledger.utxo;

import org.jetbrains.annotations.NotNull;

import java.security.PublicKey;

@LegalProseReference("example")
public class ExampleContract implements Contract {

    @Override
    public void verify(@NotNull UtxoLedgerTransaction transaction) {
    }

    private interface ExampleContractCommand extends Command, VerifiableCommand {
    }

    public static class Create implements ExampleContractCommand {

        @Override
        public void verify(@NotNull UtxoLedgerTransaction transaction, @NotNull Iterable<? extends PublicKey> signatories) {
        }
    }

    public static class Update implements ExampleContractCommand {

        @Override
        public void verify(@NotNull UtxoLedgerTransaction transaction, @NotNull Iterable<? extends PublicKey> signatories) {
        }
    }

    public static class Delete implements ExampleContractCommand {

        @Override
        public void verify(@NotNull UtxoLedgerTransaction transaction, @NotNull Iterable<? extends PublicKey> signatories) {
        }
    }
}
