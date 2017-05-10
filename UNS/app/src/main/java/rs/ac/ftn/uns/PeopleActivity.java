package rs.ac.ftn.uns;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
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
import java.util.Locale;

import rs.ac.ftn.uns.model.ABItem;
import rs.ac.ftn.uns.utils.DatabaseHelper;

public class PeopleActivity extends AppCompatActivity {

    private static String localization = "localization";
    private static String language_locale_sr = "sr";
    private static String language_locale_en = "en";

    private SharedPreferences sharedPreferences;
    private static String UNSAPP_PREFS = "unsapp_prefs";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        sharedPreferences = getSharedPreferences(UNSAPP_PREFS, MODE_PRIVATE);
        String savedLocale = sharedPreferences.getString(localization, language_locale_sr);
        Log.i("Mahab", savedLocale + " is the saved value");

        Locale locale = new Locale(savedLocale);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());



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

                Spinner sp_institution = (Spinner) findViewById(R.id.people_institution);
                String institution = sp_institution.getSelectedItem().toString();

                if(name.trim().equals("") && surname.trim().equals("") &&
                        (institution.equals("-- Svi fakulteti --") || institution.equals("-- All fakulties --"))){

                    Toast.makeText(PeopleActivity.this, getResources().getString(R.string.people_empty_form), Toast.LENGTH_SHORT).show();

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

                startActivity(intent);
            }
        });
    }
}
