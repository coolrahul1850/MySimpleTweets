package com.codepath.apps.mysimpletweets;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
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



	public void getHomeTimeline (AsyncHttpResponseHandler handler)
	{
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		//Specify the params
		RequestParams params = new RequestParams();
		params.put("count", 2);
		params.put("since_id",1);
		//Execute the request
		getClient().get(apiUrl,params,handler);
	}


	//Compose Tweet



	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}