package net.simplifiedcoding.twitterloginsample;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.User;

import retrofit.http.POST;
import retrofit.http.Query;

public class TwitterFollow extends TwitterApiClient {
        public TwitterFollow(TwitterSession session) {
            super(session);
        }
        public FollowService getFollowService() {
            return getService(FollowService.class);
        }

        public interface FollowService {
            @POST("/1.1/friendships/create.json")
            public void create(@Query("screen_name") String screen_name, @Query("user_id") String user_id, @Query("follow") boolean follow, Callback<User> cb);
        }
}
