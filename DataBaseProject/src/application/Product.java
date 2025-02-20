package application;

public class Product {
	private String ID;
	private String name;
	private double height;
	private double length;
	private double voltage;
	private double wattage;
	private String color;
	private int quantity;
	private double price;
	private String supplierName;

	public Product(String ID, String name, double height, double length, double voltage, double wattage, String color,
			int quantity, double price, String supplierID) {
		this.ID = ID;
		this.name = name;
		this.supplierName = supplierID;
		this.voltage = voltage;
		this.wattage = wattage;
		this.color = color;
		this.height = height;
		this.length = length;
		this.quantity = quantity;
		this.price = price;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSupplierID(String supplierID) {
		this.supplierName = supplierID;
	}

	public void setVoltage(double voltage) {
		this.voltage = voltage;
	}

	public void setWattage(double wattage) {
		this.wattage = wattage;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public void setLength(double length) {
		this.length = length;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getID() {
		return ID;
	}

	public String getName() {
		return name;
	}

	public String getSupplierID() {
		return supplierName;
	}

	public double getVoltage() {
		return voltage;
	}

	public double getWattage() {
		return wattage;
	}

	public String getColor() {
		return color;
	}

	public double getHeight() {
		return height;
	}

	public double getLength() {
		return length;
	}

	public int getQuantity() {
		return quantity;
	}

	public double getPrice() {
		return price;
	}

	public String getSupplierName() {
		return supplierName;
	}

	@Override
	public String toString() {
		return "Product [ID=" + ID + ", name=" + name + ", supplierName=" + supplierName + ", voltage=" + voltage
				+ ", wattage=" + wattage + ", color=" + color + ", height=" + height + ", length=" + length
				+ ", quantity=" + quantity + ", price=" + price + "]";
	}

}
