package sample.socket;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import sample.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;


public class server extends Window {


    private Stage stage;
    private Scene scene;
    private Parent parent;



    @FXML
    private Label connectionStatus;

    @FXML
    private TextField ipField;

    @FXML
    private TextField textField;

    @FXML
    private ListView<Data> listView;;

    @FXML
    private TextArea textArea;


    @FXML
    void getServerIp(ActionEvent event) throws UnknownHostException {
        String ip;
        ip=InetAddress.getLocalHost().getHostAddress();
        ipField.setText(String.valueOf(ip));
        String myString = ipField.getText();
        StringSelection stringSelection = new StringSelection(myString);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        JOptionPane.showMessageDialog(null,"ip has coped: "+ipField.getText());
    }


    boolean run=true;
    @FXML
    void start(ActionEvent event) {
        connectionStatus.setText("listening");
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ServerSocket ss = new ServerSocket(9999);
                    Socket s = ss.accept();
                    textArea.appendText("Server stating ...\n");
                    textArea.appendText("New client '" + "' has been connected...\n");
                    String ms = "Welcome, i'm receiver can you send now.\n";
                    String ms2 = " note: i can't reply to you\n";
                    PrintWriter pr = new PrintWriter(s.getOutputStream());
                    pr.println(ms+"\n"+ms2);
                    pr.flush();
                    while (run) {
                        InputStreamReader in = new InputStreamReader(s.getInputStream());
                        BufferedReader bf = new BufferedReader(in);
                        String str = bf.readLine();
                        textArea.appendText("- " + str + "\n");
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "Disconnected", JOptionPane.ERROR_MESSAGE);
                }
            }
        }).start();
    }

    @FXML
    void stop(ActionEvent event) throws InterruptedException {
        run=false;
        textArea.appendText("Server stopped ...\n");
        connectionStatus.setText("stopped");
        JOptionPane.showMessageDialog(null, "Server stopped");
    }

    @FXML
    void removeFromList(ActionEvent event){
        int selected=listView.getSelectionModel().getSelectedIndex();
        listView.getItems().remove(selected);
    }

    @FXML
    void save(ActionEvent event) {
//        Data data=(Data) mod.getElementAt(listView.getSelectionModel().getSelectedIndex());
//        FileChooser ch = new FileChooser();
////        File file = ch.showSaveDialog(this);
//        try {
//            FileOutputStream out = new FileOutputStream(ch.getInitialDirectory());
//            out.write(data.getFile());
//            out.close();
//        } catch (Exception e) {
//            JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
//        }

    }

    @FXML
    void send(ActionEvent event){

//        if (s.isClosed()) {
//            textArea.appendText("No client connected \n");
//        } else {
//            if (textField.getText().isEmpty()) {
//                JOptionPane.showMessageDialog(null,"Can't sent empty message");
//            } else {
//                try {
//                    ss = new ServerSocket(9999);
//                    s = ss.accept();
//                    textArea.appendText("");
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
//                }
//
//            }
//        }
    }


    @FXML
    private void open(ActionEvent event) {

    }

    @FXML
    private void newPoint(ActionEvent event) {
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


}
