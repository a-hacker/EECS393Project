package eecs393team.eecs3933dapp;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class CreateMain extends AppCompatActivity {

    private ServerConnection myConnection;

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
        //if true
        if (success) {
            //spawn check
            ImageView connecting_graphic = (ImageView) findViewById(R.id.imageView);
            connecting_graphic.setImageResource(R.drawable.check_mark);
        }
        //else
        else {
            // spawn failure
            ImageView connecting_graphic = (ImageView) findViewById(R.id.imageView);
            connecting_graphic.setImageResource(R.drawable.red_x);
            // go back to main
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

}
