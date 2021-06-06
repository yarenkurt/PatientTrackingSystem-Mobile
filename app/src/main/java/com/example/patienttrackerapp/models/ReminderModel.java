package com.example.patienttrackerapp.models;

public class ReminderModel {
   public int id;
   public String title;
   public String startedDate;
   public String startedTime;
   public boolean repeat;
   public int interval;
   public String nextStartedDate;

    public ReminderModel(int id, String title, String startedDate, String startedTime, boolean repeat, int interval, String nextStartedDate) {
        this.id = id;
        this.title = title;
        this.startedDate = startedDate;
        this.startedTime = startedTime;
        this.repeat = repeat;
        this.interval = interval;
        this.nextStartedDate = nextStartedDate;
    }


}
