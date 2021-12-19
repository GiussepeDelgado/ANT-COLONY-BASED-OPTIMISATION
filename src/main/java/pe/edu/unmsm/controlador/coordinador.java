/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.controlador;

import java.lang.reflect.Field;
import java.util.ArrayList;
import pe.edu.unmsm.modelo.datos.Clave;
import pe.edu.unmsm.modelo.datos.Malla;
import pe.edu.unmsm.modelo.datos.env;
import pe.edu.unmsm.modelo.och.Colonia;
import pe.edu.unmsm.modelo.och.Hormiga;

/**
 *
 * @author Windows 10 Pro
 */
public class Coordinador {

    
    public ArrayList<Clave> clavesMejor;
    public ArrayList<Clave> clavesPeor;
    public void comenzarGeneracion() {
        clavesMejor= new ArrayList<>();
        clavesPeor= new ArrayList<>();
        Colonia colonia = new Colonia();
        Hormiga[] hormigas;
        Hormiga mejorActual= new Hormiga();
        Hormiga peorActual= new Hormiga();
        Hormiga mejorGlobal = new Hormiga();
        Double tUmbral;
        int iterReinicializacion = 0;
        colonia.inicializarFeromonas();
        boolean hormigaEncontrada = false;
        Hormiga hormigaCon = new Hormiga();
        
        Clave clave;
        
        for (int i = 0; i < env.IterMax; i++) {
            
            
            if (!hormigaEncontrada) {
                hormigas = colonia.crearColoniaDeHormigas(hormigaCon);

                colonia.evaporacion();
                mejorActual = colonia.mejorSolucion(hormigas);
                colonia.buscarLocalMejor(mejorActual);

                if (i == 0) {
                    mejorGlobal = mejorActual;
                }
                if (mejorActual.calidadDeSolucion > mejorGlobal.calidadDeSolucion) {
                    mejorGlobal = mejorActual;
                }

                tUmbral = colonia.calcularTUmbral(mejorGlobal);
                peorActual = colonia.peorSolucion(hormigas);
                colonia.evaporarPeorSolucion(peorActual, mejorGlobal);
                colonia.mutacion(tUmbral, i, iterReinicializacion);

                if (i - iterReinicializacion > env.IterMax * 0.2) {
                    System.out.println("reinicio feromonas:" + i);
                    colonia.inicializarFeromonas();
                    iterReinicializacion = i;
                }
            }

            if (hormigaCon.asignaciones == env.numCursos) {
                mejorActual = hormigaCon;
                hormigaEncontrada = true;
                mejorGlobal = hormigaCon;
            }
            
            clave= new Clave(i,mejorActual.calidadDeSolucion);
            clavesMejor.add(clave);
            clave= new Clave(i,peorActual.calidadDeSolucion);
            clavesPeor.add(clave);
            System.out.print(i + "-> Actual: " + mejorActual.calidadDeSolucion);
            System.out.print(", Peor Actual: " + peorActual.calidadDeSolucion);
            System.out.println(", Global: " + mejorGlobal.calidadDeSolucion);

        }

        Malla.guardarPlan(mejorGlobal.grafo);
    }

}
