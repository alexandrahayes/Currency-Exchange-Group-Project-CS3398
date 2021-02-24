import java.util.ArrayList;

public class Fiat {
   // Attributes
   public String name;
   public ArrayList<String> denomination;
   public float conversionRate;

    // Methods
    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    ArrayList<String> getDenominations() {
        return denomination;
    }

    void setDenominations(ArrayList<String> denominations) {
        denomination = denominations;
    }

    float getConversionRate() {
        return conversionRate;
    }

    void setConversionRate(float conversionRate) {
        this.conversionRate = conversionRate;
    }

    Fiat(String name, ArrayList<String> denominations, float conversionRate) {
        this.name = name;
        denomination = denominations;
        this.conversionRate = conversionRate;
    }
}