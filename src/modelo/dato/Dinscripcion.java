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
import javax.swing.table.TableModel;

/**
 *
 * @author David Torrez
 */
public class Dinscripcion {

    private int id;
    private String fecha; // 2020-06-02 12:30:50
    private float monto;
    private int idCliente;

    private Dconexion conexion;

    public Dinscripcion() {
        conexion = new Dconexion();
    }

    public void insertBoletaInscripcion() throws Exception {
        String query = "INSERT INTO boleta_inscripcion (fecha,monto, id_cliente) VALUES ('"
                + this.fecha + "','" + this.monto + "','" + this.idCliente + "');";
        // System.out.println(query);
        Statement consulta;
        try {
            conexion.openConexion();
            consulta = (Statement) conexion.con.createStatement();
            consulta.execute(query);
            consulta.close();
//            System.out.println("Guardado con Exito");
            conexion.closeConexion();

        } catch (SQLException e) {
            System.out.println(e.toString() + " -> error sql");
            System.out.println("Error al guardar");
            throw new Exception();

        }
    }
    
    public void updateBoletaInscripcion() throws Exception {
        String query = "UPDATE boleta_inscripcion set "
                + "fecha= '" + this.fecha + "', monto= '" + this.monto
                + "' WHERE id= '" + this.id + "';";
        Statement consulta;
        try {
            conexion.openConexion();
            consulta = (Statement) conexion.con.createStatement();
            consulta.execute(query);
            consulta.close();
//            System.out.println("Guardado con Exito");
            conexion.closeConexion();

        } catch (SQLException e) {
            System.out.println(e.toString() + " -> error sql");
            System.out.println("Error al guardar");
            throw new Exception();

        }
    }
    
     public void deleteBoletaInscripcion() throws Exception{
        String query = "DELETE FROM boleta_inscripcion WHERE id = '" + this.id + "';";
        Statement consulta;
        try {
            conexion.openConexion();
            consulta = (Statement) conexion.con.createStatement();
            consulta.execute(query);
            consulta.close();
//            System.out.println("Guardado con Exito");
            conexion.closeConexion();

        } catch (SQLException e) {
            System.out.println(e.toString() + " -> error sql");
            System.out.println("Error al guardar");
            throw new Exception();
        }
    }

    public int getMaxId() {
        Map<String, String> inscripcion = new HashMap<>();
        String query = "SELECT MAX(id) as id_max FROM boleta_inscripcion;";
        // System.out.println(query);
        //---------------------------------
        Statement Consulta;
        ResultSet resultado = null;
        try {
            conexion.openConexion();
            Consulta = (Statement) conexion.con.createStatement();
            resultado = Consulta.executeQuery(query);
            ResultSetMetaData rsmd = resultado.getMetaData();
            if (!resultado.next()) {
                inscripcion.put("estado", "vacio");
            }
                inscripcion.put(rsmd.getColumnName(1).trim(), resultado.getString(1).trim());
            Consulta.close();
            conexion.closeConexion();

        } catch (SQLException e) {
            System.out.println("no se pudo listar los datos" + e.getMessage());
        }
        return Integer.parseInt(inscripcion.get("id_max"));
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public DefaultTableModel getTabla() {
        String[] columnNames = {"Nro", "Cliente", "Fecha", "Monto"};
        DefaultTableModel listar = new DefaultTableModel(columnNames, 0);
        String query = "SELECT bi.id, c.nombre, bi.fecha,bi.monto\n"
                + "FROM public.boleta_inscripcion bi\n"
                + "INNER JOIN cliente c ON c.id = bi.id_cliente\n"
                + "ORDER BY bi.id;";

        Statement Consulta;
        int columnCount = listar.getColumnCount();
        ResultSet resultado = null;
        String[] datos = new String[columnCount];
        try {
            conexion.openConexion();
            Consulta = (Statement) conexion.con.createStatement();
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

    public int getId() {
        return this.id;
    }

    

   

}
