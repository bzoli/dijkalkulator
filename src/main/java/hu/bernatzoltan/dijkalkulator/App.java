package hu.bernatzoltan.dijkalkulator;

import hu.bernatzoltan.dijkalkulator.model.impl.AllocationMemoryModel;
import hu.bernatzoltan.dijkalkulator.model.BusinessException;
import hu.bernatzoltan.dijkalkulator.ui.StandardOutView;
import hu.bernatzoltan.dijkalkulator.ui.SwingView;
import java.io.File;


/*
 * A feladat megoldasa soran a specifikacio "pontatlansaga" :) miatt 
 * a kovetkezo feltetelezesekkel elek:
 * 
 * 1. Feltetelezem, hogy egy alomanyban csupan egy szerzodes adatai vannak.
 * 
 * "Egy szerződés adatait egy állomány tartalmazza" 
 * Ez a kitetel csupan azt allitja, hogy egy szerzodes adatai nem kerulhetnek tobb allomanyba,
 * azt nem, hogy egy allomanyban nem lehetnek tobb szerzodes adatai. Megis feltetelezem, hogy 
 * egy alomanyban csupan egy szerzodes adatai vannak, mivel a csv allomany egy bejegyzeseben (soraban)
 * nincs olyan adat, ami a szerzodest azonositana, amihez tartozik a bejegyzes. Emiatt pedig nem lehetne
 * meghatarozni egy bejegyzes mely szerzodeshez tartozik, ami pedig lehetetlenne tenne a 
 * bejegyzesek szerzodesre osszegzett kapacitasdijak meghatarozasat.
 * 
 * 2. Feltetelezem, hogy az alkalmazasnak egy allomanyt kell feldolgoznia, tehat minden csv file 
 * betoltesekor, a mar a rendszerben esetlegesen bent levo korabbi csv file betoltesbol szarmazo
 * adatok torlodnek, igy azok az osszegzesben mar nem vesznek reszt.
 * 
 * "Egy szerződés adatait egy állomány tartalmazza, amely csak azonos érvényességű, 
 * de több pontra is vonatkozó kapacitáslekötéseket tartalmazhat, 
 * viszont egy ponthoz akár több eltérő időszakra vonatkozó kapacitáslekötés is érkezhet."
 * Ez ellent mond a feltetelezesemnek, mivel elobb azt allitja, hogy egy allomanyban csak
 * azonos ervenyessegu lekotesek lehetnek, majd pedig azt, hogy egy ponthoz kulonbozo ervenyessegu
 * bejegyzesek is lehetnek. Ez csak akkor lehetseges, ha a kulonbozo ervenyessegu bejegyzesek
 * kulonbozo csv allomanyokban vannak. Ha viszont csak egy allomanyt kell feldolgoznia egyszerre
 * az alkalmazasnak, akkor az irrelevans, ugyanakkor magatol ertetodo informacio, 
 * hogy egy ujabb csv file feldolgozasakor egy pont mar mas ervenyesseggel szerepel.
 * 
 * 
 * 5. Feltetelezem, hogy 1 havi lekotes lehetseges csak egy csv bejegyzesben.
 * 
 * "Díjkalkulátornak csak havi kapacitáslekötésekkel kell számolnia" 
 * Ez jelenthetne x havi lekotest is, ahol x eleme Z+ halmaznak.
 * 
 * "a hónap első napjától az utolsó napjáig tart." Ebbol viszon arra kovetkeztetek, hogy csupan
 * 1 havi lekotes lehetseges, tehat nem arrol van szo, hogy egy honap elso napjatol egy valamely masik honap
 * utolso napjaig tarthat a lekotes.
 * 
 * 
 * 21,38 Ft/MJ/nap/év ???
 * A számformátum magyar, azaz space az ezres tagolás ???
 * 
*/

public class App {
    //az alkalmazás parameterben kapja (0. param), hogy swinges felulettel, vagy anelkul fusson
    //ha nem swing-et valaszt a user, kotelezo megadni a masodik parametert is,
    //ami a lekoteseket tartalmazo csv file neve (eleresi utja)
    //ha swinget valaszt, erdektelen a 2. parameter
    private static final String SELECT_STANDARD_VIEW_PARAM = "standard";
    private static final String SELECT_SWING_VIEW_PARAM = "swing";
    
    public static void main( String[] args ) throws BusinessException {
        App app = new App();
        app.init(args);
        
        
        
    }
    
    private void init(String[] args) throws BusinessException{
        
        //properties file-ok beolvasasa. (kivetelt dob, ha gaz van veluk)
        //prefs.init()-nek le kell futnia itt, minden elott, hogy a tobbi obj
        //hasznalhassa ezeket, ne ures prop file-okat talaljonak
        Preferences prefs = Preferences.getInstance();
        prefs.init();
        
        final AllocationMemoryModel model = AllocationMemoryModel.getInstance();
        StandardOutView stdView = new StandardOutView(model);
        final SwingView swingView;
        
        if(args.length != 1 && args.length != 2){
            throw new BusinessException("Hiba. Indulási paraméterek száma 1 vagy 2 lehet.");
        }
        
        if (SELECT_SWING_VIEW_PARAM.equals(args[0])) {
            java.awt.EventQueue.invokeLater(new Runnable() {
                @Override
                public void run() {
                    final SwingView view = new SwingView(model);
                    view.setVisible(true);
                }
            });
        } else if(SELECT_STANDARD_VIEW_PARAM.equals(args[0])){
            if(args.length!=2){
                throw new BusinessException("Hiba. Hiányzik a 2. parameter.");
            } else {
                File file = new File(args[1]);
                model.loadAllocations(file);
            }
            
        } else {
            throw new BusinessException("Hiba. Az elso inditasi parameter "
                    +SELECT_STANDARD_VIEW_PARAM
                    +" vagy "
                    +SELECT_SWING_VIEW_PARAM
                    +" lehet.");
        }
        
    }
 
}
