package com.example.patienttrackerapp.db.options;

import android.provider.BaseColumns;

public final class ReminderOptions implements BaseColumns {
    public static final String TABLE_NAME = "Reminders";
    public static final String COLUMN_TITLE = "Title";
    public static final String COLUMN_STARTED_DATE = "StartedDate";
    public static final String COLUMN_STARTED_TIME = "StartedTime";
    public static final String COLUMN_IS_REPEAT= "IsRepeat";
    public static final String COLUMN_REPEAT_INTERVAL= "Interval";
    public static final String COLUMN_NEXT_STARTED_DATE= "NextStartedDate";

    public static final String DROP_SQL ="DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TITLE + " TEXT NOT NULL, " +
                    COLUMN_STARTED_DATE + " TEXT NOT NULL, " +
                    COLUMN_STARTED_TIME + " TEXT NOT NULL, " +
                    COLUMN_IS_REPEAT + " TEXT NOT NULL, " +
                    COLUMN_REPEAT_INTERVAL + " INTEGER NOT NULL, " +
                    COLUMN_NEXT_STARTED_DATE + " TEXT NOT NULL " +
                    "); ";
}
