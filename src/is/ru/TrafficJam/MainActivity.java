package is.ru.TrafficJam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity
{
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

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
        Intent intent = new Intent( this, LevelSelectActivity.class );
        startActivity( intent );
    }
}
