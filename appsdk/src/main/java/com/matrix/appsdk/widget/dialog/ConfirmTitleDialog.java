package com.matrix.appsdk.widget.dialog;

import android.content.Context;
import android.view.View;

import com.matrix.appsdk.R;


/**
 * 确认对话框，左右两个按钮,带标题
 *
 * @author trace
 */
public class ConfirmTitleDialog extends BaseDialog
{
    
    
    public ConfirmTitleDialog(Context context, int titleResId, int messageResId)
    {
        super(context);
        setContentView(R.layout.dialog_confirm);
        setTitle(context.getString(titleResId));
        setMessage(messageResId);
        setCanceledOnTouchOutside(false);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }

    public ConfirmTitleDialog(Context context, int titleResId, String message)
    {
        super(context);
        setContentView(R.layout.dialog_confirm);
        setTitle(context.getString(titleResId));
        setMessage(message);
        setCanceledOnTouchOutside(false);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }

    public ConfirmTitleDialog(Context context,int titleResId,String message,int rightBtnText)
    {
        super(context);
        setContentView(R.layout.dialog_confirm);
        setTitle(context.getString(titleResId));
        setMessage(message);
        setCanceledOnTouchOutside(false);
        getLeftButton().setVisibility(View.GONE);
        setRightText(rightBtnText);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }
    
    public ConfirmTitleDialog(Context context, int titleResId, String message,int leftBtnText,int rightBtnText)
    {
        super(context);
        setContentView(R.layout.dialog_confirm);
        setTitle(context.getString(titleResId));
        setMessage(message);
        setCanceledOnTouchOutside(false);
        setLeftText(leftBtnText);
        setRightText(rightBtnText);
        setLeftButtonListener(null);
        setRightButtonListener(null);
    }
}
