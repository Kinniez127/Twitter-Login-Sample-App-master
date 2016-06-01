package net.simplifiedcoding.twitterloginsample;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TweetViewAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;
import com.twitter.sdk.android.tweetui.UserTimeline;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ProfileActivity extends ListActivity{

    int tweetCount = Constant.user.statusesCount;

    //Image Loader object
    private ImageLoader imageLoader;

    //NetworkImageView Ojbect
    private NetworkImageView profileImage;

    //TextView object
    private TextView textViewUsername;
    private TextView textViewUserId;

    private TweetViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Getting the intent
        final Intent intent = getIntent();

        Button ShowTimeline = (Button) findViewById(R.id.showUserTimeline);

        //Getting values from intent
        String username = intent.getStringExtra(Constant.KEY_USERNAME);
        String profileImageUrl = intent.getStringExtra(Constant.KEY_PROFILE_IMAGE_URL);

        //Initializing views
        profileImage = (NetworkImageView) findViewById(R.id.profileImage);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);

        //Loading image
        imageLoader = CustomVolleyRequest.getInstance(this).getImageLoader();
        imageLoader.get(profileImageUrl, ImageLoader.getImageListener(profileImage, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));
        profileImage.setImageUrl(profileImageUrl, imageLoader);

        //Setting the username in textview
        textViewUsername.setText("@" + username);

        ShowTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent TimelimeIntent = new Intent(ProfileActivity.this,TimelineActivity.class);
                startActivity(TimelimeIntent);
            }

        });

        adapter = new TweetViewAdapter(this);
        setListAdapter(adapter);
        final SwipeRefreshLayout swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
       /* final TwitterListTimeline timeline = new TwitterListTimeline.Builder()
                .id(Constant.user.id)
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(timeline)
                .build();
        setListAdapter(adapter);

        swipeLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeLayout.setRefreshing(true);
                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        swipeLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        // Toast or some other action
                    }
                });
            }
        });*/

        /*Timer T = new Timer();
        T.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshNotification();
                    }
                });
            }
        }, 1000, 20000);*/

        loadTweets();
    }

    public void refreshNotification(){
        setTweetCount();

    }

    public void setTweetCount(){
        Twitter.getApiClient().getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void failure(TwitterException e) {
                        //If any error occurs handle it here
                    }

                    @Override
                    public void success(Result<User> userResult) {
                        //If it succeeds creating a User object from userResult.data
                        User user = userResult.data;
                        int tweetCountRefresh = user.statusesCount;

                        if (tweetCountRefresh > tweetCount) {
                            Context context = getApplicationContext();
                            CharSequence text = "New Tweets!!!!!";
                            int duration = Toast.LENGTH_SHORT;


                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                            Constant.user = user;
                            tweetCount = Constant.user.statusesCount;
                        }
                    }
                });


    }

    public void loadTweets() {
        final StatusesService service = Twitter.getInstance().getApiClient().getStatusesService();

        service.homeTimeline(null, null, null, null, null, null, null, new Callback<List<Tweet>>() {
                    @Override
                    public void success(Result<List<Tweet>> result) {
                        adapter.setTweets(result.data);
                    }

                    @Override
                    public void failure(TwitterException error) {
                        Toast.makeText(ProfileActivity.this, "Failed to retrieve timeline",
                                Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

}


