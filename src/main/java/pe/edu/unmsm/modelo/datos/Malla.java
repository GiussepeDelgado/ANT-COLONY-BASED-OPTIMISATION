/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.modelo.datos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.StringTokenizer;


/**
 *
 * @author Windows 10 Pro
 */
public class Malla {

    public static ArrayList<Curso> cursos = new ArrayList<>();
    public static Periodo[] planOptimizado=new Periodo[env.numPeriodos];
    
    

    public static void evaluarReglas(CacheRb cache, int[][] grafo, int periodo) {
        int[] sumaCreditos = obetenerSumCreditosPorPeriodo(grafo);
        int[] sumaCursos = obetenerSumCursosPorPeriodo(grafo);

        if (sumaCreditos[periodo] > env.maxCreditosPorPeriodo) {
            cache.maxCreditosPorPeriodo = 1;
        }
        if (sumaCreditos[periodo] < env.minCreditosPorPeriodo) {
            cache.minCreditosPorPeriodo = 1;
        }
        if (sumaCursos[periodo] > env.maxCursosPermitidos) {
            cache.maxCursosPermitidos = 1;
        }
        if (sumaCursos[periodo] < env.minCursosPermitidos) {
            cache.minCreditosPorPeriodo = 1;
        }

        cache.evaluarRestricionesBlandas();

    }

    public static Double max(int[][] grafo) {

        return hallarMax(obetenerSumCreditosPorPeriodo(grafo));
    }

    public static int[] obetenerSumCreditosPorPeriodo(int[][] grafo) {

        int[] sumaCreditosPorPeriodo = new int[env.numPeriodos];
        for (int i = 0; i < env.numCursos; i++) {
            for (int j = 0; j < env.numPeriodos; j++) {
                if (grafo[i][j] == 1) {
                    sumaCreditosPorPeriodo[j] += cursos.get(i).creditos;
                }
            }
        }
        return sumaCreditosPorPeriodo;
    }

    public static int[] obetenerSumCursosPorPeriodo(int[][] grafo) {

        int[] sumaCursosPorPeriodo = new int[env.numPeriodos];
        for (int i = 0; i < env.numCursos; i++) {
            for (int j = 0; j < env.numPeriodos; j++) {

                sumaCursosPorPeriodo[j] += grafo[i][j];

            }
        }
        return sumaCursosPorPeriodo;
    }

    public static Double hallarMax(int[] data) {

        int maxValue = 0;

        for (int i = 0; i < data.length; i++) {

            if (data[i] > maxValue) {
                maxValue = data[i];
            }

        }

        return (maxValue * 1.0);

    }

    public static int seleccionarPeriodoMasCreditos(int[][] grafo) {
        int[] sumaCreditosPorPeriodo = obetenerSumCreditosPorPeriodo(grafo);
        int maxPeriodo = 0;
        for (int i = 0; i < sumaCreditosPorPeriodo.length; i++) {
            if (sumaCreditosPorPeriodo[i] > maxPeriodo) {
                maxPeriodo = sumaCreditosPorPeriodo[i];
            }
        }

        return maxPeriodo;
    }

    public static int seleccionarPeriodoMenosCreditos(int[][] grafo) {
        int[] sumaCreditosPorPeriodo = obetenerSumCreditosPorPeriodo(grafo);
        int minPeriodo = sumaCreditosPorPeriodo[0];

        for (int i = 1; i < sumaCreditosPorPeriodo.length; i++) {
            if (sumaCreditosPorPeriodo[i] < minPeriodo) {
                minPeriodo = sumaCreditosPorPeriodo[i];
            }
        }

        return minPeriodo;
    }

    public static ArrayList<Integer> buscarCursosPorPeriodo(int periodo, int[][] grafo) {

        ArrayList<Integer> cursosDelPeriodo = new ArrayList<>();

        for (int i = 0; i < env.numCursos; i++) {
            if (grafo[i][periodo] == 1) {
                cursosDelPeriodo.add(i);

            }
        }
        return cursosDelPeriodo;
    }

    public static int buscarCurso(String codigo) {

        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).codigo.equalsIgnoreCase(codigo)) {
                return i;
            }
        }
        System.out.println("Curso no encontrado");
        return -1;
    }

    public static int buscarPosCurso(String codigo) {

        for (int i = 0; i < cursos.size(); i++) {
            if (cursos.get(i).codPreReq.equalsIgnoreCase(codigo)) {
                return i;
            }
        }
        System.out.println("Post curso no encontrado");
        return -1;
    }

    public static int buscarPeriodo(int curso, int[][] grafo) {

        for (int j = 0; j < env.numPeriodos; j++) {
            if (grafo[curso][j] == 1) {
                return j;
            }
        }
        System.out.println("Periodo no encontrado");
        return -1;
    }

    public static int seleccionarCurso(ArrayList<Integer> cursosP, int[][] grafo, int periodoMenor) {

        int preCurso;
        int postCurso;
        int prePeriodo;
        int postPeriodo;
        int curso;

        for (int k = 0; k < cursosP.size(); k++) {
            curso = cursosP.get(k);
            preCurso = buscarCurso(cursos.get(curso).codPreReq);
            postCurso = buscarPosCurso(cursos.get(curso).codigo);
            if (preCurso == -1 && postCurso != -1) {
                postPeriodo = buscarPeriodo(postCurso, grafo);
                if (periodoMenor < postPeriodo) {
                    return curso;
                }
            }
            if (preCurso != -1 && postCurso != -1) {
                prePeriodo = buscarPeriodo(preCurso, grafo);
                postPeriodo = buscarPeriodo(postCurso, grafo);
                if (prePeriodo < periodoMenor && periodoMenor < postPeriodo) {
                    return curso;
                }
            }
            if (preCurso != -1 && postCurso == -1) {
                prePeriodo = buscarPeriodo(preCurso, grafo);
                if (prePeriodo < periodoMenor) {
                    return curso;
                }
            }

        }
        return -1;
    }
    
    
    public static void cargarDatos(String path,ArrayList<Curso> cursosNuevos) {
        FileWriter fw;
        PrintWriter pw;
        try {
            fw = new FileWriter(path);
            pw = new PrintWriter(fw);
            Curso curso;
            for(int i = 0; i < cursosNuevos.size(); i++){
                curso = cursosNuevos.get(i);
                
                pw.println(String.valueOf(curso.codigo+
                        ","+curso.nombre+
                        ","+curso.codPreReq+
                        ","+curso.creditos));
            }
             pw.close();

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    
    public static void descargarDatos(String path) {
        File ruta = new File(path);
        try {
            FileReader fi = new FileReader(ruta);
            try (BufferedReader bu = new BufferedReader(fi)) {
                String linea;
                ArrayList<Curso> cursosDescargados = new ArrayList<>();
                Curso curso;
                while ((linea = bu.readLine()) != null) {
                    StringTokenizer st = new StringTokenizer(linea, ",");
                    String codigo = st.nextToken();
                    String nombre = st.nextToken();
                    String codPreReq = st.nextToken();
                    int creaditos = Integer.parseInt(st.nextToken());
                    curso = new Curso(codigo, nombre, codPreReq, creaditos);
                    cursosDescargados.add(curso);

                }
                cargarCursos(cursosDescargados);
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al descargar datos: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static void cargarCursos(ArrayList<Curso> cursosD) {
        try {
            Field field = Malla.class.getDeclaredField("cursos");
            field.setAccessible(true);

            field.set(cursos, cursosD);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            System.out.println("No se pudo cambiar los Cursos:(");
            e.printStackTrace(System.out);

        }
    }
    
    public static void cargarPlan(Periodo[] plan){
        try {
            Field field = Malla.class.getDeclaredField("planOptimizado");
            field.setAccessible(true);

            field.set(planOptimizado, plan);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            System.out.println("No se pudo cambiar el Plan :(");
            e.printStackTrace(System.out);

        }
    }
    public static void guardarPlan(int[][] mejorGrafo){
        Periodo[] planOpt=new Periodo[env.numPeriodos];
        Curso curso;
        for (int i = 0; i < env.numCursos; i++) {
            for (int j = 0; j < env.numPeriodos; j++) {
                if (mejorGrafo[i][j]==1) {
                    curso=new Curso(cursos.get(i).codigo,
                            cursos.get(i).nombre, 
                            cursos.get(i).codPreReq, 
                            cursos.get(i).creditos);
                    planOpt[j].cursos.add(curso);
                }
            }
        }
        cargarPlan(planOpt);
    }
}
