/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nytimesviewer;

/**
 *
 * @author dale
 */
public class NYTNewsStory {
    public String webUrl;
    public String headline;
    public String snippet;
    public String leadParagraph;
    public String newsDesk;
    public String sectionName;
    public String source;
    
    
    public NYTNewsStory(String webUrl, String headline, String snippet, 
            String leadParagraph, String newsDesk, String sectionName, String source ) {
        this.webUrl = webUrl;
        this.headline = headline;
        this.snippet = snippet;
        this.leadParagraph = leadParagraph;
        this.newsDesk = newsDesk;
        this.sectionName = sectionName;
    }
    
    
}
