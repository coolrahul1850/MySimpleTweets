package com.codepath.apps.mysimpletweets.activity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.TwitterApplication;
import com.codepath.apps.mysimpletweets.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComposeTweet extends AppCompatActivity {

    private TwitterClient client;
    public static String composeTweet;
    public static int remainingCounter;

    @Bind(R.id.composeText) EditText tvComposeTweet;
    @Bind(R.id.tvCharacterCounter) TextView tvCharacterCounter;
    @Bind(R.id.composetoolbar) Toolbar composetoolbar;
    @Bind(R.id.userProfilePicture) ImageView userProfilePicture;
    @Bind(R.id.userName) TextView userName;
    public String screenName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        ButterKnife.bind(this);
        tvComposeTweet.addTextChangedListener(mTextEditorWatcher);
        tvCharacterCounter.setText("140");

        client = TwitterApplication.getRestClient();
        setSupportActionBar(composetoolbar);

        userProfilePicture.setImageResource(android.R.color.transparent);
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("user_profile_image")).into(userProfilePicture);

        userName.setText(getIntent().getStringExtra("screenname"));
        userName.setTextSize(25L);

        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        userName.setTypeface(font);

        screenName = getIntent().getStringExtra("username");
        getSupportActionBar().setTitle("@" + screenName + " New Tweet");





    }


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            remainingCounter = 140 - Integer.valueOf(tvComposeTweet.length());
            tvCharacterCounter.setText(String.valueOf(remainingCounter));


        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_componse, menu);
        return true;

    }

    public void onClickComposeTweet(View view) {


        composeTweet = tvComposeTweet.getText().toString();
        client.composeNewTweet(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
            }

            //Failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Debug", errorResponse.toString());
            }
        });

        Intent data = new Intent();
        data.putExtra("body", tvComposeTweet.getText().toString());
        data.putExtra("code", 20);
        data.putExtra("username", userName.getText());
        data.putExtra("user_profile_picture", getIntent().getStringExtra("user_profile_image"));
        data.putExtra("screenname", screenName);
        setResult(RESULT_OK, data);
        finish();
    }

    public void onClickComposeCancel(View view) {
        finish();
    }
}
