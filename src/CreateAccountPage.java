import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;
import java.sql.*;
import java.time.LocalDate;

public class CreateAccountPage extends Application {

    @Override
    public void start(Stage primaryStage) {
        StackPane root = new StackPane();

        // 🌌 Background Image
        root.setStyle(
                "-fx-background-image: url('file:/D:/JAVA/IGRIS/IMAGES/IGRIS1.png'); " +
                        "-fx-background-size: cover;"
        );

        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.75); -fx-padding: 30px; -fx-background-radius: 20;");

        Text title = new Text("CREATE ACCOUNT");
        title.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-fill: white;");

        TextField nameField = createStyledField("Enter Name");
        TextField phoneField = createStyledField("Enter Phone Number");

        DatePicker dobField = new DatePicker();
        dobField.setPromptText("Select Date of Birth");
        dobField.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 16px; -fx-border-radius: 10; -fx-background-radius: 10;");
        dobField.setPrefWidth(260);
        addHoverEffect(dobField);

        TextField emailField = createStyledField("Enter Gmail");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Create Password");
        styleField(passwordField);

        Label messageLabel = new Label();
        messageLabel.setStyle("-fx-font-size: 14px; -fx-font-weight: bold; -fx-text-fill: red;");

        Button createAccountButton = createGlowingButton("✅ Create Account");
        createAccountButton.setOnAction(e -> handleCreateAccount(
                nameField.getText(),
                phoneField.getText(),
                dobField.getValue(),
                emailField.getText(),
                passwordField.getText(),
                messageLabel
        ));

        Button backButton = createGlowingButton("🔙 Back");
        backButton.setOnAction(e -> {
            new IGRISLogin().start(new Stage());
            primaryStage.close();
        });

        formBox.getChildren().addAll(
                title, nameField, phoneField, dobField,
                emailField, passwordField, createAccountButton,
                backButton, messageLabel
        );
        root.getChildren().add(formBox);

        Scene scene = new Scene(root, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Create Account Page");
        primaryStage.show();
    }

    private TextField createStyledField(String prompt) {
        TextField field = new TextField();
        field.setPromptText(prompt);
        field.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 16px; -fx-border-radius: 10; -fx-background-radius: 10;");
        field.setPrefWidth(260);
        addHoverEffect(field);
        return field;
    }

    private void styleField(TextField field) {
        field.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 16px; -fx-border-radius: 10; -fx-background-radius: 10;");
        field.setPrefWidth(260);
        addHoverEffect(field);
    }

    private void addHoverEffect(Control control) {
        control.setOnMouseEntered(e -> {
            control.setScaleX(1.03);
            control.setScaleY(1.03);
            control.setStyle("-fx-background-color: #444444; -fx-text-fill: white; -fx-font-size: 16px; -fx-border-radius: 10; -fx-background-radius: 10; -fx-effect: innershadow(gaussian, black, 10, 0, 0, 0);");
        });
        control.setOnMouseExited(e -> {
            control.setScaleX(1.0);
            control.setScaleY(1.0);
            control.setStyle("-fx-background-color: #333333; -fx-text-fill: white; -fx-font-size: 16px; -fx-border-radius: 10; -fx-background-radius: 10;");
        });
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

    private void handleCreateAccount(String name, String phone, LocalDate dob, String email, String password, Label messageLabel) {
        if (name.isEmpty() || phone.isEmpty() || dob == null || email.isEmpty() || password.isEmpty()) {
            messageLabel.setText("Please fill in all fields.");
            return;
        }

        try {
            String url = "jdbc:oracle:thin:@localhost:1521:xe";
            String username = "SANJIV";
            String dbPassword = "pooja";

            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection conn = DriverManager.getConnection(url, username, dbPassword);

            String sql = "INSERT INTO USERS (NAME, PHONE, DOB, EMAIL, PASSWORD) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.setString(2, phone);
            pstmt.setDate(3, java.sql.Date.valueOf(dob));
            pstmt.setString(4, email);
            pstmt.setString(5, password);

            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                messageLabel.setText("Account created and saved to database!");
                messageLabel.setTextFill(Color.LIGHTGREEN);
            } else {
                messageLabel.setText("Failed to save data.");
                messageLabel.setTextFill(Color.RED);
            }

            conn.close();

        } catch (ClassNotFoundException e) {
            messageLabel.setText("JDBC Driver not found.");
            messageLabel.setTextFill(Color.RED);
            e.printStackTrace();
        } catch (SQLException e) {
            messageLabel.setText("Database error: " + e.getMessage());
            messageLabel.setTextFill(Color.RED);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
