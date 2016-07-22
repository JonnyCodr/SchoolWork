package com.example.spotr;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import twitter4j.*;
import twitter4j.api.HelpResources;
import twitter4j.auth.AccessToken;
import twitter4j.auth.OAuth2Token;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.io.File;
import java.util.List;
import java.util.Map;

public class MainActivity extends ActionBarActivity implements SwipeRefreshLayout.OnRefreshListener {

    private Uri imageUri;
    private SwipeRefreshLayout swipeLayout;
    private AsyncTwitter twitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startTwitter();
        swipeLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        swipeLayout.setOnRefreshListener(this);
    }

    /**
     * Method to handle pull down refresh from listener and update the query.
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override public void run() {
                swipeLayout.setRefreshing(false);
                if (twitter != null) {
                    updateTweetList(twitter);
                }
            }
        }, 5000);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * Initialize the AsyncTwitter object so that it can be authorized to query
     * and post.
     */
    public void startTwitter() {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("8l7AJW6exQ8i6UZc26fokGt4q")
                .setOAuthConsumerSecret("IJr8yxOkLljh1jl8KCwohnI5OUuXMMSKJxRlH3VnNOh2u8W0Gd")
                .setOAuthAccessToken("2897953255-63U9xbHfhhh7zp3J53K4IcTgjvRRArF4kacrlVY")
                .setOAuthAccessTokenSecret("NjG5n1i18n6jDzAgOqyBqRQ4gUxgfCAxVXopFGO7plcO1");
        AsyncTwitterFactory tf = new AsyncTwitterFactory(cb.build());
        twitter = tf.getInstance();
        updateTweetList(twitter);
    }

    /**
     * Update the List view with updated query, from start of app and when pull
     * down refresh is activated.
     *
     * @param twitter
     */
    private void updateTweetList(AsyncTwitter twitter) {
        twitter.addListener(new TwitterListener() {
            @Override
            public void updatedStatus(Status status) {
                Log.d("Status Update", "To: " + status.getText());
                Toast.makeText(MainActivity.this, status.getText(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void searched(QueryResult queryResult) {
                Log.d("Search returned", "result: " + queryResult);
                List<Status> tweets = queryResult.getTweets();
                ListView tweetsToView = (ListView) findViewById(R.id.updatedTweets);
                for (Status tweet : tweets) {
                    //TODO add tweets to list view here, look at GSON for how to put JSON tweets to Java objects
                    Log.d("Single tweet", tweet.getText());
                }
            }

//Unused TwitterListener methods
            /*****************************************************************************/
            @Override
            public void gotMentions(ResponseList<Status> statuses) {
            }

            @Override
            public void gotHomeTimeline(ResponseList<Status> statuses) {
            }

            @Override
            public void gotUserTimeline(ResponseList<Status> statuses) {
            }

            @Override
            public void gotRetweetsOfMe(ResponseList<Status> statuses) {
            }

            @Override
            public void gotRetweets(ResponseList<Status> retweets) {
            }

            @Override
            public void gotShowStatus(Status status) {
            }

            @Override
            public void destroyedStatus(Status destroyedStatus) {
            }


            @Override
            public void retweetedStatus(Status retweetedStatus) {
            }

            @Override
            public void gotOEmbed(OEmbed oembed) {
            }

            @Override
            public void lookedup(ResponseList<Status> statuses) {
            }

            @Override
            public void gotDirectMessages(ResponseList<DirectMessage> messages) {
            }

            @Override
            public void gotSentDirectMessages(ResponseList<DirectMessage> messages) {
            }

            @Override
            public void gotDirectMessage(DirectMessage message) {
            }

            @Override
            public void destroyedDirectMessage(DirectMessage message) {
            }

            @Override
            public void sentDirectMessage(DirectMessage message) {
            }

            @Override
            public void gotFriendsIDs(IDs ids) {
            }

            @Override
            public void gotFollowersIDs(IDs ids) {
            }

            @Override
            public void lookedUpFriendships(ResponseList<Friendship> friendships) {
            }

            @Override
            public void gotIncomingFriendships(IDs ids) {
            }

            @Override
            public void gotOutgoingFriendships(IDs ids) {
            }

            @Override
            public void createdFriendship(User user) {
            }

            @Override
            public void destroyedFriendship(User user) {
            }

            @Override
            public void updatedFriendship(Relationship relationship) {
            }

            @Override
            public void gotShowFriendship(Relationship relationship) {
            }

            @Override
            public void gotFriendsList(PagableResponseList<User> users) {
            }

            @Override
            public void gotFollowersList(PagableResponseList<User> users) {

            }

            @Override
            public void gotAccountSettings(AccountSettings settings) {

            }

            @Override
            public void verifiedCredentials(User user) {

            }

            @Override
            public void updatedAccountSettings(AccountSettings settings) {

            }

            @Override
            public void updatedProfile(User user) {

            }

            @Override
            public void updatedProfileBackgroundImage(User user) {

            }

            @Override
            public void updatedProfileColors(User user) {

            }

            @Override
            public void updatedProfileImage(User user) {

            }

            @Override
            public void gotBlocksList(ResponseList<User> blockingUsers) {

            }

            @Override
            public void gotBlockIDs(IDs blockingUsersIDs) {

            }

            @Override
            public void createdBlock(User user) {

            }

            @Override
            public void destroyedBlock(User user) {

            }

            @Override
            public void lookedupUsers(ResponseList<User> users) {

            }

            @Override
            public void gotUserDetail(User user) {

            }

            @Override
            public void searchedUser(ResponseList<User> userList) {

            }

            @Override
            public void gotContributees(ResponseList<User> users) {

            }

            @Override
            public void gotContributors(ResponseList<User> users) {

            }

            @Override
            public void removedProfileBanner() {

            }

            @Override
            public void updatedProfileBanner() {

            }

            @Override
            public void gotMutesList(ResponseList<User> blockingUsers) {

            }

            @Override
            public void gotMuteIDs(IDs blockingUsersIDs) {

            }

            @Override
            public void createdMute(User user) {

            }

            @Override
            public void destroyedMute(User user) {

            }

            @Override
            public void gotUserSuggestions(ResponseList<User> users) {

            }

            @Override
            public void gotSuggestedUserCategories(ResponseList<Category> category) {

            }

            @Override
            public void gotMemberSuggestions(ResponseList<User> users) {

            }

            @Override
            public void gotFavorites(ResponseList<Status> statuses) {

            }

            @Override
            public void createdFavorite(Status status) {

            }

            @Override
            public void destroyedFavorite(Status status) {

            }

            @Override
            public void gotUserLists(ResponseList<UserList> userLists) {

            }

            @Override
            public void gotUserListStatuses(ResponseList<Status> statuses) {

            }

            @Override
            public void destroyedUserListMember(UserList userList) {

            }

            @Override
            public void gotUserListMemberships(PagableResponseList<UserList> userLists) {

            }

            @Override
            public void gotUserListSubscribers(PagableResponseList<User> users) {

            }

            @Override
            public void subscribedUserList(UserList userList) {

            }

            @Override
            public void checkedUserListSubscription(User user) {

            }

            @Override
            public void unsubscribedUserList(UserList userList) {

            }

            @Override
            public void createdUserListMembers(UserList userList) {

            }

            @Override
            public void checkedUserListMembership(User users) {

            }

            @Override
            public void createdUserListMember(UserList userList) {

            }

            @Override
            public void destroyedUserList(UserList userList) {

            }

            @Override
            public void updatedUserList(UserList userList) {

            }

            @Override
            public void createdUserList(UserList userList) {

            }

            @Override
            public void gotShowUserList(UserList userList) {

            }

            @Override
            public void gotUserListSubscriptions(PagableResponseList<UserList> userLists) {

            }

            @Override
            public void gotUserListMembers(PagableResponseList<User> users) {

            }

            @Override
            public void gotSavedSearches(ResponseList<SavedSearch> savedSearches) {

            }

            @Override
            public void gotSavedSearch(SavedSearch savedSearch) {

            }

            @Override
            public void createdSavedSearch(SavedSearch savedSearch) {

            }

            @Override
            public void destroyedSavedSearch(SavedSearch savedSearch) {

            }

            @Override
            public void gotGeoDetails(Place place) {

            }

            @Override
            public void gotReverseGeoCode(ResponseList<Place> places) {

            }

            @Override
            public void searchedPlaces(ResponseList<Place> places) {

            }

            @Override
            public void gotSimilarPlaces(ResponseList<Place> places) {

            }

            @Override
            public void gotPlaceTrends(Trends trends) {

            }

            @Override
            public void gotAvailableTrends(ResponseList<Location> locations) {

            }

            @Override
            public void gotClosestTrends(ResponseList<Location> locations) {

            }

            @Override
            public void reportedSpam(User reportedSpammer) {

            }

            @Override
            public void gotOAuthRequestToken(RequestToken token) {

            }

            @Override
            public void gotOAuthAccessToken(AccessToken token) {

            }

            @Override
            public void gotOAuth2Token(OAuth2Token token) {

            }

            @Override
            public void gotAPIConfiguration(TwitterAPIConfiguration conf) {

            }

            @Override
            public void gotLanguages(ResponseList<HelpResources.Language> languages) {

            }

            @Override
            public void gotPrivacyPolicy(String privacyPolicy) {

            }

            @Override
            public void gotTermsOfService(String tof) {

            }

            @Override
            public void gotRateLimitStatus(Map<String, RateLimitStatus> rateLimitStatus) {

            }

            @Override
            public void onException(TwitterException te, TwitterMethod method) {

            }
/*****************************************************************************/
        });
        Query query = new Query("\\#dogspotting");
        query.setCount(100);
        twitter.search(query);
    }

    public void doShare() {
        Intent share = new Intent(Intent.ACTION_SEND);

        share.setType("image/jpg");

        String imagePath = Environment.getExternalStorageDirectory()
                + "/pic.jpg";

        File imageFileToShare = new File(imagePath);

        Uri uri = Uri.fromFile(imageFileToShare);
        share.putExtra(Intent.EXTRA_STREAM, imageUri);

        startActivity(Intent.createChooser(share, "Share Image!"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        } else if (id == R.id.menu_rules) {
            Intent rulesIntent = new Intent(this, RulesActivity.class);
            startActivity(rulesIntent);
            return true;
        } else if (id == R.id.menu_import_photo) {
            //pull in an image from the gallery

            return true;
        } else if (id == R.id.menu_item_new_photo) {
            startActivity(new Intent(MainActivity.this, SinglePhotoViewActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
