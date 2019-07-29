package com.github.yeecode.logger.server.dao;

import com.github.yeecode.logger.client.model.AttributeModel;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AttributeDao {

    List<AttributeModel> queryByOperationIdList(List<Integer> operationIdList);

    List<AttributeModel> queryByFilter(AttributeModel attributeModel);

    Integer addBatch(@Param("attributeModelList") List<AttributeModel> attributeModelList);
}
