package hr.java.production;

import hr.java.restaurant.exception.EntityAlreadyInsertedException;
import hr.java.restaurant.exception.NumberNotCorrectException;
import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    static final int NUMBER_OF_VEGAN_MEALS = 3;
    static final int NUMBER_OF_VEGETARIAN_MEALS = 3;
    static final int NUMBER_OF_MEAT_MEALS = 3;

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
        VeganMeal[] veganMeals = new VeganMeal[NUMBER_OF_VEGAN_MEALS];
        VegetarianMeal[] vegetarianMeals = new VegetarianMeal[NUMBER_OF_VEGETARIAN_MEALS];
        MeatMeal[] meatMeals = new MeatMeal[NUMBER_OF_MEAT_MEALS];

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

        System.out.println(getEmployeeWithBiggestSalary(chefs, waiters, deliverers));
        System.out.println(getEmployeeWithLongestContract(chefs, waiters, deliverers));

        System.out.println("Molimo vas unesite 3 Restorana");
        insertRestaurants(scanner, restaurants, meals, chefs, waiters, deliverers);

        System.out.println("Molimo vas unesite naruđbu");
        insertOrders(scanner, orders, restaurants, meals, deliverers);

        System.out.println("Restoran sa najskupljom naruđbom je: " + Arrays.toString(findRestaurantsWithBiggestOrder(orders)));
        System.out.println("Dostavljač s najviše dostava je: " + Arrays.toString(findDelivererWithMostDeliveries(orders)));
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

            System.out.println("Ovdje upišite kojoj kategoriji pripada ovaj sastojak: ");
            String categoryName = scanner.nextLine();
            Category choosenCategory = findCategoryByName(categories, categoryName);
            System.out.println("Ime odabrane kategorije: " + choosenCategory.getName());

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
            System.out.println("Ovdje unesite kategoriju jela: ");
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

            meals[i] = new Meal((long) i, mealName, choosenCategory, chosenIngredients, mealPrice);
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
                                         VeganMeal[] veganMeals,
                                         Category[] categories,
                                         Ingredient[] ingredients) {
        for (int i = 0; i < NUMBER_OF_VEGAN_MEALS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". veganskog jela: ");
            String veganMealName = scanner.nextLine();

            System.out.println("Molimo unesite kojoj kategoriji " + (i + 1) + " jelo pripada: ");
            System.out.println("Ovo su sve kategorije: ");
            for (int j = 0; j < categories.length; j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categories[j].getName() + "\n");
            }
            scanner.nextLine();
            System.out.println("Ovdje unesite kategoriju jela: ");
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

            System.out.println("Molimo unesite cijenu " + (i + 1) + " jela: ");
            BigDecimal mealPrice = scanner.nextBigDecimal();

            System.out.println("Molimo unesite koliko različitih vrsta salata ide u " + (i + 1) + ". vegansko jelo: ");
            int numberOfSalads = scanner.nextInt();

            veganMeals[i] = new VeganMeal((i + 1L), veganMealName, choosenCategory, chosenIngredients, mealPrice, numberOfSalads);
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
                                              VegetarianMeal[] vegetarianMeals,
                                              Category[] categories,
                                              Ingredient[] ingredients) {
        for (int i = 0; i < NUMBER_OF_VEGETARIAN_MEALS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". veganskog jela: ");
            String veganMealName = scanner.nextLine();

            System.out.println("Molimo unesite kojoj kategoriji " + (i + 1) + " jelo pripada: ");
            System.out.println("Ovo su sve kategorije: ");
            for (int j = 0; j < categories.length; j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categories[j].getName() + "\n");
            }
            scanner.nextLine();
            System.out.println("Ovdje unesite kategoriju jela: ");
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

            System.out.println("Molimo unesite cijenu " + (i + 1) + " jela: ");
            BigDecimal mealPrice = scanner.nextBigDecimal();

            System.out.println("Molimo unesite sadrži li " + (i + 1) + ". vegansko jelo jaja. Unesite broj pokraj točnog odgovora.");
            System.out.println("1. Ovo jelo SADRŽI jaja.");
            System.out.println("2. Ovo jelo NE SADRŽI jaja.");
            boolean containsEggs = doesVegetarianMealContainsEggs(scanner);

            vegetarianMeals[i] = new VegetarianMeal((i + 1L), veganMealName, choosenCategory, chosenIngredients, mealPrice, containsEggs);
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
                                        MeatMeal[] meatMeals,
                                        Category[] categories,
                                        Ingredient[] ingredients) {
        for (int i = 0; i < NUMBER_OF_MEAT_MEALS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". veganskog jela: ");
            String veganMealName = scanner.nextLine();

            System.out.println("Molimo unesite kojoj kategoriji " + (i + 1) + " jelo pripada: ");
            System.out.println("Ovo su sve kategorije: ");
            for (int j = 0; j < categories.length; j++) {
                System.out.print("\n\t" + (j + 1) + ". " + categories[j].getName() + "\n");
            }
            scanner.nextLine();
            System.out.println("Ovdje unesite kategoriju jela: ");
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

            System.out.println("Molimo unesite cijenu " + (i + 1) + " jela: ");
            BigDecimal mealPrice = scanner.nextBigDecimal();

            System.out.println("Molimo unesite proces zamrzavanja " + (i + 1) + ". mesnog jela: ");
            String freezingMethod = scanner.nextLine();

            meatMeals[i] = new MeatMeal((i + 1L), veganMealName, choosenCategory, chosenIngredients, mealPrice, freezingMethod);
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

            System.out.println("Molimo unesite kada je " + (i + 1) + ".kuhar počeo raditi: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate startDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite kada je zadnji radni dan " + (i + 1) + ".kuhara: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate endDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite koji je tip ugovora za " + (i + 1) + ". kuhara: ");
            Contract.ContractType contractType = insertContractType(scanner);

            System.out.println("Molimo unesite koliki je bonus na kraju godine za " + (i + 1) + ".kuhara: ");
            int chefBonus = scanner.nextInt();
            scanner.nextLine();

            Contract contract = new Contract(chefSalary, startDate, endDate, contractType);
            Bonus bonus = new Bonus(chefBonus);

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

            System.out.println("Molimo unesite kada je " + (i + 1) + ". konobar počeo raditi: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate startDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite kada je zadnji radni dan " + (i + 1) + ". konobara: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate endDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite koji je tip ugovora za " + (i + 1) + ". konobara: ");
            Contract.ContractType contractType = insertContractType(scanner);

            System.out.println("Molimo unesite koliki je bonus na kraju godine za " + (i + 1) + ". konobara: ");
            int waiterBonus = scanner.nextInt();
            scanner.nextLine();

            Contract contract = new Contract(waiterSalary, startDate, endDate, contractType);
            Bonus bonus = new Bonus(waiterBonus);

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

            System.out.println("Molimo unesite kada je " + (i + 1) + ". dostavljač počeo raditi: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate startDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite kada je zadnji radni dan " + (i + 1) + ". dostavljača: ");
            System.out.println("Molimo datum unesite u sljedećem formatu: dd-MM-yyyy");
            LocalDate endDate = insertLocalDate(scanner);

            System.out.println("Molimo unesite koji je tip ugovora za " + (i + 1) + ". dostavljača: ");
            Contract.ContractType contractType = insertContractType(scanner);

            System.out.println("Molimo unesite koliki je bonus na kraju godine za " + (i + 1) + ". dostavljača: ");
            int delivererBonus = scanner.nextInt();
            scanner.nextLine();

            Contract contract = new Contract(delivererSalary, startDate, endDate, contractType);
            Bonus bonus = new Bonus(delivererBonus);

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

            System.out.println("Molimo unesite poštanski broj " + (i + 1) + ". restorana: ");
            String postalCode = scanner.nextLine();

            Address address = new Address(streetName, houseNumber, cityName, postalCode);

            System.out.println("Molimo Vas unesite koje će jelo biti u Vašem " + (i + 1) + ". restoranu:");
            System.out.println("Ovo su sva moguća jela:");
            for (int j = 0; j < meals.length; j++) {
                String mealName = meals[j].getName();
                System.out.println((j + 1) + ". " + mealName);
            }

            String mealName = scanner.nextLine();
            Meal choosenMeal = findMealByName(meals, mealName);
            Meal[] chosenMeals = new Meal[1];
            chosenMeals[0] = choosenMeal;

            System.out.println("Molimo Vas unesite koji kuhar će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući kuhari: ");
            for (int j = 0; j < chefs.length; j++) {
                String chefName = chefs[j].getFirstName();
                System.out.println((j + 1) + ". " + chefName);
            }

            String chefName = scanner.nextLine();
            Chef chosenChef = findChefByName(chefs, chefName);
            Chef[] chosenChefs = new Chef[1];
            chosenChefs[0] = chosenChef;

            System.out.println("Molimo Vas unesite koji konobar će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući konobari: ");
            for (int j = 0; j < waiters.length; j++) {
                String waiterName = waiters[j].getFirstName();
                System.out.println((j + 1) + ". " + waiterName);
            }

            String waiterName = scanner.nextLine();
            Waiter chosenWaiter = findWaiterByName(waiters, waiterName);
            Waiter[] chosenWaiters = new Waiter[1];
            chosenWaiters[0] = chosenWaiter;

            System.out.println("Molimo Vas unesite koji dostavljač će raditi u Vašem " + (i + 1) + ". restoranu");
            System.out.println("Ovo su svi mogući dostavljači: ");
            for (int j = 0; j < deliverers.length; j++) {
                System.out.println((j + 1) + ". " + deliverers[j].getFirstName());
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

        System.out.println("Ovdjee možete napraviti svoju naruđbu: ");
        for (int i = 0; i < NUMBER_OF_ORDERS; i++) {
            System.out.println("Iz kojeg restorana želite naručiti " + (i + 1) + ". naruđbu?");
            for (int j = 0; j < restaurants.length; j++) {
                System.out.println((j + 1) + ". " + restaurants[j].getName());
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

    /**
     * Function which from list of orders finds and returns which restaurant has the biggest order.
     *
     * @param orders Every order all restaurants got.
     * @return Restaurant with the biggest order.
     */
    private static Restaurant[] findRestaurantsWithBiggestOrder(Order[] orders) {
        if (orders == null || orders.length == 0) return new Restaurant[0];

        BigDecimal highestPrice = BigDecimal.ZERO;
        Restaurant[] result = new Restaurant[orders.length];
        int count = 0;

        for (Order order : orders) {
            if (order != null && order.getMeals() != null) {
                for (Meal meal : order.getMeals()) {
                    if (meal != null) {
                        int comparison = meal.getPrice().compareTo(highestPrice);
                        if (comparison > 0) {
                            highestPrice = meal.getPrice();
                            result = new Restaurant[orders.length];
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
    private static Deliverer[] findDelivererWithMostDeliveries(Order[] orders) {
        if (orders == null || orders.length == 0) {
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
    private static Contract.ContractType insertContractType(Scanner scanner) {
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
        return contractOption == 1 ? Contract.ContractType.FULL_TIME : Contract.ContractType.PART_TIME;
    }

    /**
     * Function which returns employee with the biggest salary.
     *
     * @param chefs      All chefs which are working in the restaurants.
     * @param waiters    All waiters which are working in the restaurants.
     * @param deliverers All deliverers which are working in the restaurants.
     * @return Employee with the biggest salary.
     */
    private static String getEmployeeWithBiggestSalary(Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers) {
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
    private static String getEmployeeWithLongestContract(Chef[] chefs, Waiter[] waiters, Deliverer[] deliverers) {
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
    private static String getMealWithMostCalories(VeganMeal[] veganMeals,
                                                  VegetarianMeal[] vegetarianMeals,
                                                  MeatMeal[] meatMeals) {
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
    private static String getMealWithLeastCalories(VeganMeal[] veganMeals,
                                                   VegetarianMeal[] vegetarianMeals,
                                                   MeatMeal[] meatMeals) {
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
}