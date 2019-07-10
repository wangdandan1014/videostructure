package com.sensing.core.bean;

import com.sensing.core.utils.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * JobsChannelTemp继承JobsChannel
 */
public class JobsChannelTemp extends JobsChannel {
    private static final long serialVersionUID = 1L;
    private String channelName;
    private String capState;
    private String analysis_type;//通道的分析类型
    private String jobs_analy_type;//任务的分析类型
    private List<Integer> jobs_analy_type_list;

    public String getJobs_analy_type() {

        return jobs_analy_type;
    }

    public void setJobs_analy_type(String jobs_analy_type) {
        this.jobs_analy_type = jobs_analy_type;
    }

    public List<Integer> getJobs_analy_type_list() {

        if (StringUtils.isNotEmpty(jobs_analy_type)) {
            jobs_analy_type_list = Arrays.asList(jobs_analy_type.split(",")).stream().map(a -> Integer.parseInt(a)).collect(Collectors.toList());
        }
        return jobs_analy_type_list;
    }

    public void setJobs_analy_type_list(List<Integer> jobs_analy_type_list) {
        this.jobs_analy_type_list = jobs_analy_type_list;
    }


    public String getAnalysis_type() {
        return analysis_type;
    }

    public void setAnalysis_type(String analysis_type) {
        this.analysis_type = analysis_type;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCapState() {
        return capState;
    }

    public void setCapState(String capState) {
        this.capState = capState;
    }
}