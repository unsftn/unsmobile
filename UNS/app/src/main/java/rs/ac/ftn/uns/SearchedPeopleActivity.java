package rs.ac.ftn.uns;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import rs.ac.ftn.uns.model.ABItemContent;
import rs.ac.ftn.uns.model.ABItem;
import rs.ac.ftn.uns.utils.DatabaseHelper;

public class SearchedPeopleActivity extends AppCompatActivity implements ABItemFragment.OnListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_people);

        String name = (String) getIntent().getStringExtra("name");
        String surname = (String) getIntent().getStringExtra("surname");
        String institution = (String) getIntent().getStringExtra("institution");
        String work_place = (String) getIntent().getStringExtra("work_place");



        List<ABItem> abi = DatabaseHelper.getInstance(SearchedPeopleActivity.this).getABItemsByParams(name, surname, institution);



        ABItemContent.cleanItems();
        for(ABItem item : abi) {
            ABItemContent.addItem(item);
        }

        if(abi.isEmpty()) {
            Toast.makeText(SearchedPeopleActivity.this, getResources().getString(R.string.people_search_no_results), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onListFragmentInteraction(ABItem item) {

    }
}
