/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.povale.views;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author PoVALE Team
 */
public class NewClass {
    
    public static void main(String[] args) {
        Pattern patron = Pattern.compile("\\$\\{(\\w+)\\}");
        Matcher m = patron.matcher("Aqui pongo una cadena ${x} de ejemplo ${pepe} y vale ${a34}");
        while (m.find()) {
            System.out.println("Coincidencia desde " + m.start() + " hasta " + m.end() + ": " + m.group(1));
        }
    }
}


