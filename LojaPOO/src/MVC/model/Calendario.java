/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package MVC.model;

import java.time.LocalDate;

/**
 *
 * @author joao
 */
public class Calendario {
    private LocalDate dia;
    
    public Calendario() {
        this.dia = LocalDate.now();
    }
    
    public void passarDia() {
        this.dia = this.dia.plusDays(1);
    }
    
    @Override
    public String toString() {
        return "Calendario{" + "dia=" + dia + '}';
    }

    public LocalDate getDia() {
        return dia;
    }

    public void setDia(LocalDate dia) {
        this.dia = dia;
    }
 
}
