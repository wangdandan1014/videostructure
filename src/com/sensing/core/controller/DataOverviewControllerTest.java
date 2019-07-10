//package com.sensing.core.controller;
//
//import java.util.HashMap;
//import java.util.Map;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//
//import com.alibaba.fastjson.JSONObject;
//
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:spring-mybatis.xml", "classpath:applicationContext.xml",
//		"classpath:config/spring-mvc.xml", "classpath:config/spring-mongo.xml",
//		"classpath:config/spring-kafka-comsumer.xml" })
//public class DataOverviewControllerTest {
//
//	@Autowired
//	private DataOverviewController dataOverviewController;
//
//	private MockMvc mockMvc;
//
//	@Before
//	public void setup() {
//		mockMvc = MockMvcBuilders.standaloneSetup(dataOverviewController).build();
//	}
//
//	@Test
//	public void testAlarmCountByDay() throws Exception {
//
//		Map<String, Object> param = new HashMap<>();
//		param.put("date", "-1");
//		String requestJson = JSONObject.toJSONString(param);
//
//		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/overview/alarmCountByDay")
//				.contentType(MediaType.APPLICATION_JSON).content(requestJson));
//		MvcResult mvcResult = resultActions.andReturn();
//		String result = mvcResult.getResponse().getContentAsString();
//		int status = mvcResult.getResponse().getStatus();
//		System.out.println("=====客户端获得反馈状态码:" + status);
//		System.out.println("=====客户端获得反馈数据:" + result);
//	}
//
//	@Test
//	public void testAlarmCount() throws Exception {
//		Map<String, Object> param = new HashMap<>();
//		param.put("date", "-2");
//		param.put("type", "5");
//		String requestJson = JSONObject.toJSONString(param);
//
//		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/overview/alarmCount")
//				.contentType(MediaType.APPLICATION_JSON).content(requestJson));
//		MvcResult mvcResult = resultActions.andReturn();
//		String result = mvcResult.getResponse().getContentAsString();
//		int status = mvcResult.getResponse().getStatus();
//		System.out.println("=====客户端获得反馈状态码:" + status);
//		System.out.println("=====客户端获得反馈数据:" + result);
//	}
//
//	@Test
//	public void testTrafficCount() throws Exception {
//		Map<String, Object> param = new HashMap<>();
//		param.put("date", "-2");
//		param.put("type", "5");
//		String requestJson = JSONObject.toJSONString(param);
//
//		ResultActions resultActions = this.mockMvc.perform(MockMvcRequestBuilders.post("/overview/trafficCount")
//				.contentType(MediaType.APPLICATION_JSON).content(requestJson));
//		MvcResult mvcResult = resultActions.andReturn();
//		String result = mvcResult.getResponse().getContentAsString();
//		int status = mvcResult.getResponse().getStatus();
//		System.out.println("=====客户端获得反馈状态码:" + status);
//		System.out.println("=====客户端获得反馈数据:" + result);
//	}
//
//}
