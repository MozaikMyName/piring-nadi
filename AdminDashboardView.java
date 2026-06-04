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

public class AdminDashboardView {
    private Admin admin;

    public AdminDashboardView(Admin admin) {
        this.admin = admin;
    }

    public void show(Stage stage) {
        Label titleLabel = new Label("Admin Dashboard");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#2E7D32"));

        Label subtitleLabel = new Label("Halo, " + admin.getUsername() + "!");
        subtitleLabel.setFont(Font.font("Arial", 14));
        subtitleLabel.setTextFill(Color.web("#555555"));

        Button foodListBtn = new Button("Kelola Daftar Makanan");
        foodListBtn.setPrefWidth(280);
        foodListBtn.setPrefHeight(50);
        foodListBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setPrefWidth(280);
        logoutBtn.setPrefHeight(50);
        logoutBtn.setStyle("-fx-background-color: #f44336; -fx-text-fill: white; " +
                "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;");

        foodListBtn.setOnAction(e -> {
            FoodListView flv = new FoodListView(admin, stage);
            flv.show();
        });

        logoutBtn.setOnAction(e -> {
            LoginView lv = new LoginView();
            lv.show(stage);
        });

        // Di FoodListView, backBtn balik ke sini
        VBox card = new VBox(20);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(50));
        card.setMaxWidth(380);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 4);");
        card.getChildren().addAll(titleLabel, subtitleLabel, foodListBtn, logoutBtn);

        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color: #F0F4F0;");

        Scene scene = new Scene(root, 550, 400);
        stage.setTitle("NutriTrack - Admin");
        stage.setScene(scene);
        stage.show();
    }
}
