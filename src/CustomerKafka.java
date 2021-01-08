import Common.RandomGUID;
import com.alibaba.fastjson.JSON;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author Wayne
 * @Classname CustomerKafka
 * @Description TODO
 * @Date 20/11/14 下午6:14
 * @Version V1.0
 */
public class CustomerKafka {

    private static Producer<String, String> producer = null;

    private void initProducerProps() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.11.90:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        producer = new KafkaProducer<String, String>(props);
    }
    private void init() {
        if (null == producer) {
            initProducerProps();
        }
    }


    public void sendMessage(Map message) {
        init();
//        producer.send(new KeyedMessage<>("TEST_OSN_TLOG_TOPIC", RandomGUID.getRandomGUID(), JSON.toJSONString(message)));
        System.out.println("kafka发送消息{}成功," + JSON.toJSONString(message));


//        /**
//         * 1、如果指定了某个分区,会只将消息发到这个分区上
//         * 2、如果同时指定了某个分区和key,则也会将消息发送到指定分区上,key不起作用
//         * 3、如果没有指定分区和key,那么将会随机发送到topic的分区中
//         * 4、如果指定了key,那么将会以hash<key>的方式发送到分区中 （此处按 hash(key) 路由）
//         */
//        ProducerRecord<String, String> record = new ProducerRecord<String, String>(message.getTopic(), message.getKey(), JSON.toJSONString(message));
//
//        // send方法是异步的,添加消息到缓存区等待发送,并立即返回,这使生产者通过批量发送消息来提高效率
//        // kafka生产者是线程安全的,可以单实例发送消息
//        producer.send(record, new Callback() {
//            public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
//                logger.info("kafka发送消息成功, offset:[{}]", recordMetadata.offset());
//
//                if (null != exception) {
//                    logger.error("kafka发送消息失败:" + exception.getMessage(), exception);
//                    retryKafkaMessage(message.getKey(),message.getTopic(), JSON.toJSONString(message));
//                }
//            }
//        });
    }

    public static void main(String[] args) {
        Map kafkaMessage = new HashMap();
        kafkaMessage.put("topic","TEST_OSN_TLOG_TOPIC");
        kafkaMessage.put("key", RandomGUID.getRandomGUID());
        Map map = new HashMap();
        map.put("testId", RandomGUID.getRandomGUID());
        map.put("testName", "wayne");
        kafkaMessage.put("message", kafkaMessage);
        Properties props = new Properties();
        props.put("bootstrap.servers", "192.168.11.90:9092");
        props.put("acks", "all");
        props.put("retries", 0);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        String message = "{\"key\":\"9S2e1D44Evu15o9Cq0758oP5Iv2xPZ94\",\"messageBody\":{\"capacity\":1,\"dsIndex\":\"00\",\"isLogFile\":0,\"osnReqRecvTime\":1605374680474,\"sdf\":\"yyyy-MM-dd HH:mm:ss.SSS\",\"svcType\":4,\"transIdc\":\"202011150124404741130201208111\"},\"topic\":\"TEST_DLOG_REQ_TOPIC\"}";
        Producer<String, String> producer = new KafkaProducer<String, String>(props);
        producer.send(new ProducerRecord<String, String>("TEST_TLOG_OSN_RSP_TOPIC", RandomGUID.getRandomGUID(), message));
        System.out.println("发送成功");
        producer.close();


    }

}
