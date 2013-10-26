package is.ru.TrafficJam;

import android.graphics.Point;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: AuðunVetle
 * Date: 26.10.2013
 * Time: 15:37
 * To change this template use File | Settings | File Templates.
 */
public class GameLogic
{
    ArrayList<Block> blockArray;

    public ArrayList<Block> getBlockArray() {
        return blockArray;
    }
    GameLogic(int level){
        blockArray = XMLParser.getLevel(level);
    }

    public boolean isGameOver(){
        if (blockArray.get(0).getPos().equals(4,2))
            return true;
        else
            return false;
    }

    public void moveBlock (Point blockToMove, Point posToMoveTo){
        Block block = getBlockByPos(blockToMove);
        if (block != null){
            block.setPos(posToMoveTo);
        }
        else{
            Log.d("Gamelogic: ", "Block not found at point: " + blockToMove.toString());
        }
        if (isGameOver()){
            //Get the next level
        }
    }
    private Block getBlockByPos(Point pos){
        for (Block b : blockArray){
            if (b.getPos() == pos)
                return b;
        }
        return null;
    }
}
