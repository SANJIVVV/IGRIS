import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;

public class SettingsScreen extends Application {

    private String username;

    public SettingsScreen(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-image: url('file:/D:/JAVA/IGRIS/IMAGES/IGRIS1.png');" +
                "-fx-background-size: cover;");

        VBox box = new VBox(20);
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: rgba(0, 0, 0, 0.75); -fx-background-radius: 20; -fx-padding: 40;");

        Text title = new Text("⚙️ Settings");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-fill: white;");

        TextField userField = new TextField(username);
        userField.setPromptText("Username");
        styleField(userField);
        userField.setDisable(true); // non-editable

        PasswordField oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Enter Old Password");
        styleField(oldPasswordField);

        PasswordField newPasswordField = new PasswordField();
        newPasswordField.setPromptText("Enter New Password");
        styleField(newPasswordField);

        Label message = new Label();
        message.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

        Button changeButton = createGlowingButton("🔑 Change Password");
        changeButton.setOnAction(e -> {
            String oldPass = oldPasswordField.getText();
            String newPass = newPasswordField.getText();
            if (oldPass.isEmpty() || newPass.isEmpty()) {
                message.setText("Please fill in both password fields.");
                return;
            }
            if (changePassword(username, oldPass, newPass)) {
                message.setText("✅ Password changed successfully!");
            } else {
                message.setText("❌ Incorrect old password.");
            }
        });

        Button backButton = createGlowingButton("🔙 Back");
        backButton.setOnAction(e -> {
            new HomeScreen(username).start(new Stage());
            primaryStage.close();
        });

        box.getChildren().addAll(title, userField, oldPasswordField, newPasswordField, changeButton, backButton, message);
        root.getChildren().add(box);

        Scene scene = new Scene(root, 700, 550);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Settings");
        primaryStage.show();
    }

    private void styleField(TextField field) {
        field.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 16px; -fx-border-radius: 10; -fx-background-radius: 10;");
        field.setPrefWidth(280);

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

        DropShadow glow = new DropShadow();
        glow.setColor(Color.BLACK);
        glow.setRadius(20);
        button.setEffect(glow);

        button.setOnMouseEntered(e -> animateButton(button, glow));
        return button;
    }

    private void animateButton(Button button, DropShadow glow) {
        ScaleTransition st = new ScaleTransition(Duration.millis(200), button);
        st.setToX(1.08);
        st.setToY(1.08);
        st.setCycleCount(2);
        st.setAutoReverse(true);
        st.play();
        glow.setRadius(30);
    }

    private boolean changePassword(String username, String oldPassword, String newPassword) {
        String url = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbUser = "SANJIV";
        String dbPass = "pooja";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(url, dbUser, dbPass);

            String checkSQL = "SELECT * FROM USERS WHERE NAME = ? AND PASSWORD = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkSQL);
            checkStmt.setString(1, username);
            checkStmt.setString(2, oldPassword);

            ResultSet rs = checkStmt.executeQuery();
            if (!rs.next()) {
                conn.close();
                return false;
            }

            String updateSQL = "UPDATE USERS SET PASSWORD = ? WHERE NAME = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateSQL);
            updateStmt.setString(1, newPassword);
            updateStmt.setString(2, username);
            updateStmt.executeUpdate();

            conn.close();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
