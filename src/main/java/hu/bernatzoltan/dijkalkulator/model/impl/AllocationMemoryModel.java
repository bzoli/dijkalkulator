package hu.bernatzoltan.dijkalkulator.model.impl;

import hu.bernatzoltan.dijkalkulator.model.AllocationParser;
import hu.bernatzoltan.dijkalkulator.Preferences;
import hu.bernatzoltan.dijkalkulator.model.Allocation;
import hu.bernatzoltan.dijkalkulator.model.AllocationModelIF;
import hu.bernatzoltan.dijkalkulator.model.AllocationModelListenerIF;
import hu.bernatzoltan.dijkalkulator.model.BusinessException;
import hu.bernatzoltan.dijkalkulator.model.CapacityType;
import java.io.File;
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
    
    

    public static AllocationMemoryModel getInstance() throws BusinessException {
        if (instance == null) {
            instance = new AllocationMemoryModel();
        }
        return instance;
    }

    public AllocationMemoryModel() throws BusinessException {
        //preferences = Preferences.getInstance();
        allocationParser = AllocationParser.getInstance();
        //allocationParser.setPoints(preferences.getPoints());
    }

    @Override
    public void loadAllocations(File allocationsFile) throws BusinessException {
        allocations = allocationParser.parse(allocationsFile);
        fireAllocationsLoadedEvent();
    }

    @Override
    public void loadAllocations(String allocationsString) throws BusinessException {
        allocations = allocationParser.parse(allocationsString);
        fireAllocationsLoadedEvent();
    }

    @Override
    public List<Allocation> getAllAllocation() {
        return allocations;
    }

    
    @Override
    public Map<String, Double> getTotalByPoint() {
        Map<String, Double> totalMap = new HashMap<>();
        double lastPrice;
        for(Allocation act : allocations){
            lastPrice = totalMap.get(act.getPointCode()) == null ? 0.0 : totalMap.get(act.getPointCode() );
            totalMap.put(act.getPointCode(), act.getPrice()+lastPrice);
        }

        return totalMap;
    }

    @Override
    public Map<String, Double> getTotalByCapacityType() {
        double m0 = 0d;
        double m10 = 0.0;
        for (Allocation act : allocations) {
            if (CapacityType.M0.equals(act.getCapacityType())) {
                m0 += act.getPrice();
            } else {
                m10 += act.getPrice();
            }
        }
        
        Map<String, Double> totalMap = new HashMap<>();
        totalMap.put(CapacityType.M0.name(), m0);
        totalMap.put(CapacityType.M10.name(), m10);

        return totalMap;
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

    
}
