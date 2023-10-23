package com.example.testing.service;

import com.example.testing.dto.BorrowDto;

import java.util.Collection;

public interface BorrowUseCases {

    Collection<BorrowDto> getBorrows();

    BorrowDto getBorrow(Long id);

    void borrowBook(BorrowDto borrowDto);

    void returnBook(Long id);

    void deleteBorrow(Long id);

}
