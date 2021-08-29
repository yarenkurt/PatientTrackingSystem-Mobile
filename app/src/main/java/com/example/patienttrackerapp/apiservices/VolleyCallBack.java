package com.example.patienttrackerapp.apiservices;

import com.example.patienttrackerapp.results.Result;

public interface VolleyCallBack {
    //region Failure or Succes output from API
    void onSuccess(Result result);
    void onError(Result result);
    //endregion
}
