/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package appgestionalmacenserver;

import BD.Conexion;
import java.net.*;
import java.io.*;
import java.sql.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Pepe
 */
public class AppGestionAlmacenServer {

    /**
     * @param args the command line arguments
     */
    static Conexion con;
    public static final String CLAVE_SECRETA = "clave_secreta_33";
    
    public static void main(String[] args) {
        try {
            ServerSocket servicio = null;
            Socket socketservicio = null;
            // Cargamos el fichero de propiedades
            Propiedades prop = new Propiedades();
            
            String ruta = prop.getRutaBd();
            String user = prop.getUserBd();
            String pass = prop.getPassBd();
            
            con = new Conexion();
            con.conectaBD(ruta,user,pass);
            
            
            servicio = new ServerSocket(9999);
            
            while (true) {
                socketservicio = servicio.accept();
                System.out.println("Cliente conectado");
                DataInputStream entrada = new DataInputStream(socketservicio.getInputStream());
                // Usamos un byte para ver a qué hilo mandar la llamad
                switch (entrada.readByte()){
                    case 0: 
                        new Thread(new HiloLogin(socketservicio)).start();
                        break;
                    case 1: 
                        new Thread(new HiloStock(socketservicio)).start();
                        break;
                }
                
            }
        } catch (IOException ex) {
            System.err.println("Ha ocurrido un error con el socket: ");
            ex.printStackTrace();
        }
    }
}

class HiloStock implements Runnable {
    private Socket socket;
    private DefaultTableModel modelo = new DefaultTableModel();
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    
    public HiloStock(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try { 
            entrada = new ObjectInputStream(socket.getInputStream());
            salida = new ObjectOutputStream(socket.getOutputStream());
            
            String sqlDesc = Encriptador.desencripta((String)entrada.readObject(), AppGestionAlmacenServer.CLAVE_SECRETA);
            
            ResultSet rs = AppGestionAlmacenServer.con.dameResultado(sqlDesc);
            modelo.setColumnIdentifiers(new String[]{"Almacén","Stock"});
            while (rs.next()) {
                String almacen = rs.getString("Almacen");
                String stock = rs.getString("Stock");
                modelo.addRow(new Object[]{almacen, stock});
            }
            salida.writeObject(modelo);
        } catch(IOException | ClassNotFoundException | SQLException e){
            System.err.println(e);
        } finally {
            try { 
                entrada.close();
                salida.close();
            } catch(IOException e){
                System.err.println(e);
            }   
        }
    }
}

class HiloLogin implements Runnable{
    private String user, pass;
    private String userDesc, passDesc;
    private Socket socket;
    private DataOutputStream salida;
    private DataInputStream entrada;
    
    public HiloLogin(Socket socket){
        this.socket = socket;
    }
    
    @Override
    public void run() {
        try {
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());

            for (int i = 0; i<3; i++){
                user = entrada.readUTF();
                pass = entrada.readUTF();
                userDesc = Encriptador.desencripta(user, AppGestionAlmacenServer.CLAVE_SECRETA);
                passDesc = Encriptador.desencripta(pass, AppGestionAlmacenServer.CLAVE_SECRETA);

                if (AppGestionAlmacenServer.con.compruebaUsuario(userDesc, passDesc)){
                    salida.writeUTF("1");
                    break;
                } else {
                    salida.writeUTF("0");
                }
            }
        } catch (IOException ex) {
            System.err.println("Error al contactar con el cliente");
        }
    } 
}

class Encriptador {
    public static String encripta(String texto, String clave) {
        byte[] mensajeBytes = texto.getBytes(StandardCharsets.UTF_8);
        byte[] claveBytes = clave.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec claveSecreta = new SecretKeySpec(claveBytes, "AES");
        Cipher cifrador = null;
        try {
            cifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cifrador.init(Cipher.ENCRYPT_MODE, claveSecreta);
            byte[] mensajeCifrado = cifrador.doFinal(mensajeBytes);

            return Base64.getEncoder().encodeToString(mensajeCifrado);
        } catch (Exception ex) {
            System.err.println("Ha ocurrido un error el encriptar");
            return "0";
        }
    }
    
    public static String desencripta(String texto, String clave) {
        byte[] mensajeCifrado = Base64.getDecoder().decode(texto);
        byte[] claveBytes = clave.getBytes(StandardCharsets.UTF_8);

        SecretKeySpec claveSecreta = new SecretKeySpec(claveBytes, "AES");

        try {
            Cipher descifrador = Cipher.getInstance("AES/ECB/PKCS5Padding");
            descifrador.init(Cipher.DECRYPT_MODE, claveSecreta);

            byte[] mensajeDescifrado = descifrador.doFinal(mensajeCifrado);

            return new String(mensajeDescifrado, StandardCharsets.UTF_8);
        } catch (Exception ex) {
            System.err.println("Ha ocurrido un error el desencriptar");
            return "0";
        }
    }
}
