package edu.gorillas;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Insertar {

    static Scanner sc = new Scanner(System.in);

    public static void mainInsertar(Statement sentencia, Scanner sc)
    {
        //Opción inicializada a 0
        int op = 0;

        do
        {
            op = Menu.menuInsertar(sc);

            switch(op)
            {
                case 1:
                    Insertar.nuevoAutor(sentencia);
                    break;

                case 2:
                    Insertar.nuevoLibro(sentencia);
                    break;

            }

        }while(op != 3);
    }

    private static void nuevoAutor(Statement sentencia) {
        System.out.println("Dame el DNI del nuevo autor");
        String dni = sc.nextLine();
        System.out.println("Dame el nuevo nombre del autor");
        String nombre = sc.nextLine();
        System.out.println("Dame la nacionalidad del nuevo autor");
        String nacionalidad = sc.nextLine();
        sc = new Scanner(System.in);

        try {
            sentencia.executeUpdate("INSERT INTO Autores (DNI, Nombre, Nacionalidad) VALUES('" + dni + "', '" + nombre + "', '" + nacionalidad + "')");
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al insertar el nuevo autor");
        }
    }

    private static void nuevoLibro(Statement sentencia) {
        System.out.println("Dame el título del libro");
        String titulo = sc.nextLine();
        System.out.println("Dame el precio del libro");
        float precio = sc.nextFloat();

        // Agregar una línea para consumir el salto de línea pendiente
        sc.nextLine(); // Consumir el salto de línea pendiente

        System.out.println("Dame el nombre del autor");
        String autorNombre = sc.nextLine();

        try {
            // Verificamos si el autor existe
            ResultSet resultado = sentencia.executeQuery("SELECT * FROM Autores WHERE Nombre = '" + autorNombre + "'");

            if (resultado.next()) {
                // Si el autor existe, se inserta el libro
                String autorDNI = resultado.getString("DNI");
                sentencia.executeUpdate("INSERT INTO Libros (Titulo, Precio, Autor) VALUES('" + titulo + "', " + precio + ", '" + autorDNI + "')");
                System.out.println("Libro insertado correctamente.");
            } else {
                // Si el autor no existe, se lanza el mensaje de error
                System.out.println("Error: El autor '" + autorNombre + "' no existe en la base de datos.");
            }
        } catch (SQLException e) {
            System.err.println("Se ha producido un error al insertar el libro.");
            e.printStackTrace();
        }
    }

}
