package com.sensing.core.utils.kafka;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.BatchAcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;



public class AlertConsumerListener extends AbstractKafkaMessageListener implements BatchAcknowledgingMessageListener<String,byte[]>{

	private Log log = LogFactory.getLog(AlertConsumerListener.class);
	
	/**
	 * 接收订阅的kafka消息
	 */
    public void onMessage(List<ConsumerRecord<String, byte[]>> consumerRecords, Acknowledgment acknowledgment) {
        try {
            if(consumerRecords.size()>0){
            	log.info("订阅到kafka的告警数据");

                for(ConsumerRecord<String, byte[]> record : consumerRecords){
                    //读取数据key value 反序列化
                    //引入protoBuf 进行数据反序列化，转成抓拍对象传出
                    if(record.key()==null && record.value()== null){
                        continue;
                    }else{
                    	//订阅的kafka消息转为抓拍对象
//                    	AlertMsgBody.pbAlertInfoEx alertInfoEx = AlertMsgBody.pbAlertInfoEx.parseFrom(record.value());
//
//                    	log.info("facecenter接收到的kafka告警数据.alertUuid:"+alertInfoEx.getAlertUuid()+";personid:"+alertInfoEx.getLastPersionUuid()+";jobuuid:"+alertInfoEx.getJobUuid());
//                    	
//						//遍历有哪些客户端连接了服务
//						CopyOnWriteArraySet<AlertDataPush> sockets = AlertDataPush.alertWbSockets;
//						if ( sockets != null && sockets.size() > 0  ) {
//							Map<String,String> msgMap = new HashMap<String,String>();
//							
//							msgMap.put("alertUuid",alertInfoEx.getAlertUuid());
//							msgMap.put("fcapUuid",alertInfoEx.getCapUuid());
//							msgMap.put("fcmpFobjId",alertInfoEx.getObjUuid());
//							msgMap.put("fcapTime",alertInfoEx.getIcapTime()+"");
//							msgMap.put("fcapImgUrl",Constants.SERVER_URL_PHOTO+alertInfoEx.getCapImgUrl());
//							msgMap.put("fcmpImgUrl",Constants.SERVER_URL_PHOTO+alertInfoEx.getObjImgUrl());
//							msgMap.put("dBID",alertInfoEx.getDbId());
//							msgMap.put("dBIDName",alertInfoEx.getTemplateDbName());
//							msgMap.put("channelName",alertInfoEx.getChannelName());
//							msgMap.put("name",alertInfoEx.getObjName());
//							msgMap.put("score",alertInfoEx.getDcmpFirstSocre()+"");
//							msgMap.put("alertTime",alertInfoEx.getIalertTime()+"");
//							msgMap.put("appearNumber",alertInfoEx.getIalertAppearNumber()+"");
//							msgMap.put("ackStatTags",alertInfoEx.getAckStatTag());
//							msgMap.put("ackStat",alertInfoEx.getIackStat()+"");
//							msgMap.put("fcmpUuid",alertInfoEx.getCmpUuid());
//							
//							for (AlertDataPush dataPush : sockets) {
//								//判断客户端传递来的通道id是否 和接收到的通道id相等
//								if (  alertInfoEx.getJobUuid() != null &&  dataPush.alertJobStrs.contains(alertInfoEx.getJobUuid())  ) {
////								if (  true ) {
//									dataPush.sendMessage(JSONObject.toJSONString(msgMap));
//									log.info("告警消息已推送，任务uuid:"+alertInfoEx.getJobUuid()+",告警uuid:"+alertInfoEx.getAlertUuid());
//								}
//    						}
    					}
//                    	else{
//    						log.info("告警消息推送失败，不存在链接的客户端，任务uuid:"+alertInfoEx.getJobUuid()+",告警uuid:"+alertInfoEx.getAlertUuid());
//    					}
                    }
                }
            // 最后调用acknowledgment的ack方法，提交offset
//            acknowledgment.acknowledge();
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
