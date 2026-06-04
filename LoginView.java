package uasGizi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class LoginView {
    
    public void show(Stage stage) {
        Label titleLabel = new Label("Piring Nadi");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.web("#4CAF50"));

        Label subtitleLabel = new Label("Pantau Asupan Gizi Harianmu");
        subtitleLabel.setFont(Font.font("Arial", 14));
        subtitleLabel.setTextFill(Color.web("#888888"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefHeight(40);
        usernameField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(40);
        passwordField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

        Button loginBtn = new Button("Login sebagai User");
        loginBtn.setPrefWidth(300);
        loginBtn.setPrefHeight(40);
        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

        Button adminBtn = new Button("Login sebagai Admin");
        adminBtn.setPrefWidth(300);
        adminBtn.setPrefHeight(40);
        adminBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

        Label msgLabel = new Label("");
        msgLabel.setTextFill(Color.RED);

        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                msgLabel.setText("Username dan password tidak boleh kosong!");
                return;
            }
            try {
                UserImplement ui = new UserImplement();
                User u = ui.getByUsername(username);
                if (u != null && u.getPassword().equals(password)) {
                    DashboardView dv = new DashboardView(u);
                    dv.show(stage);
                } else {
                    msgLabel.setText("Username atau password salah!");
                }
            } catch (Exception ex) {
                msgLabel.setText("Error: " + ex.getMessage());
            }
        });

        adminBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                msgLabel.setText("Username dan password tidak boleh kosong!");
                return;
            }
            try {
                AdminImplement ai = new AdminImplement();
                Admin a = ai.getByUsername(username);
                if (a != null && a.getPassword().equals(password)) {
                    AdminDashboardView adv = new AdminDashboardView(a);
                    adv.show(stage);

                } else {
                    msgLabel.setText("Username atau password admin salah!");
                }
            } catch (Exception ex) {
                msgLabel.setText("Error: " + ex.getMessage());
            }
        });

        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setMaxWidth(380);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 4);");
        card.getChildren().addAll(titleLabel, subtitleLabel,
                usernameField, passwordField, loginBtn, adminBtn, msgLabel);

        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color: #F0F4F0;");

        Scene scene = new Scene(root, 500, 550);
        stage.setTitle("NutriTrack - Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
}
