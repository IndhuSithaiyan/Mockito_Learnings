package Mockito_practice.happyhotelbooking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class Test04MultipleThenReturnCalls {

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
	void should_countAvailablePlaces_when_calledMultipleTimes() {
		//given
		List<Room> rooms = Arrays.asList(new Room("RoomNo308", 2), new Room("RoomNo307", 5));
		when(roomServiceMock.getAvailableRooms())
				.thenReturn(rooms)
				.thenReturn(Collections.singletonList(new Room("RoomNo300", 3)))
				.thenReturn(Collections.emptyList());
		int expectedFirstCall = 7;
		int expectedSecondCall = 3;
		int expectedThirdCall = 0;

		//when
		int actualFirst = bookingService.getAvailablePlaceCount();
		int actualSecond = bookingService.getAvailablePlaceCount();
		int actualThird = bookingService.getAvailablePlaceCount();

		//then
		assertAll(
				() -> assertEquals(expectedFirstCall, actualFirst),
				() -> assertEquals(expectedSecondCall, actualSecond),
				() -> assertEquals(expectedThirdCall, actualThird)
		);

	}

}
