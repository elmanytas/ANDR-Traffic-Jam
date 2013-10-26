package is.ru.TrafficJam;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created with IntelliJ IDEA.
 * User: AuðunVetle
 * Date: 26.10.2013
 * Time: 13:45
 * To change this template use File | Settings | File Templates.
 */
public class LevelSelectActivity extends Activity
{
    public final static String LEVEL_NUMBER = "is.ru.TrafficJam.LEVEL_NUMBER";

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.levelselect);
    }

    // Handles all the button presses
    public void buttonPressHandler(View view)
    {

        Button buttonView = (Button) view;
        String message = buttonView.getText().toString();

        Intent intent = new Intent( this, GameActivity.class );

        intent.putExtra(LEVEL_NUMBER, message);
/*
        switch ( view.getId() )
        {
            case R.id.button1:
            case R.id.button2:
            case R.id.button3:
            case R.id.button4:
            case R.id.button5:
            case R.id.button6:
            case R.id.button7:
            case R.id.button8:
            case R.id.button9:
        }
*/
        startActivity( intent );
    }


}