import java.util.Date;

public abstract class Item {
    protected int id;
    protected double price;
    protected int stock;
    static int idCounter = 1;

    public Item(double price, int stock) {
        this.id = idCounter++;
        this.price = price;
        this.stock = stock;
    }

    public abstract String getType();

    public String getDetails() {
        return "Item ID: " + id + "\nPrice: " + price + " $";
    }
}

abstract class Cosmetic extends Item {
    String manufacturer, brand;
    boolean isOrganic;
    int expirationYear;
    double weight;

    public Cosmetic(double price, String manufacturer, String brand, boolean isOrganic, int expirationYear, double weight) {
        super(price, 0);
        this.manufacturer = manufacturer;
        this.brand = brand;
        this.isOrganic = isOrganic;
        this.expirationYear = expirationYear;
        this.weight = weight;
    }
    @Override
    public String getDetails() {
        return super.getDetails() + "\nManufacturer: " + manufacturer + "\nBrand: " + brand + "\nOrganic: " + (isOrganic ? "Yes" : "No") + "\nExpiration Date: " + expirationYear + "\nWeight: " + weight + " g.";
    }
}

class Perfume extends Cosmetic {
    int lastingDuration;
    public Perfume(double price, String manuf, String brand, boolean org, int exp, double weight, int lasting) {
        super(price, manuf, brand, org, exp, weight);
        this.lastingDuration = lasting;
    }
    @Override public String getType() { return "PERFUME"; }
    @Override public String getDetails() { return "Type: Perfume\n" + super.getDetails() + "\nLasting Duration: " + lastingDuration + " min."; }
}

class HairCare extends Cosmetic {
    boolean isMedicated;
    public HairCare(double price, String manuf, String brand, boolean org, int exp, double weight, boolean medicated) {
        super(price, manuf, brand, org, exp, weight);
        this.isMedicated = medicated;
    }
    @Override public String getType() { return "HAIRCARE"; }
    @Override public String getDetails() { return "Type: HairCare\n" + super.getDetails() + "\nMedicated: " + (isMedicated ? "Yes" : "No"); }
}

class SkinCare extends Cosmetic {
    boolean isBabySensitive;
    public SkinCare(double price, String manuf, String brand, boolean org, int exp, double weight, boolean baby) {
        super(price, manuf, brand, org, exp, weight);
        this.isBabySensitive = baby;
    }
    @Override public String getType() { return "SKINCARE"; }
    @Override public String getDetails() { return "Type: SkinCare\n" + super.getDetails() + "\nBaby Sensitive: " + (isBabySensitive ? "Yes" : "No"); }
}

abstract class Electronic extends Item {
    String manufacturer, brand;
    int maxVolt, maxWatt;
    public Electronic(double price, String manufacturer, String brand, int maxVolt, int maxWatt) {
        super(price, 0);
        this.manufacturer = manufacturer;
        this.brand = brand;
        this.maxVolt = maxVolt;
        this.maxWatt = maxWatt;
    }
    @Override
    public String getDetails() {
        return super.getDetails() + "\nManufacturer: " + manufacturer + "\nBrand: " + brand + "\nMax Volt: " + maxVolt + " V.\nMax Watt: " + maxWatt + " W.";
    }
}

class Desktop extends Electronic {
    String os, cpuType, boxColor;
    int ram, hdd;
    public Desktop(double p, String m, String b, int v, int w, String os, String cpu, int ram, int hdd, String color) {
        super(p, m, b, v, w);
        this.os = os; this.cpuType = cpu; this.ram = ram; this.hdd = hdd; this.boxColor = color;
    }
    @Override public String getType() { return "DESKTOP"; }
    @Override public String getDetails() { return "Type: Desktop\n" + super.getDetails() + "\nOS: " + os + "\nCPU: " + cpuType + "\nRAM: " + ram + " GB\nHDD: " + hdd + " GB\nBox Color: " + boxColor; }
}

class Laptop extends Electronic {
    String os, cpuType;
    int ram, hdd;
    boolean hdmiSupport;
    public Laptop(double p, String m, String b, int v, int w, String os, String cpu, int ram, int hdd, boolean hdmi) {
        super(p, m, b, v, w);
        this.os = os; this.cpuType = cpu; this.ram = ram; this.hdd = hdd; this.hdmiSupport = hdmi;
    }
    @Override public String getType() { return "LAPTOP"; }
    @Override public String getDetails() { return "Type: Laptop\n" + super.getDetails() + "\nOS: " + os + "\nCPU: " + cpuType + "\nRAM: " + ram + " GB\nHDD: " + hdd + " GB\nHDMI Support: " + (hdmiSupport ? "Yes" : "No"); }
}

class Tablet extends Electronic {
    String os, cpuType, dimension;
    int ram, hdd;
    public Tablet(double p, String m, String b, int v, int w, String os, String cpu, int ram, int hdd, String dim) {
        super(p, m, b, v, w);
        this.os = os; this.cpuType = cpu; this.ram = ram; this.hdd = hdd; this.dimension = dim;
    }
    @Override public String getType() { return "TABLET"; }
    @Override public String getDetails() { return "Type: Tablet\n" + super.getDetails() + "\nOS: " + os + "\nCPU: " + cpuType + "\nRAM: " + ram + " GB\nHDD: " + hdd + " GB\nDimensions: " + dimension; }
}

class TV extends Electronic {
    int screenSize;
    public TV(double p, String m, String b, int v, int w, int screen) {
        super(p, m, b, v, w);
        this.screenSize = screen;
    }
    @Override public String getType() { return "TV"; }
    @Override public String getDetails() { return "Type: TV\n" + super.getDetails() + "\nScreen size: " + screenSize + "\""; }
}

class SmartPhone extends Electronic {
    String screenType;
    public SmartPhone(double p, String m, String b, int v, int w, String sType) {
        super(p, m, b, v, w);
        this.screenType = sType;
    }
    @Override public String getType() { return "SMARTPHONE"; }
    @Override public String getDetails() { return "Type: SmartPhone\n" + super.getDetails() + "\nScreen Type: " + screenType; }
}

abstract class OfficeSupplies extends Item {
    String releaseDate, coverTitle;
    public OfficeSupplies(double price, String releaseDate, String coverTitle) {
        super(price, 0);
        this.releaseDate = releaseDate;
        this.coverTitle = coverTitle;
    }
    @Override
    public String getDetails() {
        return super.getDetails() + "\nRelease Date: " + releaseDate + "\nTitle: " + coverTitle;
    }
}

class Book extends OfficeSupplies {
    String publisher, authors;
    int pages;
    public Book(double p, String rDate, String title, String pub, String auth, int pages) {
        super(p, rDate, title);
        this.publisher = pub; this.authors = auth; this.pages = pages;
    }
    @Override public String getType() { return "BOOK"; }
    @Override public String getDetails() { return "Type: Book\n" + super.getDetails() + "\nPublisher: " + publisher + "\nAuthor: " + authors + "\nPage: " + pages + " pages"; }
}

class CDDVD extends OfficeSupplies {
    String composer, songs;
    public CDDVD(double p, String rDate, String title, String comp, String songs) {
        super(p, rDate, title);
        this.composer = comp; this.songs = songs;
    }
    @Override public String getType() { return "CDDVD"; }
    @Override public String getDetails() { return "Type: CDDVD\n" + super.getDetails() + "\nComposer: " + composer + "\nSongs: " + songs; }
}