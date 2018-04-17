package cb.tourism.mq;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "test_queue")
public class Receiver {

    @RabbitHandler
    public void process(String hello){
        System.out.println("Receiver: " + hello);
    }
}
