package bingo;

import java.awt.HeadlessException;
import java.util.InputMismatchException;
import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Programa para gestionar un Bingo
 *
 * @author Javier Lanzas Gonzalez
 * @author Samuel Donato Muñoz Povedano
 * @version 1.0
 */
public class Bingo {

    public static void jugarBingo(String[] cliente) {
        SwingUtilities.invokeLater(() -> {
            BingoCarton carton = new BingoCarton();
            BingoJuego juego = new BingoJuego(carton);
            BingoGUI gui = new BingoGUI(null, juego, cliente); // null porque vienes desde consola
            gui.setVisible(true); // Modal: pausa hasta cerrar
        });
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public static void systemPause() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine(); // Espera a que el usuario presione Enter
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        int op = 0;
        do {
            try {
                clearConsole();
                Menu.menuPrincipal();// Muestra el header
                System.out.print("Introduzca una opcion: ");
                op = entrada.nextInt();
                entrada.nextLine();
                switch (op) { // Switch con las opciones del menu
                    case 1 -> {
                        String usuario = GestionClientes.buscarCliente();
                        
                        if (usuario != null) {
                            String[] cliente = usuario.split("\\s+");
                            jugarBingo(cliente);
                        }
                        systemPause();
                    }
                    case 2 -> {
                        clearConsole();
                        Menu.menuClientes();
                        System.out.print("Introduzca una opcion: ");
                        int op2 = entrada.nextInt();
                        entrada.nextLine();
                        switch (op2) { // Switch con las opciones del menu
                            case 1 -> {
                                GestionClientes.agregarCliente();// Agrega clientes
                                systemPause();
                            }
                            case 2 -> {
                                GestionClientes.buscarCliente();// Busca clientes
                                systemPause();
                            }
                            case 3 -> {
                                GestionClientes.modificarcliente();// Modifica clientes
                                systemPause();
                            }
                            case 4 -> {
                                GestionClientes.eliminarCliente();// Elimina clientes
                                systemPause();
                            }
                            case 5 -> {
                                GestionClientes.mostrarClientes();// Mostrar clientes
                                systemPause();
                            }
                            case 6 -> {
                            }
                            default -> {
                                System.out.println("Opcion no valida, debe introducir una opcion de la lista");
                                systemPause();
                            }
                        }
                    }
                    case 3 -> {
                        JFrame frame = new JFrame("Créditos");
                        frame.setSize(400, 300);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(false); // o true si quieres ver algo
                        CreditosDialog.mostrar(frame);
                    }
                    case 4 ->
                        System.out.println("Se va a cerrar el programa");
                    default -> {
                        System.out.println("Opcion no valida, debe introducir una opcion de la lista");
                        systemPause();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Error. Debe introducir un numero.\n");
                systemPause();
                entrada.nextLine();
            } catch (HeadlessException e) {
                System.out.println("Ha ocurrido un error inesperado" + e.getMessage());
                systemPause();
            }
        } while (op != 4);
    }
}
