import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

    public static void main(String[] args) {

        Laboratorio laboratorio = new Laboratorio("Laboratorio de Hawkins", 3);

        Eleven eleven = new Eleven("Eleven", laboratorio);

        Hawkins hawkins = new Hawkins ("Hawkins", laboratorio);

        eleven.start();

        hawkins.start();

    }
