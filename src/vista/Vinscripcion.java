/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import java.util.ArrayList;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelo.negocion.*;

/**
 *
 * @author David Torrez
 */
public class Vinscripcion extends javax.swing.JFrame {

    /**
     * Creates new form Vcliente
     */
    private Ninscripcion nInscripcion;
    private Ndisciplina nDisciplina;
    private Ncliente nCliente;

    public Vinscripcion() {
        initComponents();
        this.setLocationRelativeTo(null);
        nInscripcion = new Ninscripcion();
        nDisciplina = new Ndisciplina();
        nCliente = new Ncliente();
        this.getInscripciones();
        this.getDisciplinas();
        this.getClientes();
    }

    public void getInscripciones() {
        limpiar();
        this.jtableInscripciones.setModel(this.nInscripcion.getTablaInscripciones());
    }

    public void getDisciplinas() {
        DefaultTableModel lista = this.nDisciplina.getTablaDisciplinas(1);
        jTableDisciplinas.setModel(lista);
        jtextMontoTotal.setVisible(false);
    }

    public void getClientes() {
        DefaultComboBoxModel comboClientes = this.nCliente.getComboBoxClientes();
        jcbxCliente.setModel(comboClientes);
        this.jtextId.setVisible(false);
    }

    public Ninscripcion cargarDetalleBoletaInscripcion(int id) {
        nInscripcion.setId(id);
        ArrayList<Map<String, String>> datos = nInscripcion.getDetalleInscripcion();

        this.mostrarDetalleInscripcion(datos);
        return this.nInscripcion;
    }
    
    public void limpiar() {
        //limpiamos tabla detalle
        String[] columnNames = {"NRO", "Disciplina", "Fecha Inicio", "Fecha Fin", "Monto"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        jtableDetalleInscripcion.setModel(model);
        jcbxCliente.setEnabled(true);
        jcbxCliente.setSelectedIndex(0);
        getDisciplinas();
        jtextFecha.setText("2020-05-31 12:50");
        jtextId.setText("");
        jlbMontoTotal.setText("Monto total 0.0 BS");
        jtextMontoTotal.setText("");
        // System.out.println("Limpiar el formulario");
    }

    public void mostrarDetalleInscripcion(ArrayList<Map<String, String>> datos) {
        if (datos.size() > 0) {
            jtextId.setText(datos.get(0).get("id_boleta"));
            String[] columnNames = {"NRO", "Disciplina", "Fecha Inicio", "Fecha Fin", "Monto"};
            DefaultTableModel model = new DefaultTableModel(columnNames, 0);
            //Agregamos el Detalle
            for (Map dato : datos) {
                Ndisciplina disciplina = new Ndisciplina(Integer.parseInt(dato.get("id_disciplina").toString()), dato.get("disciplina_nombre").toString());
                model.addRow(new Object[]{dato.get("id_disciplina"), disciplina, dato.get("fecha_inicio"), dato.get("fecha_fin"), dato.get("sub_monto")});
                nInscripcion.addDetalleInscripcion(disciplina.getId(), dato.get("fecha_inicio").toString(), dato.get("fecha_fin").toString(), Float.parseFloat(dato.get("sub_monto").toString()));
            }
            jtableDetalleInscripcion.setModel(model);
            // cargampos Cliente
            int cant = jcbxCliente.getItemCount();
            for (int i = 1; i < cant; i++) {
                jcbxCliente.setSelectedIndex(i);
                Ncliente clienteSelected = (Ncliente) jcbxCliente.getSelectedItem();
                if (clienteSelected.getNombre().equals(datos.get(0).get("cliente_nombre").toString())) {
                    break;
                }
            }
            jcbxCliente.setEnabled(false);
            String total = datos.get(0).get("monto").toString();
            jtextMontoTotal.setText(total);
            jtextFecha.setText(datos.get(0).get("fecha").toString());
            jlbMontoTotal.setText("Monto Total " + total + " BS");
        }
    }

    public JComboBox getJcbxCliente() {
        return jcbxCliente;
    }
    public String getCiCliente() {
        Ncliente cliente = (Ncliente) this.jcbxCliente.getSelectedItem();
        return cliente.getCi();
    }
    
    
    public JTextField getJtextId() {
        return jtextId;
    }
    
    public JTextField getJtextFecha() {
        return jtextFecha;
    }

    public JLabel getJlbMontoTotal() {
        return jlbMontoTotal;
    }

    public JTextField getJtextMontoTotal() {
        return jtextMontoTotal;
    }

    public JTable getTableDisciplina() {
        return this.jTableDisciplinas;
    }

    public JTable getTableDetalleInscripcion() {
        return this.jtableDetalleInscripcion;
    }

    public void calcularMontoTotal() {
        int count = jtableDetalleInscripcion.getRowCount();
        float total = 0;
        for (int i = 0; i < count; i++) {
            String dato = jtableDetalleInscripcion.getValueAt(i, 4).toString().trim();
            if (!"".equals(dato)) {
                float monto = Float.parseFloat(dato);
                total += monto;
            }
        }
        jtextMontoTotal.setText(String.valueOf(total));
        
        jlbMontoTotal.setText("Monto Total " + String.valueOf(total) + " Bs");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton3 = new javax.swing.JButton();
        jlbId = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtableDetalleInscripcion = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        btnRegistrar = new javax.swing.JButton();
        btnQuitar = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableDisciplinas = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        btnAddInscripcion = new javax.swing.JButton();
        jcbxCliente = new javax.swing.JComboBox<>();
        jtextFecha = new javax.swing.JTextField();
        jlbMontoTotal = new javax.swing.JLabel();
        jtextMontoTotal = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jtableInscripciones = new javax.swing.JTable();
        btnModificar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        jtextId = new javax.swing.JTextField();
        btnMain = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        jButton3.setText("jButton3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jlbId.setText("Nro");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Listas Disciplinas");

        jtableDetalleInscripcion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Nro", "Disciplina", "Fecha Inicio", "Fecha Fin", "Monto (Bs)"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtableDetalleInscripcion.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(jtableDetalleInscripcion);

        jLabel3.setText("Cliente");

        btnRegistrar.setText("Registrar");

        btnQuitar.setText("Quitar");

        jLabel7.setText("Detalle Inscripcion");

        jTableDisciplinas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Nro", "Nombre", "Instructor", "Sala"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(jTableDisciplinas);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel1.setText("CTR+click add + de 1");

        btnAddInscripcion.setText("Aadd a Detalle Inscripcion");

        jcbxCliente.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Seleccione", "Item 1", "Item 2", "Item 3", "Item 4" }));

        jtextFecha.setText("2020-05-28");

        jlbMontoTotal.setText("Monto Total 0.0 Bs");

        jtextMontoTotal.setText("180");

        jtableInscripciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(jtableInscripciones);

        btnModificar.setText("Modificar");

        btnEliminar.setText("Eliminar");

        btnMain.setText("Main");

        jLabel4.setText("Inscripciones Registradas");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(180, 180, 180)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(22, Short.MAX_VALUE)
                        .addComponent(btnRegistrar, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnQuitar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEliminar)
                        .addGap(2, 2, 2)
                        .addComponent(btnMain)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbMontoTotal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jtextMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jlbId)
                                            .addGap(110, 110, 110))
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(jtextFecha)
                                            .addGap(18, 18, 18)))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel3)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jtextId, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jcbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(btnAddInscripcion)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel1))
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(jLabel4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(btnAddInscripcion)
                            .addComponent(jLabel2)
                            .addComponent(jtextId, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jcbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jlbId)
                        .addGap(3, 3, 3)
                        .addComponent(jtextFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnQuitar)
                    .addComponent(btnRegistrar)
                    .addComponent(jlbMontoTotal)
                    .addComponent(jtextMontoTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar)
                    .addComponent(btnEliminar)
                    .addComponent(btnMain))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(10, 10, 10)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(Vinscripcion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(Vinscripcion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(Vinscripcion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(Vinscripcion.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Vinscripcion().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton btnAddInscripcion;
    public javax.swing.JButton btnEliminar;
    public javax.swing.JButton btnMain;
    public javax.swing.JButton btnModificar;
    public javax.swing.JButton btnQuitar;
    public javax.swing.JButton btnRegistrar;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTableDisciplinas;
    private javax.swing.JComboBox<String> jcbxCliente;
    private javax.swing.JLabel jlbId;
    private javax.swing.JLabel jlbMontoTotal;
    public javax.swing.JTable jtableDetalleInscripcion;
    public javax.swing.JTable jtableInscripciones;
    private javax.swing.JTextField jtextFecha;
    private javax.swing.JTextField jtextId;
    private javax.swing.JTextField jtextMontoTotal;
    // End of variables declaration//GEN-END:variables

    

}
