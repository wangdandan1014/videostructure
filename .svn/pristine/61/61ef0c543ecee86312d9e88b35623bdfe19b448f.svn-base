/*
 * Java class for BitAnswer client library
 * BitAnswer Ltd. (C) 2009 - ?. All rights reserved.
 */
package com.bitanswer.library; /* please do not modify the package name */

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * The Class BitAnswer.
 */
public class BitAnswer {

	private static final byte[] APPLICATION_DATA = {
		(byte)0x40,(byte)0x80,(byte)0xbc,(byte)0x4e,(byte)0x93,(byte)0x4b,(byte)0xa2,(byte)0xae,(byte)0x6f,(byte)0xa6,(byte)0xb9,(byte)0xdd,(byte)0x32,(byte)0xb9,(byte)0x3e,(byte)0xb0,
    (byte)0x71,(byte)0xd8,(byte)0xb5,(byte)0x0b,(byte)0xde,(byte)0xdc,(byte)0x58,(byte)0x8d,(byte)0xa0,(byte)0x2a,(byte)0x49,(byte)0xbf,(byte)0x75,(byte)0x80,(byte)0x45,(byte)0x28,
    (byte)0x2f,(byte)0xc7,(byte)0x6c,(byte)0xb5,(byte)0x01,(byte)0x85,(byte)0xce,(byte)0x21,(byte)0xcb,(byte)0xbb,(byte)0x7f,(byte)0xa0,(byte)0x43,(byte)0x15,(byte)0x55,(byte)0xda,
    (byte)0x4e,(byte)0x97,(byte)0x9b,(byte)0x1a,(byte)0xc7,(byte)0xb1,(byte)0x59,(byte)0x16,(byte)0xc7,(byte)0x05,(byte)0xe5,(byte)0xf5,(byte)0xb9,(byte)0x31,(byte)0x0b,(byte)0xca,
    (byte)0x77,(byte)0xb5,(byte)0x5d,(byte)0xbb,(byte)0xd2,(byte)0x8a,(byte)0x4a,(byte)0x90,(byte)0xa1,(byte)0x34,(byte)0x7a,(byte)0x6e,(byte)0x2b,(byte)0x70,(byte)0x4a,(byte)0x6c,
    (byte)0xc8,(byte)0x5f,(byte)0xa3,(byte)0x8d,(byte)0xda,(byte)0xdd,(byte)0x75,(byte)0x11,(byte)0x45,(byte)0x07,(byte)0x45,(byte)0x91,(byte)0xf1,(byte)0x5c,(byte)0x24,(byte)0x94,
    (byte)0x1f,(byte)0x33,(byte)0x38,(byte)0x8e,(byte)0xa4,(byte)0x0f,(byte)0x95,(byte)0x9a,(byte)0xf1,(byte)0x8a,(byte)0xfa,(byte)0x30,(byte)0x56,(byte)0x4a,(byte)0x41,(byte)0xee,
    (byte)0x76,(byte)0x7e,(byte)0xa8,(byte)0xab,(byte)0x07,(byte)0x0a,(byte)0xb3,(byte)0x07,(byte)0x52,(byte)0xb1,(byte)0xdf,(byte)0x25,(byte)0xe7,(byte)0x2e,(byte)0x38,(byte)0x84,
    (byte)0xc9,(byte)0x25,(byte)0x99,(byte)0x77,(byte)0x1c,(byte)0x94,(byte)0xba,(byte)0x19,(byte)0xe4,(byte)0x47,(byte)0xfa,(byte)0xa9,(byte)0xca,(byte)0xaf,(byte)0x3e,(byte)0xee,
    (byte)0xdc,(byte)0x03,(byte)0x08,(byte)0x9b,(byte)0x2c,(byte)0x90,(byte)0xd1,(byte)0x8f,(byte)0x37,(byte)0x46,(byte)0x7c,(byte)0x28,(byte)0xa4,(byte)0x70,(byte)0x4f,(byte)0x7d,
    (byte)0xc3,(byte)0xba,(byte)0x98,(byte)0x0e,(byte)0x95,(byte)0xa8,(byte)0xcc,(byte)0xbe,(byte)0xf2,(byte)0x07,(byte)0x69,(byte)0x7c,(byte)0x70,(byte)0x21,(byte)0x88,(byte)0xdc,
    (byte)0xc7,(byte)0xcc,(byte)0x8d,(byte)0x9f,(byte)0x31,(byte)0xd7,(byte)0x48,(byte)0x52,(byte)0x6f,(byte)0xfc,(byte)0x48,(byte)0xe9,(byte)0x01,(byte)0xe7,(byte)0x3c
	};

	private static final String RUNTIME_LIBRARY_DEFAULT = "00003221_00000001.dll";
	private static final String RUNTIME_LIBRARY_DEFAULT_X64 = "00003221_00000001_x64.dll";
	
	private static final String LINUX_LIBRARY_DEFAULT = "lib00003221_00000001.so";
	private static final String LINUX_LIBRARY_DEFAULT_X64 = "lib00003221_00000001_x64.so";

   public enum LoginMode {
      LOCAL(1),
      REMOTE(2),
      AUTO(3),
      USB(8),
      PROCESS(16);

      private int value;

      LoginMode(int value) {
         this.value = value;
      }

      public int getValue() {
         return value;
      }
   }

   public enum BindingType {
      EXISTING,
      LOCAL,
      USB;
   }

   public enum SessionType {
      XML_TYPE_SN_INFO(3),
      XML_TYPE_FEATURE_INFO(4),

      BIT_ADDRESS(0x101),
      BIT_SYS_TIME(0x201),
      BIT_CONTROL_TYPE(0x302),
      BIT_VOL_NUM(0x303),
      BIT_START_DATE(0x304),
      BIT_END_DATE(0x305),
      BIT_EXPIRATION_DAYS(0x306),
      BIT_USAGE_NUM(0x307),
      BIT_CONSUMED_USAGE_NUM(0x308),
      BIT_CONCURRENT_NUM(0x309),
      BITL_ACTIVATE_DATE(0x30A),
      BIT_USER_LIMIT(0x30B),
      BIT_LAST_REMOTE_ACCESS_DATE(0x30C),
      BIT_MAX_OFFLINE_MINUTES(0x30D),
      BIT_NEXT_CONNECT_DATE(0X30E);

      private int value;

      SessionType(int value) {
         this.value = value;
      }

      int getValue() {
         return value;
      }
   }
   
   public enum InfoType {
      BIT_LIST_SRV_ADDR(0), 
      BIT_LIST_LOCAL_SN_INFO(1), 
      BIT_LIST_LOCAL_SN_FEATURE_INFO(2), 
      BIT_LIST_LOCAL_SN_LIC_INFO(3), 
      BIT_LIST_UPDATE_ERROR(4);

      private int value;

      private InfoType(int value) {
         this.value = value;
      }

      public int getValue() {
         return value;
      }
   }

   public class BitAnswerException extends Exception {

      private static final long serialVersionUID = 825304441781544159L;

      private int               errorCode;

      public BitAnswerException(int errorCode) {
         this.errorCode = errorCode;
      }

      public int getErrorCode() {
         return errorCode;
      }
   }

   public static native int Bit_Login(final String url, final String sn, final byte[] pApplicationData,
         long[] handle, int mode);

   public static native int Bit_LoginEx(final String url, final String sn, final int featureId, final String xmlScope,
         final byte[] applicationData, long[] handle, int mode);

   public static native int Bit_ReadFeature(final long handle, final int featureId, int[] featureValue);

   public static native int Bit_WriteFeature(final long handle, final int featureId, final int featureValue);

   public static native int Bit_QueryFeature(final long handle, final int featureId, int[] capacity);

   public static native int Bit_ReleaseFeature(final long handle, final int featureId, int[] capacity);

   public static native int Bit_ConvertFeature(final long handle, final int featureId, final int para1,
         final int para2, final int para3, final int para4, int[] pResult);

   public static native int Bit_EncryptFeature(final long handle, final int featureId,
         final byte[] pPlainBuffer, byte[] pCipherBuffer, int dataBufferSize);

   public static native int Bit_DecryptFeature(final long handle, final int dwFeatureId,
         final byte[] pCipherBuffer, byte[] pPlainBuffer, int dataBufferSize);

   public static native int Bit_SetDataItem(final long handle, final String dataItemName,
         final byte[] dataItemValue, final int dataItemValueSize);

   public static native int Bit_RemoveDataItem(final long handle, final String dataItemName);

   public static native int Bit_GetDataItem(final long handle, final String dataItemName,
         byte[] dataItemValue, int[] dataItemValueSize);

   public static native int Bit_GetDataItemNum(final long handle, int[] num);

   public static native int Bit_GetDataItemName(final long handle, final int index, String[] dataItemName);

   public static native int Bit_GetSessionInfo(final long handle, final int type, String[] sessionInfo);

   public static native int Bit_UpdateOnline(final String url, final String serialNumber, final byte[] applicationData);

   public static native int Bit_GetRequestInfo(final String sn, final byte[] applicationData, final int type,
         String[] requestInfo);

   public static native int Bit_GetUpdateInfo(final String url, final String sn, final byte[] applicationData,
         String requestInfo, String[] updateInfo);

   public static native int Bit_ApplyUpdateInfo(final byte[] applicationData, final String updateInfo, String[] receiptInfo);

   public static native int Bit_Revoke(final String url, final String sn, final byte[] applicationData,
         final String[] revocationInfo);

   public static native int Bit_Logout(final long handle);

   public static native int Bit_SetLocalServer(final byte[] applicationData, final String hostName, final int port, final int timeoutSecond);

   public static native int Bit_SetProxy(final byte[] applicationData, final String hostName, final int port, final String userId, final String userPassword);

   public static native int Bit_GetProductPath(final byte[] applicaitionData, String[] productPath);

   public static native int Bit_SetRootPath(final String rootPath);

   public static native int Bit_GetInfo(final String sn, byte[] applicationData, final int type, String[] info);

   public static native int Bit_GetVersion(int[] version);

   public static native int Bit_SetAppVersion(final int version);

   public static native int Bit_RemoveSn(final String sn, final byte[] applicationData);

   public static native int Bit_GetBorrowRequest(final String sn, final byte[] applicationData, final int durationMinutes, String[] requestInfo);

   public static native int Bit_ApplayBorrowInfo(final byte[] applicationData, final String borrowInfo);

   public static native int Bit_ProcessBorrowRequest(final byte[] applicationData, final String requestInfo, String[] borrowInfo);

   public static native int Bit_TestService(final String url, final String sn, final int featureId, final byte[] applicationData);

   public static native int Bit_CheckOutSn(final String url, final int featureId, final byte[] applicationData, final int durationDays);

   public static native int Bit_CheckOutFeature(final String url, final byte[] applicationData, final int[] featureList, final int durationDays);

   public static native int Bit_CheckIn(final String url, final int featureId, final byte[] applicationData);

   public static native int Bit_SetCustomInfo(final int infoId, final byte[] infoData, final int infoDataSize);

   private long handle = 0;

   /**
    * Instantiates a new BitAnswer.
    * 
    * @throws Exception
    */
   public BitAnswer() {
     String path = BitAnswer.class.getClassLoader().getResource("").getPath() + "dll/";
     
      if (path != null) {
         try {
            path = URLDecoder.decode(path, "UTF-8");
         } catch (UnsupportedEncodingException e) {
            /* please do not modify the above code! */
         }
      }

      try{
    	  String os = System.getProperty("os.name");
          /* x86 or amd64 */
          String arch = System.getProperties().getProperty("os.arch");

          /* windows library loading */
          String fileName = path;
          if (os.indexOf("Windows") >= 0) {
             if (arch.compareToIgnoreCase("x86") == 0) {
                fileName += RUNTIME_LIBRARY_DEFAULT;
             } else if (arch.compareToIgnoreCase("amd64") == 0) {
                fileName += RUNTIME_LIBRARY_DEFAULT_X64;
             } else {
                fileName += RUNTIME_LIBRARY_DEFAULT;
             }
          }
          else if (os.indexOf("Linux") >= 0) {
             if (arch.compareToIgnoreCase("x86") == 0) {
                fileName += LINUX_LIBRARY_DEFAULT;
             } else if (arch.compareToIgnoreCase("amd64") == 0) {
                fileName += LINUX_LIBRARY_DEFAULT_X64;
             } else {
                fileName += LINUX_LIBRARY_DEFAULT;
             }
          }
          System.load(fileName);
      }catch(Exception e){
    	  System.out.println("初始化BitAnswer对象失败！" + e.getMessage());
      }
   }

   /**
    * login to Authorization Code, must call it before any features or data operations
    *
    * @param url
    *           the server address, including the port. BitAnswer Platform set to null.
    * @param sn
    *           Authorization Code string, if it is null, find all available authorization code in local machine.
    * @param mode
    *           the login mode
    * @throws BitAnswerException
    */
   public int login(String url, String sn, LoginMode mode){
      long[] handles = new long[1];
      int status = Bit_Login(url, sn, APPLICATION_DATA, handles, mode.getValue());
      if (status != 0) {
    	 System.out.println("登录授权失败，错误码为：" + status);
      }
      handle = handles[0];
      return status;
   }

   /**
    * login to serial number and specified feature, must call it before any features or data operations.
    *
    * @param url
    *           url the server address, including the port. BitAnswer Platform set to null.
    * @param sn
    *           Authorization Code string, if it is null, find all available authorization code in local machine.
    * @param featureId
    *           the feature id
    * @param mode
    *           the login mode
    * @throws BitAnswerException
    */
   public void loginEx(String url, String sn, int featureId, LoginMode mode) throws BitAnswerException {
      long[] handles = new long[1];
      int status = Bit_LoginEx(url, sn, featureId, null, APPLICATION_DATA, handles, mode.getValue());
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      handle = handles[0];
   }

   /**
    * Logout.
    *
    * @throws BitAnswerException
    */
   public void logout() throws BitAnswerException {
      int status = Bit_Logout(handle);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * Read feature.
    *
    * @param featureId
    *           the feature id
    * @return the value of the feature
    * @throws BitAnswerException
    */
   public int readFeature(int featureId) throws BitAnswerException {
      int[] featureValue = new int[1];
      int status = Bit_ReadFeature(handle, featureId, featureValue);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return featureValue[0];
   }

   /**
    * Write feature.
    *
    * @param featureId
    *           the feature id
    * @param featureValue
    *           the feature value
    * @throws BitAnswerException
    */
   public void writeFeature(int featureId, int featureValue) throws BitAnswerException {
      int status = Bit_WriteFeature(handle, featureId, featureValue);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * query feature.
    * 
    * @param featureId
    *           the feature id
    * @return
    * @throws BitAnswerException
    */
   public int queryFeature(int featureId) throws BitAnswerException {
      int[] capacity = new int[1];
      int status = Bit_QueryFeature(handle, featureId, capacity);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return capacity[0];
   }

   /**
    * release feature.
    * 
    * @param featureId
    *           the feature id
    * @return
    * @throws BitAnswerException
    */
   public int releaseFeature(int featureId) throws BitAnswerException {
      int[] capacity = new int[1];
      int status = Bit_ReleaseFeature(handle, featureId, capacity);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return capacity[0];
   }

   /**
    * feature convert.
    *
    * @param featureId
    *           the feature id
    * @param para1
    *           the parameters 1
    * @param para2
    *           the parameters 2
    * @param para3
    *           the parameters 3
    * @param para4
    *           the parameters 4
    * @return the result of conversion
    * @throws BitAnswerException
    */
   public int convertFeature(int featureId, int para1, int para2, int para3, int para4) throws BitAnswerException {
      int[] pResult = new int[1];
      int status = Bit_ConvertFeature(handle, featureId, para1, para2, para3, para4, pResult);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return pResult[0];
   }

   /**
    * feature encryption.
    *
    * @param featureId
    *           the feature id
    * @param pPlainBuffer
    *           the plain buffer
    * @return the cipher buffer
    * @throws BitAnswerException
    */
   public byte[] encryptFeature(int featureId, byte[] pPlainBuffer) throws BitAnswerException {
      int dataBufferLen = pPlainBuffer.length;
      byte[] pCipherBuffer = new byte[dataBufferLen];
      int status = Bit_EncryptFeature(handle, featureId, pPlainBuffer, pCipherBuffer, dataBufferLen);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return pCipherBuffer;
   }

   /**
    * feature decryption.
    *
    * @param featureId
    *           the feature id
    * @param pCipherBuffer
    *           the cipher buffer
    * @return the plain buffer
    * @throws BitAnswerException
    */
   public byte[] decryptFeature(int featureId, byte[] pCipherBuffer) throws BitAnswerException {
      int dataBufferLen = pCipherBuffer.length;
      byte[] pPlainBuffer = new byte[dataBufferLen];
      int status = Bit_DecryptFeature(handle, featureId, pPlainBuffer, pCipherBuffer, dataBufferLen);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return pCipherBuffer;
   }

   /**
    * Sets the data item.
    *
    * @param dataItemName
    *           the data item name
    * @param dataItemValue
    *           the data item value
    * @throws BitAnswerException
    */
   public void setDataItem(String dataItemName, byte[] dataItemValue) throws BitAnswerException {
      int status = Bit_SetDataItem(handle, dataItemName, dataItemValue, dataItemValue.length);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * Removes the data item.
    *
    * @param dataItemName
    *           the data item name
    * @throws BitAnswerException
    */
   public void removeDataItem(String dataItemName) throws BitAnswerException {
      int status = Bit_RemoveDataItem(handle, dataItemName);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * Gets the data item.
    *
    * @param dataItemName
    *           the data item name
    * @return the data item value
    * @throws BitAnswerException
    */
   public byte[] getDataItem(String dataItemName) throws BitAnswerException {
      int[] lengths = { 1024 };
      byte[] dataItemValue = new byte[1024];
      int status = Bit_GetDataItem(handle, dataItemName, dataItemValue, lengths);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      byte[] result = new byte[lengths[0]];
      System.arraycopy(dataItemValue, 0, result, 0, lengths[0]);

      return result;
   }

   /**
    * Gets the data items count.
    *
    * @return the data items number
    * @throws BitAnswerException
    */
   public int getDataItemNum() throws BitAnswerException {
      int[] nums = new int[1];
      int status = Bit_GetDataItemNum(handle, nums);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return nums[0];
   }

   /**
    * Gets the data item name.
    *
    * @param index
    *           the data index
    * @return the data item name
    * @throws BitAnswerException
    */
   public String getDataItemName(int index) throws BitAnswerException {
      String[] dataItemName = new String[1];
      int status = Bit_GetDataItemName(handle, index, dataItemName);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return dataItemName[0];
   }

   /**
    * Gets the session information.
    *
    * @param type
    *           the type
    * @return the session info
    * @throws BitAnswerException
    */
   public String getSessionInfo(SessionType type) throws BitAnswerException {
      String[] sessionInfo = new String[1];
      int status = Bit_GetSessionInfo(handle, type.getValue(), sessionInfo);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return sessionInfo[0];
   }

   /**
    * Update online.
    *
    * @param url
    *           authentication url
    * @param sn
    *           the serial number
    * @throws BitAnswerException
    */
   public void updateOnline(String url, String sn) throws BitAnswerException {
      int status = Bit_UpdateOnline(url, sn, APPLICATION_DATA);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * Gets the request info code.
    *
    * @param sn
    *           the serial number
    * @param type
    *           the type
    * @return the request info
    * @throws BitAnswerException
    */
   public String getRequestInfo(String sn, BindingType type) throws BitAnswerException {
      String[] requestInfo = new String[1];
      int status = Bit_GetRequestInfo(sn, APPLICATION_DATA, type.ordinal(), requestInfo);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return requestInfo[0];
   }

   /**
    * Gets the update info code.
    *
    * @param url
    *           the url
    * @param sn
    *           the serial number
    * @param requestInfo
    *           the request info
    * @return the update info
    * @throws BitAnswerException
    */
   public String getUpdateInfo(String url, String sn, String requestInfo) throws BitAnswerException {
      String[] updateInfo = new String[1];
      int status = Bit_GetUpdateInfo(url, sn, APPLICATION_DATA, requestInfo, updateInfo);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return updateInfo[0];
   }

   /**
    * Applay the update info code.
    *
    * @param updateInfo
    *           the update info
    * @return the string
    * @throws BitAnswerException
    */
   public String applyUpdateInfo(String updateInfo) throws BitAnswerException {
      String[] receiptInfo = new String[1];
      int status = Bit_ApplyUpdateInfo(APPLICATION_DATA, updateInfo, receiptInfo);
      if (status != 0) {
         throw new BitAnswerException(status);
      }

      return receiptInfo[0];
   }

   /**
    * Revoke sn from the machine
    *
    * @param url
    *           the url
    * @param sn
    *           the sn
    * @return the string
    * @throws BitAnswerException
    */
   public String revoke(String sn) throws BitAnswerException {
      String[] revocationInfo = new String[1];
      int status = Bit_Revoke(null, sn, APPLICATION_DATA, revocationInfo);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return revocationInfo[0];
   }

   /**
    * Revoke sn from the machine online.
    *
    * @param url
    *           the url
    * @param sn
    *           the sn
    * @throws BitAnswerException
    */
   public void revokeOnline(String url, String sn) throws BitAnswerException {
      int status = Bit_Revoke(url, sn, APPLICATION_DATA, null);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * set local server host name and port if the client connects to a local service.
    * 
    * @param hostName
    *           the local server host name
    * @param port
    *           the local server port
    * @param timeout
    *           timeout in seconds
    * @throws BitAnswerException
    */
   public void setLocalServer(String hostName, int port, int timeout) throws BitAnswerException {
      int status = Bit_SetLocalServer(APPLICATION_DATA, hostName, port, timeout);
      if (status != 0) {
    	 System.out.println("license设置集团服务器失败，错误码为：" + status);
         throw new BitAnswerException(status);
      }
   }

   /**
    * set proxy name, port, user ID and password if the client is connect Internet through a proxy server.
    * 
    * @param hostName
    *           the server host name
    * @param port
    *           the server port
    * @param userId
    *           user id
    * @param password
    *           password
    * @throws BitAnswerException
    */
   public void setProxy(String hostName, int port, String userId, String password) throws BitAnswerException {
      int status = Bit_SetProxy(APPLICATION_DATA, hostName, port, userId, password);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }
   
   /**
    * get sn information
    * @param sn
    *            the sn
    * @param type
    *            information type
    * @return 
    *            information           
    * @throws BitAnswerException
    */
   public String getInfo(String sn, InfoType type) throws BitAnswerException {
      String[] infos = new String[1];
      int status = Bit_GetInfo(sn, APPLICATION_DATA, type.getValue(), infos);
      System.out.println("license查询集团服务器失败，错误码为：" + status);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return infos[0];
   }

   /**
    * set custom information
    * @param infoId
    *            information ID
    * @param infoData
    *            information data
    * @throws BitAnswerException
    */
   public void setCustomInfo(int infoId, String infoData) throws BitAnswerException {
      int status = Bit_SetCustomInfo(infoId, infoData.getBytes(), infoData.getBytes().length);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * get product path
    * 
    * @return
    * @throws BitAnswerException
    */
   public String getProductPath() throws BitAnswerException {
      String[] productPaths = new String[1];
      int status = Bit_GetProductPath(APPLICATION_DATA, productPaths);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return productPaths[0];
   }

   /**
    * check in SN
    * 
    * @param url
    * @param featureId
    * @throws BitAnswerException
    */
   public void checkIn(String url, int featureId) throws BitAnswerException {
      int status = Bit_CheckIn(url, featureId, APPLICATION_DATA);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * check out sn
    * 
    * @param url
    * @param featureId
    * @param durationDays
    * @throws BitAnswerException
    */
   public void checkOutSn(String url, int featureId, int durationDays) throws BitAnswerException {
      int status = Bit_CheckOutSn(url, featureId, APPLICATION_DATA, durationDays);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * check out feature
    * 
    * @param url
    * @param featureList
    * @param durationDays
    * @throws BitAnswerException
    */
   public void checkOutFeature(String url, int[] featureList, int durationDays) throws BitAnswerException {
      int status = Bit_CheckOutFeature(url, APPLICATION_DATA, featureList, durationDays);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * test if local service is running
    * 
    * @param url
    * @param sn
    * @param featureId
    * @throws BitAnswerException
    */
   public void testBitService(String url, String sn, int featureId) throws BitAnswerException {
      int status = Bit_TestService(url, sn, featureId, APPLICATION_DATA);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * get borrow info
    * 
    * @param requestInfo
    * @return
    * @throws BitAnswerException
    */
   public String processBorrowRequest(String requestInfo) throws BitAnswerException {
      String[] borrowInfo = new String[1];
      int status = Bit_ProcessBorrowRequest(APPLICATION_DATA, requestInfo, borrowInfo);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return borrowInfo[0];
   }

   /**
    * apply borrow info
    * 
    * @param borrowInfo
    * @throws BitAnswerException
    */
   public void applyBorrowInfo(String borrowInfo) throws BitAnswerException {
      int status = Bit_ApplayBorrowInfo(APPLICATION_DATA, borrowInfo);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * get borrow request info
    * 
    * @param sn
    * @param durationMinutes
    * @return
    * @throws BitAnswerException
    */
   public String getBorrowRequestInfo(String sn, int durationMinutes) throws BitAnswerException {
      String[] requestInfo = new String[1];
      int status = Bit_GetBorrowRequest(sn, APPLICATION_DATA, durationMinutes, requestInfo);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return requestInfo[0];
   }

   /**
    * remove sn from local machine
    * 
    * @param sn
    * @throws BitAnswerException
    */
   public void removeSn(String sn) throws BitAnswerException {
      int status = Bit_RemoveSn(sn, APPLICATION_DATA);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * set app version
    * 
    * @param version
    * @throws BitAnswerException
    */
   public void setAppVersion(int version) throws BitAnswerException {
      int status = Bit_SetAppVersion(version);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }

   /**
    * get bitanswer library version
    * 
    * @return
    * @throws BitAnswerException
    */
   public int getVersion() throws BitAnswerException {
      int[] version = new int[1];
      int status = Bit_GetVersion(version);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
      return version[0];
   }

   public void setRootPath(String rootPath) throws BitAnswerException {
      int status = Bit_SetRootPath(rootPath);
      if (status != 0) {
         throw new BitAnswerException(status);
      }
   }
}
