package rs.ac.ftn.uns;

import android.os.AsyncTask;
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


        FrameLayout f = (FrameLayout) findViewById(R.id.make_request);
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String builtUri = NetworkHelper.buildUri("android");
                new HttpFetchTask().execute(builtUri);
            }
        });
    }

    public class HttpFetchTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... uris){
            String uri = uris[0];
            String result = null;
            try{
                result = NetworkHelper.httpGetRequest(uri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String s){
            if(s != null && !s.equals("")){
                Toast.makeText(VotingActivity.this, "Pa, reklo bi se da je uspelo :)",Toast.LENGTH_SHORT).show();
                Log.i("Mahab", s);
            }
        }

    }
}
