package com.codepath.apps.mysimpletweets;

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

import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ComposeTweet extends AppCompatActivity  {

    private TwitterClient client;
    public static String composeTweet;
    ImageView userProfilePicture;
    TextView userName;
  //  TextView tvCharacterCounter;
    public static int remainingCounter;

    @Bind (R.id.composeText) EditText tvComposeTweet;
    @Bind (R.id.tvCharacterCounter) TextView tvCharacterCounter;
    


 //   EditText tvComposeTweet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose_tweet);

        ButterKnife.bind(this);
    //    tvComposeTweet = (EditText)findViewById(R.id.composeText);
        tvComposeTweet.addTextChangedListener(mTextEditorWatcher);

     //   tvCharacterCounter = (TextView)findViewById(R.id.tvCharacterCounter);
        tvCharacterCounter.setText("140");

        client = TwitterApplication.getRestClient();
        Toolbar composetoolbar = (Toolbar) findViewById (R.id.composetoolbar);
        setSupportActionBar(composetoolbar);

        userProfilePicture = (ImageView) findViewById(R.id.userProfilePicture);
        userProfilePicture.setImageResource(android.R.color.transparent);
        Picasso.with(getApplicationContext()).load(getIntent().getStringExtra("user_profile_image")).into(userProfilePicture);

        userName = (TextView) findViewById(R.id.userName);
        userName.setText(getIntent().getStringExtra("username"));
        userName.setTextSize(25L);

        Typeface font = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Regular.ttf");
        userName.setTypeface(font);

    }


    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
           // Log.d("Hey","Hey");

            remainingCounter = 140 - Integer.valueOf(tvComposeTweet.length());
         //   tvCharacterCounter.setText(String.valueOf(tvComposeTweet.length()));
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
        data.putExtra("username",userName.getText());
        data.putExtra("user_profile_picture", getIntent().getStringExtra("user_profile_image"));
        setResult(RESULT_OK, data);
        finish();
    }

    public void onClickComposeCancel(View view) {
        finish();
    }
}
