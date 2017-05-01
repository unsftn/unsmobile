package rs.ac.ftn.uns;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
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

        Button link = (Button) findViewById(R.id.route_link);
        link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToUrl("http://www.gspns.co.rs/red-voznje/gradski");
            }
        });
    }

    private void goToUrl (String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
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
        List<BusRoute> bRList = null;

        try {
            bRList = db.getBusRouteDao().queryForAll();

            for(BusRoute br: bRList){
                PolylineOptions poptions = new PolylineOptions();

                RoutePoint first = null;
                for(RoutePoint gp : br.getRoutePoints()){
                    if(first == null)
                        first =  gp;
                    poptions.add(new LatLng(gp.getLatitude(), gp.getLongitude()));
                }

                poptions.add(new LatLng(first.getLatitude(),first.getLongitude()));
                poptions.width(5).color(Color.CYAN).geodesic(true);
                Polyline p = mMap.addPolyline(poptions);
                p.setClickable(true);
                p.setTag(br);
            }

            //TODO determine the right dot! Location?
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.245372, 19.834229), 18));

            mMap.setOnPolylineClickListener(new GoogleMap.OnPolylineClickListener() {
                @Override
                public void onPolylineClick(Polyline polyline) {
                    BusRoute br = (BusRoute) polyline.getTag();
                    TextView tv = (TextView) findViewById(R.id.route_name);

                    String str = getResources().getString(R.string.route);
                    str+= " " + br.getName();
                    tv.setText(str);

                    LinearLayout lL = (LinearLayout) findViewById(R.id.route_info);
                    lL.setVisibility(View.VISIBLE);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
