import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Usuarios.*;
import java.util.ArrayList;

public class FacturaMatricula extends JFrame implements ActionListener {
    private Container contenedor;
    private BotonPersonalizado volver;
    private JLabel titulo, lblEstudiante, lblMonto, lblValorMonto;
    private User usuario;
    private Estudiante estudiante;
    private ArrayList<Estudiante> estudiantes;

    public FacturaMatricula(User usuario) {
        this.usuario = usuario;
        this.estudiantes = Estudiante.estudiantes;

        Acudiente acudiente = (Acudiente) usuario;
        // Buscar al estudiante asociado al usuario
        this.estudiante = buscarEstudiante(acudiente.getNameEstudiante());

        // Si no encuentra al estudiante, muestra un mensaje y cierra la ventana
        if (estudiante == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un estudiante asociado a este usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        // Configuración de la ventana
        contenedor = getContentPane();
        contenedor.setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        contenedor.setBackground(new Color(80, 120, 200)); // Fondo azul oscuro

        // Panel que simula la factura
        JPanel panelFactura = new JPanel();
        panelFactura.setLayout(null);
        panelFactura.setBackground(Color.WHITE);
        panelFactura.setBounds(250, 100, 500, 300);
        panelFactura.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 50, 50), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        contenedor.add(panelFactura);

        titulo = new JLabel("Factura de Matrícula", SwingConstants.CENTER);
        titulo.setBounds(50, 10, 400, 30);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 22));
        titulo.setForeground(new Color(30, 30, 30));
        panelFactura.add(titulo);

        JSeparator separador = new JSeparator();
        separador.setBounds(20, 50, 460, 2);
        panelFactura.add(separador);

        lblEstudiante = new JLabel("Estudiante: " + estudiante.getNombre());
        lblEstudiante.setBounds(20, 80, 460, 25);
        lblEstudiante.setFont(new Font("Arial", Font.PLAIN, 18));
        lblEstudiante.setForeground(new Color(50, 50, 50));
        panelFactura.add(lblEstudiante);

        lblMonto = new JLabel("Valor a pagar:");
        lblMonto.setBounds(20, 160, 200, 30);
        lblMonto.setFont(new Font("Arial", Font.BOLD, 18));
        lblMonto.setForeground(new Color(50, 50, 50));
        panelFactura.add(lblMonto);

        lblValorMonto = new JLabel();
        lblValorMonto.setBounds(220, 160, 260, 30);
        lblValorMonto.setFont(new Font("Arial", Font.BOLD, 18));

        // Si el monto de matrícula es -1, mostrar "Factura no disponible"
        if (estudiante.getValorMatricula() == -1) {
            lblValorMonto.setText("Monto a pagar no disponible");
            lblValorMonto.setForeground(Color.RED);
        } else {
            lblValorMonto.setText("$ " + estudiante.getValorMatricula());
            lblValorMonto.setForeground(new Color(0, 128, 0)); // Verde oscuro para montos disponibles
        }

        panelFactura.add(lblValorMonto);

        // Botón personalizado
        volver = new BotonPersonalizado("Volver");
        volver.setBounds(400, 450, 200, 50);
        volver.setFont(new Font("Arial", Font.BOLD, 18));
        volver.addActionListener(this);
        contenedor.add(volver);
    }

    private Estudiante buscarEstudiante(String nombreUsuario) {
        for (Estudiante e : estudiantes) {
            if (e.getNombre().equalsIgnoreCase(nombreUsuario)) {
                return e;
            }
        }
        return null; // Retorna null si no encuentra al estudiante
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == volver) {
            MenuPorRol MR = new MenuPorRol(usuario);
            this.setVisible(false);
            MR.setVisible(true);
        }
    }
}
