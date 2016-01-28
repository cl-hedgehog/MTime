package com.dreamzone.mtime.common;

import android.app.Activity;

import com.trace.mtk.log.Logger;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author BMR
 * @ClassName: ActivityStack
 * @Description: TODO:
 * @date 2016/1/10 16:34
 */
public final class ActivityStack
{
    private static final String FRAMEWORKTAG = "FrameWork";
    private static final ActivityStack INSTANCE = new ActivityStack();
    private final Stack<Activity> stack;

    public static ActivityStack getIns()
    {
        return INSTANCE;
    }

    private ActivityStack()
    {
        stack = new Stack<Activity>();
    }

    public int getSize()
    {
        return stack.size();
    }

    public void push(Activity act, boolean singleTask)
    {
        if (act == null)
        {
            return;
        }
        // if singleTask mode and contain the activity,
        // remove all activities above the activity and the activity
        if (singleTask && contain(act.getClass()))
        {
            popupAbove(act.getClass());
            stack.pop().finish();
        }
        // then push the activity
        stack.push(act);
    }

    public void push(Activity activity)
    {
        Logger.beginInfo().p("Start Activity ").p(activity.getClass().getName()).p("  hashcode = ").p(activity.hashCode()).end();
        push(activity, false);
    }

    public void popup()
    {
        if (stack.isEmpty())
        {
            return;
        }

        Activity activity = stack.pop();

        if (null != activity)
        {
            Logger.beginInfo().p("stop Activity ").p(activity.getClass().getName()).p("  hashcode = ").p(activity.hashCode()).end();
            activity.finish();
        }
    }

    public void popup(Activity activity)
    {
        if (null != activity)
        {
            Logger.beginInfo().p("stop Activity ").p(activity.getClass().getName()).p("  hashcode = ").p(activity.hashCode()).end();
            stack.removeElement(activity);
//            activity.finish();
        }
    }

    public void popup(Class<? extends Activity> activityClass)
    {
        if (null != activityClass)
        {
            Activity ba;

            for (Iterator<Activity> it = stack.iterator(); it.hasNext();)
            {
                ba = it.next();

                if (null != ba && ba.getClass() == activityClass)
                {
                    it.remove();
                    ba.finish();
                }
            }
        }
    }

    public void popupWithoutFinish(Activity curAc)
    {
        if (null != curAc)
        {
            stack.removeElement(curAc);
        }
    }

    public void popupAbove(Activity activity)
    {
        int size = stack.size();
        int loc = stack.indexOf(activity);

        if (loc == -1)
        {
            return;
        }

        Activity temp = null;

        for (int i = size - 1; i > loc; i--)
        {
            temp = stack.remove(i);
            temp.finish();
        }
    }

    public void popupAllExcept(Activity activity)
    {
        final Activity inAc = activity;
        int size = stack.size();
        Activity temp;

        Logger.debug(FRAMEWORKTAG, "size:" + size + ",activity:" + activity);

        try
        {
            for (int i = 0; i < size; i++)
            {
                temp = stack.pop();
                if ((null != temp) && (temp != inAc))
                {
                    temp.finish();
                }
            }
        }
        catch (Exception e)
        {
            Logger.error(FRAMEWORKTAG, e.toString());
        }
        if (null != inAc)
        {
            stack.push(inAc);
        }
    }


    /**
     * popup all the activity
     */
    public void popupAll()
    {
        int size = stack.size();
        Activity temp;

        Logger.debug(FRAMEWORKTAG, "size:" + size + ",popup");

        try
        {
            for (int i = 0; i < size; i++)
            {
                temp = stack.pop();
                if ((null != temp))
                {
                    temp.finish();
                }
            }
        }
        catch (Exception e)
        {
            Logger.error(FRAMEWORKTAG, e.toString());
        }
    }

    public Activity getCurActivity()
    {
        if (!stack.isEmpty())
        {

            Activity currentActivity = stack.lastElement();
            if (null == currentActivity)
            {
                popup();
                currentActivity = getCurActivity();
            }
            return currentActivity;
        }
        return null;
    }

    public Activity getActivity(int position)
    {
        if ((null != stack) && (position < stack.size()))
        {
            return stack.elementAt(position);
        }
        return null;
    }


    public boolean contain(Class<? extends Activity> activityClass)
    {
        Activity a;
        for (int i = 0; i < stack.size(); i++)
        {
            a = getActivity(i);
            if (a != null && a.getClass() == activityClass)
            {
                return true;
            }
        }
        return false;
    }


    public void popupAbove(Class<?> activityClass)
    {
        if (activityClass == null)
        {
            return;
        }

        Activity a;
        int pos = -1;
        for (int i = 0; i < stack.size(); i++)
        {
            a = getActivity(i);
            if (a != null && a.getClass() == activityClass)
            {
                pos = i;
                break;
            }
        }

        if (pos == -1)
        {
            return;
        }

        int stackSize = stack.size();
        for (int i = 0; i < stackSize - pos - 1; i++)
        {
            stack.pop().finish();
        }
    }
}

