/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import java.io.*;
import java.io.IOException;
import java.util.Properties;
import javax.swing.JOptionPane;

/**
 *
 * @author Pepe
 */
public class Propiedades {
    private String RutaBd;
    private String UserBd;
    private String PassBd;
    
    public Propiedades() throws IOException {
        File propiedades = new File("config.properties");  
        Properties prop = new Properties();
        
        if (!propiedades.isFile()){
            prop.setProperty("rutabd", "");
            prop.setProperty("userbd", "");
            prop.setProperty("passwordbd", "");
            prop.store(new BufferedWriter(new FileWriter("config.properties")), "Propiedades");
            
            JOptionPane.showMessageDialog(null, "Se ha creado el fichero 'config.properties'. "
                    + "Debe rellenarlo con los datos de la BD antes de iniciar la aplicación.", 
                    "Atención", JOptionPane.INFORMATION_MESSAGE);
            System.exit(0);
        } else {       
            prop.load(new FileReader("config.properties"));

            RutaBd = prop.getProperty("rutabd");
            UserBd = prop.getProperty("userbd");
            PassBd = prop.getProperty("passwordbd");
        }
    }
    
    public String getRutaBd() {
        return this.RutaBd;
    }
    
    public String getUserBd() {
        return this.UserBd;
    }
    
    public String getPassBd() {
        return this.PassBd;
    }
}
