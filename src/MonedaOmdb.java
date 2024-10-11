import com.google.gson.annotations.SerializedName;

import java.util.Map;

public record MonedaOmdb(@SerializedName("conversion_rates") Map<String, Double> conversiones) {
    public Map<String, Double> getTasasDeCambio() {
        return conversiones;
    }

}
