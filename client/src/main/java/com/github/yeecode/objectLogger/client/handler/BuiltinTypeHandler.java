package com.github.yeecode.objectLogger.client.handler;

import com.github.yeecode.objectLogger.client.model.BaseActionItemModel;
import com.github.yeecode.objectLogger.client.richText.Html2Text;
import com.github.yeecode.objectLogger.client.richText.RichTextHandler;
import com.github.yeecode.objectLogger.client.wrapper.FieldWrapper;

public enum BuiltinTypeHandler {
    NORMAL {
        @Override
        public BaseActionItemModel handlerAttributeChange(FieldWrapper fieldWrapper) {
            BaseActionItemModel baseActionItemModel = new BaseActionItemModel();
            baseActionItemModel.setOldValue(fieldWrapper.getOldValueString());
            baseActionItemModel.setNewValue(fieldWrapper.getNewValueString());
            return baseActionItemModel;
        }
    },
    TEXT {
        @Override
        public BaseActionItemModel handlerAttributeChange(FieldWrapper fieldWrapper) {
            String simpleOldValue = Html2Text.simpleHtml(fieldWrapper.getOldValueString());
            String simpleNewValue = Html2Text.simpleHtml(fieldWrapper.getNewValueString());
            // 去除格式，只留下可显示部分
            if (simpleOldValue == null || simpleNewValue == null || simpleOldValue.equals(simpleNewValue)) {
                // 可显示部分无不同，算是相同
                return null;
            } else {
                BaseActionItemModel baseActionItemModel = new BaseActionItemModel();
                baseActionItemModel.setOldValue(fieldWrapper.getOldValueString());
                baseActionItemModel.setNewValue(fieldWrapper.getNewValueString());
                baseActionItemModel.setDiffValue(RichTextHandler.diffText(fieldWrapper.getOldValueString(), fieldWrapper.getNewValueString()));
                return baseActionItemModel;
            }
        }
    };

    public abstract BaseActionItemModel handlerAttributeChange(FieldWrapper fieldWrapper);
}
