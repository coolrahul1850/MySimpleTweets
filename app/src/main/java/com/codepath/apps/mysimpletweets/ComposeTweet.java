package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

public class ComposeTweet extends AppCompatActivity {

    private TwitterClient client;
    public static String composeTweet;

    EditText tvComposeTweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);
        tvComposeTweet = (EditText)findViewById(R.id.composeText);

        client = TwitterApplication.getRestClient();
    }

    public void onClickComposeTweet(View view) {
        composeTweet = tvComposeTweet.getText().toString();
        client.composeNewTweet(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
            }
            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }
        });

        Intent data = new Intent();
        data.putExtra("name", tvComposeTweet.getText().toString());
        data.putExtra("code", 20);
        setResult(RESULT_OK, data);
        finish();
    }
}
