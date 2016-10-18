package com.example.bccarducci.netflixroulette;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.regex.Pattern;


/*****************************************************************************************
 * @author Brian Carducci and Reed Arnold
 * Netflix Roulette ---- 3/8/16
 *
 * Backend Java UI to sperate individualmovie
 * JSON objects taken in from the JSON array
 *
 *****************************************************************************************/
public class Movie {

    //Variables
    private String title, genre, cast, director, summary;

    //Constructor
    public Movie(String title, String genre, String cast, String director, String summary) {
        this.title = title;
        this.genre = genre;
        this.cast = cast;
        this.director = director;
        this.summary = summary;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre(){
        return genre;
    }

    public String getCast(){
        return cast;
    }

    public String getDirector(){
        return director;
    }

    public String getSummary(){
        return summary;
    }
}




