import javax.swing.*;
import Usuarios.Estudiante;
import Usuarios.Secretaria;

public class Main {

    public static void main(String[] args) {
        Secretaria.cargarUsuarios();
        Estudiante.cargarEstudiantes();
        mostrarMenuPrincipal();
    }

    private static void mostrarMenuPrincipal() {
        VentanaInicial VI = new VentanaInicial();
        VI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        VI.setVisible(true);
    }
}   