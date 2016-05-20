package com.matrix.appsdk.widget;

/**
 * @ClassName: SnackbarUtils
 * @Description:
 * @date 2016/5/20 16:13
 */

import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.matrix.appsdk.R;

public class SnackbarUtils {

    /**
     * 设置Snackbar的背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    public static void setSnackbarBackgroudnColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        if (view != null) {
            view.setBackgroundColor(backgroundColor);
        }
    }

    /**
     * 设置Snackbar的Message文字颜色
     *
     * @param snackbar
     * @param messageColor
     */
    public static void setSnackbarMessageColor(Snackbar snackbar, int messageColor) {
        View view = snackbar.getView();
        if (view != null) {
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
        }
    }


    /**
     * 向Snackbar中添加view
     *
     * @param snackbar
     * @param layoutId
     * @param index    新加布局在Snackbar中的位置
     */
    public static void addCustomViewToSnackbar(Snackbar snackbar, int layoutId, int index) {
        View snackbarview = snackbar.getView();
        Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbarview;
        View addView = LayoutInflater.from(snackbarview.getContext()).inflate(layoutId, null);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout
                .LayoutParams.WRAP_CONTENT);
        p.gravity = Gravity.CENTER_VERTICAL;
        snackbarLayout.addView(addView, index, p);
    }

}