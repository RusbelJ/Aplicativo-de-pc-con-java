import java.awt.*;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.*;
import Usuarios.*;

public class EditarInfoFinanciera extends JFrame implements ActionListener {
    private Container contenedor;
    private JButton volver, actualizarEstado, actualizarValor;
    private JLabel titulo, lblSeleccion, lblEstado, lblValorMatricula;
    private JComboBox<String> comboEstudiantes, comboEstadoMatricula;
    private JTextField txtValorMatricula;
    private User usuario;
    private ArrayList<Estudiante> estudiantes;

    public EditarInfoFinanciera(User usuario) {
        this.usuario = usuario;
        this.estudiantes = Estudiante.estudiantes;

        contenedor = getContentPane();
        contenedor.setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        JPanel panelPrincipal = new JPanel();
        panelPrincipal.setLayout(null);
        panelPrincipal.setBackground(new Color(80, 120, 200));
        panelPrincipal.setBounds(0, 0, 1000, 600);
        contenedor.add(panelPrincipal);

        titulo = new JLabel("Editar Información Financiera", SwingConstants.CENTER);
        titulo.setBounds(200, 20, 600, 50);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 30));
        panelPrincipal.add(titulo);

        lblSeleccion = new JLabel("Selecciona un estudiante:");
        lblSeleccion.setBounds(300, 80, 400, 30);
        lblSeleccion.setFont(new Font("Arial", Font.BOLD, 18));
        lblSeleccion.setForeground(Color.WHITE);
        panelPrincipal.add(lblSeleccion);

        comboEstudiantes = new JComboBox<>();
        comboEstudiantes.setBounds(300, 120, 400, 30);
        for (Estudiante e : estudiantes) {
            comboEstudiantes.addItem(e.getNombre());
        }
        panelPrincipal.add(comboEstudiantes);

        JPanel panelEstado = new JPanel();
        panelEstado.setLayout(null);
        panelEstado.setBackground(new Color(100, 150, 250));
        panelEstado.setBounds(100, 180, 380, 250);
        panelPrincipal.add(panelEstado);

        lblEstado = new JLabel("Estado de matrícula:");
        lblEstado.setBounds(20, 20, 340, 30);
        lblEstado.setFont(new Font("Arial", Font.BOLD, 18));
        lblEstado.setForeground(Color.WHITE);
        panelEstado.add(lblEstado);

        comboEstadoMatricula = new JComboBox<>(new String[]{"Matrícula Paga", "Matrícula No Paga"});
        comboEstadoMatricula.setBounds(20, 60, 340, 30);
        panelEstado.add(comboEstadoMatricula);

        actualizarEstado = new BotonPersonalizado("Actualizar Estado");
        actualizarEstado.setBounds(40, 120, 300, 50);
        actualizarEstado.setFont(new Font("Arial", Font.BOLD, 18));
        actualizarEstado.addActionListener(this);
        panelEstado.add(actualizarEstado);

        JPanel panelValor = new JPanel();
        panelValor.setLayout(null);
        panelValor.setBackground(new Color(100, 150, 250));
        panelValor.setBounds(520, 180, 380, 250);
        panelPrincipal.add(panelValor);

        lblValorMatricula = new JLabel("Monto de matrícula:");
        lblValorMatricula.setBounds(20, 20, 340, 30);
        lblValorMatricula.setFont(new Font("Arial", Font.BOLD, 18));
        lblValorMatricula.setForeground(Color.WHITE);
        panelValor.add(lblValorMatricula);

        txtValorMatricula = new JTextField();
        txtValorMatricula.setBounds(20, 60, 340, 30);
        panelValor.add(txtValorMatricula);

        actualizarValor = new BotonPersonalizado("Actualizar Monto");
        actualizarValor.setBounds(40, 120, 300, 50);
        actualizarValor.setFont(new Font("Arial", Font.BOLD, 18));
        actualizarValor.addActionListener(this);
        panelValor.add(actualizarValor);

        volver = new BotonPersonalizado("Volver");
        volver.setBounds(350, 460, 300, 50);
        volver.setFont(new Font("Arial", Font.BOLD, 18));
        volver.addActionListener(this);
        panelPrincipal.add(volver);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String nombreEstudiante = (String) comboEstudiantes.getSelectedItem();
        
        if (e.getSource() == actualizarEstado) {
            String nuevoEstado = (String) comboEstadoMatricula.getSelectedItem();
            boolean actualizado = Secretaria.actualizarInformacion(nombreEstudiante, 0, nuevoEstado);
            if (actualizado) {
                escribirNotificacion("Tu información financiera ha sido actualizada.");
                JOptionPane.showMessageDialog(this, "Estado de matrícula actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el estado de matrícula.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == actualizarValor) {
            String nuevoValorTexto = txtValorMatricula.getText();
            try {
                Double.parseDouble(nuevoValorTexto);
                boolean actualizado = Secretaria.actualizarInformacion(nombreEstudiante, 5, nuevoValorTexto);
                if (actualizado) {
                    escribirNotificacion("Tu información financiera ha sido actualizada.");
                    JOptionPane.showMessageDialog(this, "Monto de matrícula actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al actualizar el monto de matrícula.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor, ingresa un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == volver) {
            MenuPorRol MR = new MenuPorRol(usuario);
            this.setVisible(false);
            MR.setVisible(true);
        }
    }

    private void escribirNotificacion(String mensaje) {
        try (FileWriter writer = new FileWriter("notificaciones_acudiente.txt", true)) {
            writer.write(mensaje + "\n");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}