package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.apps.mysimpletweets.activity.ComposeTweet;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;


public class TwitterClient extends OAuthBaseClient {
    public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class; // Change this
    public static final String REST_URL = "https://api.twitter.com/1.1/"; // Change this, base API URL
    public static final String REST_CONSUMER_KEY = "Dwept4sprCaIu6BV4O6mFOzb8";       // Change this
    public static final String REST_CONSUMER_SECRET = "8e3UXPgKhpEDWzvEqB4bAQWX2H7Pmlgmirt2tRIEliDpe0tprL"; // Change this
    public static final String REST_CALLBACK_URL = "oauth://cpimpletweets"; // Change this (here and in manifest)

    public TwitterClient(Context context) {
        super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
    }


    public void getHomeTimeline(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        //Specify the params
        RequestParams params = new RequestParams();
        params.put("count", 20);
        params.put("since_id", 1);
        getClient().get(apiUrl, params, handler);
    }

    public void getScrollHomeTimeline(Long since_id, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/home_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 20);
        //params.put("max_id",Tweet.since_id);
        params.put("max_id", since_id);
        getClient().get(apiUrl, params, handler);
    }


    public void getScrollUserHomeTimeline (long since_id, String screenName, AsyncHttpResponseHandler handler)
    {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count",20);
        params.put("screen_name", screenName);
        params.put("max_id", since_id);
        getClient().get(apiUrl, params, handler);

    }



    public void composeNewTweet(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", ComposeTweet.composeTweet);

        getClient().post(apiUrl, params, handler);
    }

    public void getUserDetails(AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("account/verify_credentials.json");
        getClient().get(apiUrl, handler);
    }


    public void getUserShow (String screenName, AsyncHttpResponseHandler handler)
    {
        String apiUrl = getApiUrl("users/show.json");
        RequestParams params = new RequestParams();
        params.put("screen_name",screenName);
        getClient().get(apiUrl,params,handler);
    }


    public void getMentionsTimeline(JsonHttpResponseHandler handler) {

        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        //Specify the params
        RequestParams params = new RequestParams();
        params.put("count", 20);
        //	params.put("since_id",1);
        getClient().get(apiUrl, params, handler);
    }

    public void getUserTimeline(String screenName, AsyncHttpResponseHandler handler) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");
        RequestParams params = new RequestParams();
        params.put("count", 20);
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);
    }


    public void getFollowingList (String screenName, AsyncHttpResponseHandler handler)
    {
        String apiUrl = getApiUrl("friends/list.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);

    }


    public void getFollowersList (String screenName, AsyncHttpResponseHandler handler)
    {
        String apiUrl = getApiUrl("followers/list.json");
        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);
        getClient().get(apiUrl, params, handler);

    }

}