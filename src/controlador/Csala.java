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
import modelo.negocion.Nsala;
import vista.Vmain;
import vista.Vsala;

/**
 *
 * @author David Torrez
 */
public class Csala implements MouseListener, ActionListener {

    private Nsala NegocioSala;
    private Vsala VistaSala;

    public Csala(Nsala nSala, Vsala vSala) {
        this.NegocioSala = nSala;
        this.VistaSala = vSala;
        initComponente();

    }

    public enum AccionMVC {
        btnRegistrar,
        btnModificar,
        btnEliminar,
        btnMain
    }

    public void initComponente() {
        this.VistaSala.setTitle("Gestionar Salas");
        this.VistaSala.setVisible(true);
        this.VistaSala.btnRegistrar.setActionCommand("btnRegistrar");
        this.VistaSala.btnRegistrar.addActionListener(this);
        this.VistaSala.btnModificar.setActionCommand("btnModificar");
        this.VistaSala.btnModificar.addActionListener(this);
        this.VistaSala.btnEliminar.setActionCommand("btnEliminar");
        this.VistaSala.btnEliminar.addActionListener(this);
        //btnMain
        this.VistaSala.btnMain.setActionCommand("btnMain");
        this.VistaSala.btnMain.addActionListener(this);
        this.VistaSala.jtableSalas.addMouseListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent evt) {
        JTable source = (JTable) evt.getSource();
        int row = source.rowAtPoint(evt.getPoint());
        // int column = source.columnAtPoint(evt.getPoint());
        String id = source.getModel().getValueAt(row, 0) + "";
        Map<String, String> datos = NegocioSala.getSala(Integer.parseInt(id));
        if (!datos.containsKey("estado")) {
            this.VistaSala.cargarCampos(datos);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (AccionMVC.valueOf(evt.getActionCommand())) {
            case btnRegistrar:
                try {
                    if (!this.VistaSala.getJtextId().isVisible()) {
                        
                        String nombre = this.VistaSala.getJtextNombre().getText().trim();
                        int capacidad = Integer.parseInt(this.VistaSala.getJtextCapacidad().getText().trim());
                        NegocioSala.setSalaR(nombre, capacidad);
                        System.out.println("Registrado");
                    }
                    this.VistaSala.getSalas();
                } catch (Exception ex) {
                    Logger.getLogger(Csala.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error Al darle click Registrar");
                }
                break;
            case btnModificar:
                try {
                    if (this.VistaSala.getJtextId().isVisible()) {
                        
                        int id = Integer.parseInt(this.VistaSala.getJtextId().getText().trim());
                        String nombre = this.VistaSala.getJtextNombre().getText().trim();
                        int capacidad = Integer.parseInt(this.VistaSala.getJtextCapacidad().getText().trim());
                        NegocioSala.setSalaM(id, nombre, capacidad);
                        System.out.println("Modificado");
                    }
                    this.VistaSala.getSalas();
                } catch (Exception ex) {
                    Logger.getLogger(Csala.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case btnEliminar:
                try {
                    if (this.VistaSala.getJtextId().isVisible()) {
                        int id = Integer.parseInt(this.VistaSala.getJtextId().getText().trim());
                        NegocioSala.setSalaE(id);
                        System.out.println("Eliminado");
                    }
                    this.VistaSala.getSalas();
                } catch (Exception ex) {
                    Logger.getLogger(Csala.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case btnMain:
                this.VistaSala.setVisible(false);
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
//        Nsala modelo = new Nsala();
//        Vsala vista = new Vsala();
//        Csala control = new Csala(modelo, vista);
//    }
}
