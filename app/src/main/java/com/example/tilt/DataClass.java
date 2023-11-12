package com.example.tilt;

public class DataClass {

    private String dateTitle;
    private String dateDesc;
    private String dateLang;
    private String dateImage;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    private String key;

    public String getDateTitle() {
        return dateTitle;
    }

    public String getDateDesc() {
        return dateDesc;
    }

    public String getDateLang() {
        return dateLang;
    }

    public String getDateImage() {
        return dateImage;
    }


    public DataClass(String dateTitle, String dateDesc, String dateLang, String dateImage) {
        this.dateTitle = dateTitle;
        this.dateDesc = dateDesc;
        this.dateLang = dateLang;
        this.dateImage = dateImage;
    }

    public  DataClass(){

    }
}
