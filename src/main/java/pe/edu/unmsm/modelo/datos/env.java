/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.modelo.datos;

import java.lang.reflect.Field;

/**
 *
 * @author Windows 10 Pro
 */
public class env {
    public static int IterMax=10;
    public static int numCursos=66;
    public static int numPeriodos=10;
    public static int numHormigas=8;
    public static int minCursosPermitidos=4;
    public static int maxCursosPermitidos=7;
    public static int minCreditosPorPeriodo=11;
    public static int maxCreditosPorPeriodo=24;
    

    public static void inicializarVariables(int numCursos,
                                    int numPeriodos,
                                    int numHormigas,
                                    int minCursosPermitidos,
                                    int maxCursosPermitidos,
                                    int minCreditosPorPeriodo,
                                    int maxCreditosPorPeriodo,
                                    int IterMax){
        
        cambiaValor(numCursos, "numCursos");
        cambiaValor(numPeriodos, "numPeriodos");
        cambiaValor(numHormigas, "numHormigas");
        cambiaValor(minCursosPermitidos, "minCursosPermitidos");
        cambiaValor(maxCursosPermitidos, "maxCursosPermitidos");
        cambiaValor(minCreditosPorPeriodo, "minCreditosPorPeriodo");
        cambiaValor(maxCreditosPorPeriodo, "maxCreditosPorPeriodo");
        cambiaValor(IterMax, "IterMax");
    }
    
    
    public static void cambiaValor(int nuevoValor,String var) {
        try {
            Field field = env.class.getDeclaredField(var);
            field.setAccessible(true);
            field.setInt(null, nuevoValor);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            System.out.println("No se pudo cambiar el valor :(");
            e.printStackTrace(System.out);
            
        }
    }
}
