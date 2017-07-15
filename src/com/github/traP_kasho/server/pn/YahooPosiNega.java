package com.github.traP_kasho.server.pn;

import com.github.traP_kasho.server.PropertyManager;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public final class YahooPosiNega {
    private static final String END_POINT = "https://jlp.yahooapis.jp/MAService/V1/parse";
    private static final String ENCODING = "UTF-8";
    private static final String APPID = PropertyManager.getValue("APPID");
    private static final String DICTIONARY_PATH = "";
    private static final int NEGATIVE = -1;
    private static final int POSITIVE = 1;
    private static String dic;

    static {
        StringBuilder b = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(PropertyManager.getValue("DICTIONARY_PATH"))))) {
            String line;
            while ((line = reader.readLine()) != null) {
                b.append(line);
                b.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        dic = new String(b);

    }


    public static Score judge(Score score) {
        List<String> words = new ArrayList<>();

        String text = score.getText().toLowerCase();
        try {
            text = URLEncoder.encode(text, ENCODING);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        text = text.replace("*", "%2a")
                .replace("-", "%2d")
                .replace("_", "")
                .replace("+", "%20");

        URL url = null;
        try {
            url = new URL(END_POINT + "?appid=" + APPID + "&results=ma&response=baseform&filter=1|2|3|9|10&sentence=" + text);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        HttpURLConnection connection = null;

        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                words = convertXML(connection.getInputStream());
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }

        int[] sentiment = new int[2];
        for(String word: words) {
            switch (searchDictionary(word)) {
                case NEGATIVE :
                    sentiment[0]++;
                    break;
                case POSITIVE :
                    sentiment[1]++;
                    break;
            }
        }
        score.setNegScore(sentiment[0]);
        score.setPosScore(sentiment[1]);

        return score;
    }

    private static List<String> convertXML(InputStream stream) {
        List<String> res = new ArrayList<>();
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(new InputSource(stream));

            Node parentNode = doc.getDocumentElement();
            Node wordList = parentNode.getFirstChild().getLastChild();
            Node word = wordList.getFirstChild();
            Node element;
            while(word != null) {
                element = word.getFirstChild().getFirstChild();
                res.add(element.getNodeValue());
                word = word.getNextSibling();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static int searchDictionary (String word) {
        int res = 0;
        try (BufferedReader reader = new BufferedReader(new StringReader(dic))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Pattern p = Pattern.compile(word);
                Matcher m = p.matcher(line);

                if (m.find()){
                    if(line.indexOf("n") != -1){
                        res = NEGATIVE;
                    } if(line.indexOf("p") != -1){

                        res = POSITIVE;
                    }if(line.indexOf("ネガ") != -1){

                        res = NEGATIVE;
                    }if(line.indexOf("ポジ") != -1){

                        res = POSITIVE;
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
