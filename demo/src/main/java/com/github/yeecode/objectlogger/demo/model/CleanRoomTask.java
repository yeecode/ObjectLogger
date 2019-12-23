package com.github.yeecode.objectlogger.demo.model;

import com.github.yeecode.objectlogger.client.annotation.LogTag;

public class CleanRoomTask extends Task {
    @LogTag
    private Integer roomNumber;

    private String address;

    public Integer getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(Integer roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
