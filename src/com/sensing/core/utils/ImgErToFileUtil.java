package com.sensing.core.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
* <p>Title: ImgErToFileUtil</p>  
* <p>Description: 图片信息处理</p>  
* @author haowenfeng  
* @date 2018年8月2日
 */
public class ImgErToFileUtil {
	
	private static final Log log = LogFactory.getLog(ImgErToFileUtil.class);

	/**
	 * 将接收的字符串转换成图片保存
	 * 
	 * @param imgStr
	 *            二进制流转换的字符串
	 * @param imgPath
	 *            图片的保存路径
	 * @param imgName
	 *            图片的名称
	 * @return 1：保存正常 0：保存失败
	 */
	public static int saveToImgByStr(String imgStr, String imgPath, String imgName) {
		try {
			log.info("===imgStr.length()====>" + imgStr.length() + "=====imgStr=====>" + imgStr);
		} catch (Exception e) {
			e.printStackTrace();
		}
		int stateInt = 1;
		if (imgStr != null && imgStr.length() > 0) {
			try {

				// 将字符串转换成二进制，用于显示图片
				// 将上面生成的图片格式字符串 imgStr，还原成图片显示
				byte[] imgByte = hex2byte(imgStr);

				InputStream in = new ByteArrayInputStream(imgByte);

				File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
				FileOutputStream fos = new FileOutputStream(file);

				byte[] b = new byte[1024];
				int nRead = 0;
				while ((nRead = in.read(b)) != -1) {
					fos.write(b, 0, nRead);
				}
				fos.flush();
				fos.close();
				in.close();

			} catch (Exception e) {
				stateInt = 0;
				e.printStackTrace();
			} finally {
			}
		}
		return stateInt;
	}

	/**
	 * 将二进制转换成图片保存
	 * 
	 * @param imgStr
	 *            二进制流转换的字符串
	 * @param imgPath
	 *            图片的保存路径
	 * @param imgName
	 *            图片的名称
	 * @return 1：保存正常 0：保存失败
	 */
	public static int saveToImgByBytes(File imgFile, String imgPath, String imgName) {

		int stateInt = 1;
		if (imgFile.length() > 0) {
			try {
				File file = new File(imgPath, imgName);// 可以是任何图片格式.jpg,.png等
				FileOutputStream fos = new FileOutputStream(file);

				FileInputStream fis = new FileInputStream(imgFile);

				byte[] b = new byte[1024];
				int nRead = 0;
				while ((nRead = fis.read(b)) != -1) {
					fos.write(b, 0, nRead);
				}
				fos.flush();
				fos.close();
				fis.close();

			} catch (Exception e) {
				stateInt = 0;
				e.printStackTrace();
			} finally {
			}
		}
		return stateInt;
	}

	/**
	 * 二进制转字符串
	 * 
	 * @param b
	 * @return
	 */
	public static String byte2hex(byte[] b) // 二进制转字符串
	{
		StringBuffer sb = new StringBuffer();
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				sb.append("0" + stmp);
			} else {
				sb.append(stmp);
			}

		}
		return sb.toString();
	}

	/**
	 * 字符串转二进制
	 * 
	 * @param str
	 *            要转换的字符串
	 * @return 转换后的二进制数组
	 */
	public static byte[] hex2byte(String str) { // 字符串转二进制
		if (str == null)
			return null;
		str = str.trim();
		int len = str.length();
		if (len == 0 || len % 2 == 1)
			return null;
		byte[] b = new byte[len / 2];
		try {
			for (int i = 0; i < str.length(); i += 2) {
				b[i / 2] = (byte) Integer.decode("0X" + str.substring(i, i + 2)).intValue();
			}
			return b;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 生成指定长度的字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRadomStr(int length) {

		Random rd = new Random();
		StringBuffer sb = new StringBuffer();
		long result = 0;
		for (int i = 0; i < length; i++) {
			int number = rd.nextInt(3);
			switch (number) {
			// 大写字母
			case 0:
				result = Math.round(Math.random() * 25 + 65);
				sb.append(String.valueOf((char) result));
				break;
			// 小写字母
			case 1:
				result = Math.round(Math.random() * 25 + 97);
				sb.append(String.valueOf((char) result));
				break;
			// 数字
			case 2:
				sb.append(String.valueOf(new Random().nextInt(10)));
				break;
			}
		}
		return sb.toString();

	}

	/**
	 * 将文件二进制数组流转换成图片
	 * @param imgByte 图片文件二进制流
	 * @param imagPath 生成的图片文件存放路径
	 * @param imagName 生成的图片文件名称 带文件后缀 例如 xxx.jpg
	 * @return
	 */
	public static int bytesToImagFile(byte[] imgByte,String imagPath,String imagName) {
		int flag = 1;

		InputStream in = new ByteArrayInputStream(imgByte);
		File file = new File(imagPath,imagName);// 可以是任何图片格式.jpg,.png等
		FileOutputStream fos = null;
		try {
			if(!file.exists()){
				file.createNewFile();
			}
			fos = new FileOutputStream(file);
			byte[] b = new byte[1024];
			int nRead = 0;
			while ((nRead = in.read(b)) != -1) {
				fos.write(b, 0, nRead);
			}
			fos.flush();
		} catch (Exception e) {
			flag = 0;
			e.printStackTrace();
		} finally {
			try {
				if (fos != null) {
					fos.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
	
	
	/**
	 * 根据地址获得图片的BufferedImage对象
	 * @param strUrl 网络连接地址
	 * @author haowenfeng
	 * @throws Exception 
	 * @date 2018-08-02
	 */
    public static BufferedImage getImageFromNetByUrl(String strUrl) throws Exception{    
        URL url = new URL(strUrl);    
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
        conn.setRequestMethod("GET");    
        conn.setConnectTimeout(5 * 1000);    
        InputStream inStream = conn.getInputStream();//通过输入流获取图片数据    
        BufferedImage biImg = ImageIO.read(inStream);
        return biImg;    
          
    }  
	
	
	
}
