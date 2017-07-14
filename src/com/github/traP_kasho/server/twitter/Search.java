package com.github.traP_kasho.server.twitter;

import com.github.traP_kasho.server.PropertyManager;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

public class Search {
    public static List<Status> search(String name) throws TwitterException {
        final int MAX_VALUE =  Integer.parseInt(PropertyManager.getValue("NUM_TWEET_GET"));
        List<Status> statuses = new ArrayList<>();
        Twitter twitter = AuthTwitter.getTwitter();
        //このフィルター実は非公開だったりするので若干†ヤバ†がある
        Query query = new Query(name + " filter:follows");
        query.setCount(100);
        QueryResult result;

        int i = 1;
        do {
            result = twitter.search(query);
            statuses.addAll(result.getTweets());
            i++;
        } while ((query = result.nextQuery()) != null && i <= MAX_VALUE);
        return statuses;
    }
}
