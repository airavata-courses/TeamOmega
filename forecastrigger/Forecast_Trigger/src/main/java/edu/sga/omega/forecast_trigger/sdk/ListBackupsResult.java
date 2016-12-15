/**
 * Autogenerated by Thrift Compiler (0.9.3)
 * <p>
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package edu.sga.omega.forecast_trigger.sdk;

import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-10-21")
public class ListBackupsResult implements org.apache.thrift.TBase<ListBackupsResult, ListBackupsResult._Fields>, java.io.Serializable, Cloneable, Comparable<ListBackupsResult> {
    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("ListBackupsResult");
    private static final org.apache.thrift.protocol.TField BACKUPS_FIELD_DESC = new org.apache.thrift.protocol.TField("backups", org.apache.thrift.protocol.TType.SET, (short) 1);
    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    static {
        schemes.put(StandardScheme.class, new ListBackupsResultStandardSchemeFactory());
        schemes.put(TupleScheme.class, new ListBackupsResultTupleSchemeFactory());
    }

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.BACKUPS, new org.apache.thrift.meta_data.FieldMetaData("backups", org.apache.thrift.TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.SetMetaData(org.apache.thrift.protocol.TType.SET,
                        new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING))));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(ListBackupsResult.class, metaDataMap);
    }

    public Set<String> backups; // required

    public ListBackupsResult() {
    }

    public ListBackupsResult(
            Set<String> backups) {
        this();
        this.backups = backups;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public ListBackupsResult(ListBackupsResult other) {
        if (other.isSetBackups()) {
            Set<String> __this__backups = new HashSet<String>(other.backups);
            this.backups = __this__backups;
        }
    }

    public ListBackupsResult deepCopy() {
        return new ListBackupsResult(this);
    }

    @Override
    public void clear() {
        this.backups = null;
    }

    public int getBackupsSize() {
        return (this.backups == null) ? 0 : this.backups.size();
    }

    public java.util.Iterator<String> getBackupsIterator() {
        return (this.backups == null) ? null : this.backups.iterator();
    }

    public void addToBackups(String elem) {
        if (this.backups == null) {
            this.backups = new HashSet<String>();
        }
        this.backups.add(elem);
    }

    public Set<String> getBackups() {
        return this.backups;
    }

    public ListBackupsResult setBackups(Set<String> backups) {
        this.backups = backups;
        return this;
    }

    public void unsetBackups() {
        this.backups = null;
    }

    /** Returns true if field backups is set (has been assigned a value) and false otherwise */
    public boolean isSetBackups() {
        return this.backups != null;
    }

    public void setBackupsIsSet(boolean value) {
        if (!value) {
            this.backups = null;
        }
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case BACKUPS:
                if (value == null) {
                    unsetBackups();
                } else {
                    setBackups((Set<String>) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case BACKUPS:
                return getBackups();

        }
        throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
        if (field == null) {
            throw new IllegalArgumentException();
        }

        switch (field) {
            case BACKUPS:
                return isSetBackups();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof ListBackupsResult)
            return this.equals((ListBackupsResult) that);
        return false;
    }

    public boolean equals(ListBackupsResult that) {
        if (that == null)
            return false;

        boolean this_present_backups = true && this.isSetBackups();
        boolean that_present_backups = true && that.isSetBackups();
        if (this_present_backups || that_present_backups) {
            if (!(this_present_backups && that_present_backups))
                return false;
            if (!this.backups.equals(that.backups))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        List<Object> list = new ArrayList<Object>();

        boolean present_backups = true && (isSetBackups());
        list.add(present_backups);
        if (present_backups)
            list.add(backups);

        return list.hashCode();
    }

    @Override
    public int compareTo(ListBackupsResult other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;

        lastComparison = Boolean.valueOf(isSetBackups()).compareTo(other.isSetBackups());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetBackups()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.backups, other.backups);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        return 0;
    }

    public _Fields fieldForId(int fieldId) {
        return _Fields.findByThriftId(fieldId);
    }

    public void read(org.apache.thrift.protocol.TProtocol iprot) throws org.apache.thrift.TException {
        schemes.get(iprot.getScheme()).getScheme().read(iprot, this);
    }

    public void write(org.apache.thrift.protocol.TProtocol oprot) throws org.apache.thrift.TException {
        schemes.get(oprot.getScheme()).getScheme().write(oprot, this);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("ListBackupsResult(");
        boolean first = true;

        sb.append("backups:");
        if (this.backups == null) {
            sb.append("null");
        } else {
            sb.append(this.backups);
        }
        first = false;
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        // check for sub-struct validity
    }

    private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException {
        try {
            write(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(out)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        try {
            read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        BACKUPS((short) 1, "backups");

        private static final Map<String, _Fields> byName = new HashMap<String, _Fields>();

        static {
            for (_Fields field : EnumSet.allOf(_Fields.class)) {
                byName.put(field.getFieldName(), field);
            }
        }

        private final short _thriftId;
        private final String _fieldName;

        _Fields(short thriftId, String fieldName) {
            _thriftId = thriftId;
            _fieldName = fieldName;
        }

        /**
         * Find the _Fields constant that matches fieldId, or null if its not found.
         */
        public static _Fields findByThriftId(int fieldId) {
            switch (fieldId) {
                case 1: // BACKUPS
                    return BACKUPS;
                default:
                    return null;
            }
        }

        /**
         * Find the _Fields constant that matches fieldId, throwing an exception
         * if it is not found.
         */
        public static _Fields findByThriftIdOrThrow(int fieldId) {
            _Fields fields = findByThriftId(fieldId);
            if (fields == null) throw new IllegalArgumentException("Field " + fieldId + " doesn't exist!");
            return fields;
        }

        /**
         * Find the _Fields constant that matches name, or null if its not found.
         */
        public static _Fields findByName(String name) {
            return byName.get(name);
        }

        public short getThriftFieldId() {
            return _thriftId;
        }

        public String getFieldName() {
            return _fieldName;
        }
    }

    private static class ListBackupsResultStandardSchemeFactory implements SchemeFactory {
        public ListBackupsResultStandardScheme getScheme() {
            return new ListBackupsResultStandardScheme();
        }
    }

    private static class ListBackupsResultStandardScheme extends StandardScheme<ListBackupsResult> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, ListBackupsResult struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // BACKUPS
                        if (schemeField.type == org.apache.thrift.protocol.TType.SET) {
                            {
                                org.apache.thrift.protocol.TSet _set254 = iprot.readSetBegin();
                                struct.backups = new HashSet<String>(2 * _set254.size);
                                String _elem255;
                                for (int _i256 = 0; _i256 < _set254.size; ++_i256) {
                                    _elem255 = iprot.readString();
                                    struct.backups.add(_elem255);
                                }
                                iprot.readSetEnd();
                            }
                            struct.setBackupsIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    default:
                        org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                }
                iprot.readFieldEnd();
            }
            iprot.readStructEnd();

            // check for required fields of primitive type, which can't be checked in the validate method
            struct.validate();
        }

        public void write(org.apache.thrift.protocol.TProtocol oprot, ListBackupsResult struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (struct.backups != null) {
                oprot.writeFieldBegin(BACKUPS_FIELD_DESC);
                {
                    oprot.writeSetBegin(new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, struct.backups.size()));
                    for (String _iter257 : struct.backups) {
                        oprot.writeString(_iter257);
                    }
                    oprot.writeSetEnd();
                }
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class ListBackupsResultTupleSchemeFactory implements SchemeFactory {
        public ListBackupsResultTupleScheme getScheme() {
            return new ListBackupsResultTupleScheme();
        }
    }

    private static class ListBackupsResultTupleScheme extends TupleScheme<ListBackupsResult> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, ListBackupsResult struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetBackups()) {
                optionals.set(0);
            }
            oprot.writeBitSet(optionals, 1);
            if (struct.isSetBackups()) {
                {
                    oprot.writeI32(struct.backups.size());
                    for (String _iter258 : struct.backups) {
                        oprot.writeString(_iter258);
                    }
                }
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, ListBackupsResult struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(1);
            if (incoming.get(0)) {
                {
                    org.apache.thrift.protocol.TSet _set259 = new org.apache.thrift.protocol.TSet(org.apache.thrift.protocol.TType.STRING, iprot.readI32());
                    struct.backups = new HashSet<String>(2 * _set259.size);
                    String _elem260;
                    for (int _i261 = 0; _i261 < _set259.size; ++_i261) {
                        _elem260 = iprot.readString();
                        struct.backups.add(_elem260);
                    }
                }
                struct.setBackupsIsSet(true);
            }
        }
    }

}
