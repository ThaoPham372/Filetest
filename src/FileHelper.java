import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHelper {
    public static final String FILE_NAME = "products.txt";

    public static void writeProductsToFile(List<Product> productList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Product product : productList) {
                writer.write(product.toString());
                writer.newLine();
            }
            System.out.println("Write file successfully!");
        } catch (Exception e) {
            System.out.println("Write file unsuccessful");
        }
    }

    public static List<Product> readProductsFromFile() {
        List<Product> productList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] split = line.split(",");
                String name = split[0];
                double price = Double.parseDouble(split[1]);
                int quantity = Integer.parseInt(split[2]);
                productList.add(new Product(name, price, quantity));
            }
            System.out.println("Read file successfully!");
        } catch (Exception e) {
            System.out.println("Read file unsuccessful");
        }
        return productList;
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Product> productList = new ArrayList<>();
        int choice;

        while (true) {
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

