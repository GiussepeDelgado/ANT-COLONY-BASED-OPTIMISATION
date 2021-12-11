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
public class Curso {
    
    
    public String codigo;
    public String nombre;
    public String codPreReq;
    public int creditos;

    public Curso() {
    }

    public Curso(String codigo, String nombre, String codPreReq, int creditos) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.codPreReq = codPreReq;
        this.creditos = creditos;
    }
    
    
}
