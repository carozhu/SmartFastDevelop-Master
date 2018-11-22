package com.carozhu.smartfastdevmaster;

import com.carozhu.fastdev.mvp.IContractView;
import com.carozhu.fastdev.mvp.IModel;

public interface UserContract {
    interface View extends IContractView{
        void showSuccess();
    }
    interface Model extends IModel{
        void login();
    }

}
