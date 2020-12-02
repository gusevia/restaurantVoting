package ru.restaurant.model;

import java.util.List;
import java.util.Set;

public class Restaurant extends AbstractNamedEntity {

    private Set<Dish> menu;
    private Set<Vote> votes;

    public Restaurant() {
    }

    public Restaurant(Integer id, String name) {
        super(id, name);
    }

    public Restaurant(Integer id, String name, Set<Dish> menu, Set<Vote> votes) {
        super(id, name);
        this.menu = menu;
        this.votes = votes;
    }

    public Set<Dish> getMenu() {
        return menu;
    }

    public void setMenu(Set<Dish> menu) {
        this.menu = menu;
    }

    public Set<Vote> getVotes() {
        return votes;
    }

    public void setVotes(Set<Vote> votes) {
        this.votes = votes;
    }
}
