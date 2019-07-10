package com.sensing.core.bean;

import com.sensing.core.utils.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TaskChannelResp extends TaskChannel implements Serializable {
    private String channel_name;
    private Integer cap_stat;
    private String analysis_type;//通道的分析类型
    private List<Integer> analy_type_list;

    private String task_analy_type;// task的识别类型
    private List<Integer> task_analy_type_list;


    public String getTask_analy_type() {
        return task_analy_type;
    }

    public void setTask_analy_type(String task_analy_type) {
        this.task_analy_type = task_analy_type;
    }

    public List<Integer> getTask_analy_type_list() {
        if (StringUtils.isNotEmpty(task_analy_type)) {
            task_analy_type_list = Arrays.asList(task_analy_type.split(",")).stream().map(a -> Integer.parseInt(a)).collect(Collectors.toList());
        }
        return task_analy_type_list;
    }

    public void setTask_analy_type_list(List<Integer> task_analy_type_list) {
        this.task_analy_type_list = task_analy_type_list;
    }

    public List<Integer> getAnaly_type_list() {
        if (StringUtils.isNotEmpty(analysis_type)) {
            analy_type_list= Arrays.asList(analysis_type.split(",")).stream().map(a -> Integer.parseInt(a)).collect(Collectors.toList());
        }
        return analy_type_list;
    }

    public void setAnaly_type_list(List<Integer> analy_type_list) {
        this.analy_type_list = analy_type_list;
    }

    public String getAnalysis_type() {
        return analysis_type;
    }

    public void setAnalysis_type(String analysis_type) {
        this.analysis_type = analysis_type;
    }

    public String getChannel_name() {
        return channel_name;
    }

    public void setChannel_name(String channel_name) {
        this.channel_name = channel_name;
    }

    public Integer getCap_stat() {
        return cap_stat;
    }

    public void setCap_stat(Integer cap_stat) {
        this.cap_stat = cap_stat;
    }



}
