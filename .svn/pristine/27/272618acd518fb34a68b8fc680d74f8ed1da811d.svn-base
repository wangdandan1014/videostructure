package com.sensing.core.utils.props;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 以图搜图车辆颜色互斥静态变量
 * <p>Title: VehicleColorMutexCons</p>
 * <p>Description:</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	mingxingyu
 * @date	2019年6月12日
 * @version 1.0
 */
@Component
public class VehicleColorMutexCons {

	/**
	 * 互斥车辆颜色的集合
	 */
	public static List<String> VehicleColorMutexList = new ArrayList<String>();
	
	
	/**
	 * 初始化加载车辆互斥颜色
	 */
	static{
        try {
        	String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
			BufferedReader br = new BufferedReader(new FileReader(new File(path + "/" + "dll/vehicle_color_mutex.dic")));
			String line = null;
			while( (line=br.readLine()) != null ){
				if ( StringUtils.isNotEmpty(line.trim()) ) {
					String[] vcmArr = line.trim().split(":");
					VehicleColorMutexList.add(vcmArr[1]);
				}
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
