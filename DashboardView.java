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

public class DashboardView {
    private User user;

    public DashboardView(User user) {
        this.user = user;
    }

    public void show(Stage stage) {
        Label namaLabel = new Label("Halo, " + user.getNama() + "!");
        namaLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        namaLabel.setTextFill(Color.web("#2E7D32"));

        Label subLabel = new Label("Pantau asupan gizimu hari ini");
        subLabel.setFont(Font.font("Arial", 13));
        subLabel.setTextFill(Color.web("#888888"));

        double bmr;
        if (user.getGender().equals("Laki-laki")) {
            bmr = 88.362 + (13.397 * user.getBeratKg())
                + (4.799 * user.getTinggiCm())
                - (5.677 * user.getUsia());
        } else {
            bmr = 447.593 + (9.247 * user.getBeratKg())
                + (3.098 * user.getTinggiCm())
                - (4.330 * user.getUsia());
        }

        double multiplier;
        switch (user.getAktivitas()) {
            case "Ringan": multiplier = 1.375; break;
            case "Sedang": multiplier = 1.55; break;
            case "Aktif": multiplier = 1.725; break;
            case "Sangat Aktif": multiplier = 1.9; break;
            default: multiplier = 1.2; break;
        }

        int targetKalori = (int)(bmr * multiplier);
        int targetProtein = (int)(targetKalori * 0.20 / 4);
        int targetLemak = (int)(targetKalori * 0.25 / 9);
        int targetKarbo = (int)(targetKalori * 0.55 / 4);

        HBox cardBox = new HBox(15);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.getChildren().addAll(
            buatCard("Target Kalori", targetKalori + " kkal", "#FF7043"),
            buatCard("Target Protein", targetProtein + " g", "#42A5F5"),
            buatCard("Target Lemak", targetLemak + " g", "#FFCA28"),
            buatCard("Target Karbo", targetKarbo + " g", "#66BB6A")
        );

        Button inputBtn = new Button("Input Makanan");
        inputBtn.setPrefWidth(200);
        inputBtn.setPrefHeight(45);
        inputBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

        Button historyBtn = new Button("Riwayat Harian");
        historyBtn.setPrefWidth(200);
        historyBtn.setPrefHeight(45);
        historyBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setPrefWidth(200);
        logoutBtn.setPrefHeight(45);
        logoutBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

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
        btnBox.getChildren().addAll(inputBtn, historyBtn, logoutBtn);

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: #F0F4F0;");
        root.getChildren().addAll(namaLabel, subLabel, cardBox, btnBox);

        Scene scene = new Scene(root, 700, 400);
        stage.setTitle("NutriTrack - Dashboard");
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
