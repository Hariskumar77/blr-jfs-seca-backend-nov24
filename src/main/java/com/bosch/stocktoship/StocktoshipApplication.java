package com.bosch.stocktoship;
 
 
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
 
import com.bosch.stocktoship.entity.PartDetails;
import com.bosch.stocktoship.entity.SampleDetails;
import com.bosch.stocktoship.repository.DBConnection;
import com.bosch.stocktoship.service.FeedbackDAO;
import com.bosch.stocktoship.service.PartDetailsDAO;
import com.bosch.stocktoship.service.SampleDetailsDAO;
 
public class StocktoshipApplication {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Scanner scanner = new Scanner(System.in);
		PartDetails partDetails = new PartDetails();
		PartDetailsDAO partDetailsDAO = new PartDetailsDAO();
		SampleDetailsDAO sampleDetailsDAO = new SampleDetailsDAO();
		FeedbackDAO feedbackDAO = new FeedbackDAO();
		List<SampleDetails> sampleDetailsList = new ArrayList<>();
 
		System.out.println("Select the stage:");
		System.out.println("1. Stamping\n2. Blanking\n3. Welding\n4. Painting\n5. Assembling");
 
		int stageChoice = scanner.nextInt();
		scanner.nextLine();
 
		System.out.println("Enter the number of samples: ");
		int samples = scanner.nextInt();
		scanner.nextLine();
 
		System.out.println("Enter the part number: ");
		int partNumber = scanner.nextInt();
		scanner.nextLine();
 
		partDetails.setPartNumber(partNumber);
 
		boolean exitStage = false;
		while (!exitStage) {
			System.out.println("Options: 1. Enter Data 2. Edit Data 3. Delete Data 4. Save 5. Submit 6.Display 7. Enter Feedback 8. Display BOM 9. Exit Stage");
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
				if(deleted == 1) {
					System.out.println("Deleted successfully!!");
				}else {
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
				for(SampleDetails s: sampleDetailsDisplay) {
					System.out.println(s.getSampleId());
					System.out.println(s.getStageName());
				}
				break;
			case 7:
				enterFeedback(scanner, feedbackDAO, partDetailsDAO, sampleDetailsDAO);
                break;
			case 8:
                displayBOM(feedbackDAO);
                break;
            case 9:
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
	private static void enterFeedback(Scanner scanner, FeedbackDAO feedbackDAO, PartDetailsDAO partDetailsDAO, SampleDetailsDAO sampleDetailsDAO) throws ClassNotFoundException, SQLException {
        System.out.print("Enter part number: ");
        int partNumber = scanner.nextInt();
        scanner.nextLine();
 
        PartDetails partDetails = partDetailsDAO.getPartDetails(partNumber);
        if (partDetails == null) {
            System.out.println("Part number not found.");
            return;
        }
 
        System.out.println("Part Description: " + partDetails.getPartDescription());
 
        List<SampleDetails> sampleDetailsList = sampleDetailsDAO.getSamplesByPartNumber(partNumber);
        if (sampleDetailsList.isEmpty()) {
            System.out.println("No samples found for this part number.");
            return;
        }
 
        System.out.println("Available samples for Part Number " + partNumber + ":");
        for (SampleDetails sample : sampleDetailsList) {
            System.out.println("Sample ID: " + sample.getSampleId() + ", Stage Name: " + sample.getStageName());
        }
 
        System.out.print("Enter sample ID: ");
        int sampleID = scanner.nextInt();
        scanner.nextLine();
 
        SampleDetails selectedSample = sampleDetailsList.stream()
                .filter(sample -> sample.getSampleId() == sampleID)
                .findFirst()
                .orElse(null);
 
        if (selectedSample == null) {
            System.out.println("Invalid Sample ID.");
            return;
        }
        String partDescription = feedbackDAO.getPartDescription(partNumber, sampleID);
        if (partDescription == null) {
            System.out.println("No part description found for the given part number and sample ID.");
            return;
        }
        System.out.println("Part Description: " + partDescription);
 
        System.out.print("Enter feedback type (QA Failed / Meet the Requirements): ");
        String feedbackType = scanner.nextLine();
 
        String defectType = null;
        if ("QA Failed".equalsIgnoreCase(feedbackType)) {
           
            System.out.print("Enter defect type (Dimensional Error, Defective Part, Missing Part): ");
            defectType = scanner.nextLine();
 
            
            if (!defectType.equalsIgnoreCase("Dimensional Error") &&
                !defectType.equalsIgnoreCase("Defective Part") &&
                !defectType.equalsIgnoreCase("Missing Part")) {
                System.out.println("Invalid defect type. Please enter a valid defect type.");
                return;
            }
        } else if ("Meet the Requirements".equalsIgnoreCase(feedbackType)) {
           
            defectType = null;
        } else {
            
            System.out.println("Invalid feedback type. Please enter either 'QA Failed' or 'Meet the Requirements'.");
            return;
        }
 
        System.out.print("Enter remarks: ");
        String remarks = scanner.nextLine();
 
        boolean feedbackInserted = feedbackDAO.insertFeedback(partNumber, sampleID, partDescription,feedbackType, defectType, remarks);
        if (feedbackInserted) {
            System.out.println("Feedback added successfully!");
        } else {
            System.out.println("Failed to add feedback. Check if part number and sample ID are valid.");
        }
        
    }
	 private static void displayBOM(FeedbackDAO feedbackDAO) {
	        System.out.println("Displaying BOM...");
 
	       
	        String bomQuery = feedbackDAO.getBOMQuery();
 
	        try (Connection conn = DBConnection.getConnection();
	             Statement stmt = conn.createStatement();
	             ResultSet rs = stmt.executeQuery(bomQuery)) {
 
	            System.out.printf("%-15s%-25s%-25s%-15s%-20s%-15s%-15s\n",
	                    "Part Number", "Part Name", "Part Description", "Sample ID",
	                    "Stage Name", "Feedback Type", "Defect Type");
 
	            while (rs.next()) {
	                String partNumber = rs.getString("partNumber");
	                String partName = rs.getString("partName");
	                String partDescription = rs.getString("partDescription");
	                String sampleID = rs.getString("sampleID");
	                String stageName = rs.getString("stageName");
	                String feedbackType = rs.getString("feedbackType");
	                String defectType = rs.getString("defectType");
 
	                
	                System.out.printf("%-15s%-25s%-25s%-15s%-20s%-15s%-15s\n",
	                        partNumber, partName, partDescription, sampleID,
	                        stageName, feedbackType, defectType);
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
 
 
}