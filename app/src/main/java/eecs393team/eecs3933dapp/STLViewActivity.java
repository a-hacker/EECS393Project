package eecs393team.eecs3933dapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ToggleButton;

import java.io.File;

public class STLViewActivity extends Activity implements FileListDialog.OnFileListDialogListener {
    private STLView stlView;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PackageManager manager = getPackageManager();
        ApplicationInfo appInfo = null;
        try {
            appInfo = manager.getApplicationInfo(getPackageName(), 0);
            //Log.setDebug((appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE);
        } catch (PackageManager.NameNotFoundException e) {
            //Log.d(e);
        }

        Intent intent = getIntent();
        Uri uri = null;
        if (intent.getData() != null) {
            uri = getIntent().getData();
            //Log.i("Uri:" + uri);
        }
        setUpViews(uri);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (stlView != null) {
            //Log.i("onResume");
            STLRenderer.requestRedraw();
            stlView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (stlView != null) {
            //Log.i("onPause");
            stlView.onPause();
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Log.i("onRestoreInstanceState");
        Parcelable stlFileName = savedInstanceState.getParcelable("STLFileName");
        if (stlFileName != null) {
            setUpViews((Uri) stlFileName);
        }
        boolean isRotate = savedInstanceState.getBoolean("isRotate");
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.rotateOrMoveToggleButton);
        toggleButton.setChecked(isRotate);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (stlView != null) {
            //Log.i("onSaveInstanceState");
            outState.putParcelable("STLFileName", stlView.getUri());
            outState.putBoolean("isRotate", stlView.isRotate());
        }
    }

    @Override
    public void onClickFileList(File file) {
        if (file == null) {
            return;
        }

        SharedPreferences config = getSharedPreferences("PathSetting", Activity.MODE_PRIVATE);
        SharedPreferences.Editor configEditor = config.edit();
        configEditor.putString("lastPath", file.getParent());
        configEditor.commit();

        setUpViews(Uri.fromFile(file));
    }

    private void setUpViews(Uri uri) {
        setContentView(R.layout.activity_stlview);
        final ToggleButton toggleButton = (ToggleButton) findViewById(R.id.rotateOrMoveToggleButton);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (stlView != null) {
                    stlView.setRotate(isChecked);
                }
            }
        });

        final ImageButton loadButton = (ImageButton) findViewById(R.id.loadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

                intent.addCategory(Intent.CATEGORY_OPENABLE);

                intent.setType("*/*");

                startActivityForResult(intent, 0);
            }
        });

        final ImageButton preferencesButton = (ImageButton) findViewById(R.id.preferncesButton);
        preferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(STLViewActivity.this, PreferencesActivity.class);
                startActivity(intent);
            }
        });

        if (uri != null) {
            setTitle(uri.getPath().substring(uri.getPath().lastIndexOf("/") + 1));

            FrameLayout relativeLayout = (FrameLayout) findViewById(R.id.stlFrameLayout);
            stlView = new STLView(this, uri);
            relativeLayout.addView(stlView);

            toggleButton.setVisibility(View.VISIBLE);

            stlView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    if (preferencesButton.getVisibility() == View.INVISIBLE) {
                        ;
                    }
                }
            });
        }
    }


}
