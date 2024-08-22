package Mockito_practice.happyhotelbooking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test11Annotations {

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

	@Test
	void should_payCorrectPrice_when_InputOk() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, true);
		double expected = 9 * 2 * 50.0;

		//when
		bookingService.makeBooking(bookingRequest);

		//then
		verify(paymentServiceMock, times(1)).pay(eq(bookingRequest),doubleArgumentCaptor.capture());
		/*
		here Instead of providing the value (as a parameter in pay method), that we expect, we will use captor to capture the value.
		so that we can reuse it later for various purpose.
		*/

		double capturedArgument = doubleArgumentCaptor.getValue();
		System.out.println(capturedArgument);

		assertEquals(expected, capturedArgument);

	}

	@Test
	void should_payCorrectPrices_when_MultipleCalls() {
		//given
		BookingRequest bookingRequest1 = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, true);

		BookingRequest bookingRequest2 = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 02),2, true);

		List<Double> expectedValues = Arrays.asList(900.0, 100.0);
		//when
		bookingService.makeBooking(bookingRequest1);
		bookingService.makeBooking(bookingRequest2);

		//then
		verify(paymentServiceMock, times(2)).pay(any(),doubleArgumentCaptor.capture());

		List<Double> capturedArgument = doubleArgumentCaptor.getAllValues();
		System.out.println(capturedArgument);

		assertEquals(expectedValues, capturedArgument);

	}

	

}
