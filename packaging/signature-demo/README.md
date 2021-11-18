# Corda package signature POC

## Overview
This project is a simple cli tool with 2 commands `sign` and `verify`, that can be used, respectively, to sign and 
then verify the signature on a Corda package (CPK, CPB, CPI).

## Build

```bash
gradlew install
```

## Usage

```bash
build/install/signature-demo/bin/signature-demo sign --help
```

or

```bash
build/install/signature-demo/bin/signature-demo verify --help
```

to see read the documentation about the specific cli commands, 
beware you'll need to setup your own keystore to be able to use them. 
Alternatively you can run

```bash
gradlew verifyCPK
```

This creates a keystore in `build/pki/build/pki/trusted_key.p12` containing a root CA certificate and key with subject 
"CN=Test Root CA, OU=R3, O=Corda, L=Dublin, C=IE" and a leaf certificate and key with subject 
"CN=Signing cert, OU=R3, O=Corda, L=Dublin, C=IE" signed by the root one.
It also creates a certificate store in `build/pki/build/pki/certstore.p12` with only the CA certificate of
"CN=Test Root CA, OU=R3, O=Corda, L=Dublin, C=IE". Then the cpk from the `:package:test:workflow-cpk` project is signed
with "CN=Signing cert, OU=R3, O=Corda, L=Dublin, C=IE" and the resulting artifact written to `build/pki/signed.cpk`.

Finally `build/pki/signed.cpk` is verified using `build/pki/build/pki/certstore.p12` as the CA certificate store
using the verification code in `net.corda.packaging.signature.demo.command.Verify`.

Alternatively

```bash
gradlew jarsignerVerifyCPK
```

uses `jarsigner` for the signature verification.

