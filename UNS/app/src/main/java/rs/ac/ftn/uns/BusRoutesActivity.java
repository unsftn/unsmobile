package rs.ac.ftn.uns;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class BusRoutesActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_bus_routes);


        Button btnBusRoutes = (Button) findViewById(R.id.btnBusRoutes);
        btnBusRoutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.gspns.co.rs/mreza";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));

                startActivity(i);
            }
        });

    }
}
