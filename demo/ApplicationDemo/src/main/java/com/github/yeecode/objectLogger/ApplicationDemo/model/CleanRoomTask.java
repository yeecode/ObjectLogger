package com.github.yeecode.objectLogger.ApplicationDemo.model;

public class CleanRoomTask extends Task {
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
