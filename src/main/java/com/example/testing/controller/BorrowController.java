package com.example.testing.controller;

import com.example.testing.dto.BorrowDto;
import com.example.testing.service.BorrowUseCases;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class BorrowController {

    private final BorrowUseCases borrowUseCases;

    @GetMapping
    public Collection<BorrowDto> getBorrows() {
        return borrowUseCases.getBorrows();
    }

    @GetMapping("/{id}")
    public BorrowDto getBorrow(@PathVariable Long id) {
        return borrowUseCases.getBorrow(id);
    }

    @PostMapping
    public void borrowBook(@RequestBody BorrowDto borrowDto) {
        borrowUseCases.borrowBook(borrowDto);
    }

    @PatchMapping("/{id}")
    public void returnBook(@PathVariable Long id) {
        borrowUseCases.returnBook(id);
    }

    @DeleteMapping("/{id}")
    public void deleteBorrow(@PathVariable Long id) {
        borrowUseCases.deleteBorrow(id);
    }

}
