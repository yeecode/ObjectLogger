package com.github.yeecode.objectLogger.server.controller;

import com.github.yeecode.objectLogger.server.business.LogBusiness;
import com.github.yeecode.objectLogger.server.form.ActionForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@ResponseBody
@RequestMapping("/log")
public class LogController {
    @Autowired
    private LogBusiness logBusiness;

    @RequestMapping(value = "/add")
    public Map<String, Object> add(String logJsonString) {
        return logBusiness.add(logJsonString);
    }

    @RequestMapping(value = "/query")
    public Map<String, Object> query(ActionForm actionForm) {
        return logBusiness.query(actionForm);
    }
}
