package com.carozhu.smartfastdevmaster.BottomSheetDialogSimple;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.carozhu.smartfastdevmaster.R;


public class BtmSimleDialog extends StatusbarBottomSheetDialog implements View.OnClickListener {
    private String TAG = BtmSimleDialog.class.getSimpleName();

    Context context;

    EditText editTv;
    ImageView selectedImage;
    ImageView showSelectImg;
    LinearLayout contentParent;
    TextView decline;
    TextView send;



    public BtmSimleDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_push;
    }

    @Override
    protected void initView() {
        editTv = findViewById(R.id.editTv);
        selectedImage = findViewById(R.id.add_picture);
        showSelectImg = findViewById(R.id.showSelectImg);
        contentParent = findViewById(R.id.content_parent);
        decline = findViewById(R.id.decline);
        send = findViewById(R.id.send);

        selectedImage.setOnClickListener(this);
        showSelectImg.setOnClickListener(this);
        decline.setOnClickListener(this);
        send.setOnClickListener(this);

        editTv.setFocusable(true);
        editTv.requestFocus();
        editTv.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEND || (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {
                //do something;
                return true;
            }

            return false;
        });

    }


    @Override
    public void onClick(View v) {

    }
}
