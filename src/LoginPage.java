import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import java.sql.*;

public class LoginPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        // 🌌 Background Image
        root.setStyle(
                "-fx-background-image: url('file:/D:/JAVA/IGRIS/IMAGES/IGRIS1.png'); " +
                        "-fx-background-size: cover;"
        );

        VBox loginBox = new VBox(20);
        loginBox.setAlignment(Pos.CENTER);
        loginBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-background-radius: 25; -fx-padding: 40;");

        Text title = new Text("🔐 LOGIN");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-fill: white;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        styleField(usernameField);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        styleField(passwordField);

        Label messageLabel = new Label();

        Button loginButton = createGlowingButton("✅ Login");
        loginButton.setOnAction(e -> handleLogin(usernameField.getText(), passwordField.getText(), messageLabel, primaryStage));

        Button backButton = createGlowingButton("🔙 Back");
        backButton.setOnAction(e -> {
            new IGRISLogin().start(new Stage());
            primaryStage.close();
        });

        loginBox.getChildren().addAll(title, usernameField, passwordField, loginButton, backButton, messageLabel);
        root.getChildren().add(loginBox);

        Scene scene = new Scene(root, 700, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login Page");
        primaryStage.show();
    }

    private void styleField(TextField field) {
        field.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 16px; -fx-border-radius: 10; -fx-background-radius: 10;");
        field.setPrefWidth(280);

        // Add hover effect with glow
        field.setOnMouseEntered(e -> {
            field.setScaleX(1.03);
            field.setScaleY(1.03);
            field.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-font-size: 16px; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);");
        });

        field.setOnMouseExited(e -> {
            field.setScaleX(1.0);
            field.setScaleY(1.0);
            field.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 16px; -fx-border-radius: 10; -fx-background-radius: 10;");
        });
    }

    private Button createGlowingButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: linear-gradient(to right, #00f2fe, #4facfe); -fx-text-fill: black; -fx-background-radius: 20; -fx-padding: 12 28;");
        button.setMinWidth(260);

        // Apply the black glow effect
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(javafx.scene.paint.Color.BLACK);
        dropShadow.setRadius(20);
        dropShadow.setOffsetX(0);
        dropShadow.setOffsetY(0);

        button.setEffect(dropShadow);

        button.setOnMouseEntered(e -> animateButton(button, dropShadow));
        return button;
    }

    private void animateButton(Button button, DropShadow dropShadow) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
        st.setToX(1.08);
        st.setToY(1.08);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();

        // Make the glow effect more intense when hovered
        dropShadow.setRadius(30);
    }

    private void handleLogin(String username, String password, Label messageLabel, Stage primaryStage) {
        if (username.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbUser = "SANJIV";
        String dbPass = "pooja";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);

            // Use the username instead of email in the SQL query
            String sql = "SELECT * FROM USERS WHERE NAME = ? AND PASSWORD = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                messageLabel.setText("Login successful!");
                new HomeScreen().start(new Stage());
                primaryStage.close();
            } else {
                messageLabel.setText("Incorrect username or password.");
            }

            conn.close();

        } catch (Exception e) {
            messageLabel.setText("Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
