package com.sensing.core.utils;

import javax.annotation.PostConstruct;

import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TServerSocket;
import org.springframework.stereotype.Component;

import com.sensing.core.service.DataService;
import com.sensing.core.service.impl.DataServiceImpl;
import com.sensing.core.utils.props.PropUtils;


@Component
public class ThriftServer {
	
	private static int c = 0;
	@PostConstruct
	public void init(){
		if(c == 0){	
			  new Thread() {
		            @Override
		            public void run() {
		    			try {
		    				TProcessor tprocessor = new DataService.Processor<DataService.Iface>(new DataServiceImpl());  
		    		        // 阻塞IO  
		    		        TServerSocket serverTransport = new TServerSocket(PropUtils.getInt("datacenter.port"));  
		    		        //多线程服务模型  
		    		        TThreadPoolServer.Args tArgs = new TThreadPoolServer.Args(serverTransport);  
		    		        tArgs.processor(tprocessor);  
		    		        //客户端协议要一致  
		    		        tArgs.protocolFactory(new TBinaryProtocol.Factory());  
		    		         // 线程池服务模型，使用标准的阻塞式IO，预先创建一组线程处理请求。  
		    		        TServer server = new TThreadPoolServer(tArgs);  
		    		        server.setServerEventHandler(new MyTServerEventHandler());
		    		        server.serve(); // 启动服务  
		    			} catch (Exception e) {
		    				e.printStackTrace();
		    			}
		            }
		        }.start();
		}
		c++;
	}
}
