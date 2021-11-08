# Config Schema

This module contains the schema which represents configurations.

There will be several sub-sections of configuration, each specific to a unique area.  However, the root configuration
will always be `corda`.  

The following characteristics are important when considering the configuration:

- The configuration will not actually be in files but will be stored in a JSON/HOCON like manner and made available via
the configuration service.
- When updating configuration we expect that only one key will be changed at a time.
- Long term plans expect a service to validate the configuration in addition to simply read/write operations.
