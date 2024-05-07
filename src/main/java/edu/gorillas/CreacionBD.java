package edu.gorillas;

import java.sql.SQLException;
import java.sql.Statement;

public class CreacionBD {

    public static void crearBase(Statement sentencia)
    {
        try
        {
            //String con el nombre de la base de datos:
            String bd = "AppBibliotecaActualizada";

            //Sentencias de crear si no existe y usar la bd creada:
            sentencia.execute("CREATE DATABASE IF NOT EXISTS " + bd + ";");
            sentencia.execute("USE "+ bd +";");

            // Creación de la tabla Autores:
            sentencia.execute("CREATE TABLE IF NOT EXISTS Autores ("
                    + "DNI VARCHAR(9) NOT NULL PRIMARY KEY,"
                    + "Nombre VARCHAR(30) NOT NULL,"
                    + "Nacionalidad VARCHAR(30) NOT NULL)");

            // Creación de la tabla Libros:
            sentencia.execute("CREATE TABLE IF NOT EXISTS Libros ("
                    + "IdLibro INT(5) AUTO_INCREMENT PRIMARY KEY,"
                    + "Titulo VARCHAR(30),"
                    + "Precio FLOAT,"
                    + "Autor VARCHAR(9),"
                    + "FOREIGN KEY (Autor) REFERENCES Autores(DNI)"
                    + "ON DELETE CASCADE " //Para que se eliminen las claves foraneas
                    + "ON UPDATE CASCADE)");
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }
}
