package rs.ac.ftn.uns.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ntodo on 29-Apr-17.
 */
@DatabaseTable(tableName = RoutePoint.TABLE_NAME_ROUTE_POINT)
public class RoutePoint {

    public static final String TABLE_NAME_ROUTE_POINT = "route_points";

    public static final String FIELD_NAME_ROUTE_POINT_LONGITUDE = "longitude_route_point";
    public static final String FIELD_NAME_ROUTE_POINT_LATITUDE = "latitude_route_point";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = RoutePoint.FIELD_NAME_ROUTE_POINT_LONGITUDE)
    private Double longitude;

    @DatabaseField(columnName = RoutePoint.FIELD_NAME_ROUTE_POINT_LATITUDE)
    private Double latitude;

    @DatabaseField(foreign = true, foreignAutoRefresh = true)
    private BusRoute busRoute;

    public RoutePoint(){}

    public RoutePoint(BusRoute busRoute, Double latitude, Double longitude) {
        this.busRoute = busRoute;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public int getId() {
        return id;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getLatitude() {
        return latitude;
    }
}
