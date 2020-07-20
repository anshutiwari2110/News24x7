package com.example.news24x7;

import org.json.JSONObject;

public class Articles {

    public String author;
    public String title;
    public String description;
    public String url;
    public String urlToImage;
    public String publishedAt;
    public String content;

    public Source source;

    public static Articles parseJSONObject(JSONObject jsonObject) {
        Articles item = new Articles();
        item.author = jsonObject.optString("author");
        item.description = jsonObject.optString("description");
        item.title = jsonObject.optString("title");
        item.url = jsonObject.optString("url");
        item.urlToImage = jsonObject.optString("urlToImage");
        item.publishedAt = jsonObject.optString("publishedAt");
        item.content = jsonObject.optString("content");

        JSONObject sourceObject = jsonObject.optJSONObject("source");
        item.source = Source.parseSourceJSON(sourceObject);
        return item;
    }

}