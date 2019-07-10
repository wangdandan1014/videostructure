package com.sensing.core.utils.filter;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class MultiReadHttpServletRequest extends HttpServletRequestWrapper {
    private ByteArrayOutputStream cachedBytes;
    private Map<String,String[]> mParams=null;
    public MultiReadHttpServletRequest(HttpServletRequest request) {
        super(request);
    }
    public void rebuildParams(){
        mParams=new HashMap<String,String[]>();
        String lvTmp=(String) getAttribute("_request_body");
        if (lvTmp!=null){
            String[] lvItems=lvTmp.split("&");
            for(String item:lvItems){
                String[] lvItems1=item.split("=", 2);
                try {
                    if (lvItems1.length==2){
                        lvItems1[1]=URLDecoder.decode(lvItems1[1], "utf8");
                    }
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                if (mParams.containsKey(lvItems1[0])){
                    String[] lvV=mParams.get(lvItems1[0]);
                    List<String> lvList=new ArrayList<String>(Arrays.asList(lvV));
                    if (lvItems1.length==2){
                        lvList.add(lvItems1[1]);
                    }
                    else{
                        lvList.add(null);
                    }
                    mParams.put(lvItems1[0], lvList.toArray(new String[lvList.size()]));
                }
                else{
                    String[] lvV=new String[]{lvItems1.length==2? lvItems1[1]:null};
                    mParams.put(lvItems1[0], lvV);
                }
            }
        }
        Map<String,String[]> lvParams=super.getParameterMap();
        for(java.util.Map.Entry<String, String[]> item:lvParams.entrySet()){
            mParams.put(item.getKey(),item.getValue());
        }

    }
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (cachedBytes == null){
            cacheInputStream();
        }

        return new CachedServletInputStream();
    }
    public String getParameter(String pvKey){
		  /*if (super.getParameterMap().containsKey(pvKey)){
			  return super.getParameter(pvKey);
		  }
		  String[] lvRet=mParams.get(pvKey);
		  if (lvRet==null)return null;
		  return lvRet[0];*/
        Object lvV = mParams.get(pvKey);
        if (lvV == null) {
            return null;
        } else if (lvV instanceof String[]) {
            String[] lvStrArr = (String[]) lvV;
            if (lvStrArr.length > 0) {
                return lvStrArr[0];
            } else {
                return null;
            }
        } else if (lvV instanceof String) {
            return (String) lvV;
        } else {
            return lvV.toString();
        }
    }
    public Map<String, String[]> getParameterMap(){
		  /*Map<String, String[]>  lvRet=super.getParameterMap();
		  lvRet.putAll(mParams);
		  return lvRet;*/
        return mParams;
    }
    public Enumeration getParameterNames() {
        Vector l = new Vector(mParams.keySet());
        return l.elements();
    }

    public String[] getParameterValues(String name) {
        Object v = mParams.get(name);
        if (v == null) {
            return null;
        } else if (v instanceof String[]) {
            return (String[]) v;
        } else if (v instanceof String) {
            return new String[] { (String) v };
        } else {
            return new String[] { v.toString() };
        }
    }

    @Override
    public BufferedReader getReader() throws IOException{
        return new BufferedReader(new InputStreamReader(getInputStream()));
    }

    private void cacheInputStream() throws IOException {
        /* Cache the inputstream in order to read it multiple times. For
         * convenience, I use apache.commons IOUtils
         */
        cachedBytes = new ByteArrayOutputStream();
        IOUtils.copy(super.getInputStream(), cachedBytes);
    }

    /* An inputstream which reads the cached request body */
    public class CachedServletInputStream extends ServletInputStream {
        private ByteArrayInputStream input;
        private boolean mIsFinished=false;
        public CachedServletInputStream() {
            /* create a new input stream from the cached request body */
            input = new ByteArrayInputStream(cachedBytes.toByteArray());
            mIsFinished=false;
        }

        @Override
        public int read() throws IOException {
            int lvReaded=input.read();
            mIsFinished=lvReaded==-1;
            return lvReaded;
        }

        @Override
        public boolean isFinished() {
            return mIsFinished;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener arg0) {
        }
    }
}
