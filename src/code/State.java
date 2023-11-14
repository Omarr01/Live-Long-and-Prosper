package code;

import java.util.Objects;

public class State extends GenericState {
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

	@Override
	public String toString() {
		return "Prosperity: " + this.prosperity + "\n" + "Food: " + this.food + "\n" + "Materials: " + this.materials
				+ "\n" + "Energy: " + this.energy + "\n" + "Money Spent: " + this.moneySpent + "\n" + "Food Delay: "
				+ this.foodDelay + "\n" + "Materials Delay: " + this.materialsDelay + "\n" + "Energy Delay: "
				+ this.energyDelay;
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

	@Override
	public int hashCode() {
		return Objects.hash(this.energy, this.energyDelay, this.food, this.foodDelay, this.materials,
				this.materialsDelay, this.moneySpent, this.prosperity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (this.getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		return this.energy == other.energy && this.energyDelay == other.energyDelay && this.food == other.food
				&& this.foodDelay == other.foodDelay && this.materials == other.materials
				&& this.materialsDelay == other.materialsDelay && this.moneySpent == other.moneySpent
				&& this.prosperity == other.prosperity;
	}

}
