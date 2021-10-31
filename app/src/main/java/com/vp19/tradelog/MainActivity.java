package com.vp19.tradelog;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.cardview.widget.CardView;
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

import com.google.android.material.card.MaterialCardView;
import com.vp19.tradelog.dao.TradeLogDao;
import com.vp19.tradelog.database.AppDatabase;
import com.vp19.tradelog.models.SumAverageTradePojo;
import com.vp19.tradelog.models.TradeLog;

import org.w3c.dom.Text;

import java.time.LocalDate;
import java.util.List;
import static java.time.DayOfWeek.MONDAY;
import static java.time.DayOfWeek.SUNDAY;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previousOrSame;
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
            this.getOverView();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        this.getOverView();
    }

    public void viewLogs(View view){
        Intent intentMain = new Intent(MainActivity.this ,
                Logs.class);
        MainActivity.this.startActivity(intentMain);
        Log.i("Content "," Logs layout ");
    }

    public void getOverView(){
        try {
            ViewGroup overViewList= (ViewGroup) findViewById(R.id.linearLayout);
            overViewList.removeAllViews();
            Context context = getApplicationContext();
            int green= getResources().getColor(R.color.material_green);
            int red= getResources().getColor(R.color.red);
            LocalDate
                    today = LocalDate.now();
            TradeLogDao tradeLogDao = db.tradeLogDao();


            final LayoutInflater vi;

            vi = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            //Week
            View weekOverview=vi.inflate(R.layout.layout_overview,null);
            LocalDate monday = today.with(previousOrSame(MONDAY));
            LocalDate sunday = today.with(nextOrSame(SUNDAY));
            String m=monday.toString().replace("-","/");
            String s=sunday.toString().replace("-","/");
            SumAverageTradePojo weekSum = tradeLogDao.getTradeOverview(m,s);
            CardView cardView = (CardView) weekOverview.findViewById(R.id.card);
            TextView netProfitOverviewW = (TextView) weekOverview.findViewById(R.id.netProfitOverview);
            TextView grossProfitOverviewW = (TextView) weekOverview.findViewById(R.id.grossProfitOverview);
            TextView dateOverviewW = (TextView) weekOverview.findViewById(R.id.dateOverview);
            TextView grossBrokerageOverviewW = (TextView) weekOverview.findViewById(R.id.grossBrokerageOverview);
            double wGrossProfit= weekSum.getTotalProfit();
            double wGrossBrokerage = weekSum.getTotalBrokerage();
            double wNetProfit = wGrossProfit-wGrossBrokerage;
            if(wNetProfit<0){
                cardView.setCardBackgroundColor(red);
            }
            else{
                cardView.setCardBackgroundColor(green);
            }
            grossProfitOverviewW.setText("Gross Profit - "+wGrossProfit);
            grossBrokerageOverviewW.setText("Brokerage - "+wGrossBrokerage);
            netProfitOverviewW.setText("Net Profit - "+wNetProfit);
            dateOverviewW.setText(m+" - "+s);

            View monthOverview=vi.inflate(R.layout.layout_overview,null);
            LocalDate monthBegin = today.withDayOfMonth(1);
            LocalDate monthEnd = today.plusMonths(1).withDayOfMonth(1).minusDays(1);
            String mS=monthBegin.toString().replace("-","/");
            String mE=monthEnd.toString().replace("-","/");
            SumAverageTradePojo monthSum = tradeLogDao.getTradeOverview(mS,mE);
            CardView cardViewM = (CardView) monthOverview.findViewById(R.id.card);
            TextView typeM = (TextView) monthOverview.findViewById(R.id.type);
            TextView netProfitOverviewM = (TextView) monthOverview.findViewById(R.id.netProfitOverview);
            TextView grossProfitOverviewM= (TextView) monthOverview.findViewById(R.id.grossProfitOverview);
            TextView dateOverviewM = (TextView) monthOverview.findViewById(R.id.dateOverview);
            TextView grossBrokerageOverviewM = (TextView) monthOverview.findViewById(R.id.grossBrokerageOverview);
            double mGrossProfit= monthSum.getTotalProfit();
            double mGrossBrokerage = monthSum.getTotalBrokerage();
            double mNetProfit = mGrossProfit-mGrossBrokerage;
            if(mNetProfit<0){
                cardViewM.setCardBackgroundColor(red);
            }
            else{
                cardViewM.setCardBackgroundColor(green);
            }
            typeM.setText("This Month");
            grossProfitOverviewM.setText("Gross Profit - "+mGrossProfit);
            grossBrokerageOverviewM.setText("Brokerage - "+mGrossBrokerage);
            netProfitOverviewM.setText("Net Profit - "+mNetProfit);
            dateOverviewM.setText(m+" - "+s);

            //year
            View yearOverview=vi.inflate(R.layout.layout_overview,null);
            LocalDate firstDay = today.with(firstDayOfYear()); // 2015-01-01
            LocalDate lastDay = today.with(lastDayOfYear()); // 2015-12-31

            String yS=firstDay.toString().replace("-","/");
            String yE=lastDay.toString().replace("-","/");
            SumAverageTradePojo yearSum = tradeLogDao.getTradeOverview(yS,yE);
            CardView cardViewY = (CardView) yearOverview.findViewById(R.id.card);
            TextView typeY = (TextView) yearOverview.findViewById(R.id.type);
            TextView netProfitOverviewY = (TextView) yearOverview.findViewById(R.id.netProfitOverview);
            TextView grossProfitOverviewY= (TextView) yearOverview.findViewById(R.id.grossProfitOverview);
            TextView dateOverviewY = (TextView) yearOverview.findViewById(R.id.dateOverview);
            TextView grossBrokerageOverviewY = (TextView) yearOverview.findViewById(R.id.grossBrokerageOverview);
            double yGrossProfit= yearSum.getTotalProfit();
            double yGrossBrokerage = yearSum.getTotalBrokerage();
            double yNetProfit = yGrossProfit-yGrossBrokerage;
            if(yNetProfit<0){
                cardViewY.setCardBackgroundColor(red);
            }
            else{
                cardViewY.setCardBackgroundColor(green);
            }
            typeY.setText("This Year");
            grossProfitOverviewY.setText("Gross Profit - "+yGrossProfit);
            grossBrokerageOverviewY.setText("Brokerage - "+yGrossBrokerage);
            netProfitOverviewY.setText("Net Profit - "+yNetProfit);
            dateOverviewY.setText(m+" - "+s);


            overViewList.addView(weekOverview);
            overViewList.addView(monthOverview);
            overViewList.addView(yearOverview);
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