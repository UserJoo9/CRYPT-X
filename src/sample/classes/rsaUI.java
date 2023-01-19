package sample.classes;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;


public class rsaUI {

    private Stage stage;
    private Scene scene;
    private Parent parent;


    @FXML
    private Button btnDec;

    @FXML
    private Button btnDec1;

    @FXML
    private Button btnDec11;

    @FXML
    private Button btnDec12;

    @FXML
    private Button btnDec121;

    @FXML
    private Button btnDec1211;

    @FXML
    private Button btnEnc;

    @FXML
    private TextField shaField;

    @FXML
    private TextField segneture;

    @FXML
    private JFXCheckBox fileCheckBox;

    @FXML
    private TextField secPublic;

    @FXML
    private Label status;

    @FXML
    private Label status1;

    @FXML
    private Label status2;

    @FXML
    private TextArea textAreaClean;

    @FXML
    private TextArea textAreaED;

    @FXML
    private MenuButton menu;
    public  String[] splitter(String input, int[] lengths)
    {
        String[] output = new String[lengths.length];
        int pos = 0;
        for(int i=0;i<lengths.length;i++)
        {
            output[i] = input.substring(pos, pos+lengths[i]);
            pos = pos + lengths[i];
        }
        return output;
    }
    @FXML
    void encryptionRsa(ActionEvent event) throws FileNotFoundException, NoSuchAlgorithmException, InvalidKeySpecException {
        PrintWriter pw=null;
        Scanner src_scan=null;
        boolean dflag=false;
        boolean keyflag=true;
        if (fileCheckBox.isSelected()){

            RSA r1=new RSA();
            if(r1.check_Sec_publicKey()){
                if(secPublic.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"pleas enter second public key first, in specified field");
                }else {
                    try {
                        if (secPublic.getText().toCharArray().length==344){
                        String x = secPublic.getText();
                        String y = r1.CA_dec(r1.SeGSplit(x));
                        //if (y.toCharArray().length==216)
                        r1.setSec_public(y);
                        }else {
                            keyflag=false;
                            JOptionPane.showMessageDialog(null,"public key is not valid");
                        }
                    }catch (Exception r){
                        keyflag=false;
                        JOptionPane.showMessageDialog(null,"public key is not valid");
                    }
                }
            }
            if (keyflag && !r1.check_Sec_publicKey()){
            try {
                JOptionPane.showMessageDialog(null,"Select source file");
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

                if (!src_file.exists() || src_file.isDirectory()) {
                    status2.setText("");
                    status1.setText("");
                    status.setText("Pleas enter valid source file");
                } else if (dest_dir.isFile() || !dest_dir.exists() || !dest_dir.isDirectory()) {
                    status2.setText("");
                    status1.setText("");
                    status.setText("Pleas enter valid directory");
                } else {
                    int x = 0;
                    src_scan = new Scanner(src_file);
                    File dest_file =dest_file = new File(dest_dir.getAbsolutePath() + "\\" + src_file.getName());
                    if (dest_file.exists()) {
                        while (dest_file.exists()) {
                            x++;
                            dest_file = new File(dest_dir.getAbsolutePath() + "\\" + x + src_file.getName());
                        }
                    }
                    pw=new PrintWriter(dest_file);
                    String file_line="";
                    String file_txt="";
                    String encLine="";
                    String []sub_line=new String[10];
                    try {while (src_scan.hasNext()){
                        file_line=src_scan.nextLine();
                        char [] line=file_line.toCharArray();
                        if(line.length>=116){
                            int len= line.length;
                            int v=0;
                            int[]X = new int[10];
                            while(len!=0){
                                if (len>116){
                                    X[v]=116;
                                    len-=116;
                                }else {
                                    X[v]=len;
                                    len-=len;
                                }
                                v++;
                            }
                            sub_line=splitter(file_line,X);
                            for (int i=0;i< sub_line.length;i++){
                                if (sub_line[i].isEmpty()){}else {
                                    file_txt+=sub_line[i];
                                    sub_line[i]=r1.encryption(sub_line[i]);
                                    pw.print(sub_line[i]);
                                }
                            }
                        }else{String s1 = r1.encryption(file_line);
                            char[]j=s1.toCharArray();
                            file_txt+=file_line;
                            pw.println(s1);
                        }
                    }
                    }catch (Exception c){
                        pw.close();
                        dest_file.delete();
                        keyflag=false;
                        secPublic.clear();
                        r1.setSec_public("");
                        dflag=true;
                        JOptionPane.showMessageDialog(null,"Second public key IS CORRUPTED");
                    }
                    if (dflag){

                    }else {
                        status.setText("");
                        status2.setText("");
                        status1.setText("DATA HAVE BEEN SECURED");
                        pw.close();
                        src_scan.close();
                        pw.close();
                        src_scan.close();
                        String hash = r1.HashThisString(file_txt);
                        shaField.clear();
                        shaField.appendText(hash);
                        String hashEnced = r1.connect_sha(r1.hash_enc(hash));
                        segneture.clear();
                        segneture.appendText(hashEnced);
                        secPublic.clear();
                    }

                }
            }catch (Exception e){
                pw.close();
                src_scan.close();
                pw.close();
                src_scan.close();
            }}
///////////////////////////////////////// file not selected //////////////////////////////////////////////////////////////////////////////////
        }else if (!fileCheckBox.isSelected()){
            RSA r1=new RSA();
            if(r1.check_Sec_publicKey()){
                if(secPublic.getText().isEmpty()){
                    status1.setText("");
                    status2.setText("");
                    JOptionPane.showMessageDialog(null,"pleas enter second public key first, in specified field");
                }else {
                    if (secPublic.getText().toCharArray().length==344){
                       try {
                         String x=secPublic.getText();
                         String y = r1.CA_dec(r1.SeGSplit(x));
                         r1.setSec_public(y);
                       } catch (Exception j){
                           keyflag=false;
                           secPublic.clear();
                       }
                    }else {
                        secPublic.clear();
                        JOptionPane.showMessageDialog(null,"Second public key is not valid");
                    }
                }
            }
            if (keyflag){
            if (textAreaClean.getText().isEmpty()){
                status1.setText("");
                status2.setText("");
                JOptionPane.showMessageDialog(null,"pleas enter data to encrypt in specified field");
            }else {
                if (textAreaClean.getText().toCharArray().length<117){
                   try {
                       String s1= r1.encryption(textAreaClean.getText());
                       textAreaED.setText(s1);
                   }catch (Exception d){
                       keyflag=false;
                       secPublic.clear();
                       r1.setSec_public("");
                       JOptionPane.showMessageDialog(null,"Second public key IS CORRUPTED ");
                   }
                    try {
                       if (!textAreaClean.getText().isEmpty()&&keyflag){
                        String  hash=r1.HashThisString(textAreaClean.getText());
                        shaField.clear();
                        shaField.appendText(hash);
                        String hashEnced=r1.connect_sha(r1.hash_enc(hash));
                        segneture.clear();
                        segneture.appendText(hashEnced);
                        secPublic.clear();
                        status2.setText("");
                        status1.setText("DATA HAVE BEEN SECURED ");
                        status.setText("");}
                    }catch (Exception n){
                        segneture.clear();
                        JOptionPane.showMessageDialog(null,"SIGNATURE IS CORRUPTED");
                    }
                }else {
                    status1.setText("");
                    status2.setText("");
                    JOptionPane.showMessageDialog(null,"Data is to large to be handel");
                }
            }}
        }
    }


    @FXML
    void decryptionRsa(ActionEvent event) throws Exception {
        PrintWriter pw=null;
        Scanner src_scan=null;
        boolean cflag=false;
        boolean dflag = false;
        boolean keyflag=true;
        status2.setText("");
        status1.setText("");
        status.setText("");
        RSA r1=new RSA();
        if (fileCheckBox.isSelected()){


            if(r1.check_Sec_publicKey()){
                if(secPublic.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null,"pleas enter second public key first, in specified field");
                    status1.setText("");
                    status2.setText("");
                }else {
                    try {
                        if (secPublic.getText().toCharArray().length==344){
                        String x = secPublic.getText();
                        String y = r1.CA_dec(r1.SeGSplit(x));
                        r1.setSec_public(y);
                        }else {
                          secPublic.clear();
                          keyflag=false;
                          JOptionPane.showMessageDialog(null,"public key is not valid");
                    }
                    }catch (Exception r){
                        keyflag=false;
                        JOptionPane.showMessageDialog(null,"public key is not valid");
                        status1.setText("");
                        status2.setText("");
                    }
                }
            }
            if (keyflag && !r1.check_Sec_publicKey()){
            try {
                JOptionPane.showMessageDialog(null,"Select source file");
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
                if (!src_file.exists() || src_file.isDirectory()) {
                    status2.setText("");
                    status1.setText("");
                    status.setText("Pleas enter valid source file");
                } else if (dest_dir.isFile() || !dest_dir.exists() || !dest_dir.isDirectory()) {
                    status2.setText("");
                    status1.setText("");
                    status.setText("Pleas enter valid directory");
                } else {
                    int x = 0;
                    src_scan = new Scanner(src_file);
                    File dest_file =dest_file = new File(dest_dir.getAbsolutePath() + "\\" + src_file.getName());
                    if (dest_file.exists()) {
                        while (dest_file.exists()) {
                            x++;
                            dest_file = new File(dest_dir.getAbsolutePath() + "\\" + x + src_file.getName());
                        }
                    }
                    pw=new PrintWriter(dest_file);
                    String file_line="";
                    String file_txt="";
                    String encLine="";
                    String []sub_line=new String[10];
                   try {


                    while (src_scan.hasNext()){
                        file_line=src_scan.nextLine();
                        if (file_line.toCharArray().length<171){
                            cflag=true;
                            JOptionPane.showMessageDialog(null,"DATA IS CORRUPTED ");
                            break;
                        }
                        char [] line=file_line.toCharArray();
                        if(line.length>11){
                            int len= line.length;
                            int v=0;
                            int[]X = new int[10];
                            while(len!=0){
                                if (len>172){
                                    X[v]=172;
                                    len-=172;
                                }else {
                                    X[v]=len;
                                    len-=len;
                                }
                                v++;
                            }
                            sub_line=splitter(file_line,X);
                            for (int i=0;i< sub_line.length;i++){
                                if (sub_line[i].isEmpty()){}else {
                                    sub_line[i]=r1.decryption(sub_line[i]);
                                    file_txt+=sub_line[i];
                                    pw.print(sub_line[i]);
                                }
                            }
                            pw.println("");
                        }else{
                            String s1 = r1.encryption(file_line);
                            file_txt+=file_line;
                            pw.println(s1);
                        }
                    }}catch (Exception g){
                        dflag = true;
                        keyflag=false;
                        pw.close();
                        dest_file.delete();
                        secPublic.clear();
                        r1.setSec_public("");
                       JOptionPane.showMessageDialog(null,"DATA IS CORRUPTED");
                   }
                    pw.close();
                    src_scan.close();
                    pw.close();
                    src_scan.close();
                    if (!dflag&&keyflag){
                        status1.setText("Data has been Decrypted");
                        status.setText("");
                        status.setText("");
                        String hash = r1.HashThisString(file_txt);
                        shaField.clear();
                        shaField.appendText(hash);
                    }else {

                    }
                    if (segneture.getText().isEmpty()){
                        JOptionPane.showMessageDialog(null,"ALERT THIS DATA DID NOT INCLUDE SENDER SIGNATURE !!!");
                        status2.setText("");
                        status.setText("");
                    }else {
                        if (segneture.getText().toCharArray().length==344||segneture.getText().isEmpty()){
                          if (segneture.getText().isEmpty()){
                              JOptionPane.showMessageDialog(null,"ALERT THIS DATA DID NOT INCLUDE SENDER SIGNATURE !!!");

                          }else {
                              String seg=segneture.getText();
                              segneture.clear();
                              segneture.setText("");
                              segneture.appendText(r1.hash_dec(r1.SeGSplit(seg)));                          }
                        }else {
                            segneture.clear();
                            JOptionPane.showMessageDialog(null,"SIGNATURE IS CORRUPTED");
                        }
                    }
                }
            }catch (Exception e){
                pw.close();
                src_scan.close();
                pw.close();
                src_scan.close();
            }}
 ///////////////////////////////////////// file is not selected //////////////////////////////////////////////////////////////////////////////////
        }else if (!fileCheckBox.isSelected()) {

            if(r1.check_Sec_publicKey()){
                if(secPublic.getText().isEmpty()){
                    status1.setText("");
                    status2.setText("");
                    JOptionPane.showMessageDialog(null,"pleas enter second public key first, in specified field");
                }else {
                    if (secPublic.getText().toCharArray().length==344){
                        try {
                            String x=secPublic.getText();
                            String y = r1.CA_dec(r1.SeGSplit(x));
                            r1.setSec_public(y);
                        } catch (Exception j){
                            keyflag=false;
                            secPublic.clear();
                            JOptionPane.showMessageDialog(null,"Second public key IS CORRUPTED");

                        }
                    }else {
                        secPublic.clear();
                        JOptionPane.showMessageDialog(null,"Second public key is not valid");

                    }
                }
            }

            if (keyflag) {
                if (textAreaClean.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "pleas enter data to decrypt in specified field");
                    status1.setText("");
                    status2.setText("");
                } else {

                    if (textAreaClean.getText().toCharArray().length == 172) {
                        if (segneture.getText().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "ALERT THIS DATA DID NOT INCLUDE SENDER SIGNATURE !!!");
                        } else {
                            if (segneture.getText().toCharArray().length == 344) {
                            } else {
                                segneture.clear();
                                JOptionPane.showMessageDialog(null, "SIGNATURE IS CORRUPTED");
                            }
                        }

                        String seg = "";
                        seg = segneture.getText();
                       if (keyflag){
                        try {
                        String s1 = r1.decryption(textAreaClean.getText());
                            try {
                                if (!textAreaClean.getText().isEmpty()&&keyflag){
                                    shaField.setText("");
                                    shaField.appendText(r1.HashThisString(s1));
                                    textAreaED.setText(s1);
                                    segneture.clear();
                                    segneture.appendText(r1.hash_dec(r1.SeGSplit(seg)));
                                    status1.setText("Data has been Decrypted");
                                    status2.setText("");
                                    status.setText("");
                                    }
                            }catch (Exception n){
                                segneture.clear();
                                JOptionPane.showMessageDialog(null,"SIGNATURE IS CORRUPTED");
                            }

                        }catch (Exception d){
                            keyflag=false;
                            secPublic.clear();
                            r1.setSec_public("");
                            JOptionPane.showMessageDialog(null,"DATA IS CORRUPTED ");
                        }}
                    } else {
                        JOptionPane.showMessageDialog(null, "DATA IS CORRUPTED AND CAN'T BE HANDLED ");
                        status1.setText("");
                        status2.setText("");
                    }
                }
            }
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

}
