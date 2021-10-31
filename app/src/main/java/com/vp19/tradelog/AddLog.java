package com.vp19.tradelog;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;

import com.google.android.material.textfield.TextInputEditText;
import com.vp19.tradelog.dao.TradeLogDao;
import com.vp19.tradelog.database.AppDatabase;
import com.vp19.tradelog.models.TradeLog;

public class AddLog extends AppCompatActivity {
    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);
    }

    public void cancel(View v){
        finish();
    }

    public void submit(View v){
        try{
            View rootView = v.getRootView();
            Log.i("vplog","submit");
            TradeLogDao tradeDao =db.tradeLogDao();
            DatePicker datePicker = (DatePicker) rootView.findViewById(R.id.al_date_picker);
            TextInputEditText profitInput = (TextInputEditText)rootView.findViewById(R.id.profitInput);
            TextInputEditText brokerageInput = (TextInputEditText)rootView.findViewById(R.id.brokerage);
            int day = datePicker.getDayOfMonth();
            int month = datePicker.getMonth() + 1;
            int year = datePicker.getYear();
            String date = year+"/"+month+"/"+day;
            String profitValue=profitInput.getText().toString().trim();
            String brokerageValue=brokerageInput.getText().toString();
            double profit=profitValue.equals("")?0:Double.parseDouble(profitValue);
            double brokerage=brokerageValue.equals("")?0:Double.parseDouble(brokerageValue);

            TradeLog tradeLog = tradeDao.findTradeLogWithDate(date);
            if(tradeLog!=null){
                Log.i("vplog","ex date "+tradeLog.getDate());
                Log.i("vplog","ex profit "+tradeLog.getProfit());
                Log.i("vplog","ex brokerage"+ tradeLog.getBrokerage());
                tradeLog.setProfit(profit);
                tradeLog.setBrokerage(brokerage);
                tradeDao.update(tradeLog);
            }
            else{
                Log.i("vplog","date "+date);
                Log.i("vplog","profit "+profit);
                Log.i("vplog","brokerage"+ brokerage);
                tradeLog=new TradeLog();
                tradeLog.setProfit(profit);
                tradeLog.setBrokerage(brokerage);
                tradeLog.setDate(date);

                tradeDao.insert(tradeLog);
            }



        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("vplog",e.getMessage());
        }

    }
}