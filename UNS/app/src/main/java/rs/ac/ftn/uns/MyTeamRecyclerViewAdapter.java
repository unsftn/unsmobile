package rs.ac.ftn.uns;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import rs.ac.ftn.uns.TeamFragment.OnListFragmentInteractionListener;
import rs.ac.ftn.uns.model.Competitor;


public class MyTeamRecyclerViewAdapter extends RecyclerView.Adapter<MyTeamRecyclerViewAdapter.ViewHolder> {

    private List<Competitor> mValues;
    private OnListFragmentInteractionListener mListener = null;
    public static View currentView;

    public MyTeamRecyclerViewAdapter(List<Competitor> items) {
        this.mValues = items;
    }

    public MyTeamRecyclerViewAdapter(List<Competitor> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_team, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(String.valueOf(position + 1));
        holder.mTeamName.setText(mValues.get(position).teamName);
        holder.mContentView.setText(mValues.get(position).projectName);
        holder.mScientificAreaView.setText(mValues.get(position).researchField);
        holder.mProjectDescription.setText(mValues.get(position).projectDesc);
        holder.mVotesCount.setText(String.valueOf(mValues.get(position).votesCount));
        holder.mProgressBar.setVisibility(View.GONE);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //holder.mView.setBackgroundColor(0x96ffeb3b);
                    //v.getId()
                    currentView = v;
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mTeamName;
        public final TextView mContentView;
        public final TextView mScientificAreaView;
        public final TextView mProjectDescription;
        public final TextView mVotesCount;
        public final ProgressBar mProgressBar;

        public Competitor mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mTeamName = (TextView) view.findViewById(R.id.team_name);
            mContentView = (TextView) view.findViewById(R.id.content);
            mScientificAreaView = (TextView) view.findViewById(R.id.scientific_area);
            mProjectDescription = (TextView) view.findViewById(R.id.project_description);
            mVotesCount = (TextView) view.findViewById(R.id.votes_count);
            mProgressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
