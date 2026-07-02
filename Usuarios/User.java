package Usuarios;
import java.io.*;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    
    protected String name;
    protected String password;
    protected String Rol;
    
    // ✅ Se mantiene como ArrayList<User>
    public static ArrayList<User> usuarios = new ArrayList<>();

    public User(String name, String password, String Rol) {
        this.name = name;
        this.password = password;
        this.Rol = Rol;
    }

    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getRol() { return Rol; }

    public void setName(String name) { this.name = name; }
    public void setPassword(String password) { this.password = password; }
    public void setRol(String Rol) { this.Rol = Rol; }

    // Método que cada subclase deberá sobreescribir para definir su archivo de notificaciones
    protected String getNombreArchivoNotificaciones() {
        return "";
    }

    public ArrayList<String> cargarApartadoNotificaciones() {
        ImageIcon icono = new ImageIcon("imagenes/notificaciones.png");
        ArrayList<String> notificaciones = new ArrayList<>();
        String archivo = getNombreArchivoNotificaciones(); // Se obtiene el archivo específico

        if (!new File(archivo).exists()) {
            return notificaciones;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                notificaciones.add(linea);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo: " + e.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE, icono);
            return null;
        }

        return notificaciones;
    }

    public void eliminarNotificacion(String notificacion) {
        String archivo = getNombreArchivoNotificaciones();
        File file = new File(archivo);
        if (!file.exists()) return;

        try {
            ArrayList<String> notificaciones = cargarApartadoNotificaciones();
            if (notificaciones == null) return;

            // Filtramos la notificación a eliminar
            ArrayList<String> nuevasNotificaciones = new ArrayList<>(notificaciones.stream()
                    .filter(n -> !n.equals(notificacion))
                    .collect(Collectors.toList()));

            // Reescribimos el archivo sin la notificación eliminada
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                for (String linea : nuevasNotificaciones) {
                    writer.write(linea);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar la notificación: " + e.getMessage(),
                                          "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}