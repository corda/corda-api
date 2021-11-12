# Config Schema

This module contains the schema which represents configurations.

The following characteristics are important when considering the configuration:

- The configuration will not actually be in files but will be stored in a JSON/HOCON like manner and made available via
the configuration service.
- When updating configuration we expect that only one key will be changed at a time.
- Long term plans expect a service to validate the configuration in addition to simply read/write operations.

## Virtual Node Configuration

There will be several sub-sections of configuration, each specific to a unique area.  However, the root configuration
will always be `corda`.

## Cordapp Configuration

Similarly to the virtual node configuration there will be cordapp configuration.  For these cases 
the root configuration will be `cordapp`.
