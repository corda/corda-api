/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package net.corda.p2p.crypto;

import org.apache.avro.generic.GenericArray;
import org.apache.avro.specific.SpecificData;
import org.apache.avro.util.Utf8;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@org.apache.avro.specific.AvroGenerated
public class CommonHeader extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = 8240124788006063777L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"CommonHeader\",\"namespace\":\"net.corda.p2p.crypto\",\"fields\":[{\"name\":\"messageType\",\"type\":{\"type\":\"enum\",\"name\":\"MessageType\",\"symbols\":[\"INITIATOR_HELLO\",\"RESPONDER_HELLO\",\"INITIATOR_HANDSHAKE\",\"RESPONDER_HANDSHAKE\",\"DATA\"]}},{\"name\":\"protocolVersion\",\"type\":\"int\"},{\"name\":\"sessionId\",\"type\":{\"type\":\"string\",\"avro.java.string\":\"String\"}},{\"name\":\"sequenceNo\",\"type\":\"long\"},{\"name\":\"timestamp\",\"type\":\"long\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<CommonHeader> ENCODER =
      new BinaryMessageEncoder<CommonHeader>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<CommonHeader> DECODER =
      new BinaryMessageDecoder<CommonHeader>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageEncoder instance used by this class.
   * @return the message encoder used by this class
   */
  public static BinaryMessageEncoder<CommonHeader> getEncoder() {
    return ENCODER;
  }

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   * @return the message decoder used by this class
   */
  public static BinaryMessageDecoder<CommonHeader> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   * @return a BinaryMessageDecoder instance for this class backed by the given SchemaStore
   */
  public static BinaryMessageDecoder<CommonHeader> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<CommonHeader>(MODEL$, SCHEMA$, resolver);
  }

  /**
   * Serializes this CommonHeader to a ByteBuffer.
   * @return a buffer holding the serialized data for this instance
   * @throws java.io.IOException if this instance could not be serialized
   */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /**
   * Deserializes a CommonHeader from a ByteBuffer.
   * @param b a byte buffer holding serialized data for an instance of this class
   * @return a CommonHeader instance decoded from the given buffer
   * @throws java.io.IOException if the given bytes could not be deserialized into an instance of this class
   */
  public static CommonHeader fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

   private net.corda.p2p.crypto.MessageType messageType;
   private int protocolVersion;
   private java.lang.String sessionId;
   private long sequenceNo;
   private long timestamp;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public CommonHeader() {}

  /**
   * All-args constructor.
   * @param messageType The new value for messageType
   * @param protocolVersion The new value for protocolVersion
   * @param sessionId The new value for sessionId
   * @param sequenceNo The new value for sequenceNo
   * @param timestamp The new value for timestamp
   */
  public CommonHeader(net.corda.p2p.crypto.MessageType messageType, java.lang.Integer protocolVersion, java.lang.String sessionId, java.lang.Long sequenceNo, java.lang.Long timestamp) {
    this.messageType = messageType;
    this.protocolVersion = protocolVersion;
    this.sessionId = sessionId;
    this.sequenceNo = sequenceNo;
    this.timestamp = timestamp;
  }

  public org.apache.avro.specific.SpecificData getSpecificData() { return MODEL$; }
  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return messageType;
    case 1: return protocolVersion;
    case 2: return sessionId;
    case 3: return sequenceNo;
    case 4: return timestamp;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: messageType = (net.corda.p2p.crypto.MessageType)value$; break;
    case 1: protocolVersion = (java.lang.Integer)value$; break;
    case 2: sessionId = value$ != null ? value$.toString() : null; break;
    case 3: sequenceNo = (java.lang.Long)value$; break;
    case 4: timestamp = (java.lang.Long)value$; break;
    default: throw new IndexOutOfBoundsException("Invalid index: " + field$);
    }
  }

  /**
   * Gets the value of the 'messageType' field.
   * @return The value of the 'messageType' field.
   */
  public net.corda.p2p.crypto.MessageType getMessageType() {
    return messageType;
  }


  /**
   * Sets the value of the 'messageType' field.
   * @param value the value to set.
   */
  public void setMessageType(net.corda.p2p.crypto.MessageType value) {
    this.messageType = value;
  }

  /**
   * Gets the value of the 'protocolVersion' field.
   * @return The value of the 'protocolVersion' field.
   */
  public int getProtocolVersion() {
    return protocolVersion;
  }


  /**
   * Sets the value of the 'protocolVersion' field.
   * @param value the value to set.
   */
  public void setProtocolVersion(int value) {
    this.protocolVersion = value;
  }

  /**
   * Gets the value of the 'sessionId' field.
   * @return The value of the 'sessionId' field.
   */
  public java.lang.String getSessionId() {
    return sessionId;
  }


  /**
   * Sets the value of the 'sessionId' field.
   * @param value the value to set.
   */
  public void setSessionId(java.lang.String value) {
    this.sessionId = value;
  }

  /**
   * Gets the value of the 'sequenceNo' field.
   * @return The value of the 'sequenceNo' field.
   */
  public long getSequenceNo() {
    return sequenceNo;
  }


  /**
   * Sets the value of the 'sequenceNo' field.
   * @param value the value to set.
   */
  public void setSequenceNo(long value) {
    this.sequenceNo = value;
  }

  /**
   * Gets the value of the 'timestamp' field.
   * @return The value of the 'timestamp' field.
   */
  public long getTimestamp() {
    return timestamp;
  }


  /**
   * Sets the value of the 'timestamp' field.
   * @param value the value to set.
   */
  public void setTimestamp(long value) {
    this.timestamp = value;
  }

  /**
   * Creates a new CommonHeader RecordBuilder.
   * @return A new CommonHeader RecordBuilder
   */
  public static net.corda.p2p.crypto.CommonHeader.Builder newBuilder() {
    return new net.corda.p2p.crypto.CommonHeader.Builder();
  }

  /**
   * Creates a new CommonHeader RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new CommonHeader RecordBuilder
   */
  public static net.corda.p2p.crypto.CommonHeader.Builder newBuilder(net.corda.p2p.crypto.CommonHeader.Builder other) {
    if (other == null) {
      return new net.corda.p2p.crypto.CommonHeader.Builder();
    } else {
      return new net.corda.p2p.crypto.CommonHeader.Builder(other);
    }
  }

  /**
   * Creates a new CommonHeader RecordBuilder by copying an existing CommonHeader instance.
   * @param other The existing instance to copy.
   * @return A new CommonHeader RecordBuilder
   */
  public static net.corda.p2p.crypto.CommonHeader.Builder newBuilder(net.corda.p2p.crypto.CommonHeader other) {
    if (other == null) {
      return new net.corda.p2p.crypto.CommonHeader.Builder();
    } else {
      return new net.corda.p2p.crypto.CommonHeader.Builder(other);
    }
  }

  /**
   * RecordBuilder for CommonHeader instances.
   */
  @org.apache.avro.specific.AvroGenerated
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<CommonHeader>
    implements org.apache.avro.data.RecordBuilder<CommonHeader> {

    private net.corda.p2p.crypto.MessageType messageType;
    private int protocolVersion;
    private java.lang.String sessionId;
    private long sequenceNo;
    private long timestamp;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(net.corda.p2p.crypto.CommonHeader.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.messageType)) {
        this.messageType = data().deepCopy(fields()[0].schema(), other.messageType);
        fieldSetFlags()[0] = other.fieldSetFlags()[0];
      }
      if (isValidValue(fields()[1], other.protocolVersion)) {
        this.protocolVersion = data().deepCopy(fields()[1].schema(), other.protocolVersion);
        fieldSetFlags()[1] = other.fieldSetFlags()[1];
      }
      if (isValidValue(fields()[2], other.sessionId)) {
        this.sessionId = data().deepCopy(fields()[2].schema(), other.sessionId);
        fieldSetFlags()[2] = other.fieldSetFlags()[2];
      }
      if (isValidValue(fields()[3], other.sequenceNo)) {
        this.sequenceNo = data().deepCopy(fields()[3].schema(), other.sequenceNo);
        fieldSetFlags()[3] = other.fieldSetFlags()[3];
      }
      if (isValidValue(fields()[4], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[4].schema(), other.timestamp);
        fieldSetFlags()[4] = other.fieldSetFlags()[4];
      }
    }

    /**
     * Creates a Builder by copying an existing CommonHeader instance
     * @param other The existing instance to copy.
     */
    private Builder(net.corda.p2p.crypto.CommonHeader other) {
      super(SCHEMA$);
      if (isValidValue(fields()[0], other.messageType)) {
        this.messageType = data().deepCopy(fields()[0].schema(), other.messageType);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.protocolVersion)) {
        this.protocolVersion = data().deepCopy(fields()[1].schema(), other.protocolVersion);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.sessionId)) {
        this.sessionId = data().deepCopy(fields()[2].schema(), other.sessionId);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.sequenceNo)) {
        this.sequenceNo = data().deepCopy(fields()[3].schema(), other.sequenceNo);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[4].schema(), other.timestamp);
        fieldSetFlags()[4] = true;
      }
    }

    /**
      * Gets the value of the 'messageType' field.
      * @return The value.
      */
    public net.corda.p2p.crypto.MessageType getMessageType() {
      return messageType;
    }


    /**
      * Sets the value of the 'messageType' field.
      * @param value The value of 'messageType'.
      * @return This builder.
      */
    public net.corda.p2p.crypto.CommonHeader.Builder setMessageType(net.corda.p2p.crypto.MessageType value) {
      validate(fields()[0], value);
      this.messageType = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'messageType' field has been set.
      * @return True if the 'messageType' field has been set, false otherwise.
      */
    public boolean hasMessageType() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'messageType' field.
      * @return This builder.
      */
    public net.corda.p2p.crypto.CommonHeader.Builder clearMessageType() {
      messageType = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'protocolVersion' field.
      * @return The value.
      */
    public int getProtocolVersion() {
      return protocolVersion;
    }


    /**
      * Sets the value of the 'protocolVersion' field.
      * @param value The value of 'protocolVersion'.
      * @return This builder.
      */
    public net.corda.p2p.crypto.CommonHeader.Builder setProtocolVersion(int value) {
      validate(fields()[1], value);
      this.protocolVersion = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'protocolVersion' field has been set.
      * @return True if the 'protocolVersion' field has been set, false otherwise.
      */
    public boolean hasProtocolVersion() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'protocolVersion' field.
      * @return This builder.
      */
    public net.corda.p2p.crypto.CommonHeader.Builder clearProtocolVersion() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'sessionId' field.
      * @return The value.
      */
    public java.lang.String getSessionId() {
      return sessionId;
    }


    /**
      * Sets the value of the 'sessionId' field.
      * @param value The value of 'sessionId'.
      * @return This builder.
      */
    public net.corda.p2p.crypto.CommonHeader.Builder setSessionId(java.lang.String value) {
      validate(fields()[2], value);
      this.sessionId = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'sessionId' field has been set.
      * @return True if the 'sessionId' field has been set, false otherwise.
      */
    public boolean hasSessionId() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'sessionId' field.
      * @return This builder.
      */
    public net.corda.p2p.crypto.CommonHeader.Builder clearSessionId() {
      sessionId = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /**
      * Gets the value of the 'sequenceNo' field.
      * @return The value.
      */
    public long getSequenceNo() {
      return sequenceNo;
    }


    /**
      * Sets the value of the 'sequenceNo' field.
      * @param value The value of 'sequenceNo'.
      * @return This builder.
      */
    public net.corda.p2p.crypto.CommonHeader.Builder setSequenceNo(long value) {
      validate(fields()[3], value);
      this.sequenceNo = value;
      fieldSetFlags()[3] = true;
      return this;
    }

    /**
      * Checks whether the 'sequenceNo' field has been set.
      * @return True if the 'sequenceNo' field has been set, false otherwise.
      */
    public boolean hasSequenceNo() {
      return fieldSetFlags()[3];
    }


    /**
      * Clears the value of the 'sequenceNo' field.
      * @return This builder.
      */
    public net.corda.p2p.crypto.CommonHeader.Builder clearSequenceNo() {
      fieldSetFlags()[3] = false;
      return this;
    }

    /**
      * Gets the value of the 'timestamp' field.
      * @return The value.
      */
    public long getTimestamp() {
      return timestamp;
    }


    /**
      * Sets the value of the 'timestamp' field.
      * @param value The value of 'timestamp'.
      * @return This builder.
      */
    public net.corda.p2p.crypto.CommonHeader.Builder setTimestamp(long value) {
      validate(fields()[4], value);
      this.timestamp = value;
      fieldSetFlags()[4] = true;
      return this;
    }

    /**
      * Checks whether the 'timestamp' field has been set.
      * @return True if the 'timestamp' field has been set, false otherwise.
      */
    public boolean hasTimestamp() {
      return fieldSetFlags()[4];
    }


    /**
      * Clears the value of the 'timestamp' field.
      * @return This builder.
      */
    public net.corda.p2p.crypto.CommonHeader.Builder clearTimestamp() {
      fieldSetFlags()[4] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public CommonHeader build() {
      try {
        CommonHeader record = new CommonHeader();
        record.messageType = fieldSetFlags()[0] ? this.messageType : (net.corda.p2p.crypto.MessageType) defaultValue(fields()[0]);
        record.protocolVersion = fieldSetFlags()[1] ? this.protocolVersion : (java.lang.Integer) defaultValue(fields()[1]);
        record.sessionId = fieldSetFlags()[2] ? this.sessionId : (java.lang.String) defaultValue(fields()[2]);
        record.sequenceNo = fieldSetFlags()[3] ? this.sequenceNo : (java.lang.Long) defaultValue(fields()[3]);
        record.timestamp = fieldSetFlags()[4] ? this.timestamp : (java.lang.Long) defaultValue(fields()[4]);
        return record;
      } catch (org.apache.avro.AvroMissingFieldException e) {
        throw e;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<CommonHeader>
    WRITER$ = (org.apache.avro.io.DatumWriter<CommonHeader>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<CommonHeader>
    READER$ = (org.apache.avro.io.DatumReader<CommonHeader>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

  @Override protected boolean hasCustomCoders() { return true; }

  @Override public void customEncode(org.apache.avro.io.Encoder out)
    throws java.io.IOException
  {
    out.writeEnum(this.messageType.ordinal());

    out.writeInt(this.protocolVersion);

    out.writeString(this.sessionId);

    out.writeLong(this.sequenceNo);

    out.writeLong(this.timestamp);

  }

  @Override public void customDecode(org.apache.avro.io.ResolvingDecoder in)
    throws java.io.IOException
  {
    org.apache.avro.Schema.Field[] fieldOrder = in.readFieldOrderIfDiff();
    if (fieldOrder == null) {
      this.messageType = net.corda.p2p.crypto.MessageType.values()[in.readEnum()];

      this.protocolVersion = in.readInt();

      this.sessionId = in.readString();

      this.sequenceNo = in.readLong();

      this.timestamp = in.readLong();

    } else {
      for (int i = 0; i < 5; i++) {
        switch (fieldOrder[i].pos()) {
        case 0:
          this.messageType = net.corda.p2p.crypto.MessageType.values()[in.readEnum()];
          break;

        case 1:
          this.protocolVersion = in.readInt();
          break;

        case 2:
          this.sessionId = in.readString();
          break;

        case 3:
          this.sequenceNo = in.readLong();
          break;

        case 4:
          this.timestamp = in.readLong();
          break;

        default:
          throw new java.io.IOException("Corrupt ResolvingDecoder.");
        }
      }
    }
  }
}










