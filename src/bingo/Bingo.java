package bingo;

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

    public static void jugarBingo() {
        SwingUtilities.invokeLater(() -> {
            BingoCarton carton = new BingoCarton();
            BingoJuego juego = new BingoJuego(carton);
            BingoGUI gui = new BingoGUI(null, juego); // null porque vienes desde consola
            gui.setVisible(true); // Modal: pausa hasta cerrar
        });
    }

    public static void clearConsole() {
    System.out.print("\033[H\033[2J");
    System.out.flush();
    
    
    Scanner scanner = new Scanner(System.in);
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
                        jugarBingo();
//                        GestionClientes.buscarCliente();
                    }
                    case 2 -> {
                        Menu.menuClientes();
                        System.out.print("Introduzca una opcion: ");
                        int op2 = entrada.nextInt();
                        entrada.nextLine();
                        switch (op2) { // Switch con las opciones del menu
                            case 1 ->
                                GestionClientes.agregarCliente();// Agrega clientes
                            case 2 ->
                                GestionClientes.buscarCliente();// Busca clientes
                            case 3 ->
                                GestionClientes.modificarcliente();// Modifica clientes
                            case 4 ->
                                GestionClientes.eliminarCliente();// Elimina clientes
                            case 5 ->
                                GestionClientes.mostrarClientes();// Mostrar clientes
                            case 6 -> {
                            }
                            default ->
                                System.out.println("Opcion no valida, debe introducir una opcion de la lista");
                        }
                    }
                    case 3 -> {
                        JFrame ventana = new JFrame("Bingo");
                        ventana.setSize(400, 200);
                        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        ventana.setLocationRelativeTo(null);
                        ventana.setVisible(true);

                        // Mostrar créditos al final, por ejemplo:
                        CreditosDialog.mostrar(ventana);
                    }
                    case 4 ->
                        System.out.println("Se va a cerrar el programa");
                    default ->
                        System.out.println("Opcion no valida, debe introducir una opcion de la lista");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error. Debe introducir un numero.\n");
                entrada.nextLine();
            } catch (Exception e) {
                System.out.println("Ha ocurrido un error inesperado" + e.getMessage());
            }
        } while (op != 6);
    }
}
