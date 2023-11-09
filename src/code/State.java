package code;

public class State {
	private int prosperity;
	private int food;
	private int materials;
	private int energy;
	private int moneySpent;
	private int foodDelay;
	private int materialsDelay;
	private int energyDelay;

	public State(int prosperity, int food, int materials, int energy, int moneySpent, int foodDelay, int materialsDelay,
			int energyDelay) {
		this.prosperity = prosperity;
		this.food = food;
		this.materials = materials;
		this.energy = energy;
		this.moneySpent = moneySpent;
		this.foodDelay = foodDelay;
		this.materialsDelay = materialsDelay;
		this.energyDelay = energyDelay;
	}

	public int getProsperity() {
		return this.prosperity;
	}

	public void setProsperity(int prosperity) {
		this.prosperity = prosperity;
	}

	public int getFood() {
		return this.food;
	}

	public void setFood(int food) {
		this.food = food;
	}

	public int getMaterials() {
		return this.materials;
	}

	public void setMaterials(int materials) {
		this.materials = materials;
	}

	public int getEnergy() {
		return this.energy;
	}

	public void setEnergy(int energy) {
		this.energy = energy;
	}

	public int getMoneySpent() {
		return this.moneySpent;
	}

	public void setMoneySpent(int moneySpent) {
		this.moneySpent = moneySpent;
	}

	public int getFoodDelay() {
		return this.foodDelay;
	}

	public void setFoodDelay(int foodDelay) {
		this.foodDelay = foodDelay;
	}

	public int getMaterialsDelay() {
		return this.materialsDelay;
	}

	public void setMaterialsDelay(int materialsDelay) {
		this.materialsDelay = materialsDelay;
	}

	public int getEnergyDelay() {
		return this.energyDelay;
	}

	public void setEnergyDelay(int energyDelay) {
		this.energyDelay = energyDelay;
	}

	public boolean compareTo(State state) {
		if (this.prosperity == state.prosperity && this.food == state.food && this.materials == state.materials
				&& this.energy == state.energy && this.moneySpent == state.moneySpent
				&& this.foodDelay == state.foodDelay && this.materialsDelay == state.materialsDelay
				&& this.energyDelay == state.energyDelay)
			return true;
		return false;
	}

}
