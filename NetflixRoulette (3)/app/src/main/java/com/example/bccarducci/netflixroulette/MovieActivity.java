package com.example.bccarducci.netflixroulette;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;

import java.io.InputStream;
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

public class MovieActivity extends Activity {

    String posterURL = null;

    //public static ArrayList<Movie> movieAL = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        Intent intent = getIntent();
        movieChoice(intent.getIntExtra("index", -1));
        new DownloadImageTask().execute();
    }

    //Method to set movie selection from sent intent
    public void movieChoice(int index){
        //Takes in current intent
        //Sets intent values
        Intent intent = getIntent();
        posterURL = intent.getStringExtra("poster");
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
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView moviePoster = (ImageView) findViewById(R.id.movie_poster);

//        public DownloadImageTask(ImageView bmImage) {
//            this.bmImage = bmImage;
//        }

        protected Bitmap doInBackground(String... urls) {
            //String urldisplay = posterURL;
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(posterURL).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            moviePoster.setImageBitmap(result);
            if (result == null){
                moviePoster.setImageResource(R.drawable.netflix);
            }
        }
    }
}
