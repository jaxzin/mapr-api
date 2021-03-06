package us.mapr.gae.example.web;

import java.util.Collection;

import us.mapr.gae.example.client.MessagesService;
import us.mapr.gae.example.model.Message;
import us.mapr.gae.example.model.Messages;
import us.mapr.gae.example.server.MessageRepository;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class MessagesServiceImpl extends RemoteServiceServlet implements
        MessagesService {

    private Messages messageRepository = new MessageRepository();

    @Override
    public void create(Message message) {
        messageRepository.create(message);
    }

    @Override
    public void deleteById(Long id) {
        messageRepository.deleteById(id);
    }

    @Override
    public Collection<Message> getAll() {
        return messageRepository.getAll();
    }
}
