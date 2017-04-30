package rs.ac.ftn.uns;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.sql.SQLException;
import java.util.List;

import rs.ac.ftn.uns.database.DatabaseHelper;
import rs.ac.ftn.uns.model.NotableLocation;
import rs.ac.ftn.uns.model.RoutePoint;
import rs.ac.ftn.uns.model.BusRoute;

public class BusRoutesActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bus_routes);
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

        DatabaseHelper db = DatabaseHelper.getInstance(this);
        List<BusRoute> list = null;
        try {
            list = db.getBusRouteDao().queryForAll();
            BusRoute br = list.get(0);
            PolylineOptions poptions = new PolylineOptions();

            RoutePoint first = null;
            for(RoutePoint gp : br.getRoutePoints()){
                if(first == null)
                    first =  gp;
                poptions.add(new LatLng(gp.getLatitude(), gp.getLongitude()));
            }

            poptions.add(new LatLng(first.getLatitude(),first.getLongitude()));
            poptions.width(5).color(Color.CYAN).geodesic(true);
            mMap.addPolyline(poptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(first.getLatitude(),first.getLongitude()), 18));


            List<NotableLocation> nllist = db.getNotableLocationDao().queryForAll();
            NotableLocation home = nllist.get(0);

            mMap.addMarker(new MarkerOptions().position(new LatLng(home.getLatitude(),home.getLongitude())).title(home.getName()).snippet(home.getInfo()));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
