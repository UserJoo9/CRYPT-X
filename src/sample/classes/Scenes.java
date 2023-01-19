package sample.classes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import sample.Main;

import java.io.IOException;

public class Scenes {

    private Stage stage;
    private Scene scene;
    private Parent parent;



    @FXML
    private void mainCeaser(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/sample.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void vignere(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/vignere.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void railFence(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/railFence.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void playFair(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/playFair.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void oneTimePad(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/oneTimePad.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void mainSymmetric(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/symmetric.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void symmetricShift(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/symmetricShift.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    private void trySymmetricShift(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/trySymmetricShift.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void rsaScene(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/rsa.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void aboutInfo(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../fxml/info.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void sendScene(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../socket/client.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void receiveScene(javafx.event.ActionEvent event)throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../socket/server.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
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

}
