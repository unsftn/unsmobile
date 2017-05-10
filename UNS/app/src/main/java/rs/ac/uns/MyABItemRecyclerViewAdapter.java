package rs.ac.uns;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import rs.ac.uns.ABItemFragment.OnListFragmentInteractionListener;
import rs.ac.uns.model.ABItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link ABItem and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyABItemRecyclerViewAdapter extends RecyclerView.Adapter<MyABItemRecyclerViewAdapter.ViewHolder> {

    private final List<ABItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyABItemRecyclerViewAdapter(List<ABItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_abitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        String fullName = mValues.get(position).getTitle() + " " + mValues.get(position).getName()
                + " " + mValues.get(position).getSurname() + "\n" +
                ((SearchedPeopleActivity)mListener).getResources().getString(R.string.locale)+
                ": " + mValues.get(position).getLocale();
        holder.mIdView.setText(fullName);
        String workPlace = mValues.get(position).getInstitution() +" - " + mValues.get(position).getWork_place();
        holder.mContentView.setText(workPlace);

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
        public ABItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
