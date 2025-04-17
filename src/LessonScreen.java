import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label; // Added import for Label
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LessonScreen extends Application {
    private String language;
    private String username;

    public LessonScreen(String language, String username) {
        this.language = language;
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        // 🌌 Background Image
        root.setStyle(
                "-fx-background-image: url('file:/D:/JAVA/IGRIS/IMAGES/IGRIS1.png'); " +
                        "-fx-background-size: cover;"
        );

        VBox rootLayout = new VBox(40);
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-background-color: rgba(0, 0, 0, 0.75); -fx-padding: 30px; -fx-background-radius: 20;");

        Label title = new Label(language.toUpperCase() + " LESSONS"); // Now it should work
        title.setFont(Font.font("Verdana", 28));
        title.setTextFill(Color.LIGHTBLUE);

        HBox levelRow = new HBox(30);
        levelRow.setAlignment(Pos.CENTER);

        // Loop through all 5 levels and create buttons for each
        for (int i = 1; i <= 5; i++) {
            int levelNum = i;

            Button levelBtn = createAnimatedCircleButton("Level " + levelNum, levelNum);

            levelBtn.setOnAction(e -> {
                new QuestionScreen(language, username, levelNum).start(new Stage());
                primaryStage.close();
            });

            levelRow.getChildren().add(levelBtn);
        }

        // Create the back button to return to the LessonSelection screen
        Button backBtn = createGlowingButton("🔙 BACK TO LESSON SELECTION");
        backBtn.setOnAction(e -> {
            new LessonSelection(username).start(new Stage()); // Go back to LessonSelection screen
            primaryStage.close(); // Close the current LessonScreen
        });

        // Add components to the root
        rootLayout.getChildren().addAll(title, levelRow, backBtn);
        root.getChildren().add(rootLayout);

        Scene scene = new Scene(root, 600, 400);
        primaryStage.setTitle("Lessons - " + language);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Button createAnimatedCircleButton(String text, int delayIndex) {
        Button button = new Button(text);
        button.setShape(new Circle(50));
        button.setMinSize(100, 100);
        button.setMaxSize(100, 100);
        button.setStyle(
                "-fx-background-color: #1E90FF; -fx-text-fill: white;" +
                        "-fx-font-size: 14px; -fx-background-radius: 50%;"
        );
        button.setEffect(new DropShadow(10, Color.DARKBLUE));

        // Pulse Animation
        ScaleTransition scale = new ScaleTransition(Duration.seconds(1.5), button);
        scale.setFromX(1);
        scale.setFromY(1);
        scale.setToX(1.1);
        scale.setToY(1.1);
        scale.setAutoReverse(true);
        scale.setCycleCount(ScaleTransition.INDEFINITE);
        scale.play();

        // Slide-in Animation
        TranslateTransition slide = new TranslateTransition(Duration.seconds(0.8), button);
        slide.setFromX(-200);
        slide.setToX(0);
        slide.setDelay(Duration.seconds(0.15 * delayIndex));
        slide.play();

        return button;
    }

    private Button createGlowingButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: linear-gradient(to right, #00f2fe, #4facfe); -fx-text-fill: black; -fx-background-radius: 20; -fx-padding: 12 28;");
        button.setMinWidth(260);
        button.setOnMouseEntered(e -> animateButton(button));
        return button;
    }

    private void animateButton(Button button) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
        st.setToX(1.08);
        st.setToY(1.08);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
