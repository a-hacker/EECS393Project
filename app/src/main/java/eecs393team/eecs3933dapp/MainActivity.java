package eecs393team.eecs3933dapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static String serverIp = "0.0.0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void create(View view){
        Intent intent = new Intent(this, CreateMain.class);
        startActivity(intent);
    }

    public void scan(View view){
        serverIp = ((EditText)findViewById(R.id.server_ip)).getText().toString();
        Intent intent = new Intent(this, ScanActivity.class);
        //Intent intent = new Intent(this, VideoCapture.class);
        startActivity(intent);
    }

    public void edit(View view){
        //Intent intent = new Intent(this, EditMain.class);
        //startActivity(intent);

        //Commented out prior Edit intent because this may not be the final location for this.
        Intent intent = new Intent(this, STLViewActivity.class);
        startActivity(intent);
    }

    public void print(View view){
        Intent intent = new Intent(this, PrintMain.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
