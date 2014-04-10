package hu.bernatzoltan.dijkalkulator.model.impl;

import hu.bernatzoltan.dijkalkulator.model.AllocationModelListenerIF;
import hu.bernatzoltan.dijkalkulator.model.BusinessException;
import hu.bernatzoltan.dijkalkulator.model.CapacityType;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author bzoli
 */
public class AllocationMemoryModelTest {
    
    public AllocationMemoryModelTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }


    /**
     * Test of getTotalByPoint method, of class AllocationMemoryModel.
     */

    @Test
    public void testGetTotalByPoint() {
        System.out.println("getTotalByPoint");
        String allocationsString = "KAABA00011GN;2012-12-01;2012-12-31;M10;100 000;5000\n" // 159727.5
                // 1346940.0: 21,38(Külföldi belépési) Ft* 0.9(teli idoszak)*1(M0)*70 000(belepesi pont, tehat a MJ/Nap ertek kell)
                + "#\n"
                + "HABEREGD1IIN;2012-12-01;2012-12-31;M0;70 000;3000\n"
                + "KEKALOCS11GN;2012-12-01;2012-12-31;M10;50 000;4000\n" // 127782.0
                + "\n"
                + "VEBUDAOR1VEN;2012-05-01;2012-05-31;M0;10 000;1000\n"; // 63891.0
        
        AllocationMemoryModel model;
        try {
            model = AllocationMemoryModel.getInstance();
            model.loadAllocations(allocationsString);
            Map resultMap = model.getTotalByPoint();
            Double resultPoint = (Double) resultMap.get("KAABA00011GN");
            Double expResultPoint = 159727.5;
            assertEquals(expResultPoint, resultPoint);
            
            resultPoint = (Double) resultMap.get("HABEREGD1IIN");
            expResultPoint = 1346940.0;
            assertEquals(expResultPoint, resultPoint);
            
            resultPoint = (Double) resultMap.get("KEKALOCS11GN");
            expResultPoint = 127782.0;
            assertEquals(expResultPoint, resultPoint);
            
            resultPoint = (Double) resultMap.get("VEBUDAOR1VEN");
            expResultPoint = 63891.0;
            assertEquals(expResultPoint, resultPoint);
 
        } catch (BusinessException ex) {
            fail(ex.getMessage());
        }
    }

    /**
     * Test of getTotalByCapacityType method, of class AllocationMemoryModel.
     */
    //@Ignore("Ignore test for now")
    @Test
    public void testGetTotalByCapacityType() {
        System.out.println("getTotalByCapacityType");
                System.out.println("getTotalByPoint");
        String allocationsString = "KAABA00011GN;2012-12-01;2012-12-31;M10;100 000;5000\n" // 159727.5
                // 1346940.0: 21,38(Külföldi belépési) Ft* 0.9(teli idoszak)*1(M0)*70 000(belepesi pont, tehat a MJ/Nap ertek kell)
                + "#\n"
                + "HABEREGD1IIN;2012-12-01;2012-12-31;M0;70 000;3000\n"
                + "KEKALOCS11GN;2012-12-01;2012-12-31;M10;50 000;4000\n" // 127782.0
                + "\n"
                + "VEBUDAOR1VEN;2012-05-01;2012-05-31;M0;10 000;1000\n"; // 63891.0
        
        AllocationMemoryModel model;
        try {
            model = AllocationMemoryModel.getInstance();
            model.loadAllocations(allocationsString);
            Map resultMap = model.getTotalByCapacityType();
            Double resultPoint = (Double) resultMap.get(CapacityType.M0.name());
            Double expResultPoint = 1410831.0;
            assertEquals(expResultPoint, resultPoint);

            resultPoint = (Double) resultMap.get(CapacityType.M10.name());
            expResultPoint = 287509.5;
            assertEquals(expResultPoint, resultPoint);
 
        } catch (BusinessException ex) {
            fail(ex.getMessage());
        }
    }

}