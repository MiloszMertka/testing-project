package com.example.testing.mapper;

import com.example.testing.dto.BorrowDto;
import com.example.testing.model.Borrow;
import org.springframework.lang.NonNull;

public interface BorrowMapper {

    BorrowDto mapBorrowToBorrowDto(@NonNull Borrow borrow);

    Borrow mapBorrowDtoToBorrow(@NonNull BorrowDto borrowDto);

}
