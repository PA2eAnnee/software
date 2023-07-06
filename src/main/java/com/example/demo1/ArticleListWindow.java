package com.example.demo1;

import com.example.demo1.model.Article;
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

public class ArticleListWindow extends Application {
    String token;

    public static void main(String[] args) {
        launch(args);
    }

    public void showAndWait() {
        Stage stage = new Stage();
        stage.setTitle("Liste des articles");

        ArrayList<Article> articlesList = new ArrayList<>();

        CountDownLatch latch2 = new CountDownLatch(1);
        AtomicBoolean result2 = new AtomicBoolean(false);

        new Thread(() -> {
            Properties properties = new Properties();

            try (FileInputStream input = new FileInputStream("config.properties")) {
                properties.load(input);
                token = properties.getProperty("token");
            } catch (IOException e) {
                e.printStackTrace();
            }
            ApiClient.Articles getarticles = ApiClient.getRetrofitInstance().create(ApiClient.Articles.class);
            JsonObject requestBody2 = new JsonObject();
            Call<ResponseBody> call2 = getarticles.getarticles(requestBody2,token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = createCustomGson(); // Utiliser la méthode pour créer une instance de Gson avec la désérialisation personnalisée
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("articles")) {
                        ArrayList<Article> articleList = gson.fromJson(jsonObject.get("articles"), new TypeToken<ArrayList<Article>>() {
                        }.getType());
                        articlesList.addAll(articleList);
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
        ObservableList<Article> articles = FXCollections.observableArrayList();
        articles.addAll(articlesList);

        // Créez une TableView pour afficher la liste des utilisateurs
        TableView<Article> tableView = new TableView<>();
        tableView.setItems(articles);

        TableColumn<Article, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Article, String> descriptionColumn = new TableColumn<>("Description");
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableColumn<Article, Integer> stockColumn = new TableColumn<>("Stock");
        stockColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));

        TableColumn<Article, Integer> priceColumn = new TableColumn<>("Prix");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        TableColumn<Article, String> pictureColumn = new TableColumn<>("Image");
        pictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));

        tableView.getColumns().addAll(idColumn, descriptionColumn, stockColumn, priceColumn, pictureColumn);

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
