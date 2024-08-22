package Mockito_practice.happyhotelbooking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
Matcher Rules
1.Use any() for objects. For Primitives, uses anyDouble(), anyBoolean() etc.
2.Use eq() to mix matchers and concrete values: method(any(), eq(400.0))
3.For Nullable String, use any().
*/
class Test06ArgumentMatchers {

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
	void should_notCompleteBooking_when_priceTooHigh() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, true);

//		when(this.roomServiceMock.findAvailableRoomId(bookingRequest)).thenReturn("Room 1");
//		this.bookingService = mock(BookingService.class);
//		when(this.bookingService.calculatePrice(bookingRequest)).thenReturn(600.00);
//		when(this.paymentServiceMock.pay(bookingRequest, 600.00)).thenThrow(UnsupportedOperationException.class);

		when(this.paymentServiceMock.pay(any(), anyDouble())).thenThrow(UnsupportedOperationException.class);

		//when
		Executable executable = () -> bookingService.makeBooking(bookingRequest);

		//then
		assertThrows(UnsupportedOperationException.class, executable);

	}

}
