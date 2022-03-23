# Corda Services API

This module contains the API for defining Corda services, along with some service-specific platform interfaces that can
be injected for useful functionality.

## The `core` package

The core package provides interfaces that define a Corda service. Classes implementing the `CordaService` interface will
be interpreted as such at build time, and the platform will load these classes as Corda services when the CPI is 
installed into the system.

## The `core.lifecycle` package

Corda services are expected to manage their own lifecycle. This package contains some API definitions that allow the
platform to signal up to the service when certain events have happened, so that the service may respond appropriately.

## Notes

The `FlowStarterService` was removed because of its reliance on `FlowHandle`, which no longer works under the new
architecture. This, however, should be replaced to allow Corda services to start flows. It should go via the same
interface as RPC.