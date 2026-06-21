package uasGizi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import uasGizi.ahliGizi.AhliGizi;
import uasGizi.ahliGizi.AhliGiziImplement;
import javafx.stage.Stage;
import uasGizi.admin.Admin;

public class AdminProfileView extends VBox {
    
    private Admin admin;
    private Stage stage;
    private AhliGizi ahliGizi;
    private TextField usernameField;
    private PasswordField passwordField;
    private TextField namaField;
    private TextField spesialisasiField;
    private Label msgLabel;

    public AdminProfileView(Admin admin, Stage stage) {
        this.admin = admin;
        this.stage = stage;
    }
    
    public AdminProfileView(AhliGizi ahliGizi, Stage stage) {
        this.ahliGizi = ahliGizi; 
        this.stage = stage;
        initComponent();
    }

    private void initComponent() {
        this.setSpacing(25);
        this.setPadding(new Insets(35));
        this.setAlignment(Pos.TOP_LEFT);
        this.setStyle("-fx-background-color: #FFFFFF;");

        Label titleLabel = new Label("Edit Profil Ahli Gizi");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 26));
        titleLabel.setTextFill(Color.web("#9C27B0"));

        VBox formCard = new VBox(15);
        formCard.setPadding(new Insets(25));
        formCard.setMaxWidth(500);
        formCard.setStyle("-fx-background-color: #FAFAFB; -fx-border-color: #EFE5F5; -fx-border-radius: 8; -fx-background-radius: 8;");

        Label namaLabel = new Label("Nama Lengkap");
        namaLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        namaField = new TextField(ahliGizi.getNama());
        namaField.setStyle("-fx-padding: 8; -fx-background-radius: 4; -fx-border-color: #DCDCDC; -fx-border-radius: 4;");
        VBox namaGroup = new VBox(6, namaLabel, namaField);

        Label usernameLabel = new Label("Username");
        usernameLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        usernameField = new TextField(ahliGizi.getUsername());
        usernameField.setStyle("-fx-padding: 8; -fx-background-radius: 4; -fx-border-color: #DCDCDC; -fx-border-radius: 4;");
        VBox usernameGroup = new VBox(6, usernameLabel, usernameField);

        Label passwordLabel = new Label("Password Baru");
        passwordLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        passwordField = new PasswordField();
        passwordField.setPromptText("Kosongkan jika tidak ingin mengubah password");
        passwordField.setStyle("-fx-padding: 8; -fx-background-radius: 4; -fx-border-color: #DCDCDC; -fx-border-radius: 4;");
        VBox passwordGroup = new VBox(6, passwordLabel, passwordField);

        Label spesialisasiLabel = new Label("Spesialisasi");
        spesialisasiLabel.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));
        spesialisasiField = new TextField(ahliGizi.getSpesialisasi());
        spesialisasiField.setStyle("-fx-padding: 8; -fx-background-radius: 4; -fx-border-color: #DCDCDC; -fx-border-radius: 4;");
        VBox spesialisasiGroup = new VBox(6, spesialisasiLabel, spesialisasiField);

        Button saveBtn = new Button("Simpan Perubahan");
        saveBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 6; -fx-cursor: hand;");
        saveBtn.setPrefHeight(40);
        saveBtn.setMaxWidth(Double.MAX_VALUE);
        
        msgLabel = new Label("");
        msgLabel.setFont(Font.font("Segoe UI", 12));

        saveBtn.setOnAction(e -> handleSaveAction());

        formCard.getChildren().addAll(namaGroup, usernameGroup, passwordGroup, spesialisasiGroup, saveBtn, msgLabel);
        this.getChildren().addAll(titleLabel, formCard);
    }

    private void handleSaveAction() {
        String inputNama = namaField.getText().trim();
        String inputUsername = usernameField.getText().trim();
        String inputPassword = passwordField.getText().trim();
        String inputSpesialisasi = spesialisasiField.getText().trim();

        if (inputNama.isEmpty() || inputUsername.isEmpty() || inputSpesialisasi.isEmpty()) {
            msgLabel.setTextFill(Color.RED);
            msgLabel.setText("Nama, Username, dan Spesialisasi tidak boleh kosong!");
            return;
        }

        if (inputPassword.isEmpty()) {
            inputPassword = ahliGizi.getPassword(); 
        }

        ahliGizi.setNama(inputNama);
        ahliGizi.setUsername(inputUsername);
        ahliGizi.setPassword(inputPassword);
        ahliGizi.setSpesialisasi(inputSpesialisasi);

        try {
            AhliGiziImplement agImpl = new AhliGiziImplement();
            agImpl.update(ahliGizi);

            msgLabel.setTextFill(Color.GREEN);
            msgLabel.setText("Profil berhasil diperbarui!");
            
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sukses");
            alert.setHeaderText(null);
            alert.setContentText("Profil berhasil diperbarui di database!");
            alert.showAndWait();
            
        } catch (Exception ex) {
            msgLabel.setTextFill(Color.RED);
            msgLabel.setText("Gagal menyimpan: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}