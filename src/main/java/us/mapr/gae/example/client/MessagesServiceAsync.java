package us.mapr.gae.example.client;

import java.util.List;

import us.mapr.gae.example.model.Message;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MessagesServiceAsync {

    void getAll(AsyncCallback<List<Message>> callback);

    void create(Message message, AsyncCallback<Void> callback);

    void deleteById(Long id, AsyncCallback<Void> callback);

}
