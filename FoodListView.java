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
import java.util.List;

public class FoodListView {
    private Stage stage;
    private User user;
    private boolean isAdmin;
    private Admin admin;

    public FoodListView(User user, Stage stage) {
        this.user = user;
        this.stage = stage;
        this.isAdmin = false;
    }
    
    public FoodListView(Admin admin, Stage stage) {
        this.admin = admin;
        this.stage = stage;
        this.isAdmin = true;
    }

    public void show() {
        Label titleLabel = new Label("Daftar Makanan");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#2E7D32"));

        TableView<Makanan> table = new TableView<>();
        table.setPrefHeight(350);

        TableColumn<Makanan, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        idCol.setPrefWidth(50);

        TableColumn<Makanan, String> namaCol = new TableColumn<>("Nama");
        namaCol.setCellValueFactory(new PropertyValueFactory<>("nama"));
        namaCol.setPrefWidth(180);

        TableColumn<Makanan, Float> kaloriCol = new TableColumn<>("Kalori");
        kaloriCol.setCellValueFactory(new PropertyValueFactory<>("kalori"));
        kaloriCol.setPrefWidth(80);

        TableColumn<Makanan, Float> proteinCol = new TableColumn<>("Protein (g)");
        proteinCol.setCellValueFactory(new PropertyValueFactory<>("protein"));
        proteinCol.setPrefWidth(90);

        TableColumn<Makanan, Float> lemakCol = new TableColumn<>("Lemak (g)");
        lemakCol.setCellValueFactory(new PropertyValueFactory<>("lemak"));
        lemakCol.setPrefWidth(90);

        TableColumn<Makanan, Float> karboCol = new TableColumn<>("Karbo (g)");
        karboCol.setCellValueFactory(new PropertyValueFactory<>("karbohidrat"));
        karboCol.setPrefWidth(90);

        table.getColumns().addAll(idCol, namaCol, kaloriCol, proteinCol, lemakCol, karboCol);

        try {
            MakananImplement mi = new MakananImplement();
            List<Makanan> list = mi.getAll();
            table.getItems().addAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }

        TextField namaField = new TextField();
        namaField.setPromptText("Nama makanan");
        namaField.setPrefWidth(150);

        TextField kaloriField = new TextField();
        kaloriField.setPromptText("Kalori");
        kaloriField.setPrefWidth(80);

        TextField proteinField = new TextField();
        proteinField.setPromptText("Protein");
        proteinField.setPrefWidth(80);

        TextField lemakField = new TextField();
        lemakField.setPromptText("Lemak");
        lemakField.setPrefWidth(80);

        TextField karboField = new TextField();
        karboField.setPromptText("Karbo");
        karboField.setPrefWidth(80);

        Button tambahBtn = new Button("Tambah");
        tambahBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-cursor: hand;");

        Label msgLabel = new Label("");
        msgLabel.setTextFill(Color.web("#4CAF50"));

        tambahBtn.setOnAction(e -> {
            try {
                Makanan m = new Makanan();
                m.setNama(namaField.getText());
                m.setKalori(Float.parseFloat(kaloriField.getText()));
                m.setProtein(Float.parseFloat(proteinField.getText()));
                m.setLemak(Float.parseFloat(lemakField.getText()));
                m.setKarbohidrat(Float.parseFloat(karboField.getText()));

                MakananImplement mi = new MakananImplement();
                mi.insert(m);

                table.getItems().clear();
                table.getItems().addAll(mi.getAll());

                namaField.clear(); kaloriField.clear();
                proteinField.clear(); lemakField.clear(); karboField.clear();

                msgLabel.setText("Makanan berhasil ditambahkan!");
            } catch (Exception ex) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Error: " + ex.getMessage());
            }
        });

        HBox formBox = new HBox(8, namaField, kaloriField, proteinField,
                lemakField, karboField, tambahBtn);
        formBox.setAlignment(Pos.CENTER);

        Button backBtn = new Button("Kembali ke Dashboard");
        backBtn.setPrefWidth(250);
        backBtn.setPrefHeight(40);
        backBtn.setStyle("-fx-background-color: #9E9E9E; -fx-text-fill: white; " +
                "-fx-background-radius: 8; -fx-font-size: 13; -fx-cursor: hand;");

        backBtn.setOnAction(e -> {
            if (isAdmin) {
                AdminDashboardView adv = new AdminDashboardView(admin);
                adv.show(stage);
            } else {
                DashboardView dv = new DashboardView(user);
                dv.show(stage);
            }
        });

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.setStyle("-fx-background-color: #F0F4F0;");
        root.getChildren().addAll(titleLabel, table, formBox, msgLabel, backBtn);

        Scene scene = new Scene(root, 750, 550);
        stage.setTitle("NutriTrack - Daftar Makanan");
        stage.setScene(scene);
        stage.show();
    }
}
