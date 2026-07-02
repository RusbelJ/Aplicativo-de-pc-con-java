package Usuarios;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Secretaria extends User {
    private static final long serialVersionUID = 1L;
    static final String ARCHIVO_USUARIOS = "users.ser";
    protected int numeroOficina;

    public Secretaria(String name, String password, int numeroOficina) {
        super(name, password, "secretaria");
        this.numeroOficina = numeroOficina;
    }

    @Override
    public String getNombreArchivoNotificaciones() {
        return "notificaciones_secretaria.txt";
    }

    public static boolean actualizarInformacion(String nombreEstudiante, int variableEditada, String datoNuevo) { 
        ArrayList<Estudiante> estudiantes = Estudiante.estudiantes;
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().equalsIgnoreCase(nombreEstudiante)) {
                switch (variableEditada) {
                    case 0 -> estudiante.setMatriculaPaga(datoNuevo.equalsIgnoreCase("true"));
                    case 1 -> estudiante.setMatematicas(parseNota(datoNuevo));
                    case 2 -> estudiante.setBiologia(parseNota(datoNuevo));
                    case 3 -> estudiante.setIngles(parseNota(datoNuevo));
                    case 4 -> estudiante.setSociales(parseNota(datoNuevo));
                    case 5 -> { // Nueva opción para actualizar el valor de la matrícula
                        try {
                            double nuevoValor = Double.parseDouble(datoNuevo);
                            estudiante.setValorMatricula(nuevoValor);
                        } catch (NumberFormatException e) {
                            return false; // Si el dato no es un número, retorna falso
                        }
                    }
                    default -> { return false; } // Opción inválida
                }
    
                estudiante.setinformacionActualizada(true);
                return true; // Se actualizó correctamente
            }
        }
        return false; // No se encontró el estudiante
    }    

    private static double parseNota(String dato) {
        try {
            double nota = Double.parseDouble(dato);
            return (nota >= 0 && nota <= 5) ? nota : -1; // Validación de nota
        } catch (NumberFormatException e) {
            return -1; // Valor inválido
        }
    }

    public static void cargarUsuarios() {
        File archivo = new File(ARCHIVO_USUARIOS);
    
        if (archivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(archivo))) {
                @SuppressWarnings("unchecked")
                ArrayList<User> usuariosCargados = (ArrayList<User>) ois.readObject();
                usuarios = usuariosCargados;
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(null, "Error al cargar usuarios: " + e.getMessage());
            }
        } else {
            usuarios = new ArrayList<>();
        }
    
        agregarUsuariosPorDefecto();
    }

    private static void agregarUsuariosPorDefecto() {
        User usuario1 = new Rectora("nohemi", "1234");
        User usuario2 = new Secretaria("david", "1234", 12);
    
        usuarios.add(usuario1);
        usuarios.add(usuario2);

        guardarUsuarios();
    }

    public static void guardarUsuarios() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ARCHIVO_USUARIOS))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al guardar usuarios: " + e.getMessage());
        }
    }
}