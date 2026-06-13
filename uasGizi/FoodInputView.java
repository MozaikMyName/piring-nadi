package uasGizi;

import uasGizi.user.User;
import uasGizi.makanan.MakananImplement;
import uasGizi.makanan.Makanan;
import uasGizi.log.Log;
import uasGizi.log.LogImplement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class FoodInputView {
    private User user;
    private Stage stage;
    
    public FoodInputView(User user, Stage stage) {
        this.user = user;
        this.stage = stage;
    }
    
    public void show() {
        Label titleLabel = new Label("Input Makanan Hari Ini");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#F19CBB"));
        
        Label infoLabel = new Label("Pilih makanan untuk lihat info gizi");
        infoLabel.setFont(Font.font("Arial", 12));
        infoLabel.setTextFill(Color.web("#888888"));
        
        TextField searchField = new TextField();
        searchField.setPromptText("Cari makanan...");
        searchField.setPrefHeight(40);
        searchField.setPrefWidth(300);
        searchField.setStyle("-fx-background-radius: 8;");

        ListView<String> searchResult = new ListView<>();
        searchResult.setPrefHeight(150);
        searchResult.setPrefWidth(300);
        searchResult.setVisible(false);

        List<Makanan> listMakanan = null;
        try {
            MakananImplement mi = new MakananImplement();
            listMakanan = mi.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        final List<Makanan> finalListMakanan = listMakanan;
        final Makanan[] selectedMakanan = {null};
        
        searchField.setOnKeyReleased(e -> {
            String keyword = searchField.getText().toLowerCase();
            searchResult.getItems().clear();
            if (!keyword.isEmpty() && finalListMakanan != null) {
                for (Makanan m : finalListMakanan) {
                    if (m.getNama().toLowerCase().contains(keyword)) {
                        searchResult.getItems().add(m.getId() + " - " + m.getNama());
                    }
                }
                searchResult.setVisible(!searchResult.getItems().isEmpty());
            } else {
                searchResult.setVisible(false);
            }
        });
        
        searchResult.setOnMouseClicked(e -> {
            String selected = searchResult.getSelectionModel().getSelectedItem();
            if (selected != null && finalListMakanan != null) {
                int id = Integer.parseInt(selected.split(" - ")[0]);
                for (Makanan m : finalListMakanan) {
                    if (m.getId() == id) {
                        selectedMakanan[0] = m;
                        searchField.setText(m.getNama());
                        infoLabel.setText(String.format(
                            "Per 100g — Kalori: %.0f kkal | Protein: %.1fg | Lemak: %.1fg | Karbo: %.1fg",
                            m.getKalori(), m.getProtein(), m.getLemak(), m.getKarbohidrat()
                        ));
                        break;
                    }
                }
                searchResult.setVisible(false);
            }
        });

        TextField beratField = new TextField();
        beratField.setPromptText("Berat yang dimakan (gram)");
        beratField.setPrefHeight(40);
        beratField.setPrefWidth(300);
        beratField.setStyle("-fx-background-radius: 8; -fx-border-radius: 8;");

        Label previewLabel = new Label("");
        previewLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        previewLabel.setTextFill(Color.web("#FF7043"));

        beratField.setOnKeyReleased(e -> {
            if (selectedMakanan[0] != null && !beratField.getText().isEmpty()) {
                try {
                    float berat = Float.parseFloat(beratField.getText());
                    Makanan m = selectedMakanan[0];
                    float kalori = m.getKalori() * berat / 100;
                    float protein = m.getProtein() * berat / 100;
                    float lemak = m.getLemak() * berat / 100;
                    float karbo = m.getKarbohidrat() * berat / 100;
                    previewLabel.setText(String.format(
                        "Total: %.0f kkal | Protein: %.1fg | Lemak: %.1fg | Karbo: %.1fg",
                        kalori, protein, lemak, karbo
                    ));
                } catch (NumberFormatException ex) {
                    previewLabel.setText("Masukkan angka yang valid");
                }
            }
        });

        Button simpanBtn = new Button("Simpan Log");
        simpanBtn.setPrefWidth(300);
        simpanBtn.setPrefHeight(40);
        simpanBtn.setStyle("-fx-background-color: #F05F80; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

        Button backBtn = new Button("Kembali ke Dashboard");
        backBtn.setPrefWidth(300);
        backBtn.setPrefHeight(40);
        backBtn.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

        Label msgLabel = new Label("");
        msgLabel.setTextFill(Color.web("#4CAF50"));

        simpanBtn.setOnAction(e -> {
            if (selectedMakanan[0] == null) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Pilih makanan dulu!");
                return;
            }
            if (beratField.getText().isEmpty()) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Isi berat gram dulu!");
                return;
            }
            try {
                Makanan m = selectedMakanan[0];
                Log l = new Log();
                l.setUserId(user.getId());
                l.setMakananId(m.getId());
                l.setTanggal(Date.valueOf(LocalDate.now()));
                l.setBeratGram(Float.parseFloat(beratField.getText()));

                LogImplement li = new LogImplement();
                li.insert(l);

                msgLabel.setTextFill(Color.web("#F05F80"));
                msgLabel.setText("Log berhasil disimpan!");
                searchField.clear();
                beratField.clear();
                previewLabel.setText("");
                infoLabel.setText("Pilih makanan untuk lihat info gizi");
                selectedMakanan[0] = null;
                searchResult.setVisible(false);
            } catch (Exception ex) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Error: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> {
            DashboardView dv = new DashboardView(user);
            dv.show(stage);
        });

        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setMaxWidth(420);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 4);");
        card.getChildren().addAll(titleLabel, searchField, searchResult, infoLabel,
                beratField, previewLabel, simpanBtn, backBtn, msgLabel);

        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color: #F0F4F0;");

        Scene scene = new Scene(root, 550, 500);
        stage.setTitle("NutriTrack - Input Makanan");
        stage.setScene(scene);
        stage.show();
    }
}
