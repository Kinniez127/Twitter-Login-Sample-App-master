package net.simplifiedcoding.twitterloginsample;

import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.models.User;
import com.twitter.sdk.android.core.services.StatusesService;

public class Constant {
    //This is your KEY and SECRET
    //And it would be added automatically while the configuration
    public static final String TWITTER_KEY = "K0CEjDZo3MCTHmk7YovG5D2FW";
    public static final String TWITTER_SECRET = "m4aL23oDUQBLDnG4zlCPYHQzoIPSfJxx1pOHq5kFqERnW8WtRX";

    //Tags to send the username and image url to next activity using intent
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PROFILE_IMAGE_URL = "image_url";

    public static final String REQUEST_URL = "http://api.twitter.com/oauth/request_token";
    public static final String ACCESS_URL = "http://api.twitter.com/oauth/access_token";
    public static final String AUTHORIZE_URL = "http://api.twitter.com/oauth/authorize";

    public static User user;

}
