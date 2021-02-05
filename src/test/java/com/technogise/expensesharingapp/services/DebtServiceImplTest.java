package com.technogise.expensesharingapp.services;

import com.technogise.expensesharingapp.models.Debt;
import com.technogise.expensesharingapp.repositories.DebtRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ActiveProfiles("test")
public class DebtServiceImplTest {

  @Mock
  private DebtRepository mockDebtRepository;

  @InjectMocks
  private DebtServiceImpl mockDebtServiceImpl;

  @Test
  void testGetAllDebtsByUserId() {
    Debt Debt1 = new Debt(1L, 2L, 105.0);
    Debt Debt2 = new Debt(2L, 3L, 210.0);
    Debt Debt3 = new Debt(3L, 1L, 180.0);
    List<Debt> expectedDebts = List.of(Debt1, Debt2, Debt3);

    Mockito.when(mockDebtRepository.getAllDebtsByUserId(any(Long.class))).thenReturn(expectedDebts);

    List<Debt> actualDebts = mockDebtServiceImpl.getAllDebtsByUserId(4L);

    Assertions.assertEquals(expectedDebts, actualDebts);
  }
}
