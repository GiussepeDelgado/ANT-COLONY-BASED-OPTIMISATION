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
public class coordinador {
    
    
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
            
            hormigas=colonia.crearColoniaDeHormigas();
            colonia.evaporacion();
            mejorActual=colonia.mejorSolucion(hormigas);
            colonia.buscarLocalMejor(mejorActual);
            
            if (i==0) {
                mejorGlobal=mejorActual;
            }else if (mejorActual.calidadDeSolucion>mejorGlobal.calidadDeSolucion) {
                mejorGlobal=mejorActual;
            }
            
            tUmbral=colonia.calcularTUmbral(mejorGlobal);
            peorActual=colonia.peorSolucion(hormigas);
            colonia.evaporarPeorSolucion(peorActual, mejorGlobal);
            colonia.mutacion(tUmbral,i,iterReinicializacion);
            
            if (i-iterReinicializacion>env.IterMax*0.2) {
                colonia.inicializarFeromonas();
                iterReinicializacion=i;
            }
            
            
            
        }
        Malla.guardarPlan(mejorGlobal.grafo);
    }
    
}
