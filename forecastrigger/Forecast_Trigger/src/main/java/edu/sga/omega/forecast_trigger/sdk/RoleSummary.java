/**
 * Autogenerated by Thrift Compiler (0.9.3)
 * <p>
 * DO NOT EDIT UNLESS YOU ARE SURE THAT YOU KNOW WHAT YOU ARE DOING
 *
 * @generated
 */
package edu.sga.omega.forecast_trigger.sdk;

import org.apache.thrift.EncodingUtils;
import org.apache.thrift.protocol.TTupleProtocol;
import org.apache.thrift.scheme.IScheme;
import org.apache.thrift.scheme.SchemeFactory;
import org.apache.thrift.scheme.StandardScheme;
import org.apache.thrift.scheme.TupleScheme;

import javax.annotation.Generated;
import java.util.*;

@SuppressWarnings({"cast", "rawtypes", "serial", "unchecked"})
@Generated(value = "Autogenerated by Thrift Compiler (0.9.3)", date = "2016-10-21")
public class RoleSummary implements org.apache.thrift.TBase<RoleSummary, RoleSummary._Fields>, java.io.Serializable, Cloneable, Comparable<RoleSummary> {
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("RoleSummary");
    private static final org.apache.thrift.protocol.TField ROLE_FIELD_DESC = new org.apache.thrift.protocol.TField("role", org.apache.thrift.protocol.TType.STRING, (short) 1);
    private static final org.apache.thrift.protocol.TField JOB_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("jobCount", org.apache.thrift.protocol.TType.I32, (short) 2);
    private static final org.apache.thrift.protocol.TField CRON_JOB_COUNT_FIELD_DESC = new org.apache.thrift.protocol.TField("cronJobCount", org.apache.thrift.protocol.TType.I32, (short) 3);
    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();
    // isset id assignments
    private static final int __JOBCOUNT_ISSET_ID = 0;
    private static final int __CRONJOBCOUNT_ISSET_ID = 1;

    static {
        schemes.put(StandardScheme.class, new RoleSummaryStandardSchemeFactory());
        schemes.put(TupleScheme.class, new RoleSummaryTupleSchemeFactory());
    }

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.ROLE, new org.apache.thrift.meta_data.FieldMetaData("role", org.apache.thrift.TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.STRING)));
        tmpMap.put(_Fields.JOB_COUNT, new org.apache.thrift.meta_data.FieldMetaData("jobCount", org.apache.thrift.TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        tmpMap.put(_Fields.CRON_JOB_COUNT, new org.apache.thrift.meta_data.FieldMetaData("cronJobCount", org.apache.thrift.TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.FieldValueMetaData(org.apache.thrift.protocol.TType.I32)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(RoleSummary.class, metaDataMap);
    }

    public String role; // required
    public int jobCount; // required
    public int cronJobCount; // required
    private byte __isset_bitfield = 0;

    public RoleSummary() {
    }

    public RoleSummary(
            String role,
            int jobCount,
            int cronJobCount) {
        this();
        this.role = role;
        this.jobCount = jobCount;
        setJobCountIsSet(true);
        this.cronJobCount = cronJobCount;
        setCronJobCountIsSet(true);
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public RoleSummary(RoleSummary other) {
        __isset_bitfield = other.__isset_bitfield;
        if (other.isSetRole()) {
            this.role = other.role;
        }
        this.jobCount = other.jobCount;
        this.cronJobCount = other.cronJobCount;
    }

    public RoleSummary deepCopy() {
        return new RoleSummary(this);
    }

    @Override
    public void clear() {
        this.role = null;
        setJobCountIsSet(false);
        this.jobCount = 0;
        setCronJobCountIsSet(false);
        this.cronJobCount = 0;
    }

    public String getRole() {
        return this.role;
    }

    public RoleSummary setRole(String role) {
        this.role = role;
        return this;
    }

    public void unsetRole() {
        this.role = null;
    }

    /** Returns true if field role is set (has been assigned a value) and false otherwise */
    public boolean isSetRole() {
        return this.role != null;
    }

    public void setRoleIsSet(boolean value) {
        if (!value) {
            this.role = null;
        }
    }

    public int getJobCount() {
        return this.jobCount;
    }

    public RoleSummary setJobCount(int jobCount) {
        this.jobCount = jobCount;
        setJobCountIsSet(true);
        return this;
    }

    public void unsetJobCount() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __JOBCOUNT_ISSET_ID);
    }

    /** Returns true if field jobCount is set (has been assigned a value) and false otherwise */
    public boolean isSetJobCount() {
        return EncodingUtils.testBit(__isset_bitfield, __JOBCOUNT_ISSET_ID);
    }

    public void setJobCountIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __JOBCOUNT_ISSET_ID, value);
    }

    public int getCronJobCount() {
        return this.cronJobCount;
    }

    public RoleSummary setCronJobCount(int cronJobCount) {
        this.cronJobCount = cronJobCount;
        setCronJobCountIsSet(true);
        return this;
    }

    public void unsetCronJobCount() {
        __isset_bitfield = EncodingUtils.clearBit(__isset_bitfield, __CRONJOBCOUNT_ISSET_ID);
    }

    /** Returns true if field cronJobCount is set (has been assigned a value) and false otherwise */
    public boolean isSetCronJobCount() {
        return EncodingUtils.testBit(__isset_bitfield, __CRONJOBCOUNT_ISSET_ID);
    }

    public void setCronJobCountIsSet(boolean value) {
        __isset_bitfield = EncodingUtils.setBit(__isset_bitfield, __CRONJOBCOUNT_ISSET_ID, value);
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case ROLE:
                if (value == null) {
                    unsetRole();
                } else {
                    setRole((String) value);
                }
                break;

            case JOB_COUNT:
                if (value == null) {
                    unsetJobCount();
                } else {
                    setJobCount((Integer) value);
                }
                break;

            case CRON_JOB_COUNT:
                if (value == null) {
                    unsetCronJobCount();
                } else {
                    setCronJobCount((Integer) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case ROLE:
                return getRole();

            case JOB_COUNT:
                return getJobCount();

            case CRON_JOB_COUNT:
                return getCronJobCount();

        }
        throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
        if (field == null) {
            throw new IllegalArgumentException();
        }

        switch (field) {
            case ROLE:
                return isSetRole();
            case JOB_COUNT:
                return isSetJobCount();
            case CRON_JOB_COUNT:
                return isSetCronJobCount();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof RoleSummary)
            return this.equals((RoleSummary) that);
        return false;
    }

    public boolean equals(RoleSummary that) {
        if (that == null)
            return false;

        boolean this_present_role = true && this.isSetRole();
        boolean that_present_role = true && that.isSetRole();
        if (this_present_role || that_present_role) {
            if (!(this_present_role && that_present_role))
                return false;
            if (!this.role.equals(that.role))
                return false;
        }

        boolean this_present_jobCount = true;
        boolean that_present_jobCount = true;
        if (this_present_jobCount || that_present_jobCount) {
            if (!(this_present_jobCount && that_present_jobCount))
                return false;
            if (this.jobCount != that.jobCount)
                return false;
        }

        boolean this_present_cronJobCount = true;
        boolean that_present_cronJobCount = true;
        if (this_present_cronJobCount || that_present_cronJobCount) {
            if (!(this_present_cronJobCount && that_present_cronJobCount))
                return false;
            if (this.cronJobCount != that.cronJobCount)
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        List<Object> list = new ArrayList<Object>();

        boolean present_role = true && (isSetRole());
        list.add(present_role);
        if (present_role)
            list.add(role);

        boolean present_jobCount = true;
        list.add(present_jobCount);
        if (present_jobCount)
            list.add(jobCount);

        boolean present_cronJobCount = true;
        list.add(present_cronJobCount);
        if (present_cronJobCount)
            list.add(cronJobCount);

        return list.hashCode();
    }

    @Override
    public int compareTo(RoleSummary other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;

        lastComparison = Boolean.valueOf(isSetRole()).compareTo(other.isSetRole());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetRole()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.role, other.role);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetJobCount()).compareTo(other.isSetJobCount());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetJobCount()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.jobCount, other.jobCount);
            if (lastComparison != 0) {
                return lastComparison;
            }
        }
        lastComparison = Boolean.valueOf(isSetCronJobCount()).compareTo(other.isSetCronJobCount());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetCronJobCount()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.cronJobCount, other.cronJobCount);
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
        StringBuilder sb = new StringBuilder("RoleSummary(");
        boolean first = true;

        sb.append("role:");
        if (this.role == null) {
            sb.append("null");
        } else {
            sb.append(this.role);
        }
        first = false;
        if (!first) sb.append(", ");
        sb.append("jobCount:");
        sb.append(this.jobCount);
        first = false;
        if (!first) sb.append(", ");
        sb.append("cronJobCount:");
        sb.append(this.cronJobCount);
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
            // it doesn't seem like you should have to do this, but java serialization is wacky, and doesn't call the default constructor.
            __isset_bitfield = 0;
            read(new org.apache.thrift.protocol.TCompactProtocol(new org.apache.thrift.transport.TIOStreamTransport(in)));
        } catch (org.apache.thrift.TException te) {
            throw new java.io.IOException(te);
        }
    }

    /** The set of fields this struct contains, along with convenience methods for finding and manipulating them. */
    public enum _Fields implements org.apache.thrift.TFieldIdEnum {
        ROLE((short) 1, "role"),
        JOB_COUNT((short) 2, "jobCount"),
        CRON_JOB_COUNT((short) 3, "cronJobCount");

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
                case 1: // ROLE
                    return ROLE;
                case 2: // JOB_COUNT
                    return JOB_COUNT;
                case 3: // CRON_JOB_COUNT
                    return CRON_JOB_COUNT;
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

    private static class RoleSummaryStandardSchemeFactory implements SchemeFactory {
        public RoleSummaryStandardScheme getScheme() {
            return new RoleSummaryStandardScheme();
        }
    }

    private static class RoleSummaryStandardScheme extends StandardScheme<RoleSummary> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, RoleSummary struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 1: // ROLE
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRING) {
                            struct.role = iprot.readString();
                            struct.setRoleIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 2: // JOB_COUNT
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.jobCount = iprot.readI32();
                            struct.setJobCountIsSet(true);
                        } else {
                            org.apache.thrift.protocol.TProtocolUtil.skip(iprot, schemeField.type);
                        }
                        break;
                    case 3: // CRON_JOB_COUNT
                        if (schemeField.type == org.apache.thrift.protocol.TType.I32) {
                            struct.cronJobCount = iprot.readI32();
                            struct.setCronJobCountIsSet(true);
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

        public void write(org.apache.thrift.protocol.TProtocol oprot, RoleSummary struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (struct.role != null) {
                oprot.writeFieldBegin(ROLE_FIELD_DESC);
                oprot.writeString(struct.role);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldBegin(JOB_COUNT_FIELD_DESC);
            oprot.writeI32(struct.jobCount);
            oprot.writeFieldEnd();
            oprot.writeFieldBegin(CRON_JOB_COUNT_FIELD_DESC);
            oprot.writeI32(struct.cronJobCount);
            oprot.writeFieldEnd();
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class RoleSummaryTupleSchemeFactory implements SchemeFactory {
        public RoleSummaryTupleScheme getScheme() {
            return new RoleSummaryTupleScheme();
        }
    }

    private static class RoleSummaryTupleScheme extends TupleScheme<RoleSummary> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, RoleSummary struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetRole()) {
                optionals.set(0);
            }
            if (struct.isSetJobCount()) {
                optionals.set(1);
            }
            if (struct.isSetCronJobCount()) {
                optionals.set(2);
            }
            oprot.writeBitSet(optionals, 3);
            if (struct.isSetRole()) {
                oprot.writeString(struct.role);
            }
            if (struct.isSetJobCount()) {
                oprot.writeI32(struct.jobCount);
            }
            if (struct.isSetCronJobCount()) {
                oprot.writeI32(struct.cronJobCount);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, RoleSummary struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(3);
            if (incoming.get(0)) {
                struct.role = iprot.readString();
                struct.setRoleIsSet(true);
            }
            if (incoming.get(1)) {
                struct.jobCount = iprot.readI32();
                struct.setJobCountIsSet(true);
            }
            if (incoming.get(2)) {
                struct.cronJobCount = iprot.readI32();
                struct.setCronJobCountIsSet(true);
            }
        }
    }

}

