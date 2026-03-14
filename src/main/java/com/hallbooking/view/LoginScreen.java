package com.hallbooking.view;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginScreen extends Application {

    // ── Colour palette ────────────────────────────────────────────────────────
    private static final String BG_DARK        = "#0f1117";
    private static final String BG_CARD        = "#1a1d27";
    private static final String ACCENT         = "#c9a84c";   // gold
    private static final String ACCENT_HOVER   = "#e2c06a";
    private static final String TEXT_PRIMARY   = "#f0ece0";
    private static final String TEXT_MUTED     = "#7a7a8a";
    private static final String INPUT_BG       = "#12141c";
    private static final String INPUT_BORDER   = "#2e3044";
    private static final String INPUT_FOCUS    = "#c9a84c";
    private static final String ERROR_COLOR    = "#e05c5c";
    private static final String SUCCESS_COLOR  = "#4caf7d";

    @Override
    public void start(Stage stage) {
        // ── Root layout ───────────────────────────────────────────────────────
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: " + BG_DARK + ";");
        root.setPrefSize(900, 600);

        // ── Decorative background circles ─────────────────────────────────────
        root.getChildren().addAll(
            makeDecoCircle(300, "#c9a84c", 0.04, -200, -150),
            makeDecoCircle(200, "#c9a84c", 0.06,  350,  220),
            makeDecoCircle(150, "#ffffff", 0.02,  -50,  250)
        );

        // ── Main card ─────────────────────────────────────────────────────────
        HBox card = new HBox();
        card.setMaxSize(780, 460);
        card.setStyle(
            "-fx-background-color: " + BG_CARD + ";" +
            "-fx-background-radius: 18;" +
            "-fx-border-color: #2e3044;" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 18;"
        );
        card.setEffect(new DropShadow(40, Color.BLACK));

        // ── Left panel — branding ──────────────────────────────────────────────
        VBox leftPanel = buildLeftPanel();
        HBox.setHgrow(leftPanel, Priority.NEVER);

        // ── Right panel — form ────────────────────────────────────────────────
        VBox rightPanel = buildRightPanel(stage);
        HBox.setHgrow(rightPanel, Priority.ALWAYS);

        card.getChildren().addAll(leftPanel, rightPanel);
        root.getChildren().add(card);

        // ── Entry animation ───────────────────────────────────────────────────
        card.setOpacity(0);
        card.setTranslateY(30);
        FadeTransition fade = new FadeTransition(Duration.millis(600), card);
        fade.setToValue(1);
        TranslateTransition slide = new TranslateTransition(Duration.millis(600), card);
        slide.setToY(0);
        slide.setInterpolator(Interpolator.EASE_OUT);
        ParallelTransition entry = new ParallelTransition(fade, slide);
        entry.setDelay(Duration.millis(100));
        entry.play();

        Scene scene = new Scene(root, 900, 600);
        stage.setTitle("Hall Booking System — Login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // ── Left branding panel ───────────────────────────────────────────────────
    private VBox buildLeftPanel() {
        VBox panel = new VBox(16);
        panel.setPrefWidth(300);
        panel.setPadding(new Insets(48, 36, 48, 44));
        panel.setAlignment(Pos.CENTER_LEFT);
        panel.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, #1e2130, #12141c);" +
            "-fx-background-radius: 18 0 0 18;"
        );

        // Gold accent bar
        Rectangle accentBar = new Rectangle(40, 4);
        accentBar.setFill(Color.web(ACCENT));
        accentBar.setArcWidth(4);
        accentBar.setArcHeight(4);

        // Logo icon (simple hall silhouette using shapes)
        StackPane logo = buildLogoIcon();

        // App name
        Text appName = new Text("Hall Symphony");
        appName.setFont(Font.font("Georgia", FontWeight.BOLD, 22));
        appName.setFill(Color.web(TEXT_PRIMARY));

        Text appSub = new Text("Inc.");
        appSub.setFont(Font.font("Georgia", FontWeight.NORMAL, 14));
        appSub.setFill(Color.web(ACCENT));

        HBox nameRow = new HBox(6, appName, appSub);
        nameRow.setAlignment(Pos.BASELINE_LEFT);

        Text tagline = new Text("Premium hall booking\nfor every occasion.");
        tagline.setFont(Font.font("Georgia", FontPosture.ITALIC, 13));
        tagline.setFill(Color.web(TEXT_MUTED));
        tagline.setLineSpacing(4);

        // Divider
        Separator sep = new Separator();
        sep.setStyle("-fx-background-color: #2e3044;");
        sep.setMaxWidth(200);

        // Role badges
        Text rolesTitle = new Text("System Roles");
        rolesTitle.setFont(Font.font("Monospace", 10));
        rolesTitle.setFill(Color.web(TEXT_MUTED));

        VBox roles = new VBox(8,
            makeRoleBadge("● Customer",      "#4caf7d"),
            makeRoleBadge("● Scheduler",     "#c9a84c"),
            makeRoleBadge("● Manager",       "#5b9cf6"),
            makeRoleBadge("● Administrator", "#e05c5c")
        );

        panel.getChildren().addAll(
            logo, nameRow, tagline,
            sep, rolesTitle, roles,
            new Pane(), accentBar  // spacer + bottom accent
        );
        VBox.setVgrow(panel.getChildren().get(6), Priority.ALWAYS);

        return panel;
    }

    private StackPane buildLogoIcon() {
        StackPane stack = new StackPane();
        stack.setPrefSize(56, 56);

        Rectangle bg = new Rectangle(56, 56);
        bg.setFill(Color.web(ACCENT + "22"));
        bg.setArcWidth(14);
        bg.setArcHeight(14);
        bg.setStroke(Color.web(ACCENT + "55"));
        bg.setStrokeWidth(1);

        // Simple building shape
        Polygon building = new Polygon(
            28.0, 8.0,   // top peak
            44.0, 22.0,  // right roof
            44.0, 46.0,  // bottom right
            12.0, 46.0,  // bottom left
            12.0, 22.0   // left roof
        );
        building.setFill(Color.TRANSPARENT);
        building.setStroke(Color.web(ACCENT));
        building.setStrokeWidth(1.5);

        // Door
        Rectangle door = new Rectangle(23, 34, 10, 12);
        door.setFill(Color.web(ACCENT + "88"));
        door.setArcWidth(3);
        door.setArcHeight(3);

        stack.getChildren().addAll(bg, building, door);
        return stack;
    }

    private HBox makeRoleBadge(String text, String color) {
        Text t = new Text(text);
        t.setFont(Font.font("Monospace", 11));
        t.setFill(Color.web(color));
        HBox box = new HBox(t);
        box.setPadding(new Insets(2, 10, 2, 10));
        box.setStyle(
            "-fx-background-color: " + color + "18;" +
            "-fx-background-radius: 20;"
        );
        return box;
    }

    // ── Right form panel ──────────────────────────────────────────────────────
    private VBox buildRightPanel(Stage stage) {
        VBox panel = new VBox(20);
        panel.setPadding(new Insets(48, 48, 48, 44));
        panel.setAlignment(Pos.CENTER_LEFT);

        // Header
        Text welcome = new Text("Welcome back");
        welcome.setFont(Font.font("Georgia", FontWeight.NORMAL, 13));
        welcome.setFill(Color.web(TEXT_MUTED));

        Text signIn = new Text("Sign In");
        signIn.setFont(Font.font("Georgia", FontWeight.BOLD, 28));
        signIn.setFill(Color.web(TEXT_PRIMARY));

        VBox header = new VBox(4, welcome, signIn);

        // Email field
        VBox emailBox = buildInputField("Email Address", "Enter your email", false);
        TextField emailField = (TextField) ((VBox) emailBox).getChildren().get(1);

        // Password field
        VBox passwordBox = buildInputField("Password", "Enter your password", true);
        PasswordField passwordField = (PasswordField) ((VBox) passwordBox).getChildren().get(1);

        // Error label
        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: " + ERROR_COLOR + "; -fx-font-size: 11px;");
        errorLabel.setVisible(false);

        // Remember me + Forgot
        CheckBox rememberMe = new CheckBox("Remember me");
        rememberMe.setStyle(
            "-fx-text-fill: " + TEXT_MUTED + ";" +
            "-fx-font-size: 12px;"
        );

        Label forgot = new Label("Forgot password?");
        forgot.setStyle(
            "-fx-text-fill: " + ACCENT + ";" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );
        forgot.setOnMouseEntered(e -> forgot.setStyle(
            "-fx-text-fill: " + ACCENT_HOVER + ";" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        ));
        forgot.setOnMouseExited(e -> forgot.setStyle(
            "-fx-text-fill: " + ACCENT + ";" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        ));

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox rememberRow = new HBox(rememberMe, spacer, forgot);
        rememberRow.setAlignment(Pos.CENTER);

        // Login button
        Button loginBtn = new Button("Sign In");
        loginBtn.setMaxWidth(Double.MAX_VALUE);
        loginBtn.setPrefHeight(44);
        loginBtn.setStyle(
            "-fx-background-color: " + ACCENT + ";" +
            "-fx-text-fill: #0f1117;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        );
        loginBtn.setOnMouseEntered(e -> loginBtn.setStyle(
            "-fx-background-color: " + ACCENT_HOVER + ";" +
            "-fx-text-fill: #0f1117;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        ));
        loginBtn.setOnMouseExited(e -> loginBtn.setStyle(
            "-fx-background-color: " + ACCENT + ";" +
            "-fx-text-fill: #0f1117;" +
            "-fx-font-size: 14px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 8;" +
            "-fx-cursor: hand;"
        ));

        // Register link
        Label noAccount = new Label("Don't have an account? ");
        noAccount.setStyle("-fx-text-fill: " + TEXT_MUTED + "; -fx-font-size: 12px;");
        Label registerLink = new Label("Register here");
        registerLink.setStyle(
            "-fx-text-fill: " + ACCENT + ";" +
            "-fx-font-size: 12px;" +
            "-fx-cursor: hand;"
        );
        HBox registerRow = new HBox(noAccount, registerLink);
        registerRow.setAlignment(Pos.CENTER);

        // ── Button action ─────────────────────────────────────────────────────
        loginBtn.setOnAction(e -> handleLogin(
            emailField, passwordField, errorLabel, loginBtn, stage
        ));

        // Allow Enter key to submit
        passwordField.setOnAction(e -> loginBtn.fire());

        panel.getChildren().addAll(
            header,
            emailBox,
            passwordBox,
            errorLabel,
            rememberRow,
            loginBtn,
            registerRow
        );

        return panel;
    }

    private VBox buildInputField(String label, String prompt, boolean isPassword) {
        Label lbl = new Label(label);
        lbl.setStyle(
            "-fx-text-fill: " + TEXT_MUTED + ";" +
            "-fx-font-size: 11px;" +
            "-fx-font-weight: bold;"
        );

        String baseStyle =
            "-fx-background-color: " + INPUT_BG + ";" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-prompt-text-fill: #3a3a4a;" +
            "-fx-border-color: " + INPUT_BORDER + ";" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 10 14;" +
            "-fx-font-size: 13px;";

        String focusStyle =
            "-fx-background-color: " + INPUT_BG + ";" +
            "-fx-text-fill: " + TEXT_PRIMARY + ";" +
            "-fx-prompt-text-fill: #3a3a4a;" +
            "-fx-border-color: " + INPUT_FOCUS + ";" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 8;" +
            "-fx-background-radius: 8;" +
            "-fx-padding: 10 14;" +
            "-fx-font-size: 13px;";

        if (isPassword) {
            PasswordField field = new PasswordField();
            field.setPromptText(prompt);
            field.setMaxWidth(Double.MAX_VALUE);
            field.setStyle(baseStyle);
            field.focusedProperty().addListener((obs, oldVal, newVal) ->
                field.setStyle(newVal ? focusStyle : baseStyle)
            );
            VBox box = new VBox(6, lbl, field);
            return box;
        } else {
            TextField field = new TextField();
            field.setPromptText(prompt);
            field.setMaxWidth(Double.MAX_VALUE);
            field.setStyle(baseStyle);
            field.focusedProperty().addListener((obs, oldVal, newVal) ->
                field.setStyle(newVal ? focusStyle : baseStyle)
            );
            VBox box = new VBox(6, lbl, field);
            return box;
        }
    }

    // ── Login handler ─────────────────────────────────────────────────────────
    private void handleLogin(TextField emailField, PasswordField passwordField,
                              Label errorLabel, Button loginBtn, Stage stage) {

        String email    = emailField.getText().trim();
        String password = passwordField.getText().trim();

        // Basic validation
        if (email.isEmpty() || password.isEmpty()) {
            showError(errorLabel, "Please fill in all fields.");
            shakeNode(emailField.getParent().getParent());
            return;
        }

        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            showError(errorLabel, "Invalid email format.");
            shakeNode(emailField);
            return;
        }

        // Loading state
        loginBtn.setText("Signing in...");
        loginBtn.setDisable(true);

        // Simulate auth delay (replace with real AuthService later)
        PauseTransition pause = new PauseTransition(Duration.millis(800));
        pause.setOnFinished(e -> {
            loginBtn.setText("Sign In");
            loginBtn.setDisable(false);

            // ── TEMP: hardcoded test credentials ──────────────────────────────
            // Replace this block with AuthService.login(email, password) later
            if (email.equals("customer@test.com") && password.equals("test123")) {
                showSuccess(errorLabel, "Login successful! Redirecting...");
                // TODO: SceneManager.switchTo("customer-dashboard");
            } else if (email.equals("admin@test.com") && password.equals("admin123")) {
                showSuccess(errorLabel, "Login successful! Redirecting...");
                // TODO: SceneManager.switchTo("admin-dashboard");
            } else {
                showError(errorLabel, "Incorrect email or password.");
                shakeNode(loginBtn);
            }
        });
        pause.play();
    }

    private void showError(Label label, String msg) {
        label.setText("⚠  " + msg);
        label.setStyle("-fx-text-fill: " + ERROR_COLOR + "; -fx-font-size: 11px;");
        label.setVisible(true);
        FadeTransition ft = new FadeTransition(Duration.millis(200), label);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void showSuccess(Label label, String msg) {
        label.setText("✓  " + msg);
        label.setStyle("-fx-text-fill: " + SUCCESS_COLOR + "; -fx-font-size: 11px;");
        label.setVisible(true);
    }

    private void shakeNode(Node node) {
        TranslateTransition shake = new TranslateTransition(Duration.millis(60), node);
        shake.setFromX(0);
        shake.setByX(8);
        shake.setCycleCount(6);
        shake.setAutoReverse(true);
        shake.setOnFinished(e -> node.setTranslateX(0));
        shake.play();
    }

    private Circle makeDecoCircle(double radius, String color,
                                   double opacity, double x, double y) {
        Circle c = new Circle(radius);
        c.setFill(Color.web(color, opacity));
        c.setTranslateX(x);
        c.setTranslateY(y);
        return c;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
