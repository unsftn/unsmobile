package rs.ac.ftn.uns.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import rs.ac.ftn.uns.model.BusRoute;
import rs.ac.ftn.uns.model.NotableLocation;
import rs.ac.ftn.uns.model.RoutePoint;


/**
 * Created by ntodo on 28-Apr-17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "app_data.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<BusRoute, Integer> mBRDao = null;
    private Dao<RoutePoint, Integer> mRPDao = null;
    private Dao<NotableLocation, Integer> mNLDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, BusRoute.class);
            TableUtils.createTable(connectionSource, RoutePoint.class);
            TableUtils.createTable(connectionSource, NotableLocation.class);

            Log.i("Mahab", "Am here, creating tables.");

            getBusRouteDao();
            getRoutePointDao();
            getNotableLocationDao();

            BusRoute br = new BusRoute();
            br.setName("7");
            mBRDao.create(br);

            RoutePoint gp = new RoutePoint(br, 45.245372, 19.834229);
            mRPDao.create(gp);
            gp = new RoutePoint(br, 45.246106, 19.834887);
            mRPDao.create(gp);
            gp = new RoutePoint(br, 45.245290, 19.836845);
            mRPDao.create(gp);
            gp = new RoutePoint(br, 45.243900, 19.835450);
            mRPDao.create(gp);
            gp = new RoutePoint(br, 45.244808, 19.833696);
            mRPDao.create(gp);

            NotableLocation nl = new NotableLocation();
            nl.setName("Home2");
            nl.setInfo("Home2, sweet home2");
            nl.setLatitude(45.245372);
            nl.setLongitude(19.834229);
            mNLDao.create(nl);


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, BusRoute.class, true);
            TableUtils.dropTable(connectionSource, RoutePoint.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* DAOS */

    public Dao<BusRoute, Integer> getBusRouteDao() throws SQLException {
        if (mBRDao == null) {
            mBRDao = getDao(BusRoute.class);
        }

        return mBRDao;
    }

    public Dao<RoutePoint, Integer> getRoutePointDao() throws SQLException {
        if (mRPDao == null) {
            mRPDao = getDao(RoutePoint.class);
        }

        return mRPDao;
    }

    public Dao<NotableLocation, Integer> getNotableLocationDao() throws SQLException {
        if (mNLDao == null) {
            mNLDao = getDao(NotableLocation.class);
        }

        return mNLDao;
    }

    @Override
    public void close() {
        mBRDao = null;
        mRPDao = null;
        mNLDao = null;

        super.close();
    }

    private static DatabaseHelper sDatabaseHelper;

    public static DatabaseHelper getInstance(Context context) {
        if(sDatabaseHelper == null)
            sDatabaseHelper = new DatabaseHelper(context);

        return sDatabaseHelper;
    }
}
