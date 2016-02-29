package com.codepath.apps.mysimpletweets.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
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

public class FollowersActivity extends AppCompatActivity {

    //Toolbar followersToolbar;
    public String screenName;
    TwitterClient client;
    private ArrayList<User> arrayUsers;
    private FollowersArrayAdapter adapterUsers;
    @Bind (R.id.lvfollowersListView) ListView lvUsers;
    @Bind (R.id.toolbar) Toolbar followersToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_followers);
        ButterKnife.bind(this);
        screenName = getIntent().getStringExtra("screenName");

        client = TwitterApplication.getRestClient();
        arrayUsers = new ArrayList<>();
        adapterUsers = new FollowersArrayAdapter(this,arrayUsers);

       // ListView lvUsers = (ListView) findViewById(R.id.lvfollowersListView);

        lvUsers.setAdapter(adapterUsers);

        populateFollowers(screenName);

        //followersToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(followersToolbar);
        getSupportActionBar().setTitle("@" + screenName + " Followers list");


    }

    public void populateFollowers(String screenName)
    {
        //get list of following
        client.getFollowingList(screenName, new JsonHttpResponseHandler() {
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_componse, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
