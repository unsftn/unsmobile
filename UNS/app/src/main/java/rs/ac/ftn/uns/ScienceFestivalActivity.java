package rs.ac.ftn.uns;

import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rs.ac.ftn.uns.TeamFragment.OnListFragmentInteractionListener;
import rs.ac.ftn.uns.model.Competitor;
import rs.ac.ftn.uns.model.CompetitorsContent;
import rs.ac.ftn.uns.utils.NetworkHelper;

public class ScienceFestivalActivity extends AppCompatActivity implements OnListFragmentInteractionListener{

    private TextView mTextMessage;
    private Competitor currentCompetitor;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.competitors:
                    mTextMessage.setText(R.string.vote_title);
                    return true;
            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_science_festival);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    public void onListFragmentInteraction(Competitor item) {
        String android_id = Settings.Secure.getString(getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        this.currentCompetitor = item;

        if(!CompetitorsContent.HAS_VOTED) {
            new HttpPostVoteTask().execute(item.id, android_id);
        } else {
            Toast.makeText(ScienceFestivalActivity.this, "Glasanje onemogućeno, vaš glas je već zabeležen.",Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(ScienceFestivalActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
    }

    public class HttpPostVoteTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params){
            String candidate_id = params[0];
            String voter_id = params[1];
            String result = null;

            try{

            String JSON = NetworkHelper.buildVoteJSON(candidate_id, voter_id);

                result = NetworkHelper.postVote(JSON);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s){
            if(s != null && !s.equals("")){
                Toast.makeText(ScienceFestivalActivity.this, "Vaš glas za:" + currentCompetitor.projectName + " je uspešno zabeležen, hvala!",Toast.LENGTH_LONG).show();
                CompetitorsContent.HAS_VOTED = true;
                MyTeamRecyclerViewAdapter.currentView.setBackgroundColor(0x9664DD17);
            } else {
                Toast.makeText(ScienceFestivalActivity.this, "Došlo je do greške prlikom glasanja.",Toast.LENGTH_SHORT).show();
            }
            Log.i("Mahab", s);
        }

    }

}
