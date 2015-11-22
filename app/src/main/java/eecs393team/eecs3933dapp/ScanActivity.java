package eecs393team.eecs3933dapp;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Created by josh on 10/30/15.
 */
public class ScanActivity extends Activity{
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static int fileNum = 0;
    private String ip;
    private ServerConnection myConnection;
    private Intent nextState;
    private Uri fileUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_gallery);

        //create new Intent
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);

        fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1); // set the video image quality to high

        // start the Video Capture Intent
        startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
    }

    /** Create a file Uri for saving an image or video */
    private static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    /** Create a File for saving an image or video */
    private static File getOutputMediaFile(int type){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Scanner 3D");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("EECS393Project", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;

        if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "SCAN_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        return mediaFile;
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
        //Button next_button = (Button) findViewById(R.id.button);
        //if true
        if (success) {
            //spawn check
            OutputStream outputStream = new_server.getServerOutput();
            FileInputStream in = null;
            try {
                in = new FileInputStream(fileUri.getPath());
                // Write to the stream:
                byte[] buffer = new byte[1024]; // 1KB buffer size
                int length = 0;
                while ((length = in.read(buffer, 0, buffer.length)) != -1) {
                    outputStream.write(buffer, 0, length);
                }
                outputStream.flush();
            }
            catch(Exception e){

            }
            finally {
                if (in != null)
                    try {
                        in.close();
                        waitForResponse(new_server);
                    }
                    catch(Exception e){

                    }
                new_server.close(); // Will close the outputStream, too.
            }
            ImageView connecting_graphic = (ImageView) findViewById(R.id.imageView);
            connecting_graphic.setImageResource(R.drawable.check_mark);


            nextState.putExtra("ServerConnection", myConnection);

        }
        //else
        else {
            // spawn failure
            ImageView connecting_graphic = (ImageView) findViewById(R.id.imageView);
            connecting_graphic.setImageResource(R.drawable.red_x);
            // spawn back button
            nextState = new Intent(this, MainActivity.class);
        }
    }

    public void waitForResponse(ServerConnection new_server){
        FileOutputStream out = null;
        InputStream inputStream = new_server.getServerInput();
        try{
            out = new FileOutputStream(new File(Environment.DIRECTORY_DOCUMENTS, "01"));
            byte[] buffer = new byte[1024]; // 1KB buffer size
            int length = 0;
            while ((length = inputStream.read(buffer, 0, buffer.length)) != -1) {
                out.write(buffer, 0, length);
            }
            out.flush();
        } catch(Exception e) {
        } finally {
            if (out != null)
                try {
                    out.close();
                }
                catch(Exception e){

                }
            new_server.close(); // Will close the outputStream, too.
        }
    }

    public void nextState(View view){
        if (nextState.resolveActivity(getPackageManager()) != null) {
            fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);  // create a file to save the video
            nextState.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);  // set the image file name

            nextState.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);     //spawn start_video button
            nextState = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        }
        else {
            startActivity(nextState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {     //spawn start_video button
            nextState = new Intent(android.provider.MediaStore.ACTION_VIDEO_CAPTURE);
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // Video captured and saved to fileUri specified in the Intent
                //Toast.makeText(this, "Video saved to:\n" +
                 //       data.getData(), Toast.LENGTH_LONG).show();
                Log.d("ScanActivity", "Server IP: " + MainActivity.serverIp);
                setServer(MainActivity.serverIp);
                //ServerConnection.sendFiles(fileUri.getPath());
                connectToServer();
                //upload(fileUri.getPath());

            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the video capture
            } else {
                // Video capture failed, advise user
            }
        }
    }
}
