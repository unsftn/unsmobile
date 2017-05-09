package rs.ac.ftn.uns;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rs.ac.ftn.uns.TeamFragment.OnListFragmentInteractionListener;
import rs.ac.ftn.uns.model.Competitor;

import java.util.List;


public class MyTeamRecyclerViewAdapter extends RecyclerView.Adapter<MyTeamRecyclerViewAdapter.ViewHolder> {

    private  List<Competitor> mValues;
    private  OnListFragmentInteractionListener mListener = null;

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
        holder.mContentView.setText(mValues.get(position).projectName);
        holder.mScientificAreaView.setText(mValues.get(position).researchField);
        holder.mProjectDescription.setText(mValues.get(position).projectDesc);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
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
        public final TextView mContentView;
        public final TextView mScientificAreaView;
        public final TextView mProjectDescription;
        public Competitor mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            mScientificAreaView = (TextView) view.findViewById(R.id.scientific_area);
            mProjectDescription = (TextView) view.findViewById(R.id.project_description);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
