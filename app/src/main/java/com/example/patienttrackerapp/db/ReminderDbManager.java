package com.example.patienttrackerapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ArrayAdapter;

import androidx.annotation.Nullable;

import com.example.patienttrackerapp.db.options.AccountOptions;
import com.example.patienttrackerapp.db.options.ReminderOptions;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.ReminderModel;
import com.example.patienttrackerapp.results.ErrorResult;
import com.example.patienttrackerapp.results.Result;
import com.example.patienttrackerapp.results.SuccessResult;

import java.util.ArrayList;


public class ReminderDbManager extends DbManagerBase {

    //region Constructor
    private final SQLiteDatabase db;
    Context context;

    public ReminderDbManager(@Nullable Context context) {
        super(context);
        this.context = context;
        db = getWritableDatabase();
    }
    //endregion


    //region methods

    public ReminderModel get(int id) {

        Cursor cursor = db.query(ReminderOptions.TABLE_NAME,
                null,
                ReminderOptions._ID + " = " + id,
                null,
                null,
                null,
                null);
        ReminderModel result = null;
        while (cursor.moveToNext()) {

            result = new ReminderModel(
                    cursor.getInt(cursor.getColumnIndex(ReminderOptions._ID)),
                    cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_STARTED_DATE)),
                    cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_STARTED_TIME)),
                    Helper.stringToBoolean(cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_IS_REPEAT))),
                    cursor.getInt(cursor.getColumnIndex(ReminderOptions.COLUMN_REPEAT_INTERVAL)),
                    cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_NEXT_STARTED_DATE))
            );
        }
        return result;
    }

    public ArrayList<ReminderModel> getAll() {
        Cursor cursor = db.query(ReminderOptions.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                ReminderOptions._ID + " ASC");
        ArrayList<ReminderModel> result = new ArrayList<>();
        while (cursor.moveToNext()) {
            result.add(new ReminderModel(
                    cursor.getInt(cursor.getColumnIndex(ReminderOptions._ID)),
                    cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_TITLE)),
                    cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_STARTED_DATE)),
                    cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_STARTED_TIME)),
                    Helper.stringToBoolean(cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_IS_REPEAT))),
                    cursor.getInt(cursor.getColumnIndex(ReminderOptions.COLUMN_REPEAT_INTERVAL)),
                    cursor.getString(cursor.getColumnIndex(ReminderOptions.COLUMN_NEXT_STARTED_DATE))
            ));
        }

        return result;
    }

    public Result insert(ReminderModel reminder) {
        ContentValues cv = new ContentValues();
        cv.put(ReminderOptions.COLUMN_TITLE, reminder.title);
        cv.put(ReminderOptions.COLUMN_STARTED_DATE, reminder.startedDate);
        cv.put(ReminderOptions.COLUMN_STARTED_TIME, reminder.startedTime);
        cv.put(ReminderOptions.COLUMN_IS_REPEAT, reminder.repeat);
        cv.put(ReminderOptions.COLUMN_REPEAT_INTERVAL, reminder.interval);
        cv.put(ReminderOptions.COLUMN_NEXT_STARTED_DATE, reminder.nextStartedDate);
        try {
            db.insert(ReminderOptions.TABLE_NAME, null, cv);
            return new SuccessResult("inserting table is successfully");
        } catch (Exception e) {
            return new ErrorResult(e.getMessage());
        }

    }

    public Result update(ReminderModel reminder) {
        ContentValues cv = new ContentValues();
        cv.put(ReminderOptions.COLUMN_TITLE, reminder.title);
        cv.put(ReminderOptions.COLUMN_STARTED_DATE, reminder.startedDate);
        cv.put(ReminderOptions.COLUMN_STARTED_TIME, reminder.startedTime);
        cv.put(ReminderOptions.COLUMN_IS_REPEAT, reminder.repeat);
        cv.put(ReminderOptions.COLUMN_REPEAT_INTERVAL, reminder.interval);
        cv.put(ReminderOptions.COLUMN_NEXT_STARTED_DATE, reminder.nextStartedDate);
        try {
            db.update(ReminderOptions.TABLE_NAME, cv,
                    ReminderOptions._ID + "=?",
                    new String[]{String.valueOf(reminder.id)}
            );
            return new SuccessResult("updating table is successfully");
        } catch (Exception e) {
            return new ErrorResult(e.getMessage());
        }

    }

    public Result delete(int id) {

        try {
            db.delete(ReminderOptions.TABLE_NAME, ReminderOptions._ID + "=?", new String[]{String.valueOf(id)});
            return new SuccessResult("deleting table is successfully");
        } catch (Exception e) {
            return new ErrorResult(e.getMessage());
        }

    }

    //endregion

}
