package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private static long staticSinceId;
    private final int REQUEST_CODE = 200;

    @Bind(R.id.swipeContainer) SwipeRefreshLayout swipeContainer;
    @Bind(R.id.lvTweets) ListView lvTweets;
    @Bind(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        //Swipe down to refresh
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0);
            }
        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

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
        setSupportActionBar(toolbar);
        //Get the client
        client = TwitterApplication.getRestClient(); //singleton client
        populateTimeLine();
    }

    public void fetchTimelineAsync (int page)
    {
        aTweets.clear();
        populateTimeLine();
        swipeContainer.setRefreshing(false);
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
        client.getUserDetails(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User u = User.fromJSON(response);
                Intent i = new Intent(TimelineActivity.this, ComposeTweet.class);
                i.putExtra("username", u.getScreenName());
                i.putExtra("user_profile_image", u.getProfileImageUrl());
                i.putExtra("screenname", u.getName());
                startActivityForResult(i, 200);

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == 200) {

            String name = data.getExtras().getString("name");
            Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            Tweet recentTweet = new Tweet();
            User u = new User();
            String username = data.getExtras().getString("username");
            String screenName = data.getExtras().getString("screenname");
            u.setName(screenName);
            u.setScreenName(username);


            String userProfilePicture = data.getExtras().getString("user_profile_picture");
            u.setProfileImageUrl(userProfilePicture);
            String body = data.getExtras().getString("body");

            recentTweet.setUser(u);
            recentTweet.setBody(body);
            recentTweet.setUid(45L);

            recentTweet.setCreatedAt(null);
            recentTweet.setCreatedAt(null);
            aTweets.clear();
            aTweets.add(recentTweet);
            populateTimeLine();
        }
    }
}
