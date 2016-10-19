package com.example.bccarducci.netflixroulette;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Array;
import java.lang.reflect.MalformedParameterizedTypeException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

/*****************************************************************************************
 * @author Brian Carducci and Reed Arnold
 * Netflix Roulette ---- 3/8/16
 *
 * Activity to take in user inputs of actors, directors,
 * or a movie title than retrieve JSON array of movies
 * from NetflixRoulette.net API to send to movieActivity
 *
 *****************************************************************************************/

public class InputActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);
    }


    //AsyncTask to retrieve movie list from NetflixRoulette.net
    private class FetchMovie extends AsyncTask<String, Void, String> {

        //Input text variables
        EditText editTextA = (EditText) findViewById(R.id.actor);
        String actor = editTextA.getText().toString().replace(" ","%20");

        EditText editTextD = (EditText) findViewById(R.id.director);
        String director = editTextD.getText().toString().replace(" ","%20");

        ArrayList<Movie> movies = new ArrayList();

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            URL url = null;

            try {
                url = new URL("https://netflixroulette.net/api/api.php?actor=" + actor + "&director=" + director);

                //Creates URL connection with valid input
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();

                //If the input is empty
                if (inputStream == null) {
                    return null;
                }
                JsonReader readerJSON = new JsonReader(new InputStreamReader(inputStream));
                movies = readMoviesArray(readerJSON);
            } catch (IOException e) {
                Log.e("exception", "Error " + e.getMessage(), e);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                return null;
            }
        }//Ends doInBackground()

        //Method to send to movieActivity
        protected void onPostExecute(String result) {
            Random random = new Random();
            try {
                int index = random.nextInt(movies.size() - 1);  //Chooses a random movie from array list
                Intent intent = new Intent(InputActivity.this, MovieActivity.class);

                //puts JSON strings into intent
                intent.putExtra("title", movies.get(index).getTitle());
                intent.putExtra("director", movies.get(index).getDirector());
                intent.putExtra("genre", movies.get(index).getGenre());
                intent.putExtra("cast", movies.get(index).getCast());
                intent.putExtra("summary", movies.get(index).getSummary());
                intent.putExtra("poster", movies.get(index).getPoster());
                intent.putExtra("index", index);

                //movies = MovieActivity.movieAL;

                startActivity(intent);
            } catch (Exception ex){

            }
        }//Ends onPostExecute()
    }//Ends AsyncTask

    //Method for onClick (Random)
    public void go(View view){
        new FetchMovie().execute();
    }

    //Method to put movies into array list
    public ArrayList<Movie> readMoviesArray(JsonReader reader) throws IOException {
        ArrayList<Movie> movies = new ArrayList();
        reader.beginArray();
        while (reader.hasNext()) {
            movies.add(readMovie(reader));
        }
        reader.endArray();
        return movies;
    }//ENds readMoviesArray


    //Method to read JSON objects from API
    public Movie readMovie(JsonReader reader) throws IOException {
        String show_title = null;
        String genre = null;
        String show_cast = null;
        String director = null;
        String summary = null;
        String poster = null;

        reader.beginObject();
        while (reader.hasNext()) {
            String name = reader.nextName();
            if (name.equals("show_title")) {
                show_title = reader.nextString();
            } else if (name.equals("category")){
                genre = reader.nextString();
            } else if (name.equals("show_cast")) {
                show_cast = reader.nextString();
            } else if (name.equals("director")){
                director = reader.nextString();
            } else if (name.equals("summary")) {
                summary = reader.nextString();
            } else if (name.equals("poster")) {
                poster = reader.nextString();
            } else {
                reader.skipValue();
            }
        }
        reader.endObject();
        return new Movie(show_title, genre, show_cast, director, summary, poster);
    }//Ends readMovie()

}//Ends InputActivity class
