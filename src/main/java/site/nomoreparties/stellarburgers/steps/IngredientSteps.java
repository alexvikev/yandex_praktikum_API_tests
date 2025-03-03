package site.nomoreparties.stellarburgers.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import site.nomoreparties.stellarburgers.models.Order;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static io.restassured.RestAssured.given;
import static site.nomoreparties.stellarburgers.urls.EndPoints.GET_INGREDIENT_LIST_URL;
import static site.nomoreparties.stellarburgers.urls.TestStands.RC_STAND_URL;

//Класс содержит шаги для работы с ингредиентами
public class IngredientSteps {

    @Step("Получить список ингредиентов")
    public ValidatableResponse getIngredientsData(){
        return given()
                .contentType(ContentType.JSON)
                .baseUri(RC_STAND_URL)
                .when()
                .get(GET_INGREDIENT_LIST_URL)
                .then();
    }

    @Step("Добавить в заказ массив из четырех ингредиентов")
    public void setListOfIngredients(Order order){
        List<String> ingredientId = getIngredientsData().extract()
                .body().path("data._id");

        List<String> ingredientsList = new ArrayList<>();

        for(int i = 0; i < 4; i++){
            String ingredient = ingredientId.get(new Random().nextInt(ingredientId.size()));
            if(ingredient != null){
                ingredientsList.add(ingredient);
            }
        }

        order.setIngredients(ingredientsList);
    }

    @Step("Добавить в заказ пустой массив ингредиентов")
    public void setEmptyListOfIngredients(Order order){
        List<String> ingredientList = new ArrayList<>();

        order.setIngredients(ingredientList);
    }

    @Step("Добавить в заказ массив с неверным хэшем ингредиентов")
    public void setInvalidListOfIngredients(Order order){
        List<String> ingredientsList = new ArrayList<>();
        ingredientsList.add("Мякоть бедра джуниора");
        ingredientsList.add("Отбивная из заказчиков");
        ingredientsList.add("Соус из томленных ПМов");

        order.setIngredients(ingredientsList);
    }

}
