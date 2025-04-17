import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.sql.*;

public class ProgressScreen extends Application {
    private final String username;

    public ProgressScreen(String username) {
        this.username = username;
    }

    @Override
    public void start(Stage primaryStage) {
        VBox root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setPrefSize(600, 400);

        // 🎨 Stylish gradient background
        Stop[] stops = new Stop[] { new Stop(0, Color.web("#1D2B64")), new Stop(1, Color.web("#F8CDDA")) };
        LinearGradient gradient = new LinearGradient(0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stops);
        Background background = new Background(new BackgroundFill(gradient, CornerRadii.EMPTY, Insets.EMPTY));
        root.setBackground(background);

        // ✨ Glowing animated title
        Label title = new Label("📈 Your Progress");
        title.setFont(Font.font("Comic Sans MS", 30));
        title.setTextFill(Color.WHITE);
        DropShadow glow = new DropShadow();
        glow.setColor(Color.GOLD);
        glow.setRadius(20);
        title.setEffect(glow);

        // Animate the glow
        Timeline glowAnimation = new Timeline(
                new KeyFrame(Duration.seconds(0), new KeyValue(glow.radiusProperty(), 10)),
                new KeyFrame(Duration.seconds(1.5), new KeyValue(glow.radiusProperty(), 25))
        );
        glowAnimation.setAutoReverse(true);
        glowAnimation.setCycleCount(Animation.INDEFINITE);
        glowAnimation.play();

        // 📊 Progress label with fade and slide animation
        Label progressLabel = new Label();
        progressLabel.setFont(Font.font("Comic Sans MS", 18));
        progressLabel.setTextFill(Color.WHITE);
        progressLabel.setWrapText(true);
        progressLabel.setTranslateY(100);
        progressLabel.setOpacity(0);

        // 🎯 Load and animate progress
        String progressContent = loadUserProgress();
        progressLabel.setText(progressContent);

        TranslateTransition slide = new TranslateTransition(Duration.seconds(1.5), progressLabel);
        slide.setFromY(100);
        slide.setToY(0);

        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), progressLabel);
        fade.setFromValue(0);
        fade.setToValue(1);

        ParallelTransition showProgress = new ParallelTransition(slide, fade);
        showProgress.play();

        // 🔙 Fancy back button
        Button backButton = new Button("Back");
        backButton.setFont(Font.font("Comic Sans MS", 16));
        backButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 20;");
        backButton.setOnMouseEntered(e -> backButton.setStyle("-fx-background-color: #c0392b; -fx-text-fill: white; -fx-background-radius: 20;"));
        backButton.setOnMouseExited(e -> backButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-background-radius: 20;"));

        backButton.setOnAction(e -> {
            try {
                System.out.println("Back button clicked. Launching HomeScreen...");
                new HomeScreen(username).start(new Stage());
                primaryStage.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        root.getChildren().addAll(title, progressLabel, backButton);

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Progress");
        primaryStage.show();
    }

    private String loadUserProgress() {
        StringBuilder progress = new StringBuilder();
        String dbUrl = "jdbc:oracle:thin:@localhost:1521:xe";
        String dbUsername = "sanjiv";
        String dbPassword = "pooja";

        System.out.println("Fetching progress for username: " + username);
        try (Connection connection = DriverManager.getConnection(dbUrl, dbUsername, dbPassword)) {
            String query = "SELECT levelno, score FROM user_progress WHERE username = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            int totalScore = 0, count = 0;
            progress.append("📊 Progress for ").append(username).append(":\n\n");

            while (rs.next()) {
                int level = rs.getInt("levelno");
                int score = rs.getInt("score");
                totalScore += score;
                progress.append("✅ Level ").append(level).append(" ➡️ ").append(score).append(" pts\n");
                count++;
            }

            if (count == 0) return "⚠️ No progress data found.";
            progress.append("\n🏆 Total Score: ").append(totalScore).append(" points");

        } catch (SQLException e) {
            e.printStackTrace();
            return "⚠️ Error loading progress.";
        }

        return progress.toString();
    }

    public static void main(String[] args) {
        Application.launch(TestLauncher.class, args);
    }

    public static class TestLauncher extends Application {
        @Override
        public void start(Stage stage) {
            new ProgressScreen("testUser").start(stage); // Replace with real username
        }
    }
}
