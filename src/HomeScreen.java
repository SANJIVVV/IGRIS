import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HomeScreen extends Application {
    private String username;

    public HomeScreen() {
        this.username = "Guest"; // Default fallback
    }

    public HomeScreen(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        // Root layout
        BorderPane root = new BorderPane();

        // Background image setup
        BackgroundImage bgImage = new BackgroundImage(
                new Image("file:/D:/JAVA/IGRIS/IMAGES/IGRIS1.png", 1920, 1080, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER, new BackgroundSize(100, 100, true, true, false, true)
        );
        root.setBackground(new Background(bgImage));

        // Title
        Text title = new Text("WELCOME TO IGRIS LANGUAGE LEARNING");
        title.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD, 30));
        title.setStyle("-fx-fill: white;");
        BorderPane.setAlignment(title, Pos.TOP_CENTER);
        BorderPane.setMargin(title, new Insets(40, 0, 10, 0));
        root.setTop(title);

        // Center content VBox
        VBox content = new VBox(25);
        content.setAlignment(Pos.CENTER);

        // Create buttons with animation
        Button startButton = createAnimatedButton("🚀 START LEARNING");
        Button progressButton = createAnimatedButton("📊 VIEW PROGRESS");
        Button settingsButton = createAnimatedButton("⚙️ SETTINGS");
        Button logoutButton = createAnimatedButton("🔓 LOGOUT");

        // Button actions
        startButton.setOnAction(e -> {
            new LessonSelection(username).start(new Stage());
            primaryStage.close();
        });

        progressButton.setOnAction(e -> {
            new ProgressScreen(username).start(new Stage());
            primaryStage.close();
        });

        settingsButton.setOnAction(e -> {
            new SettingsScreen(username).start(new Stage());
            primaryStage.close();
        });

        logoutButton.setOnAction(e -> {
            new LoginPage().start(new Stage());
            primaryStage.close();
        });

        content.getChildren().addAll(startButton, progressButton, settingsButton, logoutButton);
        root.setCenter(content);

        // Final scene
        Scene scene = new Scene(root, 900, 600);
        primaryStage.setTitle("HOME SCREEN");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Styled button with animation
    private Button createAnimatedButton(String text) {
        Button button = new Button(text);
        button.setMinWidth(280);
        button.setMinHeight(50);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        button.setStyle(
                "-fx-background-color: rgba(0, 0, 0, 0.75);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 12;" +
                        "-fx-cursor: hand;" +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 5);"
        );

        // Hover animation
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: white;" +
                            "-fx-text-fill: black;" +
                            "-fx-background-radius: 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.5), 8, 0, 0, 6);"
            );
            animateButton(button);
        });

        // On exit
        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: rgba(0, 0, 0, 0.75);" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 12;" +
                            "-fx-cursor: hand;" +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 5);"
            );
        });

        return button;
    }

    // Button scale transition
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
