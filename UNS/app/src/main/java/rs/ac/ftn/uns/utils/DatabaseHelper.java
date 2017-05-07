package rs.ac.ftn.uns.utils;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

import rs.ac.ftn.uns.model.ABItem;
import rs.ac.ftn.uns.model.NotableLocation;


/**
 * Created by ntodo on 28-Apr-17.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME    = "application_data.db";
    private static final int    DATABASE_VERSION = 1;

    private Dao<NotableLocation, Integer> nLDao = null;
    private Dao<ABItem, Integer> aBIDao = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, NotableLocation.class);
            TableUtils.createTable(connectionSource, ABItem.class);

            Log.i("Mahab", "Am here, creating tables.");

            getNotableLocationDao();
            getABItemDao();

            NotableLocation nl = new NotableLocation("Name: home", "Info: sweet home", NotableLocation.TYPE_INSTITUTION, 19.369306, 45.253232);
            nLDao.create(nl);


            ABItem abi = new ABItem("dr", "Biljana", "Abramović", "2008", "Prorektor za nastavu", "Univerzitet u Novom Sadu");
            aBIDao.create(abi);
            abi = new ABItem("", "Ljiljana", "Mićović", "2020", "Šef kabineta", "Univerzitet u Novom Sadu");
            aBIDao.create(abi);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, NotableLocation.class, true);
            TableUtils.dropTable(connectionSource, ABItem.class, true);

            onCreate(db, connectionSource);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /* DAOS */
    public Dao<NotableLocation, Integer> getNotableLocationDao() throws SQLException {
        if (nLDao == null) {
            nLDao = getDao(NotableLocation.class);
        }

        return nLDao;
    }

    public Dao<ABItem, Integer> getABItemDao() throws SQLException {
        if (aBIDao == null) {
            aBIDao = getDao(ABItem.class);
        }

        return aBIDao;
    }

    @Override
    public void close() {

        nLDao = null;
        aBIDao = null;
        super.close();
    }

    private static DatabaseHelper sDatabaseHelper;

    public static DatabaseHelper getInstance(Context context) {
        if(sDatabaseHelper == null)
            sDatabaseHelper = new DatabaseHelper(context);

        return sDatabaseHelper;
    }

    public List<ABItem> getABItemsByParams(String name, String surname, String institution, String work_place){

        try {
            QueryBuilder<ABItem, Integer> qb = getABItemDao().queryBuilder();
            Where<ABItem, Integer> where = qb.where();
            boolean first = true;
            if(name != null){
                where.eq(ABItem.FIELD_NAME_AB_ITEM_NAME, name);
                first = false;
            }

            if(surname != null){

                if(!first) {
                    where.and();

                }else{
                    first = false;
                }
                where.eq(ABItem.FIELD_NAME_AB_ITEM_SURNAME, surname);
            }

            if(work_place != null){

                if(!first) {
                    where.and();

                }else{
                    first = false;
                }
                where.eq(ABItem.FIELD_NAME_AB_ITEM_WORK_PLACE, work_place);
            }

            if(institution != null){

                if(!first) {
                    where.and();

                }else{
                    first = false;
                }
                where.eq(ABItem.FIELD_NAME_AB_ITEM_INSTITUTION, institution);
            }

            List<ABItem> lABI = where.query();
            return lABI;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<NotableLocation> getNotableLocationsByType(String type){
        try {
            List<NotableLocation> retList = getNotableLocationDao().queryBuilder()
                    .where().eq(NotableLocation.FIELD_NAME_NOTABLE_LOCATION_TYPE, type).query();

            return retList;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

}
