import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import Usuarios.*;
import java.util.ArrayList;

public class PagarMatricula extends JFrame implements ActionListener {
    private Container contenedor;
    private BotonPersonalizado volver, pagar;
    private JLabel titulo, lblEstudiante, lblMonto, lblValorMonto, lblMetodoPago, lblInfoPago;
    private JRadioButton rbTransferencia, rbPresencial;
    private ButtonGroup grupoPago;
    private User usuario;
    private Estudiante estudiante;
    private ArrayList<Estudiante> estudiantes;

    public PagarMatricula(User usuario) {
        this.usuario = usuario;
        this.estudiantes = Estudiante.estudiantes;

        Acudiente acudiente = (Acudiente) usuario;
        this.estudiante = buscarEstudiante(acudiente.getNameEstudiante());

        if (estudiante == null) {
            JOptionPane.showMessageDialog(this, "No se encontró un estudiante asociado a este usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            dispose();
            return;
        }

        contenedor = getContentPane();
        contenedor.setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        contenedor.setBackground(new Color(80, 120, 200)); 

        JPanel panelPago = new JPanel();
        panelPago.setLayout(null);
        panelPago.setBackground(Color.WHITE);
        panelPago.setBounds(250, 80, 500, 350);
        panelPago.setBorder(BorderFactory.createLineBorder(new Color(50, 50, 50), 2));
        contenedor.add(panelPago);

        titulo = new JLabel("Pago de Matrícula", SwingConstants.CENTER);
        titulo.setBounds(50, 10, 400, 30);
        titulo.setFont(new Font("Arial Black", Font.BOLD, 22));
        panelPago.add(titulo);

        JSeparator separador = new JSeparator();
        separador.setBounds(20, 50, 460, 2);
        panelPago.add(separador);

        lblEstudiante = new JLabel("Estudiante: " + estudiante.getNombre());
        lblEstudiante.setBounds(20, 70, 460, 25);
        lblEstudiante.setFont(new Font("Arial", Font.PLAIN, 18));
        panelPago.add(lblEstudiante);

        lblMonto = new JLabel("Monto a pagar:");
        lblMonto.setBounds(20, 110, 200, 30);
        lblMonto.setFont(new Font("Arial", Font.BOLD, 18));
        panelPago.add(lblMonto);

        lblValorMonto = new JLabel();
        lblValorMonto.setBounds(220, 110, 260, 30);
        lblValorMonto.setFont(new Font("Arial", Font.BOLD, 18));

        if (estudiante.getValorMatricula() == -1) {
            lblValorMonto.setText("Monto no disponible");
            lblValorMonto.setForeground(Color.RED);
        } else {
            lblValorMonto.setText("$ " + estudiante.getValorMatricula());
            lblValorMonto.setForeground(new Color(0, 128, 0));
        }

        panelPago.add(lblValorMonto);

        lblMetodoPago = new JLabel("Método de Pago:");
        lblMetodoPago.setBounds(20, 160, 200, 25);
        lblMetodoPago.setFont(new Font("Arial", Font.BOLD, 16));
        panelPago.add(lblMetodoPago);

        rbTransferencia = new JRadioButton("Transferencia Bancaria");
        rbTransferencia.setBounds(20, 190, 200, 25);
        rbTransferencia.setBackground(Color.WHITE);
        rbTransferencia.addActionListener(this);

        rbPresencial = new JRadioButton("Pago en la Institución");
        rbPresencial.setBounds(20, 220, 200, 25);
        rbPresencial.setBackground(Color.WHITE);
        rbPresencial.addActionListener(this);

        grupoPago = new ButtonGroup();
        grupoPago.add(rbTransferencia);
        grupoPago.add(rbPresencial);

        panelPago.add(rbTransferencia);
        panelPago.add(rbPresencial);

        lblInfoPago = new JLabel("", SwingConstants.CENTER);
        lblInfoPago.setBounds(20, 260, 460, 25);
        lblInfoPago.setFont(new Font("Arial", Font.BOLD, 14));
        lblInfoPago.setForeground(new Color(200, 50, 50));
        panelPago.add(lblInfoPago);

        pagar = new BotonPersonalizado("Pagar");
        pagar.setBounds(300, 310, 150, 40);
        pagar.setFont(new Font("Arial", Font.BOLD, 16));
        pagar.addActionListener(this);
        panelPago.add(pagar);

        volver = new BotonPersonalizado("Volver");
        volver.setBounds(400, 470, 200, 50);
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
        return null;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == volver) {
            MenuPorRol MR = new MenuPorRol(usuario);
            this.setVisible(false);
            MR.setVisible(true);
        } else if (e.getSource() == rbTransferencia) {
            lblInfoPago.setText("Cuenta Bancaria: 1234-5678-90 (Banco X)");
        } else if (e.getSource() == rbPresencial) {
            lblInfoPago.setText("Horarios: 8:00 AM - 4:00 PM en la Institución");
        } else if (e.getSource() == pagar) {
            if (rbTransferencia.isSelected() || rbPresencial.isSelected()) {
                // Cambiar estado de matrícula y monto a -1
                estudiante.setValorMatricula(-1);
                estudiante.setMatriculaPaga(true);

                JOptionPane.showMessageDialog(this, "Pago realizado correctamente.\nEstado actualizado a 'paga'.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                this.setVisible(false);
                new MenuPorRol(usuario).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione un método de pago.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}