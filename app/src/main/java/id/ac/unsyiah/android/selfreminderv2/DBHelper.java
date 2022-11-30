package id.ac.unsyiah.android.selfreminderv2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "reminder";

    // below int is our database version
    private static final int DB_VERSION = 1;

    // below variable is for our table name.
    private static final String TABLE_NAME = "myreminder";

    // below variable is for our id column.
    private static final String ID_COL = "id";

    // below variable is for our course name column
    private static final String NAME_COL = "name";

    // below variable id for our course duration column.
    private static final String TIME_COL = "time";

    private static final String ICON_COL = "icon";

    // creating a constructor for our database handler.
    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    // below method is for creating a database by running a sqlite query
    @Override
    public void onCreate(SQLiteDatabase db) {
        // on below line we are creating
        // an sqlite query and we are
        // setting our column names
        // along with their data types.
        String query = "CREATE TABLE " + TABLE_NAME + " ("
                + ID_COL + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME_COL + " TEXT,"
                + TIME_COL + " TEXT,"
                + ICON_COL + " TEXT)";

        // at last we are calling a exec sql
        // method to execute above sql query
        db.execSQL(query);
    }

    // this method is use to add new reminder to our sqlite database.
    public void addNewReminder(String name, String time, String icon) {

        // on below line we are creating a variable for
        // our sqlite database and calling writable method
        // as we are writing data in our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are creating a
        // variable for content values.
        ContentValues values = new ContentValues();

        // on below line we are passing all values
        // along with its key and value pair.
        values.put(NAME_COL, name);
        values.put(TIME_COL, time);
        values.put(ICON_COL, icon);

        // after adding all values we are passing
        // content values to our table.
        db.insert(TABLE_NAME, null, values);

        // at last we are closing our
        // database after adding database.
        db.close();
    }

    // we have created a new method for reading all the reminder.
    public ArrayList<ViewConGetSet> reminderRead() {
        // on below line we are creating a
        // database for reading our database.
        SQLiteDatabase db = this.getReadableDatabase();

        // on below line we are creating a reminder with query to read data from database.
        Cursor cursorReminder = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        // on below line we are creating a new array list.
        ArrayList<ViewConGetSet> viewConGetSetArrayList = new ArrayList<>();

        // moving our reminder to first position.
        if (cursorReminder.moveToFirst()) {
            do {
                // on below line we are adding the data from cursor to our array list.
                viewConGetSetArrayList.add(new ViewConGetSet(cursorReminder.getString(1),
                        cursorReminder.getString(2),
                        cursorReminder.getString(3),
                        cursorReminder.getInt(0)));
            } while (cursorReminder.moveToNext());
            // moving our cursor to next.
        }
        // at last closing our reminder
        // and returning our array list.
        cursorReminder.close();
        return viewConGetSetArrayList;
    }

    // below is the method for deleting our reminder.
    public int delete(int id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME, "id=?", new String[]{String.valueOf(id)});

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // this method is called to check if the table exists already.
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }


}

