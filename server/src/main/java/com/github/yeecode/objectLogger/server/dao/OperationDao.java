
package com.github.yeecode.objectLogger.server.dao;

import com.github.yeecode.objectLogger.client.model.OperationModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperationDao {

    List<OperationModel> queryByFilter(OperationModel operationModel);

    Integer add(OperationModel logOperationModel);
}
