package open.callforblood.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteDatabaseHandler extends SQLiteOpenHelper {
 
    // Database Version
    public static final int DATABASE_VERSION = 2;
 
    // Database Name
    public static final String DATABASE_NAME = "userData";
 
    // Table Names
    public static final String TABLE_USER = "user";
 
    // column names
    public static final String USER_ID = "user_id";
    public static final String PASSWORD = "password";
    public static final String MAIL = "mail";
 
    
 
    // Table Create Statements
    // user table create statement
    private static final String CREATE_TABLE_USER = "CREATE TABLE "
            + TABLE_USER + "(" + USER_ID + " TEXT PRIMARY KEY," + MAIL
            + " TEXT," + PASSWORD + " TEXT)";
 
    
    public SQLiteDatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
 
    @Override
    public void onCreate(SQLiteDatabase db) {
 
        // creating required tables
        db.execSQL(CREATE_TABLE_USER);
        
    }
 
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER + ";");
 
        // create new tables
        onCreate(db);
    }
    
    
    
}