package lab1;

import java.util.Comparator;

public class FoodComparator implements Comparator<Food> {

	@Override
	public int compare(Food o1, Food o2) {
		
		if (o1==null) return 1;
		
		if(o2 == null) return -1;
		
		//return o1.getName().compareTo(o2.getName());
		//return Integer.toString(o1.calculateCalories()).compareTo(Integer.toString(o2.calculateCalories()));
		return o1.calculateCalories()-o2.calculateCalories();
	}
	
}
