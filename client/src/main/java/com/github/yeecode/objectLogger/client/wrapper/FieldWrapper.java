package com.github.yeecode.objectLogger.client.wrapper;

import com.github.yeecode.objectLogger.client.annotation.LogTag;

import java.lang.reflect.Field;

public class FieldWrapper {
    private String attributeName; // 属性名称
    private String attributeAlias; // 注解的属性名称,如果不存在则使用attributeName
    private Object oldValue; // 属性的旧值
    private Object newValue; // 属性的新值
    private String oldValueString; // 属性旧值字符串
    private String newValueString; // 属性新值字符串
    private boolean withLogTag; // 是否有注解
    private LogTag logTag; // 属性注解
    private boolean withExtendedType; // 是否是外部类型
    private String extendedType; // 外部类型具体值

    public FieldWrapper(Field field, Object oldValue, Object newValue) {
        this.attributeName = field.getName();
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.oldValueString = oldValue == null ? "" : oldValue.toString();
        this.newValueString = newValue == null ? "" : newValue.toString();
        this.logTag = field.getAnnotation(LogTag.class);
        this.withLogTag = logTag != null;
        this.attributeAlias = (withLogTag && logTag.alias().length() != 0) ? logTag.alias() : field.getName();
        this.withExtendedType = withLogTag && logTag.extendedType().length() != 0;
        this.extendedType = withExtendedType ? logTag.extendedType() : null;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    public Object getOldValue() {
        return oldValue;
    }

    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }

    public Object getNewValue() {
        return newValue;
    }

    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }

    public String getOldValueString() {
        return oldValueString;
    }

    public void setOldValueString(String oldValueString) {
        this.oldValueString = oldValueString;
    }

    public String getNewValueString() {
        return newValueString;
    }

    public void setNewValueString(String newValueString) {
        this.newValueString = newValueString;
    }

    public LogTag getLogTag() {
        return logTag;
    }

    public void setLogTag(LogTag logTag) {
        this.logTag = logTag;
    }

    public boolean isWithExtendedType() {
        return withExtendedType;
    }

    public void setWithExtendedType(boolean withExtendedType) {
        this.withExtendedType = withExtendedType;
    }

    public boolean isWithLogTag() {
        return withLogTag;
    }

    public void setWithLogTag(boolean withLogTag) {
        this.withLogTag = withLogTag;
    }

    public String getExtendedType() {
        return extendedType;
    }

    public void setExtendedType(String extendedType) {
        this.extendedType = extendedType;
    }

    public String getAttributeAlias() {
        return attributeAlias;
    }

    public void setAttributeAlias(String attributeAlias) {
        this.attributeAlias = attributeAlias;
    }
}
