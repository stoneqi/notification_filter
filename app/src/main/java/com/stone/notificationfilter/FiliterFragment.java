package com.stone.notificationfilter;

import android.annotation.SuppressLint;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
//import android.support.annotation.Nullable;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.stone.notificationfilter.entitys.notificationfilter.NotificationFilterDao;
import com.stone.notificationfilter.entitys.notificationfilter.NotificationFilterDataBase;
import com.stone.notificationfilter.entitys.notificationfilter.NotificationFilterEntity;

import java.util.ArrayList;
import java.util.List;

public class FiliterFragment extends Fragment {
    private final static  String TAG ="FiliterActivity";
    private NotificationFilterDao notificationFilterDao =null;
//    private String filiter_path = "";
    private List<NotificationFilterEntity> notificationMatcherArrayList = null;
    private static ArrayList<String> data = null;
    private static ArrayAdapter<String> adapter = null;
    private View view =null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.filiter_fragment,container,false);

        data = new ArrayList<String>();
        Log.e(TAG,"create");

//        setTitle("所有规则");
        adapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,data);
        ListView listView = (ListView)view.findViewById(R.id.filiters_list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(),AddFiliterActivity.class);
                intent.putExtra("notification", notificationMatcherArrayList.get(position));
                startActivity(intent);

            }
        });

        view.findViewById(R.id.new_filiter).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"goto AddFiliterActivity");
                Intent intent = new Intent(getContext(),AddFiliterActivity.class);
                intent.putExtra("ID",-1);
                startActivity(intent);
                return;
            }
        });
        return view;
    }

    @SuppressLint("HandlerLeak")
    private Handler handler =new Handler(){
        @Override
        //当有消息发送出来的时候就执行Handler的这个方法
        public void handleMessage(Message msg){
            super.handleMessage(msg);

            if (notificationMatcherArrayList.isEmpty()){
                view.findViewById(R.id.no_filiter).setVisibility(View.VISIBLE);
                data.clear();
                pd.dismiss();
            }else {
                view.findViewById(R.id.no_filiter).setVisibility(View.GONE);
            }

            for (NotificationFilterEntity notificationMatcher: notificationMatcherArrayList) {
                data.clear();
                data.add(notificationMatcher.name);
            }
            adapter.notifyDataSetChanged();
            pd.dismiss();
        }
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        filiter_path = SpUtil.getSp(this,"APPSETTING").getString("filiter_path", "filit.json");

//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>();
    }

    @Override
    public void onResume() {
        Log.e(TAG,"get Data");
        if(notificationFilterDao==null){
            NotificationFilterDataBase db =NotificationFilterDataBase.getInstance(getContext());
            notificationFilterDao = db.NotificationFilterDao();
        }
        processThread();
        super.onResume();
    }

    //声明变量
    private Button b1;
    private ProgressDialog pd;

    private void processThread(){
        //构建一个下载进度条
        pd= ProgressDialog.show(getContext(), "Load", "Loading…");
        new Thread(){
            public void run(){
                //在新线程里执行长耗时方法
                notificationMatcherArrayList = notificationFilterDao.loadAll();
                handler.sendEmptyMessage(0);
            }
        }.start();
    }

}