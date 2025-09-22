package common.model;

import common.util.Element;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Objects;

public class City extends Element {
    @Serial
    private static final long serialVersionUID = 1L;

    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private LocalDate creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private float area; //Значение поля должно быть больше 0
    private Long population; //Значение поля должно быть больше 0, Поле не может быть null
    private float metersAboveSeaLevel;
    private Long carCode; //Значение поля должно быть больше 0, Максимальное значение поля: 1000, Поле не может быть null
    private long agglomeration;
    private Government government; //Поле не может быть null
    private String human; //Поле может быть null
    private int ownerId;

    public City(
            int id,
            String name,
            Coordinates coordinates,
            LocalDate creationDate,
            float area,
            Long population,
            float metersAboveSeaLevel,
            Long carCode,
            long agglomeration,
            Government government,
            String human,
            int ownerId) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.area = area;
        this.population = population;
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        this.carCode = carCode;
        this.agglomeration = agglomeration;
        this.government = government;
        this.human = human;
        this.ownerId = ownerId;
    }
    public City(){}

    /**
     * Валидирует правильность полей.
     * @return true, если все поля корректны, иначе false.
     */
    @Override
    public boolean validate() {
        if (id <= 0) return false;
        if (name == null || name.isEmpty()) return false;
        if (coordinates == null) return false;
        if (creationDate == null) return false;
        if (area <= 0) return false;
        if (population == null || population <= 0) return false;
        if (metersAboveSeaLevel < -11000 || metersAboveSeaLevel >9000) return false;
        if (carCode == null || carCode <= 0 || carCode > 1000) return false;
        if (agglomeration < 0) return false;
        if (government == null) return false;
        return true;
    }

    public void update(City city) {
        this.name = city.name;
        this.coordinates = city.coordinates;
        this.creationDate = city.creationDate;
        this.area = city.area;
        this.population = city.population;
        this.metersAboveSeaLevel = city.metersAboveSeaLevel;
        this.carCode = city.carCode;
        this.agglomeration = city.agglomeration;
        this.government = city.government;
        this.human = city.human;
    }

    //Getters
    public int getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public Coordinates getCoordinates(){return coordinates;}
    public LocalDate getCreationDate(){return creationDate;}

    public java.sql.Date getSqlCreationDate() {
        return creationDate != null ? java.sql.Date.valueOf(creationDate) : null;
    }

    public float getArea(){return area;}
    public Long getPopulation(){
        return population;
    }
    public float getMetersAboveSeaLevel(){
        return metersAboveSeaLevel;
    }
    public Long getCarCode(){
        return carCode;
    }
    public long getAgglomeration(){
        return agglomeration;
    }
    public Government getGovernment(){
        return government;
    }
    public String getHuman(){
        return human;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City that = (City) o;
        return
                id == that.id &&
                Objects.equals(name, that.name) &&
                Objects.equals(coordinates, that.coordinates) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(area, that.area) &&
                Objects.equals(population, that.population) &&
                Objects.equals(metersAboveSeaLevel, that.metersAboveSeaLevel) &&
                Objects.equals(carCode, that.carCode) &&
                Objects.equals(agglomeration, that.agglomeration) &&
                government == that.government &&
                Objects.equals(human, that.human);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, area, population,
                metersAboveSeaLevel, carCode, agglomeration, government, human);
    }

    @Override
    public String toString() {
        return "City #" + id +
                " (created on " + LocalDate.now() + ")" +
                "\n Название: " + name +
                "\n Местоположение: " + coordinates +
                "\n Создан: " + creationDate +
                "\n В округе: " + area +
                "\n Население: " + population +
                "\n Высота над морем: " + metersAboveSeaLevel +
                "\n Код региона: " + carCode +
                "\n Агломерации: " + agglomeration +
                "\n Тип власти: " + government +
                "\n Личность: " + (human != null ? human : "None")
                ;
    }

    @Override
    public int compareTo(Element element) {
        return this.id - element.getId();
    }

    public void setId(int id) {
        this.id = id;
    }
    public City setName(String name) {
        this.name = name;
        return this;
    }
    public City setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        return this;
    }
    public City setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
        return this;
    }
    public City setArea(Float area){
        this.area = area;
        return this;
    }
    public City setPopulation(Long population){
        this.population = population;
        return this;
    }
    public City setMetersAboveSeaLevel(Float metersAboveSeaLevel){
        this.metersAboveSeaLevel = metersAboveSeaLevel;
        return this;
    }
    public City setCarCode(Long carCode){
        this.carCode = carCode;
        return this;
    }
    public City setAgglomeration(Long agglomeration){
        this.agglomeration = agglomeration;
        return this;
    }
    public City setGovernment(Government government){
        this.government = government;
        return this;
    }
    public City setHuman(String human){
        this.human = human;
        return this;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public City setOwnerId(int ownerId) {
        this.ownerId = ownerId;
        return this;
    }
}
