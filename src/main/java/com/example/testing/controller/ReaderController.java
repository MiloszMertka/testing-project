package com.example.testing.controller;

import com.example.testing.dto.ReaderDto;
import com.example.testing.service.ReaderUseCases;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/readers")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class ReaderController {

    private final ReaderUseCases readerUseCases;

    @GetMapping
    public Collection<ReaderDto> getReaders() {
        return readerUseCases.getReaders();
    }

    @GetMapping("/{id}")
    public ReaderDto getReader(@PathVariable Long id) {
        return readerUseCases.getReader(id);
    }

    @PostMapping
    public void createReader(@RequestBody ReaderDto readerDto) {
        readerUseCases.createReader(readerDto);
    }

    @PatchMapping("/{id}")
    public void updateReader(@PathVariable Long id, @RequestBody ReaderDto readerDto) {
        readerUseCases.updateReader(id, readerDto);
    }

    @DeleteMapping("/{id}")
    public void deleteReader(@PathVariable Long id) {
        readerUseCases.deleteReader(id);
    }

}
