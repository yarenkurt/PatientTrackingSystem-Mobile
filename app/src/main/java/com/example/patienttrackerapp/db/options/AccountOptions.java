package com.example.patienttrackerapp.db.options;

import android.provider.BaseColumns;

public final class AccountOptions implements BaseColumns {
    public static final String TABLE_NAME = "Accounts";
    public static final String COLUMN_ACCOUNT_ID = "AccountId";
    public static final String COLUMN_FULL_NAME = "FullName";
    public static final String COLUMN_PERSON_TYPE = "PersonType";
    public static final String COLUMN_TOKEN = "Token";
    public static final String COLUMN_REFRESH_TOKEN = "RefreshToken";
    public static final String COLUMN_TOKEN_EXPIRED = "TokenExpiration";
    public static final String COLUMN_REFRESH_TOKEN_EXPIRED = "RefreshTokenExpiration";

    public static final String DROP_SQL ="DROP TABLE IF EXISTS " + TABLE_NAME;

    public static final String CREATE_SQL =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
                    " (" +
                    _ID + " INTEGER PRIMARY KEY, " +
                    COLUMN_ACCOUNT_ID + " INTEGER NOT NULL, " +
                    COLUMN_FULL_NAME + " TEXT NOT NULL, " +
                    COLUMN_PERSON_TYPE + " INTEGER NOT NULL, " +
                    COLUMN_TOKEN + " TEXT NOT NULL, " +
                    COLUMN_REFRESH_TOKEN + " TEXT NOT NULL, " +
                    COLUMN_TOKEN_EXPIRED + " TEXT NOT NULL, " +
                    COLUMN_REFRESH_TOKEN_EXPIRED + " TEXT NOT NULL" +
                    "); ";
}
