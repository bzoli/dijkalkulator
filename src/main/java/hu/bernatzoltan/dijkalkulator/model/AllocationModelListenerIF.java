package hu.bernatzoltan.dijkalkulator.model;

import java.util.List;

/**
 *
 * @author bzoli
 */
public interface AllocationModelListenerIF {

    void allocationsLoaded( List<Allocation> allocations);
    
}