package uasGizi;

import uasGizi.ahliGizi.AhliGizi;
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
import java.util.List;

public class NutritionistApprovalView {
    private AhliGizi ahliGizi;
    private Stage stage;

    public NutritionistApprovalView(AhliGizi ahliGizi, Stage stage) {
        this.ahliGizi = ahliGizi;
        this.stage = stage;
    }

    public void show() {
        Label titleLabel = new Label("Verifikasi Makanan Pending");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#9C27B0"));

        TableView<Makanan> table = new TableView<>();
        table.setPrefHeight(350);

        TableColumn<Makanan, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<Makanan, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("nama"));
        namaCol.setPrefWidth(150);

        TableColumn<Makanan, Float> kaloriCol = new TableColumn<>("Kalori");
        kaloriCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("kalori"));
        kaloriCol.setPrefWidth(70);

        TableColumn<Makanan, Float> proteinCol = new TableColumn<>("Protein");
        proteinCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("protein"));
        proteinCol.setPrefWidth(70);

        TableColumn<Makanan, Float> lemakCol = new TableColumn<>("Lemak");
        lemakCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("lemak"));
        lemakCol.setPrefWidth(70);

        TableColumn<Makanan, Float> karboCol = new TableColumn<>("Karbo");
        karboCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("karbohidrat"));
        karboCol.setPrefWidth(70);

        TableColumn<Makanan, String> olehCol = new TableColumn<>("Diusulkan");
        olehCol.setCellValueFactory(new javafx.scene.control.cell.PropertyValueFactory<>("ditambahOleh"));
        olehCol.setPrefWidth(90);

        TableColumn<Makanan, Void> aksiCol = new TableColumn<>("Aksi");
        aksiCol.setPrefWidth(200);
        aksiCol.setCellFactory(col -> new TableCell<>() {
            Button approveBtn = new Button("Approve");
            Button tolakBtn = new Button("Tolak");
            HBox box = new HBox(5, approveBtn, tolakBtn);
            {
                approveBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                        "-fx-background-radius: 6; -fx-cursor: hand;");
                tolakBtn.setStyle("-fx-background-color: #F44336; -fx-text-fill: white; " +
                        "-fx-background-radius: 6; -fx-cursor: hand;");

                approveBtn.setOnAction(e -> {
                    Makanan m = getTableView().getItems().get(getIndex());

                    // Dialog isi data gizi
                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setTitle("Approve - " + m.getNama());
                    dialog.setHeaderText("Isi/koreksi data gizi per 100g:");

                    TextField kaloriF = new TextField(String.valueOf(m.getKalori()));
                    TextField proteinF = new TextField(String.valueOf(m.getProtein()));
                    TextField lemakF = new TextField(String.valueOf(m.getLemak()));
                    TextField karboF = new TextField(String.valueOf(m.getKarbohidrat()));

                    VBox form = new VBox(8,
                        new Label("Kalori:"), kaloriF,
                        new Label("Protein (g):"), proteinF,
                        new Label("Lemak (g):"), lemakF,
                        new Label("Karbo (g):"), karboF
                    );
                    form.setPadding(new Insets(10));
                    dialog.getDialogPane().setContent(form);
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                    dialog.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                MakananImplement mi = new MakananImplement();
                                mi.approve(m.getId(),
                                    Float.parseFloat(kaloriF.getText()),
                                    Float.parseFloat(proteinF.getText()),
                                    Float.parseFloat(lemakF.getText()),
                                    Float.parseFloat(karboF.getText())
                                );
                                getTableView().getItems().remove(m);
                            } catch (Exception ex) {
                                System.out.println("Error: " + ex.getMessage());
                            }
                        }
                    });
                });

                tolakBtn.setOnAction(e -> {
                    Makanan m = getTableView().getItems().get(getIndex());
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Tolak Makanan");
                    dialog.setHeaderText("Alasan penolakan untuk: " + m.getNama());
                    dialog.setContentText("Alasan:");
                    dialog.showAndWait().ifPresent(alasan -> {
                        if (!alasan.isEmpty()) {
                            try {
                                MakananImplement mi = new MakananImplement();
                                mi.tolak(m.getId(), alasan);
                                getTableView().getItems().remove(m);
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
                setGraphic(empty ? null : box);
            }
        });

        table.getColumns().addAll(idCol, namaCol, kaloriCol, proteinCol, lemakCol, karboCol, olehCol, aksiCol);

        try {
            MakananImplement mi = new MakananImplement();
            List<Makanan> pending = mi.getAllPending();
            table.getItems().addAll(pending);
            if (pending.isEmpty()) {
                titleLabel.setText("Verifikasi Makanan — Tidak ada yang pending");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        Button backBtn = new Button("Kembali");
        backBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");
        backBtn.setOnAction(e -> {
            new NutritionistView(ahliGizi).show(stage);
        });

        VBox root = new VBox(15);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F9F0FF;");
        root.getChildren().addAll(titleLabel, table, backBtn);

        Scene scene = new Scene(root, 800, 550);
        stage.setTitle("Piring Nadi - Verifikasi Makanan");
        stage.setScene(scene);
        stage.setResizable(true);
        stage.show();
    }
}
