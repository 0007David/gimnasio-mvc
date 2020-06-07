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
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author David Torrez
 */
public class Ddisciplina {

    private int id;
    private String nombre;
    private String descripcion;
    private int id_instructor;
    private int id_sala;

    private final Dconexion conexion;

    public Ddisciplina() {
        conexion = new Dconexion();
    }

    public void insertDisciplina() throws Exception {
        String query = "INSERT INTO disciplina (nombre, descripcion, id_instructor, id_sala) VALUES ('"
                + this.nombre + "','" + this.descripcion + "','"
                + this.id_instructor + "','" + this.id_sala + "');";
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

    public DefaultTableModel getTabla(int tipo) {
        DefaultTableModel listar = new DefaultTableModel();
        String query = "";
        listar.addColumn("Nro");
        listar.addColumn("Nombre");
        listar.addColumn("Instructor");
        listar.addColumn("Sala");
        if (tipo == 0) {
            listar.addColumn("Descripcion");
            query = "SELECT d.id,d.nombre,i.nombre as nombre_instructor,s.nombre as nombre_sala,d.descripcion\n"
                    + "FROM disciplina d\n"
                    + "INNER JOIN instructor i on d.id_instructor = i.id\n"
                    + "INNER JOIN sala s on d.id_sala = s.id \n"
                    + "ORDER BY d.id;";

        } else {
            query = "SELECT d.id,d.nombre,i.nombre as nombre_instructor,s.nombre as nombre_sala\n"
                    + "FROM disciplina d\n"
                    + "INNER JOIN instructor i on d.id_instructor = i.id\n"
                    + "INNER JOIN sala s on d.id_sala = s.id\n"
                    + "ORDER BY d.id;";
        }
        Statement Consulta;
        int columnCount = listar.getColumnCount();
        ResultSet resultado = null;
        String[] datos = new String[columnCount];
        try {
            conexion.openConexion();
            Consulta = (Statement) Dconexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            while (resultado.next()) {
                for (int i = 0; i < columnCount; i++) {
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

    public void updateDisciplina() throws Exception {
        String query = "UPDATE disciplina set "
                + "nombre= '" + this.nombre + "', descripcion= '" + this.descripcion + "',"
                + "id_instructor= '" + this.id_instructor + "', id_sala= '" + this.id_sala
                + "' WHERE id= '" + this.id + "';";
        System.out.println(query);
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

    public void deleteDisciplina() throws Exception {
        String query = "DELETE FROM disciplina WHERE id = '" + this.id + "';";
        System.out.println(query);
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

    public Map<String, String> getRow() {
        Map<String, String> disciplina = new HashMap<>();
        String query = "SELECT d.id ,d.nombre ,d.descripcion,i.id as id_instructor,i.nombre as nombre_instructor,s.id as id_sala,s.nombre as nombre_sala\n"
                + "FROM disciplina d\n"
                + "INNER JOIN instructor i on d.id_instructor = i.id\n"
                + "INNER JOIN sala s on d.id_sala = s.id\n"
                + "WHERE d.id= '" + this.id + "';";
        //----------------------------------
        Statement Consulta;
        ResultSet resultado = null;
        try {
            conexion.openConexion();
            Consulta = (Statement) Dconexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            if (!resultado.next()) {
                disciplina.put("estado", "vacio");
            }
            for (int i = 0; i < 7; i++) {
                disciplina.put(rsmd.getColumnName(i + 1).trim(), resultado.getString(i + 1).trim());
            }
            Consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return disciplina;
    }

    public int getMaxId() {
        Map<String, String> disciplina = new HashMap<>();
        String query = "SELECT MAX(id) as id_max FROM disciplina;";
        System.out.println(query);
        //-------------------------------
        Statement Consulta;
        ResultSet resultado = null;
        try {
            conexion.openConexion();
            Consulta = (Statement) conexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            if (!resultado.next()) {
                disciplina.put("estado", "vacio");
            }
            disciplina.put(rsmd.getColumnName(1).trim(), resultado.getString(1).trim());
            Consulta.close();
            conexion.closeConexion();

        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return Integer.parseInt(disciplina.get("id_max"));
    }

    public int getId() {
        return this.id;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId_instructor(int id_instructor) {
        this.id_instructor = id_instructor;
    }

    public void setId_sala(int id_sala) {
        this.id_sala = id_sala;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
