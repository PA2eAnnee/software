package com.example.demo1;

import com.example.demo1.model.Logins;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginInterface extends Application {
    Logins login;
    private Button loginButton;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Connexion");

        // Création des éléments de l'interface
        Label usernameLabel = new Label("Nom d'utilisateur:");
        TextField usernameTextField = new TextField();

        Label passwordLabel = new Label("Mot de passe:");
        PasswordField passwordField = new PasswordField();

        loginButton = new Button("Se connecter");

        // Création du conteneur pour les éléments
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        grid.add(usernameLabel, 0, 1);
        grid.add(usernameTextField, 1, 1);
        grid.add(passwordLabel, 0, 2);
        grid.add(passwordField, 1, 2);
        grid.add(loginButton, 1, 3);

        // Ajout de l'action du bouton de connexion
        loginButton.setOnAction(event -> {
            String username = usernameTextField.getText();
            String password = passwordField.getText();

            if (checkLogin(username, password)) {
                launchMainInterface();
            } else {
                // Traitement en cas de connexion échouée
            }
        });

        Scene scene = new Scene(grid, 325, 250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean checkLogin(final String email, final String password) {
        final CountDownLatch latch = new CountDownLatch(1);
        final AtomicBoolean result = new AtomicBoolean(false);

        new Thread(() -> {
            // Construire un objet JSON à partir des données de connexion
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("email", email);
            jsonObject.addProperty("password", password);

            // Convertir l'objet JSON en une chaîne JSON
            String json = new Gson().toJson(jsonObject);

            ApiClient.Connection connection = ApiClient.getRetrofitInstance().create(ApiClient.Connection.class);
            Call<ResponseBody> call = connection.login(new Gson().fromJson(json, JsonObject.class));
            try {
                Response<ResponseBody> response = call.execute();
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String responseBody = response.body().string();
                    Gson gson = new Gson();
                    login = gson.fromJson(responseBody, Logins.class);
                    // Vérifier la réponse de l'API pour confirmer ou infirmer la validité des identifiants de l'utilisateur
                    if (login.isSuccess()) {
                        System.out.println(login.getConnection().getConnection().getName());
                        setLoginStatus(login);
                        result.set(true);
                    }
                } else {
                    result.set(false);
                }

            } catch (IOException e) {
                e.printStackTrace();
                // Erreur de communication avec le serveur
                result.set(false);
            } finally {
                latch.countDown();
            }
        }).start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result.get();
    }

    private void setLoginStatus(Logins logins) {
        Properties properties = new Properties();

        // Écrire dans le fichier de propriétés
        try (FileOutputStream output = new FileOutputStream("config.properties")) {
            properties.setProperty("token", logins.getConnection().getConnection().getToken());
            properties.store(output, "Configuration");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void launchMainInterface() {
        MainInterface mainInterface = new MainInterface(login.getConnection().getConnection().getName());
        Stage mainStage = new Stage();
        mainInterface.start(mainStage);
        // Fermez la fenêtre de connexion si nécessaire
        Stage loginStage = (Stage) loginButton.getScene().getWindow();
        loginStage.close();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
