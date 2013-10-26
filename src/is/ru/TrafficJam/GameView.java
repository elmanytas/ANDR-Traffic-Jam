package is.ru.TrafficJam;

import android.app.Activity;
import android.content.Context;
import android.graphics.*;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Snorri A.
 * Date: 26.10.2013
 * Time: 14:40
 * To change this template use File | Settings | File Templates.
 */
public class GameView extends View
{
    private class MyShape {

        MyShape( Rect r, int c, Block b ) {
            rect = r;
            color = c;
            block = b;
        }
        Rect rect;
        Block block;
        int  color;
    }

    public int m_cellWidth = 80;
    public int m_cellHeight = 80;
    private Point m_oldPos;
    private Point m_fingerDown;
    Paint mPaint = new Paint();
    ArrayList<MyShape> mShapes = new ArrayList<MyShape>();
    MyShape mMovingShape = null;
    Rect m_rect = new Rect();
    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    protected void onDraw( Canvas canvas )
    {
        mPaint.setColor( Color.BLACK );
        mPaint.setStyle( Paint.Style.STROKE );
        for (int r=5; r>=0; --r){
            for (int c=0; c<6;++c){
                m_rect.set( c * m_cellWidth, r * m_cellHeight,
                        c * m_cellWidth + m_cellWidth, r * m_cellHeight + m_cellHeight );
                canvas.drawRect( m_rect, mPaint );
                m_rect.inset( (int)(m_rect.width() * 0.1), (int)(m_rect.height() * 0.1) );
            }
        }
        mPaint.setStyle( Paint.Style.FILL );
        for ( MyShape shape : mShapes ) {
            mPaint.setColor( shape.color );
            canvas.drawRect( shape.rect, mPaint );
        }
    }
    public void lol()
    {
        Log.d("GameViewLOL", "Lol()");
    }
    public void setBoard( ArrayList<Block> board )
    {
        Log.d("GameViewLOL", "Starting to parse");
        Log.d("GameViewLOL", board.get(0).getPos().toString());
        Log.d("GameViewLOL", "m_cellWidth:"+m_cellWidth );
        boolean isFirst = true;
        for ( Block b : board)
        {
            int color = Color.DKGRAY;
            if(isFirst){
                color = Color.CYAN;
                isFirst = false;
            }
            int xLength = m_cellWidth;
            int yLength = m_cellHeight;
            if(b.isVertical())
            {
                yLength *= b.getLength();
            }
            else
            {
                xLength *= b.getLength();
            }
            Rect tempR = new Rect(b.getPos().x*m_cellWidth,b.getPos().y*m_cellHeight,(b.getPos().x*m_cellWidth)+xLength,(b.getPos().y*m_cellHeight)+yLength);
            mShapes.add(new MyShape(tempR,color,b));
        }
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = Math.min(getMeasuredWidth(), getMeasuredHeight());
        setMeasuredDimension(size, size);
    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld) {
        super.onSizeChanged(xNew, yNew, xOld, yOld);
        Log.d("GameViewLOL", "xNew:" + xNew);
        Log.d("GameViewLOL", "yNew:" + yNew);
        Log.d("GameViewLOL", "m_cellWidth:"+m_cellWidth );
        m_cellWidth = xNew / 6;
        Log.d("GameViewLOL", "m_cellWidth:"+m_cellWidth );
        m_cellHeight = yNew / 6;
    }
    public boolean onTouchEvent( MotionEvent event ) {

    int x = (int) event.getX();
    int y = (int) event.getY();

    switch ( event.getAction() ) {
        case MotionEvent.ACTION_DOWN:
            mMovingShape = findShape( x, y );
            m_fingerDown = new Point(x,y);
            if(mMovingShape != null){
                m_oldPos = new Point( mMovingShape.rect.left,mMovingShape.rect.top);
                m_fingerDown = new Point(m_oldPos.x-x,m_oldPos.y-y);
            }
            break;
        case MotionEvent.ACTION_UP:
            if ( mMovingShape != null ) {
                int newLeft = xToCol(mMovingShape.rect.left)*m_cellWidth;
                int newTop = yToRow(mMovingShape.rect.top)*m_cellHeight;
                int newRight = newLeft+mMovingShape.rect.width();
                int newBot = newTop+mMovingShape.rect.height();
                mMovingShape.rect.set(newLeft,newTop,newRight,newBot);
                //TODO: senda makeMove með oldpos og newpos.
                invalidate();
                mMovingShape = null;
                // emit an custom event ....
            }
            break;
        case MotionEvent.ACTION_MOVE:
            if ( mMovingShape != null ) {
                //Point oldPos = new Point(mMovingShape.rect.left,mMovingShape.rect.top);
                x = x+m_fingerDown.x;
                if(x<=0)
                    x = 0;
                y = y+m_fingerDown.y;
                if(y<=0)
                    y = 0;
                x = Math.min( x, getWidth() - mMovingShape.rect.width() );
                y = Math.min( y, getHeight() - mMovingShape.rect.height() );

                if(mMovingShape.block.isVertical()){
                    MyShape lol = collision(mMovingShape);
                    if(lol == null){
                        mMovingShape.rect.offsetTo( m_oldPos.x, y );
                    } else{
                        //mMovingShape.rect.offsetTo( m_oldPos.x,oldPos.y-1);
                    }
                }
                else
                {
                    MyShape lol = collision(mMovingShape);
                    if(lol == null){
                        mMovingShape.rect.offsetTo( x, m_oldPos.y );
                    } else {
                        //mMovingShape.rect.offsetTo( oldPos.x-1,m_oldPos.y);
                    }
                }
                invalidate();
            }
            break;
    }
    return true;
}

    private MyShape findShape( int x, int y ) {
        for ( MyShape shape : mShapes ) {
            if ( shape.rect.contains( x, y ) ) {
                return shape;
            }
        }
        return null;
    }

    private MyShape collision( MyShape with ) {
        for ( MyShape shape : mShapes ) {
            if(!shape.equals(with))
            {
                if (Rect.intersects(with.rect,shape.rect)) {
                    return shape;
                }
            }
        }
        return null;
    }

    private int xToCol( int x ) {
        double xx = (double)x / m_cellWidth;
        if(((int)xx)<((int)(xx+0.5))){
            return (int)(xx+0.5);
        } else {
            return (int)xx;
        }
    }

    private int yToRow( int y ) {
        double yy = (double)y / m_cellHeight;
        if(((int)yy)<((int)(yy+0.5))){
            return (int)(yy+0.5);
        } else {
            return (int)yy;
        }
    }


}