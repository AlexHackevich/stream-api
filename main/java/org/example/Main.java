package org.example;

import org.example.model.*;
import org.example.util.Util;

import javax.lang.model.util.Elements;
import java.io.IOException;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.Period;
import java.time.chrono.JapaneseDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

import static java.lang.Long.sum;
import static java.lang.Long.valueOf;

public class Main {
    public static void main(String[] args) throws IOException {
//        task1();
//        task2();
//        task3();
//        task4();
//        task5();
//        task6();
//        task7();
//        task8();
//        task9();
//        task10();
//        task11();
//        task12();
//        task13();
//        task14();
        task15();

    }

    private static void task1() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() >= 10 && animal.getAge() <= 20)
                .sorted(Comparator.comparing(Animal::getAge))
                .skip(14)
                .limit(7)
                .forEach(System.out::println);
    }

    private static void task2() throws IOException {
        List<Animal> animals = Util.getAnimals();
        List<String> resultStream = animals.stream().
                filter(animal -> animal.getOrigin().equals("Japanese")).
                map(animal -> {
                    if (animal.getGender().equals("Female"))
                        return animal.getBread().toUpperCase();
                    else {
                        return animal.getBread();
                    }
                })
                .collect(Collectors.toList());
        resultStream.forEach(System.out::println);
    }

    private static void task3() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getAge() > 30)
                .map(Animal::getOrigin)
                .filter(a -> a.startsWith("A"))
                .distinct()
                .forEach(System.out::println);
    }

    private static void task4() throws IOException {
        List<Animal> animals = Util.getAnimals();
        long female = animals.stream()
                .filter(animal -> animal.getGender().equals("Female"))
                .count();
        System.out.println(female);
    }

    private static void task5() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean hungarian = animals.stream()
                .filter(animal -> animal.getAge() >= 20 && animal.getAge() <= 30)
                .anyMatch(animal -> animal.getOrigin().equals("Hungarian"));
        System.out.println(hungarian);
    }

    private static void task6() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean result = animals.stream()
                .allMatch(animal -> animal.getGender()
                        .equals("Female") && animal.getGender()
                        .equals("Male"));
        System.out.println(result);
    }

    private static void task7() throws IOException {
        List<Animal> animals = Util.getAnimals();
        boolean oceania = animals.stream()
                .noneMatch(animal -> animal.getOrigin()
                        .equals("Oceania"));
        System.out.println(oceania);
    }

    private static void task8() throws IOException {
        List<Animal> animals = Util.getAnimals();
        Optional<Integer> max = animals.stream()
                .sorted(Comparator.comparing(Animal::getBread))
                .limit(100)
                .map(Animal::getAge)
                .max(Integer::compare);
        System.out.println(max);
    }

    private static void task9() throws IOException {
        List<Animal> animals = Util.getAnimals();
        OptionalInt min = animals.stream()
                .map(animal -> animal.getBread().toCharArray())
                .mapToInt(arr -> arr.length)
                .min();
        System.out.println(min);
    }

    private static void task10() throws IOException {
        List<Animal> animals = Util.getAnimals();
        long count = animals.stream()
                .mapToInt(Animal::getAge)
                .sum();
        System.out.println(count);
    }

    private static void task11() throws IOException {
        List<Animal> animals = Util.getAnimals();
        animals.stream()
                .filter(animal -> animal.getOrigin().equals("Indonesian"))
                .mapToInt(Animal::getAge)
                .average()
                .ifPresent(x -> System.out.println(x));
    }

    private static void task12() throws IOException {
        List<Person> people = Util.getPersons();
        people.stream()
                .filter(person -> person.getGender().equals("Male"))
                .filter(person -> LocalDate.now().minusYears(18).isAfter(person.getDateOfBirth())
                        &&
                        LocalDate.now().minusYears(27).isBefore(person.getDateOfBirth()))
                .sorted(Comparator.comparing(Person::getRecruitmentGroup))
                .limit(200).
                forEach(System.out::println);
    }

    private static void task13() throws IOException {
        List<House> houses = Util.getHouses();

        Predicate<House> hospital = house -> house.getBuildingType().equals("Hospital");

        Predicate<Person> personPredicate = person ->
                person.getDateOfBirth()
                        .isAfter(LocalDate.now().minusYears(18)) &&
                        person.getDateOfBirth()
                                .isBefore(LocalDate.now().minusYears(65));
        Function<House, Stream<Person>> personList = house ->
                house.getPersonList().stream();
        Stream.concat(Stream.concat(
                                houses.stream()
                                        .filter(hospital)
                                        .flatMap(personList),
                                houses.stream()
                                        .filter(hospital.negate())
                                        .flatMap(personList)
                                        .filter(personPredicate)),
                        houses.stream()
                                .filter(hospital.negate())
                                .flatMap(personList)
                                .filter(personPredicate.negate()))
                .limit(500)
                .forEach(System.out::println);
    }

    private static void task14() throws IOException {
        List<Car> cars = Util.getCars();

        List<Car> allcars = new ArrayList<>(List.copyOf(cars));

        List<Car> carsTurkmenistan = Stream.concat(
                cars.stream()
                        .filter(car -> car.getCarMake().equals("Jaguar")),
                cars.stream()
                        .filter(car -> car.getColor().equals("White"))

        ).toList();
        allcars.removeAll(carsTurkmenistan);

        List<Car> carUzbekistan = allcars.stream()
                .filter(car -> car.getMass() <= 1500 || car.getCarMake().equals("BMW") ||
                        car.getCarMake().equals("Lexus") ||
                        car.getCarMake().equals("Chrysler") ||
                        car.getCarMake().equals("Toyota")).toList();

        allcars.removeAll(carUzbekistan);

        List<Car> carKazahstan = Stream.concat(
                        allcars.stream().filter(car -> car.getColor().equals("Black") || car.getMass() > 4000),
                        allcars.stream().filter(car -> car.getCarMake().equals("GMC") || car.getCarMake().equals("Dodge"))).
                toList();
        allcars.removeAll(carKazahstan);

        List<Car> carKirgistan = allcars.stream()
                .filter(car -> car.getReleaseYear() < 1982 || car.getCarModel().equals("Civic") || car.getCarModel()
                        .equals("Cherokee")).toList();

        allcars.removeAll(carKirgistan);

        List<Car> carRussia = allcars.stream()
                .filter(car -> !car.getColor().equals("Yelow") ||
                        !car.getColor().equals("Red") ||
                        !car.getColor().equals("Green") ||
                        !car.getColor().equals("Blue") ||
                        car.getPrice() > 40000).toList();

        allcars.removeAll(carRussia);

        List<Car> carMongolia = allcars.stream()
                .filter(car -> car.getVin().contains("59")).toList();

        List<Double> list = Stream.of(carsTurkmenistan, carKirgistan, carKazahstan, carMongolia, carRussia, carUzbekistan)
                .mapToDouble(value -> value.stream().mapToInt(Car::getMass).sum()).map(value -> value / 1000 * 7.14)
                .boxed().toList();

        Optional<Double> reduce = list.stream().reduce(Double::sum);

        System.out.println(list);
        System.out.println(reduce);
    }

    private static void task15() throws IOException {
        List<Flower> flowers = Util.getFlowers();

        List<Flower> flowerList = flowers.stream()
                .sorted((o1, o2) -> o2.getOrigin().compareTo(o1.getOrigin()))
                .sorted((o1, o2) -> Integer.compare(o2.getPrice(), o1.getPrice()))
                .sorted((o1, o2) -> Double.compare(o2.getWaterConsumptionPerDay(), o1.getWaterConsumptionPerDay()))
                //не знаю как написать в filter() условие: от буквы "S" до буквы "C"
                .filter(flower -> flower.getFlowerVaseMaterial().contains("Glass") ||
                        flower.getFlowerVaseMaterial().contains("Aluminum") ||
                        flower.getFlowerVaseMaterial().contains(("Steel")))
                .toList();

        flowerList.stream()
                .mapToDouble(flower -> flower.getPrice() + flower.getWaterConsumptionPerDay() * 365 * 5 * 1.39 / 1000)
                .reduce(Double::sum).ifPresent(System.out::println);
    }
}