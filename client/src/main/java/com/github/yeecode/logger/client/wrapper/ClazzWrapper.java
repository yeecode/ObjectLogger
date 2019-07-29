package com.github.yeecode.logger.client.wrapper;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClazzWrapper {
    private List<Field> fieldList;

    public ClazzWrapper(Class clazz) {
        this.fieldList = getFields(clazz);
    }

    public List<Field> getFieldList() {
        return fieldList;
    }

    private List<Field> getFields(Class clazz) {
        List<Field> fieldList = new ArrayList<>();
        return getFields(fieldList, clazz);
    }

    private List<Field> getFields(List<Field> fieldList, Class clazz) {
        fieldList.addAll(Arrays.asList(clazz.getDeclaredFields()));
        Class superClazz = clazz.getSuperclass();
        if (superClazz != null) {
            getFields(fieldList, superClazz);
        }
        return fieldList;
    }
}
