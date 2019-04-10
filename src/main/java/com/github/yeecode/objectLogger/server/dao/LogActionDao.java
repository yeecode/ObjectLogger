
package com.github.yeecode.objectLogger.server.dao;

import com.github.yeecode.objectLogger.client.model.ActionModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogActionDao {
    /**
     * 根据过滤条件查询LogActionModel对象
     *
     * @param actionModel 包含过滤条件的传入对象
     * @return 符合结果的对象集合
     */
    List<ActionModel> queryByFilter(ActionModel actionModel);

    /**
     * 插入一条对象
     *
     * @param logActionModel 要插入的对象
     * @return 插入的对象的编号
     */
    Integer add(ActionModel logActionModel);
}
