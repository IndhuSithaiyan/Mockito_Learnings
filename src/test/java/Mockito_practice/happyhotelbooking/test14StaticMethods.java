package Mockito_practice.happyhotelbooking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class test14StaticMethods {

	@InjectMocks
	private BookingService bookingService;

	@Mock
	private PaymentService paymentServiceMock;

	@Mock
	private RoomService roomServiceMock;

	@Spy
	private BookingDAO bookingDAOMock;

	@Mock
	private MailSender mailSenderMock;

	@Captor
	private ArgumentCaptor<Double> doubleArgumentCaptor;

	/*
	changes required in dependency, instead of mockito-core, provide mockito-inline

	 */

	@Test
	void should_invokePayment_when_prepaid() {
		//given
		try(MockedStatic<CurrencyConverter> mockedStatic = mockStatic(CurrencyConverter.class)){
			BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
					LocalDate.of(2020, 05, 10),2, false);

			double expected = 900.0;
			mockedStatic.when(() -> CurrencyConverter.toEuro(anyDouble())).thenReturn(900.0);

			//when
			double actual = bookingService.calculatePriceEuro(bookingRequest);

			//then
			assertEquals(expected, actual);
		}

	}



}
