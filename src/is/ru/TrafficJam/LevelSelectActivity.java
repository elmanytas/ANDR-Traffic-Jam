package is.ru.TrafficJam;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

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
        //setContentView(R.layout.levelselect);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(ScrollView.LayoutParams.MATCH_PARENT,ScrollView.LayoutParams.MATCH_PARENT));

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        TextView textView = new TextView(this);
        textView.setText("Select a level");
        textView.setTextSize(30);
        textView.setGravity(Gravity.CENTER);
        linearLayout.addView(textView);

        TableLayout tableLayout = new TableLayout(this);
        tableLayout.setStretchAllColumns(true);
        tableLayout.setGravity(Gravity.CENTER | Gravity.BOTTOM);

        int counter = 0;
        TableRow tableRow = new TableRow(this);

        View.OnClickListener listener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                buttonPressHandler(view);
            }
        };

        for (int i = 1; i<41; i++){
            Button button = new Button(this);
            button.setText(Integer.toString(i));
            button.setOnClickListener(listener);
            tableRow.addView(button);
            counter++;
            if ( counter == 3){
                tableLayout.addView(tableRow);
                tableRow = new TableRow(this);
                counter = 0;
            }
        }
        if (counter != 0)
            tableLayout.addView(tableRow);
        linearLayout.addView(tableLayout);
        scrollView.addView(linearLayout);


        setContentView(scrollView);
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