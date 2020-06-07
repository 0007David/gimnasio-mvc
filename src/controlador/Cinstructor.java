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
import javax.swing.JTable;
import modelo.negocion.Ninstructor;
import vista.Vinstructor;
import vista.Vmain;

/**
 *
 * @author David Torrez
 */
public class Cinstructor implements MouseListener, ActionListener {

    private Ninstructor NegocioInstructor;
    private Vinstructor VistaInstructor;

    public Cinstructor(Ninstructor nInstructor, Vinstructor vInstructor) {
        this.NegocioInstructor = nInstructor;
        this.VistaInstructor = vInstructor;
        initComponente();

    }

    public enum AccionMVC {
        btnRegistrar,
        btnModificar,
        btnEliminar,
        btnMain
    }

    public void initComponente() {
        this.VistaInstructor.setTitle("Gestionar Instructores");
        this.VistaInstructor.setVisible(true);
        this.VistaInstructor.btnRegistrar.setActionCommand("btnRegistrar");
        this.VistaInstructor.btnRegistrar.addActionListener(this);
        this.VistaInstructor.btnModificar.setActionCommand("btnModificar");
        this.VistaInstructor.btnModificar.addActionListener(this);
        this.VistaInstructor.btnEliminar.setActionCommand("btnEliminar");
        this.VistaInstructor.btnEliminar.addActionListener(this);
        //btnMain
        this.VistaInstructor.btnMain.setActionCommand("btnMain");
        this.VistaInstructor.btnMain.addActionListener(this);
        //Evento MouseClicked en jtableInstructores
        this.VistaInstructor.jtableInstructores.addMouseListener(this);
    }

    // Evento Cuando Haga click a una fila de la Tabla Instructor
    @Override
    public void mouseClicked(MouseEvent evt) {
        JTable source = (JTable) evt.getSource();
        int row = source.rowAtPoint(evt.getPoint());
        String ci = source.getModel().getValueAt(row, 0) + "";
        Map<String, String> datos = NegocioInstructor.getInstructor(ci, 0);
        if (!datos.containsKey("estado")) {
            this.VistaInstructor.cargarCampos(datos);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (AccionMVC.valueOf(evt.getActionCommand())) {
            case btnRegistrar:
                try {
                    if (this.VistaInstructor.getJtextCi().isEditable()
                            && this.VistaInstructor.getJcbxSexo().getSelectedIndex() != 0) {
                        
                        String ci = this.VistaInstructor.getJtextCi().getText().trim();
                        String nombre = this.VistaInstructor.getJtextNombre().getText().trim();
                        String selectedSexo = this.VistaInstructor.getJcbxSexo().getSelectedItem().toString();
                        char sexo = (selectedSexo.equals("Masculino")) ? 'm' : (selectedSexo.equals("Femenino")) ? 'f' : 'o';
                        String telefono = this.VistaInstructor.getJtextTelefono().getText().trim();
                        String profesion = this.VistaInstructor.getJtextProfesion().getText().trim();
                        NegocioInstructor.setInstructorR(ci, nombre, sexo, Integer.parseInt(telefono), profesion);
                        System.out.println("Registrado");
                    }
                    this.VistaInstructor.getInstructores();
                } catch (Exception ex) {
                    Logger.getLogger(Cinstructor.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error Al darle click Registrar");
                }
                break;
            case btnModificar:
                try {
                    if (!this.VistaInstructor.getJtextCi().isEditable()
                            && this.VistaInstructor.getJcbxSexo().getSelectedIndex() != 0) {
                        String ci = this.VistaInstructor.getJtextCi().getText().trim();
                        String nombre = this.VistaInstructor.getJtextNombre().getText().trim();
                        String selectedSexo = this.VistaInstructor.getJcbxSexo().getSelectedItem().toString();
                        System.out.println(selectedSexo);
                        char sexo = (selectedSexo.equals("Masculino")) ? 'm' : (selectedSexo.equals("Femenino")) ? 'f' : 'o';
                        int telefono = Integer.parseInt(this.VistaInstructor.getJtextTelefono().getText().trim());
                        String profesion = this.VistaInstructor.getJtextProfesion().getText().trim();
                        NegocioInstructor.setInstructorM(ci, nombre, sexo, telefono, profesion);
                        System.out.println("Modificado");
                    }
                    this.VistaInstructor.getInstructores();
                } catch (Exception ex) {
                    Logger.getLogger(Cinstructor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case btnEliminar:
                try {
                    if (!this.VistaInstructor.getJtextCi().isEditable()) {
                        String ci = this.VistaInstructor.getJtextCi().getText().trim();
                        NegocioInstructor.setInstructorE(ci);
                        System.out.println("Eliminado");
                    }
                    this.VistaInstructor.getInstructores();
                } catch (Exception ex) {
                    Logger.getLogger(Cinstructor.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case btnMain:
                this.VistaInstructor.setVisible(false);
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

    @Override
    public void mouseExited(MouseEvent e) {

    }

//    public static void main(String[] args) {
//        Ninstructor modelo = new Ninstructor();
//        Vinstructor vista = new Vinstructor();
//        Cinstructor control = new Cinstructor(modelo, vista);
//        
//    }
}
