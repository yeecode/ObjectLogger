
package com.github.yeecode.objectLogger.server.dao;

import com.github.yeecode.objectLogger.client.model.ActionModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface LogActionDao {

    List<ActionModel> queryByFilter(ActionModel actionModel);

    Integer add(ActionModel logActionModel);
}
