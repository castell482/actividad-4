package bibliotecact;
import java.util.Scanner;

public class Bibliotecact {

    static class NodoLibro {
        int id;
        String nombre;
        String autor;
        boolean prestado;
        NodoLibro izquierdo, derecho;

        public NodoLibro(int id, String nombre, String autor) {
            this.id = id;
            this.nombre = nombre;
            this.autor = autor;
            this.prestado = false;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    static class NodoUsuario {
        int cedula;
        String nombre;
        String apellidos;
        NodoUsuario izquierdo, derecho;

        public NodoUsuario(int cedula, String nombre, String apellidos) {
            this.cedula = cedula;
            this.nombre = nombre;
            this.apellidos = apellidos;
            this.izquierdo = null;
            this.derecho = null;
        }
    }

    private NodoLibro raizLibro = null;
    private NodoUsuario raizUsuario = null;

    private Scanner scanner = new Scanner(System.in);

    public void iniciar() {
        int opcion;
        do {
            System.out.println("\nMenú Principal:");
            System.out.println("1. Menú de Libros");
            System.out.println("2. Menú de Usuarios");
            System.out.println("3. Prestar Libro");
            System.out.println("4. Devolver Libro");
            System.out.println("5. Listar Libros");
            System.out.println("6. Listar Libros Disponibles");
            System.out.println("7. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> menuLibros();
                case 2 -> menuUsuarios();
                case 3 -> prestarLibro();
                case 4 -> devolverLibro();
                case 5 -> listarLibros();
                case 6 -> listarLibrosDisponibles();
                case 7 -> System.out.println("Saliendo del programa...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 7);
    }

    private void menuLibros() {
        int opcion;
        do {
            System.out.println("\nMenú de Libros:");
            System.out.println("1. Agregar Libro");
            System.out.println("2. Eliminar Libro");
            System.out.println("3. Listar Libros");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> agregarLibro();
                case 2 -> eliminarLibro();
                case 3 -> listarLibros();
                case 4 -> System.out.println("Volviendo al Menú Principal...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }

    private void menuUsuarios() {
        int opcion;
        do {
            System.out.println("\nMenú de Usuarios:");
            System.out.println("1. Agregar Usuario");
            System.out.println("2. Eliminar Usuario");
            System.out.println("3. Listar Usuarios");
            System.out.println("4. Volver al Menú Principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1 -> agregarUsuario();
                case 2 -> eliminarUsuario();
                case 3 -> listarUsuarios();
                case 4 -> System.out.println("Volviendo al Menú Principal...");
                default -> System.out.println("Opción inválida. Intente de nuevo.");
            }
        } while (opcion != 4);
    }

    private void agregarLibro() {
        System.out.print("Ingrese ID del libro: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        if (buscarLibro(raizLibro, id) != null) {
            System.out.println("El libro con ID " + id + " ya existe. Ingrese otro ID.");
            return;
        }
        System.out.print("Ingrese nombre del libro: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese autor del libro: ");
        String autor = scanner.nextLine();
        raizLibro = insertarLibro(raizLibro, new NodoLibro(id, nombre, autor));
        System.out.println("Libro agregado exitosamente.");
    }

    private NodoLibro insertarLibro(NodoLibro nodo, NodoLibro nuevo) {
        if (nodo == null) return nuevo;
        if (nuevo.id < nodo.id) {
            nodo.izquierdo = insertarLibro(nodo.izquierdo, nuevo);
        } else {
            nodo.derecho = insertarLibro(nodo.derecho, nuevo);
        }
        return nodo;
    }

    private NodoLibro buscarLibro(NodoLibro nodo, int id) {
        if (nodo == null || nodo.id == id) return nodo;
        if (id < nodo.id) return buscarLibro(nodo.izquierdo, id);
        return buscarLibro(nodo.derecho, id);
    }

    private void eliminarLibro() {
        System.out.print("Ingrese ID del libro a eliminar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        raizLibro = eliminarLibroRecursivo(raizLibro, id);
    }

    private NodoLibro eliminarLibroRecursivo(NodoLibro nodo, int id) {
        if (nodo == null) return null;
        if (id < nodo.id) nodo.izquierdo = eliminarLibroRecursivo(nodo.izquierdo, id);
        else if (id > nodo.id) nodo.derecho = eliminarLibroRecursivo(nodo.derecho, id);
        else {
            if (nodo.izquierdo == null) return nodo.derecho;
            if (nodo.derecho == null) return nodo.izquierdo;
            NodoLibro sucesor = encontrarMinimo(nodo.derecho);
            nodo.id = sucesor.id;
            nodo.nombre = sucesor.nombre;
            nodo.autor = sucesor.autor;
            nodo.derecho = eliminarLibroRecursivo(nodo.derecho, sucesor.id);
        }
        return nodo;
    }

    private NodoLibro encontrarMinimo(NodoLibro nodo) {
        while (nodo.izquierdo != null) nodo = nodo.izquierdo;
        return nodo;
    }

    private void listarLibros() {
        System.out.println("\nListado de Libros:");
        listarLibrosRecursivo(raizLibro);
    }

    private void listarLibrosRecursivo(NodoLibro nodo) {
        if (nodo != null) {
            listarLibrosRecursivo(nodo.izquierdo);
            System.out.println("ID: " + nodo.id + ", Nombre: " + nodo.nombre + ", Autor: " + nodo.autor);
            listarLibrosRecursivo(nodo.derecho);
        }
    }

    private void prestarLibro() {
        System.out.print("Ingrese el ID del libro a prestar: ");
        int idLibro = scanner.nextInt();
        scanner.nextLine();

        NodoLibro libro = buscarLibro(raizLibro, idLibro);
        if (libro == null) {
            System.out.println("El libro con ID " + idLibro + " no existe.");
            return;
        }

        if (libro.prestado) {
            System.out.println("El libro ya está prestado.");
            return;
        }

        System.out.print("Ingrese la cédula del usuario: ");
        int cedula = scanner.nextInt();
        scanner.nextLine();

        NodoUsuario usuario = buscarUsuario(raizUsuario, cedula);
        if (usuario == null) {
            System.out.println("No existe un usuario con la cédula " + cedula + ".");
            return;
        }

        libro.prestado = true;
        System.out.println("Libro prestado exitosamente a " + usuario.nombre + " " + usuario.apellidos + ".");
    }

    private void devolverLibro() {
        System.out.print("Ingrese el ID del libro a devolver: ");
        int idLibro = scanner.nextInt();
        scanner.nextLine();

        NodoLibro libro = buscarLibro(raizLibro, idLibro);
        if (libro == null) {
            System.out.println("El libro con ID " + idLibro + " no existe.");
            return;
        }

        if (!libro.prestado) {
            System.out.println("El libro no está prestado.");
            return;
        }

        libro.prestado = false;
        System.out.println("Libro devuelto exitosamente.");
    }

    private void listarLibrosDisponibles() {
        System.out.println("\nListado de Libros Disponibles:");
        listarLibrosDisponiblesRecursivo(raizLibro);
    }

    private void listarLibrosDisponiblesRecursivo(NodoLibro nodo) {
        if (nodo != null) {
            listarLibrosDisponiblesRecursivo(nodo.izquierdo);
            if (!nodo.prestado) {
                System.out.println("ID: " + nodo.id + ", Nombre: " + nodo.nombre + ", Autor: " + nodo.autor);
            }
            listarLibrosDisponiblesRecursivo(nodo.derecho);
        }
    }

    private void agregarUsuario() {
        System.out.print("Ingrese cédula del usuario: ");
        int cedula = scanner.nextInt();
        scanner.nextLine();
        if (buscarUsuario(raizUsuario, cedula) != null) {
            System.out.println("El usuario con cédula " + cedula + " ya existe. Ingrese otra cédula.");
            return;
        }
        System.out.print("Ingrese nombre del usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese apellidos del usuario: ");
        String apellidos = scanner.nextLine();
        raizUsuario = insertarUsuario(raizUsuario, new NodoUsuario(cedula, nombre, apellidos));
        System.out.println("Usuario agregado exitosamente.");
    }

    private NodoUsuario insertarUsuario(NodoUsuario nodo, NodoUsuario nuevo) {
        if (nodo == null) return nuevo;
        if (nuevo.cedula < nodo.cedula) {
            nodo.izquierdo = insertarUsuario(nodo.izquierdo, nuevo);
        } else {
            nodo.derecho = insertarUsuario(nodo.derecho, nuevo);
        }
        return nodo;
    }

    private NodoUsuario buscarUsuario(NodoUsuario nodo, int cedula) {
        if (nodo == null || nodo.cedula == cedula) return nodo;
        if (cedula < nodo.cedula) return buscarUsuario(nodo.izquierdo, cedula);
        return buscarUsuario(nodo.derecho, cedula);
    }

    private void eliminarUsuario() {
        System.out.print("Ingrese cédula del usuario a eliminar: ");
        int cedula = scanner.nextInt();
        scanner.nextLine();
        raizUsuario = eliminarUsuarioRecursivo(raizUsuario, cedula);
    }

    private NodoUsuario eliminarUsuarioRecursivo(NodoUsuario nodo, int cedula) {
        if (nodo == null) return null;
        if (cedula < nodo.cedula) nodo.izquierdo = eliminarUsuarioRecursivo(nodo.izquierdo, cedula);
        else if (cedula > nodo.cedula) nodo.derecho = eliminarUsuarioRecursivo(nodo.derecho, cedula);
        else {
            if (nodo.izquierdo == null) return nodo.derecho;
            if (nodo.derecho == null) return nodo.izquierdo;
            NodoUsuario sucesor = encontrarMinimoUsuario(nodo.derecho);
            nodo.cedula = sucesor.cedula;
            nodo.nombre = sucesor.nombre;
            nodo.apellidos = sucesor.apellidos;
            nodo.derecho = eliminarUsuarioRecursivo(nodo.derecho, sucesor.cedula);
        }
        return nodo;
    }

    private NodoUsuario encontrarMinimoUsuario(NodoUsuario nodo) {
        while (nodo.izquierdo != null) nodo = nodo.izquierdo;
        return nodo;
    }

    private void listarUsuarios() {
        System.out.println("\nListado de Usuarios:");
        listarUsuariosRecursivo(raizUsuario);
    }

    private void listarUsuariosRecursivo(NodoUsuario nodo) {
        if (nodo != null) {
            listarUsuariosRecursivo(nodo.izquierdo);
            System.out.println("Cédula: " + nodo.cedula + ", Nombre: " + nodo.nombre + ", Apellidos: " + nodo.apellidos);
            listarUsuariosRecursivo(nodo.derecho);
        }
    }

    public static void main(String[] args) {
        new Bibliotecact().iniciar();
    }
}
