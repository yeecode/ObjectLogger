package com.github.yeecode.objectLogger.server.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.github.yeecode.objectLogger.client.model.ActionItemModel;
import java.util.List;

@Mapper
public interface LogActionItemDao {
    /**
     * 根据过滤条件查询响应的logActionItemModel
     *
     * @param actionItemModel 包含过滤条件的对象
     * @return 结果列表
     */
    List<ActionItemModel> queryByFilter(ActionItemModel actionItemModel);

    /**
     * 批量增加logActionItemModel对象
     *
     * @param actionItemModelList 要批量插入的对象
     * @return 批量插入的条数
     */
    Integer addBatch(@Param("logActionItemModelList") List<ActionItemModel> actionItemModelList);
}
