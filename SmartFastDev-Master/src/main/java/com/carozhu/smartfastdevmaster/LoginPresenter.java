package com.carozhu.smartfastdevmaster;

import com.carozhu.fastdev.mvp.BasePresenter;

public class LoginPresenter extends BasePresenter<UserContract.Model,UserContract.View> implements UserContract.Model {

    public LoginPresenter(UserContract.Model model, UserContract.View contractView){
        super(model,contractView);
    }

    public LoginPresenter(UserContract.View contractView){
        super(contractView);
    }


    @Override
    public void start() {

    }

    @Override
    public void login() {
        checkViewAttach();
        mContractView.showSuccess();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }



}
