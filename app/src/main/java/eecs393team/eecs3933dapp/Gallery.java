package eecs393team.eecs3933dapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.io.File;
import java.util.HashMap;

import android.widget.LinearLayout.LayoutParams;

public class Gallery extends AppCompatActivity {

    private File baseDir;
    public HashMap<String, Button> buttons;
    //data/user/0/eecs393team.eecs3933dapp/files

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        buttons = new HashMap<String, Button>();
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        File dir = getFilesDir();
        final String path = dir.toString();
        LinearLayout layout = (LinearLayout)findViewById(R.id.linear);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //make sure the path specified is a valid directory
        if (dir.isDirectory()){
            for (final String fileName: dir.list()) {
                if (!fileName.endsWith(".STL")){
                    //if the file isn't an STL file, don't allow it to be loaded
                    continue;
                }
                //create button for each STL file
                Button fileButton = new Button(this);
                fileButton.setText(fileName);
                layout.addView(fileButton, params);
                final Gallery currentActivity = this;
                buttons.put(fileName, fileButton);
                fileButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(currentActivity, STLViewActivity.class);
                        intent.putExtra(path, fileName);
                        startActivity(intent);
                    }
                });
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public File get_path() {
        return getFilesDir();
    }

}
