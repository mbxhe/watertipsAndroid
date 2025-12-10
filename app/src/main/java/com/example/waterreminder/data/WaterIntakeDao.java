package com.example.waterreminder.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface WaterIntakeDao {
    @Insert
    void insert(WaterIntake intake);

    @Query("SELECT * FROM water_intake WHERE timestamp >= :start AND timestamp <= :end ORDER BY timestamp DESC")
    List<WaterIntake> getByRange(long start, long end);

    @Query("SELECT SUM(volumeMl) FROM water_intake WHERE timestamp >= :start AND timestamp <= :end")
    Integer totalByRange(long start, long end);
}



