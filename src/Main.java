import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonNull;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.Map;
import java.util.Scanner;

public class Main {

    private static final String API_KEY = "590aecb323be1b64d294533b";
    private static final String URL = "https://v6.exchangerate-api.com/v6/";
    private static final Scanner lectura = new Scanner(System.in);

    public static void main(String[] args) throws IOException, InterruptedException {
        int opcion = 0;

        do {
            iniciarMenu();

            if (lectura.hasNextInt()) {
                opcion = lectura.nextInt();

                if (opcion >= 1 && opcion <= 7) {
                    iniciarConversor(opcion);
                } else {
                    System.out.println("Por favor, ingresa un número entre 1 y 7.");
                }
            } else {
                System.out.println("Por favor ingresa un número entero válido.");
                lectura.next(); // Avanza el scanner para evitar el bucle infinito
            }

        } while (opcion != 7);



    }


    private static void iniciarMenu() {

            System.out.println("*****************************************");

            System.out.println();
            System.out.println("Sea bienvenido al Convertidor de Moneda");
            System.out.println("1) Dólar =>> Peso argentino");
            System.out.println("2) Peso argentino =>> Dólar");
            System.out.println("3) Dólar =>> Real brasileño");
            System.out.println("4) Real brasileño =>> Dólar");
            System.out.println("5) Dólar =>> Peso colombiano");
            System.out.println("6) Peso colombiano =>> Dólar");
            System.out.println("7) Salir");
            System.out.println("Elija una opcion válida:");

            System.out.println("*****************************************");
        }



    private static void iniciarConversor(int opcion) throws IOException, InterruptedException {


            System.out.println("Escriba el valor a convertir:");
            double cantidad = lectura.nextDouble();
            String origen = "";
            String destino = "";

            switch (opcion) {
                case 1 -> {
                    origen = "USD";
                    destino = "ARS";
                }
                case 2 -> {
                    origen = "ARS";
                    destino = "USD";
                }
                case 3 -> {
                    origen = "USD";
                    destino = "BRL";
                }
                case 4 -> {
                    origen = "BRL";
                    destino = "USD";
                }
                case 5 -> {
                    origen = "USD";
                    destino = "COP";
                }
                case 6 -> {
                    origen = "COP";
                    destino = "USD";
                }
                default -> System.out.println("Opción no válida.");
            }
            if (!origen.isEmpty() && !destino.isEmpty()) {

                cargarTasasDeCambio(cantidad, origen, destino);
            }
        }




    private static void cargarTasasDeCambio(Double cantidad,String origen, String destino) throws IOException, InterruptedException {

        String direccion = URL + API_KEY + "/latest/" + origen;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(direccion))
                .build();
        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        String json = response.body();

        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE).create();

        MonedaOmdb monedaOmdb = gson.fromJson(json, MonedaOmdb.class);
        //Map<String, Double>tasas=monedaOmdb.getTasasDeCambio();
        // System.out.println(tasas);

        if (monedaOmdb != null && monedaOmdb.getTasasDeCambio() != null) {
            Map<String, Double> tasas = monedaOmdb.getTasasDeCambio();
            Double tasaDestino = tasas.get(destino); // Obtiene solo la tasa de la moneda destino

            if (tasaDestino != null) {
                System.out.println("El valor " + cantidad + " " + origen + " corresponde al valor final de " + tasaDestino * cantidad + " [" + destino +"]");
                // Puedes continuar con la conversión
            } else {
                System.out.println("Error: Tasa de cambio no encontrada para " + destino);
            }
        } else {
            System.out.println("Error: No se pudo obtener las tasas de cambio.");


        }


    }
}


