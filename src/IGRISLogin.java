import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class IGRISLogin extends Application {

    @Override
    public void start(Stage primaryStage) {
        VBox mainContainer = new VBox(40);
        mainContainer.setAlignment(Pos.CENTER);

        // Set background image
        mainContainer.setStyle(
                "-fx-background-image: url('file:/D:/JAVA/IGRIS/IMAGES/IGRIS.png'); " +
                        "-fx-background-size: cover;"
        );

        // Tagline text
        Text tagline = new Text("The Language Learning Website");
        tagline.setFont(Font.font("Cambria", FontWeight.NORMAL, 28));
        tagline.setFill(Color.LIGHTGRAY);

        // Create animated buttons
        Button getStarted = createAnimatedButton("🚀 GET STARTED");
        Button login = createAnimatedButton("🔐 I ALREADY HAVE AN ACCOUNT");

        // Button actions
        getStarted.setOnAction(e -> {
            try {
                new CreateAccountPage().start(new Stage());
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        login.setOnAction(e -> {
            try {
                new LoginPage().start(new Stage());
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        mainContainer.getChildren().addAll(tagline, getStarted, login);

        Scene scene = new Scene(mainContainer, 1000, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("IGRIS Language Learning");
        primaryStage.show();
    }

    // Reusable button styling with animation
    private Button createAnimatedButton(String text) {
        Button button = new Button(text);
        button.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        button.setStyle(
                "-fx-background-color: rgba(0,0,0,0.8); " +
                        "-fx-text-fill: white; " +
                        "-fx-background-radius: 10; " +
                        "-fx-padding: 15px 30px; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 5);"
        );
        button.setMinWidth(320);

        // Hover animations
        button.setOnMouseEntered(e -> {
            button.setStyle(
                    "-fx-background-color: white; " +
                            "-fx-text-fill: black; " +
                            "-fx-background-radius: 10; " +
                            "-fx-padding: 15px 30px; " +
                            "-fx-cursor: hand; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.6), 8, 0, 0, 6);"
            );
            animateButton(button);
        });

        button.setOnMouseExited(e -> {
            button.setStyle(
                    "-fx-background-color: rgba(0,0,0,0.8); " +
                            "-fx-text-fill: white; " +
                            "-fx-background-radius: 10; " +
                            "-fx-padding: 15px 30px; " +
                            "-fx-cursor: hand; " +
                            "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.4), 5, 0, 0, 5);"
            );
        });

        return button;
    }

    // Hover animation scale effect
    private void animateButton(Button button) {
        ScaleTransition st = new ScaleTransition(Duration.millis(150), button);
        st.setToX(1.05);
        st.setToY(1.05);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
