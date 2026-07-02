
import javax.swing.*;
import java.awt.*;

class BotonPersonalizado extends JButton {
    public BotonPersonalizado(String texto) {
        super(texto);
        setFont(new Font("Arial Black", Font.BOLD, 18)); // Fuente grande y elegante
        setBackground(new Color(10, 36, 114)); // Azul oscuro #0A2472
        setForeground(Color.WHITE); // Texto blanco
        setFocusPainted(false);
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true)); // Borde redondeado
    }
}