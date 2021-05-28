package example;

import static io.cloudevents.core.CloudEventUtils.mapData;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.aliyun.fc.runtime.Context;
import com.aliyun.fc.runtime.FunctionInitializer;
import com.aliyun.fc.runtime.StreamRequestHandler;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.data.PojoCloudEventData;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import io.cloudevents.jackson.PojoCloudEventDataMapper;

// This sample shows how to handle EventBridge event.
public class App implements StreamRequestHandler, FunctionInitializer {

    private ObjectMapper mapper = new ObjectMapper();
    
    public void initialize(Context context) throws IOException {
        // Do initialization if needed, e.g. setup database connection.
    }

    @Override
    public void handleRequest(
            InputStream inputStream, OutputStream outputStream, Context context) throws IOException {
        byte[] bytes = IOUtils.toByteArray(inputStream);
        CloudEvent inputEvent = getFormat().deserialize(bytes);

        PojoCloudEventData<User> cloudEventData = mapData(
            inputEvent,
            PojoCloudEventDataMapper.from(mapper, User.class)
        );
        User user = cloudEventData.getValue();
        context.getLogger().info("Name: " + user.getName());
    }

    private JsonFormat getFormat() {
        return (JsonFormat) EventFormatProvider.getInstance().resolveFormat(JsonFormat.CONTENT_TYPE);
    }

    static class User {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
