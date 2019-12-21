package lab1;

public class Milk extends Food {

	public Milk() {
		super("Молоко");
	}

	@Override
	public boolean equals(Object arg0) {
		if (super.equals(arg0)) {
			return (arg0 instanceof Milk);
		} else return false;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public int calculateCalories() {
		return 52;
	}

}
