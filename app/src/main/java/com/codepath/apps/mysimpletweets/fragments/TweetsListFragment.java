package com.codepath.apps.mysimpletweets.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.EndlessScrollListener;
import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.activity.ProfileActivity;
import com.codepath.apps.mysimpletweets.adapters.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;
import java.util.List;

public class TweetsListFragment extends Fragment {

    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter aTweets;
    private ListView lvTweets;
    private SwipeRefreshLayout swipeContainer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //View v = inflater.inflate(R.layout.fragment_tweets_list, parent,false);
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        //  ButterKnife.bind(getActivity());
        lvTweets = (ListView) v.findViewById(R.id.lvTweets);
        lvTweets.setAdapter(aTweets);
        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        // Swipe down to refresh
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchTimelineAsync(0, swipeContainer, aTweets);
            }
        });


        lvTweets.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                customLoadMoreDataFromApi(page);
                return true;
            }
        });



        lvTweets.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView<?> parent, View view, final int position, long id) {
                //Log.d("hey", lvTweets.getItemAtPosition(position).toString() + "");
                final Tweet tweet = (Tweet) lvTweets.getItemAtPosition(position);

                if (view == null) {
                    view = LayoutInflater.from(getContext()).inflate(R.layout.item_tweet, parent, false);
                }
                TextView screenName = (TextView) view.findViewById(R.id.tvScreenName);
                TextView tvUserName = (TextView) view.findViewById(R.id.tvUserName);
                TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
                TextView tvRelativeDate = (TextView) view.findViewById(R.id.tvRelativeDate);


                screenName.setClickable(false);
                tvBody.setClickable(false);
                tvUserName.setClickable(false);
                tvRelativeDate.setClickable(false);
                ImageView ivProfileImage = (ImageView) view.findViewById(R.id.ivProfileImage);
                ivProfileImage.setClickable(true);
                ivProfileImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        fetchUserProfile(tweet.getUser().getScreenName());
                    }
                });

            }
        });


        return v;
    }

    public void fetchUserProfile(String screenName)
    {
        Intent i = new Intent(getActivity(), ProfileActivity.class);
        i.putExtra("screenName", screenName);
        startActivityForResult(i,200);
    }


    public void fetchTimelineAsync(int page, SwipeRefreshLayout swipeContainer, TweetsArrayAdapter aTweets) {

    }

    public void customLoadMoreDataFromApi(int offset) {


    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tweets = new ArrayList<>();
        aTweets = new TweetsArrayAdapter(getActivity(), tweets);


    }

    public void addAll(List<Tweet> tweets) {
        aTweets.addAll(tweets);
    }

    public void addRecent (Tweet tweet)
    {
        aTweets.add(tweet);
    }


}
