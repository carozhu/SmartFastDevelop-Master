package com.carozhu.smartfastdevmaster.BottomSheetDialogSimple;

import android.content.Context;
import android.support.annotation.NonNull;

import com.carozhu.smartfastdevmaster.R;

public class MyStrongBottomSheetDialog extends StrongBottomSheetDialog {


    public MyStrongBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_push;
    }

    @Override
    protected void initView() {

    }


}
