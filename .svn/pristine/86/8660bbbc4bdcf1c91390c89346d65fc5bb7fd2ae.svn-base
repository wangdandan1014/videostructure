package com.sensing.core.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.streaming.SXSSFCell;
import org.apache.poi.xssf.streaming.SXSSFDrawing;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;

/**
 * POI的工具类
 * 
 * @author ming
 * @date 2015-08-28
 * @version V1.0
 */
public class POIUtils {
	private static final Log log = LogFactory.getLog(POIUtils.class);

	/**
	 * 设置单元格
	 * 
	 * @param hssfRow 行对象
	 * @param style   样式
	 * @param value   填入值
	 * @param col     列数（从1开始）
	 * @author mingxingyu
	 * @date 2018年1月30日 上午11:00:27
	 */
	public static void setCell(SXSSFRow hssfRow, CellStyle style, String value, int col) {
		SXSSFCell cell = hssfRow.createCell(col - 1);
		cell.setCellStyle(style);
		cell.setCellValue(value);
	}

	/**
	 * 设置单元格
	 * 
	 * @param hssfRow 行对象
	 * @param style   样式
	 * @param value   填入值
	 * @param col     列数（从1开始）
	 * @author mingxingyu
	 * @date 2018年1月30日 上午11:00:27
	 */
	public static void setLinkCell(SXSSFWorkbook workbook, SXSSFRow hssfRow, CellStyle style, String value, String addr,
			int col) {
		SXSSFCell cell = hssfRow.createCell(col - 1);
		cell.setCellStyle(style);
		CreationHelper createHelper = workbook.getCreationHelper();
		XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.URL);
		link.setAddress(addr);
		cell.setHyperlink(link);
		cell.setCellValue(value);
	}

//	public static void setLinkCell(SXSSFWorkbook workbook, SXSSFRow hssfRow, CellStyle style, String value, String addr,
//			int col) {
//		SXSSFCell cell = hssfRow.createCell(col - 1);
//		CreationHelper createHelper = workbook.getCreationHelper();
//		XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.URL);
//		link.setAddress(addr);
//		cell.setHyperlink(link);
//		cell.setCellValue(value);
//		// 设置样式
//		Font font = getFont(workbook, "宋体", false, 11);
//		CellStyle linkStyle = getCellStyle(workbook, font, 2, 2, "0000");
//		getCellStyle(workbook, font, 2, 2, "0000");
//		Font cellFont = workbook.createFont();
//		cellFont.setUnderline((byte) 1);
//		cellFont.setColor(IndexedColors.BLUE.getIndex());
//		linkStyle.setFont(cellFont);
//		cell.setCellStyle(linkStyle);
//
//	}

	/**
	 * 设置超链接样式
	 * 
	 */
	public static CellStyle getLinkCellStyle(SXSSFWorkbook workbook) {
		Font font = getFont(workbook, "宋体", false, 11);
		CellStyle linkStyle = getCellStyle(workbook, font, 2, 2, "0000");
		font.setColor(IndexedColors.BLUE.getIndex());
		linkStyle.setFont(font);
		return linkStyle;

	}

	/**
	 * 设置一行数据的值
	 * 
	 * @param row       行对象
	 * @param cellStyle 每个单元格的样式
	 * @param values    数组值
	 * @author mingxingyu
	 * @date 2018年1月29日 下午3:39:42
	 */
	public static void setRow(SXSSFRow row, CellStyle cellStyle, String... values) {
		for (int i = 0; i < values.length; i++) {
			SXSSFCell cell = row.createCell(i);
			cell.setCellStyle(cellStyle);
			cell.setCellValue(values[i]);
		}
	}

	/**
	 * 获取工作簿的字体
	 * 
	 * @param workbook 工作簿对象
	 * @param fontName 字体的名称
	 * @param isBold   是否加粗 true or flase
	 * @param fontSize 字体的大小
	 * @return
	 * @author ming
	 * @date 2015-8-28
	 */
	public static Font getFont(SXSSFWorkbook workbook, String fontName, boolean isBold, Integer fontSize) {
		Font font = workbook.createFont();
		if (fontName != null && fontName != "") {
			font.setFontName(fontName);
		}
		if (isBold) {
			font.setBold(true);
		}
		if (fontSize != null) {
			font.setFontHeightInPoints(Short.parseShort(fontSize + ""));
		}
		return font;
	}

	/**
	 * 创建单元格样式
	 * 
	 * @param workbook    工作簿
	 * @param align       水平的对齐方式 1：左；2：中；3：右
	 * @param vertAlign   垂直的对齐方式 1：上；2：中；3：下
	 * @param borderStyle 边框线样式:字符串，如0000代表上下左右都不要边框，
	 *                    如1100代表上下要边框，如0011代表左右要边框，如果0220代表左边和下边要粗边框
	 * @return
	 * @author ming
	 * @date 2015-8-28
	 */
	public static CellStyle getCellStyle(SXSSFWorkbook workbook, Font fontStyle, Integer align, Integer vertAlign,
			String borderStyle) {
		CellStyle style = workbook.createCellStyle();

		if (align != null) {
			switch (align) {
			case 1:
				style.setAlignment(HorizontalAlignment.LEFT);
				break;
			case 2:
				style.setAlignment(HorizontalAlignment.CENTER);
				break;
			case 3:
				style.setAlignment(HorizontalAlignment.RIGHT);
				break;
			default:
				break;
			}
		}
		if (vertAlign != null) {
			switch (vertAlign) {
			case 1:
				style.setVerticalAlignment(VerticalAlignment.TOP);
				break;
			case 2:
				style.setVerticalAlignment(VerticalAlignment.CENTER);
				break;
			case 3:
				style.setVerticalAlignment(VerticalAlignment.BOTTOM);
				break;
			default:
				break;
			}
		}

		// 设置边框
		if (borderStyle != null && borderStyle.length() == 4) {
			char[] bs = borderStyle.toCharArray();
			if (bs[0] == '1') {
				style.setBorderTop(BorderStyle.THIN);
			} else if (bs[0] == '2') {
				style.setBorderTop(BorderStyle.MEDIUM);
			}
			if (bs[1] == '1') {
				style.setBorderBottom(BorderStyle.THIN);
			} else if (bs[1] == '2') {
				style.setBorderBottom(BorderStyle.MEDIUM);
			}
			if (bs[2] == '1') {
				style.setBorderLeft(BorderStyle.THIN);
			} else if (bs[2] == '2') {
				style.setBorderLeft(BorderStyle.MEDIUM);
			}
			if (bs[3] == '1') {
				style.setBorderRight(BorderStyle.THIN);
			} else if (bs[3] == '2') {
				style.setBorderRight(BorderStyle.MEDIUM);
			}
		}
		style.setFont(fontStyle);

		return style;
	}

	/**
	 * 设置sheet的列宽
	 * 
	 * @param sheet
	 * @param cols
	 * @author ming
	 * @date 2015-8-28
	 */
	public static void setColumnWidth(SXSSFSheet sheet, Integer... cols) {
		for (int i = 0; i < cols.length; i++) {
			sheet.setColumnWidth(i, cols[i] * 255 + 184);
		}
	}

	/**
	 * 合并sheet的单元格
	 * 
	 * @param sheet
	 * @param rowNum1 单元格1的行数
	 * @param colNum1 单元格1的列数
	 * @param rowNum2 单元格2的行数
	 * @param colNum2 单元格2的列数
	 * @author ming
	 * @date 2015-8-28
	 */
	public static void addMergedRegion(SXSSFSheet sheet, Integer rowNum1, Integer colNum1, Integer rowNum2,
			Integer colNum2) {
		sheet.addMergedRegion(new CellRangeAddress(rowNum1 - 1, rowNum2 - 1, colNum1 - 1, colNum2 - 1));
	}

	/**
	 * 获取一行的Row数据
	 * 
	 * @param sheet
	 * @param rowNum 行号（从1开始）
	 * @param height 行高
	 * @return
	 * @author ming
	 * @date 2015-8-28
	 */
	public static SXSSFRow getHSSFRow(SXSSFSheet sheet, Integer rowNum, Integer height) {
		SXSSFRow row = sheet.createRow(rowNum - 1);
		if (height != null) {
			row.setHeight(new Short((height * 20) + ""));
		}
		return row;
	}

	/**
	 * 往单元格添加图片信息
	 * 
	 * @param workbook      工作簿
	 * @param patriarch     画图工具
	 * @param bufferedImage 图片bufferedImage对象
	 * @param fileType      文件类型
	 * @param row           行数（从1开始）
	 * @param col           列数（从1开始）
	 * @author mingxingyu
	 * @date 2018年2月1日 下午2:53:12
	 */
	public static void setPicture(SXSSFWorkbook workbook, SXSSFDrawing patriarch, BufferedImage bufferedImage,
			String fileType, int row, int col) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		try {
			if (fileType == null) {
				ImageIO.write(bufferedImage, "jpg", os);
			} else if ("jpg".equals(fileType) || "JPG".equals(fileType) || "JPEG".equals(fileType)
					|| "jpeg".equals(fileType)) {
				ImageIO.write(bufferedImage, "jpg", os);
			} else if ("png".equals(fileType) || "PNG".equals(fileType)) {
				ImageIO.write(bufferedImage, "png", os);
			}

			byte[] bytes = os.toByteArray();
			int pictureIdx = 0;
			if (fileType == null) {
				pictureIdx = workbook.addPicture(bytes, SXSSFWorkbook.PICTURE_TYPE_JPEG);
			} else if ("jpg".equals(fileType) || "JPG".equals(fileType) || "JPEG".equals(fileType)
					|| "jpeg".equals(fileType)) {
				pictureIdx = workbook.addPicture(bytes, SXSSFWorkbook.PICTURE_TYPE_JPEG);
			} else if ("png".equals(fileType) || "PNG".equals(fileType)) {
				pictureIdx = workbook.addPicture(bytes, SXSSFWorkbook.PICTURE_TYPE_PNG);
			}

			// 创建一个顶级容器
			CreationHelper helper = workbook.getCreationHelper();
			ClientAnchor anchor = helper.createClientAnchor();
			anchor.setCol1(col - 1);
			anchor.setRow1(row - 1);
			anchor.setCol2(col - 1);
			anchor.setRow2(row - 1);

			Picture pict = patriarch.createPicture(anchor, pictureIdx);
			pict.resize(1, 1);
			os.close();
		} catch (Exception e) {
			log.error(e);
		}
	}

}