package com.sensing.core.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import com.sensing.core.bean.Person;
import com.sensing.core.clickhouseDao.IPersonCKDAO;
import com.sensing.core.service.impl.PersonServiceImpl;

import net.sf.json.JSONArray;

public class readcsv {

	public static void main(String[] args) {

		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//		PersonServiceImpl personService =  context.getBean(PersonServiceImpl.class);
		IPersonCKDAO personService =  context.getBean(IPersonCKDAO.class);

		File csv = new File(
				"C:\\Users\\15039\\Desktop\\测试数据\\337-2_005_2019-02-23_00.00.00-01.00.00R1b2ed43\\capture.csv"); // CSV文件路径
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(csv));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line = "";
		String everyLine = "";
		try {
			List<String> allString = new ArrayList<>();
			while ((line = br.readLine()) != null) // 读取到的内容给line变量
			{
				everyLine = line;
				System.out.println(everyLine);
				allString.add(everyLine);
			}
			for (String string : allString) {
				String[] s = string.split(":");
				Person document = new Person();
				document.setUuid(s[0]);
				document.setDeviceId(s[1]);
				document.setCapTime(Long.valueOf(s[2]));
				document.setCapDate(new Date());
				document.setFrameTime(Integer.valueOf(s[3]));
				document.setAge(Integer.valueOf(s[4]));
				document.setGenderCode(Integer.valueOf(s[5]));
				document.setBagStyle(Integer.valueOf(s[6]));
				document.setRespirator(Integer.valueOf(s[7]));
				document.setOrientation(Integer.valueOf(s[8]));
				document.setMotion(Integer.valueOf(s[9]));
				document.setGlass(Integer.valueOf(s[10]));
				document.setCap(Integer.valueOf(s[11]));
				document.setBigBagStyle(Integer.valueOf(s[12]));
				document.setCoatColor(Integer.valueOf(s[13]));
				document.setCoatLength(Integer.valueOf(s[14]));
				document.setCoatTexture(Integer.valueOf(s[15]));
				document.setTrousersColor(Integer.valueOf(s[16]));
				document.setTrousersLen(Integer.valueOf(s[17]));
				document.setTrousersTexture(Integer.valueOf(s[18]));
				document.setCapLocation(String.valueOf(s[19]));
				document.setCapUrl(String.valueOf(s[20]));
				document.setSeceneUrl(String.valueOf(s[21]));
				document.setCapFlag(3);
				List<String> taskIds = Arrays.asList(s[1].split(""));
				try {
//					personService.saveNewPersonCK(document, taskIds);
					personService.savePerson(document, taskIds);
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			System.out.println("csv表格中所有行数：" + allString.size());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
