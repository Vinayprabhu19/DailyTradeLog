package com.vp19.tradelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.vp19.tradelog.dao.TradeLogDao;
import com.vp19.tradelog.database.AppDatabase;
import com.vp19.tradelog.models.TradeLog;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            setContentView(R.layout.activity_main);

            TradeLogDao tradeLogDao = db.tradeLogDao();
            List<TradeLog> tradeLogList = tradeLogDao.getAll();
            for(TradeLog t:tradeLogList){
                Log.i("vplog","Logged - "+t.getDate()+" "+t.getProfit());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void onAddLog(View v){
        Intent intentMain = new Intent(MainActivity.this ,
                AddLog.class);
        MainActivity.this.startActivity(intentMain);
        Log.i("Content "," Main layout ");
    }
}