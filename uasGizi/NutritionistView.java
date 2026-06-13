package uasGizi;

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
import uasGizi.ahliGizi.AhliGizi;
import uasGizi.user.User;
import uasGizi.user.UserImplement;
import java.util.List;

public class NutritionistView {
    
    private AhliGizi ahliGizi;

    public NutritionistView(AhliGizi ahliGizi) {
        this.ahliGizi = ahliGizi;
    }

    public void show(Stage stage) {
        Label titleLabel = new Label("Dashboard Ahli Gizi");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        titleLabel.setTextFill(Color.web("#9C27B0"));

        Label namaLabel = new Label("Halo, " + ahliGizi.getNama());
        namaLabel.setFont(Font.font("Arial", 14));
        namaLabel.setTextFill(Color.web("#666666"));

        Label spesLabel = new Label("Spesialisasi: " + ahliGizi.getSpesialisasi());
        spesLabel.setFont(Font.font("Arial", 13));
        spesLabel.setTextFill(Color.web("#9C27B0"));

        // Tabel semua user
        TableView<User> table = new TableView<>();

        TableColumn<User, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<User, String> colNama = new TableColumn<>("Nama");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colNama.setPrefWidth(150);

        TableColumn<User, String> colUsername = new TableColumn<>("Username");
        colUsername.setCellValueFactory(new PropertyValueFactory<>("username"));
        colUsername.setPrefWidth(120);

        TableColumn<User, Integer> colUsia = new TableColumn<>("Usia");
        colUsia.setCellValueFactory(new PropertyValueFactory<>("usia"));
        colUsia.setPrefWidth(60);

        TableColumn<User, String> colGender = new TableColumn<>("Gender");
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colGender.setPrefWidth(100);

        TableColumn<User, Float> colBerat = new TableColumn<>("Berat (kg)");
        colBerat.setCellValueFactory(new PropertyValueFactory<>("beratKg"));
        colBerat.setPrefWidth(90);

        TableColumn<User, Float> colTinggi = new TableColumn<>("Tinggi (cm)");
        colTinggi.setCellValueFactory(new PropertyValueFactory<>("tinggiCm"));
        colTinggi.setPrefWidth(90);

        TableColumn<User, String> colAktivitas = new TableColumn<>("Aktivitas");
        colAktivitas.setCellValueFactory(new PropertyValueFactory<>("aktivitas"));
        colAktivitas.setPrefWidth(100);

        table.getColumns().addAll(colId, colNama, colUsername, colUsia, colGender, colBerat, colTinggi, colAktivitas);

        try {
            UserImplement ui = new UserImplement();
            List<User> users = ui.getAll();
            table.getItems().addAll(users);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        Button kelolaBtn = new Button("Kelola Makanan & Minuman");
        kelolaBtn.setStyle("-fx-background-color: #7B1FA2; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 13;");
        kelolaBtn.setPrefWidth(200);
        kelolaBtn.setPrefHeight(35);

        kelolaBtn.setOnAction(e -> {
            FoodListView flv = new FoodListView(ahliGizi, stage);
            flv.show();
        });
        
        Button approvalBtn = new Button("Verifikasi Makanan Pending");
        approvalBtn.setStyle("-fx-background-color: #E91E63; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-cursor: hand; -fx-font-size: 13;");
        approvalBtn.setPrefWidth(200);
        approvalBtn.setPrefHeight(35);

        approvalBtn.setOnAction(e -> {
            new NutritionistApprovalView(ahliGizi, stage).show();
        });

        Button logoutBtn = new Button("Logout");
        logoutBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");
        logoutBtn.setOnAction(e -> {
            LoginView lv = new LoginView();
            lv.show(stage);
        });
        
        TableColumn<User, Void> aksiCol = new TableColumn<>("Aksi");
        aksiCol.setPrefWidth(180);
        aksiCol.setCellFactory(col -> new TableCell<>() {
            Button logBtn = new Button("Lihat Log");
            Button catatBtn = new Button("Beri Catatan");
            HBox box = new HBox(5, logBtn, catatBtn);
            {
                logBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; " +
                        "-fx-background-radius: 6; -fx-cursor: hand;");
                catatBtn.setStyle("-fx-background-color: #E91E63; -fx-text-fill: white; " +
                        "-fx-background-radius: 6; -fx-cursor: hand;");

                logBtn.setOnAction(e -> {
                    User u = getTableView().getItems().get(getIndex());
                    NutritionistLogView nlv = new NutritionistLogView(u, ahliGizi, stage);
                    nlv.show();
                });

                catatBtn.setOnAction(e -> {
                    User u = getTableView().getItems().get(getIndex());
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Beri Catatan Gizi");
                    dialog.setHeaderText("Catatan untuk " + u.getNama());
                    dialog.setContentText("Masukkan catatan:");
                    dialog.showAndWait().ifPresent(catatan -> {
                        if (!catatan.isEmpty()) {
                            Alert info = new Alert(Alert.AlertType.INFORMATION);
                            info.setTitle("Catatan Tersimpan");
                            info.setHeaderText(null);
                            info.setContentText("Catatan untuk " + u.getNama() + " berhasil disimpan!");
                            info.showAndWait();
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        table.getColumns().add(aksiCol);

        HBox header = new HBox(10);
        header.setAlignment(Pos.CENTER_LEFT);
        header.getChildren().addAll(titleLabel);

        HBox topBar = new HBox();
        topBar.setAlignment(Pos.CENTER_LEFT);
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        topBar.getChildren().addAll(namaLabel, spacer, logoutBtn);

        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F9F0FF;");
        root.getChildren().addAll(header, spesLabel, topBar, kelolaBtn, approvalBtn, table);

        Scene scene = new Scene(root, 750, 500);
        stage.setTitle("Piring Nadi - Ahli Gizi");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
    
}
