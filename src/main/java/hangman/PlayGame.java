package hangman;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
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


public class PlayGame {

    private AnchorPane gamePane = new AnchorPane();

    @FXML
    private Button Abtn;

    @FXML
    private Line line;

    @FXML
    public void initialize() {
        animalInfo animalinfo = new animalInfo(); // Ensure the animalInfo object is initialized
        String randomWord = animalinfo.selectRandomWord();
        if (randomWord != null) {
            int wordLength = randomWord.length();
            int lineSpacing = 30;
            int startX = 50;
            int startY = 50;
            int lineLength = 20;

            for (int i = 0; i < wordLength; i++) {
                Line line = new Line(startX + i * (lineLength + lineSpacing), startY, startX + (i + 1) * lineLength + i * lineSpacing, startY);
                gamePane.getChildren().add(line);
            }
        }
    }

    @FXML
    void Abtn(ActionEvent event) {

    }

    @FXML
    void line(MouseEvent event) {

    }

}
