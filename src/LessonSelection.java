import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LessonSelection extends Application {
    private String username;

    public LessonSelection(String username) {
        this.username = username;
    }

    public LessonSelection() {
        this.username = "Guest"; // Default for testing
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));

        // Background image
        root.setStyle("-fx-background-image: url('file:D:/JAVA/IGRIS/IMAGES/IG5.jpg'); " +
                "-fx-background-size: cover; -fx-background-position: center center;");

        // Buttons
        Button spanishButton = createAnimatedButton("🇪🇸 SPANISH");
        Button tamilButton = createAnimatedButton("🇮🇳 TAMIL");
        Button germanButton = createAnimatedButton("🇩🇪 GERMAN");
        Button frenchButton = createAnimatedButton("🇫🇷 FRENCH");
        Button backButton = createAnimatedButton("🔙 BACK TO HOME");

        // Button Actions
        spanishButton.setOnAction(e -> startLesson("Spanish", primaryStage));
        tamilButton.setOnAction(e -> startLesson("Tamil", primaryStage));
        germanButton.setOnAction(e -> startLesson("German", primaryStage));
        frenchButton.setOnAction(e -> startLesson("French", primaryStage));
        backButton.setOnAction(e -> goBack(primaryStage));

        root.getChildren().addAll(spanishButton, tamilButton, germanButton, frenchButton, backButton);

        Scene scene = new Scene(root, 900, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("LESSON SELECTION");
        primaryStage.show();
    }

    private void startLesson(String language, Stage primaryStage) {
        LessonScreen lessonScreen = new LessonScreen(language, username);
        lessonScreen.start(new Stage());
        primaryStage.close();
    }

    private void goBack(Stage primaryStage) {
        new HomeScreen(username).start(new Stage());
        primaryStage.close();
    }

    private Button createAnimatedButton(String text) {
        Button button = new Button(text);
        button.setMinWidth(280);
        button.setMinHeight(50);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        button.setStyle(
                "-fx-background-color: rgba(255, 255, 255, 0.8);" +
                        "-fx-text-fill: #F9001E;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 5);"
        );

        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: #F9001E;" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 8, 0, 0, 6);"
            );
            animateButton(button);
        });

        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: rgba(255, 255, 255, 0.8);" +
                            "-fx-text-fill: #F9001E;" +
                            "-fx-background-radius: 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.3), 5, 0, 0, 5);"
            );
        });

        return button;
    }

    private void animateButton(Button button) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
        st.setToX(1.05);
        st.setToY(1.05);
        st.setAutoReverse(true);
        st.setCycleCount(2);
        st.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
