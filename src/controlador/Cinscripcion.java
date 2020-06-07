/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.negocion.*;
import vista.Vinscripcion;
import vista.Vmain;

/**
 *
 * @author David Torrez
 */
public class Cinscripcion implements MouseListener, ActionListener {

    private Ninscripcion nInscripcion;
    private Vinscripcion vInscripcion;

    public Cinscripcion(Ninscripcion nInscripcion, Vinscripcion vInscripcion) {
        this.nInscripcion = nInscripcion;
        this.vInscripcion = vInscripcion;
        initComponente();

    }

    public enum AccionMVC {
        btnRegistrar,
        btnModificar,
        btnEliminar,
        btnQuitar,
        btnMain,
        btnAddInscripcion
    }

    public void initComponente() {
        this.vInscripcion.setTitle("Gestionar Inscripcion");
        this.vInscripcion.setVisible(true);
        this.vInscripcion.btnRegistrar.setActionCommand("btnRegistrar");
        this.vInscripcion.btnRegistrar.addActionListener(this);
        this.vInscripcion.btnModificar.setActionCommand("btnModificar");
        this.vInscripcion.btnModificar.addActionListener(this);
        this.vInscripcion.btnEliminar.setActionCommand("btnEliminar");
        this.vInscripcion.btnEliminar.addActionListener(this);
        //btnMain
        this.vInscripcion.btnMain.setActionCommand("btnMain");
        this.vInscripcion.btnMain.addActionListener(this);
        this.vInscripcion.btnQuitar.setActionCommand("btnQuitar");
        this.vInscripcion.btnQuitar.addActionListener(this);
        this.vInscripcion.btnAddInscripcion.setActionCommand("btnAddInscripcion");
        this.vInscripcion.btnAddInscripcion.addActionListener(this);
        //Evento click de la Tabla
        this.vInscripcion.jtableDetalleInscripcion.addMouseListener(this);
        this.vInscripcion.jtableInscripciones.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        JTable source = (JTable) evt.getSource();
        if ("Cliente".equals(source.getColumnName(1))) {
            int row = source.rowAtPoint(evt.getPoint());
            String id = source.getModel().getValueAt(row, 0) + "";
            Ninscripcion inscripcion = this.vInscripcion.cargarDetalleBoletaInscripcion(Integer.parseInt(id.trim()));
            nInscripcion.setInscripcion(inscripcion);
        }
    }

    @Override
    public void mouseExited(MouseEvent evt) {
        JTable source = (JTable) evt.getSource();
        if ("Disciplina".equals(source.getColumnName(1))) {
            this.vInscripcion.calcularMontoTotal();
        }

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (AccionMVC.valueOf(evt.getActionCommand())) {
            case btnRegistrar:
                try {
                    if (this.vInscripcion.getJcbxCliente().isEnabled() && this.vInscripcion.getTableDetalleInscripcion().getRowCount() > 0
                            && this.vInscripcion.getJcbxCliente().getSelectedIndex() != 0
                            && !"0".equals(this.vInscripcion.getJtextMontoTotal().getText())) {

                        //Datos del la Boleta de Inscripcion
                        String ciCliente = this.vInscripcion.getCiCliente();
                        String fecha = this.vInscripcion.getJtextFecha().getText().trim();
                        float montoTotal = Float.parseFloat(this.vInscripcion.getJtextMontoTotal().getText().trim());

                        //insertando Inscripcion
                        nInscripcion.setInscripcionR(fecha, montoTotal, Integer.parseInt(ciCliente));

                        //Datos del Detalle de Inscripcion
                        int count = this.vInscripcion.getTableDetalleInscripcion().getRowCount();
                        for (int i = 0; i < count; i++) {
                            int idDisciplina = Integer.parseInt(this.vInscripcion.getTableDetalleInscripcion().getValueAt(i, 0).toString());

                            String fechaInicio = this.vInscripcion.getTableDetalleInscripcion().getValueAt(i, 2).toString().trim();
                            String fechaFin = this.vInscripcion.getTableDetalleInscripcion().getValueAt(i, 3).toString().trim();
                            float monto = Float.parseFloat(this.vInscripcion.getTableDetalleInscripcion().getValueAt(i, 4).toString().trim());
                            nInscripcion.addDetalleInscripcion(idDisciplina, fechaInicio, fechaFin, monto);
                        }
                        nInscripcion.setId(nInscripcion.getMaxId());

                        nInscripcion.setDetalleInscripcionR(); //inserta el detalle
                        System.out.println("Exito Registrado");
                        this.vInscripcion.getInscripciones();
                    } else {

                        JOptionPane.showMessageDialog(null, "El registro ya existe solo puede Modificar");
                        this.vInscripcion.getInscripciones();
                    }
                } catch (Exception ex) {
                    Logger.getLogger(Cinscripcion.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error Al darle click Registrar");
                }
                break;
            case btnModificar:
                try {
                    if (!this.vInscripcion.getJcbxCliente().isEnabled() && this.vInscripcion.getTableDetalleInscripcion().getRowCount() > 0
                            && this.vInscripcion.getJcbxCliente().getSelectedIndex() != 0
                            && !"0".equals(this.vInscripcion.getJtextMontoTotal().getText())) {

                        int id = Integer.parseInt(vInscripcion.getJtextId().getText().trim());
                        float monto = Float.parseFloat(vInscripcion.getJtextMontoTotal().getText().trim());
                        String fecha = vInscripcion.getJtextFecha().getText().trim();

                        //Agregamos los nuevos valores
                        this.nInscripcion.setInscripcionM(
                                id,
                                fecha,
                                monto);

                        //Eliminar los detalle de la inscripcion
                        nInscripcion.setId(id);

                        nInscripcion.setDetalleInscripcionE();

                        //Insertando nuevo Detalle de Inscripcion
                        int count = this.vInscripcion.getTableDetalleInscripcion().getRowCount();

                        for (int i = 0; i < count; i++) {
                            Ndisciplina disciplina = (Ndisciplina) vInscripcion.jtableDetalleInscripcion.getValueAt(i, 1);
                            String fechaInicio = this.vInscripcion.getTableDetalleInscripcion().getValueAt(i, 2).toString().trim();
                            String fechaFin = this.vInscripcion.getTableDetalleInscripcion().getValueAt(i, 3).toString().trim();
                            float subMonto = Float.parseFloat(this.vInscripcion.getTableDetalleInscripcion().getValueAt(i, 4).toString().trim());
                            nInscripcion.addDetalleInscripcion(disciplina.getId(), fechaInicio, fechaFin, subMonto);
                        }
                        nInscripcion.setDetalleInscripcionR(); //inserta el detalle
                        System.out.println("Exito Modificar");
                        this.vInscripcion.getInscripciones();

                    }
                } catch (Exception ex) {
                    Logger.getLogger(Cinscripcion.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error Al darle click Registrar");
                }

                break;
            case btnEliminar:

                if (!this.vInscripcion.getJcbxCliente().isEnabled()) {
                    int selectedOption = JOptionPane.showConfirmDialog(null,
                            "Esta Seguro de Elimanar la La boleta de Pago ",
                            "Eligue",
                            JOptionPane.YES_NO_OPTION);
                    if (selectedOption == JOptionPane.YES_OPTION) {
                        try {
                            //eliminamos sus Detalles
                            int id = Integer.parseInt(this.vInscripcion.getJtextId().getText().trim());
                            nInscripcion.setId(id);
                            nInscripcion.setDetalleInscripcionE();
                            //elimininamos la disciplina
                            nInscripcion.setInscripcionE();
                            System.out.println("Eliminado");
                            // this.vInscripcion.limpiar();
                            // nInscripcion.vaciarDetalleInscripcion();

                        } catch (Exception ex) {
                            Logger.getLogger(Ncliente.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    this.vInscripcion.getInscripciones();
                } else {
                    System.out.println("No se puede eliminar");
                }

                break;
            case btnAddInscripcion:
                int[] rowsTableDisciplina = this.vInscripcion.getTableDisciplina().getSelectedRows();
                if (rowsTableDisciplina.length > 0) {
                    String nombre, id;
                    for (int i : rowsTableDisciplina) {
                        id = this.vInscripcion.getTableDisciplina().getValueAt(i, 0).toString();
                        nombre = this.vInscripcion.getTableDisciplina().getValueAt(i, 1).toString();
                        Ndisciplina disciplina = new Ndisciplina(Integer.parseInt(id), nombre);
                        DefaultTableModel model = (DefaultTableModel) this.vInscripcion.getTableDetalleInscripcion().getModel();
                        model.addRow(new Object[]{id, disciplina, "2020-05-28", "", ""});
                    }
                }
                break;
            case btnQuitar:
                int rowTableDetalleInscripcion = this.vInscripcion.getTableDetalleInscripcion().getSelectedRow();
                if (rowTableDetalleInscripcion > -1) {
                    DefaultTableModel model = (DefaultTableModel) this.vInscripcion.getTableDetalleInscripcion().getModel();
                    model.removeRow(rowTableDetalleInscripcion);
                } else {
                    this.vInscripcion.getJlbMontoTotal().setText("Monto total 0.0 BS");
                    this.vInscripcion.getJtextMontoTotal().setText("0");
                }
                this.vInscripcion.calcularMontoTotal();
                break;
            case btnMain:
                this.vInscripcion.setVisible(false);
                Cmain control = new Cmain(new Vmain());
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

//    public static void main(String[] args) {
//        Ninscripcion modelo = new Ninscripcion();
//        Vinscripcion vista = new Vinscripcion();
//        Cinscripcion control = new Cinscripcion(modelo, vista);
//        
//    }
}
