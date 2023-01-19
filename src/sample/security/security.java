package sample.security;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class security {

    @FXML
    private Label loginStatus;

    @FXML
    private PasswordField passField;

    @FXML
    private TextField userField;

    private Stage stage;
    private Scene scene;
    private Parent parent;

    @FXML
    void cancelButton(ActionEvent event) {
        Platform.exit();
        System.exit(0);
    }



    @FXML
    void loginButton(ActionEvent event) {
        try {
            if (userField.getText().isEmpty()) {
                loginStatus.setText("Enter the user name !!");
            } else if (passField.getText().isEmpty()) {
                loginStatus.setText("Enter the password !!");
            } else if (userField.getText().equals("youssef") && passField.getText().equals("1234")) {
                checkLogin(event, userField.getText());
            } else if (userField.getText().equals("mousa") && passField.getText().equals("12345")) {
                checkLogin(event, userField.getText());
            } else if (userField.getText().equals("moataz") && passField.getText().equals("123456")) {
                checkLogin(event, userField.getText());
            } else {
              try {
                      File b = new File("C:\\cryptxRegistration.txt");
                      if(!b.exists()){
                          b.createNewFile();
                      }
                      Scanner dd = new Scanner(b);
//                      String userpass = dd.nextLine();
                      String userpass=userField.getText()+":"+passField.getText();
                      while (dd.hasNext()) {
                          String p = dd.nextLine();
                          if (userpass.equals(p)) {
                              mainUserCeaser(event);
                              JOptionPane.showMessageDialog(null,"Hello "+userField.getText());
                          }
                      }
                      dd.close();

              }catch (Exception rt){
                  loginStatus.setText("Invalid user or password");
              }
            }
        }catch (Exception e){
            loginStatus.setText("Connection error !!");
        }

    }


    @FXML
    void submitRegister(ActionEvent event) throws IOException {
        if(userField.getText().isEmpty()||passField.getText().isEmpty()){
           loginStatus.setText("Enter new user & password!");
        }else{
            boolean flag=true;
            File b = new File("C:\\cryptxRegistration.txt");
            if(!b.exists()){
                b.createNewFile();
            }
            Scanner s1=new Scanner(b);
            String user = userField.getText();
            String pass = passField.getText();
            while (s1.hasNext()) {
                String p = s1.nextLine();
                if (user.equals(p)) {

                    flag=false;
                }
            }
            if (flag==false){
                loginStatus.setText("Already you have account!");
            }else {
                register r1 = new register(user,pass);
                r1.tofile();
                r1.readOutWriteLogin(user+":"+pass);
                r1.readLoginWriteOut();
                loginStatus.setText("Account has been created");
                userField.clear();
                passField.clear();
            }
        }
    }


    private void mainCeaser(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/scenes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void mainUserCeaser(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/scenes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void register(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void checkLogin(ActionEvent event,String AN){
        try {
            File f=new File("C:\\Users\\Public\\Active.txt");
            File f1=new File("C:\\license.txt");
            Scanner s=new Scanner(f);
            Scanner s1=new Scanner(f1);
            String k=s.nextLine();
            String activeYoussef=s1.nextLine();
            String activeMousa=s1.nextLine();
            String activeMoataz=s1.nextLine();



            if(k.equals(activeYoussef)){
                JOptionPane.showMessageDialog(null,"Welcome admin Youssef with user : "+AN);
                mainCeaser(event);
            }else if(k.equals(activeMousa)){
                JOptionPane.showMessageDialog(null,"Welcome admin Mousa with user : "+AN);
                mainCeaser(event);
            }else if(k.equals(activeMoataz)){
                JOptionPane.showMessageDialog(null,"Welcome admin Moataz with user : "+AN);
                mainCeaser(event);
            }else {
                loginStatus.setText("!!! You are not admin !!!");
            }

        }catch (Exception e){
            loginStatus.setText("Error accrued ,You are not admin !!");
        }
    }

    @FXML
    private void mainLogin(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("security.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


}
