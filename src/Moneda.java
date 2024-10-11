import java.util.HashMap;
import java.util.Map;

public class Moneda implements Comparable<Moneda> {

    private String nombre;
    private double valor;
    private static Map<String, Double> tasasDeCambio = new HashMap<>();

    public Moneda(String nombre, double valor) {
        this.nombre = nombre;
        this.valor = valor;

    }



    public double getValor() {
        return valor;
    }

    public String getNombre() {
        return nombre;
    }
    public static void agregarTasa(String moneda, double tasa) {
        tasasDeCambio.put(moneda, tasa);
    }
    public static Map<String, Double> getTasasDeCambio() {
        return tasasDeCambio;
    }






    public double conversor(String destino, double cantidad) {
        Double tasaDestino = tasasDeCambio.get(destino);
        if (tasaDestino != null) {
            return cantidad * tasaDestino;
        } else {
            throw new IllegalArgumentException("Tasa de cambio no disponible para " + destino);


        }
    }

    @Override
    public int compareTo(Moneda otraMoneda) {
        return this.getNombre().compareTo(otraMoneda.getNombre());
    }

    @Override
    public String toString() {
        return "Moneda{" +
                 nombre + '\'' +
                ", valor=" + valor +
                '}';
    }
}