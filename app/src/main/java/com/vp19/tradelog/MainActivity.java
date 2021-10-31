package com.vp19.tradelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.vp19.tradelog.dao.TradeLogDao;
import com.vp19.tradelog.database.AppDatabase;
import com.vp19.tradelog.models.TradeLog;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            db = Room.databaseBuilder(getApplicationContext(),
                    AppDatabase.class, "tradelogdb").allowMainThreadQueries().build();
            setContentView(R.layout.activity_main);
            ViewGroup logList= (ViewGroup) findViewById(R.id.logList);
            this.prepareList(logList,getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("vplog","Resumed ");
        ViewGroup logList= (ViewGroup) findViewById(R.id.logList);
        this.prepareList(logList,getApplicationContext());
    }

    public void prepareList(ViewGroup logList, Context context){
        Log.i("vplog","Prepare List ");
        try {
            TradeLogDao tradeLogDao = db.tradeLogDao();
            List<TradeLog> tradeLogList = tradeLogDao.getAll();
            for(TradeLog t:tradeLogList){
                Log.i("vplog","Logged - "+t.getDate()+" "+t.getProfit());
            }
            final LayoutInflater vi;

                vi = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            logList.removeAllViews();
            for(int i=0;i<tradeLogList.size();i++){
                TradeLog log= tradeLogList.get(i);
                View v=vi.inflate(R.layout.layout_log,null);
                TextView profit = v.findViewById(R.id.proftiTI);
                TextView brokerage = v.findViewById(R.id.brokerageTI);
                TextView date = v.findViewById(R.id.dateTI);
                TextView indicator = v.findViewById(R.id.indicator);
                String profitString = Double.toString(log.getProfit());
                String brokerageString = Double.toString(log.getBrokerage());
                profit.setText(profitString);
                brokerage.setText(brokerageString);
                date.setText(log.getDate());
                int green= getResources().getColor(R.color.material_green);
                int red= getResources().getColor(R.color.red);
                ImageButton delete = v.findViewById(R.id.deleteLog);
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.i("vplog","Delete "+log.getDate());
                    }
                });
                if(log.getProfit()>0){
                    indicator.setText("Profit");
                    indicator.setTextColor(green);
                }
                else{
                    indicator.setText("Loss");
                    indicator.setTextColor(red);
                }
                logList.addView(v);
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