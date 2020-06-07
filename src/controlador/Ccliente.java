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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.negocion.Ncliente;
import vista.Vcliente;
import vista.Vmain;

/**
 *
 * @author David Torrez
 */
public class Ccliente implements MouseListener, ActionListener {

    private Ncliente NegocioCliente;
    private Vcliente VistaCliente;

    public Ccliente(Ncliente nCliente, Vcliente vCliente) {
        this.NegocioCliente = nCliente;
        this.VistaCliente = vCliente;
        initComponente();

    }

    public enum AccionMVC {
        btnRegistrar,
        btnModificar,
        btnEliminar,
        btnMain

    }

    public void initComponente() {
        this.VistaCliente.setTitle("Gestionar Clientes");
        this.VistaCliente.setVisible(true);
        this.VistaCliente.btnRegistrar.setActionCommand("btnRegistrar");
        this.VistaCliente.btnRegistrar.addActionListener(this);
        this.VistaCliente.btnModificar.setActionCommand("btnModificar");
        this.VistaCliente.btnModificar.addActionListener(this);
        this.VistaCliente.btnEliminar.setActionCommand("btnEliminar");
        this.VistaCliente.btnEliminar.addActionListener(this);
        //btnMain
        this.VistaCliente.btnMain.setActionCommand("btnMain");
        this.VistaCliente.btnMain.addActionListener(this);
        //Evento MouseClicked en jtableClientes
        this.VistaCliente.jtableClientes.addMouseListener(this);
    }

    // Evento Cuando Haga click a una fila de la Tabla Clientes
    @Override
    public void mouseClicked(MouseEvent evt) {
        JTable source = (JTable) evt.getSource();
        int row = source.rowAtPoint(evt.getPoint());
        String ci = source.getModel().getValueAt(row, 0) + "";
        Map<String, String> datos = NegocioCliente.getCliente(ci.trim());
        if (!datos.containsKey("estado")) {
            this.VistaCliente.cargarCampos(datos);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (AccionMVC.valueOf(evt.getActionCommand())) {
            case btnRegistrar:
                try {
                    if (this.VistaCliente.getJtextCi().isEditable()
                            && this.VistaCliente.getJcbxSexo().getSelectedIndex() != 0) {
                        
                        String ci = this.VistaCliente.getJtextCi().getText().trim();
                        String nombre = this.VistaCliente.getJtextNombre().getText().trim();
                        String selectedSexo = this.VistaCliente.getJcbxSexo().getSelectedItem().toString();
                        char sexo = (selectedSexo.equals("Masculino")) ? 'm' : (selectedSexo.equals("Femenino")) ? 'f' : 'o';
                        String telefono = this.VistaCliente.getJtextTelefono().getText().trim();
                        String correo = this.VistaCliente.getJtextCorreo().getText().trim();
                        String peso = this.VistaCliente.getJtextPeso().getText().trim();
                        NegocioCliente.setClienteR(ci, nombre, sexo, telefono, correo, Float.parseFloat(peso));
                        System.out.println("Registrado");
                    }
                    this.VistaCliente.getClientes();
                } catch (Exception ex) {
                    Logger.getLogger(Ccliente.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error Al darle click Registrar");
                }
                break;
            case btnModificar:
                try {
                    if (!this.VistaCliente.getJtextCi().isEditable()
                            && this.VistaCliente.getJcbxSexo().getSelectedIndex() != 0) {
                        
                        String ci = this.VistaCliente.getJtextCi().getText().trim();
                        String nombre = this.VistaCliente.getJtextNombre().getText().trim();
                        String selectedSexo = this.VistaCliente.getJcbxSexo().getSelectedItem().toString();
                        System.out.println(selectedSexo);
                        char sexo = (selectedSexo.equals("Masculino")) ? 'm' : (selectedSexo.equals("Femenino")) ? 'f' : 'o';
                        String telefono = this.VistaCliente.getJtextTelefono().getText().trim();
                        String correo = this.VistaCliente.getJtextCorreo().getText().trim();
                        String peso = this.VistaCliente.getJtextPeso().getText().trim();
                        NegocioCliente.setClienteM(ci, nombre, sexo, telefono, correo, Float.parseFloat(peso));
                        System.out.println("Modificado");
                    }
                    this.VistaCliente.getClientes();
                } catch (Exception ex) {
                    Logger.getLogger(Ccliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case btnEliminar:
                try {
                    if (!this.VistaCliente.getJtextCi().isEditable()) {
                        int selectedOption = JOptionPane.showConfirmDialog(null,
                                "Esta Seguro de Elimanar al Cliente: " + this.VistaCliente.getJtextNombre().getText(),
                                "Eligue",
                                JOptionPane.YES_NO_OPTION);
                        if (selectedOption == JOptionPane.YES_OPTION) {

                            String ci = this.VistaCliente.getJtextCi().getText().trim();
                            NegocioCliente.setClienteE(ci);
                            System.out.println("Eliminado");
                        }
                        this.VistaCliente.getClientes();
                    }

                } catch (Exception ex) {
                    Logger.getLogger(Ccliente.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case btnMain:
                this.VistaCliente.setVisible(false);
                Cmain control = new Cmain(new Vmain());
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // System.out.println("mousePressed");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // System.out.println("mouseReleased");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // System.out.println("mouseEntered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // System.out.println("mouseExited");
    }

//    public static void main(String[] args) {
//        Ncliente modelo = new Ncliente();
//        Vcliente vista = new Vcliente();
//        Ccliente control = new Ccliente(modelo, vista);
//        
//    }
}
