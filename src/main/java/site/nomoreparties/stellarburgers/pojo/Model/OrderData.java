package site.nomoreparties.stellarburgers.pojo.Model;

import java.util.List;
import java.util.Objects;

public class OrderData {
    private List<String> ingredients;
    private String name;
    private Order order;
    private Order number;
    private Boolean success;
    private String message;
    private List<Data> data;

    public OrderData(List<String> ingredients, String name, Order order, Order number, Boolean success, String message, List<Data> data) {
        this.ingredients = ingredients;
        this.name = name;
        this.order = order;
        this.number = number;
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public OrderData() {
    }

    public List<String> getIngredients() {
        return ingredients;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Order getOrder() {
        return order;
    }
    public Order getNumber() {
        return number;
    }
    public Boolean getSuccess() {
        return success;
    }
    public String getMessage() {
        return message;
    }
    public List<Data> getData() {
        return data;
    }
    public void setData(List<Data> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OrderData{" +
                "ingredients=" + ingredients +
                ", name='" + name + '\'' +
                ", order=" + order +
                ", number=" + number +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderData orderData = (OrderData) o;
        return Objects.equals(ingredients, orderData.ingredients) && Objects.equals(name, orderData.name) && Objects.equals(order, orderData.order) && Objects.equals(number, orderData.number) && Objects.equals(success, orderData.success) && Objects.equals(message, orderData.message) && Objects.equals(data, orderData.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ingredients, name, order, number, success, message, data);
    }
}
