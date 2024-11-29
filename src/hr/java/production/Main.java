package hr.java.production;

import hr.java.restaurant.model.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

        System.out.println("Restoran sa najskupljom naruđbom je: " + Arrays.toString(findRestaurantsWithBiggestOrder(orders)));
        System.out.println("Dostavljač s najviše dostava je: " + Arrays.toString(findDelivererWithMostDeliveries(orders)));
    }

    private static void insertCategories(Category[] categories, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_CATEGORIES; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + " kategorije: ");
            String categoryName = scanner.nextLine();

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

    private static void insertIngredients(Ingredient[] ingredients,
                                          Category[] categories,
                                          Scanner scanner) {

        for (int i = 0; i < NUMBER_OF_INGREDIENTS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + " sastojka: ");
            String ingredientName = scanner.nextLine();

            System.out.println("Molimo unesite koliko kalorija ima " + (i + 1) + " sastojak: ");
            BigDecimal kcal = scanner.nextBigDecimal();

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

            ingredients[i] = new Ingredient((long) i, ingredientName, new Category((long) i, choosenCategory.getName(), choosenCategory.getDescription()), kcal, preparationMethod);
        }
    }

    private static void insertMeals(Meal[] meals,
                                    Ingredient[] ingredients,
                                    Category[] categories,
                                    Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_MEALS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + " jela: ");
            String mealName = scanner.nextLine();

            System.out.println("Molimo unesite cijenu " + (i + 1) + " jela: ");
            BigDecimal mealPrice = scanner.nextBigDecimal();

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

    private static void insertChefs(Chef[] chefs, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_CHEFS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". kuhara:");
            String chefFirstName = scanner.nextLine();

            System.out.println("Molimo unesite prezime " + (i + 1) + ". kuhara:");
            String chefLastName = scanner.nextLine();

            System.out.println("Molimo unesite plaću " + (i + 1) + ". kuhara:");
            BigDecimal chefSalary = scanner.nextBigDecimal();
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

    private static void insertWaiters(Waiter[] waiters, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_WAITERS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". konobara:");
            String waiterFirstName = scanner.nextLine();

            System.out.println("Molimo unesite prezime " + (i + 1) + ". konobara:");
            String waiterLastName = scanner.nextLine();

            System.out.println("Molimo unesite plaću " + (i + 1) + ". konobara:");
            BigDecimal waiterSalary = scanner.nextBigDecimal();
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

    private static void insertDeliverers(Deliverer[] deliverers, Scanner scanner) {
        for (int i = 0; i < NUMBER_OF_DELIVERERS; i++) {
            System.out.println("Molimo unesite ime " + (i + 1) + ". dostavljača:");
            String delivererFirstName = scanner.nextLine();

            System.out.println("Molimo unesite prezime " + (i + 1) + ". dostavljača:");
            String delivererLastName = scanner.nextLine();

            System.out.println("Molimo unesite plaću " + (i + 1) + ". dostavljača:");
            BigDecimal delivererSalary = scanner.nextBigDecimal();
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

    private static void insertRestaurants(Scanner scanner,
                                          Restaurant[] restaurants,
                                          Meal[] meals,
                                          Chef[] chefs,
                                          Waiter[] waiters,
                                          Deliverer[] deliverers
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

    private static Category findCategoryByName(Category[] categories, String name) {
        for (Category category : categories) {
            if (category.getName().equalsIgnoreCase(name)) {
                return category;
            }
        }
        return null;
    }

    private static Ingredient findIngredientByName(Ingredient[] ingredients, String name) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().equalsIgnoreCase(name)) {
                return ingredient;
            }
        }
        return null;
    }

    private static Meal findMealByName(Meal[] meals, String name) {
        for (Meal meal : meals) {
            if (meal.getName().equalsIgnoreCase(name)) {
                return meal;
            }
        }
        return null;
    }

    private static Chef findChefByName(Chef[] chefs, String chefName) {
        for (Chef chef : chefs) {
            if (chef.getFirstName().equalsIgnoreCase(chefName)) {
                return chef;
            }
        }
        return null;
    }

    private static Waiter findWaiterByName(Waiter[] waiters, String waiterName) {
        for (Waiter waiter : waiters) {
            if (waiter.getFirstName().equalsIgnoreCase(waiterName)) {
                return waiter;
            }
        }
        return null;
    }

    private static Deliverer findDelivererByName(Deliverer[] deliverers, String delivererName) {
        for (Deliverer deliverer : deliverers) {
            if (deliverer.getFirstName().equalsIgnoreCase(delivererName)) {
                return deliverer;
            }
        }
        return null;
    }

    private static Restaurant findRestaurantByName(Restaurant[] restaurants, String restaurantName) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equalsIgnoreCase(restaurantName)) {
                return restaurant;
            }
        }
        return null;
    }

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
}