package is.ru.TrafficJam;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity
{

    public static SharedPreferences settings = null;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        settings = getSharedPreferences(getString(R.string.settings_file_name), 0);


        try
        {
            XMLParser.setIOStream(getApplicationContext().getAssets().open("challenge_classic40.xml"));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    // Start a activity that shows the level selection screen
    public void playButtonPressed( View view )
    {
        String newMessage = ""+MainActivity.settings.getInt(getString(R.string.last_level_variable_name),getResources().getInteger(R.integer.level));
        Intent intent = new Intent( this, GameActivity.class );

        intent.putExtra(LevelSelectActivity.LEVEL_NUMBER, newMessage);
        startActivity( intent );
    }

    public void levelsButtonPressed( View view )
    {
        Intent intent = new Intent( this, LevelSelectActivity.class );
        startActivity( intent );
    }

    // Start a activity that shows the options screen
    public void optionsButtonPressed( View view )
    {
        Intent intent = new Intent( this, OptionsActivity.class );
        startActivity( intent );
    }
}
