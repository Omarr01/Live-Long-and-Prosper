package code;

public class Town {
	private static int initialProsperity;
	private static int initialFood;
	private static int initialMaterials;
	private static int initialEnergy;
	private static int unitPriceFood;
	private static int unitPriceMaterials;
	private static int unitPriceEnergy;
	private static int amountRequestFood;
	private static int delayRequestFood;
	private static int amountRequestMaterials;
	private static int delayRequestMaterials;
	private static int amountRequestEnergy;
	private static int delayRequestEnergy;
	private static int priceBUILD1;
	private static int foodUseBUILD1;
	private static int materialsUseBUILD1;
	private static int energyUseBUILD1;
	private static int prosperityBUILD1;
	private static int priceBUILD2;
	private static int foodUseBUILD2;
	private static int materialsUseBUILD2;
	private static int energyUseBUILD2;
	private static int prosperityBUILD2;
	private static int totalPriceBUILD1;
	private static int totalPriceBUILD2;

	public static void setInitialState(String townParameters) {
		String[] townParamatersSplitted = townParameters.split(";");

		Town.initialProsperity = Integer.parseInt(townParamatersSplitted[0]);

		String[] initialResourcesAmounts = townParamatersSplitted[1].split(",");
		Town.initialFood = Integer.parseInt(initialResourcesAmounts[0]);
		Town.initialMaterials = Integer.parseInt(initialResourcesAmounts[1]);
		Town.initialEnergy = Integer.parseInt(initialResourcesAmounts[2]);

		String[] unitResourcePrices = townParamatersSplitted[2].split(",");
		Town.unitPriceFood = Integer.parseInt(unitResourcePrices[0]);
		Town.unitPriceMaterials = Integer.parseInt(unitResourcePrices[1]);
		Town.unitPriceEnergy = Integer.parseInt(unitResourcePrices[2]);

		String[] foodAmountAndDelay = townParamatersSplitted[3].split(",");
		Town.amountRequestFood = Integer.parseInt(foodAmountAndDelay[0]);
		Town.delayRequestFood = Integer.parseInt(foodAmountAndDelay[1]);

		String[] materialsAmountAndDelay = townParamatersSplitted[4].split(",");
		Town.amountRequestMaterials = Integer.parseInt(materialsAmountAndDelay[0]);
		Town.delayRequestMaterials = Integer.parseInt(materialsAmountAndDelay[1]);

		String[] energyAmountAndDelay = townParamatersSplitted[5].split(",");
		Town.amountRequestEnergy = Integer.parseInt(energyAmountAndDelay[0]);
		Town.delayRequestEnergy = Integer.parseInt(energyAmountAndDelay[1]);

		String[] buildOneProperties = townParamatersSplitted[6].split(",");
		Town.priceBUILD1 = Integer.parseInt(buildOneProperties[0]);
		Town.foodUseBUILD1 = Integer.parseInt(buildOneProperties[1]);
		Town.materialsUseBUILD1 = Integer.parseInt(buildOneProperties[2]);
		Town.energyUseBUILD1 = Integer.parseInt(buildOneProperties[3]);
		Town.prosperityBUILD1 = Integer.parseInt(buildOneProperties[4]);

		String[] buildTwoProperties = townParamatersSplitted[7].split(",");
		Town.priceBUILD2 = Integer.parseInt(buildTwoProperties[0]);
		Town.foodUseBUILD2 = Integer.parseInt(buildTwoProperties[1]);
		Town.materialsUseBUILD2 = Integer.parseInt(buildTwoProperties[2]);
		Town.energyUseBUILD2 = Integer.parseInt(buildTwoProperties[3]);
		Town.prosperityBUILD2 = Integer.parseInt(buildTwoProperties[4]);

		Town.totalPriceBUILD1 = Town.getPriceBUILD1() + (Town.getEnergyUseBUILD1() * Town.getUnitPriceEnergy())
				+ (Town.getFoodUseBUILD1() * Town.getUnitPriceFood())
				+ (Town.getMaterialsUseBUILD1() * Town.getUnitPriceMaterials());

		Town.totalPriceBUILD2 = Town.getPriceBUILD2() + (Town.getEnergyUseBUILD2() * Town.getUnitPriceEnergy())
				+ (Town.getFoodUseBUILD2() * Town.getUnitPriceFood())
				+ (Town.getMaterialsUseBUILD2() * Town.getUnitPriceMaterials());
	}

	public static int[] handleDelay(int resourceDelay, int resourceAmount, int resourceType) {
		int newResourceDelay = resourceDelay;
		int newResourceAmount = resourceAmount;
		int amountRequestResource = resourceType == 1 ? Town.amountRequestFood
				: resourceType == 2 ? Town.amountRequestMaterials : Town.amountRequestEnergy;
		if (resourceDelay == 1) {
			newResourceDelay = 0;
			newResourceAmount = resourceAmount + amountRequestResource;
		} else if (resourceDelay != 0) {
			newResourceDelay = resourceDelay - 1;
		}
		return new int[] { newResourceAmount, newResourceDelay };
	}

	public static Node build(Node node, int buildNumber) {
		State nodeState = node.getState();

		int prosperity = nodeState.getProsperity() + (buildNumber == 1 ? Town.prosperityBUILD1 : Town.prosperityBUILD2);
		int food = nodeState.getFood() - (buildNumber == 1 ? Town.foodUseBUILD1 : Town.foodUseBUILD2);
		int materials = nodeState.getMaterials()
				- (buildNumber == 1 ? Town.materialsUseBUILD1 : Town.materialsUseBUILD2);
		int energy = nodeState.getEnergy() - (buildNumber == 1 ? Town.energyUseBUILD1 : Town.energyUseBUILD2);
		int moneySpent = nodeState.getMoneySpent()
				+ (buildNumber == 1 ? Town.getTotalPriceBUILD1() : Town.getTotalPriceBUILD2());

		int[] newFoodAmountAndDelay = Town.handleDelay(nodeState.getFoodDelay(), food, 1);
		food = newFoodAmountAndDelay[0];
		int foodDelay = newFoodAmountAndDelay[1];

		int[] newMaterialsAmountAndDelay = Town.handleDelay(nodeState.getMaterialsDelay(), materials, 2);
		materials = newMaterialsAmountAndDelay[0];
		int materialsDelay = newMaterialsAmountAndDelay[1];

		int[] newEnergyAmountAndDelay = Town.handleDelay(nodeState.getEnergyDelay(), energy, 3);
		energy = newEnergyAmountAndDelay[0];
		int energyDelay = newEnergyAmountAndDelay[1];

		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);

		Node expandedNode = new Node(expandedNodeState, node, buildNumber == 1 ? Operator.BUILD1 : Operator.BUILD2,
				node.getDepth() + 1, moneySpent);

		return expandedNode;
	}

	public static Node requestEnergy(Node node) {
		State nodeState = node.getState();

		int prosperity = nodeState.getProsperity();
		int food = nodeState.getFood() - 1;
		int materials = nodeState.getMaterials() - 1;
		int energy = nodeState.getEnergy() - 1;
		int moneySpent = nodeState.getMoneySpent()
				+ (Town.unitPriceFood + Town.unitPriceMaterials + Town.unitPriceEnergy);
		int foodDelay = nodeState.getFoodDelay();
		int materialsDelay = nodeState.getMaterialsDelay();
		int energyDelay = Town.delayRequestEnergy;

		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);

		Node expandedNode = new Node(expandedNodeState, node, Operator.RequestEnergy, node.getDepth() + 1, moneySpent);

		return expandedNode;
	}

	public static Node requestFood(Node node) {
		State nodeState = node.getState();

		int prosperity = nodeState.getProsperity();
		int food = nodeState.getFood() - 1;
		int materials = nodeState.getMaterials() - 1;
		int energy = nodeState.getEnergy() - 1;
		int moneySpent = nodeState.getMoneySpent()
				+ (Town.unitPriceFood + Town.unitPriceMaterials + Town.unitPriceEnergy);
		int foodDelay = Town.delayRequestFood;
		int materialsDelay = nodeState.getMaterialsDelay();
		int energyDelay = nodeState.getEnergyDelay();

		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);

		Node expandedNode = new Node(expandedNodeState, node, Operator.RequestFood, node.getDepth() + 1, moneySpent);

		return expandedNode;
	}

	public static Node requestMaterials(Node node) {
		State nodeState = node.getState();

		int prosperity = nodeState.getProsperity();
		int food = nodeState.getFood() - 1;
		int materials = nodeState.getMaterials() - 1;
		int energy = nodeState.getEnergy() - 1;
		int moneySpent = nodeState.getMoneySpent()
				+ (Town.unitPriceFood + Town.unitPriceMaterials + Town.unitPriceEnergy);
		int foodDelay = nodeState.getFoodDelay();
		int materialsDelay = Town.delayRequestMaterials;
		int energyDelay = nodeState.getEnergyDelay();

		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);

		Node expandedNode = new Node(expandedNodeState, node, Operator.RequestMaterials, node.getDepth() + 1,
				moneySpent);

		return expandedNode;
	}

	public static Node wait(Node node) {
		State nodeState = node.getState();

		int prosperity = nodeState.getProsperity();
		int food = nodeState.getFood() - 1;
		int materials = nodeState.getMaterials() - 1;
		int energy = nodeState.getEnergy() - 1;
		int moneySpent = nodeState.getMoneySpent()
				+ (Town.unitPriceFood + Town.unitPriceMaterials + Town.unitPriceEnergy);

		int[] newFoodAmountAndDelay = Town.handleDelay(nodeState.getFoodDelay(), food, 1);
		food = newFoodAmountAndDelay[0];
		int foodDelay = newFoodAmountAndDelay[1];

		int[] newMaterialsAmountAndDelay = Town.handleDelay(nodeState.getMaterialsDelay(), materials, 2);
		materials = newMaterialsAmountAndDelay[0];
		int materialsDelay = newMaterialsAmountAndDelay[1];

		int[] newEnergyAmountAndDelay = Town.handleDelay(nodeState.getEnergyDelay(), energy, 3);
		energy = newEnergyAmountAndDelay[0];
		int energyDelay = newEnergyAmountAndDelay[1];

		State expandedNodeState = new State(prosperity, food, materials, energy, moneySpent, foodDelay, materialsDelay,
				energyDelay);

		Node expandedNode = new Node(expandedNodeState, node, Operator.WAIT, node.getDepth() + 1, moneySpent);

		return expandedNode;
	}

	public static int getInitialProsperity() {
		return Town.initialProsperity;
	}

	public static int getInitialFood() {
		return Town.initialFood;
	}

	public static int getInitialMaterials() {
		return Town.initialMaterials;
	}

	public static int getInitialEnergy() {
		return Town.initialEnergy;
	}

	public static int getUnitPriceFood() {
		return Town.unitPriceFood;
	}

	public static int getUnitPriceMaterials() {
		return Town.unitPriceMaterials;
	}

	public static int getUnitPriceEnergy() {
		return Town.unitPriceEnergy;
	}

	public static int getAmountRequestFood() {
		return Town.amountRequestFood;
	}

	public static int getDelayRequestFood() {
		return Town.delayRequestFood;
	}

	public static int getAmountRequestMaterials() {
		return Town.amountRequestMaterials;
	}

	public static int getDelayRequestMaterials() {
		return Town.delayRequestMaterials;
	}

	public static int getAmountRequestEnergy() {
		return Town.amountRequestEnergy;
	}

	public static int getDelayRequestEnergy() {
		return Town.delayRequestEnergy;
	}

	public static int getPriceBUILD1() {
		return Town.priceBUILD1;
	}

	public static int getFoodUseBUILD1() {
		return Town.foodUseBUILD1;
	}

	public static int getMaterialsUseBUILD1() {
		return Town.materialsUseBUILD1;
	}

	public static int getEnergyUseBUILD1() {
		return Town.energyUseBUILD1;
	}

	public static int getProsperityBUILD1() {
		return Town.prosperityBUILD1;
	}

	public static int getPriceBUILD2() {
		return Town.priceBUILD2;
	}

	public static int getFoodUseBUILD2() {
		return Town.foodUseBUILD2;
	}

	public static int getMaterialsUseBUILD2() {
		return Town.materialsUseBUILD2;
	}

	public static int getEnergyUseBUILD2() {
		return Town.energyUseBUILD2;
	}

	public static int getProsperityBUILD2() {
		return Town.prosperityBUILD2;
	}

	public static int getTotalPriceBUILD1() {
		return Town.totalPriceBUILD1;
	}

	public static int getTotalPriceBUILD2() {
		return Town.totalPriceBUILD2;
	}

}
