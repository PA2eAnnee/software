package com.example.demo1;

import com.example.demo1.model.Diplomes;
import com.example.demo1.model.Order;
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

public class OrderListWindow extends Application {

    public static void main(String[] args) {
        launch(args);
    }
String token;
    public void showAndWait() {
        Stage stage = new Stage();
        stage.setTitle("Liste des commandes");

        ArrayList<Order> ordersList = new ArrayList<>();

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
            ApiClient.Order getOrder = ApiClient.getRetrofitInstance().create(ApiClient.Order.class);
            JsonObject requestBody2 = new JsonObject();
            Call<ResponseBody> call2 = getOrder.getorders(requestBody2,token);

            try {
                Response<ResponseBody> response2 = call2.execute();
                if (response2.isSuccessful()) {
                    assert response2.body() != null;
                    String responseString = response2.body().string();
                    Gson gson = createCustomGson(); // Utiliser la méthode pour créer une instance de Gson avec la désérialisation personnalisée
                    JsonObject jsonObject = gson.fromJson(responseString, JsonObject.class);
                    if (jsonObject.has("orders")) {
                        ArrayList<Order> orderList = gson.fromJson(jsonObject.get("orders"), new TypeToken<ArrayList<Order>>() {
                        }.getType());
                        ordersList.addAll(orderList);
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
        ObservableList<Order> order = FXCollections.observableArrayList();
        order.addAll(ordersList);

        // Créez une TableView pour afficher la liste des utilisateurs
        TableView<Order> tableView = new TableView<>();
        tableView.setItems(order);

        // Créez des colonnes pour les différents paramètres de Users
        TableColumn<Order, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Order, LocalDateTime> sendDateColumn = new TableColumn<>("Date d'envoi");
        sendDateColumn.setCellValueFactory(new PropertyValueFactory<>("sendDate"));

        TableColumn<Order, Integer> totalPriceColumn = new TableColumn<>("Prix total");
        totalPriceColumn.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        TableColumn<Order, Integer> userIdColumn = new TableColumn<>("ID utilisateur");
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));

        tableView.getColumns().addAll(idColumn, sendDateColumn, totalPriceColumn, userIdColumn);

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
