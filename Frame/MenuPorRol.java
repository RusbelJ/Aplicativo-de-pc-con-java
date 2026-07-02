import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import Usuarios.*;

public class MenuPorRol extends JFrame implements ActionListener {
    private Container contenedor;
    private JPanel barraDeMenu;
    private List<JButton> botonesOpciones;
    private JButton salir;
    private JLabel rolLabel, fondo, bienvenidaLabel, rolTextoLabel;
    private ImageIcon icono, imagenFondo;
    private User usuario;
    private String rol;
    private ArrayList<User> usuarios;

    public MenuPorRol(User usuario) {
        this.usuario = usuario;
        this.rol = determinarRol(usuario);
        this.botonesOpciones = new ArrayList<>(); 
        this.usuarios = User.usuarios;
        configurarVentana();
        agregarComponentes();
        
    }

    private String determinarRol(User usuario) {
        if (usuario instanceof Acudiente) return "Acudiente";
        if (usuario instanceof Rectora) return "Rectora";
        if (usuario instanceof Secretaria) return "Secretaria";
        return "Desconocido";
    }

    private void configurarVentana() {
        contenedor = getContentPane();
        contenedor.setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
    }

    private void agregarComponentes() {
        imagenFondo = new ImageIcon(getClass().getResource("/Imagenes/fondoMenuPorRol.png"));
        fondo = new JLabel(imagenFondo);
        fondo.setBounds(0, 0, 1000, 600);
        contenedor.add(fondo);
    
        barraDeMenu = new JPanel();
        barraDeMenu.setLayout(null);
        barraDeMenu.setBounds(0, 0, 350, 600);
        barraDeMenu.setBackground(new Color(30, 80, 150)); 
        fondo.add(barraDeMenu);
    
        icono = new ImageIcon(getClass().getResource("/Imagenes/logo" + rol + ".png"));
        rolLabel = new JLabel(icono);
        rolLabel.setBounds(45, 90, 262, 337);
        barraDeMenu.add(rolLabel);
    
        // Texto de bienvenida
        bienvenidaLabel = new JLabel("¡Bienvenido/a, " + usuario.getName() + "!");
        bienvenidaLabel.setFont(new Font("Arial", Font.BOLD, 24));
        bienvenidaLabel.setForeground(Color.WHITE);
        bienvenidaLabel.setBounds(480, 50, 400, 30);
        bienvenidaLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fondo.add(bienvenidaLabel);
    
        // Texto del rol
        rolTextoLabel = new JLabel("Rol: " + rol);
        rolTextoLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        rolTextoLabel.setForeground(Color.WHITE);
        rolTextoLabel.setBounds(480, 80, 400, 30);
        rolTextoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        fondo.add(rolTextoLabel);
        
        // Si el usuario es un acudiente, mostrar el estado de matrícula del estudiante
        if (usuario instanceof Acudiente) {
            Acudiente acudiente = (Acudiente) usuario;

            Estudiante estudianteAsociado = null;
            for (Estudiante estudiante : Estudiante.estudiantes) {
                if(estudiante.getNombre().equals(acudiente.getNameEstudiante())) {
                    estudianteAsociado = estudiante;
                }
            }
            
            if (estudianteAsociado != null) {
                JLabel matriculaLabel = new JLabel("Estado de Matrícula: " + (estudianteAsociado.getMatricualPaga() ? "Pagada" : "Sin pagar"));    
                matriculaLabel.setFont(new Font("Arial", Font.PLAIN, 18));
                matriculaLabel.setForeground(Color.WHITE);
                matriculaLabel.setBounds(480, 110, 400, 30);
                matriculaLabel.setHorizontalAlignment(SwingConstants.CENTER);
                fondo.add(matriculaLabel);
            }
        }
    
        salir = new BotonPersonalizado("Cerrar sesión");
        salir.setBounds(70, 452, 200, 50);
        salir.addActionListener(this);
        barraDeMenu.add(salir);
    
        definirOpciones();
    }    

    private void definirOpciones() {
        List<String> opciones = obtenerOpcionesPorRol();
        int yPosition = 200; 
        int espacio = 90;
    
        if (rol.equals("Acudiente")) {
            espacio = 70; // Reduce la separación para que quepan más botones
        }
    
        for (String opcion : opciones) {
            JButton boton = new BotonPersonalizado(opcion);
            boton.setBounds(480, yPosition, 400, 50);
            boton.addActionListener(this);
            fondo.add(boton);
            botonesOpciones.add(boton);
            yPosition += espacio;
        }
    }

    private List<String> obtenerOpcionesPorRol() {
        switch (rol) {
            case "Acudiente":
                return List.of("Revisar Apartado de Notificaciones", "Solicitar Información Académica", "Visualizar Información del Estudiante", "Visualizar Valor de la Matrícula", "Pagar Matrícula");
            case "Rectora":
                return List.of("Revisar Apartado de Notificaciones", "Habilitar Información Académica");
            case "Secretaria":
                return List.of("Revisar Apartado de Notificaciones", "Actualizar Información Académica", "Actualizar Información Financiera");
            default:
                return new ArrayList<>();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == salir) {
            VentanaInicial Vi = new VentanaInicial();
            Vi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(false);
            Vi.setVisible(true);
        } else {
            manejarOpcion(e.getSource());
        }
    }

    private void manejarOpcion(Object source) {
        for (int i = 0; i < botonesOpciones.size(); i++) {
            if (source == botonesOpciones.get(i)) {
                ejecutarAccion(rol, i);
                break;
            }
        }
    }

    private void ejecutarAccion(String rol, int opcionIndex) {
        Acudiente acudiente;
        switch (rol) {
            case "Acudiente":
                switch (opcionIndex) {
                    case 0:
                        List<String> notificaciones = usuario.cargarApartadoNotificaciones();

                        if (notificaciones == null) {
                            JOptionPane.showMessageDialog(this, "No hay notificaciones disponibles", "Información", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }

                        MenuNotificaciones MN = new MenuNotificaciones(usuario, notificaciones);
                        MN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.setVisible(false);
                        MN.setVisible(true);
                        break;
                    case 1:
                        Acudiente.solicitarInformacionAcademica(usuario);
                       break;
                    case 2:
                        acudiente = (Acudiente) usuario;
                            if (acudiente.getHabilitado()) {
                            List<String> infoEstudiante = Acudiente.visualizarInformacionAcademica(acudiente);
                            InformacionEstudiante IE = new InformacionEstudiante(infoEstudiante, usuario);
                            IE.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            this.setVisible(false);
                            IE.setVisible(true);
                            } else {

                            JOptionPane.showMessageDialog(this, 
                                "No tienes permisos para visualizar la información del estudiante.", 
                                "Acceso Denegado", 
                                JOptionPane.ERROR_MESSAGE);
                            }
                        break;
                    case 3:
                        
                        FacturaMatricula FM = new FacturaMatricula(usuario);
                        FM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.setVisible(false);
                        FM.setVisible(true);
                        break;
                    case 4:
                        acudiente = (Acudiente) usuario;
                        Estudiante estudianteAsociado = null;
                    
                        for (Estudiante e : Estudiante.estudiantes) {
                            if (e.getNombre().equalsIgnoreCase(acudiente.getNameEstudiante())) {
                                estudianteAsociado = e;
                                break;
                            }
                        }
                    
                        if (estudianteAsociado == null) {
                            JOptionPane.showMessageDialog(this, "No se encontró un estudiante asociado a este usuario.", "Error", JOptionPane.ERROR_MESSAGE);
                        } else if (estudianteAsociado.getValorMatricula() == -1) {
                            JOptionPane.showMessageDialog(this, "La factura de matrícula aún no está disponible.", "Aviso", JOptionPane.WARNING_MESSAGE);
                        } else {
                            PagarMatricula PM = new PagarMatricula(usuario);
                            PM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                            this.setVisible(false);
                            PM.setVisible(true);
                        }
                        break;
                }
                break;
            case "Rectora":
                switch (opcionIndex) {
                    case 0:
                        List<String> notificaciones = usuario.cargarApartadoNotificaciones();

                        if (notificaciones == null) {
                            JOptionPane.showMessageDialog(this, "No hay notificaciones disponibles", "Información", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }

                        MenuNotificaciones MN = new MenuNotificaciones(usuario, notificaciones);
                        MN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.setVisible(false);
                        MN.setVisible(true);
                        break;
                    case 1:

                    int contadorAcudientes = 0;

                    for (User u : usuarios) {
                        if (u instanceof Acudiente) {
                            contadorAcudientes++;
                        }
                    }
                    
                    if (contadorAcudientes < 1) {
                        JOptionPane.showMessageDialog(this, "No hay acudientes registrados", "Información", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                        HabilitarInformacion HI = new HabilitarInformacion(usuario);
                        HI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.setVisible(false);
                        HI.setVisible(true);
                        break;
                }
                break;
            case "Secretaria":
                switch (opcionIndex) {
                    case 0:
                        List<String> notificaciones = usuario.cargarApartadoNotificaciones();

                        if (notificaciones == null) {
                            JOptionPane.showMessageDialog(this, "No hay notificaciones disponibles", "Información", JOptionPane.INFORMATION_MESSAGE);
                            break;
                        }

                        MenuNotificaciones MN = new MenuNotificaciones(usuario, notificaciones);
                        MN.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.setVisible(false);
                        MN.setVisible(true);
                        break;
                    case 1:
                        EditarInfoAcademica IA = new EditarInfoAcademica(usuario);
                        IA.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.setVisible(false);
                        IA.setVisible(true);
                        break;
                    case 2:
                        EditarInfoFinanciera IF = new EditarInfoFinanciera(usuario);
                        IF.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        this.setVisible(false);
                        IF.setVisible(true);
                        break;
                }
                break;
        }
    }
}