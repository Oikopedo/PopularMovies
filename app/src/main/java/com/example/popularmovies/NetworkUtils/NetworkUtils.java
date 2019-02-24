package com.example.popularmovies.NetworkUtils;

import android.util.Log;

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
    public static String responseFromHttpGetRequest(String urlString)throws Exception{
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

    public static Movie[] parseResponse(String response){
        try {
            JSONObject obj = new JSONObject(response);
            JSONArray res=obj.getJSONArray("results");
            Movie[] movies=new Movie[res.length()];
            JSONObject movie;
            for (int i=0;i<movies.length;i++){
                movie=res.getJSONObject(i);
                movies[i]=new Movie(movie.getString("original_title")
                        ,movie.getString("poster_path")
                        ,movie.getString("overview")
                        ,(float)movie.getDouble("vote_average")
                        ,movie.getString("release_date"));
            }
            for (Movie m: movies){
                Log.v("Movie",m.toString());
            }
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
