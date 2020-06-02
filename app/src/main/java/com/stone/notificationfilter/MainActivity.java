package com.stone.notificationfilter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
//import android.support.v7.app.AlertDialog;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.stone.notificationfilter.util.SpUtil;

/**
 * 1.0.190807
 * Hello World
 *
 * 1.1.190808
 * 悬浮磁贴图标增加圆角
 * 优化删除全部磁贴功能
 * 解决显示空白磁贴的问题
 * 优化其他内容
 *
 * 1.2.190813
 * 加入了 输入法防挡 功能
 */
public class MainActivity extends BaseActivity {
    public final String TAG ="MainActivity";
    private Toolbar mToolBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        CoordinatorLayout groupPollingAddress = (CoordinatorLayout)inflater.inflate(R.layout.tool_bar, null);
//        setTranslucentStatus();
//        mToolBar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(mToolBar);
//        //设置标题
//        getSupportActionBar().setTitle("我是ToolBar");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        if (Build.VERSION.SDK_INT >= 23) {
//            Window window = getWindow();
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.parseColor("#e5e5e5"));
//            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }

//        finishAndRemoveTask();

        boolean isFirstBoot = SpUtil.getSp(this,"appSettings").getBoolean("isFirstBoot", true);
        if (isFirstBoot) {
            new AlertDialog.Builder(this)
                    .setTitle("欢迎使用 " + getString(R.string.app_name))
                    .setMessage("在使用之前，您需要了解一些内容：" +
                            "\n本项目的开发的原因是：原作者的弃坑，，" +
                            "\n但恰好项目开源，本人又是程序，加之需要该应用" +
                            "\n所以三天入门安卓，再连肝四天完成本应用初版" +
                            "\n由于刚入门安卓开发，应用或许仍有BUG" +
                            "\n所做的改进：" +
                            "\n    几乎重写整个项目架构，修复BUG" +
                            "\n    强大的正则规则匹配系统，无限的自定义" +
                            "\n 悬浮通知提醒时，默认展开状态，上滑下滑关闭，五秒不操作也会关闭" +
                            "\n 右划后仅显示图标，不会自动关闭，可左划和点击恢复展开状态" +
                            "\n 展开状态下，点击悬浮通知，触发通知栏点击事件" +
                            "\n\n应用启动需要通知使用权和悬浮窗权限，带有感叹号的是未赋予的权限，您必须赋予后才能正常使用" +
                            "\n在修改应用配置时（如磁贴位置，磁贴方向）应用会清除所有悬浮磁贴（包括显示中和待显示）")
                    .setCancelable(false)
                    .setPositiveButton("了解", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            SpUtil.getSp(MainActivity.this,"appSettings").edit().putBoolean("isFirstBoot", false).apply();
                        }
                    })
                    .show();
        }
        replaceFragment(new MainFragment());

//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.fl, new MyFragment()).commit();



//        startService(new Intent(this, NotificationCollectorMonitorService.class));
    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu, menu);
//        return true;
//    }
//
//    public boolean showMenu(View anchor) {
//        PopupMenu popup = new PopupMenu(this, anchor);
//        popup.getMenuInflater().inflate(R.menu.menu, popup.getMenu());
//        popup.show();
//        return true;
//    }

}
