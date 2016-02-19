package com.codepath.apps.mysimpletweets.models;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


//Parse the JSON + Store the data, encapsultate state logic or display logic
public class Tweet {

    //list out the attributes
    private String body;
    private long uid;   //unique id for the tweet
    private User user; //store embeded a user object
    private String createdAt;

    //Desearilize the JSON and build Tweet Objects

    public String getBody() {
        return body;
    }

    public long getUid() {
        return uid;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return user;
    }
    // Tweet.fromJSON("{....}") ==> Tweet

    public static Tweet fromJSON (JSONObject jsonObject)
    {
        Tweet tweet = new Tweet();
        //Extract the values from JSON, store them
        try {

            tweet.body = jsonObject.getString("text");
            tweet.uid = jsonObject.getLong("id");
            tweet.createdAt = jsonObject.getString("created_at");
            tweet.user = User.fromJSON(jsonObject.getJSONObject("user"));
        } catch (JSONException e) {
            e.printStackTrace();
        }



        //Return the tweet object

        return tweet;
    }

    //Tweet.fromJSONArray ==> List<Tweet>
    public static ArrayList<Tweet> fromJSONArray (JSONArray jsonArray)
    {
        ArrayList<Tweet> tweets = new ArrayList<>();
        //Iterate the JSON array
        for (int i=0;i<jsonArray.length();i++)
        {
            try {
                JSONObject tweetJson = jsonArray.getJSONObject(i);
                Tweet tweet = Tweet.fromJSON(tweetJson);
                if (tweet!=null)
                {
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
