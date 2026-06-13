package uasGizi;

import uasGizi.user.User;
import uasGizi.makanan.Makanan;
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

public class UserAddFoodView {
    private User user;
    private Stage stage;

    public UserAddFoodView(User user, Stage stage) {
        this.user = user;
        this.stage = stage;
    }

    public void show() {
        Label titleLabel = new Label("Tambah Makanan/Minuman Baru");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#F19CBB"));

        Label infoLabel = new Label("Isi nama wajib, data gizi boleh dikosongkan.\nAhli gizi akan memverifikasi data kamu.");
        infoLabel.setFont(Font.font("Arial", 12));
        infoLabel.setTextFill(Color.web("#888888"));
        infoLabel.setWrapText(true);

        TextField namaField = new TextField();
        namaField.setPromptText("Nama makanan/minuman (wajib)");
        namaField.setPrefHeight(38);
        namaField.setStyle("-fx-background-radius: 8;");

        TextField kaloriField = new TextField();
        kaloriField.setPromptText("Kalori per 100g (opsional)");
        kaloriField.setPrefHeight(38);
        kaloriField.setStyle("-fx-background-radius: 8;");

        TextField proteinField = new TextField();
        proteinField.setPromptText("Protein per 100g (opsional)");
        proteinField.setPrefHeight(38);
        proteinField.setStyle("-fx-background-radius: 8;");

        TextField lemakField = new TextField();
        lemakField.setPromptText("Lemak per 100g (opsional)");
        lemakField.setPrefHeight(38);
        lemakField.setStyle("-fx-background-radius: 8;");

        TextField karboField = new TextField();
        karboField.setPromptText("Karbohidrat per 100g (opsional)");
        karboField.setPrefHeight(38);
        karboField.setStyle("-fx-background-radius: 8;");

        Label msgLabel = new Label("");
        msgLabel.setWrapText(true);

        Button kirimBtn = new Button("Kirim Request");
        kirimBtn.setPrefWidth(300);
        kirimBtn.setPrefHeight(40);
        kirimBtn.setStyle("-fx-background-color: #F05F80; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");

        Button backBtn = new Button("Kembali");
        backBtn.setPrefWidth(300);
        backBtn.setPrefHeight(38);
        backBtn.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");

        kirimBtn.setOnAction(e -> {
            if (namaField.getText().isEmpty()) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Nama makanan tidak boleh kosong!");
                return;
            }
            try {
                Makanan m = new Makanan();
                m.setNama(namaField.getText());
                m.setKalori(kaloriField.getText().isEmpty() ? 0 : Float.parseFloat(kaloriField.getText()));
                m.setProtein(proteinField.getText().isEmpty() ? 0 : Float.parseFloat(proteinField.getText()));
                m.setLemak(lemakField.getText().isEmpty() ? 0 : Float.parseFloat(lemakField.getText()));
                m.setKarbohidrat(karboField.getText().isEmpty() ? 0 : Float.parseFloat(karboField.getText()));
                m.setStatus("pending");
                m.setDitambahOleh("user");
                m.setUserId(user.getId());

                MakananImplement mi = new MakananImplement();
                mi.insert(m);

                msgLabel.setTextFill(Color.web("#4CAF50"));
                msgLabel.setText("Request berhasil dikirim! Menunggu verifikasi ahli gizi.");
                namaField.clear();
                kaloriField.clear();
                proteinField.clear();
                lemakField.clear();
                karboField.clear();
            } catch (NumberFormatException ex) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Data gizi harus berupa angka!");
            } catch (Exception ex) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Error: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> {
            DashboardView dv = new DashboardView(user);
            dv.show(stage);
        });

        VBox card = new VBox(12);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setMaxWidth(420);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 16; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 4);");
        card.getChildren().addAll(titleLabel, infoLabel, namaField,
                kaloriField, proteinField, lemakField, karboField,
                kirimBtn, backBtn, msgLabel);

        StackPane root = new StackPane(card);
        root.setStyle("-fx-background-color: #FFF0F5;");

        Scene scene = new Scene(root, 550, 580);
        stage.setTitle("Piring Nadi - Tambah Makanan");
        stage.setScene(scene);
        stage.show();
    }
}
