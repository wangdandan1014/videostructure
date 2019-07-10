package com.sensing.core.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.sensing.core.bean.SysSubscribe;
import com.sensing.core.utils.Pager;

/**
 * <p>
 * Title: ISysSubscribeDAO
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company: www.sensingtech.com
 * </p>
 *
 * @author haowenfeng
 * @version 2.0
 * @date 2017年8月16日下午3:49:34
 */
public interface ISysSubscribeDAO {

    public abstract int saveSysSubscribe(SysSubscribe paramSysSubscribe);

    public abstract SysSubscribe getSysSubscribe(String paramString);

    public abstract int removeSysSubscribe(String paramString);

    public abstract int removeSysSubscribeByJobId(@Param("jobId") String jobId);

    public abstract int updateSysSubscribe(SysSubscribe paramSysSubscribe);

    public abstract List<SysSubscribe> queryList(Pager paramPager);

    public abstract int selectCount(Pager paramPager);

    public abstract int removeSysSubscribeByUidAndJobId(SysSubscribe paramSysSubscribe);

    public List<SysSubscribe> queryByParam(@Param("uid") String uid, @Param("jobId") String jobId, @Param("subType") Integer subType);

    public int removeSubscribe(@Param("uid") String uid, @Param("jobId") String jobId, @Param("subType") Integer subType);


}
