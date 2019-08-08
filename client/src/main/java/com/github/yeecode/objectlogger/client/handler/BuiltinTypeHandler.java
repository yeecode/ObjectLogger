package com.github.yeecode.objectlogger.client.handler;

import com.github.yeecode.objectlogger.client.model.BaseAttributeModel;
import com.github.yeecode.objectlogger.client.richText.Html2Text;
import com.github.yeecode.objectlogger.client.wrapper.FieldWrapper;
import com.github.yeecode.objectlogger.client.richText.RichTextHandler;

public enum BuiltinTypeHandler {
    NORMAL {
        @Override
        public BaseAttributeModel handlerAttributeChange(FieldWrapper fieldWrapper) {
            BaseAttributeModel baseAttributeModel = new BaseAttributeModel();
            baseAttributeModel.setOldValue(fieldWrapper.getOldValueString());
            baseAttributeModel.setNewValue(fieldWrapper.getNewValueString());
            return baseAttributeModel;
        }
    },
    TEXT {
        @Override
        public BaseAttributeModel handlerAttributeChange(FieldWrapper fieldWrapper) {
            String simpleOldValue = Html2Text.simpleHtml(fieldWrapper.getOldValueString());
            String simpleNewValue = Html2Text.simpleHtml(fieldWrapper.getNewValueString());
            // 去除格式，只留下可显示部分
            if (simpleOldValue == null || simpleNewValue == null || simpleOldValue.equals(simpleNewValue)) {
                // 可显示部分无不同，算是相同
                return null;
            } else {
                BaseAttributeModel baseAttributeModel = new BaseAttributeModel();
                baseAttributeModel.setOldValue(fieldWrapper.getOldValueString());
                baseAttributeModel.setNewValue(fieldWrapper.getNewValueString());
                baseAttributeModel.setDiffValue(RichTextHandler.diffText(fieldWrapper.getOldValueString(), fieldWrapper.getNewValueString()));
                return baseAttributeModel;
            }
        }
    };

    public abstract BaseAttributeModel handlerAttributeChange(FieldWrapper fieldWrapper);
}
