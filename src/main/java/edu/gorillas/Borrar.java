package edu.gorillas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Borrar {

    static Scanner sc = new Scanner(System.in);

    public static void mainBorrar(Statement sentencia, Scanner sc)
    {
        int op = 0;

        do
        {
            op = Menu.menuBorrar(sc);

            switch(op)
            {
                case 1:
                    borrarLibro(sentencia);
                    break;

                case 2:
                    borrarAutor(sentencia);
                    break;

            }

        }while(op != 3);
    }

    private static void borrarLibro(Statement sentencia) {
        System.out.println("Dame nombre del libro que deseas eliminar");
        String nombreLibro = sc.nextLine();

        try {
            // Verificar si el libro existe
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM Libros WHERE Titulo = '" + nombreLibro + "'");

            if (resultado.next()) {
                // Si el libro existe, solicitar confirmación para eliminar
                System.out.println("¿Seguro que deseas eliminar el libro? (s para sí, n para no)");
                String confirmacion = sc.nextLine();

                if (confirmacion.equalsIgnoreCase("s")) {
                    // Eliminar el libro de la base de datos
                    sentencia.executeUpdate("DELETE FROM Libros WHERE Titulo = '" + nombreLibro + "'");
                    System.out.println("Libro eliminado correctamente.");
                } else {
                    System.out.println("Operación cancelada.");
                }
            } else {
                // El libro no existe en la base de datos
                System.out.println("Error: El libro '" + nombreLibro + "' no existe en la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al eliminar el libro.");
            e.printStackTrace();
        }
    }

    private static void borrarAutor(Statement sentencia) {
        System.out.println("Dame el nombre del autor que deseas eliminar");
        String nombreAutor = sc.nextLine();

        try {
            // Verificar si el autor existe
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM Autores WHERE Nombre = '" + nombreAutor + "'");

            if (resultado.next()) {
                // Si el autor existe, obtener su DNI
                String dniAutor = resultado.getString("DNI");

                // Eliminar los libros asociados al autor
                sentencia.executeUpdate("DELETE FROM Libros WHERE Autor = '" + dniAutor + "'");
                System.out.println("Libros del autor eliminados correctamente.");

                // Eliminar al autor
                sentencia.executeUpdate("DELETE FROM Autores WHERE Nombre = '" + nombreAutor + "'");
                System.out.println("Autor eliminado correctamente.");
            } else {
                // El autor no existe en la base de datos
                System.out.println("Error: El autor '" + nombreAutor + "' no existe en la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al eliminar el autor.");
            e.printStackTrace();
        }
    }

}
