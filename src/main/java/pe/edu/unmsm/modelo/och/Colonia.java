/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.modelo.och;

import java.util.ArrayList;
import pe.edu.unmsm.modelo.datos.Malla;
import pe.edu.unmsm.modelo.datos.env;

/**
 *
 * @author Windows 10 Pro
 */
public class Colonia {

    Double calidadInicial;

    public Double[][] feromonas;
    Double tasaEvaporacion;

    public Colonia() {

        calidadInicial = 0.1;
        feromonas = new Double[env.numCursos][env.numPeriodos];
        tasaEvaporacion = 0.20;
    }

    public void inicializarFeromonas() {

        for (int i = 0; i < env.numCursos; i++) {
            for (int j = 0; j < env.numPeriodos; j++) {
                feromonas[i][j] = calidadInicial;
            }
        }
    }

    public Hormiga[] crearColoniaDeHormigas(Hormiga hormigaCond) {
        Hormiga[] hormigas = new Hormiga[env.numHormigas];
        int periodo;
        boolean alert = false;
        int cont = 0;
        for (int k = 0; k < env.numHormigas; k++) {
            Hormiga hormiga = new Hormiga();

            for (int i = 0; i < env.numCursos; i++) {
                periodo = reglaDeTransicion(i, hormiga.grafo);
                if (periodo < 10) {
                    hormiga.asignarCursoPeriodo(i, periodo);
                    cont++;
                } else {

                    alert = true;
                    break;
                }

            }

            hormiga.asignaCalidadDeSolucion();
            if (hormiga.asignaciones==env.numCursos&&hormiga.calidadDeSolucion==Double.POSITIVE_INFINITY) {
                hormigaCond.grafo=hormiga.grafo;
                hormigaCond.calidadDeSolucion=hormiga.calidadDeSolucion;
                hormigaCond.asignaciones=hormiga.asignaciones;
                hormigaCond.cache=hormiga.cache; 
                
                
            }
            
            depositarFeromonas(hormiga.calidadDeSolucion, hormiga.grafo);
            hormigas[k] = hormiga;
            /*
            System.out.println("**********************************************************************");//borrar
            System.out.println("");
            System.out.println("Hormiga: "+k);//borrar
            System.out.println("calidad: "+hormiga.calidadDeSolucion);//borrar*/
           // Malla.guardarPlanTest(hormiga.grafo);//borrar
            //System.out.println("");
            
        }
        return hormigas;
    }
    int factor = 56;

    public int reglaDeTransicion(int curso, int[][] grafo) {

        Double probabilidad = 0.0;
        Double valorAle;
        ArrayList<Double> ruleta = new ArrayList<>();
        int periodoCurso = Malla.buscarPeriodoRt(curso, grafo);
        /*
        for (int j = periodoCurso + 1; j < periodoCurso + 2; j++) {
            probabilidad += calcularProbabilidad(curso, j, grafo);
            
            ruleta.add(probabilidad);
        }*/
        int k = periodoCurso + 1;
        //bor
        while (k < env.numPeriodos) {
            probabilidad += calcularProbabilidad(curso, k, grafo);

            ruleta.add(probabilidad);
            k++;
        }
        /*Emer
        if (iter < factor) {
           
              if (0 <= iter && iter <= factor* 0.2) {
                //System.out.println("muy bajo");
                while (k < env.numPeriodos) {
                    probabilidad += calcularProbabilidad(curso, k, grafo);

                    ruleta.add(probabilidad);
                    k++;
                }
            }
            if (factor * 0.2 < iter && iter <= factor * 0.4) {
                //System.out.println(" bajo");
                while (k < periodoCurso + 4 && k < env.numPeriodos) {
                    probabilidad += calcularProbabilidad(curso, k, grafo);

                    ruleta.add(probabilidad);
                    k++;
                }
            }
            if (factor * 0.4 < iter && iter <= factor * 0.6) {
                //System.out.println("bueno");
                while (k < periodoCurso + 3 && k < env.numPeriodos) {
                    probabilidad += calcularProbabilidad(curso, k, grafo);

                    ruleta.add(probabilidad);
                    k++;
                }
            }
            if (factor * 0.6 < iter && iter <= factor * 0.8) {
                //System.out.println("excelente");
                while (k < periodoCurso + 2 && k < env.numPeriodos) {
                    probabilidad += calcularProbabilidad(curso, k, grafo);

                    ruleta.add(probabilidad);
                    k++;
                }

            }
            valorAle = Math.random() * probabilidad;

            for (int i = 0; i < ruleta.size(); i++) {
                if (valorAle <= ruleta.get(i)) {
                    return (i + periodoCurso + 1);
                }
            }
            
        }
         */
        valorAle = Math.random() * probabilidad;

            for (int i = 0; i < ruleta.size(); i++) {
                if (valorAle <= ruleta.get(i)) {
                    return (i + periodoCurso + 1);
                }
            }
        return periodoCurso + 1;

    }

    public void depositarFeromonas(Double calidadSolucion, int[][] grafo) {
        for (int i = 0; i < env.numCursos; i++) {
            for (int j = 0; j < env.numPeriodos; j++) {
                if (grafo[i][j] == 1) {
                    feromonas[i][j] += calidadSolucion;
                }
            }
        }
    }

    public Double calcularProbabilidad(int curso, int periodo, int[][] grafo) {
        /*
        System.out.println("numerador:"+(Math.pow(feromonas[curso][periodo], 1)
                * Math.pow(heuristica(curso, periodo, grafo), 1)));
        System.out.println("Denominador:"+subSumaHeu(curso, periodo, grafo));
        
        return (Math.pow(feromonas[curso][periodo], Malla.cursos.get(curso).creditos)
                * Math.pow(heuristica(curso, periodo, grafo), env.minCreditosPorPeriodo))
                / (subSumaHeu(curso, periodo, grafo));
         
        */
        
        return (Math.pow(feromonas[curso][periodo], 1.0)
                * Math.pow(heuristica(curso, periodo, grafo), 1.0))
                / (subSumaHeu(curso, periodo, grafo));

    }

    public Double subSumaHeu(int curso, int periodo, int[][] grafo) {
        Double sum = 0.0;
        for (int j = 0; j < env.numPeriodos; j++) {
            sum += Math.pow(feromonas[curso][j], Malla.cursos.get(curso).creditos)
                    * Math.pow(heuristica(curso, j, grafo), env.minCreditosPorPeriodo);
        }
        return sum;
    }

    public Double heuristica(int curso, int periodo, int[][] grafo) {

        int antes, despues;

        antes = v(periodo, grafo);
        grafo[curso][periodo] = 1;
        despues = v(periodo, grafo);
        grafo[curso][periodo] = 0;

        return 1.0 / ((1 + despues - antes) * 1.0);
    }

    public int v(int periodo, int[][] grafo) {
        int suma = 0;
        int sumCursoPeriodo = Malla.obetenerSumCursosPorPeriodo(grafo)[periodo];
        int sumCreditosPeriodo = Malla.obetenerSumCreditosPorPeriodo(grafo)[periodo];
        if (sumCursoPeriodo > env.maxCursosPermitidos || sumCursoPeriodo < env.minCursosPermitidos) {
            suma++;
        }
        if (sumCreditosPeriodo > env.maxCreditosPorPeriodo || sumCreditosPeriodo < env.minCreditosPorPeriodo) {
            suma++;
        }
        return suma;
    }

    //____________________________
    public void evaporacion() {
        for (int i = 0; i < env.numCursos; i++) {
            for (int j = 0; j < env.numPeriodos; j++) {
                feromonas[i][j] *= 1 - tasaEvaporacion;
            }
        }
    }

    public void evaporarPeorSolucion(Hormiga peorHormiga, Hormiga mejorGlogal) {
        for (int i = 0; i < env.numCursos; i++) {
            for (int j = 0; j < env.numPeriodos; j++) {
                if (peorHormiga.grafo[i][j] == 1 && mejorGlogal.grafo[i][j] != 1) {
                    feromonas[i][j] *= 1 - tasaEvaporacion;
                }
            }
        }
    }

    public Hormiga mejorSolucion(Hormiga[] hormigas) {
        Double calidadSolucion = 0.0;
        int mejor = 0;

        for (int k = 0; k < env.numHormigas; k++) {
            if (hormigas[k].calidadDeSolucion > calidadSolucion) {
                calidadSolucion = hormigas[k].calidadDeSolucion;
                mejor = k;
            }
        }
        return hormigas[mejor];
    }

    public Hormiga peorSolucion(Hormiga[] hormigas) {
        Double calidadSolucion = hormigas[1].calidadDeSolucion;
        int peor = 0;

        for (int k = 1; k < env.numHormigas; k++) {
            if (hormigas[k].calidadDeSolucion < calidadSolucion) {
                calidadSolucion = hormigas[k].calidadDeSolucion;
                peor = k;
            }
        }
        return hormigas[peor];
    }

    public void buscarLocalMejor(Hormiga mejorHormiga) {
        Hormiga hormiga = new Hormiga(
                mejorHormiga.grafo,
                mejorHormiga.calidadDeSolucion,
                mejorHormiga.asignaciones,
                mejorHormiga.cache);

        int periodoMayor = Malla.seleccionarPeriodoMasCreditos(hormiga.grafo);
        int periodoMenor = Malla.seleccionarPeriodoMenosCreditos(hormiga.grafo);
        ArrayList<Integer> cursosDelPeriodo = Malla.buscarCursosPorPeriodo(periodoMayor, hormiga.grafo);
        int curso = Malla.seleccionarCurso(cursosDelPeriodo, hormiga.grafo, periodoMenor);
        if (curso != -1) {
            hormiga.grafo[curso][periodoMayor] = 0;
            hormiga.asignarCursoPeriodo(curso, periodoMenor);
            hormiga.asignaCalidadDeSolucion();
            if (hormiga.calidadDeSolucion > mejorHormiga.calidadDeSolucion) {
                mejorHormiga = hormiga;
            }
        }

    }

    public Double calcularTUmbral(Hormiga mejorGlobal) {
        return depositarMejorSolucion(mejorGlobal) / (env.numCursos * 1.0);
    }

    private Double depositarMejorSolucion(Hormiga mejorGlobal) {
        Double suma = 0.0;
        for (int i = 0; i < env.numCursos; i++) {
            for (int j = 0; j < env.numPeriodos; j++) {
                if (mejorGlobal.grafo[i][j] == 1) {
                    feromonas[i][j] += tasaEvaporacion * mejorGlobal.calidadDeSolucion;
                    suma += feromonas[i][j];
                }
            }
        }
        return suma;
    }

    public void mutacion(Double tUmbral, int iterActual, int iterReiniziacion) {

        if (iterReiniziacion != 0) {
            Double mut = calcularCoefMutacion(tUmbral, iterActual, iterReiniziacion);
            int j;
            Double a;
            for (int i = 0; i < env.numCursos; i++) {
                if (Math.random() < 0.3) {
                    j = (int) (Math.random() * env.numPeriodos);
                    a = Math.random();
                    if (a == 0) {
                        feromonas[i][j] += mut;
                    } else {
                        feromonas[i][j] -= mut;
                    }
                }
            }
        }

    }

    public Double calcularCoefMutacion(Double tUmbral, int iterActual, int iterReiniziacion) {
        return (iterActual - iterReiniziacion) / (env.IterMax - iterReiniziacion) * 0.5 * tUmbral;
    }

}
