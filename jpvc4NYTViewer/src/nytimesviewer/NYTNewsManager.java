/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nytimesviewer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import org.json.simple.*;

/**
 *
 * @author dale
 * https://docs.oracle.com/javase/tutorial/networking/urls/creatingUrls.html
 */
public final class NYTNewsManager {
    private static String urlString = "";
  
    private static final String baseUrlString = "http://api.nytimes.com/svc/search/v2/articlesearch.json";
    private static final String apiKey = "1bd23e3003632f4c95bc0ff5ea313c29:14:71568150";
    private static String searchString = "University of Missouri";
    
    private static URL url;
    private static ArrayList<NYTNewsStory> newsStories;
    
    
    private NYTNewsManager() {
        ;
    }
    
    public static void load(String searchString) throws Exception {
        if (searchString == null || searchString.equals("")) {
            throw new Exception("The search string was empty.");
        }
        
        // create the urlString
        String encodedSearchString;
        try {
            // searchString must be URL encoded to put in URL
            encodedSearchString = URLEncoder.encode(searchString, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw ex;
        }
        
        NYTNewsManager.urlString = NYTNewsManager.baseUrlString + "?q=" + encodedSearchString + "&api-key=" + NYTNewsManager.apiKey;
        
        try {
            NYTNewsManager.url = new URL(NYTNewsManager.urlString);
        } catch(MalformedURLException muex) {
           throw muex;
        }
        
        String jsonString = "";
        try {
            BufferedReader in = new BufferedReader(
            new InputStreamReader(NYTNewsManager.url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                jsonString += inputLine;
            in.close();
        } catch (IOException ioex) {
            throw ioex;
        }
        
        try {
            parseJsonNewsFeed(jsonString);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private static void parseJsonNewsFeed(String jsonString) throws Exception {
        
        // start with clean list
        NYTNewsManager.newsStories = new ArrayList<>();
        NYTNewsManager.newsStories.clear();
        
        if (jsonString == null || jsonString == "") return;
        
        JSONObject jsonObj;
        try {
            jsonObj = (JSONObject)JSONValue.parse(jsonString);
        } catch (Exception ex) {
            throw ex;
        }
        
        if (jsonObj == null) return;
        
        String status = "";
        try {
            status = (String)jsonObj.get("status");
        } catch (Exception ex) {
            throw ex;
        }
        
        if (status == null || !status.equals("OK")) {
            throw new Exception("Status returned from API was not OK.");
        }
        
        JSONObject response;
        try {
            response = (JSONObject)jsonObj.get("response");
        } catch (Exception ex) {
            throw ex;
        }
        
        JSONArray docs;
        try {
            docs = (JSONArray)response.get("docs");
        } catch (Exception ex) {
            throw ex;
        }
      
        for (Object doc : docs) {
            try {
                JSONObject story = (JSONObject)doc;
                String webUrl = (String)story.getOrDefault("web_url", "");
                String snippet = (String)story.getOrDefault("snippet", "");
                String leadParagraph = (String)story.getOrDefault("lead_paragraph", "");
                String source = (String)story.getOrDefault("source", "");
                String newsDesk = (String)story.getOrDefault("news_desk", "");
                String sectionName = (String)story.getOrDefault("section_name", "");
                JSONObject headlineObj = (JSONObject)story.getOrDefault("headline", null);
                String headline = "";
                if (headlineObj != null) {
                    headline = (String)headlineObj.getOrDefault("main", "");
                }
                
                System.out.println("headline: " + headline + "\n");
                System.out.println("webUrl: " + webUrl + "\n");
                System.out.println("snippet: " + snippet + "\n");
                System.out.println("leadParagraph: " + leadParagraph + "\n");
                System.out.println("newsDesk: " + newsDesk + "\n");
                System.out.println("sectionName: " + sectionName + "\n");
                System.out.println("source: " + source + "\n");
                System.out.println("------------------------------------------------------\n");
                
                NYTNewsStory newsStory = new NYTNewsStory(webUrl, headline, snippet, leadParagraph, newsDesk, sectionName, source );
                NYTNewsManager.newsStories.add(newsStory);
                
            } catch (Exception ex) {
                throw ex;
            }
            
        }
        
    }
    
    public static ArrayList<NYTNewsStory> getNewsStories() {
        return NYTNewsManager.newsStories;
    }
    
    public static int getNumNewsStories() {        
        return NYTNewsManager.newsStories.size();
    }
    
}
