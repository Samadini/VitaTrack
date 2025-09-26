package com.vitatrack.app.data.dao;

import android.database.Cursor;
import android.os.CancellationSignal;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.vitatrack.app.data.database.Converters;
import com.vitatrack.app.data.model.WaterIntake;
import java.lang.Class;
import java.lang.Exception;
import java.lang.Integer;
import java.lang.Long;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class WaterIntakeDao_Impl implements WaterIntakeDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<WaterIntake> __insertionAdapterOfWaterIntake;

  private final Converters __converters = new Converters();

  private final EntityDeletionOrUpdateAdapter<WaterIntake> __deletionAdapterOfWaterIntake;

  private final EntityDeletionOrUpdateAdapter<WaterIntake> __updateAdapterOfWaterIntake;

  private final SharedSQLiteStatement __preparedStmtOfDeleteWaterIntakeById;

  public WaterIntakeDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfWaterIntake = new EntityInsertionAdapter<WaterIntake>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `water_intake` (`id`,`userId`,`amountMl`,`notes`,`date`,`createdAt`,`updatedAt`) VALUES (nullif(?, 0),?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WaterIntake entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindLong(3, entity.getAmountMl());
        if (entity.getNotes() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNotes());
        }
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_2);
        }
      }
    };
    this.__deletionAdapterOfWaterIntake = new EntityDeletionOrUpdateAdapter<WaterIntake>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `water_intake` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WaterIntake entity) {
        statement.bindString(1, entity.getId());
      }
    };
    this.__updateAdapterOfWaterIntake = new EntityDeletionOrUpdateAdapter<WaterIntake>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `water_intake` SET `id` = ?,`userId` = ?,`amountMl` = ?,`notes` = ?,`date` = ?,`createdAt` = ?,`updatedAt` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          @NonNull final WaterIntake entity) {
        statement.bindString(1, entity.getId());
        statement.bindString(2, entity.getUserId());
        statement.bindLong(3, entity.getAmountMl());
        if (entity.getNotes() == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.getNotes());
        }
        final Long _tmp = __converters.dateToTimestamp(entity.getDate());
        if (_tmp == null) {
          statement.bindNull(5);
        } else {
          statement.bindLong(5, _tmp);
        }
        final Long _tmp_1 = __converters.dateToTimestamp(entity.getCreatedAt());
        if (_tmp_1 == null) {
          statement.bindNull(6);
        } else {
          statement.bindLong(6, _tmp_1);
        }
        final Long _tmp_2 = __converters.dateToTimestamp(entity.getUpdatedAt());
        if (_tmp_2 == null) {
          statement.bindNull(7);
        } else {
          statement.bindLong(7, _tmp_2);
        }
        statement.bindString(8, entity.getId());
      }
    };
    this.__preparedStmtOfDeleteWaterIntakeById = new SharedSQLiteStatement(__db) {
      @Override
      @NonNull
      public String createQuery() {
        final String _query = "DELETE FROM water_intake WHERE id = ?";
        return _query;
      }
    };
  }

  @Override
  public Object insertWaterIntake(final WaterIntake waterIntake,
      final Continuation<? super Long> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Long>() {
      @Override
      @NonNull
      public Long call() throws Exception {
        __db.beginTransaction();
        try {
          final Long _result = __insertionAdapterOfWaterIntake.insertAndReturnId(waterIntake);
          __db.setTransactionSuccessful();
          return _result;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWaterIntake(final WaterIntake waterIntake,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfWaterIntake.handle(waterIntake);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object updateWaterIntake(final WaterIntake waterIntake,
      final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __updateAdapterOfWaterIntake.handle(waterIntake);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, $completion);
  }

  @Override
  public Object deleteWaterIntakeById(final long id, final Continuation<? super Unit> $completion) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      @NonNull
      public Unit call() throws Exception {
        final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteWaterIntakeById.acquire();
        int _argIndex = 1;
        _stmt.bindLong(_argIndex, id);
        try {
          __db.beginTransaction();
          try {
            _stmt.executeUpdateDelete();
            __db.setTransactionSuccessful();
            return Unit.INSTANCE;
          } finally {
            __db.endTransaction();
          }
        } finally {
          __preparedStmtOfDeleteWaterIntakeById.release(_stmt);
        }
      }
    }, $completion);
  }

  @Override
  public LiveData<List<WaterIntake>> getWaterIntakeByUserId(final String userId) {
    final String _sql = "SELECT * FROM water_intake WHERE userId = ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    return __db.getInvalidationTracker().createLiveData(new String[] {"water_intake"}, false, new Callable<List<WaterIntake>>() {
      @Override
      @Nullable
      public List<WaterIntake> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<WaterIntake>> getWaterIntakeByDateRange(final String userId,
      final Date startDate, final Date endDate) {
    final String _sql = "SELECT * FROM water_intake WHERE userId = ? AND date >= ? AND date <= ? ORDER BY date DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    return __db.getInvalidationTracker().createLiveData(new String[] {"water_intake"}, false, new Callable<List<WaterIntake>>() {
      @Override
      @Nullable
      public List<WaterIntake> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public Object getWaterIntakeById(final long id,
      final Continuation<? super WaterIntake> $completion) {
    final String _sql = "SELECT * FROM water_intake WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<WaterIntake>() {
      @Override
      @Nullable
      public WaterIntake call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @Override
  public Object getTotalWaterIntakeByDateRange(final String userId, final Date startDate,
      final Date endDate, final Continuation<? super Integer> $completion) {
    final String _sql = "SELECT SUM(amount) FROM water_intake WHERE userId = ? AND date >= ? AND date <= ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 3);
    int _argIndex = 1;
    _statement.bindString(_argIndex, userId);
    _argIndex = 2;
    final Long _tmp = __converters.dateToTimestamp(startDate);
    if (_tmp == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp);
    }
    _argIndex = 3;
    final Long _tmp_1 = __converters.dateToTimestamp(endDate);
    if (_tmp_1 == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindLong(_argIndex, _tmp_1);
    }
    final CancellationSignal _cancellationSignal = DBUtil.createCancellationSignal();
    return CoroutinesRoom.execute(__db, false, _cancellationSignal, new Callable<Integer>() {
      @Override
      @Nullable
      public Integer call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          return _result;
        } finally {
          _cursor.close();
          _statement.release();
        }
      }
    }, $completion);
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
