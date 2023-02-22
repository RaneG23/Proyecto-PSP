/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Control;

import Ventana.Login;
import Ventana.MenuPrincipal;

/**
 *
 * @author Pepe
 */
public class APPGestionAlmacen {

    private static Login login;
    private static MenuPrincipal menu;
    public static final String CLAVE_SECRETA = "clave_secreta_33";
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        login();
    }
    
    private static void login() {
       login = new Login();
       login.setVisible(true);
    }
    
    public static void menuPrincipal() {
        menu = new MenuPrincipal();
        menu.setVisible(true);
    }
    
}