package com.example.chatroom;

import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer  {

    ServerSocket  myServerSocket;
    public MyServer() throws Exception
    {
        try {
            myServerSocket = new ServerSocket(5005);
            while (true) {
                Socket s = myServerSocket.accept();
                new ChatHandler(s);
            }
        }catch (IOException e)
        {
            e.getMessage();
        }
    }
    public static void main(String[] args) {

        try {
            new MyServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
