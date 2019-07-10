package com.sensing.core.bean;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

/**
 * 任务列表页数据实体--temp---中间变量，临时使用
 */
public class TaskRespTempList extends TaskRespList implements Serializable {
    private String channelNames;
    private String analy_type;
    private Date begin_date;
    private String begin_date_str;
    private Date end_date;
    private String end_date_str;
    private String start_time;
    private String end_time;
    private String createUserUuid;
    private Integer state;
    private String analy_start_time;
    private String analy_end_time;
    private Date analy_begin_date;
    private Date analy_end_date;
    private String runWeek;


    public String getAnaly_begin_date() {
        if (analy_begin_date != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(analy_begin_date);
        } else {
            return "";
        }
    }

    public void setAnaly_begin_date(Date analy_begin_date) {
        this.analy_begin_date = analy_begin_date;
    }

    public String getAnaly_end_date() {
        if (analy_end_date != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(analy_end_date);
        } else {
            return "";
        }
    }

    public void setAnaly_end_date(Date analy_end_date) {
        this.analy_end_date = analy_end_date;
    }

    public String getRunWeek() {
        return runWeek;
    }

    public void setRunWeek(String runWeek) {
        this.runWeek = runWeek;
    }


    public String getBegin_date_str() {
        if (begin_date != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(begin_date);
        } else {
            return "";
        }
    }

    public void setBegin_date_str(String begin_date_str)  {
        if (begin_date_str != null && !begin_date_str.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            try {
                this.begin_date = sdf.parse(begin_date_str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else
            this.begin_date = null;
    }

    public String getEnd_date_str() {
        if (end_date != null) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(end_date);
        } else {
            return "";
        }
    }

    public void setEnd_date_str(String end_date_str)  {
        if (end_date_str != null && !end_date_str.trim().equals("")) {
            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-MM-dd");
            try {
                this.end_date = sdf.parse(end_date_str);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        } else
            this.end_date = null;
    }



    public String getChannelNames() {
        return channelNames;
    }

    public void setChannelNames(String channelNames) {
        this.channelNames = channelNames;
    }

    public String getAnaly_type() {
        return analy_type;
    }

    public void setAnaly_type(String analy_type) {
        this.analy_type = analy_type;
    }

    public Date getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(Date begin_date) {
        this.begin_date = begin_date;
    }

    public Date getEnd_date() {
        return end_date;
    }

    public void setEnd_date(Date end_date) {
        this.end_date = end_date;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getCreateUserUuid() {
        return createUserUuid;
    }

    public void setCreateUserUuid(String createUserUuid) {
        this.createUserUuid = createUserUuid;
    }

    @Override
    public Integer getState() {
        return state;
    }

    @Override
    public void setState(Integer state) {
        this.state = state;
    }

    public String getAnaly_start_time() {
        return analy_start_time;
    }

    public void setAnaly_start_time(String analy_start_time) {
        this.analy_start_time = analy_start_time;
    }

    public String getAnaly_end_time() {
        return analy_end_time;
    }

    public void setAnaly_end_time(String analy_end_time) {
        this.analy_end_time = analy_end_time;
    }
}
