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
import java.util.ArrayList;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.negocion.Ndisciplina;
import modelo.negocion.Ninstructor;
import modelo.negocion.Nsala;
import vista.Vdisciplina;
import vista.Vmain;

/**
 *
 * @author David Torrez
 */
public class Cdisciplina implements MouseListener, ActionListener {

    private Ndisciplina NegocioDisciplina;
    private Vdisciplina VistaDisciplina;

    public Cdisciplina(Ndisciplina nDisciplina, Vdisciplina vDisciplina) {
        this.NegocioDisciplina = nDisciplina;
        this.VistaDisciplina = vDisciplina;

        initComponente();
        this.VistaDisciplina.setVisible(true);

    }

    

    public enum AccionMVC {
        btnRegistrar,
        btnModificar,
        btnEliminar,
        btnMain
    }

    public void initComponente() {
        this.VistaDisciplina.setTitle("Gestionar Disciplinas");
        this.VistaDisciplina.btnRegistrar.setActionCommand("btnRegistrar");
        this.VistaDisciplina.btnRegistrar.addActionListener(this);
        this.VistaDisciplina.btnModificar.setActionCommand("btnModificar");
        this.VistaDisciplina.btnModificar.addActionListener(this);
        this.VistaDisciplina.btnEliminar.setActionCommand("btnEliminar");
        this.VistaDisciplina.btnEliminar.addActionListener(this);
        //btnMain
        this.VistaDisciplina.btnMain.setActionCommand("btnMain");
        this.VistaDisciplina.btnMain.addActionListener(this);
        //Agregamos el Listener para el Evento MouseClicked en JtableDisciplinas
        this.VistaDisciplina.jtableDisciplina.addMouseListener(this);
    }
    
    // Evneto Clicked en Fila de la JtableDisciplina
    @Override
    public void mouseClicked(MouseEvent evt) {
            JTable source = (JTable) evt.getSource();
        int row = source.rowAtPoint(evt.getPoint());
        // int column = source.columnAtPoint(evt.getPoint());
        String id = source.getModel().getValueAt(row, 0) + "";
        this.VistaDisciplina.limpiar();
        Map<String, String> datos = NegocioDisciplina.getDisciplina(Integer.parseInt(id.trim()));
        if (!datos.containsKey("estado")) {
            
            //Seleccionar el instructor y la sala selectas
            this.VistaDisciplina.cargarCampos(datos);
            this.NegocioDisciplina.setId(Integer.parseInt(datos.get("id")));
            //Trae los horarios de la disciplina nDisciplina.getHorarios()
            ArrayList<String> list = NegocioDisciplina.getHorarios();
            this.VistaDisciplina.cargarHorarios(list);
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (AccionMVC.valueOf(evt.getActionCommand())) {
            case btnRegistrar:
                try {
                    if (!this.VistaDisciplina.getJtextId().isVisible()
                            && this.VistaDisciplina.getJcbxInstructores().getSelectedIndex() != 0
                            && this.VistaDisciplina.getJcbxSalas().getSelectedIndex() != 0) {

                        String nombre = this.VistaDisciplina.getJtextNombre().getText().trim();
                        String descripcion = this.VistaDisciplina.getJtextDescripcion().getText().trim();
                        Ninstructor inst = (Ninstructor) this.VistaDisciplina.getJcbxInstructores().getSelectedItem();
                        Nsala sala = (Nsala) this.VistaDisciplina.getJcbxSalas().getSelectedItem();
                        System.out.println("id_instructor: " + inst.getCi());
                        System.out.println("id_sala: " + sala.getId());

                        this.NegocioDisciplina.setDisciplinaR(
                                nombre,
                                descripcion,
                                Integer.parseInt(inst.getCi()),
                                sala.getId());
                        //agregamos los horarios a la Disciplina Recien Insertada
                        this.NegocioDisciplina.setId(NegocioDisciplina.getMaxId());
                        //insertamos los horarios
                        ArrayList<String> horarios = this.VistaDisciplina.getHorariosChecked();
                        for (String horario : horarios) {
                            System.out.println(horario);
                            this.NegocioDisciplina.addHorarioDisciplina(horario);
                        }
                        //insertar Horario
                        this.NegocioDisciplina.setHorariosR();
                        System.out.println("Registrado");
                    }
                    this.VistaDisciplina.limpiar();
                    this.VistaDisciplina.hideShowId(false);
                    this.VistaDisciplina.getDisciplinas();
                } catch (Exception ex) {
                    Logger.getLogger(Cdisciplina.class.getName()).log(Level.SEVERE, null, ex);
                    System.out.println("Error Al darle click Registrar");
                }
                break;
            case btnModificar:
                try {
                    
                    if (this.VistaDisciplina.getJtextId().isVisible() 
                            && this.VistaDisciplina.getJcbxInstructores().getSelectedIndex() != 0
                            && this.VistaDisciplina.getJcbxSalas().getSelectedIndex() != 0) {

                        int id = Integer.parseInt(this.VistaDisciplina.getJtextId().getText().trim());
                        String nombre = this.VistaDisciplina.getJtextNombre().getText().trim();
                        String descripcion = this.VistaDisciplina.getJtextDescripcion().getText().trim();
                        Ninstructor inst = (Ninstructor) this.VistaDisciplina.getJcbxInstructores().getSelectedItem();
                        Nsala sala = (Nsala) this.VistaDisciplina.getJcbxSalas().getSelectedItem();
                        this.NegocioDisciplina.setDisciplinaM(
                                id,
                                nombre,
                                descripcion,
                                Integer.parseInt(inst.getCi()),
                                sala.getId());
                        //Eliminar Los Horarios Existentes
                        NegocioDisciplina.setId(id);
                        NegocioDisciplina.setHorariosE();

                        // Insertando nuevos Horarios
                        ArrayList<String> horarios = this.VistaDisciplina.getHorariosChecked();
                        for (String horario : horarios) {
                            System.out.println(horario);
                            this.NegocioDisciplina.addHorarioDisciplina(horario);
                        }
                        this.NegocioDisciplina.setHorariosR();
                    }
                    this.VistaDisciplina.limpiar();
                    this.VistaDisciplina.hideShowId(false);
                    this.VistaDisciplina.getDisciplinas();
                } catch (Exception ex) {
                    Logger.getLogger(Cdisciplina.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case btnEliminar:
                try {
                    if (this.VistaDisciplina.getJtextId().isVisible()) {
                        int selectedOption = JOptionPane.showConfirmDialog(null,
                                "Esta Seguro de Elimanar la Disciplina "+this.VistaDisciplina.getJtextNombre().getText()+" ?",
                                "Eligue",
                                JOptionPane.YES_NO_OPTION);
                        if (selectedOption == JOptionPane.YES_OPTION) {
                            int id = Integer.parseInt(this.VistaDisciplina.getJtextId().getText().trim());
                            NegocioDisciplina.setId(id);
                            //Eliminamos sus horarios
                            NegocioDisciplina.setHorariosE();
                            //elimininamos la disciplina
                            NegocioDisciplina.setDisciplinaE();
                            System.out.println("se puede Eliminar: " + id);
                        }
                    }
                    this.VistaDisciplina.limpiar();
                    this.VistaDisciplina.hideShowId(false);
                    this.VistaDisciplina.getDisciplinas();
                } catch (Exception ex) {
                    Logger.getLogger(Cdisciplina.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
                case btnMain:
                this.VistaDisciplina.setVisible(false);
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
//        Ndisciplina modelo = new Ndisciplina();
//        Vdisciplina vista = new Vdisciplina();
//        Cdisciplina control = new Cdisciplina(modelo, vista);
//
//    }

}
