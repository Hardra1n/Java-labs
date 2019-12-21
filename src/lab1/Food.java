package lab1;

public abstract class Food implements Nutritious {
	String name = null;
	
	
	
	public Food (String name) {
		this.name = name;
	}
	
	
	@Override
	public boolean equals(Object arg0) {
		if (!(arg0 instanceof Food)) return false;
		if (name == null || ((Food)arg0).name == null) return false;
		return name.equals(((Food)arg0).name);
	}


	@Override 
	public String toString() {
		return name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public int calculateCalories() {
		return 0;
	}

}
