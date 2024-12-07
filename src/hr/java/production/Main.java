package hr.java.production;

import hr.java.restaurant.enumeration.ContractType;
import hr.java.restaurant.exception.EntityAlreadyInsertedException;
import hr.java.restaurant.exception.NumberNotCorrectException;
import hr.java.restaurant.generics.RestaurantLabourExchangeOffice;
import hr.java.restaurant.model.*;
import hr.java.restaurant.sort.EmployeesByContractLenghtSorter;
import hr.java.restaurant.sort.EmployeesBySalarySorter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    static final int NUMBER_OF_CATEGORIES = 3;
    static final int NUMBER_OF_INGREDIENTS = 3;
    static final int NUMBER_OF_MEALS = 3;
    static final int NUMBER_OF_CHEFS = 3;
    static final int NUMBER_OF_WAITERS = 3;
    static final int NUMBER_OF_DELIVERERS = 3;
    static final int NUMBER_OF_RESTAURANTS = 3;
    static final int NUMBER_OF_ORDERS = 3;
    static final int NUMBER_OF_VEGAN_MEALS = 3;
    static final int NUMBER_OF_VEGETARIAN_MEALS = 3;
    static final int NUMBER_OF_MEAT_MEALS = 3;

    /**
     * Starting point of our application.
     * This function is first called when we start application.
     */
    public static void main(String[] args) {
        List<Address> addresses = getAddressesFromFile();
        List<Bonus> bonuses = getBonusesFromFile();
        List<Category> categories = getCategoriesFromFile();
        List<Contract> contracts = getContractsFromFile();
        List<Chef> chefs = getChefsFromFile(contracts, bonuses);
        List<Waiter> waiters = getWaitersFromFile(contracts, bonuses);
        List<Deliverer> deliverers = getDeliverersFromFile(contracts, bonuses);
        List<Ingredient> ingredients = getIngredientsFromFile(categories);
        List<Meal> meals = getMealsFromFile(categories, ingredients);
        List<Restaurant> restaurants = getRestaurantsFromFile(addresses, meals, chefs, waiters, deliverers);
        List<Order> orders = getOrdersFromFile(restaurants, meals, deliverers);

        for (Order order : orders) {
            System.out.println("Id --> " + order.getId());
            System.out.println("Restaurant --> " + order.getRestaurant().getName());
            System.out.println("Meal --> " + order.getMeals().getFirst().getName());
            System.out.println("Deliverer --> " + order.getDeliverer().getFirstName());
            System.out.println("Delivery date and time --> " + order.getDeliveryDateAndTime());
        }

        /*
        Scanner scanner = new Scanner(System.in);
        Set<Category> categories = new HashSet<>(NUMBER_OF_CATEGORIES);
        Set<Ingredient> ingredients = new HashSet<>(NUMBER_OF_INGREDIENTS);
        Set<Meal> meals = new HashSet<>(NUMBER_OF_MEALS);
        Set<Chef> chefs = new HashSet<>(NUMBER_OF_CHEFS);
        Set<Waiter> waiters = new HashSet<>(NUMBER_OF_WAITERS);
        Set<Deliverer> deliverers = new HashSet<>(NUMBER_OF_DELIVERERS);
        Set<Restaurant> restaurants = new HashSet<>(NUMBER_OF_RESTAURANTS);
        Set<Order> orders = new HashSet<>(NUMBER_OF_ORDERS);
        Set<VeganMeal> veganMeals = new HashSet<>(NUMBER_OF_VEGAN_MEALS);
        Set<VegetarianMeal> vegetarianMeals = new HashSet<>(NUMBER_OF_VEGETARIAN_MEALS);
        Set<MeatMeal> meatMeals = new HashSet<>(NUMBER_OF_MEAT_MEALS);
        RestaurantLabourExchangeOffice<Restaurant> restaurantLabourExchangeOffice = new RestaurantLabourExchangeOffice<>(restaurants);

        System.out.println("Molimo Vas unesite koji će sve kategorije biti u Vašim restoranima.");
        insertCategories(categories, scanner);

        System.out.println("Molimo Vas unesite koji sve sastojci će biti u Vašim restoranima.");
        insertIngredients(ingredients, categories, scanner);

        System.out.println("Molimo Vas unesite koja će sve jela biti u Vašim restoranima.");
        insertMeals(meals, ingredients, categories, scanner);

        System.out.println("Molimo Vas unesite koja će sve veganska jela biti u Vašim restoranima.");
        insertVeganMeals(scanner, veganMeals, categories, ingredients);

        System.out.println("Molimo Vas unesite koja će sve vegetarijanska jela biti u Vašim restoranima.");
        insertVegetarianMeals(scanner, vegetarianMeals, categories, ingredients);

        System.out.println("Molimo Vas unesite koja će sve mesna jela biti u Vašim restoranima.");
        insertMeatMeals(scanner, meatMeals, categories, ingredients);

        System.out.println("Ovo je jelo koje ima najveći broj kilokalorija: ");
        System.out.println(getMealWithMostCalories(veganMeals, vegetarianMeals, meatMeals));

        System.out.println("Ovo je jelo koje ima najmanji broj kilokalorija: ");
        System.out.println(getMealWithLeastCalories(veganMeals, vegetarianMeals, meatMeals));

        System.out.println("Molimo Vas unesite 3 kuhara koji će raditi.");
        insertChefs(chefs, scanner);

        System.out.println("Molimo Vas unesite 3 konobara");
        insertWaiters(waiters, scanner);

        System.out.println("Molimo vas unesite 3 dostavljača");
        insertDeliverers(deliverers, scanner);

        System.out.println("Ovo je raspored svih zaposlenih po iznosu plaće or najveće prema najmanjoj: ");
        sortEmployeesBySalary(chefs, waiters, deliverers);

        System.out.println("Ovo je raspored svih zaposlenih po broju dana u restoranu: ");
        sortEmployeesByDaysWorking(chefs, waiters, deliverers);

        System.out.println(getEmployeeWithBiggestSalary(chefs, waiters, deliverers));
        System.out.println(getEmployeeWithLongestContract(chefs, waiters, deliverers));

        System.out.println("Molimo vas unesite 3 Restorana");
        insertRestaurants(scanner, restaurants, restaurantLabourExchangeOffice, meals, chefs, waiters, deliverers);

        System.out.println("Molimo vas unesite naruđbu");
        insertOrders(scanner, orders, restaurantLabourExchangeOffice, meals, deliverers);

        System.out.println("Restoran sa najskupljom naruđbom je: " + Arrays.toString(findRestaurantsWithBiggestOrder(orders)));
        System.out.println("Dostavljač s najviše dostava je: " + Arrays.toString(findDelivererWithMostDeliveries(orders)));

        findRestaurantWithMostEmployees(restaurants);
        findMostCommonMeal(restaurants);
        displayIngredientsForMeals(orders);
        displayTotalOrderPrice(orders);
        displayRestaurantsAtAddress(restaurants);
         */
    }

    /**
     * Function which contains logic for asking user to insert which categories will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param categories Categories field with default size which allows us to iterate through its size and store value.
     * @param scanner    Object which allows users to insert values through console.
     */
    private static void insertCategories(Set<Category> categories, Scanner scanner) {
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

            categories.add(new Category((long) i, categoryName, categoryDescription));
        }
        System.out.println("Hvala na unošenju kategorija!");
        System.out.println("Vaše kategorije su:");
        List<Category> categoryList = new ArrayList<>(categories);
        for (int i = 0; i < categories.size(); i++) {
            System.out.print("\n\t" + (i + 1) + ". " + categoryList.get(i).getName() + "\n");
        }
    }

    /**
     * Function which contains logic for asking user to insert which ingredients will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param ingredients Ingredients field with default size to iterate through it.
     * @param categories  Categories field with default size to iterate through it.
     * @param scanner     Object which allows users to insert values through console.
     */
    private static void insertIngredients(Set<Ingredient> ingredients,
                                          Set<Category> categories,
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
            List<Category> categoryList = new ArrayList<>(categories);
            for (int j = 0; j < categories.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categoryList.get(j).getName() + "\n");
            }

            System.out.println("Ovdje upišite kojoj kategoriji pripada ovaj sastojak: ");
            String categoryName = scanner.nextLine();
            Category choosenCategory = findCategoryByName(categories, categoryName);
            System.out.println("Ime odabrane kategorije: " + choosenCategory.getName());

            ingredients.add(new Ingredient(
                    (long) i,
                    ingredientName,
                    new Category((long) i, choosenCategory.getName(), choosenCategory.getDescription()),
                    kcal,
                    preparationMethod));
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
    private static void insertMeals(Set<Meal> meals,
                                    Set<Ingredient> ingredients,
                                    Set<Category> categories,
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
            List<Category> categoryList = new ArrayList<>(categories);
            for (int j = 0; j < categories.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categoryList.get(j).getName() + "\n");
            }
            scanner.nextLine();
            System.out.println("Ovdje unesite kategoriju jela: ");
            String categoryName = scanner.nextLine();
            Category choosenCategory = findCategoryByName(categories, categoryName);

            System.out.println("Molimo unesite koji sve sastojci idu u ovo jelo: ");
            System.out.println("Ovo su svi sastojci: ");
            List<Ingredient> ingredientList = new ArrayList<>(ingredients);
            for (int j = 0; j < ingredients.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + ingredientList.get(j).getName() + "\n");
            }
            System.out.println("Koliko sastojaka želite unijeti: ");
            int ingredientsNumber = scanner.nextInt();
            List<Ingredient> chosenIngredients = new ArrayList<>(ingredientsNumber);
            for (int k = 0; k < ingredientsNumber; k++) {
                System.out.println("Molimo unesite ime " + (i + 1) + " sastojka: ");
                scanner.nextLine();
                String ingredientName = scanner.nextLine();
                Ingredient choosenIngredient = findIngredientByName(ingredients, ingredientName);
                chosenIngredients.add(choosenIngredient);
            }

            meals.add(new Meal((long) i, mealName, choosenCategory, chosenIngredients, mealPrice));
        }
    }

    /**
     * Function which contains logic for inserting and storing details about vegan meals
     * which user inserted.
     *
     * @param scanner     Object which allows users to insert data.
     * @param veganMeals  All vegan meals which user has already inserted.
     * @param categories  All categories which user has already inserted.
     * @param ingredients All ingredients which user has already inserted.
     */
    private static void insertVeganMeals(Scanner scanner,
                                         Set<VeganMeal> veganMeals,
                                         Set<Category> categories,
                                         Set<Ingredient> ingredients) {
        for (int i = 0; i < NUMBER_OF_VEGAN_MEALS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". veganskog jela: ");
            String veganMealName = scanner.nextLine();

            System.out.println("Molimo unesite kojoj kategoriji " + (i + 1) + " jelo pripada: ");
            System.out.println("Ovo su sve kategorije: ");
            List<Category> categoryList = new ArrayList<>(categories);
            for (int j = 0; j < categories.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categoryList.get(j).getName() + "\n");
            }

            scanner.nextLine();
            System.out.println("Ovdje unesite kategoriju jela: ");
            String categoryName = scanner.nextLine();
            Category choosenCategory = findCategoryByName(categories, categoryName);

            System.out.println("Molimo unesite koji sve sastojci idu u ovo jelo: ");
            System.out.println("Ovo su svi sastojci: ");
            List<Ingredient> ingredientList = new ArrayList<>(ingredients);
            for (int j = 0; j < ingredients.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + ingredientList.get(j).getName() + "\n");
            }
            System.out.println("Koliko sastojaka želite unijeti: ");
            int ingredientsNumber = scanner.nextInt();

            List<Ingredient> chosenIngredients = new ArrayList<>(ingredientsNumber);
            for (int k = 0; k < ingredientsNumber; k++) {
                System.out.println("Molimo unesite ime " + (i + 1) + " sastojka: ");
                scanner.nextLine();
                String ingredientName = scanner.nextLine();
                Ingredient choosenIngredient = findIngredientByName(ingredients, ingredientName);
                chosenIngredients.add(choosenIngredient);
            }

            System.out.println("Molimo unesite cijenu " + (i + 1) + " jela: ");
            BigDecimal mealPrice = scanner.nextBigDecimal();

            System.out.println("Molimo unesite koliko različitih vrsta salata ide u " + (i + 1) + ". vegansko jelo: ");
            int numberOfSalads = scanner.nextInt();
            scanner.nextLine();

            veganMeals.add(new VeganMeal((i + 1L), veganMealName, choosenCategory, chosenIngredients, mealPrice, numberOfSalads));
        }
    }

    /**
     * Function which contains logic for inserting and storing details about vegetarian meals
     * which user inserted.
     *
     * @param scanner         Object which allows users to insert data.
     * @param vegetarianMeals All vegetarian meals which user has already inserted.
     * @param categories      All categories which user has already inserted.
     * @param ingredients     All ingredients which user has already inserted.
     */
    private static void insertVegetarianMeals(Scanner scanner,
                                              Set<VegetarianMeal> vegetarianMeals,
                                              Set<Category> categories,
                                              Set<Ingredient> ingredients) {
        for (int i = 0; i < NUMBER_OF_VEGETARIAN_MEALS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". vegetarijanskog jela: ");
            String vegetarianMealName = scanner.nextLine();

            System.out.println("Molimo unesite kojoj kategoriji " + (i + 1) + " jelo pripada: ");
            System.out.println("Ovo su sve kategorije: ");
            List<Category> categoryList = new ArrayList<>(categories);
            for (int j = 0; j < categories.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categoryList.get(j).getName() + "\n");
            }
            scanner.nextLine();
            System.out.println("Ovdje unesite kategoriju jela: ");
            String categoryName = scanner.nextLine();
            Category choosenCategory = findCategoryByName(categories, categoryName);

            System.out.println("Molimo unesite koji sve sastojci idu u ovo jelo: ");
            System.out.println("Ovo su svi sastojci: ");
            List<Ingredient> ingredientList = new ArrayList<>(ingredients);
            for (int j = 0; j < ingredients.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + ingredientList.get(j).getName() + "\n");
            }
            System.out.println("Koliko sastojaka želite unijeti: ");
            int ingredientsNumber = scanner.nextInt();
            List<Ingredient> chosenIngredients = new ArrayList<>(ingredientsNumber);
            for (int k = 0; k < ingredientsNumber; k++) {
                System.out.println("Molimo unesite ime " + (i + 1) + " sastojka: ");
                scanner.nextLine();
                String ingredientName = scanner.nextLine();
                Ingredient choosenIngredient = findIngredientByName(ingredients, ingredientName);
                chosenIngredients.add(choosenIngredient);
            }

            System.out.println("Molimo unesite cijenu " + (i + 1) + " jela: ");
            BigDecimal mealPrice = scanner.nextBigDecimal();

            System.out.println("Molimo unesite sadrži li " + (i + 1) + ". vegetarijansko jelo jaja. Unesite broj pokraj točnog odgovora.");
            System.out.println("1. Ovo jelo SADRŽI jaja.");
            System.out.println("2. Ovo jelo NE SADRŽI jaja.");
            boolean containsEggs = doesVegetarianMealContainsEggs(scanner);

            vegetarianMeals.add(new VegetarianMeal((i + 1L), vegetarianMealName, choosenCategory, chosenIngredients, mealPrice, containsEggs));
        }
    }

    /**
     * Function which contains logic for inserting and storing details about meat meals
     * which user inserted.
     *
     * @param scanner     Object which allows users to insert data.
     * @param meatMeals   All vegan meals which user has already inserted.
     * @param categories  All categories which user has already inserted.
     * @param ingredients All ingredients which user has already inserted.
     */
    private static void insertMeatMeals(Scanner scanner,
                                        Set<MeatMeal> meatMeals,
                                        Set<Category> categories,
                                        Set<Ingredient> ingredients) {
        for (int i = 0; i < NUMBER_OF_MEAT_MEALS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". mesnog jela: ");
            String meatMealName = scanner.nextLine();

            System.out.println("Molimo unesite kojoj kategoriji " + (i + 1) + " jelo pripada: ");
            System.out.println("Ovo su sve kategorije: ");
            List<Category> categoryList = new ArrayList<>(categories);
            for (int j = 0; j < categories.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categoryList.get(j).getName() + "\n");
            }
            scanner.nextLine();
            System.out.println("Ovdje unesite kategoriju jela: ");
            String categoryName = scanner.nextLine();
            Category choosenCategory = findCategoryByName(categories, categoryName);

            System.out.println("Molimo unesite koji sve sastojci idu u ovo jelo: ");
            System.out.println("Ovo su svi sastojci: ");
            List<Ingredient> ingredientList = new ArrayList<>(ingredients);
            for (int j = 0; j < ingredients.size(); j++) {
                System.out.print("\n\t" + (j + 1) + ". " + ingredientList.get(j).getName() + "\n");
            }
            System.out.println("Koliko sastojaka želite unijeti: ");
            int ingredientsNumber = scanner.nextInt();
            List<Ingredient> chosenIngredients = new ArrayList<>(ingredientsNumber);
            for (int k = 0; k < ingredientsNumber; k++) {
                System.out.println("Molimo unesite ime " + (i + 1) + " sastojka: ");
                scanner.nextLine();
                String ingredientName = scanner.nextLine();
                Ingredient choosenIngredient = findIngredientByName(ingredients, ingredientName);
                chosenIngredients.add(choosenIngredient);
            }

            System.out.println("Molimo unesite cijenu " + (i + 1) + " jela: ");
            BigDecimal mealPrice = scanner.nextBigDecimal();
            scanner.nextLine();

            System.out.println("Molimo unesite proces zamrzavanja " + (i + 1) + ". mesnog jela: ");
            String freezingMethod = scanner.nextLine();

            meatMeals.add(new MeatMeal((i + 1L), meatMealName, choosenCategory, chosenIngredients, mealPrice, freezingMethod));
        }
    }

    /**
     * Function which contains logic for asking user to insert which chefs will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param chefs   Chefs field with default size to iterate through it.
     * @param scanner Object which allows users to insert values through console.
     */
    private static void insertChefs(Set<Chef> chefs, Scanner scanner) {
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

            System.out.println("Molimo unesite kada je " + (i + 1) + ".kuhar počeo raditi: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate startDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite kada je zadnji radni dan " + (i + 1) + ".kuhara: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate endDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite koji je tip ugovora za " + (i + 1) + ". kuhara: ");
            ContractType contractType = insertContractType(scanner);

            System.out.println("Molimo unesite koliki je bonus na kraju godine za " + (i + 1) + ".kuhara: ");
            int chefBonus = scanner.nextInt();
            scanner.nextLine();

            Contract contract = new Contract(i, chefSalary, startDate, endDate, contractType);
            Bonus bonus = new Bonus(i, chefBonus);

            chefs.add(new Chef(i, chefFirstName, chefLastName, contract, bonus));
        }

        System.out.println("Ovo su kuhari koji rade u Vašim restoranima: ");
        List<Chef> chefList = new ArrayList<>(chefs);
        for (int i = 0; i < chefs.size(); i++) {
            System.out.println("Ime " + (i + 1) + ". kuhara: " + chefList.get(i).getFirstName() + " " + chefList.get(i).getLastName());
        }
    }

    /**
     * Function which contains logic for asking user to insert which waiters will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param waiters Waiters field with default size to iterate through it.
     * @param scanner Object which allows users to insert values through console.
     */
    private static void insertWaiters(Set<Waiter> waiters, Scanner scanner) {
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

            System.out.println("Molimo unesite kada je " + (i + 1) + ". konobar počeo raditi: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate startDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite kada je zadnji radni dan " + (i + 1) + ". konobara: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate endDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite koji je tip ugovora za " + (i + 1) + ". konobara: ");
            ContractType contractType = insertContractType(scanner);

            System.out.println("Molimo unesite koliki je bonus na kraju godine za " + (i + 1) + ". konobara: ");
            int waiterBonus = scanner.nextInt();
            scanner.nextLine();

            Contract contract = new Contract(i, waiterSalary, startDate, endDate, contractType);
            Bonus bonus = new Bonus(i, waiterBonus);

            waiters.add(new Waiter(i, waiterFirstName, waiterLastName, contract, bonus));
        }

        System.out.println("Ovo su konobari koji rade u Vašim restoranima: ");
        List<Waiter> waiterList = new ArrayList<>(waiters);
        for (int i = 0; i < waiters.size(); i++) {
            System.out.println("Ime " + (i + 1) + ". konobara: " + waiterList.get(i).getFirstName() + " " + waiterList.get(i).getLastName());
        }
    }

    /**
     * Function which contains logic for asking user to insert which deliverers will be available
     * in the restaurant and after inserting them displaying them to users.
     *
     * @param deliverers Deliverers field with default size to iterate through it.
     * @param scanner    Object which allows users to insert values through console.
     */
    private static void insertDeliverers(Set<Deliverer> deliverers, Scanner scanner) {
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

            System.out.println("Molimo unesite kada je " + (i + 1) + ". dostavljač počeo raditi: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate startDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite kada je zadnji radni dan " + (i + 1) + ". dostavljača: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate endDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite koji je tip ugovora za " + (i + 1) + ". dostavljača: ");
            ContractType contractType = insertContractType(scanner);

            System.out.println("Molimo unesite koliki je bonus na kraju godine za " + (i + 1) + ". dostavljača: ");
            int delivererBonus = scanner.nextInt();
            scanner.nextLine();

            Contract contract = new Contract(i, delivererSalary, startDate, endDate, contractType);
            Bonus bonus = new Bonus(i, delivererBonus);

            deliverers.add(new Deliverer(i, delivererFirstName, delivererLastName, contract, bonus));
        }

        System.out.println("Ovo su dostavljači koji rade u Vašim restoranima: ");
        List<Deliverer> delivererList = new ArrayList<>(deliverers);
        for (int i = 0; i < deliverers.size(); i++) {
            System.out.println("Ime " + (i + 1) + ". dostavljača: " + delivererList.get(i).getFirstName() + " " + delivererList.get(i).getLastName());
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
                                          Set<Restaurant> restaurants,
                                          RestaurantLabourExchangeOffice<Restaurant> restaurantLabourExchangeOffice,
                                          Set<Meal> meals,
                                          Set<Chef> chefs,
                                          Set<Waiter> waiters,
                                          Set<Deliverer> deliverers
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

            System.out.println("Molimo unesite poštanski broj " + (i + 1) + ". restorana: ");
            String postalCode = scanner.nextLine();

            Address address = new Address(i, streetName, houseNumber, cityName, postalCode);

            System.out.println("Molimo Vas unesite koje će jelo biti u Vašem " + (i + 1) + ". restoranu:");
            System.out.println("Ovo su sva moguća jela:");
            List<Meal> mealList = new ArrayList<>(meals);
            for (int j = 0; j < meals.size(); j++) {
                String mealName = mealList.get(j).getName();
                System.out.println("1. " + mealName);
            }

            String mealName = scanner.nextLine();
            Meal choosenMeal = findMealByName(meals, mealName);
            List<Meal> chosenMeals = new ArrayList<>(1);
            chosenMeals.add(choosenMeal);

            System.out.println("Molimo Vas unesite koji kuhar će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući kuhari: ");
            List<Chef> chefList = new ArrayList<>(chefs);
            for (int j = 0; j < chefs.size(); j++) {
                String chefName = chefList.get(j).getFirstName();
                System.out.println("1. " + chefName);
            }

            String chefName = scanner.nextLine();
            Chef chosenChef = findChefByName(chefs, chefName);
            List<Chef> chosenChefs = new ArrayList<>(1);
            chosenChefs.add(chosenChef);

            System.out.println("Molimo Vas unesite koji konobar će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući konobari: ");
            List<Waiter> waiterList = new ArrayList<>(waiters);
            for (int j = 0; j < waiters.size(); j++) {
                String waiterName = waiterList.get(j).getFirstName();
                System.out.println("1. " + waiterName);
            }

            String waiterName = scanner.nextLine();
            Waiter chosenWaiter = findWaiterByName(waiters, waiterName);
            List<Waiter> chosenWaiters = new ArrayList<>(1);
            chosenWaiters.add(chosenWaiter);

            System.out.println("Molimo Vas unesite koji dostavljač će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući dostavljači: ");
            List<Deliverer> delivererList = new ArrayList<>(deliverers);
            for (int j = 0; j < deliverers.size(); j++) {
                System.out.println("1. " + delivererList.get(j).getFirstName());
            }

            String delivererName = scanner.nextLine();
            Deliverer chosenDeliverer = findDelivererByName(deliverers, delivererName);
            List<Deliverer> chosenDeliverers = new ArrayList<>(1);
            chosenDeliverers.add(chosenDeliverer);

            restaurants.add(new Restaurant(
                    (long) i,
                    restaurantName,
                    address,
                    chosenMeals,
                    chosenChefs,
                    chosenWaiters,
                    chosenDeliverers));

            restaurantLabourExchangeOffice.setRestaurants(restaurants);
        }
    }

    /**
     * Function which is used for inserting data about specific order.
     *
     * @param scanner                        Object which allows users to insert value through console.
     * @param orders                         Orders field with default size to iterate through it.
     * @param restaurantLabourExchangeOffice Restaurants field with default size to iterate through it.
     * @param meals                          Meals field with default size to iterate through it.
     * @param deliverers                     Deliverers field with default size to iterate through it.
     */
    private static void insertOrders(Scanner scanner,
                                     Set<Order> orders,
                                     RestaurantLabourExchangeOffice<Restaurant> restaurantLabourExchangeOffice,
                                     Set<Meal> meals,
                                     Set<Deliverer> deliverers) {

        System.out.println("Ovdje možete napraviti svoju naruđbu: ");
        for (int i = 0; i < NUMBER_OF_ORDERS; i++) {
            System.out.println("Iz kojeg restorana želite naručiti " + (i + 1) + ". naruđbu?");
            List<Restaurant> restaurantList = new ArrayList<>(restaurantLabourExchangeOffice.getRestaurants());
            for (int j = 0; j < restaurantLabourExchangeOffice.getRestaurants().size(); j++) {
                System.out.println(j + ". " + restaurantList.get(j).getName());
            }
            String restaurantName = scanner.nextLine();
            Restaurant chosenRestaurant = findRestaurantByName(restaurantLabourExchangeOffice.getRestaurants(), restaurantName);

            System.out.println("Koje jelo želite naručiti iz " + chosenRestaurant.getName() + " restorana?");
            System.out.println("Ovo su sva jela: ");
            List<Meal> mealList = new ArrayList<>(meals);
            for (int k = 0; k < meals.size(); k++) {
                System.out.println("1. " + mealList.get(k).getName());
            }
            String mealName = scanner.nextLine();
            Meal chosenMeal = findMealByName(meals, mealName);
            List<Meal> chosenMeals = new ArrayList<>(1);
            chosenMeals.add(chosenMeal);

            System.out.println("Koji dostavljač želite da Vam dostavi hranu?");
            System.out.println("Ovo su dostupni dostavljači: ");
            List<Deliverer> delivererList = new ArrayList<>(deliverers);
            for (int h = 0; h < deliverers.size(); h++) {
                System.out.println("1. " + delivererList.get(h).getFirstName());
            }
            String delivererName = scanner.nextLine();
            Deliverer chosenDeliverer = findDelivererByName(deliverers, delivererName);

            LocalDateTime deliveryDateAndTime = LocalDateTime.now();

            orders.add(new Order((long) i, chosenRestaurant, chosenMeals, chosenDeliverer, deliveryDateAndTime));
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
    private static Category findCategoryByName(Set<Category> categories, String categoriesName) {
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
    private static Ingredient findIngredientByName(Set<Ingredient> ingredients, String ingredientName) {
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
    private static void processInputtedCategoryEntities(Set<Category> categories, String categoryName) throws EntityAlreadyInsertedException {
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
    private static void processInputtedIngredientEntities(Set<Ingredient> ingredients, String categoryName) throws EntityAlreadyInsertedException {
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
    private static void processInputtedMealEntities(Set<Meal> meals, String mealName) throws EntityAlreadyInsertedException {
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
    private static void processInputtedChefName(Set<Chef> chefs, String firstName, String lastName) throws EntityAlreadyInsertedException {
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
    private static void processInputtedWaiterName(Set<Waiter> waiters, String firstName, String lastName) throws EntityAlreadyInsertedException {
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
    private static void processInputtedDelivererName(Set<Deliverer> deliverers, String firstName, String lastName) throws EntityAlreadyInsertedException {
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
    private static void processInputtedRestaurantEntities(Set<Restaurant> restaurants, String restaurantName) throws EntityAlreadyInsertedException {
        for (Restaurant restaurant : restaurants) {
            if (restaurant != null && restaurant.getName().equals(restaurantName)) {
                throw new EntityAlreadyInsertedException("Restoran sa imenom " + restaurantName + " već postoji. Molimo unesite kuhara sa drugim imenom.");
            }
        }
    }

    /**
     * Function which from list of orders finds and returns which restaurant has the biggest order.
     *
     * @param orders Every order all restaurants got.
     * @return Restaurant with the biggest order.
     */
    private static Restaurant[] findRestaurantsWithBiggestOrder(Set<Order> orders) {
        if (orders == null || orders.isEmpty()) return new Restaurant[0];

        BigDecimal highestPrice = BigDecimal.ZERO;
        Restaurant[] result = new Restaurant[orders.size()];
        int count = 0;

        for (Order order : orders) {
            if (order != null && order.getMeals() != null) {
                for (Meal meal : order.getMeals()) {
                    if (meal != null) {
                        int comparison = meal.getPrice().compareTo(highestPrice);
                        if (comparison > 0) {
                            highestPrice = meal.getPrice();
                            result = new Restaurant[orders.size()];
                            result[0] = order.getRestaurant();
                            count = 1;
                        } else if (comparison == 0) {
                            result[count++] = order.getRestaurant();
                        }
                    }
                }
            }
        }

        Restaurant[] finalResult = new Restaurant[count];
        System.arraycopy(result, 0, finalResult, 0, count);
        return finalResult;
    }

    /**
     * Function which from list of orders finds and returns deliverer who delivered most.
     *
     * @param orders Every order all restaurants got.
     * @return Deliverer which had most deliveries.
     */
    private static Deliverer[] findDelivererWithMostDeliveries(Set<Order> orders) {
        if (orders == null || orders.isEmpty()) {
            return new Deliverer[0];
        }

        List<Deliverer> deliverers = new ArrayList<>();
        List<Integer> deliveryCounts = new ArrayList<>();

        for (Order order : orders) {
            if (order != null && order.getDeliverer() != null) {
                Deliverer deliverer = order.getDeliverer();
                int index = deliverers.indexOf(deliverer);
                if (index == -1) {
                    deliverers.add(deliverer);
                    deliveryCounts.add(1);
                } else {
                    deliveryCounts.set(index, deliveryCounts.get(index) + 1);
                }
            }
        }

        int maxDeliveries = deliveryCounts.stream().max(Integer::compare).orElse(0);

        List<Deliverer> topDeliverers = new ArrayList<>();
        for (int i = 0; i < deliverers.size(); i++) {
            if (deliveryCounts.get(i) == maxDeliveries) {
                topDeliverers.add(deliverers.get(i));
            }
        }

        return topDeliverers.toArray(new Deliverer[0]);
    }

    /**
     * Function which lets user input date in String and then parses it into LocalDate.
     * If format is not correct user is prompted again to insert date.
     *
     * @param scanner Object which allows user to input data into console.
     * @return Date user inputted but in LocalDate data type.
     */
    private static LocalDate insertLocalDate(Scanner scanner) {
        LocalDate date = null;

        while (date == null) {
            String dateInput = scanner.nextLine();
            try {
                date = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                System.out.println("Uneseni datum: " + date);
            } catch (Exception exception) {
                System.out.println("Pogrešan format! Molimo pokušajte ponovno.");
            }
        }
        return date;
    }

    /**
     * Function which allows user to insert what contract type does the employee have.
     *
     * @param scanner Object which allows user to insert data through console.
     * @return Returns contract type user inserted through console.
     */
    private static ContractType insertContractType(Scanner scanner) {
        int contractOption = -1;
        while (contractOption != 1 && contractOption != 2) {
            System.out.println("Ovo su opcije ugovora. Odaberite broj ispred opcije ugovora: ");
            System.out.println("1. Puno radno vrijeme");
            System.out.println("2. Pola radnog vremena");
            System.out.println("Ovdje unesite broj opcije ugovora: ");
            try {
                contractOption = Integer.parseInt(scanner.nextLine());
                if (contractOption != 1 && contractOption != 2) {
                    System.out.println("Unijeli ste nepostojeću opciju! Molimo pokušajte ponovno.");
                }
            } catch (NumberFormatException exception) {
                System.out.println("Pogrešan unos! Molimo unesite broj (1 ili 2).");
            }
        }
        return contractOption == 1 ? ContractType.FULL_TIME : ContractType.PART_TIME;
    }

    /**
     * Function which returns employee with the biggest salary.
     *
     * @param chefs      All chefs which are working in the restaurants.
     * @param waiters    All waiters which are working in the restaurants.
     * @param deliverers All deliverers which are working in the restaurants.
     * @return Employee with the biggest salary.
     */
    private static String getEmployeeWithBiggestSalary(Set<Chef> chefs,
                                                       Set<Waiter> waiters,
                                                       Set<Deliverer> deliverers) {
        String employeeWithBiggestSalary = "";
        BigDecimal maxSalary = BigDecimal.ZERO;

        for (Chef chef : chefs) {
            if (chef != null && chef.getContract().getSalary().compareTo(maxSalary) > 0) {
                maxSalary = chef.getContract().getSalary();
                employeeWithBiggestSalary = "Zaposlenik s najvećom plaćom je kuhar " + chef.getFirstName() + " " + chef.getLastName();
            }
        }

        for (Waiter waiter : waiters) {
            if (waiter != null && waiter.getContract().getSalary().compareTo(maxSalary) > 0) {
                maxSalary = waiter.getContract().getSalary();
                employeeWithBiggestSalary = "Zaposlenik s najvećom plaćom je konobar " + waiter.getFirstName() + " " + waiter.getLastName();
            }
        }

        for (Deliverer deliverer : deliverers) {
            if (deliverer != null && deliverer.getContract().getSalary().compareTo(maxSalary) > 0) {
                maxSalary = deliverer.getContract().getSalary();
                employeeWithBiggestSalary = "Zaposlenik s najvećom plaćom je dostavljač " + deliverer.getFirstName() + " " + deliverer.getLastName();
            }
        }

        return employeeWithBiggestSalary;
    }

    /**
     * Function which returns employee with the longest contract.
     *
     * @param chefs      All chefs which are working in the restaurants.
     * @param waiters    All waiters which are working in the restaurants.
     * @param deliverers All deliverers which are working in the restaurants.
     * @return Employee with the longest contract.
     */
    private static String getEmployeeWithLongestContract(Set<Chef> chefs,
                                                         Set<Waiter> waiters,
                                                         Set<Deliverer> deliverers) {
        String employeeWithLongestContract = "";
        LocalDate longestContract = LocalDate.now();

        for (Chef chef : chefs) {
            if (chef != null && chef.getContract().getStartDate().isBefore(longestContract)) {
                longestContract = chef.getContract().getStartDate();
                employeeWithLongestContract = "Zaposlenik s najdužim stažem je kuhar " + chef.getFirstName() + " " + chef.getLastName();
            }
        }

        for (Waiter waiter : waiters) {
            if (waiter != null && waiter.getContract().getStartDate().isBefore(longestContract)) {
                longestContract = waiter.getContract().getStartDate();
                employeeWithLongestContract = "Zaposlenik s najdužim stažem je konobar " + waiter.getFirstName() + " " + waiter.getLastName();
            }
        }

        for (Deliverer deliverer : deliverers) {
            if (deliverer != null && deliverer.getContract().getStartDate().isBefore(longestContract)) {
                longestContract = deliverer.getContract().getStartDate();
                employeeWithLongestContract = "Zaposlenik s najdužim stažem je dostavljač " + deliverer.getFirstName() + " " + deliverer.getLastName();
            }
        }

        return employeeWithLongestContract;
    }

    /**
     * Function which determines does vegetarian meal contains eggs based on what user
     * inputted through console.
     *
     * @param scanner Object which allows user to insert data through console.
     * @return Boolean value if meal contains eggs or i doesn't contain.
     */
    private static boolean doesVegetarianMealContainsEggs(Scanner scanner) {
        while (true) {
            int input;
            try {
                input = Integer.parseInt(scanner.nextLine());
                if (input == 1) return true;
                else if (input == 2) return false;
                else System.out.println("Pogrešan unos! Molimo unesite ili 1 ili 2.");
            } catch (NumberFormatException exception) {
                System.out.println("Molimo unesite valjani broj (1 ili 2).");
            }
        }
    }

    /**
     * Function which returns meal with the most calories.
     *
     * @param veganMeals      All vegan meals that are available in the restaurant.
     * @param vegetarianMeals All vegetarian meals that are available in the restaurant.
     * @param meatMeals       All meat meals that are available in the restaurant.
     * @return Meal with the most calories.
     */
    private static String getMealWithMostCalories(Set<VeganMeal> veganMeals,
                                                  Set<VegetarianMeal> vegetarianMeals,
                                                  Set<MeatMeal> meatMeals) {
        BigDecimal biggestCalorie = BigDecimal.ZERO;
        String biggestCalorieMealData = "";

        for (VeganMeal veganMeal : veganMeals) {
            biggestCalorieMealData = "Ime: " + veganMeal.getName() + ", kategorija:  " + veganMeal.getCategory().getName() + ", broj kalorija: " + 12;
        }

        for (VegetarianMeal vegetarianMeal : vegetarianMeals) {
            biggestCalorieMealData = "Ime: " + vegetarianMeal.getName() + ", kategorija:  " + vegetarianMeal.getCategory().getName() + ", broj kalorija: " + 12;
        }

        for (MeatMeal meatMeal : meatMeals) {
            biggestCalorieMealData = "Ime: " + meatMeal.getName() + ", kategorija:  " + meatMeal.getCategory().getName() + ", broj kalorija: " + 12;
        }
        return biggestCalorieMealData;
    }

    /**
     * Function which returns meal with the least number of calories.
     *
     * @param veganMeals      All vegan meals that are available in the restaurant.
     * @param vegetarianMeals All vegetarian meals that are available in the restaurant.
     * @param meatMeals       All meat meals that are available in the restaurant.
     * @return Meal with the least number of calories.
     */
    private static String getMealWithLeastCalories(Set<VeganMeal> veganMeals,
                                                   Set<VegetarianMeal> vegetarianMeals,
                                                   Set<MeatMeal> meatMeals) {
        BigDecimal lowestCalorie = BigDecimal.ZERO;
        String lowestCalorieMealData = "";

        for (VeganMeal veganMeal : veganMeals) {
            lowestCalorieMealData = "Ime: " + veganMeal.getName() + ", kategorija:  " + veganMeal.getCategory().getName() + ", broj kalorija: " + 12;
        }

        for (VegetarianMeal vegetarianMeal : vegetarianMeals) {
            lowestCalorieMealData = "Ime: " + vegetarianMeal.getName() + ", kategorija:  " + vegetarianMeal.getCategory().getName() + ", broj kalorija: " + 12;
        }

        for (MeatMeal meatMeal : meatMeals) {
            lowestCalorieMealData = "Ime: " + meatMeal.getName() + ", kategorija:  " + meatMeal.getCategory().getName() + ", broj kalorija: " + 12;
        }

        return lowestCalorieMealData;
    }

    /**
     * Prints employees and their salaries sorted ascending by salary value.
     *
     * @param chefs      All chefs that are working in the restaurant.
     * @param waiters    All waiters that are working in the restaurant.
     * @param deliverers All deliverers that are working in the restaurant.
     */
    private static void sortEmployeesBySalary(Set<Chef> chefs, Set<Waiter> waiters, Set<Deliverer> deliverers) {
        List<Object> employees = new ArrayList<>();

        employees.add(chefs);
        employees.add(waiters);
        employees.add(deliverers);
        employees.sort(new EmployeesBySalarySorter());

        employees.forEach(System.out::println);
    }

    /**
     * Prints employees and their working days ascending based on days working in restaurant.
     *
     * @param chefs      All chefs that are working in the restaurant.
     * @param waiters    All waiters that are working in the restaurant.
     * @param deliverers All deliverers that are working in the restaurant.
     */
    private static void sortEmployeesByDaysWorking(Set<Chef> chefs, Set<Waiter> waiters, Set<Deliverer> deliverers) {
        List<Object> employees = new ArrayList<>();

        employees.add(chefs);
        employees.add(waiters);
        employees.add(deliverers);
        employees.sort(new EmployeesByContractLenghtSorter());

        employees.forEach(System.out::println);
    }

    /**
     * Finds and displays in the console restaurant which has most employees.
     *
     * @param restaurants All the restaurants in the organisation.
     */
    private static void findRestaurantWithMostEmployees(Set<Restaurant> restaurants) {
        Optional<Restaurant> maxEmployeesRestaurant = restaurants.stream()
                .max(Comparator.comparingInt(Restaurant::getEmployeeCount));
        maxEmployeesRestaurant.ifPresent(restaurant ->
                System.out.println("Restoran sa najviše zaposlenika je: " + restaurant.getName() +
                        ", a broj zaposelnika je: " + restaurant.getEmployeeCount()));
    }


    /**
     * Finds and displays in the console meals which are most common in the restaurants.
     *
     * @param restaurants All the restaurants in the organisation.
     */
    private static void findMostCommonMeal(Set<Restaurant> restaurants) {
        // Step 1: Flatten all meals into a stream and count occurrences using a map
        Map<Meal, Long> mealCountMap = restaurants.stream()
                .flatMap(restaurant -> restaurant.getMeals().stream()) // Combine all meals into one stream
                .collect(Collectors.groupingBy(meal -> meal, Collectors.counting())); // Count occurrences

        // Step 2: Find the meal with the maximum count using a comparator
        mealCountMap.entrySet().stream()
                .max(Map.Entry.comparingByValue()) // Find the entry with the highest count
                .ifPresent(entry ->
                        System.out.println("Najčešće jelo: " + entry.getKey() + " (Ponavljanja: " + entry.getValue() + ")")
                );
    }

    /**
     * Finds and displays all ingredients for every meal in order.
     *
     * @param orders Every order user ordered.
     */
    private static void displayIngredientsForMeals(Set<Order> orders) {
        // Step 1: Extract all ingredients from all meals in all orders
        Set<Ingredient> allIngredients = orders.stream()
                .flatMap(order -> order.getMeals().stream()) // Flatten meals across all orders
                .flatMap(meal -> meal.getIngredients().stream()) // Flatten ingredients across all meals
                .collect(Collectors.toSet()); // Collect unique ingredients into a set

        // Step 2: Display the ingredients
        System.out.println("Svi sastojci unutar naruđbe su :");
        allIngredients.forEach(ingredient -> System.out.println(ingredient.getName()));
    }

    /**
     * Calculates and displays total price of all meals in the orders.
     *
     * @param orders All the orders user made.
     */
    private static void displayTotalOrderPrice(Set<Order> orders) {
        // Step 1: Calculate the total price
        BigDecimal totalPrice = orders.stream()
                .flatMap(order -> order.getMeals().stream()) // Flatten all meals from all orders
                .map(Meal::getPrice) // Extract the price of each meal
                .reduce(BigDecimal.ZERO, BigDecimal::add); // Sum up all prices, starting from BigDecimal.ZERO

        // Step 2: Display the total price
        System.out.println("Ukupna cijena naruđbe je : " + totalPrice);
    }


    /**
     * Finds and displays every address and restaurant that is on that address.
     *
     * @param restaurants Every restaurant in the organisation.
     */
    private static void displayRestaurantsAtAddress(Set<Restaurant> restaurants) {
        // Step 1: Extract all unique cities
        Set<String> allCities = restaurants.stream()
                .map(restaurant -> restaurant.getAddress().getCity()) // Extract the city from the address
                .collect(Collectors.toSet());

        // Step 2: Group restaurants by city
        Map<String, List<Restaurant>> restaurantsByCity = restaurants.stream()
                .collect(Collectors.groupingBy(restaurant -> restaurant.getAddress().getCity()));

        // Step 3: Iterate through cities and display restaurants
        allCities.forEach(city -> {
            System.out.println("Grad: " + city);
            List<Restaurant> restaurantsInCity = restaurantsByCity.getOrDefault(city, List.of());
            restaurantsInCity.forEach(restaurant -> System.out.println(" - " + restaurant.getName()));
        });
    }

    private static List<Address> getAddressesFromFile() {
        List<Address> addresses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/addresses.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Integer id = Integer.parseInt(line.trim());
                String street = reader.readLine().trim();
                String houseNumber = reader.readLine().trim();
                String city = reader.readLine().trim();
                String postalCode = reader.readLine().trim();

                Address address = new Address(id, street, houseNumber, city, postalCode);
                addresses.add(address);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return addresses;
    }

    private static List<Bonus> getBonusesFromFile() {
        List<Bonus> bonuses = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/bonuses.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Integer id = Integer.parseInt(line.trim());
                Integer bonusValue = Integer.parseInt(reader.readLine().trim());

                Bonus bonus = new Bonus(id, bonusValue);
                bonuses.add(bonus);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return bonuses;
    }

    private static List<Category> getCategoriesFromFile() {
        List<Category> categories = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/categories.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Long id = Long.parseLong(line.trim());
                String name = reader.readLine().trim();
                String description = reader.readLine().trim();

                Category category = new Category(id, name, description);
                categories.add(category);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return categories;
    }

    private static List<Contract> getContractsFromFile() {
        List<Contract> contracts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/contracts.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Integer id = Integer.parseInt(line.trim());
                BigDecimal salary = new BigDecimal(reader.readLine().trim());
                LocalDate startDate = LocalDate.parse(reader.readLine().trim());
                LocalDate endDate = LocalDate.parse(reader.readLine().trim());
                ContractType contractType = ContractType.valueOf(reader.readLine().trim());

                Contract contract = new Contract(id, salary, startDate, endDate, contractType);
                contracts.add(contract);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return contracts;
    }

    private static List<Chef> getChefsFromFile(List<Contract> contracts, List<Bonus> bonuses) {
        List<Chef> chefs = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/chefs.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Integer id = Integer.parseInt(line.trim());
                String firstName = reader.readLine().trim();
                String lastName = reader.readLine().trim();
                Integer contractId = Integer.parseInt(reader.readLine().trim());
                Contract contract = contracts.stream()
                        .filter(contract1 -> contract1.getId().equals(contractId))
                        .findFirst()
                        .orElse(null);
                Integer bonusId = Integer.parseInt(reader.readLine().trim());
                Bonus bonus = bonuses.stream()
                        .filter(bonus1 -> bonus1.id().equals(bonusId))
                        .findFirst()
                        .orElse(null);

                Chef chef = new Chef(id, firstName, lastName, contract, bonus);
                chefs.add(chef);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return chefs;
    }

    private static List<Waiter> getWaitersFromFile(List<Contract> contracts, List<Bonus> bonuses) {
        List<Waiter> waiters = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/waiters.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Integer id = Integer.parseInt(line.trim());
                String firstName = reader.readLine().trim();
                String lastName = reader.readLine().trim();
                Integer contractId = Integer.parseInt(reader.readLine().trim());
                Contract contract = contracts.stream()
                        .filter(contract1 -> contract1.getId().equals(contractId))
                        .findFirst()
                        .orElse(null);
                Integer bonusId = Integer.parseInt(reader.readLine().trim());
                Bonus bonus = bonuses.stream()
                        .filter(bonus1 -> bonus1.id().equals(bonusId))
                        .findFirst()
                        .orElse(null);

                Waiter waiter = new Waiter(id, firstName, lastName, contract, bonus);
                waiters.add(waiter);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return waiters;
    }

    private static List<Deliverer> getDeliverersFromFile(List<Contract> contracts, List<Bonus> bonuses) {
        List<Deliverer> deliverers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/deliverers.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Integer id = Integer.parseInt(line.trim());
                String firstName = reader.readLine().trim();
                String lastName = reader.readLine().trim();
                Integer contractId = Integer.parseInt(reader.readLine().trim());
                Contract contract = contracts.stream()
                        .filter(contract1 -> contract1.getId().equals(contractId))
                        .findFirst()
                        .orElse(null);
                Integer bonusId = Integer.parseInt(reader.readLine().trim());
                Bonus bonus = bonuses.stream()
                        .filter(bonus1 -> bonus1.id().equals(bonusId))
                        .findFirst()
                        .orElse(null);

                Deliverer deliverer = new Deliverer(id, firstName, lastName, contract, bonus);
                deliverers.add(deliverer);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return deliverers;
    }

    private static List<Ingredient> getIngredientsFromFile(List<Category> categories) {
        List<Ingredient> ingredients = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/ingredients.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Long id = Long.parseLong(line.trim());
                String name = reader.readLine().trim();
                Long categoryId = Long.parseLong(reader.readLine().trim());
                Category category = categories.stream()
                        .filter(category1 -> category1.getId().equals(categoryId))
                        .findFirst()
                        .orElse(null);
                BigDecimal kcal = new BigDecimal(reader.readLine().trim());
                String preparationMethod = reader.readLine().trim();

                Ingredient ingredient = new Ingredient(id, name, category, kcal, preparationMethod);
                ingredients.add(ingredient);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return ingredients;
    }

    private static List<Meal> getMealsFromFile(List<Category> categories, List<Ingredient> ingredients) {
        List<Meal> meals = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/meals.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Long id = Long.parseLong(line.trim());
                String name = reader.readLine().trim();
                Long categoryId = Long.parseLong(reader.readLine().trim());
                Category category = categories.stream()
                        .filter(category1 -> category1.getId().equals(categoryId))
                        .findFirst()
                        .orElse(null);
                String ingredientsIds = reader.readLine().trim();
                String[] ingredientIdStrings = ingredientsIds.split(",");
                List<Ingredient> mealIngredients = Arrays.stream(ingredientIdStrings)
                        .map(Long::parseLong)
                        .map(ingredientId -> ingredients.stream()
                                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                                .findFirst()
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .toList();

                BigDecimal price = new BigDecimal(reader.readLine().trim());

                Meal meal = new Meal(id, name, category, mealIngredients, price);
                meals.add(meal);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return meals;
    }

    private static List<Restaurant> getRestaurantsFromFile(List<Address> addresses,
                                                           List<Meal> meals,
                                                           List<Chef> chefs,
                                                           List<Waiter> waiters,
                                                           List<Deliverer> deliverers) {
        List<Restaurant> restaurants = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/restaurants.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Long id = Long.parseLong(line.trim());
                String name = reader.readLine().trim();
                Integer addressId = Integer.parseInt(reader.readLine().trim());
                Address address = addresses.stream()
                        .filter(address1 -> address1.getId().equals(addressId))
                        .findFirst()
                        .orElse(null);
                String[] mealsStringIds = reader.readLine().trim().split(",");
                String[] chefsStringIds = reader.readLine().trim().split(",");
                String[] waitersStringIds = reader.readLine().trim().split(",");
                String[] deliverersStringIds = reader.readLine().trim().split(",");

                List<Meal> mealsList = Arrays.stream(mealsStringIds)
                        .map(Long::parseLong)
                        .map(mealId -> meals.stream()
                                .filter(meal -> meal.getId().equals(mealId))
                                .findFirst()
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .toList();

                List<Chef> chefsList = Arrays.stream(chefsStringIds)
                        .map(Integer::parseInt)
                        .map(chefId -> chefs.stream()
                                .filter(chef -> chef.getId().equals(chefId))
                                .findFirst()
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .toList();

                List<Waiter> waitersList = Arrays.stream(waitersStringIds)
                        .map(Integer::parseInt)
                        .map(waiterId -> waiters.stream()
                                .filter(waiter -> waiter.getId().equals(waiterId))
                                .findFirst()
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .toList();

                List<Deliverer> deliverersList = Arrays.stream(deliverersStringIds)
                        .map(Integer::parseInt)
                        .map(delivererId -> deliverers.stream()
                                .filter(deliverer -> deliverer.getId().equals(delivererId))
                                .findFirst()
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .toList();

                Restaurant restaurant = new Restaurant(id,
                        name,
                        address,
                        mealsList,
                        chefsList,
                        waitersList,
                        deliverersList);
                restaurants.add(restaurant);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return restaurants;
    }

    private static List<Order> getOrdersFromFile(List<Restaurant> restaurants,
                                                 List<Meal> meals,
                                                 List<Deliverer> deliverers) {
        List<Order> orders = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("dat/orders.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Long id = Long.parseLong(line.trim());
                Long restaurantId = Long.parseLong(reader.readLine().trim());
                Restaurant restaurant = restaurants.stream()
                        .filter(restaurant1 -> restaurant1.getId().equals(restaurantId))
                        .findFirst()
                        .orElse(null);

                String[] mealStringIds = reader.readLine().trim().split(",");
                List<Meal> orderMeals = Arrays.stream(mealStringIds)
                        .map(Long::parseLong)
                        .map(mealId -> meals.stream()
                                .filter(meal -> meal.getId().equals(mealId))
                                .findFirst()
                                .orElse(null))
                        .filter(Objects::nonNull)
                        .toList();

                Integer delivererId = Integer.parseInt(reader.readLine().trim());
                Deliverer deliverer = deliverers.stream()
                        .filter(deliverer1 -> deliverer1.getId().equals(delivererId))
                        .findFirst()
                        .orElse(null);

                String dateLine = reader.readLine().trim();
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
                LocalDateTime deliveryDateAndTime = LocalDateTime.parse(dateLine, dateTimeFormatter);

                Order order = new Order(id, restaurant, orderMeals, deliverer, deliveryDateAndTime);
                orders.add(order);
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        return orders;
    }
}