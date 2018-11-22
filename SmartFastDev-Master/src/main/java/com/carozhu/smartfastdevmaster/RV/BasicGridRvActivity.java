package com.carozhu.smartfastdevmaster.RV;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.carozhu.fastdev.base.BaseActivity;
import com.carozhu.fastdev.helper.StatusBarHelper;
import com.carozhu.fastdev.mvp.BasePresenter;
import com.carozhu.fastdev.mvp.IPresenter;
import com.carozhu.smartfastdevmaster.R;
import com.sunfusheng.progress.GlideApp;

import java.util.ArrayList;
import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * Author: carozhu
 * Date  : On 2018/7/26
 * Desc  :
 */
public class BasicGridRvActivity extends BaseActivity {
    RecyclerView recyclerView;


    @Override
    public int getLayoutId(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_rv_basic_grid;
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        StatusBarHelper.setColorForSwipeBack(activity, ContextCompat.getColor(context,R.color.md_orange_50),0);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
    }

    @Override
    public void render() {
        BasicGridRvAdapter adapter = new BasicGridRvAdapter(loadData());
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2, LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE:
                        //滑动停止
                        try {
                            //GlideApp.with(context).resumeRequests();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                    case RecyclerView.SCROLL_STATE_SETTLING:
                    default:
                        //正在滚动
                        try {
                            //GlideApp.with(context).pauseRequests();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;

                }
            }
        });

    }

    private List<String> loadData() {
        List<String> imageArrayList = new ArrayList<>();
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/5a0d0b3349790.jpg?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/1531972764189.jpg?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/11718571528898755402.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/10887011516900268162.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/5a0d0b3349790.jpg?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/1531972764189.jpg?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/13895781528899672091.png?x-oss-process=style/sty1");

        imageArrayList.add("http://vs-m.zsyj.com.cn/img/12020571529634350512.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/10439411523686142111.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/12962521531310151431.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/12638721528055492461.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/1532328597543.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/15647041532331906181.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/14046821528635916584.png?x-oss-process=style/sty1");


        imageArrayList.add("http://vs-m.zsyj.com.cn/img/15003401531150831132.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/10960761518490475313.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/13747451532417298350.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/14991611530885026461.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/11327121518932424048.png?x-oss-process=style/sty1");

        imageArrayList.add("http://vs-m.zsyj.com.cn/img/12878591526215066618.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/15332731532063750393.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/1517301951526.jpg?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/5a0d343c2c5d9.jpg?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/1532266714446.jpg?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/10887011516900268162.png?x-oss-process=style/sty1");

        imageArrayList.add("http://vs-m.zsyj.com.cn/img/11718571528898755402.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/15173024001833.jpg?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/13667241527902547315.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/14129011528852363722.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/5a226d5802cb7.jpg?x-oss-process=style/sty1");

        imageArrayList.add("http://vs-m.zsyj.com.cn/img/15607551532246866499.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/1527559645906.jpg?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/14017111531748951645.png?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/1517303233966.jpg?x-oss-process=style/sty1");
        imageArrayList.add("http://vs-m.zsyj.com.cn/img/153230905255.jpg?x-oss-process=style/sty1");


        imageArrayList.add("http://e.hiphotos.baidu.com/image/pic/item/a1ec08fa513d2697e542494057fbb2fb4316d81e.jpg");
        imageArrayList.add("http://c.hiphotos.baidu.com/image/pic/item/30adcbef76094b36de8a2fe5a1cc7cd98d109d99.jpg");
        imageArrayList.add("http://h.hiphotos.baidu.com/image/pic/item/7c1ed21b0ef41bd5f2c2a9e953da81cb39db3d1d.jpg");
        imageArrayList.add("http://g.hiphotos.baidu.com/image/pic/item/55e736d12f2eb938d5277fd5d0628535e5dd6f4a.jpg");
        imageArrayList.add("http://e.hiphotos.baidu.com/image/pic/item/4e4a20a4462309f7e41f5cfe760e0cf3d6cad6ee.jpg");
        imageArrayList.add("http://b.hiphotos.baidu.com/image/pic/item/9d82d158ccbf6c81b94575cfb93eb13533fa40a2.jpg");
        imageArrayList.add("http://e.hiphotos.baidu.com/image/pic/item/4bed2e738bd4b31c1badd5a685d6277f9e2ff81e.jpg");
        imageArrayList.add("http://www.huabian.com/uploadfile/2014/1202/20141202025659854.jpg");
        imageArrayList.add("http://www.huabian.com/uploadfile/2014/1202/20141202025700989.jpg");
        imageArrayList.add("http://g.hiphotos.baidu.com/image/pic/item/0d338744ebf81a4c87a3add4d52a6059252da61e.jpg");
        imageArrayList.add("http://a.hiphotos.baidu.com/image/pic/item/f2deb48f8c5494ee5080c8142ff5e0fe99257e19.jpg");
        imageArrayList.add("http://f.hiphotos.baidu.com/image/pic/item/4034970a304e251f503521f5a586c9177e3e53f9.jpg");
        imageArrayList.add("http://b.hiphotos.baidu.com/image/pic/item/279759ee3d6d55fbb3586c0168224f4a20a4dd7e.jpg");
        imageArrayList.add("http://img2.xkhouse.com/bbs/hfhouse/data/attachment/forum/corebbs/2009-11/2009113011534566298.jpg");
        imageArrayList.add("http://a.hiphotos.baidu.com/image/pic/item/e824b899a9014c087eb617650e7b02087af4f464.jpg");
        imageArrayList.add("http://c.hiphotos.baidu.com/image/pic/item/9c16fdfaaf51f3de1e296fa390eef01f3b29795a.jpg");
        imageArrayList.add("http://d.hiphotos.baidu.com/image/pic/item/b58f8c5494eef01f119945cbe2fe9925bc317d2a.jpg");
        imageArrayList.add("http://h.hiphotos.baidu.com/image/pic/item/902397dda144ad340668b847d4a20cf430ad851e.jpg");
        imageArrayList.add("http://b.hiphotos.baidu.com/image/pic/item/359b033b5bb5c9ea5c0e3c23d139b6003bf3b374.jpg");
        imageArrayList.add("http://a.hiphotos.baidu.com/image/pic/item/8d5494eef01f3a292d2472199d25bc315d607c7c.jpg");
        imageArrayList.add("http://b.hiphotos.baidu.com/image/pic/item/e824b899a9014c08878b2c4c0e7b02087af4f4a3.jpg");
        imageArrayList.add("http://g.hiphotos.baidu.com/image/pic/item/6d81800a19d8bc3e770bd00d868ba61ea9d345f2.jpg");


        return imageArrayList;
    }

    @Override
    public void recvRxEvents(Object rxPostEvent) {

    }

    @Override
    public void netReConnected(int connectType, String connectName) {

    }

    @Override
    public void netDisConnected() {

    }
}
