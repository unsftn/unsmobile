package rs.ac.ftn.uns;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.sql.SQLException;
import java.util.List;

import rs.ac.ftn.uns.model.ABItem;
import rs.ac.ftn.uns.utils.DatabaseHelper;

public class PeopleActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        WebView view = new WebView(this);
        view.setVerticalScrollBarEnabled(false);

        ((LinearLayout)findViewById(R.id.web_view_content)).addView(view);

        view.loadData(getString(R.string.address_book_text), "text/html; charset=utf-8", "utf-8");

        /*
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        List<ABItem> aBlist = null;
        try {
            aBlist = db.getABItemDao().queryForAll();
            ABItem first = aBlist.get(0);
;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
        Button button = (Button) findViewById(R.id.people_search_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText et_name = (EditText) findViewById(R.id.people_name_input);
                String name = et_name.getText().toString();

                EditText et_surname = (EditText) findViewById(R.id.people_surname_input);
                String surname = et_surname.getText().toString();

                Spinner sp_work_place = (Spinner) findViewById(R.id.people_work_place);
                String work_place = sp_work_place.getSelectedItem().toString();

                Spinner sp_institution = (Spinner) findViewById(R.id.people_institution);
                String institution = sp_institution.getSelectedItem().toString();

                if(name.trim().equals("") && surname.trim().equals("") &&
                        institution.equals(getResources().getString(R.string.i_blank)) && work_place.equals(getResources().getString(R.string.wp_blank))) {

                    Toast.makeText(PeopleActivity.this, "Nothing to search by!", Toast.LENGTH_SHORT).show();

                    return;
                }

                Intent intent = new Intent(PeopleActivity.this, SearchedPeopleActivity.class);

                if(!name.trim().equals("")){
                    intent.putExtra("name", name);
                }

                if(!surname.trim().equals("")){
                    intent.putExtra("surname", surname);
                }

                if(!institution.equals(getResources().getString(R.string.i_blank))){
                    intent.putExtra("institution", institution);
                }

                if(!work_place.equals(getResources().getString(R.string.wp_blank))){
                    intent.putExtra("work_place", work_place);
                }

                startActivity(intent);
            }
        });
    }
}
