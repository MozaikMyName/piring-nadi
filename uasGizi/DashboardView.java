package uasGizi;

import java.sql.SQLException;
import uasGizi.user.User;
import uasGizi.log.LogImplement;
import uasGizi.log.Log;
import uasGizi.makanan.MakananImplement;
import uasGizi.makanan.Makanan;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;
import uasGizi.user.UserImplement;

public class DashboardView {
    private User user;
    private BorderPane mainLayout;
    private Stage stage;
    private Runnable onFinish;

    public DashboardView(User user) {
        this.user = user;
    }
    
    public void show(Stage stage) throws SQLException {
        this.stage = stage;
        this.mainLayout = new BorderPane();
        User updatedUser = new UserImplement().getById(this.user.getId());
        if (updatedUser == null) {
            System.out.println("ERROR: User tidak ditemukan di database!");
            return;
        }
        this.user = updatedUser; 
    
        mainLayout.setLeft(createSidebar());
        mainLayout.setCenter(createDashboardHomeContent());

        Scene currentScene = stage.getScene();
        if (currentScene == null) {
            currentScene = new Scene(mainLayout, 1100, 700);
            stage.setScene(currentScene);
        } else {
            currentScene.setRoot(mainLayout);
        }

        stage.setTitle("Piring Nadi - Dashboard");
        stage.setResizable(true);
        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        stage.show();
    }

    private void setContent(Node node) {
        mainLayout.setCenter(node);
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(12);
        sidebar.setPadding(new Insets(35, 18, 35, 18));
        sidebar.setPrefWidth(260);
        sidebar.setStyle("-fx-background-color: #F9F9FB; " +
                         "-fx-border-color: #EBEBEF; " +
                         "-fx-border-width: 0 1 0 0;");

        Label logoLabel = new Label("Piring Nadi");
        logoLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
        logoLabel.setTextFill(Color.web("#9C27B0"));

        Label roleLabel = new Label("Halo, " + user.getNama() + "!");
        roleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        roleLabel.setTextFill(Color.web("#B2B2C2"));
        roleLabel.setPadding(new Insets(-5, 0, 30, 0));

        VBox logoContainer = new VBox(5, logoLabel, roleLabel);
        logoContainer.setPadding(new Insets(0, 0, 10, 8));

        Button menuDashboard = createSidebarButton("Dashboard", "#9C27B0");
        Button menuInput    = createSidebarButton("Input Makanan", "#E91E63");
        Button menuHistory  = createSidebarButton("Riwayat Harian", "#E91E63");
        Button menuAddFood  = createSidebarButton("Usul Makanan Baru", "#42A5F5");
        Button btnGantiTarget  = createSidebarButton("Ganti Target Gizi", "#42A5F5");
        Button menuEditProfile = createSidebarButton("Edit Profil", "#42A5F5");
        Button menuLogout   = createSidebarButton("Logout", "#7F8C8D");

        menuDashboard.setOnAction(e -> setContent(createDashboardHomeContent()));

        menuInput.setOnAction(e -> {
            FoodInputView fiv = new FoodInputView(user);
            setContent(fiv.getView());
        });

        menuHistory.setOnAction(e -> {
            HistoryView hv = new HistoryView(user);
            setContent(hv.getView());
        });

        menuAddFood.setOnAction(e -> {
            UserAddFoodView uafv = new UserAddFoodView(user);
            setContent(uafv.getView());
        });
        
        btnGantiTarget.setOnAction(e -> {
            try {
                GoalSelectionView gsv = new GoalSelectionView(user, stage, () -> {
                    try {
                        new DashboardView(user).show(stage);
                    } catch (SQLException ex) {
                        System.getLogger(DashboardView.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                    }
                }, null);
                gsv.show();
            } catch (Exception ex) {
                System.out.println("Error saat membuka GoalSelection: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
        
        menuEditProfile.setOnAction(e -> {
            setContent(new EditProfileView(user).getView());
        });

        menuLogout.setOnAction(e -> {
            LoginView lv = new LoginView();
            lv.show(stage);
        });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(logoContainer, menuDashboard, menuInput, menuHistory, menuAddFood, menuEditProfile, btnGantiTarget, spacer, menuLogout);
        return sidebar;
    }

    private VBox createDashboardHomeContent() {
        VBox mainContent = new VBox(30);
        mainContent.setPadding(new Insets(45, 45, 45, 45));
        mainContent.setStyle("-fx-background-color: #FFFFFF;");
        HBox.setHgrow(mainContent, Priority.ALWAYS);

        VBox headerBox = new VBox(6);
        Label welcomeLabel = new Label("Selamat Datang, " + user.getNama() + "!");
        welcomeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        welcomeLabel.setTextFill(Color.web("#1E1E2F"));
        Label subLabel = new Label("Pantau asupan gizi harianmu dan jaga pola makan yang sehat.");
        subLabel.setFont(Font.font("Segoe UI", 14));
        subLabel.setTextFill(Color.web("#9292A6"));
        headerBox.getChildren().addAll(welcomeLabel, subLabel);

        int totalLog = 0;
        float totalKaloriHariIni = 0;
        try {
            LogImplement li = new LogImplement();
            MakananImplement mi = new MakananImplement();
            List<Log> logs = li.getByUserId(user.getId());
            List<Makanan> makananList = mi.getAll();
            totalLog = logs.size();

            String today = java.time.LocalDate.now().toString();
            for (Log l : logs) {
                if (l.getTanggal().toString().equals(today)) {
                    for (Makanan m : makananList) {
                        if (m.getId() == l.getMakananId()) {
                            totalKaloriHariIni += m.getKalori() * l.getBeratGram() / 100;
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Gagal memuat statistik: " + e.getMessage());
        }

        float targetKalori = user.getTargetKalori() > 0 ? user.getTargetKalori() : 2000;
        float protein = (targetKalori * 0.25f) / 4; 
        float lemak = (targetKalori * 0.25f) / 9;
        float karbo = (targetKalori * 0.50f) / 4;

        HBox statsContainer = new HBox(25);
        statsContainer.getChildren().addAll(
            buatStatCard("Kalori Hari Ini", String.format("%.0f kkal", totalKaloriHariIni), "#E91E63", "🔥"),
            buatStatCard("Target Kalori", String.format("%.0f kkal", targetKalori), "#9C27B0", "🎯"),
            buatStatCard("Total Log", String.valueOf(totalLog), "#42A5F5", "📋")
        );

        HBox macroContainer = new HBox(25);
        macroContainer.getChildren().addAll(
            buatStatCard("Protein", String.format("%.0fg", protein), "#E91E63", "🥩"),
            buatStatCard("Lemak", String.format("%.0fg", lemak), "#FF9800", "🥑"),
            buatStatCard("Karbo", String.format("%.0fg", karbo), "#4CAF50", "🍞")
        );

        VBox profileCard = new VBox(10);
        profileCard.setPadding(new Insets(20, 25, 20, 25));
        profileCard.setMaxWidth(500);
        profileCard.setStyle("-fx-background-color: #FAFAFB; -fx-border-color: #E6E8F0; -fx-border-width: 1; -fx-border-radius: 12; -fx-background-radius: 12;");

        Label profileTitle = new Label("Profil Saya");
        profileTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 16));
        
        String tujuan = (user.getTujuan() != null && !user.getTujuan().isEmpty()) ? user.getTujuan() : "-";
        Label profileInfo = new Label(
            "Usia: " + user.getUsia() + " tahun  |  Gender: " + user.getGender() + "\n" +
            "Berat: " + user.getBeratKg() + " kg  |  Tinggi: " + user.getTinggiCm() + " cm\n" +
            "Aktivitas: " + user.getAktivitas() + "  |  Tujuan: " + tujuan
        );
        profileInfo.setFont(Font.font("Segoe UI", 13));
        profileCard.getChildren().addAll(profileTitle, profileInfo);
        
        VBox catatanCard = new VBox(10);
        catatanCard.setPadding(new Insets(20));
        catatanCard.setMaxWidth(Double.MAX_VALUE);
        catatanCard.setStyle("-fx-background-color: #FFF3E0; -fx-border-color: #FFE0B2; -fx-border-width: 1; -fx-border-radius: 12; -fx-background-radius: 12;");

        Label catatanTitle = new Label("💡 Catatan dari Ahli Gizi");
        catatanTitle.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        catatanTitle.setTextFill(Color.web("#E65100"));

        String isiCatatan = (user.getCatatanAhliGizi() != null && !user.getCatatanAhliGizi().isEmpty()) 
                            ? user.getCatatanAhliGizi() 
                            : "Belum ada catatan baru. Tetap semangat menjaga kesehatan!";

        Label catatanText = new Label(isiCatatan);
        catatanText.setWrapText(true);
        catatanText.setFont(Font.font("Segoe UI", 13));

        catatanCard.getChildren().addAll(catatanTitle, catatanText);

        mainContent.getChildren().addAll(headerBox, statsContainer, macroContainer, profileCard, catatanCard);
        return mainContent;
    }

    private Button createSidebarButton(String text, String activeColorHex) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(48);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(0, 0, 0, 20));
        btn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));

        String defaultStyle = "-fx-background-color: #F0F0F4; " +
                             "-fx-text-fill: #4A4A68; " +
                             "-fx-background-radius: 10; " +
                             "-fx-cursor: hand;";
        String hoverStyle   = "-fx-background-color: " + activeColorHex + "; " +
                             "-fx-text-fill: #FFFFFF; " +
                             "-fx-background-radius: 10; " +
                             "-fx-cursor: hand; " +
                             "-fx-effect: dropshadow(gaussian, " + activeColorHex + "4D, 8, 0, 0, 4);";

        btn.setStyle(defaultStyle);
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e  -> btn.setStyle(defaultStyle));
        return btn;
    }

    private VBox buatStatCard(String title, String value, String colorHex, String icon) {
        VBox card = new VBox(8);
        card.setPadding(new Insets(22, 25, 22, 25));
        card.setMinWidth(200);
        card.setPrefWidth(260);
        card.setMaxWidth(Double.MAX_VALUE);
        card.setStyle("-fx-background-color: #FFFFFF; " +
                     "-fx-background-radius: 16; " +
                     "-fx-effect: dropshadow(gaussian, rgba(160,163,189,0.12), 20, 0, 0, 8); " +
                     "-fx-border-color: #E6E8F0; " +
                     "-fx-border-width: 1; " +
                     "-fx-border-radius: 16;");

        HBox topRow = new HBox();
        topRow.setAlignment(Pos.CENTER_LEFT);

        Label titleLabel = new Label(title);
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 13));
        titleLabel.setTextFill(Color.web("#78788C"));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label iconLabel = new Label(icon);
        iconLabel.setFont(Font.font("Segoe UI", 20));

        topRow.getChildren().addAll(titleLabel, spacer, iconLabel);

        Label numLabel = new Label(value);
        numLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
        numLabel.setTextFill(Color.web("#1E1E2F"));

        Pane accentBar = new Pane();
        accentBar.setPrefHeight(4);
        accentBar.setMaxWidth(60);
        accentBar.setStyle("-fx-background-color: " + colorHex + "; -fx-background-radius: 2;");

        card.getChildren().addAll(topRow, numLabel, accentBar);
        return card;
    }
}