package is.ru.TrafficJam;

import android.content.Context;
import android.graphics.*;
import android.media.MediaPlayer;
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
 * GameView er view sem sýnir borðið sem maður er að spila.
 */
public class GameView extends View
{
    private class MyShape {

        MyShape( Rect r, String c, Block b ) {
            rect = r;
            type = c;
            block = b;
        }
        Rect rect;
        Block block;
        String  type;
    }

    private int m_cellWidth = 80;
    private int m_cellHeight = 80;
    private Point m_oldPos;
    private Point m_fingerDown;
    private int m_movementMin;
    private int m_movementMax;
    private Bitmap lee;
    private Bitmap carter;
    private Bitmap rotatedcarter;
    private MediaPlayer leeSound;
    private MediaPlayer carterSound;
    private Paint mPaint = new Paint();
    private ArrayList<MyShape> mShapes = new ArrayList<MyShape>();
    private MyShape mMovingShape = null;
    private Rect m_rect = new Rect();
    private GameLogic m_logic;

    public GameView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        leeSound = MediaPlayer.create(context, R.raw.carter);
        carterSound = MediaPlayer.create(context, R.raw.lee);
        lee = BitmapFactory.decodeResource(context.getResources(),R.drawable.lee);
        carter = BitmapFactory.decodeResource(context.getResources(),R.drawable.carter);

        Matrix matrix = new Matrix();
        matrix.postRotate(-90);
        rotatedcarter = Bitmap.createBitmap(carter, 0, 0,
                carter.getWidth(), carter.getHeight(),
                matrix, true);

        lee = Bitmap.createBitmap(lee, 0, 0,
                lee.getWidth(), lee.getHeight(),
                matrix, true);
    }


    protected void onDraw( Canvas canvas )
    {
        mPaint.setColor( Color.BLACK );
        for (int r=5; r>=0; --r){
            for (int c=0; c<6;++c){
                m_rect.set( c * m_cellWidth, r * m_cellHeight,
                        c * m_cellWidth + m_cellWidth, r * m_cellHeight + m_cellHeight );
                mPaint.setColor( Color.BLACK );
                mPaint.setStyle( Paint.Style.FILL );
                canvas.drawRect( m_rect, mPaint );
                //m_rect.inset( (int)(m_rect.width() * 0.1), (int)(m_rect.height() * 0.1) );
                mPaint.setColor( Color.WHITE );
                mPaint.setStyle( Paint.Style.STROKE );
                canvas.drawRect( m_rect, mPaint );
            }
        }
        mPaint.setStyle( Paint.Style.FILL );
        for ( MyShape shape : mShapes ) {
            if(shape.type.equals("lee"))
                canvas.drawBitmap(lee,null,shape.rect,mPaint);
            else{
                if(shape.block.isVertical())
                    canvas.drawBitmap(carter,null,shape.rect,mPaint);
                else
                    canvas.drawBitmap(rotatedcarter,null,shape.rect,mPaint);
            }
        }
    }

    public void setLogic( GameLogic logic )
    {
        m_logic = logic;
    }

    private void makeShapes( )
    {
        mShapes = new ArrayList<MyShape>();
        ArrayList<Block> board = m_logic.getBlockArray();
        boolean isFirst = true;
        for ( Block b : board)
        {
            String type = "carter";
            if(isFirst){
                type = "lee";
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
            mShapes.add(new MyShape(tempR,type,b));
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
        makeShapes();
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
                m_movementMin = m_logic.getMin(mMovingShape.block)*m_cellHeight;
                m_movementMax = m_logic.getMax(mMovingShape.block)*m_cellHeight;
                Log.d("GameViewLOL", "m_movementMin:" + m_movementMin);
                Log.d("GameViewLOL", "m_movementMax:" + m_movementMax);

            }
            break;
        case MotionEvent.ACTION_UP:
            if ( mMovingShape != null ) {
                int newLeft = xToCol(mMovingShape.rect.left)*m_cellWidth;
                int newTop = yToRow(mMovingShape.rect.top)*m_cellHeight;
                int newRight = newLeft+mMovingShape.rect.width();
                int newBot = newTop+mMovingShape.rect.height();
                mMovingShape.rect.set(newLeft,newTop,newRight,newBot);

                m_logic.moveBlock(screenToWorld(m_oldPos),screenToWorld(new Point(mMovingShape.rect.left,mMovingShape.rect.top)));


                //Sound
                if(MainActivity.settings.getBoolean(getContext().getString(R.string.settings_sound_variable_name),getResources().getBoolean(R.bool.sound)))
                {
                    if(mMovingShape.type.equals("lee"))
                    {
                        if(leeSound.isPlaying()){
                            leeSound.seekTo(0);
                        } else {
                            leeSound.start();
                        }
                    }
                    else
                    {
                        if(carterSound.isPlaying())
                        {
                            carterSound .seekTo(0);
                        } else {
                            carterSound.start();
                        }
                    }
                }
                if(m_logic.isGameOver()){
                    m_logic.loadNextLevel();
                    makeShapes();
                }
                invalidate();
                mMovingShape = null;
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
                if(mMovingShape.block.isVertical())
                {
                    /*if(y>m_movementMin && y < m_movementMax)
                    {
                        mMovingShape.rect.offsetTo( m_oldPos.x, y );
                    }*/
                    mMovingShape.rect.offsetTo( m_oldPos.x, Math.min(m_movementMax,Math.max(m_movementMin,y)) );
                }
                else {
                    /*if(x>m_movementMin && x < m_movementMax)
                    {
                        mMovingShape.rect.offsetTo( x, m_oldPos.y );
                    }*/
                    mMovingShape.rect.offsetTo( Math.min(m_movementMax,Math.max(m_movementMin,x)), m_oldPos.y );
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

    private Point screenToWorld(Point screen)
    {
        return new Point(xToCol(screen.x),yToRow(screen.y));
    }


}