package hangman;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

import static hangman.UserSession.currentUsername;

public class PlayGame {

    private int wrongGuessCount = 0;

    String word;

    @FXML
    private Button Abtn;

    @FXML
    private Button Bbtn;

    @FXML
    private Button Cbtn;

    @FXML
    private Button Dbtn;

    @FXML
    private Button Ebtn;

    @FXML
    private Button Fbtn;

    @FXML
    private Button Gbtn;

    @FXML
    private Button Hbtn;

    @FXML
    private Button Ibtn;

    @FXML
    private Button Jbtn;

    @FXML
    private Button Kbtn;

    @FXML
    private Button Lbtn;

    @FXML
    private Button Mbtn;

    @FXML
    private Button Nbtn;

    @FXML
    private Button Obtn;

    @FXML
    private Button Pbtn;

    @FXML
    private Button Qbtn;

    @FXML
    private Button Rbtn;

    @FXML
    private Button Sbtn;

    @FXML
    private Button Tbtn;

    @FXML
    private Button Ubtn;

    @FXML
    private Button Vbtn;

    @FXML
    private Button Wbtn;

    @FXML
    private Button Xbtn;

    @FXML
    private Button Ybtn;

    @FXML
    private Button Zbtn;

    @FXML
    private VBox VBox;

    @FXML
    private ImageView eight;

    @FXML
    private ImageView eleven;

    @FXML
    private ImageView five;

    @FXML
    private ImageView four;

    @FXML
    private ImageView nine;

    @FXML
    private ImageView one;

    @FXML
    private ImageView seven;

    @FXML
    private ImageView six;

    @FXML
    private ImageView ten;

    @FXML
    private ImageView three;

    @FXML
    private ImageView twelve;

    @FXML
    private ImageView two;

    public void initialize() throws IOException {
        animalInfo.fetchAnimals("a");
        String input = animalInfo.selectRandomWord();
        word = input.replaceAll("[\\s\\p{Punct}]", "").toLowerCase();
        System.out.println(word);
        addLinesForWord(word);
        one.setVisible(false);
        two.setVisible(false);
        three.setVisible(false);
        four.setVisible(false);
        five.setVisible(false);
        six.setVisible(false);
        seven.setVisible(false);
        eight.setVisible(false);
        nine.setVisible(false);
        ten.setVisible(false);
        eleven.setVisible(false);
        twelve.setVisible(false);
    }

    private void addLinesForWord(String word) {
        HBox hbox = new HBox();
        for (int i = 0; i < word.length(); i++) {
            Label line = new Label("_ ");
            line.setMinWidth(20);
            hbox.getChildren().add(line);
        }
        VBox.getChildren().add(hbox);
    }

    @FXML
    void handleGuessButton(String letter) throws Exception {
        if (word.contains(letter.toLowerCase())) {
            updateLinesForWord(letter);
        } else {
            wrongGuessCount++;
            switch (wrongGuessCount){
                case 1:
                    one.setVisible(true);
                    break;

                case 2:
                    two.setVisible(true);
                    break;

                case 3:
                    three.setVisible(true);
                    break;

                case 4:
                    four.setVisible(true);
                    break;

                case 5:
                    five.setVisible(true);
                    break;

                case 6:
                    six.setVisible(true);
                    break;

                case 7:
                    seven.setVisible(true);
                    break;

                case 8:
                    eight.setVisible(true);
                    break;

                case 9:
                    nine.setVisible(true);
                    break;

                case 10:
                    ten.setVisible(true);
                    break;

                case 11:
                    eleven.setVisible(true);
                    break;

                case 12:
                    twelve.setVisible(true);
                    break;
            }
            showWrongGuesses(letter);
        }
    }


    private void updateLinesForWord(String letter) throws Exception {
        boolean isUpdated = false;
        for (Node node : VBox.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                int index = 0;
                for (Node childNode : hbox.getChildren()) {
                    if (childNode instanceof Label) {
                        Label label = (Label) childNode;
                        if (word.charAt(index) == letter.charAt(0)) {
                            label.setText(letter + " ");
                            isUpdated = true;
                        }
                        index++;
                    }
                }
            }
        }
        if (isUpdated) {
            checkForWin();
        }
    }


    private void showWrongGuesses(String letter) {
        Label wrongGuessLabel = new Label(letter + " ");
        HBox hbox = new HBox();
        hbox.getChildren().add(wrongGuessLabel);
        VBox.getChildren().add(hbox);
        if (wrongGuessCount >= 12) {
            endGame(false);
        }
    }


    private void checkForWin() {
        boolean isWin = true;
        for (Node node : VBox.getChildren()) {
            if (node instanceof HBox) {
                HBox hbox = (HBox) node;
                for (Node childNode : hbox.getChildren()) {
                    if (childNode instanceof Label) {
                        Label label = (Label) childNode;
                        if (label.getText().equals("_ ")) {
                            isWin = false;
                            break;
                        }
                    }
                }
            }
        }
        if (isWin) {
            endGame(true);
        }
    }

    private void endGame(boolean isWin) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        if (isWin) {
            alert.setTitle("Congratulations!");
            alert.setHeaderText("You won!");
            alert.setContentText("You guessed the word correctly!");

            DataBaseManager dbManager = new DataBaseManager("C:\\Users\\Ailin Ghoreishi\\Desktop\\hangmandb.db");
            dbManager.incrementUserScore(currentUsername);
        } else {
            alert.setTitle("Game Over");
            alert.setHeaderText("You lost!");
            alert.setContentText("The correct word was: " + word);
        }
        alert.showAndWait();
    }

    @FXML
    void Abtn(ActionEvent event) throws Exception {
        handleGuessButton("a");
    }

    @FXML
    void Bbtn(ActionEvent event) throws Exception {
        handleGuessButton("b");
    }

    @FXML
    void Cbtn(ActionEvent event) throws Exception {
        handleGuessButton("c");
    }

    @FXML
    void Dbtn(ActionEvent event) throws Exception {
        handleGuessButton("d");
    }

    @FXML
    void Ebtn(ActionEvent event) throws Exception {
        handleGuessButton("e");
    }

    @FXML
    void Fbtn(ActionEvent event) throws Exception {
        handleGuessButton("f");
    }

    @FXML
    void Gbtn(ActionEvent event) throws Exception {
        handleGuessButton("g");
    }

    @FXML
    void Hbtn(ActionEvent event) throws Exception {
        handleGuessButton("h");
    }

    @FXML
    void Ibtn(ActionEvent event) throws Exception {
        handleGuessButton("i");
    }

    @FXML
    void Jbtn(ActionEvent event) throws Exception {
        handleGuessButton("j");
    }

    @FXML
    void Kbtn(ActionEvent event) throws Exception {
        handleGuessButton("k");
    }

    @FXML
    void Lbtn(ActionEvent event) throws Exception {
        handleGuessButton("l");
    }

    @FXML
    void Mbtn(ActionEvent event) throws Exception {
        handleGuessButton("m");
    }

    @FXML
    void Nbtn(ActionEvent event) throws Exception {
        handleGuessButton("n");
    }

    @FXML
    void Obtn(ActionEvent event) throws Exception {
        handleGuessButton("o");
    }

    @FXML
    void Pbtn(ActionEvent event) throws Exception {
        handleGuessButton("p");
    }

    @FXML
    void Qbtn(ActionEvent event) throws Exception {
        handleGuessButton("q");
    }

    @FXML
    void Rbtn(ActionEvent event) throws Exception {
        handleGuessButton("r");
    }

    @FXML
    void Sbtn(ActionEvent event) throws Exception {
        handleGuessButton("s");
    }

    @FXML
    void Tbtn(ActionEvent event) throws Exception {
        handleGuessButton("t");
    }

    @FXML
    void Ubtn(ActionEvent event) throws Exception {
        handleGuessButton("u");
    }

    @FXML
    void Vbtn(ActionEvent event) throws Exception {
        handleGuessButton("v");
    }

    @FXML
    void Wbtn(ActionEvent event) throws Exception {
        handleGuessButton("w");
    }

    @FXML
    void Xbtn(ActionEvent event) throws Exception {
        handleGuessButton("x");
    }

    @FXML
    void Ybtn(ActionEvent event) throws Exception {
        handleGuessButton("y");
    }

    @FXML
    void Zbtn(ActionEvent event) throws Exception {
        handleGuessButton("z");
    }

    @FXML
    void VBox(MouseEvent event) {

    }

    @FXML
    void eight(MouseEvent event) {

    }

    @FXML
    void eleven(MouseEvent event) {

    }

    @FXML
    void five(MouseEvent event) {

    }

    @FXML
    void four(MouseEvent event) {

    }

    @FXML
    void nine(MouseEvent event) {

    }

    @FXML
    void one(MouseEvent event) {

    }

    @FXML
    void seven(MouseEvent event) {

    }

    @FXML
    void six(MouseEvent event) {

    }

    @FXML
    void ten(MouseEvent event) {

    }

    @FXML
    void three(MouseEvent event) {

    }

    @FXML
    void twelve(MouseEvent event) {

    }

    @FXML
    void two(MouseEvent event) {

    }
}
