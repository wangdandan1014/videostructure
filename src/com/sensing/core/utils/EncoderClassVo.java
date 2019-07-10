package com.sensing.core.utils;

import java.io.IOException;
import java.util.Map;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import org.codehaus.jackson.map.ObjectMapper;

public class EncoderClassVo implements Encoder.Text<Map<String, Object>>{

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {
    }
//我向web端传递的是Map类型的
    @Override
    public String encode(Map<String, Object> map) throws EncodeException {
        ObjectMapper    mapMapper= new ObjectMapper();
        try {
            String json="";
            json=mapMapper.writeValueAsString(map);
            return  json;
        } catch (IOException e) {
            e.printStackTrace();
            return "false";
        }
    }


}

