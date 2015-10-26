package eecs393team.eecs3933dapp;

import android.os.AsyncTask;

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
        return doInBackground();
    }

    protected Boolean doInBackground(String... strings){
        try {
            server= new Socket(ip, 8081);
            return true;
        } catch(Exception e){
            return false;
        }
    }


    public boolean filesToSend(){
        return false;
    }

    public boolean sendFiles(){
        return false;
    }

    public boolean receiveMesh(){
        return false;
    }

}
