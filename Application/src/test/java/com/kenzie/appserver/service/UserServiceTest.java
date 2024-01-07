package com.kenzie.appserver.service;

import com.kenzie.appserver.exception.EventNotFoundException;
import com.kenzie.appserver.exception.UserNotFoundException;
import com.kenzie.appserver.repositories.EventRepository;
import com.kenzie.appserver.repositories.UserRepository;
import com.kenzie.appserver.repositories.model.EventRecord;
import com.kenzie.appserver.repositories.model.UserRecord;
import com.kenzie.appserver.service.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private UserRecord mockUser;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mockUser = mock(UserRecord.class);

        when(mockUser.getUserID()).thenReturn("testUser");
        when(mockUser.getUserName()).thenReturn("testUser");
        when(mockUser.getPassword()).thenReturn("testPassword");
        when(mockUser.getEmail()).thenReturn("test@example.com");
        when(mockUser.getFirstName()).thenReturn("Test");
        when(mockUser.getLastName()).thenReturn("User");
        when(mockUser.getUserType()).thenReturn("Standard");
        when(mockUser.getEventsList()).thenReturn(new ArrayList<>());
        when(mockUser.getNotifications()).thenReturn(new ArrayList<>());
        when(mockUser.getFriends()).thenReturn(new ArrayList<>());
    }

    @Test
    void findUserById_WhenUserExists() {
        // GIVEN
        String validId = "validId";
        when(userRepository.findById(validId)).thenReturn(Optional.of(mockUser));

        // WHEN
        UserRecord result = userService.findUserById(validId);

        // THEN
        assertNotNull(result);
        assertEquals(mockUser.getUserID(), result.getUserID());
    }

    @Test
    void findUserById_WhenUserDoesNotExist() {
        // GIVEN
        String invalidId = "invalidId";
        when(userRepository.findById(invalidId)).thenReturn(Optional.empty());

        // WHEN


        // THEN
        assertThrows(UserNotFoundException.class, () -> userService.findUserById(invalidId));
    }

    @Test
    void validateUser_WhenUserExists() {
        // GIVEN
        String validId = "validId";
        String validPassword = "validPassword";
        when(userRepository.findById(validId)).thenReturn(Optional.of(mockUser));
        when(mockUser.getPassword()).thenReturn(validPassword);

        // WHEN
        boolean validResult = userService.validateUser(validId, validPassword);
        boolean invalidResult = userService.validateUser(validId, "wrongPassword");

        // THEN
        assertTrue(validResult);
        assertFalse(invalidResult);
    }

    @Test
    void validateUser_WhenUserDoesNotExist() {
        // GIVEN
        String invalidId = "invalidId";
        String validPassword = "validPassword";
        when(userRepository.findById(invalidId)).thenReturn(Optional.empty());

        // WHEN
        boolean result = userService.validateUser(invalidId, validPassword);

        // THEN
        assertFalse(result);
    }

    @Test
    void getAllUsers_WhenUsersExist() {
        // GIVEN
        when(userRepository.findAll()).thenReturn(Arrays.asList(mockUser, mockUser));

        // WHEN
        List<UserRecord> result = userService.getAllUsers();

        // THEN
        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getAllUsers_WhenUsersDoNotExist() {
        // GIVEN
        when(userRepository.findAll()).thenReturn(Arrays.asList());

        // WHEN
        List<UserRecord> result = userService.getAllUsers();

        // THEN
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    @Test
    void validateUser_ValidCredentials() {
        // GIVEN
        String userId = "userId";
        String password = "password";
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(mockUser.getPassword()).thenReturn(password);

        // WHEN
        boolean result = userService.validateUser(userId, password);

        // THEN
        assertTrue(result);
    }
    @Test
    void validateUser_InvalidCredentials() {
        // GIVEN
        String userId = "userId";
        String correctPassword = "correctPassword";
        String incorrectPassword = "incorrectPassword";
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(mockUser.getPassword()).thenReturn(correctPassword);

        // WHEN
        boolean result = userService.validateUser(userId, incorrectPassword);

        // THEN
        assertFalse(result);
    }

    @Test
    void addNewUser_Successful() {
        // GIVEN
        String userName = "username";
        String password = "password";
        String email = "email@example.com";
        String firstName = "FirstName";
        String lastName = "LastName";
        String userType = "UserType";
        when(userRepository.save(any(UserRecord.class))).thenReturn(mockUser);
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        // WHEN
        UserRecord result = userService.addNewUser(userName, password, email, firstName, lastName, userType);

        // THEN
        assertNotNull(result);
        assertEquals(userName, result.getUserName());
    }

    @Test
    void updateUser_WhenUserExists() {
        // GIVEN
        User user = new User("f", "f", "f", "f", "f", "f");
        when(userRepository.save(any(UserRecord.class))).thenReturn(mockUser);
        when(userRepository.existsById(user.getUserID())).thenReturn(true);

        // WHEN
        userService.updateUser(user);

        // THEN
        verify(userRepository).save(any(UserRecord.class));
    }


    @Test
    void updateUser_UserDoesNotExist() {
        // GIVEN
        User nonExistingUser = new User("f", "f", "f", "f", "f", "f");
        when(userRepository.existsById(nonExistingUser.getUserID())).thenReturn(false);

        // WHEN & THEN
        userService.updateUser(nonExistingUser);
    }


    @Test
    void deleteUserById_ExistingUser() {
        // GIVEN
        String userId = "userId";

        // WHEN
        userService.deleteUserById(userId);

        // THEN
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteUserById_NonExistingUser() {
        // GIVEN
        String nonExistingUserId = "nonExistingUserId";
        doThrow(new UserNotFoundException("")).when(userRepository).deleteById(nonExistingUserId);

        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> userService.deleteUserById(nonExistingUserId));
    }


    @Test
    void shareEventsWithFriends_AlwaysReturnsTrue() {
        // GIVEN
        String userId = "userId";
        String eventId = "eventId";
        UserRecord userRecord = new UserRecord("l", "p", "k", "f", "g", "m");
        when(userRepository.findById(userId)).thenReturn(Optional.of(userRecord));
        // WHEN

        // THEN
        assertDoesNotThrow(() -> userService.shareEventWithFriend(userId, eventId));
    }

    @Test
    void validateUser_UserExistsButPasswordMismatch() {
        // GIVEN
        String userId = "existingUserId";
        String correctPassword = "correctPassword";
        String incorrectPassword = "incorrectPassword";
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(mockUser.getPassword()).thenReturn(correctPassword);

        // WHEN
        boolean result = userService.validateUser(userId, incorrectPassword);

        // THEN
        assertFalse(result);
    }

    @Test
    void addNewUser_FailsToAdd() {
        // GIVEN
        when(userRepository.save(any(UserRecord.class))).thenThrow(new RuntimeException("Database error"));

        // WHEN & THEN
        assertThrows(RuntimeException.class, () -> userService.addNewUser("username", "password", "email", "firstName", "lastName", "userType"));
    }

    @Test
    void addEventToList_EventSuccessfullyAdded() {
        // GIVEN
        String userId = "userId";
        String eventId = "eventId";
        EventRecord eventRecord = new EventRecord();
        eventRecord.setPeopleAttending(new ArrayList<>());
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(eventRecord));

        // WHEN
        userService.addEventToList(userId, eventId);

        // THEN
        verify(userRepository).save(mockUser);
        assertTrue(mockUser.getEventsList().contains(eventId));
    }
    @Test
    void addEventToList_UserNotFound() {
        // GIVEN
        String nonExistingUserId = "nonUserId";
        String nonExistingEventId = "nonEventId";
        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());
        when(eventRepository.findById(nonExistingEventId)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> userService.addEventToList(nonExistingUserId, nonExistingEventId));
    }

    @Test
    void addEventToList_EventNotFound() {
        // GIVEN
        String nonExistingUserId = "nonUserId";
        String nonExistingEventId = "nonEventId";
        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.of(new UserRecord()));
        when(eventRepository.findById(nonExistingEventId)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(EventNotFoundException.class, () -> userService.addEventToList(nonExistingUserId, nonExistingEventId));
    }

    @Test
    void addFriend_FriendSuccessfullyAdded() {
        // GIVEN
        String userId = "userId";
        String friendId = "friendId";
        UserRecord user = new UserRecord();
        user.setUserID("l");
        user.setFriends(new ArrayList<>());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserRecord friendUser = new UserRecord();
        friendUser.setUserID("k");
        friendUser.setFriends(new ArrayList<>());
        when(userRepository.findById(friendId)).thenReturn(Optional.of(friendUser));

        // WHEN
        userService.addFriend(userId, friendId);

        // THEN
        verify(userRepository).save(user);
        verify(userRepository).save(friendUser);
        assertTrue(user.getFriends().contains(friendId));
        assertTrue(friendUser.getFriends().contains(userId));
    }

    @Test
    void addFriend_FriendAlreadyAdded() {
        // GIVEN
        String userId = "userId";
        String friendId = "friendId";
        UserRecord user = new UserRecord();
        user.setUserID("l");
        user.setFriends(new ArrayList<>());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserRecord friendUser = new UserRecord();
        friendUser.setUserID("k");
        friendUser.setFriends(new ArrayList<>());
        user.getFriends().add(friendId);
        when(userRepository.findById(friendId)).thenReturn(Optional.of(friendUser));

        // WHEN
        userService.addFriend(userId, friendId);

        // THEN
        verify(userRepository, times(0)).save(user);
        verify(userRepository, times(0)).save(friendUser);
    }


    @Test
    void addFriend_UserOrFriendNotFound() {
        // GIVEN
        String nonExistingUserId = "nonUserId";
        String nonExistingFriendId = "nonFriendId";
        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());
        when(userRepository.findById(nonExistingFriendId)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> userService.addFriend(nonExistingUserId, nonExistingFriendId));
    }

    @Test
    void viewFriendsEvents_SuccessfulRetrieval() {
        // GIVEN
        String userId = "userId";
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        List<String> mockEventsList = Arrays.asList("event1", "event2");
        when(mockUser.getEventsList()).thenReturn(mockEventsList);

        // WHEN
        List<String> events = userService.viewFriendsEvents(userId);

        // THEN
        assertNotNull(events);
        assertEquals(mockEventsList, events);
    }
    @Test
    void viewFriendsEvents_UserNotFound() {
        // GIVEN
        String nonExistingUserId = "nonUserId";
        when(userRepository.findById(nonExistingUserId)).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(UserNotFoundException.class, () -> userService.viewFriendsEvents(nonExistingUserId));
    }


}

