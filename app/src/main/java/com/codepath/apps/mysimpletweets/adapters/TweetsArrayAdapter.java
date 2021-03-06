package com.codepath.apps.mysimpletweets.adapters;


//Taking the Tweet objects and turing them into Views displayed in the list

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.ParseRelativeDate;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.codepath.apps.mysimpletweets.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TweetsArrayAdapter extends ArrayAdapter<Tweet> {

    private TwitterClient client;

    public TweetsArrayAdapter(Context context, List<Tweet> tweets) {
        super(context, android.R.layout.simple_list_item_1, tweets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //1. Get the tweet
        Tweet tweet = getItem(position);
        //2. find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
        }
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivProfileImage);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.tvUserName);
        TextView tvBody = (TextView) convertView.findViewById(R.id.tvBody);
        TextView tvRelativeDate = (TextView) convertView.findViewById(R.id.tvRelativeDate);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.tvScreenName);

        tvUserName.setText("@" + tweet.getUser().getScreenName());
        tvBody.setText(tweet.getBody());
        tvScreenName.setText("  " + tweet.getUser().getName());

        ParseRelativeDate tvDate = new ParseRelativeDate();
        tvRelativeDate.setText(tvDate.getRelativeTimeAgo(tweet.getCreatedAt()));

        ivProfileImage.setImageResource(android.R.color.transparent);

        Picasso.with(getContext()).load(tweet.getUser().getProfileImageUrl()).into(ivProfileImage);


        return convertView;


    }
}
