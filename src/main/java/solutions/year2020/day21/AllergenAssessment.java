package solutions.year2020.day21;

import org.apache.commons.lang3.StringUtils;
import solutions.SolutionMain;

import java.util.*;
import java.util.stream.Collectors;

public class AllergenAssessment extends SolutionMain {
    private String RESOURCE_PATH = "/year2020/Day21/";

    public AllergenAssessment() {
        setResourcePath(RESOURCE_PATH);
    }

    @Override
    protected String solve(List<String> data) {
        List<Food> foods = data.stream().map(Food::new).collect(Collectors.toList());
        List<Ingredient> ingredients = new ArrayList<>();
        List<Allergen> allergens = new ArrayList<>();
        for(Food food : foods) {
            food.populateFields(ingredients, allergens);
        }
        solveAllAllergens(allergens, foods);
        foods.forEach(x -> printInfo(x.toString()));
        Long part1Answer = foods.stream().map(Food::getNumberOfSafeIngredients).reduce(0L, (x, y) -> x + y);
        printInfo("Part 1 answer:");
        printInfo(part1Answer);
        String dangerousList = buildDangerousList(ingredients);
        return dangerousList;
    }

    String buildDangerousList(List<Ingredient> ingredients) {
        List<Ingredient> sorted = ingredients.stream().filter(Ingredient::isFound).sorted((x, y) -> StringUtils.compare(x.allergen.name, y.allergen.name)).collect(Collectors.toList());
        StringBuilder out = new StringBuilder();
        for(Ingredient ingredient : sorted) {
            out.append(ingredient.name);
            out.append(",");
        }
        out.deleteCharAt(out.length()-1);
        return out.toString();
    }

    void solveAllAllergens(List<Allergen> allergens, List<Food> foods) {
        while(allergens.stream().anyMatch(x -> !x.isFound())) {
            tryAllAllergens(allergens.stream().filter(x -> !x.isFound()).collect(Collectors.toList()), foods);
        }
    }

    void tryAllAllergens(List<Allergen> allergens, List<Food> foods) {
        for(Allergen allergen : allergens) {
            List<Food> matches = new ArrayList<>();
            for(Food food : foods) {
                if(food.allergens.contains(allergen)) {
                    matches.add(food);
                }
            }
            Set<Ingredient> commonIngredients = findCommonIngredients(matches);
            commonIngredients = commonIngredients.stream().filter(x -> !x.isFound()).collect(Collectors.toSet());
            if (commonIngredients.size() == 1) {
                Ingredient found = commonIngredients.iterator().next();
                found.allergen = allergen;
                allergen.ingredient = found;
            }
        }
    }

    Set<Ingredient> findCommonIngredients(List<Food> foods) {
        List<Set<Ingredient>> ingredientSets = foods.stream().map(x -> x.ingredients).collect(Collectors.toList());
        Set<Ingredient> common = new HashSet<>(ingredientSets.remove(0));
        for(Set<Ingredient> ingredients : ingredientSets) {
            common.retainAll(ingredients);
        }
        return common;
    }


    private class Food {
        String line;
        Set<Ingredient> ingredients = new HashSet<>();
        Set<Allergen> allergens = new HashSet<>();

        Food(String line) {
            this.line = line;
        }

        @Override
        public String toString() {
            return "Ingredients: " + ingredients.toString() + ". Allergens: " + allergens.toString();
        }

        long getNumberOfSafeIngredients() {
            return ingredients.stream().filter(x -> !x.isFound()).count();
        }

        void populateFields(List<Ingredient> allIngredients, List<Allergen> allAllergens) {
            String[] split = line.split("\\(|\\)");
            String[] ingredientArray = split[0].substring(0, split[0].length()-1).split(" ");
            String[] allergenArray = split[1].replace("contains ", "").replace(",", "").split(" ");
            for(String ing : ingredientArray) {
                Optional<Ingredient> found = allIngredients.stream().filter(x -> x.name.equals(ing)).findFirst();
                if(found.isPresent()) {
                    ingredients.add(found.get());
                } else {
                    Ingredient newIngredient = new Ingredient(ing);
                    allIngredients.add(newIngredient);
                    ingredients.add(newIngredient);
                }
            }
            for(String allergen : allergenArray) {
                Optional<Allergen> found = allAllergens.stream().filter(x -> x.name.equals(allergen)).findFirst();
                if(found.isPresent()) {
                    allergens.add(found.get());
                } else {
                    Allergen newAllergen = new Allergen(allergen);
                    allAllergens.add(newAllergen);
                    allergens.add(newAllergen);
                }
            }
        }
    }

    private class Ingredient {
        String name;
        Allergen allergen = null;

        Ingredient(String name) {
            this.name = name;
        }

        boolean isFound() {
            return allergen != null;
        }

        @Override
        public String toString() {
            return name;
        }
    }

    private class Allergen {
        String name;
        Ingredient ingredient = null;

        Allergen(String name) {
            this.name = name;
        }

        boolean isFound() {
            return ingredient != null;
        }

        @Override
        public String toString() {
            return name + (isFound() ? "(" + ingredient.toString() + ")" : "");
        }
    }
}
