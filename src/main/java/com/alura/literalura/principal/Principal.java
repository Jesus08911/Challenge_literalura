package com.alura.literalura.principal;

import com.alura.literalura.service.ProcesadorOpciones;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class Principal {

    @Autowired
    ProcesadorOpciones procesdoropciones;

    public void muestraElMenu(){
        Scanner scanner = new Scanner(System.in);
        int opcion = 0;

        do {
            mostrarMenu();

            //Validando que el usuario no ingrese caracteres
            String opcionStr = scanner.nextLine();
            try {
                opcion = Integer.parseInt(opcionStr);
            } catch (NumberFormatException e) {
                opcion = -1;
            }

            //Asegurando una opcion valida
            while (opcion > 5 || opcion < 0) {
                System.out.println("Opcion invalida elija un numero del menú:");
                opcion = scanner.nextInt();
            }

            procesdoropciones.proceasarOpcion(opcion);

        }
        while (opcion != 0);
        System.exit(0);





//
//        //Busqueda de libros por nombre
//        System.out.println("Ingrese el nombre del libro que desea buscar");
//        var tituloLibro = teclado.nextLine();
//        json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ","+"));
//        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
//        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
//                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
//                .findFirst();
//        if(libroBuscado.isPresent()){
//            System.out.println("Libro Encontrado ");
//            System.out.println(libroBuscado.get());
//        }else {
//            System.out.println("Libro no encontrado");
//        }

    }
    private void mostrarMenu() {
        System.out.println(
                """
                  ----------------- MENU ------------------------
                  Elija la opción de acuerdo a su número:
                  1.- Buscar libro por titulo
                  2.- Listar libros registrados
                  3.- Listar autores registrados
                  4.- Listar autores vivos en un determinado año
                  5.- Listar libros por idioma
                  0.- Salir
                  -----------------------------------------------
                """
        );
    }
}
