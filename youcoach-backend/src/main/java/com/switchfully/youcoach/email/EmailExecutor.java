package com.switchfully.youcoach.email;

import com.switchfully.youcoach.email.command.EmailCommand;
import com.switchfully.youcoach.email.command.EmailHandler;
import com.switchfully.youcoach.email.exception.SendingMailError;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;

import static java.lang.String.format;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

@Component
public class EmailExecutor {

    private final Map<Class<?>, EmailHandler> handlerMap;

    public EmailExecutor(List<EmailHandler<? extends EmailCommand>> emailHandlerList) {
        this.handlerMap = emailHandlerList.stream()
                .collect(toMap(EmailHandler::getCommandType, identity()));
    }

    public void execute(EmailCommand command) {
        EmailHandler<? super EmailCommand> handler = handlerMap.get(command.getClass());

        if (handler == null) {
            throw new RuntimeException(format("No commandhandler found for command type %s", command.getClass().getSimpleName()));
        }

        try {
            handler.handle(command);
        } catch (MessagingException exception) {
            throw new SendingMailError(exception);
        }
    }
}
