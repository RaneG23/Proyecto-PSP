/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BD;

import java.sql.*;
/**
 *
 * @author Pepe
 */
public class Conexion {
    private static Connection con;
    
    public static boolean conectaBD(String Ruta, String User, String Pass){
        try {
            con = DriverManager.getConnection(Ruta, User, Pass);
            System.out.println("CONECTADO!!");
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }  
    }
    
    public boolean compruebaUsuario(String user, String pass) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try { 
            // Preparamos la sentencia
            String query = "SELECT * FROM USUARIOS "
                    + "where usuario=? and contrasenya=?";
            ps = con.prepareStatement(query);

            // Le pasamos los parámetros
            ps.setString(1, user);
            ps.setString(2, pass);
            
            rs = ps.executeQuery();
            // next() decuelve true si existe algún resultado
            return rs.next();
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error");
            System.exit(0);
            return false;
        } finally {
            try{rs.close();} catch (Exception e) {}
            try{ps.close();} catch (Exception e) {}
        }
    }
    
    public ResultSet dameResultado(String sql) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try { 
            // Preparamos la sentencia
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error");
        } finally {
            return rs;
        }
    }
    
    public boolean insertaDato(String sql) {
        ResultSet rs = null;
        PreparedStatement ps = null;
        try { 
            // Preparamos la sentencia
            ps = con.prepareStatement(sql);
            int insertados = ps.executeUpdate();
            return (insertados > 0);
        } catch (SQLException ex) {
            System.out.println("Se ha producido un error");
            ex.getStackTrace();
            return false;
        } finally {
            try{rs.close();} catch (Exception e) {}
            try{ps.close();} catch (Exception e) {}
        }
    }
}
