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


public class railFence {

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


    @FXML
    void encode(ActionEvent event) {
        boolean flag = true;
        status1.setText("");
        status.setText("");
        status2.setText("");
        if (!fileCheckBox.isSelected()) {

            if (textAreaClean.getText().isEmpty()) {
                status.setText("enter required data !");
                status1.setText("");
                status2.setText("");
            } else if (keyField.getText().isEmpty()) {
                status.setText("enter key !");
                status1.setText("");
                status2.setText("");
            }else {
                String s = keyField.getText();
                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (Character.isAlphabetic(arrx[d])) {
                        flag = false;
                        break;
                    }
                }
            }
            if (flag&&!textAreaClean.getText().isEmpty()) {
                String message = textAreaClean.getText();
                int key = Integer.parseInt(keyField.getText());
                String encodedMessage = "";
                for (int row = 0; row < key; row++) {
                    int iter = 0;
                    for (int i = row; i < message.length(); i += getTerm(iter++, row, key)) {
                        encodedMessage += message.charAt(i);
                    }
                }
                status1.setText(" ");
                status.setText("");
                status2.setText("Finish Encrypting");
                textAreaED.setText(encodedMessage);
            } else {

                status.setText("enter numbers only in key field !");
                status1.setText("");
                status2.setText("");
            }
        } else {
////////////////////////////////////////////file handel///////////////////////////////////////////////////////////////////////////////////////////
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
                System.out.println(dest);
                flag = true;
                File src_file = new File(src);
                File dest_dir = new File(dest);
                status.setText("");
                status1.setText("");
                status2.setText("");


                String s = keyField.getText();

                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (Character.isAlphabetic(arrx[d])) {
                        flag = false;
                        break;
                    }
                }

                if (flag) {
                    int k = Integer.parseInt(keyField.getText());
                    if (k < 2) {
                        status.setText("enter a valid key ");
                    } else {
                        if (!src_file.exists() || src_file.isDirectory() || textAreaClean.getText().isEmpty()) {
                            status.setText("");
                            status.setText("Pleas enter valid source file");
                        } else {
                            if (dest_dir.isFile() || !dest_dir.exists() || textAreaED.getText().isEmpty()) {

                                status1.setText("");
                                status2.setText("");
                                status.setText("Pleas enter valid directory");
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


                                    while (src_scan.hasNext()) {
                                        String message = src_scan.nextLine();
                                        int key = Integer.parseInt(keyField.getText());

                                        String encodedMessage = "";
                                        for (int row = 0; row < key; row++) {
                                            int iter = 0;
                                            for (int i = row; i < message.length(); i += getTerm(iter++, row, key)) {
                                                encodedMessage += message.charAt(i);
                                            }
                                        }
                                        status1.setText(" ");
                                        status.setText("");
                                        status2.setText("Finish Encrypting");
                                        pw.println(encodedMessage);

                                    }
                                    pw.close();
                                    src_scan.close();
                                } catch (Exception e) {
                                }
                            }
                        }
                    }
                } else {

                    status.setText("enter valid value in key field !");
                    status1.setText("");
                    status2.setText("");
                }
            }
        }
    }


    @FXML
    void decode(ActionEvent event) {
        String message = textAreaClean.getText();

        boolean flag = true;
        if (!fileCheckBox.isSelected()) {

            //if (!textAreaClean.getText().isEmpty()) {

                if (keyField.getText().isEmpty()) {
                    status.setText("enter required key !");
                    status1.setText("");
                    status2.setText("");
                } else if (textAreaClean.getText().isEmpty()) {
                    status.setText("enter required data");
                    status1.setText("");
                    status2.setText("");
                }else {
                String s = keyField.getText();
                char[] arrx = s.toCharArray();
                for (int d = 0; d < arrx.length; d++) {
                    if (Character.isAlphabetic(arrx[d])) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    int key = Integer.parseInt(keyField.getText());
                    StringBuilder decodedMessage = new StringBuilder(message);
                    int currPosition = 0; // Position in source string
                    for (int row = 0; row < key; row++) { // Look rows
                        int iter = 0; // The number of the character in the row
                        for (int i = row; i < message.length(); i += getTerm(iter++, row, key)) {
                            decodedMessage.setCharAt(i, message.charAt(currPosition++));
                        }


                    }
                    status1.setText("");
                    status.setText("");
                    status2.setText("Finish Decrypting");
                    textAreaED.setText(decodedMessage.toString());
                } else {
                    status.setText("enter valid key");
                }}
//            }else {
//                status1.setText("");
//                status.setText("enter required data");
//                status2.setText("");
//            }
        }/////////////////////////////////////// file select //////////////////////////////////////////////////////////////////////////////////////////////
        else {
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
                    if (Character.isAlphabetic(arrx[d])) {
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

                        }
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
                        int key = Integer.parseInt(keyField.getText());
                        dest_file.createNewFile();
                        PrintWriter pw = new PrintWriter(dest_file);

                        if (key < 1) {
                            status.setText("key can not be negative value");
                        } else {
                            while (src_scan.hasNext()) {
                                message = src_scan.nextLine();
                                key = Integer.parseInt(keyField.getText());
                                StringBuilder decodedMessage = new StringBuilder(message);
                                int currPosition = 0; // Position in source string
                                for (int row = 0; row < key; row++) { // Look rows
                                    int iter = 0; // The number of the character in the row
                                    for (int i = row; i < message.length(); i += getTerm(iter++, row, key)) {
                                        decodedMessage.setCharAt(i, message.charAt(currPosition++));
                                    }
                                }
                                pw.println(decodedMessage);
                            }
                            status.setText("");
                            status1.setText("");
                            status2.setText("Finish Encrypted");

                            pw.close();
                            src_scan.close();
                        }
                    } catch (Exception e) {
                    }
                } else {
                    status.setText("enter numbers only in key field !");
                    status1.setText("");
                    status2.setText("");
                }
            }
        }
    }


    private int getTerm(int iteration, int row, int size) {
        if ((size == 0) || (size == 1)) {
            return 1;
        }
        if((row == 0) || (row == size-1)) { // Max. distance is achieved at the ends and equally (size-1)*2
            return (size-1)*2;
        }

        if (iteration % 2 == 0) { // In the description of the method above this identity is demonstrated
            return (size-1-row)*2;
        }
        return 2*row;
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
