package hu.bernatzoltan.dijkalkulator.model;

import hu.bernatzoltan.dijkalkulator.Preferences;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 *
 * @author bzoli
 */
public class Allocation {
    
    //A feladat kovetelmenyeinek teljesitesehez eleg lenne a lekotes pontjanak kodjat,
    //a kapacitas tipusat es a kalkulalt árat tarolni. A felxibilitas miatt tarolok minden infot megis
    
    //Pont kódja;Érvényesség kezdete;Érvényesség vége;Kapacitástípus;Mennyiség MJ/nap;Mennyiség MJ/óra
    //KAABA00011GN;2012-12-01;2012-12-31;M10;100 000;5000
    private String pointCode;
    
    //Folosleges tarolni a datumokat, hiszen csak havi (EGY HAVI???) lekotes lehetseges
    //azaz lenyegtelen a datum, csak az szamit, hogy teli, vagy nyari(nem teli) idoszakra esik a lekotes
    private AllocationSeason season;
    private Date validFrom;
    private Date validTo;
    
    
    private CapacityType capacityType;
    
    //folosleges tarolni mindket megadott mennyiseget (MJ/nap-ban és MJ/óra-ban értelmezve),
    //mert csak az egyik relevans adat
    private double quantityDay;
    private double quantityHour;
    //a lekotott mennyiseg. a groupType-tol fuggoen a quantityDay vagy a quantityHour
    private double quantity;
    
    //belepesi, vagy kilepesi pont csoporthoz tartozik az adott pont?
    //kalkulalt ertek, a pontCode-bol is meghatarozhato.
    //Ettol fugg, hogy a melyik mennyiseggel kell szamolni az ar kalkulalasakor
    private GroupType groupType;
    
    //a lekotes ara. Kalkulalt ertek
    private double price;
    

    
    private static final Preferences prefs = Preferences.getInstance();

    public Allocation(String pointCode, Date validFrom, Date validTo, CapacityType capacityType, double quantityDay, double quantityHour) {
        this.pointCode = pointCode;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.capacityType = capacityType;
        this.quantityDay = quantityDay;
        this.quantityHour = quantityHour;
        
        
        //Teli, vagy nyari idoszakba esik?
        int startDateMonthOrdinal;
        int endDateMonthOrdinal;
        Calendar cal = Calendar.getInstance();
        cal.setTime(validFrom);
        startDateMonthOrdinal = cal.get(Calendar.MONTH);
        cal.setTime(validTo);
        endDateMonthOrdinal = cal.get(Calendar.MONTH);
        
        season = AllocationSeason.isWinter(startDateMonthOrdinal) ? AllocationSeason.WINTER : AllocationSeason.SUMMER;
       
        
        //belepesei, vagy kilepesi pontcsopba tartozik
        groupType = prefs.getPoints().getProperty(pointCode).toLowerCase()
                .contains(GroupType.BELÉPÉSI.name().toLowerCase()) 
                ? GroupType.BELÉPÉSI
                : GroupType.KILÉPÉSI;
        
        if(groupType.equals(GroupType.BELÉPÉSI)){
            quantity = quantityDay;
        } else {
            quantity = quantityHour;
        }
        
        price = calculatePrice();
    }

    private double calculatePrice(){
        String pointGroup = prefs.getPoints().getProperty(getPointCode());
        double unitPrice = Double.parseDouble(prefs.getPonitGroups().getProperty(pointGroup));
        //ar*evszak*tipus*mennyiseg(MJ/nap vagy MJ/ora)
        return(unitPrice*getSeason().getMultiplier()
                *getCapacityType().getMultiplier()
                *getQuantity());
    }
   
    
    public double getQuantity(){
        return quantity;
    }

    public String getPointCode() {
        return pointCode;
    }

    public AllocationSeason getSeason() {
        return season;
    }

    public CapacityType getCapacityType() {
        return capacityType;
    }

    public double getQuantityDay() {
        return quantityDay;
    }

    public double getQuantityHour() {
        return quantityHour;
    }

    public GroupType getGroupType() {
        return groupType;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.pointCode);
        hash = 67 * hash + Objects.hashCode(this.validFrom);
        hash = 67 * hash + Objects.hashCode(this.validTo);
        hash = 67 * hash + Objects.hashCode(this.capacityType);
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.quantityDay) ^ (Double.doubleToLongBits(this.quantityDay) >>> 32));
        hash = 67 * hash + (int) (Double.doubleToLongBits(this.quantityHour) ^ (Double.doubleToLongBits(this.quantityHour) >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Allocation other = (Allocation) obj;
        if (!Objects.equals(this.pointCode, other.pointCode)) {
            return false;
        }
        if (!Objects.equals(this.validFrom, other.validFrom)) {
            return false;
        }
        if (!Objects.equals(this.validTo, other.validTo)) {
            return false;
        }
        if (this.capacityType != other.capacityType) {
            return false;
        }
        if (Double.doubleToLongBits(this.quantityDay) != Double.doubleToLongBits(other.quantityDay)) {
            return false;
        }
        if (Double.doubleToLongBits(this.quantityHour) != Double.doubleToLongBits(other.quantityHour)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Allocation{" + "pointCode=" + pointCode + ", season=" + season + ", validFrom=" + validFrom + ", validTo=" + validTo + ", capacityType=" + capacityType + ", quantityDay=" + quantityDay + ", quantityHour=" + quantityHour + ", quantity=" + quantity + ", groupType=" + groupType + ", price=" + price + '}';
    }



 


    
    
    





    
    

    
    
}
