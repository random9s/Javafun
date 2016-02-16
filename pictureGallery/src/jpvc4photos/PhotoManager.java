/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpvc4photos;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.json.simple.*;
/**
 *
 * @author jakeparham
 */
public class PhotoManager {
    private String urlString = "http://dalemusser.net/data/nocaltrip/photos.json";
    private ArrayList<Photo> photos;
    
    
    public PhotoManager(){
        photos = new ArrayList<>();
    }
    
    public void load() throws Exception{
        URL url;
        
        try {
            url = new URL(urlString);
        } catch(MalformedURLException muex) {
           throw muex;
        }
        
        String jsonString = "";
        try {
            BufferedReader in = new BufferedReader(
            new InputStreamReader(url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                jsonString += inputLine;
            in.close();
        } catch (IOException ioex) {
            throw ioex;
        }
        
        try {
            parseJson(jsonString);
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    private void parseJson(String jsonString) throws Exception{
        photos.clear();
        
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
        
        if (status == null || !status.equals("ok")) {
            throw new Exception("Status returned from API was not OK.");
        }

        String photoPath = "";
        try{
            photoPath = (String)jsonObj.get("photosPath");
        } catch (Exception ex){
            throw ex;
        }
        
        JSONArray photoJson;
        try {
            photoJson = (JSONArray)jsonObj.get("photos");
        } catch (Exception ex) {
            throw ex;
        }
      
        for (Object p : photoJson) {
            try {
                JSONObject photoData = (JSONObject)p;
                String image = (String) photoData.getOrDefault("image", "");
                String title = (String) photoData.getOrDefault("title", "");
                String description = (String) photoData.getOrDefault("description", "");
                double latitude = (double) photoData.getOrDefault("latitude", "");
                double longitude = (double) photoData.getOrDefault("longitude", "");
                String date = (String) photoData.getOrDefault("date", "");
                String url = "http://dalemusser.net/data/nocaltrip/"+photoPath+"/"+image;

                Photo photo = new Photo(url, title, description, latitude, longitude, date);
                photos.add(photo);
                
            } catch (Exception ex) {
                throw ex;
            }
        }
    }
    
    public ArrayList<Photo> getPhotos() {
        return photos;
    }
}
