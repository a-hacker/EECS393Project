package eecs393team.eecs3933dapp;

import android.os.AsyncTask;
import android.os.Environment;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.InputStreamEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.*;

public class ServerConnection extends AsyncTask<String, Void, Boolean> implements Serializable{

    private String ip;
    private Socket server;

    public ServerConnection(String ip){
        this.ip = ip;
    }

    public String getServerIP(){
        return ip;
    }

    public boolean connectToServer(){
        try {
            server= new Socket(ip, 8080);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    protected OutputStream getServerOutput(){
        try {
            return server.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    protected Boolean doInBackground(String... strings){
        return connectToServer();
    }


    public String filesToSend(){
        return "false";
    }

    public static boolean sendFiles(String filename){
        File file = new File(filename);
        try {
            HttpClient httpclient = new DefaultHttpClient();

            HttpPost httppost = new HttpPost("172.20.11.49");

            InputStreamEntity reqEntity = new InputStreamEntity(new FileInputStream(file), -1);
            reqEntity.setContentType("binary/octet-stream");
            reqEntity.setChunked(true); // Send in multiple parts if needed
            httppost.setEntity(reqEntity);
            HttpResponse response = httpclient.execute(httppost);

        } catch (Exception e) {
            // show error
        }

        return false;
    }

    public boolean receiveMesh(){
        return false;
    }

    public void close() {
        try {
            server.close();
        }
        catch (Exception e){

        }
    }
}
