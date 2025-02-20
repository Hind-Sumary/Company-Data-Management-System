package application;

import java.sql.Connection;

import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;
import javafx.util.converter.DoubleStringConverter;

public class FXPane extends Application {
	static TableView<Product> productTableView;
	static ObservableList<Product> data;
	static TableView<Order> orderTableView;
	static ObservableList<Order> orderList;

	static Scene empScene = new Scene(creatEmployeeScene(), 920, 600);
	static Scene orderScene = new Scene(createOrderScene(), 920, 600);
	static Scene mainScene = new Scene(createMainScene(), 920, 600);

	static TextField nameTextField;
	static TextField numberTextField;
	static TextField emailTextField;
	static TextField transactionInfoTextField;

	private static String dbURL;
	private static String dbUsername = "root";
	private static String dbPassword = "qazwsxedc";
	private static String URL = "127.0.0.1";
	private static String port = "3306";
	private static String dbName = "project";
	private static Connection con;

	static ObservableList<Employee> empdataList;
	static ObservableList<Department> depdataList;
	static ObservableList<Company> companydataList;

	static TableView<Employee> employeesTableView;
	static TableView<Department> departmentsTableView;
	static TableView<Company> companyTableView;
	static Stage primaryStage = new Stage();
	Scene prevScene;

	static VBox createButtonVBox() {
		VBox buttonVBox = new VBox();
		buttonVBox.setPrefSize(165, 600);
		buttonVBox.setPadding(new Insets(0, 10, 0, 0));

		Button employeeButton = createButton("Employee");
		Button ordersButton = createButton("Orders");
		Button supplierButton = createButton("Supplier");
		Button productButton = createButton("Product");
		Button reportButton = createButton("Report");
		Button logoutButton = createButtonLogOut("Log out");

		employeeButton.setMinHeight(100);
		ordersButton.setMinHeight(100);
		supplierButton.setMinHeight(100);
		productButton.setMinHeight(100);
		reportButton.setMinHeight(100);
		logoutButton.setMinHeight(100);

		addHoverEffect(employeeButton);
		addHoverEffect(ordersButton);
		addHoverEffect(supplierButton);
		addHoverEffect(productButton);
		addHoverEffect(reportButton);
		addLogoutButtonEffects(logoutButton);

		employeeButton.setOnAction(e -> {
			System.out.println("Employee button clicked");
			primaryStage.setScene(empScene);
		});
		ordersButton.setOnAction(e -> {
			System.out.println("Order button clicked");
			primaryStage.setScene(orderScene);
		});
		supplierButton.setOnAction(e -> {
			System.out.println("Supplier button clicked");
			primaryStage.setScene(mainScene);
		});
		productButton.setOnAction(e -> {
			System.out.println("Product button clicked");
			primaryStage.setScene(mainScene);
		});
		reportButton.setOnAction(e -> {
			System.out.println("Report button clicked");
			primaryStage.setScene(mainScene);
		});
		logoutButton.setOnAction(e -> {
			System.out.println("Logout button clicked");
			Platform.exit();
		});
		buttonVBox.getChildren().addAll(employeeButton, ordersButton, supplierButton, productButton, reportButton,
				logoutButton);

		return buttonVBox;
	}

	public static BorderPane creatEmployeeScene() {

		BorderPane root = new BorderPane();

		// Left side
		VBox leftVBox = new VBox();
		leftVBox = createButtonVBox();

		HBox hboxup = new HBox();
		hboxup.getChildren().addAll(createDepartmentTable(), createAddDeleteButtonsDepartment(), createCompanyTable(),
				createAddDeleteButtonsCompany());
		HBox hboxdown = new HBox();
		hboxdown.getChildren().addAll(createEmployeeTable(), createAddDeleteButtonsEmployee());

		VBox centerVBox = new VBox(hboxup, hboxdown);

		centerVBox.setSpacing(10);
		root.setCenter(centerVBox);
		root.setLeft(leftVBox);
		return root;
	}

	private static VBox createAddDeleteButtonsEmployee() {
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(3));
		Button addButton = new Button("Add");
		Button deleteButton = new Button("Delete");
		addHoverEffectYellow(deleteButton);
		addHoverEffectYellow(addButton);
		addButton.setMinWidth(60);
		deleteButton.setMinWidth(60);
		vbox.getChildren().addAll(addButton, deleteButton);
		addButton.setStyle("-fx-background-color: #FCA311; -fx-font-weight: bold; -fx-background-radius: 15;");
		deleteButton.setStyle("-fx-background-color: #FCA311; -fx-font-weight: bold; -fx-background-radius: 15;");
		addButton.setOnAction(e -> showEntryEmployeeDialog());
		deleteButton.setOnAction(e -> {
			Employee selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();
			if (selectedRecord != null) {
				empdataList.remove(selectedRecord);
				deleteEmployeeRow(selectedRecord);
				employeesTableView.refresh();

			} else {
				showAlert(AlertType.ERROR, "No Selection", "Please select a record to delete.");
			}
		});
		return vbox;

	}

	private static VBox createAddDeleteButtonsDepartment() {
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(3));
		Button addButton = new Button("Add");
		Button deleteButton = new Button("Delete");
		addHoverEffectYellow(deleteButton);
		addHoverEffectYellow(addButton);
		addButton.setMinWidth(60);
		deleteButton.setMinWidth(60);
		vbox.getChildren().addAll(addButton, deleteButton);
		addButton.setStyle("-fx-background-color: #FCA311; -fx-font-weight: bold; -fx-background-radius: 15;");
		deleteButton.setStyle("-fx-background-color: #FCA311; -fx-font-weight: bold; -fx-background-radius: 15;");
		addButton.setOnAction(e -> showEntryDepartmentDialog());
		deleteButton.setOnAction(e -> {
			Department selectedRecord = departmentsTableView.getSelectionModel().getSelectedItem();
			if (selectedRecord != null) {
				depdataList.remove(selectedRecord);
				deleteDepartmentRow(selectedRecord);
				departmentsTableView.refresh();

			} else {
				showAlert(AlertType.ERROR, "No Selection", "Please select a record to delete.");
			}
		});
		return vbox;
	}

	private static VBox createAddDeleteButtonsCompany() {
		VBox vbox = new VBox();
		vbox.setSpacing(5);
		vbox.setPadding(new Insets(3));
		Button addButton = new Button("Add");
		Button deleteButton = new Button("Delete");
		addHoverEffectYellow(deleteButton);
		addHoverEffectYellow(addButton);
		addButton.setMinWidth(60);
		deleteButton.setMinWidth(60);
		vbox.getChildren().addAll(addButton, deleteButton);
		addButton.setStyle("-fx-background-color: #FCA311; -fx-font-weight: bold; -fx-background-radius: 15;");
		deleteButton.setStyle("-fx-background-color: #FCA311; -fx-font-weight: bold; -fx-background-radius: 15;");
		addButton.setOnAction(e -> showEntryCompanyDialog());
		deleteButton.setOnAction(e -> {
			Company selectedRecord = companyTableView.getSelectionModel().getSelectedItem();
			if (selectedRecord != null) {
				companydataList.remove(selectedRecord);
				deleteCompanyRow(selectedRecord);
				companyTableView.refresh();

			} else {
				showAlert(AlertType.ERROR, "No Selection", "Please select a record to delete.");
			}
		});
		return vbox;
	}

	private static VBox createDepartmentTable() {
		depdataList = FXCollections.observableArrayList();
		Label label = new Label("Department Table");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Set font and bold
		departmentsTableView = new TableView<>();
		departmentsTableView.setEditable(true);
		departmentsTableView.setStyle(" -fx-background-radius: 15;");
		departmentsTableView.setPrefHeight(260.0);
		departmentsTableView.setPrefWidth(320.0);

		TableColumn<Department, String> depIdColumn = new TableColumn<>("ID");
		depIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
		TableColumn<Department, String> depNameColumn = new TableColumn<>("Name");
		depNameColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepartmentName()));
		depNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn<Department, String> managerNameColumn = new TableColumn<>("Manager's Name");
		managerNameColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getManagerName()));
		managerNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn<Department, String> companyIdColumn = new TableColumn<>("Company ID");
		companyIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompanyId()));
		companyIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		depIdColumn.setStyle("-fx-background-color: #E5E5E5;");
		depNameColumn.setStyle("-fx-background-color: #E5E5E5;");
		managerNameColumn.setStyle("-fx-background-color: #E5E5E5;");
		companyIdColumn.setStyle("-fx-background-color: #E5E5E5;");

		depIdColumn.setMaxWidth(60);
		depNameColumn.setMaxWidth(60);
		managerNameColumn.setMinWidth(95);
		companyIdColumn.setMinWidth(95);

		departmentsTableView.getColumns().addAll(depIdColumn, depNameColumn, managerNameColumn, companyIdColumn);

		depNameColumn.setOnEditCommit(event -> {
			// Get the selected item
			Department selectedRecord = departmentsTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setDepartmentName(event.getNewValue());
				updateDepartmentName(selectedRecord.getId(), event.getNewValue());
				departmentsTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		managerNameColumn.setOnEditCommit(event -> {
			// Get the selected item
			Department selectedRecord = departmentsTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setManagerName(event.getNewValue());
				updateDepartmentManagerName(selectedRecord.getId(), event.getNewValue());
				departmentsTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		companyIdColumn.setOnEditCommit(event -> {
			// Get the selected item
			Department selectedRecord = departmentsTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setCompanyId(event.getNewValue());
				updateDepartmentCompanyID(selectedRecord.getId(), event.getNewValue());
				departmentsTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		departmentsTableView.setItems(depdataList);

		VBox departmentVBox = new VBox(label, departmentsTableView);
		departmentVBox.setPrefSize(306, 300);
		return departmentVBox;
	}

	private static VBox createCompanyTable() {
		companydataList = FXCollections.observableArrayList();
		Label label = new Label("Company Table");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Set font and bold
		companyTableView = new TableView<>();
		companyTableView.setEditable(true);
		companyTableView.setPrefHeight(260.0);
		companyTableView.setPrefWidth(300.0);
		companyTableView.setStyle(" -fx-background-radius: 15;");
		TableColumn<Company, String> companyIdColumn = new TableColumn<>("Company ID");
		companyIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompanyId()));
		TableColumn<Company, String> companyNameColumn = new TableColumn<>("Company Name");
		companyNameColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCompanyName()));
		companyNameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		companyIdColumn.setStyle("-fx-background-color: #E5E5E5;");
		companyNameColumn.setStyle("-fx-background-color: #E5E5E5;");

		companyIdColumn.setMinWidth(155);
		companyNameColumn.setMinWidth(155);

		companyTableView.getColumns().addAll(companyIdColumn, companyNameColumn);

		companyNameColumn.setOnEditCommit(event -> {
			// Get the selected item
			Company selectedRecord = companyTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setCompanyName(event.getNewValue());
				updateCompanyName(selectedRecord.getCompanyId(), event.getNewValue());
				companyTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		companyTableView.setItems(companydataList);

		VBox managerVBox = new VBox(label, companyTableView);
		managerVBox.setPrefSize(310, 300);
		return managerVBox;
	}

	private static VBox createEmployeeTable() {
		Label label = new Label("Employee Table");
		label.setFont(Font.font("Arial", FontWeight.BOLD, 16)); // Set font and bold
		employeesTableView = new TableView<>();
		employeesTableView.setEditable(true);
		employeesTableView.setStyle(" -fx-background-radius: 15;");
		employeesTableView.setPrefHeight(290.0);
		employeesTableView.setPrefWidth(671.0);

		TableColumn<Employee, String> idColumn = new TableColumn<>("ID");
		idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getID()));
		TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		nameColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn<Employee, Date> dobColumn = new TableColumn<>("Date of Birth");
		dobColumn.setCellValueFactory(cellData -> {
			java.sql.Date dateValue = (Date) cellData.getValue().getDOB();
			return new SimpleObjectProperty<>(dateValue);
		});

		SimpleDateFormat dateFormatDisplay = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormatEdit = new SimpleDateFormat("yyyy-MM-dd");

		dobColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
			@Override
			public String toString(Date object) {
				if (object != null) {
					return dateFormatDisplay.format(object);
				} else {
					return "";
				}
			}

			@Override
			public Date fromString(String string) {
				try {
					if (string != null && !string.isEmpty()) {
						return new Date(dateFormatEdit.parse(string).getTime());
					} else {
						return null;
					}
				} catch (ParseException e) {
					e.printStackTrace(); // Handle the parse exception appropriately
					return null;
				}
			}
		}));

		TableColumn<Employee, Date> empDateColumn = new TableColumn<>("Employment Date");
		empDateColumn.setCellValueFactory(cellData -> {
			java.sql.Date dateValue = (Date) cellData.getValue().getEmpDate();
			return new SimpleObjectProperty<>(dateValue);
		});

		SimpleDateFormat dateFormatDisplay2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormatEdit2 = new SimpleDateFormat("yyyy-MM-dd");

		empDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
			@Override
			public String toString(Date object) {
				if (object != null) {
					return dateFormatDisplay.format(object);
				} else {
					return "";
				}
			}

			@Override
			public Date fromString(String string) {
				try {
					if (string != null && !string.isEmpty()) {
						return new Date(dateFormatEdit.parse(string).getTime());
					} else {
						return null;
					}
				} catch (ParseException e) {
					e.printStackTrace(); // Handle the parse exception appropriately
					return null;
				}
			}
		}));

		TableColumn<Employee, String> emailColumn = new TableColumn<>("Email Address");
		emailColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmailAddress()));
		emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn<Employee, String> depIdColumn2 = new TableColumn<>("Dep ID");
		depIdColumn2.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDepId()));
		depIdColumn2.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn<Employee, String> phoneNumberColumn = new TableColumn<>("Phone Number");
		phoneNumberColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPhoneNum()));
		phoneNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn<Employee, String> addressColumn = new TableColumn<>("Address");
		addressColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress()));
		addressColumn.setCellFactory(TextFieldTableCell.forTableColumn());
		TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
		salaryColumn
				.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getSalary()).asObject());
		salaryColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		TableColumn<Employee, String> bankInfoColumn = new TableColumn<>("Bank Info");
		bankInfoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getBankInfo()));
		bankInfoColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		dobColumn.setCellValueFactory(new PropertyValueFactory<>("DOB"));
		depIdColumn2.setCellValueFactory(new PropertyValueFactory<>("depId"));

		employeesTableView.getColumns().addAll(idColumn, nameColumn, dobColumn, empDateColumn, emailColumn,
				depIdColumn2, phoneNumberColumn, addressColumn, salaryColumn, bankInfoColumn);

		// Set column widths (adjust as needed)
		idColumn.setPrefWidth(60.0);
		nameColumn.setPrefWidth(55.0);
		dobColumn.setPrefWidth(63.0);
		empDateColumn.setPrefWidth(74.0);
		emailColumn.setPrefWidth(91.0);
		depIdColumn2.setPrefWidth(57.0);
		phoneNumberColumn.setPrefWidth(106.0);
		addressColumn.setPrefWidth(55.0);
		salaryColumn.setPrefWidth(46.0);
		bankInfoColumn.setPrefWidth(68.0);

		for (TableColumn<Employee, ?> column : employeesTableView.getColumns()) {
			column.setStyle("-fx-background-color: #E5E5E5;");
		}

		nameColumn.setOnEditCommit(event -> {
			// Get the selected item
			Employee selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setName(event.getNewValue());
				updateEmpName(selectedRecord.getID(), event.getNewValue());
				employeesTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		dobColumn.setOnEditCommit(event -> {
			// Get the selected item
			Employee selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setDOB((Date) event.getNewValue());
				updateEmpDOB(selectedRecord.getID(), (Date) event.getNewValue());
				employeesTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		empDateColumn.setOnEditCommit(event -> {
			// Get the selected item
			Employee selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setEmpDate((Date) event.getNewValue());
				updateEmpDate(selectedRecord.getID(), (Date) event.getNewValue());
				employeesTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		emailColumn.setOnEditCommit(event -> {
			// Get the selected item
			Employee selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setEmailAddress(event.getNewValue());
				updateEmpEmailAdress(selectedRecord.getID(), event.getNewValue());
				employeesTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		depIdColumn2.setOnEditCommit(event -> {
			// Get the selected item
			Employee selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setName(event.getNewValue());
				updateEmpDepID(selectedRecord.getID(), event.getNewValue());
				employeesTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		phoneNumberColumn.setOnEditCommit(event -> {
			// Get the selected item
			Employee selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setPhoneNum(event.getNewValue());
				updateEmpPhoneNum(selectedRecord.getID(), event.getNewValue());
				employeesTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		addressColumn.setOnEditCommit(event -> {
			// Get the selected item
			Employee selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setAddress(event.getNewValue());
				updateEmpAdress(selectedRecord.getID(), event.getNewValue());
				employeesTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		salaryColumn.setOnEditCommit(event -> {
			// Get the selected item
			Employee selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setSalary(event.getNewValue());
				updateEmpSalary(selectedRecord.getID(), event.getNewValue());
				employeesTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		bankInfoColumn.setOnEditCommit(event -> {
			// Get the selected item
			Employee selectedRecord = employeesTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setBankInfo(event.getNewValue());
				updateEmpBankInfo(selectedRecord.getID(), event.getNewValue());
				employeesTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		employeesTableView.setItems(empdataList);

		VBox employeeVBox = new VBox(label, employeesTableView);
		employeeVBox.setPrefSize(675, 300);
		return employeeVBox;
	}

	private static void showEntryEmployeeDialog() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Data Addition");

		// Create a Text node for the HeaderText and make it bold
		Text headerText = new Text("Enter data:");
		headerText.setStyle("-fx-background-color: #FCA311;");
		headerText.setFont(Font.font(null, FontWeight.BOLD, 18));

		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.setHeader(headerText); // Set the custom header text
		dialogPane.setStyle("-fx-alignment: CENTER; -fx-background-color: #FCA311;  -fx-background-radius: 15;");

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10));

		dialogPane.setContent(gridPane);
		dialogPane.setPadding(new Insets(10, 10, 10, 10)); // Adjust the insets as needed

		// Labels and TextFields for employee information
		Label nameLabel = new Label("Name:");
		TextField nameField = new TextField();
		setLabelStyle(nameLabel);
		setTextFieldStyle(nameField);

		Label dobLabel = new Label("Date Of Birth:");
		setLabelStyle(dobLabel);
		DatePicker dobDatePicker = new DatePicker();
		setDatePickerStyle(dobDatePicker);

		Label empDateLabel = new Label("Employment Date:");
		setLabelStyle(empDateLabel);
		DatePicker empDatePicker = new DatePicker();
		setDatePickerStyle(empDatePicker); // Apply styles for DatePicker

		Label emailLabel = new Label("Email Address:");
		TextField emailField = new TextField();
		setLabelStyle(emailLabel);
		setTextFieldStyle(emailField);

		Label depIdLabel = new Label("Department ID:");
		ComboBox<String> depComboBox = new ComboBox<>();
		setLabelStyle(depIdLabel);
		depComboBox.getItems().addAll(Department.getDepartmentNames()); // You need a method to get department names
		depComboBox.setPromptText("Select Department");
		depComboBox.setStyle("-fx-background-radius: 15; -fx-background-color: #FFFFFF;");
		depComboBox.setMinWidth(200);

		Label phoneLabel = new Label("Phone Number:");
		TextField phoneField = new TextField();
		phoneField.setPromptText("10-digits");
		setLabelStyle(phoneLabel);
		setTextFieldStyle(phoneField);

		Label addressLabel = new Label("Address:");
		TextField addressField = new TextField();
		setLabelStyle(addressLabel);
		setTextFieldStyle(addressField);

		Label salaryLabel = new Label("Salary:");
		setLabelStyle(salaryLabel);
		TextField salaryField = new TextField();
		setTextFieldStyle(salaryField);

		Label bankInfoLabel = new Label("Bank Information:");
		TextField bankInfoField = new TextField();
		setLabelStyle(bankInfoLabel);
		setTextFieldStyle(bankInfoField);

		gridPane.add(nameLabel, 0, 1);
		gridPane.add(nameField, 1, 1);
		gridPane.add(dobLabel, 0, 2);
		gridPane.add(dobDatePicker, 1, 2);
		gridPane.add(empDateLabel, 0, 3);
		gridPane.add(empDatePicker, 1, 3);
		gridPane.add(emailLabel, 0, 4);
		gridPane.add(emailField, 1, 4);
		gridPane.add(depIdLabel, 0, 5);
		gridPane.add(depComboBox, 1, 5);
		gridPane.add(phoneLabel, 0, 6);
		gridPane.add(phoneField, 1, 6);
		gridPane.add(addressLabel, 0, 7);
		gridPane.add(addressField, 1, 7);
		gridPane.add(salaryLabel, 0, 8);
		gridPane.add(salaryField, 1, 8);
		gridPane.add(bankInfoLabel, 0, 9);
		gridPane.add(bankInfoField, 1, 9);

		gridPane.setStyle("-fx-background-color: #FCA311;");

		Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		cancelButton.setStyle(
				"-fx-font-weight: bold; -fx-background-color: #14213D; -fx-text-fill: #FFFFFF; -fx-background-radius: 15;");
		addButtonEffects(cancelButton);
		Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setStyle(
				"-fx-font-weight: bold; -fx-background-color: #14213D; -fx-text-fill: #FFFFFF; -fx-background-radius: 15;");
		okButton.setText("Add");
		addButtonEffects(okButton);

		okButton.setOnAction(e -> {
			String employeeId = generateEmployeeID();

			// Check if the ComboBox is empty
			if (depComboBox.getSelectionModel().isEmpty()) {
				showAlert(AlertType.ERROR, "Missing Department", "Please select a department for the employee.");
				return;
			}

			Employee newEmployee = createEmployeeFromFields(employeeId, nameField.getText(), dobDatePicker.getValue(),
					empDatePicker.getValue(), emailField.getText(), getDepartmentId(depComboBox.getValue()),
					formatPhoneNumber(phoneField.getText()), addressField.getText(),
					Double.parseDouble(salaryField.getText()), bankInfoField.getText());

			String formattedPhoneNumber = formatPhoneNumber(phoneField.getText());
			if (formattedPhoneNumber == null) {
				showAlert(AlertType.ERROR, "Invalid Phone Number", "Please enter a valid 10-digit phone number.");
				return;
			}

			// Call the insertEmployeeData method
			insertEmployeeData(newEmployee);
			empdataList.add(newEmployee);
			employeesTableView.refresh();
		});

		dialog.initStyle(StageStyle.UTILITY);

		dialog.showAndWait();
	}

	private static String getDepartmentId(String departmentName) {
		return Department.getDepartmentIdByName(departmentName);
	}

	static int employeeCounter = 16;

	// Helper method to generate the next employee ID
	private static String generateEmployeeID() {
		employeeCounter++;
		return "EMP" + String.format("%02d", employeeCounter);
	}

	private static String formatPhoneNumber(String phoneNumber) {
		if (!phoneNumber.matches("\\d*") || phoneNumber.length() != 10) {
			return null; // Invalid phone number
		}

		return phoneNumber.substring(0, 3) + "-" + phoneNumber.substring(3, 6) + "-" + phoneNumber.substring(6);
	}

	private static void setDatePickerStyle(DatePicker datePicker) {
		// Set background color
		datePicker.setStyle("-fx-background-color: #FCA311; -fx-font-weight: bold;");

		// Set rounded border for DatePicker container
		Region datePickerContent = (Region) datePicker.getEditor();
		datePickerContent.setStyle("-fx-background-radius: 15;");
	}

	private static void showEntryDepartmentDialog() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Data Addition");

		// Create a Text node for the HeaderText and make it bold
		Text headerText = new Text("Enter data:");
		headerText.setStyle("-fx-background-color: #FCA311;");
		headerText.setFont(Font.font(null, FontWeight.BOLD, 18));

		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.setHeader(headerText); // Set the custom header text
		dialogPane.setStyle("-fx-alignment: CENTER; -fx-background-color: #FCA311;  -fx-background-radius: 15;");

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10));

		dialogPane.setContent(gridPane);
		dialogPane.setPadding(new Insets(10, 10, 10, 10)); // Adjust the insets as needed

		ComboBox<String> compComboBox = new ComboBox<>();
		compComboBox.getItems().addAll(Company.getCompanyNames());
		compComboBox.setPromptText("Select Company");
		compComboBox.setStyle("-fx-background-radius: 15; -fx-background-color: #FFFFFF;");
		compComboBox.setMinWidth(200);

		Label depNamelbl = new Label("Department Name:");
		TextField depNametxt = new TextField();
		setLabelStyle(depNamelbl);
		setTextFieldStyle(depNametxt);

		Label managerNamelbl = new Label("Manager's Name:");
		TextField managerNametxt = new TextField();
		setLabelStyle(managerNamelbl);
		setTextFieldStyle(managerNametxt);

		Label companyIDlbl = new Label("Company ID:");
		TextField companyIDtxt = new TextField();
		setLabelStyle(companyIDlbl);
		setTextFieldStyle(companyIDtxt);

		gridPane.add(depNamelbl, 0, 1);
		gridPane.add(depNametxt, 1, 1);
		gridPane.add(managerNamelbl, 0, 2);
		gridPane.add(managerNametxt, 1, 2);
		gridPane.add(companyIDlbl, 0, 3);
		gridPane.add(compComboBox, 1, 3);

		gridPane.setStyle("-fx-background-color: #FCA311;");

		Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		cancelButton.setStyle(
				"-fx-font-weight: bold; -fx-background-color: #14213D; -fx-text-fill: #FFFFFF; -fx-background-radius: 15;");
		addButtonEffects(cancelButton);
		Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setStyle(
				"-fx-font-weight: bold; -fx-background-color: #14213D; -fx-text-fill: #FFFFFF; -fx-background-radius: 15;");
		okButton.setText("Add");
		addButtonEffects(okButton);

		okButton.setOnAction(e -> {
			// Create an Employee object with the entered data
			Department newDepartment = new Department(generateDepartmentID(), depNametxt.getText(),
					managerNametxt.getText(), getCompanyNames(compComboBox.getValue()));

			// Call the insertEmployeeData method
			insertDepartmentData(newDepartment);
			depdataList.add(newDepartment);
			departmentsTableView.refresh();
		});

		dialog.initStyle(StageStyle.UTILITY);

		dialog.showAndWait();
	}

	private static String getCompanyNames(String compayName) {
		return Company.getCompanyNamebyID(compayName);
	}

	static int departmentCounter = 4;

	// Helper method to generate the next employee ID
	private static String generateDepartmentID() {
		departmentCounter++;
		return "DEP" + String.format("%02d", departmentCounter);
	}

	private static void showEntryCompanyDialog() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Data Addition");

		// Create a Text node for the HeaderText and make it bold
		Text headerText = new Text("Enter data:");
		headerText.setStyle("-fx-background-color: #FCA311;");
		headerText.setFont(Font.font(null, FontWeight.BOLD, 18));

		DialogPane dialogPane = dialog.getDialogPane();
		dialogPane.setHeader(headerText); // Set the custom header text
		dialogPane.setStyle("-fx-alignment: CENTER; -fx-background-color: #FCA311;  -fx-background-radius: 15;");

		GridPane gridPane = new GridPane();
		gridPane.setHgap(10);
		gridPane.setVgap(10);
		gridPane.setPadding(new Insets(10));

		dialogPane.setContent(gridPane);
		dialogPane.setPadding(new Insets(10, 10, 10, 10)); // Adjust the insets as needed

		Label compNamelbl = new Label("Company Name:");
		TextField compNametxt = new TextField();
		setLabelStyle(compNamelbl);
		setTextFieldStyle(compNametxt);

		gridPane.add(compNamelbl, 0, 1);
		gridPane.add(compNametxt, 1, 1);

		gridPane.setStyle("-fx-background-color: #FCA311;");

		Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);
		cancelButton.setStyle(
				"-fx-font-weight: bold; -fx-background-color: #14213D; -fx-text-fill: #FFFFFF; -fx-background-radius: 15;");
		addButtonEffects(cancelButton);
		Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
		okButton.setStyle(
				"-fx-font-weight: bold; -fx-background-color: #14213D; -fx-text-fill: #FFFFFF; -fx-background-radius: 15;");
		okButton.setText("Add");
		addButtonEffects(okButton);

		okButton.setOnAction(e -> {
			// Create an Employee object with the entered data
			Company newCompany = new Company(generateCompanyID(), compNametxt.getText());

			// Call the insertEmployeeData method
			insertCompanyData(newCompany);
			companydataList.add(newCompany);
			departmentsTableView.refresh();
		});

		dialog.initStyle(StageStyle.UTILITY);

		dialog.showAndWait();
	}

	static int companycounter = 2;

	// Helper method to generate the next employee ID
	private static String generateCompanyID() {
		companycounter++;
		return "Company" + String.format("%02d", companycounter);
	}

	// Set style for a label
	private static void setLabelStyle(Label label) {
		label.setStyle("-fx-background-color: #FCA311; -fx-background-radius: 0; -fx-font-weight: bold;");
	}

	private static void setTextFieldStyle(TextField txt) {
		txt.setStyle("-fx-background-radius: 15;");
	}

	private static Employee createEmployeeFromFields(String empId, String name, LocalDate dob, LocalDate empDate,
			String emailAddress, String depId, String phoneNum, String address, double salary, String bankInfo) {
		try {
			// Convert LocalDate to Date
			Date parsedDOB = Date.valueOf(dob);
			Date parsedEmpDate = Date.valueOf(empDate);

			return new Employee(empId, name, parsedDOB, parsedEmpDate, emailAddress, depId, phoneNum, address, salary,
					bankInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	// ****************************************

	private static void insertEmployeeData(Employee employee) {

		try {
			System.out.println(
					"INSERT INTO Employee (EmployeeID, EmployeeName, DOB, EmploymentDate, EmailAddress, DepartmentID, PhoneNum, Address, Salary, BankInfo) VALUES ( '"
							+ employee.getID() + "', '" + employee.getName() + "', '" + employee.getDOB() + "', '"
							+ employee.getEmpDate() + "', '" + employee.getEmailAddress() + "', '" + employee.getDepId()
							+ "', '" + employee.getPhoneNum() + "', '" + employee.getAddress() + "', "
							+ employee.getSalary() + ", '" + employee.getBankInfo() + "' );");
			connectDB();
			ExecuteStatement(
					"INSERT INTO Employee (EmployeeID, EmployeeName, DOB, EmploymentDate, EmailAddress, DepartmentID, PhoneNum, Address, Salary, BankInfo) VALUES ( '"
							+ employee.getID() + "', '" + employee.getName() + "', '" + employee.getDOB() + "', '"
							+ employee.getEmpDate() + "', '" + employee.getEmailAddress() + "', '" + employee.getDepId()
							+ "', '" + employee.getPhoneNum() + "', '" + employee.getAddress() + "', "
							+ employee.getSalary() + ", '" + employee.getBankInfo() + "' );");

			con.close();
			System.out.println("Connection closed" + empdataList.size());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void insertDepartmentData(Department deparment) {

		try {
			System.out.println("INSERT INTO Department (ID, DepartmentName, Managername, CompanyID) VALUES ( '"
					+ deparment.getId() + "', '" + deparment.getDepartmentName() + "', '" + deparment.getManagerName()
					+ "', '" + deparment.getCompanyId() + "' );");
			connectDB();
			ExecuteStatement("INSERT INTO Department (ID, DepartmentName, Managername, CompanyID) VALUES ( '"
					+ deparment.getId() + "', '" + deparment.getDepartmentName() + "', '" + deparment.getManagerName()
					+ "', '" + deparment.getCompanyId() + "' );");

			con.close();
			System.out.println("Connection closed" + depdataList.size());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void insertCompanyData(Company company) {

		try {
			System.out.println("INSERT INTO Company (CompanyID, CompanyName) VALUES ( '" + company.getCompanyId()
					+ "', '" + company.getCompanyName() + "' );");
			connectDB();
			ExecuteStatement("INSERT INTO Company (CompanyID, CompanyName) VALUES ( '" + company.getCompanyId() + "', '"
					+ company.getCompanyName() + "' );");

			con.close();
			Company com = new Company(company.getCompanyId(), company.getCompanyName());
			companydataList.add(com);
			System.out.println("Connection closed" + companydataList.size());

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ****************************************

	private static void deleteEmployeeRow(Employee row) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from  Employee where EmployeeID = '" + row.getID() + "';");
			connectDB();
			ExecuteStatement("delete from  Employee where EmployeeID = '" + row.getID() + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void deleteDepartmentRow(Department row) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from  Department where ID = '" + row.getId() + "';");
			connectDB();
			ExecuteStatement("delete from  Department where ID = '" + row.getId() + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	private static void deleteCompanyRow(Company row) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from  Company where CompanyID = '" + row.getCompanyId() + "';");
			connectDB();
			ExecuteStatement("delete from  Company where CompanyID = '" + row.getCompanyId() + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

//****************************************8

	public static void updateEmpName(String employeeID, String name) {

		try {
			System.out.println(
					"update  Employee set EmployeeName = '" + name + "' where EmployeeID = '" + employeeID + "';");
			connectDB();
			ExecuteStatement(
					"update  Employee set EmployeeName = '" + name + "' where EmployeeID = '" + employeeID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateEmpDOB(String employeeID, Date dob) {

		try {
			System.out.println("update  Employee set DOB = '" + dob + "' where EmployeeID = '" + employeeID + "';");
			connectDB();
			ExecuteStatement("update  Employee set DOB = '" + dob + "' where EmployeeID = '" + employeeID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateEmpDate(String employeeID, Date empDate) {

		try {
			System.out.println(
					"update  Employee set EmploymentDate = '" + empDate + "' where EmployeeID = '" + employeeID + "';");
			connectDB();
			ExecuteStatement(
					"update  Employee set EmploymentDate = '" + empDate + "' where EmployeeID = '" + employeeID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateEmpEmailAdress(String EmployeeID, String empEmail) {

		try {
			System.out.println(
					"update  Employee set EmailAddress = '" + empEmail + "' where EmployeeID = '" + EmployeeID + "';");
			connectDB();
			ExecuteStatement(
					"update  Employee set EmailAddress = '" + empEmail + "' where EmployeeID = '" + EmployeeID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateEmpDepID(String EmployeeID, String depID) {

		try {
			System.out.println(
					"update  Employee set DepartmentID = '" + depID + "' where EmployeeID = '" + EmployeeID + "';");
			connectDB();
			ExecuteStatement(
					"update  Employee set DepartmentID = '" + depID + "' where EmployeeID = '" + EmployeeID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateEmpPhoneNum(String employeeID, String phoneNum) {

		try {
			System.out.println(
					"update  Employee set PhoneNum = '" + phoneNum + "' where EmployeeID = '" + employeeID + "';");
			connectDB();
			ExecuteStatement(
					"update  Employee set PhoneNum = '" + phoneNum + "' where EmployeeID = '" + employeeID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateEmpAdress(String employeeID, String adress) {

		try {
			System.out.println("update  Employee set Adress = '" + adress + "' where EmployeeID = '" + employeeID + "';");
			connectDB();
			ExecuteStatement("update  Employee set Adress = '" + adress + "' where EmployeeID = '" + employeeID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateEmpSalary(String EmployeeID, Double salary) {

		try {
			System.out.println("update  Employee set Salary = '" + salary + "' where EmployeeID = '" + EmployeeID + "';");
			connectDB();
			ExecuteStatement("update  Employee set Salary = '" + salary + "' where EmployeeID = '" + EmployeeID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateEmpBankInfo(String EmployeeID, String bankInfo) {

		try {
			System.out.println("update  Employee set BankInfo = '" + bankInfo + "' where EmployeeID = '" + EmployeeID + "';");
			connectDB();
			ExecuteStatement(
					"update  Employee set BankInfo = '" + bankInfo + "' where EmployeeID = '" + EmployeeID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

//	******************************************

	public static void updateDepartmentName(String depID, String name) {

		try {
			System.out.println("update  Department set DepartmentName = '" + name + "' where ID = '" + depID + "';");
			connectDB();
			ExecuteStatement("update  Department set DepartmentName = '" + name + "' where ID = '" + depID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateDepartmentManagerName(String depID, String name) {

		try {
			System.out.println("update  Department set ManagerName = '" + name + "' where ID = '" + depID + "';");
			connectDB();
			ExecuteStatement("update  Department set ManagerName = '" + name + "' where ID = '" + depID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateDepartmentCompanyID(String depID, String compID) {

		try {
			System.out.println("update  Department set CompanyID = '" + compID + "' where ID = '" + depID + "';");
			connectDB();
			ExecuteStatement("update  Department set CompanyID = '" + compID + "' where ID = '" + depID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

//	******************************************

	public static void updateCompanyName(String depID, String compName) {

		try {
			System.out.println("update  Company set CompanyName = '" + compName + "' where CompanyID = '" + depID + "';");
			connectDB();
			ExecuteStatement("update  Company set CompanyName = '" + compName + "' where CompanyID = '" + depID + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static BorderPane createOrderScene() {
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #E5E5E5;");
		root.setPadding(new Insets(0, 10, 0, 0));
		root.setMinHeight(600);
		root.setMinWidth(1000);

		VBox buttonVBox = createButtonVBox();
		VBox centerVBox = createCenterVBox();
		VBox rightVBox = createRightVBox();

		HBox hboxup = new HBox();
		hboxup.getChildren().addAll(centerVBox, rightVBox);

		HBox hboxdown = new HBox();
		hboxdown.getChildren().add(getProductTable());

		VBox mainBox = new VBox();
		mainBox.getChildren().addAll(hboxup, hboxdown);

		// Left VBox for buttons
		root.setLeft(buttonVBox);
		root.setCenter(mainBox);

		return root;
	}

	private static VBox createCenterVBox() {
		VBox centerVBox = new VBox();
		centerVBox.setStyle("-fx-background-color: #E5E5E5;");
		centerVBox.setPrefSize(370, 300);
		centerVBox.setPadding(new Insets(10));

		Label clientInfoLabel = new Label("Client's Info");
		clientInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Set font and bold
		clientInfoLabel.setPrefHeight(40);

		HBox searchHBox = new HBox(10);
		searchHBox.setPadding(new Insets(10));

		TextField searchTextField = new TextField();
		searchTextField.setStyle(" -fx-background-radius: 15; -fx-font-weight: bold; -fx-text-fill: black;");
		searchTextField.setPromptText("Search a Product!");
		Button addToCartbtn = new Button("Add to Cart");
		addHoverEffectYellow(addToCartbtn);
		addToCartbtn.setStyle(
				"-fx-background-color: #FCA311; -fx-background-radius: 15; -fx-font-weight: bold; -fx-text-fill: black;");
		searchHBox.getChildren().addAll(searchTextField, addToCartbtn);

		HBox quantityAndMakeHBox = new HBox(10);
		quantityAndMakeHBox.setPadding(new Insets(10));

		ComboBox<String> quantityComboBox = new ComboBox<>();
		quantityComboBox.setPromptText("Quantity");
		for (int i = 1; i <= 50; i++) {
			quantityComboBox.getItems().add(String.valueOf(i));
		}
		quantityComboBox.setStyle("-fx-background-color: #E5E5E5; -fx-font-weight: bold; -fx-background-radius: 15");

		// Add a style for hovering
		quantityComboBox.setOnMouseEntered(e -> {
			quantityComboBox.setStyle(
					"-fx-background-color: black; -fx-text-fill: #FCA311; -fx-font-weight: bold; -fx-background-radius: 15");
		});

		// Reset the style when not hovering
		quantityComboBox.setOnMouseExited(e -> {
			quantityComboBox.setStyle(
					"-fx-background-color: #E5E5E5; -fx-text-fill: black; -fx-font-weight: bold; -fx-background-radius: 15");
		});

		Button makeOrderbtn = new Button("Make Order");
		addHoverEffectYellow(makeOrderbtn);
		makeOrderbtn.setStyle(
				"-fx-background-color: #FCA311; -fx-background-radius: 15; -fx-font-weight: bold; -fx-text-fill: black;");

		Button cancelOrderbtn = new Button("Cancel Order");
		addHoverEffectYellow(cancelOrderbtn);
		cancelOrderbtn.setStyle(
				"-fx-background-color: #FCA311; -fx-background-radius: 15; -fx-font-weight: bold; -fx-text-fill: black;");

		quantityAndMakeHBox.getChildren().addAll(quantityComboBox, makeOrderbtn, cancelOrderbtn);

		HBox clientInfoHBox = new HBox(10);
		clientInfoHBox.setPadding(new Insets(10));

		GridPane clientInfoGridPane = createClientInfoGridPane();
		clientInfoHBox.getChildren().addAll(clientInfoGridPane);

		ListView<String> listView = new ListView<>();
		listView.setStyle(" -fx-background-radius: 15; -fx-font-weight: bold; -fx-text-fill: black;");

		listView.setMaxWidth(195);
		listView.setMinHeight(180);

		List<String> productNames = (data != null) ? data.stream().map(Product::getName).collect(Collectors.toList())
				: new ArrayList<>();

		FilteredList<String> filteredProductNames = new FilteredList<>(FXCollections.observableArrayList(productNames));

		searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredProductNames
					.setPredicate(productName -> productName.toLowerCase().contains(newValue.toLowerCase()));
		});

		ComboBox<String> suggestionsComboBox = new ComboBox<>(filteredProductNames);
		suggestionsComboBox.setVisibleRowCount(5);

		suggestionsComboBox.setOnAction(event -> {
			String selectedSuggestion = suggestionsComboBox.getSelectionModel().getSelectedItem();
			searchTextField.setText(selectedSuggestion);
			suggestionsComboBox.getSelectionModel().clearSelection();
		});

		final Client[] clientHolder = new Client[1];

		makeOrderbtn.setOnAction(e -> {
			String id = generateClientID();

			Date currentDate = convertLocalDateToSqlDate(LocalDate.now());

			String employeeId = "EMP" + String.format("%02d", new Random().nextInt(16) + 1);

			String orderNumber = getNextOrderNumber();

			Order order = new Order(orderNumber, currentDate, employeeId, id);
			final Client newClient = new Client(id, nameTextField.getText(), numberTextField.getText(),
					emailTextField.getText(), transactionInfoTextField.getText());

			if (order != null) {
				insertOrderData(order, newClient);
				orderList.add(order);
				searchTextField.clear();

				listView.getItems().clear();
				nameTextField.clear();
				numberTextField.clear();
				emailTextField.clear();
				transactionInfoTextField.clear();
		        clientHolder[0] = newClient;
			} else {
				showAlert(AlertType.ERROR, "Empty Field", "Check all fields before adding order!");
				searchTextField.clear();
			}
		});

		cancelOrderbtn.setOnAction(e -> {
			Order order = orderTableView.getSelectionModel().getSelectedItem();
			if (order != null && clientHolder[0] != null) {
				deleteOrderRow(order, clientHolder[0]);
				orderList.remove(order);
			} else {
				showAlert(AlertType.ERROR, "Empty Selection", "There are no records selected!");
			}
		});

		addToCartbtn.setOnAction(e -> {
			String selectedProductName = searchTextField.getText();

			if (data != null) {
				Optional<Product> selectedProduct = data.stream()
						.filter(product -> product.getName().equals(selectedProductName)).findFirst();

				if (selectedProduct.isPresent()) {
					// Set default quantity to 1 if quantityComboBox is null or empty
					int quantity = quantityComboBox.getValue() == null || quantityComboBox.getValue().isEmpty() ? 1
							: Integer.parseInt(quantityComboBox.getValue());

					String listViewItem = selectedProduct.get().getName() + " - Quantity: " + quantity;
					listView.getItems().add(listViewItem);
					searchTextField.clear();
				} else {
					showAlert(AlertType.ERROR, "Product Not Found", "The selected product was not found.");
				}
			}
		});

		HBox listViewHBox = new HBox(10);
		listViewHBox.getChildren().addAll(clientInfoHBox, listView);

		centerVBox.getChildren().addAll(searchHBox, quantityAndMakeHBox, clientInfoLabel, listViewHBox);

		return centerVBox;
	}

	public static int orderNumberCounter = 4;

	public static String getNextOrderNumber() {
		return "Order" + orderNumberCounter++;
	}

	static int clientcounter = 3;

	// Helper method to generate the next employee ID
	private static String generateClientID() {
		clientcounter++;
		return "Client" + String.format("%02d", clientcounter);
	}

	public static Date convertLocalDateToSqlDate(LocalDate localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = localDate.format(formatter);

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			java.util.Date utilDate = simpleDateFormat.parse(formattedDate);
			return new Date(utilDate.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static VBox createRightVBox() {
		VBox rightVBox = new VBox();
		rightVBox.setStyle("-fx-background-color: #E5E5E5;");
		rightVBox.setPadding(new Insets(10));

		Label orderTableLabel = new Label("Order Table");
		orderTableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Set font and bold
		orderTableLabel.setPrefHeight(40);
		orderTableLabel.setPrefWidth(360);

		TableView<Order> orderTableView = createOrderTableView();
		rightVBox.getChildren().addAll(orderTableLabel, orderTableView);

		return rightVBox;
	}

	private static GridPane createClientInfoGridPane() {
		GridPane gridPane = new GridPane();
		gridPane.setStyle("-fx-background-color: #E5E5E5;");
		gridPane.setMinWidth(170);
		gridPane.setMinHeight(180);
		gridPane.setVgap(10); // Adjust the vertical gap as needed
		gridPane.setHgap(10);
		gridPane.setPadding(new Insets(10));

		nameTextField = new TextField();
		nameTextField.setMaxWidth(120);
		nameTextField.setStyle(" -fx-background-radius: 15; -fx-font-weight: bold; -fx-text-fill: black;");
		numberTextField = new TextField();
		numberTextField.setMaxWidth(120);
		numberTextField.setStyle(" -fx-background-radius: 15; -fx-font-weight: bold; -fx-text-fill: black;");
		emailTextField = new TextField();
		emailTextField.setMaxWidth(120);
		emailTextField.setStyle(" -fx-background-radius: 15; -fx-font-weight: bold; -fx-text-fill: black;");
		transactionInfoTextField = new TextField();
		transactionInfoTextField.setMaxWidth(120);
		transactionInfoTextField.setStyle(" -fx-background-radius: 15; -fx-font-weight: bold; -fx-text-fill: black;");

		Label nameLabel = new Label("Name:");
		nameLabel.setMinWidth(50);
		nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		Label numberLabel = new Label("Number:");
		numberLabel.setMinWidth(50);
		numberLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		Label emailLabel = new Label("Email:");
		emailLabel.setMinWidth(50);
		emailLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));
		Label transactionInfoLabel = new Label("Trans Info :");
		transactionInfoLabel.setMinWidth(70);
		transactionInfoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 12));

		nameLabel.setBackground(
				new Background(new BackgroundFill(Color.web("#E5E5E5"), CornerRadii.EMPTY, Insets.EMPTY)));
		numberLabel.setBackground(
				new Background(new BackgroundFill(Color.web("#E5E5E5"), CornerRadii.EMPTY, Insets.EMPTY)));
		emailLabel.setBackground(
				new Background(new BackgroundFill(Color.web("#E5E5E5"), CornerRadii.EMPTY, Insets.EMPTY)));
		transactionInfoLabel.setBackground(
				new Background(new BackgroundFill(Color.web("#E5E5E5"), CornerRadii.EMPTY, Insets.EMPTY)));

		gridPane.add(nameLabel, 0, 1);
		gridPane.add(nameTextField, 1, 1);

		gridPane.add(numberLabel, 0, 2);
		gridPane.add(numberTextField, 1, 2);

		gridPane.add(emailLabel, 0, 3);
		gridPane.add(emailTextField, 1, 3);

		gridPane.add(transactionInfoLabel, 0, 4);
		gridPane.add(transactionInfoTextField, 1, 4);

		return gridPane;
	}

	private static TableView<Order> createOrderTableView() {
		orderTableView = new TableView<>();
		orderTableView.setStyle(" -fx-background-radius: 15;");
		orderTableView.setPrefSize(310, 280);
		orderTableView.setEditable(true);

		TableColumn<Order, String> orderNumberColumn = new TableColumn<>("Order Number");
		orderNumberColumn
				.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getOrderNumber()));

		TableColumn<Order, Date> orderDateColumn = new TableColumn<>("Order Date");
		orderDateColumn.setCellValueFactory(cellData -> {
			java.sql.Date dateValue = (Date) cellData.getValue().getOrderDate();
			return new SimpleObjectProperty<>(dateValue);
		});

		SimpleDateFormat dateFormatDisplay = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat dateFormatEdit = new SimpleDateFormat("yyyy-MM-dd");

		orderDateColumn.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Date>() {
			@Override
			public String toString(Date object) {
				if (object != null) {
					return dateFormatDisplay.format(object);
				} else {
					return "";
				}
			}

			@Override
			public Date fromString(String string) {
				try {
					if (string != null && !string.isEmpty()) {
						return new Date(dateFormatEdit.parse(string).getTime());
					} else {
						return null;
					}
				} catch (ParseException e) {
					e.printStackTrace(); // Handle the parse exception appropriately
					return null;
				}
			}
		}));

		TableColumn<Order, String> employeeIdColumn = new TableColumn<>("Employee ID");
		employeeIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmployeeID()));
		employeeIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn<Order, String> clientIdColumn = new TableColumn<>("Client ID");
		clientIdColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getClientID()));
		clientIdColumn.setCellFactory(TextFieldTableCell.forTableColumn());

		orderDateColumn.setOnEditCommit(event -> {
			// Get the selected item
			Order selectedRecord = orderTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setOrderDate((Date) event.getNewValue());
				updateOrderDate(selectedRecord.getOrderNumber(), (Date) event.getNewValue());
				orderTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Order Date cannot be null!");
			}
		});

		employeeIdColumn.setOnEditCommit(event -> {
			// Get the selected item
			Order selectedRecord = orderTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setEmployeeID((String) event.getNewValue());
				updateOrderEmployeeID(selectedRecord.getOrderNumber(), selectedRecord.getEmployeeID());
				orderTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Employee ID cannot be null or empty!");
			}
		});

		clientIdColumn.setOnEditCommit(event -> {
			// Get the selected item
			Order selectedRecord = orderTableView.getSelectionModel().getSelectedItem();

			if (event.getNewValue() != null && !event.getNewValue().toString().isEmpty()) {
				selectedRecord.setClientID((String) event.getNewValue());
				updateOrderClientID(selectedRecord.getOrderNumber(), selectedRecord.getClientID());
				orderTableView.refresh();
			} else {
				showAlert(AlertType.WARNING, "Invalid Value!", "Client ID cannot be null!");
			}
		});

		orderNumberColumn.setMinWidth(95);
		clientIdColumn.setMinWidth(60);
		orderDateColumn.setMinWidth(90);
		employeeIdColumn.setMinWidth(100);

		orderNumberColumn.setStyle("-fx-background-color: #E5E5E5;");
		clientIdColumn.setStyle("-fx-background-color: #E5E5E5;");
		orderDateColumn.setStyle("-fx-background-color: #E5E5E5;");
		employeeIdColumn.setStyle("-fx-background-color: #E5E5E5;");

		orderTableView.setOnMouseClicked(event -> {
			if (event.getClickCount() == 2) {
				TablePosition<Order, ?> pos = orderTableView.getSelectionModel().getSelectedCells().get(0);
				int row = pos.getRow();
				TableColumn<Order, ?> col = pos.getTableColumn();
				orderTableView.edit(row, col);
			}
		});

		orderTableView.getColumns().addAll(orderNumberColumn, clientIdColumn, orderDateColumn, employeeIdColumn);

		return orderTableView;
	}

	public static VBox getProductTable() {
		VBox productTableVBox = new VBox();
		productTableVBox.setStyle("-fx-background-color: #E5E5E5;");
		productTableVBox.setPrefHeight(350);
		productTableVBox.setPrefWidth(740);

		Label productTableLabel = new Label("Product Table");
		productTableLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18)); // Set font and bold
		productTableLabel.setPrefHeight(29);
		productTableLabel.setPrefWidth(626);

		productTableView = new TableView<>();
		productTableView.setStyle(" -fx-background-radius: 15;");
		productTableView.setPrefSize(800, 400);

		TableColumn<Product, String> idCol = new TableColumn<>("ID");
		idCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getID()));
		idCol.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn<Product, String> nameCol = new TableColumn<>("Name");
		nameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		nameCol.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn<Product, Double> heightCol = new TableColumn<>("Height");
		heightCol.setCellValueFactory(new PropertyValueFactory<>("pr_height"));
		heightCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		TableColumn<Product, Double> lengthCol = new TableColumn<>("Length");
		lengthCol.setCellValueFactory(new PropertyValueFactory<>("pr_length"));
		lengthCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		TableColumn<Product, Double> voltageCol = new TableColumn<>("Voltage");
		voltageCol.setCellValueFactory(new PropertyValueFactory<>("pr_voltage"));
		voltageCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		TableColumn<Product, Double> wattageCol = new TableColumn<>("Wattage");
		wattageCol.setCellValueFactory(new PropertyValueFactory<>("pr_wattage"));
		wattageCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		TableColumn<Product, String> colorCol = new TableColumn<>("Color");
		colorCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getColor()));
		colorCol.setCellFactory(TextFieldTableCell.forTableColumn());

		TableColumn<Product, Double> quantityCol = new TableColumn<>("Quantity");
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("pr_quantity"));

		TableColumn<Product, Double> priceCol = new TableColumn<>("Price");
		priceCol.setCellValueFactory(new PropertyValueFactory<>("pr_price"));
		priceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));

		TableColumn<Product, String> supplierNameCol = new TableColumn<>("Supplier's Name");
		supplierNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getSupplierID()));
		supplierNameCol.setCellFactory(TextFieldTableCell.forTableColumn());

		idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
		nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
		supplierNameCol.setCellValueFactory(new PropertyValueFactory<>("SupplierID"));
		voltageCol.setCellValueFactory(new PropertyValueFactory<>("Voltage"));
		wattageCol.setCellValueFactory(new PropertyValueFactory<>("Wattage"));
		colorCol.setCellValueFactory(new PropertyValueFactory<>("Color"));
		heightCol.setCellValueFactory(new PropertyValueFactory<>("Height"));
		lengthCol.setCellValueFactory(new PropertyValueFactory<>("Length"));
		priceCol.setCellValueFactory(new PropertyValueFactory<>("Price"));
		quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));

		idCol.setPrefWidth(100.0);
		nameCol.setPrefWidth(100.0);
		supplierNameCol.setPrefWidth(115.0);
		voltageCol.setPrefWidth(57.0);
		wattageCol.setPrefWidth(57.0);
		colorCol.setPrefWidth(90.0);
		heightCol.setPrefWidth(55.0);
		lengthCol.setPrefWidth(55.0);
		quantityCol.setPrefWidth(60);
		priceCol.setPrefWidth(80.0);

		idCol.setStyle("-fx-background-color: #E5E5E5;");
		nameCol.setStyle("-fx-background-color: #E5E5E5;");
		supplierNameCol.setStyle("-fx-background-color: #E5E5E5;");
		voltageCol.setStyle("-fx-background-color: #E5E5E5;");
		wattageCol.setStyle("-fx-background-color: #E5E5E5;");
		colorCol.setStyle("-fx-background-color: #E5E5E5;");
		heightCol.setStyle("-fx-background-color: #E5E5E5;");
		lengthCol.setStyle("-fx-background-color: #E5E5E5;");
		priceCol.setStyle("-fx-background-color: #E5E5E5;");
		quantityCol.setStyle("-fx-background-color: #E5E5E5;");

		productTableView.getColumns().addAll(idCol, nameCol, heightCol, lengthCol, voltageCol, wattageCol, colorCol,
				quantityCol, priceCol, supplierNameCol);

		productTableVBox.getChildren().addAll(productTableLabel, productTableView);
		return productTableVBox;
	}

	public static void updateOrderDate(String orderNumber, Date date) {

		try {
			System.out.println("update  Orders set OrderDate = '" + date + "' where OrderNumber = '" + orderNumber + "';");
			connectDB();
			ExecuteStatement("update  Orders set OrderDate = '" + date + "' where OrderNumber = '" + orderNumber + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateOrderEmployeeID(String orderNumber, String id) {

		try {
			System.out.println("update  Orders set EmployeeID = '" + id + "' where OrderNumber = '" + orderNumber + "';");
			connectDB();
			ExecuteStatement("update  Orders set EmployeeID = '" + id + "' where OrderNumber = '" + orderNumber + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void updateOrderClientID(String orderNumber, String id) {

		try {
			System.out.println("update  Orders set ClientID = '" + id + "' where OrderNumber = '" + orderNumber + "';");
			connectDB();
			ExecuteStatement("update  Orders set ClientID = '" + id + "' where OrderNumber = '" + orderNumber + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ****************************************

	private static void insertOrderData(Order order, Client client) {
		try {
			// Step 1: Connect to the database
			connectDB();

			// Step 2: Insert client data first
			ExecuteStatement("INSERT INTO Clients (ClientID, ClientName, PhoneNumber, EmailAddress, BankInfo) VALUES ('"
					+ client.getClientID() + "', '" + client.getClientName() + "', '" + client.getPhoneNumber() + "', '"
					+ client.getEmailAddress() + "', '" + client.getBankInfo() + "');");

			// Step 3: Insert order data
			ExecuteStatement("INSERT INTO Orders (OrderNumber, OrderDate, EmployeeID, ClientID) VALUES ('"
					+ order.getOrderNumber() + "', '" + order.getOrderDate() + "', '" + order.getEmployeeID() + "', '"
					+ order.getClientID() + "');");

			// Step 4: Close the connection
			con.close();
			System.out.println("Order successfully inserted for OrderNumber: " + order.getOrderNumber());

		} catch (SQLException e) {
			// Step 5: Handle SQL exceptions
			e.printStackTrace();
			System.err.println("Error inserting order: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			// Step 6: Handle class not found exceptions
			e.printStackTrace();
			System.err.println("Error loading MySQL JDBC driver: " + e.getMessage());
		}
	}

	// ****************************************

	private static void deleteOrderRow(Order row, Client client) {
		// TODO Auto-generated method stub

		try {
			System.out.println("delete from  Orders where OrderNumber = '" + row.getOrderNumber() + "';");
			System.out.println("delete from  Clients where ClientID = '" + client.getClientID() + "';");
			connectDB();
			ExecuteStatement("delete from  Orders where OrderNumber = '" + row.getOrderNumber() + "';");
			ExecuteStatement("delete from  Clients where ClientID = '" + client.getClientID() + "';");
			con.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	// ****************************************

	public static BorderPane createMainScene() {
		BorderPane root = new BorderPane();
		root.setStyle("-fx-background-color: #E5E5E5;");
		root.setPadding(new Insets(0, 10, 0, 0));
		root.setMinHeight(600);
		root.setMinWidth(1000);

		VBox buttonVBox = createButtonVBox();

		VBox mainBox = new VBox();
		Label adminLabel = new Label("This section is for admins only");
		adminLabel.setStyle("-fx-text-fill: black; -fx-font-size: 24; -fx-opacity: 0.4; -fx-font-weight: bold;");
		mainBox.setAlignment(Pos.CENTER);
		mainBox.getChildren().add(adminLabel);

		// Left VBox for buttons
		root.setLeft(buttonVBox);
		root.setCenter(mainBox);

		return root;
	}

	private static void showAlert(AlertType t, String string, String string2) {
		Alert alert = new Alert(t);
		alert.setHeaderText(string);
		alert.setContentText(string2);
		alert.show();

	}

	static void connectDB() throws ClassNotFoundException, SQLException {

		dbURL = "jdbc:mysql://" + URL + ":" + port + "/" + dbName + "?verifyServerCertificate=false";
		Properties p = new Properties();
		p.setProperty("user", dbUsername);
		p.setProperty("password", dbPassword);
		p.setProperty("useSSL", "false");
		p.setProperty("autoReconnect", "true");
		Class.forName("com.mysql.jdbc.Driver");

		con = DriverManager.getConnection(dbURL, p);

	}

	public static void ExecuteStatement(String SQL) throws SQLException {

		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(SQL);
			stmt.close();

		} catch (SQLException s) {
			s.printStackTrace();
			System.out.println("SQL statement is not executed!");

		}

	}

	private static void addHoverEffect(Button button) {
		button.setOnMouseEntered(e -> {
			button.setTextFill(Color.web("#14213D"));
			button.setStyle(
					"-fx-background-color: #FFFFFF; -fx-background-radius: 0; -fx-font-weight: bold; -fx-font-size: 18;");
		});

		button.setOnMouseExited(e -> {
			button.setTextFill(Color.web("#FFFFFF"));
			button.setStyle(
					"-fx-background-color: #14213D; -fx-background-radius: 0; -fx-font-weight: bold; -fx-font-size: 18;");
		});
	}

	private static void addHoverEffectYellow(Button button) {
		button.setOnMouseEntered(e -> {
			button.setTextFill(Color.web("#FCA311"));
			button.setStyle("-fx-background-color: #000000; -fx-background-radius: 15; -fx-font-weight: bold;");
		});

		button.setOnMouseExited(e -> {
			button.setTextFill(Color.web("#000000"));
			button.setStyle("-fx-background-color: #FCA311; -fx-background-radius: 15; -fx-font-weight: bold;");
		});
	}

	private static void addLogoutButtonEffects(Button logoutButton) {
		logoutButton.setOnMouseEntered(e -> {
			logoutButton.setTextFill(Color.web("#14213D"));
			logoutButton.setStyle(
					"-fx-background-color: #FCA311; -fx-background-radius: 0; -fx-font-weight: bold; -fx-font-size: 18;");
		});

		logoutButton.setOnMouseExited(e -> {
			logoutButton.setTextFill(Color.web("#FCA311"));
			logoutButton.setStyle(
					"-fx-background-color: #14213D; -fx-background-radius: 0; -fx-font-weight: bold; -fx-font-size: 18;");
		});
	}

	private static void addButtonEffects(Button btn) {
		btn.setOnMouseEntered(e -> {
			btn.setTextFill(Color.web("#14213D"));
			btn.setStyle("-fx-background-color: #FFFFFF; -fx-background-radius: 15; -fx-font-weight: bold;");
		});

		btn.setOnMouseExited(e -> {
			btn.setTextFill(Color.web("#FFFFFF"));
			btn.setStyle("-fx-background-color: #14213D; -fx-background-radius: 15; -fx-font-weight: bold;");
		});
	}

	private static Button createButton(String text) {
		Button button = new Button(text);
		button.setPrefSize(165, 106);
		button.setStyle(
				"-fx-background-color: #14213D; -fx-text-fill: #FFFFFF; -fx-background-radius: 0; -fx-font-weight: bold; -fx-font-size: 18;");
		return button;
	}

	private static Button createButtonLogOut(String text) {
		Button button = new Button(text);
		button.setPrefSize(165, 106);
		button.setStyle(
				"-fx-background-color: #14213D; -fx-text-fill: #FCA311; -fx-background-radius: 0; -fx-font-weight: bold; -fx-font-size: 18;");
		return button;
	}

	public void fillEmployeeTable() {
		empdataList = DBase.getEmployeeData();
		employeesTableView.setItems(empdataList);
	}

	public void fillDepartmentTable() {
		depdataList = DBase.getDepartmentData();
		departmentsTableView.setItems(depdataList);
	}

	public void fillCompanyTable() {
		companydataList = DBase.getCompanyData();
		companyTableView.setItems(companydataList);
	}

	public void fillProductTable() {
		data = DBase.getProductData();
		productTableView.setItems(data);
	}

	public void fillOrderTable() {
		orderList = DBase.getOrderData();
		orderTableView.setItems(orderList);
	}

	public void start(Stage primaryStage) {
		FXPane.primaryStage = primaryStage;
	}

	public void setPrimaryStage(Stage stage) {
		primaryStage = stage;
	}

}
