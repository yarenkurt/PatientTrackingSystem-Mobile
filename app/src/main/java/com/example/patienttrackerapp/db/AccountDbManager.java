package com.example.patienttrackerapp.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.patienttrackerapp.apiservices.AuthenticationService;
import com.example.patienttrackerapp.apiservices.VolleyCallBack;
import com.example.patienttrackerapp.db.options.AccountOptions;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.AccountModel;
import com.example.patienttrackerapp.results.ErrorResult;
import com.example.patienttrackerapp.results.Result;
import com.example.patienttrackerapp.results.SuccessResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;


public class AccountDbManager extends DbManagerBase {

    //region Constructor
    private final SQLiteDatabase db;
    Context context;

    public AccountDbManager(@Nullable Context context) {
        super(context);
        this.context = context;
        db = getWritableDatabase();
    }
    //endregion


    //region methods

    public boolean isAuthenticated() {

        if (!any()) return false;

        AccountModel account = get();
        long now = new Date().getTime();
        long refreshTokenExpiration = account.refreshTokenExpiration.getTime();
        Date difference = new Date(refreshTokenExpiration - now);
        if (difference.getTime() <= 0) return false;
        long tokenExpiration = account.tokenExpiration.getTime();
        Date differenceToken = new Date(tokenExpiration - now);
        if (differenceToken.getTime() <= 0) {
            final boolean[] res = new boolean[1];
            AuthenticationService authenticationService = new AuthenticationService(context);
            JSONObject object = new JSONObject();
            try {
                object.put("refreshToken", account.refreshToken);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            authenticationService.RefreshLogin(object, new VolleyCallBack() {
                @Override
                public void onSuccess(Result result) {

                }

                @Override
                public void onError(Result result) {
                }
            });
        }
        return true;
    }


    public boolean any() {
        @SuppressLint("Recycle") Cursor cursor = db.query(AccountOptions.TABLE_NAME,
                null,
                AccountOptions._ID + " = 1",
                null,
                null,
                null,
                null);
        return cursor.getCount() > 0;
    }


    public Result addOrUpdate(AccountModel account) {

        ContentValues cv = new ContentValues();

        cv.put(AccountOptions.COLUMN_ACCOUNT_ID, account.personId);
        cv.put(AccountOptions.COLUMN_FULL_NAME, account.fullName);
        cv.put(AccountOptions.COLUMN_PERSON_TYPE, account.personType);
        cv.put(AccountOptions.COLUMN_TOKEN, account.token);
        cv.put(AccountOptions.COLUMN_REFRESH_TOKEN, account.refreshToken);
        cv.put(AccountOptions.COLUMN_TOKEN_EXPIRED, Helper.dateTimeToString(account.tokenExpiration));
        cv.put(AccountOptions.COLUMN_REFRESH_TOKEN_EXPIRED, Helper.dateTimeToString(account.refreshTokenExpiration));

        if (any()) {
            try {
                db.update(AccountOptions.TABLE_NAME, cv, AccountOptions._ID + " = 1", null);
                return new SuccessResult("updating table is successfully");
            } catch (Exception e) {
                return new ErrorResult(e.getMessage());
            }
        } else {
            try {
                cv.put(AccountOptions._ID, 1);
                db.insert(AccountOptions.TABLE_NAME, null, cv);
                return new SuccessResult("inserting table is successfully");
            } catch (Exception e) {
                return new ErrorResult(e.getMessage());
            }
        }

    }

    public AccountModel get() {
        Cursor cursor = db.query(AccountOptions.TABLE_NAME,
                null,
                AccountOptions._ID + " = 1",
                null,
                null,
                null,
                null);
        while (cursor.moveToNext()) {
            Date tokenExpired = Helper.stringToDateTime(cursor.getString(cursor.getColumnIndex(AccountOptions.COLUMN_TOKEN_EXPIRED)));
            Date refreshTokenExpired = Helper.stringToDateTime(cursor.getString(cursor.getColumnIndex(AccountOptions.COLUMN_REFRESH_TOKEN_EXPIRED)));
            AccountModel account = new AccountModel(
                    Helper.stringToInt(cursor.getString(cursor.getColumnIndex(AccountOptions.COLUMN_ACCOUNT_ID))),
                    cursor.getString(cursor.getColumnIndex(AccountOptions.COLUMN_FULL_NAME)),
                    Helper.stringToInt(cursor.getString(cursor.getColumnIndex(AccountOptions.COLUMN_PERSON_TYPE))),
                    cursor.getString(cursor.getColumnIndex(AccountOptions.COLUMN_TOKEN)),
                    cursor.getString(cursor.getColumnIndex(AccountOptions.COLUMN_REFRESH_TOKEN)),
                    tokenExpired,
                    refreshTokenExpired
            );
            return account;
        }
        return null;
    }

    public String getToken() {
        Cursor cursor = db.query(AccountOptions.TABLE_NAME,
                null,
                AccountOptions._ID + " = 1",
                null,
                null,
                null,
                null);
        while (cursor.moveToNext())
            return cursor.getString(cursor.getColumnIndex(AccountOptions.COLUMN_TOKEN));
        return "";
    }
    //endregion

}
