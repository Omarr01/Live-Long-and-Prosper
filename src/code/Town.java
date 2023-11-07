package code;

public class Town {
	private int initialProsperity;
	private int initialFood;
	private int initialMaterials;
	private int initialEnergy;
	private int unitPriceFood;
	private int unitPriceMaterials;
	private int unitPriceEnergy;
	private int amountRequestFood;
	private int delayRequestFood;
	private int amountRequestMaterials;
	private int delayRequestMaterials;
	private int amountRequestEnergy;
	private int delayRequestEnergy;
	private int priceBUILD1;
	private int foodUseBUILD1;
	private int materialsUseBUILD1;
	private int energyUseBUILD1;
	private int prosperityBUILD1;
	private int priceBUILD2;
	private int foodUseBUILD2;
	private int materialsUseBUILD2;
	private int energyUseBUILD2;
	private int prosperityBUILD2;

	public Town(String townParameters) {
		String[] townParamatersSplitted = townParameters.split(";");

		this.initialProsperity = Integer.parseInt(townParamatersSplitted[0]);
		this.initialFood = Integer.parseInt(townParamatersSplitted[1]);
		this.initialMaterials = Integer.parseInt(townParamatersSplitted[2]);
		this.initialEnergy = Integer.parseInt(townParamatersSplitted[3]);
		this.unitPriceFood = Integer.parseInt(townParamatersSplitted[4]);
		this.unitPriceMaterials = Integer.parseInt(townParamatersSplitted[5]);
		this.unitPriceEnergy = Integer.parseInt(townParamatersSplitted[6]);
		this.amountRequestFood = Integer.parseInt(townParamatersSplitted[7]);
		this.delayRequestFood = Integer.parseInt(townParamatersSplitted[8]);
		this.amountRequestMaterials = Integer.parseInt(townParamatersSplitted[9]);
		this.delayRequestMaterials = Integer.parseInt(townParamatersSplitted[10]);
		this.amountRequestEnergy = Integer.parseInt(townParamatersSplitted[11]);
		this.delayRequestEnergy = Integer.parseInt(townParamatersSplitted[12]);
		this.priceBUILD1 = Integer.parseInt(townParamatersSplitted[13]);
		this.foodUseBUILD1 = Integer.parseInt(townParamatersSplitted[14]);
		this.materialsUseBUILD1 = Integer.parseInt(townParamatersSplitted[15]);
		this.energyUseBUILD1 = Integer.parseInt(townParamatersSplitted[16]);
		this.prosperityBUILD1 = Integer.parseInt(townParamatersSplitted[17]);
		this.priceBUILD2 = Integer.parseInt(townParamatersSplitted[18]);
		this.foodUseBUILD2 = Integer.parseInt(townParamatersSplitted[19]);
		this.materialsUseBUILD2 = Integer.parseInt(townParamatersSplitted[20]);
		this.energyUseBUILD2 = Integer.parseInt(townParamatersSplitted[21]);
		this.prosperityBUILD2 = Integer.parseInt(townParamatersSplitted[22]);
	}

	public Node build(Node node, int buildNumber) {
		State nodeState = node.getState();

		int prosperity = nodeState.getProsperity() + (buildNumber == 1 ? this.prosperityBUILD1 : this.prosperityBUILD2);
		int food = nodeState.getFood() - (buildNumber == 1 ? this.foodUseBUILD1 : this.foodUseBUILD2);
		int materials = nodeState.getMaterials()
				- (buildNumber == 1 ? this.materialsUseBUILD1 : this.materialsUseBUILD2);
		int energy = nodeState.getEnergy() - (buildNumber == 1 ? this.energyUseBUILD1 : this.energyUseBUILD2);
		int moneySpent = nodeState.getMoneySpent() + (buildNumber == 1
				? (this.foodUseBUILD1 * this.unitPriceFood + this.materialsUseBUILD1 * this.unitPriceMaterials
						+ this.energyUseBUILD1 * this.unitPriceEnergy + this.getPriceBUILD1())
				: (this.foodUseBUILD2 * this.unitPriceFood + this.materialsUseBUILD2 * this.unitPriceMaterials
						+ this.energyUseBUILD2 * this.unitPriceEnergy + this.getPriceBUILD2()));
		int foodDelay = nodeState.getFoodDelay() == 0 ? 0 : nodeState.getFoodDelay() - 1;
		int materialsDelay = nodeState.getMaterialsDelay() == 0 ? 0 : nodeState.getMaterialsDelay() - 1;
		int energyDelay = nodeState.getEnergyDelay() == 0 ? 0 : nodeState.getEnergyDelay() - 1;

		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);

		Node expandedNode = new Node(expandedNodeState, node, buildNumber == 1 ? Operator.BUILD1 : Operator.BUILD2,
				node.getDepth() + 1, moneySpent);

		return expandedNode;
	}

	public Node requestEnergy(Node node) {
		State nodeState = node.getState();

		int prosperity = nodeState.getProsperity();
		int food = nodeState.getFood() - 1;
		int materials = nodeState.getMaterials() - 1;
		int energy = nodeState.getEnergy() - 1;
		int moneySpent = nodeState.getMoneySpent()
				+ (this.unitPriceFood + this.unitPriceMaterials + this.unitPriceEnergy);
		int foodDelay = nodeState.getFoodDelay();
		int materialsDelay = nodeState.getMaterialsDelay();
		int energyDelay = this.delayRequestEnergy;

		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);

		Node expandedNode = new Node(expandedNodeState, node, Operator.RequestEnergy, node.getDepth() + 1, moneySpent);

		return expandedNode;
	}

	public Node requestFood(Node node) {
		State nodeState = node.getState();

		int prosperity = nodeState.getProsperity();
		int food = nodeState.getFood() - 1;
		int materials = nodeState.getMaterials() - 1;
		int energy = nodeState.getEnergy() - 1;
		int moneySpent = nodeState.getMoneySpent()
				+ (this.unitPriceFood + this.unitPriceMaterials + this.unitPriceEnergy);
		int foodDelay = this.delayRequestFood;
		int materialsDelay = nodeState.getMaterialsDelay();
		int energyDelay = nodeState.getEnergyDelay();

		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);

		Node expandedNode = new Node(expandedNodeState, node, Operator.RequestFood, node.getDepth() + 1, moneySpent);

		return expandedNode;
	}

	public Node requestMaterials(Node node) {
		State nodeState = node.getState();

		int prosperity = nodeState.getProsperity();
		int food = nodeState.getFood() - 1;
		int materials = nodeState.getMaterials() - 1;
		int energy = nodeState.getEnergy() - 1;
		int moneySpent = nodeState.getMoneySpent()
				+ (this.unitPriceFood + this.unitPriceMaterials + this.unitPriceEnergy);
		int foodDelay = nodeState.getFoodDelay();
		int materialsDelay = this.delayRequestMaterials;
		int energyDelay = nodeState.getEnergyDelay();

		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);

		Node expandedNode = new Node(expandedNodeState, node, Operator.RequestMaterials, node.getDepth() + 1,
				moneySpent);

		return expandedNode;
	}

	public Node wait(Node node) {
		State nodeState = node.getState();

		int prosperity = nodeState.getProsperity();
		int food = nodeState.getFood() - 1;
		int materials = nodeState.getMaterials() - 1;
		int energy = nodeState.getEnergy() - 1;
		int moneySpent = nodeState.getMoneySpent()
				+ (this.unitPriceFood + this.unitPriceMaterials + this.unitPriceEnergy);
		int foodDelay = nodeState.getFoodDelay() == 0 ? 0 : nodeState.getFoodDelay() - 1;
		int materialsDelay = nodeState.getMaterialsDelay() == 0 ? 0 : nodeState.getMaterialsDelay() - 1;
		int energyDelay = nodeState.getEnergyDelay() == 0 ? 0 : nodeState.getEnergyDelay() - 1;
		
		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);
		
		Node expandedNode = new Node(expandedNodeState, node, Operator.WAIT, node.getDepth() + 1,
				moneySpent);
		
		return expandedNode;
	}

	public int getInitialProsperity() {
		return initialProsperity;
	}

	public void setInitialProsperity(int initialProsperity) {
		this.initialProsperity = initialProsperity;
	}

	public int getInitialFood() {
		return initialFood;
	}

	public void setInitialFood(int initialFood) {
		this.initialFood = initialFood;
	}

	public int getInitialMaterials() {
		return initialMaterials;
	}

	public void setInitialMaterials(int initialMaterials) {
		this.initialMaterials = initialMaterials;
	}

	public int getInitialEnergy() {
		return initialEnergy;
	}

	public void setInitialEnergy(int initialEnergy) {
		this.initialEnergy = initialEnergy;
	}

	public int getUnitPriceFood() {
		return unitPriceFood;
	}

	public void setUnitPriceFood(int unitPriceFood) {
		this.unitPriceFood = unitPriceFood;
	}

	public int getUnitPriceMaterials() {
		return unitPriceMaterials;
	}

	public void setUnitPriceMaterials(int unitPriceMaterials) {
		this.unitPriceMaterials = unitPriceMaterials;
	}

	public int getUnitPriceEnergy() {
		return unitPriceEnergy;
	}

	public void setUnitPriceEnergy(int unitPriceEnergy) {
		this.unitPriceEnergy = unitPriceEnergy;
	}

	public int getAmountRequestFood() {
		return amountRequestFood;
	}

	public void setAmountRequestFood(int amountRequestFood) {
		this.amountRequestFood = amountRequestFood;
	}

	public int getDelayRequestFood() {
		return delayRequestFood;
	}

	public void setDelayRequestFood(int delayRequestFood) {
		this.delayRequestFood = delayRequestFood;
	}

	public int getAmountRequestMaterials() {
		return amountRequestMaterials;
	}

	public void setAmountRequestMaterials(int amountRequestMaterials) {
		this.amountRequestMaterials = amountRequestMaterials;
	}

	public int getDelayRequestMaterials() {
		return delayRequestMaterials;
	}

	public void setDelayRequestMaterials(int delayRequestMaterials) {
		this.delayRequestMaterials = delayRequestMaterials;
	}

	public int getAmountRequestEnergy() {
		return amountRequestEnergy;
	}

	public void setAmountRequestEnergy(int amountRequestEnergy) {
		this.amountRequestEnergy = amountRequestEnergy;
	}

	public int getDelayRequestEnergy() {
		return delayRequestEnergy;
	}

	public void setDelayRequestEnergy(int delayRequestEnergy) {
		this.delayRequestEnergy = delayRequestEnergy;
	}

	public int getPriceBUILD1() {
		return priceBUILD1;
	}

	public void setPriceBUILD1(int priceBUILD1) {
		this.priceBUILD1 = priceBUILD1;
	}

	public int getFoodUseBUILD1() {
		return foodUseBUILD1;
	}

	public void setFoodUseBUILD1(int foodUseBUILD1) {
		this.foodUseBUILD1 = foodUseBUILD1;
	}

	public int getMaterialsUseBUILD1() {
		return materialsUseBUILD1;
	}

	public void setMaterialsUseBUILD1(int materialsUseBUILD1) {
		this.materialsUseBUILD1 = materialsUseBUILD1;
	}

	public int getEnergyUseBUILD1() {
		return energyUseBUILD1;
	}

	public void setEnergyUseBUILD1(int energyUseBUILD1) {
		this.energyUseBUILD1 = energyUseBUILD1;
	}

	public int getProsperityBUILD1() {
		return prosperityBUILD1;
	}

	public void setProsperityBUILD1(int prosperityBUILD1) {
		this.prosperityBUILD1 = prosperityBUILD1;
	}

	public int getPriceBUILD2() {
		return priceBUILD2;
	}

	public void setPriceBUILD2(int priceBUILD2) {
		this.priceBUILD2 = priceBUILD2;
	}

	public int getFoodUseBUILD2() {
		return foodUseBUILD2;
	}

	public void setFoodUseBUILD2(int foodUseBUILD2) {
		this.foodUseBUILD2 = foodUseBUILD2;
	}

	public int getMaterialsUseBUILD2() {
		return materialsUseBUILD2;
	}

	public void setMaterialsUseBUILD2(int materialsUseBUILD2) {
		this.materialsUseBUILD2 = materialsUseBUILD2;
	}

	public int getEnergyUseBUILD2() {
		return energyUseBUILD2;
	}

	public void setEnergyUseBUILD2(int energyUseBUILD2) {
		this.energyUseBUILD2 = energyUseBUILD2;
	}

	public int getProsperityBUILD2() {
		return prosperityBUILD2;
	}

	public void setProsperityBUILD2(int prosperityBUILD2) {
		this.prosperityBUILD2 = prosperityBUILD2;
	}

}
