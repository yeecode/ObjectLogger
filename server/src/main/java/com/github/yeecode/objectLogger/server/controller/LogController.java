package com.github.yeecode.objectLogger.server.controller;

import com.github.yeecode.objectLogger.server.business.LogBusiness;
import com.github.yeecode.objectLogger.server.form.OperationForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogBusiness logBusiness;

    @RequestMapping(value = "/add")
    public Map<String, Object> add(String logJsonString) {
        return logBusiness.add(logJsonString);
    }

    @RequestMapping(value = "/query")
    public Map<String, Object> query(OperationForm operationForm) {
        return logBusiness.query(operationForm);
    }
}
