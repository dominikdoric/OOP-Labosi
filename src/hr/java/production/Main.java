package hr.java.production;

import hr.java.restaurant.enumeration.ContractType;
import hr.java.restaurant.exception.NumberNotCorrectException;
import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class Main {
    static final int NUMBER_OF_CATEGORIES = 3;
    static final int NUMBER_OF_INGREDIENTS = 3;
    static final int NUMBER_OF_MEALS = 3;
    static final int NUMBER_OF_CHEFS = 3;
    static final int NUMBER_OF_WAITERS = 3;
    static final int NUMBER_OF_DELIVERERS = 3;
    static final int NUMBER_OF_RESTAURANTS = 3;
    static final int NUMBER_OF_ORDERS = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Set<Category> categories = new HashSet<>(NUMBER_OF_CATEGORIES);
        Set<Ingredient> ingredients = new HashSet<>(NUMBER_OF_INGREDIENTS);
        Set<Meal> meals = new HashSet<>(NUMBER_OF_MEALS);
        Set<Chef> chefs = new HashSet<>(NUMBER_OF_CHEFS);
        Set<Waiter> waiters = new HashSet<>(NUMBER_OF_WAITERS);
        Set<Deliverer> deliverers = new HashSet<>(NUMBER_OF_DELIVERERS);
        Set<Restaurant> restaurants = new HashSet<>(NUMBER_OF_RESTAURANTS);
        Set<Order> orders = new HashSet<>(NUMBER_OF_ORDERS);

        System.out.println("Molimo Vas unesite koji će sve kategorije biti u Vašim restoranima.");
        insertCategories(categories, scanner);

        System.out.println("Molimo Vas unesite koji sve sastojci će biti u Vašim restoranima.");
        insertIngredients(ingredients, categories, scanner);

        System.out.println("Molimo Vas unesite koja će sve jela biti u Vašim restoranima.");
        insertMeals(meals, ingredients, categories, scanner);

        System.out.println("Molimo Vas unesite 3 kuhara koji će raditi.");
        insertChefs(chefs, scanner);

        System.out.println("Molimo Vas unesite 3 konobara");
        insertWaiters(waiters, scanner);

        System.out.println("Molimo vas unesite 3 dostavljača");
        insertDeliverers(deliverers, scanner);

        System.out.println("Molimo vas unesite 3 Restorana");
        insertRestaurants(scanner, restaurants, meals, chefs, waiters, deliverers);

        System.out.println("Molimo vas unesite naruđbu");
        insertOrders(scanner, orders, restaurants, meals, deliverers);
    }

    private static void insertCategories(Set<Category> categories, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + " kategorije: ");
            String categoryName = scanner.nextLine();

            System.out.println("Molimo unesite opis " + (i + 1) + " kategorije: ");
            String categoryDescription = scanner.nextLine();

            categories.add(new Category((long) i, categoryName, categoryDescription));
        }
        System.out.println("Hvala na unošenju kategorija!");
        System.out.println("Vaše kategorije su:");
        List<Category> categoryList = new ArrayList<>(categories);
        for (int i = 0; i < categories.size(); i++) {
            System.out.print("\n\t" + (i + 1) + ". " + categoryList.get(i).getName() + "\n");
        }
    }

    // TODO: Implementirati da kada korisnik ne unese točno ime kategorije da ga traži da ponovno unese
    private static void insertIngredients(Set<Ingredient> ingredients,
                                          Set<Category> categories,
                                          Scanner scanner) {

        for (int i = 0; i < NUMBER_OF_INGREDIENTS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + " sastojka: ");
            String ingredientName = scanner.nextLine();

            System.out.println("Molimo unesite koliko kalorija ima " + (i + 1) + " sastojak: ");
            BigDecimal kcal = BigDecimal.ZERO;
            boolean isInputValid = false;
            while (!isInputValid) {
                try {
                    kcal = scanner.nextBigDecimal();
                    processNumberInput(kcal.intValue());
                    isInputValid = true;
                } catch (InputMismatchException exception) {
                    System.out.println("Potrebno je unijeti broj, a vi ste unjeli drugaciji tip podataka");
                    scanner.next();
                } catch (NumberNotCorrectException exception) {
                    System.out.println(exception.getMessage());
                }
            }

            System.out.println("Molimo unesite na koji način se priprema " + (i + 1) + " sastojak: ");
            scanner.nextLine();
            String preparationMethod = scanner.nextLine();

            System.out.println("Molimo unesite u kojoj kategoriji jela se ovaj sastojak može koristiti: ");
            System.out.println("Ovo su sve kategorije: ");
            List<Category> categoryList = new ArrayList<>(categories);
            for (int j = 0; j < categories.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categoryList.get(i).getName() + "\n");
            }

            String categoryName = scanner.nextLine();
            Category choosenCategory = findCategoryByName(categories, categoryName);
            System.out.println("Ime odabrane kategorije: " + choosenCategory.getName());
            System.out.println("Opis odabrane kategorije: " + choosenCategory.getDescription());

            ingredients.add(new Ingredient(
                    (long) i,
                    ingredientName,
                    new Category((long) i, choosenCategory.getName(), choosenCategory.getDescription()),
                    kcal,
                    preparationMethod));
        }
    }

    private static void insertMeals(Set<Meal> meals,
                                    Set<Ingredient> ingredients,
                                    Set<Category> categories,
                                    Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_MEALS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + " jela: ");
            String mealName = scanner.nextLine();

            System.out.println("Molimo unesite cijenu " + (i + 1) + " jela: ");
            BigDecimal mealPrice = BigDecimal.ZERO;
            boolean isInputValid = false;
            while (!isInputValid) {
                try {
                    mealPrice = scanner.nextBigDecimal();
                    processNumberInput(mealPrice.intValue());
                    isInputValid = true;
                } catch (Exception exception) {
                    System.out.println("Molimo unesite broj, unijeli ste drugaciji tip podatka.");
                    scanner.next();
                }
            }

            System.out.println("Molimo unesite kojoj kategoriji " + (i + 1) + " jelo pripada: ");
            System.out.println("Ovo su sve kategorije: ");
            List<Category> categoryList = new ArrayList<>(categories);
            for (int j = 0; j < categories.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categoryList.get(i).getName() + "\n");
            }
            scanner.nextLine();
            String categoryName = scanner.nextLine();
            Category choosenCategory = findCategoryByName(categories, categoryName);

            System.out.println("Molimo unesite koji sve sastojci idu u ovo jelo: ");
            System.out.println("Ovo su svi sastojci: ");
            List<Ingredient> ingredientList = new ArrayList<>(ingredients);
            for (int j = 0; j < ingredients.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + ingredientList.get(i).getName() + "\n");
            }
            System.out.println("Koliko sastojaka želite unijeti: ");
            int ingredientsNumber = scanner.nextInt();
            Set<Ingredient> chosenIngredients = new HashSet<>(ingredientsNumber);
            for (int k = 0; k < ingredientsNumber; k++) {
                System.out.println("Molimo unesite ime " + (i + 1) + " sastojka: ");
                scanner.nextLine();
                String ingredientName = scanner.nextLine();
                Ingredient choosenIngredient = findIngredientByName(ingredients, ingredientName);
                chosenIngredients.add(choosenIngredient);
            }

            /*
            System.out.println("Vaši odabrani sastojci su: ");
            for (int h = 0; h < chosenIngredients.length; h++) {
                System.out.print("\n\t" + (h + 1) + ". " + chosenIngredients[h].getName() + "\n");
            }*/

            meals.add(new Meal((long) i, mealName, choosenCategory, chosenIngredients, mealPrice));
        }
    }

    private static void insertChefs(Set<Chef> chefs, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_CHEFS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". kuhara:");
            String chefFirstName = scanner.nextLine();

            System.out.println("Molimo unesite prezime " + (i + 1) + ". kuhara:");
            String chefLastName = scanner.nextLine();

            System.out.println("Molimo unesite plaću " + (i + 1) + ". kuhara:");

            BigDecimal chefSalary = BigDecimal.ZERO;
            boolean isInputValid = false;
            while (!isInputValid) {
                try {
                    chefSalary = scanner.nextBigDecimal();
                    processNumberInput(chefSalary.intValue());
                    isInputValid = true;
                } catch (Exception exception) {
                    System.out.println("Molimo unesite broj, unijeli ste drugaciji tip podatka.");
                    scanner.next();
                }
            }
            scanner.nextLine();

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = LocalDate.ofYearDay(2024, 360);
            Contract contract = new Contract(chefSalary, startDate, endDate, ContractType.FULL_TIME);
            Bonus bonus = new Bonus(1500);

            chefs.add(new Chef(chefFirstName, chefLastName, contract, bonus));
        }

        System.out.println("Ovo su kuhari koji rade u Vašim restoranima: ");
        List<Chef> chefList = new ArrayList<>(chefs);
        for (int i = 0; i < chefs.size(); i++) {
            System.out.println("Ime " + (i + 1) + ". kuhara: " + chefList.get(i).getFirstName() + " " + chefList.get(i).getLastName());
        }
    }

    private static void insertWaiters(Set<Waiter> waiters, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_WAITERS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". konobara:");
            String waiterFirstName = scanner.nextLine();

            System.out.println("Molimo unesite prezime " + (i + 1) + ". konobara:");
            String waiterLastName = scanner.nextLine();

            System.out.println("Molimo unesite plaću " + (i + 1) + ". konobara:");
            BigDecimal waiterSalary = BigDecimal.ZERO;
            boolean isValidInput = false;
            while (!isValidInput) {
                try {
                    waiterSalary = scanner.nextBigDecimal();
                    processNumberInput(waiterSalary.intValue());
                    isValidInput = true;
                } catch (Exception exception) {
                    System.out.println("Molimo unesite broj, unijeli ste drugačiji tip podatka.");
                    scanner.next();
                }
            }
            scanner.nextLine();

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = LocalDate.ofYearDay(2024, 360);
            Contract contract = new Contract(waiterSalary, startDate, endDate, ContractType.PART_TIME);
            Bonus bonus = new Bonus(500);

            waiters.add(new Waiter(waiterFirstName, waiterLastName, contract, bonus));
        }

        System.out.println("Ovo su konobari koji rade u Vašim restoranima: ");
        List<Waiter> waiterList = new ArrayList<>(waiters);
        for (int i = 0; i < waiters.size(); i++) {
            System.out.println("Ime " + (i + 1) + ". konobara: " + waiterList.get(i).getFirstName() + " " + waiterList.get(i).getLastName());
        }
    }

    private static void insertDeliverers(Set<Deliverer> deliverers, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_DELIVERERS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". dostavljača:");
            String delivererFirstName = scanner.nextLine();

            System.out.println("Molimo unesite prezime " + (i + 1) + ". dostavljača:");
            String delivererLastName = scanner.nextLine();

            System.out.println("Molimo unesite plaću " + (i + 1) + ". dostavljača:");
            BigDecimal delivererSalary = BigDecimal.ZERO;
            boolean isValidInput = false;
            while (!isValidInput) {
                try {
                    delivererSalary = scanner.nextBigDecimal();
                    processNumberInput(delivererSalary.intValue());
                    isValidInput = true;
                } catch (Exception exception) {
                    System.out.println("Molimo unesite broj, unijeli ste drugačiji tip podatka");
                    scanner.next();
                }
            }
            scanner.nextLine();

            LocalDate startDate = LocalDate.now();
            LocalDate endDate = LocalDate.ofYearDay(2024, 360);
            Contract contract = new Contract(delivererSalary, startDate, endDate, ContractType.FULL_TIME);
            Bonus bonus = new Bonus(2500);

            deliverers.add(new Deliverer(delivererFirstName, delivererLastName, contract, bonus));
        }

        System.out.println("Ovo su dostavljači koji rade u Vašim restoranima: ");
        List<Deliverer> delivererList = new ArrayList<>(deliverers);
        for (int i = 0; i < deliverers.size(); i++) {
            System.out.println("Ime " + (i + 1) + ". dostavljača: " + delivererList.get(i).getFirstName() + " " + delivererList.get(i).getLastName());
        }
    }

    private static void insertRestaurants(Scanner scanner,
                                          Set<Restaurant> restaurants,
                                          Set<Meal> meals,
                                          Set<Chef> chefs,
                                          Set<Waiter> waiters,
                                          Set<Deliverer> deliverers
    ) {
        for (int i = 0; i < NUMBER_OF_RESTAURANTS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". restorana: ");
            String restaurantName = scanner.nextLine();

            System.out.println("Molimo unesite ulicu " + (i + 1) + ". restorana: ");
            String streetName = scanner.nextLine();

            System.out.println("Molimo unesite broj zgrade " + (i + 1) + ". restorana: ");
            String houseNumber = scanner.nextLine();

            System.out.println("Molimo unesite ime grada u kojem se nalazi " + (i + 1) + ". restoran: ");
            String cityName = scanner.nextLine();

            System.out.println("Molimo unesite poštanski broj: " + (i + 1) + ". restorana: ");
            String postalCode = scanner.nextLine();

            Address address = new Address(streetName, houseNumber, cityName, postalCode);

            System.out.println("Molimo Vas unesite koje će jelo biti u Vašem " + (i + 1) + ". restoranu:");
            System.out.println("Ovo su sva moguća jela:");
            List<Meal> mealList = new ArrayList<>(meals);
            for (int j = 0; j < meals.size(); j++) {
                String mealName = mealList.get(i).getName();
                System.out.println("1. " + mealName);
            }

            String mealName = scanner.nextLine();
            Meal choosenMeal = findMealByName(meals, mealName);
            Set<Meal> chosenMeals = new HashSet<>(1);
            chosenMeals.add(choosenMeal);

            System.out.println("Molimo Vas unesite koji kuhar će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući kuhari: ");
            List<Chef> chefList = new ArrayList<>(chefs);
            for (int j = 0; j < chefs.size(); j++) {
                String chefName = chefList.get(i).getFirstName();
                System.out.println("1. " + chefName);
            }

            String chefName = scanner.nextLine();
            Chef chosenChef = findChefByName(chefs, chefName);
            Set<Chef> chosenChefs = new HashSet<>(1);
            chosenChefs.add(chosenChef);

            System.out.println("Molimo Vas unesite koji konobar će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući konobari: ");
            List<Waiter> waiterList = new ArrayList<>(waiters);
            for (int j = 0; j < waiters.size(); j++) {
                String waiterName = waiterList.get(i).getFirstName();
                System.out.println("1. " + waiterName);
            }

            String waiterName = scanner.nextLine();
            Waiter chosenWaiter = findWaiterByName(waiters, waiterName);
            Set<Waiter> chosenWaiters = new HashSet<>(1);
            chosenWaiters.add(chosenWaiter);

            System.out.println("Molimo Vas unesite koji dostavljač će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući dostavljači: ");
            List<Deliverer> delivererList = new ArrayList<>(deliverers);
            for (int j = 0; j < deliverers.size(); j++) {
                System.out.println("1. " + delivererList.get(i).getFirstName());
            }

            String delivererName = scanner.nextLine();
            Deliverer chosenDeliverer = findDelivererByName(deliverers, delivererName);
            Set<Deliverer> chosenDeliverers = new HashSet<>(1);
            chosenDeliverers.add(chosenDeliverer);

            restaurants.add(new Restaurant(
                    (long) i,
                    restaurantName,
                    address,
                    chosenMeals,
                    chosenChefs,
                    chosenWaiters,
                    chosenDeliverers));
        }
    }

    private static void insertOrders(Scanner scanner,
                                     Set<Order> orders,
                                     Set<Restaurant> restaurants,
                                     Set<Meal> meals,
                                     Set<Deliverer> deliverers) {

        System.out.println("Ovjde možete napraviti svoju naruđbu: ");
        for (int i = 0; i < NUMBER_OF_ORDERS; i++) {
            System.out.println("Iz kojeg restorana želite naručiti " + (i + 1) + ". naruđbu?");
            List<Restaurant> restaurantList = new ArrayList<>(restaurants);
            for (int j = 0; j < restaurants.size(); j++) {
                System.out.println("1. " + restaurantList.get(i).getName());
            }
            String restaurantName = scanner.nextLine();
            Restaurant chosenRestaurant = findRestaurantByName(restaurants, restaurantName);

            System.out.println("Koje jelo želite naručiti iz " + chosenRestaurant.getName() + " restorana?");
            System.out.println("Ovo su sva jela: ");
            List<Meal> mealList = new ArrayList<>(meals);
            for (int k = 0; k < meals.size(); k++) {
                System.out.println("1. " + mealList.get(i).getName());
            }
            String mealName = scanner.nextLine();
            Meal chosenMeal = findMealByName(meals, mealName);
            Set<Meal> chosenMeals = new HashSet<>(1);
            chosenMeals.add(chosenMeal);

            System.out.println("Koji dostavljač želite da Vam dostavi hranu?");
            System.out.println("Ovo su dostupni dostavljači: ");
            List<Deliverer> delivererList = new ArrayList<>(deliverers);
            for (int h = 0; h < deliverers.size(); h++) {
                System.out.println("1. " + delivererList.get(i).getFirstName());
            }
            String delivererName = scanner.nextLine();
            Deliverer chosenDeliverer = findDelivererByName(deliverers, delivererName);

            LocalDateTime deliveryDateAndTime = LocalDateTime.now();

            orders.add(new Order((long) i, chosenRestaurant, chosenMeals, chosenDeliverer, deliveryDateAndTime));
        }
    }

    private static Category findCategoryByName(Set<Category> categories, String name) {
        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
    }

    private static Ingredient findIngredientByName(Set<Ingredient> ingredients, String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null;
    }

    /**
     * Finds specific meal from meals list by iterating through meals list elements and
     * returns meal which mealLame corresponds to mealLame passed as a parameter.
     *
     * @param meals    List of all available meals in restaurant.
     * @param mealLame Name which we want to find in the list.
     * @return Meal which mealLame matches to mealLame in the parameter.
     */
    private static Meal findMealByName(Set<Meal> meals, String mealLame) {
        for (Meal meal : meals) {
            if (meal.getName().equalsIgnoreCase(mealLame)) {
                return meal;
            }
        }
        return null;
    }

    /**
     * Finds specific chef from chefs list by iterating through chefs list elements and
     * returns chef which name corresponds to name passed as a parameter.
     *
     * @param chefs    List of all available chefs in restaurant.
     * @param chefName Name which we want to find in the list.
     * @return Chef which name matches to name in the parameter.
     */
    private static Chef findChefByName(Set<Chef> chefs, String chefName) {
        for (Chef chef : chefs) {
            if (chef.getFirstName().equalsIgnoreCase(chefName)) {
                return chef;
            }
        }
        return null;
    }

    /**
     * Finds specific waiter from waiters list by iterating through waiter list elements and
     * returns waiter which name corresponds to name passed as a parameter.
     *
     * @param waiters    List of all available waiters in restaurant.
     * @param waiterName Name which we want to find in the list.
     * @return Waiter which name matches to name in the parameter.
     */
    private static Waiter findWaiterByName(Set<Waiter> waiters, String waiterName) {
        for (Waiter waiter : waiters) {
            if (waiter.getFirstName().equalsIgnoreCase(waiterName)) {
                return waiter;
            }
        }
        return null;
    }

    /**
     * Finds specific deliverer from deliverers list by iterating through deliverers list elements and
     * returns deliverer which name corresponds to name passed as a parameter.
     *
     * @param deliverers    List of all available deliverers in restaurant.
     * @param delivererName Name which we want to find in the list.
     * @return Deliverer which name matches to name in the parameter.
     */
    private static Deliverer findDelivererByName(Set<Deliverer> deliverers, String delivererName) {
        for (Deliverer deliverer : deliverers) {
            if (deliverer.getFirstName().equalsIgnoreCase(delivererName)) {
                return deliverer;
            }
        }
        return null;
    }

    /**
     * Finds specific restaurant from restaurants list by iterating through restaurant list elements and
     * returns restaurant which name corresponds to name passed as a parameter.
     *
     * @param restaurants    List of all available restaurants.
     * @param restaurantName Name which we want to find in the list.
     * @return Restaurant which name matches to name in the parameter.
     */
    private static Restaurant findRestaurantByName(Set<Restaurant> restaurants, String restaurantName) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equalsIgnoreCase(restaurantName)) {
                return restaurant;
            }
        }
        return null;
    }

    private static void processNumberInput(int input) throws NumberNotCorrectException {
        if (input <= 0) {
            throw new NumberNotCorrectException("Broj koji ste unijeli je 0 ili je manji od 0.");
        }
    }
}