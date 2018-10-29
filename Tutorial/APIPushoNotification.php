<?php 
// Server file
//tutorial 
//https://gist.github.com/joashp/b2f6c7e24127f2798eb2
class PushNotifications
{

	// (Android)API access key from Google API's Console.
	private static $API_ACCESS_KEY = 'AIzaSyDG3fYAj1uW7VB-wejaMJyJXiO5JagAsYI';
	// (iOS) Private key's passphrase.
	private static $passphrase = '1234567';
	// (Windows Phone 8) The name of our push channel.
	private static $channelName = "joashp";
	private static $certificatePEM = 'VoipP12.pem';
	
	// Change the above three vriables as per your app.

	public function __construct()
	{
		exit('Init function is not allowed');
	}
	
        // Sends Push notification for Android users
	public function android($data, $reg_id)
	{
		$url = 'https://android.googleapis.com/gcm/send';
		$message = array(
			'title' => $data['mtitle'],
			'message' => $data['mdesc'],
			'subtitle' => '',
			'tickerText' => '',
			'msgcnt' => 1,
			'vibrate' => 1
		);

		$headers = array(
			'Authorization: key=' . self::$API_ACCESS_KEY,
			'Content-Type: application/json'
		);

		$fields = array(
			'registration_ids' => array($reg_id),
			'data' => $message,
		);

		return $this->useCurl($url, $headers, json_encode($fields));
	}
	
	// Sends Push's toast notification for Windows Phone 8 users
	public function WP($data, $uri)
	{
		$delay = 2;
		$msg = "<?xml version=\"1.0\" encoding=\"utf-8\"?>" .
			"<wp:Notification xmlns:wp=\"WPNotification\">" .
			"<wp:Toast>" .
			"<wp:Text1>" . htmlspecialchars($data['mtitle']) . "</wp:Text1>" .
			"<wp:Text2>" . htmlspecialchars($data['mdesc']) . "</wp:Text2>" .
			"</wp:Toast>" .
			"</wp:Notification>";

		$sendedheaders = array(
			'Content-Type: text/xml',
			'Accept: application/*',
			'X-WindowsPhone-Target: toast',
			"X-NotificationClass: $delay"
		);

		$response = $this->useCurl($uri, $sendedheaders, $msg);

		$result = array();
		foreach (explode("\n", $response) as $line) {
			$tab = explode(":", $line, 2);
			if (count($tab) == 2)
				$result[$tab[0]] = trim($tab[1]);
		}

		return $result;
	}
	
        // Sends Push notification for iOS users
	public function iOS($token, $message)
	{
		$privateKeyPassword = '1234567';
		$pushCertAndKeyPemFile = 'VoipP12.pem';

		$token = str_replace('<', '', $token);
		$token = str_replace('>', '', $token);
		$token = pack('H*', str_replace(' ', '', $token));

		$stream = stream_context_create();
		stream_context_set_option($stream, 'ssl', 'local_cert', $pushCertAndKeyPemFile);
		stream_context_set_option($stream, 'ssl', 'passphrase', $privateKeyPassword);

		$connectionTimeout = 10;
		$apnsServer = 'ssl://gateway.sandbox.push.apple.com:2195';
		$connectionType = STREAM_CLIENT_CONNECT | STREAM_CLIENT_PERSISTENT;

		$connection = stream_socket_client(
			$apnsServer,
			$errorNumber,
			$errorString,
			$connectionTimeout,
			$connectionType,
			$stream
		);

		if (!$connection) {
			echo "Failed to connect to the APNS server. Error no = $errorNumber<br/>";
			exit;
		} else {
			echo "Successfully connected to the APNS. Processing...</br>";

			$messageBody['aps'] = array(
				'alert' => $message,
				'sound' => 'default',
				'badge' => 1
			);

			$payload = json_encode($messageBody);
			$notification = chr(0) . pack('n', 32) . $token . pack('n', strlen($payload)) . $payload;
			$wroteSuccessfully = fwrite($connection, $notification, strlen($notification));

			if (!$wroteSuccessfully) {
				echo "Could not send the message <br/>";
			} else {
				echo "Successfully sent the message <br/>";
			}
		}

		fclose($connection);
	}
	
	// Curl 
	private function useCurl(&$model, $url, $headers, $fields = null)
	{
	        // Open connection
		$ch = curl_init();
		if ($url) {
	            // Set the url, number of POST vars, POST data
			curl_setopt($ch, CURLOPT_URL, $url);
			curl_setopt($ch, CURLOPT_POST, true);
			curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
			curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
	     
	            // Disabling SSL Certificate support temporarly
			curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
			if ($fields) {
				curl_setopt($ch, CURLOPT_POSTFIELDS, $fields);
			}
	     
	            // Execute post
			$result = curl_exec($ch);
			if ($result === false) {
				die('Curl failed: ' . curl_error($ch));
			}
	     
	            // Close connection
			curl_close($ch);

			return $result;
		}
	}
}
?>

<?php

require_once('PushNotifications.php');
// Message payload

$token = '<85afc3d0 be937cf2 f3637824 01886f4b ccbeef74 a524873c 315e3b82 aeebd47e>';
PushNotifications::iOS($token, "Teste de Menssagem");

?>
