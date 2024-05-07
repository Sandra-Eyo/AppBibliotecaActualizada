package edu.gorillas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Consultas {

    static Scanner sc = new Scanner(System.in);

    public static void mainConsultas(Statement sentencia, Scanner sc)
    {
        int op = 0;

        do
        {

            op = Menu.menuConsultas(sc);

            switch(op)
            {
                case 1:
                    consultarLibroPorTitulo(sentencia);
                    break;

                case 2:
                    consultarLibrosPorAutor(sentencia);
                    break;

                case 3:
                    listarLibros(sentencia);
                    break;

                case 4:
                    listarAutoresConLibros(sentencia);
                    break;
            }

        }while(op != 5);

    }

    private static void consultarLibroPorTitulo(Statement sentencia) {
        System.out.println("Introduce el título del libro que deseas consultar:");
        String tituloLibro = sc.nextLine();

        try {
            // Consultar el libro por título
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM Libros WHERE Titulo = '" + tituloLibro + "'");

            if (resultado.next()) {
                // Si se encuentra el libro, mostrar sus detalles
                String titulo = resultado.getString("Titulo");
                float precio = resultado.getFloat("Precio");
                String autorDNI = resultado.getString("Autor");

                // Obtener el nombre del autor utilizando el DNI del libro
                ResultSet resultadoAutor = sentencia.executeQuery("SELECT Nombre FROM Autores WHERE DNI = '" + autorDNI + "'");
                resultadoAutor.next();
                String nombreAutor = resultadoAutor.getString("Nombre");

                System.out.println("Título: " + titulo);
                System.out.println("Precio: " + precio);
                System.out.println("Autor: " + nombreAutor);
            } else {
                // Si no se encuentra el libro, mostrar un mensaje de error
                System.out.println("No se encontró ningún libro con el título '" + tituloLibro + "'.");
            }
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al consultar el libro por título.");
            e.printStackTrace();
        }
    }

    private static void consultarLibrosPorAutor(Statement sentencia) {
        System.out.println("Introduce el nombre del autor:");
        String nombreAutor = sc.nextLine();

        try {
            // Consultar el autor por nombre para obtener su DNI
            ResultSet resultadoAutor = sentencia.executeQuery("SELECT DNI FROM Autores WHERE Nombre = '" + nombreAutor + "'");

            if (resultadoAutor.next()) {
                // Si se encuentra el autor, obtener su DNI
                String autorDNI = resultadoAutor.getString("DNI");

                // Consultar los libros del autor utilizando su DNI
                ResultSet resultadoLibros = sentencia.executeQuery("SELECT * FROM Libros WHERE Autor = '" + autorDNI + "'");

                if (resultadoLibros.next()) {
                    // Si el autor tiene libros, mostrar los detalles de cada libro
                    System.out.println("Libros del autor '" + nombreAutor + "':");
                    do {
                        String titulo = resultadoLibros.getString("Titulo");
                        float precio = resultadoLibros.getFloat("Precio");

                        System.out.println("Título: " + titulo + ", Precio: " + precio);
                    } while (resultadoLibros.next());
                } else {
                    // Si el autor no tiene libros, mostrar un mensaje indicando que no hay libros del autor
                    System.out.println("El autor '" + nombreAutor + "' no tiene libros asociados.");
                }
            } else {
                // Si no se encuentra el autor, mostrar un mensaje indicando que el autor no existe
                System.out.println("No se encontró ningún autor con el nombre '" + nombreAutor + "'.");
            }
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al consultar los libros del autor.");
            e.printStackTrace();
        }
    }

    private static void listarLibros(Statement sentencia) {
        try {
            // Consultar todos los libros
            ResultSet resultadoLibros = sentencia.executeQuery("SELECT * FROM Libros");

            if (resultadoLibros.next()) {
                // Si hay libros, mostrar los detalles de cada libro
                System.out.println("Listado de todos los libros:");

                do {
                    String titulo = resultadoLibros.getString("Titulo");
                    float precio = resultadoLibros.getFloat("Precio");
                    String autorDNI = resultadoLibros.getString("Autor");

                    // Obtener el nombre del autor utilizando el DNI del libro
                    ResultSet resultadoAutor = sentencia.executeQuery("SELECT Nombre FROM Autores WHERE DNI = '" + autorDNI + "'");
                    resultadoAutor.next();
                    String nombreAutor = resultadoAutor.getString("Nombre");

                    System.out.println("Título: " + titulo + ", Precio: " + precio + ", Autor: " + nombreAutor);
                } while (resultadoLibros.next());
            } else {
                // Si no hay libros, mostrar un mensaje indicando que no se encontraron libros
                System.out.println("No se encontraron libros en la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al listar los libros.");
            e.printStackTrace();
        }
    }

    private static void listarAutoresConLibros(Statement sentencia) {
        try {
            // Consultar todos los autores
            ResultSet resultadoAutores = sentencia.executeQuery("SELECT * FROM Autores");

            while (resultadoAutores.next()) {
                // Para cada autor, obtener su nombre y DNI
                String dniAutor = resultadoAutores.getString("DNI");
                String nombreAutor = resultadoAutores.getString("Nombre");

                // Consultar los libros del autor utilizando su DNI
                ResultSet resultadoLibros = sentencia.executeQuery("SELECT * FROM Libros WHERE Autor = '" + dniAutor + "'");

                // Mostrar el nombre del autor
                System.out.println("Autor: " + nombreAutor);

                if (resultadoLibros.next()) {
                    // Si el autor tiene libros, mostrar los detalles de cada libro
                    System.out.println("Libros:");

                    do {
                        String titulo = resultadoLibros.getString("Titulo");
                        float precio = resultadoLibros.getFloat("Precio");

                        System.out.println("  Título: " + titulo + ", Precio: " + precio);
                    } while (resultadoLibros.next());
                } else {
                    // Si el autor no tiene libros, mostrar un mensaje indicando que no hay libros del autor
                    System.out.println("  Este autor no tiene libros asociados.");
                }

                // Separador entre autores
                System.out.println();
            }
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al listar los autores con sus libros.");
            e.printStackTrace();
        }
    }

}
