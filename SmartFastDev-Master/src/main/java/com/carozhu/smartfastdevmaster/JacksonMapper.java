package com.carozhu.smartfastdevmaster;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Created by Administrator on 2017/9/26 0026.
 */

public class JacksonMapper {
    private static final ObjectMapper mapper = new ObjectMapper();

    private JacksonMapper() {
    }

    public static ObjectMapper getInstance() {
        return mapper;
    }
}
