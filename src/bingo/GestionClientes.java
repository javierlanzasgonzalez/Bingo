package bingo;

import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class GestionClientes {
    
    /**
     * Metodo para mostrar el header del programa
     */
    public static void header() {
        System.out.println("===================================================");
        System.out.println("||                                               ||");
        System.out.println("||  ██████╗ ██╗███╗   ██╗ ██████╗  ██████╗       ||");
        System.out.println("||  ██╔══██╗██║████╗  ██║██╔════╝ ██╔═══██╗      ||");
        System.out.println("||  ██████╔╝██║██╔██╗ ██║██║  ███╗██║   ██║      ||");
        System.out.println("||  ██╔══██╗██║██║╚██╗██║██║   ██║██║   ██║      ||");
        System.out.println("||  ██████╔╝██║██║ ╚████║╚██████╔╝╚██████╔╝      ||");
        System.out.println("||  ╚═════╝ ╚═╝╚═╝  ╚═══╝ ╚═════╝  ╚═════╝       ||");
        System.out.println("||                                               ||");
        System.out.println("||              ¡Bienvenido al BINGO!            ||");
        System.out.println("||                                               ||");
        System.out.println("||               1. Jugar Bingo                  ||");
        System.out.println("||               2. Ver reglas                   ||");
        System.out.println("||               3. Créditos                     ||");
        System.out.println("||               4. Salir                        ||");
        System.out.println("||                                               ||");
        System.out.println("===================================================");
    }

    /**
     * Metodo para mostrar el menu principal
     */
    public static void menu() {
        System.out.println("1. Agregar alumno");
        System.out.println("2. Buscar alumno");
        System.out.println("3. Modificar alumno");
        System.out.println("4. Eliminar alumno");
        System.out.println("5. Mostrar alumnos\n");
        System.out.println("6. Salir\n\n");
    }

    /**
     * Metodo para mostrar el menu del alumno
     */
    public static void menuAlumno() {
        System.out.println("1. Nombre ");
        System.out.println("2. Primer apellido");
        System.out.println("3. Segundo apellido");
        System.out.println("4. Asignatura");
        System.out.println("5. Nota\n");
        System.out.println("6. Volver atras\n\n");
    }

    /**
     * Funcion recursiva para introducir el codigo del alumno verificando que
     * sean 5 digitos
     *
     * @param entrada
     * @return codigo
     */
    public static String introducirCodigo(Scanner entrada) {
        String codigo;
        System.out.print("Introduzca el codigo del alumno: ");
        codigo = entrada.nextLine();
        if (codigo.matches("\\d{5}")) { // Se comprueba que los digitos introducidos son 5
            return codigo;
        }
        System.out.println("El codigo debe tener 5 digitos");
        return introducirCodigo(entrada); // Si no se introducen correctamente, se vuelve a llamar a la misma funcion recursiva
    }

    /**
     * Funcion para introducir el nombre del alumno
     *
     * @param entrada
     * @return nombre
     */
    public static String introducirNombre(Scanner entrada) {
        String nombre;
        System.out.print("Introduzca el nombre: ");
        nombre = entrada.nextLine();

        return nombre;
    }

    /**
     * Funcion para introducir el primer apellido del alumno
     *
     * @param entrada
     * @return apellido1
     */
    public static String introducirApellido1(Scanner entrada) {
        String apellido1;
        System.out.print("Introduzca el primer apellido: ");
        apellido1 = entrada.nextLine();

        return apellido1;
    }

    /**
     * Funcion para introducir el segundo apellido del alumno
     *
     * @param entrada
     * @return apellido2
     */
    public static String introducirApellido2(Scanner entrada) {
        String apellido2;
        System.out.print("Introduzca el segundo apellido: ");
        apellido2 = entrada.nextLine();

        return apellido2;
    }

    /**
     * Funcion para introducir la asignatura
     *
     * @param entrada
     * @return asignatura
     */
    public static String introducirAsignatura(Scanner entrada) {
        String asignatura;
        System.out.print("Introduzca la asignatura: ");
        asignatura = entrada.nextLine();

        return asignatura;
    }

    /**
     * Funcion recursiva para introducir la nota del alumno y se verifica si es
     * una nota entre 0 y 10
     *
     * @param entrada
     * @return
     */
    public static int introducirNota(Scanner entrada) {
        int nota;
        System.out.print("Introduzca la nota: ");
        nota = entrada.nextInt();
        if (nota >= 0 && nota <= 10) { // Se comprueba que la nota sea entre 0 y 10
            return nota;
        }
        System.out.println("La nota debe ser entre 0 y 10");
        return introducirNota(entrada); // Si la nota no es correcta se vuelve a llamar a la misma funcion
    }

    /**
     * Funcion para comprobar si existe el alumno y devuelve un booleano
     *
     * @param codigo
     * @param fichero
     * @return
     */
    public static boolean comprobarAlumno(String codigo, File fichero) {
        try {
            BufferedReader leer = new BufferedReader(new FileReader(fichero));
            String linea;
            if (!fichero.exists()) {
                System.out.println("El fichero no existe");
            } else {
                while ((linea = leer.readLine()) != null) {
                    // Si se encuentra al alumno se devuelve un true
                    if (linea.startsWith(codigo)) {
                        return true;
                    }
                }
            }
            leer.close();
        } catch (InputMismatchException e) {
            System.out.println("Error. Debe introducir un numero.\n");
        } catch (IOException e) {
            System.out.println("Ha ocurrido un error inesperado");
        }
        return false;
    }

    /**
     * Metodo para agregar un alumno
     */
    public static void agregarAlumno() {
        Scanner entrada = new Scanner(System.in);
        File fichero = new File("alumnos.txt");
        String codigo, nombre, apellido1, apellido2, asignatura;
        int nota;
        // Si el fichero no existe muestra el mensaje
        if (!fichero.exists()) {
            System.out.println("No se encuentra el archivo");
        } else {
            try {
                // Se abre el fichero
                BufferedWriter escribir = new BufferedWriter(new FileWriter(fichero, true));
                // Introduccion de datos, se verifica primero si el alumno existe o no, si ya existe sale al menu
                codigo = introducirCodigo(entrada);
                if (!comprobarAlumno(codigo, fichero)) {
                    nombre = introducirNombre(entrada);
                    apellido1 = introducirApellido1(entrada);
                    apellido2 = introducirApellido2(entrada);
                    asignatura = introducirAsignatura(entrada);
                    nota = introducirNota(entrada);
                    // Escribe el fichero concatenado las variables. 
                    escribir.write(codigo + " " + nombre + " " + apellido1 + " " + apellido2
                            + " " + asignatura + " " + nota + "\n");
                    // Se cierra el fichero
                    escribir.close();
                    System.out.println("El alumno se ha creado correctamente");
                } else {
                    System.out.println("Ya existe el alumno");
                }
            } catch (IOException e) {
                System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo para modificar un alumno
     */
    public static void modificarAlumno() {
        Scanner entrada = new Scanner(System.in);
        File fichero = new File("alumnos.txt");
        File temp = new File("temporal.txt");
        // Si el fichero no existe muestra el mensaje
        if (!fichero.exists()) {
            System.out.println("El fichero no existe");
        } else {
            try {
                // Se abren los ficheros de lectura y escritura
                BufferedReader leer = new BufferedReader(new FileReader(fichero));
                BufferedWriter escribir = new BufferedWriter(new FileWriter(temp));
                String linea, codigo;
                int op;
                boolean cambios = false; // Booleano para controlar si se hacen cambios
                boolean encontrado = false; // Booleano para controlar si se encuentra alumno
                codigo = introducirCodigo(entrada);
                while ((linea = leer.readLine()) != null) {
                    if (linea.startsWith(codigo)) {
                        encontrado = true;
                        System.out.println("Se va a modificar el alumno: " + linea);
                        String[] palabra = linea.split(" "); // Se crea un array con las palabras que
                        // tiene la linea para modificarlas despues
                        menuAlumno();
                        System.out.print("Seleccione que dato desea modificar: ");
                        op = entrada.nextInt();
                        entrada.nextLine();
                        switch (op) {
                            // Cada menu se utiliza para introducir el valor nuevo al alumno
                            case 1 ->
                                palabra[1] = introducirNombre(entrada);
                            case 2 ->
                                palabra[2] = introducirApellido1(entrada);
                            case 3 ->
                                palabra[3] = introducirApellido2(entrada);
                            case 4 ->
                                palabra[4] = introducirAsignatura(entrada);
                            case 5 ->
                                palabra[5] = Integer.toString(introducirNota(entrada));
                            case 6 -> {
                                // Al volver atras no se realizaran cambios y el booleano se pone false
                                System.out.println("No se va a realizar ningun cambio");
                                cambios = false;
                            }
                            default -> {
                                System.out.println("Opcion no valida, debe introducir una opcion de la lista");
                            }
                        }
                        if (op != 6) {
                            // Si se realiza algun cambio, se unen todas las palabras de nuevo en un String y se escribe en el fichero
                            escribir.write(String.join(" ", palabra) + "\n");
                            cambios = true;
                        }
                    } else {
                        escribir.write(linea + "\n");
                    }
                }
                // Cierre de los archivos
                escribir.close();
                leer.close();
                // Si no hay cambios se elimina el fichero temporal y si se hacen cambios
                // se elimina el fichero original y se renombra el temporal
                if (cambios) {
                    if (fichero.delete() && temp.renameTo(fichero)) {
                        System.out.println("Datos modificados correctamente");
                    } else {
                        throw new IOException();
                    }
                } else {
                    temp.delete();
                }
                // Si no se encuentra al alumno se muestra el mensaje
                if (!encontrado) {
                    System.out.println("No se ha encontrado el alumno");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error. Debe introducir un numero.\n");
                entrada.nextLine();
            } catch (IOException e) {
                System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo para eliminar un alumno
     */
    public static void eliminarAlumno() {
        Scanner entrada = new Scanner(System.in);
        File fichero = new File("alumnos.txt");
        File temp = new File("temporal.txt");
        // Si el fichero no existe muestra el mensaje
        if (!fichero.exists()) {
            System.out.println("El fichero no existe");
        } else {
            try {
                // Se abren los ficheros de lectura y escritura
                BufferedReader leer = new BufferedReader(new FileReader(fichero));
                BufferedWriter escribir = new BufferedWriter(new FileWriter(temp));
                String linea, codigo;
                boolean encontrado = false; // Booleano para controlar si se encuentra alumno
                boolean borrado = false; // Booleano para controlar si se ha borrado el alumno
                codigo = introducirCodigo(entrada);
                while ((linea = leer.readLine()) != null) {
                    if (linea.startsWith(codigo)) {
                        // Si se encuentra el alumno se borra omitiendo la linea del alumno
                        encontrado = true;
                        borrado = true;
                        System.out.println("Se va a eliminar el alumno: " + linea);
                    } else {
                        // Se sigue escribiendo las lineas en el archivo
                        escribir.write(linea + "\n");
                    }
                }
                // Cierre de los archivos
                escribir.close();
                leer.close();
                // Si no se encuentra el alumno se muestra el mensaje
                if (!encontrado) {
                    System.out.println("No se encuentra el alumno");
                }
                // Si se elimina el alumno se muestra el mensaje y
                // se elimina el fichero original y se renombra el temporal
                if (borrado) {
                    if (fichero.delete() && temp.renameTo(fichero)) {
                        System.out.println("Se ha eliminado el alumno correctamente");
                    } else {
                        throw new IOException();
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("Error. Debe introducir un numero.\n");
                entrada.nextLine();
            } catch (IOException e) {
                System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo para buscar un alumno
     */
    public static void buscarAlumno() {
        Scanner entrada = new Scanner(System.in);
        File fichero = new File("alumnos.txt");
        // Si el fichero no existe muestra el mensaje
        if (!fichero.exists()) {
            System.out.println("El fichero no existe");
        } else {
            try {
                BufferedReader leer = new BufferedReader(new FileReader(fichero));
                String linea, codigo;
                boolean encontrado = false;
                codigo = introducirCodigo(entrada);
                while ((linea = leer.readLine()) != null) {
                    if (linea.startsWith(codigo)) // Si se encuentra el alumno se muestran los datos del mismo
                    {
                        encontrado = true;
                        System.out.println("\nAlumno: " + linea + "\n");
                    }
                }
                // Cierre del fichero
                leer.close();
                if (!encontrado) {
                    System.out.println("No se encuentra el alumno");
                }
            } catch (IOException e) {
                System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
            }
        }
    }

    /**
     * Metodo para mostrar los alumnos
     */
    public static void mostrarAlumnos() {
        File fichero = new File("alumnos.txt");
        // Si el fichero no existe muestra el mensaje
        if (!fichero.exists()) {
            System.out.println("El fichero no existe");
        } else {
            try {
                BufferedReader leer = new BufferedReader(new FileReader(fichero));
                String linea;
                // Se listan todos los alumnos del fichero
                System.out.println("----- LISTADO DE ALUMNOS -----------------------------------------");
                while ((linea = leer.readLine()) != null) {
                    System.out.println(linea);
                }
                System.out.println("------------------------------------------------------------------");
                // Cierre del archivo
                leer.close();
            } catch (IOException e) {
                System.out.println("Ha ocurrido un error inesperado: " + e.getMessage());
            }
        }
    }
    
    public static void gestionClientes(){
    Scanner entrada = new Scanner(System.in);
        int op = 0;
        do {
            try {
                header(); // Muestra el header
                menu(); // Muestra el menu
                System.out.print("Introduzca una opcion: ");
                op = entrada.nextInt();
                entrada.nextLine();
                switch (op) { // Switch con las opciones del menu
                    case 1 ->
                        GestionClientes.agregarAlumno(); // Agrega alumnos
                    case 2 ->
                        GestionClientes.buscarAlumno(); // Busca alumnos
                    case 3 ->
                        GestionClientes.modificarAlumno(); // Modifica alumnos
                    case 4 ->
                        GestionClientes.eliminarAlumno(); // Elimina alumnos
                    case 5 ->
                        GestionClientes.mostrarAlumnos(); // Mostrar alumnos
                    case 6 ->
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