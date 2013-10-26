package is.ru.TrafficJam;

import android.graphics.Point;

/**
 * Created with IntelliJ IDEA.
 * User: AuðunVetle
 * Date: 26.10.2013
 * Time: 15:15
 * To change this template use File | Settings | File Templates.
 */
public class Block
{
    private int length;
    public int getLength()
    {
        return length;
    }

    public void setLength(int length)
    {
        this.length = length;
    }

    private boolean isVertical;
    public boolean isVertical()
    {
        return isVertical;
    }

    public void setVertical(boolean vertical)
    {
        isVertical = vertical;
    }

    private Point pos;
    public Point getPos()
    {
        return pos;
    }

    public void setPos(Point pos)
    {
        this.pos = pos;
    }

    public Block(int length, boolean vertical, Point pos)
    {
        this.length = length;
        this.isVertical = vertical;
        this.pos = pos;
    }


    @Override
    public String toString(){
        String tempString = new String();
        tempString+= "(";
        String v = (isVertical)?"V ":"H ";

        tempString+=v+pos.x + " " + pos.y + " " + length + ")";
        return tempString;
    }
}
