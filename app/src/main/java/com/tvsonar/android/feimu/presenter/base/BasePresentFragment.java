package com.tvsonar.android.feimu.presenter.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;
import com.tvsonar.android.feimu.common.Mlog;
import com.tvsonar.android.feimu.event.OnClickEvent;
import com.tvsonar.android.feimu.event.loadNetworkEvent;
import com.tvsonar.android.feimu.view.base.MvpView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import javax.net.ssl.SSLHandshakeException;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public abstract class BasePresentFragment<T extends MvpView> extends RxFragment {
    public T mvpView;
    protected   String  tag = null;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        tag=getClass().getName()+"----fragment----";
        Mlog.l(tag+"onAttach");
    }

    private void initView(){
        Class<T> viewClass=getPresentClass();
        try {
            mvpView=viewClass.newInstance();
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tag=getClass().getName()+"----";
        Mlog.l(tag+"onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initView();
        EventBus.getDefault().register(this);
        mvpView.regist(inflater,this);
        return mvpView.getRootView();
    }

    public MvpView getMvpView() {
        return mvpView;
    }

    public abstract Class<T> getPresentClass();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        mvpView.unRegist();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void filterClickEvent(OnClickEvent clickEvent) {
        if (clickEvent != null && clickEvent.getMvpView() == mvpView) {
            onClick(clickEvent.getClickView());
        }
    }

    protected void onClick(View v) {
        Mlog.e("未捕获的点击事件");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadNetworkEvent(loadNetworkEvent loadNetworkEvent){
        onNetWorkErorRetry();
    }

    public class errorConsumer implements Consumer<Throwable> {

        @Override
        public void accept(Throwable e) throws Exception {
            e.printStackTrace();
            //mvpView.showSnackbar();
            Log.e("accept",e.getClass().getName());
            mvpView.dissmissLoading();
            if (e instanceof SSLHandshakeException) {
                //mvpView.showToast("请关闭");
            } else if (e instanceof InterruptedIOException ||
                    e instanceof SocketException
                    || e instanceof UnknownHostException) {
                // mvpView.showToast("请检查网络设置");
            } else {
                //先注释掉，服务端总是报500，好烦。
                //mvpView.showToast("请检查网络设置");
                Log.e("test",""+getClass().getName()+":exception:"+e);
            }
        }
    }

    public errorConsumer consumer=new errorConsumer();

    public  abstract void  onNetWorkErorRetry();

    protected void toActivity(Class<? extends Activity>   clazz){
        Intent intent=new Intent(getActivity(),clazz);
        startActivity(intent);
    }

    public  <T> ObservableTransformer<T, T> applyIOSchedulersAndLifecycle() {
        return observable -> observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(FragmentEvent.DESTROY.DESTROY));}
}

