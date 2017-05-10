package rs.ac.uns;

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

import rs.ac.uns.model.Competitor;
import rs.ac.uns.model.CompetitorsContent;
import rs.ac.uns.model.Vote;
import rs.ac.uns.utils.NetworkHelper;

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
            recyclerView.setAdapter(new MyTeamRecyclerViewAdapter(CompetitorsContent.ITEMS));
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
            } catch (Exception e) {
                e.printStackTrace();
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result){
            updateCompetitorsList(result);

            if(result != null && !result.equals("")){
                Toast.makeText(getActivity(), getString(R.string.team_fetch_success),Toast.LENGTH_SHORT).show();
                Log.i("Mahab", result);
            } else {
                Toast.makeText(getActivity(), getString(R.string.team_fetch_failure),Toast.LENGTH_SHORT).show();
            }
        }

        private void updateCompetitorsList(String result) {
            String teams = null;
            String votes = null;
            JSONObject activePoll = null;
            JSONObject jsonResult = null;

            try {
                jsonResult = new JSONObject(result);
                teams = jsonResult.getString("teams");
                votes = jsonResult.getString("vote");
                activePoll = jsonResult.getJSONObject("pollActive");

                if(activePoll.getString("itHas").equals("false")) {
                    Toast.makeText(getActivity(), getString(R.string.poll_has_ended),Toast.LENGTH_SHORT).show();
                    return;
                }

                if(votes != null && votes.equals("null")) {
                    CompetitorsContent.HAS_VOTED = false;
                } else {
                    CompetitorsContent.HAS_VOTED = true;
                }

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(teams != null) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Competitor>>() {
                }.getType();
                CompetitorsContent.ITEMS = (ArrayList<Competitor>) gson.fromJson(teams, listType);
            }
            if(votes != null && votes != "" && !votes.equals("null")) {
                Toast.makeText(getActivity(), getString(R.string.already_voted),Toast.LENGTH_SHORT).show();
            }

            new HttpGetVoteResultsTask().execute();
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
        protected void onPostExecute(String s) {
            ArrayList<Vote> votes = null;

            if (s != null) {
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Vote>>() {
                }.getType();
                votes = (ArrayList<Vote>) gson.fromJson(s, listType);

                if(votes != null) {
                    for (Vote v : votes) {
                        for (Competitor c : CompetitorsContent.ITEMS) {
                            if (c.id.equals(v.candidateId)) {
                                c.votesCount = v.countNum;
                            }
                        }
                    }
                }
                adapter.setAdapter(new MyTeamRecyclerViewAdapter(CompetitorsContent.ITEMS, mListener));
                adapter.invalidate();
            }
        }

    }

}
