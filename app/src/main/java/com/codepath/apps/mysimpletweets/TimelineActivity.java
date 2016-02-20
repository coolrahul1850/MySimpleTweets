package com.codepath.apps.mysimpletweets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

    private static long staticSinceId;
    private final int REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);



        lvTweets = (ListView) findViewById(R.id.lvTweets);
        //Create the arraylist
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(this, tweets);
        lvTweets.setAdapter(aTweets);
        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById (R.id.toolbar);
        setSupportActionBar(toolbar);
        //Get the client
        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeLine();
    }

    //Menu icons are inflated just as they were actionbar


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    public void customLoadMoreDataFromApi (int offset)
    {
       client.getScrollHomeTimeline(new JsonHttpResponseHandler(){
           @Override
           public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
               aTweets.addAll(Tweet.fromJSONArray(json));
           }
       });

    }

    //send an API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json

    private void populateTimeLine() {
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            //Success
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                aTweets.addAll(Tweet.fromJSONArray(json));
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }
        });
    }

    public void btnComposeTweet(MenuItem item) {
        Intent composeIntent = new Intent(TimelineActivity.this,ComposeTweet.class);
        startActivityForResult(composeIntent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 200) {

            String name = data.getExtras().getString("name");
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            Tweet recentTweet = new Tweet();
            populateTimeLine();
            aTweets.notifyDataSetChanged();


        }
    }
}
