package com.hungnguyen.luxphone.model;

public class Detail {
    String name, RAM, IM, display, pin;

    public Detail() {
    }

    public Detail(String IM, String RAM, String display, String name, String pin) {
        this.name = name;
        this.RAM = RAM;
        this.IM = IM;
        this.display = display;
        this.pin = pin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public String getIM() {
        return IM;
    }

    public void setIM(String IM) {
        this.IM = IM;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }
}
