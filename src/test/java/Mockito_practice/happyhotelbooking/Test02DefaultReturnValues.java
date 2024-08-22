package Mockito_practice.happyhotelbooking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

class Test02DefaultReturnValues {

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

		System.out.println("List Returned "+ roomServiceMock.getAvailableRooms());
		System.out.println("Object Returned "+ roomServiceMock.findAvailableRoomId(null));
		System.out.println("Primitive Returned "+ roomServiceMock.getRoomCount());

	}

	@Test
	void should_countAvailablePlaces() {
		//given
		int expected = 0;

		//when
		int actual = bookingService.getAvailablePlaceCount();	// here roomService.getAvailableRooms() is returning default value of List as a empty.

		//then
		assertEquals(expected, actual);

	}



}
