package uasGizi;

import uasGizi.admin.Admin;
import uasGizi.user.UserImplement;
import uasGizi.makanan.MakananImplement;
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
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#C75B7A"));

        Label subtitleLabel = new Label("Halo, " + admin.getUsername() + "!");
        subtitleLabel.setFont(Font.font("Segoe UI", 14));
        subtitleLabel.setTextFill(Color.web("#888888"));
        
        int totalUser = 0, totalMakanan = 0;
        try {
            UserImplement ui = new UserImplement();
            totalUser = ui.getAll().size();
            MakananImplement mi = new MakananImplement();
            totalMakanan = mi.getAll().size();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        HBox statsBox = new HBox(15);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.getChildren().addAll(
            buatStatCard("Total User", String.valueOf(totalUser), "#42A5F5"),
            buatStatCard("Total Makanan", String.valueOf(totalMakanan), "#66BB6A")
        );

        Button foodListBtn = new Button("Kelola Makanan & Minuman");
        foodListBtn.setPrefWidth(320);
        foodListBtn.setPrefHeight(48);
        foodListBtn.setStyle("-fx-background-color: #F33A6A; -fx-text-fill: white; " +
                "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;");

        Button userListBtn = new Button("Lihat Daftar User");
        userListBtn.setPrefWidth(320);
        userListBtn.setPrefHeight(48);
        userListBtn.setStyle("-fx-background-color: #42A5F5; -fx-text-fill: white; " +
                "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setPrefWidth(320);
        logoutBtn.setPrefHeight(48);
        logoutBtn.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white; " +
                "-fx-background-radius: 10; -fx-font-size: 14; -fx-cursor: hand;");

        foodListBtn.setOnAction(e -> {
            FoodListView flv = new FoodListView(admin, stage);
            flv.show();
        });
        
        userListBtn.setOnAction(e -> {
            try {
                AdminUserListView ulv = new AdminUserListView(admin, stage);
                ulv.show();
            } catch (Exception ex) {
                ex.printStackTrace();
                System.out.println("Error: " + ex.getMessage());
            }
        });

        logoutBtn.setOnAction(e -> {
            LoginView lv = new LoginView();
            lv.show(stage);
        });

        VBox card = new VBox(18);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setMaxWidth(450);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 4);");
        card.getChildren().addAll(titleLabel, subtitleLabel, statsBox,
                foodListBtn, userListBtn, logoutBtn);

        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color: #FFF0F5;");

        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Piring Nadi - Admin");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(850);
        stage.setMinHeight(650);
        stage.show();
    }

    private VBox buatStatCard(String judul, String nilai, String warna) {
        Label judulLbl = new Label(judul);
        judulLbl.setFont(Font.font("Arial", 12));
        judulLbl.setTextFill(Color.WHITE);

        Label nilaiLbl = new Label(nilai);
        nilaiLbl.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        nilaiLbl.setTextFill(Color.WHITE);

        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setPrefWidth(140);
        card.setStyle("-fx-background-color: " + warna + "; -fx-background-radius: 12;");
        card.getChildren().addAll(nilaiLbl, judulLbl);
        return card;
    }
    
}
