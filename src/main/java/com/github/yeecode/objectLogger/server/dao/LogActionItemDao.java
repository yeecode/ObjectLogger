package com.github.yeecode.objectLogger.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.github.yeecode.objectLogger.client.model.ActionItemModel;
import java.util.List;

@Mapper
public interface LogActionItemDao {

    List<ActionItemModel> queryByFilter(ActionItemModel actionItemModel);

    Integer addBatch(@Param("logActionItemModelList") List<ActionItemModel> actionItemModelList);
}
