package com.example.patienttrackerapp.apiservices;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.patienttrackerapp.db.AccountDbManager;
import com.example.patienttrackerapp.helpers.Helper;
import com.example.patienttrackerapp.models.AccountModel;
import com.example.patienttrackerapp.models.Defaults;
import com.example.patienttrackerapp.models.TokenInfo;
import com.example.patienttrackerapp.results.ErrorResult;
import com.example.patienttrackerapp.results.Result;
import com.example.patienttrackerapp.results.SuccessResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthenticationService {
    private static final String BASE_URL = Defaults.BASE_URL + "/api/Authentication";
    private final Context _context;
    private final AccountDbManager _dbManager;

    public AuthenticationService(Context context) {
        _context = context;
        _dbManager = new AccountDbManager(_context);
    }

    public void Login(JSONObject object, final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL + "/login", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (Helper.stringToBoolean(response.getString("success"))) {
                                JSONObject obj = new JSONObject(response.getString("data"));
                                String tokenExpiration=obj.getString("tokenExpiration");
                                String refreshTokenExpiration=obj.getString("refreshTokenExpiration");

                                TokenInfo tokenInfo = new TokenInfo(
                                        obj.getString("token"),
                                        obj.getString("refreshToken"),
                                        Helper.jsonStringToDate(obj.getString("tokenExpiration")),
                                        Helper.jsonStringToDate(obj.getString("refreshTokenExpiration")));
                                GetUserInfo(tokenInfo);
                                callBack.onSuccess(new SuccessResult());
                            } else {
                                callBack.onError(new ErrorResult(response.getString("message")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onError(new ErrorResult(e.getMessage()));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                callBack.onError(new ErrorResult("Service unavailable"));
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void RefreshLogin(JSONObject object, final VolleyCallBack callBack) {
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, BASE_URL + "/RefreshLogin", object,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (Helper.stringToBoolean(response.getString("success"))) {
                                JSONObject obj = new JSONObject(response.getString("data"));

                                TokenInfo tokenInfo = new TokenInfo(
                                        obj.getString("token"),
                                        obj.getString("refreshToken"),
                                        Helper.jsonStringToDate(obj.getString("tokenExpiration")),
                                        Helper.jsonStringToDate(obj.getString("refreshTokenExpiration")));
                                GetUserInfo(tokenInfo);
                                callBack.onSuccess(new SuccessResult());
                            } else {
                                callBack.onError(new ErrorResult(response.getString("message")));
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            callBack.onError(new ErrorResult(e.getMessage()));
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
                callBack.onError(new ErrorResult("Service unavailable"));
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void GetUserInfo(TokenInfo tokenInfo) {
        RequestQueue requestQueue = Volley.newRequestQueue(_context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, BASE_URL + "/UserInfo", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String id = response.getString("id");
                            String fullName = response.getString("fullName");
                            String personType = response.getString("personType");
                            Result result = _dbManager.addOrUpdate(new AccountModel(
                                    Helper.stringToInt(id),
                                    fullName,
                                    Helper.stringToInt(personType),
                                    tokenInfo.token,
                                    tokenInfo.refreshToken,
                                   tokenInfo.tokenExpired,
                                   tokenInfo.refreshTokenExpired
                            ));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + tokenInfo.token);
                return headers;
            }
        };
        requestQueue.add(jsonObjectRequest);
    }


}
