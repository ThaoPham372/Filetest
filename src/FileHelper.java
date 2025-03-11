import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {
    public static final String FILE_NAME = "products.txt";

    public static void showCurrentFolder() {
        File currentDirectory = new File(".");
        try {
            System.out.println("Current directory: " + currentDirectory.getCanonicalPath());
            System.out.println("Add your file to this directory.");
        } catch (IOException e) {
            System.out.println("An error occurred while getting the current directory.");
        }
    }

    public static void writeProductsToFile(List<Product> productList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Product product : productList) {
                // Ensure consistent format with no extra spaces around commas
                writer.write(String.format("%s,%f,%d",
                        product.getName(), product.getPrice(), product.getQuantity()));
                writer.newLine();
            }
            System.out.println("Write file successfully!");
        } catch (Exception e) {
            System.out.println("Write file unsuccessful: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static List<Product> readProductsFromFile() {
        List<Product> productList = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("File does not exist: " + FILE_NAME);
            return productList;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                try {
                    String[] split = line.split(",");
                    if (split.length < 3) {
                        System.out.println("Warning: Line " + lineNumber + " has invalid format: " + line);
                        continue;
                    }
                    String name = split[0].trim();
                    double price = Double.parseDouble(split[1].trim());
                    int quantity = Integer.parseInt(split[2].trim());
                    Product product = new Product(name, price, quantity);
                    productList.add(product);
                } catch (NumberFormatException e) {
                    System.out.println("Error parsing line " + lineNumber + ": " + line);
                    System.out.println("Error details: " + e.getMessage());
                }
            }

            if (productList.isEmpty()) {
                System.out.println("No valid products found in file");
            } else {
                System.out.println("Read file successfully! Found " + productList.size() + " products.");
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return productList;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Product> productList = new ArrayList<>();
        int choice;

        while (true) {
            showCurrentFolder();
            System.out.println("Chọn chức năng:");
            System.out.println("1. Enter product and write to file: ");
            System.out.println("2. Read list of product and read file");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.println("Enter product name: ");
                    String name = sc.nextLine();
                    System.out.println("Enter product price: ");
                    double price = sc.nextDouble();
                    System.out.println("Enter product quantity: ");
                    int quantity = sc.nextInt();
                    sc.nextLine();
                    productList.add(new Product(name, price, quantity));
                    writeProductsToFile(productList);
                    break;

                case 2:
                    List<Product> readProducts = readProductsFromFile();
                    for (Product product : readProducts) {
                        System.out.println(product);
                    }
                    break;

                case 3:
                    System.out.println("Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        }
    }
}
