package lab1;

public class Tea extends Food {

	public Tea() {
		super("×àé");
	}

	@Override
	public boolean equals(Object arg0) {
		if (super.equals(arg0)) {
			return (arg0 instanceof Tea);
		} else return false;
	}

	@Override
	public String toString() {
		return super.toString();
	}

	@Override
	public int calculateCalories() {
		return 5;
	}

}
