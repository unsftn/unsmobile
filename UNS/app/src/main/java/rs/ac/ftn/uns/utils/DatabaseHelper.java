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
import java.util.ArrayList;
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

    private Context ctx;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.ctx = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, NotableLocation.class);
            TableUtils.createTable(connectionSource, ABItem.class);

            Log.i("Mahab", "Am here, creating tables.");

            getNotableLocationDao();
            getABItemDao();

            nLDao.create(initInstitutionLocations());
            nLDao.create(initDormitoriesLocations());
            nLDao.create(initCanteenLocations());

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

    private String getStringResourceByName(String aString) {
        String packageName = ctx.getPackageName();
        int resId = ctx.getResources()
                .getIdentifier(aString, "string", packageName);
        if (resId == 0) {
            return aString;
        } else {
            return ctx.getString(resId);
        }
    }

    private ArrayList<NotableLocation> initInstitutionLocations() {
        ArrayList<NotableLocation> notableLocations = new ArrayList<>();

        NotableLocation nl = new NotableLocation(getStringResourceByName("rectory_title"), getStringResourceByName("rectory_desc"), NotableLocation.TYPE_INSTITUTION,  19.854033, 45.247292);
        NotableLocation n2 = new NotableLocation(getStringResourceByName("ftn_title"), "", NotableLocation.TYPE_INSTITUTION, 19.851415, 45.246111);
        NotableLocation n3 = new NotableLocation(getStringResourceByName("pmf_title"), "", NotableLocation.TYPE_INSTITUTION, 19.852786, 45.245412);
        NotableLocation n4 = new NotableLocation(getStringResourceByName("philosophy_title"), "", NotableLocation.TYPE_INSTITUTION, 19.853481, 45.246807);
        NotableLocation n5 = new NotableLocation(getStringResourceByName("medicine_title"), "", NotableLocation.TYPE_INSTITUTION, 19.823697, 45.252833);
        NotableLocation n6 = new NotableLocation(getStringResourceByName("economics_title"), "", NotableLocation.TYPE_INSTITUTION, 19.676470, 46.100616);
        NotableLocation n7 = new NotableLocation(getStringResourceByName("law_title"), "", NotableLocation.TYPE_INSTITUTION, 19.852957, 45.246298);
        NotableLocation n8 = new NotableLocation(getStringResourceByName("agriculture_title"), "", NotableLocation.TYPE_INSTITUTION, 19.851735, 45.247538);
        NotableLocation n9 = new NotableLocation(getStringResourceByName("arts_academy_title"), "", NotableLocation.TYPE_INSTITUTION, 19.843696, 45.258615);
        NotableLocation n10 = new NotableLocation(getStringResourceByName("civil_engineering_title"), "", NotableLocation.TYPE_INSTITUTION, 19.650746, 46.111839);
        NotableLocation n11 = new NotableLocation(getStringResourceByName("technology_title"), "", NotableLocation.TYPE_INSTITUTION, 19.851009, 45.247819);
        NotableLocation n12 = new NotableLocation(getStringResourceByName("pupin_title"), "", NotableLocation.TYPE_INSTITUTION, 20.382006, 45.379584);
        NotableLocation n13 = new NotableLocation(getStringResourceByName("dif_title"), "", NotableLocation.TYPE_INSTITUTION, 19.847920, 45.247239);
        NotableLocation n14 = new NotableLocation(getStringResourceByName("education_title"), "", NotableLocation.TYPE_INSTITUTION, 19.122061, 45.768172);
        NotableLocation n15 = new NotableLocation(getStringResourceByName("edu_hun_title"), "", NotableLocation.TYPE_INSTITUTION, 19.665671, 46.098112);
        NotableLocation n16 = new NotableLocation("Rekreativni centar", "", NotableLocation.TYPE_INSTITUTION,  19.853623, 45.244084);
        NotableLocation n17 = new NotableLocation("Zavod za zdravstvenu zaštitu studenata", "", NotableLocation.TYPE_INSTITUTION,  19.849412, 45.245867);


        notableLocations.add(nl);
        notableLocations.add(n2);
        notableLocations.add(n3);
        notableLocations.add(n4);
        notableLocations.add(n5);
        notableLocations.add(n6);
        notableLocations.add(n7);
        notableLocations.add(n8);
        notableLocations.add(n9);
        notableLocations.add(n10);
        notableLocations.add(n11);
        notableLocations.add(n12);
        notableLocations.add(n13);
        notableLocations.add(n14);
        notableLocations.add(n15);
        notableLocations.add(n16);
        notableLocations.add(n17);

        return notableLocations;
    }

    private ArrayList<NotableLocation> initDormitoriesLocations() {
        ArrayList<NotableLocation> notableLocations = new ArrayList<>();

        NotableLocation nl = new NotableLocation("Studentski dom Slobodan Bajić", "", NotableLocation.TYPE_DORMITORY,  19.849363, 45.245123);
        NotableLocation n2 = new NotableLocation("Studentski dom Veljko Vlahović", "", NotableLocation.TYPE_DORMITORY,  19.848454, 45.245604);
        NotableLocation n3 = new NotableLocation("Uplata stanarine za studentske domove", "", NotableLocation.TYPE_DORMITORY,  19.849269, 45.245492);
        NotableLocation n4 = new NotableLocation("Studentski dom A", "", NotableLocation.TYPE_DORMITORY,  19.840576, 45.236570);
        NotableLocation n5 = new NotableLocation("Studentski dom B", "", NotableLocation.TYPE_DORMITORY,   19.838369, 45.236333);
        NotableLocation n6 = new NotableLocation("Studentski dom C", "", NotableLocation.TYPE_DORMITORY,   19.838671, 45.235765);
        NotableLocation n7 = new NotableLocation("Studentski dom Klara Feješ", "", NotableLocation.TYPE_DORMITORY,  19.836128, 45.245049);
        NotableLocation n8 = new NotableLocation("Studentski dom Sajmište", "", NotableLocation.TYPE_DORMITORY,  19.8267572, 45.2570944);

        notableLocations.add(nl);
        notableLocations.add(n2);
        notableLocations.add(n3);
        notableLocations.add(n4);
        notableLocations.add(n5);
        notableLocations.add(n6);
        notableLocations.add(n7);
        notableLocations.add(n8);

        return notableLocations;
    }

    private ArrayList<NotableLocation> initCanteenLocations() {
        ArrayList<NotableLocation> notableLocations = new ArrayList<>();

        NotableLocation n1 = new NotableLocation("Uplata obroka za menzu", "", NotableLocation.TYPE_CANTEEN,  19.848970, 45.245387);
        NotableLocation n2 = new NotableLocation("Velika menza", "", NotableLocation.TYPE_CANTEEN, 19.849393, 45.246070);
        NotableLocation n3 = new NotableLocation("Mala menza", "", NotableLocation.TYPE_CANTEEN, 19.852067, 45.244620);

        notableLocations.add(n1);
        notableLocations.add(n2);
        notableLocations.add(n3);

        return notableLocations;
    }

    private ArrayList<NotableLocation> initLibrariesLocations() {
        ArrayList<NotableLocation> notableLocations = new ArrayList<>();


        return notableLocations;
    }

    }
