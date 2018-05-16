

//=============================================
//FILECHOOSER
//=============================================

fileChooser chooser = new FileChooser();
chooser.setInitialDirectory(new File(user + "\\videos"));
chooser.getExtensionFilters().addAll(new ExtensionFilter("Videos", "*.mp4"));
File fileChooser = chooser.showOpenDialog(null);//abre o arquvio


//=============================================
//CAMINHO ASOLUTO
//=============================================
String userHome = System.getProperty("user.home");

//validação
if (fileChooser != null) {
	txtInput.setText(fileChooser.getName());
}

//=============================================
//EVENTOS - button
//=============================================

this.btnConvert.setOnAction(new EventHandler<ActionEvent>() {
	@Override
	public void handle(ActionEvent t) {
		System.out.println("Clicou");
	}
});

//============================================= 
//TROCAR FOTO
//=============================================
public void eventHandler() {

		// add image layout
		this.btnFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				String user = System.getProperty("user.home");
				FileChooser chooser = new FileChooser();
				chooser.setInitialDirectory(new File(user + "\\Pictures"));
				chooser.setTitle("Selecione m arquivo");
				chooser.getExtensionFilters().add(new ExtensionFilter("Imagem", "*.jpg"));
				File file = chooser.showOpenDialog(null);

				if (file != null) {

					try {
						imgTemp = new Image(file.toURI().toURL().toString());
						viewTemp = new ImageView(imgTemp);
						viewTemp.setFitWidth(300);
						viewTemp.setFitHeight(300);
						labelTitulo.setText(file.getName().toLowerCase().toString());
						add(viewTemp, 0, 1);
					} catch (MalformedURLException e) {
						System.out.println(e.getMessage());
					} catch (NullPointerException e) {
						System.out.println(e.getMessage());
					}
				}
			}
		});
	}
}

//=============================================
//MAIN
//=============================================

public class VideosconverteMP3 extends Application {
    
    @Override
    public void start(Stage stage) {
        
        PrincipalLayout c = new BorderPane();

        c.setTop(new Label("Topo"));
        c.setCenter(new Label("Centro"));
        c.setBottom(new Label("Bottom"));

        Scene scene = new Scene(c, 500, 200);
        
        stage.setTitle("Converte Video em  MP3!");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}

//========================================================================
//JSON OBJECT [ESCREVE JSON: ARQUIVO CRIADO NA RAIZ DO PEOJETO]
//========================================================================
JSONObject json = new JSONObject();
		
FileWriter writer = null;
		
//Armazena os dados no JSON
json.put("nome","Ednei");
json.put("idade",35);
json.put("email","Ednei@gmail.com");
		
		//Escreve Arquivo JSON
try {
	writer = new FileWriter("saida.json");
	//escreve no coneudo JSON
	writer.write(json.toJSONString());
	writer.close();
} catch (IOException e) {
	System.out.println(e.getMessage());	
}

System.out.println(json);
//========================================================================
//JSON OBJECT [LER JSON: ARQUIVO CRIADO NA RAIZ DO PEOJETO]
//========================================================================

public class ReaderJSON {
	
	String nome;
	String idade;
	String email;
	JSONObject json;
	
	public void read() throws IOException, ParseException {
				
		//criar parse json
		JSONParser parse = new JSONParser();
		
		String arquivo ="saida.json";
		FileReader reader = new FileReader(arquivo);
		json = (JSONObject) parse.parse(reader);
		
		//Atribui os valores nas variaveis
		nome = (String)json.get("nome");
		idade = (String)json.get("idade");
		email = (String)json.get("email");

		System.out.println(nome+"\n"+idade+"\n"+email);
	}
}

//========================================================================
//JSON OBJECT [LER JSON URL]
//========================================================================
public class Main {

	public static void main(String[] args) {

		JSONParser parser = new JSONParser();
			
		try {

	        URL url = new URL("https://appeste.000webhostapp.com/appMap_php/teste.php");
	        URLConnection urlConnection = url.openConnection();

	        if(urlConnection.getURL()!=null){
	        	BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
	        	String inputLine;

	        	//le o json
	        	while((inputLine = in.readLine())!=null) {

	        		JSONArray arrayJson = (JSONArray) parser.parse(inputLine);

	        		for(int i=0;i<arrayJson.size();i++) {
	        			JSONObject jsonObject = (JSONObject) arrayJson.get(i);
	        			String nome= (String) jsonObject.get("nome");
	        			String idade= (String) jsonObject.get("idade");
	        			String email= (String) jsonObject.get("email");
	        			System.out.println("Nome: "+nome+" Idade: "+idade+" E-mail: "+email);
	        		}
	        	}
	        }else {
	        	System.out.println("Erro de conexao");
	        }
	      }catch (IOException e) {
	        e.printStackTrace();
	      }catch (ParseException e) {
			e.printStackTrace();
		}
	}
}

//========================================================================
//IMPORT DEPENDECIA
//========================================================================

apply plugin: 'com.android.application'

android {
    compileSdkVersion 27
    defaultConfig {
        applicationId "algrimsoromano.com.br.sistemafibras"
        minSdkVersion 15
        targetSdkVersion 27
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "android.support.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android.txt'), 'proguard-rules.pro'
        }
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation 'com.android.support:appcompat-v7:27.1.1'
    implementation 'com.android.support.constraint:constraint-layout:1.1.0'
    implementation 'com.google.android.gms:play-services-maps:15.0.0'
    implementation 'com.android.support:support-v4:27.1.1'
    // https://mvnrepository.com/artifact/org.json/json
    // https://mvnrepository.com/artifact/com.googlecode.json-simple/json-simple
    compile group: 'com.googlecode.json-simple', name: 'json-simple', version: '1.1'


    compile 'com.android.support:design:27.1.1'

    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}

//========================================================================
//JSON OBJECT [LER JSON URL * LISTA]
//========================================================================

public class ReaderJSON {

	private JSONParser parser;

	public List<Pessoa> read(URL url) throws IOException, ParseException {

		parser = new JSONParser();
		URLConnection urlConnection = url.openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
		String inputLine;
		
		List<Pessoa> lista = new ArrayList<>();
		Pessoa p = new Pessoa();
		
		// le o json
		while ((inputLine = in.readLine()) != null) {

			JSONArray arrayJson = (JSONArray) parser.parse(inputLine);
			// le array
			for (int i = 0; i < arrayJson.size(); i++) {
				
				JSONObject jsonObject = (JSONObject) arrayJson.get(i);
				String nome  =  (String) jsonObject.get("nome");
				String idade = (String) jsonObject.get("idade");
				String email = (String) jsonObject.get("email");
				p.setNome(nome);
				p.setEmail(email);
				p.setIdade(idade);
				lista.add(p);
			}
		}
		return lista;
	}
}
//=================== MAIN ===================

public static void main(String[] args) {

	ReaderJSON readerJson =null;
	try {
		readerJson =  new ReaderJSON();
		List<Pessoa> lista = readerJson.read(new URL("https://appeste.000webhostapp.com/appMap_php/teste.php"));
		for(int i=0;i<lista.size();i++) {
			System.out.println(lista.get(i).getNome()+" "+lista.get(i).getEmail());
		}
	} catch (IOException | ParseException e) {
		System.out.println(e.getMessage());
	}
}

//==================== PHP ==============================
<?php

	header('Access-Control-Allow-Origin: *'); 
	header("Content-Type: application/json; charset=UTF-8");
	
	for($i=0;$i<20;$i++)
	{
	    $data[$i] = array(
	        'nome'=>'Ednie',
	        'idade'=>'35',
	        'email'=>'ednei@gmail.com'
	 	);
	}	    
	echo json_encode($data);
?>

//========================================================================
//SEND POST  baixar libs org.apache.http
//========================================================================

public class SendPostJSON {

	JSONArray arrayJson;
	
	public void send() throws IOException {
		
		HttpClient client = HttpClientBuilder.create().build();
        HttpPost post = new HttpPost("https://appeste.000webhostapp.com/json_java/resposta.php");
        
        // Create some NameValuePair for HttpPost parameters
        List<NameValuePair> arguments = new ArrayList<>();
        arguments.add(new BasicNameValuePair("nome", "ednei"));

        try {
            post.setEntity(new UrlEncodedFormEntity(arguments));
            HttpResponse response = client.execute(post);
            System.out.println("Enviado: "+EntityUtils.toString(response.getEntity()));
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}




//=============================================
//IO
//==============================================
//LER ARQUIVO
public void readerFiles(String file) throws IOException{
		
	InputStream is = new FileInputStream(file);
	InputStreamReader irs = new InputStreamReader(is);
	BufferedReader br = new BufferedReader(irs);
		
	String s = br.readLine();
	while(s!=null){
		System.out.println(s);
		s = br.readLine();
	}
	br.close();
}

//ESCREVE ARQUIVO
public void writerFiles(String saida, String texto) throws IOException{
		
	OutputStream os =  new FileOutputStream(saida);
	OutputStreamWriter ows = new OutputStreamWriter(os);
	BufferedWriter bf = new BufferedWriter(ows);
	bf.write(texto);
	bf.close();
}
//USANDO NO MAIN
services.readerFiles("teste.txt");
String texto ="Lorem Ipsum é simplesmente uma simulação de texto da indústria tipográfica "
	+ "e de impressos, e vem sendo utilizado desde o século XVI, quando um impressor"
	+ " desconhecido pegou uma bandeja de tipos e os embaralhou para fazer um livro "
	+ "de modelos de tipos.";
		
services.writerFiles("saida.txt", texto);


//======================================================================================
//DOWLOAD FILES
//======================================================================================

URL url  = new URL(params[0]);
URLConnection conection = url.openConnection();
conection.connect();

int lenghtofFile = conection.getContentLength();
Log.i("lenghtofFile ", "lenghtofFile  "+lenghtofFile );

InputStream in = new BufferedInputStream(url.openStream());
OutputStream outputStream = new FileOutputStream("sdcard/audio.mp4");

byte data[] = new byte[1024];
long total =0;
while((count= in.read(data))!=-1){
	total +=count;
    publishProgress("" +(int) ((total * 100)/lenghtofFile));
   	outputStream.write(data, 0, count);
}
outputStream.flush();
outputStream.close();
in.close();
//======================================================================================
//MAIN
//======================================================================================
try {
	String url = "http://www.topimagens.com.br/imagens/imagens-mais-novas.jpg";
	new Download().download(url);			
} catch (IOException e) {
	System.out.println(e.getMessage());
	e.printStackTrace();
}
//======================================================================================
//	DOWNLOAD
//======================================================================================
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class Main {

	public static void main(String[] args) {

		downloadFile("fotos.png");

	}

	private static void downloadFile(String str) {
		int count;
		try {

			URL url = new URL("https://appeste.000webhostapp.com/ocrJava/images/"+str);
			URLConnection conection = url.openConnection();
			conection.connect();

			int lenghtofFile = conection.getContentLength();

			InputStream in = new BufferedInputStream(url.openStream());
			String useHome = System.getProperty("user.home");
			OutputStream outputStream = new FileOutputStream(useHome+"\\Pictures\\"+str);

			byte data[] = new byte[1024];
			long total = 0;
			while ((count = in.read(data)) != -1) {
				total += count;
				System.out.println("" + (int) ((total * 100) / lenghtofFile)+"%");
				outputStream.write(data, 0, count);
			}
			outputStream.flush();
			outputStream.close();
			in.close();
		} catch (NullPointerException e) {

		} catch (MalformedURLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
}



//======================================================================================
//CHAMAR JANELA E FECHAR OUTRA
//======================================================================================

public class MainController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    //EVENTO CLICK
    public void startGame(ActionEvent actionEvent) throws Exception {
        Game game = new Game();
        Stage stage = new Stage();
        game.start(new Stage());//ABRE NOVA JANELA
        Main.myStage.close();//FECHA MAIN
    }
}

//======================================================================================


public class Main extends Application {

    public static Stage myStage;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Main.myStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/Main.fxml"));
        primaryStage.setTitle("Snaker");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

//======================================================================================

public class Game extends Application {

    private static Stage myStage;

    @Override
    public void start(Stage primaryStage) throws Exception {

        Game.myStage = primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("/view/Game.fxml"));
        primaryStage.setTitle("Snaker");
        primaryStage.setScene(new Scene(root, 600, 600));
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}



//======================================================================================
//	OPENCV
//======================================================================================
//EXIBIR IMAGEM NA TELA
//======================================================================================
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deteccao;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import org.opencv.core.Mat;

/**
 *
 * @author freta
 */
public class Utilitarios {

    //recebe a mat e converte em bufferImage
    public BufferedImage convertMatToImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }

        int bufferSize = mat.channels() * mat.cols() * mat.rows();
        byte[] bytes = new byte[bufferSize];
        mat.get(0, 0, bytes);
        BufferedImage imagem = new BufferedImage(mat.cols(), mat.rows(), type);
        byte[] targetPixels = ((DataBufferByte) imagem.getRaster().getDataBuffer()).getData();
        System.arraycopy(bytes, 0, targetPixels, 0, bytes.length);
        return imagem;
    }

    public void mostraImagem(BufferedImage imagem) {
        ImageIcon icon = new ImageIcon(imagem);
        JFrame frame = new JFrame();
        frame.setLayout(new FlowLayout());
        frame.setSize(imagem.getWidth() + 50, imagem.getHeight() + 50);
        JLabel lbl = new JLabel();
        lbl.setIcon(icon);
        frame.add(lbl);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

//======================================================================================

package deteccao;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_COLOR;
import static org.opencv.imgcodecs.Imgcodecs.imread;

public class TesteOpenCv {
    
    public static void main(String args[]){
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.VERSION);
        Mat imagemColorida = imread("src\\deteccao\\opencv-java.jpg", CV_LOAD_IMAGE_COLOR);
        
        Utilitarios  ut = new Utilitarios();
        ut.mostraImagem(ut.convertMatToImage(imagemColorida));
        
    }
}


//======================================================================================
//CONVERTE IMAGEM COLORIDA PRA ESCALA DE SINZA
//OBJ: EIMPORTANTE SEMPRE REALIZAR A CONVERSAO DE IMAGEM 
//PARA ESCALADE SINZA ASSIM TENDO SUCESSO EM DETECCAO FACIAL
//======================================================================================
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deteccao;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import static org.opencv.imgcodecs.Imgcodecs.CV_LOAD_IMAGE_COLOR;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;

public class TesteOpenCv {
    
    public static void main(String args[]){
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        System.out.println(Core.VERSION);
        Mat imagemColorida = imread("src\\deteccao\\opencv-java.jpg", CV_LOAD_IMAGE_COLOR);
        
        Utilitarios  ut = new Utilitarios();
        //========================================================
        //converte imagem
        //========================================================
        Mat imagemCinza = new Mat();
        Imgproc.cvtColor(imagemColorida, imagemCinza, COLOR_BGR2GRAY);
        ut.mostraImagem(ut.convertMatToImage(imagemCinza));
    }
}


//======================================================================================
//HAAR CASCADE
//DENTRO DA PASTA SOURCE EXISTE OS XML PRINTOS
//======================================================================================
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package deteccao;

import javafx.scene.transform.Scale;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author freta
 */
public class Exemplo1 {
    
    public static void main(String args[]){
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String foto ="src\\pessoas\\beatles.jpg";
        Mat imagemColorida = Imgcodecs.imread(foto);
        Mat imagemCinza = new Mat();
        Imgproc.cvtColor(imagemColorida, imagemCinza, COLOR_BGR2GRAY);
        
        CascadeClassifier classificador = 
                new CascadeClassifier("src\\cascade\\haarcascade_frontalface_default.xml");
        
        //matriz de retangulo
        MatOfRect facesDetectadas = new MatOfRect();
        classificador.detectMultiScale(imagemCinza, facesDetectadas);
        
        //exibe as pocisoes das faces na imagem
        for(Rect rect: facesDetectadas.toArray()){
            System.out.println(rect.x+" "+rect.y+" "+rect.width+" "+rect.height);
            
            //desenhar
            Imgproc.rectangle(imagemColorida, /*ponto inicial*/new Point(rect.x, rect.y), 
                /*ponto final*/new Point(rect.x+rect.width, rect.y+rect.height),
                /*cor*/new Scalar(255, 255, 255), 1/*tamnho da borda*/);
        }
        
        Utilitarios ut = new Utilitarios();
        ut.mostraImagem(ut.convertMatToImage(imagemColorida));
        System.out.println("Total de faces detectadas: "+facesDetectadas.toArray().length);
    }
}

//======================================================================================
//EXIBE O RETANGULO NA FOTOS DAS PESSOAS
//DENTRO DA PASTA SOURCE EXISTE OS XML PRINTOS
//======================================================================================

package deteccao;

import javafx.scene.transform.Scale;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author freta
 */
public class Exemplo1 {
    
    public static void main(String args[]){
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String foto ="src\\pessoas\\pessoas1.jpg";
        Mat imagemColorida = Imgcodecs.imread(foto);
        Mat imagemCinza = new Mat();
        Imgproc.cvtColor(imagemColorida, imagemCinza, COLOR_BGR2GRAY);
        
        CascadeClassifier classificador = 
                new CascadeClassifier("src\\cascade\\haarcascade_frontalface_default.xml");
        
        //matriz de retangulo
        MatOfRect facesDetectadas = new MatOfRect();
        classificador.detectMultiScale(imagemCinza, facesDetectadas);
        
        //exibe as pocisoes das faces na imagem
        for(Rect rect: facesDetectadas.toArray()){
            System.out.println(rect.x+" "+rect.y+" "+rect.width+" "+rect.height);
            
            //desenhar
            Imgproc.rectangle(imagemColorida, /*ponto inicial*/new Point(rect.x, rect.y), 
                /*ponto final*/new Point(rect.x+rect.width, rect.y+rect.height),
                /*cor*/new Scalar(255, 255, 255), 1/*tamnho da borda*/);
        }
        
        Utilitarios ut = new Utilitarios();
        ut.mostraImagem(ut.convertMatToImage(imagemColorida));
        System.out.println("Total de faces detectadas: "+facesDetectadas.toArray().length);
    }
}


//======================================================================================
//PARAMETROS ADICIONIAS
//SCALE FACTOR --> QUANDO AS FACES ESTOA PERTO DA CAMERA, ELAS SERAO MAIORES DO QUE DO FUNDO DA IMAGEM
//a ideia e redimensonar o rosto maior para o orsto menor
//======================================================================================
//NIMNEIGHBORS--> QUANTOS VIZINHOS CADA RETANGULO(FACE) DEVE TER PARA MANTELOS
//VALOR MUITO ALTO, MENOR DETECÇAO, PREM APRESENTA MAIOR QUALIDADE
//======================================================================================
//FLAGS --> REGEITA ALGU,AS REGIOES DA IMAGEM , ONDE NAO CONTEM OBJETOS PROCURADOS
//OBS:SEMPRE ESTA COM O VALOR 0
//======================================================================================
//MIN SIZE--> ESPECIFICA O MENOR AOBJETO  A SER RECONHECIDO, 30X30 E O VALOR PADRAO
//======================================================================================

package deteccao;

import javafx.scene.transform.Scale;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author freta
 */
public class Exemplo1 {
    
    public static void main(String args[]){
        
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String foto ="src\\pessoas\\foto.jpg";
        Mat imagemColorida = Imgcodecs.imread(foto);
        Mat imagemCinza = new Mat();
        Imgproc.cvtColor(imagemColorida, imagemCinza, COLOR_BGR2GRAY);
        
        CascadeClassifier classificador = 
                new CascadeClassifier("src\\cascade\\haarcascade_frontalface_default.xml");
        
        //matriz de retangulo
        MatOfRect facesDetectadas = new MatOfRect();
        classificador.detectMultiScale(imagemCinza, facesDetectadas,
                1.19/*scale factor*/,
                3/*minNeighbos*/,
                0/*flag*/,
                /*
                    detecta apenas os valores com os menor tamanho 30 e maior 30
                */
                new Size(30,30)/*min size*/,
                new Size(30,30)/*max size*/);
        
        //exibe as pocisoes das faces na imagem
        for(Rect rect: facesDetectadas.toArray()){
            System.out.println(rect.x+" "+rect.y+" "+rect.width+" "+rect.height);
            
            //desenhar
            Imgproc.rectangle(imagemColorida, /*ponto inicial*/new Point(rect.x, rect.y), 
                /*ponto final*/new Point(rect.x+rect.width, rect.y+rect.height),
                /*cor*/new Scalar(0, 0, 255), 2/*tamnho da borda*/);
        }
        
        Utilitarios ut = new Utilitarios();
        ut.mostraImagem(ut.convertMatToImage(imagemColorida));
        System.out.println("Total de faces detectadas: "+facesDetectadas.toArray().length);
    }
}


//======================================================================================
//PARAMETROS ADICIONIAS
//SCALE FACTOR --> QUANDO AS FACES ESTOA PERTO DA CAMERA, ELAS SERAO MAIORES DO QUE 
//======================================================================================

package deteccao;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author freta
 */
public class DeteccaoDeOlhos {
    
    public static void main(String args[]){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String foto1 ="src\\pessoas\\beatles.jpg";
        Mat imagemColorida = Imgcodecs.imread(foto1);
        Mat imagemCinza = new Mat();
        Imgproc.cvtColor(imagemColorida, imagemCinza, COLOR_BGR2GRAY);
        
        CascadeClassifier classificadorOlho = 
                new CascadeClassifier("src\\cascade\\haarcascade_frontalface_default.xml");
        
        //matriz de retangulo
        MatOfRect facesDetectadas = new MatOfRect();
        classificadorOlho.detectMultiScale(imagemCinza, facesDetectadas);
        
        //exibe as pocisoes das faces na imagem
        for(Rect rect: facesDetectadas.toArray()){
            System.out.println(rect.x+" "+rect.y+" "+rect.width+" "+rect.height);
            
            //desenhar
            Imgproc.rectangle(imagemColorida, 
                /*ponto inicial*/new Point(rect.x, rect.y), 
                /*ponto final*/new Point(rect.x+rect.width, rect.y+rect.height),
                /*cor*/new Scalar(0, 255, 255), 2/*tamnho da borda*/
            );
        }
        
        
        MatOfRect olhosDetectados = new MatOfRect();
        CascadeClassifier classificadorOlhor = 
                new CascadeClassifier("src\\cascade\\haarcascade_eye.xml");
        classificadorOlhor.detectMultiScale(imagemCinza, olhosDetectados,
                1.05,
                15,
                0,
                new Size(5,5),
                new Size(100,100));
        
        
        for(Rect rect: olhosDetectados.toArray()){
            System.out.println(rect.x+" "+rect.y+" "+rect.width+" "+rect.height);
            
            //desenhar
            Imgproc.rectangle(imagemColorida, 
                /*ponto inicial*/new Point(rect.x, rect.y), 
                /*ponto final*/new Point(rect.x+rect.width, rect.y+rect.height),
                /*cor*/new Scalar(0, 0, 255), 2/*tamnho da borda*/
            );
        }
        
        Utilitarios ut = new Utilitarios();
        ut.mostraImagem(ut.convertMatToImage(imagemColorida));
    }
}

//======================================================================================
//DETECCAO DE OLHOS
//======================================================================================

package deteccao;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import org.opencv.objdetect.CascadeClassifier;

/**
 *
 * @author freta
 */
public class DeteccaoDeOlhos {
    
    public static void main(String args[]){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String foto1 ="src\\pessoas\\beatles.jpg";
        Mat imagemColorida = Imgcodecs.imread(foto1);
        Mat imagemCinza = new Mat();
        Imgproc.cvtColor(imagemColorida, imagemCinza, COLOR_BGR2GRAY);
        
        CascadeClassifier classificadorOlho = 
                new CascadeClassifier("src\\cascade\\haarcascade_frontalface_default.xml");
        
        //matriz de retangulo
        MatOfRect facesDetectadas = new MatOfRect();
        classificadorOlho.detectMultiScale(imagemCinza, facesDetectadas);
        
        //exibe as pocisoes das faces na imagem
        for(Rect rect: facesDetectadas.toArray()){
            System.out.println(rect.x+" "+rect.y+" "+rect.width+" "+rect.height);
            
            //desenhar
            Imgproc.rectangle(imagemColorida, 
                /*ponto inicial*/new Point(rect.x, rect.y), 
                /*ponto final*/new Point(rect.x+rect.width, rect.y+rect.height),
                /*cor*/new Scalar(0, 255, 255), 2/*tamnho da borda*/
            );
        }
        
        
        MatOfRect olhosDetectados = new MatOfRect();
        CascadeClassifier classificadorOlhor = 
                new CascadeClassifier("src\\cascade\\haarcascade_eye.xml");
        classificadorOlhor.detectMultiScale(imagemCinza, olhosDetectados,
                1.05,
                15,
                0,
                new Size(5,5),
                new Size(100,100));
        
        
        for(Rect rect: olhosDetectados.toArray()){
            System.out.println(rect.x+" "+rect.y+" "+rect.width+" "+rect.height);
            
            //desenhar
            Imgproc.rectangle(imagemColorida, 
                /*ponto inicial*/new Point(rect.x, rect.y), 
                /*ponto final*/new Point(rect.x+rect.width, rect.y+rect.height),
                /*cor*/new Scalar(0, 0, 255), 2/*tamnho da borda*/
            );
        }
        
        Utilitarios ut = new Utilitarios();
        ut.mostraImagem(ut.convertMatToImage(imagemColorida));
    }
}




//======================================================================================
//WEB CAM
//======================================================================================
package deteccao;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

/**
 *
 * @author freta
 */
public class Webcam extends javax.swing.JFrame {

    /**
     * Creates new form webCam
     */
    public Webcam() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 387, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 418, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>                        

    /**
     * @param args the command line arguments
    */

    public static void main(String args[]) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Webcam janela = new Webcam();
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(600, 500);
        janela.setVisible(true);

        //EXECUTA O METODO
        janela.mostraVideo();

    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel jPanel1;
    // End of variables declaration                   

    private void mostraVideo() {
        
        //cria  a matriz 
        Mat matrizVideo = new Mat();
        VideoCapture captura = new VideoCapture(0/*valor do primeros dispositivo*/);
        
        if (captura.isOpened()) {
            
            while (true) {
                //caprura da imagem
                captura.read(matrizVideo);

                if (!matrizVideo.empty()) {

                	//seta o tamno da matriz
                    setSize(matrizVideo.width() + 50, matrizVideo.height() + 70);

                    Mat imagemColorida 	= matrizVideo;
                    Mat imagemCinza 	= new Mat();

                    //proceso de conversao matriz colirida oara cinza
                    Imgproc.cvtColor(imagemColorida/*recebe a mtrazi colorida*/, imagemCinza /*recebe a mariz cinza*/, COLOR_BGR2GRAY/* converte para cinza*/);

                    CascadeClassifier classificadorOlho = new CascadeClassifier("src\\cascade\\haarcascade_frontalface_default.xml");;
                    
                    MatOfRect facesDetectadas = new MatOfRect();

                    classificadorOlho.detectMultiScale(imagemCinza, facesDetectadas,
						1.1,
                        1,
                        0,
                        new Size(100,100),
                        new Size(500, 500)
                    );

                    for (Rect rect : facesDetectadas.toArray()) {
                        
                    	//desenha o retangula com as faces
                        Imgproc.rectangle(imagemColorida,
                            new Point(rect.x, rect.y),
                            new Point(rect.x + rect.width, rect.y + rect.height),
                            new Scalar(0, 255, 255),
                            2
                        );
                    }

                    //add a matriz  dentro
                    BufferedImage imagem = new Utilitarios().convertMatToImage(matrizVideo);
                    Graphics g = jPanel1.getGraphics();
                    g.drawImage(imagem, /*borda*/ 0, 0, imagem.getWidth(), imagem.getHeight(), null);
                }
            }
        }

    }
}


//======================================================================================
//SWING
//======================================================================================
//ADD JINTERNAL FRAME--> NOVO-->OUTRO-->FORMGUI-->JFORMINTERNALFRAME[NOME: TelaCadastroUsuario]
//======================================================================================
//duplu click no menu item
//======================================================================================
private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {                                           
    // TODO add your handling code here:    
	//exiboir tela de cadastro
    TelaCadastroUsuario telaCadUser = new TelaCadastroUsuario();
        jDesktopPane.add(telaCadUser);
        telaCadUser.setVisible(true);
}           
//======================================================================================
//MASCARA 
//CAMPO FORAMTADO
//PRORPIEDADES FORMATFACTORY
//======================================================================================


                               
