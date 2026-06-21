package uasGizi;

import uasGizi.user.User;
import uasGizi.makanan.Makanan;
import uasGizi.makanan.MakananImplement;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class UserAddFoodView {
    private User user;

    public UserAddFoodView(User user) {
        this.user = user;
    }

    public Node getView() {
        Label titleLabel = new Label("Tambah Makanan/Minuman Baru");
        titleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
        titleLabel.setTextFill(Color.web("#F19CBB"));

        Label infoLabel = new Label("Isi nama wajib, data gizi boleh dikosongkan. Ahli gizi akan memverifikasi data.");
        infoLabel.setWrapText(true);

        TextField namaField = new TextField();
        namaField.setPromptText("Nama makanan/minuman (wajib)");
        namaField.setMaxWidth(400);

        TextField kaloriField = new TextField();
        kaloriField.setPromptText("Kalori per 100g (opsional)");
        kaloriField.setMaxWidth(400);

        TextField proteinField = new TextField();
        proteinField.setPromptText("Protein per 100g (opsional)");
        proteinField.setMaxWidth(400);

        TextField lemakField = new TextField();
        lemakField.setPromptText("Lemak per 100g (opsional)");
        lemakField.setMaxWidth(400);

        TextField karboField = new TextField();
        karboField.setPromptText("Karbohidrat per 100g (opsional)");
        karboField.setMaxWidth(400);

        Label msgLabel = new Label("");
        
        Button kirimBtn = new Button("Kirim Request");
        kirimBtn.setPrefWidth(400);
        kirimBtn.setStyle("-fx-background-color: #F05F80; -fx-text-fill: white; -fx-background-radius: 8; -fx-cursor: hand;");

        kirimBtn.setOnAction(e -> {
            if (namaField.getText().isEmpty()) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Nama makanan tidak boleh kosong!");
                return;
            }
            try {
                Makanan m = new Makanan();
                m.setNama(namaField.getText());
                m.setKalori(kaloriField.getText().isEmpty() ? 0 : Float.parseFloat(kaloriField.getText()));
                m.setProtein(proteinField.getText().isEmpty() ? 0 : Float.parseFloat(proteinField.getText()));
                m.setLemak(lemakField.getText().isEmpty() ? 0 : Float.parseFloat(lemakField.getText()));
                m.setKarbohidrat(karboField.getText().isEmpty() ? 0 : Float.parseFloat(karboField.getText()));
                m.setStatus("pending");
                m.setDitambahOleh("user");
                m.setUserId(user.getId());

                MakananImplement mi = new MakananImplement();
                mi.insert(m);

                msgLabel.setTextFill(Color.web("#4CAF50"));
                msgLabel.setText("Request berhasil dikirim!");
                
                namaField.clear(); kaloriField.clear(); proteinField.clear(); 
                lemakField.clear(); karboField.clear();
            } catch (NumberFormatException ex) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Data gizi harus berupa angka!");
            } catch (Exception ex) {
                msgLabel.setTextFill(Color.RED);
                msgLabel.setText("Error: " + ex.getMessage());
            }
        });

        VBox card = new VBox(15);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(40));
        card.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 16;");
        
        card.getChildren().addAll(titleLabel, infoLabel, namaField, kaloriField, 
                                  proteinField, lemakField, karboField, kirimBtn, msgLabel);

        return card;
    }
}