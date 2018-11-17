package com.tvsonar.android.feimu.presenter.base;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.tvsonar.android.feimu.R;
import com.tvsonar.android.feimu.common.ToastUtil;

import static java.security.AccessController.getContext;

public class BaseLoadingFragment extends BaseDialogFragment {
    ProgressBar circleProgressBar;
    private int backClicktimes=1;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        Dialog dialog=new Dialog(getActivity());
        dialog.setContentView(R.layout.loading_dialog);
        circleProgressBar=dialog.findViewById(R.id.progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0f;
        window.setAttributes(params);
        dialog.getWindow().setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_transparent));
        dialog.setOnKeyListener((a,b,c)->{
                    if(b == KeyEvent.KEYCODE_BACK){
                        backClicktimes++;
                        ToastUtil.showToast(getContext(),"加载中。。。少侠留步，再按一次退出");
                        if(backClicktimes>2){
                            return false;
                        }
                        return true;
                    }
                    else return false;
                }
        );
        return dialog;
    }

    @Override
    public void show(FragmentManager fm, String tag) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    public void show(FragmentManager fm){
        show(fm,"loadingFragment");
    }

    @Override
    public void dismiss() {
        dismissAllowingStateLoss();
    }
}
