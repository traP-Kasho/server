package com.github.traP_kasho.server.pn;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class pojinega_jisaku {
    public static void main(String[] args)throws UnsupportedEncodingException {

    	int textnum = 0;//textnum番目の文字列
    	
    	for(; textnum <= 2; textnum++){//textは仮に3つとしている
    		int[] score = new int [99];//ポジネガのスコア（positiveなら+1、negativeなら-1）

    			score[1] = 0;
    		
    	
    	//textは100個のString型配列と仮定（渡されるtext形式によって随時変更予定）
        String[] texts = new String[100];
        texts[0] = "MSMさん気持ち悪い気がします。近寄らないでください。汚らわしい。痛む"; 
        texts[1] = "あなたとは関わり合いになりたくないです";
        texts[2] = "こっちにこないでください気色悪い";//ここまで仮定
        
        
        //textは文字列と仮定し、以下はURLエンコード
        String encoding = "UTF-8";
        String lower_text = texts[textnum].toLowerCase();
        String result = URLEncoder.encode(lower_text, encoding);
         result= result.replace("*", "%2a");
         result = result.replace("-", "%2d");
         result= result.replace("_", "");
         result= result.replace("+", "%20");
    	
       
        try {//形態語解析開始、感情分析に関わる形容詞・形容動詞・感動詞・名詞・動詞のみを抽出
        
            URL url = new URL("https://jlp.yahooapis.jp/MAService/V1/parse?appid=dj00aiZpPWVveWhyVUx3S0t3eCZzPWNvbnN1bWVyc2VjcmV0Jng9YzM-&results=ma,uniq&filter=1|2|3|9|10&sentence=" + result);

            HttpURLConnection connection = null;

            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    try (InputStreamReader isr = new InputStreamReader(connection.getInputStream(),
                                                                       StandardCharsets.UTF_8);
                         BufferedReader reader = new BufferedReader(isr)) {
                        String line;
                        while ((line = reader.readLine()) != null) {
                            //System.out.println(line);

                        	 String[] element = line.split("<word><surface>",0);

                        	 int num = 0;//繰り返しの管理/


                        	 for(; num < element.length; num++){
                        	if(num != 0){
                        		//System.out.println(element[num]);
                        		String[] word = element[num].split("</surface>",0);
                        		//形態語解析終了、1文字のものは検索しづらいので除外
                        		int word_num = word[0].length();
                        		if(word_num > 1){
                        			//System.out.println(word[0]);

                        			//テキストファイルから単語を含む行を取ってくる
                        	        putLine("pojinega_data.txt",  word[0], textnum, score);
                        		}
                        	}
                         }
                       }
                    }
                }
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("text[" + textnum + "]のスコアは" + score[textnum]);
        //System.out.println("===== HTTP GET End =====");
    
    	}
      
    } 
    
 @Deprecated
  public static int putLine(String fileName, String searchString, int textnum, int score[]){
 
    try {
        //ファイルを読み込む
        FileReader fr = new FileReader(fileName);
        BufferedReader br = new BufferedReader(fr);
 
        //条件にあう行を画面出力する
        String line;
        while ((line = br.readLine()) != null) {
          Pattern p = Pattern.compile(searchString);
          Matcher m = p.matcher(line);

         if (m.find()){
          //System.out.println(line);

         if(line.indexOf("n") != -1){
        	 score[textnum]--;
         }
         if(line.indexOf("p") != -1){
        	 score[textnum]++;
         }
         if(line.indexOf("ネガ") != -1){
        	 score[textnum]--;
         }
         if(line.indexOf("ポジ") != -1){
        	 score[textnum]++;
         }

        }
        }
 
        br.close();
        fr.close();
        
 
    } catch (IOException ex) {
        ex.printStackTrace();
    }
    //System.out.println(score[textnum]);
    return score[textnum];
  }
    
}
   