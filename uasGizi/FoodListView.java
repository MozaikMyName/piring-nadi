package uasGizi;

import uasGizi.user.User;
import uasGizi.makanan.MakananImplement;
import uasGizi.makanan.Makanan;
import uasGizi.admin.Admin;
import uasGizi.ahliGizi.AhliGizi;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import java.util.List;

public class FoodListView {
    private Stage stage;
    private User user;
    private boolean isAdmin;
    private Admin admin;
    private AhliGizi ahliGizi;
    private boolean isAhliGizi;
    
    private VBox mainNode;
    private Makanan makananSedangDiedit = null;
    
    private ObservableList<Makanan> masterData = FXCollections.observableArrayList();
    private FilteredList<Makanan> filteredData;

    public FoodListView(User user, Stage stage) {
        this.user = user;
        this.stage = stage;
        this.isAdmin = false;
        initComponent();
    }
    
    public FoodListView(Admin admin, Stage stage) {
        this.admin = admin;
        this.stage = stage;
        this.isAdmin = true;
        initComponent();
    }
    
    public FoodListView(AhliGizi ahliGizi, Stage stage) {
        this.ahliGizi = ahliGizi;
        this.stage = stage;
        this.isAhliGizi = true;
        this.isAdmin = false;
        initComponent();
    }
    
    public void initComponent() {
        mainNode = new VBox(20);
        mainNode.setAlignment(Pos.TOP_LEFT); 
        mainNode.setPadding(new Insets(35));
        mainNode.setStyle("-fx-background-color: #FAFAFB;");
        HBox.setHgrow(mainNode, Priority.ALWAYS);
        
        Label titleLabel = new Label("Daftar Manajemen Makanan & Gizi");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 22));
        titleLabel.setTextFill(Color.web("#1E1E2F"));
        
        HBox controlDeck = new HBox();
        controlDeck.setAlignment(Pos.CENTER_LEFT);
        controlDeck.setPadding(new Insets(5, 0, 5, 0));
        
        HBox entriesBox = new HBox(8);
        entriesBox.setAlignment(Pos.CENTER_LEFT);
        Label showLabel = new Label("Tampilkan:");
        showLabel.setStyle("-fx-text-fill: #495057; -fx-font-family: 'Segoe UI';");
        
        ComboBox<Integer> entriesComboBox = new ComboBox<>();
        entriesComboBox.getItems().addAll(5, 10, 15, 20, 50);
        entriesComboBox.setValue(10);
        entriesComboBox.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CED4DA; -fx-border-radius: 4;");
        
        Label entriesLabel = new Label("data");
        entriesLabel.setStyle("-fx-text-fill: #495057; -fx-font-family: 'Segoe UI';");
        entriesBox.getChildren().addAll(showLabel, entriesComboBox, entriesLabel);
        
        Region deckSpacer = new Region();
        HBox.setHgrow(deckSpacer, Priority.ALWAYS);
        
        TextField searchField = new TextField();
        searchField.setPromptText("Cari nama makanan...");
        searchField.setPrefWidth(220);
        searchField.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #CED4DA; -fx-border-radius: 4; -fx-padding: 6 10;");
        
        controlDeck.getChildren().addAll(entriesBox, deckSpacer, searchField);
        
        TextField namaField = new TextField(); namaField.setPromptText("Nama makanan"); namaField.setPrefWidth(160);
        TextField kaloriField = new TextField(); kaloriField.setPromptText("Kalori"); kaloriField.setPrefWidth(80);
        TextField proteinField = new TextField(); proteinField.setPromptText("Protein"); proteinField.setPrefWidth(80);
        TextField lemakField = new TextField(); lemakField.setPromptText("Lemak"); lemakField.setPrefWidth(80);
        TextField karboField = new TextField(); karboField.setPromptText("Karbo"); karboField.setPrefWidth(80);
        
        Button tambahBtn = new Button("Tambah Makanan");
        tambahBtn.setStyle("-fx-background-color: #0D6EFD; -fx-text-fill: white; -fx-background-radius: 4; -fx-font-weight: bold; -fx-padding: 7 15; -fx-cursor: hand;");

        HBox formTambahBox = new HBox(12, namaField, kaloriField, proteinField, lemakField, karboField, tambahBtn);
        formTambahBox.setAlignment(Pos.CENTER_LEFT);
        formTambahBox.setPadding(new Insets(15));
        formTambahBox.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 8; -fx-border-color: #DEE2E6; -fx-border-radius: 8;");
        
        String inputStyle = "-fx-background-color: #FFFFFF; -fx-border-color: #CED4DA; -fx-border-radius: 4; -fx-padding: 6 10; -fx-text-fill: #495057;";
        namaField.setStyle(inputStyle); kaloriField.setStyle(inputStyle); proteinField.setStyle(inputStyle);
        lemakField.setStyle(inputStyle); karboField.setStyle(inputStyle);

        TextField editNamaField = new TextField(); editNamaField.setPromptText("Nama makanan"); editNamaField.setPrefWidth(160);
        TextField editKaloriField = new TextField(); editKaloriField.setPromptText("Kalori"); editKaloriField.setPrefWidth(80);
        TextField editProteinField = new TextField(); editProteinField.setPromptText("Protein"); editProteinField.setPrefWidth(80);
        TextField editLemakField = new TextField(); editLemakField.setPromptText("Lemak"); editLemakField.setPrefWidth(80);
        TextField editKarboField = new TextField(); editKarboField.setPromptText("Karbo"); editKarboField.setPrefWidth(80);
        
        Button updateBtn = new Button("Update Makanan");
        updateBtn.setStyle("-fx-background-color: #2196F3; -fx-text-fill: white; -fx-background-radius: 4; -fx-font-weight: bold; -fx-padding: 7 15; -fx-cursor: hand;");
        
        Button batalBtn = new Button("Batal");
        batalBtn.setStyle("-fx-background-color: #6C757D; -fx-text-fill: white; -fx-background-radius: 4; -fx-font-weight: bold; -fx-padding: 7 15; -fx-cursor: hand;");

        HBox formEditBox = new HBox(12, editNamaField, editKaloriField, editProteinField, editLemakField, editKarboField, updateBtn, batalBtn);
        formEditBox.setAlignment(Pos.CENTER_LEFT);
        formEditBox.setPadding(new Insets(15));
        formEditBox.setStyle("-fx-background-color: #FFF3CD; -fx-background-radius: 8; -fx-border-color: #FFEBAA; -fx-border-radius: 8;");
        
        editNamaField.setStyle(inputStyle); editKaloriField.setStyle(inputStyle); editProteinField.setStyle(inputStyle);
        editLemakField.setStyle(inputStyle); editKarboField.setStyle(inputStyle);
        
        formEditBox.setVisible(false);
        formEditBox.setManaged(false);

        TableView<Makanan> table = new TableView<>();
        table.setPrefHeight(380);
        VBox.setVgrow(table, Priority.ALWAYS);
        table.setStyle(
            "-fx-background-color: transparent; " +
            "-fx-background-insets: 0; " +
            "-fx-border-color: #DEE2E6; " + 
            "-fx-border-width: 1 0 1 0; " + 
            "-fx-table-cell-border-color: #DEE2E6; " + 
            "-fx-table-header-border-color: transparent; "
        );

        TableColumn<Makanan, Makanan> noCol = new TableColumn<>("#");
        noCol.setPrefWidth(50);
        noCol.setResizable(false);
        noCol.setCellValueFactory(features -> new javafx.beans.property.SimpleObjectProperty<>(features.getValue()));
        noCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Makanan item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                    setStyle("");
                } else {
                    int nomorUrut = getIndex() + 1;
                    setText(String.valueOf(nomorUrut));
                    setStyle("-fx-alignment: CENTER; -fx-text-fill: #6C757D; -fx-padding: 12 8;");
                }
            }
        });

        TableColumn<Makanan, String> namaCol = new TableColumn<>("Nama Makanan");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        namaCol.setPrefWidth(220);
        namaCol.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    setStyle("-fx-alignment: CENTER_LEFT; -fx-font-weight: bold; -fx-text-fill: #212529; -fx-padding: 12 8;");
                }
            }
        });

        TableColumn<Makanan, Float> kaloriCol = new TableColumn<>("Kalori (kcal)");
        kaloriCol.setCellValueFactory(new PropertyValueFactory<>("kalori"));
        kaloriCol.setPrefWidth(110);
        setupNumericColumnStyle(kaloriCol);

        TableColumn<Makanan, Float> proteinCol = new TableColumn<>("Protein (Gram)");
        proteinCol.setCellValueFactory(new PropertyValueFactory<>("protein"));
        proteinCol.setPrefWidth(100);
        setupNumericColumnStyle(proteinCol);

        TableColumn<Makanan, Float> lemakCol = new TableColumn<>("Lemak (Gram)");
        lemakCol.setCellValueFactory(new PropertyValueFactory<>("lemak"));
        lemakCol.setPrefWidth(100);
        setupNumericColumnStyle(lemakCol);

        TableColumn<Makanan, Float> karboCol = new TableColumn<>("Karbohidrat (Gram)");
        karboCol.setCellValueFactory(new PropertyValueFactory<>("karbohidrat"));
        karboCol.setPrefWidth(110);
        setupNumericColumnStyle(karboCol);
        
        TableColumn<Makanan, Makanan> aksiCol = new TableColumn<>("Aksi");
        aksiCol.setPrefWidth(160);
        aksiCol.setCellValueFactory(features -> new javafx.beans.property.SimpleObjectProperty<>(features.getValue()));
        aksiCol.setCellFactory(col -> new TableCell<>() {
            private final Button editBtn = new Button("Edit");
            private final Button deleteBtn = new Button("Hapus");
            private final HBox box = new HBox(8, editBtn, deleteBtn);

            {
                box.setAlignment(Pos.CENTER);
                editBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #FFC107; -fx-border-color: #FFC107; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 11; -fx-cursor: hand; -fx-padding: 4 10;");
                deleteBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #DC3545; -fx-border-color: #DC3545; -fx-border-radius: 4; -fx-background-radius: 4; -fx-font-size: 11; -fx-cursor: hand; -fx-padding: 4 10;");

                deleteBtn.setOnAction(e -> {
                    Makanan m = getTableRow().getItem();
                    if (m != null) {
                        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                        confirm.setTitle("Konfirmasi");
                        confirm.setHeaderText("Hapus makanan ini dari sistem?");
                        confirm.setContentText("Nama: " + m.getNama());
                        confirm.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                try {
                                    MakananImplement mi = new MakananImplement();
                                    mi.delete(m.getId());
                                    masterData.remove(m);
                                    applyFilters(entriesComboBox.getValue(), searchField.getText(), table);
                                    
                                    if (makananSedangDiedit != null && makananSedangDiedit.getId() == m.getId()) {
                                        makananSedangDiedit = null;
                                        formEditBox.setVisible(false);
                                        formEditBox.setManaged(false);
                                    }
                                } catch (Exception ex) {
                                    ex.printStackTrace();
                                }
                            }
                        });
                    }
                });

                editBtn.setOnAction(e -> {
                    Makanan m = getTableRow().getItem(); 
                    if (m != null) {
                        makananSedangDiedit = m;
                        
                        editNamaField.setText(makananSedangDiedit.getNama());
                        editKaloriField.setText(String.valueOf(makananSedangDiedit.getKalori()));
                        editProteinField.setText(String.valueOf(makananSedangDiedit.getProtein()));
                        editLemakField.setText(String.valueOf(makananSedangDiedit.getLemak()));
                        editKarboField.setText(String.valueOf(makananSedangDiedit.getKarbohidrat()));

                        formEditBox.setVisible(true);
                        formEditBox.setManaged(true);
                        editNamaField.requestFocus();
                    }
                });
            }

            @Override
            protected void updateItem(Makanan item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                    setStyle("");
                } else {
                    setGraphic(box); 
                    setStyle("-fx-padding: 8 4; -fx-alignment: CENTER;");
                }
            }
        });
        
        table.getColumns().addAll(noCol, namaCol, kaloriCol, proteinCol, lemakCol, karboCol, aksiCol);
        
        loadDataFromDatabase();
        filteredData = new FilteredList<>(masterData, p -> true);
        
        applyFilters(entriesComboBox.getValue(), searchField.getText(), table);

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters(entriesComboBox.getValue(), newValue, table);
        });
        entriesComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            applyFilters(newValue, searchField.getText(), table);
        });
        
        Label msgLabel = new Label("");
        msgLabel.setFont(Font.font("Segoe UI", 12));

        tambahBtn.setOnAction(e -> {
            try {
                if (namaField.getText().trim().isEmpty()) {
                    throw new Exception("Nama makanan tidak boleh kosong");
                }
                MakananImplement mi = new MakananImplement();
                Makanan m = new Makanan();
                m.setNama(namaField.getText());
                m.setKalori(Float.parseFloat(kaloriField.getText().replace(",", ".")));
                m.setProtein(Float.parseFloat(proteinField.getText().replace(",", ".")));
                m.setLemak(Float.parseFloat(lemakField.getText().replace(",", ".")));
                m.setKarbohidrat(Float.parseFloat(karboField.getText().replace(",", ".")));
                m.setStatus("approved");
                m.setDitambahOleh(isAdmin ? "admin" : "ahli_gizi");
                m.setUserId(0);
                
                mi.insert(m);
                msgLabel.setTextFill(Color.GREEN);
                msgLabel.setText("Makanan berhasil ditambahkan!");

                loadDataFromDatabase();
                applyFilters(entriesComboBox.getValue(), searchField.getText(), table);
                
                namaField.clear(); kaloriField.clear(); 
                proteinField.clear(); lemakField.clear(); karboField.clear();

            } catch (Exception ex) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Format input salah: " + ex.getMessage());
            }
        });
        
        updateBtn.setOnAction(e -> {
            try {
                if (makananSedangDiedit != null) {
                    MakananImplement mi = new MakananImplement();

                    makananSedangDiedit.setNama(editNamaField.getText());
                    makananSedangDiedit.setKalori(Float.parseFloat(editKaloriField.getText().trim().replace(",", ".")));
                    makananSedangDiedit.setProtein(Float.parseFloat(editProteinField.getText().trim().replace(",", ".")));
                    makananSedangDiedit.setLemak(Float.parseFloat(editLemakField.getText().trim().replace(",", ".")));
                    makananSedangDiedit.setKarbohidrat(Float.parseFloat(editKarboField.getText().trim().replace(",", ".")));

                    if (makananSedangDiedit.getStatus() == null || makananSedangDiedit.getStatus().isEmpty()) {
                        makananSedangDiedit.setStatus("approved");
                    }
                    if (makananSedangDiedit.getDitambahOleh() == null || makananSedangDiedit.getDitambahOleh().isEmpty()) {
                        makananSedangDiedit.setDitambahOleh(isAdmin ? "admin" : "ahli_gizi");
                    }

                    mi.update(makananSedangDiedit);

                    msgLabel.setTextFill(Color.BLUE);
                    msgLabel.setText("Makanan berhasil diperbarui!");

                    makananSedangDiedit = null;
                    formEditBox.setVisible(false);
                    formEditBox.setManaged(false);

                    loadDataFromDatabase();
                    applyFilters(entriesComboBox.getValue(), searchField.getText(), table);
                }
            } catch (Exception ex) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Gagal memperbarui: " + ex.getMessage());
            }
        });


        batalBtn.setOnAction(e -> {
            makananSedangDiedit = null;
            formEditBox.setVisible(false);
            formEditBox.setManaged(false);
            msgLabel.setText("");
        });
        
        mainNode.getChildren().addAll(titleLabel, formTambahBox, controlDeck, table, formEditBox, msgLabel);
        
        table.setRowFactory(tv -> {
            TableRow<Makanan> row = new TableRow<>();
            row.styleProperty().bind(javafx.beans.binding.Bindings.when(row.hoverProperty())
                .then("-fx-background-color: #F8F9FA;") 
                .otherwise("-fx-background-color: transparent;"));
            return row;
        });

        mainNode.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                table.lookupAll(".column-header").forEach(node -> {
                    node.setStyle("-fx-background-color: #F8F9FA; -fx-border-color: #DEE2E6; -fx-border-width: 0 0 2 0; -fx-padding: 10 4;");
                    Label label = (Label) node.lookup(".label");
                    if (label != null) {
                        label.setStyle("-fx-text-fill: #495057; -fx-font-weight: bold; -fx-font-family: 'Segoe UI';");
                    }
                });
                table.lookupAll(".column-header-background").forEach(node -> node.setStyle("-fx-background-color: #F8F9FA;"));
            }
        });
    }
    
    private void loadDataFromDatabase() {
        try {
            masterData.clear();
            MakananImplement mi = new MakananImplement();
            List<Makanan> list = mi.getAll();
            masterData.addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void applyFilters(int maxEntries, String searchText, TableView<Makanan> table) {
        filteredData.setPredicate(makanan -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            String lowerCaseFilter = searchText.toLowerCase();
            return makanan.getNama() != null && makanan.getNama().toLowerCase().contains(lowerCaseFilter);
        });

        ObservableList<Makanan> limitedList = FXCollections.observableArrayList();
        int limit = Math.min(filteredData.size(), maxEntries);
        for (int i = 0; i < limit; i++) {
            limitedList.add(filteredData.get(i));
        }
        table.setItems(limitedList);
    }
    
    public Node getViewNode() {
        return this.mainNode;
    }
    
    private void setupNumericColumnStyle(TableColumn<Makanan, Float> column) {
        column.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Float item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(String.format("%.1f", item));
                    setStyle("-fx-alignment: CENTER_RIGHT; -fx-text-fill: #495057; -fx-padding: 12 8;");
                }
            }
        });
    }
}