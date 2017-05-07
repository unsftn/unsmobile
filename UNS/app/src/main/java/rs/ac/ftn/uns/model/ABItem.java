package rs.ac.ftn.uns.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = ABItem.TABLE_NAME_AB_ITEM)
public class ABItem {

    public static final String TABLE_NAME_AB_ITEM = "ab_item";

    public static final String FIELD_NAME_AB_ITEM_TITLE = "title_ab_item";
    public static final String FIELD_NAME_AB_ITEM_NAME = "name_ab_item";
    public static final String FIELD_NAME_AB_ITEM_SURNAME = "surname_ab_item";
    public static final String FIELD_NAME_AB_ITEM_LOCALE = "locale_ab_item";
    public static final String FIELD_NAME_AB_ITEM_WORK_PLACE = "work_place_ab_item";
    public static final String FIELD_NAME_AB_ITEM_INSTITUTION = "institution_ab_item";

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(columnName = ABItem.FIELD_NAME_AB_ITEM_TITLE)
    private String title;

    @DatabaseField(columnName = ABItem.FIELD_NAME_AB_ITEM_NAME)
    private String name;

    @DatabaseField(columnName = ABItem.FIELD_NAME_AB_ITEM_SURNAME)
    private String surname;

    @DatabaseField(columnName = ABItem.FIELD_NAME_AB_ITEM_LOCALE)
    private String locale;

    @DatabaseField(columnName = ABItem.FIELD_NAME_AB_ITEM_WORK_PLACE)
    private String work_place;

    @DatabaseField(columnName = ABItem.FIELD_NAME_AB_ITEM_INSTITUTION)
    private String institution;

    public ABItem(){}

    public ABItem(String title, String name, String surname, String locale, String work_place, String institution){
        this.title = title;
        this.name = name;
        this.surname = surname;
        this.locale = locale;
        this.work_place = work_place;
        this.institution = institution;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public String getWork_place() {
        return work_place;
    }

    public void setWork_place(String work_place) {
        this.work_place = work_place;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }
}
