package hr.java.restaurant.model;

/**
 * Represents bonus value which each employee will get at the end of the year.
 *
 * @param id    Identifier of specific bonus value
 * @param bonus Bonus value which each employee will get.
 */
public record Bonus(Integer id, Integer bonus) {
}
