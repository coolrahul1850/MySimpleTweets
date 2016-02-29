package com.codepath.apps.mysimpletweets.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.adapters.FollowersArrayAdapter;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FollowingActivity extends AppCompatActivity {

   // Toolbar followingToolbar;
    public String screenName;
    TwitterClient client;
    private ArrayList<User> arrayUsers;
    private FollowersArrayAdapter adapterUsers;

    @Bind(R.id.toolbar) Toolbar followingToolbar;
    @Bind(R.id.lvfollowingListView) ListView lvUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);
        ButterKnife.bind(this);
        screenName = getIntent().getStringExtra("screenName");
        client = TwitterApplication.getRestClient();
        arrayUsers = new ArrayList<>();
        adapterUsers = new FollowersArrayAdapter(this,arrayUsers);

        Log.d(screenName, "ScreenName");


    //    ListView lvUsers = (ListView) findViewById(R.id.lvfollowingListView);
        lvUsers.setAdapter(adapterUsers);

       populateFollowing(screenName);

     //   followingToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(followingToolbar);
        getSupportActionBar().setTitle("@" + screenName + " Following list");

    }

    public void populateFollowing (String screenName)
    {
        //get list of followers
        client.getFollowersList(screenName, new JsonHttpResponseHandler() {
            User user;
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray json = new JSONArray();
                try {
                    json = response.getJSONArray("users");
                    for (int i = 0; i < json.length(); i++) {
                        user = User.fromJSON(json.getJSONObject(i));
                        arrayUsers.add(user);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapterUsers.notifyDataSetChanged();
            }
        });
    }

}
