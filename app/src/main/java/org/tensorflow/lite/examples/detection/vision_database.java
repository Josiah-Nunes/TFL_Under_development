package org.tensorflow.lite.examples.detection;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class vision_database {

    public static final String USER_NAME = "users_name";
    public static final String USER_CONTACT = "user_contact";
    public static final String USER_EMER1 = "users_emergency1";
    public static final String USER_EMER2 = "users_emergency2";

    private static final String DATABASE_NAME = "visionDatabase";
    private static final String TABLE_NAME = "users";
    private static final int DATABASE_VERSION = 1;

    private DbHelper helper;
    private final Context passedContext;
    private SQLiteDatabase database;


    private static class DbHelper extends SQLiteOpenHelper {

        //constructor
        public DbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            String createDatabaseQuery = "CREATE  TABLE " + TABLE_NAME +
                    " (" + USER_NAME + " VARCHAR, "  + USER_CONTACT + " VARCHAR, "
                    + USER_EMER1 + " VARCHAR, " + USER_EMER2 + " VARCHAR)";
            db.execSQL(createDatabaseQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

    //constructor
    public vision_database(Context c) {
        passedContext = c;
    }

    //method to open database
    public vision_database open() throws SQLException {
        helper = new DbHelper(passedContext);
        database = helper.getWritableDatabase();
        return this;
    }

    //method to close database
    public void close() {
        helper.close();
    }

    //Creating Entry of row
    public long createEntry(String name,  String phone, String emergency1, String emergency2) {
        ContentValues cv = new ContentValues();
        cv.put(USER_NAME, name);
        cv.put(USER_CONTACT, phone);
        cv.put(USER_EMER1, emergency1);
        cv.put(USER_EMER2, emergency2);
        return database.insert(TABLE_NAME, null, cv);

    }

    public String getData() {
        String[] columns = new String[]{USER_NAME, USER_CONTACT, USER_EMER1, USER_EMER2};
        Cursor c = database.query(TABLE_NAME, columns, null, null, null, null, null, null);
        String result = "";
        int iName = c.getColumnIndex(USER_NAME);
        int iCon = c.getColumnIndex(USER_CONTACT);
        int iEmer1 = c.getColumnIndex(USER_EMER1);
        int iEmer2 = c.getColumnIndex(USER_EMER2);

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            result = result + c.getString(iName) + " " + c.getString(iCon)
                    + " " + c.getString(iEmer1) + " " + c.getString(iEmer2) + "\n";
        }
        return result;
    }
}
