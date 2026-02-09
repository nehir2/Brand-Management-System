import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class User {
    String name, email, birthDate;
    public User(String name, String email, String birthDate) {
        this.name = name; this.email = email; this.birthDate = birthDate;
    }
    public String getName() { return name; }
}

class Employee extends User {
    double salary;
    public Employee(String n, String e, String b, double s) { super(n, e, b); this.salary = s; }
}

class Admin extends Employee {
    String password;
    public Admin(String n, String e, String b, double s, String p) {
        super(n, e, b, s); this.password = p;
    }
    @Override
    public String toString() {
        return "Admin info\nAdmin name: " + name + "\nAdmin e-mail: " + email + "\nAdmin date of birth: " + birthDate;
    }
}

class Technician extends Employee {
    boolean isSenior;
    public Technician(String n, String e, String b, double s, boolean sen) {
        super(n, e, b, s); this.isSenior = sen;
    }
}

class Customer extends User {
    int id;
    String password;
    double balance;
    String status;
    double totalSpent;
    List<Item> cart;
    static int customerIdCounter = 1;

    public Customer(String n, String e, String b, double bal, String pass) {
        super(n, e, b);
        this.id = customerIdCounter++;
        this.balance = bal;
        this.password = pass;
        this.status = "CLASSIC";
        this.cart = new ArrayList<>();
        this.totalSpent = 0;
    }

    @Override
    public String toString() {
        return "Customer name: " + name + " ID: " + id + " e-mail: " + email + " Date of Birth: " + birthDate + " Status: " + status;
    }
}

class Order {
    Date orderDate;
    int customerId;
    double totalCost;
    int itemCount;

    public Order(int cId, double cost, int count) {
        this.orderDate = new Date();
        this.customerId = cId;
        this.totalCost = cost;
        this.itemCount = count;
    }
}

class Campaign {
    String initiator, startDate, endDate, itemType;
    double rate;
    public Campaign(String init, String start, String end, String type, double rate) {
        this.initiator = init; this.startDate = start; this.endDate = end; this.itemType = type; this.rate = rate;
    }
}