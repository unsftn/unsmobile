package rs.ac.uns;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;

import java.util.Locale;

import rs.ac.uns.utils.DatabaseHelper;


public class MainActivity extends AppCompatActivity {

    private Menu menu;


    private static String localization = "localization";
    private static String language_locale_sr = "sr";
    private static String language_locale_en = "en";

    private SharedPreferences sharedPreferences;
    private static String UNSAPP_PREFS = "unsapp_prefs";


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        Locale locale = new Locale("sr_RS");
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getApplicationContext().getResources().updateConfiguration(config, null);
        */

        sharedPreferences = getSharedPreferences(UNSAPP_PREFS, MODE_PRIVATE);
        String savedLocale = sharedPreferences.getString(localization, language_locale_sr);
        Log.i("Mahab", savedLocale + " is the saved value");

        Locale locale = new Locale(savedLocale);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initListeners();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.menu = menu;
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    public void updateOptionsMenu(){
        if (menu != null) {
            onPrepareOptionsMenu(menu);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.english_localization) {

            Locale locale = new Locale(language_locale_en);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(localization, language_locale_en);
            editor.commit();
            initListeners();

            updateOptionsMenu();

            return true;
        }
        if (id == R.id.serbian_localization) {

            Locale locale = new Locale(language_locale_sr);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,
                    getBaseContext().getResources().getDisplayMetrics());

            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(localization, language_locale_sr);
            editor.commit();
            initListeners();

            updateOptionsMenu();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initListeners(){

        ImageButton buttonFaculties = (ImageButton) findViewById(R.id.facultiesButton);
        buttonFaculties.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FacultiesActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonBR = (ImageButton) findViewById(R.id.busRoutesButton);
        buttonBR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BusRoutesActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonDormitories = (ImageButton) findViewById(R.id.dormitoriesButton);
        buttonDormitories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DormitoriesActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonDining = (ImageButton) findViewById(R.id.diningButton);
        buttonDining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DiningActivity.class);
                startActivity(intent);
            }
        });
        ImageButton buttonNL = (ImageButton) findViewById(R.id.notableLocationsButton);
        buttonNL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NotableLocationsActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonVo = (ImageButton) findViewById(R.id.voteButton);
        buttonVo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, VotingActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonWebsite = (ImageButton) findViewById(R.id.websiteButton);
        buttonWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.uns.ac.rs/index.php";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        ImageButton buttonPeople = (ImageButton) findViewById(R.id.peopleButton);
        buttonPeople.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PeopleActivity.class);
                startActivity(intent);
            }
        });

        ImageButton buttonAbout = (ImageButton) findViewById(R.id.aboutButton);
        buttonAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(intent);
            }
        });


        ImageButton buttonScienceFestival = (ImageButton) findViewById(R.id.voteButton);
        buttonScienceFestival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ScienceFestivalActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onDestroy(){
        DatabaseHelper.getInstance(MainActivity.this).close();

        super.onDestroy();
    }
}
