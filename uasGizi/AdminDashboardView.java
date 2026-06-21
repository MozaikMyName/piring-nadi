    package uasGizi;

    import javafx.geometry.Insets;
    import javafx.geometry.Pos;
    import javafx.scene.Scene;
    import javafx.scene.control.Button;
    import javafx.scene.control.Label;
    import javafx.scene.layout.*;
    import javafx.scene.paint.Color;
    import javafx.scene.text.Font;
    import javafx.scene.text.FontWeight;
    import javafx.stage.Stage;
    import uasGizi.admin.Admin;
    import uasGizi.user.UserImplement;
    import uasGizi.makanan.MakananImplement;

    public class AdminDashboardView {
        private Admin admin;
        private BorderPane mainLayout; 
        private Stage stage;

        public AdminDashboardView(Admin admin) {
            this.admin = admin;
        }

        public void show(Stage stage) {

            this.stage = stage;

            this.mainLayout = new BorderPane();

            VBox sidebar = createSidebar();
            mainLayout.setLeft(sidebar);

            VBox dashboardHomeContent = createDashboardHomeContent();
            mainLayout.setCenter(dashboardHomeContent);

            Scene currentScene = stage.getScene();
            if (currentScene == null) {
                currentScene = new Scene(mainLayout, 1100, 700);
                stage.setScene(currentScene);
            } else {
                currentScene.setRoot(mainLayout);
            }

            stage.setResizable(true);
            stage.setMinWidth(1100);
            stage.setMinHeight(700);
            stage.show();stage.setTitle("Piring Nadi - Admin Dashboard");
            stage.setResizable(true);
            stage.setMinWidth(1100);
            stage.setMinHeight(700);
            stage.show();
        }

       private VBox createSidebar() {
            VBox sidebar = new VBox(12);
            sidebar.setPadding(new Insets(35, 18, 35, 18));
            sidebar.setPrefWidth(260);
            sidebar.setStyle("-fx-background-color: #F9F9FB; " +
                             "-fx-border-color: #EBEBEF; " +
                             "-fx-border-width: 0 1 0 0;");

            Label logoLabel = new Label("Piring Nadi");
            logoLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 25));
            logoLabel.setTextFill(Color.web("#C75B7A"));

            Label roleLabel = new Label("Halo, " + admin.getUsername() + "!");
            roleLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 14));
            roleLabel.setTextFill(Color.web("#B2B2C2"));
            roleLabel.setPadding(new Insets(-5, 0, 30, 0));

            VBox logoContainer = new VBox(5, logoLabel, roleLabel);
            logoContainer.setPadding(new Insets(0, 0, 10, 8));

            Button menuDashboard = createSidebarButton("Dashboard", "#C75B7A");
            Button menuFood = createSidebarButton("Kelola Makanan", "#F33A6A");
            Button menuUser = createSidebarButton("Lihat Daftar User", "#42A5F5");
            Button profileMenuButton = createSidebarButton("Profil", "#42A5F5");
            Button menuLogout = createSidebarButton("Logout", "#7F8C8D");

            menuDashboard.setOnAction(e -> {
                mainLayout.setCenter(createDashboardHomeContent());
            });

            menuFood.setOnAction(e -> {
                FoodListView flv = new FoodListView(admin, stage);
                mainLayout.setCenter(flv.getViewNode());
            });

            menuUser.setOnAction(e -> {
                try {
                    AdminUserListView ulv = new AdminUserListView(admin, stage);
                    mainLayout.setCenter(ulv.getViewNode());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            menuLogout.setOnAction(e -> {
                LoginView lv = new LoginView();
                lv.show(stage);
            });
            
            profileMenuButton.setOnAction(e -> {
                try {
                    AdminProfileView profileView = new AdminProfileView(this.admin, stage);
                    mainLayout.setCenter(profileView); 
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });

            Region spacer = new Region();
            VBox.setVgrow(spacer, Priority.ALWAYS);

            sidebar.getChildren().addAll(logoContainer, menuDashboard, menuFood, menuUser, profileMenuButton, spacer, menuLogout);
            return sidebar;
        }

        private VBox createDashboardHomeContent() {
            VBox mainContent = new VBox(30);
            mainContent.setPadding(new Insets(45, 45, 45, 45));
            mainContent.setStyle("-fx-background-color: #FFFFFF;");
            HBox.setHgrow(mainContent, Priority.ALWAYS);

            VBox headerTitleBox = new VBox(6);
            Label welcomeLabel = new Label("Selamat Datang, Admin!");
            welcomeLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 28));
            welcomeLabel.setTextFill(Color.web("#1E1E2F"));

            Label subHeaderLabel = new Label("Pantau perkembangan data teknis dan manajemen gizi pengguna.");
            subHeaderLabel.setFont(Font.font("Segoe UI", 14));
            subHeaderLabel.setTextFill(Color.web("#9292A6"));
            headerTitleBox.getChildren().addAll(welcomeLabel, subHeaderLabel);

            int totalUser = 0;
            int totalMakanan = 0;
            try {
                UserImplement ui = new UserImplement();
                totalUser = ui.getAll().size();
                MakananImplement mi = new MakananImplement();
                totalMakanan = mi.getAll().size();
            } catch (Exception e) {
                System.out.println("Gagal memuat statistik: " + e.getMessage());
            }

            HBox statsContainer = new HBox(25);
            statsContainer.setAlignment(Pos.CENTER_LEFT);
            statsContainer.setMaxWidth(Double.MAX_VALUE);

            VBox cardUser = buatStatCard("Total Pengguna Aktif", String.valueOf(totalUser), "#42A5F5", "👥");
            VBox cardFood = buatStatCard("Database Makanan", String.valueOf(totalMakanan), "#66BB6A", "🍲");

            HBox.setHgrow(cardUser, Priority.ALWAYS);
            HBox.setHgrow(cardFood, Priority.ALWAYS);

            statsContainer.getChildren().addAll(cardUser, cardFood);
            mainContent.getChildren().addAll(headerTitleBox, statsContainer);

            return mainContent;
        }

        private Button createSidebarButton(String text, String activeColorHex) {
            Button btn = new Button(text);
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setPrefHeight(48);
            btn.setAlignment(Pos.CENTER_LEFT);
            btn.setPadding(new Insets(0, 0, 0, 20));
            btn.setFont(Font.font("Segoe UI", FontWeight.SEMI_BOLD, 14));

            String defaultStyle = "-fx-background-color: #F0F0F4; " +
                                 "-fx-text-fill: #4A4A68; " +
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

        private VBox buatStatCard(String title, String value, String colorHex, String icon) {
            VBox card = new VBox(8);
            card.setPadding(new Insets(22, 25, 22, 25));
            card.setMinWidth(260);
            card.setPrefWidth(300);
            card.setMaxWidth(Double.MAX_VALUE);
            card.setStyle("-fx-background-color: #FFFFFF; " +
                         "-fx-background-radius: 16; " +
                         "-fx-effect: dropshadow(gaussian, rgba(160, 163, 189, 0.12), 20, 0, 0, 8); " +
                         "-fx-border-color: #E6E8F0; " +
                         "-fx-border-width: 1; " +
                         "-fx-border-radius: 16;");

            HBox topRow = new HBox();
            topRow.setAlignment(Pos.CENTER_LEFT);

            Label titleLabel = new Label(title);
            titleLabel.setFont(Font.font("Segoe UI", FontWeight.MEDIUM, 14));
            titleLabel.setTextFill(Color.web("#78788C"));

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Label iconLabel = new Label(icon);
            iconLabel.setFont(Font.font("Segoe UI", 20));
            iconLabel.setStyle("-fx-text-fill: " + colorHex + ";");

            topRow.getChildren().addAll(titleLabel, spacer, iconLabel);

            Label numLabel = new Label(value);
            numLabel.setFont(Font.font("Segoe UI", FontWeight.BOLD, 36));
            numLabel.setTextFill(Color.web("#1E1E2F"));

            Pane accentBar = new Pane();
            accentBar.setPrefHeight(4);
            accentBar.setMaxWidth(60);
            accentBar.setStyle("-fx-background-color: " + colorHex + "; -fx-background-radius: 2;");

            card.getChildren().addAll(topRow, numLabel, accentBar);
            return card;
        }

    }
