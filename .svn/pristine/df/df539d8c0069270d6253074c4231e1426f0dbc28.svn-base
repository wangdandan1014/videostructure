//package com.sensing.core.alarm;
//
//import java.util.concurrent.Executors;
//import java.util.concurrent.ScheduledExecutorService;
//import java.util.concurrent.TimeUnit;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//
//import org.springframework.stereotype.Component;
//
//import com.sensing.core.dao.IAlarmInfoDAO;
//import com.sensing.core.dao.ICmpDAO;
//import com.sensing.core.dao.IDataInitDAO;
//import com.sensing.core.utils.UuidUtil;
//
////@RunWith(SpringJUnit4ClassRunner.class)
////@ContextConfiguration(locations = {
////		"classpath*:/applicationContext.xml",
////		"classpath*:/spring-mybatis.xml"
////		})
//@Component
//public class StartUp {
//
//	static AtomicInteger i = new AtomicInteger(1) ;
//
//	@Resource
//	public ICmpDAO cmpDAO;
//	@Resource
//	public IAlarmInfoDAO alarmInfoDAO;
//	@Resource
//	public IDataInitDAO dataInitDAO;
//	
//	@PostConstruct
//	public void process(){
//
//		final String[] plateNos = {"浙A1L612","京C123DV","豫C7451D","川H65231","川H65232","川H65233","川H65234","川H65235","川H65230","豫A1L61x"};
//		final Integer[] plateColors = {1,2,3,4,5};
//		final String[] vehicleBrandTags = {"大众","宝马","别克","日产","红旗","凯瑞"};
//		final Integer[] vehicleColors = {1,2,3,4,5};
//		final Integer[] vehicleClasses = {1,2,3,4,5,6,7,8,9,10};
////		final String[] identityIds = {"ids1","ids2","ids3","ids4","ids5","ids6","ids7","ids8","ids9","ids10","ids11"};
//
//		for (int i = 0; i  < 0; i++) {
//			AlarmTask.dataSaveService.submit(new AlarmProcess(alarmInfoDAO));
//		}
//		
//		for (int j = 0; j < 0; j++) {
//			ScheduledExecutorService service = Executors.newScheduledThreadPool(10);
//			service.scheduleAtFixedRate(new Runnable() {
//				public void run() {
//					int k = i.getAndIncrement();
//					if ( k == Integer.MAX_VALUE ) {
//						i.set(1);
//					}
//					if ( CountInfo.startTime == null ) {
//						CountInfo.startTime = System.currentTimeMillis()/1000;
//					}
//					CapBean capBean = new CapBean();
//					capBean.setCapPlateNo(plateNos[k%plateNos.length]);
//					capBean.setPlateColor(plateColors[k%plateColors.length]);
//					capBean.setVehicleBrandTag(vehicleBrandTags[k%vehicleBrandTags.length]);
//					capBean.setVehicleColor(vehicleColors[k%vehicleColors.length]);
//					capBean.setVehicleClass(vehicleClasses[k%vehicleClasses.length]);
//					capBean.setDeviceUuid("1707cd4a51644258a6ac8454dc410596");
//					capBean.setIdentityId(k+"");
//					capBean.setCapTime(System.currentTimeMillis()/1000);
//					capBean.setCapUuid(UuidUtil.getUuid());
//					
//					capBean.setOrientation(k);
//					capBean.setVehicleBrandTag("VehicleBrandTag"+k);
//					capBean.setVehicleModelTag("vehicleModelTag"+k);
//					capBean.setVehicleStylesTag("VehicleStylesTag"+k);
//					capBean.setVehicleMarkerMot(k);
//					capBean.setVehicleMarkerTissuebox(k);
//					capBean.setVehicleMarkerPendant(k);
//					capBean.setSunvisor(k);
//					capBean.setSafetyBelt(k);
//					capBean.setSafetyBeltCopilot(k);
//					capBean.setCalling(k);
//					capBean.setCapLocation("capLocation"+k);
//					
//					capBean.setCapUrl("capImgUrl...");
//					capBean.setSceneUrl("capSceneUrl...");
//					
////					AlarmTask.cmpService.submit(new CmpProcess(capBean,cmpDAO));
////					CountInfo.capCount.getAndIncrement();
//				}
//			}, 10000, 10, TimeUnit.MILLISECONDS);
//		}
//	}
//}
