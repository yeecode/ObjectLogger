package com.github.yeecode.objectlogger.demo.controller;

import com.github.yeecode.objectlogger.demo.model.CleanRoomTask;
import com.github.yeecode.objectlogger.client.model.BaseAttributeModel;
import com.github.yeecode.objectlogger.client.service.LogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
    @Autowired
    private LogClient logClient;

    private CleanRoomTask cleanRoomTask = new CleanRoomTask();

    @RequestMapping(value = "/add")
    public String add() {
        initTask();
        // Omission: Read and write database operations

        // Usage 1: Record a log without property change
        logClient.logAttributes(
                "CleanRoomTask",
                cleanRoomTask.getId().toString(),
                "Tom",
                "add",
                "Add New Task",
                "Create a cleanRoomTask",
                "taskName is :" + cleanRoomTask.getTaskName(),
                null);

        return "success";
    }

    @RequestMapping(value = "/start")
    public String start() {
        initTask();
        cleanRoomTask.setStatus("DOING");
        // Omission: Read and write database operations

        List<BaseAttributeModel> baseActionItemModelList = new ArrayList<>();
        BaseAttributeModel baseActionItemModel = new BaseAttributeModel();
        baseActionItemModel.setAttributeType("NORMAL");
        baseActionItemModel.setAttributeName("status");
        baseActionItemModel.setAttributeAlias("Status");
        baseActionItemModel.setOldValue("TODO");
        baseActionItemModel.setNewValue("DOING");
        baseActionItemModel.setDiffValue(null);
        baseActionItemModelList.add(baseActionItemModel);

        // Usage 2: Record a log with property changes
        logClient.logAttributes(
                "CleanRoomTask",
                cleanRoomTask.getId().toString(),
                "Jone",
                "start",
                "Start a Task",
                "Begin to clean room...",
                "Come on and start cleaning up.",
                baseActionItemModelList);

        return "success";
    }

    @RequestMapping(value = "/update")
    public String update() {
        initTask();
        CleanRoomTask oldTask = cleanRoomTask;
        // Omission: Read and write database operations

        CleanRoomTask newTask = new CleanRoomTask();
        newTask.setId(5);
        newTask.setTaskName("Demo Task");
        newTask.setStatus("DOING");
        newTask.setDescription("" +
                "<p>" +
                "What is poetry? Who knows<br>" +
                "Not a rose, but the scent of the rose.<br>" +
                "<p>" +
                "add some words" +
                "</p>" +
                "Not the sky, but the light of the sky." +
                "</p>" +
                "<p>" +
                "<span style=\\\"font-weight: bold;\\\">Hi~ this is demo</span>" +
                "</p>" +
                "<p>Not the fly, but the gleam of the fly.<br>" +
                "Not the sea, but the sound of sea .</p>" +
                "<p><br>" +
                "</p>" +
                "<p><br>" +
                "Not myself, but what makes me .<br>" +
                "See ,hear and feel something that prose<br>" +
                "Cannot: and what it is who knows.</p>" +
                "<h2>yes!</h2>");
        newTask.setAddress("Sunny Street");
        newTask.setRoomNumber(702);


        // Usage 3: Automatically analyze and record changes in object attributes
        logClient.logObject(
                cleanRoomTask.getId().toString(),
                "Tom",
                "update",
                "Update a Task",
                null,
                null,
                oldTask,
                newTask);

        return "success";
    }

    private void initTask() {
        cleanRoomTask.setId(5);
        cleanRoomTask.setTaskName("Demo Task");
        cleanRoomTask.setStatus("TODO");
        cleanRoomTask.setDescription("" +
                "<p>" +
                "What is poetry? Who knows<br>" +
                "Not a rose, but the scent of the rose.<br>" +
                "Not the sky, but the light of the sky." +
                "</p>" +
                "<p>" +
                "<span style=\\\"font-weight: bold;\\\">Hi~</span>" +
                "</p>" +
                "<p>Not the fly, but the gleam of the fly.<br>" +
                "Not the sea, but the sound of sea .</p>" +
                "<p><br>" +
                "</p>" +
                "<p><br>" +
                "Not myself, but what makes us .<br>" +
                "See you,hear and feel something that prose<br>" +
                "Cannot: and what it is who knows.</p>" +
                "<h2>yes!</h2>" +
                "<p><br></p>" +
                "<p><br></p>" +
                "<p>that is the end</p>");
    }

}
