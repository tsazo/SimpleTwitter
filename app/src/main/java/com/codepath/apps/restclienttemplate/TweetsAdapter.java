package com.codepath.apps.restclienttemplate;

import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.models.Media;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import org.json.JSONException;
import org.parceler.Parcels;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder>{

    Context context;
    List<Tweet> tweets;

    // Pass in the context and list of tweets
    public TweetsAdapter(Context context, List<Tweet> tweets) {
        this.context = context;
        this.tweets = tweets;
    }

    // For each row, inflate the layout
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view for the item — in this case a tweet
        View view = LayoutInflater.from(context).inflate(R.layout.item_tweet, parent, false);

        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the data at position
        Tweet tweet = tweets.get(position);

        // Bind the tweet with view holder
        holder.bind(tweet);
    }

    @Override
    public int getItemCount() {
        return tweets.size();
    }


    /* Within the RecyclerView.Adapter class to implement the pull-to-refresh action */
    // Clean all elements of the recycler
    public void clear() {
        tweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items -- change to type used
    public void addAll(List<Tweet> tweetList) {
        tweets.addAll(tweetList);
        notifyDataSetChanged();
    }

    // Define a ViewHolder
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imageViewProfileImage;
        ImageView imageViewMedia;
        TextView textViewBody;
        TextView textViewScreenName;
        TextView textViewName;
        TextView textViewRelativeTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewProfileImage = itemView.findViewById(R.id.imageViewProfileImage);
            textViewBody = itemView.findViewById(R.id.textViewBody);
            textViewScreenName = itemView.findViewById(R.id.textViewScreenName);
            textViewName = itemView.findViewById(R.id.textViewName);
            textViewRelativeTime = itemView.findViewById(R.id.textViewRelativeTime);
            imageViewMedia = itemView.findViewById(R.id.imageViewMedia);

            itemView.setOnClickListener(this);
        }

        // Take each attribute of the tweet and use those values to bind them to the screen
        public void bind(Tweet tweet) {
            textViewBody.setText(tweet.body);
            textViewScreenName.setText("@"+ tweet.user.screenName);
            textViewName.setText(tweet.user.name);
            textViewRelativeTime.setText(tweet.getRelativeTimeAgo());

            // Loading external image into imageView
            String imageURL = tweet.user.profileImageUrl;
            Glide.with(context).load(imageURL)
                    .fitCenter()
                    .circleCrop()
                    .into(imageViewProfileImage);

            if(tweet.media.size() > 0){
                imageViewMedia.setVisibility(View.VISIBLE);
                String embeddedImageURL = tweet.media.get(0).baseURL;
                Log.i("TweetsAdapter", "baseURl: " + embeddedImageURL);
                Glide.with(context).load(embeddedImageURL)
                        .fitCenter()
                        .transform(new RoundedCornersTransformation(10, 10))
                        .into(imageViewMedia);
            } else {
                imageViewMedia.setVisibility(View.GONE);
            }
        }

        // when user clicks on a row, show TweetDetailsActivity for the selected movie
        @Override
        public void onClick(View view) {
            // Gets the item position
            int position = getAdapterPosition();

            Log.i("TweetsAdapter", "Position of tweet: " + String.valueOf(position));

            // make sure the position is valid, i.e. exists in the view
            if (position != RecyclerView.NO_POSITION){

                // get the movie at the position, this won't work if the class is static
                Tweet tweet = tweets.get(position);

                // create intent for the new activity
                Intent intent = new Intent(context, TweetDetailsActivity.class);

                // serialize the movie using parceler, use its short name as a key
                // First string is the name, second string is the value
                intent.putExtra(Tweet.class.getSimpleName(), Parcels.wrap(tweet));

                // show the activity
                context.startActivity(intent);
            }
        }

    }
}
