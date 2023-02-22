/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Ventana.Login;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JOptionPane;

/**
 *
 * @author Pepe
 */
public class ControlLogin {
    private int Intentos = 0;
    private static Login ventana;
    private Socket socket;
    DataInputStream entrada;
    DataOutputStream salida;
    
    public ControlLogin(Login ventana) {
        this.ventana = ventana;
        try {
            socket = new Socket("localhost",9999);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(ventana, "No se ha encontrado el servidor. Saliendo.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
            
        try {
            salida = new DataOutputStream(socket.getOutputStream());
            salida.writeByte(0);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void CompruebaUsuario() {
        String respuesta = "0";
        String userEnc, passEnc;
        try {
            userEnc = Encriptador.encripta(ventana.tfUsuario.getText(), APPGestionAlmacen.CLAVE_SECRETA);
            passEnc = Encriptador.encripta(String.valueOf(ventana.tfContrasenya.getPassword()), APPGestionAlmacen.CLAVE_SECRETA);
            salida.writeUTF(userEnc); // Usuario
            salida.writeUTF(passEnc); // Contraseña
            
            entrada = new DataInputStream(socket.getInputStream());
            respuesta = entrada.readUTF();
        } catch (IOException e) {
            System.err.println(e);
        }
        // Comprobamos el usuario en la base de datos (comentado durante el desarrollo por comodidad)
        if (respuesta.equals("1")) {/*true/*con.compruebaUsuario(ventana.tfUsuario.getText(), String.valueOf(ventana.tfContrasenya.getPassword()))*/
            ventana.setVisible(false);
            APPGestionAlmacen.menuPrincipal();
            try {
                salida.close();
                entrada.close();
                socket.close();
            } catch (IOException e) {
                System.err.println(e);
            }
        } else {
            // Si fallamos mostramos un mensaje de error
            JOptionPane.showMessageDialog(ventana, "Usuario incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
            // Incrementamos el contador de intentos y si llega a 3 cerramos la aplicación
            Intentos++;
            if (Intentos == 3)
                System.exit(0);
        }
    }
}
