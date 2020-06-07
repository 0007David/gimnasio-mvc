/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dato;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David Torrez
 */
public class Dhorario {
    private int id;
    private String dias;
    private String horaInicio;
    private String horaFin;
    private int id_disciplina;
    private Dconexion conexion;

    public Dhorario() {
        conexion = new Dconexion();
    }
    
    public Dhorario(int id,String dias,String horaInicio,String horaFin){
        this.setId(id);
        this.setDias(dias);
        this.setHoraInicio(horaInicio);
        this.setHoraFin(horaFin);
    }

    public void insertHorario() throws Exception {
        String query = "INSERT INTO horario (dias, hora_inicio,hora_fin,id_disciplina) VALUES ('" 
                              + this.dias + "','" + this.horaInicio + "','" 
                              + this.horaFin + "','" + this.id_disciplina +"');";
        Statement consulta;
        try {
            conexion.openConexion();
            consulta = (Statement) conexion.con.createStatement();
            consulta.execute(query);
            consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println(e.toString() + " -> error sql");
            System.out.println("Error al guardar");
            throw new Exception();
        }
    }
    public void setHorario(String horario,int idDisciplina){
        String dias = horario.substring(0, horario.indexOf('.'));
        int inicio = horario.indexOf('.') + (int) 1;
        int fin = horario.indexOf("am") + (int) 2;
        String horaInicio = horario.substring(inicio, fin);
        fin++;
        String horaFin = horario.substring(fin);
        this.setDias(dias);
        this.setHoraInicio(horaInicio);
        this.setHoraFin(horaFin);
        this.setId_disciplina(idDisciplina);
    }

    public DefaultTableModel getTabla() {
        String[] columnNames = {"Nro", "Dias", "Hora Inicio", "Hora Fin"};
        DefaultTableModel listar = new DefaultTableModel(columnNames, 0);
        listar.addColumn("Nro");
        listar.addColumn("Dias");
        listar.addColumn("Hora Inicio");
        listar.addColumn("Hora Fin");
        String query = "SELECT id, dias, hora_inicio, hora_fin FROM horario WHERE id_disciplina= '" + this.id_disciplina+"';";
        //-----------------------------
        Statement Consulta;
        ResultSet resultado = null;
        String[] datos = new String[columnNames.length];
        try {
            conexion.openConexion();
            Consulta = (Statement) Dconexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            while (resultado.next()) {
                for (int i = 0; i < columnNames.length; i++) {
                    datos[i] = resultado.getString(i + 1);
                }
                listar.addRow(datos);
            }
            Consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return listar; 
    }
    
    public ArrayList<Dhorario> getTablaL(){
        ArrayList<Dhorario> list =  new ArrayList<>();
        String query = "SELECT id, dias, hora_inicio, hora_fin FROM horario WHERE id_disciplina= '" + this.id_disciplina+"';";
        //---------
        // System.out.println(query);
        Statement Consulta;
        ResultSet resultado = null;
        String[] datos = new String[4];
        try {
            this.conexion.openConexion();
            Consulta = (Statement) this.conexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            while (resultado.next()) {
                for (int i = 0; i < 4; i++) {
                    datos[i] = resultado.getString(i + 1).trim();
                }
                list.add(new Dhorario(Integer.parseInt(datos[0]), datos[1],datos[2],datos[3]));
            }
            Consulta.close();
            this.conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return list; 
    }

//    public void updateHorario() throws Exception {
//        String query = "UPDATE horario set "
//                     + "dias= '"+ this.dias +"', hora_inicio= '"+ this.horaInicio
//                    + "hora_fin= '"+ this.horaFin +"', id_disciplina= '"+ this.id_disciplina
//                     +"' WHERE id= '" + this.id+"';";
//        conexion.querySQL(query);
//    }

    public void deleteHorario() throws Exception{
        String query = "DELETE FROM horario WHERE id_disciplina = '" + this.id_disciplina+"';";
        Statement consulta;
        // System.out.println(query);
        try {
            conexion.openConexion();
            consulta = (Statement) conexion.con.createStatement();
            consulta.execute(query);
            consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println(e.toString() + " -> error sql");
            System.out.println("Error al guardar");
            throw new Exception();
        }
    }
    
    public Map<String,String> getRow(){
        Map<String, String> horario = new HashMap<>();
        String query = "SELECT id, dias, hora_inicio, hora_fin, id_disciplina FROM horario WHERE id= '" + this.id+"';";
        Statement Consulta;
        ResultSet resultado = null;
        try {
            conexion.openConexion();
            Consulta = (Statement) Dconexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            if (!resultado.next()) {
                horario.put("estado", "vacio");
            }
            for (int i = 0; i < 5; i++) {
                horario.put(rsmd.getColumnName(i + 1).trim(), resultado.getString(i + 1).trim());
            }
            Consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return horario;
    }
    public int getId(){
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setDias(String dias) {
        this.dias = dias;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setHoraFin(String horaFin) {
        this.horaFin = horaFin;
    }

    public void setId_disciplina(int id_disciplina) {
        this.id_disciplina = id_disciplina;
    }
    

    @Override
    public String toString() {
        
        return  dias + ". " + horaInicio + "-" + horaFin;
    }
   
    
}
