package rs.ac.ftn.uns;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {

    private TextView txtAboutTitle;
    private TextView txtAboutContent;



    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.about_university:
                    txtAboutTitle.setText(R.string.about_university_title);
                    txtAboutContent.setText(R.string.about_university);
                    return true;
                case R.id.about_application:
                    txtAboutTitle.setText(R.string.about_application_title);
                    txtAboutContent.setText(R.string.about_application);
                    return true;
            }
            return false;
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        txtAboutTitle = (TextView) findViewById(R.id.about_title);
        txtAboutContent= (TextView) findViewById(R.id.about_content);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
