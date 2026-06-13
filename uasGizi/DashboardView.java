package uasGizi;

import uasGizi.user.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class DashboardView {
    private User user;

    public DashboardView(User user) {
        this.user = user;
    }

    public void show(Stage stage) {
        Label namaLabel = new Label("Halo, " + user.getNama() + "!");
        namaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        namaLabel.setTextFill(Color.web("#F19CBB"));

        Label subLabel = new Label("Pantau asupan gizimu hari ini");
        subLabel.setFont(Font.font("Arial", 13));
        subLabel.setTextFill(Color.web("#888888"));
        
        int targetKalori = (int) user.getTargetKalori();
        int targetProtein = (int)(targetKalori * 0.20 / 4);
        int targetLemak = (int)(targetKalori * 0.25 / 9);
        int targetKarbo = (int)(targetKalori * 0.55 / 4);

        HBox cardBox = new HBox(15);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.getChildren().addAll(
            buatCard("Target Kalori", targetKalori + " kkal", "#FF7F50"),
            buatCard("Target Protein", targetProtein + " g", "#F88379"),
            buatCard("Target Lemak", targetLemak + " g", "#FF69B4"),
            buatCard("Target Karbo", targetKarbo + " g", "#FAA0A0")
        );

        Button inputBtn = new Button("Input Makanan");
        inputBtn.setPrefWidth(200);
        inputBtn.setPrefHeight(45);
        inputBtn.setStyle("-fx-background-color: #F33A6A; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

        Button historyBtn = new Button("Riwayat Harian");
        historyBtn.setPrefWidth(200);
        historyBtn.setPrefHeight(45);
        historyBtn.setStyle("-fx-background-color: #E0115F; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setPrefWidth(200);
        logoutBtn.setPrefHeight(45);
        logoutBtn.setStyle("-fx-background-color: #DC143C; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");
        
        Button tambahMakananBtn = new Button("Usul Makanan/Minuman Baru");
        tambahMakananBtn.setPrefWidth(200);
        tambahMakananBtn.setPrefHeight(45);
        tambahMakananBtn.setStyle("-fx-background-color: #F19CBB; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");
        
        Button gantiTargetBtn = new Button("Ganti Target");
        gantiTargetBtn.setPrefWidth(200);
        gantiTargetBtn.setPrefHeight(45);
        gantiTargetBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

        gantiTargetBtn.setOnAction(e -> {
            new GoalSelectionView(user, stage).show();
        });

        tambahMakananBtn.setOnAction(e -> {
            UserAddFoodView uafv = new UserAddFoodView(user, stage);
            uafv.show();
        });

        inputBtn.setOnAction(e -> {
            FoodInputView fiv = new FoodInputView(user, stage);
            fiv.show();
        });

        historyBtn.setOnAction(e -> {
            HistoryView hv = new HistoryView(user, stage);
            hv.show();
        });

        logoutBtn.setOnAction(e -> {
            LoginView lv = new LoginView();
            lv.show(stage);
        });

        HBox btnBox = new HBox(15);
        btnBox.setAlignment(Pos.CENTER);
        btnBox.getChildren().addAll(inputBtn, historyBtn, tambahMakananBtn, gantiTargetBtn, logoutBtn);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #F0F4F0;");
        root.getChildren().addAll(namaLabel, subLabel, cardBox, btnBox);

        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("Piring Nadi - Dashboard");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private VBox buatCard(String judul, String nilai, String warna) {
        Label judulLabel = new Label(judul);
        judulLabel.setFont(Font.font("Arial", 12));
        judulLabel.setTextFill(Color.WHITE);

        Label nilaiLabel = new Label(nilai);
        nilaiLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        nilaiLabel.setTextFill(Color.WHITE);

        VBox card = new VBox(5);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setPrefWidth(140);
        card.setStyle("-fx-background-color: " + warna + "; " +
                "-fx-background-radius: 12;");
        card.getChildren().addAll(judulLabel, nilaiLabel);
        return card;
    }
}
