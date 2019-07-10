package com.sensing.core.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.github.junrar.Archive;
import com.github.junrar.exception.RarException;
import com.github.junrar.exception.RarException.RarExceptionType;
import com.github.junrar.rarfile.FileHeader;

public class RarUtil {

	/**
	 * @param rarFileName rar file name
	 * @param outFilePath output file path
	 * @return success Or Failed
	 * @throws Exception
	 */
	public static List<String> unrar(String rarFileName, String outFilePath) throws Exception {
		List<String> list = new ArrayList<String>();
		try {
			Archive archive = new Archive(new File(rarFileName));
			if (archive == null) {
				throw new FileNotFoundException(rarFileName + " NOT FOUND!");
			}
			if (archive.isEncrypted()) {
				throw new Exception(rarFileName + " IS ENCRYPTED!");
			}
			List<FileHeader> files = archive.getFileHeaders();
			for (FileHeader fh : files) {
				if (fh.isEncrypted()) {
					throw new Exception(rarFileName + " IS ENCRYPTED!");
				}
				String fileName = fh.getFileNameW();
				if (fileName != null && fileName.trim().length() > 0) {
					String saveFileName = outFilePath + "\\" + fileName;
					// D://\车辆导入模版\pictures\1-1.jpg
					// D://\车辆导入模版\pictures\1-2.jpg
					File saveFile = new File(saveFileName);
					File parent = saveFile.getParentFile();
					if (!parent.exists()) {
						parent.mkdirs();
					}
					if (!saveFile.exists() && !saveFile.isDirectory()) {
						list.add(saveFile.getAbsolutePath());
						saveFile.createNewFile();
						FileOutputStream fos = new FileOutputStream(saveFile);
						try {
							archive.extractFile(fh, fos);
							fos.flush();
							fos.close();
						} catch (RarException e) {
							if (e.getType().equals(RarExceptionType.notImplementedYet)) {
							}
						} finally {
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return list;
	}
//	/**
//	 * @param rarFileName rar file name
//	 * @param outFilePath output file path  
//	 * @return success Or Failed
//	 * @author zhuss
//	 * @throws Exception
//	 */
//	public static boolean  unrar(String rarFileName, String outFilePath)  throws  Exception{  
//		
//		boolean flag = false;
//		try  {  
//			Archive archive = new  Archive(new File(rarFileName));  
//			if(archive == null){
//				throw new FileNotFoundException(rarFileName + " NOT FOUND!");
//			}
//			if(archive.isEncrypted()){
//				throw new Exception(rarFileName + " IS ENCRYPTED!");
//			}
//			List<FileHeader> files =  archive.getFileHeaders();
//			for (FileHeader fh : files) {
//				if(fh.isEncrypted()){
//					throw new Exception(rarFileName + " IS ENCRYPTED!");
//				} 
//				String fileName = fh.getFileNameW();
//				if(fileName != null && fileName.trim().length() > 0){
//					String saveFileName = outFilePath+"\\"+fileName;
//					File saveFile = new File(saveFileName);
//					File parent =  saveFile.getParentFile();
//					if(!parent.exists()){
//						parent.mkdirs();
//					}
//					if(!saveFile.exists()){
//						saveFile.createNewFile();
//					}
//					FileOutputStream fos = new FileOutputStream(saveFile);
//					try { 
//						archive.extractFile(fh, fos); 
//						fos.flush();
//						fos.close();
//					} catch (RarException e) { 
//						if(e.getType().equals(RarExceptionType.notImplementedYet)){ 
//						} 
//					}finally{ 
//					} 
//				}
//			}
//			flag = true;
//		} catch  (Exception e) {
//			e.printStackTrace();
//			throw e;
//		}  
//		return flag;
//	}

}
