package net.corda.v5.ledger.notary.plugin.api;

import net.corda.v5.base.annotations.CordaSerializable;

@CordaSerializable
public enum NotarizationType {

    WRITE, READ
}