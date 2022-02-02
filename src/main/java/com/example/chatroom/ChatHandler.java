package com.example.chatroom;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Vector;

public class ChatHandler extends Thread{
    DataInputStream display;
    PrintStream sender;
    static Vector<ChatHandler> ClientVector= new Vector<ChatHandler>();
    public ChatHandler(Socket cs)
    {
        try {
            display = new DataInputStream(cs.getInputStream());
            sender = new PrintStream(cs.getOutputStream());
        } catch (IOException e) {
            e.getMessage();
        }
        ClientVector.add(this);
        start();

    }
    public void run()
    {
        while(true)
        {
            String str= null;
            try {
                str = display.readLine();
                sendMassageToAll(str);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void sendMassageToAll(String msg)
    {try {
        for (ChatHandler ch : ClientVector) {
            ch.sender.println(msg + " " + ch.getId());
        }
    }catch(Exception e){e.getMessage();}
    }
}
