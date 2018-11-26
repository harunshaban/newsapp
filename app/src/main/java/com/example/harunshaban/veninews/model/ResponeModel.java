package com.example.harunshaban.veninews.model;

import com.example.harunshaban.veninews.model.Article;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponeModel {

    @SerializedName("status")
    private String status;
    @SerializedName("totalResult")
    private int totalResult;
    @SerializedName("articles")
    private List<Article> articles = null;

    public String getStatus() {
        return status;
    }

    public int getTotalResult() {
        return totalResult;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setTotalResult(int totalResult) {
        this.totalResult = totalResult;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
