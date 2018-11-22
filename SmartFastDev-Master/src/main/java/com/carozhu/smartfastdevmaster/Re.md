
public class LoginActivity extends BaseActivity<LoginPresenter, UserContract.View> implements UserContract.View {
    @BindView(R.id.button)
    Button button;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    int mStatusBarColor = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public LoginPresenter initPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public void initView() {
        surportToolbar(this, R.id.toolbar, true);
        setToolbarTitle("HELLO", true);
        mStatusBarColor = getResources().getColor(R.color.colorAccent);
    }

    @Override
    protected void render() {
        NetWorkStateReceiver.getInstance().registerReceiver();
        mPresenter.login();
        String UUID = new DeviceUUIDFactory(context).getDeviceUuid();
        Log.i("LOGIN", "UUID ------ " + UUID);
        StatusBarHelper.setStatusBarDarkMode(this);

        //背景作为root background时，设置setTranslucent就可以达到效果
        StatusBarHelper.setTranslucent(this, 0);
        swipeBack(true);
        mStatusBarColor = ContextCompat.getColor(context, android.R.color.transparent);
        toolbar.setBackgroundColor(mStatusBarColor);

        Map maps = new HashMap();
        maps.put("pid", "1");
        maps.put("sid", "1");
        maps.put("version", String.valueOf(AppInfoUtil.getVerName(context)));
        maps.put("vercode", String.valueOf(AppInfoUtil.getVerCode(context)));
        maps.put("sver", "4.5");
        maps.put("noncestr", RandomHelper.getRandomStr(12));
        maps.put("timestamp", String.valueOf(DateUtil.getCurrentTimeMillis() / 1000));
        maps.put("uuid", "1");
        maps.put("key", "1");

        HabitAPIService habitAPIService = ApiHelper.getInstance().getAPIService(HabitAPIService.class);
        Disposable disposable =  habitAPIService.getConfig((Map<String, String>) maps)
                .compose(RxSchedulersHelper.io_main())
                .compose(bindToLifecycle())
                .subscribe(configBean -> {

                    weakHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            button.setText("请求数据成功");
                            Toast.makeText(activity, "请求数据成功", Toast.LENGTH_LONG).show();
                        }
                    }, 1000);
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        throwable.printStackTrace();
                        Toast.makeText(activity, "@@@@@ when parse server data. occur erro !", Toast.LENGTH_LONG).show();
                    }
                });

        weakHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                activity.finish();
            }
        }, 1);
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetWorkStateReceiver.getInstance().unRegisterReceiver();
    }

    @Override
    protected void recvRxEvents(Object rxPostEvent) {

    }

    @OnClick({R.id.button})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
                startActivity(context, BasicGridRvActivity.class);
                break;
        }
    }


    @Override
    protected void netReConnected(int connectType, String connectName) {
        Toast.makeText(context, "------netReConnected", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void netDisConnected() {
        Toast.makeText(context, "-----netDisConnected", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSuccess() {
        Toast.makeText(context, "LOGIN showSuccess !!! ", Toast.LENGTH_LONG).show();
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void dismissLoading() {

    }


}