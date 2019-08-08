package com.github.yeecode.objectlogger.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
    @RequestMapping(value = "")
    public String index() {
        return "<h1>ObjectLogger Deployed Success!</h1> " +
                "<br/> " +
                "<span>You can query logs via <code>/log/query</code></span>, and use param in <code>OperationForm</code> to filter logs." +
                "<br>" +
                "<span>For example:</span>" +
                "<br>" +
                "<span><code>http://localhost:8080/ObjectLogger/log/query?appName=ObjectLoggerDemo&objectName=CleanRoomTask&objectId=5<code></span>";
    }
}
