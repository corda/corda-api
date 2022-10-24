package net.corda.v5.ledger.notary.pluggable

import net.corda.v5.application.crypto.DigitalSignatureAndMetadata
import net.corda.v5.application.flows.SubFlow

/**
 * A basic interface that needs to be implemented by the client-side logic of the notary plugin.
 *
 * This interface has a single method called [call] which contains the main logic for the client.
 * The client flow must implement this interface in order for it to be instantiated in the provider.
 * If the client flow doesn't implement this interface the plugin selection will not work.
 * To learn more about plugin selection and types see [PluggableNotaryType].
 *
 * Implementations must specify the [net.corda.v5.application.flows.InitiatingFlow] annotation.
 * Further, the [call] method must specify the [net.corda.v5.base.annotations.Suspendable] annotation.
 *
 * The server side will not have an interface like the client, it will only implement the
 * [net.corda.v5.application.flows.ResponderFlow] interface.
 *
 * For an example client and server implementation please refer to the non-validating notary plugin under
 * the `notary-plugins/notary-plugin-non-validating` module in the `corda-runtime-os` repository,
 * or the quick starter guide.
 */
interface PluggableNotaryClientFlow : SubFlow<List<DigitalSignatureAndMetadata>>