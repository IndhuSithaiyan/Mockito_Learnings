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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Test12BddAliases {

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
	void should_countAvailablePlaces_when_multipleRoomAvailable() {
		//given
		List<Room> rooms = Arrays.asList(new Room("RoomNo308", 2), new Room("RoomNo307", 5));
		given(roomServiceMock.getAvailableRooms()).willReturn(rooms);
		int expected = 7;

		//when
		int actual = bookingService.getAvailablePlaceCount();

		//then
		assertEquals(expected, actual);

	}

	@Test
	void should_invokePayment_when_prepaid() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, true);

		//when
		bookingService.makeBooking(bookingRequest);

		//then
		verify(paymentServiceMock).pay(bookingRequest,900.0);
		then(paymentServiceMock).should(times(1)).pay(bookingRequest,900.0);

	}



}
