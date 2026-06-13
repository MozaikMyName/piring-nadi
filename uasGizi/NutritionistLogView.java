package uasGizi;

import uasGizi.ahliGizi.AhliGizi;
import uasGizi.user.User;
import uasGizi.makanan.Makanan;
import uasGizi.makanan.MakananImplement;
import uasGizi.log.Log;
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

public class NutritionistLogView {
    private User user;
    private AhliGizi ahliGizi;
    private Stage stage;

    public NutritionistLogView(User user, AhliGizi ahliGizi, Stage stage) {
        this.user = user;
        this.ahliGizi = ahliGizi;
        this.stage = stage;
    }

    public void show() {
        Label titleLabel = new Label("Log Makan - " + user.getNama());
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#9C27B0"));

        Label infoLabel = new Label("Usia: " + user.getUsia() +
                " | Gender: " + user.getGender() +
                " | Berat: " + user.getBeratKg() + "kg" +
                " | Tinggi: " + user.getTinggiCm() + "cm");
        infoLabel.setFont(Font.font("Arial", 13));
        infoLabel.setTextFill(Color.web("#666666"));

        TableView<uasGizi.log.LogDetail> table = new TableView<>();
        table.setPrefHeight(350);

        TableColumn<uasGizi.log.LogDetail, String> tanggalCol = new TableColumn<>("Tanggal");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        tanggalCol.setPrefWidth(100);

        TableColumn<uasGizi.log.LogDetail, String> makananCol = new TableColumn<>("Makanan");
        makananCol.setCellValueFactory(new PropertyValueFactory<>("namaMakanan"));
        makananCol.setPrefWidth(150);

        TableColumn<uasGizi.log.LogDetail, String> beratCol = new TableColumn<>("Berat (g)");
        beratCol.setCellValueFactory(new PropertyValueFactory<>("beratGram"));
        beratCol.setPrefWidth(80);

        TableColumn<uasGizi.log.LogDetail, String> kaloriCol = new TableColumn<>("Kalori");
        kaloriCol.setCellValueFactory(new PropertyValueFactory<>("totalKalori"));
        kaloriCol.setPrefWidth(80);

        TableColumn<uasGizi.log.LogDetail, String> proteinCol = new TableColumn<>("Protein (g)");
        proteinCol.setCellValueFactory(new PropertyValueFactory<>("totalProtein"));
        proteinCol.setPrefWidth(90);

        TableColumn<uasGizi.log.LogDetail, String> lemakCol = new TableColumn<>("Lemak (g)");
        lemakCol.setCellValueFactory(new PropertyValueFactory<>("totalLemak"));
        lemakCol.setPrefWidth(80);

        TableColumn<uasGizi.log.LogDetail, String> karboCol = new TableColumn<>("Karbo (g)");
        karboCol.setCellValueFactory(new PropertyValueFactory<>("totalKarbo"));
        karboCol.setPrefWidth(80);

        table.getColumns().addAll(tanggalCol, makananCol, beratCol,
                kaloriCol, proteinCol, lemakCol, karboCol);

        Label totalLabel = new Label("");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 13));
        totalLabel.setTextFill(Color.web("#9C27B0"));

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

                        table.getItems().add(new uasGizi.log.LogDetail(
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
                "Total: %.0f kkal | Protein: %.1fg | Lemak: %.1fg | Karbo: %.1fg",
                totalKalori, totalProtein, totalLemak, totalKarbo
            ));

        } catch (Exception e) {
            totalLabel.setText("Error: " + e.getMessage());
        }

        Button backBtn = new Button("Kembali");
        backBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            NutritionistView nv = new NutritionistView(ahliGizi);
            nv.show(stage);
        });

        VBox root = new VBox(12);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F9F0FF;");
        root.getChildren().addAll(titleLabel, infoLabel, table, totalLabel, backBtn);

        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("Piring Nadi - Log User");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}