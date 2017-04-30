package rs.ac.ftn.uns.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ntodo on 29-Apr-17.
 */

@DatabaseTable(tableName = NotableLocation.TABLE_NAME_NOTABLE_LOCATION)
public class NotableLocation {
    public static final String TABLE_NAME_NOTABLE_LOCATION = "notable_location";

    public static final String FIELD_NAME_NOTABLE_LOCATION_NAME = "name_notable_location";
    public static final String FIELD_NAME_NOTABLE_LOCATION_INFO = "info_notable_location";

    public static final String FIELD_NAME_ROUTE_POINT_LONGITUDE = "longitude_notable_location";
    public static final String FIELD_NAME_ROUTE_POINT_LATITUDE = "latitude_notable_location";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = NotableLocation.FIELD_NAME_NOTABLE_LOCATION_NAME)
    private String name;

    @DatabaseField(columnName = NotableLocation.FIELD_NAME_NOTABLE_LOCATION_INFO)
    private String info;

    @DatabaseField(columnName = NotableLocation.FIELD_NAME_ROUTE_POINT_LONGITUDE)
    private Double longitude;

    @DatabaseField(columnName = NotableLocation.FIELD_NAME_ROUTE_POINT_LATITUDE)
    private Double latitude;

    public NotableLocation() {
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
