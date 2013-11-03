package is.ru.TrafficJam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: AuðunVetle
 * Date: 26.10.2013
 * Time: 14:32
 * To change this template use File | Settings | File Templates.
 */
public class GameActivity extends Activity
{
    GameView m_gv;
    GameLogic m_logic;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String levelNumber = intent.getStringExtra(LevelSelectActivity.LEVEL_NUMBER);



        setContentView(R.layout.game);
        m_gv = (GameView) findViewById( R.id.gameview );
        m_logic = new GameLogic(Integer.parseInt(levelNumber),getApplicationContext());
        m_gv.setLogic(m_logic);
        if ( savedInstanceState != null ) {
            String state = savedInstanceState.getString( "boardState" );
            m_logic.setState( state );
        }
        //Log.d("GameViewLOL",m_gv.getState());
    }
    @Override
    public void onSaveInstanceState( Bundle savedInstanceState ) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString( "boardState", m_logic.toString() );
    }
}