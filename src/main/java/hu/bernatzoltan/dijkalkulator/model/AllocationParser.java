package hu.bernatzoltan.dijkalkulator.model;

import hu.bernatzoltan.dijkalkulator.Preferences;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author bzoli
 */
public class AllocationParser {
    
    //a tageket elvalaszto token
    private static final String CSV_SPLIT_BY = ";";
    private static final String DATE_FORMAT = "yyyy-MM-dd";
    private static final String COMMENT_TOKEN = "#";
    
    //private Map<Object, Object> points = new Hashtable<>();
    private Properties points = new Properties();
    
    private Preferences preferences;
    
    private static AllocationParser instance;

    public static AllocationParser getInstance() {
        if (instance == null) {
            instance = new AllocationParser();
        }
        return instance;
    }

    private AllocationParser(){
        preferences = Preferences.getInstance();
        points = preferences.getPoints();
    }
    
 
    
    public List<Allocation> parse(BufferedReader br) throws BusinessException{
        String line;
        List<Allocation> allocations = new ArrayList();

        int rowCounter = 0;
        try {
            while ((line = br.readLine()) != null) {
                rowCounter++;
                //az esetleges ures sorokat es a kommenteket atugrom
                if (line.trim().length()==0 || line.startsWith(COMMENT_TOKEN))
                    continue;
                String[] tags = line.split(CSV_SPLIT_BY);
                allocations.add(parseRow(tags));
                //TODO egymast kizaro sorok hibakezelese. (pl csak azonos idoszak lehet)
            }

        } catch (BusinessException e) {
            e.printStackTrace();
            throw new BusinessException("Cannot parse csv file: error at line "+rowCounter+". "+e.getMessage());
        }  catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException("Cannot parse csv file: "+e.getMessage());
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return allocations;
    }
    
    
    public List<Allocation> parse(File file) throws BusinessException{
        BufferedReader br = null;
        String line;
        List<Allocation> allocations = new ArrayList();
        
        //TODO ha null a file
        File file1 = null;
        int rowCounter = 1;
        try {
            br = new BufferedReader(new FileReader(file));
        }  catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new BusinessException("Cannot parse csv file: "+e.getMessage());
        } 
        return parse(br);
    }
    
    
    public List<Allocation> parse(String str) throws BusinessException{
        BufferedReader br = new BufferedReader(new StringReader(str));
        return parse(br);
    }
    
    
    
    private Allocation parseRow(String[] parts) throws BusinessException{
        //6 elemu kell legyen a tomb
        //Pont kódja;Érvényesség kezdete;Érvényesség vége;Kapacitástípus;Mennyiség MJ/nap;Mennyiség MJ/óra
        //KAABA00011GN;2012-12-01;2012-12-31;M10;100 000;5000
        if (parts == null || parts.length != 6) {
            throw new BusinessException("Invalid allocation");
        }
        //ismert point?
        if(points.getProperty( parts[0])==null  ){
            throw new BusinessException("Invalid allocation");
        }

        //parse-olhatok az idopontok?
        DateFormat df = new SimpleDateFormat(DATE_FORMAT);
        Date startDate;
        Date endDate;
        try {
            startDate = df.parse(parts[1]);
            endDate = df.parse(parts[2]);
        } catch (ParseException e) {
            e.printStackTrace();
            throw new BusinessException("Invalid allocation: "+e.getMessage());
        }
        
        //megfelelo az idointervallum?
        int startDateMonth;
        int startDateYear;
        int startDateDayOfMonth;

        
        int endDateMonth;
        int endDateYear;
        int endDateDayOfMonth;
        
        int maxDayOfMounth;
        
        Calendar cal = Calendar.getInstance();
        
        cal.setTime(startDate);
        startDateMonth = cal.get(Calendar.MONTH); //a honap sorszama
        startDateYear = cal.get(Calendar.YEAR); //az ev
        startDateDayOfMonth = cal.get(Calendar.DAY_OF_MONTH); //a nap sorszama a honapban
        maxDayOfMounth = cal.getActualMaximum(Calendar.DAY_OF_MONTH); //az adott honap ucso napjanak sorszama
        
        cal.setTime(endDate);
        endDateMonth = cal.get(Calendar.MONTH);
        endDateYear = cal.get(Calendar.YEAR);
        endDateDayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        
        //ha nem a honap elso napjatol indul, hiba
        if(startDateDayOfMonth!=1){
            throw new BusinessException("Invalid allocation: wrong date");
        }
        //ha nem a ho utolso napja, hiba
        if(endDateDayOfMonth!=maxDayOfMounth){
            throw new BusinessException("Invalid allocation: wrong date");
        }
        
        //evnek is passzolni kell
        if(startDateYear!=endDateYear){
            throw new BusinessException("Invalid allocation: wrong date");
        }
        
        //honap csak azonos lehet, mert csak egyhavi lekotes lehet
        if(startDateMonth!=endDateMonth){
            throw new BusinessException("Invalid allocation: wrong date");
        }
        
        
        //kapacitas tipusa (M0 vagy M10)
        CapacityType capacityType;
        if(CapacityType.M0.name().equals(parts[3]) ){
            capacityType = CapacityType.M0;
        } else if(CapacityType.M10.name().equals(parts[3])){
            capacityType = CapacityType.M10;
        } else {
            throw new BusinessException("Invalid allocation: wrong capacity type");
        }
        
        //igenyel kapacitas merteke
        double quantityDay;
        double quantityHour;
        
        //Double.parseDouble("1 000,5" sem a space, sem a tizedes veszzo nem ok
        
        //NumberFormat format = NumberFormat.getInstance(Locale.HUNGARY);
        //The problem with NumberFormat is that it will silently ignore invalid characters
        //"1,23abc" it will happily return 1.23 without indicating to you that the passed-in String contained non-parsable characters.
        String quantityDayStr = parts[4].replaceFirst(",", ".").replaceAll(" ", "");
        String quantityHourStr = parts[5].replaceFirst(",", ".").replaceAll(" ", "");
        
        try{
            quantityDay = Double.parseDouble(quantityDayStr);
            quantityHour = Double.parseDouble(quantityHourStr);
        } catch (NumberFormatException e){
            throw new BusinessException("Invalid allocation: wrong price format");
        }
        return new Allocation(parts[0], startDate, endDate, capacityType, quantityDay, quantityHour);
    }


    
}
