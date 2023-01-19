package sample.socket;

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
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Main;

import javax.swing.*;
import java.io.*;
import java.net.Socket;

public class client {

    private Stage stage;
    private Scene scene;
    private Parent parent;

    @FXML
    private JFXCheckBox messageBox;

    @FXML
    private Label connectionStatus;

    @FXML
    private TextField ipField;

    @FXML
    private TextField myName;

    @FXML
    private TextField fileHint;

    @FXML
    private TextArea textArea;

    FileChooser ch = new FileChooser();
    private Socket socket;
    private ObjectOutputStream out;
    boolean run=true;

    @FXML
    void connect(ActionEvent event) throws IOException {
        if(ipField.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Type the receiver ip address");
        }if(myName.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Type your name");
        }else {

            try {
                socket = new Socket(ipField.getText().trim(), 9999);
                if(socket.isConnected()) {
                    connectionStatus.setText("connected");
                    textArea.appendText("Connect success ...\n");
                    out = new ObjectOutputStream(socket.getOutputStream());
                    InputStreamReader in = new InputStreamReader(socket.getInputStream());
                    BufferedReader bf = new BufferedReader(in);
                    Data data = new Data();
                    data.setStatus("new");
                    data.setName(myName.getText());
                    out.writeObject(data);
                    out.flush();
                    new Thread (new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String str = bf.readLine();
                                textArea.appendText("- " + str + "\n");
                            }catch (IOException e){
//                                connectionStatus.setText("an error");
                                JOptionPane.showMessageDialog(null, e, "Disconnected", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }).start();
                }else {
                    textArea.appendText("Receiver ip not found!");
                }
            } catch (IOException e) {
                connectionStatus.setText("an error");
                JOptionPane.showMessageDialog(null, e, "Disconnected", JOptionPane.ERROR_MESSAGE);

            }
        }


        try {

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e, "Disconnected", JOptionPane.ERROR_MESSAGE);
        }


    }

    @FXML
    void disconnect(ActionEvent event) {
        try {
            run=false;
            out.close();
            socket.close();
            connectionStatus.setText("disconnected");
            textArea.appendText("Disconnected! \n");
        }catch (Exception e){

        }
    }

    @FXML
    void submit(ActionEvent event) {
        if(!messageBox.isSelected()){
            if(socket.isClosed()||!socket.isConnected()){
                textArea.appendText("Receiver ip address is not found \n");
            }else if(fileHint.getText().isEmpty()){
                JOptionPane.showMessageDialog(null,"Can't sent empty message");
            }else {
                try {
                    Socket s = new Socket(ipField.getText().trim(), 9999);
                    String ms=fileHint.getText();
                    PrintWriter pr=new PrintWriter(s.getOutputStream());
                    pr.println(ms);
                    pr.flush();
                    textArea.appendText("You: "+ms+"\n");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
//        }else if(messageBox.isSelected()){
//            if(socket.isClosed()||!socket.isConnected()){
//                textArea.appendText("Receiver ip address is not found \n");
//            }else if(fileHint.getText().isEmpty()){
//                JOptionPane.showMessageDialog(null,"Can't sent empty message");
//            }else {
//                try {
//                    File file = ch.showOpenDialog(new Stage());
//                    FileInputStream in = new FileInputStream(file);
//                    byte b[] = new byte[in.available()];
//                    in.read(b);
//                    Data data = new Data();
//                    data.setName(fileHint.getText().trim());
//                    data.setFile(b);
//                    out.writeObject(data);
//                    out.flush();
//                    textArea.appendText("send 1 file ..\n");
//
//                } catch (Exception e) {
//                    JOptionPane.showMessageDialog(null, e, "Error", JOptionPane.ERROR_MESSAGE);
//                }
//            }
//        }else {
//
           }
    }


//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        ch.setInitialDirectory(new File("D:\\programming\\java\\Projects\\CRYPT-X DR.Usama\\src\\sample"));
//    }

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
}
