package com.carozhu.smartfastdevmaster;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class JSONUtil {

    public static String getJsonString(Object object) throws Exception {
        return JacksonMapper.getInstance().writeValueAsString(object);
    }

    public static Object toObject(String jsonString, Class cls) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(jsonString, cls);
    }

    /**
     * 将Json字符串解析成对应的ArrayList<T>对象
     *
     * @param jsonStr        需要解析的Json字符串
     * @param mTypeReference 需要解析成的Java对象类型引用
     * @return 解析后的ArrayList<T>集合
     */
    public static <T> ArrayList<T> JacksonToListObjectOrString(String jsonStr, com.fasterxml.jackson.core.type.TypeReference<T> mTypeReference) {
        ObjectMapper mObjectMapper = new ObjectMapper();
        ArrayList<T> mList = null;
        try {
            mList = mObjectMapper.readValue(jsonStr, mTypeReference);
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mList;
    }
//
//    /**
//     * 获取需要转换的List<T>类型
//     *
//     * @param mClass
//     * @return
//     */
//    public static <T> com.fasterxml.jackson.core.type.TypeReference<T> getListTypeReference(Class<T> mClass) {
//        com.fasterxml.jackson.core.type.TypeReference mTypeReference = null;
//        if (mClass == VideoListDataBean.ContentBean.class) {
//            mTypeReference = new com.fasterxml.jackson.core.type.TypeReference<List<VideoListDataBean.ContentBean>>() {
//            };
//        } else if (mClass == String.class) {
//            mTypeReference = new com.fasterxml.jackson.core.type.TypeReference<List<String>>() {
//            };
//        }
//        return mTypeReference;
//    }
}
