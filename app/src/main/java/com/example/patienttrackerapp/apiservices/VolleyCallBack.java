package com.example.patienttrackerapp.apiservices;

import com.example.patienttrackerapp.results.Result;

public interface VolleyCallBack {
    void onSuccess(Result result);
    void onError(Result result);
}
