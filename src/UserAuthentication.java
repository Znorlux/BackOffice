import java.io.*;
import java.util.*;

public class UserAuthentication {

    private static final String FILE_NAME = "users.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
      //Creamos una lista con objetos User donde guardaremos todos los usuarios del txt             (esto llamado al metodo readUsersFromFile)
        List<User> users = readUsersFromFile(); 

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
                    handleHelp();
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

  private static void handleHelp() {
      System.out.println("¿En qué puedo ayudarte?");
      System.out.println("Selecciona una opción de ayuda:");

      // Crear un mapa de problemas comunes y soluciones asociadas
      Map<Integer, Runnable> helpOptions = new HashMap<>();
      helpOptions.put(1, () -> System.out.println("Si has perdido tu vuelo, te recomendamos que te pongas en contacto con la aerolínea lo antes posible para ver si es posible reprogramar tu vuelo o solicitar un reembolso según sus políticas."));
      helpOptions.put(2, () -> System.out.println("Si has cancelado tu viaje, primero verifica las políticas de cancelación de tu reserva. Dependiendo de las políticas, es posible que puedas recibir un reembolso o un crédito para usar en futuras reservas. Además, ponte en contacto con el servicio de atención al cliente para obtener ayuda adicional."));
      helpOptions.put(3, () -> System.out.println("Si necesitas cambiar tus fechas de viaje, te recomendamos que revises las políticas de cambio de tu reserva. Muchas veces, puedes realizar cambios en tus fechas de viaje sujeto a disponibilidad y cargos adicionales, si corresponde."));
      helpOptions.put(4, () -> System.out.println("Si tienes problemas con el pago de tu reserva, asegúrate de que la información de tu tarjeta de crédito sea correcta y que tengas fondos suficientes disponibles. Si el problema persiste, ponte en contacto con tu institución financiera o el servicio de atención al cliente de la plataforma de reservas."));
      helpOptions.put(5, () -> System.out.println("Si necesitas asistencia especial, como accesibilidad para discapacitados o alimentos especiales durante el vuelo, te recomendamos que lo indiques al realizar la reserva o que te pongas en contacto con la aerolínea con anticipación."));
      helpOptions.put(6, () -> System.out.println("Si tienes alguna pregunta sobre la política de equipaje, te recomendamos que consultes las políticas de equipaje de la aerolínea en su sitio web o te pongas en contacto con la aerolínea directamente para obtener información actualizada."));
      helpOptions.put(7, () -> System.out.println("Si necesitas ayuda con el check-in en línea o el proceso de embarque, consulta las instrucciones proporcionadas por la aerolínea en su sitio web o en tu correo electrónico de confirmación de reserva. Si aún necesitas ayuda, ponte en contacto con la aerolínea."));

      // Mostrar las opciones de ayuda
      helpOptions.forEach((key, value) -> System.out.println(key + ". " + getOptionName(key)));

      Scanner scanner = new Scanner(System.in);
      int choice = scanner.nextInt();

      // Ejecutar la acción asociada con la opción seleccionada
      Runnable selectedAction = helpOptions.get(choice);
      if (selectedAction != null) {
          selectedAction.run();
      } else {
          System.out.println("Opción de ayuda no válida.");
      }
  }

  private static String getOptionName(int option) {
      switch (option) {
          case 1:
              return "Perdiste tu vuelo";
          case 2:
              return "Cancelé mi viaje";
          case 3:
              return "Necesito cambiar mis fechas de viaje";
          case 4:
              return "Problemas con el pago";
          case 5:
              return "Necesito asistencia especial";
          case 6:
              return "Preguntas sobre la política de equipaje";
          case 7:
              return "Ayuda con el check-in y el embarque";
          default:
              return "Opción no válida";
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
