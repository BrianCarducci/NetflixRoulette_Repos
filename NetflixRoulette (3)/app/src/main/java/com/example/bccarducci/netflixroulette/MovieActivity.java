package com.example.bccarducci.netflixroulette;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;


/*****************************************************************************************
 * @author Brian Carducci and Reed Arnold
 * Netflix Roulette ---- 3/8/16
 *
 * Activity to display the movies taken in from movie java class.
 * Displays movie title, director, genre, cast, and summarry
 *
 *****************************************************************************************/

public class MovieActivity extends AppCompatActivity {

    public static ArrayList<Movie> movieAL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        movieChoice(intent.getIntExtra("index", -1));
    }



    //Method to set movie selection from sent intent
    public void movieChoice(int index){
        //Takes in current intent
        //Sets intent values
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String director = intent.getStringExtra("director");
        String genre = intent.getStringExtra("genre");
        String cast = intent.getStringExtra("cast");
        String summary = intent.getStringExtra("summary");

        //Displays them into textView on screen
        TextView titleView = (TextView) findViewById(R.id.title);
        TextView directorView = (TextView) findViewById(R.id.director);
        TextView castView = (TextView) findViewById(R.id.cast);
        TextView genreView = (TextView) findViewById(R.id.genre);
        TextView summaryView = (TextView) findViewById(R.id.summary);

        //Sets the display texts
        titleView.setText(title);
        directorView.setText(director);
        castView.setText(cast);
        genreView.setText(genre);
        summaryView.setText(summary);
    }//Ends movieChoice()

    //Future method to reroll random selection
    public void random(View view){
        Random random = new Random();
        int index = random.nextInt(movieAL.size()-1);
        movieChoice(index);
    }
}
