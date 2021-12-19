/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.modelo.och;


import java.util.ArrayList;
import pe.edu.unmsm.modelo.datos.*;

/**
 *
 * @author Windows 10 Pro
 */
public class Hormiga {

    public int[][] grafo;
    public Double calidadDeSolucion;
    public int asignaciones;
    ArrayList<CacheRb> cache;
    public Hormiga() {
        
        grafo = new int[env.numCursos][env.numPeriodos];

        for (int i = 0; i < env.numCursos; i++) {
            for (int j = 0; j < env.numPeriodos; j++) {
                grafo[i][j] = 0;
            }
        }
        this.calidadDeSolucion = 0.0;
        this.asignaciones=0;
        
        inicializarCache();
    }

    public Hormiga(int[][] grafo, Double calidadDeSolucion, int asignaciones, ArrayList<CacheRb> cache) {
        this.grafo = grafo;
        this.calidadDeSolucion = calidadDeSolucion;
        this.asignaciones = asignaciones;
        this.cache = cache;
    }
    
       
    private void inicializarCache(){
        cache= new ArrayList<>();
        CacheRb r0=new CacheRb();
        cache.add(r0);
    }

    public void asignarCursoPeriodo(int curso, int periodo) {
        
        grafo[curso][periodo] = 1;
        
       
        /*
        for (int i = 0; i < env.numCursos; i++) {
            for (int j = 0; j < env.numPeriodos; j++) {
                System.out.print(""+grafo[i][j]+" ");
            }
            System.out.println("");
        }
*/
        CacheRb reglasBlandas=new CacheRb();
        Malla.evaluarReglas(reglasBlandas, grafo, periodo);
        cache.add(reglasBlandas);
        asignaciones++;
    }

    
    
    public void asignaCalidadDeSolucion() {
        
        
        calidadDeSolucion=0.02*asignaciones/(1.0*Malla.evaluarGrafo(grafo));//bor
        //Emer calidadDeSolucion = costo()-0.5*Malla.max(grafo);
    }
    public void asignaCalidadDeSolucionErr(int iter) {
        calidadDeSolucion = 1.0 / (Malla.max(grafo)+8*costo())-1/(10*iter);
    }
    public Double costo(){
        int suma=0;
        
        for (int i = 0; i < cache.size(); i++) {
            suma+=cache.get(i).grado;
        }
        
        return suma*1.0;
    }

    

}
