package com.codepath.apps.mysimpletweets.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


//Parse the JSON + Store the data, encapsultate state logic or display logic
public class Tweet {
    public void setBody(String body) {
        this.body = body;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    //list out the attributes
    private String body;
    private long uid;   //unique id for the tweet
    private User user; //store embeded a user object
    private String createdAt;

    //the max since id
    public static long since_id = 0L;

    //Desearilize the JSON and build Tweet Objects

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public long getSince_id() {
        return since_id;
    }

    public String getCreatedAt() {
        return createdAt;
    }


    public User getUser() {
        return user;
    }
    // Tweet.fromJSON("{....}") ==> Tweet

    public static Tweet fromJSON(JSONObject jsonObject) {
        Tweet tweet = new Tweet();
        //Extract the values from JSON, store them
        try {

            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
            if (since_id > tweet.uid) {
                since_id = tweet.uid;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        //Return the tweet object

        return tweet;
    }

    //Tweet.fromJSONArray ==> List<Tweet>
    public static ArrayList<Tweet> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Tweet> tweets = new ArrayList<>();
        //Iterate the JSON array
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (i == 0) {
                    since_id = tweet.uid;
                }
                if (tweet != null) {
                    tweets.add(tweet);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }

        }
        return tweets;
    }


}
