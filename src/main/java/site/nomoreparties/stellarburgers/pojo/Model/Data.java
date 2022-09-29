package site.nomoreparties.stellarburgers.pojo.Model;

import java.util.Objects;

public class Data {
    private String _id;
    private String name;
    private String type;
    private Integer proteins;
    private Integer fat;
    private Integer carbohydrates;
    private Integer calories;
    private Integer price;
    private String image;
    private String image_mobile;
    private String image_large;
    private Integer __v;

    public Data(String _id, String name, String type, Integer proteins, Integer fat, Integer carbohydrates, Integer calories, Integer price, String image, String image_mobile, String image_large, Integer __v) {
        this._id = _id;
        this.name = name;
        this.type = type;
        this.proteins = proteins;
        this.fat = fat;
        this.carbohydrates = carbohydrates;
        this.calories = calories;
        this.price = price;
        this.image = image;
        this.image_mobile = image_mobile;
        this.image_large = image_large;
        this.__v = __v;
    }

    public Data() {
    }

    public String get_id() {
        return _id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public Integer getProteins() {
        return proteins;
    }
    public Integer getFat() {
        return fat;
    }
    public Integer getCarbohydrates() {
        return carbohydrates;
    }
    public Integer getCalories() {
        return calories;
    }
    public Integer getPrice() {
        return price;
    }
    public String getImage() {
        return image;
    }
    public String getImage_mobile() {
        return image_mobile;
    }
    public String getImage_large() {
        return image_large;
    }
    public Integer get__v() {
        return __v;
    }

    @Override
    public String toString() {
        return "Data{" +
                "_id='" + _id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", proteins=" + proteins +
                ", fat=" + fat +
                ", carbohydrates=" + carbohydrates +
                ", calories=" + calories +
                ", price=" + price +
                ", image='" + image + '\'' +
                ", image_mobile='" + image_mobile + '\'' +
                ", image_large='" + image_large + '\'' +
                ", __v=" + __v +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Data data = (Data) o;
        return Objects.equals(_id, data._id) && Objects.equals(name, data.name) && Objects.equals(type, data.type) && Objects.equals(proteins, data.proteins) && Objects.equals(fat, data.fat) && Objects.equals(carbohydrates, data.carbohydrates) && Objects.equals(calories, data.calories) && Objects.equals(price, data.price) && Objects.equals(image, data.image) && Objects.equals(image_mobile, data.image_mobile) && Objects.equals(image_large, data.image_large) && Objects.equals(__v, data.__v);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, name, type, proteins, fat, carbohydrates, calories, price, image, image_mobile, image_large, __v);
    }
}
