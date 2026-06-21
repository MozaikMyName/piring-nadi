package uasGizi;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import uasGizi.user.User;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.DropShadow;
import javafx.scene.shape.Rectangle;

public class RegisterView {
    
    public void show(Stage stage) {
        Scene scene = stage.getScene();
        if (scene == null) {
            scene = new Scene(new StackPane(), 900, 650);
            stage.setScene(scene);
        }
        show(stage, scene);
    }

    public void show(Stage stage, Scene primaryScene) {
        
        Label titleLabel = new Label("Daftar Akun");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 32));
        titleLabel.setTextFill(Color.web("#C75B7A"));

        Label subtitleLabel = new Label("Mulai perjalanan hidup sehatmu");
        subtitleLabel.setFont(Font.font("Segoe UI", 14));
        subtitleLabel.setTextFill(Color.web("#888888"));

        TextField namaField = new TextField();
        namaField.setPromptText("Nama Lengkap");
        namaField.setPrefHeight(40);
        namaField.setPrefWidth(360);
        namaField.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-border-color: #E8A0BF;" + 
                "-fx-border-width: 1.5; -fx-background-color: rgba(255,255,255,0.8); -fx-padding: 0 15 0 15; -fx-font-size: 13;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setPrefHeight(40);
        usernameField.setPrefWidth(360);
        usernameField.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-border-color: #E8A0BF;" + 
                "-fx-border-width: 1.5; -fx-background-color: rgba(255,255,255,0.8); -fx-padding: 0 15 0 15; -fx-font-size: 13;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefHeight(40);
        passwordField.setPrefWidth(360);
        passwordField.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-border-color: #E8A0BF;" + 
                "-fx-border-width: 1.5; -fx-background-color: rgba(255,255,255,0.8); -fx-padding: 0 15 0 15; -fx-font-size: 13;");

        TextField usiaField = new TextField();
        usiaField.setPromptText("Usia");
        usiaField.setPrefHeight(40);
        usiaField.setPrefWidth(360);
        usiaField.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-border-color: #E8A0BF;" + 
                "-fx-border-width: 1.5; -fx-background-color: rgba(255,255,255,0.8); -fx-padding: 0 15 0 15; -fx-font-size: 13;");

        TextField beratField = new TextField();
        beratField.setPromptText("Berat Badan (kg)");
        beratField.setPrefHeight(40);
        beratField.setPrefWidth(360);
        beratField.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-border-color: #E8A0BF;" + 
                "-fx-border-width: 1.5; -fx-background-color: rgba(255,255,255,0.8); -fx-padding: 0 15 0 15; -fx-font-size: 13;");

        TextField tinggiField = new TextField();
        tinggiField.setPromptText("Tinggi Badan (cm)");
        tinggiField.setPrefHeight(40);
        tinggiField.setPrefWidth(360);
        tinggiField.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-border-color: #E8A0BF;" + 
                "-fx-border-width: 1.5; -fx-background-color: rgba(255,255,255,0.8); -fx-padding: 0 15 0 15; -fx-font-size: 13;");

        ComboBox<String> genderBox = new ComboBox<>();
        genderBox.getItems().addAll("Laki-laki", "Perempuan");
        genderBox.setPromptText("Jenis Kelamin");
        genderBox.setPrefWidth(360);
        genderBox.setPrefHeight(40);
        genderBox.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-border-color: #E8A0BF;" + 
                "-fx-border-width: 1.5; -fx-background-color: rgba(255,255,255,0.8);");

        ComboBox<String> aktivitasBox = new ComboBox<>();
        aktivitasBox.getItems().addAll("Sedentary", "Ringan", "Sedang", "Aktif", "Sangat Aktif");
        aktivitasBox.setPromptText("Tingkat Aktivitas");
        aktivitasBox.setPrefWidth(360);
        aktivitasBox.setPrefHeight(40);
        aktivitasBox.setStyle("-fx-background-radius: 25; -fx-border-radius: 25; -fx-border-color: #E8A0BF; -fx-border-width: 1.5; -fx-background-color: rgba(255,255,255,0.8);");

        Label msgLabel = new Label("");
        msgLabel.setTextFill(Color.RED);
        msgLabel.setWrapText(true);

        Button registerBtn = new Button("Daftar");
        registerBtn.setPrefWidth(360);
        registerBtn.setPrefHeight(40);
        registerBtn.setStyle("-fx-background-color: #D64D6E; -fx-text-fill: white;" + 
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");
        registerBtn.setOnMouseEntered(e -> registerBtn.setStyle(
                "-fx-background-color: #D64D6E; -fx-text-fill: white;" +
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;"
        ));
        registerBtn.setOnMouseExited(e -> registerBtn.setStyle(
                "-fx-background-color: #F05F80; -fx-text-fill: white;" + 
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;"
        ));

        Button backBtn = new Button("Sudah punya akun? Login");
        backBtn.setPrefWidth(360);
        backBtn.setPrefHeight(38);
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #F19CBB; -fx-border-color: #F19CBB;" + 
                "-fx-border-radius: 8; -fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;");
        backBtn.setOnMouseEntered(e -> backBtn.setStyle(
                "-fx-background-color: #F05F80; -fx-text-fill: white;" + 
                "-fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;"
        ));
        backBtn.setOnMouseExited(e -> backBtn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: #F19CBB; -fx-border-color: #F19CBB;" + 
                "-fx-border-radius: 8; -fx-background-radius: 8; -fx-font-size: 14; -fx-cursor: hand;"
        ));

        registerBtn.setOnAction(e -> {
            if (namaField.getText().isEmpty() || usernameField.getText().isEmpty()
                    || passwordField.getText().isEmpty() || usiaField.getText().isEmpty()
                    || beratField.getText().isEmpty() || tinggiField.getText().isEmpty()
                    || genderBox.getValue() == null || aktivitasBox.getValue() == null) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Semua field harus diisi!");
                return;
            }
            try {
                User u = new User();
                u.setNama(namaField.getText());
                u.setUsername(usernameField.getText());
                u.setPassword(passwordField.getText());
                u.setUsia(Integer.parseInt(usiaField.getText()));
                u.setBeratKg(Float.parseFloat(beratField.getText()));
                u.setTinggiCm(Float.parseFloat(tinggiField.getText()));
                u.setGender(genderBox.getValue());
                u.setAktivitas(aktivitasBox.getValue());

                uasGizi.user.UserImplement ui = new uasGizi.user.UserImplement();
                ui.insert(u);

                msgLabel.setTextFill(Color.web("#4CAF50"));
                msgLabel.setText("Akun berhasil dibuat! Silakan login.");
            } catch (NumberFormatException ex) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Usia, berat, dan tinggi harus berupa angka!");
            } catch (Exception ex) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Error: " + ex.getMessage());
            }
        });

        backBtn.setOnAction(e -> {
            LoginView lv = new LoginView();
            lv.show(stage, primaryScene);
        });

        VBox formBox = new VBox(12);
        formBox.setAlignment(Pos.CENTER_LEFT);
        formBox.setPadding(new Insets(30, 35, 30, 35));
        formBox.setPrefWidth(380);
        formBox.setMinHeight(520);
        formBox.setStyle("-fx-background-color: rgba(255,255,255,0.95);");
        formBox.getChildren().addAll(
            titleLabel, subtitleLabel,
            namaField, usernameField, passwordField, 
            usiaField, beratField, tinggiField,
            genderBox, aktivitasBox, registerBtn, backBtn, msgLabel
        );

        ScrollPane formScroll = new ScrollPane(formBox);
        formScroll.setFitToWidth(true);
        formScroll.setPrefWidth(420);
        formScroll.setPrefHeight(550);
        formScroll.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); 
        formScroll.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        formScroll.setStyle("-fx-background-color: transparent; -fx-background: transparent;" + 
                "-fx-border-color: transparent; -fx-viewport-background:" + 
                "transparent; -fx-padding: 0; -fx-vbar-policy: never;");

        StackPane cardForm = new StackPane(formScroll);
        cardForm.setPrefWidth(420);
        cardForm.setPrefHeight(550);
        cardForm.setStyle("-fx-background-color: rgba(255,255,255,0.95);"); 

        Image makananImg = new Image("file:D:/piring-nadi/gmb/kirby-2.jpg");
        ImageView makananView = new ImageView(makananImg);
        makananView.setPreserveRatio(true);  
        makananView.setFitWidth(450);
        makananView.setFitHeight(550);

        StackPane makananPane = new StackPane(makananView);
        makananPane.setPrefWidth(450);
        makananPane.setPrefHeight(550);

        HBox layout = new HBox(0);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(cardForm, makananPane); 

        StackPane card = new StackPane(layout);
        card.setAlignment(Pos.CENTER);
        card.setMaxWidth(870);  
        card.setMaxHeight(550);
        card.setStyle("-fx-background-radius: 20; -fx-background-color: white;");
        
        Rectangle finalCardMask = new Rectangle();
        finalCardMask.widthProperty().bind(card.widthProperty());
        finalCardMask.heightProperty().bind(card.heightProperty());
        finalCardMask.setArcWidth(40);
        finalCardMask.setArcHeight(40);
        card.setClip(finalCardMask);
        
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.2));
        shadow.setRadius(25);
        shadow.setOffsetY(5);
        card.setEffect(shadow);
        
        Image bgImage = new Image("file:D:/piring-nadi/gmb/bg-pink.jpg");
        ImageView bgView = new ImageView(bgImage);
        bgView.setPreserveRatio(false);
        bgView.fitWidthProperty().bind(stage.widthProperty());
        bgView.fitHeightProperty().bind(stage.heightProperty());

        StackPane root = new StackPane(bgView, card);
        StackPane.setAlignment(card, Pos.CENTER);

        primaryScene.setRoot(root);

        stage.setTitle("Piring Nadi - Daftar");
        stage.setMinWidth(850);
        stage.setMinHeight(650);
        stage.show();
    }
}