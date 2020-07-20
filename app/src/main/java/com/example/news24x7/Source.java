package com.example.news24x7;

import org.json.JSONObject;

public class Source {

    public String id;
    public String name;

    public static Source parseSourceJSON(JSONObject jsonObject){
        Source item = new Source();
        item.id = jsonObject.optString("id");
        item.name = jsonObject.optString("name");
        return item;
    }
}

