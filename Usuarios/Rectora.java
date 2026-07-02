package Usuarios;
import java.util.ArrayList;


public class Rectora extends User {
    private static final long serialVersionUID = 1L;

    public Rectora(String name, String password) {
        super(name, password, "rectora");
    }

    public static Boolean habilitarInformacionAcademica(String nombreAcudiente, boolean estado) {
        ArrayList<Estudiante> estudiantes = Estudiante.estudiantes;
        for (Acudiente acudiente : Acudiente.acudientes) {
            System.out.println(acudiente.getNombre());
            if (acudiente.getNombre() == nombreAcudiente) {
                acudiente.setHabilitado(true); 
                for (Estudiante e : estudiantes) {
                    if (e.getNombre().equals(acudiente.getNameEstudiante())) {
                        e.setMatriculaPaga(true);
                    }
                }   
                return true;
            }
        }
        return false;
    }

    @Override
    public String getNombreArchivoNotificaciones() {
        return "notificaciones_rectora.txt";
    }

    @Override
    public String toString() {
        return "Rectora{name='" + name + "', password='" + password + "'}";
    }
}