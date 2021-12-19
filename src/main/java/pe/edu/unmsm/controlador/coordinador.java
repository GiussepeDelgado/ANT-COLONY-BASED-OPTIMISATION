/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.controlador;

import java.lang.reflect.Field;
import pe.edu.unmsm.modelo.datos.Malla;
import pe.edu.unmsm.modelo.datos.env;
import pe.edu.unmsm.modelo.och.Colonia;
import pe.edu.unmsm.modelo.och.Hormiga;

/**
 *
 * @author Windows 10 Pro
 */
public class Coordinador {
    
    
    public void comenzarGeneracion(){
        Colonia colonia=new Colonia();
        Hormiga[] hormigas;
        Hormiga mejorActual;
        Hormiga peorActual;
        Hormiga mejorGlobal=new Hormiga();
        Double tUmbral;
        int iterReinicializacion=0;
        colonia.inicializarFeromonas();
        
        for (int i = 0; i < env.IterMax; i++) {
            
            hormigas=colonia.crearColoniaDeHormigas(i);
            colonia.evaporacion();
            mejorActual=colonia.mejorSolucion(hormigas);
            colonia.buscarLocalMejor(mejorActual);
            
            if (i==0) {
                mejorGlobal=mejorActual;
            }
            if (mejorActual.calidadDeSolucion>mejorGlobal.calidadDeSolucion) {
                mejorGlobal=mejorActual;
            }
             
            tUmbral=colonia.calcularTUmbral(mejorGlobal);
            peorActual=colonia.peorSolucion(hormigas);
            colonia.evaporarPeorSolucion(peorActual, mejorGlobal);
            colonia.mutacion(tUmbral,i,iterReinicializacion);
            
            if (i-iterReinicializacion>env.IterMax*0.2) {
                System.out.println("reinicio feromonas:"+i);
                colonia.inicializarFeromonas();
                iterReinicializacion=i;
            }
            
            
            System.out.println(i+":2Mejor Calidad de solucion:"+mejorGlobal.calidadDeSolucion);
            System.out.println("Mejor Actual Calidad de solucion:"+mejorActual.calidadDeSolucion);
            System.out.println("Peor Actual Calidad de solucion:"+peorActual.calidadDeSolucion);
            
        }
        
        Malla.guardarPlan(mejorGlobal.grafo);
    }
    
}
