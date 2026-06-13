package uasGizi;

import uasGizi.admin.Admin;
import uasGizi.user.User;
import uasGizi.user.UserImplement;
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

public class AdminUserListView {
    private Admin admin;
    private Stage stage;

    public AdminUserListView(Admin admin, Stage stage) {
        this.admin = admin;
        this.stage = stage;
    }

    public void show() {
        Label titleLabel = new Label("Daftar User Terdaftar");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.web("#42A5F5"));

        TableView<User> table = new TableView<>();
        table.setPrefHeight(400);

        TableColumn<User, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<User, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        namaCol.setPrefWidth(150);

        TableColumn<User, String> usernameCol = new TableColumn<>("Username");
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        usernameCol.setPrefWidth(120);

        TableColumn<User, Integer> usiaCol = new TableColumn<>("Usia");
        usiaCol.setCellValueFactory(new PropertyValueFactory<>("usia"));
        usiaCol.setPrefWidth(60);

        TableColumn<User, String> genderCol = new TableColumn<>("Gender");
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        genderCol.setPrefWidth(100);

        TableColumn<User, Float> beratCol = new TableColumn<>("Berat (kg)");
        beratCol.setCellValueFactory(new PropertyValueFactory<>("beratKg"));
        beratCol.setPrefWidth(90);

        TableColumn<User, Float> tinggiCol = new TableColumn<>("Tinggi (cm)");
        tinggiCol.setCellValueFactory(new PropertyValueFactory<>("tinggiCm"));
        tinggiCol.setPrefWidth(90);

        TableColumn<User, String> aktivitasCol = new TableColumn<>("Aktivitas");
        aktivitasCol.setCellValueFactory(new PropertyValueFactory<>("aktivitas"));
        aktivitasCol.setPrefWidth(100);

        table.getColumns().addAll(idCol, namaCol, usernameCol, usiaCol,
                genderCol, beratCol, tinggiCol, aktivitasCol);

        try {
            UserImplement ui = new UserImplement();
            List<User> users = ui.getAll();
            table.getItems().addAll(users);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        Button backBtn = new Button("Kembali ke Dashboard");
        backBtn.setPrefWidth(250);
        backBtn.setPrefHeight(40);
        backBtn.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");

        backBtn.setOnAction(e -> {
            new AdminDashboardView(admin).show(stage);
        });
        
        TableColumn<User, Void> deleteCol = new TableColumn<>("Aksi");
        deleteCol.setPrefWidth(80);
        deleteCol.setCellFactory(col -> new TableCell<>() {
            Button deleteBtn = new Button("Hapus");
            {
                deleteBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; " +
                        "-fx-background-radius: 6; -fx-cursor: hand;");
                deleteBtn.setOnAction(e -> {
                    User u = getTableView().getItems().get(getIndex());
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                    confirm.setTitle("Konfirmasi");
                    confirm.setHeaderText("Hapus user " + u.getNama() + "?");
                    confirm.setContentText("Data tidak bisa dikembalikan!");
                    confirm.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                UserImplement ui = new UserImplement();
                                ui.delete(u.getId());
                                getTableView().getItems().remove(u);
                            } catch (Exception ex) {
                                System.out.println("Error: " + ex.getMessage());
                            }
                        }
                    });
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });

        table.getColumns().add(deleteCol);

        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #FFF0F5;");
        root.getChildren().addAll(titleLabel, table, backBtn);

        Scene scene = new Scene(root, 900, 650);
        stage.setTitle("Piring Nadi - Daftar User");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.setMinWidth(850);
        stage.setMinHeight(650);
        stage.show();
    }
}