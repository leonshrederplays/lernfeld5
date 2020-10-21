package commands;

import constructors.CustomerList;
import instances.ConfigInstance;

import java.io.Console;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

public class Customers {

    public void passwordManager(List<String> command) {
        ConfigInstance conf = new ConfigInstance();
        Console console = System.console();
        int attempts = 0;
        if(ConfigInstance.isAdminPassed) {
            try {
                if (command.size() >= 1) {
                    Customers.customerDescription(command, command.size() == 2);
                } else {
                    Customers.customer();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            if(console == null) {
                Scanner passwordInput = new Scanner(System.in);
                System.out.println("WARNING: The Console isnt initialized so the password will be visible!");
                do{
                    System.out.println("Enter the Password:");
                    String password = passwordInput.next();
                    if (password.equals("Admin")){
                        conf.lockCustomers();
                        ConfigInstance.isAdminPassed = true;
                        attempts = 3;
                        try {
                            if (command.size() >= 1) {
                                customerDescription(command, command.size() == 2);
                            } else {
                                Customers.customer();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        attempts++;
                        System.out.println("Wrong password! You have " + (3 - attempts) + " tries left!");
                    }
                } while (attempts < 3);
            } else {
                do{
                    char[] password = console.readPassword("Enter your Password:");
                    if (new String(password).equals("Admin")){
                        conf.lockCustomers();
                        ConfigInstance.isAdminPassed = true;
                        attempts = 3;
                        try {
                            if (command.size() >= 1) {
                                customerDescription(command, command.size() == 2);
                            } else {
                                customer();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        attempts++;
                        System.out.println("Wrong password! You have " + (3 - attempts) + " tries left!");
                    }
                } while (attempts < 3);
            }
        }
    }

    public static void customer(){
        List<CustomerList> list = ConfigInstance.customerList;
        list.forEach(customer -> {
            String str ="CustomerID: " + customer.getKUNDENNR()
                    + " | Last name: " + customer.getNACHNAME()
                    + " First name: " + customer.getVORNAME();
            System.out.println(str);
        });
    }

    public static void customerDescription(List<String> arg, boolean isTwo) {
        List<CustomerList> list = ConfigInstance.customerList;
        if (isTwo) {
            list.stream().filter(customer -> arg.get(0).toLowerCase().equals(customer.getNACHNAME().toLowerCase())).findAny().ifPresentOrElse(customer -> {
                if (customer.getVORNAME().toLowerCase().equals(arg.get(1).toLowerCase())) {
                    String str =
                            "Data of Customer: "
                                    + "\nCustomerID: " + customer.getKUNDENNR()
                                    + " | Last name: " + customer.getNACHNAME()
                                    + "\nFirst name: " + customer.getVORNAME() 
                                    + "\nDate of birth: " + customer.getGEBURTSDATUM() 
                                    + "\nStreet: " + customer.getSTRASSE() 
                                    + "\nHouse-Number: " + customer.getHAUSNR() 
                                    + "\nZIP-Code: " + customer.getPLZ() 
                                    + "\nLocation: " + customer.getORT() 
                                    + "\nPhone: " + customer.getTELEFON() 
                                    + "\nE-Mail: " + customer.getEMAIL();
                    System.out.println(str);
                } else {
                    System.out.println("The Customer with that last name: " + arg.get(0) + " and first name: " + arg.get(1) + " does not exist. type customers to list all customers.");
                }
            }, () -> System.out.println("The customer with that last name: " + arg.get(0) + " does not exist. type customers to list all customers."));
        } else {
            try {
                int id = Integer.parseInt(arg.get(0));
                list.stream().filter(customer -> new BigDecimal(id).equals(customer.getKUNDENNR())).findAny().ifPresentOrElse(customer -> {
                    String str =
                            "Data of Customer: "
                                    + "\nCustomerID: " + customer.getKUNDENNR()
                                    + " | Last name: " + customer.getNACHNAME()
                                    + "\nFirst name: " + customer.getVORNAME() 
                                    + "\nDate of birth: " + customer.getGEBURTSDATUM() 
                                    + "\nStreet: " + customer.getSTRASSE() 
                                    + "\nHouse-Number: " + customer.getHAUSNR() 
                                    + "\nZIP-Code: " + customer.getPLZ() 
                                    + "\nLocation: " + customer.getORT() 
                                    + "\nPhone: " + customer.getTELEFON() 
                                    + "\nE-Mail: " + customer.getEMAIL();
                    System.out.println(str);
                }, () -> System.out.println("The Customer: " + arg.get(0) + " does not exist. type customers to list all customers."));
            } catch (NumberFormatException e) {
                System.out.println("type help to see the usage of this command.");

            }
        }
    }
}
