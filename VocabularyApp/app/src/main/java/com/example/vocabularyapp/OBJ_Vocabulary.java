package com.example.vocabularyapp;

public class OBJ_Vocabulary{
    private String eng, vietnamese, negative;
    private String spelling = "None";

    public OBJ_Vocabulary(String eng, String vietnamese, String negative) {
        this.eng = eng;
        this.vietnamese = vietnamese;
        this.negative = negative;
    }

    public String getEng() {
        return eng;
    }

    public String getVietnamese() {
        return vietnamese;
    }

    public String getNegative() {
        return negative;
    }

    public void setSpelling(String test){
        this.spelling = test;
    }

    public String getSpelling(){
        return spelling;
    }
}
