package application.view;

import java.util.stream.Collectors;

import application.Main;
import application.model.Ingredient;
import application.util.Utilities;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.NumberBinding;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;

public class MainTableViewController {

	@FXML
	private TableView<Ingredient> tableView;
	@FXML
	private TableColumn<Ingredient, String> labelColumn;
	@FXML
	private TableColumn<Ingredient, Number> amountColumn;
	@FXML
	private TableColumn<Ingredient, Number> caloriesColumn;
	@FXML
	private TextField labelTextField;
	@FXML
	private TextField amountTextField;
	@FXML
	private TextField caloriesTextField;
	@FXML
	private Button addButton;
	@FXML
	private Button removeButton;
	@FXML
	private TextField resultTextField;
	@FXML
	private TextField sumTextField;
	/**
	 * Observable list with extractor to observe changes to the list as well as the properties inside the items
	 * @see FXCollections#observableArrayList(javafx.util.Callback)
	 */
	private final ObservableList<Ingredient> ingredients = FXCollections
			.observableArrayList(ing -> new Observable[] { ing.amountProperty(), ing.caloriesProperty() }); // extractor
	private NumberBinding resultBinding;
	private NumberBinding amountSumBinding;
	private NumberBinding calorieSumBinding;
	private Main mainApp;

	@FXML
	private void initialize() {
		initTableColumns();
		initBindings();
	}

	private void initTableColumns() {
		labelColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
		labelColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		labelColumn.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
				.setName(event.getNewValue()));
		amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
		amountColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		amountColumn.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
				.setAmount(event.getNewValue().doubleValue()));
		caloriesColumn.setCellValueFactory(new PropertyValueFactory<>("calories"));
		caloriesColumn.setCellFactory(TextFieldTableCell.forTableColumn(new NumberStringConverter()));
		caloriesColumn.setOnEditCommit(event -> event.getTableView().getItems().get(event.getTablePosition().getRow())
				.setCalories(event.getNewValue().doubleValue()));
	}

	private void initBindings() {
		amountSumBinding = Bindings.createDoubleBinding(
				() -> ingredients.stream().collect(Collectors.summingDouble(Ingredient::getAmount)));
		calorieSumBinding = Bindings.createDoubleBinding(
				() -> ingredients.stream().collect(Collectors.summingDouble(Ingredient::getCalories)));
		resultBinding = Bindings.multiply(100, calorieSumBinding.divide(amountSumBinding));
		// TextField bindings
		sumTextField.textProperty().bind(amountSumBinding.asString());
		resultTextField.textProperty().bind(resultBinding.asString());
	}

	@FXML
	private void addIngredient() {
		Ingredient tmpIngredient = new Ingredient(labelTextField.getText(),
				Utilities.Strings.parseDouble(amountTextField.getText()),
				Utilities.Strings.parseDouble(caloriesTextField.getText()));
		if (Utilities.Ingredients.isValid(tmpIngredient)) {
			ingredients.add(tmpIngredient);
			labelTextField.clear();
			amountTextField.clear();
			caloriesTextField.clear();
		} else {
			showAlertDialog(AlertTypeEnum.INGREDIENT_INVALID, tmpIngredient);
		}
	}

	@FXML
	private void removeIngredient() {
		int selectedIndex = tableView.getSelectionModel().getSelectedIndex();
		if (selectedIndex >= 0) {
			ingredients.remove(selectedIndex);
		} else {
			showAlertDialog(AlertTypeEnum.NO_SELECTION);
		}
	}

	public void setMainApp(Main mainApp) {
		this.mainApp = mainApp;
	}

	private void showAlertDialog(AlertTypeEnum alertType) {
		showAlertDialog(alertType, null);
	}

	private void showAlertDialog(AlertTypeEnum alertType, Object context) {
		Alert alert = new Alert(AlertType.WARNING);
		alert.initOwner(mainApp.getPrimaryStage());
		alert.setTitle(alertType.title);
		alert.setHeaderText(alertType.headerText);
		alert.setContentText(
				(context != null) ? alertType.contentText + "\n" + context.toString() : alertType.contentText);
		alert.showAndWait();
	}

	private class NumberToStringConverter extends StringConverter<Number> {

		@Override
		public Number fromString(String string) {
			return Utilities.Strings.parseDouble(string);
		}

		@Override
		public String toString(Number object) {
			return object.toString();
		}

	}

	private enum AlertTypeEnum {

		INGREDIENT_INVALID(
				"Zutat ungültig",
				"Ungültige Werte in Zutatfeldern",
				"Eingabefelder der Zutat sind ungültig oder leer."),
		NO_SELECTION("Keine Auswahl", "Keine Zutat ausgewählt", "Zuvor muss eine Zutat ausgewählt werden.");

		private String title;
		private String headerText;
		private String contentText;

		private AlertTypeEnum(String title, String headerText, String contentText) {
			this.title = title;
			this.headerText = headerText;
			this.contentText = contentText;
		}

	}

}
