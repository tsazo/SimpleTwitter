package com.codepath.apps.restclienttemplate;

import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetDetailsActivity extends AppCompatActivity {

    // the Tweet to display
    Tweet tweet;

    // the view objects
    ImageView imageViewProfileImage;
    //ImageView imageViewMedia;
    TextView textViewBody;
    TextView textViewScreenName;
    TextView textViewName;
    //TextView textViewRelativeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        // resolve the view objects
        imageViewProfileImage = (ImageView) findViewById(R.id.imageViewProfileImage);
        textViewBody = (TextView) findViewById(R.id.textViewBody);
        textViewScreenName = (TextView) findViewById(R.id.textViewScreenName);
        textViewName = (TextView) findViewById(R.id.textViewName);

        // Set the values to the instantiated views
        setValues();

        // On Click Listener for the image
        //videoListener();
    }

    // Sets the values of the Views in the display
    private void setValues(){

        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        Log.d("TweetDetailsActivity", String.format("Showing details for '%s'", tweet.body));


        // set the title and overview
        textViewBody.setText(tweet.body);
        textViewScreenName.setText(tweet.user.screenName);
        textViewName.setText(tweet.user.name);

        String imageURL = tweet.user.profileImageUrl;

        // Use Glide to have image load into activity
        Glide.with(this)
                .load(imageURL)
                .fitCenter() // retains size of images
                .circleCrop()
                .into(imageViewProfileImage);
    }

}
