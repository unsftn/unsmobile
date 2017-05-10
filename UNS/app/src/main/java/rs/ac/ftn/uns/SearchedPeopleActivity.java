package rs.ac.ftn.uns;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import rs.ac.ftn.uns.model.ABItemContent;
import rs.ac.ftn.uns.model.ABItem;
import rs.ac.ftn.uns.utils.DatabaseHelper;

public class SearchedPeopleActivity extends AppCompatActivity implements ABItemFragment.OnListFragmentInteractionListener {
    /*
    private static String localization = "localization";
    private static String language_locale_sr = "sr";
    private static String language_locale_en = "en";

    private SharedPreferences sharedPreferences;
    private static String UNSAPP_PREFS = "unsapp_prefs";
    */
    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        sharedPreferences = getSharedPreferences(UNSAPP_PREFS, MODE_PRIVATE);
        String savedLocale = sharedPreferences.getString(localization, language_locale_sr);
        Log.i("Mahab", savedLocale + " is the saved value");

        Locale locale = new Locale(savedLocale);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

                */

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
