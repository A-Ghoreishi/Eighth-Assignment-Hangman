package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HangmanController {

    @FXML
    private ImageView hangman;

    @FXML
    private ImageView hangman2;

    @FXML
    private Button login;

    @FXML
    private Button signup;

    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField thename;

    @FXML
    private Button playgame;

    private Stage stage;

    public void initialize(){
        thename.setVisible(false);
        hangman.setVisible(true);
        hangman2.setVisible(false);
        usernameField.setVisible(false);
        passwordField.setVisible(false);
    }
    @FXML
    void hangman(MouseEvent event) {

    }

    @FXML
    void hangman2(MouseEvent event) {

    }

    @FXML
    void login(ActionEvent event) throws SQLException {
        thename.setVisible(false);
        login.setDisable(false);
        hangman.setVisible(false);
        hangman2.setVisible(true);
        usernameField.setVisible(true);
        passwordField.setVisible(true);

        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("Please enter username and password!");
            return;
        }

        DataBaseManager databaseManager = new DataBaseManager("C:\\Users\\Ailin Ghoreishi\\Desktop\\hangmandb.db");
        if (databaseManager.isUserAvailable(username, password,false)) {
            if(databaseManager.isAuthenticated(username,password)){
                try {
                    // Load the FXML file for the new page
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("playGame.fxml"));
                    Parent root = fxmlLoader.load();

                    // Create a new stage (window) and set the scene
                    Stage stage = new Stage();
                    stage.setTitle("Play Game!");
                    stage.setScene(new Scene(root));
                    stage.show();
                } catch (IOException e) {
//            e.printStackTrace();
                    System.out.println(1);
                }
            }
            else{
                System.out.println("Invalid username or password!");
            }
        } else {
            System.out.println("Invalid username or password!");
        }
    }

    @FXML
    void passwordField(ActionEvent event) {

    }

    @FXML
    void usernameField(ActionEvent event) {

    }

    @FXML
    void playgame(ActionEvent event) throws IOException {

    }

    @FXML
    void thename(ActionEvent event) {

    }

    @FXML
    void signup(ActionEvent event) {
        login.setDisable(false);
        thename.setVisible(true);
        hangman.setVisible(false);
        hangman2.setVisible(true);
        usernameField.setVisible(true);
        passwordField.setVisible(true);

        String name = thename.getText();
        String username = usernameField.getText();
        String passwordText = passwordField.getText();

        if (name.isEmpty() || username.isEmpty() || passwordText.isEmpty()) {
            System.out.println("Please enter name, username, and password!");
            return;
        }

        DataBaseManager databaseManager = new DataBaseManager("C:\\Users\\Ailin Ghoreishi\\Desktop\\hangmandb.db");
        if (databaseManager.createNewUser(username, name, passwordText)) {
            try {
                // Load the FXML file for the new page
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("playGame.fxml"));
                Parent root = fxmlLoader.load();

                // Create a new stage (window) and set the scene
                Stage stage = new Stage();
                stage.setTitle("Play Game!");
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException e) {
//            e.printStackTrace();
                System.out.println(1);
            }
        } else {
            System.out.println("Error adding user or username already exists!");
        }
    }

}
