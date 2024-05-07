package edu.gorillas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Modificar {

    static Scanner sc = new Scanner(System.in);

    public static void mainModificar(Statement sentencia, Scanner sc)
    {
        int op = 0;

        do
        {

            op = Menu.menuModificar(sc);

            switch (op)
            {
                case 1:
                    modificarLibroPorTitulo(sentencia);
                    break;

                case 2:
                    modificarAutorPorDNI(sentencia);
                    break;
            }

        } while (op != 3);
    }

    private static void modificarLibroPorTitulo(Statement sentencia) {
        System.out.println("Introduce el título del libro que deseas modificar:");
        String tituloLibro = sc.nextLine();

        try {
            // Consultar el libro por título
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM Libros WHERE Titulo = '" + tituloLibro + "'");

            if (resultado.next()) {
                // Si se encuentra el libro, solicitar nuevos datos al usuario
                System.out.println("Introduce el nuevo título del libro:");
                String nuevoTitulo = sc.nextLine();
                System.out.println("Introduce el nuevo precio del libro:");
                float nuevoPrecio = sc.nextFloat();
                sc.nextLine(); // Consumir el salto de línea pendiente

                // Actualizar los datos del libro en la base de datos
                sentencia.executeUpdate("UPDATE Libros SET Titulo = '" + nuevoTitulo + "', Precio = " + nuevoPrecio + " WHERE Titulo = '" + tituloLibro + "'");
                System.out.println("Libro modificado correctamente.");
            } else {
                // Si no se encuentra el libro, mostrar un mensaje de error
                System.out.println("No se encontró ningún libro con el título '" + tituloLibro + "'.");
            }
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al modificar el libro por título.");
            e.printStackTrace();
        }
    }

    private static void modificarAutorPorDNI(Statement sentencia) {
        System.out.println("Introduce el DNI del autor que deseas modificar:");
        String dniAutor = sc.nextLine();

        try {
            // Consultar el autor por DNI
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM Autores WHERE DNI = '" + dniAutor + "'");

            if (resultado.next()) {
                // Si se encuentra el autor, solicitar nuevos datos al usuario
                System.out.println("Introduce el nuevo nombre del autor:");
                String nuevoNombre = sc.nextLine();
                System.out.println("Introduce la nueva nacionalidad del autor:");
                String nuevaNacionalidad = sc.nextLine();

                // Actualizar los datos del autor en la base de datos
                sentencia.executeUpdate("UPDATE Autores SET Nombre = '" + nuevoNombre + "', Nacionalidad = '" + nuevaNacionalidad + "' WHERE DNI = '" + dniAutor + "'");
                System.out.println("Autor modificado correctamente.");
            } else {
                // Si no se encuentra el autor, mostrar un mensaje de error
                System.out.println("No se encontró ningún autor con el DNI '" + dniAutor + "'.");
            }
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al modificar el autor por DNI.");
            e.printStackTrace();
        }
    }

}
