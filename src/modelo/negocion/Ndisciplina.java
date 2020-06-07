/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.negocion;

import java.util.ArrayList;
import modelo.dato.Ddisciplina;
import java.util.Map;
import javax.swing.table.DefaultTableModel;
import modelo.dato.Dhorario;

/**
 *
 * @author David Torrez
 */
public class Ndisciplina {
    
    private Ddisciplina Datodisciplina;
    private ArrayList<Dhorario> DatoHorarios;
    public Ndisciplina(){
        Datodisciplina = new Ddisciplina();
        DatoHorarios = new ArrayList<Dhorario>();
    }
//    public void setDisciplina(int id ,String nombre, String descripcion, int id_instructor,int id_sala ){
//        Datodisciplina.setId(id);
//        Datodisciplina.setNombre(nombre);
//        Datodisciplina.setDescripcion(descripcion);
//        Datodisciplina.setId_instructor(id_instructor);
//        Datodisciplina.setId_sala(id_sala);
//    }

    public Ndisciplina(int id,String nombre) {
        Datodisciplina = new Ddisciplina();
        Datodisciplina.setId(id);
        Datodisciplina.setNombre(nombre);
    }
    
    public void setDisciplinaR(String nombre, String descripcion, int id_instructor,int id_sala) throws Exception {
        Datodisciplina.setNombre(nombre);
        Datodisciplina.setDescripcion(descripcion);
        Datodisciplina.setId_instructor(id_instructor);
        Datodisciplina.setId_sala(id_sala);
        Datodisciplina.insertDisciplina();
    }

    public void setDisciplinaM(int id,String nombre, String descripcion, int id_instructor,int id_sala) throws Exception {
        Datodisciplina.setId(id);
        Datodisciplina.setNombre(nombre);
        Datodisciplina.setDescripcion(descripcion);
        Datodisciplina.setId_instructor(id_instructor);
        Datodisciplina.setId_sala(id_sala);
        Datodisciplina.updateDisciplina();
    }

    public void setDisciplinaE() throws Exception {
        Datodisciplina.setId(getId());
        Datodisciplina.deleteDisciplina();
    }

    public DefaultTableModel getTablaDisciplinas(int type) {
        return Datodisciplina.getTabla(type);
    }

    public Map<String, String> getDisciplina(int id) {
        Datodisciplina.setId(id);
        return Datodisciplina.getRow();
    }
    
    // Metodos de Horarios
    
    public void addHorarioDisciplina(String horarioAdd) throws Exception {
        Dhorario horario = new Dhorario();
        horario.setHorario(horarioAdd, this.getId());
        this.DatoHorarios.add(horario);
    }
    
    public void setHorariosR() throws Exception {

        for (Dhorario horario : DatoHorarios) {
            horario.setId_disciplina(getId());
            horario.insertHorario();
        }
    }
    
    public ArrayList<String> getHorarios() {
        Dhorario horario = new Dhorario();
        horario.setId_disciplina(getId()); 
        ArrayList<String> horariosL = new ArrayList<>();
        ArrayList<Dhorario> horarios = horario.getTablaL();
        for( Dhorario dHorario: horarios){
            horariosL.add(dHorario.toString());
        }
        return horariosL;
    }
    
    public void setHorariosE() throws Exception {
        Dhorario horario = new Dhorario();
        horario.setId_disciplina(getId());
        horario.deleteHorario();
    }
    
    public int getMaxId(){
        return Datodisciplina.getMaxId();
    }
    
    public void setId(int id) {
        Datodisciplina.setId(id);
    }
    
    public int getId(){
        return Datodisciplina.getId();
    }
    
    public String getNombre(){
        return Datodisciplina.getNombre();
    }

    @Override
    public String toString() {
        return this.Datodisciplina.toString();
    }
    
}
