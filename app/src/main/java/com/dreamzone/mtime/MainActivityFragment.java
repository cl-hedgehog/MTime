package com.dreamzone.mtime;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dreamzone.mtime.adapter.DemoListRecyclerAdapter;

import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Demo列表
 */
public class MainActivityFragment extends Fragment {

    @Bind(R.id.tv_demo_info)
    TextView tvDemoInfo;
    @Bind(R.id.rv_demo_list)
    RecyclerView rvDemoList;

    private List<String> titleList;
    private DemoListRecyclerAdapter demoRecyclerAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        tvDemoInfo.setText(R.string.demo_list_title);
        // 获取demo的题目列表
        titleList = Arrays.asList(getResources().getStringArray(R.array.title_array));
        initRecyclerView();
        tvDemoInfo.requestFocus();
        return rootView;
    }

    private void initRecyclerView() {
        demoRecyclerAdapter = new DemoListRecyclerAdapter(getActivity(), titleList);
        rvDemoList.setHasFixedSize(true);
        rvDemoList.setAdapter(demoRecyclerAdapter);
        rvDemoList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvDemoList.setItemAnimator(new DefaultItemAnimator());
        demoRecyclerAdapter.setItemClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                displayDemoActivity(pos);
            }
        });
    }

    private void displayDemoActivity(int pos) {
        switch (pos) {
            case 0:
                SimpleMediaPlayActivity.start(getActivity());
                break;
            case 1:
                PlayRemoteMusicActivity.start(getActivity());
                break;
            case 2:
                SensorListActivity.start(getActivity());
                break;
            case 3:
                OkHttpActivity.start(getActivity());
                break;
            default:
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
