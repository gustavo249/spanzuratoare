package sample;

import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Controller {
    public static Stage window = new Stage();
    public static void start() {
        PasswordField passwordField = new PasswordField();
        Label label = new Label("Insert the word: ");
        Label welcome = new Label("Le wild spanzuratoare\n appears :))");
        welcome.setFont(new Font(18));
        label.setAlignment(Pos.TOP_CENTER);

        Button enter = getButton("Enter", e -> {
            setButtonActionEvent(passwordField);
        });

        setUpScene(passwordField, label, welcome, enter);
        window.show();
    }

    private static void setButtonActionEvent(PasswordField passwordField) {
        String word = passwordField.getText();
        if (word.isEmpty() || word == null)
            System.out.println("Enter a word please !");
        else
            processWord(passwordField.getText());
    }

    private static Button getButton(String enter2, EventHandler<ActionEvent> actionEventEventHandler) {
        Button enter = new Button(enter2);
        enter.setOnAction(actionEventEventHandler);
        return enter;
    }

    private static void setUpScene(PasswordField passwordField, Label label, Label welcome, Button enter) {
        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setConstraints(welcome, 0, 0);
        GridPane.setConstraints(label, 0, 1);
        GridPane.setConstraints(passwordField, 1, 1);
        GridPane.setConstraints(enter, 1, 2);
        gridPane.getChildren().addAll(welcome, label, passwordField, enter);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);

        Scene scene = new Scene(borderPane, 350, 350);
        window.setScene(scene);
    }

    private static void processWord(String word) {
        Label wordHint = createWordHint(word);
        List<Character> letters = new ArrayList<>();

        Label label = new Label("Add a letter: ");
        TextField letterInput = createLetterInput();

        final int[] livesValue = {word.length()};
        Label lives = new Label("Lives: " + livesValue[0]);
        lives.setFont(new Font(20));

        Button addLetter = new Button("Try");
        addLetter.setOnAction(e -> {
            Character letter = letterInput.getText().charAt(0);

            if (word.contains("" + letter)) {
                letters.add(letter);
                wordHint.setText(updateWord(word, letters));
                String text = wordHint.getText();
                if (word.equals(text)) {
                    newGame("You win :)", word);
                }
            }
            else {
                livesValue[0] -= 1;
                if (livesValue[0] > 0) {
                    lives.setText("Lives:" + livesValue[0]);
                } else
                    newGame("You lost :(", word);
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);
        GridPane.setConstraints(wordHint, 0, 0);
        GridPane.setConstraints(label, 0, 1);
        GridPane.setConstraints(letterInput, 1, 1);
        GridPane.setConstraints(lives, 1, 5);
        GridPane.setConstraints(addLetter, 2, 1);
        gridPane.getChildren().addAll(wordHint, label, letterInput, lives, addLetter);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(gridPane);
        Scene scene = new Scene(borderPane, 350, 350);
        window.setScene(scene);
    }


    private static void newGame(String message, String word) {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);

        Label label = new Label(message);
        label.setFont(new Font(30));
        Label winner = new Label("The word was: " + word);
        winner.setFont(new Font(17));
        Button newGame = getButton("Start a new game", e -> start());
        vBox.getChildren().addAll(label, winner, newGame);
        Scene scene = new Scene(vBox, 350, 350);
        window.setScene(scene);
    }

    private static String updateWord(String word, List<Character> letters) {
        StringBuilder gaps = new StringBuilder();
        int ok;

        for (int i = 1; i < word.length() - 1; i++) {
            ok = 0;
            for (int j = 0; j < letters.size(); j++) {
                if (word.charAt(i) == letters.get(j) && ok == 0) {
                    gaps.append(letters.get(j));
                    ok = 1;
                }
            }
            if (ok == 0) {
                gaps.append(" _ ");
            }
        }
        String hint = new String("" + word.charAt(0) + gaps + word.charAt(word.length() - 1));
        return hint;
    }

    private static TextField createLetterInput() {
        TextField letterInput = new TextField();
        letterInput.setPrefWidth(30);
        letterInput.setTextFormatter(new TextFormatter<String>((TextFormatter.Change change) -> {
            String newText = change.getControlNewText();
            if (newText.length() > 1) {
                return null;
            } else {
                return change;
            }
        }));
        return letterInput;
    }

    private static Label createWordHint(String word) {
        String blankLines = new String();
        for (int i = 0; i < word.length() - 2; i++)
            blankLines = blankLines.concat(" _ ");
        Label wordHint = new Label("" + word.charAt(0) + blankLines + word.charAt(word.length() - 1));
        wordHint.setFont(new Font(30));
        return wordHint;
    }
}
