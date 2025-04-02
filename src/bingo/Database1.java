package bingo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Database1 {
    
    public static String SQLBuscarClientes() throws SQLException {
        Scanner entrada = new Scanner(System.in);
        String cliente = null;
        try (Connection conexion = ConexionBD.getConnection()) {
            if (conexion != null) {
                try {
                    PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM clientes where dni=?");
                    stmt.setString(1, GestionClientes.introducirDNI(entrada));
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
                        cliente = dni + " " + nombre + " " + apellidos + " " + fechaNacimiento + " " + partidasGanadas;
                        System.out.printf("%-15s %-15s %-20s %-27s %-15s\n",
                                dni, nombre, apellidos, fechaNacimiento, partidasGanadas);
                    }
                    System.out.println("------------------------------------------------------------------");
                } catch (SQLException e) {
                    System.out.println("Error en la consulta: " + e.getMessage());
                }
            }
        }
        return cliente;
    }

    public static void SQLmodificarVictorias(String codigo) throws SQLException {
        try (Connection conexion = ConexionBD.getConnection()) {
            if (conexion != null) {
                try {
                    PreparedStatement stmt = conexion.prepareStatement("UPDATE clientes SET partidas=partidas+1 WHERE dni=? ");
                    stmt.setString(1, codigo);
                    stmt.execute();

                } catch (SQLException e) {
                    System.out.println("Error en la consulta: " + e.getMessage());
                }
            }
        }
    }

}
