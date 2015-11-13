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
import android.widget.LinearLayout.LayoutParams;

public class Gallery extends AppCompatActivity {

    private File baseDir;
    ///data/user/0/eecs393team.eecs3933dapp/files

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        File dir = getFilesDir();
        Log.d("stuff",dir.list().toString());
        final String path = dir.toString();
        //make sure the path specified is a valid directory
        if (dir.isDirectory()){
            for (final String fileName: dir.list()) {
                //create button for each file
                Button fileButton = new Button(this);
                fileButton.setText(fileName);
                LinearLayout ll = (LinearLayout)findViewById(R.id.linear);
                LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
                ll.addView(fileButton, lp);
                final Gallery blah = this;
                fileButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        Intent intent = new Intent(blah, STLViewActivity.class);
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
