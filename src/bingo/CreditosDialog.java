package bingo;

import javax.swing.*;
import java.awt.*;

public class CreditosDialog extends JWindow {

    public CreditosDialog(JFrame parent) {
        super(parent);
        setAlwaysOnTop(true); // Que aparezca delante de todo
        setBackground(new Color(0, 0, 0, 0)); // Transparente si quieres hacerlo más pro

        // Panel principal con fondo blanco y borde redondeado opcional
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
            }
        };
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        // Texto animado
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
        textArea.setEditable(false);
        textArea.setOpaque(false);
        textArea.setFocusable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        panel.add(textArea, BorderLayout.CENTER);
        add(panel);

        // Texto completo
        String textoCreditos = """
                🎉 Créditos del Bingo 🎉

                Este programa ha sido desarrollado con
                ilusión, esfuerzo y muchas ganas de aprender
                por:

                👨‍💻  Javier Lanzas González
                👨‍💻  Samuel Donato Muñoz Povedano

                ¡Gracias por jugar!
                ¡Buena suerte y que cante línea el mejor! 🏆
                """;

        // Calcular tamaño según el texto
        textArea.setText(textoCreditos);
        textArea.setSize(400, Short.MAX_VALUE);
        Dimension preferredSize = textArea.getPreferredSize();
        setSize(preferredSize.width + 60, preferredSize.height + 60);
        textArea.setText(""); // Limpiar para animación

        // Centrar en pantalla
        setLocationRelativeTo(null);

        // Mostrar ventana
        setVisible(true);

        // Animar texto (efecto máquina de escribir)
        new Thread(() -> {
            for (char c : textoCreditos.toCharArray()) {
                textArea.append(String.valueOf(c));
                try {
                    Thread.sleep(40); // velocidad de escritura
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Esperar unos segundos y cerrar
            try {
                Thread.sleep(4000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            dispose();
        }).start();
    }

    public static void mostrar(JFrame parent) {
        new CreditosDialog(parent);
    }
}