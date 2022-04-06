# Application API

The `application` API module contains interfaces and classes useful for writing flows. It also provides a base set of
interfaces used elsewhere in the API for higher-level concerns, for example Corda services and the ledger.

## Configuration

The `configuration` package contains definitions of Cordapp configuration access.

## Crypto

The `crypto` package contains services for performing cryptographic operations in flows (e.g. hashing, signing,
generating key pairs), as well as supporting types for carrying out those operations.

## Diagnostics

The `diagnostics` package contains services for finding out information about the cluster the CorDapp is deployed on.

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

## Membership

The `membership` package contains services for accessing the current virtual node's view of the membership group.

## Persistence

The `persistence` package contains services for accessing the database. This includes writing entities to the database,
reading entities from the database, and generating queries. Named queries and query filters are defined using the types
in the `query` package.

## Query

The `query` package contains interfaces that can be implemented to define a named query to be executed against the
database, as well as filters and post-processors that can be executed before the results are returned.

For executing these queries in flows, see the `persistence` package.

## Serialization

The `serialization` package contains service definitions for serializing and deserializing types. It includes a generic
interface applicable to any serialization scheme, and a more specific JSON serialization scheme with some extra
utilities.

## Time

The `time` package contains a service definition for accessing the system time.

