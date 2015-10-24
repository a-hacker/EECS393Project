package eecs393team.eecs3933dapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class ServerConnection {

    private String ip;

    public ServerConnection(String ip){
        this.ip = ip;
    }

    public String getServerIP(){
        return ip;
    }

    public boolean connectToServer(){
        return false;
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
