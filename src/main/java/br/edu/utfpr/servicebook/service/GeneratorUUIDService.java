package br.edu.utfpr.servicebook.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GeneratorUUIDService {

    public String generateUUID() {
        return UUID.randomUUID().toString();
    }

}
