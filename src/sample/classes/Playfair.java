package sample.classes;

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
import javafx.stage.Stage;
import sample.Main;

import java.awt.*;
import java.io.IOException;
import java.util.Scanner;

public class Playfair{

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
    private TextArea TableStructure;


    private int length = 0;
    private String [][] table;

    @FXML
    void Enc(ActionEvent event) {
        String keyword = keyField.getText().toUpperCase();
        String input = textAreaClean.getText().toUpperCase();
        if(keyword.equals("")){
            status2.setText("");
            status.setText("Keyword can't empty!");
        }else if (input.equals("")){
            status2.setText("");
            status.setText("Plain text can't empty!");
        }else {
            table = this.cipherTable(keyword);
            String output = cipher(input);
            this.printTable(table);
            textAreaED.clear();
            textAreaED.appendText(output);
            status.setText("");
            status2.setText("Text encrypted successfully");
        }
    }

    @FXML
    void Dec(ActionEvent event) {
        String keyword = keyField.getText().toUpperCase();
        String input = textAreaED.getText().toUpperCase();
        if(keyword.equals("")){
            status2.setText("");
            status.setText("Keyword can't empty!");

        }else if (input.equals("")){
            status2.setText("");
            status.setText("Plain text can't empty!");
        }else {
            table = this.cipherTable(keyword);
            String decodedOutput = decode(input);
            this.printTable(table);
            textAreaClean.clear();
            textAreaClean.appendText(decodedOutput);
            status.setText("");
            status2.setText("Text decrypted successfully");
        }
    }

    private String parseString(Scanner s){
        String parse = s.nextLine();
        parse = parse.toUpperCase();
        parse = parse.replaceAll("[^A-Z]", "");
        parse = parse.replace("J", "I");
        return parse;
    }

    private String[][] cipherTable(String key){
        String[][] playfairTable = new String[5][5];
        String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                playfairTable[i][j] = "";

        for(int k = 0; k < keyString.length(); k++){
            boolean repeat = false;
            boolean used = false;
            for(int i = 0; i < 5; i++){
                for(int j = 0; j < 5; j++){
                    if(playfairTable[i][j].equals("" + keyString.charAt(k))){
                        repeat = true;
                    }else if(playfairTable[i][j].equals("") && !repeat && !used){
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
        }
        return playfairTable;
    }

    private String cipher(String in){
        length = (int) in.length() / 2 + in.length() % 2;

        for(int i = 0; i < (length - 1); i++){
            if(in.charAt(2 * i) == in.charAt(2 * i + 1)){
                in = new StringBuffer(in).insert(2 * i + 1, 'X').toString();
                length = (int) in.length() / 2 + in.length() % 2;
            }
        }

        String[] digraph = new String[length];
        for(int j = 0; j < length ; j++){
            if(j == (length - 1) && in.length() / 2 == (length - 1))
                in = in + "X";
            digraph[j] = in.charAt(2 * j) +""+ in.charAt(2 * j + 1);
        }

        String out = "";
        String[] encDigraphs = new String[length];
        encDigraphs = encodeDigraph(digraph);
        for(int k = 0; k < length; k++)
            out = out + encDigraphs[k];
        return out;
    }

    private String[] encodeDigraph(String di[]){
        String[] enc = new String[length];
        for(int i = 0; i < length; i++){
            char a = di[i].charAt(0);
            char b = di[i].charAt(1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();

            if(r1 == r2){
                c1 = (c1 + 1) % 5;
                c2 = (c2 + 1) % 5;

            }else if(c1 == c2){
                r1 = (r1 + 1) % 5;
                r2 = (r2 + 1) % 5;

            }else{
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }

            enc[i] = table[r1][c1] + "" + table[r2][c2];
        }
        return enc;
    }

    private String decode(String out){
        String decoded = "";
        for(int i = 0; i < out.length() / 2; i++){
            char a = out.charAt(2*i);
            char b = out.charAt(2*i+1);
            int r1 = (int) getPoint(a).getX();
            int r2 = (int) getPoint(b).getX();
            int c1 = (int) getPoint(a).getY();
            int c2 = (int) getPoint(b).getY();
            if(r1 == r2){
                c1 = (c1 + 4) % 5;
                c2 = (c2 + 4) % 5;
            }else if(c1 == c2){
                r1 = (r1 + 4) % 5;
                r2 = (r2 + 4) % 5;
            }else{
                int temp = c1;
                c1 = c2;
                c2 = temp;
            }
            decoded = decoded + table[r1][c1] + table[r2][c2];
        }
        return decoded;
    }

    private Point getPoint(char c){
        Point pt = new Point(0,0);
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
                if(c == table[i][j].charAt(0))
                    pt = new Point(i,j);
        return pt;
    }

    private void printTable(String[][] printedTable){
        TableStructure.clear();
        TableStructure.appendText("#------Matrix------#\n");
        for(int i = 0; i < 5; i++){
            for(int j = 0; j < 5; j++){
                TableStructure.appendText(printedTable[i][j]+" ");
            }
            TableStructure.appendText("\n");
        }
        TableStructure.appendText("\n");
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