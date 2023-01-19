package sample.classes;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Scanner;

public class symmetric {

    private Stage stage;
    private Scene scene;
    private Parent parent;


    @FXML
    private TextField keyField;

    @FXML
    private TextArea textAreaClean;

    @FXML
    private TextArea textAreaED;

    @FXML
    private Label status;

    @FXML
    private Label status1;

    @FXML
    private Label status2;

    @FXML
    private TextField shaField;

    @FXML
    private JFXCheckBox fileCheckBox;

    @FXML
    private RadioButton ceaserScene;

    @FXML
    private RadioButton symmetricScene;


    @FXML
    void symmetricEnc() {
        boolean flag=true;
        File src_file =null;
        File dest_dir =null;
        if (!fileCheckBox.isSelected()) {

            if (keyField.getText().isEmpty()) {
                status.setText("Pleas enter any key");
                status1.setText("");
                status2.setText("");
            }else {
                String s = keyField.getText();
                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag=false;
                        break;
                    }
                }
                if(flag){
                    if (Integer.parseInt(keyField.getText()) < 121) {
                        status.setText("The Key is too weak");
                        status1.setText("");
                        status2.setText("");
                    } else if (textAreaClean.getText().isEmpty()) {
                        status.setText("Pleas enter any paragraph to work on it");
                        status1.setText("");
                        status2.setText("");
                    } else {
                        shaField.clear();
                        shaField.appendText(encryptThisString(textAreaClean.getText()));
                        textAreaED.clear();
                        status.setText("");
                        status1.setText("");
                        int key = Integer.parseInt(keyField.getText());
                        String ORGtxt = textAreaClean.getText();
                        char[] arr = ORGtxt.toCharArray();
                        int[] keyarr = new int[arr.length];
                        StringBuffer ENCtxt = new StringBuffer("");
                        String temp = Integer.toString(key);
                        String temp2;
                        int temp3;
                        int[] newGuess = new int[temp.length()];
                        int keyIndex = 0;
                        for (int i = 0; i < temp.length(); i++) {
                            if (i != temp.length()) {
                                temp2 = temp.substring(i, i + 1);
                            } else {
                                temp2 = temp.substring(i);
                            }
                            temp3 = Integer.parseInt(temp2);
                            newGuess[i] = temp3;
                        }
                        for (int i = 0; i < arr.length; i++) {
                            if (keyIndex == newGuess.length) {
                                keyIndex = 0;
                            }
                            keyarr[i] = newGuess[keyIndex];
                            keyIndex++;
                        }
                        status2.setText("Encryption Key Length: " + keyarr.length);
                        for (int a = 0; a < arr.length; a++) {
                            for (int j = 0; j < arr.length; j++) {
                                arr[j] += keyarr[j];
                            }
                        }
                        status1.setText("Finish Encrypting ");
                        ENCtxt.append(arr);
                        textAreaED.appendText(ENCtxt.toString());
                    }
                }else{
                    keyField.clear();
                    status.setText("Key must be numeric only");
                    status1.setText("");
                    status2.setText("");
                }
            }///////////////////////////////////////file select ///////////////////////////////////////////////////////////////////////////////////
        }else {

            if (keyField.getText().isEmpty()) {
                status.setText("Pleas enter any key");
                status1.setText("");
                status2.setText("");
            }
            else {

                String s = keyField.getText();
                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    JOptionPane.showMessageDialog(null,"Select file");
                    FileChooser fileChooser=new FileChooser();
                    File fFile=fileChooser.showOpenDialog(new Stage());
                    String src= String.valueOf(fFile);
                    textAreaClean.setText(src);
                    JOptionPane.showMessageDialog(null,"Select output folder");
                    DirectoryChooser dChooser=new DirectoryChooser();
                    File file=dChooser.showDialog(new Stage());
                    String dest= String.valueOf(file);
                    textAreaED.setText(dest);
                    src_file = new File(src);
                    dest_dir = new File(dest);
                    try {
                        int x = 0;
                        Scanner src_scan = new Scanner(src_file);
                        File dest_file = new File(dest_dir.getAbsolutePath() + "\\" + src_file.getName());
                        if (dest_file.exists()) {
                            while (dest_file.exists()) {
                                x++;
                                dest_file = new File(dest_dir.getAbsolutePath() + "\\" + x + src_file.getName());
                            }
                        }
                        if (Integer.parseInt(keyField.getText()) < 121) {
                            status.setText("The Key is too weak");
                            status1.setText("");
                            status2.setText("");
                        } else if (textAreaClean.getText().isEmpty()) {
                            status.setText("Pleas enter any paragraph to work on it");
                            status1.setText("");
                            status2.setText("");
                        } else {
                            shaField.clear();
                            //shaField.appendText(encryptThisString(textAreaClean.getText()));
                            //textAreaED.clear();
                            //status.setText("");
                            status1.setText("");
                            int key = Integer.parseInt(keyField.getText());
                            PrintWriter pw=new PrintWriter(dest_file);
                            while (src_scan.hasNext()){
                                String ORGtxt = src_scan.nextLine();
                                char[] arr = ORGtxt.toCharArray();
                                int[] keyarr = new int[arr.length];
                                StringBuffer ENCtxt = new StringBuffer("");
                                String temp = Integer.toString(key);
                                String temp2;
                                int temp3;
                                int[] newGuess = new int[temp.length()];
                                int keyIndex = 0;
                                for (int i = 0; i < temp.length(); i++) {
                                    if (i != temp.length()) {
                                        temp2 = temp.substring(i, i + 1);
                                    } else {
                                        temp2 = temp.substring(i);
                                    }
                                    temp3 = Integer.parseInt(temp2);
                                    newGuess[i] = temp3;
                                }
                                for (int i = 0; i < arr.length; i++) {
                                    if (keyIndex == newGuess.length) {
                                        keyIndex = 0;
                                    }
                                    keyarr[i] = newGuess[keyIndex];
                                    keyIndex++;
                                }
                                // status2.setText("Encryption Key Length: " + keyarr.length);
                                for (int a = 0; a < arr.length; a++) {
                                    for (int j = 0; j < arr.length; j++) {
                                        arr[j] += keyarr[j];
                                    }
                                }
                                ENCtxt.append(arr);
                                pw.println(ENCtxt.toString());
                            }
                            status.setText("");
                            status2.setText("");
                            status1.setText("Finish Encrypting");
                            status.setText("");
                            pw.close();
                            src_scan.close();
                        }}catch (Exception e){}
                } else {
                    keyField.clear();
                    status.setText("Key must be numeric only");
                    status1.setText("");
                    status2.setText("");
                }
            }
        }
    }
    @FXML
    void symmetricDec() {
        boolean flag=true;
        File src_file = null;
        File dest_dir =null;
        if (!fileCheckBox.isSelected()) {

            if (keyField.getText().isEmpty()) {
                status.setText("Pleas enter any key");
                status1.setText("");
                status2.setText("");
            }else {
                String s = keyField.getText();
                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag=false;
                        break;
                    }
                }
                if(flag){
                    if (Integer.parseInt(keyField.getText()) < 121) {
                        status.setText("The Key is too weak");
                        status1.setText("");
                        status2.setText("");
                    } else if (textAreaClean.getText().isEmpty()) {
                        status.setText("Pleas enter any paragraph to work on it");
                        status1.setText("");
                        status2.setText("");
                    } else {
                        shaField.clear();
                        shaField.appendText(encryptThisString(textAreaClean.getText()));
                        textAreaED.clear();
                        status.setText("");
                        status1.setText("");
                        int key = Integer.parseInt(keyField.getText());
                        String ORGtxt = textAreaClean.getText();
                        char[] arr = ORGtxt.toCharArray();
                        int[] keyarr = new int[arr.length];
                        StringBuffer ENCtxt = new StringBuffer("");
                        String temp = Integer.toString(key);
                        String temp2;
                        int temp3;
                        int[] newGuess = new int[temp.length()];
                        int keyIndex = 0;
                        for (int i = 0; i < temp.length(); i++) {
                            if (i != temp.length()) {
                                temp2 = temp.substring(i, i + 1);
                            } else {
                                temp2 = temp.substring(i);
                            }
                            temp3 = Integer.parseInt(temp2);
                            newGuess[i] = temp3;
                        }
                        for (int i = 0; i < arr.length; i++) {
                            if (keyIndex == newGuess.length) {
                                keyIndex = 0;
                            }
                            keyarr[i] = newGuess[keyIndex];
                            keyIndex++;
                        }
                        status2.setText("Encryption Key Length: " + keyarr.length);
                        for (int a = 0; a < arr.length; a++) {
                            for (int j = 0; j < arr.length; j++) {
                                arr[j] -= keyarr[j];
                            }
                        }
                        status1.setText("Finish Decrypting");
                        ENCtxt.append(arr);
                        textAreaED.appendText(ENCtxt.toString());
                    }
                }else{
                    keyField.clear();
                    status.setText("Key must be numeric only");
                    status1.setText("");
                    status2.setText("");
                }
            }////////////////////////////////////////file is not select ///////////////////////////////////////////////////////////////////////////////////
        }else {

            if (keyField.getText().isEmpty()) {
                status.setText("Pleas enter any key");
                status1.setText("");
                status2.setText("");
            }
            else {
                String s = keyField.getText();
                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    JOptionPane.showMessageDialog(null,"Select file");
                    FileChooser fileChooser=new FileChooser();
                    File fFile=fileChooser.showOpenDialog(new Stage());
                    String src= String.valueOf(fFile);
                    textAreaClean.setText(src);
                    JOptionPane.showMessageDialog(null,"Select output folder");
                    DirectoryChooser dChooser=new DirectoryChooser();
                    File file=dChooser.showDialog(new Stage());
                    String dest= String.valueOf(file);
                    textAreaED.setText(dest);
                    src_file = new File(src);
                    dest_dir = new File(dest);
                    try {
                        int x = 0;
                        Scanner src_scan = new Scanner(src_file);
                        File dest_file = new File(dest_dir.getAbsolutePath() + "\\" + src_file.getName());
                        if (dest_file.exists()) {
                            while (dest_file.exists()) {
                                x++;
                                dest_file = new File(dest_dir.getAbsolutePath() + "\\" + x + src_file.getName());
                            }
                        }
                        if (Integer.parseInt(keyField.getText()) < 121) {
                            status.setText("The Key is too weak");
                            status1.setText("");
                            status2.setText("");
                        } else if (textAreaClean.getText().isEmpty()) {
                            status.setText("Pleas enter any paragraph to work on it");
                            status1.setText("");
                            status2.setText("");
                        } else {
                            shaField.clear();
                            //shaField.appendText(encryptThisString(textAreaClean.getText()));
                            //textAreaED.clear();
                            status.setText("");
                            status1.setText("");
                            int key = Integer.parseInt(keyField.getText());
                            PrintWriter pw=new PrintWriter(dest_file);
                            while (src_scan.hasNext()){
                                String ORGtxt = src_scan.nextLine();
                                char[] arr = ORGtxt.toCharArray();
                                int[] keyarr = new int[arr.length];
                                StringBuffer ENCtxt = new StringBuffer("");
                                String temp = Integer.toString(key);
                                String temp2;
                                int temp3;
                                int[] newGuess = new int[temp.length()];
                                int keyIndex = 0;
                                for (int i = 0; i < temp.length(); i++) {
                                    if (i != temp.length()) {
                                        temp2 = temp.substring(i, i + 1);
                                    } else {
                                        temp2 = temp.substring(i);
                                    }
                                    temp3 = Integer.parseInt(temp2);
                                    newGuess[i] = temp3;
                                }
                                for (int i = 0; i < arr.length; i++) {
                                    if (keyIndex == newGuess.length) {
                                        keyIndex = 0;
                                    }
                                    keyarr[i] = newGuess[keyIndex];
                                    keyIndex++;
                                }
                                // status2.setText("Encryption Key Length: " + keyarr.length);
                                for (int a = 0; a < arr.length; a++) {
                                    for (int j = 0; j < arr.length; j++) {
                                        arr[j] -= keyarr[j];
                                    }
                                }
                                status.setText("");
                                status2.setText("");
                                status1.setText("Finish Decrypting");
                                ENCtxt.append(arr);
                                pw.println(ENCtxt.toString());
                            }
                            pw.close();
                            src_scan.close();
                        }}catch (Exception e){}
                } else {
                    keyField.clear();
                    status.setText("Key must be numeric only");
                    status1.setText("");
                    status2.setText("");
                }
            }
        }
    }







//--------------------------------------------------------------


    @FXML
    private void Logout(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../security/security.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void back(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/scenes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void newPoint(ActionEvent event) throws IOException {

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../fxml/scenes.fxml"));
            Parent root1 = (Parent) fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("CRYPT-X");
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("image/favicon.png")));
            stage.setResizable(false);
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    void clearClean(){
        textAreaClean.clear();
    }

    @FXML
    void clearED(){
        textAreaED.clear();
    }

//------------------------------------------------------------------------------------------------------


    public static String encryptThisString(String input)
    {
        try {
            // getInstance() method is called with algorithm SHA3-512
            MessageDigest md = MessageDigest.getInstance("SHA3-512");

            // digest() method is called
            // to calculate message digest of the input string
            // returned as array of byte
            byte[] messageDigest = md.digest(input.getBytes());

            // Convert byte array into signum representation
            BigInteger no = new BigInteger(1, messageDigest);

            // Convert message digest into hex value
            String hashtext = no.toString(16);

            // Add preceding 0s to make it 32 bit
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }

            // return the HashText
            return hashtext;
        }

        // For specifying wrong message digest algorithms
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }



}