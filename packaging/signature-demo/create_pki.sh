#!/bin/sh

set -e
BASE_DIR=build/pki
mkdir -p ${BASE_DIR}

KEYSTORE=${BASE_DIR}/trusted_key.p12
STOREPASS=password
STORETYPE=pkcs12
KEYPASS=password

REQUEST=request.csr
CERTIFICATE=${BASE_DIR}/certificate.der
CERTSTORE=${BASE_DIR}/certstore.p12

rm -f ${REQUEST} ${CERTIFICATE} ${CERTSTORE} ${KEYSTORE}

# Create root key
keytool -keystore ${KEYSTORE} \
    -storetype pkcs12 \
    -genkey \
    -alias root \
    -dname 'CN=Test Root CA, OU=R3, O=Corda, L=Dublin, C=IE' \
    -keyalg EC \
    -groupname secp256r1 \
    -validity 3650 \
    -ext BC:critical=ca:true \
    -ext KU=DigitalSignature,KeyCertSign \
    -ext EKU=codeSigning \
    -keypass ${KEYPASS} \
    -storepass ${STOREPASS} \
    -v

# Create signing key
keytool -keystore ${KEYSTORE} \
    -storetype pkcs12 \
    -genkey \
    -alias child \
    -dname 'CN=Signing cert, OU=R3, O=Corda, L=Dublin, C=IE' \
    -keyalg EC \
    -groupname secp256r1 \
    -validity 3650 \
    -ext KU=DigitalSignature \
    -ext EKU=codeSigning \
    -keypass ${KEYPASS} \
    -storepass ${STOREPASS} \
    -v

# Create certificate signing request
keytool -keystore ${KEYSTORE} \
        -storetype ${STORETYPE} \
        -certreq \
        -alias child \
        -keypass ${KEYPASS} \
        -storepass ${STOREPASS} \
        -file ${REQUEST}

# Create signing certificate
keytool -keystore ${KEYSTORE} \
        -storetype ${STORETYPE} \
        -gencert \
        -alias root \
        -keypass ${KEYPASS} \
        -storepass ${STOREPASS} \
        -infile ${REQUEST} \
        -outfile ${CERTIFICATE} \
        -dname 'CN=Signing cert, OU=R3, O=Corda, L=Dublin, C=IE' \
        -validity 3650 \
        -ext KU=DigitalSignature \
        -ext EKU=codeSigning

# Import signing certificate
keytool -keystore ${KEYSTORE} \
        -storetype ${STORETYPE} \
        -importcert \
        -alias child \
        -keypass ${KEYPASS} \
        -storepass ${STOREPASS} \
        -file ${CERTIFICATE}

# create certificate store containing only root certificate
for ALIAS_NAME in root
do
  keytool -keystore ${KEYSTORE} \
          -storetype ${STORETYPE} \
          -exportcert \
          -alias ${ALIAS_NAME} \
          -keypass ${KEYPASS} \
          -storepass ${STOREPASS} \
          -file ${CERTIFICATE}
  keytool -keystore ${CERTSTORE} \
          -storetype ${STORETYPE} \
          -importcert \
          -noprompt \
          -alias ${ALIAS_NAME} \
          -storepass ${STOREPASS} \
          -file ${CERTIFICATE}
done

rm -f ${REQUEST} ${CERTIFICATE}

