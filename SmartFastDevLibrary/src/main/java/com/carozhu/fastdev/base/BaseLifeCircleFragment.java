package com.carozhu.fastdev.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.carozhu.fastdev.lifecycle.FragmentLifecycleable;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.Subject;

public class BaseLifeCircleFragment extends RxFragment implements FragmentLifecycleable {
    private  final String TAG = BaseLifeCircleFragment.class.getSimpleName();
    private final BehaviorSubject<FragmentEvent> mLifecycleSubject = BehaviorSubject.create();
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Log.i(TAG,getClass().getSimpleName() + "  onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //Log.i(TAG,getClass().getSimpleName() + "  setUserVisibleHint " + isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        //Log.i(TAG,getClass().getSimpleName() + "  onHiddenChanged " + hidden);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Log.i(TAG,getClass().getSimpleName() + "  onActivityCreated ");

    }

    @Override
    public void onStart() {
        super.onStart();
    }


    @Override
    public void onResume() {
        super.onResume();
        //Log.i(TAG,getClass().getSimpleName() + " onResume  ");


    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.i(TAG,getClass().getSimpleName() + "  onPause ");


    }

    @Override
    public void onStop() {
        super.onStop();
        //Log.i(TAG,getClass().getSimpleName() + "  onStop ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //Log.i(TAG,getClass().getSimpleName() + "  onDestroyView ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.i(TAG,getClass().getSimpleName() + "  onDestroy ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //Log.i(TAG,getClass().getSimpleName() + "  onDetach ");
    }


    @NonNull
    @Override
    public Subject<FragmentEvent> provideLifecycleSubject() {
        return mLifecycleSubject;
    }
}
