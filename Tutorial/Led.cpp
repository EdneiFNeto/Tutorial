#include <SPI.h>
#include <Ethernet.h>

byte mac[] = {0xDe, 0xAD, 0xBE, 0xEF, 0xFE, 0xED};
IPAddress ip(192,168,0,16);
EthernetServer server(80);

int pinLed1 = 3;
int led     = 0;


void setup(){
    Serial.begin(9600);
    while (!Serial){
      ;
    }
    if(digitalRead(pinLed1) == HIGH)led = 1;
    else if(digitalRead(pinLed1) == LOW) led = 0;

    pinMode(pinLed1, OUTPUT);
    digitalWrite(pinLed1, LOW);

    Ethernet.begin(mac,ip);
    server.begin();
    Serial.print("Server is at ");
    Serial.println(Ethernet.localIP());
}

void loop(){
    
    EthernetClient client = server.available();
    
    if(client){
  
        Serial.println("New client");
        boolean currentLineIsBlank = true;
        String DatEth;
        String msg = "LED DESLIGADO";

        while (client.connected()){

            if(client.available()){

              char c = client.read();
              Serial.write(c);
              DatEth.concat(c);
              
              if(DatEth.endsWith("/ledon")) {
                led = 1; 
                msg = "LED LIGADO";
                Serial.println(msg);
              }

              if(DatEth.endsWith("/ledof")) {
                led = 0; 
                msg = "LED DESLIGADO";
                Serial.println(msg);
              }

              if(c == '\n' && currentLineIsBlank){
                
                //============================================================
                //CODIGO HTML
                //============================================================
                
                client.println("<!DOCTYPE html>");
              client.println("<html lang='pt-br'>");
              client.println("<head>");
              client.println("<title>Bootstrap Example</title>");
              client.println("<meta charset='utf-8'>");
              client.println("<meta name='viewport' content='width=device-width, initial-scale=1'>");
              client.println("<link rel='stylesheet' href='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css'>");
              client.println("<script src='https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js'></script>");
              client.println("<script src='https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js'></script>");
              client.println("</head>");
                  
              client.println("<html>");
                client.println("<body>");     
                    client.println("<div class='container'>");        
                        formularioHtml(client, msg, led);
                    client.println("</div>");
                client.println("</body>");
              client.println("</html>");
                
                //============================================================
                //ASCENDE LED & LIGA LED
                //============================================================

                if(led == 1){
                  digitalWrite(pinLed1, HIGH);
                }

                if(led == 0){
                  digitalWrite(pinLed1, LOW);
                }

                break;
              }

              if(c == '\n') {
                  currentLineIsBlank = true;
              }

              else if (c != '\r') {
                  currentLineIsBlank = false;
              }
           }
        }

        delay(1);
        client.stop();
        Serial.println("Client disconnected");
    }
}

void formularioHtml(EthernetClient client, String msg, int led){

    client.println("<div class='col-sm-12'>");
      client.println("<h2 class='text-center'>LIGAR LÂMPADA</h2>");
      client.println("<hr>");
    client.println("</div>");

    //formulario
    client.println("<div class='col-sm-12'>");

      	client.println("<form>");
          	client.println("<div class='form-group'>");
            	client.println("<h3 class='text-center' style='padding-bottom:5%'>"+msg+"</h3>");
            
	            if(led == 1){
	              	client.println("<img style='position:relative; left:50%; margin-left:-100px;' 
	              		src=\"https://appeste.000webhostapp.com/image/lampada_acesa.png\" width=\"200\">"); 
	            }

              	if(led == 0){
                	client.println("<img style='position:relative; left:50%; margin-left:-100px;' 
                		src=\"https://appeste.000webhostapp.com/image/lampada_apagada.png\" width=\"200\">");
              	}              
              	 
            	client.println("<button type=\"submit\" formaction=\"ledoff\" class=\"btn btn-danger btn-block\">Desligar</button>");
          	client.println("</div>");
      	client.println("</form>");
    client.println("</div>");
}                        

20181estruturadedados2@gmail.com
boente_2018
boente2018@1
boente2018_1