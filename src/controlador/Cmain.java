/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import modelo.negocion.*;
import vista.*;


/**
 *
 * @author David Torrez
 */
public class Cmain implements ActionListener {

    Vmain vMain;

    public Cmain(Vmain vistaMain) {
        this.vMain = vistaMain;
        initComponente();
    }

    public enum AccionMVC {
        btnClientes,
        btnInstructores,
        btnSalas,
        btnDisciplinas,
        btnInscripciones
    }

    public void initComponente() {
        this.vMain.setTitle("Software de Gestion para Gimnasio");
        this.vMain.setVisible(true);
        this.vMain.btnClientes.setActionCommand("btnClientes");
        this.vMain.btnClientes.addActionListener(this);
        this.vMain.btnInstructores.setActionCommand("btnInstructores");
        this.vMain.btnInstructores.addActionListener(this);
        this.vMain.btnSalas.setActionCommand("btnSalas");
        this.vMain.btnSalas.addActionListener(this);
        this.vMain.btnDisciplinas.setActionCommand("btnDisciplinas");
        this.vMain.btnDisciplinas.addActionListener(this);
        this.vMain.btnInscripciones.setActionCommand("btnInscripciones");
        this.vMain.btnInscripciones.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        switch (AccionMVC.valueOf(evt.getActionCommand())) {
            case btnClientes:
                this.vMain.setVisible(false);
                Ncliente modelo = new Ncliente();
                Vcliente vista = new Vcliente();
                Ccliente control = new Ccliente(modelo, vista);
                break;
            case btnInstructores:
                this.vMain.setVisible(false);
                Ninstructor modeloInsctructor = new Ninstructor();
                Vinstructor vistaInstructor = new Vinstructor();
                Cinstructor controlInsctructor = new Cinstructor(modeloInsctructor, vistaInstructor);
                break;
            case btnSalas:
                this.vMain.setVisible(false);
                Nsala modeloSala = new Nsala();
                Vsala vistaSala = new Vsala();
                Csala controlSala = new Csala(modeloSala, vistaSala);
               
                break;
            case btnDisciplinas:
                this.vMain.setVisible(false);
                Ndisciplina modeloDisciplina = new Ndisciplina();
                Vdisciplina vistaDisciplina = new Vdisciplina();
                Cdisciplina controlDisciplina = new Cdisciplina(modeloDisciplina, vistaDisciplina);
                break;
            case btnInscripciones:
                this.vMain.setVisible(false);
                Ninscripcion modeloInscripcion = new Ninscripcion();
                Vinscripcion vistaInscripcion = new Vinscripcion();
                Cinscripcion controlInscripcion = new Cinscripcion(modeloInscripcion, vistaInscripcion);
                break;
        }
    }

    public static void main(String[] args) {
        Vmain vistaMain = new Vmain();
        Cmain control = new Cmain(vistaMain);
    }
}
