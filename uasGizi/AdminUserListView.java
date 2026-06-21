package uasGizi;

import java.util.ArrayList;
import uasGizi.admin.Admin;
import uasGizi.user.User;
import uasGizi.user.UserImplement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import java.util.List;
import javafx.scene.Node;

public class AdminUserListView {
    private Admin admin;
    private Stage stage;
    private VBox root;
    
    private TableView<User> table;
    private TextField searchField;
    private ComboBox<Integer> entriesComboBox;
    private List<User> allUsersList = new ArrayList<>();

    public AdminUserListView(Admin admin, Stage stage) {
        this.admin = admin;
        this.stage = stage;
        initComponent();
    }
    
    public Node getViewNode() {
        return this.root; 
    }

    private void initComponent() {
        Label titleLabel = new Label("Daftar User Terdaftar");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.web("#2c3e50"));
        
        HBox controlDeck = new HBox();
        controlDeck.setAlignment(Pos.CENTER_LEFT);
        
        HBox leftControl = new HBox(8);
        leftControl.setAlignment(Pos.CENTER_LEFT);
        Label showLabel = new Label("Tampilkan:");
        showLabel.setFont(Font.font("Segoe UI", 12));
        
        entriesComboBox = new ComboBox<>();
        entriesComboBox.getItems().addAll(5, 10, 15, 25, 50);
        entriesComboBox.setValue(10);
        entriesComboBox.setStyle("-fx-background-radius: 4; -fx-border-radius: 4;");
        
        Label dataLabel = new Label("data");
        dataLabel.setFont(Font.font("Segoe UI", 12));
        leftControl.getChildren().addAll(showLabel, entriesComboBox, dataLabel);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        searchField = new TextField();
        searchField.setPromptText("Cari nama atau username...");
        searchField.setPrefWidth(250);
        searchField.setStyle(
            "-fx-background-color: #ffffff; " +
            "-fx-border-color: #cccccc; " +
            "-fx-border-radius: 4; " +
            "-fx-background-radius: 4; " +
            "-fx-padding: 6 10 6 10;"
        );

        controlDeck.getChildren().addAll(leftControl, spacer, searchField);

        table = new TableView<>(); 
        table.setPrefHeight(400);
        VBox.setVgrow(table, Priority.ALWAYS);
        table.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-border-color: #e0e0e0; " +
            "-fx-border-width: 1; " +
            "-fx-border-radius: 4; " +
            "-fx-background-radius: 4;"
        );

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
        
        TableColumn<User, Void> deleteCol = new TableColumn<>("Aksi");
        deleteCol.setPrefWidth(100);
        deleteCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button("Hapus");
            {
                deleteBtn.setStyle(
                    "-fx-background-color: #ffffff; " +
                    "-fx-text-fill: #E53935; " +
                    "-fx-border-color: #E53935; " +
                    "-fx-border-width: 1; " +
                    "-fx-border-radius: 4; " +
                    "-fx-background-radius: 4; " +
                    "-fx-cursor: hand; " +
                    "-fx-font-size: 11px; " +
                    "-fx-padding: 3 8 3 8;"
                );
                
                deleteBtn.setOnMouseEntered(e -> deleteBtn.setStyle(
                    "-fx-background-color: #E53935; " +
                    "-fx-text-fill: #ffffff; " +
                    "-fx-border-color: #E53935; " +
                    "-fx-border-width: 1; " +
                    "-fx-border-radius: 4; " +
                    "-fx-background-radius: 4; " +
                    "-fx-cursor: hand; " +
                    "-fx-font-size: 11px; " +
                    "-fx-padding: 3 8 3 8;"
                ));
                
                deleteBtn.setOnMouseExited(e -> deleteBtn.setStyle(
                    "-fx-background-color: #ffffff; " +
                    "-fx-text-fill: #E53935; " +
                    "-fx-border-color: #E53935; " +
                    "-fx-border-width: 1; " +
                    "-fx-border-radius: 4; " +
                    "-fx-background-radius: 4; " +
                    "-fx-cursor: hand; " +
                    "-fx-font-size: 11px; " +
                    "-fx-padding: 3 8 3 8;"
                ));

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
                                
                                allUsersList.remove(u);
                                loadData(); 
                            } catch (Exception ex) {
                                System.out.println("Error saat menghapus: " + ex.getMessage());
                            }
                        }
                    });
                });
            }
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox container = new HBox(deleteBtn);
                    container.setAlignment(Pos.CENTER);
                    setGraphic(container);
                }
            }
        });

        table.getColumns().addAll(idCol, namaCol, usernameCol, usiaCol,
                genderCol, beratCol, tinggiCol, aktivitasCol, deleteCol);

        try {
            UserImplement ui = new UserImplement();
            allUsersList = ui.getAll(); 
            loadData();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            loadData();
        });

        entriesComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            loadData();
        });

        root = new VBox(20);
        root.setPadding(new Insets(35));
        root.setAlignment(Pos.TOP_LEFT); 
        root.setStyle("-fx-background-color: #FAFAFB;"); 
        
        root.getChildren().addAll(titleLabel, controlDeck, table);
    }
    
    private void loadData() {
        String keyword = searchField.getText().trim().toLowerCase();
        int limit = entriesComboBox.getValue();
        
        table.getItems().clear();
        int count = 0;
        
        for (User u : allUsersList) {
            if (count >= limit) {
                break; 
            }
            
            boolean matchesNama = u.getNama() != null && u.getNama().toLowerCase().contains(keyword);
            boolean matchesUsername = u.getUsername() != null && u.getUsername().toLowerCase().contains(keyword);
            
            if (keyword.isEmpty() || matchesNama || matchesUsername) {
                table.getItems().add(u);
                count++;
            }
        }
    }
}