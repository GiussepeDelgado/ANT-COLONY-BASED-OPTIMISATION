/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.edu.unmsm.vista;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import pe.edu.unmsm.modelo.datos.Curso;
import pe.edu.unmsm.modelo.datos.Malla;
import pe.edu.unmsm.modelo.datos.env;

/**
 *
 * @author Windows 10 Pro
 */
public class Interface {

    public static String configurarVariables(JTextField numHormigas,
            JTextField iterMax,
            JTextField minCursosPermitidos,
            JTextField maxCursosPermitodos,
            JTextField minCreditosPermitidos,
            JTextField maxCreditosPermitodos) {

        if (numHormigas.getText().trim().isEmpty()
                || iterMax.getText().trim().isEmpty()
                || minCursosPermitidos.getText().trim().isEmpty()
                || maxCursosPermitodos.getText().trim().isEmpty()
                || minCreditosPermitidos.getText().trim().isEmpty()
                || maxCreditosPermitodos.getText().trim().isEmpty()) {
            return "No debe haber campos vacios!";
        }

        if (isNumeric(numHormigas.getText().trim())
                && isNumeric(iterMax.getText().trim())
                && isNumeric(minCursosPermitidos.getText().trim())
                && isNumeric(maxCursosPermitodos.getText().trim())
                && isNumeric(minCreditosPermitidos.getText().trim())
                && isNumeric(maxCreditosPermitodos.getText().trim())) {

            int numHormigasInt = Integer.parseInt(numHormigas.getText().trim());
            int iterMaxInt = Integer.parseInt(iterMax.getText().trim());
            int minCursosPermitidosInt = Integer.parseInt(minCursosPermitidos.getText().trim());
            int maxCursosPermitodosInt = Integer.parseInt(maxCursosPermitodos.getText().trim());
            int minCreditosPermitidosInt = Integer.parseInt(minCreditosPermitidos.getText().trim());
            int maxCreditosPermitodosInt = Integer.parseInt(maxCreditosPermitodos.getText().trim());

            env.inicializarVariables(env.numCursos,
                    env.numPeriodos,
                    numHormigasInt,
                    minCursosPermitidosInt,
                    maxCursosPermitodosInt,
                    minCreditosPermitidosInt,
                    maxCreditosPermitodosInt,
                    iterMaxInt);

            numHormigas.setText("");
            iterMax.setText("");
            minCursosPermitidos.setText("");
            maxCursosPermitodos.setText("");
            minCreditosPermitidos.setText("");
            maxCreditosPermitodos.setText("");

            return "Configuracion Guardada";

        } else {

            return "Todos los campos deben ser numericos!";
        }

    }

    public static boolean isNumeric(String campo) {
        return campo.matches("[+-]?\\d*(\\.\\d+)?");
    }

    public static void establecerInfoVariables(JLabel numHormigas,
            JLabel iterMax,
            JLabel minCursosPermitidos,
            JLabel maxCursosPermitodos,
            JLabel minCreditosPermitidos,
            JLabel maxCreditosPermitodos) {

        numHormigas.setText("" + env.numHormigas);
        iterMax.setText("" + env.IterMax);
        minCursosPermitidos.setText("" + env.minCursosPermitidos);
        maxCursosPermitodos.setText("" + env.maxCursosPermitidos);
        minCreditosPermitidos.setText("" + env.minCreditosPorPeriodo);
        maxCreditosPermitodos.setText("" + env.maxCreditosPorPeriodo);

    }

    public static void inicializarListaDePlanes(JComboBox<String> lista) {

        File ruta = new File("src\\main\\java\\pe\\edu\\unmsm\\modelo\\datos\\curriculas\\Curriculas.txt");
        try {
            FileReader fi = new FileReader(ruta);
            try (BufferedReader bu = new BufferedReader(fi)) {
                String linea;

                while ((linea = bu.readLine()) != null) {

                    lista.addItem(linea);

                }

            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al descargar datos: " + e.getMessage());
            System.out.println(e.getMessage());
        }

    }

    public static ArrayList<String> descargarPlanes(String path) {
        ArrayList<String> listaPlanes = new ArrayList<>();

        File ruta = new File(path);
        try {
            FileReader fi = new FileReader(ruta);
            try (BufferedReader bu = new BufferedReader(fi)) {
                String linea;

                while ((linea = bu.readLine()) != null) {

                    listaPlanes.add(linea);

                }

            }

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al descargar datos: " + e.getMessage());
            System.out.println(e.getMessage());
        }
        return listaPlanes;
    }

    public static void limpiarTablas(JTable t1,
            JTable t2,
            JTable t3,
            JTable t4,
            JTable t5,
            JTable t6,
            JTable t7,
            JTable t8,
            JTable t9,
            JTable t10) {
        DefaultTableModel tabla;

        JTable[] tablas = new JTable[env.numPeriodos];
        tablas[0] = t1;
        tablas[1] = t2;
        tablas[2] = t3;
        tablas[3] = t4;
        tablas[4] = t5;
        tablas[5] = t6;
        tablas[6] = t7;
        tablas[7] = t8;
        tablas[8] = t9;
        tablas[9] = t10;

        for (int i = 0; i < env.numPeriodos; i++) {

            tabla = new DefaultTableModel();
            tabla.addColumn("ID");
            tabla.addColumn("CODIGO");
            tabla.addColumn("NOMBRE");
            tabla.addColumn("CREDITOS");
            tabla.addColumn("PRE-REQUISITOS");

            tablas[i].setModel(tabla);

        }

    }

    public static void generarTablas(JTable t1,
            JTable t2,
            JTable t3,
            JTable t4,
            JTable t5,
            JTable t6,
            JTable t7,
            JTable t8,
            JTable t9,
            JTable t10) {
        DefaultTableModel tabla;

        JTable[] tablas = new JTable[env.numPeriodos];
        tablas[0] = t1;
        tablas[1] = t2;
        tablas[2] = t3;
        tablas[3] = t4;
        tablas[4] = t5;
        tablas[5] = t6;
        tablas[6] = t7;
        tablas[7] = t8;
        tablas[8] = t9;
        tablas[9] = t10;

        Curso curso;
        for (int i = 0; i < env.numPeriodos; i++) {

            int numero;
            tabla = new DefaultTableModel();
            tabla.addColumn("ID");
            tabla.addColumn("CODIGO");
            tabla.addColumn("NOMBRE");
            tabla.addColumn("CREDITOS");
            tabla.addColumn("PRE-REQUISITOS");
            Object columna[] = new Object[tabla.getColumnCount()];
            for (int j = 0; j < Malla.planOptimizado[i].cursos.size(); j++) {

                curso = Malla.planOptimizado[i].cursos.get(j);
                numero = j + 1;
                columna[0] = numero;
                columna[1] = curso.codigo;
                columna[2] = curso.nombre;
                columna[3] = curso.creditos;
                String preReqCodigos = "";
                for (int l = 0; l < curso.codPreReq.size(); l++) {

                    if (l == curso.codPreReq.size() - 1) {
                        preReqCodigos += curso.codPreReq.get(l);
                    } else {
                        preReqCodigos += curso.codPreReq.get(l) + ", ";
                    }
                }

                columna[4] = preReqCodigos;
                tabla.addRow(columna);
                tablas[i].setModel(tabla);
                tablas[i].setRowHeight(30);
            }
        }
        System.out.println("fin de generacion");
    }

    public static void actualizarTablaPreReq(ArrayList<String> lista, JTable tablaReq) {
        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn("ID");
        tabla.addColumn("PREREQUISITO");
        Object columna[] = new Object[tabla.getColumnCount()];
        for (int i = 0; i < lista.size(); i++) {
            columna[0] = i + 1;
            columna[1] = lista.get(i);
            tabla.addRow(columna);
            tablaReq.setModel(tabla);
            tablaReq.setRowHeight(30);
        }

    }

    public static String agregarCurso(JTextField codigo,
            JTextField nombre,
            JComboBox<String> creditos,
            ArrayList<String> preReq,
            JTable tablaCursos,
            ArrayList<Curso> plan) {

        ArrayList<String> preReqTemp;
        preReqTemp = (ArrayList<String>) preReq.clone();

        if (codigo.getText().trim().isEmpty()
                || nombre.getText().trim().isEmpty()) {
            return "Los campos codigo o nombre estan vacios";
        }
        if (preReq.isEmpty()) {
            return "La lista de pre-requisitos esta vacia";
        }

        int numCreditos = Integer.parseInt((String) creditos.getSelectedItem());
        Curso curso = new Curso(codigo.getText(), nombre.getText(), preReqTemp.size(), preReqTemp, numCreditos);

        plan.add(curso);
        preReq.clear();

        DefaultTableModel tabla = new DefaultTableModel();
        tabla.addColumn("ID");
        tabla.addColumn("CODIGO");
        tabla.addColumn("NOMBRE");
        tabla.addColumn("CREDITOS");
        tabla.addColumn("PRE-REQUISITOS");
        Object columna[] = new Object[tabla.getColumnCount()];
        for (int i = 0; i < plan.size(); i++) {
            columna[0] = i + 1;
            columna[1] = curso.codigo;
            columna[2] = curso.nombre;
            columna[3] = curso.creditos;
            String preReqCodigos = "";
            for (int l = 0; l < curso.codPreReq.size(); l++) {

                if (l == curso.codPreReq.size() - 1) {
                    preReqCodigos += curso.codPreReq.get(l);
                } else {
                    preReqCodigos += curso.codPreReq.get(l) + ", ";
                }
            }

            columna[4] = preReqCodigos;
            tabla.addRow(columna);
            tablaCursos.setModel(tabla);
            tablaCursos.setRowHeight(30);
        }
        return "Curso Agregado";
    }

    public static String agregarPlan(ArrayList<Curso> plan, JTextField nombrePlan) {
        String pathNombrePlan = "src\\main\\java\\pe\\edu\\unmsm\\modelo\\datos\\curriculas\\" + nombrePlan.getText() + ".txt";
        String pathPlanes = "src\\main\\java\\pe\\edu\\unmsm\\modelo\\datos\\curriculas\\Curriculas.txt";

        if (nombrePlan.getText().trim().isEmpty()) {
            return "Nombre del plan vacio";
        }

        File file = new File(pathNombrePlan);

        if (file.exists()) {
            ArrayList<Curso> listaDescargada;
            listaDescargada = Malla.descargarDatosAL(pathNombrePlan);
            for (int i = 0; i < plan.size(); i++) {
                listaDescargada.add(plan.get(i));
            }
            Malla.cargarDatos(pathNombrePlan, listaDescargada);
            return "Cursos guardados a archivo existente";

        }

        Malla.cargarDatos(pathNombrePlan, plan);
        actualizarPlanes(pathPlanes, nombrePlan.getText());

        return "Plan de estudio guardado";
    }

    public static void actualizarPlanes(String path, String nombrePlan) {

        ArrayList<String> listaPlanes;
        listaPlanes = descargarPlanes(path);
        listaPlanes.add(nombrePlan);
        FileWriter fw;
        PrintWriter pw;
        try {
            fw = new FileWriter(path);
            pw = new PrintWriter(fw);

            for (int i = 0; i < listaPlanes.size(); i++) {

                pw.println(String.valueOf(listaPlanes.get(i)));

            }
            pw.close();

        } catch (IOException | NumberFormatException e) {
            System.out.println("Error al cargar datos: " + e.getMessage());
            System.out.println(e.getMessage());
        }
    }

}
