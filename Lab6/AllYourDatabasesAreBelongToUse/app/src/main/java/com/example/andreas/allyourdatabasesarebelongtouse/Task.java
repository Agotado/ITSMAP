package com.example.andreas.allyourdatabasesarebelongtouse;

public class Task {

    private Long taskId;
    private String taskName;
    private String place;

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long id) {
        this.taskId = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String name) {
        this.taskName = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String placeName) {
        this.place = placeName;
    }
}
