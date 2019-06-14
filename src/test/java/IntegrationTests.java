import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.local.embedded.DynamoDBEmbedded;
import com.amazonaws.services.dynamodbv2.local.main.ServerRunner;
import com.amazonaws.services.dynamodbv2.local.server.DynamoDBProxyServer;
import com.amazonaws.services.dynamodbv2.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.serverless.dal.Movie;
import org.junit.After;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

@RunWith(PowerMockRunner.class)
public class IntegrationTests {

    @Mock
    Movie movie;

    @Mock
    DynamoDBMapper dynamoDBMapper;

    private AmazonDynamoDB dynamodb;

    private static DynamoDBProxyServer server;

    @Before
    public void setupClass() throws Exception {
        System.setProperty("sqlite4java.library.path", "native-libs");
        String port = "8000";
        server = ServerRunner.createServerFromCommandLineArgs(new String[]{"-inMemory", "-port", port});
        server.start();

        this.dynamodb = DynamoDBEmbedded.create().amazonDynamoDB();

        CreateTableRequest createTableRequest = new CreateTableRequest(
                Arrays.asList(new AttributeDefinition("id", "S"),
                        new AttributeDefinition("name", "S")),
                "java-movies-rating-dev",
                Arrays.asList(new KeySchemaElement("id", KeyType.HASH),
                        new KeySchemaElement("name", KeyType.RANGE)),
                new ProvisionedThroughput(10L, 10L));


        dynamodb.createTable(createTableRequest);
    }

    @After
    public void teardownClass() throws Exception {
        server.stop();
    }

    @Test
    public void handleRequestTest() throws Exception {
        System.setProperty("sqlite4java.library.path", "native-libs");

        Map<String, String> props = prepareProperties();
        Map<String, Object> input = prepareInput(props);
        String responseMovieBody = new ObjectMapper().writeValueAsString(movie);

        Movie movie = prepareMovie(dynamodb);

        PowerMockito.whenNew(Movie.class).withNoArguments().thenReturn(movie);

       /* ApiGatewayResponse apiGatewayResponse = new AddMovieHandler()
                .handleRequest(input, null);

        assertNotNull(apiGatewayResponse);
        assertEquals(apiGatewayResponse.getHeaders().get("Access-Control-Allow-Origin"), "*");
        assertEquals(apiGatewayResponse.getStatusCode(), 200);
        assertEquals(apiGatewayResponse.getBody(), responseMovieBody);*/
    }

    private Map<String, Object> prepareInput(Map<String, String> props) {
        Map<String, Object> input = new HashMap<>();
        input.put("body", props);
        return input;
    }

    private Map<String, String> prepareProperties() {
        Map<String, String> props = new HashMap<>();
        props.put("name", "Ogniem i mieczem");
        props.put("rate", "3");
        props.put("author", "Andrzej Wajda");
        return props;
    }

    private Movie prepareMovie(AmazonDynamoDB dynamodb) {
        Movie movie = new Movie(dynamodb);
        movie.setAuthor("Andrzej Wajda");
        movie.setName("Ogniem i mieczem");
        movie.setRate(3f);
        return movie;
    }

    @Test
    public void test() {
        assertTrue(true);
    }
}
