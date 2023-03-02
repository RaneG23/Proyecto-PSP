/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Ventana.VerStock;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Pepe
 */
public class ControlVerStock {
    private static VerStock ventana;
    private Socket socket;
    private DataOutputStream sal;
    private ObjectOutputStream salida;
    private ObjectInputStream entrada;
    
    public ControlVerStock(VerStock ventana) {
        this.ventana = ventana;
       /* try {
             sal = new DataOutputStream(socket.getOutputStream());
            sal.writeByte(1);
        } catch (IOException ex) {
            System.err.println(ex);
        }*/
    }
    
    public void dameStock(String articulo) {
        
        try {
            socket = new Socket(APPGestionAlmacen.IP,9999);
            String sql = "SELECT * FROM stocks where articulo='"+articulo+"'";
            String sqlEnc = Encriptador.encripta(sql, APPGestionAlmacen.CLAVE_SECRETA);
            sal = new DataOutputStream(socket.getOutputStream());
            sal.writeByte(1);
            salida = new ObjectOutputStream(socket.getOutputStream());
            salida.writeObject(sqlEnc);
            
            entrada = new ObjectInputStream(socket.getInputStream());
            DefaultTableModel modelo = (DefaultTableModel)entrada.readObject();
            ventana.grStock.setModel(modelo);
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println(ex);
        } finally {
            try {
                sal.close();
                salida.close();
                entrada.close();
                socket.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}
