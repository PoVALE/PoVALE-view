/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package es.ucm.povale.views;

import es.ucm.povale.entity.Entity;
import es.ucm.povale.environment.Environment;
import es.ucm.povale.variable.Var;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Daniel
 */
public class ZipExport {
    private Environment environment;
    private String zipPath;
    public ZipExport(Environment env, String path){
        this.environment = env;
        this.zipPath = path + "/prueba.zip";
    }
    
    public void export(){

        try{
            FileOutputStream f = new FileOutputStream(zipPath);
            ZipOutputStream zipFile = new ZipOutputStream(f);
            List<Var> list = this.environment.getVariables().stream().collect(Collectors.toList());
            
            for (int i = 0; i < list.size(); i++) {
                String name = list.get(i).getName();
                Entity e = environment.getValue(list.get(i).getName());
                e.writeToZip(zipFile, name);
            }
            
            zipFile.close();            
      
        }catch(IOException ex){
            ex.printStackTrace();
        }
    
    }
 
}
