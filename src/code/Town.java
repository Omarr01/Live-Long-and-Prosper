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

		String[] initialResourcesAmounts = townParamatersSplitted[1].split(",");
		this.initialFood = Integer.parseInt(initialResourcesAmounts[0]);
		this.initialMaterials = Integer.parseInt(initialResourcesAmounts[1]);
		this.initialEnergy = Integer.parseInt(initialResourcesAmounts[2]);

		String[] unitResourcePrices = townParamatersSplitted[2].split(",");
		this.unitPriceFood = Integer.parseInt(unitResourcePrices[0]);
		this.unitPriceMaterials = Integer.parseInt(unitResourcePrices[1]);
		this.unitPriceEnergy = Integer.parseInt(unitResourcePrices[2]);

		String[] foodAmountAndDelay = townParamatersSplitted[3].split(",");
		this.amountRequestFood = Integer.parseInt(foodAmountAndDelay[0]);
		this.delayRequestFood = Integer.parseInt(foodAmountAndDelay[1]);

		String[] materialsAmountAndDelay = townParamatersSplitted[4].split(",");
		this.amountRequestMaterials = Integer.parseInt(materialsAmountAndDelay[0]);
		this.delayRequestMaterials = Integer.parseInt(materialsAmountAndDelay[1]);

		String[] energyAmountAndDelay = townParamatersSplitted[5].split(",");
		this.amountRequestEnergy = Integer.parseInt(energyAmountAndDelay[0]);
		this.delayRequestEnergy = Integer.parseInt(energyAmountAndDelay[1]);

		String[] buildOneProperties = townParamatersSplitted[6].split(",");
		this.priceBUILD1 = Integer.parseInt(buildOneProperties[0]);
		this.foodUseBUILD1 = Integer.parseInt(buildOneProperties[1]);
		this.materialsUseBUILD1 = Integer.parseInt(buildOneProperties[2]);
		this.energyUseBUILD1 = Integer.parseInt(buildOneProperties[3]);
		this.prosperityBUILD1 = Integer.parseInt(buildOneProperties[4]);

		String[] buildTwoProperties = townParamatersSplitted[7].split(",");
		this.priceBUILD2 = Integer.parseInt(buildTwoProperties[0]);
		this.foodUseBUILD2 = Integer.parseInt(buildTwoProperties[1]);
		this.materialsUseBUILD2 = Integer.parseInt(buildTwoProperties[2]);
		this.energyUseBUILD2 = Integer.parseInt(buildTwoProperties[3]);
		this.prosperityBUILD2 = Integer.parseInt(buildTwoProperties[4]);
	}

	public int[] handleDelay(int resourceDelay, int resourceAmount, int resourceType) {
		int newResourceDelay = resourceDelay;
		int newResourceAmount = resourceAmount;
		int amountRequestResource = resourceType == 1 ? this.amountRequestFood
				: resourceType == 2 ? this.amountRequestMaterials : this.amountRequestEnergy;
		if (resourceDelay == 1) {
			newResourceDelay = 0;
			newResourceAmount = Math.min(resourceAmount + amountRequestResource, 50);
		} else if (resourceDelay != 0) {
			newResourceDelay = resourceDelay - 1;
		}
		return new int[] { newResourceAmount, newResourceDelay };
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

		int[] newFoodAmountAndDelay = this.handleDelay(nodeState.getFoodDelay(), food, 1);
		food = newFoodAmountAndDelay[0];
		int foodDelay = newFoodAmountAndDelay[1];

		int[] newMaterialsAmountAndDelay = this.handleDelay(nodeState.getMaterialsDelay(), materials, 2);
		materials = newMaterialsAmountAndDelay[0];
		int materialsDelay = newMaterialsAmountAndDelay[1];

		int[] newEnergyAmountAndDelay = this.handleDelay(nodeState.getEnergyDelay(), energy, 3);
		energy = newEnergyAmountAndDelay[0];
		int energyDelay = newEnergyAmountAndDelay[1];

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

		int[] newFoodAmountAndDelay = this.handleDelay(nodeState.getFoodDelay(), food, 1);
		food = newFoodAmountAndDelay[0];
		int foodDelay = newFoodAmountAndDelay[1];

		int[] newMaterialsAmountAndDelay = this.handleDelay(nodeState.getMaterialsDelay(), materials, 2);
		materials = newMaterialsAmountAndDelay[0];
		int materialsDelay = newMaterialsAmountAndDelay[1];

		int[] newEnergyAmountAndDelay = this.handleDelay(nodeState.getEnergyDelay(), energy, 3);
		energy = newEnergyAmountAndDelay[0];
		int energyDelay = newEnergyAmountAndDelay[1];

		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);

		Node expandedNode = new Node(expandedNodeState, node, Operator.WAIT, node.getDepth() + 1, moneySpent);

		return expandedNode;
	}

	public int getInitialProsperity() {
		return this.initialProsperity;
	}

	public int getInitialFood() {
		return this.initialFood;
	}

	public int getInitialMaterials() {
		return this.initialMaterials;
	}

	public int getInitialEnergy() {
		return this.initialEnergy;
	}

	public int getUnitPriceFood() {
		return this.unitPriceFood;
	}

	public int getUnitPriceMaterials() {
		return this.unitPriceMaterials;
	}

	public int getUnitPriceEnergy() {
		return this.unitPriceEnergy;
	}

	public int getAmountRequestFood() {
		return this.amountRequestFood;
	}

	public int getDelayRequestFood() {
		return this.delayRequestFood;
	}

	public int getAmountRequestMaterials() {
		return this.amountRequestMaterials;
	}

	public int getDelayRequestMaterials() {
		return this.delayRequestMaterials;
	}

	public int getAmountRequestEnergy() {
		return this.amountRequestEnergy;
	}

	public int getDelayRequestEnergy() {
		return this.delayRequestEnergy;
	}

	public int getPriceBUILD1() {
		return this.priceBUILD1;
	}

	public int getFoodUseBUILD1() {
		return this.foodUseBUILD1;
	}

	public int getMaterialsUseBUILD1() {
		return this.materialsUseBUILD1;
	}

	public int getEnergyUseBUILD1() {
		return this.energyUseBUILD1;
	}

	public int getProsperityBUILD1() {
		return this.prosperityBUILD1;
	}

	public int getPriceBUILD2() {
		return this.priceBUILD2;
	}

	public int getFoodUseBUILD2() {
		return this.foodUseBUILD2;
	}

	public int getMaterialsUseBUILD2() {
		return this.materialsUseBUILD2;
	}

	public int getEnergyUseBUILD2() {
		return this.energyUseBUILD2;
	}

	public int getProsperityBUILD2() {
		return this.prosperityBUILD2;
	}

}
