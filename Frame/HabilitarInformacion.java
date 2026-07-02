import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;
import Usuarios.*;

public class HabilitarInformacion extends JFrame implements ActionListener {
    private Container contenedor;
    private JButton volver, habilitar;
    private JLabel titulo, lblSeleccion, lblEstado;
    private JComboBox<String> comboAcudientes;
    private JCheckBox chkHabilitar;
    private User usuario;
    private ArrayList<User> usuarios;

    public HabilitarInformacion(User usuario) {
        this.usuario = usuario;
        this.usuarios = User.usuarios;

        // Configuración de la ventana
        contenedor = getContentPane();
        contenedor.setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panel principal
        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(80, 120, 200));
        panelPrincipal.setBounds(0, 0, 1000, 600);
        contenedor.add(panelPrincipal);

        // Título
        titulo = new JLabel("Habilitar Información", SwingConstants.CENTER);
        titulo.setBounds(200, 20, 600, 50);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 30));
        panelPrincipal.add(titulo);

        // Selección de acudiente
        lblSeleccion = new JLabel("Selecciona un acudiente:");
        lblSeleccion.setBounds(300, 100, 400, 30);
        lblSeleccion.setFont(new Font("Arial", Font.BOLD, 18));
        lblSeleccion.setForeground(Color.WHITE);
        panelPrincipal.add(lblSeleccion);

        comboAcudientes = new JComboBox<>();
        comboAcudientes.setBounds(300, 140, 400, 30);
        panelPrincipal.add(comboAcudientes);

        // Llenar el JComboBox con los acudientes
        for (User a : usuarios) {
            if (a instanceof Acudiente) {
                comboAcudientes.addItem(a.getName());
            }
        }

        // Checkbox para habilitar/deshabilitar
        lblEstado = new JLabel("Habilitar acudiente:");
        lblEstado.setBounds(300, 190, 400, 30);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 18));
        lblEstado.setForeground(Color.WHITE);
        panelPrincipal.add(lblEstado);

        chkHabilitar = new JCheckBox("Habilitado");
        chkHabilitar.setBounds(300, 230, 400, 30);
        chkHabilitar.setFont(new Font("Arial", Font.PLAIN, 16));
        panelPrincipal.add(chkHabilitar);

        // Botón Habilitar
        habilitar = new BotonPersonalizado("Guardar Cambios");
        habilitar.setBounds(350, 300, 300, 50);
        habilitar.setFont(new Font("Arial", Font.BOLD, 18));
        habilitar.addActionListener(this);
        panelPrincipal.add(habilitar);

        // Botón Volver
        volver = new BotonPersonalizado("Volver");
        volver.setBounds(350, 380, 300, 50);
        volver.setFont(new Font("Arial", Font.BOLD, 18));
        volver.addActionListener(this);
        panelPrincipal.add(volver);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == habilitar) {
            String nombreAcudiente = (String) comboAcudientes.getSelectedItem();

            boolean estado = chkHabilitar.isSelected();
            boolean actualizado = Rectora.habilitarInformacionAcademica(nombreAcudiente, estado);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Apartado de información académica habilitado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el estado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == volver) {
            MenuPorRol MR = new MenuPorRol(usuario);
            MR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(false);
            MR.setVisible(true);
        }
    }
}