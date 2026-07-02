import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.*;
import Usuarios.*;

public class InformacionEstudiante extends JFrame implements ActionListener {
    private Container contenedor;
    private JButton volver;
    private JLabel titulo;
    private JPanel panelInfo, panelPrincipal;
    private List<String> informacion;
    private User usuario;

    public InformacionEstudiante(List<String> informacion, User usuario) {
        this.informacion = informacion;
        this.usuario = usuario;

        // Configuración de la ventana
        contenedor = getContentPane();
        contenedor.setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal
        panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(80, 120, 200));
        panelPrincipal.setBounds(0, 0, 1000, 600);
        contenedor.add(panelPrincipal);

        // Título
        titulo = new JLabel("Información del Estudiante", SwingConstants.CENTER);
        titulo.setBounds(250, 20, 500, 50);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 30));
        panelPrincipal.add(titulo);

        // Panel de información
        panelInfo = new JPanel();
        panelInfo.setLayout(new GridLayout(informacion.size(), 1, 10, 10));
        panelInfo.setBounds(250, 100, 500, 350);
        panelInfo.setBackground(Color.WHITE);
        panelPrincipal.add(panelInfo);

        // Botón Volver
        volver = new BotonPersonalizado("Volver");
        volver.setBounds(400, 500, 200, 50);
        volver.setFont(new Font("Arial", Font.BOLD, 18));
        volver.addActionListener(this);
        panelPrincipal.add(volver);

        mostrarInformacion();
    }

    private void mostrarInformacion() {
        panelInfo.removeAll();

        for (String linea : informacion) {
            JPanel panelDato = new JPanel();
            panelDato.setBackground(new Color(173, 216, 230)); // Azul claro
            panelDato.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));
            panelDato.setLayout(new BoxLayout(panelDato, BoxLayout.Y_AXIS));

            JLabel etiqueta = new JLabel(linea);
            etiqueta.setFont(new Font("Arial", Font.BOLD, 16));
            etiqueta.setForeground(Color.BLACK);
            etiqueta.setAlignmentX(JLabel.CENTER_ALIGNMENT);

            panelDato.add(etiqueta);
            panelInfo.add(panelDato);
        }

        panelInfo.revalidate();
        panelInfo.repaint();
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