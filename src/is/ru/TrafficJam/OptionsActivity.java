package is.ru.TrafficJam;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;
import is.ru.TrafficJam.DataBase.TrafficJamSQLiteAdapter;

/**
 * Created with IntelliJ IDEA.
 * User: AuðunVetle
 * Date: 26.10.2013
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
public class OptionsActivity extends Activity
{
    private TrafficJamSQLiteAdapter trafficJamAdapter = new TrafficJamSQLiteAdapter( this );

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        // Setup the vibrate switch:
        Switch switchVibrate = (Switch) findViewById(R.id.switchVibrate); // Get a view of the vibrate switch.

        switchVibrate.setChecked(MainActivity.settings.getBoolean(getString(R.string.settings_vibrate_variable_name),getResources().getBoolean(R.bool.vibrate)));// Load the state of the variable and set the swich to that state

        switchVibrate.setOnCheckedChangeListener(new OnCheckedChangeListener() // Bind a lissener to the swich to save the new variable.
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Editor editor = MainActivity.settings.edit();
                editor.putBoolean(getString(R.string.settings_vibrate_variable_name), isChecked);
                editor.commit();
            }
        });

        // Setup the sound switch:
        Switch switchSound = (Switch) findViewById(R.id.switchSound); // Get a view of the sound switch.

        switchSound.setChecked(MainActivity.settings.getBoolean(getString(R.string.settings_sound_variable_name),getResources().getBoolean(R.bool.sound)));// Load the state of the variable and set the swich to that state

        switchSound.setOnCheckedChangeListener(new OnCheckedChangeListener() // Bind a lissener to the swich to save the new variable.
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                Editor editor = MainActivity.settings.edit();
                editor.putBoolean(getString(R.string.settings_sound_variable_name), isChecked);
                editor.commit();
            }
        });

    }

    public void buttonPressHandler(View view)
    {
        //Button buttonView = (Button) view;

        switch ( view.getId() )
        {
            case R.id.resetLevels:
                trafficJamAdapter.resetLevels();
                break;
        }
    }


}