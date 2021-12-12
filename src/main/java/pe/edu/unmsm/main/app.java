/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.main;

import java.lang.reflect.Field;
import java.util.ArrayList;
import pe.edu.unmsm.controlador.Coordinador;
import pe.edu.unmsm.modelo.datos.Curso;
import pe.edu.unmsm.modelo.datos.Malla;
import pe.edu.unmsm.modelo.datos.env;

/**
 *
 * @author Windows 10 Pro
 */
public class app {
    
    public static void main(String[] args) {
        
       /*
        ArrayList<Curso> cursosD = new ArrayList<>();
        Curso c1=new Curso("cas","matematica","asdas",1);
        Curso c2=new Curso("c","Programcion","as",4);
        Curso c3=new Curso("ca","Economia","asd",5);
        cursosD.add(c1);
        cursosD.add(c2);
        cursosD.add(c3);
        Malla.cargarDatos("src\\main\\java\\pe\\edu\\unmsm\\modelo\\datos\\curriculas\\Plan2014.txt",cursosD);
*/
       /*
       
        Malla.descargarDatos("src\\main\\java\\pe\\edu\\unmsm\\modelo\\datos\\curriculas\\Plan2014.txt");
        String preRequisitos="{";
        for (int i = 0; i < Malla.cursos.size(); i++) {
            
            for (int j = 0; j < Malla.cursos.get(i).numPrerequisito; j++) {
                
                if (j==Malla.cursos.get(i).numPrerequisito-1) {
                    preRequisitos+="\""+Malla.cursos.get(i).codPreReq.get(j)+"\"}";
                }else{
                    preRequisitos+="\""+Malla.cursos.get(i).codPreReq.get(j)+"\",";
                }
            }
            System.out.println("{\nID:\""+i+"\","+
                                "\nCodigo:\""+Malla.cursos.get(i).codigo+"\","+
                                "\nNombre:\""+Malla.cursos.get(i).nombre+"\","+
                                "\nPreRequisitos:"+preRequisitos+","+
                                "\nCreditos:"+Malla.cursos.get(i).creditos+"\n}");
            preRequisitos="{";
        }
        */
        Malla.descargarDatos("src\\main\\java\\pe\\edu\\unmsm\\modelo\\datos\\curriculas\\Plan2014.txt");
        Coordinador co=new Coordinador();
        co.comenzarGeneracion();
        mostrar();
    }
    
    
    public static void mostrar(){
        Curso curso;
        for (int i = 0; i < env.numPeriodos; i++) {
            int ciclo=i+1;
            int numero;
            System.out.println("____________CICLO "+ciclo+"________________");
            for (int j = 0; j < Malla.planOptimizado[i].cursos.size(); j++) {
                
                curso=Malla.planOptimizado[i].cursos.get(j);
                numero=j+1;
                System.out.println(numero+":"+curso.codigo+","+curso.nombre);
                
            }
        }
    }
    
    
    
    
    
}
