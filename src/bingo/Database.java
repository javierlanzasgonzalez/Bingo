package bingo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Database {

    public static void SQLAgregarCliente() throws SQLException {
        Scanner entrada = new Scanner(System.in);
        try (Connection conexion = ConexionBD.getConnection()) {
            if (conexion != null) {
                try {
                    PreparedStatement stmt = conexion.prepareStatement("INSERT INTO clientes VALUES (?, ?, ?, ?, ?, ?)");
                    stmt.setString(1, GestionClientes.introducirDNI(entrada));
                    stmt.setString(2, GestionClientes.introducirNombre(entrada));
                    stmt.setString(3, GestionClientes.introducirApellido1(entrada));
                    stmt.setString(4, GestionClientes.introducirApellido2(entrada));
                    stmt.setString(5, GestionClientes.introducirFecha(entrada));
                    stmt.setInt(6, 0);
                    int update = stmt.executeUpdate();
                    System.out.println((update == 1) ? "Se ha creado el cliente correctamente" : "No se ha podido crear el cliente");
                } catch (SQLException e) {
                    System.out.println("Error en la consulta: " + e.getMessage());
                }
            }
        }
    }

    public static void SQLMostrarClientes() throws SQLException {
        try (Connection conexion = ConexionBD.getConnection()) {
            if (conexion != null) {
                try {
                    PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM clientes");
                    ResultSet resultado = stmt.executeQuery();
                    System.out.println("----- LISTADO DE CLIENTES -----------------------------------------\n");
                    System.out.printf("%-15s %-15s %-20s %-20s %-15s\n",
                            "DNI", "Nombre", "Apellidos", "Fecha de Nacimiento", "Partidas Ganadas");
                    while (resultado.next()) {
                        String dni = resultado.getString(1);
                        String nombre = resultado.getString(2);
                        String apellido1 = resultado.getString(3);
                        String apellido2 = resultado.getString(4);
                        String fechaNacimiento = resultado.getString(5);
                        String partidasGanadas = resultado.getString(6);
                        String apellidos = apellido1 + " " + apellido2;

                        System.out.printf("%-15s %-15s %-20s %-27s %-15s\n",
                                dni, nombre, apellidos, fechaNacimiento, partidasGanadas);
                    }
                    System.out.println("------------------------------------------------------------------");
                } catch (SQLException e) {
                    System.out.println("Error en la consulta: " + e.getMessage());
                }
            }
        }
    }
}
