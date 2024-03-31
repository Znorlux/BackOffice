import java.io.*;
import java.util.*;

public class UserAuthentication {

    private static final String FILE_NAME = "users.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<User> users = readUsersFromFile(); //Creamos una lista con objetos User donde guardaremos todos los usuarios del txt (esto llamado al metodo readUsersFromFile)

        System.out.println("Bienvenido a la app de Booking");
        while (true) {
            System.out.println("Selecciona una opcion:");
            System.out.println("1. Registrarse");
            System.out.println("2. Iniciar Sesion");
            System.out.println("3. Salir de la aplicación");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Limpiamos el buffer de lectura

            // Switch para lanzar el metodo correspondiente a la opción
            switch (choice) {
                case 1:
                    registerUser(scanner, users);
                    break;
                case 2:
                    loginUser(scanner, users);
                    break;
                case 3:
                    System.out.println("Hasta luego!");
                    return;
                default:
                    System.out.println("Opcion invalida, vuelve a intentarlo!");
            }
        }
    }

    // Metodo para retornar una lista con los usuarios y contraseñas del txt
    private static List<User> readUsersFromFile() {
        List<User> users = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(","); // Separamos el string (por ejemplo "admin,123") por su separador y lo guardamos en una lista
                if (parts.length == 2) { // Si efectivamente esa lista de usuario y contraseña tiene 2 elementos, entonces
                    users.add(new User(parts[0], parts[1])); //Añadimos a la lista de usuarios un nuevo objeto de user
                }
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir en el archivo " + e.getMessage());
        }
        return users;
    }

    private static void saveUsersToFile(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (User user : users) {
                writer.write(user.getUsername() + "," + user.getPassword());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Ocurrió un error al escribir en el archivo: " + e.getMessage());
        }
    }

    private static void registerUser(Scanner scanner, List<User> users) {
        System.out.println("Ingresa un nombre de usuario:");
        String username = scanner.nextLine();
        // Para realizar la verificacion de usuario existente usando programacion funcional hacemos lo siguiente
        if (users.stream().anyMatch(user -> user.getUsername().equals(username))) { //Utilizamos el metodo anyMatch para pasarle una funcion lambda la cual busca si el nombre de usuario actual, ya está en la lista de usuarios
            System.out.println("Este nombre de usuario ya existe, vuelve a intentarlo");
            return;
        }

        System.out.println("Ingresa una contraseña");
        String password = scanner.nextLine();
        users.add(new User(username, password));
        saveUsersToFile(users);
        System.out.println("Registro correcto!");
    }

    private static void loginUser(Scanner scanner, List<User> users) {
        System.out.println("Ingresa tu nombre de usuario:");
        String username = scanner.nextLine();
        System.out.println("Ingresa tu contraseña:");
        String password = scanner.nextLine();

        if (users.stream().anyMatch(user -> user.getUsername().equals(username) && user.getPassword().equals(password))) {
            System.out.println("Inicio de sesion exitoso");
            // Aqui continuariamos con la siguiente logica que nos ponga el profesor
        } else {
            System.out.println("Usuario o contraseña incorrecto, intentalo de nuevo");
        }
    }

    static class User {
        private final String username;
        private final String password;

        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }
    }
}
