package server;

import common.util.Commands;
import io.github.cdimascio.dotenv.Dotenv;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import server.commands.*;
import server.handler.CommandHandler;
import server.managers.AuthManager;
import server.managers.CommandManager;
import server.network.TCPDatagramServer;
import server.service.CityService;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;

/**
 * Серверная часть приложения.
 */
public class ServerApp {
    public static final int PORT = 1506;
    public static Logger logger = LoggerFactory.getLogger("ServerLogger");

    public static Dotenv dotenv = Dotenv
            .configure()
            .directory("../")
            .load();

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        try {
            var url = dotenv.get("DB_URL");
            var user = dotenv.get("DB_USER");
            var password = dotenv.get("DB_PASSWORD");

            if (url == null || url.isEmpty() || user == null || user.isEmpty() || password == null || password.isEmpty()) {
                System.out.println("В .env файле не обнаружены данные для подключения к базе данных, введи норм данные лох на овнере");
                //System.exit(1);
            }
            Class.forName("org.postgresql.Driver");
            Connection connection = DriverManager.getConnection(url, user, password);

            CityService cityService = new CityService(connection);

            AuthManager authManager = new AuthManager(connection, dotenv.get("PEPPER")); //TODO ADD PEPPER

            if (!isPortAvailable(PORT)) {
                logger.error("Порт {} уже занят", PORT);
                System.exit(3);
            }
            var commandManager = new CommandManager() {{
                register(Commands.ADD, new Add(cityService));
                register(Commands.CLEAR, new Clear(cityService));
                register(Commands.EXECUTE_SCRIPT, new ExecuteScript(cityService));
                register(Commands.EXIT, new Exit(cityService));
                register(Commands.HEAD, new Head(cityService));
                register(Commands.HELP, new Help(this));
                register(Commands.INFO, new Info(cityService));
                register(Commands.REMOVE_BY_ID, new RemoveById(cityService));
                register(Commands.SHOW, new Show(cityService));
                register(Commands.SUM_OF_POPULATION, new SumOfPopulation(cityService));
                register(Commands.TAIL, new Tail(cityService));
                register(Commands.UPDATE, new Update(cityService));

                register(Commands.REGISTER, new Register(authManager));
                register(Commands.AUTH, new Auth(authManager));
            }};

            try {
                var server = new TCPDatagramServer(InetAddress.getLocalHost(), PORT, new CommandHandler(commandManager, authManager));
                server.run();
            } catch (SocketException e) {
                logger.error("Ошибка создания сокета на порту {}", PORT, e);
            } catch (UnknownHostException e) {
                logger.error("Неизвестный хост при инициализации сервера", e);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isPortAvailable(int port) {
        try (ServerSocket socket = new ServerSocket(port)) {
            socket.setReuseAddress(true);
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}
// ./gradlew server:run