package Usuarios;

import java.util.ArrayList;
import java.util.List;

public class Estudiante {
    private String nombre;
    private String identificacion;
    private boolean matriculaPaga;
    private double matematicas;
    private double biologia;
    private double ingles;
    private double sociales;
    private boolean informacionActualizada = false;
    private double valorMatricula = -1;
    public static ArrayList<Estudiante> estudiantes = new ArrayList<>();

    public Estudiante(String nombre, String identificacion, boolean matriculaPaga) {
        this.nombre = nombre;
        this.identificacion = identificacion;
        this.matriculaPaga = matriculaPaga;
        this.matematicas = -1;
        this.biologia = -1;
        this.ingles = -1;
        this.sociales = -1;
        estudiantes.add(this);
    }

    public static void cargarEstudiantes() {
        new Estudiante("Tomás Acevedo", "1001", true);
        new Estudiante("María López", "1002", true);
        new Estudiante("Santiago Ochoa", "1003", false);
        new Estudiante("Ana Torres", "1004", true);
        new Estudiante("Pedro Sánchez", "1005", true);
        new Estudiante("Laura Fernández", "1006", false);
        new Estudiante("Miguel Ramírez", "1007", true);
        new Estudiante("Elena Ríos", "1008", true);
        new Estudiante("Ricardo Díaz", "1009", false);
        new Estudiante("Carmen Castillo", "1010", true);
    }

    public boolean getinformacionActualizada() {
        return informacionActualizada;
    }

    public void setinformacionActualizada(boolean informacionActualizada) {
        this.informacionActualizada = informacionActualizada;
    }

    public double getValorMatricula() {
        return valorMatricula;
    }

    public void setValorMatricula(double valorMatricula) {
        this.valorMatricula = valorMatricula;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public boolean getMatricualPaga() {
        return matriculaPaga;
    }

    public double getMatematicas() {
        return matematicas;
    }

    public double getBiologia() {
        return biologia;
    }

    public double getIngles() {
        return ingles;
    }

    public double getSociales() {
        return sociales;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public void setMatriculaPaga(boolean matriculaPaga) {
        this.matriculaPaga = matriculaPaga;
    }

    public void setMatematicas(double matematicas) {
        this.matematicas = matematicas;
    }

    public void setBiologia(double biologia) {
        this.biologia = biologia;
    }

    public void setIngles(double ingles) {
        this.ingles = ingles;
    }

    public void setSociales(double sociales) {
        this.sociales = sociales;
    }

    public List<String> mostrarInformacion() {
        List<String> info = new ArrayList<>();
        info.add("Nombre: " + nombre);
        info.add("ID: " + identificacion);
        info.add("Estado de Matrícula: " + (matriculaPaga ? "Matrícula paga" : "Matrícula no paga"));
        info.add(" - Matemáticas: " + (matematicas >= 0 ? matematicas : "Sin nota"));
        info.add(" - Biología: " + (biologia >= 0 ? biologia : "Sin nota"));
        info.add(" - Inglés: " + (ingles >= 0 ? ingles : "Sin nota"));
        info.add(" - Sociales: " + (sociales >= 0 ? sociales : "Sin nota"));

        return info;
    }
}