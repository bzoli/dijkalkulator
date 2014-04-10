
package hu.bernatzoltan.dijkalkulator;

import hu.bernatzoltan.dijkalkulator.model.BusinessException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author bzoli
 */
public class Preferences {
    //pontGroups prop filename
    private static final String POINT_GROUPS_PROPS = "pointGroups.properties";
    //ponts prop filename
    private static final String POINTS_PROPS = "points.properties";
    
    
    private Properties pointGroups = new Properties();
    private Properties points = new Properties();
    
    
    private static Preferences instance;

    public static Preferences getInstance() {
        if (instance == null) {
            instance = new Preferences();
        }
        return instance;
    }

    public Preferences(){
        //nem akarok a konstruktorban ellenorzott kivetelt dobni kepes muveletet csinalni,
        //mert akkor minden - ebbol a singleton classbol - peldanyt kero objnak tovabbmenne a kivetel (mert a getInstancs() is megkapna)
        //igy azt mindegyiknek specifikalnia kellene (elkapni nem tanacsos nekik)
        //loadProperties();
    }
    
    public void init()  throws BusinessException{
        loadProperties();
    }
    
    private void loadProperties() throws BusinessException{
        InputStream is = null;
        try {
            is = getClass().getClassLoader().getResourceAsStream(POINT_GROUPS_PROPS);
            pointGroups.load(is);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new BusinessException("There was an error loading groups prop file: "+e.getMessage());
        } finally{
            try {
                is.close();
            } catch (IOException ex) {
                //TODO
                ex.printStackTrace();
            }
        }
        try {
            is = getClass().getClassLoader().getResourceAsStream(POINTS_PROPS);
            points.load(is);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new BusinessException("There was an error loading points prop file: "+e.getMessage());
        } finally{
            try {
                is.close();
            } catch (IOException ex) {
                //TODO
                ex.printStackTrace();
            }
        }
        
    }

    
    public Properties getPonitGroups() {
        return pointGroups;
    }

    public Properties getPoints() {
        return points;
    }
    
    
    
}
