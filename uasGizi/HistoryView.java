package uasGizi;

import uasGizi.user.User;
import uasGizi.makanan.MakananImplement;
import uasGizi.makanan.Makanan;
import uasGizi.log.Log;
import uasGizi.log.LogDetail;
import uasGizi.log.LogImplement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class HistoryView {
    private User user;

    public HistoryView(User user) {
        this.user = user;
    }

    public Node getView() {
        Label titleLabel = new Label("Riwayat Asupan Gizi - " + user.getNama());
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#2E7D32"));

        TableView<LogDetail> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); 

        TableColumn<LogDetail, String> tanggalCol = new TableColumn<>("Tanggal");
        tanggalCol.setCellValueFactory(new PropertyValueFactory<>("tanggal"));
        
        TableColumn<LogDetail, String> makananCol = new TableColumn<>("Makanan");
        makananCol.setCellValueFactory(new PropertyValueFactory<>("namaMakanan"));

        TableColumn<LogDetail, String> beratCol = new TableColumn<>("Berat (g)");
        beratCol.setCellValueFactory(new PropertyValueFactory<>("beratGram"));

        TableColumn<LogDetail, String> kaloriCol = new TableColumn<>("Kalori");
        kaloriCol.setCellValueFactory(new PropertyValueFactory<>("totalKalori"));

        table.getColumns().addAll(tanggalCol, makananCol, beratCol, kaloriCol);

        Label totalLabel = new Label("");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        totalLabel.setTextFill(Color.web("#FF7043"));

        try {
            LogImplement li = new LogImplement();
            MakananImplement mi = new MakananImplement();
            List<Log> logs = li.getByUserId(user.getId());
            List<Makanan> makananList = mi.getAll();

            float totalKalori = 0;
            for (Log l : logs) {
                for (Makanan m : makananList) {
                    if (m.getId() == l.getMakananId()) {
                        float berat = l.getBeratGram();
                        float kal = m.getKalori() * berat / 100;
                        table.getItems().add(new LogDetail(
                            l.getTanggal().toString(), m.getNama(), 
                            String.format("%.0f", berat), String.format("%.0f", kal), 
                            "0", "0", "0"
                        ));
                        totalKalori += kal;
                        break;
                    }
                }
            }
            totalLabel.setText("Total Kalori: " + String.format("%.0f", totalKalori) + " kkal");
        } catch (Exception e) {
            totalLabel.setText("Error memuat data.");
        }

        Button exportBtn = new Button("Export ke .txt");
        exportBtn.setPrefHeight(40);
        exportBtn.setStyle("-fx-background-color: #2E7D32; -fx-text-fill: white; -fx-background-radius: 8; -fx-cursor: hand;");

        exportBtn.setOnAction(e -> {
        });

        VBox container = new VBox(15);
        container.setPadding(new Insets(30));
        container.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 16;");
        container.getChildren().addAll(titleLabel, table, totalLabel, exportBtn);

        return container;
    }
}