package hu.bernatzoltan.dijkalkulator.ui.standard;

import hu.bernatzoltan.dijkalkulator.model.Allocation;
import hu.bernatzoltan.dijkalkulator.model.AllocationModelIF;
import hu.bernatzoltan.dijkalkulator.model.AllocationModelListenerIF;
import hu.bernatzoltan.dijkalkulator.model.CapacityType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author bzoli
 */


public class StandardOutView implements AllocationModelListenerIF{
    private AllocationModelIF model;

    public StandardOutView(AllocationModelIF model) {
        this.model = model;
        model.addModelListener(this);
    }

    @Override
    public void allocationsLoaded(List<Allocation> allocations) {
        Map<String, Double> rowsPoint = model.getTotalByPoint();
        
        if(rowsPoint.isEmpty()){
            System.out.println("Nincsenek lekötések.");
            return;
        }

        List<String> sortedKeys = new ArrayList(rowsPoint.keySet());
        Collections.sort(sortedKeys);
        
        double total = 0D;
        double price = 0.0;
        for (String s : sortedKeys) {
            price = rowsPoint.get(s);
            System.out.println(s + ": " + price+" Ft.");
            total += price;
        }
        System.out.println("Összesen: "+total+" Ft.");
        
        System.out.println("\n====================\n");
        
        Map<String, Double> rowsCapacityType = model.getTotalByCapacityType();
        System.out.println(CapacityType.M0.name()+": "+rowsCapacityType.get(CapacityType.M0.name())+" Ft.");
        System.out.println(CapacityType.M10.name()+": "+rowsCapacityType.get(CapacityType.M10.name())+" Ft.");
        total = rowsCapacityType.get(CapacityType.M0.name())+ rowsCapacityType.get(CapacityType.M10.name());
        System.out.println("Összesen: "+total+" Ft.");
    }
}
