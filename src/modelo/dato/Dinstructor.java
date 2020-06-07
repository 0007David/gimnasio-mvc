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
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import static modelo.dato.Dconexion.con;
import modelo.negocion.Ncliente;
import modelo.negocion.Ninstructor;

/**
 *
 * @author David Torrez
 */
public class Dinstructor {

    private String ci;
    private String nombre;
    private char sexo;
    private int telefono;
    private String profesion;

    private Dconexion conexion;

    public Dinstructor() {
        this.conexion = new Dconexion();
    }

    public Dinstructor(String ci, String nombre) {
        this.ci = ci;
        this.nombre = nombre;
    }

    public void insertInstructor() throws Exception {
        String query = "INSERT INTO instructor (ci, nombre, sexo, telefono, profesion) VALUES ('"
                + this.ci + "','" + this.nombre + "','"
                + this.sexo + "','" + this.telefono + "','"
                + this.profesion + "');";
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

    public DefaultTableModel getTabla() {
        String[] columnNames = {"CI", "Nombre", "Sexo", "Telefono", "Profesión"};
        DefaultTableModel listar = new DefaultTableModel(columnNames, 0);

        String query = "SELECT ci, nombre, sexo,telefono,profesion FROM instructor";
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

    public void updateInstructor() throws Exception {
        String query = "UPDATE instructor set "
                + "nombre= '" + this.nombre + "', profesion= '" + this.profesion + "', telefono='" + this.telefono + "', sexo='" + this.sexo
                + "' WHERE ci= '" + this.ci + "';";
        // System.out.println(query);
        Statement consulta;
        try {
            conexion.openConexion();
            consulta = (Statement) conexion.con.createStatement();
            consulta.execute(query);
            consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println(e.toString() + " -> error sql");
            System.out.println("Error al update");
            throw new Exception();
        }
    }

    public void deleteInstructor() throws Exception {
        String query = "DELETE FROM instructor WHERE ci = '" + this.ci + "';";
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

    public DefaultComboBoxModel getComboBox() {
        DefaultComboBoxModel comboBox = new DefaultComboBoxModel();
        String query = "SELECT id, nombre FROM instructor;";
        // System.out.println(query);
        //---------------------------------------
        Statement Consulta;
        ResultSet resultado = null;
        String[] datos = new String[2];
        try {
            this.conexion.openConexion();
            Consulta = (Statement) con.createStatement();
            resultado = Consulta.executeQuery(query);
            comboBox.addElement(new Ncliente("0", "Seleccione"));
            while (resultado.next()) {
                for (int i = 0; i < 2; i++) {
                    datos[i] = resultado.getString(i + 1).trim();
                }
                comboBox.addElement(new Ninstructor(datos[0], datos[1]));
            }
            Consulta.close();
            this.conexion.closeConexion();

        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return comboBox;
    }

    public Map<String, String> getRow(int type) {
        Map<String, String> cliente = new HashMap<>();
//        map.put("name", "demo");
        String query = "";
        if (type == 0) {
            query = "SELECT ci, nombre, sexo,telefono,profesion FROM instructor WHERE ci= '" + this.ci + "';";
        } else {
            query = "SELECT ci, nombre, sexo,telefono,profesion FROM instructor WHERE id= '" + this.ci + "';";
        }
        //----------------------------
        Statement Consulta;
        ResultSet resultado = null;
        try {
            conexion.openConexion();
            Consulta = (Statement) Dconexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            if (!resultado.next()) {
                cliente.put("estado", "vacio");
            }
            for (int i = 0; i < 5; i++) {
                cliente.put(rsmd.getColumnName(i + 1).trim(), resultado.getString(i + 1).trim());
            }
            Consulta.close();
            conexion.closeConexion();
        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return cliente;
    }

    public String getCi() {
        return this.ci;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setSexo(char sexo) {
        this.sexo = sexo;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    @Override
    public String toString() {
        return nombre;
    }

}
