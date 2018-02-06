package com.dreamzone.mtime;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.dreamzone.mtime.base.BaseCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author BMR
 * @ClassName: MainActivity
 * @Description: 几个简单的示例程序:列表页用RecyclerView和CardView
 * @date 2016/1/27 11:03
 */
public class MainActivity extends BaseCompatActivity {

    @BindView(R.id.ll_root)
    CoordinatorLayout llRoot;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initToolbar();
        toolbar.setTitle("MainActivity");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        //        final Snackbar snackbar = Snackbar.make(fab, "Simple Demo will continue to update...", Snackbar.LENGTH_LONG);
        //        snackbar.setAction("Cancel", new View.OnClickListener() {
        //            @Override
        //            public void onClick(View v) {
        //                snackbar.dismiss();
        //            }
        //        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(llRoot, "floatAction", Snackbar.LENGTH_SHORT).setAction("Action", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(MainActivity.this, "Action click", Toast.LENGTH_SHORT).show();
//                    }
//                }).show();
                showSnackbarCallback(llRoot);
            }
        });
    }


    private void showSnackbarCallback(View view) {
        Snackbar snackbar = Snackbar.make(view, R.string.on_loading_text, Snackbar.LENGTH_LONG).setAction("cancel", new View
                .OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        }).setCallback(callback);
        snackbar.show();
        snackbar.getView().setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        snackbar.setActionTextColor(getResources().getColor(R.color.colorAccent));
        ((TextView) snackbar.getView().findViewById(R.id.snackbar_text)).setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private Snackbar.Callback callback = new Snackbar.Callback() {
        @Override
        public void onDismissed(Snackbar snackbar, int event) {
            super.onDismissed(snackbar, event);
            switch (event) {
                case DISMISS_EVENT_SWIPE:
                    toolbar.setTitle("DISMISS_EVENT_SWIPE");
                    break;
                case DISMISS_EVENT_ACTION:
                    toolbar.setTitle("DISMISS_EVENT_ACTION");
                    break;
                case DISMISS_EVENT_TIMEOUT:
                    toolbar.setTitle("DISMISS_EVENT_TIMEOUT");
                    break;
                case DISMISS_EVENT_MANUAL:
                    toolbar.setTitle("DISMISS_EVENT_MANUAL");
                    break;
                case DISMISS_EVENT_CONSECUTIVE:
                    toolbar.setTitle("DISMISS_EVENT_CONSECUTIVE");
                    break;
            }

        }

        @Override
        public void onShown(Snackbar snackbar) {
            super.onShown(snackbar);
            toolbar.setTitle("snackbar onShown");
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
