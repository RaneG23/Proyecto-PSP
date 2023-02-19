/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Pepe
 */
public class Encriptador {
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
            System.err.println("Ha ocurrido un error el encriptar: " + ex.getMessage());
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
            System.err.println("Ha ocurrido un error el desencriptar: " + ex.getMessage());
            return "0";
        }
    }
}
