package com.vp19.tradelog.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vp19.tradelog.models.TradeLog;

import java.util.List;

@Dao
public interface TradeLogDao {

    @Query("SELECT * FROM TradeLog")
    List<TradeLog> getAll();

    @Insert
    void insertAll(TradeLog... tradeLogs);

    @Insert
    void insert(TradeLog tradeLog);

    @Query("SELECT * FROM TradeLog WHERE date = :date")
    TradeLog findTradeLogWithDate(String date);

    @Delete
    void delete(TradeLog tradeLog);

    @Update
    void update(TradeLog tradeLog);
}
