package com.tvsonar.android.feimu.view.base;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.gyf.barlibrary.ImmersionBar;
import com.jakewharton.rxbinding2.view.RxView;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.tvsonar.android.feimu.R;
import com.tvsonar.android.feimu.common.Mlog;
import com.tvsonar.android.feimu.common.ToastUtil;
import com.tvsonar.android.feimu.event.OnClickEvent;
import com.tvsonar.android.feimu.presenter.base.BaseLoadingFragment;
import com.tvsonar.android.feimu.presenter.base.BasePresentActivity;
import com.tvsonar.android.feimu.view.weidget.SmartToolbar;

import org.greenrobot.eventbus.EventBus;

import java.io.InterruptedIOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.SSLHandshakeException;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

public abstract class MvpView implements BaseView {
//    private TimePickerView timeChooseDialog;
    protected View rootView;
    private BaseLoadingFragment loadingFragment;
    private Unbinder unbinder;
    protected LifecycleProvider mLifecycleProvider;
    protected ImmersionBar mImmersionBar;
//    BaseConfirmDialogFragment confirmDialog;
//    private ChooseTakePhotoOrFromXiangceDialogFragment dialogFragment;


    @Override
    public void regist(@NonNull LayoutInflater inflater, LifecycleProvider provider) {
        rootView=inflater.inflate(getLayoutId(),null);
        this.mLifecycleProvider = provider;
        unbinder= ButterKnife.bind(this,rootView);
        if (null != getToolbar()) {
            mImmersionBar = ImmersionBar.with(getActivity())
                    .titleBar(getToolbar())
                    .fitsSystemWindows(false);
            mImmersionBar.init();
        }
    }

    protected void setStateBarWhite(){
        mImmersionBar = ImmersionBar.with(getActivity())
                //.titleBar(getToolbar())
                .barColor(R.color.white)
                .statusBarDarkFont(true)
                .fitsSystemWindows(false);
        mImmersionBar.init();
        // }
    }

    protected void setStatusBarDark(){
        if (null != getToolbar()) {
            mImmersionBar = ImmersionBar.with(getActivity())
                    .titleBar(getToolbar())
                    .statusBarDarkFont(true)
//                    .keyboardEnable(true)
//                    .keyboardMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
                    .fitsSystemWindows(false);
            mImmersionBar.init();
        }
    }

    protected void setStatusBarLight(){
        if (null != getToolbar()) {
            mImmersionBar = ImmersionBar.with(getActivity())
                    .titleBar(getToolbar())
                    .statusBarDarkFont(false)
                    .fitsSystemWindows(false);
            mImmersionBar.init();
        }
    }

    protected int getStatebarHeight(){
        return mImmersionBar.getStatusBarHeight(getActivity());
    }


    public View getRootView() {
        return rootView;
    }

    @Override
    public void unRegist() {
        if(unbinder==null)return;
        unbinder.unbind();
        if (mImmersionBar != null) mImmersionBar.destroy();
        rootView=null;
    }

    @Override
    public void showLoading() {
        if(loadingFragment==null){
            this.loadingFragment=new BaseLoadingFragment();
        }if(!loadingFragment.isAdded()&&(getFragment("loadingFragment")==null)){
            loadingFragment.show(((AppCompatActivity)getActivity()).getSupportFragmentManager());
        }
    }

    @Override
    public void dissmissLoading() {
        if(getFragment("loadingFragment")==null)return;
        loadingFragment.dismiss();
    }

//    //弹出选图dialog
//    public void showPickDilag(){
//        if (null == dialogFragment) {
//            dialogFragment = new ChooseTakePhotoOrFromXiangceDialogFragment();
//            dialogFragment.setOnClickListener(((dialogFragment1, view) -> {
//                if (view.getId() == R.id.bt_cancel) {
//                    dialogFragment1.dismiss();
//                    return;
//                }
//                EventBus.getDefault()
//                        .post(new OnClickEvent(this, view));
//                dialogFragment1.dismiss();
//            }));
//        }
//        FragmentActivity activity = getActivity();
//        dialogFragment.show(activity.getSupportFragmentManager());
//    }

    //初始时间格式为2018-10-25
//    public void showDatePicker(String time,String tag) {
//        Calendar startDate = Calendar.getInstance();
//        startDate.set(1900, 0, 1);
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        Calendar nowDate = Calendar.getInstance();
//        try {
//            java.util.Date date = dateFormat.parse(time);
//            nowDate.setTime(date);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        TimePickerView timePickerView = new TimePickerBuilder(getActivity(), new OnTimeSelectListener() {
//            @Override
//            public void onTimeSelect(java.util.Date date, View v) {
//                EventBus.getDefault()
//                        .post(new onTimePickEvent(MvpView.this, tag, dateFormat.format(date),date.getTime()));
//            }
//        })
//                .setType(new boolean[]{true, true, true, false, false, false})
//                .setRangDate(startDate, Calendar.getInstance())
//                .build();
//        timePickerView.setDate(nowDate);
//        timePickerView.show();
//    }

    @Override
    public void showErrorMessage(String msg) {
        showToast(msg);
    }

    @Override
    public void showLongMessage(String msg) {
        showToast(msg);
    }

    public void showSnackbar(){
//        SnackBarUtils.IndefiniteSnackbar(getRootView(),"无网络",R.color.white, R.color.colorBlack,-1)
//                .setAction("点击重试",v -> {
//                    EventBus.getDefault().post(new loadNetworkEvent(this));
//                }).show();
    }

    protected   void  go2Activity(Class<?extends BasePresentActivity>  activity) {
        Intent intent=new Intent(getActivity(), activity);
        getActivity().startActivity(intent);

    }

    @Override
    public <T extends Activity> T getActivity() {
        return null != rootView ? (T) rootView.getContext() : null;
    }

    public <T extends Fragment> T getFragment(String tag) {
        FragmentManager manager=((AppCompatActivity)getActivity()).getSupportFragmentManager();
        T t= (T) manager.findFragmentByTag(tag);
        if(t!=null)return t;
        else {
            Mlog.t("null");
            return null;
        }
    }

    public void showToast(String s){
        new ToastUtil().Short(getActivity(),s).setToastBackground(getActivity().getResources().getColor(R.color.colorAccent),R.drawable.bg_round_white).show();
    }

    protected void initToolbar(SmartToolbar toolbar) {
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    protected void postClick(View view) {
        RxView.clicks(view)
                .throttleFirst(500, TimeUnit.MILLISECONDS)
                .compose(applySchedulersAndLifecycle())
                .subscribe(it -> EventBus.getDefault()
                        .post(new OnClickEvent(this, view)));
    }

    protected <T> ObservableTransformer<T, T> applySchedulersAndLifecycle() {
        if (mLifecycleProvider instanceof Activity) {
            return observable -> observable.observeOn(AndroidSchedulers.mainThread())
                    .compose(mLifecycleProvider.bindUntilEvent(ActivityEvent.DESTROY));
        } else {
            return observable -> observable.observeOn(AndroidSchedulers.mainThread())
                    .compose(mLifecycleProvider.bindUntilEvent(FragmentEvent.DESTROY));
        }
    }


    public abstract int getLayoutId();


    public View getToolbar() {
        return null;
    }



    public class errorConsumer implements Consumer<Throwable> {

        @Override
        public void accept(Throwable e) throws Exception {
            e.printStackTrace();
            Log.e("test",e.getClass().getName());
            dissmissLoading();
            if (e instanceof SSLHandshakeException) {
                showToast("请检关闭");
            } else if (e instanceof InterruptedIOException ||
                    e instanceof SocketException
                    || e instanceof UnknownHostException) {
                showToast("请检查网络");
            } else {
                //先注释掉，服务端总是报500，好烦。
                showToast("光纤被挖断");
                Log.e("test",""+getClass().getName()+":exception:"+e);
            }
        }
    }

    public errorConsumer consumer=new errorConsumer();

    public String getString(int id){
        return getActivity().getResources().getString(id);
    }

    public int getColor(int id){
        return getActivity().getResources().getColor(id);
    }




//    public void showErrorDialog(String error){
//        if(confirmDialog!=null)confirmDialog.dismiss();
//        confirmDialog=new BaseConfirmDialogFragment();
//        confirmDialog.show(((AppCompatActivity)getActivity()).getSupportFragmentManager());
//        confirmDialog.setContent(error);
//        confirmDialog.setTitle("提示");
//        confirmDialog.setSiginalButtom();
//        confirmDialog.setOnClickListener((f,v)->{
//            if(v.getId()==R.id.bt_confirm){
//                confirmDialog.dismiss();
//            }
//        });
//    }

//    public void showDialogWithMessage(String error,onDialogConfirm dialogConfirm){
//        if(confirmDialog!=null)confirmDialog.dismiss();
//        confirmDialog=new BaseConfirmDialogFragment();
//        confirmDialog.show(((AppCompatActivity)getActivity()).getSupportFragmentManager());
//        confirmDialog.setContent(error);
//        confirmDialog.setTitle("提示");
//        confirmDialog.setOnClickListener((f,v)->{
//            if(v.getId()==R.id.bt_confirm){
//                dialogConfirm.onConfirm();
//
//            }
//            confirmDialog.dismiss();
//        });
//    }

    public interface onDialogConfirm{
        public void onConfirm();
    }

//    private PickerDialogFragment pickerDialog;
//    public void showpickerDialog(String title,List<String> items) {
//        FragmentActivity activity = getActivity();
//        if (null == pickerDialog) {
//            pickerDialog = PickerDialogFragment.newInstance(title);
//            pickerDialog.addItems(0, items);
//            pickerDialog.setOnSelectedCompletedLister(((dialogFragment, indexs) -> {
//                int index = indexs[0];
//                PickDialogSelectedEvent event=new PickDialogSelectedEvent(this,title,index,items.get(index));
//                EventBus.getDefault().post(event);
//            }));
//        }
//        pickerDialog.show(activity.getSupportFragmentManager(),"pickerDialog");
//    }
//
//    public View getNoMoreFooter(){
//        LayoutInflater inflater=getActivity().getLayoutInflater();
//        View view=inflater.inflate(R.layout.item_no_more_data,null);
//        return view;
//    }
}


