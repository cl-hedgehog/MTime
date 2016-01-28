package com.matrix.appsdk.widget.dialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by trace on 15/4/22.
 */
public class DialogCache
{
    private static DialogCache ins;

    private final List<BaseDialog> dialogs = new ArrayList<BaseDialog>();

    public static synchronized DialogCache getIns()
    {
        if (ins == null)
        {
            ins = new DialogCache();
        }
        return ins;
    }

    public void add(BaseDialog dialog)
    {
        synchronized (dialogs)
        {
            dialogs.add(dialog);
        }
    }

    public void close()
    {
        synchronized (dialogs)
        {
            for (BaseDialog dialog : dialogs)
            {
                if (dialog != null)
                {
                    dialog.dismiss();
                }
            }
            dialogs.clear();
        }
    }
}
