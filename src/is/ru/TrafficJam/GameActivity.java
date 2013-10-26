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
    ArrayList<Block> m_board;
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Get the message from the intent
        Intent intent = getIntent();
        String levelNumber = intent.getStringExtra(LevelSelectActivity.LEVEL_NUMBER);




        setContentView(R.layout.game);
        m_gv = (GameView) findViewById( R.id.gameview );
        m_board = new ArrayList<Block>();
        m_board.add(new Block(2,false,new Point(1,2)));
        m_board.add(new Block(3,true,new Point(0,1)));
        m_board.add(new Block(2,false,new Point(0,0)));
        m_board.add(new Block(3,true,new Point(3,1)));
        m_board.add(new Block(3,false,new Point(2,5)));
        m_board.add(new Block(2,true,new Point(0,4)));
        m_board.add(new Block(2,false,new Point(4,4)));
        m_board.add(new Block(3,true,new Point(5,0)));


        updateDisplay();
    }
    public void updateDisplay()
    {
        m_gv.setBoard(m_board);
    }
}