package main;

import fachada.Fachada;
import tela.MainFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Fachada fachada = new Fachada();
        SwingUtilities.invokeLater(() -> new MainFrame(fachada).setVisible(true));
    }
}
