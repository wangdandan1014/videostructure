package com.sensing.core.bean;

import java.io.Serializable;

public class TaskRequest  implements Serializable {
   private Task task;
   private TaskChannel taskChannel;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public TaskChannel getTaskChannel() {
        return taskChannel;
    }

    public void setTaskChannel(TaskChannel taskChannel) {
        this.taskChannel = taskChannel;
    }
}
