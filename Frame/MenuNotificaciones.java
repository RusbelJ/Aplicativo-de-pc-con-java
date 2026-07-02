import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import Usuarios.Acudiente;
import Usuarios.Rectora;
import Usuarios.Secretaria;
import Usuarios.User;

public class MenuNotificaciones extends JFrame implements ActionListener {

    private Container contenedor;
    private JButton volver;
    private JLabel textoInicial;
    private JPanel panelNotificaciones, panelPrincipal;
    private List<String> notificaciones; 
    private User usuario; 

    public MenuNotificaciones(User usuario, List<String> notificaciones) {
        this.usuario = usuario; // Guardamos el usuario
        this.notificaciones = (notificaciones != null) ? notificaciones : new java.util.ArrayList<>(); // Evita null

        contenedor = getContentPane();
        contenedor.setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(80, 120, 200)); // Azul más claro
        panelPrincipal.setBounds(0, 0, 1000, 600);
        contenedor.add(panelPrincipal);

        textoInicial = new JLabel("Notificaciones", SwingConstants.CENTER);
        textoInicial.setBounds(275, 20, 450, 50);
        textoInicial.setForeground(Color.WHITE);
        textoInicial.setFont(new Font("Arial Black", Font.BOLD, 30));
        panelPrincipal.add(textoInicial);

        panelNotificaciones = new JPanel();
        panelNotificaciones.setLayout(null);
        panelNotificaciones.setBackground(Color.WHITE);
        panelNotificaciones.setBounds(225, 80, 550, 400);
        panelPrincipal.add(panelNotificaciones);

        // Botón Volver más arriba
        volver = new BotonPersonalizado("Volver");
        volver.setBounds(400, 500, 200, 50);
        volver.addActionListener(this);
        panelPrincipal.add(volver);

        actualizarListaNotificaciones();
    }

    private void actualizarListaNotificaciones() {
        panelNotificaciones.removeAll();
        int y = 10;

        if (notificaciones.isEmpty()) {
            JLabel mensajeVacio = new JLabel("No hay notificaciones disponibles", SwingConstants.CENTER);
            mensajeVacio.setBounds(20, 170, 510, 40);
            mensajeVacio.setFont(new Font("Arial", Font.ITALIC, 18));
            mensajeVacio.setForeground(Color.GRAY);
            panelNotificaciones.add(mensajeVacio);
        } else {
            for (String notificacion : notificaciones) {
                JButton btnNotificacion = new JButton(notificacion);
                btnNotificacion.setBounds(20, y, 510, 40);
                btnNotificacion.addActionListener( _-> {
                    notificaciones.remove(notificacion);
                    eliminarNotificacionDelArchivo(notificacion);
                    actualizarListaNotificaciones();
                });
                panelNotificaciones.add(btnNotificacion);
                y += 50;
            }
        }

        panelNotificaciones.revalidate();
        panelNotificaciones.repaint();
    }    

    private void eliminarNotificacionDelArchivo(String notificacion) {
        if (usuario instanceof Acudiente) {
            usuario.eliminarNotificacion(notificacion);
        } else if (usuario instanceof Rectora) {
            usuario.eliminarNotificacion(notificacion);
        } else if (usuario instanceof Secretaria) {
            usuario.eliminarNotificacion(notificacion);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == volver) {
            MenuPorRol MR = new MenuPorRol(usuario);
            MR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(false);
            MR.setVisible(true);
        }
    }
}