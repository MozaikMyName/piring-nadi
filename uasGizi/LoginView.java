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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.DropShadow;
import javafx.scene.shape.Rectangle;

public class LoginView {
    
    public void show(Stage stage) {
        Label titleLabel = new Label("Piring Nadi");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.web("#C75B7A"));

        Label subtitleLabel = new Label("Pantau Asupan Gizi Harianmu");
        subtitleLabel.setFont(Font.font("Segoe UI", 14));
        subtitleLabel.setTextFill(Color.web("#888888"));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefHeight(40);
        usernameField.setPrefWidth(360);  
        usernameField.setStyle(
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-border-color: #E8A0BF;" +
            "-fx-border-width: 1.5;" +
            "-fx-background-color: rgba(255,255,255,0.8);" +
            "-fx-padding: 0 15 0 15;" +
            "-fx-font-size: 13;"
        );

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(40);
        passwordField.setPrefWidth(360);  
        passwordField.setStyle(
            "-fx-background-radius: 25;" +
            "-fx-border-radius: 25;" +
            "-fx-border-color: #E8A0BF;" +
            "-fx-border-width: 1.5;" +
            "-fx-background-color: rgba(255,255,255,0.8);" +
            "-fx-padding: 0 15 0 15;" +
            "-fx-font-size: 13;"
        );

        Label msgLabel = new Label("");
        msgLabel.setTextFill(Color.RED);
        msgLabel.setWrapText(true);
        
        Button loginBtn = new Button("Login");
        loginBtn.setPrefWidth(360);  
        loginBtn.setPrefHeight(40);
        loginBtn.setStyle("-fx-background-color: #D64D6E; -fx-text-fill: white; " +
            "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;");
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(
            "-fx-background-color: #D64D6E; -fx-text-fill: white; " +
            "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;"
        ));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle(
            "-fx-background-color: #F05F80; -fx-text-fill: white; " +
            "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;"
        ));
        
        loginBtn.setOnAction(e -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            if (username.isEmpty() || password.isEmpty()) {
                msgLabel.setText("Username dan password tidak boleh kosong!");
                return;
            }
            try {
                uasGizi.admin.AdminImplement ai = new uasGizi.admin.AdminImplement();
                uasGizi.admin.Admin a = ai.getByUsername(username);
                if (a != null && a.getPassword().equals(password)) {
                    new AdminDashboardView(a).show(stage);
                    return;
                }
                uasGizi.ahliGizi.AhliGiziImplement agi = new uasGizi.ahliGizi.AhliGiziImplement();
                uasGizi.ahliGizi.AhliGizi ag = agi.getByUsername(username);
                if (ag != null && ag.getPassword().equals(password)) {
                    new NutritionistView(ag).show(stage);
                    return;
                }
                uasGizi.user.UserImplement ui = new uasGizi.user.UserImplement();
                uasGizi.user.User u = ui.getByUsername(username);
                if (u != null && u.getPassword().equals(password)) {
                    if (u.getTujuan() == null || u.getTujuan().isEmpty()) {
                        new GoalSelectionView(u, stage).show();
                    } else {
                        new DashboardView(u).show(stage);
                    }
                    return;
                }
                msgLabel.setText("Username atau password salah!");
            } catch (Exception ex) {
                msgLabel.setText("Error: " + ex.getMessage());
            }
        });
        
        Button registerBtn = new Button("Belum punya akun? Daftar");
        registerBtn.setPrefWidth(360); 
        registerBtn.setPrefHeight(38);
        registerBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #F19CBB; " +
                "-fx-border-color: #F19CBB; -fx-border-radius: 8; " +
                "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;");
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle(
            "-fx-background-color: #F05F80; -fx-text-fill: white; " +
            "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;"
        ));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: #F19CBB; " +
            "-fx-border-color: #F19CBB; -fx-border-radius: 8; " +
            "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;"
        ));
        registerBtn.setOnAction(e -> {
            RegisterView rv = new RegisterView();
            rv.show(stage);
        });
        
        VBox formBox = new VBox(12);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setPadding(new Insets(40, 35, 40, 35));
        formBox.setPrefWidth(380);  
        formBox.setMinHeight(400);
        formBox.setMaxHeight(400);
        formBox.setStyle(
            "-fx-background-color: rgba(255,255,255,0.95);" +
            "-fx-background-radius: 0 20 20 0;"
        );
        formBox.getChildren().addAll(
            titleLabel, subtitleLabel,
            usernameField, passwordField, 
            loginBtn, registerBtn, msgLabel
        );
        
        Image makananImg = new Image("file:D:/piring-nadi/gmb/makanan-1.jpeg");
        ImageView makananView = new ImageView(makananImg);
        makananView.setPreserveRatio(false);  
        makananView.setFitWidth(450);
        makananView.setFitHeight(500);
        
        Rectangle mask = new Rectangle(450, 500);
        mask.setArcWidth(40);
        mask.setArcHeight(40);

        makananView.setClip(mask);

        StackPane makananPane = new StackPane(makananView);
        makananPane.setPrefWidth(450);
        makananPane.setPrefHeight(550);
        makananPane.setStyle("-fx-background-radius: 20;");
        
        StackPane cardForm = new StackPane(formBox);
        cardForm.setPrefWidth(420);  
        cardForm.setPrefHeight(480);
        cardForm.setStyle("-fx-background-radius: 0 20 20 0;");
        
        StackPane card = new StackPane();
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(900);  
        card.setMaxHeight(550);

        HBox layout = new HBox(0);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(makananPane, cardForm);

        card.getChildren().add(layout);
        card.setStyle("-fx-background-radius: 20;");
        
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        shadow.setRadius(25);
        shadow.setOffsetY(5);
        card.setEffect(shadow);
        
        Image bgImage = new Image("file:D:/piring-nadi/gmb/bg-pink.jpg");
        ImageView bgView = new ImageView(bgImage);
        bgView.setPreserveRatio(false);
        bgView.fitWidthProperty().bind(stage.widthProperty());
        bgView.fitHeightProperty().bind(stage.heightProperty());

        StackPane root = new StackPane(bgView,card);
        StackPane.setAlignment(card, Pos.CENTER);

        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Piring Nadi - Login");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(850);
        stage.setMinHeight(650);
        stage.show();
    }
    
}