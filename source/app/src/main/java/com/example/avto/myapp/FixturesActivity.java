package com.example.avto.myapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FixturesActivity extends AppCompatActivity {
    private static final String TAG = "RecyclerViewExample";
    private List<MyFixtures> fixtures;
    private RecyclerView mRecyclerView;
    private FixturesAdapter madapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fixtures);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_fixtures);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        Bundle extras = getIntent().getExtras();
        String myurl=extras.getString("href");
        // Downloading data from below url
        final String url = myurl;
        new FixturesActivity.AsyncHttpTask().execute(url);

    }

    

    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
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
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }
            } catch (Exception e) {
                Log.d(TAG, e.getLocalizedMessage());
            }
            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {
            // Download complete. Let us update UI
            //

            if (result == 1) {
                madapter = new FixturesAdapter( fixtures,FixturesActivity.this);
                mRecyclerView.setAdapter(madapter);
            } else {
                Toast.makeText(FixturesActivity.this, "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void parseResult(String result) {
        try {
            JSONObject jsonObject=new JSONObject(result);
            JSONArray jsonArray=jsonObject.optJSONArray("fixtures");
            fixtures = new ArrayList<>();

            for (int i = jsonArray.length()-1; i >=0; i--) {
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                MyFixtures item = new MyFixtures();

                JSONObject jsonObject3=jsonObject1.getJSONObject("_links");
                JSONObject jsonObject4=jsonObject3.optJSONObject("homeTeam");
                item.getFirstClubUrl=jsonObject4.optString("href");
                jsonObject3=jsonObject3.optJSONObject("awayTeam");
                item.getSecondClubUrl=jsonObject3.optString("href");

                item.firstClub=jsonObject1.optString("homeTeamName");
                item.secondClub=jsonObject1.optString("awayTeamName");
                item.status=jsonObject1.optString("status");
                item.time=jsonObject1.optString("date");
                JSONObject jsonObject2=jsonObject1.optJSONObject("result");
                String goalsAwayTeam=jsonObject2.optString("goalsAwayTeam");
                String goalsHomeTeam=jsonObject2.optString("goalsHomeTeam");
                if(goalsAwayTeam=="null")
                {
                    goalsAwayTeam="?";
                    goalsHomeTeam="?";
                }
                item.secondClubScore=goalsAwayTeam;
                item.firstClubScore=goalsHomeTeam;

                fixtures.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id=item.getItemId();
        if(id==android.R.id.home){
            finish();
            System.exit(0);
        }
        return super.onOptionsItemSelected(item);
    }
}
