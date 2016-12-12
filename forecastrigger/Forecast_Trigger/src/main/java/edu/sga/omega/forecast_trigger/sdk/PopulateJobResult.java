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
public class PopulateJobResult implements org.apache.thrift.TBase<PopulateJobResult, PopulateJobResult._Fields>, java.io.Serializable, Cloneable, Comparable<PopulateJobResult> {
    // isset id assignments
    public static final Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> metaDataMap;
    private static final org.apache.thrift.protocol.TStruct STRUCT_DESC = new org.apache.thrift.protocol.TStruct("PopulateJobResult");
    private static final org.apache.thrift.protocol.TField TASK_CONFIG_FIELD_DESC = new org.apache.thrift.protocol.TField("taskConfig", org.apache.thrift.protocol.TType.STRUCT, (short) 2);
    private static final Map<Class<? extends IScheme>, SchemeFactory> schemes = new HashMap<Class<? extends IScheme>, SchemeFactory>();

    static {
        schemes.put(StandardScheme.class, new PopulateJobResultStandardSchemeFactory());
        schemes.put(TupleScheme.class, new PopulateJobResultTupleSchemeFactory());
    }

    static {
        Map<_Fields, org.apache.thrift.meta_data.FieldMetaData> tmpMap = new EnumMap<_Fields, org.apache.thrift.meta_data.FieldMetaData>(_Fields.class);
        tmpMap.put(_Fields.TASK_CONFIG, new org.apache.thrift.meta_data.FieldMetaData("taskConfig", org.apache.thrift.TFieldRequirementType.DEFAULT,
                new org.apache.thrift.meta_data.StructMetaData(org.apache.thrift.protocol.TType.STRUCT, TaskConfig.class)));
        metaDataMap = Collections.unmodifiableMap(tmpMap);
        org.apache.thrift.meta_data.FieldMetaData.addStructMetaDataMap(PopulateJobResult.class, metaDataMap);
    }

    public TaskConfig taskConfig; // required

    public PopulateJobResult() {
    }

    public PopulateJobResult(
            TaskConfig taskConfig) {
        this();
        this.taskConfig = taskConfig;
    }

    /**
     * Performs a deep copy on <i>other</i>.
     */
    public PopulateJobResult(PopulateJobResult other) {
        if (other.isSetTaskConfig()) {
            this.taskConfig = new TaskConfig(other.taskConfig);
        }
    }

    public PopulateJobResult deepCopy() {
        return new PopulateJobResult(this);
    }

    @Override
    public void clear() {
        this.taskConfig = null;
    }

    public TaskConfig getTaskConfig() {
        return this.taskConfig;
    }

    public PopulateJobResult setTaskConfig(TaskConfig taskConfig) {
        this.taskConfig = taskConfig;
        return this;
    }

    public void unsetTaskConfig() {
        this.taskConfig = null;
    }

    /** Returns true if field taskConfig is set (has been assigned a value) and false otherwise */
    public boolean isSetTaskConfig() {
        return this.taskConfig != null;
    }

    public void setTaskConfigIsSet(boolean value) {
        if (!value) {
            this.taskConfig = null;
        }
    }

    public void setFieldValue(_Fields field, Object value) {
        switch (field) {
            case TASK_CONFIG:
                if (value == null) {
                    unsetTaskConfig();
                } else {
                    setTaskConfig((TaskConfig) value);
                }
                break;

        }
    }

    public Object getFieldValue(_Fields field) {
        switch (field) {
            case TASK_CONFIG:
                return getTaskConfig();

        }
        throw new IllegalStateException();
    }

    /** Returns true if field corresponding to fieldID is set (has been assigned a value) and false otherwise */
    public boolean isSet(_Fields field) {
        if (field == null) {
            throw new IllegalArgumentException();
        }

        switch (field) {
            case TASK_CONFIG:
                return isSetTaskConfig();
        }
        throw new IllegalStateException();
    }

    @Override
    public boolean equals(Object that) {
        if (that == null)
            return false;
        if (that instanceof PopulateJobResult)
            return this.equals((PopulateJobResult) that);
        return false;
    }

    public boolean equals(PopulateJobResult that) {
        if (that == null)
            return false;

        boolean this_present_taskConfig = true && this.isSetTaskConfig();
        boolean that_present_taskConfig = true && that.isSetTaskConfig();
        if (this_present_taskConfig || that_present_taskConfig) {
            if (!(this_present_taskConfig && that_present_taskConfig))
                return false;
            if (!this.taskConfig.equals(that.taskConfig))
                return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        List<Object> list = new ArrayList<Object>();

        boolean present_taskConfig = true && (isSetTaskConfig());
        list.add(present_taskConfig);
        if (present_taskConfig)
            list.add(taskConfig);

        return list.hashCode();
    }

    @Override
    public int compareTo(PopulateJobResult other) {
        if (!getClass().equals(other.getClass())) {
            return getClass().getName().compareTo(other.getClass().getName());
        }

        int lastComparison = 0;

        lastComparison = Boolean.valueOf(isSetTaskConfig()).compareTo(other.isSetTaskConfig());
        if (lastComparison != 0) {
            return lastComparison;
        }
        if (isSetTaskConfig()) {
            lastComparison = org.apache.thrift.TBaseHelper.compareTo(this.taskConfig, other.taskConfig);
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
        StringBuilder sb = new StringBuilder("PopulateJobResult(");
        boolean first = true;

        sb.append("taskConfig:");
        if (this.taskConfig == null) {
            sb.append("null");
        } else {
            sb.append(this.taskConfig);
        }
        first = false;
        sb.append(")");
        return sb.toString();
    }

    public void validate() throws org.apache.thrift.TException {
        // check for required fields
        // check for sub-struct validity
        if (taskConfig != null) {
            taskConfig.validate();
        }
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
        TASK_CONFIG((short) 2, "taskConfig");

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
                case 2: // TASK_CONFIG
                    return TASK_CONFIG;
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

    private static class PopulateJobResultStandardSchemeFactory implements SchemeFactory {
        public PopulateJobResultStandardScheme getScheme() {
            return new PopulateJobResultStandardScheme();
        }
    }

    private static class PopulateJobResultStandardScheme extends StandardScheme<PopulateJobResult> {

        public void read(org.apache.thrift.protocol.TProtocol iprot, PopulateJobResult struct) throws org.apache.thrift.TException {
            org.apache.thrift.protocol.TField schemeField;
            iprot.readStructBegin();
            while (true) {
                schemeField = iprot.readFieldBegin();
                if (schemeField.type == org.apache.thrift.protocol.TType.STOP) {
                    break;
                }
                switch (schemeField.id) {
                    case 2: // TASK_CONFIG
                        if (schemeField.type == org.apache.thrift.protocol.TType.STRUCT) {
                            struct.taskConfig = new TaskConfig();
                            struct.taskConfig.read(iprot);
                            struct.setTaskConfigIsSet(true);
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

        public void write(org.apache.thrift.protocol.TProtocol oprot, PopulateJobResult struct) throws org.apache.thrift.TException {
            struct.validate();

            oprot.writeStructBegin(STRUCT_DESC);
            if (struct.taskConfig != null) {
                oprot.writeFieldBegin(TASK_CONFIG_FIELD_DESC);
                struct.taskConfig.write(oprot);
                oprot.writeFieldEnd();
            }
            oprot.writeFieldStop();
            oprot.writeStructEnd();
        }

    }

    private static class PopulateJobResultTupleSchemeFactory implements SchemeFactory {
        public PopulateJobResultTupleScheme getScheme() {
            return new PopulateJobResultTupleScheme();
        }
    }

    private static class PopulateJobResultTupleScheme extends TupleScheme<PopulateJobResult> {

        @Override
        public void write(org.apache.thrift.protocol.TProtocol prot, PopulateJobResult struct) throws org.apache.thrift.TException {
            TTupleProtocol oprot = (TTupleProtocol) prot;
            BitSet optionals = new BitSet();
            if (struct.isSetTaskConfig()) {
                optionals.set(0);
            }
            oprot.writeBitSet(optionals, 1);
            if (struct.isSetTaskConfig()) {
                struct.taskConfig.write(oprot);
            }
        }

        @Override
        public void read(org.apache.thrift.protocol.TProtocol prot, PopulateJobResult struct) throws org.apache.thrift.TException {
            TTupleProtocol iprot = (TTupleProtocol) prot;
            BitSet incoming = iprot.readBitSet(1);
            if (incoming.get(0)) {
                struct.taskConfig = new TaskConfig();
                struct.taskConfig.read(iprot);
                struct.setTaskConfigIsSet(true);
            }
        }
    }

}

