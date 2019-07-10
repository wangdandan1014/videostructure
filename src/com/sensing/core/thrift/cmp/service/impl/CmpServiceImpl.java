package com.sensing.core.thrift.cmp.service.impl;

import java.nio.ByteBuffer;
import java.util.List;

import org.apache.thrift.TException;

import com.sensing.core.thrift.cmp.bean.Cmp1vnRetInfo;
import com.sensing.core.thrift.cmp.bean.CmpRetInfo;
import com.sensing.core.thrift.cmp.bean.DBInfoReturn;
import com.sensing.core.thrift.cmp.bean.FeatureInfo;
import com.sensing.core.thrift.cmp.service.CmpService.Iface;
import com.sensing.core.thrift.cmp.bean.FaceTemplateDB;
import com.sensing.core.thrift.cmp.bean.Result;

/**
 * 
 * <p>Title: CmpServiceImpl</p>
 * <p>Description: 1:1比对接口
 *   删除比对服务器的指定特征库
 *   列出比对服务器的特征库信息
 *   查询比对服务器的指定的特征库信息</p>
 * <p>Company: www.sensingtech.com</p> 
 * @author	haowenfeng
 * @date	2017年6月15日上午9:54:57
 * @version 1.2
 */
public class CmpServiceImpl implements Iface {
	
	public CmpServiceImpl() {}

	
	public List<CmpRetInfo> CompareFeature(List<FeatureInfo> fi, List<Integer> DBID, double threshold,
			int nMaxReturnCount) throws TException {
		
		return null;
	}

	
	public Cmp1vnRetInfo Compare1vnFeature(ByteBuffer feature, int nDB_ID, double threshold, int nMaxReturnCount)
			throws TException {
		
		return null;
	}

	
	public int ModifyTemplate(int DBID, String feaID, ByteBuffer feature) throws TException {
		
		return 0;
	}

	
	public int DeleteTemplate(int DBID, String feaID) throws TException {
		
		return 0;
	}

	
	public int AddTemplate(int DBID, String feaID, ByteBuffer feature) throws TException {
		
		return 0;
	}

	
	public Result AddTDB(FaceTemplateDB ftdb) throws TException {
		
		return null;
	}

	
	public int DeleteDB(int DBID) throws TException {
		
		return 0;
	}

	
	public double CompareFea(ByteBuffer feature1, ByteBuffer feature2) throws TException {
		
		return 0;
	}

	
	public DBInfoReturn ListDBIDs() throws TException {
		
		return null;
	}

	
	public int ReloadTempDB(List<Integer> DBIDs) throws TException {
		
		return 0;
	}

	public int QueryReloadStatus(int nQueryID) throws TException {
		return 0;
	}

	
}
