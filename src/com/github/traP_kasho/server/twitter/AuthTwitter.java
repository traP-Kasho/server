package com.github.traP_kasho.server.twitter;


import com.github.traP_kasho.server.PropertyManager;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ResourceBundle;


public final class AuthTwitter {
    private static String conKey, conSec, accToc, accSec;
    private static Configuration conf;

    static {
        conKey = PropertyManager.getValue("CONKEY");
        conSec = PropertyManager.getValue("CONSEC");
        accToc = PropertyManager.getValue("ACCTOK");
        accSec = PropertyManager.getValue("ACCSEC");

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
