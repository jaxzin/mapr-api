package us.mapr.gae.example.client;

import us.mapr.gae.example.model.Messages;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("messages")
public interface MessagesService extends RemoteService, Messages {
    // marker interface
}
