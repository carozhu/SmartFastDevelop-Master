package com.carozhu.fastdev.receiver;

public class NetChangeObser {
    public boolean connect;
    public int connectType;
    public String connectTypeName;

    public NetChangeObser(boolean connect,int connectType,String connectTypeName){
        this.connect=connect;
        this.connectType=connectType;
        this.connectTypeName=connectTypeName;
    }

}
