# Application API

The `application` API module contains interfaces and classes useful for writing flows. It also provides a base set of
interfaces used elsewhere in the API for higher-level concerns, for example Corda services and the ledger.

## Flows

The `flows` package contains interfaces that are used to define flows, as well as a small set of foundational services
that most flows will use to perform basic operations (e.g. subflows, messaging). The `flows` package is subdivided into
the `audit`, `core`, `engine`, `error` and `messaging` packages.

### Audit

The `audit` section of the flows API defines a service for auditing the execution of flows.

### Core

The `core` section of the flows API contains the interfaces and annotations used to define a flow class. The key
interface here is `Flow` - classes implementing this will be interpreted by the system as flows. The remaining
annotations are used to define particular characteristics of a flow, for example whether it is startable over RPC, or
if it is initiating. The `CordaInject` annotation is also defined here, which most flows will use to inject particular
pieces of functionality provided by the platform.

### Engine

The `engine` section of the flows API contains services that the platform provides that expand the very basic flow
functionality. The main service here is `FlowEngine`, which provides the ability to execute subflows as well as 
providing information about the execution context (flow ID, member X500 name, etc).

### Error

The `error` section of the flows API should define exception types that user code can throw to trigger specific behaviour
in the platform. Errors that particular services could throw should be defined alongside those services.

### Messaging

The `messaging` section of the flows API provides the flow session API for communicating with counterparties.

## Injection

The `injection` package contains interfaces that declare where services implementing them can be injected. These are
public API as the service interfaces using them rely on them being available.

## Query

The `query` package contains interfaces that can be implemented to define a named query to be executed against the
database, as well as filters and post-processors that can be executed before the results are returned.

For executing these queries in flows, see the `persistence` package in `services`.

## Services

The `services` package contains service interface definitions. These services are provided by the platform and can be
injected into either flows or user-provided services. The `services` package is subdivided to group similar services
together, along with errors from those services.

### Configuration

The `configuration` section of the `services` API provides a service for accessing the current user-specified 
configuration for the virtual node.

### Diagnostics

The `diagnostics` section of the `services` API provides information about the current virtual node and cluster, which
can be used to print diagnostic information.

### JSON

The `json` section of the `services` API provides utilities for serializing types to and from JSON, including a JSON
serialization service that is properly sandbox aware.

### Membership

The `membership` section of the `services` API provides services for accessing details about the membership group of the
current virtual node.

### Persistence

The `persistence` section of the `services` API provides a service for writing to the virtual node database, as well as
functionality for invoking named queries and persisting single entities.

### Serialization

The `serialization` section of the `services` API defines an interface implemented by various serialization schemes used
by the platform.

### Time

The `time` section of the `services` API defines an interface for retrieving time-based information from the system
clock. This should be preferred over the standard system clock.

