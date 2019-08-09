package com.github.yeecode.objectlogger.server.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {
    @RequestMapping(value = "")
    public String index() {
        return "<div>\n" +
                "\t<span style=\"font-size: 20px; font-weight: bolder;\">ObjectLoggerServer Deployed Success!</span>\n" +
                "</div>\n" +
                "<br/>\n" +
                "<hr/>\n" +
                "<br/>\n" +
                "<div>\n" +
                "\t\n" +
                "<div>\n" +
                "\tYou can query logs via<code>/log/query</code>, For example:\n" +
                "\t<br/>\n" +
                "\t<code>http://localhost:12301/ObjectLoggerServer/log/query</code> <a href=\"/ObjectLoggerServer/log/query\" target=\"_blank\"><button>Try It</button></a>\n" +
                "\t<br/>\n" +
                "</div>\n" +
                "<br/>\n" +
                "<br/>\n" +
                "<div>\n" +
                "\tAnd you can use the following parameters to filter the logs to be queried:\n" +
                "\t<br/>\n" +
                "\t<ul>\n" +
                "\t\t<li><b>appName</b>: The name of the business system that generated the log.</li>\n" +
                "\t\t<li><b>objectName</b>: The type of object to which the log belongs.</li>\n" +
                "\t\t<li><b>objectId</b>: The id of object to which the log belongs.</li>\n" +
                "\t\t<li><b>operator</b>: The operator of the log.</li>\n" +
                "\t\t<li><b>operationName</b>: The operation name of the log.</li>\n" +
                "\t\t<li><b>operationAlias</b>: The operation alias the log.</li>\n" +
                "\t</ul>\n" +
                "\t<br/>\n" +
                "\tFor example:\n" +
                "\t<br>\n" +
                "\t<code>http://localhost:12301/ObjectLoggerServer/log/query?appName=ObjectLoggerDemo&objectName=CleanRoomTask&objectId=5</code>\n" +
                "\t<a href=\"/ObjectLoggerServer/log/query?appName=ObjectLoggerDemo&objectName=CleanRoomTask&objectId=5\" target=\"_blank\"><button>Try It</button></a>\n" +
                "</div>\n" +
                "</div>";
    }
}
