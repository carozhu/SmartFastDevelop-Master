package com.carozhu.smartfastdevmaster;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.sunfusheng.progress.GlideApp;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ConfigBeanDebug {
    /**
     * Header : {"Result":101,"Msg":"参数不全"}
     * Content : {"data":""}
     */
    private HeaderBean Header;

    public ContentBean getContent() {
        return Content;
    }

    public void setContent(ContentBean content) {
        Content = content;
    }

    private ContentBean Content;

    public HeaderBean getHeader() {
        return Header;
    }

    public void setHeader(HeaderBean Header) {
        this.Header = Header;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class HeaderBean {
        /**
         * Result : 101
         * Msg : 参数不全
         */

        private int Result;
        private String Msg;

        public int getResult() {
            return Result;
        }

        public void setResult(int Result) {
            this.Result = Result;
        }

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String Msg) {
            this.Msg = Msg;
        }
    }
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ContentBean {
        /**
         * data :
         */

        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }
    }
}
