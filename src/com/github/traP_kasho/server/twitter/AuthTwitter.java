package com.github.traP_kasho.server.twitter;


import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ResourceBundle;


public class AuthTwitter {
    private static String conKey, conSec, accToc, accSec;
    private static final String PROPERTY_FILE_NAME = "keys";
    private static Configuration conf;

    static {
        ResourceBundle bundle = ResourceBundle.getBundle(PROPERTY_FILE_NAME);
        conKey = bundle.getString("CONKEY");
        conSec = bundle.getString("CONSEC");
        accToc = bundle.getString("ACCTOK");
        accSec = bundle.getString("ACCSEC");

        ConfigurationBuilder builder = new ConfigurationBuilder();
        builder.setDebugEnabled(true).setOAuthConsumerKey(conKey).setOAuthConsumerSecret(conSec).setOAuthAccessToken(accToc).setOAuthAccessTokenSecret(accSec);
        conf = builder.build();
    }

    public static Configuration getConfiguration() {
        return conf;
    }

    public static Twitter getTwitter() {
        return new TwitterFactory(conf).getInstance();
    }
}
