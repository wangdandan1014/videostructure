package com.sensing.core.utils.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.sensing.core.cacahes.PreviewCache;
import com.sensing.core.service.IPreviewService;
import com.sensing.core.utils.props.PropUtils;
import com.sensing.core.utils.time.DateUtil;

/**
 * 定时任务：视频预览更新通道缓存的任务
 */
@Component
public class DevicePreviewTimer {

	private static final Log log = LogFactory.getLog(DevicePreviewTimer.class);

	@Resource
	IPreviewService previewService;
//	@Resource
//	private MongoTemplate mongoTemplate;

//    public void startTaskbk() {
//        try {
//            log.info("视频预览更新通道缓存定时任务执行.时间点:"+DateUtil.DateToString(new Date()));
//            Set<String> previewkeySet = PreviewCache.userPreviewMap.keySet();
//
//            Long currentTime = System.currentTimeMillis()/1000;
//            int cacheTime = PropUtils.getInt("devicepreview.update.time");
//            for (String userId : previewkeySet) {
//                String[] vals = PreviewCache.userPreviewMap.get(userId);
//                long lastTime = Long.parseLong(vals[1]);
//
//                //缓存已超时的清除
//                if ( currentTime - lastTime >= cacheTime*60 ) {
//                    previewService.delDevice(userId);
//                }
//
//            }
//        } catch (Exception e) {
//            log.error("视频预览更新通道缓存定时任务执行，发生异常."+e.getMessage());
//            e.printStackTrace();
//        }
//    }
	public void startTask() {
		try {
			Set<String> keys = PreviewCache.deviceTimeMap.keySet();
			for (String deviceId : keys) {
				Long time = PreviewCache.deviceTimeMap.get(deviceId);
				String date = DateUtil.stampToDate(time * 1000 + "");
				log.info("视频预览更新通道缓存定时任务执行.时间点:" + DateUtil.DateToString(new Date()) + "--通道uuid--" + deviceId
						+ "--通道更新时间--" + date);
			}

			int cacheTime = PropUtils.getInt("devicepreview.update.time");
			if (PreviewCache.deviceTimeMap == null || PreviewCache.deviceTimeMap.size() == 0) {
				return;
			}
			Set<String> deviceSet = PreviewCache.deviceTimeMap.keySet();
			Long currentTime = System.currentTimeMillis() / 1000;
			List<String> deviceList = new ArrayList<>();
			for (String device : deviceSet) {
				Long lastTime = PreviewCache.deviceTimeMap.get(device);
				if (currentTime - lastTime >= cacheTime * 60) {
					deviceList.add(device);
				}
			}
			if (!CollectionUtils.isEmpty(deviceList)) {
				log.info("DevicePreviewTimer.startTask 视频预览更新通道" + deviceList.toString());
				previewService.delDevice(deviceList);
			}
			// 查询出符合条件的所有结果，并将符合条件的所有数据删除,删除10分钟前的数据
//			long time = new Date().getTime() / 1000 - 10 * 60;
//			Query query = Query.query(Criteria.where("capTime").lte(time));
//			mongoTemplate.remove(query, "PersonTemp");
//			mongoTemplate.remove(query, "MotorVehicleTemp");
//			mongoTemplate.remove(query, "NonmotorVehicleTemp");
//			log.info("本次mongotemp删除时间为：" + DateUtil.DateToString(new Date()));
		} catch (Exception e) {
			log.error("视频预览更新通道缓存定时任务执行，发生异常." + e.getMessage());
			e.printStackTrace();
		}
	}

}
