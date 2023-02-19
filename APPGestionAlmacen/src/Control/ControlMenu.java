/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Ventana.MenuPrincipal;
import BD.*;
/**
 *
 * @author Pepe
 */
public class ControlMenu {
    private int Intentos = 0;
    private static MenuPrincipal ventana;
    private static Conexion con;
    
    public ControlMenu(MenuPrincipal ventana) {
        this.ventana = ventana;
       // con = APPGestionAlmacen.con;
    }
}
