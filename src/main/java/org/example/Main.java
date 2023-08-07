package org.example;

import org.example.service.ClientService;

public class Main {
    public static void main(String[] args) {
        System.out.println(new ClientService().listAll());
    }
}
