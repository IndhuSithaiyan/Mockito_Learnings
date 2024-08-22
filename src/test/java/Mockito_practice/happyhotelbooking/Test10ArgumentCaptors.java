package Mockito_practice.happyhotelbooking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class Test10ArgumentCaptors {

	/*
	Argument captors are allow us to capture the arguments passed to methods.
	 */

	private BookingService bookingService;
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;
	private ArgumentCaptor<Double> doubleArgumentCaptor;

	@BeforeEach
	void setUp(){
		this.paymentServiceMock = mock(PaymentService.class);
		this.bookingDAOMock = mock(BookingDAO.class);
		this.roomServiceMock = mock(RoomService.class);
		this.mailSenderMock = mock(MailSender.class);

		this.bookingService = new BookingService(paymentServiceMock,roomServiceMock,bookingDAOMock,mailSenderMock);

		this.doubleArgumentCaptor = ArgumentCaptor.forClass(Double.class);
	}

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
