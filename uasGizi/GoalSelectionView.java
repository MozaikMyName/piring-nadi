package uasGizi;

import java.sql.SQLException;
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
    private Runnable onSuccess;
    private Runnable onFinish;

    public GoalSelectionView(User user, Stage stage, Runnable onSuccess, Runnable onFinish) {
        this.user = user;
        this.stage = stage;
        this.onSuccess = onSuccess;
        this.onFinish = onFinish;
    }

    public void show() {
        VBox container = new VBox(20);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(50));
        container.setStyle("-fx-background-color: #FFF0F5;");

        Label titleLabel = new Label("Apa tujuanmu?");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        titleLabel.setTextFill(Color.web("#9C27B0"));

        Label subLabel = new Label("Pilih tujuanmu agar kami bisa menghitung target kalori harianmu.");
        subLabel.setFont(Font.font("Segoe UI", 14));
        subLabel.setTextFill(Color.web("#888888"));
        subLabel.setWrapText(true);

        Button dietBtn = buatTombol("🥗 Menurunkan Berat Badan", "Defisit kalori untuk diet sehat", "#F05F80");
        Button tambahBtn = buatTombol("💪 Menambah Berat Badan", "Surplus kalori untuk massa otot", "#42A5F5");
        Button jagaBtn = buatTombol("⚖️ Menjaga Berat Ideal", "Kalori maintenance untuk tubuh seimbang", "#66BB6A");
        Button atletBtn = buatTombol("🏃 Meningkatkan Performa", "Kalori optimal untuk aktivitas tinggi", "#FF9800");

        float maintenance = hitungBMR() * getMultiplier();
        
        Button backToDashboardBtn = new Button("← Kembali ke Dashboard");
        backToDashboardBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #9C27B0; -fx-font-size: 14px; -fx-cursor: hand;");
        backToDashboardBtn.setOnAction(e -> {
            try {
                new DashboardView(user).show(stage);
            } catch (SQLException ex) {
                System.getLogger(GoalSelectionView.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        });

        dietBtn.setOnAction(e   -> showPilihTarget("Diet",        maintenance, container));
        tambahBtn.setOnAction(e -> showPilihTarget("Tambah Berat", maintenance, container));
        jagaBtn.setOnAction(e   -> showPilihTarget("Jaga Berat",   maintenance, container));
        atletBtn.setOnAction(e -> showPilihTarget("Tingkatkan Performa", maintenance, container));

        container.getChildren().addAll(titleLabel, subLabel, dietBtn, tambahBtn, jagaBtn, atletBtn, backToDashboardBtn);

        Scene currentScene = stage.getScene();
        if (currentScene == null) {
            currentScene = new Scene(container, 900, 650);
            stage.setScene(currentScene);
        } else {
            currentScene.setRoot(container);
        }
        stage.setTitle("Piring Nadi - Pilih Tujuan");
        stage.show();
    }

    private void showPilihTarget(String tujuan, float maintenance, VBox parent) {
        parent.getChildren().clear();

        float target;
        String desc;
       

        switch (tujuan) {
            case "Diet": target = maintenance * 0.85f; desc = "Target yang disarankan untuk penurunan berat sehat."; break;
            case "Tambah Berat": target = maintenance * 1.15f; desc = "Target yang disarankan untuk menambah massa."; break;
            case "Tingkatkan Performa": target = maintenance * 1.10f; desc = "Target untuk mendukung aktivitas beratmu."; break;
            default: target = maintenance; desc = "Target untuk mempertahankan berat badan saat ini."; break;
        }

        Label title = new Label("Target Kalori Kamu");
        title.setFont(Font.font("Segoe UI", FontWeight.BOLD, 24));
        
        Label val = new Label(String.format("%.0f kkal", target));
        val.setFont(Font.font("Segoe UI", FontWeight.BOLD, 45));
        val.setTextFill(Color.web("#9C27B0"));

        Label info = new Label(desc);
        info.setTextFill(Color.web("#78788C"));

        Button btnKonfirmasi = new Button("Gunakan Target Ini");
        btnKonfirmasi.setPrefSize(300, 50);
        btnKonfirmasi.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; -fx-background-radius: 10; -fx-cursor: hand;");
        btnKonfirmasi.setOnAction(e -> simpanDanMasuk(tujuan, target));

        Button backBtn = new Button("← Kembali");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #9C27B0; -fx-cursor: hand;");
        backBtn.setOnAction(e -> show());

        parent.getChildren().addAll(title, val, info, btnKonfirmasi, backBtn);
    }

    private void simpanDanMasuk(String tujuan, float targetKalori) {
        try {
            user.setTujuan(tujuan);
            user.setTargetKalori(targetKalori);

            new UserImplement().update(user); 

            if (onSuccess != null) {
                onSuccess.run();
            }
        } catch (Exception ex) {
            ex.printStackTrace(); 
        }
    }

    private float hitungBMR() {
        float berat  = user.getBeratKg();
        float tinggi = user.getTinggiCm();
        int   usia   = user.getUsia();
        String gender = user.getGender();

        if ("Laki-laki".equalsIgnoreCase(gender)) {
            return 88.362f + (13.397f * berat) + (4.799f * tinggi) - (5.677f * usia);
        } else {
            return 447.593f + (9.247f * berat) + (3.098f * tinggi) - (4.330f * usia);
        }
    }

    private float getMultiplier() {
        String aktivitas = user.getAktivitas();
        if (aktivitas == null) return 1.2f;
        switch (aktivitas) {
            case "Ringan":      return 1.375f;
            case "Sedang":      return 1.55f;
            case "Aktif":       return 1.725f;
            case "Sangat Aktif": return 1.9f;
            default:            return 1.2f; 
        }
    }

    private Button buatTombol(String judul, String deskripsi, String warna) {
        Button btn = new Button(judul + "\n" + deskripsi);
        btn.setMaxWidth(420);
        btn.setPrefHeight(60);
        btn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        btn.setStyle("-fx-background-color: " + warna + "; " +
                "-fx-text-fill: white; " +
                "-fx-background-radius: 12; " +
                "-fx-cursor: hand;");
        btn.setOnMouseEntered(e -> btn.setOpacity(0.85));
        btn.setOnMouseExited(e  -> btn.setOpacity(1.0));
        return btn;
    }
}