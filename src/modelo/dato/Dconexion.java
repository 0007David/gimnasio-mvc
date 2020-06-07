/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo.dato;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import modelo.negocion.*;

/**
 *
 * @author David Torrez
 */
public class Dconexion {

    public static Connection con = null;
    private String nameDatabase = "db_gimnasio";
    private String user = "postgres";
    private String password = "1234";
    private static String driver = "org.postgresql.Driver";

    public Connection openConexion() {
        try {
            Class.forName(driver);
            con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/" + nameDatabase, user, password);
//            System.out.println("con realizada con exito");
        } catch (Exception ee) {
            System.out.println(ee.toString() + " conexion fallida");
        }
        return con;
    }

    public Connection closeConexion() {
        try {
            con.close();
//            System.out.println("Cerrrando Conexion");
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return con = null;
    }


   
}
