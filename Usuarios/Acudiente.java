package Usuarios;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Acudiente extends User {
    private static final long serialVersionUID = 1L;
    protected String nombreAcudiente;
    protected String nombreCompletoEstudiante;
    protected int identificacionEstudiante;
    private Boolean habilitado;
    public static ArrayList<Acudiente> acudientes = new ArrayList<>();

    public Acudiente(String name, String password, String nombreCompletoEstudiante, int identificacionEstudiante) {
        super(name, password, "acudiente");
        this.nombreAcudiente = name;
        this.nombreCompletoEstudiante = nombreCompletoEstudiante;
        this.identificacionEstudiante = identificacionEstudiante;
        this.habilitado = false;

        acudientes.add(this);
    }

    public static boolean registrarAcudiente(String nombreAcudiente, String contraseña, String nombreEstudiante, String codigoEstudiante) {
        Estudiante estudianteAsociado = null;

        for (Estudiante alumno : Estudiante.estudiantes) {
            if (alumno.getNombre().equalsIgnoreCase(nombreEstudiante.trim()) &&
                alumno.getIdentificacion().equals(codigoEstudiante.trim())) {
                estudianteAsociado = alumno;
                break;
            }
        }

        if(estudianteAsociado == null) {
            return false;
        }

        return true;
    }
    public Boolean getHabilitado(){
        return this.habilitado;
    }
    public void setHabilitado(Boolean h){
        this.habilitado = h;
    }
    public String getNombre() {
        return nombreAcudiente;
    }

    public String getNameEstudiante() {return nombreCompletoEstudiante;}
    @Override
    public String getNombreArchivoNotificaciones() {
        return "notificaciones_acudiente.txt";
    }


    public static void solicitarInformacionAcademica(User usuario) {

        if (usuario instanceof Acudiente) {
            Acudiente acudiente = (Acudiente) usuario;
            String nombreEstudiante = acudiente.getNameEstudiante();

            Estudiante estudianteSeleccionado = null;
            for (Estudiante e : Estudiante.estudiantes) {
                if (e.getNombre().trim().equalsIgnoreCase(nombreEstudiante.trim())) {
                    estudianteSeleccionado = e;
                    break;
                }
            }

            if (estudianteSeleccionado.getinformacionActualizada()) {
                JOptionPane.showMessageDialog(null, 
                                            "La información del estudiante ya se encuentra actualizada", 
                                            "Información Disponible", 
                                            JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            String nombreArchivo = "notificaciones_secretaria.txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(nombreArchivo, true))) {
                writer.write("El acudiente " + acudiente.getName() + 
                            " solicita información académica del estudiante " + acudiente.getNameEstudiante());
                writer.newLine();
                JOptionPane.showMessageDialog(null, "Solicitud de información académica realizada.", 
                                            "Solicitud Enviada", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error al manejar el archivo: " + e.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static List<String> visualizarInformacionAcademica(Acudiente acudiente) {
        ArrayList<Estudiante> estudiantes = Estudiante.estudiantes;
        Estudiante estudianteSeleccionado = null;
        for (Estudiante e : estudiantes) {
            if (e.getNombre().equals(acudiente.getNameEstudiante())) {
                estudianteSeleccionado = e;
                break;
            }
        }

        return estudianteSeleccionado.mostrarInformacion();
    }

    
}