package com.sensing.core.utils.kafka;



import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/1/6.
 */
public abstract class AbstractKafkaMessageListener{
  //  protected static final Logger logger = LogManager.getLogger("monitor");
    protected final DecimalFormat decimalFormat = new DecimalFormat("#0.00");

    // 统计数据
    protected Map<String, Metric> subNumMap = new HashMap<>();


    protected void putMetrictoMap(String desc,Metric metric){
        if(metric!=null){
            subNumMap.put(desc,metric);
        }
    }

    public String getErrorInfoFromException(Exception e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception e2) {
            return "ErrorInfoFromException";
        }
    }
}
