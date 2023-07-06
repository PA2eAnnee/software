package com.example.demo1;
import com.google.gson.JsonObject;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ApiClient {

    private static final String BASE_URL = "https://api.cookmaster.best";

    private static Retrofit retrofit = null;
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }


    public interface Connection {
        @POST("/connection")
        Call<ResponseBody> login(@Body JsonObject body);
    }

    public interface ConnectionToken {
        @POST("/connectiontoken")

        Call<ResponseBody> connectoken(@Body JsonObject body);
    }

    public interface GetActivites {
        @POST("/getevents")

        Call<ResponseBody> allactivities(@Body JsonObject body,@Header("Authorization") String headerValue);
    }


    public interface GetGoActivities {
        @POST("/getgoestos")

        Call<ResponseBody> goactivites(@Body JsonObject body,@Header("Authorization") String headerValue);
    }

    public interface GoActivities {
        @POST("/deletegoestos")

        Call<ResponseBody> deleteActivites(@Body JsonObject body,@Header("Authorization") String headerValue);

        @POST("/goestos")

        Call<ResponseBody> addActivites(@Body JsonObject body,@Header("Authorization") String headerValue);

    }

    public interface Recipe {
        @POST("/getrecipe")

        Call<ResponseBody> getrecipe(@Body JsonObject body,@Header("Authorization") String headerValue);

    }

    public interface RecipeIngredient {
        @POST("/getrecipeIngredient")

        Call<ResponseBody> getRecipeIngredient(@Body JsonObject body,@Header("Authorization") String headerValue);

    }

    public interface Users {
        @POST("/getusers")

        Call<ResponseBody> getusers(@Body JsonObject body, @Header("Authorization") String headerValue);

    }

    public interface Order {
        @POST("/getorders")

        Call<ResponseBody> getorders(@Body JsonObject body,@Header("Authorization") String headerValue);

    }

    public interface Ticket {
        @POST("/gettickets")

        Call<ResponseBody> gettickets(@Body JsonObject body,@Header("Authorization") String headerValue);

    }

    public interface Diplomes {
        @POST("/getdiplomes")

        Call<ResponseBody> getdiplomes(@Body JsonObject body,@Header("Authorization") String headerValue);

    }

    public interface Articles {
        @POST("/getarticles")

        Call<ResponseBody> getarticles(@Body JsonObject body,@Header("Authorization") String headerValue);

    }









    public interface SiteApi {
        // Ajoutez ici les méthodes d'appel API pour la table Site
    }

    public interface ArticleApi {
        // Ajoutez ici les méthodes d'appel API pour la table Articles
    }

    public interface SpaceApi {
        // Ajoutez ici les méthodes d'appel API pour la table Space
    }

    public interface EventApi {
        // Ajoutez ici les méthodes d'appel API pour la table Event
    }

    public interface GoesToApi {
        // Ajoutez ici les méthodes d'appel API pour la table GoesTo
    }

    public interface ContainsApi {
        // Ajoutez ici les méthodes d'appel API pour la table Contains
    }

    public interface BooksApi {
        // Ajoutez ici les méthodes d'appel API pour la table Books
    }
}
