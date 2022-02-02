package com.example.chatroom;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;


public class HelloApplication extends Application {
    BorderPane root;
    TextArea chatArea;
    Label wMassage;
    TextField massage;
    Button send;
    Button show ;
    Button save;
    FlowPane myform;
    Socket mySocket;
    DataInputStream display;
    PrintStream  sender;
    public  void init()  {
        root = new BorderPane();
        chatArea = new TextArea();
        wMassage = new Label("write your massage");
        massage = new TextField();
        send = new Button("Send");
        show = new Button("Show");
        save = new Button("Save");
        try {
            mySocket = new Socket("127.0.0.1", 5005);
            display =  new DataInputStream(mySocket.getInputStream());
            sender = new PrintStream(mySocket.getOutputStream());
        }catch (Exception e)
        {
            e.getMessage();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    String replymsg;
                    try{
                        replymsg =display.readLine();
                        System.out.println(replymsg);
                        chatArea.appendText("\n"+replymsg);
                    }catch (Exception e){e.getMessage();}
                }
            }
        }).start();



    }
    @Override
    public void start(Stage stage) throws IOException {
        send.setDefaultButton(true);
        chatArea.setEditable(false);
        myform = new FlowPane(wMassage,massage,send,show,save);
        root.setCenter(chatArea);
        root.setBottom(myform);
        massage.setPromptText("write massage..");
        Scene scene = new Scene(root, 380, 240);
        stage.setTitle("ChatRoom!");
        stage.setScene(scene);
        stage.show();
        send.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
               try {
                   sender.println(massage.getText());
                   massage.setText("");
               }
               catch (Exception e)
               {
                   e.getMessage();
               }
            }
        });
        show.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Open Resource File");
                try {

                    FileInputStream fr = new FileInputStream(fileChooser.showOpenDialog(stage));
                    byte[] myarr = new byte[fr.available()];
                    fr.read(myarr);
                    String str = new String(myarr);
                    chatArea.setText(str);
                    fr.close();
                } catch (Exception e) {
                    e.getMessage();

                }
            }

        });
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"));

                byte[] str = chatArea.getText().getBytes();
                FileOutputStream fw = null;
                try {
                    fw = new FileOutputStream(fileChooser.showSaveDialog(stage));
                    for(byte v:str){fw.write(v);}

                    fw.close();
                } catch (Exception e) {
                    e.getMessage();
                }
            }});


    }


    public static void main(String[] args) {
        launch();
        new HelloApplication();
    }
}