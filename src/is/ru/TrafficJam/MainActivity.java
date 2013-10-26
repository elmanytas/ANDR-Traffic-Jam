package is.ru.TrafficJam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
    }

    // Start a activity that shows the level selection screen
    public void playButtonPressed( View view )
    {
        Intent intent = new Intent( this, LevelSelectActivity.class );
        startActivity( intent );
    }
}
