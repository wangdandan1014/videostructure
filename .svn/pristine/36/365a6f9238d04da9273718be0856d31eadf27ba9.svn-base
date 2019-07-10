package com.sensing.core.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.sensing.core.utils.Exception.BussinessException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.alibaba.fastjson.JSONObject;
import com.sensing.core.bean.ImageFile;
import com.sensing.core.thrift.cap.bean.CapFacesSet;
import com.sensing.core.thrift.cap.service.CaptureServer.Client;
import com.sensing.core.thrift.cap.bean.CapFaceDetectResult;
import com.sensing.core.utils.httputils.HttpDeal;
import com.sensing.core.utils.props.PropUtils;
import com.sensing.core.utils.props.RemoteInfoUtil;
import com.sensing.core.utils.time.DateUtil;
import sun.misc.BASE64Decoder;

/**
 * cmp业务公共方法集合
 */
@SuppressWarnings("all")
public class CmpUtils {
    private static final Log log = LogFactory.getLog(CmpUtils.class);

    /**
     * 调用抓拍返回特征值,多个特征值返回最大的
     *
     * @param imageBytes
     * @return feature
     * @throws BussinessException
     * @author liuwei
     */
    public static ByteBuffer getFaceFeature(byte[] imageBytes) throws BussinessException {
        List<ByteBuffer> lstImgs = new ArrayList<ByteBuffer>();
        List<CapFacesSet> detectFaces = new ArrayList<CapFacesSet>();
        ByteBuffer feature = null;
        ByteBuffer byteBuffer = ByteBuffer.wrap(imageBytes);
        if (byteBuffer != null) {
            lstImgs.add(byteBuffer);
        } else {
            throw new BussinessException("图片传递有误，请确认");
        }
        TTransport transport = new TSocket(RemoteInfoUtil.CAP_SERVER_IP, RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
        Client client = null;
        if (!transport.isOpen()) {
            try {
                transport.open();
            } catch (TTransportException e) {
                log.error("抓拍服务连接异常！", e.fillInStackTrace());
                throw new BussinessException("抓拍服务连接异常!");
            }
        }

        TProtocol protocol = new TBinaryProtocol(transport);
        client = new Client(protocol);
        try {
            detectFaces = client.DetectFaces(lstImgs);//调用抓拍
            if (transport.isOpen()) {
                transport.close();
            }
        } catch (Exception e) {
            log.error("抓拍信息处理发生异常", e);
            throw new BussinessException("抓拍信息处理发生异常!");
        }


        // 赋特征值：如果有两个特征值，则获取最大的一个；计算方法：人脸特征的 脸宽度 X人脸高度 作为比较依据
        if (detectFaces != null && detectFaces.size() > 0) {
            // 判断是否有返回值
            CapFacesSet facesInfo = detectFaces.get(0);
            if (facesInfo != null && facesInfo.getLstFaces().size() > 0) {
                feature = getMaxFaceByte(facesInfo);
            } else {
                log.error("抓拍服务没有找到特征信息!");
                throw new BussinessException("抓拍服务没有找到特征信息!");
            }
        } else {
            log.error("抓拍服务没有找到特征信息!");
            throw new BussinessException("抓拍服务没有找到特征信息!");
        }
        return feature;
    }

    /**
     * 获取map中key值最大的对象
     *
     * @param map
     * @return
     * @author liuwei
     * @date 2018年3月29日上午16:30:03
     */
    private static Object getMaxKey(Map<Integer, Object> map) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        int maxValue = Integer.MIN_VALUE;
        Set<Integer> keys = map.keySet();
        for (Integer key : keys) {
            if (maxValue < key) {
                maxValue = key;
            }
        }
        return map.get(maxValue);
    }

    public static ByteBuffer getMaxFaceByte(CapFacesSet facesInfo) {
        ByteBuffer feature = null;
        /*
         * 需要调用抓拍服务的接口；获取ft_fea(模板特征)、face_x(模版人脸X坐标)、face_y(模版人脸Y坐标)、
         * face_cx(模版人脸宽度)、face_cy(模版人脸高度)、ft_quality(模版质量)。
         */
        // 判断返回的特征值有几个
        if (facesInfo.getLstFaces().size() == 1) {
            feature = ByteBuffer.wrap(facesInfo.getLstFaces().get(0).getBinFea());

        } else {
            // 特征值大于1个，需要计算获取人脸面积最大的一个特征。
//			List<com.sensing.core.thrift.cap.bean.CapFaceDetectResult> lstFaces = facesInfo.getLstFaces();
            List<CapFaceDetectResult> list = facesInfo.getLstFaces();// 查询出的特征值
            Map<Integer, Object> facesMap = new HashMap<Integer, Object>();// 过滤多个特征值

            for (CapFaceDetectResult CapFaceDetectResult : list) {
                int right = CapFaceDetectResult.getRcFace().getRight();
                int left = CapFaceDetectResult.getRcFace().getLeft();
                int bottom = CapFaceDetectResult.getRcFace().getBottom();
                int top = CapFaceDetectResult.getRcFace().getTop();
                int Integer = (right - left) * (bottom - top);
                facesMap.put(Integer, CapFaceDetectResult);
            }
            // 选择出最大的一个对象。
            CapFaceDetectResult maxCapFaceDetect = (CapFaceDetectResult) getMaxKey(facesMap);
            if (maxCapFaceDetect == null) {
                // 处理失败
                log.error("获取在多个特征值中最大特征值失败！");
                throw new BussinessException("获取在多个特征值中最大特征值失败!");
            }
            feature = ByteBuffer.wrap(maxCapFaceDetect.getBinFea());
        }
        return feature;
    }

    public static ByteBuffer reGetFaceFeature(byte[] imageBytes) throws BussinessException {
        ByteBuffer feature = null;
        try {
            feature = getFaceFeature(imageBytes);
        } catch (BussinessException e) {
            if (e.getErrorCode().equals("抓拍服务没有找到特征信息!")) {
                //放大3倍重新提前特征值
                feature = getFaceFeature(ImgUtil.ShowImgByByte(imageBytes));
            }
        }
        return feature;
    }

    /**
     * @param lstImgs
     * @return List<byte                                                                                                                               [                                                                                                                               ]> 特征值数组
     * @throws Exception
     * @Description: 提取图片特征值 并 上传图片服务器
     * @author liuwei
     * @Date 2018年07月27日
     */
    public static List<Map<String, byte[]>> getCapResult(List<byte[]> imgList)
            throws Exception {
        List<Map<String, byte[]>> list = new ArrayList<Map<String, byte[]>>();
        for (byte[] imgByte : imgList) {
            List<CapFacesSet> detectFaces = new ArrayList<CapFacesSet>(); // 特征列表
            List<ByteBuffer> lstImgs = new ArrayList<ByteBuffer>();// 抓拍所需图片参数
            ByteBuffer byteBuffer = ByteBuffer.wrap(imgByte);
            // 为lstImgs赋值
            if (byteBuffer != null) {
                lstImgs.add(byteBuffer);
            } else {
                log.info(DateUtil.DateToString(new Date()) + ":图片传递有误，请确认！");
                throw new Exception("图片传递有误，请确认！");
            }
            // 开启抓拍
            TTransport transport = new TSocket(RemoteInfoUtil.CAP_SERVER_IP,
                    RemoteInfoUtil.CAP_SERVER_PORT, RemoteInfoUtil.CAP_TIMEOUT);
            com.sensing.core.thrift.cap.service.CaptureServer.Client client = null;
            if (!transport.isOpen()) {
                try {
                    transport.open();
                } catch (TTransportException e) {
                    log.error("抓拍服务连接异常！", e.fillInStackTrace());
                    e.printStackTrace();
                    if (transport.isOpen()) {
                        transport.close();
                    }
                    throw new Exception("抓拍服务连接异常！");
                }
            }
            // 调用抓拍 start
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new com.sensing.core.thrift.cap.service.CaptureServer.Client(
                    protocol);
            try {
                detectFaces = client.DetectFaces(lstImgs);// 调用抓拍,返回特征值列表
                //-------原图抓不到特征，放大三倍后再提取特征--------
                CapFacesSet facesInfo = detectFaces.get(0);
                if (facesInfo == null || facesInfo.getLstFaces().size() <= 0) {
                    ByteBuffer newbyteBuffer = ByteBuffer.wrap(ImgUtil.ShowImgByByte(imgByte));
                    if (newbyteBuffer != null) {
                        lstImgs.clear();
                        lstImgs.add(newbyteBuffer);
                    }
                    detectFaces = client.DetectFaces(lstImgs);// 调用抓拍,返回特征值列表
                }
                //-------二次提取特征 end--------
                if (transport.isOpen()) {
                    transport.close();
                }
            } catch (Exception e) {
                log.error("抓拍信息处理发生异常", e);
                e.getStackTrace();
                throw new Exception("抓拍信息处理发生异常！");
            }
            // -------------调用抓拍 end----------

            if (detectFaces != null && detectFaces.size() > 0) {
                CapFacesSet facesInfo = detectFaces.get(0);
                if (facesInfo != null && facesInfo.getLstFaces().size() > 0) {
                    // 查询出的特征值
                    CapFaceDetectResult capres = facesInfo.getLstFaces().get(0);// 查询出的特征值列表
                    //上传人像图片到服务器 start
                    String seceneURI = UuidUtil.getUuid() + ".jpg";
                    ImageFile seceneImageFile = new ImageFile();
                    String secenePut = HttpDeal.doPut(seceneURI, capres.getBinImg());
                    String imgURL = null;
                    if (StringUtils.isNotEmpty(secenePut)) {
                        seceneImageFile = JSONObject.toJavaObject(JSONObject.parseObject(secenePut), ImageFile.class);
                        imgURL = PropUtils.getString("remote.img.url") + seceneImageFile.getMessage();
                        Map<String, byte[]> map = new HashMap<String, byte[]>();
                        map.put(imgURL, capres.getBinFea());
                        list.add(map);
                    } else {
                        log.error("抓拍获取返回值中人像图片为空！");
                    }
                    //上传人像图片到服务器 end
                } else {
                    log.error("抓拍服务没有找到特征信息!");
                    throw new Exception("抓拍服务没有找到特征信息！");
                }
            } else {
                log.error("抓拍服务没有找到特征信息!");
                throw new Exception("抓拍服务没有找到特征信息！");
            }

        }
        return list;
    }


    public static void main(String[] args) throws Exception {
//
        String featureByUuid = getFeatureByUuid("13e510ac-1f7c-11e9-9f54-1866daf5acac");


        GenerateImage(featureByUuid, "/Users/LXH/Desktop/fea/fea1-1");

        getSimilarByFeatureImg("/Users/LXH/Desktop/fea/fea1-1");

    }

    /**
     * 通过抓拍uuid拿到对比的base64编码
     *
     * @param uuid
     */
    public static String getFeatureByUuid(String uuid) {
        JSONObject params = new JSONObject();
        // 组装请求的参数
        params.put("type", 3);
        params.put("uuid", uuid);
        String behaviorAddr = "http://192.168.1.217:7700/feature/get";
        long startTime = System.currentTimeMillis();
        String res = HttpDeal.sendPost(behaviorAddr, JSONObject.toJSONString(params));
        long endTime = System.currentTimeMillis();
        JSONObject jo = JSONObject.parseObject(res);
        log.info(jo.toString());
        JSONObject map = JSONObject.parseObject(jo.getString("map"));
        String fea = map.getString("fea");
        return fea;
    }

    /**
     * base64字符串转化成图片  对字节数组字符串进行Base64解码并生成图片
     *
     * @param imgStr
     * @param path
     * @return
     */
    public static boolean GenerateImage(String imgStr, String path) {
        if (imgStr == null) //图像数据为空
            return false;
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            String imgFilePath = path;//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 通过特征文件拿到相似的
     *
     * @param featurePath
     */
    public static void getSimilarByFeatureImg(String featurePath) {
        byte[] bs = new byte[0];
        try {
            bs = image2Bytes(featurePath);
        } catch (Exception e) {
            log.error(StringUtils.getStackTrace(e));
        }
        String base64 = byte2Base64StringFun(bs);

        int count = 1;
        long l1 = System.currentTimeMillis();
        for (int i = 0; i < count; i++) {
            JSONObject params = new JSONObject();
            // 组装请求的参数
            params.put("fea1", base64);
            params.put("fea2", "");
            params.put("fea3", "");
            params.put("fea4", "");
            params.put("fea5", "");
            params.put("channel_array", "");
            params.put("threshold", 0.1210000122);
            params.put("cap_time_start", 1548212301);//修改时间范围
            params.put("cap_time_end", 1548248301);//修改时间范围
            params.put("type", 3);
            params.put("retNums", 3);//返回的数量
            String behaviorAddr = "http://192.168.1.217:7700/rck/search";
            String res = HttpDeal.sendPost(behaviorAddr, JSONObject.toJSONString(params));
            JSONObject jo = JSONObject.parseObject(res);
            log.info(jo.toJSONString());
        }
        long l2 = System.currentTimeMillis();
        log.info((l2 - l1) / count);
    }

    public static String byte2Base64StringFun(byte[] b) {
        return Base64.encodeBase64String(b);
    }

    public static byte[] image2Bytes(String imgSrc) throws Exception {

        FileInputStream fin = new FileInputStream(new File(imgSrc));
        // 可能溢出,简单起见就不考虑太多,如果太大就要另外想办法，比如一次传入固定长度byte[]
        byte[] bytes = new byte[fin.available()];
        // 将文件内容写入字节数组，提供测试的case
        fin.read(bytes);
        fin.close();
        return bytes;
    }

}
