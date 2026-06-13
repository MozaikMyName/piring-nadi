package uasGizi;

import uasGizi.user.User;
import uasGizi.makanan.MakananImplement;
import uasGizi.makanan.Makanan;
import uasGizi.log.Log;
import uasGizi.log.LogDetail;
import uasGizi.log.LogImplement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class HistoryView {
    private User user;
    private Stage stage;

    public HistoryView(User user, Stage stage) {
        this.user = user;
        this.stage = stage;
    }

    public void show() {
        Label titleLabel = new Label("Riwayat Asupan Gizi - " + user.getNama());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#2E7D32"));

        // Tabel riwayat
        TableView<LogDetail> table = new TableView<>();
        table.setPrefHeight(350);

        TableColumn<LogDetail, String> tanggalCol = new TableColumn<>("Tanggal");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        tanggalCol.setPrefWidth(100);

        TableColumn<LogDetail, String> makananCol = new TableColumn<>("Makanan");
        makananCol.setCellValueFactory(new PropertyValueFactory<>("namaMakanan"));
        makananCol.setPrefWidth(150);

        TableColumn<LogDetail, String> beratCol = new TableColumn<>("Berat (g)");
        beratCol.setCellValueFactory(new PropertyValueFactory<>("beratGram"));
        beratCol.setPrefWidth(80);

        TableColumn<LogDetail, String> kaloriCol = new TableColumn<>("Kalori");
        kaloriCol.setCellValueFactory(new PropertyValueFactory<>("totalKalori"));
        kaloriCol.setPrefWidth(80);

        TableColumn<LogDetail, String> proteinCol = new TableColumn<>("Protein (g)");
        proteinCol.setCellValueFactory(new PropertyValueFactory<>("totalProtein"));
        proteinCol.setPrefWidth(90);

        TableColumn<LogDetail, String> lemakCol = new TableColumn<>("Lemak (g)");
        lemakCol.setCellValueFactory(new PropertyValueFactory<>("totalLemak"));
        lemakCol.setPrefWidth(90);

        TableColumn<LogDetail, String> karboCol = new TableColumn<>("Karbo (g)");
        karboCol.setCellValueFactory(new PropertyValueFactory<>("totalKarbo"));
        karboCol.setPrefWidth(90);

        table.getColumns().addAll(tanggalCol, makananCol, beratCol,
                kaloriCol, proteinCol, lemakCol, karboCol);

        // Load data
        Label totalLabel = new Label("");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        totalLabel.setTextFill(Color.web("#FF7043"));

        try {
            LogImplement li = new LogImplement();
            MakananImplement mi = new MakananImplement();
            List<Log> logs = li.getByUserId(user.getId());
            List<Makanan> makananList = mi.getAll();

            float totalKalori = 0, totalProtein = 0, totalLemak = 0, totalKarbo = 0;

            for (Log l : logs) {
                for (Makanan m : makananList) {
                    if (m.getId() == l.getMakananId()) {
                        float berat = l.getBeratGram();
                        float kal = m.getKalori() * berat / 100;
                        float pro = m.getProtein() * berat / 100;
                        float lem = m.getLemak() * berat / 100;
                        float kar = m.getKarbohidrat() * berat / 100;

                        table.getItems().add(new LogDetail(
                            l.getTanggal().toString(),
                            m.getNama(),
                            String.format("%.0f", berat),
                            String.format("%.0f", kal),
                            String.format("%.1f", pro),
                            String.format("%.1f", lem),
                            String.format("%.1f", kar)
                        ));

                        totalKalori += kal;
                        totalProtein += pro;
                        totalLemak += lem;
                        totalKarbo += kar;
                        break;
                    }
                }
            }

            totalLabel.setText(String.format(
                "Total Semua: %.0f kkal | Protein: %.1fg | Lemak: %.1fg | Karbo: %.1fg",
                totalKalori, totalProtein, totalLemak, totalKarbo
            ));

        } catch (Exception e) {
            totalLabel.setText("Error memuat data: " + e.getMessage());
        }

        Button backBtn = new Button("Kembali ke Dashboard");
        backBtn.setPrefWidth(250);
        backBtn.setPrefHeight(40);
        backBtn.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 13; -fx-cursor: hand;");

        backBtn.setOnAction(e -> {
            DashboardView dv = new DashboardView(user);
            dv.show(stage);
        });
        
        Button exportBtn = new Button("Export ke .txt");
        exportBtn.setPrefWidth(250);
        exportBtn.setPrefHeight(40);
        exportBtn.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 13; -fx-cursor: hand;");

        exportBtn.setOnAction(e -> {
            try {
                String folderPath = "D:/piring-nadi/export/";
                new java.io.File(folderPath).mkdirs();
                String namaFile = folderPath + "riwayat_" + user.getUsername() + ".txt";
                PrintWriter pw = new PrintWriter(new FileWriter(namaFile));

                pw.println("============================");
                pw.println("RIWAYAT ASUPAN GIZI");
                pw.println("Nama  : " + user.getNama());
                pw.println("============================");
                pw.printf("%-12s | %-20s | %-6s | %-7s | %-9s | %-8s | %-8s%n",
                    "Tanggal", "Makanan", "Berat", "Kalori", "Protein", "Lemak", "Karbo");
                pw.println("--------------------------------------------------------------------");

                for (LogDetail ld : table.getItems()) {
                    pw.printf("%-12s | %-20s | %-6s | %-7s | %-9s | %-8s | %-8s%n",
                        ld.getTanggal(),
                        ld.getNamaMakanan(),
                        ld.getBeratGram() + "g",
                        ld.getTotalKalori(),
                        ld.getTotalProtein() + "g",
                        ld.getTotalLemak() + "g",
                        ld.getTotalKarbo() + "g"
                    );
                }

                pw.println("============================");
                pw.println(totalLabel.getText());
                pw.println("============================");
                pw.close();

                Alert info = new Alert(Alert.AlertType.INFORMATION);
                info.setTitle("Export Berhasil");
                info.setHeaderText(null);
                info.setContentText("Riwayat berhasil disimpan ke file:\n" + namaFile);
                info.showAndWait();

            } catch (IOException ex) {
                Alert err = new Alert(Alert.AlertType.ERROR);
                err.setTitle("Export Gagal");
                err.setHeaderText(null);
                err.setContentText("Error: " + ex.getMessage());
                err.showAndWait();
            }
        });
        
        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F0F4F0;");
        root.getChildren().addAll(titleLabel, table, totalLabel, exportBtn, backBtn);

        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Piring Nadi - Riwayat Harian");
        stage.setScene(scene);
        stage.show();
    }
}
