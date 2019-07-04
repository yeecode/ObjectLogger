package com.github.yeecode.objectLogger.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.github.yeecode.objectLogger.client.model.AttributeModel;
import java.util.List;

@Mapper
public interface AttributeDao {

    List<AttributeModel> queryByFilter(AttributeModel attributeModel);

    Integer addBatch(@Param("attributeModelList") List<AttributeModel> attributeModelList);
}
