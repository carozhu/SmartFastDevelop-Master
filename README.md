# Smart Fast Development Android Classic Application 
## surport

## RxHttp

## base

## butterknife
```text
在Library中引入butterknife(@see BaseActivity and LazyLoadBaseFragment)时，需要额外在你的Module中添加:
annotationProcessor 'com.jakewharton:butterknife-compiler:8.8.1'
否则在Module中 @BindView(IdRes)时,使用会报错NullPointerException

原因：
    这个不是 bug ，因为 butterknife 的工作机制是：利用 annotationProcessor 来生成 XXX_ViewBinding 类的，
    对应的 XXX 里面的 View 的初始化是在 XXX_ViewBinding 内完成的。
    而 annotationProcessor 是以 module 为单位工作的，所以解决方法很简单，
    在 app module 内引用 annotationProcessor 而在 library 内引用 butterknife 即可解决。
@see https://github.com/JakeWharton/butterknife/issues/1127
    
```



## utils

## helper

## Debug

## MVP

## NetWorkStateReceiver
>网络状态改变监听器。请在你的主MainActivity中的onCreate周期中注册服务，在onDestory周期中解除注册。>
```text
注册
NetWorkStateReceiver.getInstance().registerReceiver();
解除
NetWorkStateReceiver.getInstance().unRegisterReceiver();
```

## lifecycle

## cacheThreedPoll 

## priorityThreedPool

## JavaEmail



## 参考&鸣谢
* [QMUI](http://qmuiteam.com/android/)
* [FragmentLiftCycle](https://github.com/ImportEffort/FragmentLiftCycle ) 
* [MVPArms](https://github.com/JessYanCoding/MVPArms) 
* [MVPArt](https://github.com/JessYanCoding/MVPArt) 

## Link
* https://google-developer-training.gitbooks.io/android-developer-advanced-course-concepts/content/unit-1-expand-the-user-experience/lesson-1-fragments/1-2-c-fragment-lifecycle-and-communications/1-2-c-fragment-lifecycle-and-communications.html
* https://juejin.im/entry/57cc61d3128fe1006978e849
* https://www.jianshu.com/p/210eab345423
* https://blog.csdn.net/yan8024/article/details/44172389
* https://www.jianshu.com/p/3da543063b8c
* https://blog.csdn.net/qq_32175491/article/details/77861795
* https://www.jianshu.com/p/0e4a5e70bf0e

```text
  很多工具类都参考来源于网上很多开源项目，并根据自己的实际需求抽掉出来精简至用。特别鸣谢，如有侵权，请告知。
```


### CoordinatorLayout中的RecyclerView快速滑动到边界时,SCROLL_STATE_IDLE事件不能及时获取

```text
问题描述:
当在CooridnatoryLayout中嵌套AppBarLayout + RecyclerView时, 发现一个问题. 当RecyclerView快速滑动到底部, 原来的加载更多功能不能及时触发, 需要等待一定的时间, 比如几秒种, 才会触发加载更多. 检查发现, 是因为RecyclerView 的onScrollStateChanged()回调没有及时调用, 为什么会这样的, 原来是快速滑动时, 触发了一个fling, RecyclerView一直等到这fling结束才调用onScrollStateChanged()回调方法.
实际上这个问题在这里有更深入的讨论:
https://stackoverflow.com/questions/48204549/recyclerview-scroll-state-idle-is-being-called-late
https://issuetracker.google.com/issues/66996774
里面也附带了chrisbanes的解决方法.

我自己通过监听RecyclerView的状态, 也整理了一份解决方法. 放在下面这个地址了:
https://github.com/shaopx/RecyclerView66996774Workaround

作者：vb12
链接：https://www.jianshu.com/p/4212c6580550
來源：简书
简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。
```