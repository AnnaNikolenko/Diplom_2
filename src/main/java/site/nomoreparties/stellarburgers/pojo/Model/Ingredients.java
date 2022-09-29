package site.nomoreparties.stellarburgers.pojo.Model;

import java.util.List;
import java.util.Objects;

public class Ingredients {
    private List<Data> data;
    private String success;

    public Ingredients(List<Data> data, String success) {
        this.data = data;
        this.success = success;
    }

    public Ingredients() {
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public String getSuccess() {
        return success;
    }

    @Override
    public String toString() {
        return "Ingredients{" +
                "data=" + data +
                ", success='" + success + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredients that = (Ingredients) o;
        return Objects.equals(data, that.data) && Objects.equals(success, that.success);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, success);
    }
}
