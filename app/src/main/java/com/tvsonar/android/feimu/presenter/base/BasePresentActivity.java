package com.tvsonar.android.feimu.presenter.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;

import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
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

public abstract class BasePresentActivity<T extends MvpView> extends RxAppCompatActivity {
    public T mvpView;
    protected View rootView;
    protected String tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        initView();
        tag=getLocalClassName()+"-----";
        Mlog.l(tag+"onCreate");
    }

    public T getMvpView() {
        return mvpView;
    }

    private void initView(){
        Class<T> viewClass=getPresentClass();
        try {
            mvpView=viewClass.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mvpView.regist(LayoutInflater.from(this),this);
        if(mvpView.getRootView()!=null){
            setContentView(mvpView.getRootView());
            if (mvpView.getToolbar() instanceof Toolbar) {
                Toolbar toolbar = (Toolbar) mvpView.getToolbar();
                setSupportActionBar(toolbar);
            }
        }
        rootView=mvpView.getRootView();

    }

    public abstract Class<T> getPresentClass();

    @Override
    protected void onDestroy() {
        super.onDestroy();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (getMenuRes() != 0) getMenuInflater().inflate(getMenuRes(), menu);
        return true;
    }

    protected int getMenuRes() {
        return 0;
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
                Log.e("test",""+getLocalClassName()+":exception:"+e);
            }
        }
    }

    public errorConsumer consumer=new errorConsumer();

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoadNetworkEvent(loadNetworkEvent loadNetworkEvent){
        onNetWorkErorRetry();
    }

    public  abstract void  onNetWorkErorRetry();

    protected void toActivity(Class<? extends Activity>   clazz){
        Intent intent=new Intent(this,clazz);
        startActivity(intent);
    }

    public void toActivity(Class<? extends Activity> activityClass, Bundle args) {
        if (null == activityClass) {
            throw new IllegalArgumentException("activityClass为空");
        }
        Intent intent = new Intent(this, activityClass);
        if (null != args) {
            intent.putExtras(args);
        }
        startActivity(intent);
    }

    protected <T> ObservableTransformer<T, T> applyIOSchedulersAndLifecycle() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindUntilEvent(ActivityEvent.DESTROY));
    }

}

