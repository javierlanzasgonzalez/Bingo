package bingo;

import java.awt.HeadlessException;
import java.sql.SQLException;
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

    /**
     * Inicia el juego del Bingo con un cliente dado. Lanza la interfaz gráfica en el hilo de eventos de Swing.
     *
     * @param cliente Array de Strings con la información del cliente.
     */
    public static void jugarBingo(String[] cliente) {
        SwingUtilities.invokeLater(() -> {
            BingoCarton carton = new BingoCarton();                 // Crear un nuevo cartón
            BingoJuego juego = new BingoJuego(carton);             // Crear un nuevo juego con ese cartón
            BingoGUI gui = new BingoGUI(null, juego, cliente);     // Crear la interfaz gráfica
            gui.setVisible(true);                                  // Mostrar interfaz (bloquea hasta cerrarla)
        });
    }

    /**
     * Limpia la consola
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    /**
     * Pausa el sistema hasta que el usuario presione Enter.
     */
    public static void systemPause() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Presiona Enter para continuar...");
        scanner.nextLine(); // Espera entrada del usuario
    }

    /**
     * Método principal del programa. Muestra un menú interactivo en consola para gestionar el bingo.
     *
     * @param args
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        Scanner entrada = new Scanner(System.in);
        int op = 0;

        // Bucle principal del programa
        do {
            try {
                clearConsole();              // Limpia consola
                Menu.menuPrincipal();        // Muestra el menú principal
                System.out.print("Introduzca una opcion: ");
                op = entrada.nextInt();      // Lee opción del usuario
                entrada.nextLine();          // Limpia el buffer

                switch (op) {
                    case 1 -> {
                        // Jugar al bingo
                        String usuario = Database.SQLBuscarClientes(); // Buscar cliente

                        if (usuario != null) {
                            String[] cliente=usuario.split("\\s+");
                                 // Dividir datos del cliente por espacios
                            jugarBingo(cliente);                          // Iniciar juego
                        }
                        systemPause(); // Espera después de jugar
                    }
                    case 2 -> {
                        // Menú de gestión de clientes
                        clearConsole();
                        Menu.menuClientes();                             // Mostrar submenú
                        System.out.print("Introduzca una opcion: ");
                        int op2 = entrada.nextInt();                     // Leer subopción
                        entrada.nextLine();                              // Limpiar buffer

                        switch (op2) {
                            case 1 -> {
                                GestionClientes.agregarCliente();        // Añadir cliente
                                systemPause();
                            }
                            case 2 -> {
                                GestionClientes.buscarCliente();         // Buscar cliente
                                systemPause();
                            }
                            case 3 -> {
                                GestionClientes.modificarcliente();      // Modificar cliente
                                systemPause();
                            }
                            case 4 -> {
                                GestionClientes.eliminarCliente();       // Eliminar cliente
                                systemPause();
                            }
                            case 5 -> {
                                GestionClientes.mostrarClientes();       // Mostrar todos los clientes
                                systemPause();
                            }
                            case 6 -> {
                                // Volver al menú anterior
                            }
                            default -> {
                                // Opción inválida en el submenú
                                System.out.println("Opcion no valida, debe introducir una opcion de la lista");
                                systemPause();
                            }
                        }
                    }
                    case 3 -> {
                        // Mostrar créditos con interfaz gráfica
                        JFrame frame = new JFrame("Créditos");
                        frame.setSize(400, 300);
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setLocationRelativeTo(null);
                        frame.setVisible(false); // Ocultar ventana base
                        CreditosDialog.mostrar(frame); // Mostrar diálogo de créditos
                    }
                    case 4 -> {
                        Database.SQLmodificarVictorias(Database.SQLBuscarClientes());  // Test SQL
                        systemPause();
                    }
                    case 5 -> {
                        // Salir del programa
                        System.out.println("Se va a cerrar el programa");
                    }
                    default -> {
                        // Opción inválida en el menú principal
                        System.out.println("Opcion no valida, debe introducir una opcion de la lista");
                        systemPause();
                    }
                }

            } catch (InputMismatchException e) {
                // Captura errores de entrada (cuando no se introduce un número)
                System.out.println("Error. Debe introducir un numero.\n");
                systemPause();
                entrada.nextLine(); // Limpiar buffer después del error
            } catch (HeadlessException e) {
                // Captura errores gráficos si no hay entorno gráfico disponible
                System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
                systemPause();
            }

        } while (op != 5); // Sale cuando el usuario elige la opción 4
    }
}
