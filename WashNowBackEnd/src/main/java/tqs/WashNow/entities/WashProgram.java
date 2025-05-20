package tqs.WashNow.entities;

// adicionei os tipos mais gerais de lavagem que englobo os outros atributos
// para se calhar os usarmos e colocar lá dentro os enums mais específicos como SOAP, WAX,..
public enum WashProgram {
    PRE_WASH,
    WATER_ONLY,
    FOAM_SOAP,
    SOAP,
    WAX,
    FOAM,
    HIGH_PRESSURE_RINSE,
    NORMAL,
    SUPER,
    EXTRA,
    PREMIUM
}