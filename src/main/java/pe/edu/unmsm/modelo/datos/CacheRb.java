/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.modelo.datos;

/**
 *
 * @author Windows 10 Pro
 */
public class CacheRb {
    
    //Almacena las restricciones blandas
    int minCursosPermitidos;
    int maxCursosPermitidos;
    int minCreditosPorPeriodo;
    int maxCreditosPorPeriodo;
    public int grado;
    
    public CacheRb() {
        this.minCursosPermitidos = 0;
        this.maxCursosPermitidos = 0;
        this.minCreditosPorPeriodo = 0;
        this.maxCreditosPorPeriodo = 0;
        this.grado =0;
        
    }
    
    
    
    public void evaluarRestricionesBlandas(){
        
        grado = minCursosPermitidos+maxCursosPermitidos+minCreditosPorPeriodo+maxCreditosPorPeriodo;
        
    }
    
}
