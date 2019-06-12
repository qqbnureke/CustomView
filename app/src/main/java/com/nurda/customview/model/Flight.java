package com.nurda.customview.model;

public final class Flight {

    public final String number;
    public final String departureDate;
    public final String duration;

    public final String takeOffTime;
    public final String landingTime;

    public final Airport departureAirport;
    public final Airport arrivalAirport;

    public Flight( String number,
                   String departureDate,
                   String takeOffTime,
                   String landingTime,
                   String duration,
                   Airport departureAirport,
                   Airport arrivalAirport) {
        this.number = number;
        this.departureDate = departureDate;
        this.takeOffTime = takeOffTime;
        this.landingTime = landingTime;
        this.duration = duration;
        this.departureAirport = departureAirport;
        this.arrivalAirport = arrivalAirport;
    }

}