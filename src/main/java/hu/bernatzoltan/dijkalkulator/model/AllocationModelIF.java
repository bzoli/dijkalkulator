package hu.bernatzoltan.dijkalkulator.model;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bzoli
 */
public interface AllocationModelIF {
    
    public void loadAllocations(File allocations) throws BusinessException;
    public void loadAllocations(String allocations) throws BusinessException;
    public List<Allocation> getAllAllocation();
    public Map<String, Double> getTotalByPoint();
    public Map<String, Double> getTotalByCapacityType();
    
    public void addModelListener(AllocationModelListenerIF listener);
    public void removeModelListener(AllocationModelListenerIF listener);
}
