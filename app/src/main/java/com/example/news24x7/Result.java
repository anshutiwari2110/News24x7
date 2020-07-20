package com.example.news24x7;

import com.google.gson.annotations.SerializedName;

import org.json.JSONObject;

import java.util.ArrayList;

public class Result {

    @SerializedName("status")
    public String responseStatus;
    public int totalResults;

    @SerializedName("articles")
    public ArrayList<Articles> articleList;

}
