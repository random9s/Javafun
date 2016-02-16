/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpvc4photos;

/**
 *
 * @author jakeparham
 */
public class Photo {
    private final String fullUrl;
    private final String title;
    private final String description;
    private final double latitude;
    private final double longitude;
    private final String date;
    
    public Photo(String url, String title, String description, double lat, double lon, String date){
        this.fullUrl = url;
        this.title = title;
        this.description = description;
        this.latitude = lat;
        this.longitude = lon;
        this.date = date;
    }
    
    public String getUrl(){ return this.fullUrl; }
    public String getTitle() { return this.title; }
    public String getDesc() { return this.description; }
    public double getLatitude() { return this.latitude; }
    public double getLongitude() { return this.longitude; }
    public String getDate() { return this.date; }
    
    @Override
    public String toString() {
        return "Title: " + this.title + "\nDescription: " + this.description + "\nTaken on: " + this.date + "\n";
    }
}
