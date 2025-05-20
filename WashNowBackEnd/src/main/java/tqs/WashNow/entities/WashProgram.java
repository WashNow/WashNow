package tqs.WashNow.entities;

// adicionei os tipos mais gerais de lavagem que englobo os outros atributos
// para se calhar os usarmos e colocar lá dentro os enums mais específicos como SOAP, WAX,..
public enum WashProgram {
    //PRE_WASH,
    //WATER_ONLY,
    //FOAM_SOAP,
    //SOAP,
    //WAX,
    //FOAM,
    //HIGH_PRESSURE_RINSE,
    NORMAL(0.20),
    SUPER(0.30),
    EXTRA(0.40),
    PREMIUM(0.50),
    PERSONALIZED(0.60);

    private final double pricePerMinute;

    WashProgram(double pricePerMinute) {
        this.pricePerMinute = pricePerMinute;
    }

    public double getPricePerMinute() {
        return pricePerMinute;
    }
}