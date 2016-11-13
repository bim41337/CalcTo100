package application.model;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Ingredient {

	private StringProperty name;
	private DoubleProperty amount;
	private DoubleProperty calories;

	private Ingredient() {
		name = new SimpleStringProperty(this, "name");
		amount = new SimpleDoubleProperty(this, "amount");
		calories = new SimpleDoubleProperty(this, "calories");
	}

	public Ingredient(String name, Double amount, Double calories) {
		this();
		setName(name);
		setAmount(amount);
		setCalories(calories);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getName() {
		return nameProperty().get();
	}

	public void setName(String name) {
		nameProperty().set(name);
	}

	public DoubleProperty amountProperty() {
		return amount;
	}

	public Double getAmount() {
		return amountProperty().get();
	}

	public void setAmount(Double amount) {
		amountProperty().set(amount);
	}

	public DoubleProperty caloriesProperty() {
		return calories;
	}

	public Double getCalories() {
		return caloriesProperty().get();
	}

	public void setCalories(Double calories) {
		caloriesProperty().set(calories);
	}

	@Override
	public String toString() {
		return getName() + ": " + getAmount() + " Gramm, " + getCalories() + " kcal / 100g";
	}

}
