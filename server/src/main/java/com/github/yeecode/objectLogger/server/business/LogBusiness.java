package com.github.yeecode.objectLogger.server.business;

import com.github.yeecode.objectLogger.client.model.ActionItemModel;
import com.github.yeecode.objectLogger.client.model.ActionModel;
import com.github.yeecode.objectLogger.client.service.LogServer;
import com.github.yeecode.objectLogger.server.constant.RespConstant;
import com.github.yeecode.objectLogger.server.dao.LogActionDao;
import com.github.yeecode.objectLogger.server.dao.LogActionItemDao;
import com.github.yeecode.objectLogger.server.form.ActionForm;
import com.github.yeecode.objectLogger.server.form.AddLogForm;
import com.github.yeecode.objectLogger.server.util.RespUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

@Service
public class LogBusiness {
    private static final Log LOG = LogFactory.getLog(LogBusiness.class);

    @Autowired
    private LogServer logServer;
    @Autowired
    private LogActionDao logActionDao;
    @Autowired
    private LogActionItemDao logActionItemDao;

    public Map<String, Object> add(AddLogForm addLogForm) {
        try {
            String logJsonString = addLogForm.getLogJsonString();

            ActionModel actionModel = logServer.resolveActionModel(logJsonString);
            logActionDao.add(actionModel);
            Integer logActionId = actionModel.getId();

            List<ActionItemModel> logActionItemModelList = actionModel.getActionItemModelList();
            for (ActionItemModel logActionItemModel : logActionItemModelList) {
                logActionItemModel.setId(null);
                logActionItemModel.setActionId(logActionId);
            }

            if (!CollectionUtils.isEmpty(logActionItemModelList)) {
                logActionItemDao.addBatch(logActionItemModelList);
            }

            return RespUtil.getSuccessMap();
        } catch (Exception ex) {
            LOG.error("ObjectLogger ERROR : add log error,", ex);
            return RespUtil.getCommonErrorMap(RespConstant.INSERT_EXCEPTION);
        }
    }

    public Map<String, Object> query(ActionForm actionForm) {
        try {
            ActionModel actionModel = new ActionModel();
            if (actionForm.getId() != null) {
                actionModel.setId(Integer.parseInt(actionForm.getId()));
            }
            actionModel.setAppName(actionForm.getAppName());
            actionModel.setObjectName(actionForm.getObjectName());
            if (actionForm.getObjectId() != null) {
                actionModel.setObjectId(Integer.parseInt(actionForm.getObjectId()));
            }
            actionModel.setActor(actionForm.getActor());
            actionModel.setAction(actionForm.getAction());
            actionModel.setActionName(actionForm.getActionName());

            List<ActionModel> logActionModelList = logActionDao.queryByFilter(actionModel);

            for (ActionModel oneActionModel : logActionModelList) {
                ActionItemModel logActionItemModel = new ActionItemModel();
                logActionItemModel.setActionId(oneActionModel.getId());
                oneActionModel.getActionItemModelList().addAll(logActionItemDao.queryByFilter(logActionItemModel));
            }

            return RespUtil.getSuccessMap(logActionModelList);
        } catch (Exception ex) {
            LOG.error("ObjectLogger ERROR : query log error,", ex);
            return RespUtil.getCommonErrorMap(RespConstant.QUERY_EXCEPTION);
        }
    }
}
