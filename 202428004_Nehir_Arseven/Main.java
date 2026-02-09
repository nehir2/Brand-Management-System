import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Main {
    static List<User> allUsers = new ArrayList<>();
    static List<Item> allItems = new ArrayList<>();
    static List<Order> allOrders = new ArrayList<>();
    static List<Campaign> activeCampaigns = new ArrayList<>();

    static final String CLASSIC = "CLASSIC";
    static final String SILVER = "SILVER";
    static final String GOLDEN = "GOLDEN";
    static final double SILVER_DISCOUNT = 0.10;
    static final double GOLDEN_DISCOUNT = 0.15;
    static final double SILVER_LIMIT = 1000.0;
    static final double GOLDEN_LIMIT = 5000.0;

    public static void main(String[] args) {
        if(args.length != 3) {
            System.out.println("Usage: java Main users.txt item.txt commands.txt");
            return;
        }
        loadUsers(args[0]);
        loadItems(args[1]);
        processCommands(args[2]);
    }

    static void loadUsers(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if(parts.length < 2) continue;
                String type = parts[0];
                if (type.equals("ADMIN")) {
                    allUsers.add(new Admin(parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), parts[5]));
                } else if (type.equals("TECH")) {
                    allUsers.add(new Technician(parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), parts[5].equals("1")));
                } else if (type.equals("CUSTOMER")) {
                    allUsers.add(new Customer(parts[1], parts[2], parts[3], Double.parseDouble(parts[4]), parts[5]));
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    static void loadItems(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                String[] p = line.split("\t");
                Item item = parseItem(p);
                if (item != null) {
                    item.stock = 10;
                    allItems.add(item);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    static Item parseItem(String[] p) {
        String type = p[0];
        try {
            if (type.equals("BOOK")) return new Book(Double.parseDouble(p[1]), p[2], p[3], p[4], p[5], Integer.parseInt(p[6]));
            else if (type.equals("CDDVD")) return new CDDVD(Double.parseDouble(p[1]), p[2], p[3], p[4], p[5]);
            else if (type.equals("DESKTOP")) return new Desktop(Double.parseDouble(p[1]), p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[5]), p[6], p[7], Integer.parseInt(p[8]), Integer.parseInt(p[9]), p[10]);
            else if (type.equals("LAPTOP")) return new Laptop(Double.parseDouble(p[1]), p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[5]), p[6], p[7], Integer.parseInt(p[8]), Integer.parseInt(p[9]), p[10].equals("1"));
            else if (type.equals("TABLET")) return new Tablet(Double.parseDouble(p[1]), p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[5]), p[6], p[7], Integer.parseInt(p[8]), Integer.parseInt(p[9]), p[10]);
            else if (type.equals("TV")) return new TV(Double.parseDouble(p[1]), p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[5]), Integer.parseInt(p[6]));
            else if (type.equals("SMARTPHONE")) return new SmartPhone(Double.parseDouble(p[1]), p[2], p[3], Integer.parseInt(p[4]), Integer.parseInt(p[5]), p[6]);
            else if (type.equals("HAIRCARE")) return new HairCare(Double.parseDouble(p[1]), p[2], p[3], p[4].equals("1"), Integer.parseInt(p[5]), Double.parseDouble(p[6]), p[7].equals("1"));
            else if (type.equals("PERFUME")) return new Perfume(Double.parseDouble(p[1]), p[2], p[3], p[4].equals("1"), Integer.parseInt(p[5]), Double.parseDouble(p[6]), Integer.parseInt(p[7]));
            else if (type.equals("SKINCARE")) return new SkinCare(Double.parseDouble(p[1]), p[2], p[3], p[4].equals("1"), Integer.parseInt(p[5]), Double.parseDouble(p[6]), p[7].equals("1"));
        } catch (Exception e) { return null; }
        return null;
    }

    static void processCommands(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.trim().isEmpty()) continue;
                String[] p = line.split("\t");
                String command = p[0];

                switch (command) {
                    case "ADDCUSTOMER": addCustomer(p); break;
                    case "SHOWCUSTOMER": showCustomer(p); break;
                    case "SHOWCUSTOMERS": showCustomers(p); break;
                    case "SHOWADMININFO": showAdminInfo(p); break;
                    case "CREATECAMPAIGN": createCampaign(p); break;
                    case "ADDADMIN": addAdmin(p); break;
                    case "ADDTECH": addTech(p); break;
                    case "LISTITEM": listItem(p); break;
                    case "SHOWITEMSLOWONSTOCK": showItemsLowStock(p); break;
                    case "SHOWVIP": showVip(p); break;
                    case "DISPITEMSOF": dispItemsOf(p); break;
                    case "ADDITEM": addItem(p); break;
                    case "SHOWORDERS": showOrders(p); break;
                    case "CHPASS": changePass(p); break;
                    case "DEPOSITMONEY": depositMoney(p); break;
                    case "SHOWCAMPAIGNS": showCampaigns(p); break;
                    case "ADDTOCART": addToCart(p); break;
                    case "EMPTYCART": emptyCart(p); break;
                    case "ORDER": placeOrder(p); break;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
    }

    static User findUser(String nameOrId) {
        for (User u : allUsers) {
            if (u.getName().equals(nameOrId)) return u;
            if (u instanceof Customer && String.valueOf(((Customer) u).id).equals(nameOrId)) return u;
        }
        return null;
    }
    static Item findItem(int id) {
        for(Item i : allItems) if(i.id == id) return i;
        return null;
    }

    static void addCustomer(String[] p) {
        User admin = findUser(p[1]);
        if (!(admin instanceof Admin)) {
            System.out.println("No admin person named " + p[1] + " exists!");
            return;
        }
        allUsers.add(new Customer(p[2], p[3], p[4], Double.parseDouble(p[5]), p[6]));
    }

    static void showCustomer(String[] p) {
        User admin = findUser(p[1]);
        if (!(admin instanceof Admin)) {
            System.out.println("No admin person named " + p[1] + " exists!");
            return;
        }
        User cust = findUser(p[2]);
        if (cust instanceof Customer) System.out.println(cust.toString());
        else System.out.println("No customer with ID number " + p[2] + " exists!");
    }

    static void showCustomers(String[] p) {
        User admin = findUser(p[1]);
        if (!(admin instanceof Admin)) {
            System.out.println("No admin person named " + p[1] + " exists!");
            return;
        }
        for (User u : allUsers) if (u instanceof Customer) System.out.println(u.toString());
    }

    static void showAdminInfo(String[] p) {
        User u = findUser(p[1]);
        if (u instanceof Admin) System.out.println(u.toString());
        else System.out.println("No admin person named " + p[1] + " exists!");
    }

    static void createCampaign(String[] p) {
        User admin = findUser(p[1]);
        if (!(admin instanceof Admin)) {
            System.out.println("No admin person named " + p[1] + " exists!");
            return;
        }
        double rate = Double.parseDouble(p[5]);
        if (rate > 50) {
            System.out.println("Campaign was not created. Discount rate exceeds maximum rate of 50%.");
            return;
        }
        for(Campaign c : activeCampaigns) {
            if(c.itemType.equalsIgnoreCase(p[4])) {
                System.out.println("Campaign already exists for this type.");
                return;
            }
        }
        activeCampaigns.add(new Campaign(p[1], p[2], p[3], p[4], rate));
    }

    static void addAdmin(String[] p) {
        User admin = findUser(p[1]);
        if (!(admin instanceof Admin)) {
            System.out.println("No admin person named " + p[1] + " exists!");
            return;
        }
        allUsers.add(new Admin(p[2], p[3], p[4], Double.parseDouble(p[5]), p[6]));
    }

    static void addTech(String[] p) {
        User admin = findUser(p[1]);
        if (!(admin instanceof Admin)) {
            System.out.println("No admin person named " + p[1] + " exists!");
            return;
        }
        allUsers.add(new Technician(p[2], p[3], p[4], Double.parseDouble(p[5]), p[6].equals("1")));
    }

    static void listItem(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Admin || u instanceof Technician)) {
            System.out.println("No admin or technician person named " + p[1] + " exists!");
            return;
        }
        for (Item i : allItems) {
            System.out.println(i.getDetails());
            System.out.println("-----------------------");
        }
    }

    static void showItemsLowStock(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Admin || u instanceof Technician)) {
            System.out.println("No admin or technician person named " + p[1] + " exists!");
            return;
        }
        int limit = (p.length > 2) ? Integer.parseInt(p[2]) : Integer.MAX_VALUE;
        for (Item i : allItems) {
            if (i.stock < limit) System.out.println(i.getType() + " : " + i.stock);
        }
    }

    static void showVip(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Admin || u instanceof Technician)) {
            System.out.println("No admin or technician person named " + p[1] + " exists!");
            return;
        }
        for (User user : allUsers) {
            if (user instanceof Customer && ((Customer)user).status.equals(GOLDEN)) {
                System.out.println(user.toString());
            }
        }
    }

    static void dispItemsOf(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Technician)) {
            System.out.println("No technician person named " + p[1] + " exists!");
            return;
        }
        String[] types = p[2].split(":");
        for(String type : types) {
            for(Item i : allItems) {
                if(i.getType().equalsIgnoreCase(type.trim())) System.out.println(i.getDetails());
            }
        }
    }

    static void addItem(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Technician)) {
            System.out.println("No technician person named " + p[1] + " exists!");
            return;
        }

        String[] args = p[2].split(":");
        String type = args[0];
        Item newItem = null;

        try {
            if (type.equals("BOOK")) {
                newItem = new Book(Double.parseDouble(args[1]), args[2], args[3], args[4], args[5], Integer.parseInt(args[6]));
            } else if (type.equals("CDDVD")) {
                newItem = new CDDVD(Double.parseDouble(args[1]), args[2], args[3], args[4], args[5]);
            } else if (type.equals("DESKTOP")) {
                newItem = new Desktop(Double.parseDouble(args[1]), args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), args[6], args[7], Integer.parseInt(args[8]), Integer.parseInt(args[9]), args[10]);
            } else if (type.equals("LAPTOP")) {
                newItem = new Laptop(Double.parseDouble(args[1]), args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), args[6], args[7], Integer.parseInt(args[8]), Integer.parseInt(args[9]), args[10].equals("1"));
            } else if (type.equals("TABLET")) {
                newItem = new Tablet(Double.parseDouble(args[1]), args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), args[6], args[7], Integer.parseInt(args[8]), Integer.parseInt(args[9]), args[10]);
            } else if (type.equals("TV")) {
                newItem = new TV(Double.parseDouble(args[1]), args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), Integer.parseInt(args[6]));
            } else if (type.equals("SMARTPHONE")) {
                newItem = new SmartPhone(Double.parseDouble(args[1]), args[2], args[3], Integer.parseInt(args[4]), Integer.parseInt(args[5]), args[6]);
            } else if (type.equals("HAIRCARE")) {
                newItem = new HairCare(Double.parseDouble(args[1]), args[2], args[3], args[4].equals("1"), Integer.parseInt(args[5]), Double.parseDouble(args[6]), args[7].equals("1"));
            } else if (type.equals("PERFUME")) {
                newItem = new Perfume(Double.parseDouble(args[1]), args[2], args[3], args[4].equals("1"), Integer.parseInt(args[5]), Double.parseDouble(args[6]), Integer.parseInt(args[7]));
            } else if (type.equals("SKINCARE")) {
                newItem = new SkinCare(Double.parseDouble(args[1]), args[2], args[3], args[4].equals("1"), Integer.parseInt(args[5]), Double.parseDouble(args[6]), args[7].equals("1"));
            } else {
                System.out.println("No item type " + type + " found");
                return;
            }

            if (newItem != null) {
                newItem.stock = 10;
                allItems.add(newItem);
            }

        } catch (Exception e) {
            System.out.println("Error adding item: " + e.getMessage());
        }
    }

    static void showOrders(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Technician)) {
            System.out.println("No technician person named " + p[1] + " exists!");
            return;
        }
        if (!((Technician)u).isSenior) {
            System.out.println(u.getName() + " is not authorized to display orders!");
            return;
        }
        System.out.println("Order History:");
        for(Order o : allOrders) {
            System.out.println("Order date: " + o.orderDate + " Customer ID: " + o.customerId + " Total Cost: " + o.totalCost + " Number of purchased items: " + o.itemCount);
        }
    }

    static void changePass(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Customer)) {
            System.out.println("No customer with ID number " + p[1] + " exists!");
            return;
        }
        Customer c = (Customer) u;
        if(!c.password.equals(p[2])) {
            System.out.println("The given password does not match the current password.");
            return;
        }
        c.password = p[3];
        System.out.println("The password has been successfully changed.");
    }

    static void depositMoney(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Customer)) {
            System.out.println("No customer with ID number " + p[1] + " exists!");
            return;
        }
        ((Customer) u).balance += Double.parseDouble(p[2]);
    }

    static void showCampaigns(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Customer)) {
            System.out.println("No customer with ID number " + p[1] + " exists!");
            return;
        }
        if(activeCampaigns.isEmpty()) System.out.println("No campaign has been created so far!");
        else {
            System.out.println("Active campaigns:");
            for(Campaign c : activeCampaigns) System.out.println(c.rate + "% sale of " + c.itemType + " until " + c.endDate);
        }
    }

    static void addToCart(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Customer)) {
            System.out.println("No customer with ID number " + p[1] + " exists!");
            return;
        }
        Item item = findItem(Integer.parseInt(p[2]));
        if(item == null) {
            System.out.println("Invalid item ID");
            return;
        }
        if(item.stock <= 0) {
            System.out.println("We are sorry. The item is temporarily unavailable.");
            return;
        }
        ((Customer)u).cart.add(item);
        System.out.println("The item " + item.getType() + " has been successfully added to your cart.");
    }

    static void emptyCart(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Customer)) {
            System.out.println("No customer with ID number " + p[1] + " exists!");
            return;
        }
        ((Customer)u).cart.clear();
        System.out.println("The cart has been emptied.");
    }

    static void placeOrder(String[] p) {
        User u = findUser(p[1]);
        if (!(u instanceof Customer)) {
            System.out.println("No customer with ID number " + p[1] + " exists!");
            return;
        }
        Customer c = (Customer) u;
        if(!c.password.equals(p[2])) {
            System.out.println("Order could not be placed. Invalid password.");
            return;
        }
        if(c.cart.isEmpty()) {
            System.out.println("You should add some items to your cart before order request!");
            return;
        }

        double totalCost = 0;
        for(Item i : c.cart) {
            double itemPrice = i.price;
            for(Campaign camp : activeCampaigns) {
                if(camp.itemType.equalsIgnoreCase(i.getType())) {
                    itemPrice -= (itemPrice * camp.rate / 100.0);
                    break;
                }
            }
            totalCost += itemPrice;
        }
        if(c.status.equals(SILVER)) totalCost *= (1.0 - SILVER_DISCOUNT);
        else if(c.status.equals(GOLDEN)) totalCost *= (1.0 - GOLDEN_DISCOUNT);

        if(c.balance < totalCost) {
            System.out.println("Order could not be placed. Insufficient funds.");
            return;
        }

        c.balance -= totalCost;
        c.totalSpent += totalCost;
        for(Item i : c.cart) i.stock--;
        allOrders.add(new Order(c.id, totalCost, c.cart.size()));
        c.cart.clear();
        System.out.println("Done! Your order will be delivered as soon as possible. Thank you!");

        if(c.totalSpent >= GOLDEN_LIMIT && !c.status.equals(GOLDEN)) {
            c.status = GOLDEN;
            System.out.println("Congratulations! You have been upgraded to a GOLDEN MEMBER! You have earned a discount of 15% on all purchases.");
        } else if(c.totalSpent >= SILVER_LIMIT && c.status.equals(CLASSIC)) {
            c.status = SILVER;
            System.out.println("Congratulations! You have been upgraded to a SILVER MEMBER! You have earned a discount of 10% on all purchases.");
            System.out.println("You need to spend " + (GOLDEN_LIMIT - c.totalSpent) + " more TL to become a GOLDEN MEMBER.");
        }
    }
}