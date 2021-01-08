package ru.restaurant;

import ru.restaurant.model.Dish;

import java.time.LocalDate;

import static ru.restaurant.model.AbstractBaseEntity.START_SEQ;

public class DishTestData {
    public static TestMatcher<Dish> TEST_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(Dish.class);

    public final static int DISH_1_ID = START_SEQ + 7;
    public final static int DISH_2_ID = START_SEQ + 8;
    public final static int DISH_3_ID = START_SEQ + 9;
    public final static int DISH_4_ID = START_SEQ + 10;
    public final static int DISH_5_ID = START_SEQ + 11;

    public final static Dish DISH_1 = new Dish(DISH_1_ID, "Салат новогодний", 500, LocalDate.of(2020, 12, 31));
    public final static Dish DISH_2 = new Dish(DISH_2_ID, "Салат Мимоза", 300, LocalDate.now());
    public final static Dish DISH_3 = new Dish(DISH_3_ID, "Суп куриный", 300, LocalDate.now());
    public final static Dish DISH_4 = new Dish(DISH_4_ID, "Плов", 400, LocalDate.now());
    public final static Dish DISH_5 = new Dish(DISH_5_ID, "Компот", 150, LocalDate.now());

    public static Dish getNew() {
        return new Dish("Запеканка", 200);
    }

    public static Dish getUpdated() {
        Dish dish = new Dish(DISH_2);
        dish.setName("Салат Оливье");
        return dish;
    }
}
