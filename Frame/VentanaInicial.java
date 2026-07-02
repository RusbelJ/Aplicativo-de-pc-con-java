import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Usuarios.Secretaria;

public class VentanaInicial extends JFrame implements ActionListener {
    private Container contenedor;
    private JButton iniciarSesion, registrarUsuario, cerrarAplicacion;
    private JLabel textoInicial, logoColegio, fondo;
    private ImageIcon imagenLogoColegio, imagenFondo;

    public VentanaInicial() {
        contenedor = getContentPane();
        contenedor.setLayout(null);
        setTitle("Centro Educativo Neosistemas");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        // Imagen de fondo
        imagenFondo = new ImageIcon(getClass().getResource("/Imagenes/ImagenFondo.png"));
        fondo = new JLabel(imagenFondo);
        fondo.setBounds(0, 0, 1000, 600);
        contenedor.add(fondo);

        // Logo en la esquina superior izquierda
        imagenLogoColegio = new ImageIcon(getClass().getResource("/Imagenes/LogoColegio.png"));
        logoColegio = new JLabel(imagenLogoColegio);
        logoColegio.setBounds(4, 4, 300, 300);
        fondo.add(logoColegio);

        // Texto "NEOSISTEMAS" con estilo de título
        textoInicial = new JLabel("NEOSISTEMAS", SwingConstants.CENTER);
        textoInicial.setBounds(260, 60, 500, 70);
        textoInicial.setFont(new Font("Arial Black", Font.BOLD, 48));
        textoInicial.setForeground(new Color(0, 51, 102));
        textoInicial.setOpaque(false);
        fondo.add(textoInicial);

        // Botones Personalizados
        iniciarSesion = new BotonPersonalizado("Iniciar Sesión");
        iniciarSesion.setBounds(90, 460, 250, 60);
        iniciarSesion.addActionListener(this);
        fondo.add(iniciarSesion);

        registrarUsuario = new BotonPersonalizado("Registrar Usuario");
        registrarUsuario.setBounds(370, 460, 250, 60);
        registrarUsuario.addActionListener(this);
        fondo.add(registrarUsuario);

        cerrarAplicacion = new BotonPersonalizado("Cerrar Aplicación");
        cerrarAplicacion.setBounds(650, 460, 250, 60);
        cerrarAplicacion.addActionListener(this);
        fondo.add(cerrarAplicacion);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == iniciarSesion) {
            InicioSesion VR = new InicioSesion();
            VR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(false);
            VR.setVisible(true);
        }
        else if (e.getSource() == registrarUsuario) {
            Registrar VR = new Registrar();
            VR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(false);
            VR.setVisible(true);
        }
        else if (e.getSource() == cerrarAplicacion){
            Secretaria.guardarUsuarios();
            System.exit(0);
        }
    }
}