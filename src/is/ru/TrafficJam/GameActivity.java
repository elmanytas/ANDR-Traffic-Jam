package is.ru.TrafficJam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * Created with IntelliJ IDEA.
 * User: AuðunVetle
 * Date: 26.10.2013
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String levelNumber = intent.getStringExtra(LevelSelectActivity.LEVEL_NUMBER);





        setContentView(R.layout.game);
    }
}