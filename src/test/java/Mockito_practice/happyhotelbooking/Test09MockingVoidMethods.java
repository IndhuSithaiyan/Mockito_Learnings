package Mockito_practice.happyhotelbooking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class Test09MockingVoidMethods {

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
	void should_throwException_when_mailNotReady() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, false);
		doThrow(new BusinessException()).when(mailSenderMock).sendBookingConfirmation(any());

		//when
		Executable executable = () -> bookingService.makeBooking(bookingRequest);

		//then
		assertThrows(BusinessException.class, executable);
	}

	@Test
	void should_NotThrowException_when_mailReady() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, false);
		doNothing().when(mailSenderMock).sendBookingConfirmation(any());

		//when
		bookingService.makeBooking(bookingRequest);

		//then
		// no exception is thrown
		verify(mailSenderMock).sendBookingConfirmation(any());
	}

}
