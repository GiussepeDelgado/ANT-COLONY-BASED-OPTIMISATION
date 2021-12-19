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
    public static Periodo[] planOptimizado = new Periodo[env.numPeriodos];

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

    public static int hallarMaxInt(int[] data) {

        int maxValue = 0;

        for (int i = 0; i < data.length; i++) {

            if (data[i] > maxValue) {
                maxValue = data[i];
            }

        }

        return (maxValue);

    }

    public static int seleccionarPeriodoMasCreditos(int[][] grafo) {
        int[] sumaCreditosPorPeriodo = obetenerSumCreditosPorPeriodo(grafo);
        int maxPeriodo = 0;
        int valorMax = 0;
        for (int i = 0; i < sumaCreditosPorPeriodo.length; i++) {
            if (sumaCreditosPorPeriodo[i] > valorMax) {
                valorMax = sumaCreditosPorPeriodo[i];
                maxPeriodo = i;
            }
        }

        return maxPeriodo;
    }

    public static int seleccionarPeriodoMenosCreditos(int[][] grafo) {
        int[] sumaCreditosPorPeriodo = obetenerSumCreditosPorPeriodo(grafo);
        int minPeriodo = 0;
        int valorMin = 1000;
        for (int i = 1; i < sumaCreditosPorPeriodo.length; i++) {
            if (sumaCreditosPorPeriodo[i] < valorMin) {
                valorMin = sumaCreditosPorPeriodo[i];
                minPeriodo = i;
            }
        }

        return minPeriodo;
    }

    public static ArrayList<Integer> buscarCursosPorPeriodo(int periodo, int[][] grafo) {
        //System.out.println("PERIODOMAYOR:" + periodo);
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
        //System.out.println("Curso no encontrado");
        return -1;
    }

    public static int buscarPostCurso(String codigo, int[][] grafo) {

        ArrayList<Integer> postCursos = new ArrayList<>();
        ArrayList<Integer> postPeriodos = new ArrayList<>();
        for (int i = 0; i < cursos.size(); i++) {

            for (int j = 0; j < cursos.get(i).codPreReq.size(); j++) {
                if (cursos.get(i).codPreReq.get(j).equalsIgnoreCase(codigo)) {
                    postCursos.add(i);
                    for (int k = 0; k < env.numPeriodos; k++) {
                        if (grafo[i][k] == 1) {
                            postPeriodos.add(k);
                        }
                    }
                }
            }

        }
        int minPeriodos = 1000;
        int minCurso = 1000;
        if (!(postCursos.isEmpty() || postPeriodos.isEmpty())) {
            //System.out.println("afuera del for");
            for (int i = 0; i < postPeriodos.size(); i++) {
                //System.out.println("adentro del for");
                if (postPeriodos.get(i) < minPeriodos) {
                    //System.out.println("entro al if");
                    minPeriodos = postPeriodos.get(i);
                    minCurso = postCursos.get(i);
                } else {
                    //System.out.println("no entro al if");
                }
            }
            return minCurso;

        }
        //System.out.println("Post curso no encontrado");
        return -1;
    }

    public static int buscarPeriodo(int curso, int[][] grafo) {
        //System.out.println("Buscar periodo Curso:" + curso);
        for (int j = 0; j < env.numPeriodos; j++) {
            if (grafo[curso][j] == 1) {
                return j;
            }
        }
        //System.out.println("Periodo no encontrado");
        return -1;
    }

    public static int periodoMayor(int curso, int[][] grafo) {
        String preRequisito;
        int cursoPreRequisito;
        int k = 0;
        int[] periodo = new int[cursos.get(curso).numPrerequisito];
        for (int i = 0; i < cursos.get(curso).numPrerequisito; i++) {
            preRequisito = cursos.get(curso).codPreReq.get(i);
            cursoPreRequisito = buscarCurso(preRequisito);
            if (!preRequisito.equalsIgnoreCase("nn")) {
                for (int j = 0; j < env.numPeriodos; j++) {
                    if (grafo[cursoPreRequisito][j] == 1) {
                        periodo[k] = j;
                        k++;
                        break;
                    }
                }
            } else {
                return -1;
            }
        }

        return Malla.hallarMaxInt(periodo);
    }

    public static int buscarPeriodoRt(int curso, int[][] grafo) {
        //evalua las restricciones

        int periodo = periodoMayor(curso, grafo);

        int[] sumCursos = obetenerSumCursosPorPeriodo(grafo);
        int[] sumCreditos = obetenerSumCreditosPorPeriodo(grafo);
        int periodoTemp = periodo + 1;
        for (int j = periodoTemp; j < env.numPeriodos; j++) {
            if (sumCursos[j] >= env.maxCursosPermitidos || sumCreditos[j] >= env.maxCreditosPorPeriodo) {
                periodo++;
            }
        }

        return periodo;
    }

    public static int buscarPreCursoMayor(int curso, int[][] grafo) {

        String preRequisito = "nn";
        int cursoPreRequisito;
        int k = 0;
        int[] periodo = new int[cursos.get(curso).numPrerequisito];
        int[] preCursos = new int[cursos.get(curso).numPrerequisito];
        for (int i = 0; i < cursos.get(curso).numPrerequisito; i++) {
            preRequisito = cursos.get(curso).codPreReq.get(i);
            cursoPreRequisito = buscarCurso(preRequisito);
            if (!preRequisito.equalsIgnoreCase("nn")) {
                for (int j = 0; j < env.numPeriodos; j++) {
                    if (grafo[cursoPreRequisito][j] == 1) {
                        periodo[k] = j;
                        preCursos[k] = cursoPreRequisito;
                        k++;
                        break;
                    }
                }
            }
        }
        if (!preRequisito.equalsIgnoreCase("nn")) {
            int periodoMayor = Malla.hallarMaxInt(periodo);
            for (int i = 0; i < cursos.get(curso).numPrerequisito; i++) {
                if (periodo[i] == periodoMayor) {
                    return preCursos[i];
                }
            }
        }

        //return Malla.hallarMaxInt(periodo);
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
            preCurso = buscarPreCursoMayor(curso, grafo);/*cursos.get(curso).codPreReq*/
            postCurso = buscarPostCurso(cursos.get(curso).codigo, grafo);
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

    public static void cargarDatos(String path, ArrayList<Curso> cursosNuevos) {
        FileWriter fw;
        PrintWriter pw;
        try {
            fw = new FileWriter(path);
            pw = new PrintWriter(fw);
            Curso curso;
            String tupla;
            for (int i = 0; i < cursosNuevos.size(); i++) {
                curso = cursosNuevos.get(i);
                tupla = curso.codigo + "," + curso.nombre + "," + curso.numPrerequisito;
                for (int j = 0; j < curso.numPrerequisito; j++) {
                    tupla += "," + curso.codPreReq.get(j);
                }
                tupla += "," + curso.creditos;

                pw.println(String.valueOf(tupla));

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
                ArrayList<String> codPreReq;
                while ((linea = bu.readLine()) != null) {
                    codPreReq = new ArrayList<>();
                    StringTokenizer st = new StringTokenizer(linea, ",");
                    String codigo = st.nextToken();
                    String nombre = st.nextToken();
                    int numCodPreReq = Integer.parseInt(st.nextToken());
                    for (int i = 0; i < numCodPreReq; i++) {
                        codPreReq.add(st.nextToken());
                    }
                    int creaditos = Integer.parseInt(st.nextToken());
                    curso = new Curso(codigo, nombre, numCodPreReq, codPreReq, creaditos);
                    cursosDescargados.add(curso);

                }
                cargarCursos(cursosDescargados);
            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al descargar datos: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    public static ArrayList<Curso> descargarDatosAL(String path) {
        File ruta = new File(path);
        ArrayList<Curso> cursosDescargados = new ArrayList<>();
        try {
            FileReader fi = new FileReader(ruta);
            try (BufferedReader bu = new BufferedReader(fi)) {
                String linea;

                Curso curso;
                ArrayList<String> codPreReq;
                while ((linea = bu.readLine()) != null) {
                    codPreReq = new ArrayList<>();
                    StringTokenizer st = new StringTokenizer(linea, ",");
                    String codigo = st.nextToken();
                    String nombre = st.nextToken();
                    int numCodPreReq = Integer.parseInt(st.nextToken());
                    for (int i = 0; i < numCodPreReq; i++) {
                        codPreReq.add(st.nextToken());
                    }
                    int creaditos = Integer.parseInt(st.nextToken());
                    curso = new Curso(codigo, nombre, numCodPreReq, codPreReq, creaditos);
                    cursosDescargados.add(curso);

                }

            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al descargar datos: " + e.getMessage());
            System.out.println(e.getMessage());
        }
        return cursosDescargados;
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
        env.cambiaValor(cursosD.size(), "numCursos");
    }

    public static void cargarPlan(Periodo[] plan) {
        try {
            Field field = Malla.class.getDeclaredField("planOptimizado");
            field.setAccessible(true);

            field.set(planOptimizado, plan);
        } catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
            System.out.println("No se pudo cambiar el Plan :(");
            e.printStackTrace(System.out);

        }
    }

    public static void guardarPlan(int[][] mejorGrafo) {
        Periodo[] planOpt = new Periodo[env.numPeriodos];
        Curso curso;
        int c = 0;
        for (int j = 0; j < env.numPeriodos; j++) {
            planOpt[j] = new Periodo();
            for (int i = 0; i < env.numCursos; i++) {
                if (mejorGrafo[i][j] == 1) {

                    curso = new Curso(cursos.get(i).codigo,
                            cursos.get(i).nombre,
                            cursos.get(i).numPrerequisito,
                            cursos.get(i).codPreReq,
                            cursos.get(i).creditos);

                    planOpt[j].cursos.add(curso);
                    c++;
                }
            }
        }
        System.out.println("Resultado////////////////////////////////////////////");
        System.out.println("Cursos asignados:" + c);
        cargarPlan(planOpt);
    }

    //
    public static int evaluarGrafo(int[][] Grafo) {

        int[] sumCursos = obetenerSumCursosPorPeriodo(Grafo);
        int[] sumCreditos = obetenerSumCreditosPorPeriodo(Grafo);
        int suma=0;
        
        for (int j = 0; j < env.numPeriodos; j++) {
            if (sumCursos[j]<env.minCursosPermitidos) {
                suma++;
            }
            if (sumCursos[j]>env.maxCursosPermitidos) {
                suma++;
            }
            if (sumCreditos[j]<env.minCreditosPorPeriodo) {
                suma++;
            }
            if (sumCreditos[j]>env.maxCreditosPorPeriodo) {
                suma++;
            }
        }
        
        return suma;

    }

    public static Periodo[] convertirGrafoaPlan(int[][] mejorGrafo) {
        Periodo[] planOpt = new Periodo[env.numPeriodos];
        Curso curso;
        int c = 0;
        for (int j = 0; j < env.numPeriodos; j++) {
            planOpt[j] = new Periodo();
            for (int i = 0; i < env.numCursos; i++) {
                if (mejorGrafo[i][j] == 1) {

                    curso = new Curso(cursos.get(i).codigo,
                            cursos.get(i).nombre,
                            cursos.get(i).numPrerequisito,
                            cursos.get(i).codPreReq,
                            cursos.get(i).creditos);

                    planOpt[j].cursos.add(curso);
                    c++;
                }
            }
        }
        System.out.println("Cursos asignados:" + c);
        return planOpt;
    }

    //test
    public static void guardarPlanTest(int[][] mejorGrafo) {
        Periodo[] planOpt = convertirGrafoaPlan(mejorGrafo);

        mostrarTest(planOpt);

    }

    public static void mostrarTest(Periodo[] plan) {
        Curso curso;

        for (int i = 0; i < env.numPeriodos; i++) {
            int ciclo = i + 1;
            int numero;
            System.out.println("____________CICLO " + ciclo + "________________");
            for (int j = 0; j < plan[i].cursos.size(); j++) {

                curso = plan[i].cursos.get(j);
                numero = j + 1;
                System.out.println(numero + ":" + curso.codigo + "," + curso.creditos + "," + curso.nombre);

            }
        }
    }

}
