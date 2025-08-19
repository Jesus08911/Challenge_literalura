package com.alura.literalura.service;

import com.alura.literalura.model.Datos;

import java.util.Scanner;

public class ProcesadorOpciones {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);
    public void proceasarOpcion(int opcion){

        switch (opcion){

            case 1:{
                System.out.println("opcion elegida " + opcion);
                System.out.println("Ingrese el Titulo del Libro");
                String tituloLibro = teclado.nextLine();
                var json = consumoAPI.obtenerDatos(URL_BASE + tituloLibro.replace(" ","+"));
                System.out.println(json);
                var datos = conversor.obtenerDatos(json, Datos.class);
                System.out.println(datos);



                break;
            }
            case 2:
                System.out.println("opcion elegida " + opcion);
                break;
            case 3:
                System.out.println("opcion elegida " + opcion);
                break;
            case 4:
                System.out.println("opcion elegida " + opcion);
                break;
            case 5:
                System.out.println("opcion elegida " + opcion);
                break;


        }

    }
}
