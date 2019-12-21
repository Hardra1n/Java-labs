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
				if(!parts[0].equals("����") && !parts[0].equals("������") && !parts[0].equals("���")) {
					System.out.println(parts[0] + " �� �������������.");
				}
				if(parts[0].equals("����")) {
					drinks[itemsSoFar] = new Coffee(parts[1]);
				} else if (parts[0].equals("������")) {
					drinks[itemsSoFar] = new Milk();
				} else if (parts[0].equals("���")) {
					drinks[itemsSoFar] = new Tea();
				}
				itemsSoFar++;
			}
		}

		System.out.println();


		if(flagSort) {
			Arrays.sort(drinks, new FoodComparator());
			System.out.println("������� ��������������� �� ������������: ");
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
			System.out.println("���-�� ������� � ��������: " + Calories);
		}


	}


}
