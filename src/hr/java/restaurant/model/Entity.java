package hr.java.restaurant.model;

/**
 * Represents base class which will handle id for every other class which extends this one.
 */
abstract public class Entity {
    Long id;

    Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
