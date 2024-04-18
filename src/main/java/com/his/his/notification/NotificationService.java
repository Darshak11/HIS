package com.his.his.notification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import io.github.jav.exposerversdk.ExpoPushMessage;
import io.github.jav.exposerversdk.ExpoPushMessageTicketPair;
import io.github.jav.exposerversdk.ExpoPushTicket;
import io.github.jav.exposerversdk.PushClient;
import io.github.jav.exposerversdk.PushClientException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Service
@Builder
@AllArgsConstructor
public class NotificationService {
    private final NotificationTokenRepository notificationTokenRepository;

    public NotificationToken addToken(NotificationToken notificationToken) {
        if(!notificationTokenRepository.existsByToken(notificationToken.getToken())){
            return notificationTokenRepository.save(notificationToken);
        }
        return notificationTokenRepository.findByToken(notificationToken.getToken());
    }

    @Transactional
    public String removeToken(UUID employeeId,String token){
        System.out.println("Removing notification token for employee: "+employeeId+" with token: "+token);
        notificationTokenRepository.deleteDistinctByEmployeeIdAndToken(employeeId,token);
        return "Successfully removed the notification token";
    }

    public void sendNotificationToEmployee(UUID employeeId, String title, String message) {
        if (notificationTokenRepository.existsByEmployeeId(employeeId)) {
            List<NotificationToken> notificationTokens = notificationTokenRepository.findAllByEmployeeId(employeeId);
            for (NotificationToken notificationToken: notificationTokens) {
                try {
                    sendPushNotification(notificationToken.getToken(), title, message);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }

    public void sendPushNotification(String token, String title, String message) throws PushClientException, InterruptedException, ExecutionException{
        System.out.println("in sendPushNotification");
        if(!PushClient.isExponentPushToken(token))
            throw new RuntimeException("Token: "+ token + " is not valid.");

        ExpoPushMessage expoPushMessage = new ExpoPushMessage();
        expoPushMessage.getTo().add(token);
        expoPushMessage.setTitle(title);
        expoPushMessage.setBody(message);

        System.out.println("expoPushMessage" + expoPushMessage);

        List<ExpoPushMessage> expoPushMessageList = new ArrayList<>();
        expoPushMessageList.add(expoPushMessage);

        PushClient client = new PushClient();
        System.out.println("client.baseApiUrl" + client.baseApiUrl);

        List<List<ExpoPushMessage>> chunks = client.chunkPushNotifications(expoPushMessageList);
        System.out.println("chunks" + chunks);
        List<CompletableFuture<List<ExpoPushTicket>>> messageRepliesFutures = new ArrayList<>();

        for (List<ExpoPushMessage> chunk: chunks) {
            messageRepliesFutures.add(client.sendPushNotificationsAsync(chunk));
            System.out.println("messageRepliesFutures" + messageRepliesFutures);
        }

        List<ExpoPushTicket> allTickets = new ArrayList<>();
        for (CompletableFuture<List<ExpoPushTicket>> messageReplyFuture: messageRepliesFutures) {
            try {
//                for (ExpoPushTicket ticket: messageReplyFuture.get()) {
//                    allTickets.add(ticket);
//                }
                allTickets.addAll(messageReplyFuture.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> zippedMessagesTickets = client.zipMessagesTickets(expoPushMessageList, allTickets);

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> okTicketMessages = client.filterAllSuccessfulMessages(zippedMessagesTickets);
        String okTicketMessagesString = okTicketMessages.stream().map(
                p -> "Title: " + p.message.getTitle() + ", Id:" + p.ticket.getId()
        ).collect(Collectors.joining(","));
        System.out.println(
                "Received OK ticket for " +
                        okTicketMessages.size() +
                        " messages: " + okTicketMessagesString
        );

        List<ExpoPushMessageTicketPair<ExpoPushMessage>> errorTicketMessages = client.filterAllMessagesWithError(zippedMessagesTickets);
        String errorTicketMessagesString = errorTicketMessages.stream().map(
                p -> "Title: " + p.message.getTitle() + ", Error: " + p.ticket.getDetails().getError()
        ).collect(Collectors.joining(","));
        System.out.println(
                "Received ERROR ticket for " +
                        errorTicketMessages.size() +
                        " messages: " +
                        errorTicketMessagesString
        );

        /*
        // Countdown 30s
        int wait = 30;
        for (int i = wait; i >= 0; i--) {
            System.out.print("Waiting for " + wait + " seconds. " + i + "s\r");
            Thread.sleep(1000);
        }
        System.out.println("Fetching receipts...");

        List<String> ticketIds = (client.getTicketIdsFromPairs(okTicketMessages));
        CompletableFuture<List<ExpoPushReceipt>> receiptFutures = client.getPushNotificationReceiptsAsync(ticketIds);

        List<ExpoPushReceipt> receipts = new ArrayList<>();
        try {
            receipts = receiptFutures.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                "Received " + receipts.size() + " receipts:");

        for (ExpoPushReceipt receipt : receipts) {
            System.out.println(
                    "Receipt for id: " +
                            receipt.getId() +
                            " had status: " +
                            receipt.getStatus());

        }
         */
    }
}
