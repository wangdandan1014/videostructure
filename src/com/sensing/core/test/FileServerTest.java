package com.sensing.core.test;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.ImageFile;
import com.sensing.core.utils.httputils.HttpDeal;

public class FileServerTest {
	
	public static final String PHOTO_SERVER_ADDR = "http://192.168.1.217:8400/image/group1/M00/12/1A/wKgB2Vz1_wGAUh5TAAAXqvTBYF0239.jpg";
	public static final String PHOTO_ADDR = "C:\\Users\\15039\\Desktop\\图片\\1.jpg";
//	public static final String PHOTO_ADDR = "C:\\Users\\Administrator\\Desktop\\1.mp4";
	
	
	public static void main(String[] args) throws Exception {
		// 将图片保存到图片服务器
		String seceneURI = UUID.randomUUID().toString().replaceAll("-","")+".jpg";
		String secenePut = HttpDeal.doPut(seceneURI, image2Bytes(PHOTO_ADDR));
		ImageFile seceneImageFile = JSONObject.toJavaObject(
				JSONObject.parseObject(secenePut), ImageFile.class);
		if (seceneImageFile.getError() >= 0) {
			System.out.println("返回的地址为："+seceneImageFile.getMessage());
		} else {
			System.out.println("未获取到图片的地址。");
		}
	}
	public static byte[] image2Bytes(String imgSrc) throws Exception {
		
        byte[] buffer = null;  
        try {  
            File file = new File(imgSrc);  
            FileInputStream fis = new FileInputStream(file);  
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
            byte[] b = new byte[1000];  
            int n;  
            while ((n = fis.read(b)) != -1) {  
                bos.write(b, 0, n);  
            }  
            fis.close();  
            bos.close();  
            buffer = bos.toByteArray();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        
        
        return buffer;  
        
//		FileInputStream fin = new FileInputStream(new File(imgSrc));
//		// 可能溢出,简单起见就不考虑太多,如果太大就要另外想办法，比如一次传入固定长度byte[]
//		byte[] bytes = new byte[fin.available()];
//		// 将文件内容写入字节数组，提供测试的case
//		fin.read(bytes);
//		fin.close();
//		return bytes;
	}
	
   public static void ByteToFile(byte[] bytes)throws Exception{   
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);     
        BufferedImage bi1 =ImageIO.read(bais);   
        try {     
            File w2 = new File("W:\\img\\00000000003.jpg");//可以是jpg,png,gif格式     
            ImageIO.write(bi1, "jpg", w2);//不管输出什么格式图片，此处不需改动     
        } catch (IOException e) {     
            e.printStackTrace();     
        }finally{  
            bais.close();  
        }  
    }
}
