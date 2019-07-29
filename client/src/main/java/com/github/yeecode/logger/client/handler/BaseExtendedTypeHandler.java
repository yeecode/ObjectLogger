package com.github.yeecode.logger.client.handler;


import com.github.yeecode.logger.client.model.BaseAttributeModel;
import org.springframework.stereotype.Component;

/**
 * When ObjectLogger processes this property, it passes information about the property into the `handleAttributeChange'method of the extended bean.
 * The interface Base extended type handler.
 */
@Component
public interface BaseExtendedTypeHandler {
    /**
     * Handle attribute change base attribute model.
     * LogTag(alias = "UserId", extendedType = "userIdType")
     *
     * @param extendedType   Extended Type.In this example, userIdType.
     * @param attributeName  Attribute Name. In this example,userId.
     * @param attributeAlias Attribute alias, from @LogTag. In this example,UserId.
     * @param oldValue       Old value of the attribute.
     * @param newValue       New value of the attribute.
     * @return the base attribute model
     */
    BaseAttributeModel handleAttributeChange(String extendedType, String attributeName, String attributeAlias, Object oldValue, Object newValue);
}
