package com.alura.literalura.service;

import com.alura.literalura.model.Datos;
import com.alura.literalura.model.DatosAutor;
import com.alura.literalura.model.DatosLibros;
import com.alura.literalura.model.entities.Autor;
import com.alura.literalura.model.entities.Libro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

@Service
public class ProcesadorOpciones {
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private static final String URL_BASE = "https://gutendex.com/books/?search=";
    private Scanner teclado = new Scanner(System.in);

    @Autowired
    private AutorRepository autorRepository;

    @Autowired
    private LibroRepository libroRepository;

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

                List<Libro> libroBdList = libroRepository.findByTitulo(datos.resultados().get(0).titulo());

                //Validacion para no repetir libros guardados
                if (!libroBdList.isEmpty()) {
                    System.out.println("No se puede guardar el mismo libro mas de una vez, en la bd");
                    break;
                }

                Autor autor = cargarAutorEntity(datos);
                Libro libro = cargarlibroEntity(datos, autor);
                autorRepository.save(autor);
                libro = libroRepository.save(libro);
                mostrarLibros(libro);

                break;
            }
            case 2: {
                System.out.println("opcion elegida " + opcion);
                List<Libro> libroBdList = libroRepository.findAllWithAutor();
                if (!libroBdList.isEmpty()) {
                    for (Libro libro : libroBdList) {
                        mostrarLibros(libro);
                        System.out.println("\n");
                    }
                } else {
                    System.out.println("No hay libros registrados aun");
                }

                break;
            }
            case 3: {
                System.out.println("opcion elegida " + opcion);
                List<Autor> autorBdList = autorRepository.findAllWithLibros();
                if (!autorBdList.isEmpty()) {
                    for (Autor autor : autorBdList) {
                        mostrarAutores(autor);
                        System.out.println("\n");
                    }
                } else {
                    System.out.println("No hay autores registrados aun");
                }
                break;
            }
            case 4: {
                System.out.println("opcion elegida " + opcion);
                System.out.println("Ingrese el año vivo del autor que desee buscar: ");
                int year = teclado.nextInt();
                List<Autor> autoresVivosYearList = autorRepository.findAutoresVivosEnAnio(year);
                if (!autoresVivosYearList.isEmpty()) {
                    for (Autor autor : autoresVivosYearList) {
                        mostrarAutores(autor);
                        System.out.println("\n");
                    }
                } else {
                    System.out.println("No se encontraron autores vivos en el año especificado \n");
                }

                break;
            }
            case 5:
                System.out.println("opcion elegida " + opcion);
                break;


        }

    }

    private Autor cargarAutorEntity(Datos datos) {
        Autor autor = new Autor();
        DatosAutor datosAutor = datos.resultados().get(0).autor().get(0);

        autor.setNombre(datosAutor.nombre());
        autor.setFechaNacimiento(LocalDate.of(Integer.parseInt(datosAutor.fechaDeNacimiento()), 1, 1));
        autor.setFechaFallecimiento(LocalDate.of(Integer.parseInt(datosAutor.fechaDeFallecimiento()), 1, 1));

        return autor;
    }

    private Libro cargarlibroEntity(Datos datos, Autor autor) {
        Libro libro = new Libro();
        DatosLibros datosLibros = datos.resultados().get(0);
        libro.setTitulo(datosLibros.titulo());
        libro.setAutor(autor);
        libro.setIdioma(datosLibros.idiomas().get(0));
        libro.setNumDescargas(datosLibros.numeroDeDescargas());

        return libro;
    }

    private void mostrarLibros(Libro libro) {
        System.out.println(
                "------------------ LIBRO ------------------- \n" +
                        "Titulo: " + libro.getTitulo() + "\n" +
                        "Autor: " + libro.getAutor().getNombre() + "\n" +
                        "Idioma: " + libro.getIdioma() + "\n" +
                        "Numero de descargas: " + libro.getNumDescargas() + "\n" +
                        "-------------------------------------------- \n"
        );
    }

    private void mostrarAutores(Autor autor) {

        System.out.println(
                "------------------ AUTOR ------------------- \n" +
                        "Nombre: " + autor.getNombre() + "\n" +
                        "Fecha de nacimiento: " + autor.getFechaNacimiento().getYear() + "\n" +
                        "Fecha de fallecimiento: " + autor.getFechaFallecimiento().getYear() + "\n" +
                        "Libros: " + autor.getTitulosLibros().toString() + "\n" +
                        "-------------------------------------------- \n"
        );
    }
}
