package server.service;

import common.model.City;
import common.model.User;
import org.slf4j.Logger;
import server.ServerApp;
import server.exceptions.UserNotOwnerOfObject;
import server.repo.CityJDBCBasedRepository;
import server.repo.CityNativeBasedRepository;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;

public class CityService {
    private final Connection connection;
    private final CityJDBCBasedRepository cityJdbcBasedRepository;
    private final Logger logger = ServerApp.logger;
    private final CityNativeBasedRepository cityNativeBasedRepository;
    private final ReentrantLock lock = new ReentrantLock();

    public CityService(Connection connection) {
        this.connection = connection;
        cityJdbcBasedRepository = new CityJDBCBasedRepository(connection);
        try {
            Set<City> set = new HashSet<>(cityJdbcBasedRepository.readCollection());
            Queue<City> queue = new PriorityQueue<>(set);
            cityNativeBasedRepository = new CityNativeBasedRepository(queue);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Queue<City> get() {
        return cityNativeBasedRepository.get();
    }

    public int add(User user, City element) {
        int id = -1;
        try {
            id = cityJdbcBasedRepository.add(user, element);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return id;
        }
        element.setId(id);
        element.setOwnerId(user.getId());
        lock.lock();
        cityNativeBasedRepository.add(element);
        lock.unlock();

        return id;
    }

    public LocalDateTime getLastInitTime() {
        return cityNativeBasedRepository.getLastInitTime();
    }

    public LocalDateTime getLastSaveTime() {
        return cityNativeBasedRepository.getLastSaveTime();
    }

    public String getTypeOfCollection() {
        return cityNativeBasedRepository.type();
    }

    public List<City> getSortedCitys() {
        return cityNativeBasedRepository.sorted();
    }

    public boolean isExist(int id) {
        return cityNativeBasedRepository.checkExist(id);
    }

    public void updateCity(User user, City city) throws SQLException {
        var c = cityNativeBasedRepository.getById(city.getId());
        if (c == null) {
            add(user, city);
            return;
        }

        if (c.getOwnerId() == user.getId()) {
            cityJdbcBasedRepository.updateCity(city);
            lock.lock();
            var cityInCollection = cityNativeBasedRepository.getById(city.getId());
            cityInCollection.update(city);
            lock.unlock();
        } else {
            throw new UserNotOwnerOfObject("анвак не твое");
        }
    }

    public void removeById(User user, int id) throws SQLException {
        City city = cityNativeBasedRepository.getById(id);
        if (city == null) {
            logger.warn("Ничего не было удалено.");
            return;
        }

        if (city.getOwnerId() != user.getId()) {
            throw new UserNotOwnerOfObject("анвак не твое");
        }

        cityJdbcBasedRepository.remove(user.getId(), id);
        lock.lock();
        cityNativeBasedRepository.remove(id);
        lock.unlock();
    }

    public void clear(User user) {
        try {
            cityJdbcBasedRepository.clear(user.getId());
        } catch (SQLException e) {
            logger.error(e.getMessage(), e);
        }

        lock.lock();
        cityNativeBasedRepository.get().removeIf(it -> it.getOwnerId() == user.getId());
        lock.unlock();
    }

    public City getFirstElement() {
        return cityNativeBasedRepository.first();
    }

    public City getLastElement() {
        return cityNativeBasedRepository.last();
    }

    public long getSumOfPopulation() {
        return cityNativeBasedRepository.get().stream()
                .map(City::getPopulation)
                .mapToLong(Long::longValue)
                .sum();
    }

    public void executeScript(int id) {
    }

    public void exit(User user) {
    }

    public List<City> getAllCities() {
        return null;
    }
}
