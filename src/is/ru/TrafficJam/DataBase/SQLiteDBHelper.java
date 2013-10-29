package is.ru.TrafficJam.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created with IntelliJ IDEA.
 * User: AuðunVetle
 * Date: 29.10.2013
 * Time: 18:57
 * To change this template use File | Settings | File Templates.
 */
public class SQLiteDBHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = "TRAFFICJAM_DB";
    public static final int DB_VERSION = 8;

    public static final String TableFinishedLevels = "finishedlevels";
    public static final String[] TableFinishedLevelsCols = { "_id", "level", "done" };

    private static final String sqlCreateTableFinishedLevels =
            "CREATE TABLE " + TableFinishedLevels + " (" +
                    "_id  INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "level INTEGER NOT NULL," +
                    "done INTEGER" +
                    ");";
    private static final String sqlDropTableFinishedLevels =
            "DROP TABLE IF EXISTS " + TableFinishedLevels + ";";

    public SQLiteDBHelper(Context context )
    {
        super(context, DB_NAME, null, DB_VERSION);
        Log.d("TrafficJamDB", "Initialising  SQLiteDBHelper");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        Log.d("TrafficJamDB", "Starting to create table: " + TableFinishedLevels);
        sqLiteDatabase.execSQL( sqlCreateTableFinishedLevels );
        Log.d("TrafficJamDB", "Created table: " + TableFinishedLevels);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldV, int newV)
    {
        sqLiteDatabase.execSQL( sqlDropTableFinishedLevels );
        onCreate( sqLiteDatabase );
    }
}
