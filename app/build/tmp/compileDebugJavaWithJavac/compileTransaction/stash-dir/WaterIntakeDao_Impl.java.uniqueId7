package com.example.waterreminder.data;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class WaterIntakeDao_Impl implements WaterIntakeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WaterIntake> __insertionAdapterOfWaterIntake;

  public WaterIntakeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWaterIntake = new EntityInsertionAdapter<WaterIntake>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `water_intake` (`id`,`timestamp`,`volumeMl`) VALUES (nullif(?, 0),?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final WaterIntake entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.timestamp);
        statement.bindLong(3, entity.volumeMl);
      }
    };
  }

  @Override
  public void insert(final WaterIntake intake) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfWaterIntake.insert(intake);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<WaterIntake> getByRange(final long start, final long end) {
    final String _sql = "SELECT * FROM water_intake WHERE timestamp >= ? AND timestamp <= ? ORDER BY timestamp DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTimestamp = CursorUtil.getColumnIndexOrThrow(_cursor, "timestamp");
      final int _cursorIndexOfVolumeMl = CursorUtil.getColumnIndexOrThrow(_cursor, "volumeMl");
      final List<WaterIntake> _result = new ArrayList<WaterIntake>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final WaterIntake _item;
        final long _tmpTimestamp;
        _tmpTimestamp = _cursor.getLong(_cursorIndexOfTimestamp);
        final int _tmpVolumeMl;
        _tmpVolumeMl = _cursor.getInt(_cursorIndexOfVolumeMl);
        _item = new WaterIntake(_tmpTimestamp,_tmpVolumeMl);
        _item.id = _cursor.getLong(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Integer totalByRange(final long start, final long end) {
    final String _sql = "SELECT SUM(volumeMl) FROM water_intake WHERE timestamp >= ? AND timestamp <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, start);
    _argIndex = 2;
    _statement.bindLong(_argIndex, end);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Integer _result;
      if (_cursor.moveToFirst()) {
        final Integer _tmp;
        if (_cursor.isNull(0)) {
          _tmp = null;
        } else {
          _tmp = _cursor.getInt(0);
        }
        _result = _tmp;
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
