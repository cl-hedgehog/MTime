package com.matrix.appsdk.widget.dialog;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.matrix.appsdk.R;


/**
 * Created by cmcc on 15/4/22.
 */
public class ProcessDialog extends BaseDialog {
    public static final String TAG = "[ProcessDialog]";

    private ProgressBar mProgressBar;
    private TextView mMessageText;

    public ProcessDialog(Context context, int resId, boolean showProcess) {
        this(context, context.getString(resId));
    }

    public ProcessDialog(Context context, int resId) {
        this(context, context.getString(resId));
    }

    public ProcessDialog(Context context, String message) {
//        super(context);
//        setContentView(R.layout.dialog_process);
//        setMessage(message);
//        setCanceledOnTouchOutside(false);
//        setCancelable(true);
        this(context, message, true);
    }

    public ProcessDialog(Context context, String message, boolean showProgress) {
        super(context);
        setContentView(R.layout.dialog_process);
        setMessage(message);
        setCanceledOnTouchOutside(false);
        setCancelable(true);

        mProgressBar = (ProgressBar) findViewById(R.id.dialog_progressbar);
        if (showProgress) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }

    public void updateView(String msg) {
        this.updateView(msg, true);
    }

    public void updateView(String msg, boolean showProgress) {
        setMessage(msg);
        if (showProgress) {
            mProgressBar.setVisibility(View.VISIBLE);
        } else {
            mProgressBar.setVisibility(View.GONE);
        }
    }


    @Override
    public void show() {
        DialogCache.getIns().close();
        DialogCache.getIns().add(this);
        super.show();
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }

}
