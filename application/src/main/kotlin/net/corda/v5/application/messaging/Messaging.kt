package net.corda.v5.application.messaging

import net.corda.v5.base.annotations.CordaSerializable

/** The interface for a group of message recipients (which may contain only one recipient) */
@CordaSerializable
interface MessageRecipients

/** A base class for the case of point-to-point messages */
interface SingleMessageRecipient : MessageRecipients

/** A base class for a set of recipients specifically identified by the sender. */
interface MessageRecipientGroup : MessageRecipients

/** A special base class for the set of all possible recipients, without having to identify who they all are. */
interface AllPossibleRecipients : MessageRecipients
