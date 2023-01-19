package sample.classes;

import com.jfoenix.controls.JFXCheckBox;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.Scanner;


public class Vignere {

    private Stage stage;
    private Scene scene;
    private Parent parent;
    

    @FXML
    private Button btnDec;

    @FXML
    private Button btnEnc;

    @FXML
    private TextField keyField;

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
    private JFXCheckBox fileCheckBox;


    static String generateKey(String str, String key)
    {
        for (int i = 0;i<str.length() ; i++)
        {
            if (str.length() == i)
                i = 0;
            if (key.length() == str.length())
                break;
            key+=(key.charAt(i));
        }
        return key;
    }


    @FXML
    void Enc(ActionEvent event) {
        String str = textAreaClean.getText().toUpperCase();
        String key = keyField.getText().toUpperCase();

        String cipher_text = "";
        boolean flag = true;
        boolean txtv=true;
        status.setText("");
        status1.setText("");
        status2.setText("");
           if (!fileCheckBox.isSelected()) {

            if( keyField.getText().isEmpty()){
                status.setText("enter required key !");
                status1.setText("");
                status2.setText("");
            }else if (textAreaClean.getText().isEmpty() ) {
                status.setText("enter required data !");
                status1.setText("");
                status2.setText("");
            } else {

                key = generateKey(str, key);
                char[] arrx = key.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isAlphabetic(arrx[d])) {
                        flag = false;
                        break;
                    }
                }
              //har ss[]={'1','2','3','4','5','6','7','8','9','0','~','`','!',',','@','#','$','%','^','&','*','(',')','_','-','+','=','|','[',']','{','}','.',',','?','/','>','<',' '};

              //for (int x=0;x<str.length();x++){
              //    System.out.println("1");
              //    for (int i=0;x<ss.length;i++){
              //    if (str.charAt(x)==ss[i]||str.charAt(x)==92){
              //        txtv=false;
              //        System.out.println("false");
              //        break;
              //    }
              //    }
              //}
                if (flag) {
                       key = generateKey(str, key);
                       for (int i = 0; i < str.length()&&txtv; i++) {
                            if (Character.isAlphabetic(str.charAt(i))&&!Character.isDigit(str.charAt(i))){
                           int z = (str.charAt(i) + key.charAt(i)) % 26;
                           z+= 'A';
                           cipher_text += (char) (z);
                            }else {
                                status.setText("data must have no non alphabetic character");
                                status1.setText("");
                                status2.setText("");
                                txtv=false;
                                break;
                            }
                       }
                       if (txtv){
                       status.setText("");
                       status1.setText("");
                       status2.setText("Finish Encrypted");
                       textAreaED.setText(cipher_text);}

                } else {
                    status.setText("enter characters only in key field !");
                    status1.setText("");
                    status2.setText("");
                }
            }
        } else {
///////////////////////////////////// file select ///////////////////////////////////////////////////////////////////////////////////
            if (keyField.getText().isEmpty()) {
                status.setText("enter required key");
                status1.setText("");
                status2.setText("");
            }else{

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

                status.setText("");
                status1.setText("");
                status2.setText("");
                File src_file = new File(src);
                File dest_dir = new File(dest);
                String s = keyField.getText();
                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (!Character.isAlphabetic(arrx[d])) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {

                    if (!src_file.exists() || src_file.isDirectory()) {
                        status.setText("");
                        status.setText("Pleas enter valid source file");
                    } else {
                        if (dest_dir.isFile() || !dest_dir.exists()) {
                            status.setText("");
                            status1.setText("");
                            status2.setText("");
                            status.setText("Pleas enter valid directory");

                        }else {
                    }
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

                        while (src_scan.hasNext()&&txtv) {
                            str=src_scan.nextLine();
                            str = str.toUpperCase();
                            key = key.toUpperCase();
                            key = generateKey(str, key);
                            cipher_text="";
                            for (int i = 0; i < str.length(); i++) {
                                if (Character.isAlphabetic(str.charAt(i))&&!Character.isDigit(str.charAt(i))){
                                    int z = (str.charAt(i) + key.charAt(i)) % 26;
                                    z+= 'A';
                                    cipher_text += (char) (z);}
                                else {
                                    status.setText("data must have no non alphabetic character");
                                    status1.setText("");
                                    status2.setText("");
                                    txtv=false;
                                    break;
                                }

                            }
                            pw.println(cipher_text);
                        }if (txtv){
                        status.setText("");
                        status1.setText("");
                        status2.setText("Finish Encrypted");
                        }else {
                            pw.close();
                            dest_file.delete();
                        }

                        pw.close();
                        src_scan.close();

                    } catch (Exception e) {
                    }}
                } else {
                    status.setText("enter words only in key field !");
                    status1.setText("");
                    status2.setText("");
                }
            }
        }

    }



    @FXML
     void Dec(ActionEvent event) {
         String cipher_text=textAreaClean.getText().toUpperCase();
         String key=keyField.getText().toUpperCase();
         String orig_text="";
         boolean flag=true;
         boolean txtv = true;
         if (!fileCheckBox.isSelected()) {
                 if(keyField.getText().isEmpty()){
                     status.setText("enter required key");
                     status1.setText("");
                     status2.setText("");
                 }
                 else if (textAreaClean.getText().isEmpty() ) {
                     status.setText("enter required data !");
                     status1.setText("");
                     status2.setText("");
                 } else {

                     key = generateKey(cipher_text, key);
                     char[] arrx = key.toCharArray();
                     for (int d = 0; d < arrx.length; d++) {
                         if (!Character.isAlphabetic(arrx[d])) {
                             flag = false;
                             break;
                         }
                     }

                     if (flag) {

                            for (int i = 0; i < cipher_text.length() && i < key.length(); i++) {
                                if (Character.isAlphabetic(cipher_text.charAt(i))&&!Character.isDigit(cipher_text.charAt(i))) {

                                    int z = (cipher_text.charAt(i) - key.charAt(i) + 26) % 26;
                                    z += 'A';
                                    orig_text += (char) (z);

                                }else {
                                        status.setText("data must have no non alphabetic character");
                                        status1.setText("");
                                        status2.setText("");
                                        txtv=false;
                                        break;
                                    }
                            }
                            if (txtv) {
                                textAreaED.setText(orig_text);
                                status.setText("");
                                status1.setText("");
                                status2.setText("Finish Encrypted");
                            }

                     } else {
                         status.setText("enter characters only in key field !");
                         status1.setText("");
                         status2.setText("");
                     }
                 }

             }
             ///////////////////////////////////// file select ///////////////////////////////////////////////////////////////////////////////////
             else if (fileCheckBox.isSelected()){
                 status.setText("aaa");
                 status1.setText("");
                 status2.setText("");
                 if (keyField.getText().isEmpty()) {
                     status.setText("enter required key");
                     status1.setText("");
                     status2.setText("");
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

                     status.setText("");
                     status1.setText("");
                     status2.setText("");

                     File src_file = new File(src);
                     File dest_dir = new File(dest);

                     String s = keyField.getText();

                     char[] arrx = s.toCharArray();
                     for (int d = 0; d < arrx.length; d++) {
                         if (!Character.isAlphabetic(arrx[d])) {
                             flag = false;
                             break;
                         }
                     }

                     if (flag) {

                         if (!src_file.exists() || src_file.isDirectory()) {
                             status.setText("");
                             status.setText("Pleas enter valid source file");
                         } else {
                             if (dest_dir.isFile() || !dest_dir.exists()) {
                                 status.setText("");
                                 status1.setText("");
                                 status2.setText("");
                                 status.setText("Pleas enter valid directory");

                             } else {
                             }
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
                                 key = keyField.getText();
                                 while (src_scan.hasNext()&&txtv) {

                                     key = key.toUpperCase();

                                     orig_text = "";
                                     cipher_text = src_scan.nextLine();
                                     key = generateKey(cipher_text, key);

                                     for (int i = 0; i < cipher_text.length() && i < key.length(); i++) {

                                         if (Character.isAlphabetic(cipher_text.charAt(i))&&!Character.isDigit(cipher_text.charAt(i))){
                                             int z = (cipher_text.charAt(i) - key.charAt(i) + 26) % 26;
                                             z += 'A';
                                             orig_text += (char) (z);
                                         } else {
                                             status.setText("data must have no non alphabetic character");
                                             status1.setText("");
                                             status2.setText("");
                                             txtv=false;
                                             break;
                                         }
                                     }

                                     pw.println(orig_text);
                                 }
                                 if (txtv) {
                                     status.setText("");
                                     status1.setText("");
                                     status2.setText("Finish Decrypted");
                                 }else {
                                     pw.close();
                                     dest_file.delete();
                                 }
                                 pw.close();
                                 src_scan.close();

                             } catch (Exception e) {

                             }
                         }
                     } else {
                         status.setText("enter words only in key field !");
                         status1.setText("");
                         status2.setText("");
                     }
                 }
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

}
