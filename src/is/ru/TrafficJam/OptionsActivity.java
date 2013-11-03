package is.ru.TrafficJam;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.CompoundButton.OnCheckedChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: AuðunVetle
 * Date: 26.10.2013
 * Time: 18:58
 * To change this template use File | Settings | File Templates.
 */
public class OptionsActivity extends Activity {

private Switch switchVibrate;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options);

        switchVibrate = (Switch) findViewById(R.id.switchVibrate);

        switchVibrate.setChecked(MainActivity.settings.getBoolean("vibrate",false));

        switchVibrate.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Editor editor = MainActivity.settings.edit();
                editor.putBoolean(getString(R.string.settings_file_name), switchVibrate.isChecked());
                editor.commit();
            }
        });

    }



    public void onSwichClicked( View view )
    {
        //switchVibrate
        Switch switchView = (Switch) view;
        boolean switchState = switchView.isChecked();
        switch ( view.getId() )
        {
            case R.id.switchVibrate:

                break;

        }



    }

}