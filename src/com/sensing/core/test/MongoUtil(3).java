//package com.ming.publictest.mongodb;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Random;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import org.bson.Document;
//import org.bson.conversions.Bson;
//import org.bson.types.ObjectId;
//
//import com.ming.utils.base.UuidUtil;
//import com.mongodb.BasicDBObject;
//import com.mongodb.MongoClient;
//import com.mongodb.MongoClientOptions;
//import com.mongodb.MongoClientOptions.Builder;
//import com.mongodb.WriteConcern;
//import com.mongodb.client.MongoCollection;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;
//import com.mongodb.client.MongoIterable;
//import com.mongodb.client.model.Filters;
//import com.mongodb.client.model.IndexOptions;
//import com.mongodb.client.result.DeleteResult;
//
//
//public enum MongoUtil {
//     /**
//     * 定义一个枚举的元素，它代表此类的一个实例
//     */
//    instance;
//
//    private static MongoClient mongoClient;
//    private static String ip = "192.168.1.174";
//    private static int port = 27017;
//    
//    private static String dbName = "videostructure";
//    private static String collName = "Person";
//    
//    static {
//        System.out.println("===============MongoDBUtil初始化========================");
//        
//
////        MongoCredential credential = MongoCredential.createCredential("shenxing", "bigdata", "shenxing".toCharArray());
////        MongoClient mongoClient = new MongoClient(new ServerAddress("IP", 端口号), Arrays.asList(credential));
//        
////        instance.mongoClient = new MongoClient(new ServerAddress(ip, port),Arrays.asList(credential));
//        instance.mongoClient = new MongoClient(ip, port);
//        // 大部分用户使用mongodb都在安全内网下，但如果将mongodb设为安全验证模式，就需要在客户端提供用户名和密码：
//        
////        boolean auth = instance.mongoClient.
//        Builder options = new MongoClientOptions.Builder();
//        options.cursorFinalizerEnabled(true);
//        // options.autoConnectRetry(true);// 自动重连true
//        // options.maxAutoConnectRetryTime(10); // the maximum auto connect retry time
//        options.connectionsPerHost(300);// 连接池设置为300个连接,默认为100
//        options.connectTimeout(30000);// 连接超时，推荐>3000毫秒
//        options.maxWaitTime(5000); //
//        options.socketTimeout(0);// 套接字超时时间，0无限制
//        options.threadsAllowedToBlockForConnectionMultiplier(5000);// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
//        options.writeConcern(WriteConcern.SAFE);//
//        
//        options.build();
//    }
//
//    // ------------------------------------共用方法---------------------------------------------------
//    /**
//     * 获取DB实例 - 指定DB
//     * 
//     * @param dbName
//     * @return
//     */
//    public static MongoDatabase getDB(String dbName) {
//        if (dbName != null && !"".equals(dbName)) {
//            MongoDatabase database = mongoClient.getDatabase(dbName);
//            return database;
//        }
//        return null;
//    }
//
//    /**
//     * 获取collection对象 - 指定Collection
//     * 
//     * @param collName
//     * @return
//     */
//    public MongoCollection<Document> getCollection(String dbName, String collName) {
//        if (null == collName || "".equals(collName)) {
//            return null;
//        }
//        if (null == dbName || "".equals(dbName)) {
//            return null;
//        }
//        MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collName);
//        return collection;
//    }
//
//    /**
//     * 查询DB下的所有表名
//     */
//    public List<String> getAllCollections(String dbName) {
//        MongoIterable<String> colls = getDB(dbName).listCollectionNames();
//        List<String> _list = new ArrayList<String>();
//        for (String s : colls) {
//            _list.add(s);
//        }
//        return _list;
//    }
//
//    /**
//     * 获取所有数据库名称列表
//     * 
//     * @return
//     */
//    public MongoIterable<String> getAllDBNames() {
//        MongoIterable<String> s = mongoClient.listDatabaseNames();
//        return s;
//    }
//
//    /**
//     * 删除一个数据库
//     */
//    public void dropDB(String dbName) {
//        getDB(dbName).drop();
//    }
//
//    /**
//     * 查找对象 - 根据主键_id
//     * 
//     * @param collection
//     * @param id
//     * @return
//     */
//    public Document findById(MongoCollection<Document> coll, String id) {
//        ObjectId _idobj = null;
//        try {
//            _idobj = new ObjectId(id);
//        } catch (Exception e) {
//            return null;
//        }
//        Document myDoc = coll.find(Filters.eq("_id", _idobj)).first();
//        return myDoc;
//    }
//
//    /** 统计数 */
//    public int getCount(MongoCollection<Document> coll) {
//        int count = (int) coll.count();
//        return count;
//    }
//
//    /** 条件查询 */
//    public MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
//        return coll.find(filter).iterator();
//    }
//
//    /** 分页查询 */
//    public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
//        Bson orderBy = new BasicDBObject("_id", 1);
//        return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
//    }
//    
//
//    /**
//     * 通过ID删除
//     * 
//     * @param coll
//     * @param id
//     * @return
//     */
//    public int deleteById(MongoCollection<Document> coll, String id) {
//        int count = 0;
//        ObjectId _id = null;
//        try {
//            _id = new ObjectId(id);
//        } catch (Exception e) {
//            return 0;
//        }
//        Bson filter = Filters.eq("_id", _id);
//        DeleteResult deleteResult = coll.deleteOne(filter);
//        count = (int) deleteResult.getDeletedCount();
//        return count;
//    }
//
//    /**
//     * 
//     * 
//     * @param coll
//     * @param id
//     * @param newdoc
//     * @return
//     */
//    public Document updateById(MongoCollection<Document> coll, String id, Document newdoc) {
//        ObjectId _idobj = null;
//        try {
//            _idobj = new ObjectId(id);
//        } catch (Exception e) {
//            return null;
//        }
//        Bson filter = Filters.eq("_id", _idobj);
//        // coll.replaceOne(filter, newdoc); // 完全替代
//        coll.updateOne(filter, new Document("$set", newdoc));
//        return newdoc;
//    }
//
//    public static  void dropCollection(String dbName, String collName) {
//        getDB(dbName).getCollection(collName).drop();
//    }
//
//    /**
//     * 关闭Mongodb
//     */
//    public void close() {
//        if (mongoClient != null) {
//            mongoClient.close();
//            mongoClient = null;
//        }
//    }
//
//    
//    public static void testSave(){
//    	String dbName = "videostructure";
//		String collName = "CapMotor";
//		
//		MongoCollection<Document> people = MongoUtil.instance.getCollection(dbName, collName);
//		
//      
//		people.createIndex(new Document("uuid",1), new IndexOptions().unique(true));
//		people.createIndex(new Document("channelUuid",1));
//		people.createIndex(new Document("capTime",-1));
//		
//		long startTime = System.currentTimeMillis();
//		
//		int totalCount = 1000;
//		
//		for (int i = 0; i < totalCount ; i++) {
//			Document document = new Document();
//			document.put("uuid",UuidUtil.getUuid());
//			document.put("channelUuid","123");
//			document.put("capTime",(startTime/1000+i*3));
//			people.insertOne(document);
//			
//			if ( (i % 100 == 0) ) {
//				System.out.println(i);
//			}
//			
//		}
//		
//		long endTime = System.currentTimeMillis();
//		System.out.println((totalCount/((endTime-startTime)*1.0/1000.0))+"条/秒");
//		System.out.println(((endTime-startTime)/1000)+"s");
//    }
//    
//    
//    public static void save(){
//    	String dbName = "test";
//		String collName = "test";
//		
//		MongoCollection<Document> people = MongoUtil.instance.getCollection(dbName, collName);
//		
//		people.createIndex(new Document("uuid",1), new  IndexOptions().unique(true));
//		people.createIndex(new Document("channelUuid",1));
//		people.createIndex(new Document("capTime",-1));
////		people.createIndex(new Document("channelUuid",1).append("capTime",-1));
//		people.createIndex(new Document("frameTime",-1));
//		people.createIndex(new Document("age",-1));
//		people.createIndex(new Document("sex",-1));
//		people.createIndex(new Document("carryThingsBag",-1));
//		people.createIndex(new Document("carryThingsPortable",-1));
//		people.createIndex(new Document("orientation",-1));
//		people.createIndex(new Document("moveState",-1));
//		people.createIndex(new Document("hat",-1));
//		people.createIndex(new Document("mask",-1));
//		people.createIndex(new Document("glass",-1));
//		people.createIndex(new Document("upperClothesColor",-1));
//		people.createIndex(new Document("upperClothesType",-1));
//		people.createIndex(new Document("upperClothesTexture",-1));
//		people.createIndex(new Document("lowerClothesColor",-1));
//		people.createIndex(new Document("lowerClothesType",-1));
//		people.createIndex(new Document("lowerClothesTexture",-1));
//
//		long l1 = System.currentTimeMillis();
//		
//		int s1001 = 1538323200;
//		int e1001 = 1538409600;
//		
//		
//		int totalCount = 5000000 ;
//		String channelUuid = UuidUtil.getUuid();
//		for (int i = 0; i < totalCount ; i++) {
//			
//			//创建Random类对象
//	        Random random = new Random();              
//	        //产生随机数
//	        int capTime = random.nextInt(e1001 - s1001+ 1) + s1001;
//			
//			
//			Document document = new Document();
//			
//			document.put("uuid", UuidUtil.getUuid());
//            document.put("channelUuid",channelUuid);
//            document.put("capTime",System.currentTimeMillis()/1000);
//            document.put("frameTime",1000);
//            document.put("age",1);
//            document.put("sex",2);
//            document.put("carryThingsBag",0);
//            document.put("carryThingsPortable",1);
//            document.put("orientation",2);
//            document.put("moveState",0);
//            document.put("hat",1);
//            document.put("mask",2);
//            document.put("glass",0);
//            document.put("upperClothesColor",1);
//            document.put("upperClothesType",2);
//            document.put("upperClothesTexture",0);
//            document.put("lowerClothesColor",1);
//            document.put("lowerClothesType",2);
//            document.put("lowerClothesTexture",0);
//            document.put("capLocation","210,11,280,303");
//            document.put("capUrl","group1/M00/00/00/wKgB2VvRlUqISBWFAACkgrXp4Q4AAAAAQEqf6wAAKSa908.jpg");
//            document.put("seceneUrl","group1/M00/00/00/wKgB2VvRlUqIHWsgAARgUFanl2UAAAAAQEmH0QABGBo683.jpg");
//			
//			people.insertOne(document);
//			
//			if ( i % 10000 == 0 && i != 0 ){
//                long l3 = System.currentTimeMillis();
//                double s = i/((l3-l1)/1000*1.0);
//                System.out.println("i值为"+i+"，平均每秒"+s+"条");
//            }
//			
//		}
//		long l2 = System.currentTimeMillis();
//		System.out.println((totalCount/((l2-l1)*1.0/1000.0))+"条/秒");
//		System.out.println(((l2-l1)/1000)+"s");
//    }
//    
//    public static void saveByThread(){
//    	String dbName = "test";
//		String collName = "test";
//		
//		MongoCollection<Document> people = MongoUtil.instance.getCollection(dbName, collName);
//		
//		people.createIndex(new Document("uuid",1), new  IndexOptions().unique(true));
//		people.createIndex(new Document("channelUuid",1));
//		people.createIndex(new Document("capTime",-1));
////		people.createIndex(new Document("channelUuid",1).append("capTime",-1));
//		people.createIndex(new Document("frameTime",-1));
//		people.createIndex(new Document("age",-1));
//		people.createIndex(new Document("sex",-1));
//		people.createIndex(new Document("carryThingsBag",-1));
//		people.createIndex(new Document("carryThingsPortable",-1));
//		people.createIndex(new Document("orientation",-1));
//		people.createIndex(new Document("moveState",-1));
//		people.createIndex(new Document("hat",-1));
//		people.createIndex(new Document("mask",-1));
//		people.createIndex(new Document("glass",-1));
//		people.createIndex(new Document("upperClothesColor",-1));
//		people.createIndex(new Document("upperClothesType",-1));
//		people.createIndex(new Document("upperClothesTexture",-1));
//		people.createIndex(new Document("lowerClothesColor",-1));
//		people.createIndex(new Document("lowerClothesType",-1));
//		people.createIndex(new Document("lowerClothesTexture",-1));
//
//		long l1 = System.currentTimeMillis();
//		
//		int totalCount = 10000000 ;
//		String channelUuid = UuidUtil.getUuid();
//		
//		int threadCount = 25;
//		AtomicInteger ai = new AtomicInteger(0);
//		
//		ExecutorService service =Executors.newFixedThreadPool(threadCount); 
//		for (int i = 0; i < totalCount ; i++) {
//			service.submit(new Runnable() {
//				public void run() {
//					
//					Document document = new Document();
//					
//					document.put("uuid", UuidUtil.getUuid());
//		            document.put("channelUuid",channelUuid);
//		            document.put("capTime",System.currentTimeMillis()/1000);
//		            document.put("frameTime",1000);
//		            document.put("age",1);
//		            document.put("sex",2);
//		            document.put("carryThingsBag",0);
//		            document.put("carryThingsPortable",1);
//		            document.put("orientation",2);
//		            document.put("moveState",0);
//		            document.put("hat",1);
//		            document.put("mask",2);
//		            document.put("glass",0);
//		            document.put("upperClothesColor",1);
//		            document.put("upperClothesType",2);
//		            document.put("upperClothesTexture",0);
//		            document.put("lowerClothesColor",1);
//		            document.put("lowerClothesType",2);
//		            document.put("lowerClothesTexture",0);
//		            document.put("capLocation","210,11,280,303");
//		            document.put("capUrl","group1/M00/00/00/wKgB2VvRlUqISBWFAACkgrXp4Q4AAAAAQEqf6wAAKSa908.jpg");
//		            document.put("seceneUrl","group1/M00/00/00/wKgB2VvRlUqIHWsgAARgUFanl2UAAAAAQEmH0QABGBo683.jpg");
//					
//					people.insertOne(document);
//					
//					int aiai = ai.getAndIncrement();
//					if ( aiai % 10000 == 0 && aiai != 0 ){
//		                long l3 = System.currentTimeMillis();
//		                double s = aiai/((l3-l1)/1000*1.0);
//		                System.out.println("i值为"+aiai+"，平均每秒"+s+"条");
//		            }
//				}
//			});
//		}
//		
//    }
//
//    public static void saveCluster(){
//    	String dbName = "bigdata";
//		String collName = "faceCapture";
//		
//		MongoCollection<Document> people = MongoUtil.instance.getCollection(dbName, collName);
//		
////		people.createIndex(new Document("uuid",1), new  IndexOptions().unique(true));
//
//		long startTime = System.currentTimeMillis();
//		
//		int totalCount = 1000000 ;
//		
//		for (int i = 0; i < totalCount ; i++) {
//			
//	        //产生随机数
//			Document document = new Document();
//			document.put("uuid",UuidUtil.getUuid());
//			document.put("fcap_time",System.currentTimeMillis());
//			document.put("fcap_id",UuidUtil.getUuid());
//			document.put("fcap_dcid","1111111111111111111");
//			people.insertOne(document);
//			
//			if ( (i % 10000 == 0) ) {
//				System.out.println(i);
//			}
//		}
//		long endTime = System.currentTimeMillis();
//		System.out.println((totalCount/((endTime-startTime)*1.0/1000.0))+"条/秒");
//		System.out.println(((endTime-startTime)/1000)+"s");
//    }
//    
//    public static void query(int deviceId){
//		
//		MongoCollection<Document> people = MongoUtil.instance.getCollection(dbName, collName);
//
////		Bson filter = Filters.and(Filters.regex("uuid",Pattern.compile("/*aaa/*", Pattern.CASE_INSENSITIVE)),Filters.eq("carryThingsPortable", 1),Filters.eq("upperClothesColor", 1));
//		Bson filter = Filters.and(Filters.eq("deviceId", deviceId+""));
//		
////		Bson filter = Filters.and(Filters.eq("serialVersionUID", 1));
////		long startTime = System.currentTimeMillis();
//		
//		MongoCursor<Document> cursor = people.find(filter)
////		MongoCursor<Document> cursor = people.find()
////				.sort(new Document("capTime",-1))
////				.skip(0)
////				.limit(10000).iterator();
//				.iterator();
////		FindIterable<Document> limit = people.find(filter)
////				.sort(new Document("capTime",-1))
////				.skip(0)
////				.limit(10);
////		long endTime = System.currentTimeMillis();
////		System.out.println((endTime-startTime)+"ms");
////		MongoCursor<Document> cursor = limit.iterator();
//		
//		int i = 0 ;
//		while( cursor.hasNext() ){
//			i++;
////			Document document = cursor.next();
////			String json = document.toJson();
////			JSONObject jo = JSONObject.parseObject(json);
////			System.out.println("uuid:"+jo.getString("uuid")+";deviceId:"+jo.getString("deviceId")+";"+"capTime:"+uninxToStringDate(jo.getLongValue("capTime")));
//        }
//		System.out.println(deviceId+":"+i);
//    }
//    
//    /**
//     * 测试入口
//     * 
//     * @param args
//     * @throws CloneNotSupportedException 
//     */
//	public static void main(String[] args) {
////		saveByThread();
////		saveCluster();
//		for (int i = 1; i < 2 ; i++) {
//			query(i);
//		}
////		save();
////		dropCollection(dbName, collName);
//    }
//	
//	public static String uninxToStringDate(Long uninxTime) {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		Date date = new Date(uninxTime*1000);
//		return sdf.format(date);
//	}
//	
//}
//
//
//
//
