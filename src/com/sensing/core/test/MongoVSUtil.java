package com.sensing.core.test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.IndexOptions;
import com.sensing.core.utils.UuidUtil;

public enum MongoVSUtil {
	/**
	 * 定义一个枚举的元素，它代表此类的一个实例
	 */
	instance;

	private static MongoClient mongoClient;

	static {
		System.out.println("===============MongoDBUtil初始化========================");
		String ip = "192.168.1.80";
		int port = 27017;
		instance.mongoClient = new MongoClient(ip, port);
		// 大部分用户使用mongodb都在安全内网下，但如果将mongodb设为安全验证模式，就需要在客户端提供用户名和密码：
		// boolean auth = db.authenticate(myUserName, myPassword);
		Builder options = new MongoClientOptions.Builder();
		options.cursorFinalizerEnabled(true);
		// options.autoConnectRetry(true);// 自动重连true
		// options.maxAutoConnectRetryTime(10); // the maximum auto connect retry time
		options.connectionsPerHost(400);// 连接池设置为300个连接,默认为100
		options.connectTimeout(30000);// 连接超时，推荐>3000毫秒
		options.maxWaitTime(5000); //
		options.socketTimeout(0);// 套接字超时时间，0无限制
		options.threadsAllowedToBlockForConnectionMultiplier(5000);// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get
																	// db”错误。
		options.writeConcern(WriteConcern.SAFE);//
		options.build();
	}

	// ------------------------------------共用方法---------------------------------------------------
	/**
	 * 获取DB实例 - 指定DB
	 * 
	 * @param dbName
	 * @return
	 */
	public MongoDatabase getDB(String dbName) {
		if (dbName != null && !"".equals(dbName)) {
			MongoDatabase database = mongoClient.getDatabase(dbName);
			return database;
		}
		return null;
	}

	/**
	 * 获取collection对象 - 指定Collection
	 * 
	 * @param collName
	 * @return
	 */
	public MongoCollection<Document> getCollection(String dbName, String collName) {
		if (null == collName || "".equals(collName)) {
			return null;
		}
		if (null == dbName || "".equals(dbName)) {
			return null;
		}
		MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collName);
		return collection;
	}

	/**
	 * 关闭Mongodb
	 */
	public void close() {
		if (mongoClient != null) {
			mongoClient.close();
			mongoClient = null;
		}
	}
	
	public static void saveDataByMutiThread(){
		String dbName = "testdb";
		String collName = "test_tab";
				
		MongoCollection<Document> people = MongoVSUtil.instance.getCollection(dbName, collName);
				
		people.createIndex(new Document("uuid",1), new  IndexOptions().unique(true));
		people.createIndex(new Document("channelUuid",1));
		people.createIndex(new Document("capTime",-1));
		people.createIndex(new Document("channelUuid",1).append("capTime",-1));
		people.createIndex(new Document("frameTime",-1));
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
		
		long startTime = System.currentTimeMillis();
		
		int totalCount = 25000000;
		
		final String[] channelIds = new String[50];
		for (int i = 0; i < channelIds.length; i++) {
			channelIds[i] = UuidUtil.getUuid();
		}
		
		List<Document> list = new ArrayList<Document>(); 
		for ( int i = 0 ; i < totalCount ; i++) {
			Document document = new Document();
			document.put("uuid",UuidUtil.getUuid());
			document.put("channelUuid",channelIds[getRandom(50)]);
			document.put("capTime",getTime());
			document.put("frameTime",(getRandom(1000)));
			document.put("age",(getRandom(5)));
			document.put("sex",getRandom(3));
			document.put("carryThingsBag",new int[]{0,1,4}[getRandom(3)]);
			document.put("carryThingsPortable",new int[]{0,2,3,6}[(getRandom(4))]);
			document.put("orientation",getRandom(5));
			document.put("moveState",getRandom(3));
			document.put("hat",getRandom(3));
			document.put("mask",getRandom(3));
			document.put("glass",getRandom(3));
			document.put("upperClothesColor",getRandom(15));
			document.put("upperClothesType",getRandom(15));
			document.put("upperClothesTexture",getRandom(5));
			document.put("lowerClothesColor",getRandom(15));
			document.put("lowerClothesType",getRandom(5));
			document.put("lowerClothesTexture",getRandom(5));
			document.put("capLocation","944,490,62,76");
			document.put("capUrl","group1/M00/00/00/wKgB2VuD6CyIIaDNAABlwx5YxbcAAAAAQADR5YAAGXb907.jpg");
			document.put("seceneUrl","group1/M00/00/00/wKgB2VuD6FmIbdM2AAHe4L6p9Y0AAAAAQADrXEAAd74566.jpg");
			list.add(document);
			if ( list.size() == 20  ) {
				people.insertMany(list);
				list.clear();
			}
			if ( ( i % 10000 == 0) ) {
				System.out.println();
				long endTime = System.currentTimeMillis();
				System.out.println("存储速率:"+(i/((endTime-startTime)*1.0/1000.0))+"条/秒;已存储:"+i+"条;当前已耗时:"+((endTime-startTime)/1000)+"s");
			}
		}
	}

	static Random random = new Random();
	public static int getRandom(int i){
		return random.nextInt(i);
	}
	
	public static Long getTime(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			Date startDate = sdf.parse("20190501000000");
			Date endDate = sdf.parse("20191101000000");
			
			long min = startDate.getTime()/1000;
			long max = endDate.getTime()/1000;
			long rangeLong = min + (((long) (new Random().nextDouble() * (max - min))));
			
			return rangeLong;
		} catch (Exception e) {
		}
		return 0L;
	}
	
	
	/**
	 * 测试入口
	 * 
	 * @param args
	 * @throws CloneNotSupportedException
	 */
	public static void main(String[] args) {
		saveDataByMutiThread();
		
	}
}