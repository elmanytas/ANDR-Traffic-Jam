package is.ru.TrafficJam.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: AuðunVetle
 * Date: 29.10.2013
 * Time: 19:07
 * To change this template use File | Settings | File Templates.
 */
public class TrafficJamSQLiteAdapter
{

    SQLiteDatabase db;
    SQLiteDBHelper dbHelper;
    Context context;

    public TrafficJamSQLiteAdapter( Context c )
    {
        context = c;
    }

    public TrafficJamSQLiteAdapter openToRead()
    {
        dbHelper = new SQLiteDBHelper( context );
        db = dbHelper.getReadableDatabase();
        return this;
    }

    public TrafficJamSQLiteAdapter openToWrite()
    {
        dbHelper = new SQLiteDBHelper( context );
        db = dbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        db.close();
    }

    public long markLevelAsFinished( int level )
    {
        String[] cols = SQLiteDBHelper.TableFinishedLevelsCols;
        ContentValues contentValues = new ContentValues();
        contentValues.put( cols[1], ((Integer)level).toString());
        contentValues.put( cols[2], "1" ); // Setur að levelið sé finished í true
        openToWrite();
        long value = db.insert(SQLiteDBHelper.TableFinishedLevels, null, contentValues );
        close();
        return value;
    }

    public Cursor getFinishedLevels()
    {
        openToRead();
        Cursor cursor = db.query( SQLiteDBHelper.TableFinishedLevels,
                SQLiteDBHelper.TableFinishedLevelsCols, null, null, null, null, null );
        return cursor;
    }

    public HashMap getFinishedLevelsMap()
    {
        Log.d("TrafficJamDB", "Started running [public HashMap getFinishedLevelsMap()]");
        HashMap<String, Boolean> temp = new HashMap<String, Boolean>();

        Cursor c = getFinishedLevels();

        String[] cols = SQLiteDBHelper.TableFinishedLevelsCols;
        while(c.moveToNext())
        {
            String level = c.getString(c.getColumnIndex(cols[1]));
            String done = c.getString(c.getColumnIndex(cols[2]));
            temp.put(level, (done.equals("1")));

            Log.d("TrafficJamDB", "c.moveToNext()");
        }

        close();

        Log.d("TrafficJamDB", "Finised running [public HashMap getFinishedLevelsMap()]");
        return temp;
    }

    public void resetLevels()
    {
        dbHelper.resetLevels(db);
    }


}
