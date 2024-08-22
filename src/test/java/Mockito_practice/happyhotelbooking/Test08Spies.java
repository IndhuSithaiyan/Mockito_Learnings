package Mockito_practice.happyhotelbooking;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.mockito.Mockito.*;

class Test08Spies {

	private BookingService bookingService;
	private PaymentService paymentServiceMock;
	private RoomService roomServiceMock;
	private BookingDAO bookingDAOMock;
	private MailSender mailSenderMock;

	@BeforeEach
	void setUp(){
		this.paymentServiceMock = mock(PaymentService.class);
		this.bookingDAOMock = spy(BookingDAO.class);
		this.roomServiceMock = mock(RoomService.class);
		this.mailSenderMock = mock(MailSender.class);

		this.bookingService = new BookingService(paymentServiceMock,roomServiceMock,bookingDAOMock,mailSenderMock);

	}

	/*
	Difference between mock and spies
	Mock = dummy object with no real logic
	Spy = real object with real logic that we can modify

	-> Mock doesn't have any logic of the mocked class and they simply return the default values such as null, empty lists. unless we their behaviour.
	-> Spies have all the logic from the mocked class, so they behave just like normal object of a class unless you modify some of the behaviour.

	mock : when(mock.method()).thenReturn();
	spy: doReturn().when(spy).method();

	so Spy is partial mock = a real object with real method that we can modify.
	 */

	@Test
	void should_makeBooking_when_CorrectInput() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, true);

		//when
		String bookingID = bookingService.makeBooking(bookingRequest);

		//then
		verify(bookingDAOMock).save(bookingRequest);
		System.out.println("BookingID : "+ bookingID);	// null -> mock is giving default value of the return type which is String = null

	}

	@Test
	void should_CancelBooking_when_CorrectInput() {
		//given
		BookingRequest bookingRequest = new BookingRequest("1", LocalDate.of(2020, 05, 01),
				LocalDate.of(2020, 05, 10),2, true);
		bookingRequest.setRoomId("1.3");
		String bookingId = "1";

		/*
		here why we are using doReturn is, we have BookingDAO is spy so this will trying to get the real value so we have null if you go through the code you can understand..
		so we want to change the behaviour of spy so that it returns bookingRequest from the "given" section.
		 */
		doReturn(bookingRequest).when(bookingDAOMock).get(bookingId);

		//when
		bookingService.cancelBooking(bookingId);

		//then
		verify(bookingDAOMock).get("1");
		verify(roomServiceMock).unbookRoom("1.3");
	}

}
