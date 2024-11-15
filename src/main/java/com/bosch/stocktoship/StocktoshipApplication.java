package com.bosch.stocktoship;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.bosch.stocktoship.entity.PartDetails;
import com.bosch.stocktoship.entity.SampleDetails;
import com.bosch.stocktoship.service.PartDetailsDAO;
import com.bosch.stocktoship.service.SampleDetailsDAO;

public class StocktoshipApplication {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
		PartDetails partDetails = new PartDetails();
		PartDetailsDAO partDetailsDAO = new PartDetailsDAO();
		SampleDetailsDAO sampleDetailsDAO = new SampleDetailsDAO();
		List<SampleDetails> sampleDetailsList = new ArrayList<>();

		System.out.println("Select the stage:");
		System.out.println("1. Stamping\n2. Blanking\n3. Welding\n4. Painting\n5. Assembling");

		int stageChoice = scanner.nextInt();
		scanner.nextLine();

		System.out.print("Enter the number of samples: ");
		int samples = scanner.nextInt();
		scanner.nextLine();

		System.out.print("Enter the part number: ");
		int partNumber = scanner.nextInt();
		scanner.nextLine();
        System.out.println();
		partDetails.setPartNumber(partNumber);

		boolean exitStage = false;
		while (!exitStage) {
			System.out.println(
					"Options: 1. Enter Data 2. Edit Data 3. Delete Data 4. Save 5. Submit 6. Display 7. Exit Stage");
			System.out.print("Choose an option: ");
			int option = scanner.nextInt();
			scanner.nextLine();

			switch (option) {
			case 1:
				switch (stageChoice) {
				case 1:
					handleStampingStage(samples, scanner, partDetails, sampleDetailsList);
					break;
				case 2:
					handleBlankingStage(samples, scanner, partDetails, sampleDetailsList);
					break;
				case 3:
					handleWeldingStage(samples, scanner, partDetails, sampleDetailsList);
					break;
				case 4:
					handlePaintingStage(samples, scanner, partDetails, sampleDetailsList);
					break;
				case 5:
					handleAssemblingStage(samples, scanner, partDetails, sampleDetailsList);
					break;
				default:
					System.out.println("Invalid stage selected.");
				}
				break;
			case 2:
				System.out.print("Enter the sample ID to edit: ");
				int sampleToEdit = scanner.nextInt();
				scanner.nextLine();
				SampleDetails sample = sampleDetailsDAO.getSampleDetails(sampleToEdit);
				if (sample != null) {
					editSampleData(sample, scanner);
					sampleDetailsDAO.updateSampleDetails(sample);
				} else {
					System.out.println("Sample ID not found.");
				}
				break;
			case 3:
				System.out.print("Enter the sample id to delete: ");
				int sampleToDelete = scanner.nextInt();
				scanner.nextLine();
				int deleted = sampleDetailsDAO.deleteSampleDetails(sampleToDelete);
				if (deleted == 1) {
					System.out.println("Deleted successfully!!");
				} else {
					System.out.println("Sample details is not available");
				}
				break;
			case 4:
				sampleDetailsDAO.saveSampleDetails(sampleDetailsList);
				System.out.println("Data saved successfully!");
				break;
			case 5:
				System.out.println("Data submitted!");
				exitStage = true;
				break;
			case 6:
				System.out.println("Displaying details: ");
				List<SampleDetails> sampleDetailsDisplay = new ArrayList<SampleDetails>();
				sampleDetailsDisplay = sampleDetailsDAO.getAllSampleDetails();
				for (SampleDetails s : sampleDetailsDisplay) {
					System.out.print(s.getSampleId() + " ");
					System.out.println(s.getStageName());
				}
				break;
			case 7:
				exitStage = true;
				break;
			default:
				System.out.println("Invalid option. Try again.");
			}
		}
	}

	private static void handleStampingStage(int samples, Scanner scanner, PartDetails partDetails,
			List<SampleDetails> sampleDetailsList) {
		for (int i = 0; i < samples; i++) {
			SampleDetails sampleDetails = new SampleDetails();
			sampleDetails.setPartDetails(partDetails);
			sampleDetails.setStageName("Stamping");
			System.out.println("Entering data for sample " + (i + 1));
			System.out.print("Bend radius: ");
			sampleDetails.setBendRadius(scanner.nextDouble());
			System.out.print("Forming force: ");
			sampleDetails.setFormingForce(scanner.nextDouble());
			scanner.nextLine();
			sampleDetailsList.add(sampleDetails);
		}
	}

	private static void handleBlankingStage(int samples, Scanner scanner, PartDetails partDetails,
			List<SampleDetails> sampleDetailsList) {
		for (int i = 0; i < samples; i++) {
			SampleDetails sampleDetails = new SampleDetails();
			sampleDetails.setPartDetails(partDetails);
			sampleDetails.setStageName("Blanking");
			System.out.println("Entering data for sample " + (i + 1));
			System.out.print("Sheet metal thickness: ");
			sampleDetails.setSheetMetalThickness(scanner.nextDouble());
			scanner.nextLine();
			System.out.print("Lubrication condition: ");
			sampleDetails.setLubricationCondition(scanner.nextLine());
			System.out.print("Sheet metal strength: ");
			sampleDetails.setSheetMetalStrength(scanner.nextLine());
			scanner.nextLine();
			System.out.print("Tool geometry - die: ");
			sampleDetails.setToolGeometryDie(scanner.nextLine());
			System.out.print("Tool geometry - punch: ");
			sampleDetails.setToolGeometryPunch(scanner.nextLine());
			System.out.print("Punch pressure: ");
			sampleDetails.setPunchPressure(scanner.nextDouble());
			System.out.print("Punch speed: ");
			sampleDetails.setPunchSpeed(scanner.nextDouble());
			scanner.nextLine();
			sampleDetailsList.add(sampleDetails);
		}
	}

	private static void handleWeldingStage(int samples, Scanner scanner, PartDetails partDetails,
			List<SampleDetails> sampleDetailsList) {
		for (int i = 0; i < samples; i++) {
			SampleDetails sampleDetails = new SampleDetails();
			sampleDetails.setPartDetails(partDetails);
			sampleDetails.setStageName("Welding");
			System.out.println("Entering data for sample " + (i + 1));
			System.out.print("Ductility of welded joint: ");
			sampleDetails.setDuctility(scanner.nextLine());
			System.out.print("Welding speed: ");
			sampleDetails.setWeldingSpeed(scanner.nextDouble());
			scanner.nextLine();
			System.out.print("Welding method: ");
			sampleDetails.setWeldingMethod(scanner.nextLine());
			sampleDetailsList.add(sampleDetails);
		}
	}

	private static void handlePaintingStage(int samples, Scanner scanner, PartDetails partDetails,
			List<SampleDetails> sampleDetailsList) {
		for (int i = 0; i < samples; i++) {
			SampleDetails sampleDetails = new SampleDetails();
			sampleDetails.setPartDetails(partDetails);
			sampleDetails.setStageName("Painting");
			System.out.println("Entering data for sample " + (i + 1));
			System.out.print("Paint type: ");
			sampleDetails.setPaintType(scanner.nextLine());
			System.out.print("Coating thickness: ");
			sampleDetails.setCoatingThickness(scanner.nextDouble());
			scanner.nextLine();
			sampleDetailsList.add(sampleDetails);
		}
	}

	private static void handleAssemblingStage(int samples, Scanner scanner, PartDetails partDetails,
			List<SampleDetails> sampleDetailsList) {
		for (int i = 0; i < samples; i++) {
			SampleDetails sampleDetails = new SampleDetails();
			sampleDetails.setPartDetails(partDetails);
			sampleDetails.setStageName("Assembling");
			System.out.println("Entering data for sample " + (i + 1));
			System.out.print("Alignment: ");
			sampleDetails.setAlignment(scanner.nextLine());
			System.out.print("Functional checks: ");
			sampleDetails.setFunctionalChecks(scanner.nextLine());
			sampleDetailsList.add(sampleDetails);
		}
	}

	private static void editSampleData(SampleDetails sampleDetails, Scanner scanner) {
		System.out.println("Editing data for sample ID: " + sampleDetails.getSampleId());
		System.out.println("Select the field to edit:");
		switch (sampleDetails.getStageName()) {
		case "Stamping":
			System.out.println("1. Bend Radius\n2. Forming Force");
			int stampingChoice = scanner.nextInt();
			scanner.nextLine();
			if (stampingChoice == 1) {
				System.out.print("Enter new bend radius: ");
				sampleDetails.setBendRadius(scanner.nextDouble());
			} else if (stampingChoice == 2) {
				System.out.print("Enter new forming force: ");
				sampleDetails.setFormingForce(scanner.nextDouble());
			}
			break;
		case "Blanking":
			System.out.println(
					"1. Sheet Metal Thickness\n2. Lubrication Condition\n3. Sheet Metal Strength\n4. Tool Geometry - Die\n5. Tool Geometry - Punch\n6. Punch Pressure\n7. Punch Speed");
			int blankingChoice = scanner.nextInt();
			scanner.nextLine();
			handleBlankingEdit(blankingChoice, sampleDetails, scanner);
			break;
		case "Welding":
			System.out.println("1. Ductility of Welded Joint\n2. Welding Speed\n3. Welding Method");
			int weldingChoice = scanner.nextInt();
			scanner.nextLine();
			handleWeldingEdit(weldingChoice, sampleDetails, scanner);
			break;
		case "Painting":
			System.out.println("1. Paint Type\n2. Coating Thickness");
			int paintingChoice = scanner.nextInt();
			scanner.nextLine();
			handlePaintingEdit(paintingChoice, sampleDetails, scanner);
			break;
		case "Assembling":
			System.out.println("1. Alignment\n2. Functional Checks");
			int assemblingChoice = scanner.nextInt();
			scanner.nextLine();
			handleAssemblingEdit(assemblingChoice, sampleDetails, scanner);
			break;
		default:
			System.out.println("Invalid stage selected for editing.");
		}
		System.out.println("Sample data updated.");
	}

	private static void handleBlankingEdit(int choice, SampleDetails sampleDetails, Scanner scanner) {
		switch (choice) {
		case 1:
			System.out.print("Enter new sheet metal thickness: ");
			sampleDetails.setSheetMetalThickness(scanner.nextDouble());
			break;
		case 2:
			System.out.print("Enter new lubrication condition: ");
			sampleDetails.setLubricationCondition(scanner.nextLine());
			break;
		case 3:
			System.out.print("Enter new sheet metal strength: ");
			sampleDetails.setSheetMetalStrength(scanner.nextLine());
			break;
		case 4:
			System.out.print("Enter new tool geometry - die: ");
			sampleDetails.setToolGeometryDie(scanner.nextLine());
			break;
		case 5:
			System.out.print("Enter new tool geometry - punch: ");
			sampleDetails.setToolGeometryPunch(scanner.nextLine());
			break;
		case 6:
			System.out.print("Enter new punch pressure: ");
			sampleDetails.setPunchPressure(scanner.nextDouble());
			break;
		case 7:
			System.out.print("Enter new punch speed: ");
			sampleDetails.setPunchSpeed(scanner.nextDouble());
			break;
		default:
			System.out.println("Invalid option selected.");
		}
	}

	private static void handleWeldingEdit(int choice, SampleDetails sampleDetails, Scanner scanner) {
		switch (choice) {
		case 1:
			System.out.print("Enter new ductility of welded joint: ");
			sampleDetails.setDuctility(scanner.nextLine());
			break;
		case 2:
			System.out.print("Enter new welding speed: ");
			sampleDetails.setWeldingSpeed(scanner.nextDouble());
			break;
		case 3:
			System.out.print("Enter new welding method: ");
			sampleDetails.setWeldingMethod(scanner.nextLine());
			break;
		default:
			System.out.println("Invalid option selected.");
		}
	}

	private static void handlePaintingEdit(int choice, SampleDetails sampleDetails, Scanner scanner) {
		switch (choice) {
		case 1:
			System.out.print("Enter new paint type: ");
			sampleDetails.setPaintType(scanner.nextLine());
			break;
		case 2:
			System.out.print("Enter new coating thickness: ");
			sampleDetails.setCoatingThickness(scanner.nextDouble());
			break;
		default:
			System.out.println("Invalid option selected.");
		}
	}

	private static void handleAssemblingEdit(int choice, SampleDetails sampleDetails, Scanner scanner) {
		switch (choice) {
		case 1:
			System.out.print("Enter new alignment: ");
			sampleDetails.setAlignment(scanner.nextLine());
			break;
		case 2:
			System.out.print("Enter new functional checks: ");
			sampleDetails.setFunctionalChecks(scanner.nextLine());
			break;
		default:
			System.out.println("Invalid option selected.");
		}
	}

}
