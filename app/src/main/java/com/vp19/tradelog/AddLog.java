package com.vp19.tradelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.room.Room;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.vp19.tradelog.dao.TradeLogDao;
import com.vp19.tradelog.database.AppDatabase;
import com.vp19.tradelog.models.TradeLog;

public class AddLog extends AppCompatActivity {
    private AppDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "tradelogdb").allowMainThreadQueries().build();
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
            String date = day + " "+getMonth(month) + " "+year;
            String dateString = year+"/"+(month<10?"0"+month:month)+"/"+(day<10?"0"+day:day);
            String profitValue=profitInput.getText().toString().trim();
            String brokerageValue=brokerageInput.getText().toString();
            double profit=profitValue.equals("")?0:Double.parseDouble(profitValue);
            double brokerage=brokerageValue.equals("")?0:Double.parseDouble(brokerageValue);

             TradeLog tradeLog = new TradeLog();
            Log.i("vplog","date "+dateString);
            Log.i("vplog","profit "+profit);
            Log.i("vplog","brokerage"+ brokerage);
            tradeLog=new TradeLog();
            tradeLog.setProfit(profit);
            tradeLog.setBrokerage(brokerage);
            tradeLog.setDate(date);
            tradeLog.setDateString(dateString);
            tradeDao.insert(tradeLog);
            Context context = getApplicationContext();
            CharSequence text = "Logged entry successfully";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

        }
        catch(Exception e){
            e.printStackTrace();
            Log.e("vplog",e.getMessage());
        }

    }

   private String getMonth(int month){
        switch (month){
            case 1:return "Jan";
            case 2:return "Feb";
            case 3:return "Mar";
            case 4:return "Apr";
            case 5:return "May";
            case 6:return "Jun";
            case 7:return "Jul";
            case 8:return "Aug";
            case 9:return "Sep";
            case 10:return "Oct";
            case 11:return "Nov";
            case 12:return "Dec";
            default:return "";
        }
    }
}