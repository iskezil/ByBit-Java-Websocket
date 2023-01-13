package com.justice.services;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import javax.management.openmbean.InvalidKeyException;
import java.io.IOException;
import java.net.URI;
import java.security.NoSuchAlgorithmException;

public class WebSocketListeners extends WebSocketClient {
    private static final GetProperties properties = new GetProperties();
    private static final Long timestamp = System.currentTimeMillis();
    public WebSocketListeners(URI serverUri) {
        super(serverUri);
    }

    //Отработает когда соединение, будет открыто
    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("OPENED_CONNECTION: " + handshakedata);

        //Согласно документации https://bybit-exchange.github.io/docs/spot/v3/#t-websocketauthentication
        //После открытия соединения необходимо ответным сообщение авторизоваться
        //Для этого собрать следующий JSON
        //{
        //    "req_id": "10001", // optional
        //    "op": "auth",
        //    "args": [
        //    "api_key",
        //    1662350400000, //expires greater than currentTimeStamp
        //    "singature"
        //    ]
        //}
        try {
            String message = new JSONObject()
                .put("req_id", "10001")
                .put("op", "auth")
                .put("args", new JSONArray()
                    .put(properties.getProperty("API_KEY"))
                    .put(timestamp)
                    .put(SignatureGenerate.getSignature(timestamp.toString(), properties.getProperty("API_SECRET"))))
                .toString();

            System.out.println("AUTH_JSON_OBJECT: " + message);

            //отправляем сообщение на сервер
            send(message);
        } catch (JSONException | NoSuchAlgorithmException | InvalidKeyException | java.security.InvalidKeyException |
                 IOException e) {
            throw new RuntimeException(e);
        }

    }

    //Отработает когда, будет получено новое сообщение с сервера
    @Override
    public void onMessage(String message) {
        System.out.println("RECEIVED_MESSAGE: " + message);
    }

    //Отработает когда, соединение будет закрыто
    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println(
                "Connection closed by " + (remote ? "remote peer" : "us") + " Code: " + code + " Reason: "
                        + reason);
    }

    //Отработает когда, соединение будет закрыто по ошибке (например потеря связи)
    @Override
    public void onError(Exception ex) {
        System.out.println("Error connections: " + ex);
    }
}
