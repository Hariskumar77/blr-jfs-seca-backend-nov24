package com.bosch.stocktoship.service;

import java.util.ArrayList;
/**
 * QualityControl class handles the quality control process in a production environment.
 * It manages stages of production, collects sample data, allows editing of data, 
 * and submits quality control feedback.
 * 
 * @author BPOO1KOR
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

import com.bosch.stocktoship.entity.ParameterData;

public class QualityControl {
	private ArrayList<String> stages;
	private String currentStage;
	public Map<String, ArrayList<ParameterData>> stageData;

	public QualityControl() {
		stages = new ArrayList<>();
		stageData = new HashMap<>();
		initializeStages();
	}

	private void initializeStages() {
		stages.add("Stamping");
		stages.add("Blanking");
		stages.add("Welding");
		stages.add("Painting");
		stages.add("Assembling");
	}

	public ArrayList<String> getStages() {
		return stages;
	}

	public void displayStages() {
		for (int i = 0; i < stages.size(); i++) {
			System.out.println((i + 1) + ". " + stages.get(i));
		}
	}

	public void setCurrentStage(String stage) {
		currentStage = stage;
		stageData.putIfAbsent(stage, new ArrayList<>());
		System.out.println("\nSelected Stage: " + currentStage);
	}

	/**
	 * Collects data for the given sample size and part code from the user.
	 * @author BPOO1KOR
	 * @param sampleSize the number of samples to collect data for.
	 * @param partCode   the part code to associate the data with.
	 * @param scanner    the scanner object for input.
	 */

	public void collectData(int sampleSize, String partCode, Scanner scanner) {
		ArrayList<ParameterData> parameters = stageData.get(currentStage);

		for (int i = 0; i < sampleSize; i++) {
			System.out.println("\nEntering data for Sample " + (i + 1));
			ParameterData data = new ParameterData(partCode);

			System.out.print("Sheet Metal Thickness: ");
			data.setSheetMetalThickness(scanner.nextDouble());
			scanner.nextLine();

			System.out.print("Lubrication Condition: ");
			data.setLubricationCondition(scanner.nextLine());

			System.out.print("Sheet Metal Strength: ");
			data.setSheetMetalStrength(scanner.nextDouble());
			scanner.nextLine();

			System.out.print("Tool Geometry - Die: ");
			data.setToolGeometryDie(scanner.nextLine());

			System.out.print("Tool Geometry - Punch: ");
			data.setToolGeometryPunch(scanner.nextLine());

			System.out.print("Punch Pressure: ");
			data.setPunchPressure(scanner.nextDouble());
			scanner.nextLine();

			System.out.print("Punch Speed: ");
			data.setPunchSpeed(scanner.nextDouble());
			scanner.nextLine();

			parameters.add(data);
		}
	}

	/**
	 * @author BPOO1KOR
	 * @param editData This method allows the user to edit the data of a specific
	 *                 sample by part number and sample number.
	 */

	public void editData(Scanner scanner) {
		System.out.print("Enter the Part Number to edit: ");
		String partNumber = scanner.next();

		ArrayList<ParameterData> parameters = stageData.get(currentStage);

		List<ParameterData> filteredSamples = parameters.stream().filter(data -> data.getPartCode().equals(partNumber))
				.collect(Collectors.toList());

		if (filteredSamples.isEmpty()) {
			System.out.println("No samples found for Part Number: " + partNumber);
			return;
		}

		System.out.println("Samples available for editing for Part Number " + partNumber + ":");
		for (int i = 0; i < filteredSamples.size(); i++) {
			ParameterData data = filteredSamples.get(i);
			System.out.println((i + 1) + ". Sample Number: " + (i + 1) + " with Part Code: " + data.getPartCode());
		}

		System.out.print("Enter the sample number to edit: ");
		int sampleNumber = scanner.nextInt();
		scanner.nextLine();

		if (sampleNumber > 0 && sampleNumber <= filteredSamples.size()) {
			ParameterData data = filteredSamples.get(sampleNumber - 1);
			System.out.println("Editing data for Sample " + sampleNumber + " with Part Code: " + data.getPartCode());
			System.out.println("Which attribute would you like to edit?");
			System.out.println("1. Sheet Metal Thickness");
			System.out.println("2. Lubrication Condition");
			System.out.println("3. Sheet Metal Strength");
			System.out.println("4. Tool Geometry - Die");
			System.out.println("5. Tool Geometry - Punch");
			System.out.println("6. Punch Pressure");
			System.out.println("7. Punch Speed");
			System.out.print("Enter the number of the attribute to edit (1-7): ");

			int choice = scanner.nextInt();
			scanner.nextLine();

			switch (choice) {
			case 1:
				System.out.print("Sheet Metal Thickness (" + data.getSheetMetalThickness() + "): ");
				data.setSheetMetalThickness(scanner.nextDouble());
				break;
			case 2:
				System.out.print("Lubrication Condition (" + data.getLubricationCondition() + "): ");
				data.setLubricationCondition(scanner.nextLine());
				break;
			case 3:
				System.out.print("Sheet Metal Strength (" + data.getSheetMetalStrength() + "): ");
				data.setSheetMetalStrength(scanner.nextDouble());
				break;
			case 4:
				System.out.print("Tool Geometry - Die (" + data.getToolGeometryDie() + "): ");
				data.setToolGeometryDie(scanner.nextLine());
				break;
			case 5:
				System.out.print("Tool Geometry - Punch (" + data.getToolGeometryPunch() + "): ");
				data.setToolGeometryPunch(scanner.nextLine());
				break;
			case 6:
				System.out.print("Punch Pressure (" + data.getPunchPressure() + "): ");
				data.setPunchPressure(scanner.nextDouble());
				break;
			case 7:
				System.out.print("Punch Speed (" + data.getPunchSpeed() + "): ");
				data.setPunchSpeed(scanner.nextDouble());
				break;
			default:
				System.out.println("Invalid choice. Please try again.");
				return;
			}

			System.out.println("Sample " + sampleNumber + " data updated.");
		} else {
			System.out.println("Invalid sample number. Please try again.");
		}
	}

	public void saveData() {
		System.out.println("Data saved successfully for stage: " + currentStage);
	}

	public void submitData() {
		System.out.println("\n--- Submitting Quality Control Data for " + currentStage + " ---");
		displayData();
	}

	public void displayData() {
		ArrayList<ParameterData> parameters = stageData.get(currentStage);

		int sampleNumber = 1;
		for (ParameterData data : parameters) {
			System.out.println("\nSample " + sampleNumber + ": ");
			System.out.println("Part Code: " + data.getPartCode());
			System.out.println("Sheet Metal Thickness: " + data.getSheetMetalThickness());
			System.out.println("Lubrication Condition: " + data.getLubricationCondition());
			System.out.println("Sheet Metal Strength: " + data.getSheetMetalStrength());
			System.out.println("Tool Geometry - Die: " + data.getToolGeometryDie());
			System.out.println("Tool Geometry - Punch: " + data.getToolGeometryPunch());
			System.out.println("Punch Pressure: " + data.getPunchPressure());
			System.out.println("Punch Speed: " + data.getPunchSpeed());
			sampleNumber++;
		}
	}
}
