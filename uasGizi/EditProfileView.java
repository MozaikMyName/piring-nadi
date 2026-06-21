package uasGizi;

import uasGizi.user.User;
import uasGizi.user.UserImplement; 
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

public class EditProfileView {
    private User user;

    public EditProfileView(User user) {
        this.user = user;
    }

    public VBox getView() {
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(30));

        TextField usernameField = new TextField(user.getUsername());
        usernameField.setPromptText("Username");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password baru (kosongkan jika tidak ingin mengubah)");

        TextField namaField = new TextField(user.getNama());
        namaField.setPromptText("Nama Lengkap");

        TextField beratField = new TextField(String.valueOf(user.getBeratKg()));
        beratField.setPromptText("Berat (kg)");

        TextField tinggiField = new TextField(String.valueOf(user.getTinggiCm()));
        tinggiField.setPromptText("Tinggi (cm)");

        TextField genderField = new TextField(user.getGender());
        genderField.setPromptText("Gender");

        TextField aktivitasField = new TextField(user.getAktivitas());
        aktivitasField.setPromptText("Aktivitas");

        Button saveBtn = new Button("Simpan Perubahan");
        saveBtn.setStyle("-fx-background-color: #9C27B0; -fx-text-fill: white;");

        saveBtn.setOnAction(e -> {
            try {
                user.setUsername(usernameField.getText());
                if (!passwordField.getText().isEmpty()) {
                    user.setPassword(passwordField.getText());
                }
                user.setNama(namaField.getText());
                user.setBeratKg(Float.parseFloat(beratField.getText()));
                user.setTinggiCm(Float.parseFloat(tinggiField.getText()));
                user.setGender(genderField.getText());
                user.setAktivitas(aktivitasField.getText());
                
                new UserImplement().update(user);
                
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Profil berhasil diupdate!");
                alert.show();
            } catch (Exception ex) {
                new Alert(Alert.AlertType.ERROR, "Input tidak valid! Pastikan berat & tinggi adalah angka.").show();
            }
        });

        layout.getChildren().addAll(
            new Label("Edit Profil"), 
            usernameField,
            passwordField,
            namaField, 
            beratField, 
            tinggiField, 
            genderField,
            aktivitasField,
            saveBtn
        );
        return layout;
    }
}
