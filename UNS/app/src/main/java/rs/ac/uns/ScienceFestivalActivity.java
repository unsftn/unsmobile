package rs.ac.uns;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import rs.ac.uns.TeamFragment.OnListFragmentInteractionListener;
import rs.ac.uns.model.Competitor;
import rs.ac.uns.model.CompetitorsContent;
import rs.ac.uns.utils.NetworkHelper;

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

        this.currentCompetitor = item;

        if(!CompetitorsContent.HAS_VOTED) {

            AlertDialog.Builder b = new AlertDialog.Builder(ScienceFestivalActivity.this);
            b.setMessage(getResources().getString(R.string.vote_r_u_sure) + " " + item.projectName + "?").
                    setPositiveButton(getResources().getString(R.string.vote_yes), dialogClickListener).
                    setNegativeButton(getResources().getString(R.string.vote_no), dialogClickListener).show();
            //
        } else {
            Toast.makeText(ScienceFestivalActivity.this, getResources().getString(R.string.voted_already),Toast.LENGTH_SHORT).show();
        }

        //Toast.makeText(ScienceFestivalActivity.this, item.toString(), Toast.LENGTH_SHORT).show();
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int choice) {
            switch (choice) {
                case DialogInterface.BUTTON_POSITIVE:
                    String android_id = Settings.Secure.
                            getString(getBaseContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    new ScienceFestivalActivity.HttpPostVoteTask()
                            .execute(ScienceFestivalActivity.this.currentCompetitor.id, android_id);

                case DialogInterface.BUTTON_NEGATIVE:
            }
        }
    };

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
                Toast.makeText(ScienceFestivalActivity.this, getResources().getString(R.string.vote_success_I) + " " + currentCompetitor.projectName + " " + getResources().getString(R.string.vote_success_II),Toast.LENGTH_LONG).show();
                CompetitorsContent.HAS_VOTED = true;
                MyTeamRecyclerViewAdapter.currentView.setBackgroundColor(0x9664DD17);
            } else {
                Toast.makeText(ScienceFestivalActivity.this, getResources().getString(R.string.vote_failure),Toast.LENGTH_SHORT).show();
            }
            Log.i("Mahab", s);
        }

    }

    public Competitor getCurrentCompetitor(){
        return currentCompetitor;
    }

}
