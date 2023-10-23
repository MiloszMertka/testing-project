package com.example.testing.service.internal;

import com.example.testing.dto.BorrowDto;
import com.example.testing.mapper.BorrowMapper;
import com.example.testing.repository.BorrowRepository;
import com.example.testing.service.BorrowUseCases;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class BorrowService implements BorrowUseCases {

    private final BorrowRepository borrowRepository;
    private final BorrowMapper borrowMapper;

    @Override
    public Collection<BorrowDto> getBorrows() {
        final var borrows = borrowRepository.findAll();
        return borrows.stream()
                .map(borrowMapper::mapBorrowToBorrowDto)
                .toList();
    }

    @Override
    public BorrowDto getBorrow(@NonNull Long id) {
        final var borrow = borrowRepository.findById(id).orElseThrow();
        return borrowMapper.mapBorrowToBorrowDto(borrow);
    }

    @Override
    public void borrowBook(@NonNull @Valid BorrowDto borrowDto) {
        final var borrow = borrowMapper.mapBorrowDtoToBorrow(borrowDto);
        borrowRepository.save(borrow);
    }

    @Override
    public void returnBook(@NonNull Long id) {
        final var borrow = borrowRepository.findById(id).orElseThrow();
        borrow.returnBook();
        borrowRepository.save(borrow);
    }

    @Override
    public void deleteBorrow(@NonNull Long id) {
        borrowRepository.deleteById(id);
    }

}
