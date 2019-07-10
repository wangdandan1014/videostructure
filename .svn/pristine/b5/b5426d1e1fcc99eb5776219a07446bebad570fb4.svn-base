
package com.sensing.core.service.impl;

import javax.annotation.Resource;

import com.sensing.core.utils.ResponseBean;
import com.sensing.core.utils.results.ResultUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.sensing.core.cacahes.PreviewCache;
import com.sensing.core.service.IPreviewService;
import com.sensing.core.utils.task.JobsAndTaskTimer;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class PreviewServiceImpl implements IPreviewService {

    private static final Log log = LogFactory.getLog(PreviewServiceImpl.class);

    @Resource
    JobsAndTaskTimer jobsAndTaskTimer;

    /**
     * 心跳更新预览缓存
     *
     * @param userId   用户的uuid
     * @param deviceId 通道的uuid
     * @throws Exception
     * @author mingxingyu
     * @date 2019年1月17日 下午5:24:23
     */
    public ResponseBean updateData(String userId, String deviceId) throws Exception {

//		//该用户是否已经打开了通道
//		if ( PreviewCache.userPreviewMap.containsKey(userId) ) {
//			//该用户一直在预览该通道
//			if ( PreviewCache.userPreviewMap.get(userId)[0].equals(deviceId) ) {
//				//更新时间
//				String[] vals = new String[]{deviceId,System.currentTimeMillis()/1000+""};
//				PreviewCache.userPreviewMap.put(userId,vals);
//			}
//			//该用户切换了通道
//			else{
//				//判断之前打开的通道，是否需要关闭
//				String lastDeviceId = PreviewCache.userPreviewMap.get(userId)[0];
//				String[] vals = new String[]{deviceId,System.currentTimeMillis()/1000+""};
//				PreviewCache.userPreviewMap.put(userId, vals);
//				if ( PreviewCache.devicePreviewMap.get(lastDeviceId) == 1 ) {
//					PreviewCache.devicePreviewMap.remove(lastDeviceId);
//					// 查询通道的 抓拍类型，不满足1,3,4的情况下，重新通知抓拍更新
//					jobsAndTaskTimer.startJobs();
//				}else{
//					Integer channelOpenCount = PreviewCache.devicePreviewMap.get(lastDeviceId);
//					PreviewCache.devicePreviewMap.put(deviceId,channelOpenCount-1);
//				}
//			}
//		}else{
//			//该用户从未打开过通道
//			String[] vals = new String[]{deviceId,System.currentTimeMillis()/1000+""};
//			PreviewCache.userPreviewMap.put(userId, vals);
//			if ( PreviewCache.devicePreviewMap.containsKey(deviceId) ) {
//				Integer channelOpenCount = PreviewCache.devicePreviewMap.get(deviceId)+1;
//				PreviewCache.devicePreviewMap.put(deviceId,channelOpenCount);
//			}else{
//				PreviewCache.devicePreviewMap.put(deviceId,1);
//			}
//			// 查询通道的 抓拍类型，不满足1,3,4的情况下，重新通知抓拍更新
//			jobsAndTaskTimer.startJobs();
//		}
        if (StringUtils.isEmpty(deviceId)) {
            return ResultUtils.error(-1, "通道id为空");
        }
        if (PreviewCache.deviceTimeMap != null && PreviewCache.deviceTimeMap.size() > 0 &&
                PreviewCache.deviceTimeMap.keySet().contains(deviceId)) {
            PreviewCache.deviceTimeMap.remove(deviceId);
            PreviewCache.deviceTimeMap.put(deviceId, System.currentTimeMillis() / 1000);
        } else {
            PreviewCache.deviceTimeMap.put(deviceId, System.currentTimeMillis() / 1000);
            ResponseBean result = jobsAndTaskTimer.startJobs();
            if (result.getError() != 0) {
                return ResultUtils.error(-1, "更新通道状态异常");
            }
        }
        return ResultUtils.success();
    }

    /**
     * 用户退出通道的视频预览
     *
     * @param deviceIdList 用户的uuid
     * @throws Exception
     * @author mingxingyu
     * @date 2019年1月18日 上午11:10:41
     */
    public void delDevice(List<String> deviceIdList) throws Exception {

        if (!CollectionUtils.isEmpty(deviceIdList)) {
            for (String deviceId : deviceIdList) {
                PreviewCache.deviceTimeMap.remove(deviceId);
            }
            jobsAndTaskTimer.startJobs();
        }


//        if (PreviewCache.userPreviewMap.containsKey(userId)) {
//            String[] vals = PreviewCache.userPreviewMap.get(userId);
//            String deviceId = vals[0];
//
//            if (PreviewCache.devicePreviewMap != null && PreviewCache.devicePreviewMap.size() > 0 && PreviewCache.devicePreviewMap.get(deviceId) == 1) {
//                PreviewCache.userPreviewMap.remove(userId);
//                PreviewCache.devicePreviewMap.remove(deviceId);
//                // 查询通道的 抓拍类型，不满足1,3,4的情况下，重新通知抓拍更新
//                jobsAndTaskTimer.startJobs();
//            } else {
//                Integer channelOpenCount = PreviewCache.devicePreviewMap.get(deviceId);
//                if (channelOpenCount != null && channelOpenCount > 0) {
//                    PreviewCache.devicePreviewMap.put(deviceId, channelOpenCount - 1);
//                }
//            }
//
//        } else {
//            log.error("用户退出视频预览时，未找到该对应的缓存信息.");
//        }
    }

}
	
	
	