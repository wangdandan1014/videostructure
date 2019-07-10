//package com.ming;
//
//import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
//import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.common.xcontent.XContentFactory;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.transport.client.PreBuiltTransportClient;
//
//import java.io.IOException;
//import java.net.InetAddress;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.UUID;
//
//public class ElasticsearchTest1 {
//
//    public final static String HOST = "192.168.3.240";
//    
//    public final static int PORT = 9300;//http请求的端口是9200，客户端是9300
//
//    public static TransportClient client = null;
//
//    public final static String DATA_INDEX = "test";
//
//    public final static String DATA_TYPE = "test";
//
//    /**
//     * 创建连接
//     * @throws Exception
//     */
//    public static void  getConnect() throws Exception {
//        //创建客户端
//        client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddresses(
//                                 new InetSocketTransportAddress(InetAddress.getByName(HOST),PORT));
//
//        System.out.println("链接已创建...");
//    }
//
//    /**
//     *  关闭连接
//     */
//    public static void closeConnect(){
//        if (null != client) {
//            client.close();
//            System.out.println("链接已关闭...");
//        }
//    }
//
//    /**
//     * 索引映射关系
//     * @return
//     * @throws IOException
//     */
//    public static XContentBuilder getIndexSource() throws IOException {
//        XContentBuilder source = XContentFactory.jsonBuilder().startObject().startObject(DATA_TYPE)
//                .startObject("properties")
//                .startObject("uuid").field("type", "keyword").field("store", false).field("index", true).endObject()
//                .startObject("channelUuid").field("type", "keyword").field("store", false).field("index", true).endObject()
//                .startObject("capTime").field("type", "integer").field("store", true).field("index", true).endObject()
//                .startObject("frameTime").field("type", "integer").field("store", true).field("index", true).endObject()
//                .startObject("age").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("sex").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("carryThingsBag").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("carryThingsPortable").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("orientation").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("moveState").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("hat").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("mask").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("glass").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("upperClothesColor").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("upperClothesType").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("upperClothesTexture").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("lowerClothesColor").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("lowerClothesType").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("lowerClothesTexture").field("type", "short").field("store", true).field("index", true).endObject()
//                .startObject("capLocation").field("type", "keyword").field("store", true).field("index", true).endObject()
//                .startObject("capUrl").field("type", "keyword").field("store", true).field("index", true).endObject()
//                .startObject("seceneUrl").field("type", "keyword").field("store", true).field("index", true).endObject()
//                .endObject().endObject().endObject();
//        return source;
//    }
//
//
//
//    /**
//     * 创建索引
//     */
//    public static void createIndex() {
//        try {
//            CreateIndexRequestBuilder builder = client.admin().indices().prepareCreate(DATA_INDEX);
//            builder.addMapping(DATA_TYPE, getIndexSource());
//
//            CreateIndexResponse res = builder.get();
//            System.out.println(res.isAcknowledged() ? "索引创建成功！" : "索引创建失败！");
//        } catch (Exception e) {
//            System.out.println("创建索引失败！"+e);
//        }
//    }
//
//    /**
//     * 创建索引库
//     * @Title: addIndex1
//     * @author sunt
//     * @date 2017年11月23日
//     * @return void
//     * 需求:创建一个索引库为：msg消息队列,类型为：tweet,id为1
//     * 索引库的名称必须为小写
//     * @throws IOException
//     */
//    public static void addIndex1() throws IOException {
//
//        IndexResponse response = client.prepareIndex(DATA_INDEX, DATA_TYPE).setSource(XContentFactory.jsonBuilder()
//                .startObject().field("userName", "张三")
//                .field("sendDate", new Date())
//                .field("msg", "你好李四")
//                .endObject()).get();
//
//        System.out.println("索引名称:" + response.getIndex() + "\n类型:" + response.getType()
//                + "\n文档ID:" + response.getId() + "\n当前实例状态:" + response.status());
//
//    }
//
//    public static void addData()throws  Exception{
//
//        String channelUuid = getUuid();
//        int  dataCount = 10000;
//
//        long l1 = System.currentTimeMillis();
//        for ( int i = 0 ; i < dataCount ; i ++ ){
//
//            String dataUuid = getUuid();
//
//            Map<String, Object> data = new HashMap<String, Object>();
//            data.put("uuid", dataUuid);
//            data.put("channelUuid",channelUuid);
//            data.put("capTime",System.currentTimeMillis()/1000);
//            data.put("frameTime",1000);
//            data.put("age",1);
//            data.put("sex",2);
//            data.put("carryThingsBag",0);
//            data.put("carryThingsPortable",1);
//            data.put("orientation",2);
//            data.put("moveState",0);
//            data.put("hat",1);
//            data.put("mask",2);
//            data.put("glass",0);
//            data.put("upperClothesColor",1);
//            data.put("upperClothesType",2);
//            data.put("upperClothesTexture",0);
//            data.put("lowerClothesColor",1);
//            data.put("lowerClothesType",2);
//            data.put("lowerClothesTexture",0);
//            data.put("capLocation","210,11,280,303");
//            data.put("capUrl","group1/M00/00/00/wKgB2VvRlUqISBWFAACkgrXp4Q4AAAAAQEqf6wAAKSa908.jpg");
//            data.put("seceneUrl","group1/M00/00/00/wKgB2VvRlUqIHWsgAARgUFanl2UAAAAAQEmH0QABGBo683.jpg");
//
//
//            client.prepareIndex(DATA_INDEX,DATA_TYPE).setId(dataUuid).setSource(data).get();
//
//            if ( i % 100 == 0 && i != 0 ){
//                long l3 = System.currentTimeMillis();
//                double s = i/((l3-l1)/1000*1.0);
//                System.out.println("i值为"+i+"，平均每条耗时"+s);
//            }
//
//        }
//        long l2 = System.currentTimeMillis();
//        double s = dataCount/((l2-l1)/1000*1.0);
//        System.out.println("共耗时"+(l2-l1)+"，平均每条耗时"+s);
//
//    }
//
//    public static void getData1() {
//        GetResponse getResponse = client.prepareGet("msg", "tweet", "1").get();
//        System.out.println("索引库的数据:" + getResponse.getSourceAsString());
//    }
//
//
//    /**
//     * 单个精确值查找(termQuery)
//     */
//    public void termQuery() {
//        QueryBuilder queryBuilder = QueryBuilders.termQuery("code", "01");
//        queryBuilder = QueryBuilders.termQuery("isDelete", true);
//        queryBuilder = QueryBuilders.termQuery("my_title", "我的标题12323abcd");
//        //searchFunction(queryBuilder);
//    }
//
//    /**
//     * 获取uuid
//     * @return
//     */
//    public static String getUuid(){
//        return UUID.randomUUID().toString().replaceAll("-","");
//    }
//
//    public  static  void main(String[] args)throws  Exception{
//        getConnect();
//        //addIndex1();
//        //getData1();
//        //createIndex();
//        addData();
//        closeConnect();
//    }
//
//}