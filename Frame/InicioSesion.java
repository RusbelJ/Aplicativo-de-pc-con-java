import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import Usuarios.User;

public class InicioSesion extends JFrame implements ActionListener {

    private Container contenedor;
    private JButton entrar, volver;
    private JLabel textoInicial, textoContraseña, imagenIcono, fondo, textoUsuario;
    private ImageIcon icono, imagenFondo;
    private JTextField usuarioEntrada, contraseñaEntrada;

    public InicioSesion(){
        contenedor = getContentPane();
        contenedor.setLayout(null);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);

        imagenFondo = new ImageIcon(getClass().getResource("/Imagenes/fondoInicio.png"));
        fondo = new JLabel(imagenFondo);
        fondo.setBounds(0, 0, 1000, 600);
        contenedor.add(fondo);

        icono = new ImageIcon(getClass().getResource("/Imagenes/iconosesion.png"));
        imagenIcono = new JLabel(icono);
        imagenIcono.setBounds(0, 0, 500, 512);
        fondo.add(imagenIcono);

        textoInicial = new JLabel("Inicio de sesión");
        textoInicial.setBounds(120, 450, 400, 50);
        textoInicial.setForeground(Color.WHITE);
        textoInicial.setFont(new Font("Arial Black", Font.BOLD, 30));
        fondo.add(textoInicial);

        textoUsuario = new JLabel("Usuario:");
        textoUsuario.setBounds(500, 130, 400, 50);
        textoUsuario.setForeground(Color.GRAY);
        textoUsuario.setFont(new Font("Arial Black", Font.BOLD, 30));
        fondo.add(textoUsuario);

        usuarioEntrada = new JTextField(15);
        usuarioEntrada.setFont(new Font("Arial", Font.PLAIN, 18));
        usuarioEntrada.setBackground(new Color(247,242,246,255));
        usuarioEntrada.setForeground(Color.BLACK);
        usuarioEntrada.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        usuarioEntrada.setBounds(500, 180, 400, 50);
        fondo.add(usuarioEntrada);

        textoContraseña = new JLabel("Contraseña:");
        textoContraseña.setBounds(500, 230, 400, 50);
        textoContraseña.setForeground(Color.GRAY);
        textoContraseña.setFont(new Font("Arial Black", Font.BOLD, 30));
        fondo.add(textoContraseña);

        contraseñaEntrada = new JTextField(15);
        contraseñaEntrada.setFont(new Font("Arial", Font.PLAIN, 18));
        contraseñaEntrada.setBackground(new Color(247,242,246,255));
        contraseñaEntrada.setForeground(Color.BLACK);
        contraseñaEntrada.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contraseñaEntrada.setBounds(500, 280, 400, 50);
        fondo.add(contraseñaEntrada);

        entrar = new BotonPersonalizado("Entrar");
        entrar.setBounds(500, 350, 190, 50);
        entrar.addActionListener(this);
        fondo.add(entrar);

        volver = new BotonPersonalizado("Volver");
        volver.setBounds(710, 350, 190, 50);
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
        else if (e.getSource() == entrar){
            String usuario = usuarioEntrada.getText();
            String contraseña = contraseñaEntrada.getText();

            if (usuario.trim().isEmpty() || contraseña.trim().isEmpty()){
                JOptionPane.showMessageDialog(null, "Error: Campos sin llenar", "Error", JOptionPane.ERROR_MESSAGE);
            }
            else{
                boolean condicion = false;
                for (User u : User.usuarios) {
                    if (u.getName().equals(usuario) && u.getPassword().equals(contraseña)) {
                        JOptionPane.showMessageDialog(null, "Login exitoso. Rol: " + u.getRol(), "Éxito", JOptionPane.INFORMATION_MESSAGE);
                        MenuPorRol VR = new MenuPorRol(u);
                        this.setVisible(false);
                        VR.setVisible(true);
                        condicion = true;
                        break;
                    }
                }
                if (condicion == false){
                    JOptionPane.showMessageDialog(null, "Credenciales incorrectas.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}