package uasGizi;

import uasGizi.user.User;
import uasGizi.user.UserImplement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GoalSelectionView {
    
    private User user;
    private Stage stage;

    public GoalSelectionView(User user, Stage stage) {
        this.user = user;
        this.stage = stage;
    }

    public void show() {
        Label titleLabel = new Label("Apa tujuanmu?");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#F05F80"));

        Label subtitleLabel = new Label("Pilih tujuan untuk menentukan target kalori harianmu");
        subtitleLabel.setFont(Font.font("Arial", 13));
        subtitleLabel.setTextFill(Color.web("#888888"));
        subtitleLabel.setWrapText(true);

        Button dietBtn = buatTombol("🥗 Menurunkan Berat Badan", "Defisit kalori untuk diet sehat", "#F05F80");
        Button tambahBtn = buatTombol("💪 Menambah Berat Badan", "Surplus kalori untuk massa otot", "#42A5F5");
        Button jagaBtn = buatTombol("⚖️ Menjaga Berat Ideal", "Kalori maintenance untuk tubuh seimbang", "#66BB6A");
        Button atletBtn = buatTombol("🏃 Meningkatkan Performa", "Kalori optimal untuk aktivitas tinggi", "#FF9800");

        dietBtn.setOnAction(e -> showRekomendasi("Diet"));
        tambahBtn.setOnAction(e -> showRekomendasi("Tambah Berat"));
        jagaBtn.setOnAction(e -> showRekomendasi("Jaga Berat"));
        atletBtn.setOnAction(e -> showRekomendasi("Tingkatkan Performa"));

        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setMaxWidth(420);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 4);");
        card.getChildren().addAll(titleLabel, subtitleLabel, dietBtn, tambahBtn, jagaBtn, atletBtn);

        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color: #FFF0F5;");

        Scene scene = new Scene(root, 550, 500);
        stage.setTitle("Piring Nadi - Pilih Tujuan");
        stage.setScene(scene);
        stage.show();
    }

    private void showRekomendasi(String tujuan) {
        float bmr = hitungBMR();
        float multiplier = getMultiplier();
        float maintenance = bmr * multiplier;

        float[] targets;
        String[] labels;
        String[] deskripsi;
        String warna;

        if (tujuan.equals("Diet")) {
            targets = new float[]{maintenance - 250, maintenance - 500, maintenance - 750};
            labels = new String[]{"🟢 Ringan", "🟡 Sedang", "🔴 Agresif"};
            deskripsi = new String[]{
                "Defisit 250 kkal — turun pelan, lebih sustainable",
                "Defisit 500 kkal — standar, ~0.5kg/minggu",
                "Defisit 750 kkal — cepat tapi butuh komitmen"
            };
            warna = "#F05F80";
        } else if (tujuan.equals("Tambah Berat")) {
            targets = new float[]{maintenance + 250, maintenance + 500, maintenance + 750};
            labels = new String[]{"🟢 Ringan", "🟡 Sedang", "🔴 Agresif"};
            deskripsi = new String[]{
                "Surplus 250 kkal — naik pelan, minim lemak",
                "Surplus 500 kkal — standar untuk mass building",
                "Surplus 750 kkal — cepat naik, cocok untuk skinny"
            };
            warna = "#42A5F5";
        } else if (tujuan.equals("Jaga Berat")) {
            targets = new float[]{maintenance};
            labels = new String[]{"⚖️ Maintenance"};
            deskripsi = new String[]{"Kalori seimbang untuk menjaga berat ideal"};
            warna = "#66BB6A";
        } else {
            targets = new float[]{maintenance + 300};
            labels = new String[]{"🏃 Performa Optimal"};
            deskripsi = new String[]{"Kalori lebih untuk mendukung aktivitas tinggi"};
            warna = "#FF9800";
        }

        Label titleLabel = new Label("Pilih Target Kalori");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.web(warna));

        Label infoLabel = new Label(String.format(
            "Kalori maintenance kamu: %.0f kkal/hari", maintenance));
        infoLabel.setFont(Font.font("Arial", 13));
        infoLabel.setTextFill(Color.web("#888888"));

        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setMaxWidth(450);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 4);");
        card.getChildren().addAll(titleLabel, infoLabel);

        for (int i = 0; i < targets.length; i++) {
            final float target = targets[i];
            final String tujuanFinal = tujuan;
            Button btn = new Button(labels[i] + " — " + String.format("%.0f", target) + " kkal/hari\n" + deskripsi[i]);
            btn.setPrefWidth(370);
            btn.setPrefHeight(60);
            btn.setWrapText(true);
            btn.setStyle("-fx-background-color: " + warna + "; -fx-text-fill: white; " +
                    "-fx-background-radius: 10; -fx-font-size: 13; -fx-cursor: hand;");
            btn.setOnAction(e -> simpan(tujuanFinal, target));
            card.getChildren().add(btn);
        }
        
        Label atauLabel = new Label("— atau —");
        atauLabel.setFont(Font.font("Arial", 13));
        atauLabel.setTextFill(Color.web("#888888"));

        TextField manualField = new TextField();
        manualField.setPromptText("Masukkan target kalori sendiri");
        manualField.setPrefWidth(370);
        manualField.setPrefHeight(40);
        manualField.setStyle("-fx-background-radius: 8;");

        Button manualBtn = new Button("Pakai Target Ini");
        manualBtn.setPrefWidth(370);
        manualBtn.setPrefHeight(40);
        manualBtn.setStyle("-fx-background-color: #607D8B; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");
        manualBtn.setOnAction(e -> {
            try {
                float targetManual = Float.parseFloat(manualField.getText());
                if (targetManual < 500 || targetManual > 10000) {
                    manualField.setStyle("-fx-background-radius: 8; -fx-border-color: red;");
                    return;
                }
                simpan(tujuan, targetManual);
            } catch (NumberFormatException ex) {
                manualField.setStyle("-fx-background-radius: 8; -fx-border-color: red;");
            }
        });

        card.getChildren().addAll(atauLabel, manualField, manualBtn);

        Button backBtn = new Button("← Kembali");
        backBtn.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");
        backBtn.setOnAction(e -> show());
        card.getChildren().add(backBtn);

        ScrollPane scroll = new ScrollPane(card);
        scroll.setFitToWidth(true);
        scroll.setStyle("-fx-background: #FFF0F5; -fx-background-color: #FFF0F5;");

        StackPane root = new StackPane(scroll);
        root.setStyle("-fx-background-color: #FFF0F5;");

        Scene scene = new Scene(root, 550, 550);
        stage.setScene(scene);
        stage.show();
    }

    private void simpan(String tujuan, float targetKalori) {
        try {
            user.setTujuan(tujuan);
            user.setTargetKalori(targetKalori);
            UserImplement ui = new UserImplement();
            ui.update(user);
            new DashboardView(user).show(stage);
        } catch (Exception ex) {
            System.out.println("Error simpan tujuan: " + ex.getMessage());
        }
    }

    private float hitungBMR() {
        float berat = user.getBeratKg();
        float tinggi = user.getTinggiCm();
        int usia = user.getUsia();
        if ("Laki-laki".equals(user.getGender())) {
            return 88.362f + (13.397f * berat) + (4.799f * tinggi) - (5.677f * usia);
        } else {
            return 447.593f + (9.247f * berat) + (3.098f * tinggi) - (4.330f * usia);
        }
    }

    private float getMultiplier() {
        return switch (user.getAktivitas()) {
            case "Sedentary" -> 1.2f;
            case "Ringan" -> 1.375f;
            case "Sedang" -> 1.55f;
            case "Aktif" -> 1.725f;
            case "Sangat Aktif" -> 1.9f;
            default -> 1.2f;
        };
    }

    private Button buatTombol(String judul, String deskripsi, String warna) {
        Button btn = new Button(judul + "\n" + deskripsi);
        btn.setPrefWidth(340);
        btn.setPrefHeight(60);
        btn.setWrapText(true);
        btn.setStyle("-fx-background-color: " + warna + "; -fx-text-fill: white; " +
                "-fx-background-radius: 10; -fx-font-size: 13; -fx-cursor: hand;");
        return btn;
    }
    
}
