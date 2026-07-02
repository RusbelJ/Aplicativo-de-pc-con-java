import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Usuarios.Acudiente;
import Usuarios.User;


public class Registrar extends JFrame implements ActionListener{
    private Container contenedor;
    private JButton volver, registrar;
    private JLabel fondo,imagenIcono,textoInicial, textoNombre, textoContraseña, textoEstudiante, textoCodigo;
    private JTextField usuarioNombre, usuarioContraseña, estudianteNombre, estudianteCodigo;
    private ImageIcon icono, imagenFondo;

    public Registrar(){
        contenedor = getContentPane();
        contenedor.setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        imagenFondo = new ImageIcon(getClass().getResource("/Imagenes/FondoRegistrar.png"));
        fondo = new JLabel(imagenFondo);
        fondo.setBounds(0, 0, 1000, 600);
        contenedor.add(fondo);

        icono = new ImageIcon(getClass().getResource("/Imagenes/IconoRegistrarse.png"));
        imagenIcono = new JLabel(icono);
        imagenIcono.setBounds(0, 0, 500, 492);
        fondo.add(imagenIcono);

        textoInicial = new JLabel("Registrar Usuario");
        textoInicial.setBounds(88, 390, 400, 50);
        textoInicial.setForeground(Color.WHITE);
        textoInicial.setFont(new Font("Arial Black", Font.BOLD, 32));
        fondo.add(textoInicial);

        textoNombre = new JLabel("Usuario:");
        textoNombre.setBounds(500, 30, 400, 50);
        textoNombre.setForeground(Color.GRAY);
        textoNombre.setFont(new Font("Arial Black", Font.BOLD, 24));
        fondo.add(textoNombre);

        usuarioNombre = new JTextField(15);
        usuarioNombre.setFont(new Font("Arial", Font.PLAIN, 18));
        usuarioNombre.setBackground(new Color(247,242,246,255));
        usuarioNombre.setForeground(Color.BLACK);
        usuarioNombre.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        usuarioNombre.setBounds(500, 80, 400, 50);
        fondo.add(usuarioNombre);

        textoContraseña = new JLabel("Contraseña:");
        textoContraseña.setBounds(500, 130, 400, 50);
        textoContraseña.setForeground(Color.GRAY);
        textoContraseña.setFont(new Font("Arial Black", Font.BOLD, 24));
        fondo.add(textoContraseña);

        usuarioContraseña = new JTextField(15);
        usuarioContraseña.setFont(new Font("Arial", Font.PLAIN, 18));
        usuarioContraseña.setBackground(new Color(247,242,246,255));
        usuarioContraseña.setForeground(Color.BLACK);
        usuarioContraseña.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        usuarioContraseña.setBounds(500, 180, 400, 50);
        fondo.add(usuarioContraseña);

        textoEstudiante = new JLabel("Nombre del Estudiante:");
        textoEstudiante.setBounds(500, 230, 400, 50);
        textoEstudiante.setForeground(Color.GRAY);
        textoEstudiante.setFont(new Font("Arial Black", Font.BOLD, 24));
        fondo.add(textoEstudiante);

        estudianteNombre = new JTextField(15);
        estudianteNombre.setFont(new Font("Arial", Font.PLAIN, 18));
        estudianteNombre.setBackground(new Color(247,242,246,255));
        estudianteNombre.setForeground(Color.BLACK);
        estudianteNombre.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        estudianteNombre.setBounds(500, 280, 400, 50);
        fondo.add(estudianteNombre);

        textoCodigo = new JLabel("Código del Estudiante:");
        textoCodigo.setBounds(500, 330, 400, 50);
        textoCodigo.setForeground(Color.GRAY);
        textoCodigo.setFont(new Font("Arial Black", Font.BOLD, 24));
        fondo.add(textoCodigo);

        estudianteCodigo = new JTextField(15);
        estudianteCodigo.setFont(new Font("Arial", Font.PLAIN, 18));
        estudianteCodigo.setBackground(new Color(247,242,246,255));
        estudianteCodigo.setForeground(Color.BLACK);
        estudianteCodigo.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        estudianteCodigo.setBounds(500, 380, 400, 50);
        fondo.add(estudianteCodigo);

        registrar = new BotonPersonalizado("Registrar");
        registrar.setBounds(500, 450, 190, 50);
        registrar.addActionListener(this);
        fondo.add(registrar);

        volver = new BotonPersonalizado("Volver");
        volver.setBounds(710, 450, 190, 50);
        volver.addActionListener(this);
        fondo.add(volver);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == volver) {
            VentanaInicial Vi = new VentanaInicial();
            Vi.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(false);
            Vi.setVisible(true);
        }
        else if (e.getSource() == registrar){
            String usuario = usuarioNombre.getText();
            String contraseña = usuarioContraseña.getText();
            String nombreEstudiante = estudianteNombre.getText();
            String codigoEstudiante = estudianteCodigo.getText();

            if (usuario.trim().isEmpty() || contraseña.trim().isEmpty() || nombreEstudiante.trim().isEmpty() || codigoEstudiante.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Error: Campos sin llenar", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(!usuario.trim().matches("[a-zA-Z]+")) {
                JOptionPane.showMessageDialog(null, "El nombre de usuario solo debe contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if(Acudiente.registrarAcudiente(usuario, contraseña, nombreEstudiante, codigoEstudiante)) {
                User nuevoUsuario = new Acudiente(usuario, contraseña, nombreEstudiante.trim(), Integer.parseInt(codigoEstudiante));
                User.usuarios.add(nuevoUsuario);
                JOptionPane.showMessageDialog(null, "Acudiente registrado exitosamente.","Registro Exitoso", JOptionPane.INFORMATION_MESSAGE);
                MenuPorRol VR = new MenuPorRol(nuevoUsuario);
                VR.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setVisible(false);
                VentanaInicial VI = new VentanaInicial();
                VI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                VI.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Estudiante no econtrado","Registro Erróneo", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
}
