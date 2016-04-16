package com.example.avto.myapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by avto on 4/9/2016.
 */
public class GetPlayer extends AsyncTask<String, Void, String> {


    String players;

    @Override
    protected String doInBackground(String... params) {
        Integer result = 0;
        HttpURLConnection urlConnection;
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            int statusCode = urlConnection.getResponseCode();

            // 200 represents HTTP OK
            if (statusCode == 200) {
                BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    response.append(line);
                }
                parseResult(response.toString());
            }else {
                return "Faild";
            }
        } catch (Exception e) {
            e.toString();
        }
        //players=players.substring(4);
        return players;
    }

   /* @Override
    protected void onPostExecute(Integer result) {
        // Download complete. Let us update UI
        //

        if (result == 1) {
            madapter = new TeamAdapter( teams,TeamAcivity.this);
            mRecyclerView.setAdapter(madapter);
        } else {
            Toast.makeText(TeamAcivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
        }
    }
}*/

    private void parseResult(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.optJSONArray("players");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject player = jsonArray.optJSONObject(i);
                players += player.optString("name");
                players+="\n";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}