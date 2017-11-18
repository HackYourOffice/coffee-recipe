#include <Arduino.h>
#include <ESP8266WiFi.h>
#include <ESP8266WiFiMulti.h>
#include <ESP8266HTTPClient.h>
#include <SPI.h>
#include <MFRC522.h>

#define SS_PIN D8 //Pin on WeMos D1 Mini
#define RST_PIN D3 //Pin on WeMos D1 Mini

MFRC522 rfid(SS_PIN, RST_PIN); // Instance of the class
MFRC522::MIFARE_Key key;

#define MACHINE "a020a600f704"
#define SERVICE_URL "http://10.0.20.212:8080/make"

ESP8266WiFiMulti WiFiMulti;

void data2hex(byte *input, int input_size, char *buffer, int buffer_size);

#define URL_BUFFER_SIZE 200
#define TOKEN_BUFFER_SIZE 100
char UrlBuffer[URL_BUFFER_SIZE] = {0};
char TokenBuffer[TOKEN_BUFFER_SIZE] = {0};

void setup() {
    // // No wotchdog timer
    // ESP.wdtDisable();
  
    Serial.begin(9600);

    Serial.print("Setting up WIFI ...");
    WiFi.mode(WIFI_STA);
    WiFiMulti.addAP("synyx-guest", "entfernenfreundinmache");
    Serial.println(" done");

    Serial.print("Setting up RFID ...");
    
    SPI.begin();
    rfid.PCD_Init();

    delay(500);
    
    Serial.println(" done");
}

void loop() {
    delay(1000);
    
    // // Check that wifi is setup
    if (WiFiMulti.run() != WL_CONNECTED) return;

    // Check that a card is near the sensor
    if (!rfid.PICC_IsNewCardPresent()) return;
    if (!rfid.PICC_ReadCardSerial()) return;

    // Clear buffers
    memset(UrlBuffer, 0, URL_BUFFER_SIZE);
    memset(TokenBuffer, 0, TOKEN_BUFFER_SIZE);

    // Get token
    data2hex(rfid.uid.uidByte, rfid.uid.size, TokenBuffer, TOKEN_BUFFER_SIZE -1);

    // Refresh Rffid
    rfid.PICC_HaltA();
    rfid.PCD_StopCrypto1();

    // Generate url
    sprintf(UrlBuffer, SERVICE_URL "?machine=" MACHINE "&token=%s", TokenBuffer);
    
    Serial.print(UrlBuffer);
    Serial.print(" ...");

    // Notify coffee service
    HTTPClient http;
    http.begin(UrlBuffer); //HTTP
    int httpCode = http.GET();
    Serial.printf(" %d", httpCode);
    http.end();
        
    Serial.println("");
}

void data2hex(byte *input, int input_size, char *buffer, int buffer_size) {
    int buffer_i, i;
    for (
        i = 0, buffer_i = 0;
        (i < input_size) && (buffer_i < (buffer_size-1));
        i++, buffer_i += 2
    ) {
        sprintf(&buffer[buffer_i], "%02x", input[i] & 0xff);
    }
}
