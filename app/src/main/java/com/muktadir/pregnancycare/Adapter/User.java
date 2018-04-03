package com.muktadir.pregnancycare.Adapter;

public class User {

    public String name;
    public String periodDay;
    public String periodMonth;
    public String periodYear;
    public String birthDay;
    public String birthMonth;
    public String birthYear;
    public String finalAge;

    public User(String name, String periodDay, String periodMonth, String periodYear, String birthDay, String birthMonth, String birthYear, String finalAge) {
        this.name = name;
        this.periodDay = periodDay;
        this.periodMonth = periodMonth;
        this.periodYear = periodYear;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        this.finalAge = finalAge;
    }
}
