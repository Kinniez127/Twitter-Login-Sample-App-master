package net.simplifiedcoding.twitterloginsample;

import android.content.Intent;
import android.support.v4.content.IntentCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterAuthToken;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import com.twitter.sdk.android.core.models.User;

import io.fabric.sdk.android.DefaultLogger;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {



    //Twitter Login Button
    TwitterLoginButton twitterLoginButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializing TwitterAuthConfig, these two line will also added automatically while configuration we did
        TwitterAuthConfig authConfig = new TwitterAuthConfig(Constant.TWITTER_KEY, Constant.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig), new Crashlytics());

        setContentView(R.layout.activity_main);

        //Initializing twitter login button
        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitterLogin);

        //Adding callback to the button
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //If login succeeds passing the Calling the login method and passing Result object
                TwitterSession session = Twitter.getSessionManager()
                        .getActiveSession();
                TwitterAuthToken authToken = session.getAuthToken();
                String token = authToken.token;
                String secret = authToken.secret;
                login(result);
            }

            @Override
            public void failure(TwitterException exception) {
                //If failure occurs while login handle it here
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });

        final Fabric fabric = new Fabric.Builder(this)
                .kits(new Twitter(authConfig))
                .logger(new DefaultLogger(Log.DEBUG))
                .debuggable(true)
                .build();

        Fabric.with(fabric);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Adding the login result back to the button
        twitterLoginButton.onActivityResult(requestCode, resultCode, data);
    }

    //The login function accepting the result object
    public void login(Result<TwitterSession> result) {

        //Creating a twitter session with result's data
        TwitterSession session = result.data;

        //Getting the username from session
        final String username = session.getUserName();

        //This code will fetch the profile image URL
        //Getting the account service of the user logged in
        Twitter.getApiClient(session).getAccountService()
                .verifyCredentials(true, false, new Callback<User>() {
                    @Override
                    public void failure(TwitterException e) {
                        //If any error occurs handle it here
                    }

                    @Override
                    public void success(Result<User> userResult) {
                        //If it succeeds creating a User object from userResult.data
                        User user = userResult.data;
                        Constant.user = user;
                        //Getting the profile image url
                        String profileImage = user.profileImageUrl.replace("_normal", "");

                        //Creating an Intent
                        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);

                        //Adding the values to intent
                        intent.putExtra(Constant.KEY_USERNAME, username);
                        intent.putExtra(Constant.KEY_PROFILE_IMAGE_URL, profileImage);

                        //Clear activity stack
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                                | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);

                        //Starting intent
                        startActivity(intent);
                    }
                });
    }

}
