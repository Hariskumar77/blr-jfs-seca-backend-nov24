package com.bosch.stocktoship;

import java.util.Scanner;

import com.bosch.stocktoship.service.QualityControl;

/**
 * class for handling user interactions in the Quality Control process.
 * This application allows users to select different quality control stages, 
 * enter or edit sample data, save it, and submit for inspection.
 * 
 * @author BPOO1KOR
 */

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		QualityControl qualityControl = new QualityControl();
		String partCode = null;
		System.out.println("Quality Control");
		while (true) {
			System.out.println("\nSelect the stage:");
			qualityControl.displayStages();
			int stageChoice = scanner.nextInt();
			scanner.nextLine();

			if (stageChoice > 0 && stageChoice <= qualityControl.getStages().size()) {
				String selectedStage = qualityControl.getStages().get(stageChoice - 1);
				qualityControl.setCurrentStage(selectedStage);

				boolean exitStage = false;
				while (!exitStage) {

					System.out.println("\nOptions: 1. Enter Data 2. Edit Data 3. Save 4. Submit 5. Exit Stage");
					System.out.print("Choose an option: ");

					int option = scanner.nextInt();
					switch (option) {
					case 1:
						System.out.print("Enter the sample size: ");
						int sampleSize = scanner.nextInt();
						scanner.nextLine();

						System.out.print("Enter/Scan the part code: ");
						partCode = scanner.nextLine();

						qualityControl.collectData(sampleSize, partCode, scanner);
						break;

					case 2:
						qualityControl.editData(scanner);
						break;

					case 3:
						qualityControl.saveData();
						break;

					case 4:
						qualityControl.submitData();
						break;

					case 5:
						exitStage = true;
						break;

					default:
						System.out.println("Invalid option. Please try again.");
					}
				}
			} else {
				System.out.println("Invalid stage selection. Try again.");
			}
		}
	}
}