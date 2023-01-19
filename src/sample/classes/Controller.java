package sample.classes;

//@Author's "Youssef Alkhodary" , "Mousa Ahmed" , "Moataz Gamal"

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Controller {

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
    private JFXCheckBox fileCheckBox;



//---------------------------------------------------------------------------


    @FXML
    void EncStr(ActionEvent event) {
        boolean flag=true;

        if(!fileCheckBox.isSelected()) {
            if (keyField.getText().isEmpty()) {
                status.setText("Pleas enter any key");
            } else {
                String s = keyField.getText();
                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag = false;
                        break;
                    }
                }
                if (flag == true) {
                    if (Integer.parseInt(keyField.getText()) < 1) {
                        status.setText("The Key is too weak");
                        status1.setText("");
                        status2.setText("");
                    } else if (textAreaClean.getText().isEmpty()) {
                        status.setText("Pleas enter any paragraph to encrypt ");
                        status1.setText("");
                        status2.setText("");
                    } else {
                        textAreaED.clear();
                        status.setText("");
                        status1.setText("Text has been encrypted");
                        String EnkKey = keyField.getText();
                        int key = Integer.parseInt(EnkKey);
                        char[] chars = textAreaClean.getText().toCharArray();
                        for (char c : chars) {
                            c += key;
                            textAreaED.appendText(String.valueOf(c));
                        }
                        status.setText("");
                        status1.setText("");
                        status2.setText("Finish Encrypting");
                        textAreaClean.clear();
                    }
                } else {
                    keyField.clear();
                    status.setText("Key must be numeric only");
                    status1.setText("");
                    status2.setText("");
                           }
            }//////////////////////////////////////////// FILE IS SELECTED ////////////////////////////////////////////////////////////////////////////////////
        }

        else if (fileCheckBox.isSelected()) {
            if (keyField.getText().isEmpty()) {
                status2.setText("");
                status1.setText("");
                status.setText("Pleas enter any key");
            } else {

                JOptionPane.showMessageDialog(null, "Select file");
                FileChooser fileChooser = new FileChooser();
                File fFile = fileChooser.showOpenDialog(new Stage());
                String src = String.valueOf(fFile);
                textAreaClean.setText(src);

                JOptionPane.showMessageDialog(null, "Select output folder");
                DirectoryChooser dChooser = new DirectoryChooser();
                File file = dChooser.showDialog(new Stage());
                String dest = String.valueOf(file);
                textAreaED.setText(dest);

                File src_file = new File(src);
                File dest_dir = new File(dest);

                if (!src_file.exists() || src_file.isDirectory()) {
                    status1.setText("");
                    status2.setText("");
                    status.setText("Pleas enter valid source file");
                } else {
                    if (dest_dir.isFile() || !dest_dir.exists()) {
                        status2.setText("");
                        status1.setText("");
                        status.setText("Pleas enter valid directory");
                    }
                }
                String s = keyField.getText();
                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag = false;
                        break;
                    }
                }
                if (flag == true) {
                    if (Integer.parseInt(keyField.getText()) < 1) {
                        status.setText("The Key is too weak");
                        status1.setText("");
                        status2.setText("");
                    } else {
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
                            dest_file.createNewFile();
                            PrintWriter pw = new PrintWriter(dest_file);
                            status.setText("");
                            String EnkKey = keyField.getText();
                            while (src_scan.hasNext()) {
                                String enc = "";
                                String data = src_scan.nextLine();

                                int key = Integer.parseInt(EnkKey);
                                char[] chars = data.toCharArray();
                                for (char c : chars) {
                                    c += key;
                                    enc += c;
                                }
                                pw.println(enc);
                            }
                            pw.close();
                            src_scan.close();
                            status1.setText("");
                            status.setText("");
                            status2.setText("File has been encrypted");
                        } catch (Exception e) {

                        }
                    }
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
    void DecStr(ActionEvent event) {
        boolean flag=true;

        if(!fileCheckBox.isSelected()) {
            if (keyField.getText().isEmpty()) {
                status.setText("Pleas enter any key");
                status1.setText("");
                status2.setText("");
            } else {
                String s = keyField.getText();
                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isDigit(arrx[d])) {
                        flag = false;
                        break;
                    }
                }
                if (flag == true) {
                    if (textAreaClean.getText().isEmpty()) {
                        status.setText("Pleas enter any paragraph to work on it");
                        status1.setText("");
                        status2.setText("");
                    } else {
                        textAreaED.clear();
                        status.setText("");
                        status1.setText("");
                        status2.setText("");
                        String EnkKey = keyField.getText();
                        int key = Integer.parseInt(EnkKey);
                        char[] chars = textAreaClean.getText().toCharArray();
                        for (char c : chars) {
                            c -= key;
                            textAreaED.appendText(String.valueOf(c));
                        }
                        status.setText("");
                        status1.setText("");
                        status2.setText("Finish Decrypting");
                        textAreaClean.clear();
                    }
                } else {
                    keyField.clear();
                    status.setText("Key must be numeric only");
                    status1.setText("");
                    status2.setText("");
                }
            }
            /////////////////////file selected //////////////////////////////////////////////////////////////////////////////////////////////////////
        }else {

            if (keyField.getText().isEmpty()) {
                status2.setText("");
                status1.setText("");
                status.setText("Pleas enter any key");
            }else {
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

            File src_file =new File(src);
            File dest_dir =new File(dest);

            if(!src_file.exists()||src_file.isDirectory()){
                status2.setText("");
                status1.setText("");
                status.setText("Pleas enter valid source file");
            }
            else {
                if (dest_dir.isFile()||!dest_dir.exists()){
                    status2.setText("");
                    status1.setText("");
                    status.setText("Pleas enter valid directory");
                }
            }
            String s = keyField.getText();
            char[] arrx = s.toCharArray();
            for (int d = 0; d < arrx.length; d++) {
                if (!Character.isDigit(arrx[d])) {
                    flag = false;
                    break;
                }
            }
            if (flag == true) {
                if (Integer.parseInt(keyField.getText()) < 1) {
                    status.setText("The Key is too weak");
                    status1.setText("");
                    status2.setText("");
                } else {
                    try {
                        int x=0;
                        Scanner src_scan=new Scanner(src_file);
                        File dest_file=new File(dest_dir.getAbsolutePath()+"\\"+src_file.getName());
                        if (dest_file.exists()){
                            while (dest_file.exists()){
                                x++;
                                dest_file=new File(dest_dir.getAbsolutePath()+"\\"+x+src_file.getName());
                            }}
                        dest_file.createNewFile();
                        PrintWriter pw=new PrintWriter(dest_file);
                        String EnkKey = keyField.getText();
                        while (src_scan.hasNext()){
                            String enc="";
                            String data=src_scan.nextLine();
                            int key = Integer.parseInt(EnkKey);
                            char[] chars =data.toCharArray();
                            for (char c : chars) {
                                c -= key;
                                enc+=c;
                            }
                            pw.println(enc);
                        }
                        pw.close();
                        src_scan.close();
                        status2.setText("File has been encrypted");
                        status.setText("");
                        status1.setText("");
                    }catch (Exception e){

                    }
                }
            } else {
                keyField.clear();
                status.setText("Key must be numeric only");
                status1.setText("");
                status2.setText("");
            }
            }
        }
    }
    
 
//--------------------------------------------------------------------------


    @FXML
    void clearClean(){
        textAreaClean.clear();
    }

    @FXML
    void clearED(){
        textAreaED.clear();
    }


//--------------------------------------------------------------------------

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
            stage.setScene(new Scene(root1));
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    @FXML
    private void Logout(ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../security/security.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


//-----------------------------------------------------------------------------




}
