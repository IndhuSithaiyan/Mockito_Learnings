package Mockito_practice.happyhotelbooking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class Test07VerifyingBehaviour {

	private BookingService bookingService;
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;

	@BeforeEach
	void setUp(){
		this.paymentServiceMock = mock(PaymentService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.roomServiceMock = mock(RoomService.class);
		this.mailSenderMock = mock(MailSender.class);

		this.bookingService = new BookingService(paymentServiceMock,roomServiceMock,bookingDAOMock,mailSenderMock);

	}

	@Test
	void should_invokePayment_when_prepaid() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, true);
//		double expected = 9 * 2 * 50.0;
//		System.out.println(expected);  //900.0

		//when
		bookingService.makeBooking(bookingRequest);

		//then
		verify(paymentServiceMock).pay(bookingRequest,900.0);
		verify(paymentServiceMock, times(1)).pay(bookingRequest,900.0);
		verifyNoMoreInteractions(paymentServiceMock);		// checks if any other methods from this mock were called. here if pay method is called again for a second time, this will throw an exception.

	}


	@Test
	void should_noInvokePayment_when_notPrepaid() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, false);
//		double expected = 9 * 2 * 50.0;
//		System.out.println(expected);  //900.0

		//when
		bookingService.makeBooking(bookingRequest);

		//then
		/*
		this line verifies that Payment service was never called and the method pay() was never invoked for any kind of input
		 */
		verify(paymentServiceMock, never()).pay(bookingRequest,900.0);
		verifyNoInteractions(paymentServiceMock);
	}

}
