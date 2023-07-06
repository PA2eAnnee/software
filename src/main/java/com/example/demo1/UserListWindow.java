package com.example.demo1;

import com.example.demo1.model.Diplomes;
import com.example.demo1.model.Users;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import okhttp3.Headers;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class UserListWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    private String token;

    public void showAndWait() {
        Stage stage = new Stage();
        stage.setTitle("Liste des utilisateurs");

        ArrayList<Users> usersList = new ArrayList<>();

        CountDownLatch latch2 = new CountDownLatch(1);
        AtomicBoolean result2 = new AtomicBoolean(false);

        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            properties.load(input);
            token = properties.getProperty("token");
        } catch (IOException e) {
            e.printStackTrace();
        }



        new Thread(() -> {

            ApiClient.Users getusers = ApiClient.getRetrofitInstance().create(ApiClient.Users.class);
            JsonObject requestBody2 = new JsonObject();
            Call<ResponseBody> call2 = getusers.getusers(requestBody2, token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = createCustomGson(); // Utiliser la méthode pour créer une instance de Gson avec la désérialisation personnalisée
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("users")) {
                        ArrayList<Users> userList = gson.fromJson(jsonObject.get("users"), new TypeToken<ArrayList<Users>>() {
                        }.getType());
                        usersList.addAll(userList);
                        result2.set(true);
                    }
                } else {
                    result2.set(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
                result2.set(false);
            } finally {
                latch2.countDown();
            }
        }).start();

        try {
            latch2.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Créez une liste factice d'objets Users
        ObservableList<Users> users = FXCollections.observableArrayList();
        users.addAll(usersList);

        // Créez une TableView pour afficher la liste des utilisateurs
        TableView<Users> tableView = new TableView<>();
        tableView.setItems(users);

        // Créez des colonnes pour les différents paramètres de Users
        TableColumn<Users, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Users, String> nameColumn = new TableColumn<>("Nom");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Users, String> firstNameColumn = new TableColumn<>("Prénom");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        TableColumn<Users, String> usernameColumn = new TableColumn<>("Nom d'utilisateur");
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));

        TableColumn<Users, String> emailColumn = new TableColumn<>("Email");
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        TableColumn<Users, Integer> roleColumn = new TableColumn<>("Rôle");
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));

        TableColumn<Users, String> subscriptionColumn = new TableColumn<>("Abonnement");
        subscriptionColumn.setCellValueFactory(new PropertyValueFactory<>("subscription"));

        TableColumn<Users, String> pictureColumn = new TableColumn<>("Image");
        pictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));

        TableColumn<Users, LocalDateTime> creationTimeColumn = new TableColumn<>("Date de création");
        creationTimeColumn.setCellValueFactory(new PropertyValueFactory<>("creationTime"));

        // Ajoutez les colonnes à la TableView
        tableView.getColumns().addAll(idColumn, nameColumn, firstNameColumn, usernameColumn, emailColumn,
                roleColumn, subscriptionColumn, pictureColumn, creationTimeColumn);

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().add(tableView);

        Scene scene = new Scene(vbox, 900, 600);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @Override
    public void start(Stage stage) throws Exception {
    }

    private Gson createCustomGson() {
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> {
            String datetime = json.getAsJsonPrimitive().getAsString();
            return LocalDateTime.parse(datetime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        });
        return gsonBuilder.create();
    }
}
