package edu.gorillas;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    //Inicializamos el scanner con el nombre sc
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        Statement sentencia = null;
        Connection conexion = null;

        //Variable opción (op) inicializada a 0
        int op = 0;

        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String url = "jdbc:mariadb://localhost:3306/?user=root&password=";
        try {
            conexion = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("No hay ningún driver que reconozca la URL especificada");
        } catch (Exception e) {
            System.out.println("Se ha producido algún otro error");
        }

        try {
            sentencia = conexion.createStatement();
        } catch (SQLException e) {
            System.out.println("Error");
        }

        CreacionBD.crearBase(sentencia);



        do
        {

            op = Menu.menuMain(sc);

            switch(op)
            {
                case 1:
                    Insertar.mainInsertar(sentencia,sc);
                    break;

                case 2:
                    Borrar.mainBorrar(sentencia,sc);
                    break;

                case 3:
                    Modificar.mainModificar(sentencia,sc);
                    break;

                case 4:
                    Consultas.mainConsultas(sentencia,sc);
                    break;
            }

        }while(op != 5);

    }
}