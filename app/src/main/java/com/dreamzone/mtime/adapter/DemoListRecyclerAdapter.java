package com.dreamzone.mtime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamzone.mtime.R;
import com.matrix.appsdk.BaseApp;
import com.matrix.appsdk.widget.FlexibleToast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * @author BMR
 * @ClassName: DemoListAdapter
 * @Description: 主页的Demo列表展示用的Adapter
 * @date 2016/1/27 13:50
 */
public class DemoListRecyclerAdapter extends RecyclerView.Adapter<DemoListRecyclerAdapter.ImageTextViewHoler> {

    private List<String> mTitleList;
    private Context mContext;
    private LayoutInflater mInflater;

    private View.OnLongClickListener mItemLongClickListener = null;
    private View.OnClickListener mItemClickListener = null;

    public DemoListRecyclerAdapter(Context context, List<String> titleList) {
        this.mContext = context;
        this.mTitleList = titleList;
        this.mInflater = LayoutInflater.from(mContext);
    }

    public void setItemLongClickListener(View.OnLongClickListener itemLongClickListener) {
        this.mItemLongClickListener = itemLongClickListener;

    }

    public void setItemClickListener(View.OnClickListener itemClickListener) {
        this.mItemClickListener = itemClickListener;

    }

    @Override
    public ImageTextViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageTextViewHoler(mInflater.inflate(R.layout.cardview_for_demo_list, parent, false));
    }

    @Override
    public void onBindViewHolder(ImageTextViewHoler holder, final int position) {
        holder.tvItemIndex.setText((position + 1) + "");
        holder.tvItemName.setText(mTitleList.get(position));
        holder.itemView.setTag(mTitleList.get(position));
        // set long click listener
        if (mItemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

                @Override
                public boolean onLongClick(View v) {
                    // tag for position
                    v.setTag(position);
                    mItemLongClickListener.onLongClick(v);
                    return false;
                }
            });
        }
        // set click listener
        if (mItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    v.setTag(position);
                    mItemClickListener.onClick(v);
                    FlexibleToast.Builder builder = new FlexibleToast.Builder(mContext).setFirstText
                            ("clicked " + position);
                    BaseApp.getApp().toastShowByBuilder(builder);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return null == mTitleList ? 0 : mTitleList.size();
    }


    public static class ImageTextViewHoler extends RecyclerView.ViewHolder {
        @Bind(R.id.tv_item_index)
        TextView tvItemIndex;
        @Bind(R.id.tv_item_name)
        TextView tvItemName;

        public ImageTextViewHoler(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }
}
