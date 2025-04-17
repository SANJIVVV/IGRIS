import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;

public class QuestionScreen extends Application {
    private String language = "English";
    private String username = "User";
    private int level = 1;

    private int questionIndex = 0;
    private int points = 0;
    private Label pointLabel;
    private String[][] questions;
    private Connection connection;

    public QuestionScreen() {}

    public QuestionScreen(String language, String username, int level) {
        this.language = language;
        this.username = username;
        this.level = level;
        this.questions = QuestionBank.getQuestions(language, level);

        try {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String dbUsername = "SANJIV";
            String dbPassword = "pooja";

            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.connection = DriverManager.getConnection(url, dbUsername, dbPassword);
            if (this.connection != null) {
                System.out.println("Database connected successfully.");
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error while connecting to the database: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        if (questions == null || questions.length == 0) {
            Label noQ = new Label("No questions found for selected language/level.");
            Scene scene = new Scene(new StackPane(noQ), 600, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
            return;
        }

        BorderPane root = new BorderPane();
        VBox centerBox = new VBox(20);
        centerBox.setPadding(new Insets(40));
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #232526, #414345);");

        HBox topBox = new HBox();
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.TOP_RIGHT);

        pointLabel = new Label("❤️ " + points);
        pointLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: red; -fx-font-family: 'Verdana'; font-weight: bold;");
        topBox.getChildren().add(pointLabel);

        Label questionTitle = new Label("Level " + level + " - Question");
        questionTitle.setStyle("-fx-font-size: 28px; -fx-text-fill: #00FFFF; -fx-font-family: 'Verdana'; font-weight: bold;");

        Label question = new Label();
        question.setStyle("-fx-font-size: 24px; -fx-text-fill: white; -fx-font-family: 'Verdana'; font-weight: bold;");

        ToggleGroup options = new ToggleGroup();
        RadioButton opt1 = new RadioButton();
        RadioButton opt2 = new RadioButton();
        RadioButton opt3 = new RadioButton();

        for (RadioButton opt : new RadioButton[]{opt1, opt2, opt3}) {
            opt.setToggleGroup(options);
            opt.setStyle("-fx-font-size: 20px; -fx-text-fill: white; -fx-font-family: 'Verdana'; font-weight: bold;");
        }

        Button checkBtn = new Button("Check");
        Button skipBtn = new Button("Skip");
        styleButton(checkBtn);
        styleButton(skipBtn);

        Label feedback = new Label();
        feedback.setStyle("-fx-font-size: 18px; -fx-font-family: 'Verdana'; -fx-text-fill: orange; font-weight: bold;");

        loadQuestion(question, opt1, opt2, opt3);

        checkBtn.setOnAction(e -> {
            RadioButton selected = (RadioButton) options.getSelectedToggle();
            if (selected == null) return;

            int correct = Integer.parseInt(questions[questionIndex][4]);
            boolean isCorrect = (selected == opt1 && correct == 1) ||
                    (selected == opt2 && correct == 2) ||
                    (selected == opt3 && correct == 3);

            if (isCorrect) {
                feedback.setText("✅ Correct!");
                points += 10;
                animatePoints();
                updatePoints();
            } else {
                feedback.setText("❌ Incorrect. Correct: " + questions[questionIndex][correct]);
            }

            new Timeline(new KeyFrame(Duration.seconds(1.5), ev ->
                    nextQuestion(question, opt1, opt2, opt3, options, feedback, primaryStage))).play();
        });

        skipBtn.setOnAction(e -> {
            feedback.setText("⏭️ Skipped.");
            nextQuestion(question, opt1, opt2, opt3, options, feedback, primaryStage);
        });

        HBox btnBox = new HBox(20, checkBtn, skipBtn);
        btnBox.setAlignment(Pos.CENTER);

        centerBox.getChildren().addAll(questionTitle, question, opt1, opt2, opt3, btnBox, feedback);
        root.setTop(topBox);
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Language Quiz - " + language);
        primaryStage.show();
    }

    private void loadQuestion(Label question, RadioButton opt1, RadioButton opt2, RadioButton opt3) {
        String[] q = questions[questionIndex];
        question.setText(q[0]);
        opt1.setText(q[1]);
        opt2.setText(q[2]);
        opt3.setText(q[3]);
    }

    private void updatePoints() {
        pointLabel.setText("❤️ " + points);
    }

    private void animatePoints() {
        Timeline pulse = new Timeline(
                new KeyFrame(Duration.ZERO, evt -> pointLabel.setStyle("-fx-font-size: 32px; -fx-text-fill: red; -fx-font-family: 'Verdana'; font-weight: bold;")),
                new KeyFrame(Duration.seconds(0.3), evt -> pointLabel.setStyle("-fx-font-size: 28px; -fx-text-fill: red; -fx-font-family: 'Verdana'; font-weight: bold;"))
        );
        pulse.play();
    }

    private void nextQuestion(Label question, RadioButton opt1, RadioButton opt2, RadioButton opt3,
                              ToggleGroup options, Label feedback, Stage stage) {
        questionIndex++;
        if (questionIndex < questions.length) {
            loadQuestion(question, opt1, opt2, opt3);
            options.selectToggle(null);
            feedback.setText("");
        } else {
            saveProgress();
            showResult(stage);
        }
    }

    // ✅ MERGE-based Oracle-safe progress saving
    private void saveProgress() {
        if (connection == null) {
            System.out.println("Database connection is null. Cannot save progress.");
            return;
        }

        try {
            String mergeSQL = "MERGE INTO USER_PROGRESS tgt " +
                    "USING (SELECT ? AS username FROM dual) src " +
                    "ON (tgt.username = src.username) " +
                    "WHEN MATCHED THEN " +
                    "  UPDATE SET tgt.score = ?, tgt.levelno = ? " +
                    "WHEN NOT MATCHED THEN " +
                    "  INSERT (username, score, levelno) VALUES (?, ?, ?)";

            PreparedStatement pstmt = connection.prepareStatement(mergeSQL);
            pstmt.setString(1, username); // src.username
            pstmt.setInt(2, points);      // UPDATE score
            pstmt.setInt(3, level);       // UPDATE levelno
            pstmt.setString(4, username); // INSERT username
            pstmt.setInt(5, points);      // INSERT score
            pstmt.setInt(6, level);       // INSERT levelno

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Progress saved successfully.");
            } else {
                System.out.println("No changes made.");
            }

        } catch (SQLException e) {
            System.out.println("Error while saving progress: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void showResult(Stage stage) {
        VBox resultBox = new VBox(20);
        resultBox.setAlignment(Pos.CENTER);
        resultBox.setStyle("-fx-background-color: linear-gradient(to bottom right, #000000, #434343);");
        resultBox.setPadding(new Insets(40));

        Text msg = new Text();
        msg.setText(points >= 30
                ? "🎉 Great job, " + username + "! You scored " + points + "\nLevel Passed!"
                : "😢 Try again, " + username + ". You scored " + points);
        msg.setFont(Font.font("Verdana", 32));
        msg.setFill(Color.LIGHTSKYBLUE);

        FadeTransition ft = new FadeTransition(Duration.seconds(2), msg);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();

        Button nextRound = new Button("Next Level");
        Button tryAgain = new Button("Try Again");
        Button back = new Button("Back");

        styleButton(nextRound);
        styleButton(tryAgain);
        styleButton(back);

        nextRound.setOnAction(e -> {
            new QuestionScreen(language, username, level + 1).start(new Stage());
            stage.close();
        });

        tryAgain.setOnAction(e -> {
            new QuestionScreen(language, username, level).start(new Stage());
            stage.close();
        });

        back.setOnAction(e -> {
            new LessonScreen(language, username).start(new Stage());
            stage.close();
        });

        if (points >= 30) {
            resultBox.getChildren().addAll(msg, nextRound, back);
        } else {
            resultBox.getChildren().addAll(msg, tryAgain, back);
        }

        Scene resultScene = new Scene(resultBox, 700, 500);
        stage.setScene(resultScene);
    }

    private void styleButton(Button btn) {
        btn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 10;");
        btn.setOnMouseEntered(e -> {
            btn.setScaleX(1.1);
            btn.setScaleY(1.1);
            btn.setStyle("-fx-background-color: white; -fx-text-fill: #1E90FF; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 10;");
        });
        btn.setOnMouseExited(e -> {
            btn.setScaleX(1);
            btn.setScaleY(1);
            btn.setStyle("-fx-background-color: #1E90FF; -fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold; -fx-background-radius: 10;");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
