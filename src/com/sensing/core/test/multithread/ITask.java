package com.sensing.core.test.multithread;

import java.util.Map;

public interface ITask<T, E> {
	/**
	 * 
	 * 任务执行方法接口<BR>
	 * 方法名：execute<BR>
	 * 创建人：wangbeidou <BR>
	 * 时间：2018年8月4日-下午6:13:44 <BR>
	 * 
	 * @param e      传入对象
	 * @param params 其他辅助参数
	 * @return T<BR>
	 *         返回值类型
	 * @exception <BR>
	 * @since 2.0
	 */
	T execute(E e, Map<String, Object> params);
}
