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

public class trySemmetricShift {


    private Stage stage;
    private Scene scene;
    private Parent parent;


    @FXML
    private TextField keyField;

    @FXML
    private TextField keyField2;

    @FXML
    private TextField keyField3;

    @FXML
    private TextArea textAreaClean;

    @FXML
    private TextArea textAreaED;

    @FXML
    private JFXCheckBox fileCheckBox;


    @FXML
    private Label status;

    @FXML
    private Label status1;

    @FXML
    private Label status2;

    @FXML
    private TextField shaField;

    @FXML
    private RadioButton ceaserScene;

    @FXML
    private RadioButton symmetricScene;








    @FXML
    void symmetricEncTRiShift(){
        boolean flag=true;
        boolean flag2=true;
        boolean flag3=true;
        if (fileCheckBox.isSelected()){
            if (keyField.getText().isEmpty() || keyField2.getText().isEmpty() || keyField3.getText().isEmpty()) {
                status.setText("Pleas enter required key set");
                status1.setText("");
                status2.setText("");
            }else {
                String s = keyField.getText();
                String s2 = keyField2.getText();
                String s3 = keyField3.getText();
                char[] arrx = s.toCharArray();
                char[] arrx2 = s2.toCharArray();
                char[] arrx3 = s3.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag=false;
                        break;
                    }
                }
                for (int d = 0; d < arrx2.length; d++) {
                    if (!Character.isDigit(arrx2[d])) {
                        flag2=false;
                        break;
                    }
                }
                for (int d = 0; d < arrx3.length; d++) {
                    if (!Character.isDigit(arrx3[d])) {
                        flag3=false;
                        break;
                    }
                }
                if(flag==true && flag2==true && flag3==true){
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
                    File src_file = new File(src);
                    File dest_dir = new File(dest);

                    try {
                        int x = 0;
                        Scanner src_scan = new Scanner(src_file);
                        File dest_file = dest_file = new File(dest_dir.getAbsolutePath() + "\\" + src_file.getName());
                        if (dest_file.exists()) {
                            while (dest_file.exists()) {
                                x++;
                                dest_file = new File(dest_dir.getAbsolutePath() + "\\" + x + src_file.getName());
                            }
                        }
                        status.setText("");
                        status1.setText("");
                        status2.setText("");
                        shaField.clear();
                        //  shaField.appendText(encryptThisString(textAreaClean.getText()));
                        int key = Integer.parseInt(keyField.getText());
                        int key2 = Integer.parseInt(keyField2.getText());
                        int key3 = Integer.parseInt(keyField3.getText());
                        PrintWriter pw=new PrintWriter(dest_file);
                        while (src_scan.hasNext()){
                            String ORGtxt = src_scan.nextLine();
                            char[] arr = ORGtxt.toCharArray();
                            int[] keyarr = new int[arr.length];
                            int[] keyarr2 = new int[arr.length];
                            int[] keyarr3 = new int[arr.length];
                            StringBuffer DECtxt = new StringBuffer("");
                            String temp = Integer.toString(key);
                            String tempk2 = Integer.toString(key2);
                            String tempk3 = Integer.toString(key3);
                            String temp2;
                            int temp3;
                            int[] newGuess = new int[temp.length()];
                            int[] newGuess2 = new int[temp.length()];
                            int[] newGuess3 = new int[temp.length()];
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
                            }///      ////////////////////////////////////////////key 2/////////////////////////////////////
                            tempk2 = Integer.toString(key2);
                            for (int i = 0; i < tempk2.length(); i++) {
                                if (i != tempk2.length()) {
                                    temp2 = tempk2.substring(i, i + 1);
                                } else {
                                    temp2 = tempk2.substring(i);
                                }
                                temp3 = Integer.parseInt(temp2);
                                newGuess2[i] = temp3;
                            }
                            for (int i = 0; i < arr.length; i++) {
                                if (keyIndex == newGuess2.length) {
                                    keyIndex = 0;
                                }
                                keyarr2[i] = newGuess2[keyIndex];
                                keyIndex++;
                            }///      //////////////////////////////////key 3/////////////////////////////////////////////////
                            tempk3 = Integer.toString(key3);
                            for (int i = 0; i < tempk3.length(); i++) {
                                if (i != tempk3.length()) {
                                    temp2 = tempk3.substring(i, i + 1);
                                } else {
                                    temp2 = tempk3.substring(i);
                                }
                                temp3 = Integer.parseInt(temp2);
                                newGuess3[i] = temp3;
                            }
                            for (int i = 0; i < arr.length; i++) {
                                if (keyIndex == newGuess3.length) {
                                    keyIndex = 0;
                                }
                                keyarr3[i] = newGuess3[keyIndex];
                                keyIndex++;
                            }
                            int shiftfaq = 0;
                            int shiftlim = 0;
                            for (int c = 0; c < 3; c++) {
                                for (int a = 0; a < arr.length; a++) {
                                    for (int j = 0; j < arr.length; j++) {
                                        if (shiftfaq != newGuess[0]) {
                                            arr[j] += keyarr[j];
                                            shiftfaq++;
                                        } else {
                                            if (shiftlim == newGuess[1]) {
                                                shiftfaq = 0;
                                            } else {
                                                arr[j] -= keyarr[j];
                                                shiftlim++;
                                            }
                                        }
                                    }
                                }
                            }
                            shiftfaq = 0;
                            shiftlim = 0;
                            for (int c = 0; c < 3; c++) {
                                for (int a = 0; a < arr.length; a++) {
                                    for (int j = 0; j < arr.length; j++) {
                                        if (shiftfaq != newGuess2[0]) {
                                            arr[j] += keyarr2[j];
                                            shiftfaq++;
                                        } else {
                                            if (shiftlim == newGuess2[1]) {
                                                shiftfaq = 0;
                                            } else {
                                                arr[j] -= keyarr2[j];
                                                shiftlim++;
                                            }
                                        }
                                    }
                                }
                            }
                            shiftfaq = 0;
                            shiftlim = 0;
                            for (int c = 0; c < 3; c++) {
                                for (int a = 0; a < arr.length; a++) {
                                    for (int j = 0; j < arr.length; j++) {
                                        if (shiftfaq != newGuess3[0]) {
                                            arr[j] += keyarr3[j];
                                            shiftfaq++;
                                        } else {
                                            if (shiftlim == newGuess3[1]) {
                                                shiftfaq = 0;
                                            } else {
                                                arr[j] -= keyarr3[j];
                                                shiftlim++;
                                            }
                                        }
                                    }
                                }
                            }
                            DECtxt.append(arr);

                            pw.println(DECtxt);
                            status.setText("");
                            status1.setText("Finish Encrypting ");//textAreaED.appendText(DECtxt.toString());
                            status2.setText("");
                        }
                        pw.close();
                        src_scan.close();}catch (Exception e){}

                }else{
                    if (flag==false){
                        keyField.clear();
                        status.setText("Key 1 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                    if (flag2==false){
                        keyField2.clear();
                        status.setText("Key 2 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                    if (flag3==false){
                        keyField3.clear();
                        status.setText("Key 3 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                }
            }
        }else {
            if (keyField.getText().isEmpty() || keyField2.getText().isEmpty() || keyField3.getText().isEmpty()) {
                status.setText("Pleas enter any key");
                status1.setText("");
                status2.setText("");
            }else {
                String s = keyField.getText();
                String s2 = keyField2.getText();
                String s3 = keyField3.getText();
                char[] arrx = s.toCharArray();
                char[] arrx2 = s2.toCharArray();
                char[] arrx3 = s3.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag=false;
                        break;
                    }
                }
                for (int d = 0; d < arrx2.length; d++) {
                    if (!Character.isDigit(arrx2[d])) {
                        flag2=false;
                        break;
                    }
                }
                for (int d = 0; d < arrx3.length; d++) {
                    if (!Character.isDigit(arrx3[d])) {
                        flag3=false;
                        break;
                    }
                }
                if(flag==true && flag2==true && flag3==true){
                    if (textAreaClean.getText().isEmpty()) {
                        status.setText("Pleas enter any paragraph to work on it");
                        status1.setText("");
                        status2.setText("");
                    } else {
                        textAreaED.clear();
                        status.setText("");
                        status1.setText("");
                        status2.setText("");
                        shaField.clear();
                        shaField.appendText(encryptThisString(textAreaClean.getText()));
                        int key = Integer.parseInt(keyField.getText());
                        int key2 = Integer.parseInt(keyField2.getText());
                        int key3 = Integer.parseInt(keyField3.getText());
                        String ORGtxt = textAreaClean.getText();
                        char[] arr = ORGtxt.toCharArray();
                        int[] keyarr = new int[arr.length];
                        int[] keyarr2 = new int[arr.length];
                        int[] keyarr3 = new int[arr.length];
                        StringBuffer DECtxt = new StringBuffer("");
                        String temp = Integer.toString(key);
                        String tempk2 = Integer.toString(key2);
                        String tempk3 = Integer.toString(key3);
                        String temp2;
                        int temp3;
                        int[] newGuess = new int[temp.length()];
                        int[] newGuess2 = new int[temp.length()];
                        int[] newGuess3 = new int[temp.length()];
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
                        }///      ////////////////////////////////////////////key 2/////////////////////////////////////
                        tempk2 = Integer.toString(key2);
                        for (int i = 0; i < tempk2.length(); i++) {
                            if (i != tempk2.length()) {
                                temp2 = tempk2.substring(i, i + 1);
                            } else {
                                temp2 = tempk2.substring(i);
                            }
                            temp3 = Integer.parseInt(temp2);
                            newGuess2[i] = temp3;
                        }
                        for (int i = 0; i < arr.length; i++) {
                            if (keyIndex == newGuess2.length) {
                                keyIndex = 0;
                            }
                            keyarr2[i] = newGuess2[keyIndex];
                            keyIndex++;
                        }///      //////////////////////////////////key 3/////////////////////////////////////////////////
                        tempk3 = Integer.toString(key3);
                        for (int i = 0; i < tempk3.length(); i++) {
                            if (i != tempk3.length()) {
                                temp2 = tempk3.substring(i, i + 1);
                            } else {
                                temp2 = tempk3.substring(i);
                            }
                            temp3 = Integer.parseInt(temp2);
                            newGuess3[i] = temp3;
                        }
                        for (int i = 0; i < arr.length; i++) {
                            if (keyIndex == newGuess3.length) {
                                keyIndex = 0;
                            }
                            keyarr3[i] = newGuess3[keyIndex];
                            keyIndex++;
                        }
                        int shiftfaq=0;
                        int shiftlim=0;
                        for (int c = 0; c< 3; c++) {
                            for (int a = 0; a < arr.length; a++) {
                                for (int j = 0; j < arr.length; j++) {
                                    if (shiftfaq!=newGuess[0]){
                                        arr[j] += keyarr[j];
                                        shiftfaq++;
                                    }else {
                                        if (shiftlim==newGuess[1]){
                                            shiftfaq=0;
                                        }
                                        else {
                                            arr[j] -= keyarr[j];
                                            shiftlim++;
                                        }
                                    }
                                }
                            }
                        }
                        shiftfaq=0;
                        shiftlim=0;
                        for (int c = 0; c< 3; c++) {
                            for (int a = 0; a < arr.length; a++) {
                                for (int j = 0; j < arr.length; j++) {
                                    if (shiftfaq!=newGuess2[0]){
                                        arr[j] += keyarr2[j];
                                        shiftfaq++;
                                    }else {
                                        if (shiftlim==newGuess2[1]){
                                            shiftfaq=0;
                                        }
                                        else {
                                            arr[j] -= keyarr2[j];
                                            shiftlim++;
                                        }
                                    }
                                }
                            }
                        }
                        shiftfaq=0;
                        shiftlim=0;
                        for (int c = 0; c< 3; c++) {
                            for (int a = 0; a < arr.length; a++) {
                                for (int j = 0; j < arr.length; j++) {
                                    if (shiftfaq!=newGuess3[0]){
                                        arr[j] += keyarr3[j];
                                        shiftfaq++;
                                    }else {
                                        if (shiftlim==newGuess3[1]){
                                            shiftfaq=0;
                                        }
                                        else {
                                            arr[j] -= keyarr3[j];
                                            shiftlim++;
                                        }
                                    }
                                }
                            }
                        }
                        status1.setText("Finish Encrypting ");
                        status.setText("");
                        status2.setText("");
                        DECtxt.append(arr);
                        textAreaED.appendText(DECtxt.toString());
                    }
                }else{
                    if (flag==false){
                        keyField.clear();
                        status.setText("Key 1 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                    if (flag2==false){
                        keyField2.clear();
                        status.setText("Key 2 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                    if (flag3==false){
                        keyField3.clear();
                        status.setText("Key 3 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                }
            }
        }
    }


    @FXML
    void symmetricDECTRiShift(){
        boolean flag=true;
        boolean flag2=true;
        boolean flag3=true;

        if (fileCheckBox.isSelected()){

            if (keyField.getText().isEmpty() || keyField2.getText().isEmpty() || keyField3.getText().isEmpty()) {
                status.setText("Pleas enter required key set");
                status1.setText("");
                status2.setText("");
            }else {
                String s = keyField.getText();
                String s2 = keyField2.getText();
                String s3 = keyField3.getText();
                char[] arrx = s.toCharArray();
                char[] arrx2 = s2.toCharArray();
                char[] arrx3 = s3.toCharArray();

                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag=false;
                        break;
                    }
                }
                for (int d = 0; d < arrx2.length; d++) {
                    if (!Character.isDigit(arrx2[d])) {
                        flag2=false;
                        break;
                    }
                }
                for (int d = 0; d < arrx3.length; d++) {
                    if (!Character.isDigit(arrx3[d])) {
                        flag3=false;
                        break;
                    }
                }

                if(flag==true && flag2==true && flag3==true){
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

                    File src_file = new File(src);
                    File dest_dir = new File(dest);

                    try {
                        System.out.println("try");
                        int x = 0;
                        Scanner src_scan = new Scanner(src_file);
                        File dest_file = dest_file = new File(dest_dir.getAbsolutePath() + "\\" + src_file.getName());
                        if (dest_file.exists()) {
                            while (dest_file.exists()) {
                                x++;
                                dest_file = new File(dest_dir.getAbsolutePath() + "\\" + x + src_file.getName());
                            }
                        }
                        status.setText("");
                        status1.setText("");
                        status2.setText("");
                        shaField.clear();
                        //  shaField.appendText(encryptThisString(textAreaClean.getText()));
                        int key = Integer.parseInt(keyField.getText());
                        int key2 = Integer.parseInt(keyField2.getText());
                        int key3 = Integer.parseInt(keyField3.getText());
                        PrintWriter pw=new PrintWriter(dest_file);
                        while (src_scan.hasNext()){
                            String ORGtxt = src_scan.nextLine();
                            char[] arr = ORGtxt.toCharArray();
                            int[] keyarr = new int[arr.length];
                            int[] keyarr2 = new int[arr.length];
                            int[] keyarr3 = new int[arr.length];
                            StringBuffer DECtxt = new StringBuffer("");
                            String temp = Integer.toString(key);
                            String tempk2 = Integer.toString(key2);
                            String tempk3 = Integer.toString(key3);
                            String temp2;
                            int temp3;
                            int[] newGuess = new int[temp.length()];
                            int[] newGuess2 = new int[temp.length()];
                            int[] newGuess3 = new int[temp.length()];
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
                            //      ////////////////////////////////////////////key 2/////////////////////////////////////
                            tempk2 = Integer.toString(key2);

                            for (int i = 0; i < tempk2.length(); i++) {
                                if (i != tempk2.length()) {
                                    temp2 = tempk2.substring(i, i + 1);
                                } else {
                                    temp2 = tempk2.substring(i);
                                }
                                temp3 = Integer.parseInt(temp2);
                                newGuess2[i] = temp3;
                            }

                            for (int i = 0; i < arr.length; i++) {
                                if (keyIndex == newGuess2.length) {
                                    keyIndex = 0;
                                }
                                keyarr2[i] = newGuess2[keyIndex];
                                keyIndex++;
                            }//      //////////////////////////////////key 3/////////////////////////////////////////////////

                            tempk3 = Integer.toString(key3);

                            for (int i = 0; i < tempk3.length(); i++) {
                                if (i != tempk3.length()) {
                                    temp2 = tempk3.substring(i, i + 1);
                                } else {
                                    temp2 = tempk3.substring(i);
                                }
                                temp3 = Integer.parseInt(temp2);
                                newGuess3[i] = temp3;
                            }

                            for (int i = 0; i < arr.length; i++) {
                                if (keyIndex == newGuess3.length) {
                                    keyIndex = 0;
                                }
                                keyarr3[i] = newGuess3[keyIndex];
                                keyIndex++;
                            }


                            int shiftfaq = 0;
                            int shiftlim = 0;
                            for (int c = 0; c < 3; c++) {
                                for (int a = 0; a < arr.length; a++) {
                                    for (int j = 0; j < arr.length; j++) {
                                        if (shiftfaq != newGuess[0]) {
                                            arr[j] -= keyarr[j];
                                            shiftfaq++;
                                        } else {
                                            if (shiftlim == newGuess[1]) {
                                                shiftfaq = 0;

                                            } else {
                                                arr[j] += keyarr[j];
                                                shiftlim++;
                                            }
                                        }
                                    }
                                }
                            }
                            shiftfaq = 0;
                            shiftlim = 0;
                            for (int c = 0; c < 3; c++) {
                                for (int a = 0; a < arr.length; a++) {
                                    for (int j = 0; j < arr.length; j++) {
                                        if (shiftfaq != newGuess2[0]) {
                                            arr[j] -= keyarr2[j];
                                            shiftfaq++;
                                        } else {
                                            if (shiftlim == newGuess2[1]) {
                                                shiftfaq = 0;

                                            } else {
                                                arr[j] += keyarr2[j];
                                                shiftlim++;
                                            }
                                        }
                                    }
                                }
                            }
                            shiftfaq = 0;
                            shiftlim = 0;
                            for (int c = 0; c < 3; c++) {
                                for (int a = 0; a < arr.length; a++) {
                                    for (int j = 0; j < arr.length; j++) {
                                        if (shiftfaq != newGuess3[0]) {
                                            arr[j] -= keyarr3[j];
                                            shiftfaq++;
                                        } else {
                                            if (shiftlim == newGuess3[1]) {
                                                shiftfaq = 0;

                                            } else {
                                                arr[j] += keyarr3[j];
                                                shiftlim++;
                                            }
                                        }
                                    }
                                }
                            }
                            DECtxt.append(arr);
                            pw.println(DECtxt);
                            status.setText("");
                            status1.setText("Finish Decrypting ");
                            //textAreaED.appendText(DECtxt.toString());
                        }

                        pw.close();
                        src_scan.close();}catch (Exception e){}


                }else{
                    if (flag==false){
                        keyField.clear();
                        status.setText("Key 1 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                    if (flag2==false){
                        keyField2.clear();
                        status.setText("Key 2 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                    if (flag3==false){
                        keyField3.clear();
                        status.setText("Key 3 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                }
            }
        }else {
            if (keyField.getText().isEmpty() || keyField2.getText().isEmpty() || keyField3.getText().isEmpty()) {
                status.setText("Pleas enter any key");
                status1.setText("");
                status2.setText("");
            }else {
                String s = keyField.getText();
                String s2 = keyField2.getText();
                String s3 = keyField3.getText();
                char[] arrx = s.toCharArray();
                char[] arrx2 = s2.toCharArray();
                char[] arrx3 = s3.toCharArray();

                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag=false;
                        break;
                    }
                }
                for (int d = 0; d < arrx2.length; d++) {
                    if (!Character.isDigit(arrx2[d])) {
                        flag2=false;
                        break;
                    }
                }
                for (int d = 0; d < arrx3.length; d++) {
                    if (!Character.isDigit(arrx3[d])) {
                        flag3=false;
                        break;
                    }
                }

                if(flag==true && flag2==true && flag3==true){
                    if (textAreaClean.getText().isEmpty()) {
                        status.setText("Pleas enter any paragraph to work on it");
                        status1.setText("");
                        status2.setText("");
                    } else {
                        textAreaED.clear();
                        status.setText("");
                        status1.setText("");
                        status2.setText("");
                        shaField.clear();
                        shaField.appendText(encryptThisString(textAreaClean.getText()));
                        int key = Integer.parseInt(keyField.getText());
                        int key2 = Integer.parseInt(keyField2.getText());
                        int key3 = Integer.parseInt(keyField3.getText());
                        String ORGtxt = textAreaClean.getText();
                        char[] arr = ORGtxt.toCharArray();
                        int[] keyarr = new int[arr.length];
                        int[] keyarr2 = new int[arr.length];
                        int[] keyarr3 = new int[arr.length];
                        StringBuffer DECtxt = new StringBuffer("");
                        String temp = Integer.toString(key);
                        String tempk2 = Integer.toString(key2);
                        String tempk3 = Integer.toString(key3);
                        String temp2;
                        int temp3;
                        int[] newGuess = new int[temp.length()];
                        int[] newGuess2 = new int[temp.length()];
                        int[] newGuess3 = new int[temp.length()];
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
                        //      ////////////////////////////////////////////key 2/////////////////////////////////////
                        tempk2 = Integer.toString(key2);

                        for (int i = 0; i < tempk2.length(); i++) {
                            if (i != tempk2.length()) {
                                temp2 = tempk2.substring(i, i + 1);
                            } else {
                                temp2 = tempk2.substring(i);
                            }
                            temp3 = Integer.parseInt(temp2);
                            newGuess2[i] = temp3;
                        }

                        for (int i = 0; i < arr.length; i++) {
                            if (keyIndex == newGuess2.length) {
                                keyIndex = 0;
                            }
                            keyarr2[i] = newGuess2[keyIndex];
                            keyIndex++;
                        }//      //////////////////////////////////key 3/////////////////////////////////////////////////

                        tempk3 = Integer.toString(key3);

                        for (int i = 0; i < tempk3.length(); i++) {
                            if (i != tempk3.length()) {
                                temp2 = tempk3.substring(i, i + 1);
                            } else {
                                temp2 = tempk3.substring(i);
                            }
                            temp3 = Integer.parseInt(temp2);
                            newGuess3[i] = temp3;
                        }

                        for (int i = 0; i < arr.length; i++) {
                            if (keyIndex == newGuess3.length) {
                                keyIndex = 0;
                            }
                            keyarr3[i] = newGuess3[keyIndex];
                            keyIndex++;
                        }


                        int shiftfaq=0;
                        int shiftlim=0;
                        for (int c = 0; c< 3; c++) {
                            for (int a = 0; a < arr.length; a++) {
                                for (int j = 0; j < arr.length; j++) {
                                    if (shiftfaq!=newGuess[0]){
                                        arr[j] -= keyarr[j];
                                        shiftfaq++;
                                    }else {
                                        if (shiftlim==newGuess[1]){
                                            shiftfaq=0;

                                        }
                                        else {
                                            arr[j] += keyarr[j];
                                            shiftlim++;
                                        }
                                    }
                                }
                            }
                        }
                        shiftfaq=0;
                        shiftlim=0;
                        for (int c = 0; c< 3; c++) {
                            for (int a = 0; a < arr.length; a++) {
                                for (int j = 0; j < arr.length; j++) {
                                    if (shiftfaq!=newGuess2[0]){
                                        arr[j] -= keyarr2[j];
                                        shiftfaq++;
                                    }else {
                                        if (shiftlim==newGuess2[1]){
                                            shiftfaq=0;

                                        }
                                        else {
                                            arr[j] += keyarr2[j];
                                            shiftlim++;
                                        }
                                    }
                                }
                            }
                        }
                        shiftfaq=0;
                        shiftlim=0;
                        for (int c = 0; c< 3; c++) {
                            for (int a = 0; a < arr.length; a++) {
                                for (int j = 0; j < arr.length; j++) {
                                    if (shiftfaq!=newGuess3[0]){
                                        arr[j] -= keyarr3[j];
                                        shiftfaq++;
                                    }else {
                                        if (shiftlim==newGuess3[1]){
                                            shiftfaq=0;

                                        }
                                        else {
                                            arr[j] += keyarr3[j];
                                            shiftlim++;
                                        }
                                    }
                                }
                            }
                        }
                        status.setText("");
                        status1.setText("Finish Decrypting");

                        DECtxt.append(arr);
                        textAreaED.appendText(DECtxt.toString());
                    }
                }else{
                    if (flag==false){
                        keyField.clear();
                        status.setText("Key 1 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                    if (flag2==false){
                        keyField2.clear();
                        status.setText("Key 2 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                    if (flag3==false){
                        keyField3.clear();
                        status.setText("Key 3 must be numeric only");
                        status1.setText("");
                        status2.setText("");
                    }
                }
            }
        }
    }




//-------------------------------------------------------------------------------


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
    private void back(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/scenes.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void Logout(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../security/security.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void clearClean(){
        textAreaClean.clear();
    }

    @FXML
    void clearED(){
        textAreaED.clear();
    }

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


//-----------------------------------------------------------------

}
