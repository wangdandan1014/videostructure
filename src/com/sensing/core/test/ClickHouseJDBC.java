package com.sensing.core.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ClickHouseJDBC {

	public static List<File> list = new ArrayList<>();

	public static List<File> getFileList(String strPath) {
		List<File> allfile = new ArrayList<>();
		List<File> filelist = new ArrayList<>();
		File dir = new File(strPath);
		File[] files = dir.listFiles(); // 该文件目录下文件全部放入数组
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				String fileName = files[i].getName();
				if (files[i].isDirectory()) { // 判断是文件还是文件夹
					getFileList(files[i].getAbsolutePath()); // 获取文件绝对路径
				} else {
					String strFileName = files[i].getAbsolutePath();
					System.out.println("---" + strFileName);
					filelist.add(files[i]);
				}
			}
			allfile.addAll(filelist);
		}
		System.out.println(filelist);
		System.out.println(allfile.size());
		list.addAll(filelist);
		return filelist;
	}

	public static void main(String[] args) {
		String strPath = "C:\\Users\\15039\\Desktop\\测试数据";
		getFileList(strPath);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				File file = list.get(i);
				boolean b = file.getName().contains(".csv");
				if (b) {
					service(file.getAbsolutePath());
				}
			}
		}

	}

	private static void service(String strPath) {
		File csv = new File(strPath); // CSV文件路径
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

				String deviceId = String.valueOf(s[1]);
				String[] s1 = deviceId.split("_");
				String dd = s1[0] + "_" + s1[1];

				String capUrl = String.valueOf(s[20]).replaceAll("/dahuadata/", "");
				String seceneUrl = String.valueOf(s[21]).replaceAll("/dahuadata/", "");
				
				String capLocation = String.valueOf(s[19]);
				String[] loca = capLocation.split(",");
				String capl = loca[1]+","+loca[0]+","+loca[2]+","+loca[3];
				
				String insertSql = "insert into Person(uuid, deviceId, capTime, capFlag,capDate, frameTime, age, genderCode, bagStyle, bigBagStyle, orientation, motion, cap, glass, "
						+ "coatColor, coatLength, coatTexture, trousersColor, trousersLen, trousersTexture, respirator, capLocation, capUrl, seceneUrl, taskIds)"
						+ "	   values('" + String.valueOf(s[0]) + "','" + dd + "'," + Long.valueOf(s[2]) + "," + (int) 3
						+ ",'" + s1[2] + "'," + Integer.valueOf(s[3]) + "," + Integer.valueOf(s[4]) + ","
						+ Integer.valueOf(s[5]) + "," + Integer.valueOf(s[6]) + "," + Integer.valueOf(s[12]) + ","
						+ Integer.valueOf(s[8]) + "," + Integer.valueOf(s[9]) + "," + Integer.valueOf(s[11]) + ","
						+ Integer.valueOf(s[10]) + "," + Integer.valueOf(s[13]) + "," + Integer.valueOf(s[14]) + ","
						+ Integer.valueOf(s[15]) + "," + Integer.valueOf(s[16]) + "," + Integer.valueOf(s[17]) + ","
						+ Integer.valueOf(s[18]) + "," + Integer.valueOf(s[7]) + ",'" + String.valueOf(s[19]) + "','"
						+ capUrl + "','" + seceneUrl + "',['" + String.valueOf(s[1]) + "'])";
				System.out.println(insertSql);
				exeSql(insertSql);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void readFile() {
		// TODO Auto-generated method stub

	}

	public static void exeSql(String sql) {
		String address = "jdbc:clickhouse://192.168.0.154:8123/default";
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		try {
			Class.forName("ru.yandex.clickhouse.ClickHouseDriver");
			connection = DriverManager.getConnection(address);
			statement = connection.createStatement();
			long begin = System.currentTimeMillis();

			results = statement.executeQuery(sql);
			long end = System.currentTimeMillis();
			System.out.println("执行（" + sql + "）耗时：" + (end - begin) + "ms");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {// 关闭连接
			try {
				if (results != null) {
					results.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
