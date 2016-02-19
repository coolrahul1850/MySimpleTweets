package com.codepath.apps.mysimpletweets;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class    TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;

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

        //Get the client
        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeLine();
    }

    public void customLoadMoreDataFromApi (int offset)
    {
       // populateTimeLine();

    }

    //send an API request to get the timeline json
    // Fill the listview by creating the tweet objects from the json

    private void populateTimeLine()
    {
        client.getHomeTimeline(new JsonHttpResponseHandler(){
            //Sucess

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
               //Log.d("Debug Success", json.toString());
                //JSON here
                //Deserialize JSON
                //Create Models and add them to the adapter
                //Load the model data into listview
               aTweets.addAll(Tweet.fromJSONArray(json));
            }


            //Failure


            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }
        });
    }
}
