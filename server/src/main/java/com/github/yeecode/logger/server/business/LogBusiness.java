package com.github.yeecode.logger.server.business;

import com.github.yeecode.logger.client.model.AttributeModel;
import com.github.yeecode.logger.client.model.OperationModel;
import com.github.yeecode.logger.client.service.LogServer;
import com.github.yeecode.logger.server.constant.RespConstant;
import com.github.yeecode.logger.server.dao.AttributeDao;
import com.github.yeecode.logger.server.dao.OperationDao;
import com.github.yeecode.logger.server.form.OperationForm;
import com.github.yeecode.logger.server.util.RespUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LogBusiness {
    private static final Log LOG = LogFactory.getLog(LogBusiness.class);

    @Autowired
    private LogServer logServer;
    @Autowired
    private OperationDao operationDao;
    @Autowired
    private AttributeDao attributeDao;

    public Map<String, Object> add(String logJsonString) {
        try {
            OperationModel operationModel = logServer.resolveOperationModel(logJsonString);
            operationDao.add(operationModel);
            Integer operationId = operationModel.getId();

            List<AttributeModel> attributeModelList = operationModel.getAttributeModelList();
            for (AttributeModel attributeModel : attributeModelList) {
                attributeModel.setId(null);
                attributeModel.setOperationId(operationId);
            }

            if (!CollectionUtils.isEmpty(attributeModelList)) {
                attributeDao.addBatch(attributeModelList);
            }

            return RespUtil.getSuccessMap();
        } catch (Exception ex) {
            LOG.error("ObjectLogger ERROR : add log error,", ex);
            return RespUtil.getCommonErrorMap(RespConstant.INSERT_EXCEPTION);
        }
    }

    public Map<String, Object> query(OperationForm operationForm) {
        try {
            OperationModel operationFilterModel = new OperationModel();
            if (operationForm.getId() != null) {
                operationFilterModel.setId(Integer.parseInt(operationForm.getId()));
            }
            operationFilterModel.setAppName(operationForm.getAppName());
            operationFilterModel.setObjectName(operationForm.getObjectName());
            if (operationForm.getObjectId() != null) {
                operationFilterModel.setObjectId(Integer.parseInt(operationForm.getObjectId()));
            }
            operationFilterModel.setOperator(operationForm.getOperator());
            operationFilterModel.setOperationName(operationForm.getOperationName());
            operationFilterModel.setOperationAlias(operationForm.getOperationAlias());
            List<OperationModel> operationModelList = operationDao.queryByFilter(operationFilterModel);

            List<Integer> operationIdList = new ArrayList<>();
            for (OperationModel operationModel : operationModelList) {
                operationIdList.add(operationModel.getId());
            }
            List<AttributeModel> attributeModelList = attributeDao.queryByOperationIdList(operationIdList);

            Map<Integer, List<AttributeModel>> attributeModelMap = new HashMap<>();
            for (AttributeModel attributeModel : attributeModelList) {
                attributeModelMap.putIfAbsent(attributeModel.getOperationId(), new ArrayList<>());
                attributeModelMap.get(attributeModel.getOperationId()).add(attributeModel);
            }

            for (OperationModel operationModel : operationModelList) {
                if (attributeModelMap.containsKey(operationModel.getId())) {
                    operationModel.getAttributeModelList().addAll(attributeModelMap.get(operationModel.getId()));
                }
            }
            return RespUtil.getSuccessMap(operationModelList);
        } catch (Exception ex) {
            LOG.error("ObjectLogger ERROR : query log error,", ex);
            return RespUtil.getCommonErrorMap(RespConstant.QUERY_EXCEPTION);
        }
    }
}
