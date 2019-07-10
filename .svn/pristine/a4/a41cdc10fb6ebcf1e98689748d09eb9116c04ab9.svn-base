package com.sensing.core.utils;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;

import com.bitanswer.library.BitAnswer;
import com.bitanswer.library.BitAnswer.BitAnswerException;
import com.sensing.core.utils.props.PropUtils;


@SuppressWarnings("all")
public class BitCheck implements ServletContextListener{
	private static final Log log = LogFactory.getLog(BitCheck.class);
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		 String sn = PropUtils.getString("license.sno");
		 String type = PropUtils.getString("license.type");
		 BitAnswer bitAnswer = new BitAnswer();
		 if(!type.equals("0")){
			int status = bitAnswer.login(null, sn, BitAnswer.LoginMode.AUTO);
			try {
				String info;
				info = bitAnswer.getInfo(sn, BitAnswer.InfoType.BIT_LIST_SRV_ADDR);
				log.info("获取集团服务信息：" + info);
				 Document document = DocumentHelper.parseText(info);
				 List<Node> nodeList = document.selectNodes("//server");
				 Element serverEle = (Element) nodeList.get(0);
				 if(serverEle!=null){
					 String ip = serverEle.attributeValue("ip");
					 int port = Integer.valueOf(serverEle.attributeValue("port"));
					 String host = serverEle.attributeValue("hostname");
					 bitAnswer.setLocalServer(ip, port, 30000);
					 log.info("设置集团服务器成功：" + "ip:" + ip + ";port:" + port);
				 }
			} catch (Exception e) {
				log.error("未找到集团授权服务器");
			}
		 }


	}

}
