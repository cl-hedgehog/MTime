package com.matrix.appsdk.widget;


import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.matrix.appsdk.R;


/**
 * @author BMR
 * @ClassName: ToastWithTwoText
 * @Description: TODO:
 * @date 2015/12/22 14:24
 */
public class ToastWithTwoText {
    private static ToastWithTwoText toastWithTwoText;

    private Toast toast;
    private Context mContext;

    private ToastWithTwoText(Context context) {
        this.mContext = context;
    }

    public static ToastWithTwoText createToastConfig(Context context) {
        if (toastWithTwoText == null) {
            toastWithTwoText = new ToastWithTwoText(context);
        }
        return toastWithTwoText;
    }

    /**
     * 显示Toast
     *
     * @param tvStrOne
     * @param tvStrTwo
     */

    public void toastShow(String tvStrOne, String tvStrTwo) {
        View layout = LayoutInflater.from(mContext).inflate(R.layout.layout_toast_with_two_text, null);
        TextView tvOne = (TextView) layout.findViewById(R.id.tv_text_one);
        TextView tvTwo = (TextView) layout.findViewById(R.id.tv_text_two);
        tvOne.setText(tvStrOne);
        tvTwo.setText(tvStrTwo);
        toast = new Toast(mContext);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_VERTICAL, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(layout);
        toast.show();
    }

    public void toastShow(int idStrOne, int idStrTwo) {
        toastShow(mContext.getString(idStrOne), mContext.getString(idStrTwo));
    }

}
