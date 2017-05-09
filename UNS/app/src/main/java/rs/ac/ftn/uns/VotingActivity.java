package rs.ac.ftn.uns;

import android.os.AsyncTask;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.io.IOException;

import rs.ac.ftn.uns.utils.NetworkHelper;

public class VotingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voting);


        FrameLayout fr = (FrameLayout) findViewById(R.id.fetch_request);
        fr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String android_id = Settings.Secure.getString(getBaseContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);
                new HttpFetchTask().execute(android_id);
            }
        });

        FrameLayout vr = (FrameLayout) findViewById(R.id.vote_request);
        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String android_id = Settings.Secure.getString(getBaseContext().getContentResolver(),
                        Settings.Secure.ANDROID_ID);

                new HttpPostVoteTask().execute("5911fd2b871409416cb24d4e", android_id);
            }
        });

        FrameLayout gr = (FrameLayout) findViewById(R.id.results_request);
        gr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new HttpGetVoteResultsTask().execute();
            }
        });
    }

    public class HttpFetchTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... ids){
            String id = ids[0];
            String result = null;
            try{
                result = NetworkHelper.fetchData(id);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result){
            if(result != null && !result.equals("")){
                Toast.makeText(VotingActivity.this, "Pa, reklo bi se da je uspelo :)",Toast.LENGTH_SHORT).show();
                Log.i("Mahab", result);
            }
        }

    }

    public class HttpPostVoteTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params){
            String candidate_id = params[0];
            String voter_id = params[1];


            String JSON = NetworkHelper.buildVoteJSON(candidate_id, voter_id);
            String result = null;
            try{
                result = NetworkHelper.postVote(JSON);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s){
            if(s != null && !s.equals("")){
                Toast.makeText(VotingActivity.this, "Pa, reklo bi se da si glasao:)",Toast.LENGTH_SHORT).show();
                Log.i("Mahab", s);
            }
        }

    }

    public class HttpGetVoteResultsTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params){

            String result = "";
            try{
                result = NetworkHelper.getVotesResult();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s){
            if(s != null && !s.equals("")){
                Toast.makeText(VotingActivity.this, "Pa, reklo bi se da si glasao:)",Toast.LENGTH_SHORT).show();
                Log.i("Mahab", s);
            }
        }

    }
}
