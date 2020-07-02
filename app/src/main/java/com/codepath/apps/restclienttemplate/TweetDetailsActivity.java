package com.codepath.apps.restclienttemplate;

import android.content.Intent;
import android.graphics.Movie;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONException;
import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import okhttp3.Headers;

public class TweetDetailsActivity extends AppCompatActivity {

    public static final String TAG = "TweetDetailsActivity";

    // the Tweet to display
    Tweet tweet;

    TwitterClient client;

    // the view objects
    ImageView imageViewProfileImage;
    ImageView imageViewMedia;
    TextView textViewBody;
    TextView textViewScreenName;
    TextView textViewName;
    ImageView imageViewRetweet;
    String id;
    //TextView textViewRelativeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tweet_details);

        client = TwitterApplication.getRestClient(this);

        // resolve the view objects
        imageViewProfileImage = (ImageView) findViewById(R.id.imageViewProfileImage);
        textViewBody = (TextView) findViewById(R.id.textViewBody);
        textViewScreenName = (TextView) findViewById(R.id.textViewScreenName);
        textViewName = (TextView) findViewById(R.id.textViewName);
        imageViewMedia = (ImageView) findViewById(R.id.imageViewMedia);
        imageViewRetweet = (ImageView) findViewById(R.id.imageViewRetweet);

        // Set the values to the instantiated views
        setValues();

        // On Click Listener for the attached media
        //mediaListener();

        // Set click listener on retweet image
        Log.i(TAG, "right before onclick");
        imageViewRetweet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Make an API call to Twitter to publish the tweet
                Log.i(TAG, "onclick works!");
                client.retweet(id, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Headers headers, JSON json) {
                        Log.i(TAG, "onSuccess Retweet");
//                        try {
//                            Tweet tweet = Tweet.fromJson(json.jsonObject);
//                            Log.i(TAG, "Retweeted tweet says: " + tweet.body);
//
//                            // Prepare data intent
//                            Intent intent = new Intent();
//
//                            // Pass relevant data back as a result
//                            intent.putExtra("tweet", Parcels.wrap(tweet));
//
//                            // Activity finished ok, return the data
//                            // set result code and bundle data for response
//                            setResult(RESULT_OK, intent);
//
//                            // Closed the activity, passes data to parent
//                            finish();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                        Log.e(TAG, "onFailure to retweet", throwable);
                    }
                });
            }
        });
    }

    // Sets the values of the Views in the display
    private void setValues(){

        // unwrap the movie passed in via intent, using its simple name as a key
        tweet = (Tweet) Parcels.unwrap(getIntent().getParcelableExtra(Tweet.class.getSimpleName()));
        Log.d("TweetDetailsActivity", String.format("Showing details for '%s'", tweet.body));

        id = tweet.stringId;

        // set the title and overview
        textViewBody.setText(tweet.body);
        textViewScreenName.setText(tweet.user.screenName);
        textViewName.setText("@" + tweet.user.name);

        String imageURL = tweet.user.profileImageUrl;

        // Use Glide to have image load into activity
        Glide.with(this)
                .load(imageURL)
                .fitCenter() // retains size of images
                .circleCrop()
                .into(imageViewProfileImage);

        if(tweet.media.size() > 0){
            imageViewMedia.setVisibility(View.VISIBLE);
            String embeddedImageURL = tweet.media.get(0).baseURL;
            Log.i("TweetsAdapter", "baseURl: " + embeddedImageURL);
            Glide.with(this).load(embeddedImageURL)
                    .fitCenter()
                    .transform(new RoundedCornersTransformation(10, 10))
                    .into(imageViewMedia);
        } else {
            imageViewMedia.setVisibility(View.GONE);
        }
    }

    // OnClickListener to make media screen appear
//    private void mediaListener(){
//        imageViewMedia.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                 Create the activity
//                Intent intent = new Intent(TweetDetailsActivity.this, MovieTrailerActivity.class);
//
//                Log.d("MovieDetailsActivity", "Intent made!");
//
//                // Add link to the new activity
//                intent.putExtra("movieURL", LINK);
//
//                Log.d("MovieDetailsActivity", "putextra");
//
//                // Display the new activity
//                startActivity(intent);
//            }
//        });
//    }

}
