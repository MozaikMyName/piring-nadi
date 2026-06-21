package uasGizi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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
import java.util.concurrent.atomic.AtomicInteger;
import java.sql.SQLException;

public class NutritionistView {
    
    private AhliGizi ahliGizi;
    private BorderPane mainLayout;
    private Stage stage;
    
    private ObservableList<User> masterDataPasien = FXCollections.observableArrayList();

    public NutritionistView(AhliGizi ahliGizi) {
        this.ahliGizi = ahliGizi;
        this.mainLayout = new BorderPane();
    }

    public void show(Stage stage) {
        this.stage = stage;
        
        Scene currentScene = stage.getScene();
        if (currentScene == null) {
            currentScene = new Scene(mainLayout, 1100, 700);
            stage.setScene(currentScene);
        } else {
            currentScene.setRoot(mainLayout);
        }
        
        mainLayout.setLeft(createSidebar());
        mainLayout.setCenter(createDashboardHomeContent());

        stage.setTitle("Piring Nadi - Dashboard Ahli Gizi");
        stage.setResizable(true);
        stage.setMinWidth(1100);
        stage.setMinHeight(700);
        stage.show();
    }
    
    private VBox createSidebar() {
        VBox sidebar = new VBox(12);
        sidebar.setPadding(new Insets(35, 18, 35, 18));
        sidebar.setPrefWidth(260);
        sidebar.setStyle("-fx-background-color: #FDF8FF; " +
                         "-fx-border-color: #EFE5F5; " +
                         "-fx-border-width: 0 1 0 0;");
        
        Label logoLabel = new Label("Piring Nadi");
        logoLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
        logoLabel.setTextFill(Color.web("#9C27B0"));
        
        Label roleLabel = new Label("Halo, " + ahliGizi.getNama() + " (Ahli Gizi)");
        roleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
        roleLabel.setTextFill(Color.web("#B2A2B2"));
        
        Label spesLabel = new Label("Spesialisasi: " + ahliGizi.getSpesialisasi());
        spesLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 15));
        spesLabel.setTextFill(Color.web("#9C27B0"));
        spesLabel.setPadding(new Insets(-5, 0, 30, 0));

        VBox logoContainer = new VBox(5, logoLabel, roleLabel, spesLabel);
        logoContainer.setPadding(new Insets(0, 0, 10, 8));
        
        Button menuDashboard = createSidebarButton("Daftar Pasien/User", "#9C27B0");
        Button menuFood = createSidebarButton("Kelola Makanan", "#7B1FA2");
        Button menuApproval = createSidebarButton("Verifikasi Makanan", "#E91E63");
        Button menuProfile = createSidebarButton("Edit Profil", "#9C27B0");
        Button menuLogout = createSidebarButton("Logout", "#7F8C8D");
        
        menuDashboard.setOnAction(e -> mainLayout.setCenter(createDashboardHomeContent()));
        
        menuFood.setOnAction(e -> {
            FoodListView flv = new FoodListView(ahliGizi, stage);
            mainLayout.setCenter(flv.getViewNode()); 
        });
        
        menuApproval.setOnAction(e -> {
            NutritionistApprovalView nav = new NutritionistApprovalView(ahliGizi, stage);
            mainLayout.setCenter(nav.getViewNode());
        }); 

        menuLogout.setOnAction(e -> {
            LoginView lv = new LoginView();
            lv.show(stage);
        });

        menuProfile.setOnAction(e -> {
            try {
                AdminProfileView profileView = new AdminProfileView(this.ahliGizi, this.stage);
                mainLayout.setCenter(profileView); 
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        sidebar.getChildren().addAll(logoContainer, menuDashboard, menuFood, menuApproval, menuProfile, spacer, menuLogout);
        return sidebar;
    }
    
    private VBox createDashboardHomeContent() {
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(45));
        mainContent.setStyle("-fx-background-color: #FFFFFF;");
        HBox.setHgrow(mainContent, Priority.ALWAYS);
        VBox.setVgrow(mainContent, Priority.ALWAYS);

        Label titleLabel = new Label("Daftar Pasien Terdaftar");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        titleLabel.setTextFill(Color.web("#9C27B0"));
        
        Label tampilkanLabel = new Label("Tampilkan:");
        tampilkanLabel.setFont(Font.font("Segoe UI", 14));
        
        ComboBox<Integer> limitComboBox = new ComboBox<>();
        limitComboBox.getItems().addAll(5, 10, 25, 50, 100);
        limitComboBox.setValue(10); 
        limitComboBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #DCDCDC; -fx-border-radius: 4;");
        
        Label dataLabel = new Label("data");
        dataLabel.setFont(Font.font("Segoe UI", 14));

        TextField searchField = new TextField();
        searchField.setPromptText("Cari nama makanan...");
        searchField.setPrefWidth(220);
        searchField.setStyle("-fx-padding: 6 10 6 10; -fx-background-radius: 4; -fx-border-color: #DCDCDC; -fx-border-radius: 4;");

        Region topSpacer = new Region();
        HBox.setHgrow(topSpacer, Priority.ALWAYS);

        HBox topBar = new HBox(10, tampilkanLabel, limitComboBox, dataLabel, topSpacer, searchField);
        topBar.setAlignment(Pos.CENTER_LEFT);
        topBar.setPadding(new Insets(10, 0, 10, 0));

        TableView<User> table = new TableView<>();
        table.setPrefHeight(450);
        VBox.setVgrow(table, Priority.ALWAYS);

        TableColumn<User, Integer> colId = new TableColumn<>("#");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colId.setPrefWidth(50);

        TableColumn<User, String> colNama = new TableColumn<>("Nama");
        colNama.setCellValueFactory(new PropertyValueFactory<>("nama"));
        colNama.setPrefWidth(180);

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
        colBerat.setPrefWidth(100);

        TableColumn<User, Float> colTinggi = new TableColumn<>("Tinggi (cm)");
        colTinggi.setCellValueFactory(new PropertyValueFactory<>("tinggiCm"));
        colTinggi.setPrefWidth(100);

        TableColumn<User, String> colAktivitas = new TableColumn<>("Aktivitas");
        colAktivitas.setCellValueFactory(new PropertyValueFactory<>("aktivitas"));
        colAktivitas.setPrefWidth(120);

        TableColumn<User, Void> aksiCol = new TableColumn<>("Aksi");
        aksiCol.setPrefWidth(200);
        aksiCol.setCellFactory(col -> new TableCell<>() {
            private final Button logBtn = new Button("Lihat Log");
            private final Button catatBtn = new Button("Beri Catatan");
            private final HBox box = new HBox(8, logBtn, catatBtn);
            {
                logBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; -fx-background-radius: 4; -fx-cursor: hand;");
                catatBtn.setStyle("-fx-background-color: #E91E63; -fx-text-fill: white; -fx-background-radius: 4; -fx-cursor: hand;");

                logBtn.setOnAction(e -> {
                    User u = getTableView().getItems().get(getIndex());
                    NutritionistLogView nlv = new NutritionistLogView(u, ahliGizi, () -> {
                        mainLayout.setCenter(createDashboardHomeContent());
                    });
                    mainLayout.setCenter(nlv.getViewNode());
                });

                catatBtn.setOnAction(e -> {
                    User u = getTableView().getItems().get(getIndex());
                    TextInputDialog dialog = new TextInputDialog();
                    dialog.setTitle("Beri Catatan Gizi");
                    dialog.setHeaderText("Catatan untuk " + u.getNama());
                    dialog.setContentText("Masukkan catatan:");

                    dialog.showAndWait().ifPresent(catatan -> {
                        if (!catatan.isEmpty()) {
                            try {
                                UserImplement ui = new UserImplement();
                                ui.updateCatatanGizi(u.getId(), catatan); 

                                Alert info = new Alert(Alert.AlertType.INFORMATION);
                                info.setContentText("Catatan berhasil disimpan ke profil " + u.getNama());
                                info.showAndWait();
                            } catch (SQLException ex) {
                                ex.printStackTrace();
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
        
        table.getColumns().addAll(colId, colNama, colUsername, colUsia, colGender, colBerat, colTinggi, colAktivitas, aksiCol);
        
        try {
            UserImplement ui = new UserImplement();
            List<User> users = ui.getAll();
            masterDataPasien.setAll(users); 
        } catch (Exception e) {
            System.out.println("Error memuat pasien: " + e.getMessage());
        }
        
        FilteredList<User> filteredData = new FilteredList<>(masterDataPasien, p -> true);
        
        Runnable updateFilterAndLimit = () -> {
            String keyword = searchField.getText() == null ? "" : searchField.getText().toLowerCase().trim();
            int limit = limitComboBox.getValue();
            
            AtomicInteger count = new AtomicInteger(0);
            
            filteredData.setPredicate(pasien -> {
                boolean matchesSearch = true;
                if (!keyword.isEmpty()) {
                    boolean namaMatch = pasien.getNama().toLowerCase().contains(keyword);
                    boolean userMatch = pasien.getUsername().toLowerCase().contains(keyword);
                    matchesSearch = namaMatch || userMatch;
                }
                
                if (matchesSearch) {
                    return count.getAndIncrement() < limit;
                }
                
                return false;
            });
        };

        searchField.textProperty().addListener((obs, oldVal, newVal) -> updateFilterAndLimit.run());
        limitComboBox.valueProperty().addListener((obs, oldVal, newVal) -> updateFilterAndLimit.run());

        updateFilterAndLimit.run();

        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedData);

        mainContent.getChildren().addAll(titleLabel, topBar, table);
        return mainContent;
    }
    
    private Button createSidebarButton(String text, String activeColorHex) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPrefHeight(48);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(0, 0, 0, 20));
        btn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));

        String defaultStyle = "-fx-background-color: #F5F0F8; " +
                             "-fx-text-fill: #4A3B50; " +
                             "-fx-background-radius: 10; " +
                             "-fx-cursor: hand;";
        
        String hoverStyle = "-fx-background-color: " + activeColorHex + "; " +
                           "-fx-text-fill: #FFFFFF; " +
                           "-fx-background-radius: 10; " +
                           "-fx-cursor: hand; " +
                           "-fx-effect: dropshadow(gaussian, " + activeColorHex + "4D, 8, 0, 0, 4);";

        btn.setStyle(defaultStyle);
        btn.setOnMouseEntered(e -> btn.setStyle(hoverStyle));
        btn.setOnMouseExited(e -> btn.setStyle(defaultStyle));
        
        return btn;
    }
}