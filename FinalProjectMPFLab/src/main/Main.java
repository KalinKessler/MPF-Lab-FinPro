package main;

//File: /FinalProjectMPFLab/src/DTeaApp.java

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;

public class Main extends Application {

 private final Map<String, String> userDatabase = new HashMap<>();
 private final Map<String, TeaItem> items = new HashMap<>();
 private final String adminEmail = "admin";
 private final String adminPassword = "admin123";
 private Stage primaryStage;

 @Override
 public void start(Stage primaryStage) {
     this.primaryStage = primaryStage;
     loadData();
     showLoginWindow();
 }

 private void loadData() {
     items.put("Apple Tea", new TeaItem("Apple Tea", getClass().getResource("/Assets/apple_tea.jpg").toExternalForm(), 25000, 20, "A delightful infusion blending the sweetness of ripe apples with the comforting warmth of tea, creating a fruity and aromatic beverage that evokes the essence of a crisp autumn day"));
     items.put("Black Tea", new TeaItem("Black Tea", getClass().getResource("/Assets/black_tea.jpg").toExternalForm(), 10000, 20, "A robust and full-bodied brew, characterized by its deep, malty notes and a bold, satisfying taste that makes it a classic choice for tea enthusiasts"));
     items.put("Honey Tea", new TeaItem("Honey Tea", getClass().getResource("/Assets/honey_tea.jpg").toExternalForm(), 30000, 20, "A soothing concoction that combines the natural sweetness of honey with the mellow tones of tea, resulting in a comforting and mildly sweetened drink that is perfect for relaxation"));
     items.put("Lemon Tea", new TeaItem("Lemon Tea", getClass().getResource("/Assets/lemon_tea.jpg").toExternalForm(), 20000, 10, "A refreshing infusion of black tea subtly brightened by the zesty essence of fresh lemons, offering a tangy and invigorating flavor profile"));
     items.put("Milk Tea", new TeaItem("Milk Tea", getClass().getResource("/Assets/milk_tea.jpg").toExternalForm(), 35000, 0, "A rich and creamy fusion of tea and milk, offering a harmonious balance of bold tea flavors and the velvety smoothness of milk, creating a comforting and indulgent beverage enjoyed worldwide"));
 }

 private void showLoginWindow() {
     GridPane gridPane = new GridPane();
     BorderPane borderPane = new BorderPane();
     
     gridPane.setVgap(5);
     gridPane.setHgap(10);

     Label emailLabel = new Label("Email");
     TextField emailField = new TextField();
     Label passwordLabel = new Label("Password");
     PasswordField passwordField = new PasswordField();
     Button loginButton = new Button("Login");
     loginButton.setPrefWidth(200);
     loginButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white;");

     Hyperlink registerLink = new Hyperlink("Not a member? Register");
     
     Label titleLabel = new Label("Login");
     titleLabel.setFont(Font.font("Arial", 24));
     
     gridPane.add(titleLabel, 0, 0, 2, 1);
     
     GridPane.setHalignment(titleLabel, javafx.geometry.HPos.CENTER);
     GridPane.setHalignment(registerLink, javafx.geometry.HPos.CENTER);
     
     gridPane.add(emailLabel, 0, 1);
     gridPane.add(emailField, 0, 2);
     gridPane.add(passwordLabel, 0, 3);
     gridPane.add(passwordField, 0, 4);
     GridPane.setMargin(passwordField, new Insets(0, 0, 10, 0));
     
     gridPane.add(loginButton, 0, 5);
     gridPane.add(registerLink, 0, 6);
     
     gridPane.setAlignment(Pos.CENTER);
     
     borderPane.setCenter(gridPane);
     
     loginButton.setOnAction(e -> {
         String email = emailField.getText();
         String password = passwordField.getText();
         if (validateLogin(email, password)) {
             if (adminEmail.equals(email) && adminPassword.equals(password)) {
                 showAdminWindow();
             } else {
                 showWelcomeCustomerWindow();
             }
         } else {
             showAlert("Invalid email or password.");
         }
     });

     registerLink.setOnAction(e -> showRegisterWindow());

     Scene scene = new Scene(borderPane, 400, 400);
     primaryStage.setScene(scene);
     primaryStage.setTitle("DTea Application");
     primaryStage.getIcons().add(new Image(getClass().getResource("/Assets/logo.png").toExternalForm()));
     primaryStage.show();
 }

 private boolean validateLogin(String email, String password) {
     if (email == null || email.trim().isEmpty() || password == null 
    		 || password.trim().isEmpty() || !email.contains("@") 
    		 || !email.endsWith(".com") || password.length() < 8) {
    	 if (adminEmail.equals(email) && adminPassword.equals(password)) {
    		 return true;
    	 }
         return false;
     }
     return userDatabase.containsKey(email) && userDatabase.get(email).equals(password) || 
             (adminEmail.equals(email) && adminPassword.equals(password));
 }

 private void showRegisterWindow() {
     GridPane gridPane = new GridPane();
     BorderPane borderPane = new BorderPane();
     gridPane.setVgap(5);
     gridPane.setHgap(10);

     Label emailLabel = new Label("Email");
     TextField emailField = new TextField();
     Label nameLabel = new Label("Name");
     TextField nameField = new TextField();
     Label passwordLabel = new Label("Password");
     PasswordField passwordField = new PasswordField();
     Label confirmPasswordLabel = new Label("Confirm Password");
     PasswordField confirmPasswordField = new PasswordField();

     Button registerButton = new Button("Register");
     registerButton.setPrefWidth(200);
     registerButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white;");
     Hyperlink loginLink = new Hyperlink("Already a member? Login");
     
     Label titleLabel = new Label("Register");
     titleLabel.setFont(Font.font("Arial", 24));
     
     gridPane.add(titleLabel, 0, 0, 2, 1);
     GridPane.setHalignment(titleLabel, javafx.geometry.HPos.CENTER);
     GridPane.setHalignment(loginLink, javafx.geometry.HPos.CENTER);

     gridPane.add(emailLabel, 0, 1);
     gridPane.add(emailField, 0, 2);
     gridPane.add(nameLabel, 0, 3);
     gridPane.add(nameField, 0, 4);
     gridPane.add(passwordLabel, 0, 5);
     gridPane.add(passwordField, 0, 6);
     gridPane.add(confirmPasswordLabel, 0, 7);
     gridPane.add(confirmPasswordField, 0, 8);
     gridPane.add(registerButton, 0, 9);
     gridPane.add(loginLink, 0, 10);
     
     gridPane.setAlignment(Pos.CENTER);
     borderPane.setCenter(gridPane);

     registerButton.setOnAction(e -> {
         String email = emailField.getText();
         String name = nameField.getText();
         String password = passwordField.getText();
         String confirmPassword = confirmPasswordField.getText();
         if (validateRegistration(email, name, password, confirmPassword)) {
             userDatabase.put(email, password);
             showLoginWindow();
         }
     });

     loginLink.setOnAction(e -> showLoginWindow());

     Scene scene = new Scene(borderPane, 400, 400);
     primaryStage.setScene(scene);
     primaryStage.setTitle("DTea Application");
     primaryStage.show();
 }

 private boolean validateRegistration(String email, String name, String password, String confirmPassword) {
     if (email == null || email.trim().isEmpty() || name == null || name.trim().isEmpty() ||
             password == null || password.trim().isEmpty() || confirmPassword == null || confirmPassword.trim().isEmpty()) {
         showAlert("All fields are required.");
         return false;
     }
     if (!email.contains("@") || !email.endsWith(".com") || userDatabase.containsKey(email)) {
         showAlert("Invalid or already used email.");
         return false;
     }
     if (password.length() < 8) {
    	 showAlert("Password must be at least 8 character");
    	 return false;
     }
     if (!password.equals(confirmPassword)) {
         showAlert("Passwords do not match.");
         return false;
     }
     return true;
 }

 private void showAdminWindow() {
	    VBox vbox2 = new VBox(10);
	    vbox2.setSpacing(10);

	    Label titleLabel = new Label("Manage Items");
	    titleLabel.setFont(Font.font("Arial", 24));

	    for (TeaItem item : items.values()) {
	        VBox vbox = new VBox(10);
	        vbox.setSpacing(10);

	        HBox hbox = new HBox(10);
	        HBox hbox2 = new HBox(10);

	        ImageView imageView = new ImageView(new Image(item.getImagePath()));
	        imageView.setFitHeight(250);
	        imageView.setFitWidth(250);

	        Label name = new Label(item.getName());
	        name.setFont(Font.font("Arial", 20));
	        TextField nameField = new TextField(item.getName());
	        TextField priceField = new TextField(String.valueOf(item.getPrice()));
	        Spinner<Integer> stockSpinner = new Spinner<>(0, 1000, item.getStock(), 1);

	        // Replace TextArea with TextFlow for non-scrollable description
	        
	        TextArea descriptionArea = new TextArea(item.getDescription());
	        descriptionArea.setWrapText(true); // Wrap text
	        descriptionArea.setPrefRowCount(5); // Initial row count
	        descriptionArea.setMinHeight(Region.USE_PREF_SIZE); // Use preferred height
	        descriptionArea.setMaxHeight(Region.USE_PREF_SIZE); // Use preferred height
	        descriptionArea.setPrefWidth(270); // Set preferred width
	        descriptionArea.setMaxWidth(270); // Set max width
	        descriptionArea.setScrollTop(Double.MAX_VALUE); // Ensure no scrolling

	        Button saveButton = new Button("Save Changes");
	        saveButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white;");
	        HBox saveButtonHBox = new HBox(saveButton);
	        saveButtonHBox.setPadding(new Insets(10));
	        Region spacer = new Region();
	        HBox.setHgrow(spacer, Priority.ALWAYS);

	        HBox saveButtonHbox = new HBox(10);
	        saveButtonHbox.getChildren().addAll(spacer, saveButton);

	        imageView.setOnMouseClicked(event -> showViewImageWindow(item.getImagePath()));

	        saveButton.setOnAction(e -> {
	            if (validateAdminFields(nameField.getText(), priceField.getText(), stockSpinner.getValue(), descriptionArea.getText())) {
	                item.setName(nameField.getText());
	                item.setPrice(Double.parseDouble(priceField.getText()));
	                item.setStock(stockSpinner.getValue());
	                item.setDescription(descriptionArea.getText());
	            }
	        });

	        VBox vboxNameField = new VBox();
	        Label nameFieldLabel = new Label("Item Name");
	        vboxNameField.getChildren().addAll(nameFieldLabel, nameField);

	        VBox vboxPriceField = new VBox();
	        Label priceFieldLabel = new Label("Item Price");
	        vboxPriceField.getChildren().addAll(priceFieldLabel, priceField);

	        VBox vboxStockSpinner = new VBox();
	        Label stockSpinnerLabel = new Label("Item Stock");
	        vboxStockSpinner.getChildren().addAll(stockSpinnerLabel, stockSpinner);

	        VBox vboxDescriptionArea = new VBox();
	        Label descriptionAreaLabel = new Label("Item Description");
	        vboxDescriptionArea.getChildren().addAll(descriptionAreaLabel, descriptionArea);

	        hbox.getChildren().addAll(vboxNameField, vboxPriceField, vboxStockSpinner);
	        vbox.getChildren().addAll(name, hbox, vboxDescriptionArea, saveButtonHbox);
	        hbox2.getChildren().addAll(imageView, vbox);
	        vbox2.getChildren().add(hbox2);
	    }

	    ScrollPane scrollPane = new ScrollPane(vbox2);
	    scrollPane.setFitToWidth(true);

	    Button logoutButton = new Button("Logout");
	    logoutButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white;");
	    logoutButton.setOnAction(e -> showLoginWindow());

	    HBox bottomHBox = new HBox(logoutButton);
	    bottomHBox.setPadding(new Insets(10));
	    bottomHBox.setAlignment(Pos.BOTTOM_RIGHT);

	    BorderPane borderPane = new BorderPane();
	    borderPane.setCenter(scrollPane);
	    borderPane.setBottom(bottomHBox);
	    borderPane.setTop(titleLabel);
	    BorderPane.setAlignment(titleLabel, Pos.TOP_CENTER);

	    BorderPane.setMargin(titleLabel, new Insets(10, 10, 10, 10));
	    BorderPane.setMargin(bottomHBox, new Insets(10));
	    BorderPane.setMargin(scrollPane, new Insets(10, 10, 0, 10));

	    Scene scene = new Scene(borderPane, 600, 400);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("DTea Application");
	    primaryStage.show();
	}


 private boolean validateAdminFields(String name, String price, int stock, String description) {
     if (name == null || name.trim().isEmpty()) {
         showAlert("Item name is required.");
         return false;
     }
     try {
         double priceValue = Double.parseDouble(price);
         if (priceValue < 10000) {
             showAlert("Price must be at least 10000.");
             return false;
         }
     } catch (NumberFormatException e) {
         showAlert("Invalid price value.");
         return false;
     }
     if (stock < 1) {
         showAlert("Stock must be at least 1.");
         return false;
     }
     if (description == null || description.trim().isEmpty() || description.length() < 10) {
         showAlert("Description must be at least 10 characters.");
         return false;
     }
     return true;
 }
 
 private void showViewImageWindow(String imagePath) {
	    Stage imageStage = new Stage();
	    BorderPane borderPane = new BorderPane();

	    ImageView imageView = new ImageView(new Image(imagePath));
	    imageView.setPreserveRatio(true);
	    
	    // Set maximum dimensions for the image view
	    double maxImageWidth = 300;
	    double maxImageHeight = 300;
	    imageView.setFitWidth(maxImageWidth);
	    imageView.setFitHeight(maxImageHeight);

	    // Initial scale and rotation
	    double initialScale = 1.0;
	    double initialRotation = 0.0;
	    imageView.setScaleX(initialScale);
	    imageView.setScaleY(initialScale);
	    imageView.setRotate(initialRotation);

	    HBox buttonBox = new HBox(10);
	    buttonBox.setAlignment(Pos.CENTER);
	    buttonBox.setPadding(new Insets(10));

	    Button zoomInButton = new Button("Zoom In");
	    Button zoomOutButton = new Button("Zoom Out");
	    Button rotateLeftButton = new Button("Rotate Left");
	    Button rotateRightButton = new Button("Rotate Right");

	    buttonBox.getChildren().addAll(zoomInButton, zoomOutButton, rotateLeftButton, rotateRightButton);

	    zoomInButton.setOnAction(e -> {
	        imageView.setScaleX(imageView.getScaleX() * 1.2);
	        imageView.setScaleY(imageView.getScaleY() * 1.2);
	    });

	    zoomOutButton.setOnAction(e -> {
	        imageView.setScaleX(imageView.getScaleX() * 0.8);
	        imageView.setScaleY(imageView.getScaleY() * 0.8);
	    });

	    rotateLeftButton.setOnAction(e -> {
	        imageView.setRotate(imageView.getRotate() - 90);
	    });

	    rotateRightButton.setOnAction(e -> {
	        imageView.setRotate(imageView.getRotate() + 90);
	    });

	    borderPane.setCenter(imageView);
	    borderPane.setBottom(buttonBox);
	    BorderPane.setAlignment(buttonBox, Pos.CENTER);

	    Scene scene = new Scene(borderPane, 400, 400);
	    imageStage.setScene(scene);
	    imageStage.setTitle("Image Preview");
	    imageStage.show();
	}


 private void showWelcomeCustomerWindow() {
	    VBox vbox = new VBox(10);
	    vbox.setSpacing(20);

	    Label welcomeLabel = new Label("Welcome to DTea !");
	    welcomeLabel.setFont(Font.font("Arial", 24));
	    
	    Media media = new Media(getClass().getResource("/Assets/video.mp4").toExternalForm());
	    MediaPlayer mediaPlayer = new MediaPlayer(media);
	    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

	    MediaView mediaView = new MediaView(mediaPlayer);
	    mediaView.setFitWidth(300); // Set desired width
	    mediaView.setFitHeight(200); // Set desired height

	    Button continueButton = new Button("Continue");
	    continueButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white;");
	    continueButton.setPrefWidth(300);
	    continueButton.setOnAction(e -> {
	        mediaPlayer.stop();
	        showCatalogueAndCartWindow();
	    });

	    vbox.getChildren().addAll(welcomeLabel, mediaView, continueButton);
	    vbox.setAlignment(Pos.CENTER);
	    
	    Scene scene = new Scene(vbox, 500, 500);
	    primaryStage.setScene(scene);
	    primaryStage.setTitle("DTea Application");
	    primaryStage.show();

	    mediaPlayer.play();
	}

//Declare media player as a class-level variable
private MediaPlayer mediaPlayer;

//Declare cart as Map<String, Integer>
private final Map<String, Integer> cart = new HashMap<>();

private void showCatalogueAndCartWindow() {
    GridPane itemGridPane = new GridPane();
    itemGridPane.setHgap(10);
    itemGridPane.setVgap(10);
    int column = 0;
    int row = 0;

    HBox rootHBox = new HBox(10);
    rootHBox.setPadding(new Insets(10));

    if (mediaPlayer == null) {
        Media media = new Media(getClass().getResource("/Assets/piano.mp3").toExternalForm());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
    }

    VBox itemListVBox = new VBox(10);
    itemListVBox.setSpacing(10);
    itemListVBox.setPadding(new Insets(10));

    Label itemListLabel = new Label("Enjoy our tea");
    itemListLabel.setFont(Font.font("Arial", 24));
    itemListLabel.setAlignment(Pos.CENTER);
    VBox.setMargin(itemListLabel, new Insets(0, 0, 10, 0));

    for (TeaItem item : items.values()) {
        VBox priceAndStockVBox = new VBox();
        priceAndStockVBox.setAlignment(Pos.CENTER_LEFT);

        VBox itemVBox = new VBox(5);
        itemVBox.setAlignment(Pos.CENTER_LEFT);
        itemVBox.setPadding(new Insets(10));
        itemVBox.setMinSize(180, 250);
        itemVBox.setPrefSize(180, 250);
        itemVBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

        ImageView imageView = new ImageView(new Image(item.getImagePath()));
        imageView.setFitHeight(100);
        imageView.setFitWidth(100);

        Label nameLabel = new Label(item.getName());
        Label priceLabel = new Label("Price: Rp. " + item.getPrice());
        Label stockLabel = new Label("Stock: " + item.getStock());
        Label descriptionLabel = new Label(item.getDescription());
        descriptionLabel.setWrapText(true);

        imageView.setOnDragDetected(event -> {
            Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putString(item.getName());
            db.setContent(content);
            event.consume();
        });

        imageView.setOnMouseClicked(event -> showViewImageWindow(item.getImagePath()));

        priceAndStockVBox.getChildren().addAll(priceLabel, stockLabel);
        itemVBox.getChildren().addAll(imageView, nameLabel, priceAndStockVBox, descriptionLabel);
        itemGridPane.add(itemVBox, column, row);

        column = (column + 1) % 2;
        if (column == 0) {
            row++;
        }
    }

    ScrollPane scrollPane = new ScrollPane(itemGridPane);
    scrollPane.setFitToWidth(true);
    scrollPane.setPrefHeight(450);
    scrollPane.setPrefWidth(350);

    VBox cartVBox = new VBox(10);
    cartVBox.setSpacing(10);
    cartVBox.setPadding(new Insets(10));
    cartVBox.setStyle("-fx-border-color: black; -fx-border-width: 1;");

    // Aligning the "Your Cart" label to the center
    Label cartLabel = new Label("Your Cart");
    cartLabel.setFont(Font.font("Arial", 24));
    HBox cartLabelHBox = new HBox(cartLabel);
    cartLabelHBox.setAlignment(Pos.CENTER);
    cartVBox.getChildren().add(cartLabelHBox);

    cartVBox.setOnDragOver(event -> {
        if (event.getGestureSource() != cartVBox && event.getDragboard().hasString()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    });

    cartVBox.setOnDragDropped(event -> {
        Dragboard db = event.getDragboard();
        boolean success = false;
        if (db.hasString()) {
            String itemName = db.getString();
            TeaItem item = items.get(itemName);
            if (item.getStock() > 0) {
                item.setStock(item.getStock() - 1);
                if (cart.containsKey(itemName)) {
                    int currentQuantity = cart.get(itemName);
                    cart.put(itemName, currentQuantity + 1);
                } else {
                    cart.put(itemName, 1);
                }
                showAlertInfo("Item added to cart.");
                showCatalogueAndCartWindow(); 
                success = true;
            } else {
                showAlert("Out of stock.");
            }
        }
        event.setDropCompleted(success);
        event.consume();
    });

    for (String itemName : cart.keySet()) {
        int quantity = cart.get(itemName);
        TeaItem item = items.get(itemName);

        HBox cartItemHBox = new HBox(10);
        VBox itemVBox = new VBox();

        ImageView imageView = new ImageView(new Image(item.getImagePath()));
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);

        Label nameLabel = new Label(item.getName() + " x" + quantity);
        Label priceLabel = new Label("Price: Rp. " + item.getPrice());
        itemVBox.getChildren().addAll(nameLabel, priceLabel);
        cartItemHBox.getChildren().addAll(imageView, itemVBox);
        cartVBox.getChildren().add(cartItemHBox);
    }

    Button purchaseAllButton = new Button("Purchase");
    purchaseAllButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white;");
    purchaseAllButton.setPrefWidth(200);  // Set preferred width
    HBox purchaseAllButtonHBox = new HBox(purchaseAllButton);
    purchaseAllButtonHBox.setAlignment(Pos.CENTER);
    purchaseAllButton.setOnAction(e -> {
        cart.clear();
        showAlertInfo("Purchase successful. Your cart is now empty.");
        showCatalogueAndCartWindow();
    });

    Region spacer = new Region();
    VBox.setVgrow(spacer, Priority.ALWAYS);
    cartVBox.getChildren().addAll(spacer, purchaseAllButtonHBox);

    Button logoutButton = new Button("Logout");
    logoutButton.setStyle("-fx-background-color: turquoise; -fx-text-fill: white;");
    logoutButton.setOnAction(e -> {
        mediaPlayer.stop();
        showLoginWindow();
    });

    rootHBox.getChildren().addAll(scrollPane, cartVBox);
    HBox.setHgrow(scrollPane, Priority.ALWAYS);
    HBox.setHgrow(cartVBox, Priority.ALWAYS);

    BorderPane borderPane = new BorderPane();
    borderPane.setTop(itemListLabel);
    BorderPane.setAlignment(itemListLabel, Pos.CENTER);
    borderPane.setCenter(rootHBox);
    borderPane.setBottom(logoutButton);
    BorderPane.setAlignment(logoutButton, Pos.BOTTOM_RIGHT);

    BorderPane.setMargin(itemListLabel, new Insets(10));
    BorderPane.setMargin(rootHBox, new Insets(10));
    BorderPane.setMargin(logoutButton, new Insets(10));

    Scene scene = new Scene(borderPane, 750, 500);
    primaryStage.setScene(scene);
    primaryStage.setTitle("DTea Application");
    primaryStage.show();
}


 private void showAlert(String message) {
     Alert alert = new Alert(Alert.AlertType.ERROR);
     alert.setContentText(message);
     alert.showAndWait();
 }

 private void showAlertInfo(String message) {
     Alert alert = new Alert(Alert.AlertType.INFORMATION);
     alert.setContentText(message);
     alert.showAndWait();
 }
 
 public static void main(String[] args) {
     launch(args);
 }
}

class TeaItem {
 private String name;
 private String imagePath;
 private double price;
 private int stock;
 private String description;

 public TeaItem(String name, String imagePath, double price, int stock, String description) {
     this.name = name;
     this.imagePath = imagePath;
     this.price = price;
     this.stock = stock;
     this.description = description;
 }

 public String getName() { return name; }
 public void setName(String name) { this.name = name; }

 public String getImagePath() { return imagePath; }
 public void setImagePath(String imagePath) { this.imagePath = imagePath; }

 public double getPrice() { return price; }
 public void setPrice(double price) { this.price = price; }

 public int getStock() { return stock; }
 public void setStock(int stock) { this.stock = stock; }

 public String getDescription() { return description; }
 public void setDescription(String description) { this.description = description; }
}
