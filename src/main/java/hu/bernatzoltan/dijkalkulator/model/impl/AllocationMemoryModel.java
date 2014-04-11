package hu.bernatzoltan.dijkalkulator.model.impl;

import hu.bernatzoltan.dijkalkulator.model.AllocationParser;
import hu.bernatzoltan.dijkalkulator.model.Allocation;
import hu.bernatzoltan.dijkalkulator.model.AllocationModelIF;
import hu.bernatzoltan.dijkalkulator.model.AllocationModelListenerIF;
import hu.bernatzoltan.dijkalkulator.model.BusinessException;
import hu.bernatzoltan.dijkalkulator.model.CapacityType;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bzoli
 */
public class AllocationMemoryModel implements AllocationModelIF {

    private AllocationParser allocationParser;
    //private Preferences preferences;
    private List<Allocation> allocations = new ArrayList<>();
    private List<AllocationModelListenerIF> modelListeners = new ArrayList<>();
    private static AllocationMemoryModel instance = null;
    
    private String originalText = "";
    
 
    private Map<String, Double> totalByPoint = new HashMap<>();
    private Map<String, Double> totalByCapacityType = new HashMap<>();

    public static AllocationMemoryModel getInstance() throws BusinessException {
        if (instance == null) {
            instance = new AllocationMemoryModel();
        }
        return instance;
    }

    public AllocationMemoryModel() throws BusinessException {
        allocationParser = AllocationParser.getInstance();
     }

    @Override
    public void loadAllocations(File allocationsFile) throws BusinessException {

        try {
            originalText = new String(Files.readAllBytes(allocationsFile.toPath()));
        } catch (IOException ex) {
            throw new BusinessException(ex.getMessage());
        }
        //allocations = allocationParser.parse(allocationsFile);
        allocations = allocationParser.parse(originalText);
        //uj adatok erkezesekor azonnal kiszamitom az alabbi ket "publikus valtozo"
        //tartalmat, hogy ne kelljen minden lekeresukkor azokat ujra meg ujra kiszamitani
        calculateTotalByPoint();
        calculateTotalByCapacityType();
        fireAllocationsLoadedEvent();
    }

    @Override
    public void loadAllocations(String allocationsString) throws BusinessException {
        originalText = allocationsString;
        allocations = allocationParser.parse(allocationsString);
        //uj adatok erkezesekor azonnal kiszamitom az alabbi ket "publikus valtozo"
        //tartalmat, hogy ne kelljen minden lekeresukkor azokat ujra meg ujra kiszamitani
        calculateTotalByPoint();
        calculateTotalByCapacityType();
        fireAllocationsLoadedEvent();
    }

    @Override
    public List<Allocation> getAllAllocation() {
        return allocations;
    }

    

    private void calculateTotalByPoint() {
        totalByPoint.clear();
        double lastPrice;
        for(Allocation act : allocations){
            lastPrice = totalByPoint.get(act.getPointCode()) == null ? 0.0 : totalByPoint.get(act.getPointCode() );
            totalByPoint.put(act.getPointCode(), act.getPrice()+lastPrice);
        }
    }


    private void calculateTotalByCapacityType() {
        double m0 = 0d;
        double m10 = 0.0;
        for (Allocation act : allocations) {
            if (CapacityType.M0.equals(act.getCapacityType())) {
                m0 += act.getPrice();
            } else {
                m10 += act.getPrice();
            }
        }
        
        //nem kell clear, mindig u.ez a ket kulcs van benne
        //totalByCapacityType.clear();
        totalByCapacityType.put(CapacityType.M0.name(), m0);
        totalByCapacityType.put(CapacityType.M10.name(), m10);


    }
    
    

    // ****************************************
    @Override
    public void addModelListener(AllocationModelListenerIF listener) {
        modelListeners.add(listener);
    }

    @Override
    public void removeModelListener(AllocationModelListenerIF listener) {
        modelListeners.remove(listener);
    }

    protected void fireAllocationsLoadedEvent() {
        for (AllocationModelListenerIF listener : modelListeners) {
            listener.allocationsLoaded(allocations);
        }
    }

    @Override
    public Map<String, Double> getTotalByPoint() {
        return totalByPoint;
    }

    @Override
    public Map<String, Double> getTotalByCapacityType() {
        return totalByCapacityType;
    }

    @Override
    public String getOriginalText() {
        return originalText;
    }

    
}
