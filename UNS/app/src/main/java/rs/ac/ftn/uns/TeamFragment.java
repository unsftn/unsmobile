package rs.ac.ftn.uns;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import rs.ac.ftn.uns.model.Competitor;
import rs.ac.ftn.uns.model.CompetitorsContent;
import rs.ac.ftn.uns.utils.NetworkHelper;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class TeamFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private RecyclerView adapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TeamFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TeamFragment newInstance(int columnCount) {
        TeamFragment fragment = new TeamFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // fetch data from server
        String android_id = Settings.Secure.getString(getActivity().getBaseContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        new HttpFetchTask().execute(android_id);

        View view = inflater.inflate(R.layout.fragment_team_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(new MyTeamRecyclerViewAdapter(CompetitorsContent.ITEMS, mListener));
            this.adapter = recyclerView;
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Competitor item);
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
            updateCompetitorsList(result);

            if(result != null && !result.equals("")){
                Toast.makeText(getActivity(), "Pa, reklo bi se da je uspelo :)",Toast.LENGTH_SHORT).show();
                Log.i("Mahab", result);
            }
        }

        private void updateCompetitorsList(String result) {
            String teams = null;
            ArrayList<Competitor> competitors = new ArrayList<>();
            JSONObject jsonResult = null;

            try {
                jsonResult = new JSONObject(result);
                teams = jsonResult.getString("teams");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Gson gson = new Gson();
            Type listType = new TypeToken<List<Competitor>>(){}.getType();
            competitors = (ArrayList<Competitor>) gson.fromJson(teams, listType);

            CompetitorsContent.ITEMS = competitors;
            adapter.setAdapter(new MyTeamRecyclerViewAdapter(competitors));
            adapter.invalidate();
        }

    }
    /*
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
    */
}
