package eecs393team.eecs3933dapp;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class CreateMain extends AppCompatActivity {

    private ServerConnection myConnection;
    private Intent nextState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_main);
        myConnection = new ServerConnection("111.111.11.111");
        connectToServer();
    }

    private void connectToServer(){
        //spawn loading icon
        //connect to server
        boolean success = myConnection.connectToServer();
        Button next_button = (Button) findViewById(R.id.button);
        //if true
        if (success) {
            //spawn check
            ImageView connecting_graphic = (ImageView) findViewById(R.id.imageView);
            connecting_graphic.setImageResource(R.drawable.check_mark);
            //spawn start_video button
            nextState = new Intent(this, MainActivity.class); //TODO: create class for video capture
            next_button.setText(R.string.start_video);
        }
        //else
        else {
            // spawn failure
            ImageView connecting_graphic = (ImageView) findViewById(R.id.imageView);
            connecting_graphic.setImageResource(R.drawable.red_x);
            // spawn back button
            nextState = new Intent(this, MainActivity.class);
            next_button.setText(R.string.back);
        }
        next_button.setClickable(true);
    }

    public void nextState(View view){
        startActivity(nextState);
    }

}
