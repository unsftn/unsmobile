package rs.ac.ftn.uns;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.List;

import rs.ac.ftn.uns.model.ABItem;
import rs.ac.ftn.uns.utils.DatabaseHelper;

public class SearchedPeopleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searched_people);


        String name = (String) getIntent().getStringExtra("name");
        String surname = (String) getIntent().getStringExtra("surname");
        String institution = (String) getIntent().getStringExtra("institution");
        String work_place = (String) getIntent().getStringExtra("work_place");

        Toast.makeText(SearchedPeopleActivity.this, "Do ovde stigoh :P", Toast.LENGTH_SHORT).show();

        List<ABItem> abi = DatabaseHelper.getInstance(SearchedPeopleActivity.this).getABItemsByParams(name, surname, institution, work_place);

    }
}
