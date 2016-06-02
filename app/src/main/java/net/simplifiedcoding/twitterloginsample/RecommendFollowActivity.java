package net.simplifiedcoding.twitterloginsample;

import android.app.ListActivity;
import android.widget.Toast;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.User;

/**
 * Created by asus-K555L on 2/6/2559.
 */
public class RecommendFollowActivity extends ListActivity {

    /*TwitterFollow apiClient = new TwitterFollow(session);
    apiClient.getFollowService().create("Screen_Name_of_person_to_follow", null, true, new Callback<User>() {
        @Override
        public void success(Result<User> result) {
            Toast.makeText(RecommendFollowActivity.this, "Thanks for following!", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failure(TwitterException e) {
            Toast.makeText(MainActivity.this, "Error following", Toast.LENGTH_SHORT).show();
        }
    });*/

}
