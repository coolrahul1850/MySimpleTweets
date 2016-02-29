package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.fragments.HomeTimelineFragment;
import com.codepath.apps.mysimpletweets.fragments.MentionsTimelineFragment;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.codepath.apps.mysimpletweets.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import butterknife.ButterKnife;

public class TimelineActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TwitterClient client;
    private ViewPager vpPager;
    public static int composeflag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        ButterKnife.bind(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        client = TwitterApplication.getRestClient();
        composeflag = 0;



        //toolbar
        setSupportActionBar(toolbar);
        // Get the viewpager
        vpPager = (ViewPager) findViewById(R.id.viewpager);
        // set the viewpager adapter for the pager
        vpPager.setAdapter(new TweetsPagerAdapter(getSupportFragmentManager()));
        // find the pager sliding tabs
        PagerSlidingTabStrip tabStrip = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        // Attach the tabstrip to the viewpager
        tabStrip.setViewPager(vpPager);


    }


    public void onProfileView(MenuItem mi) {
        client.getUserDetails(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                User u = User.fromJSON(response);
                Intent i = new Intent(TimelineActivity.this, ProfileActivity.class);
                i.putExtra("username", u.getScreenName());
                i.putExtra("user_profile_image", u.getProfileImageUrl());
                i.putExtra("screenName", u.getScreenName());
                startActivityForResult(i, 200);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {

            }
        });

    }

//Menu icons are inflated just as they were actionbar

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        return super.onOptionsItemSelected(item);
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
        //    Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
            Tweet recentTweet = new Tweet();
            User u = new User();
            String username = data.getExtras().getString("username");
            String screenName = data.getExtras().getString("screenname");
            u.setName(screenName);
            u.setScreenName(username);

            String userProfilePicture = data.getExtras().getString("user_profile_picture");
            u.setProfileImageUrl(userProfilePicture);
            String body = data.getExtras().getString("body");

            Log.d("Body", body);
            recentTweet.setUser(u);
            recentTweet.setBody(body);
            recentTweet.setUid(45L);
            composeflag = 1;




        }
    }

    //return the order of the fragments in the view pager
    public class TweetsPagerAdapter extends FragmentPagerAdapter {
        private String tabTitles[] = {"Home", "Mentions"};

        //Adapter gets the manager insert or remove fragment from activity
        public TweetsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //the order and creation of fragments withing the pager
        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new HomeTimelineFragment();
            } else if (position == 1) {
                return new MentionsTimelineFragment();
            } else {
                return null;
            }
        }

        //Return the tab title
        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        // How many fragments there are to swipe between
        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
