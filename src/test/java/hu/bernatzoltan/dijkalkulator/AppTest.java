package hu.bernatzoltan.dijkalkulator;

import hu.bernatzoltan.dijkalkulator.model.BusinessException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class AppTest {

    @Before
    public void MySetUp() {
        System.out.println("setUp - Intialize common test data");
    }


    @After
    public void MyTearDown() {
        System.out.println("tearDown - Clean up");
    }


    @BeforeClass
    public static void myBeforeClassMethod() {
        System.out.println("myBeforeClassMethod - Set things up once for all");
    }

    @AfterClass
    public static void myAfterClassMethod() {
        System.out.println("myBeforeClassMethod - Clean things up once for all");
    }


    @Test(expected = BusinessException.class)
    public void testFileNotFound() throws BusinessException{
        String[] args = {"nincsIlyenFile.csv"};
        App.main(args);
    }

    @Ignore("Ignore testFileFound test for now")
    @Test
    public void testFileFound() throws BusinessException{
        String[] args = {"f:\\workspace\\ipsystems\\Dijkalkulator\\allocations1.csv"};
        App.main(args);
    }

}
