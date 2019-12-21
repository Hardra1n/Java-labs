package lab1;

public class Coffee extends Food {
	String aroma = null;

	public String getAroma() {
		return aroma;
	}

	public void setAroma(String aroma) {
		this.aroma = aroma;
	}

	public Coffee(String aroma) {
		super("����");
		this.aroma = aroma;
	}

	@Override
	public boolean equals(Object arg0) {
		if (super.equals(arg0)) {
			if (!(arg0 instanceof Coffee)) return false;
			if (this.aroma == null || ((Coffee)arg0).aroma == null) return false;
			return aroma.equals(((Coffee)arg0).aroma);
		} else return false;
	}

	@Override
	public String toString() {
		return this.aroma + " " + super.toString();
	}

	@Override
	public int calculateCalories() {
		if (aroma.equals("����������")) return 2;
		if (aroma.equals("�������")) return 4;
		if (aroma.equals("���������")) return 12;
		return super.calculateCalories();
	}

}
