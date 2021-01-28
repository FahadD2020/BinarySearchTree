import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

/**
 * COP 3530: Project 2 - Binary Search Trees
 * <p>
 *     This code tests the functionality of BinarySearchTree class.
 *     This calls and prints the various methods defined.
 * </p>
 *
 * @author Fahad Dawood n01425457
 * @version 19 July, 2020
 * 
 */
public class Project2 { // open
    private static String dashedLine = "-----------------------------------------";

 /**
 * Main driver code that tests BinarySearchTree functionality.
 *
 * @param args command line arguments to this class. We pass nothing.
 * @throws Exception incase of any exception (file IO / number format etc)
 */
    public static void main(String[] args) throws Exception { // main open
        // read input (Countries2.csv)
        System.out.print("Enter the file name: ");
        Scanner scanner = new Scanner(System.in);
        String fileName = scanner.nextLine();

        // create BST from file read
        BinarySearchTree bst = new BinarySearchTree();
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        br.readLine();  //ignore 1st header line

        String line;
        while ((line = br.readLine()) != null) { // while open
            String[] splits = line.split(",");
            String name = splits[0];
            double population = Double.valueOf(splits[3]);
            double gdp =  Double.valueOf(splits[4]);
            double gdpPerCapita = gdp / population;
            bst.insert(name, gdpPerCapita);
        } // while close

        // inorder traversal
        System.out.println("\nInorder Traversal: \n");
        System.out.printf("%-25s%-20s\n", "Name", "GDP Per Capita");
        System.out.println(dashedLine);
        bst.printInorder();
        System.out.println("");

        // delete "Australia", "Czech Republic" and "Norway"
        String[] toDelete = {"Australia", "Czech Republic", "Norway"};
        deleteCountries(bst, toDelete);

        // preorder traversal
        System.out.println("\nPreorder Traversal: \n");
        System.out.printf("%-25s%-20s\n", "Name", "GDP Per Capita");
        System.out.println(dashedLine);
        bst.printPreorder();
        System.out.println("");

        // search for "Sri Lanka", "North Cyprus", "Czech Republic", "Norway"
        // using find() method. Print GDP info of the found countries and
        // not-found message if not found. Also print the no. of nodes
        // visited to find or not fine the target.
        String[] toFind = {"Sri Lanka", "North Cyprus", "Czech Republic", "Norway"};

        for (int i = 0; i < toFind.length; i++) { // for open
            bst.find(toFind[i]);
        } // for close 

        // Delete "Malawi", "Somalia", "Canada", "Tunisia" and "New Zealand"
        String[] toDel = {"Malawi", "Somalia", "Canada", "Tunisia", "New Zealand"};
        deleteCountries(bst, toDel);

        // and postorder the remaining tree.
        System.out.println("\nPostorder Traversal: \n");
        System.out.printf("%-25s%-20s\n", "Name", "GDP Per Capita");
        System.out.println(dashedLine);
        bst.printPostOrder();
        System.out.println("");

        // printBottomTen
        System.out.println("Bottom ten countries regarding GDP per Capita\n");
        System.out.printf("%-25s%-20s\n", "Name", "GDP Per Capita");
        System.out.println(dashedLine);
        bst.printBottomTen();

        // printTopTen
        System.out.println("\nTop ten countries regarding GDP per Capita\n");
        System.out.printf("%-25s%-20s\n", "Name", "GDP Per Capita");
        System.out.println(dashedLine);
        bst.printTopTen();
    } // main close

    // helper method to delete all counties in a given array from a given bst
    private static void deleteCountries(BinarySearchTree bst, String[] countries) { // open delete
        for (int i = 0; i < countries.length; i++) { // for
            bst.delete(countries[i]);
            System.out.printf("%s has been deleted from tree\n", countries[i]);
        } // for
    } // close delete
} // end
