package bingo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Database {

    public static void SQL() throws SQLException {
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
                    if (update == 1) {
                        System.out.println("Se han insertado los datos");
                    } else {
                        System.out.println("No se han insertado los datos");
                    }
                    
                    stmt = conexion.prepareStatement("SELECT * FROM clientes");
                    stmt.executeQuery();
                    ResultSet resultado = stmt.executeQuery();
                    while (resultado.next()) {
                        System.out.println("DNI: " + resultado.getString(1) + " " + "Nombre: " + resultado.getString(2) + " " + "Apellidos: " + resultado.getString(3)
                                + " " + resultado.getString(4) + " " + "Fecha de nacimiento: " + resultado.getString(5)
                                + " " + "Partidas ganadas: " + resultado.getInt(6));
                    }
                } catch (SQLException e) {
                    System.out.println("Error en la consulta: " + e.getMessage());
                }
            }
        }
    }
}