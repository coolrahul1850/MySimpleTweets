package com.codepath.apps.mysimpletweets.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by rmukhedkar on 2/28/16.
 */
public class FollowersArrayAdapter extends ArrayAdapter<User> {

    public FollowersArrayAdapter (Context context, List<User> arrayUsers)
    {
        super(context,android.R.layout.simple_list_item_1,arrayUsers);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //1. Get the tweet
        User user = getItem(position);
        //2. find or inflate the template
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_followers, parent, false);
        }
        ImageView ivProfileImage = (ImageView) convertView.findViewById(R.id.ivFollowersImageView);
        TextView tvUserName = (TextView) convertView.findViewById(R.id.lvFollowersScreenName);
        TextView tvScreenName = (TextView) convertView.findViewById(R.id.lvFollowersUserName);

        tvUserName.setText("@" + user.getScreenName());
        tvScreenName.setText(user.getName());
        ivProfileImage.setImageResource(android.R.color.transparent);

        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivProfileImage);

        return convertView;


    }

}
