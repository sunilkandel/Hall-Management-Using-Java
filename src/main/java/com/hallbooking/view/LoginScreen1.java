package com.hallbooking.view;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LoginScreen1 extends Application {

    // ── Palette: warm white + deep navy + single steel-blue accent ────────────
    private static final String BG            = "#F7F6F3";
    private static final String CARD_BG       = "#FFFFFF";
    private static final String LEFT_BG       = "#1C2B3A";
    private static final String ACCENT        = "#2E6DA4";
    private static final String ACCENT_HOVER  = "#245A8C";
    private static final String TEXT_DARK     = "#1A1A2E";
    private static final String TEXT_MID      = "#5A5A72";
    private static final String TEXT_LIGHT    = "#9A9AAF";
    private static final String TEXT_WHITE    = "#EDF2F7";
    private static final String TEXT_WHITE_DIM= "#8DA4B8";
    private static final String BORDER        = "#E2E0DB";
    private static final String INPUT_BG      = "#F9F8F6";
    private static final String INPUT_FOCUS   = "#2E6DA4";
    private static final String ERROR_COLOR   = "#C0392B";
    private static final String SUCCESS_COLOR = "#1A7A4A";

    @Override
    public void start(Stage stage) {
        StackPane root = new StackPane();
        root.setStyle("-fx-background-color: " + BG + ";");
        root.setPrefSize(860, 540);

        HBox card = new HBox();
        card.setMaxSize(760, 440);
        card.setStyle(
            "-fx-background-color: " + CARD_BG + ";" +
            "-fx-background-radius: 4;" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 4;"
        );
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.rgb(0, 0, 0, 0.08));
        shadow.setRadius(24);
        shadow.setOffsetY(8);
        card.setEffect(shadow);

        card.getChildren().addAll(buildLeftPanel(), buildRightPanel(stage));
        root.getChildren().add(card);

        card.setOpacity(0);
        FadeTransition ft = new FadeTransition(Duration.millis(400), card);
        ft.setToValue(1);
        ft.setDelay(Duration.millis(80));
        ft.play();

        Scene scene = new Scene(root, 860, 540);
        stage.setTitle("Hall Booking System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private VBox buildLeftPanel() {
        VBox panel = new VBox();
        panel.setPrefWidth(280);
        panel.setPadding(new Insets(44, 36, 44, 40));
        panel.setStyle(
            "-fx-background-color: " + LEFT_BG + ";" +
            "-fx-background-radius: 4 0 0 4;"
        );

        VBox top = new VBox(12);

        Label systemLabel = new Label("HALL SYMPHONY INC.");
        systemLabel.setStyle(
            "-fx-text-fill: " + TEXT_WHITE_DIM + ";" +
            "-fx-font-size: 9px;" +
            "-fx-font-family: 'Monospaced';"
        );

        Rectangle line = new Rectangle(32, 2);
        line.setFill(Color.web(ACCENT));

        Label heading = new Label("Book the\nperfect hall.");
        heading.setStyle(
            "-fx-text-fill: " + TEXT_WHITE + ";" +
            "-fx-font-size: 22px;" +
            "-fx-font-family: 'Georgia';" +
            "-fx-font-weight: bold;"
        );
        heading.setWrapText(true);

        Label subtext = new Label("Auditoriums, banquet halls,\nand meeting rooms.");
        subtext.setStyle(
            "-fx-text-fill: " + TEXT_WHITE_DIM + ";" +
            "-fx-font-size: 11px;" +
            "-fx-font-family: 'Georgia';" +
            "-fx-font-style: italic;"
        );
        subtext.setWrapText(true);

        top.getChildren().addAll(systemLabel, line, heading, subtext);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        Label rolesLabel = new Label("SYSTEM ACCESS");
        rolesLabel.setStyle(
            "-fx-text-fill: " + TEXT_WHITE_DIM + ";" +
            "-fx-font-size: 8px;" +
            "-fx-font-family: 'Monospaced';"
        );

        VBox roleList = new VBox(5,
            makeRoleRow("Customer"),
            makeRoleRow("Scheduler"),
            makeRoleRow("Manager"),
            makeRoleRow("Administrator")
        );

        Label version = new Label("v1.0.0");
        version.setStyle(
            "-fx-text-fill: #3A4F62;" +
            "-fx-font-size: 9px;" +
            "-fx-font-family: 'Monospaced';"
        );
        VBox.setMargin(version, new Insets(14, 0, 0, 0));

        panel.getChildren().addAll(top, spacer, rolesLabel, roleList, version);
        return panel;
    }

    private HBox makeRoleRow(String role) {
        Circle dot = new Circle(3);
        dot.setFill(Color.web(ACCENT));
        Label label = new Label(role);
        label.setStyle(
            "-fx-text-fill: " + TEXT_WHITE_DIM + ";" +
            "-fx-font-size: 11px;"
        );
        HBox row = new HBox(8, dot, label);
        row.setAlignment(Pos.CENTER_LEFT);
        return row;
    }

    private VBox buildRightPanel(Stage stage) {
        VBox panel = new VBox(18);
        panel.setPadding(new Insets(44, 48, 44, 44));
        panel.setAlignment(Pos.CENTER_LEFT);

        Label title = new Label("Sign in");
        title.setStyle(
            "-fx-text-fill: " + TEXT_DARK + ";" +
            "-fx-font-size: 24px;" +
            "-fx-font-family: 'Georgia';" +
            "-fx-font-weight: bold;"
        );
        Label subtitle = new Label("Enter your credentials to continue");
        subtitle.setStyle(
            "-fx-text-fill: " + TEXT_MID + ";" +
            "-fx-font-size: 12px;"
        );
        VBox header = new VBox(4, title, subtitle);

        Rectangle accentLine = new Rectangle(36, 2);
        accentLine.setFill(Color.web(ACCENT));
        accentLine.setArcWidth(2);
        accentLine.setArcHeight(2);

        VBox emailBox = buildField("Email", "you@example.com", false);
        TextField emailField = (TextField) emailBox.getChildren().get(1);

        VBox passBox = buildField("Password", "••••••••", true);
        PasswordField passField = (PasswordField) passBox.getChildren().get(1);

        Label errorLabel = new Label("");
        errorLabel.setStyle("-fx-text-fill: " + ERROR_COLOR + "; -fx-font-size: 11px;");
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);

        CheckBox remember = new CheckBox("Remember me");
        remember.setStyle("-fx-text-fill: " + TEXT_MID + "; -fx-font-size: 11px;");

        Label forgot = new Label("Forgot password?");
        forgot.setStyle("-fx-text-fill: " + ACCENT + "; -fx-font-size: 11px; -fx-cursor: hand;");
        forgot.setOnMouseEntered(e -> forgot.setStyle(
            "-fx-text-fill: " + ACCENT_HOVER + "; -fx-font-size: 11px; -fx-cursor: hand; -fx-underline: true;"));
        forgot.setOnMouseExited(e -> forgot.setStyle(
            "-fx-text-fill: " + ACCENT + "; -fx-font-size: 11px; -fx-cursor: hand;"));

        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        HBox optRow = new HBox(remember, sp, forgot);
        optRow.setAlignment(Pos.CENTER);

        Button signInBtn = new Button("Sign In");
        signInBtn.setMaxWidth(Double.MAX_VALUE);
        signInBtn.setPrefHeight(40);
        applyBtnStyle(signInBtn, false);
        signInBtn.setOnMouseEntered(e -> applyBtnStyle(signInBtn, true));
        signInBtn.setOnMouseExited(e  -> applyBtnStyle(signInBtn, false));

        Label noAcc = new Label("Don't have an account?");
        noAcc.setStyle("-fx-text-fill: " + TEXT_LIGHT + "; -fx-font-size: 11px;");
        Label regLink = new Label("Register");
        regLink.setStyle("-fx-text-fill: " + ACCENT + "; -fx-font-size: 11px; -fx-cursor: hand;");
        regLink.setOnMouseEntered(e -> regLink.setStyle(
            "-fx-text-fill: " + ACCENT_HOVER + "; -fx-font-size: 11px; -fx-cursor: hand; -fx-underline: true;"));
        regLink.setOnMouseExited(e -> regLink.setStyle(
            "-fx-text-fill: " + ACCENT + "; -fx-font-size: 11px; -fx-cursor: hand;"));

        HBox regRow = new HBox(6, noAcc, regLink);
        regRow.setAlignment(Pos.CENTER_LEFT);

        signInBtn.setOnAction(e ->
            handleLogin(emailField, passField, errorLabel, signInBtn, stage));
        passField.setOnAction(e -> signInBtn.fire());

        panel.getChildren().addAll(
            header, accentLine,
            emailBox, passBox,
            errorLabel, optRow,
            signInBtn, regRow
        );
        return panel;
    }

    private VBox buildField(String labelText, String prompt, boolean isPassword) {
        Label lbl = new Label(labelText);
        lbl.setStyle(
            "-fx-text-fill: " + TEXT_MID + ";" +
            "-fx-font-size: 11px;" +
            "-fx-font-weight: bold;"
        );

        String base =
            "-fx-background-color: " + INPUT_BG + ";" +
            "-fx-text-fill: " + TEXT_DARK + ";" +
            "-fx-prompt-text-fill: " + TEXT_LIGHT + ";" +
            "-fx-border-color: " + BORDER + ";" +
            "-fx-border-width: 1;" +
            "-fx-border-radius: 3;" +
            "-fx-background-radius: 3;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 12px;";

        String focused =
            "-fx-background-color: " + CARD_BG + ";" +
            "-fx-text-fill: " + TEXT_DARK + ";" +
            "-fx-prompt-text-fill: " + TEXT_LIGHT + ";" +
            "-fx-border-color: " + INPUT_FOCUS + ";" +
            "-fx-border-width: 1.5;" +
            "-fx-border-radius: 3;" +
            "-fx-background-radius: 3;" +
            "-fx-padding: 9 12;" +
            "-fx-font-size: 12px;";

        if (isPassword) {
            PasswordField f = new PasswordField();
            f.setPromptText(prompt);
            f.setMaxWidth(Double.MAX_VALUE);
            f.setStyle(base);
            f.focusedProperty().addListener((o, ov, nv) -> f.setStyle(nv ? focused : base));
            return new VBox(5, lbl, f);
        } else {
            TextField f = new TextField();
            f.setPromptText(prompt);
            f.setMaxWidth(Double.MAX_VALUE);
            f.setStyle(base);
            f.focusedProperty().addListener((o, ov, nv) -> f.setStyle(nv ? focused : base));
            return new VBox(5, lbl, f);
        }
    }

    private void applyBtnStyle(Button btn, boolean hover) {
        btn.setStyle(
            "-fx-background-color: " + (hover ? ACCENT_HOVER : ACCENT) + ";" +
            "-fx-text-fill: white;" +
            "-fx-font-size: 13px;" +
            "-fx-font-weight: bold;" +
            "-fx-background-radius: 3;" +
            "-fx-cursor: hand;"
        );
    }

    private void handleLogin(TextField emailField, PasswordField passField,
                              Label errorLabel, Button btn, Stage stage) {
        String email = emailField.getText().trim();
        String pass  = passField.getText().trim();

        if (email.isEmpty() || pass.isEmpty()) {
            showError(errorLabel, "Please fill in all fields.");
            shake(btn);
            return;
        }
        if (!email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            showError(errorLabel, "Invalid email address.");
            shake(emailField);
            return;
        }

        btn.setText("Signing in...");
        btn.setDisable(true);

        PauseTransition pause = new PauseTransition(Duration.millis(700));
        pause.setOnFinished(e -> {
            btn.setText("Sign In");
            btn.setDisable(false);

            // Temp credentials — replace with AuthService later
            if (email.equals("customer@test.com") && pass.equals("test123")) {
                showSuccess(errorLabel, "Login successful.");
                // TODO: SceneManager.switchTo("customer-dashboard");
            } else if (email.equals("admin@test.com") && pass.equals("admin123")) {
                showSuccess(errorLabel, "Login successful.");
                // TODO: SceneManager.switchTo("admin-dashboard");
            } else {
                showError(errorLabel, "Incorrect email or password.");
                shake(btn);
            }
        });
        pause.play();
    }

    private void showError(Label label, String msg) {
        label.setText("✕  " + msg);
        label.setStyle("-fx-text-fill: " + ERROR_COLOR + "; -fx-font-size: 11px;");
        label.setVisible(true);
        label.setManaged(true);
        FadeTransition ft = new FadeTransition(Duration.millis(200), label);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }

    private void showSuccess(Label label, String msg) {
        label.setText("✓  " + msg);
        label.setStyle("-fx-text-fill: " + SUCCESS_COLOR + "; -fx-font-size: 11px;");
        label.setVisible(true);
        label.setManaged(true);
    }

    private void shake(Node node) {
        TranslateTransition t = new TranslateTransition(Duration.millis(55), node);
        t.setByX(6);
        t.setCycleCount(6);
        t.setAutoReverse(true);
        t.setOnFinished(e -> node.setTranslateX(0));
        t.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}