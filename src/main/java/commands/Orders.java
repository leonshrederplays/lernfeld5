package commands;

import constructors.OrderList;
import instances.ConfigInstance;

import java.math.BigDecimal;
import java.util.List;

public class Orders {

    public void orders(){
        List<OrderList> list = ConfigInstance.orderList;
        list.forEach(orders -> {
            String str ="Order-Number: " + orders.getBESTELLNR()
                    + " Customer-Number: " + orders.getKUNDENNR();
            System.out.println(str);
        });
    }

    public void orderDescription(String arg) {
        List<OrderList> list = ConfigInstance.orderList;
        try {
            int id = Integer.parseInt(arg);
            list.stream().filter(orders -> new BigDecimal(id).equals(orders.getBESTELLNR())).findAny().ifPresentOrElse(order -> {
                String str =
                        "Properties of this Order: "
                                + "\nOrdering-Number: " + order.getBESTELLNR()
                                + "\nCustomer-Number: " + order.getKUNDENNR()
                                + "\nOrder date: " + order.getBESTELLDATUM()
                                + "\nInvoice amount: " + order.getRECHNUNGSBETRAG() + "$";

                System.out.println(str);
            }, () -> System.out.println("The order: " + arg + " does not exist. type orders to list all orders."));
        } catch (NumberFormatException e) {
            System.out.println();
            System.out.println("The order must be searched with the order number! to display all orders type orders.");
        }
    }
}
