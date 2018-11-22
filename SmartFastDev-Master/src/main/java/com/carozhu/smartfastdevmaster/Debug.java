package com.carozhu.smartfastdevmaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sunfusheng.progress.GlideApp;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Debug {
    private String type;
    private B a;
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class B {
        private String c;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public B getA() {
        return a;
    }

    public void setA(B a) {
        this.a = a;
    }
}
