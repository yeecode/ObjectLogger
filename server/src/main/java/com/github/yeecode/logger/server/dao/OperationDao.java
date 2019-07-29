
package com.github.yeecode.logger.server.dao;

import com.github.yeecode.logger.client.model.OperationModel;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OperationDao {

    List<OperationModel> queryByFilter(OperationModel operationModel);

    Integer add(OperationModel logOperationModel);
}
