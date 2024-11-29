package hr.java.production;

import hr.java.restaurant.exception.EntityAlreadyInsertedException;
import hr.java.restaurant.exception.NumberNotCorrectException;
import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static final int NUMBER_OF_CATEGORIES = 3;
    static final int NUMBER_OF_INGREDIENTS = 3;
    static final int NUMBER_OF_MEALS = 3;
    static final int NUMBER_OF_CHEFS = 3;
    static final int NUMBER_OF_WAITERS = 3;
    static final int NUMBER_OF_DELIVERERS = 3;
    static final int NUMBER_OF_RESTAURANTS = 3;
    static final int NUMBER_OF_ORDERS = 3;

    /**
     * Starting point of our application.
     * This function is first called when we start application.
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Category[] categories = new Category[NUMBER_OF_CATEGORIES];
        Ingredient[] ingredients = new Ingredient[NUMBER_OF_INGREDIENTS];
        Meal[] meals = new Meal[NUMBER_OF_MEALS];
        Chef[] chefs = new Chef[NUMBER_OF_CHEFS];
        Waiter[] waiters = new Waiter[NUMBER_OF_WAITERS];
        Deliverer[] deliverers = new Deliverer[NUMBER_OF_DELIVERERS];
        Restaurant[] restaurants = new Restaurant[NUMBER_OF_RESTAURANTS];
        Order[] orders = new Order[NUMBER_OF_ORDERS];

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

    /**
     * Function which contains logic for asking user to insert which categories will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param categories Categories field with default size which allows us to iterate through its size and store value.
     * @param scanner    Object which allows users to insert values through console.
     */
    private static void insertCategories(Category[] categories, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + " kategorije: ");
            String categoryName = "";
            boolean isInputValid = false;
            while (!isInputValid) {
                try {
                    categoryName = scanner.nextLine();
                    if (i > 0) processInputtedCategoryEntities(categories, categoryName);
                    isInputValid = true;
                } catch (EntityAlreadyInsertedException exception) {
                    System.out.println(exception.getMessage());
                }
            }

            System.out.println("Molimo unesite opis " + (i + 1) + " kategorije: ");
            String categoryDescription = scanner.nextLine();

            categories[i] = new Category((long) i, categoryName, categoryDescription);
        }
        System.out.println("Hvala na unošenju kategorija!");
        System.out.println("Vaše kategorije su:");
        for (int i = 0; i < categories.length; i++) {
            System.out.print("\n\t" + (i + 1) + ". " + categories[i].getName() + "\n");
        }
    }

    // TODO: Implementirati da kada korisnik ne unese točno ime kategorije da ga traži da ponovno unese

    /**
     * Function which contains logic for asking user to insert which ingredients will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param ingredients Ingredients field with default size to iterate through it.
     * @param categories  Categories field with default size to iterate through it.
     * @param scanner     Object which allows users to insert values through console.
     */
    private static void insertIngredients(Ingredient[] ingredients,
                                          Category[] categories,
                                          Scanner scanner) {

        for (int i = 0; i < NUMBER_OF_INGREDIENTS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + " sastojka: ");
            String ingredientName = "";
            boolean isNameInputValid = false;
            while (!isNameInputValid) {
                try {
                    ingredientName = scanner.nextLine();
                    if (i > 0) processInputtedIngredientEntities(ingredients, ingredientName);
                    isNameInputValid = true;
                } catch (EntityAlreadyInsertedException exception) {
                    System.out.println(exception.getMessage());
                }
            }

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
            for (int j = 0; j < categories.length; j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categories[j].getName() + "\n");
            }

            String categoryName = scanner.nextLine();
            Category choosenCategory = findCategoryByName(categories, categoryName);
            System.out.println("Ime odabrane kategorije: " + choosenCategory.getName());
            System.out.println("Opis odabrane kategorije: " + choosenCategory.getDescription());

            ingredients[i] = new Ingredient(
                    (long) i,
                    ingredientName,
                    new Category((long) i, choosenCategory.getName(), choosenCategory.getDescription()),
                    kcal,
                    preparationMethod);
        }
    }

    /**
     * Function which contains logic for asking user to insert which categories will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param meals       Meals field with default size to iterate through it.
     * @param ingredients Ingredients field with default size to iterate through it.
     * @param categories  Categories field with default size to iterate through it.
     * @param scanner     Object which allows users to insert values through console.
     */
    private static void insertMeals(Meal[] meals,
                                    Ingredient[] ingredients,
                                    Category[] categories,
                                    Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_MEALS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + " jela: ");
            String mealName = "";
            boolean isInputNameValid = false;
            while (!isInputNameValid) {
                try {
                    mealName = scanner.nextLine();
                    if (i > 0) processInputtedMealEntities(meals, mealName);
                    isInputNameValid = true;
                } catch (EntityAlreadyInsertedException exception) {
                    System.out.println(exception.getMessage());
                }
            }

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
            for (int j = 0; j < categories.length; j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categories[j].getName() + "\n");
            }
            scanner.nextLine();
            String categoryName = scanner.nextLine();
            Category choosenCategory = findCategoryByName(categories, categoryName);

            System.out.println("Molimo unesite koji sve sastojci idu u ovo jelo: ");
            System.out.println("Ovo su svi sastojci: ");
            for (int j = 0; j < ingredients.length; j++) {
                System.out.print("\n\t" + (j + 1) + ". " + ingredients[j].getName() + "\n");
            }
            System.out.println("Koliko sastojaka želite unijeti: ");
            int ingredientsNumber = scanner.nextInt();
            Ingredient[] chosenIngredients = new Ingredient[ingredientsNumber];
            for (int k = 0; k < ingredientsNumber; k++) {
                System.out.println("Molimo unesite ime " + (i + 1) + " sastojka: ");
                scanner.nextLine();
                String ingredientName = scanner.nextLine();
                Ingredient choosenIngredient = findIngredientByName(ingredients, ingredientName);
                chosenIngredients[k] = choosenIngredient;
            }

            /*
            System.out.println("Vaši odabrani sastojci su: ");
            for (int h = 0; h < chosenIngredients.length; h++) {
                System.out.print("\n\t" + (h + 1) + ". " + chosenIngredients[h].getName() + "\n");
            }*/

            meals[i] = new Meal((long) i, mealName, choosenCategory, chosenIngredients, mealPrice);
        }
    }

    /**
     * Function which contains logic for asking user to insert which chefs will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param chefs   Chefs field with default size to iterate through it.
     * @param scanner Object which allows users to insert values through console.
     */
    private static void insertChefs(Chef[] chefs, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_CHEFS; i++) {
            String chefFirstName = "";
            String chefLastName = "";
            boolean isChefNameValid = false;
            while (!isChefNameValid) {
                try {
                    System.out.println("Molimo unesite ime " + (i + 1) + ". kuhara:");
                    chefFirstName = scanner.nextLine();

                    System.out.println("Molimo unesite prezime " + (i + 1) + ". kuhara:");
                    chefLastName = scanner.nextLine();
                    if (i > 0) processInputtedChefName(chefs, chefFirstName, chefLastName);
                    isChefNameValid = true;
                } catch (EntityAlreadyInsertedException exception) {
                    System.out.println(exception.getMessage());
                }
            }

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
            Contract contract = new Contract(chefSalary, startDate, endDate, Contract.ContractType.FULL_TIME);
            Bonus bonus = new Bonus(1500);

            chefs[i] = new Chef(chefFirstName, chefLastName, contract, bonus);
        }

        System.out.println("Ovo su kuhari koji rade u Vašim restoranima: ");
        for (int i = 0; i < chefs.length; i++) {
            System.out.println("Ime " + (i + 1) + ". kuhara: " + chefs[i].getFirstName() + " " + chefs[i].getLastName());
        }
    }

    /**
     * Function which contains logic for asking user to insert which waiters will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param waiters Waiters field with default size to iterate through it.
     * @param scanner Object which allows users to insert values through console.
     */
    private static void insertWaiters(Waiter[] waiters, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_WAITERS; i++) {
            String waiterFirstName = "";
            String waiterLastName = "";
            boolean isInputNameValid = false;
            while (!isInputNameValid) {
                try {
                    System.out.println("Molimo unesite ime " + (i + 1) + ". konobara:");
                    waiterFirstName = scanner.nextLine();

                    System.out.println("Molimo unesite prezime " + (i + 1) + ". konobara:");
                    waiterLastName = scanner.nextLine();

                    if (i > 0) processInputtedWaiterName(waiters, waiterFirstName, waiterLastName);
                    isInputNameValid = true;
                } catch (EntityAlreadyInsertedException exception) {
                    System.out.println(exception.getMessage());
                }
            }

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
            Contract contract = new Contract(waiterSalary, startDate, endDate, Contract.ContractType.PART_TIME);
            Bonus bonus = new Bonus(500);

            waiters[i] = new Waiter(waiterFirstName, waiterLastName, contract, bonus);
        }

        System.out.println("Ovo su konobari koji rade u Vašim restoranima: ");
        for (int i = 0; i < waiters.length; i++) {
            System.out.println("Ime " + (i + 1) + ". konobara: " + waiters[i].getFirstName() + " " + waiters[i].getLastName());
        }
    }

    /**
     * Function which contains logic for asking user to insert which deliverers will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param deliverers Deliverers field with default size to iterate through it.
     * @param scanner    Object which allows users to insert values through console.
     */
    private static void insertDeliverers(Deliverer[] deliverers, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_DELIVERERS; i++) {
            String delivererFirstName = "";
            String delivererLastName = "";
            boolean isInputNameValid = false;

            while (!isInputNameValid) {
                try {
                    System.out.println("Molimo unesite ime " + (i + 1) + ". dostavljača:");
                    delivererFirstName = scanner.nextLine();

                    System.out.println("Molimo unesite prezime " + (i + 1) + ". dostavljača:");
                    delivererLastName = scanner.nextLine();

                    if (i > 0) processInputtedDelivererName(deliverers, delivererFirstName, delivererLastName);
                    isInputNameValid = true;
                } catch (EntityAlreadyInsertedException exception) {
                    System.out.println(exception.getMessage());
                }
            }

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
            Contract contract = new Contract(delivererSalary, startDate, endDate, Contract.ContractType.FULL_TIME);
            Bonus bonus = new Bonus(2500);

            deliverers[i] = new Deliverer(delivererFirstName, delivererLastName, contract, bonus);
        }

        System.out.println("Ovo su dostavljači koji rade u Vašim restoranima: ");
        for (int i = 0; i < deliverers.length; i++) {
            System.out.println("Ime " + (i + 1) + ". dostavljača: " + deliverers[i].getFirstName() + " " + deliverers[i].getLastName());
        }
    }

    /**
     * Function which is used for inserting data about specific restaurant.
     *
     * @param scanner     Object which allows users to insert values through console.
     * @param restaurants Restaurants field with default size to iterate through it.
     * @param meals       Meals field with default size to iterate through it.
     * @param chefs       Chefs field with default size to iterate through it.
     * @param waiters     Waiters field with default size to iterate through it.
     * @param deliverers  Deliverers field with default size to iterate through it.
     */
    private static void insertRestaurants(Scanner scanner,
                                          Restaurant[] restaurants,
                                          Meal[] meals,
                                          Chef[] chefs,
                                          Waiter[] waiters,
                                          Deliverer[] deliverers
    ) {
        for (int i = 0; i < NUMBER_OF_RESTAURANTS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". restorana: ");
            String restaurantName = "";
            boolean isInputNameValid = false;

            while (!isInputNameValid) {
                try {
                    restaurantName = scanner.nextLine();
                    if (i > 0) processInputtedRestaurantEntities(restaurants, restaurantName);
                    isInputNameValid = true;
                } catch (EntityAlreadyInsertedException exception) {
                    System.out.println(exception.getMessage());
                }
            }

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
            for (int j = 0; j < meals.length; j++) {
                String mealName = meals[i].getName();
                System.out.println("1. " + mealName);
            }

            String mealName = scanner.nextLine();
            Meal choosenMeal = findMealByName(meals, mealName);
            Meal[] chosenMeals = new Meal[1];
            chosenMeals[0] = choosenMeal;

            System.out.println("Molimo Vas unesite koji kuhar će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući kuhari: ");
            for (int j = 0; j < chefs.length; j++) {
                String chefName = chefs[i].getFirstName();
                System.out.println("1. " + chefName);
            }

            String chefName = scanner.nextLine();
            Chef chosenChef = findChefByName(chefs, chefName);
            Chef[] chosenChefs = new Chef[1];
            chosenChefs[0] = chosenChef;

            System.out.println("Molimo Vas unesite koji konobar će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući konobari: ");
            for (int j = 0; j < waiters.length; j++) {
                String waiterName = waiters[i].getFirstName();
                System.out.println("1. " + waiterName);
            }

            String waiterName = scanner.nextLine();
            Waiter chosenWaiter = findWaiterByName(waiters, waiterName);
            Waiter[] chosenWaiters = new Waiter[1];
            chosenWaiters[0] = chosenWaiter;

            System.out.println("Molimo Vas unesite koji dostavljač će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući dostavljači: ");
            for (int j = 0; j < deliverers.length; j++) {
                System.out.println("1. " + deliverers[i].getFirstName());
            }

            String delivererName = scanner.nextLine();
            Deliverer chosenDeliverer = findDelivererByName(deliverers, delivererName);
            Deliverer[] chosenDeliverers = new Deliverer[1];
            chosenDeliverers[0] = chosenDeliverer;

            restaurants[i] = new Restaurant(
                    (long) i,
                    restaurantName,
                    address,
                    chosenMeals,
                    chosenChefs,
                    chosenWaiters,
                    chosenDeliverers);
        }
    }

    /**
     * Function which is used for inserting data about specific order.
     *
     * @param scanner     Object which allows users to insert value through console.
     * @param orders      Orders field with default size to iterate through it.
     * @param restaurants Restaurants field with default size to iterate through it.
     * @param meals       Meals field with default size to iterate through it.
     * @param deliverers  Deliverers field with default size to iterate through it.
     */
    private static void insertOrders(Scanner scanner,
                                     Order[] orders,
                                     Restaurant[] restaurants,
                                     Meal[] meals,
                                     Deliverer[] deliverers) {

        System.out.println("Ovjde možete napraviti svoju naruđbu: ");
        for (int i = 0; i < NUMBER_OF_ORDERS; i++) {
            System.out.println("Iz kojeg restorana želite naručiti " + (i + 1) + ". naruđbu?");
            for (int j = 0; j < restaurants.length; j++) {
                System.out.println("1. " + restaurants[j].getName());
            }
            String restaurantName = scanner.nextLine();
            Restaurant chosenRestaurant = findRestaurantByName(restaurants, restaurantName);

            System.out.println("Koje jelo želite naručiti iz " + chosenRestaurant.getName() + " restorana?");
            System.out.println("Ovo su sva jela: ");
            for (int k = 0; k < meals.length; k++) {
                System.out.println("1. " + meals[k].getName());
            }
            String mealName = scanner.nextLine();
            Meal chosenMeal = findMealByName(meals, mealName);
            Meal[] chosenMeals = new Meal[1];
            chosenMeals[0] = chosenMeal;

            System.out.println("Koji dostavljač želite da Vam dostavi hranu?");
            System.out.println("Ovo su dostupni dostavljači: ");
            for (int h = 0; h < deliverers.length; h++) {
                System.out.println("1. " + deliverers[h].getFirstName());
            }
            String delivererName = scanner.nextLine();
            Deliverer chosenDeliverer = findDelivererByName(deliverers, delivererName);

            LocalDateTime deliveryDateAndTime = LocalDateTime.now();

            orders[i] = new Order((long) i, chosenRestaurant, chosenMeals, chosenDeliverer, deliveryDateAndTime);
        }
    }

    /**
     * Finds specific category from category list by iterating through category list elements and
     * returns category which categoryName corresponds to categoryName passed as a parameter.
     *
     * @param categories     List of all available categories in restaurant.
     * @param categoriesName Name which we want to find in the list.
     * @return Category which categoryName matches to categoryName in the parameter.
     */
    private static Category findCategoryByName(Category[] categories, String categoriesName) {
        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(categoriesName)) {
                return category;
            }
        }
        return null;
    }

    /**
     * Finds specific ingredient from ingredients list by iterating through ingredients list elements and
     * returns ingredient which ingredientName corresponds to ingredientName passed as a parameter.
     *
     * @param ingredients    List of all available meals in restaurant.
     * @param ingredientName Name which we want to find in the list.
     * @return Meal which mealLame matches to mealLame in the parameter.
     */
    private static Ingredient findIngredientByName(Ingredient[] ingredients, String ingredientName) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(ingredientName)) {
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
    private static Meal findMealByName(Meal[] meals, String mealLame) {
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
    private static Chef findChefByName(Chef[] chefs, String chefName) {
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
    private static Waiter findWaiterByName(Waiter[] waiters, String waiterName) {
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
    private static Deliverer findDelivererByName(Deliverer[] deliverers, String delivererName) {
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
    private static Restaurant findRestaurantByName(Restaurant[] restaurants, String restaurantName) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equalsIgnoreCase(restaurantName)) {
                return restaurant;
            }
        }
        return null;
    }

    /**
     * Function which checks value of input number and if number is zero or negative
     * throws NumberNotCorrectException.
     *
     * @param input Input number which we want to process
     * @throws NumberNotCorrectException If number is zero or negative
     */
    private static void processNumberInput(int input) throws NumberNotCorrectException {
        if (input <= 0) {
            throw new NumberNotCorrectException("Broj koji ste unijeli je 0 ili je manji od 0.");
        }
    }

    /**
     * Function which checks if entity which user is inserting has already been inserted.
     *
     * @param categoryName Input string of specific entity.
     * @param categories   Field which contains all categories which user already inserted.
     * @throws EntityAlreadyInsertedException If entity has already been inserted.
     */
    private static void processInputtedCategoryEntities(Category[] categories, String categoryName) throws EntityAlreadyInsertedException {
        for (Category category : categories) {
            if (category != null && category.getName().equals(categoryName)) {
                throw new EntityAlreadyInsertedException("Kategorija sa imenom '" + categoryName + "' već postoji. Molimo unesite kategoriju sa drugim imenom.");
            }
        }
    }

    /**
     * Function which checks if entity which user is inserting has already been inserted.
     *
     * @param categoryName Input string of specific entity.
     * @param ingredients  Field which contains all ingredients which user already inserted.
     * @throws EntityAlreadyInsertedException If entity has already been inserted.
     */
    private static void processInputtedIngredientEntities(Ingredient[] ingredients, String categoryName) throws EntityAlreadyInsertedException {
        for (Ingredient ingredient : ingredients) {
            if (ingredient != null && ingredient.getName().equals(categoryName)) {
                throw new EntityAlreadyInsertedException("Sastojak sa imenom '" + categoryName + "' već postoji. Molimo unesite sastojak sa drugim imenom.");
            }
        }
    }

    /**
     * Function which checks if entity which user is inserting has already been inserted.
     *
     * @param meals    Field which contains all meals which user already inserted.
     * @param mealName Input string of specific entity.
     * @throws EntityAlreadyInsertedException If entity has already been inserted.
     */
    private static void processInputtedMealEntities(Meal[] meals, String mealName) throws EntityAlreadyInsertedException {
        for (Meal meal : meals) {
            if (meal != null && meal.getName().equals(mealName)) {
                throw new EntityAlreadyInsertedException("Sastojak sa imenom '" + mealName + "' već postoji. Molimo unesite sastojak sa drugim imenom.");
            }
        }
    }

    /**
     * Function which checks if entity which user is inserting has already been inserted.
     *
     * @param chefs     Field which contains all chefs which user has already inserted.
     * @param firstName First name of chef which user inserted.
     * @param lastName  Last name of chef which user inserted.
     * @throws EntityAlreadyInsertedException If entity with that first name and last name is already inserted.
     */
    private static void processInputtedChefName(Chef[] chefs, String firstName, String lastName) throws EntityAlreadyInsertedException {
        for (Chef chef : chefs) {
            if (chef != null && chef.getFirstName().equals(firstName) && chef.getLastName().equals(lastName)) {
                throw new EntityAlreadyInsertedException("Kuhar sa imenom " + firstName + " " + lastName + " već postoji. Molimo unesite kuhara sa drugim imenom.");
            }
        }
    }

    /**
     * Function which checks if entity which user is inserting has already been inserted.
     *
     * @param waiters   Field which contains all waiters which user has already inserted.
     * @param firstName First name of waiter which user inserted.
     * @param lastName  Last name of waiter which user inserted.
     * @throws EntityAlreadyInsertedException If entity with that first name and last name is already inserted.
     */
    private static void processInputtedWaiterName(Waiter[] waiters, String firstName, String lastName) throws EntityAlreadyInsertedException {
        for (Waiter waiter : waiters) {
            if (waiter != null && waiter.getFirstName().equals(firstName) && waiter.getLastName().equals(lastName)) {
                throw new EntityAlreadyInsertedException("Kuhar sa imenom " + firstName + " " + lastName + " već postoji. Molimo unesite kuhara sa drugim imenom.");
            }
        }
    }

    /**
     * Function which checks if entity which user is inserting has already been inserted.
     *
     * @param deliverers Field which contains all deliverers which user has already inserted.
     * @param firstName  First name of deliverers which user inserted.
     * @param lastName   Last name of deliverers which user inserted.
     * @throws EntityAlreadyInsertedException If entity with that first name and last name is already inserted.
     */
    private static void processInputtedDelivererName(Deliverer[] deliverers, String firstName, String lastName) throws EntityAlreadyInsertedException {
        for (Deliverer deliverer : deliverers) {
            if (deliverer != null && deliverer.getFirstName().equals(firstName) && deliverer.getLastName().equals(lastName)) {
                throw new EntityAlreadyInsertedException("Kuhar sa imenom " + firstName + " " + lastName + " već postoji. Molimo unesite kuhara sa drugim imenom.");
            }
        }
    }

    /**
     * Function which checks if entity which user is inserting has already been inserted.
     *
     * @param restaurants    Field which contains all restaurants which user has already inserted.
     * @param restaurantName Name of restaurant which user already inserted.
     * @throws EntityAlreadyInsertedException If entity with that first name and last name is already inserted.
     */
    private static void processInputtedRestaurantEntities(Restaurant[] restaurants, String restaurantName) throws EntityAlreadyInsertedException {
        for (Restaurant restaurant : restaurants) {
            if (restaurant != null && restaurant.getName().equals(restaurantName)) {
                throw new EntityAlreadyInsertedException("Restoran sa imenom " + restaurantName + " već postoji. Molimo unesite kuhara sa drugim imenom.");
            }
        }
    }
}