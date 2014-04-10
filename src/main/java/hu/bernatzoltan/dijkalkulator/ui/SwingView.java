package hu.bernatzoltan.dijkalkulator.ui;

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


public class SwingView implements AllocationModelListenerIF{
    private AllocationModelIF model;

    public SwingView(AllocationModelIF model) {
        this.model = model;
        model.addModelListener(this);
    }
    
    
    
    //private DKForm form = new DKForm();


    
    public void setVisible(boolean visible) {
        //form.setVisible(visible);
    }
    

    @Override
    public void allocationsLoaded(List<Allocation> allocations) {

    }
}
