package com.card.game.message;

import com.card.game.message.pojo.Message;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author tomyou
 * @version v1.0 2023-01-15-9:39 PM
 */
@Component
public class ProviderManager implements MessageManager {

    private final List<MessageProvider> providers;

    public ProviderManager(List<MessageProvider> providers) {
        this.providers = providers;
    }

    @Override
    public Boolean sendMessage(Message message) {
        Class<? extends Message> toTest = message.getClass();
        Boolean flag = false;

        for (MessageProvider provider : providers) {
            if (!provider.supports(toTest)) {
                continue;
            }
            flag = provider.sendMessage(message);
        }


        return flag;
    }
}
