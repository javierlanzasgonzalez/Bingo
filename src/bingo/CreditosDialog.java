package bingo;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class CreditosDialog extends JDialog {

    private BufferedImage backgroundImage;

    public CreditosDialog(JFrame parent) {
        super(parent, "Créditos del Bingo", false);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        // Cargar imagen de fondo
        try {
            backgroundImage = ImageIO.read(getClass().getResource("/bingo/creditos.png")); // asegúrate de que fondo.jpg esté en resources o misma carpeta
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Panel con textura de fondo
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // Área de texto para la animación
        JTextArea textArea = new JTextArea();
        textArea.setFont(new Font("Ubuntu Mono", Font.BOLD, 22));
        textArea.setEditable(false);
        textArea.setOpaque(false); // Para que el fondo sea visible
        textArea.setFocusable(false);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);

        panel.add(textArea, BorderLayout.CENTER);
        add(panel);

        // Texto a mostrar
        String textoCreditos = """
                               
                                     🎉 Créditos del Bingo 🎉

                        Este programa ha sido desarrollado con
                    ilusión, esfuerzo y muchas ganas de aprender

                                      Trabajo realizado por:

                                    Javier Lanzas González
                             Samuel Donato Muñoz Povedano

                                       ¡Gracias por jugar!
                    ¡Buena suerte y que cante bingo el mejor! 🏆
                               
                """;

        textArea.setText(textoCreditos);
        textArea.setSize(500, Short.MAX_VALUE);
        Dimension preferredSize = textArea.getPreferredSize();
        setSize(preferredSize.width + 80, preferredSize.height + 100);
        textArea.setText(""); // limpiar para animación

        setLocationRelativeTo(parent);
        setVisible(true);

        // Animación
        new Thread(() -> {
            for (char c : textoCreditos.toCharArray()) {
                SwingUtilities.invokeLater(() -> textArea.append(String.valueOf(c)));
                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
    }

    public static void mostrar(JFrame parent) {
        new CreditosDialog(parent);
    }
}
