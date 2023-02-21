# Introduction

Q: Why do we have classes and not just interfaces?
A: because we wish to define new abstract data types which are serializable, and we want the calling code to be
able to construct them. We could potentially use factories with interfaces, but that's a bigger change than is justified
at this stage and arguably makes the API larger and harder to understand.

Q: What do we do about extension methods?
A: In some cases, move them to static class methods. In the cases where they are not strictly
   necessary, or overly endorse something we wish to be agnostic about such as the use of SHA256
   as an advisable short hash, delete them. We can provide a new CPK at some point with
   these extension methods, and they can be incorporated into `corda-runtime-os` for now.

Q: Should we be declaring interfaces for exceptions?
A: TBC. Not sure it's even possible, and seems a change we may not need at this point 

# crypto


## corda-api update prepared and builds, working on corda-runtime-os changes

* CompositeKey - easy interface, and added COMPOSITE_KEY_CHILDREN_LIMIT
* CordaOID - tricky. Top level constant declarations, so all references need to be to a new static class
* SecureHash - Class with standard and static method implementations.
* CryptoUtils - nasty. Some top level constant declarations, moved to new class MessageAuthenticationCode.
  Some assumptions about SHA256 being recommended we wish to avoid, so they have been deleted and need
  code changes. Many extension functions which we delete due to emphasising SHA256.
  Another extension functions moved to a static function in a new KeyUtils class.
  This means extensive changes to calling code that deals with public keys and hashing.
* DigitalSignature - an open class with an embedded open class, and a clash on "bytes" between the two levels.
  In the end though this doesn't involve much code change.
* SignatureSpec - awkward. A class that defines a number of static instances of the class and a subclass, and has
  methods. 
* ParameterizedSignatureSpec - straightforward data-class-like subclass of SignatureSpec
* KeySchemeCodes - top level constant string declarations, so will need calling code changes
* DigestAlgorithmName - awkward class with some constant instances and data class methods
* PublicKeyHash - class with methods defined, nothing seems too hard
* CompositeKeyNodeAndWeight - data class with a precondition, need to figure out how to handle preconditioins
* merkle/HashDigestConstants - top level string constants, okay but will need calling code changes
* merkle/IndexedMerkleLeaf - data class, with complex equals method
* merkle/MerkleProof - interface, should be straightforward
* merkle/MerkleProofRebuildFailureException - straightforward
* merkle/MerkleProofType - small enum, straightforward
* merkle/MerkleTree - interface with attributes, should be okay
* merkle/MerkleTreeHashDigest

## TODO

* exceptions/CryptoException 
* exceptions/CryptoRetryException 
* exceptions/CryptoSignatureException 
* exceptions/CryptoThrottlingException - looks fairly complicated. 

# crypto-extensions 

## Done

* DigestAlgorithmFactory - interface with data attribute, should be okay
* DigestAlgorithm.kt - easy interface only
* merkle/MerkleTreeHashDigestProvider - 2 interfaces, no big concerns


