package fi.tuni.endOfWaste;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpMethods;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

/**
 * To get this work on an android device add following line to
 * AndroidManifest.xml:
 * <uses-permission android:name="android.permission.INTERNET" />
 * above tag <application
 * @author Viljami Pietarila
 */
public class HighScoreServer {
    /**
     * url is the address of the highscore server.
     */
    private static String url;

    /**
     * If verbose is true, this class will print out messages to the Gdx log.
     */
    private static boolean verbose = false;

    /**
     * Password for the highscore host.
     */
    private static String password;

    /**
     * Username for the highscore host.
     */
    private static String user;

    /**
     * fetchHighScores gets high score entries from the server.
     *
     * This gets high score entries from a server, converts the json file to
     * List of HighScoreEntries and sends it back to the source.
     * @param source source class implementing HighScoreListener
     */
    public static void fetchHighScores(final HighScoreListener source) {
        Net.HttpRequest request = new Net.HttpRequest(HttpMethods.GET);
        request.setUrl(url);
        Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
            @SuppressWarnings("unchecked")
            @Override
            public void handleHttpResponse (Net.HttpResponse httpResponse) {
                String r = httpResponse.getResultAsString();

                JsonValue jsonObject = (new JsonReader().parse(r));

                ArrayList<HighScoreEntry> highScores = new ArrayList<>();

                for (int i = 0; i <= 9; i++) {
                    HighScoreEntry score = new HighScoreEntry(
                            jsonObject.get(i).get(0).asString(),
                            jsonObject.get(i).get(1).asInt());
                    highScores.add(score);
                }
                source.receiveHighScore(highScores);
            }

            @Override
            public void failed (Throwable t) {
                source.failedToRetrieveHighScores(t);
            }

            @Override
            public void cancelled () {
            }

        });
    }

    /**
     * sendHighScore entry sends new high score data to the server
     *
     * It will then send a confirmation of success back to the source.
     * @param highScore The new HighScoreEntry data to be sent to the server.
     * @param source source class implementing HighScoreListener
     */
    public static void sendNewHighScore(HighScoreEntry highScore,
                                        final HighScoreListener source) {
        Json json = new Json();
        json.setOutputType(JsonWriter.OutputType.json);

        String content = "name=" + highScore.getName() +
                "&score=" + highScore.getScore() +
                "&user=" + user +
                "&password=" + password;

        Net.HttpRequest request = new Net.HttpRequest(HttpMethods.POST);
        request.setUrl(url);
        request.setContent(content);

        if (user == null) {
        } else if (password == null) {
        } else if (url == null) {
        } else {
            Gdx.net.sendHttpRequest(request, new Net.HttpResponseListener() {
                @Override
                public void handleHttpResponse(Net.HttpResponse httpResponse) {
                    source.receiveSendReply(httpResponse);
                }

                @Override
                public void failed(Throwable t) {
                    source.failedToSendHighScore(t);
                }

                @Override
                public void cancelled() {
                }

            });
        }
    }

    public static void readConfig(String propFileName) {
        Properties prop = new Properties();
        FileHandle file = Gdx.files.internal(propFileName);
        InputStream inputStream = file.read();

        try {
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("Config file '" + propFileName + "' not found.");
            }
        } catch (IOException e) {
        }

        user = prop.getProperty("user");
        password = prop.getProperty("password");
        url = prop.getProperty("url");
    }
}
