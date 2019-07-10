package com.sensing.core.utils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 查询分页总类
 */
@SuppressWarnings("all")
public class Pager implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private Map<String, String> f = new HashMap<String, String>();
	private String pageFlag = "pageFlag";
	private List resultList;
	private int pageNo = 1;
	private int pageRows = 10;
	private int totalCount = -1;
	private int totalPages;
	
	/**
	 * 添加过滤器,paramKey为request传递的parameter的key,对应页面表单中输入项的name,用于对原有查询进行限定
	 */
	public void addF(String paramKey, String value) {
		if (value != null && !value.equals("")) {
			this.f.put(paramKey, value);
		}
	}

	public int getPageRows() {
		return pageRows;
	}

	public void setPageRows(int pageRows) {
		this.pageRows = pageRows;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * 当前页.
	 */
	public int getPageNo() {
		if (pageNo < 1)
			pageNo = 1;
		return pageNo;
	}
	
	/**
	 * 设置当前页.
	 */
	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;

	}
	

	/**
	 * 首记录.
	 */
	public int getFirst() {
		return ((getPageNo() - 1) * pageRows) + 1;
	}

	/**
	 * 尾记录.
	 */
	public int getLast() {
		return (getFirst() + pageRows) > getTotalCount() ? getTotalCount()
				: (getFirst() + pageRows - 1);
	}

	public int getTotalPages() {
		int count = (totalCount + pageRows - 1) / pageRows;
		return count;
	}

	public void setTotalPages(int totalPages) {
		if (totalPages == 0)
			totalPages = 1;
		this.totalPages = totalPages;
	}

	/**
	 * 总记录数
	 */
	public void resetTotalCount(int totalCount) {
		if (totalCount < 0) {
			totalCount = 0;
		}
		int count = (totalCount + pageRows - 1) / pageRows;
		setTotalCount(totalCount);
		setTotalPages(count);
		if (getPageNo() > getTotalPages()) {
			setPageNo(getTotalPages());
		}

	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return (pageNo + 1 <= getTotalPages());
	}

	/**
	 * 下一页页号.
	 */
	public int getNextPage() {
		if (isHasNext()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return (pageNo - 1 >= 1);
	}

	/**
	 * 上一页页号.
	 */
	public int getPrePage() {
		if (isHasPre()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	/**
	 * 供后台查询调用 判断最后一页数据大小
	 */
	public int getBeginCount() {
		int beginCount = (getPageNo() - 1) * pageRows;
//		if (beginCount >= this.getTotalCount()) {
//			int modpageSize = this.getTotalCount() % this.pageRows;
//			if (modpageSize == 0) {
//				modpageSize = this.pageRows;
//			}
//			beginCount = this.getTotalCount() + 1 - modpageSize;
//		}
		return beginCount < 0 ? 0 : beginCount;
	}

	public Map<String, String> getF() {
		return f;
	}

	public void setF(Map<String, String> f) {
		this.f = f;
	}

	public List getResultList() {
		return resultList;
	}

	public void setResultList(List resultList) {
		this.resultList = resultList;
	}

	public String getPageFlag() {
		return pageFlag;
	}

	public void setPageFlag(String pageFlag) {
		this.pageFlag = pageFlag;
	}
	
	public void setPageFlagNull(String pageFlag) {
		this.pageFlag = pageFlag;
	}
	
}