package com.kenzie.capstone.service;


import com.kenzie.capstone.service.dao.NotificationDao;
import com.kenzie.capstone.service.model.NotificationData;
import com.kenzie.capstone.service.model.NotificationRecord;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LambdaServiceTest {

    /** ------------------------------------------------------------------------
     *  expenseService.getExpenseById
     *  ------------------------------------------------------------------------ **/

    private NotificationDao notificationDao;
    private LambdaService lambdaService;

    @BeforeAll
    void setup() {
        this.notificationDao = mock(NotificationDao.class);
        this.lambdaService = new LambdaService(notificationDao);
    }

    @Test
    void setDataTest_validData_returnsCorrectResponse() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<String> dataCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String data = "somedata";

        // WHEN
        NotificationData response = this.lambdaService.setNotificationData(data);

        // THEN
        verify(notificationDao, times(1)).setNotificationData(idCaptor.capture(), dataCaptor.capture());

        assertNotNull(idCaptor.getValue(), "An ID is generated");
        assertEquals(data, dataCaptor.getValue(), "The data is saved");

        assertNotNull(response, "A response is returned");
        assertEquals(idCaptor.getValue(), response.getId(), "The response id should match");
        assertEquals(data, response.getData(), "The response data should match");
    }

    @Test
    void getDataTest_validData_returnsCorrectResponse() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String id = "fakeid";
        String data = "somedata";
        NotificationRecord record = new NotificationRecord();
        record.setId(id);
        record.setData(data);


        when(notificationDao.getNotificationData(id)).thenReturn(Arrays.asList(record));

        // WHEN
        NotificationData response = this.lambdaService.getNotificationData(id);

        // THEN
        verify(notificationDao, times(1)).getNotificationData(idCaptor.capture());

        assertEquals(id, idCaptor.getValue(), "The correct id is used");

        assertNotNull(response, "A response is returned");
        assertEquals(id, response.getId(), "The response id should match");
        assertEquals(data, response.getData(), "The response data should match");
    }

    // Write additional tests here

    @Test
    void getDataTest_noNotifications_returnsNull() {
        ArgumentCaptor<String> idCaptor = ArgumentCaptor.forClass(String.class);

        // GIVEN
        String id = "fakeid";
        String data = "somedata";
        NotificationRecord record = new NotificationRecord();
        record.setId(id);
        record.setData(data);


        when(notificationDao.getNotificationData(id)).thenReturn(new ArrayList<>());

        // WHEN
        NotificationData response = this.lambdaService.getNotificationData(id);

        // THEN

        assertNull(response, "A response is not returned.");
    }

}
