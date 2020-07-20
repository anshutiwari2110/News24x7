package com.example.news24x7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements NewsCategoryAdapter.NewsCategoryClickListener, AdapterView.OnItemSelectedListener {

    private RecyclerView mRcNews;
    private ProgressDialog progressDialog;
    private EditText mEtSearchNews;
    private NewsAdapter adapter;
    private Spinner mSpinCountryId;
    private TextView mTvNewsMarquee;
    private String countryId;
    TextView mTvCountryName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView mRcNewsCategory = findViewById(R.id.rc_news_category);
        mRcNews = findViewById(R.id.rc_news);

        mSpinCountryId = findViewById(R.id.spin_country);
        mTvCountryName = findViewById(R.id.tv_country_name);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(this, R.array.country_code, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinCountryId.setAdapter(arrayAdapter);
        mSpinCountryId.setOnItemSelectedListener(this);

        mRcNews.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL, false));
        mRcNewsCategory.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false));
        NewsCategoryAdapter newsCategoryAdapter = new NewsCategoryAdapter(MainActivity.this);
        newsCategoryAdapter.setListener(this);
        mRcNewsCategory.setAdapter(newsCategoryAdapter);

        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Getting news");

        final ImageView mIvClearSearch = findViewById(R.id.iv_clear_search);
        mEtSearchNews = findViewById(R.id.et_search);

        mEtSearchNews.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    mIvClearSearch.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(s);
                } else {
                    mIvClearSearch.setVisibility(View.INVISIBLE);
                }
            }
        });

        mIvClearSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEtSearchNews.setText("");
                adapter.clearSearch();
                mIvClearSearch.setVisibility(View.GONE);
            }
        });

        getNews("");
    }

    private void getNews(String category) {
        progressDialog.show();
        API_Interface api_interface = APIClient.getClient().create(API_Interface.class);

        Map <String, Object> parameters = new HashMap<>();

        parameters.put("apiKey" , "982679c1932a4c71a109373918d8efa4");
        parameters.put("category" , category);
        parameters.put("sources","google-news");

        Call<Result> getCategoryNews = api_interface.getNews(parameters);
        getCategoryNews.enqueue(new Callback<Result>() {
            @Override
            public void onResponse(Call<Result> call, Response<Result> response) {
                Result responseValue = response.body();
                ArrayList<Articles> newsArticles = responseValue.articleList;
                adapter = new NewsAdapter(MainActivity.this, newsArticles);
                mRcNews.setAdapter(adapter);
                progressDialog.hide();
            }

            @Override
            public void onFailure(Call<Result> call, Throwable t) {
                progressDialog.hide();

            }
        });
    }

    @Override
    public void onNewsCategoryClicked(String category) {
        getNews(category);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        countryId = parent.getItemAtPosition(position).toString();

        switch (countryId) {
            case "in":
                mTvCountryName.setText("India");
                break;
            case "us" :
                mTvCountryName.setText("United State of America");
                break;
            case "ru":
                mTvCountryName.setText("Russia");
                break;
            case "ch":
                mTvCountryName.setText("China");
                break;
            case "ca":
                mTvCountryName.setText("Canada");
                break;
            case "au":
                mTvCountryName.setText("Australia");
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}