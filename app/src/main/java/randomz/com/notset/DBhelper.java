package randomz.com.notset;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by harshul garg on 05-Aug-15.
 */
public class DBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "expenseManager.db";
    public static final String EXPENSE_TABLE_NAME = "expenses";
    public static final String EXPENSE_COLUMN_ID = "id";
    public static final String EXPENSE_COLUMN_AMOUNT = "amount";
    public static final String EXPENSE_COLUMN_CATEGORY = "category";

    public DBhelper(Context context) {
        super(context,DATABASE_NAME,null,1);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + EXPENSE_TABLE_NAME +
                "(id int primary key, " +
                        "category text )"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public String saveData(double amount, String category) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(EXPENSE_COLUMN_AMOUNT, amount);
            contentValues.put(EXPENSE_COLUMN_CATEGORY, category);

            db.insert(EXPENSE_TABLE_NAME, null, contentValues);
            return "Saved";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
}
