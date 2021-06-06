package com.example.patienttrackerapp.models;

import java.util.ArrayList;

public class PatientInfo {
    public static Integer id;
    public static String firstName;
    public static String lastName;
    public static String identityNumber;
    public static String email;
    public static String gsm;
    public static Integer age;
    public static Double weight;
    public static Integer height;
    public static Double healthScore;
    public static ArrayList<DiseaseInfo> patientDiseases = new ArrayList<>();
    public static ArrayList<MultipleChoiceQuestionInfo> mcQuestions = new ArrayList<>();
    public static ArrayList<NumericQuestion> numericQuestions = new ArrayList<>();
    public static ArrayList<Integer> departments = new ArrayList<>();
    public static ArrayList<AdviceInfo> adviceList = new ArrayList<>();

}
