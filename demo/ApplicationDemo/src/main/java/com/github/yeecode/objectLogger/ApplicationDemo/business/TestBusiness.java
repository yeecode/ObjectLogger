package com.github.yeecode.objectLogger.ApplicationDemo.business;

import com.github.yeecode.objectLogger.ApplicationDemo.model.CleanRoomTask;
import com.github.yeecode.objectLogger.client.service.LogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class TestBusiness {
    @Autowired
    private LogClient logClient;

    @RequestMapping(value = "/")
    public String main() {
        CleanRoomTask oldTask = new CleanRoomTask();
        oldTask.setId(8);
        oldTask.setTaskName("Old Task");
        oldTask.setUserId(15);
        oldTask.setDescription("This is a task\n" +
                "some works should be down.");
        oldTask.setAddress(null);

        CleanRoomTask newTask = new CleanRoomTask();
        newTask.setId(8);
        newTask.setTaskName("New Task");
        newTask.setUserId(17);
        newTask.setDescription("This is a task\n" +
                "more works should be down.");
        newTask.setAddress("Sunny Street");

        logClient.sendLogForObject(8, "Licy", "doTest", "Test", null, null, oldTask, newTask);

        return "success";
    }
}
