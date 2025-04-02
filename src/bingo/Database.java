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
                    String codigo = GestionClientes.introducirDNI(entrada);
                    stmt.setString(1, codigo);
                    if (!SQLComprobarCliente(codigo)) {
                        stmt.setString(2, GestionClientes.introducirNombre(entrada));
                        stmt.setString(3, GestionClientes.introducirApellido1(entrada));
                        stmt.setString(4, GestionClientes.introducirApellido2(entrada));
                        stmt.setString(5, GestionClientes.introducirFecha(entrada));
                        stmt.setInt(6, 0);

                        boolean update = stmt.execute();
                        System.out.println((update) ? "No se ha podido crear el cliente" : "Se ha creado el cliente correctamente");
                    } else {
                        System.out.println("El cliente con ese DNI ya existe.");
                    }
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

    /**
     * Funcion para comprobar si existe el cliente y devuelve un booleano
     *
     * @param codigo
     * @return boolean
     * @throws java.sql.SQLException
     */
    public static boolean SQLComprobarCliente(String codigo) throws SQLException {
        try (Connection conexion = ConexionBD.getConnection()) {
            if (conexion != null) {
                try {
                    PreparedStatement stmt = conexion.prepareStatement("SELECT * FROM clientes WHERE dni=?");
                    stmt.setString(1, codigo);
                    ResultSet resultado = stmt.executeQuery();
                    while (resultado.next()) {
                        String dni = resultado.getString(1);
                        if (dni.equals(codigo)) {
                            return true;
                        }
                    }
                } catch (SQLException e) {
                    System.out.println("Error. Debe introducir un numero.\n");
                }
            }
        }
        return false;
    }

    /**
     * Metodo para eliminar un cliente
     *
     * @throws java.sql.SQLException
     */
    public static void SQLEliminarCliente() throws SQLException {
        Scanner entrada = new Scanner(System.in);
        try (Connection conexion = ConexionBD.getConnection()) {
            if (conexion != null) {
                try {
                    PreparedStatement stmt = conexion.prepareStatement("DELETE FROM clientes WHERE dni=?");
                    String codigo = GestionClientes.introducirDNI(entrada);
                    stmt.setString(1, codigo);
                    if (SQLComprobarCliente(codigo)) {
                        boolean update = stmt.execute();
                        System.out.println((update) ? "No se ha podido eliminar el cliente" : "Se ha eliminado el cliente correctamente");
                    } else {
                        System.out.println("El cliente que desea eliminar no existe.");
                    }
                } catch (SQLException e) {
                    System.out.println("Error en la consulta: " + e.getMessage());
                }
            }
        }
    }

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
