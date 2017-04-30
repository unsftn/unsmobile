package rs.ac.ftn.uns.model;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by ntodo on 29-Apr-17.
 */

@DatabaseTable(tableName = BusRoute.TABLE_NAME_BUS_ROUTE)
public class BusRoute {

    public static final String TABLE_NAME_BUS_ROUTE = "bus_route";

    public static final String FIELD_NAME_BUS_ROUTE_NAME = "name_bus_route";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = BusRoute.FIELD_NAME_BUS_ROUTE_NAME)
    private String name;

    @ForeignCollectionField(eager = true)
    private ForeignCollection<RoutePoint> routePoints;

    public BusRoute() {}

    public ForeignCollection<RoutePoint> getRoutePoints(){
        return routePoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
