package commands;

import constructors.CustomerList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.util.List;

public class Customers {

    public static void customer(){
        List<CustomerList> list = ConfigInstance.customerList;
        list.forEach(customer -> {
            String str ="Customer-Number: " + customer.getKUNDENNR()
                    + " | Last name: " + customer.getNACHNAME()
                    + " First name: " + customer.getVORNAME();
            System.out.println(str);
        });
    }

    public static void customerDescription(String[] arg, boolean isTwo) {
        List<CustomerList> list = ConfigInstance.customerList;
        if (isTwo) {
            list.stream().filter(customer -> arg[1].toLowerCase().equals(customer.getNACHNAME().toLowerCase())).findAny().ifPresentOrElse(customer -> {
                if (customer.getVORNAME().toLowerCase().equals(arg[2].toLowerCase())) {
                    String str =
                            "Data of Customer: "
                                    + "Customer-Number: " + customer.getKUNDENNR()
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
                    System.out.println("The Customer with that last name: " + arg[1] + " and first name: " + arg[2] + " does not exist. type customers to list all customers.");
                }
            }, () -> {
                System.out.println("The customer with that last name: " + arg[1] + " does not exist. type customers to list all customers.");
            });
        } else {
            try {
                int id = Integer.parseInt(arg[1]);
                list.stream().filter(customer -> new BigDecimal(id).equals(customer.getKUNDENNR())).findAny().ifPresentOrElse(customer -> {
                    String str =
                            "Data of Customer: "
                                    + "Customer-Number: " + customer.getKUNDENNR()
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
                }, () -> {
                    System.out.println("The Customer: " + arg[1] + " does not exist. type customers to list all customers.");
                });
            } catch (NumberFormatException e) {
                System.out.println("type help to see the usage of this command.");

            }
        }
    }
}
