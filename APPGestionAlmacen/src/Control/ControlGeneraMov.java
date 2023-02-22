/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Ventana.GeneraMov;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Pepe
 */
public class ControlGeneraMov {
    private final GeneraMov ventana;
    private Socket socket;
    private DataInputStream ent;
    private DataOutputStream sal;
    
    public ControlGeneraMov(GeneraMov ventana) {
        this.ventana = ventana;
    }
    
    public void crearMovimiento(String Tipo, String Alm, String Art, int Ud){
        try {
            socket = new Socket("localhost",9999);
            
            if (Tipo.equals("S")) {
                Ud = Ud * -1;
            }
            String sql = "INSERT INTO stocks (almacen, articulo, stock) "
                    + "VALUES('"+Alm+"','"+Art+"',"+Ud+") ON DUPLICATE KEY UPDATE stock = stock + " + Ud;
            String sqlEnc = Encriptador.encripta(sql, APPGestionAlmacen.CLAVE_SECRETA);
            sal = new DataOutputStream(socket.getOutputStream());
            sal.writeByte(2);
            sal.writeUTF(sqlEnc);
            
            ent = new DataInputStream(socket.getInputStream());
            
            if (ent.readBoolean()) {
                JOptionPane.showMessageDialog(ventana, "Se ha ajustado el stock correctaente.");
            } else {
                JOptionPane.showMessageDialog(ventana, "Se ha producido un error. " +
                        "Compruebe los datos y vuelva a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE); 
            }
        } catch(Exception e) {
            e.getStackTrace();
        }
        
        
    }
}
