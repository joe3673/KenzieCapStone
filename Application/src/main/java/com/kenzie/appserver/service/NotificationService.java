package com.kenzie.appserver.service;

import com.kenzie.appserver.service.model.Notification;
import com.kenzie.capstone.service.client.LambdaServiceClient;
import com.kenzie.capstone.service.model.NotificationData;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {




        private LambdaServiceClient lambdaServiceClient;

        public NotificationService( LambdaServiceClient lambdaServiceClient) {
            this.lambdaServiceClient = lambdaServiceClient;
        }

        public Notification findById(String id) {
            NotificationData dataFromLambda = lambdaServiceClient.getNotificationData(id);

            Notification notification = new Notification(dataFromLambda.getId(), dataFromLambda.getData());



            return notification;
        }

        public Notification createNewNotification(String text) {

            NotificationData dataFromLambda = lambdaServiceClient.setNotificationData(text);


            Notification notification = new Notification(dataFromLambda.getId(),text);
            return notification;
        }


    }

