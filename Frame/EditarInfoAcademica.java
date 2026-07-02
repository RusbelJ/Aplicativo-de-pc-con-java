import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import Usuarios.*;

public class EditarInfoAcademica extends JFrame implements ActionListener {
    private Container contenedor;
    private JButton volver, actualizar;
    private JLabel titulo, lblSeleccion, lblDato, lblNuevo;
    private JComboBox<String> comboEstudiantes, comboDatos;
    private JTextField txtNuevoDato;
    private User usuario;
    private ArrayList<Estudiante> estudiantes;

    public EditarInfoAcademica(User usuario) {
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

        titulo = new JLabel("Editar Información Académica", SwingConstants.CENTER);
        titulo.setBounds(200, 20, 600, 50);
        titulo.setForeground(Color.WHITE);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 30));
        panelPrincipal.add(titulo);

        lblSeleccion = new JLabel("Selecciona un estudiante:");
        lblSeleccion.setBounds(300, 100, 400, 30);
        lblSeleccion.setFont(new Font("Arial", Font.BOLD, 18));
        lblSeleccion.setForeground(Color.WHITE);
        panelPrincipal.add(lblSeleccion);

        comboEstudiantes = new JComboBox<>();
        comboEstudiantes.setBounds(300, 140, 400, 30);
        for (Estudiante e : estudiantes) {
            comboEstudiantes.addItem(e.getNombre());
        }
        panelPrincipal.add(comboEstudiantes);

        lblDato = new JLabel("Selecciona la nota a editar:");
        lblDato.setBounds(300, 190, 400, 30);
        lblDato.setFont(new Font("Arial", Font.BOLD, 18));
        lblDato.setForeground(Color.WHITE);
        panelPrincipal.add(lblDato);

        String[] opciones = {"Nota Matemáticas", "Nota Biología", "Nota Inglés", "Nota Sociales"};
        comboDatos = new JComboBox<>(opciones);
        comboDatos.setBounds(300, 230, 400, 30);
        panelPrincipal.add(comboDatos);

        lblNuevo = new JLabel("Ingresa la nueva nota:");
        lblNuevo.setBounds(300, 280, 400, 30);
        lblNuevo.setFont(new Font("Arial", Font.BOLD, 18));
        lblNuevo.setForeground(Color.WHITE);
        panelPrincipal.add(lblNuevo);

        txtNuevoDato = new JTextField();
        txtNuevoDato.setBounds(300, 320, 400, 30);
        panelPrincipal.add(txtNuevoDato);

        actualizar = new BotonPersonalizado("Actualizar");
        actualizar.setBounds(350, 380, 300, 50);
        actualizar.setFont(new Font("Arial", Font.BOLD, 18));
        actualizar.addActionListener(this);
        panelPrincipal.add(actualizar);

        volver = new BotonPersonalizado("Volver");
        volver.setBounds(350, 450, 300, 50);
        volver.setFont(new Font("Arial", Font.BOLD, 18));
        volver.addActionListener(this);
        panelPrincipal.add(volver);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == actualizar) {
            String nombreEstudiante = (String) comboEstudiantes.getSelectedItem();
            int variableEditada = comboDatos.getSelectedIndex() + 1;
            String nuevoDato = txtNuevoDato.getText().trim();
            
            try {
                float nota = Float.parseFloat(nuevoDato);
                if (nota < 0 || nota > 5) {
                    JOptionPane.showMessageDialog(this, "La nota debe estar entre 0 y 5.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean actualizado = Secretaria.actualizarInformacion(nombreEstudiante, variableEditada, nuevoDato);
            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Información actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar la información.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (e.getSource() == volver) {
            MenuPorRol MR = new MenuPorRol(usuario);
            this.setVisible(false);
            MR.setVisible(true);
        }
    }
}