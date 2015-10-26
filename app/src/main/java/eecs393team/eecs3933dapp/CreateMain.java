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

import java.util.concurrent.TimeUnit;

public class CreateMain extends AppCompatActivity {

    private String ip;
    private ServerConnection myConnection;
    private Intent nextState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_main);
        setServer("172.19.11.14");
        connectToServer();
    }

    protected void setServer(String ip){
        myConnection = new ServerConnection(ip);
        this.ip = ip;
    }

    public ServerConnection getServer(){
        return myConnection;
    }

    protected void connectToServer(){
        //spawn loading icon
        //connect to server
        boolean success = false;
        ServerConnection new_server = (ServerConnection) new ServerConnection(ip).execute(); //myConnection.connectToServer();
        try {
            success = new_server.get(20, TimeUnit.SECONDS);

        } catch(Exception e){

        }
        Button next_button = (Button) findViewById(R.id.button);
        //if true
        if (success) {
            //spawn check
            ImageView connecting_graphic = (ImageView) findViewById(R.id.imageView);
            connecting_graphic.setImageResource(R.drawable.check_mark);
            //spawn start_video button
            nextState = new Intent(this, StartVideo.class);
            nextState.putExtra("ServerConnection", myConnection);
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
