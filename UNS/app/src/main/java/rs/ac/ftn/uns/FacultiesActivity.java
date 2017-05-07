package rs.ac.ftn.uns;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;

public class FacultiesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculties);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initButtons();
    }

    private void initButtons() {
        addMenuItemClickURL(R.id.ftnButton, "http://www.ftn.uns.ac.rs");
        addMenuItemClickURL(R.id.pmfButton, "http://www.pmf.uns.ac.rs");
        addMenuItemClickURL(R.id.agricultureButton, "http://polj.uns.ac.rs");
        addMenuItemClickURL(R.id.lawButton, "http://www.pf.uns.ac.rs");
        addMenuItemClickURL(R.id.medicineButton, "http://www.medical.uns.ac.rs");
        addMenuItemClickURL(R.id.philosophyButton, "http://www.ff.uns.ac.rs");
        addMenuItemClickURL(R.id.economicsButton, "http://www.ef.uns.ac.rs");
        addMenuItemClickURL(R.id.technologyButton, "http://www.tf.uns.ac.rs");
        addMenuItemClickURL(R.id.artsAcademyButton, "http://www.akademija.uns.ac.rs");
        addMenuItemClickURL(R.id.civilEngineeringButton, "http://www.gf.uns.ac.rs");
        addMenuItemClickURL(R.id.pupinButton, "http://www.tfzr.uns.ac.rs");
        addMenuItemClickURL(R.id.difButton, "http://www.fsfvns.rs");
        addMenuItemClickURL(R.id.educationButton, "http://www.pef.uns.ac.rs");
        addMenuItemClickURL(R.id.eduHunButton, "http://www.magister.uns.ac.rs");
    }

    private void addMenuItemClickURL(int buttonId, final String url) {
        ImageButton buttonWebsite = (ImageButton) findViewById(buttonId);
        buttonWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openURLActivity(url);
            }
        });
    }

    private void openURLActivity(String url) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
