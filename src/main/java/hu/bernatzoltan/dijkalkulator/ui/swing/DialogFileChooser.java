package hu.bernatzoltan.dijkalkulator.ui.swing;

import hu.bernatzoltan.dijkalkulator.model.AllocationModelIF;
import hu.bernatzoltan.dijkalkulator.model.BusinessException;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 *
 * @author bzoli
 */
public class DialogFileChooser extends javax.swing.JDialog {
    private AllocationModelIF model;

    public DialogFileChooser(javax.swing.JFrame jFrame, AllocationModelIF model) {
        super(jFrame, true); //true means modal
        this.model = model;
        initComponents();
        jFileChooser1.addChoosableFileFilter(new MyFilter());        
    }
    
    class MyFilter extends javax.swing.filechooser.FileFilter {
        @Override
        public boolean accept(File file) {
            if(file.isDirectory())
                return false;
            String filename = file.getName();
            return filename.endsWith(".csv") || filename.endsWith(".txt");
        }

        @Override
        public String getDescription() {
            return "*.csv";
        }
    }

    private void displayError(String error){
        JOptionPane.showMessageDialog(this, error, "IpSystems", JOptionPane.ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jFileChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jFileChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jFileChooser1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jFileChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jFileChooser1ActionPerformed
        //System.out.println(evt);
        //System.out.println(evt.getActionCommand()); 
        //System.out.println(evt.paramString());

        //ok gombot nyom a user: "ApproveSelection"
        //cancel-t: "CancelSelection"
        if (!evt.getActionCommand().equals(JFileChooser.APPROVE_SELECTION)) {
            this.setVisible(false);
            return;
        }

        //csak single selection lehet
        //File[] f = jFileChooser1.getSelectedFiles();
        File f = jFileChooser1.getSelectedFile();
        this.setVisible(false);
        try {
            model.loadAllocations(f);
        } catch (BusinessException ex) {
            displayError(ex.getMessage());
        }
    }//GEN-LAST:event_jFileChooser1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JFileChooser jFileChooser1;
    // End of variables declaration//GEN-END:variables
}
