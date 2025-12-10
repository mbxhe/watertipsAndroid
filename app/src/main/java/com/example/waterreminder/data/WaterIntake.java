package com.example.waterreminder.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "water_intake")
public class WaterIntake {
    @PrimaryKey(autoGenerate = true)
    public long id;
    public long timestamp;
    public int volumeMl;

    public WaterIntake(long timestamp, int volumeMl) {
        this.timestamp = timestamp;
        this.volumeMl = volumeMl;
    }
}



