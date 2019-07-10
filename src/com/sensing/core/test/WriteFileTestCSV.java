package com.sensing.core.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

/**
 * User: mingxingyu Date: 2019-04-19 21:16 Description:
 */
public class WriteFileTestCSV {

	public static void main(String[] args) throws Exception {

		String[] deviceIds = new String[50];
		for (int i = 0; i < deviceIds.length; i++) {
			deviceIds[i] = getUuid();
		}

		String[] taskIds = new String[3];
		for (int i = 0; i < taskIds.length; i++) {
			taskIds[i] = getUuid();
		}

//        File file = new File("C:\\Users\\Administrator\\Desktop\\test123.data");
		File file = new File("D:\\Person12.csv");
		BufferedWriter out = new BufferedWriter(new FileWriter(file));
		long l1 = System.currentTimeMillis();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		for (int i = 0; i < 1000; i++) {
			StringBuffer sb = new StringBuffer();
			long timeRandom = getTimeRandom();
			Date date = new Date(timeRandom * 1000);
			String dateStr = sdf.format(date);
			int len = getRandom(3) + 1;
			String taskArrstr = "[";
			for (int ii = 0; ii < len; ii++) {
				if (ii != len - 1) {
					taskArrstr += "'" + taskIds[getRandom(3)] + "',";
				} else {
					taskArrstr += "'" + taskIds[getRandom(3)] + "']";
				}
			}
			sb.append(getUuid() + "," + deviceIds[getRandom(50)] + "," + timeRandom + "," + dateStr + ", "
					+ getRandom(5) + "," + getRandom(3) + "," + getRandom(4) + "," + getRandom(4) + "," + getRandom(4)
					+ "," + getRandom(4) + "," + getRandom(4) + "," + getRandom(3) + "," + getRandom(15) + ","
					+ getRandom(6) + "," + getRandom(10) + "," + getRandom(15) + "," + getRandom(7) + "," + getRandom(8)
					+ "," + getRandom(5) + "," + getRandom(5) + ","
					+ "528-199-80-200,group1/M00/00/00/wKgB2VycnYSIIKF4AABGPkGZ21kAAASbALCYGMAAEZW294.jpg,group1/M00/00/00/wKgB2VycnNCIOmNSABRe4IkmNGUAAASaQDpbSEAFF74869.jpg,"
					+ taskArrstr);
			int j = 0;
			String s = (j = getRandom(3)) == 0 ? "" : "'" + taskIds[j] + "'";
			out.write(sb.toString());
			out.newLine();
			if (i % 1000000 == 0 && i != 0) {
				long l2 = System.currentTimeMillis();
				System.out.println("当前耗时(百万)" + i / 1000000 + ":" + (l2 - l1) / 1000);
			}
		}
		out.close();

	}

	/**
	 * @Description: 获取0-num间的随机数
	 * @Param: [num]
	 * @return: int
	 * @Author: mingxingyu
	 * @Date: 2019/4/19 21:44
	 */
	public static int getRandom(int num) {
		Random random = new Random();
		return random.nextInt(num);
	}

	/***
	 * @Description: 获取uuid
	 * @Param: []
	 * @return: java.lang.String
	 * @Author: mingxingyu
	 * @Date: 2019/4/19 21:45
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * @Description: 生成时间的范围数
	 * @Param: []
	 * @return: int
	 * @Author: mingxingyu
	 * @Date: 2019/4/19 21:58
	 */
	public static long getTimeRandom() {
		int min = 1554048000;// 2019-04-01 00:00:00
		int max = 1556640000;// 2019-5-01 00:00:00
		int cap = max - min;
		Random random = new Random();
		int r = random.nextInt(cap) + min;
		return Long.parseLong(r + "");
	}
}