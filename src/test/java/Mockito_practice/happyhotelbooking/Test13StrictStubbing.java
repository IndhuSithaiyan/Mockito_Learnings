package Mockito_practice.happyhotelbooking;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
class Test13StrictStubbing {

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
	Mockito provides a feature. If you have Unnecessary stubbings in your code that will be detected.
	if for any reason you want to keep the unnecessary stubbing in place then mockito provides a method called lenient() and it should be placed in front of stubbing
	 */

	@Test
	void should_invokePayment_when_prepaid() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, false);

		lenient().when(paymentServiceMock.pay(any(),anyDouble())).thenReturn("1");

		//when
		bookingService.makeBooking(bookingRequest);

		//then
		// no exception is thrown
	}



}
