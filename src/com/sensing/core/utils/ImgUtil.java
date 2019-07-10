package com.sensing.core.utils;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImgUtil {

	// 图片宽度小于200，放大3倍，返回byte[]
	public static byte[] ShowImgByByte(byte[] imgBytesIn) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.createImage(imgBytesIn);
		image = new ImageIcon(image).getImage();
		int srcWidth = image.getWidth(null); // 源图宽度
		if (srcWidth < 200) {
			Image new_img = image.getScaledInstance(srcWidth * 3, image.getHeight(null) * 3, Image.SCALE_SMOOTH);
			// 保存放大后的图
			try {
				BufferedImage bufferedImage = toBufferedImage(new_img);
				return imageToBytes(new_img, bufferedImage);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		} else {
			return imgBytesIn;
		}
		return null;
	}

	// Image 转 byte[]
	public static byte[] imageToBytes(Image image, BufferedImage bufferedImage) {

		Graphics bg = bufferedImage.getGraphics();
		bg.drawImage(image, 0, 0, null);
		bg.dispose();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bufferedImage, "jpg", out);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return out.toByteArray();
	}

	// Image 转 BufferedImage
	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent Pixels
		// boolean hasAlpha = hasAlpha(image);

		// Create a buffered image with a format that's compatible with the screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			/*
			 * if (hasAlpha) { transparency = Transparency.BITMASK; }
			 */

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			// int type = BufferedImage.TYPE_3BYTE_BGR;//by wang
			/*
			 * if (hasAlpha) { type = BufferedImage.TYPE_INT_ARGB; }
			 */
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}

	public static byte[] loadRawDataFromURL(String u) throws Exception {
		URL url = new URL(u);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		InputStream is = conn.getInputStream();
		BufferedInputStream bis = new BufferedInputStream(is);

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// 缓存2KB
		final int BUFFER_SIZE = 2 * 1024;
		final int EOF = -1;

		int c;
		byte[] buf = new byte[BUFFER_SIZE];

		while (true) {
			c = bis.read(buf);
			if (c == EOF)
				break;

			baos.write(buf, 0, c);
		}

		conn.disconnect();
		is.close();

		byte[] data = baos.toByteArray();
		baos.flush();

		return data;
	}

	/**
	 * 通过图片的url获取图片的base64字符串
	 * 
	 * @param imgUrl 图片url
	 * @return 返回图片base64的字符串
	 */
	public static String image2Base64(String imgUrl) {
		URL url = null;
		InputStream is = null;
		ByteArrayOutputStream outStream = null;
		HttpURLConnection httpUrl = null;
		try {
			url = new URL(imgUrl);
			httpUrl = (HttpURLConnection) url.openConnection();
			httpUrl.connect();
			httpUrl.getInputStream();
			is = httpUrl.getInputStream();
			outStream = new ByteArrayOutputStream();
			// 创建一个Buffer字符串
			byte[] buffer = new byte[1024];
			// 每次读取的字符串长度，如果为-1，代表全部读取完毕
			int len = 0;
			// 使用一个输入流从buffer里把数据读取出来
			while ((len = is.read(buffer)) != -1) {
				// 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
				outStream.write(buffer, 0, len);
			}
			// 对字节数组Base64编码
			return Base64Util.encode(outStream.toByteArray());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outStream != null) {
				try {
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (httpUrl != null) {
				httpUrl.disconnect();
			}
		}
		return imgUrl;
	}

}
