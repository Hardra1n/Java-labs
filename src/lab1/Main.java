package lab1;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {
		Food[] drinks = new Food [20];
		int itemsSoFar = 0;
		boolean flagSort = false, flagCalories = false;
		for (String arg : args) {
			if (arg.equals("-calories")) {
				flagCalories = true;
			} else if (arg.equals("-sort")) {
				flagSort = true;
			} else {
				String[] parts = arg.split("/");
				if(!parts[0].equals("Кофе") && !parts[0].equals("Молоко") && !parts[0].equals("Чай")) {
					System.out.println(parts[0] + " Не предусмотрено.");
				}
				if(parts[0].equals("Кофе")) {
					drinks[itemsSoFar] = new Coffee(parts[1]);
				} else if (parts[0].equals("Молоко")) {
					drinks[itemsSoFar] = new Milk();
				} else if (parts[0].equals("Чай")) {
					drinks[itemsSoFar] = new Tea();
				}
				itemsSoFar++;
			}
		}

		System.out.println();


		if(flagSort) {
			Arrays.sort(drinks, new FoodComparator());
			System.out.println("Напитки отсортированные по калорийность: ");
			for (Food i : drinks) {
				if (i != null) {
					System.out.println(i.toString() + " " + i.calculateCalories());
				} else break;
			}
		}
		if(flagCalories) {
			int Calories = 0;
			for (Food drink : drinks) {
				if (drink != null) {
					Calories += drink.calculateCalories();
				} else break;
			}
			System.out.println("Кол-во калорий в напитках: " + Calories);
		}


	}


}
