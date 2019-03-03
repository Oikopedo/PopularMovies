package com.example.popularmovies.NetworkUtils;


import android.util.Log;

import com.example.popularmovies.Movie.Movie;
import com.example.popularmovies.Movie.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {
    private static final String KEY_ID="id";
    private static final String KEY_RESULTS="results";
    private static final String KEY_NAME="original_title";
    private static final String KEY_IMAGE_SOURCE="poster_path";
    private static final String KEY_SYNOPSIS="overview";
    private static final String KEY_RATING="vote_average";
    private static final String KEY_RELEASE_DATE="release_date";
    private static final String TRAILER_PREFIX="http://api.themoviedb.org/3/movie/";
    private static final String TRAILER_SUFFIX="/videos?api_key=1be795252d059d8804d4c2ff0386b872";

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
            Log.v("SKATA",json.toString());
            return  json.toString();

        }catch (IOException e){
            e.printStackTrace();
        }finally {
            con.disconnect();
        }
        return null;
    }

    private static List<Trailer> parseTrailerRespone(String response){
        try{
            JSONObject obj=new JSONObject(response);
            JSONArray res=obj.getJSONArray(KEY_RESULTS);
            List<Trailer> trailers=new ArrayList<>();
            JSONObject trailer;
            for (int i=0;i<res.length();i++){
                trailer=res.getJSONObject(i);
                if (trailer.getString("site").equals("YouTube")){
                    trailers.add(new Trailer(trailer.getString("key"),trailer.getString("name"),trailer.getString("type")));
                }
            }
            //for(Trailer t:trailers){
            //    Log.v("TRAILER",t.getName()+t.getType());
            //}
            return trailers;
        }catch (JSONException e){
            e.printStackTrace();
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

    public static List<Trailer> getTrailers(int movieId){
        String urlString=TRAILER_PREFIX+String.valueOf(movieId)+TRAILER_SUFFIX;
        String jsonResp;
        try {
            jsonResp=responseFromHttpGetRequest(urlString);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
        return parseTrailerRespone(jsonResp);
    }
}
