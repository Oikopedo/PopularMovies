package com.example.popularmovies.NetworkUtils;


import com.example.popularmovies.Movie.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String KEY_ID="id";
    private static final String KEY_RESULTS="results";
    private static final String KEY_NAME="original_title";
    private static final String KEY_IMAGE_SOURCE="poster_path";
    private static final String KEY_SYNOPSIS="overview";
    private static final String KEY_RATING="vote_average";
    private static final String KEY_RELEASE_DATE="release_date";

    private static String responseFromHttpGetRequest(String urlString)throws Exception{
        StringBuilder json = new StringBuilder();
        URL url=new URL(urlString);
        HttpURLConnection con=(HttpURLConnection) url.openConnection();
        try{
            BufferedReader in=new BufferedReader(new InputStreamReader(con.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                json.append(line + "\n");
            }
            in.close();
            return  json.toString();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        return null;
    }

    private static Movie[] parseResponse(String response){
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray res=obj.getJSONArray(KEY_RESULTS);
            Movie[] movies=new Movie[res.length()];
            JSONObject movie;
            for (int i=0;i<movies.length;i++){
                movie=res.getJSONObject(i);
                movies[i]=new Movie(movie.getInt(KEY_ID)
                        ,movie.getString(KEY_NAME)
                        ,movie.getString(KEY_IMAGE_SOURCE)
                        ,movie.getString(KEY_SYNOPSIS)
                        ,(float)movie.getDouble(KEY_RATING)
                        ,movie.getString(KEY_RELEASE_DATE));
            }
            //for (Movie m: movies){
            //    Log.v("Movie",m.toString());
            //}
            return movies;
        }catch (JSONException e){
            e.printStackTrace();
        }
        return null;
    }

    public static Movie[] getMovies(String urlString){
        String jsonResp;
        try{
            jsonResp=responseFromHttpGetRequest(urlString);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return parseResponse(jsonResp);
    }
}
