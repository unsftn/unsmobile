package rs.ac.uns;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import rs.ac.uns.model.NotableLocation;
import rs.ac.uns.utils.DatabaseHelper;

public class DiningActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.clear();

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        List<NotableLocation> nLlist = null;
        nLlist = db.getNotableLocationsByType(NotableLocation.TYPE_CANTEEN);

        for (NotableLocation nl: nLlist) {
            mMap.addMarker(new MarkerOptions().position(new LatLng(nl.getLatitude(),nl.getLongitude())).title(nl.getName()).snippet(nl.getInfo()));
        }

        // campus display
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.2456205,19.8497552), 15));
    }
}
