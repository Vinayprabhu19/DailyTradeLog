package com.vp19.tradelog.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.vp19.tradelog.dao.TradeLogDao;
import com.vp19.tradelog.models.TradeLog;

@Database(entities = {TradeLog.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TradeLogDao tradeLogDao();
}
