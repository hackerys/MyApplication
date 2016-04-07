package com.jansen.myapplication.utils;

/**
 * Created Jansen on 2016/3/29.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.SimpleCursorAdapter;

import com.jansen.myapplication.R;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBHelper extends SQLiteOpenHelper {
    private static String DB_PATH = "/data/data/name.dohkoos.linkage/databases/";
    private static String DB_NAME = "xzqh.db";
    private static DBHelper databaseHelper;
    private static SQLiteDatabase db;

    private Context context;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    public static DBHelper getInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new DBHelper(context);
            databaseHelper.openDataBase();

            if (db == null) {
                try {
                    db = databaseHelper.getWritableDatabase();
                    databaseHelper.copyDatabase();
                }
                catch (Exception e) {
                    Log.d("DBHelper", "Error in database creation");
                }

                databaseHelper.openDataBase();
            }
        }
        return databaseHelper;
    }

    private void copyDatabase() throws IOException {
        InputStream is = context.getResources().openRawResource(R.raw.zone);
        OutputStream os = new FileOutputStream(DB_PATH + DB_NAME);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = is.read(buffer)) > 0) {
            os.write(buffer, 0, length);
        }

        os.flush();
        os.close();
        is.close();
    }

    private void openDataBase() {
        try {
            db = SQLiteDatabase.openDatabase(
                    DB_PATH + DB_NAME,
                    null,
                    SQLiteDatabase.OPEN_READONLY | SQLiteDatabase.NO_LOCALIZED_COLLATORS);
        } catch (SQLiteException e) {
            // database does't exist yet
        }
    }

    public SimpleCursorAdapter getListByParentCode(Context context, String parentCode) {
        SimpleCursorAdapter list = null;
        DBHelper dHelper = new DBHelper(context);
        SQLiteDatabase db = dHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select code as _id, region from xzqhdm where parent_code = ?", new String[] {parentCode});
        if (cursor.getCount() != 0) {
            list = new SimpleCursorAdapter(context,
                    android.R.layout.simple_spinner_item,
                    cursor,
                    new String[] {"region"},
                    new int[] {android.R.id.text1});
            list.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        }
        return list;
    }

    @Override
    public synchronized void close() {
        if (db != null) {
            db.close();
        }
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
