/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Ventana.Traspasos;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Pepe
 */
public class ControlTraspasos {
    private final Traspasos ventana;
    private Socket socket;
    private DataInputStream ent;
    private DataOutputStream sal;
    
    public ControlTraspasos(Traspasos ventana) {
        this.ventana = ventana;
    }
    
    public void crearTraspaso(String AlmO, String AlmD, String Art, int Ud){
        try {
            socket = new Socket(APPGestionAlmacen.IP,9999);
            String sql = "INSERT INTO traspasos(alm_origen,alm_destino,articulo,unidades,recibido) " +
                    "VALUES('"+AlmO+"','"+AlmD+"','"+Art+"',"+Ud+",0)";
            String sqlEnc = Encriptador.encripta(sql, APPGestionAlmacen.CLAVE_SECRETA);
            sal = new DataOutputStream(socket.getOutputStream());
            sal.writeByte(2);
            sal.writeUTF(sqlEnc);
            
            ent = new DataInputStream(socket.getInputStream());
            
            if (ent.readBoolean()) {
                JOptionPane.showMessageDialog(ventana, "Se ha creado el traspaso correctamente.");
            } else {
                JOptionPane.showMessageDialog(ventana, "Se ha producido un error. " +
                        "Compruebe los datos y vuelva a intentarlo.", "Error", JOptionPane.ERROR_MESSAGE); 
            }
        } catch(Exception e) {
            e.getStackTrace();
        }
        
        
    }
}
