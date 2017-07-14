package com.github.traP_kasho.server;


import com.github.traP_kasho.server.pn.Score;
import com.github.traP_kasho.server.pn.YahooPosiNega;
import com.github.traP_kasho.server.twitter.AuthTwitter;
import com.github.traP_kasho.server.twitter.Search;
import twitter4j.JSONArray;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.FileSystemNotFoundException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class Main {

    /**
     * cronで定期実行するにしても毎回全名前を検索してると確実にTwitterのREST API呼び出しの上限に引っかかるので,一人の教員を6時間に1回というふうにする。
     * そして毎時間(TwitterのRESTAPIは15分に1度limitがクリアされるから15分に1度でも問題はない)これを定期実行したい。
     */
    public static void main(String[] args) {
        File file = new File(PropertyManager.getValue("PROF_DEF_FILE_PATH"));
        if(! file.exists()) {
            new FileSystemNotFoundException().printStackTrace();
            System.exit(-1);
        }

        List<Professor> professors = null;

        try {
            professors = ProfessorJSONConverter.toProfessor(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        //以上ok


        List<Professor> determinedProfs = determineSerchedProfessor(professors);
        Map<String, Professor> determinedProfsMap = new LinkedHashMap();
        for(Professor prof: determinedProfs) {
            determinedProfsMap.put(prof.getDisplayName(), prof);
        }
        File tweetJSON = new File(PropertyManager.getValue("TWEET_FILE_PATH"));
        try {
            professors = ProfessorJSONConverter.getTweet(new FileInputStream(tweetJSON), determinedProfsMap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        JSONArray jArray = new JSONArray();
        for(Professor professor : determinedProfs) {
            for(Score score: professor.getScores()) {
                YahooPosiNega.judge(score);
            }
            jArray.put(ProfessorJSONConverter.toJSON(professor));
        }


        final String SAVE_PATH = PropertyManager.getValue("SAVE_PLACE") + "/" + PropertyManager.getValue("SAVE_FILE_NAME") + ".json";
        File jFile = new File(SAVE_PATH);

        try(PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(jFile)))) {
            writer.print(jArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    //It'll be implemented soon.
    public static List<Professor> determineSerchedProfessor(List<Professor> profs) {
        return profs;
    }
}
