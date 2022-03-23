package net.corda.v5.application.services.diagnostics

import net.corda.v5.application.injection.CordaFlowInjectable
import net.corda.v5.application.injection.CordaServiceInjectable
import net.corda.v5.base.annotations.DoNotImplement

/**
 * A [DiagnosticsService] provides APIs that allow CorDapps to query information about the node that CorDapp is currently running on.
 */
/*
JH: Should this exist? We could do a standard versioning log at the start of each flow if the intent is just to provide
some diagnostic info. If we're expecting something a bit more serious here (like switching behaviour based on platform
version) I think we should look at this a bit differently and name the service differently too.

NodeVersionInfo presumably needs a rework to reflect the VNode/cluster split.
 */
@DoNotImplement
interface DiagnosticsService : CordaServiceInjectable, CordaFlowInjectable {

    /**
     * Retrieve information about the current node version.
     *
     * @return The [NodeVersionInfo] holding information about the current node version.
     */
    val nodeVersionInfo : NodeVersionInfo
}