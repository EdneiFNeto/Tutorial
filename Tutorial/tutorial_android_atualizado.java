
//==============================================================================
// NUMEROS ALEATRIOS SEM REPETIÇÃO
//==============================================================================   

var take = (0..40).shuffled().take(30) as ArrayList
        take.sort()
    print("$take")

//==============================================================================
// DESENHAR LINHA
//==============================================================================   

fun desenhaLinha(nome: String, valor: String, caracter: String): String? {

    var linha = StringBuilder()
    val diferenca = nome.length + valor.length

    if (tamPapel < diferenca)
        throw Exception("Valor incorreto")

    var total = tamPapel - diferenca

    for (i in 0 until total)
        linha.append("$caracter")

    return "$nome${linha}$valor"
}

//==============================================================================
// MASK MONEY
//==============================================================================   

edt_text_altura.addTextChangedListener(object: TextWatcher{
            var current:String? = null
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //Nesse bloco ele monta a maskara para money
                edt_text_altura?.removeTextChangedListener(this)
                var replace = s.toString().replace("\\D".toRegex(), "")
                var toDouble = replace.toDouble()
                var formatted = NumberFormat.getCurrencyInstance().format((toDouble/ 100))
                current = formatted
                edt_text_altura?.setText(formatted)
                edt_text_altura?.setSelection(formatted.length)
                edt_text_altura?.addTextChangedListener(this)

            }
        })
//==============================================================================
// LOCAL BROADCAST
//==============================================================================    
var mReceivar = Receiver()
var broadcastManager = LocalBroadcastManager.getInstance(this)
broadcastManager.registerReceiver(mReceivar, IntentFilter(resources.getString(R.string.action_seve_receita)))
	
inner class Receiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {

            Log.e(TAG, "Data ${intent?.data}")
            Log.e(TAG, "Extras ${intent?.extras}")

            when(intent?.action){
                context?.resources?.getString(R.string.action_seve_receita)->{
                    if(intent?.hasExtra(context?.resources?.getString(R.string.send_success)) == true){
                        startActivity(Intent(this@SplashActivity, MainActivity::class.java))
                    }
                    if(intent?.hasExtra(context?.resources?.getString(R.string.send_failed)) == true){
                        Log.e(TAG, "Receiver failed ")
                    }
                }
            }
        }
}

override fun onPause() {
super.onPause()
  if(mReceivar!=null){
   broadcastManager.unregisterReceiver(mReceivar)
  }
}

//==============================================================================
// USAR 
//============================================================================== 
fun success(context: Context, action:String){
	val intent = Intent(action) //context.resources.getString(R.string.action_seve_receita)
            intent.putExtra(context.resources.getString(R.string.send_success), true)
            send(context,intent)
        }

        fun failed(context: Context, action: String) {
            val intent = Intent(action)
            intent.putExtra(context.resources.getString(R.string.send_failed), true)
            send(context, intent)
        }

        private fun send(context: Context, intent: Intent) {
            LocalBroadcastManager.getInstance(context).sendBroadcast(intent)
        }
//==============================================================================
// Shared QRcode
//==============================================================================    

@Override
    protected void onResume() {
        super.onResume();

        ImageView imageView = findViewById(R.id.image_qr);

        try {

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode("1234", BarcodeFormat.QR_CODE, 240, 240);
            BarcodeEncoder encoder = new BarcodeEncoder();
            bitmap = encoder.createBitmap(bitMatrix);
            imageView.setImageBitmap(bitmap);
            
        } catch (WriterException e) {
            e.printStackTrace();
        }


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                SaveAndShare.save(QRCodeActivity.this, bitmap, "12323s", "RSC-1244", "Cod.Ticket:12443");

                createFile(bitmap);
            }
        });
    }

    private void createFile(Bitmap bitmap) {

        try {

            File root = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            File cachePath = new File(root + "/" + System.currentTimeMillis() + "_image.jpg");

            if (cachePath.createNewFile()) {

                FileOutputStream ostream = new FileOutputStream(cachePath);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, ostream);
                ostream.close();
                SharedUtil.Companion.shared(QRCodeActivity.this, cachePath);

            } else {
                Log.e(TAG, "Error create file");
            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//==============================================================================
// Send message Whatsap
//==============================================================================
    override fun onResume() {
        super.onResume()
        button_whatsap.setOnClickListener{
            val number = "+5502199612-8608"
            openWhatsApp(number, "Olá tudo bem, preciso de ajuda.")
        }
    }

    private fun openWhatsApp(numero: String, mensaje: String ){

        try{
            var packageManager = packageManager
            var i = Intent(Intent.ACTION_VIEW);
            var url = "https://api.whatsapp.com/send?phone="+ numero +"&text=" + URLEncoder.encode(mensaje, "UTF-8");
            i.setPackage("com.whatsapp")
            i.data = Uri.parse(url)
            if (i.resolveActivity(packageManager) != null) {
                startActivity(i)
            }
        } catch(e: Exception ) {
            Log.e("ERROR WHATSAPP",e.toString())
        }

    }

//==============================================================================
// callback volley
//==============================================================================
package br.com.urbanopark.model

import android.content.Context
import android.util.Log
import br.com.urbanopark.model.interfaces.VolleyCallback
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONException

/*********************************************************
 *@Add params:
 *@context: Application context
 *@params: MutableMap<String, String>
 *@Method.POST or GET
 *@url: String
 *Example:
 *val params: MutableMap<String, String> = HashMap()
 *params["key"] = "Value"
 *************************************************************/

class VolleyUtil(val context: Context){

    val TAG = "VolleyLog"

    fun post(params: MutableMap<String, String>, url:String, callback: VolleyCallback){

        val stringRequest: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                try {
                    Log.e(TAG, "response $response")
                    var jsonArray = JSONArray(response)
                    callback.onSuccess(jsonArray)

                } catch (e: JSONException) {
                    e.printStackTrace()
                    callback.onError(e)
                }
            },
            Response.ErrorListener { error ->
                error.printStackTrace()
            }) {
            override fun getParams(): Map<String, String> {
                return params
            }
        }

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(stringRequest)
    }
}

//INTERFACE
interface VolleyCallback {
    fun onSuccess(jsonArray: JSONArray)
    fun onError(e: JSONException)
}

//==============================================================================
//USANDO
//==============================================================================
val params: MutableMap<String, String> = HashMap()
params["estacionamento_id"] = "${listUserGestao[0].estacionamento_id}"

var volleyUtil = VolleyUtil(context)
volleyUtil.post(params, URLS.GET_TIPO_VEICULOS, object : VolleyCallback {
	override fun onError(e: JSONException) {
		Log.e(TAG, "Error ${e.printStackTrace()}")
	}

        override fun onSuccess(jsonArray: JSONArray) {
        	Log.e(TAG, "JSONArray callback $jsonArray")
                saveTipoDeVeiculo(jsonArray)
	}
})



//==============================================================================
// runOnUiThread
//==============================================================================
private void showErrorPrinter(Context context) {
        this.runOnUiThread(new Runnable() {
            public void run() {

                ToastUtils.Companion.message(BaseActivityPrinter.this,
                        ResourcesUtil.Companion.getString(context, R.string.faild_printer));

                IntentUtils.Companion.startActivityUsingExtras(context, PageViewInitActivity.class, null, null, null,
                        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            }
        });
    }
    
//==============================================================================
// GET IMEI
//==============================================================================
public static String getIMEI(Context context) {

        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
        }

        return tm.getDeviceId();
    }

//==============================================================================
// CONVERTE BITMAP IN BYTE ARRAY
//==============================================================================
var bitmap = BitmapFactory.decodeResource(resources, R.mipmap.ic_android)
            var byteArrayOutputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
            var byte = byteArrayOutputStream.toByteArray()

//==============================================================================
// UPDATE onCreateOptionsMenu
//CREATE BADIGE IN MENU
//==============================================================================

//METODO INCREMENT
button_shared.setOnClickListener {
	count++
	//update menu
	invalidateOptionsMenu()
}
override fun onCreateOptionsMenu(menu: Menu): Boolean {

        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_list, menu)
        var menuItem = menu.findItem(R.id.item_menu_refresh)
        menuItem.setActionView(R.layout.layout_badige)
        badige = menuItem.actionView.findViewById(R.id.text_badige)
        if(count>0)
            badige?.visibility = View.VISIBLE

        badige?.text = "$count"
        Log.e("MenuLog", "Count $count")

        return true
}
//MENU
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">
    <item android:id="@+id/item_menu_refresh"
        app:showAsAction="ifRoom"
        android:icon="@drawable/ic_action_refresh"
        android:actionLayout="@layout/layout_badige"
        android:title="@string/title_menu" >
    </item>
</menu>

//LAYOUT
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:orientation="vertical" android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="8dp"
        android:layout_marginBottom="8dp"
        android:src="@drawable/ic_action_shopping_cart"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />

    <TextView

        android:visibility="gone"
        android:id="@+id/text_badige"
        android:layout_width="20dp"
        android:layout_height="22dp"
        android:background="@drawable/border_radius"
        android:gravity="center"
        android:padding="2dp"
        android:text="1"
        android:textAlignment="center"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintHorizontal_bias="0.03"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.0" />

</androidx.constraintlayout.widget.ConstraintLayout>

//==============================================================================
// FIM
//==============================================================================
package com.example.arca.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.arca.asyncTasks.base.BaseSelect;
import com.example.arca.model.CarrinhoRifa;
import com.example.arca.model.ConfigLocalidade;
import com.example.arca.model.Modalidade;
import com.example.arca.retrofit.model.Pule;
import com.example.arca.ui.CarrinhoRifaActivity;
import com.example.arca.util.LoggerUtisl;
import com.example.arca.util.PreferencesUtils;
import com.example.arca.util.ResourcesUtil;
import com.example.arca.util.StringUtils;
import com.example.arca.util.ToastUtils;
import com.mazenrashed.printooth.Printooth;
import com.example.arca.R;
import com.mazenrashed.printooth.data.PrintingImagesHelper;
import com.mazenrashed.printooth.data.converter.ArabicConverter;
import com.mazenrashed.printooth.data.printable.ImagePrintable;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;

import org.jetbrains.annotations.NotNull;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class TestePrinterActivity extends AppCompatActivity {

    private int PERMISSION_BLUETOOTH = 101;
    private Printing printing = null;
    private PrintingCallback printingCallback = null;
    private Bitmap bitmapImage;
    private String TAG = "PrinterTermicaActivity";
    private String address = null;
    private String nameDevice = null;
    private boolean isPrinter = false;
    private long codigoOperador = 0L;
    private String numberSerial = null;
    private String localidadeDescr = null;
    private String dataPule = null;
    private ArrayList<CarrinhoRifa> carrinhosRifas = new ArrayList<>();
    private ArrayList<Pule> pules = new ArrayList<>();
    private String sumTotal = null;
    private String cliente = null;
    private String tel = null;
    private String cpf = null;
    private int SIZE_PAPEL = 60;
    private String puleId = null;
    private String operador_codigo = null;
    private SharedPreferences sharedPreferences = null;
    private ArrayList<ConfigLocalidade> configLocalidades = new ArrayList<>();
    private Bitmap logo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_printer);
        Printooth.INSTANCE.init(this);
        new SelectConfigLocalidade(this, configLocalidades, ResourcesUtil.Companion.getString(this, R.string.class_configs_localidade)).execute();
    }

    class SelectConfigLocalidade extends BaseSelect<ConfigLocalidade> {

        public SelectConfigLocalidade(Context context, ArrayList<ConfigLocalidade> arrayList, String nameModel) {
            super(context, arrayList, nameModel);
        }

        @Override
        protected void onPostExecute(List<? extends ConfigLocalidade> result) {
            super.onPostExecute(result);
            if (result != null) {
                if (!result.isEmpty()) {
                    for (ConfigLocalidade c : result) {
                        configLocalidades.add(c);
                    }
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Printooth.INSTANCE.hasPairedPrinter()) {
            printing = Printooth.INSTANCE.printer();
        }

        sharedPreferences = PreferencesUtils.Companion.preferences(this);
        numberSerial = sharedPreferences.getString(PreferencesUtils.Companion.getNUMBER_SERIAL(), null);
        address = sharedPreferences.getString(PreferencesUtils.Companion.getPRINTER_ADDRESS(), null);
        nameDevice = sharedPreferences.getString(PreferencesUtils.Companion.getPRINTER_NAME(), null);
        TextView text_termal_printer = findViewById(R.id.text_termal_printer_teste);
        if (address != null)
            text_termal_printer.setText("Name: " + nameDevice + "\nAddredd " + address);
        else
            text_termal_printer.setText("Nenhuma impressora selecionada!");

        if (getIntent().hasExtra(ResourcesUtil.Companion.getString(this, R.string.extra_login))
                && getIntent().hasExtra(ResourcesUtil.Companion.getString(this, R.string.extra_tel))
                && getIntent().hasExtra(ResourcesUtil.Companion.getString(this, R.string.extra_carrinhos_rifas))
                && getIntent().hasExtra(ResourcesUtil.Companion.getString(this, R.string.extra_pules))
                && getIntent().hasExtra(ResourcesUtil.Companion.getString(this, R.string.extra_cpf))
                && getIntent().hasExtra("byteArray")) {

            carrinhosRifas = (ArrayList<CarrinhoRifa>) getIntent().getSerializableExtra(ResourcesUtil.Companion.getString(this, R.string.extra_carrinhos_rifas));
            pules = (ArrayList<Pule>) getIntent().getSerializableExtra(ResourcesUtil.Companion.getString(this, R.string.extra_pules));
            sumTotal = getIntent().getStringExtra(ResourcesUtil.Companion.getString(this, R.string.extra_sum_bets));
            dataPule = getIntent().getStringExtra(ResourcesUtil.Companion.getString(this, R.string.extra_data_pule));
            cliente = getIntent().getStringExtra(ResourcesUtil.Companion.getString(this, R.string.extra_login));
            cpf = getIntent().getStringExtra(ResourcesUtil.Companion.getString(this, R.string.extra_cpf));
            tel = getIntent().getStringExtra(ResourcesUtil.Companion.getString(this, R.string.extra_tel));
            operador_codigo = getIntent().getStringExtra(ResourcesUtil.Companion.getString(this, R.string.extra_operador_codigo));

            initViews();
            initListeners();

            if (carrinhosRifas != null && !pules.isEmpty() && !carrinhosRifas.isEmpty()) {
                bitmapImage = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("byteArray"), 0, getIntent().getByteArrayExtra("byteArray").length);

                ImageView imgQr = findViewById(R.id.image_view_qr_printer_termincal_teste);
                imgQr.setImageBitmap(bitmapImage);
                isPrinter = !isPrinter;
                if (isPrinter) {
                    btnPrint();
                }
            } else {
                new LoggerUtisl(TAG).error("Empty carrinhos NULL");
            }

        } else {
            new LoggerUtisl(TAG).error("Not exists extras");
        }
    }

    private void initViews() {
        if (Printooth.INSTANCE.getPairedPrinter() != null) {
            Button button = findViewById(R.id.button_teste_printer);
            button.setText((Printooth.INSTANCE.hasPairedPrinter()) ? ("Un-pair " + Printooth.INSTANCE.getPairedPrinter().getName()) : "Pair with printer");
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnPrint();
                }
            });
        } else {
            ToastUtils.Companion.message(this, "Not paread");
            Button btn_parear = findViewById(R.id.button_parear);
            btn_parear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btnPiarUnpair();
                }
            });
        }
    }

    public void btnPrint() {
        if (!Printooth.INSTANCE.hasPairedPrinter())
            startActivityForResult(new Intent(this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
        else printSomePrintable();
    }

    private void printSomePrintable() {
        Log.d("xxx", "printSomePrintable ");
        if (printing != null) {
            printing.print(getSomePrintables());
            finish();
        } else
            Log.e("xxx", "printing null");

    }

    public void btnPrintImages() {
        if (!Printooth.INSTANCE.hasPairedPrinter())
            startActivityForResult(new Intent(this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
        else printSomeImages();
    }

    private void printSomeImages() {
        if (printing != null) {
            Log.d("xxx", "printSomeImages ");
            ArrayList<Printable> al = new ArrayList<>();
            Resources resources = getResources();
            Bitmap image = BitmapFactory.decodeResource(resources, R.drawable.logo_mega_rifa_80x80);
            al.add(new ImagePrintable.Builder(image).build());
            //al.add(new ImagePrintable.Builder(R.drawable.image1,  resources ).build());
            //al.add(ImagePrintable.Builder(R.drawable.image2, resources).build());
            //al.add(ImagePrintable.Builder(R.drawable.image3, resources).build());
            printing.print(al);
        }
    }

    public void btnPiarUnpair() {
        if (Printooth.INSTANCE.hasPairedPrinter()) {
            Printooth.INSTANCE.removeCurrentPrinter();
        } else {
            startActivityForResult(new Intent(this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
            initViews();
        }
    }

    private void initListeners() {
        if (printing != null && printingCallback == null) {
            Log.d("xxx", "initListeners ");
            printingCallback = new PrintingCallback() {

                public void connectingWithPrinter() {
                    Toast.makeText(getApplicationContext(), "Connecting with printer", Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "Connecting");
                }

                public void printingOrderSentSuccessfully() {
                    Toast.makeText(getApplicationContext(), "printingOrderSentSuccessfully", Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "printingOrderSentSuccessfully");
                }

                public void connectionFailed(@NonNull String error) {
                    Toast.makeText(getApplicationContext(), "connectionFailed :" + error, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "connectionFailed : " + error);
                }

                public void onError(@NonNull String error) {
                    Toast.makeText(getApplicationContext(), "onError :" + error, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "onError : " + error);
                }

                public void onMessage(@NonNull String message) {
                    Toast.makeText(getApplicationContext(), "onMessage :" + message, Toast.LENGTH_SHORT).show();
                    Log.d("xxx", "onMessage : " + message);
                }
            };

            Printooth.INSTANCE.printer().setPrintingCallback(printingCallback);
        }
    }

    private ArrayList<Printable> getSomePrintables() {

        ArrayList<Printable> al = new ArrayList<>();
        al.add(new RawPrintable.Builder(new byte[]{27, 100, 4}).build()); // feed lines example in raw mode

        //header
//        al.add(getImage(R.drawable.logo_mega_rifa_80x80));
        al.add(getTextPrinteable("Mega Rifa", DefaultPrinter.Companion.getALIGNMENT_CENTER(), DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));
        al.add(getTextPrinteable("Via do cliente", DefaultPrinter.Companion.getALIGNMENT_CENTER(), DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        if (!pules.isEmpty()) {
            for (Pule p : pules) {
                al.add(getTextPrinteable("POULE " + (p.getPule_id() - 1), DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                        DefaultPrinter.Companion.getFONT_SIZE_LARGE()));
            }
        }

        al.add(getTextPrinteable("Realização: " + StringUtils.Companion.removeAcentos(dataPule), DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("Terminal: " + numberSerial, DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("Ponto: " + operador_codigo,DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("---------------------------------------------",DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable(cliente != null ? "Cliente: "+cliente : "Cliente: Não existe cliente",DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable(tel != null ? "Tel: " + tel : "Tel: Não existe telefone",DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("---------------------------------------------", DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("Rifas", DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        Modalidade modality = Modalidade.Companion.helperPreferences(sharedPreferences);

        //max unity from used
        long unity = modality.getUnidade();

        for (CarrinhoRifa c : carrinhosRifas) {

            al.add(getTextPrinteable(c.getNome() != null ? StringUtils.Companion.removeAcentos(c.getNome()) : "Não existe rifa", DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                    DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

            al.add(getTextPrinteable(c.getValor_rifa()!=null ? "(R$" + StringUtils.Companion.numberFormater(c.getValor_rifa())+")":
                            "(R$0,00)", DefaultPrinter.Companion.getALIGNMENT_CENTER(), DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

            al.add(getTextPrinteable(c.getDataSorteio() != null ? "(Sorteio: " + StringUtils.Companion.removeAcentos(c.getDataSorteio())+")" : "(Sorteio: Não existe)",
                    DefaultPrinter.Companion.getALIGNMENT_CENTER(), DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

            al.add(getTextPrinteable(c.getConcurso() != null ? "(Concurso: " + StringUtils.Companion.removeAcentos(c.getConcurso())+")" : "(Concurso: Não existe)",
                    DefaultPrinter.Companion.getALIGNMENT_CENTER(), DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

            if (c.getNumeros() != null) {
                String numeros = StringUtils.Companion.removeChar(c.getNumeros());
                String strAposta = StringUtils.Companion.regex(numeros, (int) unity);
                al.add(getTextPrinteable("N°: " + strAposta, DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                        DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));
            }
        }

        al.add(getTextPrinteable("---------------------------------------------", DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable(sumTotal != null ? "Total " + carrinhosRifas.size() + " jogos ":"Total 0 jogos", DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("", DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable(sumTotal != null ? "R$" + StringUtils.Companion.numberFormater(sumTotal) : "Total: R$ 0.00" , DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("---------------------------------------------",
                DefaultPrinter.Companion.getALIGNMENT_CENTER(), DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("Vale o impresso e confira o seu jogo.",  DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("A sorte é sua e a garantia é nossa.",  DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("---------------------------------------------",  DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("Reclamações 5 dias",DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        al.add(getTextPrinteable("---------------------------------------------",  DefaultPrinter.Companion.getALIGNMENT_CENTER(),
                DefaultPrinter.Companion.getFONT_SIZE_NORMAL()));

        //QRCode
        al.add(createQRCode(bitmapImage));

        return al;
    }

    private void image() {
        if (printing != null) {
            Log.d("xxx", "printSomeImages ");
            ArrayList<Printable> al = new ArrayList<>();
            Resources resources = getResources();
            //getContext();
            Bitmap image = BitmapFactory.decodeResource(resources,R.drawable.logo_200w);
            al.add(new ImagePrintable.Builder(image).build());
            //al.add(new ImagePrintable.Builder(R.drawable.image1,  resources ).build());
            //al.add(ImagePrintable.Builder(R.drawable.image2, resources).build());
            //al.add(ImagePrintable.Builder(R.drawable.image3, resources).build());
            printing.print(al);
        }
    }

    private Printable space(int size) {
        return new TextPrintable.Builder()
                .setNewLinesAfter(size)
                .build();
    }


    private Printable createQRCode(Bitmap bitmapImage) {
        return new ImagePrintable.Builder(bitmapImage)
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .build();
    }

    private Printable getImage(int icon) {
        Resources resources = getResources();
        Bitmap image = BitmapFactory.decodeResource(resources, icon);
        Bitmap bitmap = Bitmap.createScaledBitmap(image, 150, 150, true);
        return new ImagePrintable.Builder(bitmap)
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .build();
    }

    private Printable getTextPrinteable(String text, byte align, byte font) {
        return new TextPrintable.Builder()
                .setText(text)
                .setFontSize(font)
//                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
//                .setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252())
                .setAlignment(align)
                .setNewLinesAfter(1)
                .build();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("xxx", "onActivityResult " + requestCode);
        if (requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK) {
            initListeners();
            printSomePrintable();
        }
        initViews();
    }

}

//====================================================================
//                Retrofit passando token header
//====================================================================  
class RetrofitInicializador {

    private val retrofit: Retrofit

    constructor(contex: Context, token:String?) {

        val MYURL = MyIp.getLocalIpAddress()
        var interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        var client = OkHttpClient.Builder()
        client.addInterceptor(interceptor)
		
	//add token header
        client.addInterceptor{chain ->
            val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            chain.proceed(newRequest)
        }

        retrofit = Retrofit.Builder()
                .baseUrl(MYURL)
                .client(client.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }

    fun getExtracao(): ExtracaoService {
        return retrofit.create(ExtracaoService::class.java)
    }
}
//====================================================================
//                Select impresora
//====================================================================  

package com.example.arca.fragments

import android.app.Activity
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.arca.R
import com.example.arca.adapter.MyBluetoothAdapter
import com.example.arca.fragments.base.BaseFrament
import com.example.arca.model.MyDevices
import com.example.arca.util.LoggerUtisl

class SettingKotlinFraments : BaseFrament(R.layout.layout_fragment_settings) {

    private var adapter: MyBluetoothAdapter? = null
    private var devices = ArrayList<MyDevices>()
    private val TAG: String = "SettingKotlinFramentsLog"
    private val REQUEST_ENABLE_BT: Int = 101
    private var bluetoothAdapter: BluetoothAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initBluetooth()

    }

    private fun initBluetooth() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            LoggerUtisl(TAG).error("Not supported bluetooth")
        }

        if (bluetoothAdapter?.isEnabled == false) {
            LoggerUtisl(TAG).error("Bluetooth inactive")
            val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
        }
    }

    private fun listdevices() {

        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
        if (pairedDevices?.isNotEmpty() == true) {
            pairedDevices?.forEach { device ->
                val name = device.name
                val address = device.address // MAC address
                LoggerUtisl(TAG).error("devices $name\nAddress $address")
                addDeveices(device)
            }
        } else {

            LoggerUtisl(TAG).error("Not exists device")
            // Register for broadcasts when a device is discovered.
            val filter = IntentFilter(BluetoothDevice.ACTION_FOUND)
            context?.registerReceiver(receiver, filter)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            if (receiver != null)
                context?.unregisterReceiver(receiver)
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        }
    }

    private val receiver = object : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {
            val action: String = intent.action.toString()
            LoggerUtisl(TAG).error("Execute action $action")

            when (action) {
                BluetoothDevice.ACTION_FOUND -> {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    val device: BluetoothDevice = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE)
                    val deviceName = device.name
                    val address = device.address //MAC address
                    var type = device.type
                    
                    if (deviceName != null) {
                        addDeveices(device)
                    }

                }
                else -> LoggerUtisl(TAG).error("Not exits action")
            }
        }
    }

    private fun addDeveices(device: BluetoothDevice) {
        devices.add(MyDevices(null, device.name, device.address, device.type))
        adapter?.notifyDataSetChanged()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_ENABLE_BT -> {
                if (resultCode == Activity.RESULT_OK) {
                    LoggerUtisl(TAG).error("Activity.RESULT_OK")
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var recyclerView = view.findViewById<RecyclerView>(R.id.recycle_view_bluetooth)
        view.findViewById<ImageButton>(R.id.button_sync_bluetooth).setOnClickListener {
            bluetoothAdapter?.startDiscovery()
        }

        devices = ArrayList()
        adapter = MyBluetoothAdapter(context, devices)
        recyclerView.adapter = adapter
        listdevices()
        bluetoothAdapter?.startDiscovery()
    }
}

//====================================================================
//                 mascara edtText
//====================================================================
edt_number_cartao.addTextChangedListener(listenerChangedEditText(text_view_number_card, 4, " "))
private fun listenerChangedEditText(myValor: TextView, unidade: Int, char: String): TextWatcher? {
        return object : TextWatcher {
            var current = ""
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isNotEmpty()) {
                    when {
                        unidade > 2 -> myValor.text = "${StringUtils.addCharecterIn(s.toString(), unidade, char)}"
                        unidade == 2 -> {

                            edt_date_explire.removeTextChangedListener(this)
                            var cleanString = s.toString().replace("[^0-9]".toRegex(), "")
                            var formatted = StringUtils.addCharecterIn(cleanString, 4, "/")
                            edt_date_explire.setText(formatted)
                            edt_date_explire.setSelection(formatted.length)
                            myValor.text = "${edt_date_explire.text}"
                            edt_date_explire.addTextChangedListener(this)

                        }
                        else -> myValor.text = "$s"
                    }
                }
            }
        }
    }
//====================================================================
//                 LOCK onBackPressed
//====================================================================

 override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialogDiscartGame()
        }
        return true
    }

//====================================================================
//                 REGEX
//====================================================================
fun removeAll(str:String):String?{
   return str.replace("[^A-Z0-9]".toRegex(), "").toRegex().toString()
}
fun aceptNumber(str:String):String?{
	return str.replace("[^0-9]".toRegex(), "")
}

//====================================================================
//                 MEDIA PLAYER
//====================================================================	
var mediaPlayer: MediaPlayer? = MediaPlayer.create(context, R.raw.sound_file_1)
mediaPlayer?.start() // no need to call prepare(); create() does that for you

//====================================================================
//                 CHANGE BACKGOUR ITEM RECYCLKE VIEW
//====================================================================	
package com.example.urbano_park_fiscal.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.urbano_park_fiscal.R;
import com.example.urbano_park_fiscal.model.Tarifas;
import com.example.urbano_park_fiscal.util.PrefendecsUtil;
import com.example.urbano_park_fiscal.util.StringsUtil;

import java.util.ArrayList;
import java.util.List;

public class TarifasAdapter extends RecyclerView.Adapter<TarifasAdapter.MyHolder> {

    private static final String TAG = "TarifasAdapterLog";
    private final Context context;
    private final List<Tarifas> tarifas;
    private final Tarifas tarifa;
    private final SharedPreferences sharedPreferences;
    private List<LinearLayout> layouts = new ArrayList<>();

    public TarifasAdapter(Context context, List<Tarifas> tarifas) {
        this.context = context;
        this.tarifas = tarifas;
        tarifa = new Tarifas();
        sharedPreferences = PrefendecsUtil.getInstance(context);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.tarifas_layout, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, final int position) {
        holder.add(tarifas.get(position));
        layouts.add(holder.layout);
        if (layouts.size() > 0) {
            layouts.get(0).setBackgroundResource(R.color.colorPrimary);
            if (tarifas.size() > 0) {
                tarifa.setDescricao(tarifas.get(0).getDescricao());
                tarifa.setDescricao(tarifas.get(0).getId());
                tarifa.setDescricao(tarifas.get(0).getValor());
            }
        }

        holder.layout.setOnClickListener(listener(tarifas.get(position), tarifas, position));

    }

    private View.OnClickListener listener(final Tarifas tarifa, final List<Tarifas> tarifas, final int position) {
        return new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                for (int i = 0; i < layouts.size(); i++) {
                    if (position == i) {
                        Log.e(TAG, "Position "+tarifa.getValor());
                        layouts.get(i).setBackgroundResource(R.color.colorPrimary);
                        //select o tipo de veculo
                    } else {
                        layouts.get(i).setBackgroundResource(android.R.color.transparent);
                    }
                }

                tarifa.setId(tarifas.get(position).getId());
                tarifa.setDescricao(tarifas.get(position).getDescricao());
                tarifa.setValor(tarifas.get(position).getValor());
                PrefendecsUtil.saveTrifaPreferences(sharedPreferences, tarifa);
            }
        };
    }

    @Override
    public int getItemCount() {
        return tarifas.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder{

        private TextView descr ;
        private TextView valor ;
        private LinearLayout layout;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            descr = itemView.findViewById(R.id.text_tarifa_descricao);
            layout = itemView.findViewById(R.id.layout_tarifas_adapter);
            valor = itemView.findViewById(R.id.text_tarifa_valor);
        }

        public void add(Tarifas tarifas) {
            descr.setText(tarifas.getDescricao());
            valor.setText(StringsUtil.formater(tarifas.getValor()));
        }
    }
}

<?xml version="1.0" encoding="utf-8"?>
//====================================================================
//                 XML
//====================================================================	

<LinearLayout android:id="@+id/layout_tarifas_adapter"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@drawable/ic_bg_layout"
    android:orientation="vertical"
    android:padding="12dp"
    xmlns:android="http://schemas.android.com/apk/res/android">

        <TextView
            android:id="@+id/text_tarifa_descricao"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="30 Min"
            android:textAlignment="center"
            android:textColor="@android:color/black"
            android:textSize="14dp" />

        <TextView
            android:id="@+id/text_tarifa_valor"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="R$ 2,00"
            android:textAlignment="center"
            android:textColor="@android:color/black"
            android:textSize="18dp"
            android:textStyle="bold" />
    </LinearLayout>




//====================================================================
//                 RETROFIT
//====================================================================	
package com.watssap.retrofit;
import com.watssap.retrofit.service.UsuarioService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInicializador {
    private final Retrofit retrofit;
    public RetrofitInicializador() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.addInterceptor(interceptor);
        retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.15/api/public/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public UsuarioService getUser(){
        return retrofit.create(UsuarioService.class);
    }
}


//====================================================================
//INTERFACE 
//====================================================================
package com.watssap.retrofit.service;
import com.watssap.model.Usuario;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UsuarioService {

    @POST("usuario/usuario")
    Call<List<Usuario>> insert(@Body Usuario usuario);
}


//====================================================================
//                 USANDO RETREFIT
//====================================================================	 
UsuarioService user = new RetrofitInicializador().getUser();
                Usuario usuario = new Usuario();
                Call<List<Usuario>> call = user.insert(usuario);
                call.enqueue(new Callback<List<Usuario>>() {
                    @Override
                    public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                        Log.e(TAG, "onReponse "+response.body());
                    }
                    @Override
                    public void onFailure(Call<List<Usuario>> call, Throwable t) {
                        Log.e(TAG, "onFailure  "+t.getMessage());
                    }
                });

//====================================================================
//                 GRADLE CONFIGURACOES
//====================================================================	 
apply plugin: 'com.android.application'
apply plugin: 'kotlin-android'
apply plugin: 'kotlin-android-extensions'

android {
    compileSdkVersion 29
    buildToolsVersion "29.0.2"
    defaultConfig {
        applicationId "com.watssap"
        minSdkVersion 15
        targetSdkVersion 29
        versionCode 1
        versionName "1.0"
        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }

    //Add
    packagingOptions {
        exclude "META-INF/LICENSE"
    }

    //Add
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
        targetCompatibility JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation fileTree(dir: 'libs', include: ['*.jar'])
    implementation "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$kotlin_version"
    implementation 'androidx.appcompat:appcompat:1.0.2'
    implementation 'androidx.core:core-ktx:1.0.2'
    implementation 'androidx.constraintlayout:constraintlayout:1.1.3'
    implementation 'com.google.android.material:material:1.0.0'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'androidx.test.ext:junit:1.1.0'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.1.1'

    //Retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.7.1'

    //Gojson
    implementation'com.squareup.retrofit2:converter-gson:2.5.0'
	   
	    //log interceptor
    implementation "com.squareup.okhttp3:logging-interceptor:4.2.1"
}


//====================================================================
//                 FORMATAR EDTTEXT MONETARIO
//====================================================================	 

private String current = "";
@Override
public void onTextChanged(CharSequence s, int start, int before, int count) {
	String formater = RegextUtil.formater(s.toString());
        if(!s.toString().equals(current)) {
	Locale myLocale = new Locale("pt", "BR");
        //Nesse bloco ele monta a maskara para money
        myValor.removeTextChangedListener(this);
        String cleanString = s.toString().replaceAll("[R$,.]", "");
        Double parsed = Double.parseDouble(cleanString);
        String formatted = NumberFormat.getCurrencyInstance(myLocale).format((parsed / 100));
        current = formatted;

        myValor.setText(formatted);
        myValor.setSelection(formatted.length());
        myValor.addTextChangedListener(this)
}

//====================================================================
//                 SHARPREFERENCES
//====================================================================	
package com.example.arca.util

import android.content.Context
import android.content.SharedPreferences

class PreferencesUtils {

    val LOCALIDADE_ID = "localidade_id"
    private lateinit var sharedPreferences: SharedPreferences
    private val PREFERENCES = "PREFERENCES"

    fun preferences(context: Context): SharedPreferences {
        sharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        return sharedPreferences
    }
}
//====================================================================
//ADD
//====================================================================
if (operadores.isNotEmpty()) {
            //init session preferences
            preferences = PreferencesUtils().preferences(this)
            var editor = preferences.edit()
            for (n in operadores) {
                editor?.putLong(PreferencesUtils().LOCALIDADE_ID, n.localidade_id)
            }
            editor?.commit()
        }
//====================================================================
//LISTAR
//====================================================================
var preferences = PreferencesUtils().preferences(this)
localidade_id = preferences.getLong(PreferencesUtils().LOCALIDADE_ID, 0)

//====================================================================
//                 DATA E HORA UTIL
//====================================================================	

package com.example.urbano_park_fiscal.util;
import android.util.Log;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DataUtil {

    private static final String TAG = "DataUtilLog";

    public static String getDataAtualAmericana() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDataAtual() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }
    public static String getDataAtualHora() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getDataAtualHoraPadraoAmericano() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String converteDataHoraPadraoAmericano(String strData)  {
        // Format of the date defined in the input String

        Log.e(TAG, "Data params "+strData);

        String[] data = strData.split("/");
        String dia = data[0];
        String mes = data[1];
        String ano = data[2];

        String [] hora = ano.split(" ");
        ano = hora[0];

        String dataFormatada = ano+"/"+mes+"/"+dia+" "+hora[1];
        Log.e(TAG, "Data formatada "+dataFormatada);

        return dataFormatada;
    }

    public static String converteDataPadraoBrasilerio(String strData)  {
        // Format of the date defined in the input String

        Log.e(TAG, "Data params "+strData);

        String[] data = strData.split("/");
        String dia = data[2];
        String mes = data[1];
        String ano = data[0];

        String dataFormatada = dia+"/"+mes+"/"+ano;
        Log.e(TAG, "Data formatada "+dataFormatada);

        return dataFormatada;
    }

    public static String getHora() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date hora = Calendar.getInstance().getTime(); // Ou qualquer outra forma que tem
        return sdf.format(hora);
    }

    public static String converteDataPadraoAmericano(String dataInicio) {
        String[] dataSplit = dataInicio.split("/");
        String dia = dataSplit[0];
        String mes = dataSplit[1];
        String ano = dataSplit[2];
        return ano +"/"+mes+"/"+dia;
    }
}

//====================================================================
//                  PASSANDO ARRAY POR INTENT
//====================================================================	

//ENVIANDO
var intent = Intent(context, PrinterActivity::class.java)
intent.putExtra("produtos",listaProdutos as Serializable)
context.startActivity(intent)
	
//RECEBENDO
if (intent.getSerializableExtra("produtos") as ArrayList<Produto> != null) {
            var produtos = intent.getSerializableExtra("produtos") as ArrayList<Produto>
            if (produtos.isNotEmpty()) {
                for (p in produtos){
                    imprimir(p)
                }
            }
        }

//====================================================================
//                  IMPRIMIR QR CONDE BLUETHOP
//====================================================================	
try{

            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            BitMatrix bitMatrix = multiFormatWriter.encode("Vc E B...a :-)",
                    BarcodeFormat.QR_CODE, 230, 230);

            BarcodeEncoder encoder = new BarcodeEncoder();
            Bitmap bitmap = encoder.createBitmap(bitMatrix);

            ImageView imgQr = findViewById(R.id.img_qr);
            imgQr.setImageBitmap(bitmap);

            final Intent i = new Intent(this, PrinterTest.class);
            ByteArrayOutputStream bs = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bs);
            i.putExtra("byteArray", bs.toByteArray());

            Button button_qr = findViewById(R.id.button_gerar_qr);
            button_qr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(i);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


//====================================================================
//                  GERAR IMPRESSAO
//====================================================================	

package com.example.urbano_park_fiscal.ui;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.urbano_park_fiscal.R;
import com.example.urbano_park_fiscal.model.Convenio;
import com.example.urbano_park_fiscal.model.Estacionamento;
import com.example.urbano_park_fiscal.model.Estatistica;
import com.example.urbano_park_fiscal.model.Fechamento;
import com.example.urbano_park_fiscal.model.URLS;
import com.example.urbano_park_fiscal.util.CheckNetowrkUtil;
import com.example.urbano_park_fiscal.util.DialogUtil;
import com.example.urbano_park_fiscal.util.FormatarStringUtil;
import com.example.urbano_park_fiscal.util.PrinterCommands;
import com.example.urbano_park_fiscal.util.PrinterUtils;
import com.example.urbano_park_fiscal.util.UtilsText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PrinterTest extends AppCompatActivity implements Runnable {

    private static final int REQUEST_ENABLE_BT = 2;
    private static final int REQUEST_CONNECT_DEVICE = 1;
    private static String BILL = "";
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;
    private UUID applicationUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private String TAG = "PrinterTestLOg";
    private boolean isPrinter2Via;
    private boolean isPrinter = false;
    private File PATH;
    private String NOME_PDF = "cupom_fiscal.pdf";
    private String NOME_TEXT = "arquivo.txt";
    private OutputStream outputStream;
    private byte FONT_TYPE;
    private TextView text_imprimindo;
    private String comeco, placa, marca, cod_ticket;
    private String estacionamento_id;
    private List<Estacionamento> estacionamentos;
    private List<Fechamento> fechamentos;
    private List<Estatistica> estatisticas;
    private List<Convenio> convenios;
    private static String resp, msg;
    private Bitmap bitmapImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_printer);

        Toolbar toolbar = findViewById(R.id.my_toolbar);
        toolbar.setTitle("IMPRESSÃO DE CUPOM");
        setSupportActionBar(toolbar);

        fechamentos = new ArrayList<>();
        estatisticas = new ArrayList<>();
        convenios = new ArrayList<>();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("byteArray")) {

            bitmapImage = BitmapFactory.decodeByteArray(
                    getIntent().getByteArrayExtra("byteArray"), 0,
                    getIntent().getByteArrayExtra("byteArray").length);

            ImageView imgQr = findViewById(R.id.imageViewImpressora);
            imgQr.setImageBitmap(bitmapImage);

            isPrinter = !isPrinter;
            if (isPrinter) {
                imprimir();
            }
        }
    }

    private void imprimir() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null) {
            if (!mBluetoothAdapter.isEnabled()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            } else {
                try {
                    ListPairedDevices();
                    //envia para essa activity
                    Intent connectIntent = new Intent(PrinterTest.this, DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void ListPairedDevices() {
        Set<BluetoothDevice> mPairedDevices = mBluetoothAdapter.getBondedDevices();
        if (mPairedDevices.size() > 0) {
            for (BluetoothDevice mDevice : mPairedDevices) {
                Log.e(TAG, "PairedDevices: " + mDevice.getName() + "\n" + mDevice.getAddress());
            }
        }
    }

    @Override
    public void onActivityResult(int mRequestCode, int mResultCode,
                                 Intent mDataIntent) {
        super.onActivityResult(mRequestCode, mResultCode, mDataIntent);

        switch (mRequestCode) {
            case REQUEST_CONNECT_DEVICE:

                //when receive result code, execute printer
                if (mResultCode == Activity.RESULT_OK) {

                    Log.e(TAG, "RESULT_OK ");
                    Bundle mExtra = mDataIntent.getExtras();
                    String mDeviceAddress = mExtra.getString("DeviceAddress");

                    //calling device
                    mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(mDeviceAddress);
                    Log.e(TAG, "Coming incoming address " + mDeviceAddress);

                    Thread mBlutoothConnectThread = new Thread(PrinterTest.this);
                    mBlutoothConnectThread.start();

                    // pairToDevice(mBluetoothDevice); This method is replaced by
                    // progress falhaConexao with thread
                }
                break;

            case REQUEST_ENABLE_BT:
                if (mResultCode == Activity.RESULT_OK) {
                    ListPairedDevices();
                    Intent connectIntent = new Intent(this, DeviceListActivity.class);
                    startActivityForResult(connectIntent, REQUEST_CONNECT_DEVICE);
                } else {
                    Toast.makeText(PrinterTest.this, "Message", Toast.LENGTH_SHORT).show();
                }
                break;

            case 10:
                break;
        }
    }

    @Override
    public void run() {
        try {
            mBluetoothSocket = mBluetoothDevice
                    .createRfcommSocketToServiceRecord(applicationUUID);
            mBluetoothAdapter.cancelDiscovery();
            mBluetoothSocket.connect();
            mHandler.sendEmptyMessage(0);
        } catch (IOException eConnectException) {
            Log.d(TAG, "CouldNotConnectToSocket", eConnectException);
            closeSocket(mBluetoothSocket);
            return;
        }
    }

    private void closeSocket(BluetoothSocket mBluetoothSocket) {
        try {
            mBluetoothSocket.close();
            Log.d(TAG, "SocketClosed");
        } catch (IOException ex) {
            PrinterUtils.falhaNaImpressao(PrinterTest.this);
            finish();
            Log.d(TAG, "CouldNotCloseSocket");
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    printer();
                }
            }, 2000);
        }
    };


    protected void printer() {

        try {

            outputStream = mBluetoothSocket.getOutputStream();
            byte[] printformat = new byte[]{0x1B, 0x21, 0x03};
            outputStream.write(printformat);

            printPhoto(R.drawable.ic_logo_cupom52x74);

            printCustom("FIRMEZA TOTAL", 3, 1);
            printCustom("MAIS UM ANO SE PASSOU", 3, 1);
            printCustom("GRACAS A DEUS AGENTE", 3, 1);
            printCustom("ESTA COM SAUDE", 3, 1);
            printCustom("MUITA COLETIVIDADE DA QUEBRADA", 3, 1);
            printCustom("DINHEIRO NO BOLSO, SEM MISERIA", 3, 1);
            printCustom("VAMOS BRINDAR O DIA DE HJ", 3, 1);
            printCustom("QUE O AMANHA SO PERTENCE A DEUS", 3, 1);
            printCustom("A VIDA E LOUCA", 3, 1);
            printCustom("", 1, 1);
            printNewLine();

            printCustom("", 1, 1);
            printNewLine();
            printBitmap(bitmapImage);
            printCustom("", 1, 1);
            printCustom("", 1, 1);
            printNewLine();
            printNewLine();


            printCustom("", 0, 1);
            printCustom("www.urbanopark.com.br", 0, 1);
            printNewLine();
            printCustom("", 0, 1);
            printNewLine();
            printNewLine();

            outputStream.flush();
            finish();

        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    private String leftRightAlign(String str1, String str2) {
        String ans = str1 + str2;
        if (ans.length() < 31) {
            int n = (31 - str1.length() + str2.length());
            ans = str1 + new String(new char[n]).replace("\0", " ") + str2;
        }
        return ans;
    }

    //print custom
    private void printCustom(String msg, int size, int align) {
        //Print config "mode"
        byte[] cc = new byte[]{0x1B, 0x21, 0x03};  // 0- normal size text
        //byte[] cc1 = new byte[]{0x1B,0x21,0x00};  // 0- normal size text
        byte[] bb = new byte[]{0x1B, 0x21, 0x08};  // 1- only bold text
        byte[] bb2 = new byte[]{0x1B, 0x21, 0x20}; // 2- bold with medium text
        byte[] bb3 = new byte[]{0x1B, 0x21, 0x10}; // 3- bold with large text

        try {
            switch (size) {
                case 0:
                    outputStream.write(cc);
                    break;
                case 1:
                    outputStream.write(bb);
                    break;
                case 2:
                    outputStream.write(bb2);
                    break;
                case 3:
                    outputStream.write(bb3);
                    break;
            }

            switch (align) {
                case 0:
                    //left align
                    outputStream.write(PrinterCommands.ESC_ALIGN_LEFT);
                    break;
                case 1:
                    //center align
                    outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                    break;
                case 2:
                    //right align
                    outputStream.write(PrinterCommands.ESC_ALIGN_RIGHT);
                    break;
            }
            outputStream.write(msg.getBytes());
            outputStream.write(PrinterCommands.LF);
            //outputStream.write(cc);
            //printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void printPhoto(int img) {

        try {
            Bitmap bmp = BitmapFactory.decodeResource(getResources(), img);
            if (bmp != null) {
                byte[] command = UtilsText.decodeBitmap(bmp);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(command);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }

    public void printBitmap(Bitmap bitmap) {


        try {
            if (bitmap != null) {
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream);
                byte[] image = UtilsText.decodeBitmap(bitmap);
                outputStream.write(PrinterCommands.ESC_ALIGN_CENTER);
                printText(image);
            } else {
                Log.e("Print Photo error", "the file isn't exists");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("PrintTools", "the file isn't exists");
        }
    }


    //print new line
    private void printNewLine() {
        try {
            outputStream.write(PrinterCommands.FEED_LINE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //print byte[]
    private void printText(byte[] msg) {
        try {
            // Print normal text
            outputStream.write(msg);
            printNewLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}

//====================================================================
//                  ATUALIZAR NAVIGATION BOTTOM
//====================================================================	
    private void updateNavigationBarState(int index) {
        Menu menu = bottom_navigation.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            if (item.toString().equals("Home") && index == 0) {
                item.setChecked(true);
            }
            if (item.toString().equals("Estacionados") && index == 1) {
                item.setChecked(true);
            }
            if (item.toString().equals("Mensalista") && index == 2) {
                item.setChecked(true);
            }
            if (item.toString().equals("Painel") && index == 3) {
                item.setChecked(true);
            }

            Log.e(TAG, "Item " + item.toString());
            Log.e(TAG, "Index " + index);
        }


    }



//====================================================================
//                          ADD MENU FRAGMENTS
//====================================================================	
 @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        MenuItem item = menu.add("Pequisar");
        item.setIcon(R.drawable.ic_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(onSearch());
        super.onCreateOptionsMenu(menu, inflater);
    }

    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //usuari faz a busca
                Toast.makeText(getActivity(), "Placa "+ query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //mudou o texto
                return false;
            }
        };
    }


//====================================================================
//                          DIALOG
//====================================================================	
//show alert dialog for window filter
    private void showAlertFiltro() {

        View view = LayoutInflater.from(this).inflate(R.layout.filter_layout, null, false);
        //DatePicker
	final DatePicker datapicker = view.findViewById(R.id.datePicker);
	//BUTTON
        view.findViewById(R.id.button_filtrar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //filtar

                month = datapicker.getMonth() + 1;
                day = datapicker.getDayOfMonth();
                year = datapicker.getYear();

                if (month > 12) {
                    month = 1;
                }
                data_atual = year + "-" + month + "-" + day;
                new ExtratoApi(ExtratoActivity.this, listView, progressbar, usuario, data_atual).execute();
                alert.dismiss();
            }
        });


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setView(view);
        alert = alertDialog.create();
        alert.show();
    }


//====================================================================
//                          ALURA
//====================================================================

//image bitmapt
private Bitmap getBitmap(int w, int h) {
        Bitmap bitmap = BitmapFactory.decodeFile(caminho);
        return Bitmap.createScaledBitmap(bitmap, w, h, true);
}

myImage = findViewById(R.id.my_image);
Bitmap reduze = getBitmap(300, 200);
myImage.setImageBitmap(reduze);


//====================================================================
//                          camera
//====================================================================
camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    caminho = Environment.getExternalStorageDirectory().toString()+ "/Pictures/" + System.currentTimeMillis() + ".jpg";
                    file = new File(caminho);
                    file_uri = Uri.fromFile(file);
                    i.putExtra(MediaStore.EXTRA_OUTPUT, file_uri);
                    startActivityForResult(i, CAMERA_CODE);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

//==============================================
//EVENTO [ObjectExcpression]
//==============================================
btn.setOnClickLostener{
	Toast.makeText(this@Activity, "", tempo)
}

//==============================================
//DIOLOG LAYOUT
//==============================================
lista_transacoes_adiciona_receita
    .setOnClickListener {

    	//Criar layout
    	val view: View = window.decorView
        
        val viewCriada = LayoutInflater.from(context: this)
        	.inflate(R.layout.form_trasacao, view as ViewGroup, attachToRoot: false)

            AlertDialog.Builder(context: this)
                .setTitle(R.string.adiciona_receita)
                .setView(viewCriada)//ADD LAYPUT
                .show()
}

private void permission() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //ask for permission
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }

            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                //ask for permission
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
            }
        }
    }
    
//salva camera no diretoriop e no campo imagemview
 @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {
            foto = findViewById(R.id.foto);
            Bitmap bitmap = BitmapFactory.decodeFile(caminho);
            Bitmap reduzido = Bitmap.createScaledBitmap(bitmap, 400, 300, true);
            foto.setImageBitmap(reduzido);

            ImageView galeria = findViewById(R.id.galeria);
            galeria.setImageBitmap(reduzido);
        }
    }
//===================================================================================================
//FORMATANDO DATA
//===================================================================================================
val formatoBroasileiro = "dd/MM/yyyy"
val format = SimpleDateFormat(formatoBroasileiro)
val dataFormatada = format.format(lista[position].data.time)
//===================================================================================================
//FORMATANDO MOEDA
//===================================================================================================
NumberFormat formato = DecimalFormat.getCurrencyInstance(new Locale("pt", "br"));
BigDecimal precoDoPacote =  pacote.getPreco();
String moeda = formato
                .format(precoDoPacote)
                .replace("R$", "R$ ");
item_preco.setText(moeda);
//===========================================================
//converter string em Drawable
//===========================================================
Resources resources = view.getResources();
int idDoDrawable = resources.getIdentifier(pacote.getImagem(), "drawable", context.getPackageName());
Drawable drawable = resources.getDrawable(idDoDrawable);
imageView.setImageDrawable(drawable);

//====================================================================
//======================== CONSTRAINT LAYOUT =========================
//====================================================================
app:layout_constraintStart_toStartOf="parent" //linha left
app:layout_constraintEnd_toEndOf="parent" //alinha righrt
app:layout_constraintTop_toTopOf="parent" //alinha top
app:layout_constraintBottom_toBottomOf="parent"//alinha bottom
android:scaleType="centerCrop"//mantem centralizada e preenche toda o espaco
app:layout_constraintVertical_bias="0.0" //alinha top
//====================================================================
//TESTAR LIST VIEW USANDO XML
//====================================================================
//DETRO DO LIST VIEW ADD TOOLS
 <ListView
        
        android:id="@+id/lista_pacote_listView"
        tools:listitem="@layout/item_pacote" //add layout
        android:layout_width="0dp"
        android:layout_height="0dp"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintTop_toBottomOf="@id/imageView"
        app:layout_constraintBottom_toBottomOf="parent"/>
//====================================================================
//SAMPLE DATA DIRECTY
//====================================================================

//====================================================================
//================== FRAGMENT ADAPTADO PARA TABLET ===================
//====================================================================
//1 - CRIAR 1° CLASS FRAGMENT
//====================================================================
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetalheFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhe, container, false);
        return view;
    }

}
//====================================================================
//2 - CRIAR LAYOUT FRAGMENT
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:cardCornerRadius="12dp"
    app:cardElevation="10dp"
    app:cardUseCompatPadding="true">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#00abec"
            android:padding="16dp">

            <TextView
                android:id="@+id/detalhe_prova_materia"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Materia"
                android:textColor="#fff"
                android:textSize="18dp"
                android:textStyle="bold" />

            <TextView
                android:id="@+id/detalhe_prova_data"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentRight="true"
                android:text="Data"
                android:textColor="#fff"
                android:textSize="18dp"
                android:textStyle="bold" />
        </RelativeLayout>

        <ListView
            android:id="@+id/detalhe_prova_topicos"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </LinearLayout>
</android.support.v7.widget.CardView>

//====================================================================
//3 - CRIAR 2° CLASS FRAGMENT
//====================================================================
package br.com.alura.alura;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import modelo.Prova;

public class ListaPorvaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //cria a view para referenciar o fragment
        View view = inflater.inflate(R.layout.fragment_lista_prova, container, false);

        List<String> topicosPortugues = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        List<String> topicosMatematica = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");

        Prova provaPortugues  = new Prova("Porugues", "25/07/19", topicosPortugues);
        Prova provamatematica = new Prova("Matematica", "25/07/19", topicosMatematica);

        List<Prova> provas = Arrays.asList(provaPortugues, provamatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);
        ListView listView = (ListView) view.findViewById(R.id.prova_lista);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetelhesActivity.class);
                intent.putExtra("prova", prova);
                startActivity(intent);
            }
        });

        return view;
    }
}


//====================================================================
//4 - CRIAR 2° LAYOUT FRAGMENT
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView

    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:cardElevation="12dp"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@+id/prova_lista"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</android.support.v7.widget.CardView>


//====================================================================
//5 - CRIAR  1 LAYOUT PARA PORTRAINT COM MESMO NOME
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout

    android:id="@+id/frame_principal"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

</FrameLayout>

//====================================================================
//6 - CRIAR  1 LAYOUT PARA LANDSCAPE COM MESMO NOME
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    android:orientation="horizontal"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:ignore="MissingDefaultResource">

    <!--guarda espaco para fragemt-->
    <FrameLayout
        android:id="@+id/frame_principal"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"/>


    <FrameLayout
        android:id="@+id/frame_secundario"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"/>

</LinearLayout>


//====================================================================
//8 - CRIAR 2 XML PARA VERIFICAR A ORIENTACAO
//====================================================================
<?xml version="1.0" encoding="utf-8"?>

//DEFINE A PROPRIEDADE DO XML COMO ORIENTACAO LANDSCAPE
//COLOCAR O MESMO NOME PARA AMBOS
<resources>
    <bool name="modoPaisagem">false</bool>
</resources>

<?xml version="1.0" encoding="utf-8"?>
<resources>
    <bool name="modoPaisagem">true</bool>
</resources>

//====================================================================
//9 - ADD OS FRAGMENTS NA CLASSE PRINCIPAL
//====================================================================
package br.com.alura.alura;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova);

        FragmentManager fragmentManager = getSupportFragmentManager();

        
        FragmentManager fragmentManager = getSupportFragmentManager();

        //abre a transacao
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.frame_principal/*pega esse layout*/, /*troca por esse*/ new ListaPorvaFragment());

        //verifica no xml o tipo de orientacao
        if (estaNoModoPaisagem()) {
            tx.replace(R.id.frame_secundario/*pega esse layout*/, /*troca por esse*/ new DetalheProvaFragment());
        }

        tx.commit();
    }
}


//====================================================================
//================ TROCAR DE FRAGMENT USANDO CLIK EVENTO =============
//====================================================================
//1 - ADD METHODO NA ACIVITY PRINCIPAL
//====================================================================
package br.com.alura.alura;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import modelo.Prova;

public class ProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova);

        FragmentManager fragmentManager = getSupportFragmentManager();

        //abre a transacao
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.frame_principal/*pega esse layout*/, /*troca por esse*/ new ListaPorvaFragment());

        //verifica no xml o tipo de orientacao
        if (estaNoModoPaisagem()) {
            tx.replace(R.id.frame_secundario/*pega esse layout*/, /*troca por esse*/ new DetalheProvaFragment());
        }

        tx.commit();
    }

    private boolean estaNoModoPaisagem() {
        return getResources().getBoolean(R.bool.modoPaisagem);
    }


    //seleciona o fragment
    public void selecionaProva(Prova prova) {

        FragmentManager manager = getSupportFragmentManager();


        if (!estaNoModoPaisagem()) {

            FragmentTransaction tx = manager.beginTransaction();
            DetalheProvaFragment detalheProvaFragment = new DetalheProvaFragment();

            Bundle parametros = new Bundle();
            parametros.putSerializable("prova", prova);

            detalheProvaFragment.setArguments(parametros);//pupular a activity
            tx.replace(R.id.frame_principal, detalheProvaFragment);
            tx.commit();

        } else {

            DetalheProvaFragment detalheFragment = (DetalheProvaFragment) manager.findFragmentById(R.id.frame_secundario);
            detalheFragment.popularcampo(prova);

        }
    }
}

//====================================================================
//2 - CRIAR CLASS FRAGMENT 1
//====================================================================
package br.com.alura.alura.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import br.com.alura.alura.R;
import modelo.Prova;


public class DetalheProvaFragment extends Fragment {


    private TextView campoMateria;
    private TextView campoData;
    private ListView listaTopicos;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhe, container, false);
        campoMateria    = (TextView) view.findViewById(R.id.detalhe_prova_materia);
        campoData       = (TextView) view.findViewById(R.id.detalhe_prova_data);
        listaTopicos    = (ListView) view.findViewById(R.id.detalhe_prova_topicos);

        Bundle parametros = getArguments();

        if (parametros != null) {

            Prova prova = (Prova) parametros.getSerializable("prova");
            popularcampo(prova);
        }

        return view;
    }

    //preenche  os campos
    public void popularcampo(Prova prova) {

        campoMateria.setText(prova.getMateria());
        campoData.setText(prova.getData());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, prova.getTopicos());
        listaTopicos.setAdapter(adapter);
    }

}

//====================================================================
//3 - CRIAR LAYOUT FRAGMENT 1
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:cardCornerRadius="12dp"
    app:cardElevation="10dp"
    app:cardUseCompatPadding="true">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#00abec"
            android:padding="16dp">

            <TextView
                android:id="@+id/detalhe_prova_materia"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Materia"
                android:textColor="#fff"
                android:textSize="18dp"
                android:textStyle="bold" />

            <TextView
                android:id="@+id/detalhe_prova_data"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentRight="true"
                android:text="Data"
                android:textColor="#fff"
                android:textSize="18dp"
                android:textStyle="bold" />
        </RelativeLayout>

        <ListView
            android:id="@+id/detalhe_prova_topicos"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </LinearLayout>
</android.support.v7.widget.CardView>


//====================================================================
//4 - CRIAR CLASS FRAGMENT 2
//====================================================================
package br.com.alura.alura.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import br.com.alura.alura.ProvaActivity;
import br.com.alura.alura.R;
import modelo.Prova;

public class ListaPorvaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //cria a view para referenciar o fragment
        View view = inflater.inflate(R.layout.fragment_lista_prova, container, false);

        List<String> topicosPortugues = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        List<String> topicosMatematica = Arrays.asList("Matematica discreta", "Funcao 2°", "funcao Afin");

        Prova provaPortugues  = new Prova("Porugues", "25/07/19", topicosPortugues);
        Prova provamatematica = new Prova("Matematica", "25/07/19", topicosMatematica);

        List<Prova> provas = Arrays.asList(provaPortugues, provamatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);

        //list view xml
        ListView listView = (ListView) view.findViewById(R.id.prova_lista);
        listView.setAdapter(adapter);

        //trocar o fragment por outro
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //pega a posicao da prova
                Prova prova = (Prova) parent.getItemAtPosition(position);

                //seleciona a activity evento click
                ProvaActivity provaActivity = (ProvaActivity) getActivity();

                //envia os dados da prova com os campos preenvhidos
                provaActivity.selecionaProva(prova);
            }
        });

        return view;
    }
}

//====================================================================
//5 - CRIAR LAYOUT FRAGMENT 2
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView

    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:cardElevation="12dp"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@+id/prova_lista"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</android.support.v7.widget.CardView>

//====================================================================
//6 - CRIAR LAYOUT ACTIVITY PRINCIPAL COM O MESMO NOME
//====================================================================
<!-- fragment landscape -->
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    android:orientation="horizontal"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:ignore="MissingDefaultResource">

    <!--guarda espaco para fragemt-->
    <FrameLayout
        android:id="@+id/frame_principal"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"/>


    <FrameLayout
        android:id="@+id/frame_secundario"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="2"/>
</LinearLayout>

<!-- fragment portrait -->
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    android:id="@+id/frame_principal"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</FrameLayout>


//====================================================================
//======================== MAPAS =====================================
//====================================================================
//1 - CRIAR GOOGLE MAPS ACTIVITY AUTOMATICA 
//====================================================================
//ADD CHAVE XML
//====================================================================
<resources>
    <!--
    TODO: Before you run your application, you need a Google Maps API key.

    To get one, follow this link, follow the directions and press "Create" at the end:

    https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&r=3A:21:2D:2A:EE:6B:E7:B0:0D:36:52:21:63:D7:1F:CA:8E:F7:14:4B%3Bbr.com.alura.alura

    You can also add your credentials to an existing key, using these values:

    Package name:
    3A:21:2D:2A:EE:6B:E7:B0:0D:36:52:21:63:D7:1F:CA:8E:F7:14:4B

    SHA-1 certificate fingerprint:
    3A:21:2D:2A:EE:6B:E7:B0:0D:36:52:21:63:D7:1F:CA:8E:F7:14:4B

    Alternatively, follow the directions here:
    https://developers.google.com/maps/documentation/android/start#get-key

    Once you have your key (it starts with "AIza"), replace the "google_maps_key"
    string in this file.
    -->
    <string name="google_maps_key" templateMergeStrategy="preserve" 
	    translatable="false">KEY</string>
</resources>
//====================================================================
//DELETAR GOOGLE MAPS ACTIVITY
//====================================================================
//2 - CRIAR  CALSS MAPA ACTIVITY
//====================================================================
package br.com.alura.alura;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.android.gms.maps.SupportMapFragment;
import br.com.alura.alura.fragments.MapaFragment;

public class MapaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction tx = manager.beginTransaction();
        tx.replace(R.id.frame_mapa, new MapaFragment());
        tx.commit();
    }
}

//====================================================================
//2 - CRIAR FRAGMENT MAPA FRAGEMT
//====================================================================
package br.com.alura.alura.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

import dao.AlunoDao;
import modelo.Aluno;

public class MapaFragment extends SupportMapFragment implements OnMapReadyCallback {

    private String TAG = "MapLog";

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng posicaoEscola = pegaCoordenadaDoEndereco("Rua Vergueiro 3185, Vila Maria, São Paulo");
        if(posicaoEscola!=null){
            CameraUpdate update = CameraUpdateFactory.newLatLngZoom(posicaoEscola, 17);
            googleMap.moveCamera(update);
        }else{
            Log.e(TAG, "Mapa null");
        }

        //pegas a pocsicao dos  alunos   cadastrado no mapa
        AlunoDao dao = new AlunoDao(getContext());
        for(Aluno aluno: dao.buscaAlunos()){
            LatLng coordenada = pegaCoordenadaDoEndereco(aluno.getEndereco());
            if(coordenada!=null){
                MarkerOptions marcador = new MarkerOptions();
                marcador.position(coordenada);
                marcador.title(aluno.getNome());
                marcador.snippet(String.valueOf(aluno.getNota()));
                googleMap.addMarker(marcador);
            }

            dao.close();
        }
    }

    //PEGA AS CORDENADAS DO MAPA
    private LatLng pegaCoordenadaDoEndereco(String endereco){

        Geocoder geocoder = new Geocoder(getContext());
        try {
            List<Address> resultado = geocoder.getFromLocationName(endereco, 1);
            if(resultado.isEmpty()){
                LatLng latLng = new LatLng(resultado.get(0).getLatitude(), resultado.get(0).getLongitude());
                return latLng;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  null;
    }
}


//====================================================================
//FRAGMENT ADAPTADO PARA TABLET
//====================================================================
//1 - CRIAR 1° CLASS FRAGMENT
//====================================================================
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DetalheFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_detalhe, container, false);
        return view;
    }

}
//====================================================================
//2 - CRIAR LAYOUT FRAGMENT
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:cardCornerRadius="12dp"
    app:cardElevation="10dp"
    app:cardUseCompatPadding="true">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

        <RelativeLayout
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#00abec"
            android:padding="16dp">

            <TextView
                android:id="@+id/detalhe_prova_materia"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="Materia"
                android:textColor="#fff"
                android:textSize="18dp"
                android:textStyle="bold" />

            <TextView
                android:id="@+id/detalhe_prova_data"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:layout_alignParentRight="true"
                android:text="Data"
                android:textColor="#fff"
                android:textSize="18dp"
                android:textStyle="bold" />
        </RelativeLayout>

        <ListView
            android:id="@+id/detalhe_prova_topicos"
            android:layout_width="match_parent"
            android:layout_height="match_parent" />
    </LinearLayout>
</android.support.v7.widget.CardView>

//====================================================================
//3 - CRIAR 2° CLASS FRAGMENT
//====================================================================
package br.com.alura.alura;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import modelo.Prova;

public class ListaPorvaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //cria a view para referenciar o fragment
        View view = inflater.inflate(R.layout.fragment_lista_prova, container, false);

        List<String> topicosPortugues = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        List<String> topicosMatematica = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");

        Prova provaPortugues  = new Prova("Porugues", "25/07/19", topicosPortugues);
        Prova provamatematica = new Prova("Matematica", "25/07/19", topicosMatematica);

        List<Prova> provas = Arrays.asList(provaPortugues, provamatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);
        ListView listView = (ListView) view.findViewById(R.id.prova_lista);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetelhesActivity.class);
                intent.putExtra("prova", prova);
                startActivity(intent);
            }
        });

        return view;
    }
}


//====================================================================
//4 - CRIAR 2° LAYOUT FRAGMENT
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView

    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:cardElevation="12dp"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@+id/prova_lista"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</android.support.v7.widget.CardView>


//====================================================================
//5 - CRIAR  1 LAYOUT PARA PORTRAINT COM MESMO NOME
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout

    android:id="@+id/frame_principal"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

</FrameLayout>

//====================================================================
//6 - CRIAR  1 LAYOUT PARA LANDSCAPE COM MESMO NOME
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    android:orientation="horizontal"
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:ignore="MissingDefaultResource">

    <!--guarda espaco para fragemt-->
    <FrameLayout
        android:id="@+id/frame_principal"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"/>


    <FrameLayout
        android:id="@+id/frame_secundario"
        android:layout_width="0dp"
        android:layout_height="match_parent"
        android:layout_weight="1"/>

</LinearLayout>



//====================================================================
//7 - ADD OS FRAGMENTS NA CLASSE PRINCIPAL
//====================================================================
package br.com.alura.alura;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prova);

        FragmentManager fragmentManager = getSupportFragmentManager();

        //abre a transacao
        FragmentTransaction tx = fragmentManager.beginTransaction();
        tx.replace(R.id.frame_principal/*pega esse layout*/, /*troca por esse*/ new ListaPorvaFragment());
        tx.replace(R.id.frame_secundario/*pega esse layout*/, /*troca por esse*/ new DetalheFragment());
        tx.commit();
    }
}

//====================================================================
//FRAGMENTS
//====================================================================
//1 - CRIAR ACTIVITY PRINCIPAL
//====================================================================
package br.com.alura.alura;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import modelo.Prova;

public class ProvaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_prova);

        FragmentManager fragmentManager = getSupportFragmentManager();

        //abre a transacao
        FragmentTransaction tx = fragmentManager.beginTransaction();

        tx.replace(R.id.frame_principal/*pega esse layout*/, /*troca por esse*/ new ListaPorvaFragment());
        tx.commit();
    }
}

//====================================================================
//2 - CRIAR ACTIVITY LAYOUT
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout
    android:id="@+id/frame_principal"
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
</FrameLayout>

//====================================================================
//3 - CRIAR FRAGMENT
//====================================================================
package br.com.alura.alura;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;
import java.util.List;

import modelo.Prova;

public class ListaPorvaFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //cria a view para referenciar o fragment
        View view = inflater.inflate(R.layout.fragment_lista_prova, container, false);

        List<String> topicosPortugues = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");
        List<String> topicosMatematica = Arrays.asList("Sujeito", "Objeto direto", "Objeto indireto");

        Prova provaPortugues  = new Prova("Porugues", "25/07/19", topicosPortugues);
        Prova provamatematica = new Prova("Matematica", "25/07/19", topicosMatematica);

        List<Prova> provas = Arrays.asList(provaPortugues, provamatematica);

        ArrayAdapter<Prova> adapter = new ArrayAdapter<Prova>(getContext(), android.R.layout.simple_list_item_1, provas);
        ListView listView = (ListView) view.findViewById(R.id.prova_lista);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Prova prova = (Prova) parent.getItemAtPosition(position);
                Intent intent = new Intent(getContext(), DetelhesActivity.class);
                intent.putExtra("prova", prova);
                startActivity(intent);
            }
        });

        return view;
    }
}

//====================================================================
//4 - CRIAR LAYOUT DO FRAGMENT
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.CardView

    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    app:cardElevation="12dp"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ListView
        android:id="@+id/prova_lista"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>

</android.support.v7.widget.CardView>


//====================================================================
//WEBSERVICE
//====================================================================

//cria uma lista 
List<Aluno> alunos  = dao.buscaAlunos();
AlunoConverte conversor = new AlunoConverte();
//chama o metodo para converte json em string
String json = conversor.converteParaJSON(alunos);

//cnverte json
public String converteParaJSON(List<Aluno> alunos) {

        //montar o json
        JSONStringer js = new JSONStringer();
        try {
            js.object().key("List").array().object().key("alunos").array();
            for(Aluno aluno: alunos){
                js.object();//abre objeto
                js.key("nome").value(aluno.getNome());
                js.key("nota").value(aluno.getNome());
                js.endObject();//fecha objeto
            }

            js.endArray().endObject().endArray().endObject();

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return js.toString();
    }
//======================================================================================
//enviar json post
//======================================================================================
//enviar os dados post
//======================================================================================
WebCliente webCliente = new WebCliente();
String resposata = webCliente.post(json);
//======================================================================================
//criar classe de service
//======================================================================================
package br.com.alura.alura;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;
import br.com.alura.alura.converter.AlunoConverte;
import dao.AlunoDao;
import modelo.Aluno;

import java.util.List;

public class EnviarAlunoTask extends AsyncTask<Object, Object, String/*retorn das funcoes*/> {

    private Context context;

    public EnviarAlunoTask(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(Object ...objects) {

        AlunoDao dao = new AlunoDao(context);
        List<Aluno> alunos  = dao.buscaAlunos();
        AlunoConverte conversor = new AlunoConverte();
        String json = conversor.converteParaJSON(alunos);

        //enviar os dados post
        WebCliente webCliente = new WebCliente();
        String resposata = webCliente.post(json);
        return resposata;
    }

    @Override
    protected void onPostExecute(String resposata) {
        super.onPostExecute(resposata);
        Toast.makeText(context,  resposata, Toast.LENGTH_SHORT).show();
    }
}

//======================================================================================
//class que enviar post  get
//======================================================================================
package br.com.alura.alura;
import java.io.IOException;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class WebCliente {

    public String  post(String json){

        try {
            URL url = new URL("http://www.caelum.com.br/mobile");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-type", "json");
            connection.setRequestProperty("Accept", "application/json");
            connection.setDoInput(true);//informa que quer realizar post
            PrintStream out = new PrintStream(connection.getOutputStream());
            out.println(json);
            connection.connect();//connecta com servidro

            //ler reesposta
            Scanner scanner = new Scanner(connection.getInputStream());
            String resposta = scanner.next();

            return resposta;

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}

//======================================================================================
//executar web service
//======================================================================================
Acitviti principal
new EnviarAlunoTask(MainActivity.this).execute();

//====================================================================
//                          BRAODCAST
//====================================================================
//CRIAR CLASS BROADCAST
package br.com.alura.alura.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SMSReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        
        Toast.makeText(context, "Receiver", Toast.LENGTH_SHORT).show();
    }
}
//REGISTRAR ORADCAST DENTRO O MANIFEST
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
          package="br.com.alura.alura">


    <uses-permission android:name="android.permission.CALL_PHONE "/>

    <!-- Add permissao SMS -->
    <uses-permission android:name="android.permission.RECEIVE_SMS "/>


    <application
            android:allowBackup="true"
            android:icon="@mipmap/ic_launcher"
            android:label="@string/app_name"
            android:roundIcon="@mipmap/ic_launcher_round"
            android:supportsRtl="true"
            android:theme="@style/AppTheme">

        <activity android:name=".FormularioActivity">
        </activity>

        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN"/>
                <category android:name="android.intent.category.LAUNCHER"/>
            </intent-filter>
        </activity>

        <!-- registrar receive -->
        <receiver android:name=".receiver.SMSReceiver">
            <!-- detecta qual o tipo de acao -->
            <intent-filter>
                <action android:name="android.provider.Telephony.SMS_RECEIVED"/>
            </intent-filter>
        </receiver>
    </application>
</manifest>

//====================================================================
//                          TIRAR FOTO
//====================================================================
@Override
protected void onCreate(Bundle savedInstanceState) {
      //executar click para tirar foto
      ImageButton button = (ImageButton) findViewById(R.id.formulario_botao);
      button.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {

                      Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                      String caminhoFoto = getExternalFilesDir(null)+"/"+System.currentTimeMillis()+".jpg";//inseire o aquivo na pasta
                      File file  = new File(caminhoFoto);
                      intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                      startActivityForResult(intent, 567);
                  }
              });
}
//methodo executado toda vez qye chamar statactivity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (resultCode){
            //verifica se a foto nao foi cancelada
            case  Activity.RESULT_OK:
                //verifica se o codigo para tirar a foto
                switch (requestCode){

                    case CODIGO_CAMERA:

                        // Abre foto da camera
                        ImageView foto = (ImageView) findViewById(R.id.formulario_photo);
                        Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
                            //reduz a imagem
                        Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                        foto.setImageBitmap(bitmapReduzido);
                        foto.setScaleType(ImageView.ScaleType.FIT_XY); //ocupa todo espaco disponivel
                        break;
                }
                break;
        }

    }

//====================================================================
//                          ListView
//====================================================================
package br.com.alura.alura.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import br.com.alura.alura.MainActivity;
import br.com.alura.alura.R;
import modelo.Aluno;

import java.util.List;

public class AlunosAdapter extends BaseAdapter {

    List<Aluno> alunos;
    Context context;

    public AlunosAdapter(Context context, List<Aluno> alunos) {
        this.alunos = alunos;
        this.context = context;
    }

    @Override
    public int getCount() {
        return alunos.size();
    }

    @Override
    public Object getItem(int position) {
        return alunos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return alunos.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Aluno aluno = alunos.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item, parent, false);
        }

        TextView item_nome = (TextView) view.findViewById(R.id.item_nome);
        item_nome.setText(aluno.toString());

        TextView item_telefone = (TextView) view.findViewById(R.id.item_telefone);
        item_telefone.setText(aluno.getTelefone());

        ImageView foto = (ImageView) view.findViewById(R.id.item_foto);
        String caminhoFoto = context.getExternalFilesDir(null) + "/" + System.currentTimeMillis() + ".jpg";

        return view;
    }
}

//====================================================================
//                          ANDROID
//====================================================================

//================================================================
//SEND DELETE FILES DIR DOWNLOAD
//=================================================================
//delete files
      File file = new File(Environment.getExternalStorageDirectory().getPath()+"/Download/");
      File filesInDirectory[] = file.listFiles();

      if (filesInDirectory != null) {
         for (int i = 0; i < filesInDirectory.length;i++) {
            filesInDirectory[i].delete();
         }
      }

//================================================================
//SEND POST USANDO API VOLLEY
//=================================================================

package broadcast.com.br.lausherprojectsv2.services;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.SocketException;
import java.util.HashMap;
import java.util.Map;

import broadcast.com.br.lausherprojectsv2.enums.ConfigEnum;
import broadcast.com.br.lausherprojectsv2.utils.ConsumoUtil;


public class SendPOSTHTTP {


   private static final String TAG = "HttpSendDataLog";
   private static Context context;

   public SendPOSTHTTP(Context context) {
      this.context = context;
   }

   public static void sendPOST() {


      StringRequest postRequest = new StringRequest(Request.Method.POST, ConfigEnum.URL_POST.getConfig(),
              new Response.Listener<String>() {
                 @Override
                 public void onResponse(String response) {
                    try {
                       JSONArray jsonarray = new JSONArray(response);
                       Log.e(TAG, "ArrayJson: "+jsonarray);

                       /*for (int i = 0; i < jsonarray.length(); i++) {
                          JSONObject jsonobject = jsonarray.getJSONObject(i);
                          String resp = jsonobject.getString("resp");
                          Log.e(TAG, "resp: " + resp);
                       }*/
                    } catch (JSONException e) {
                       e.printStackTrace();
                    }
                 }
              },
              new Response.ErrorListener() {
                 @Override
                 public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                 }
              }
      ) {
         @Override
         protected Map<String, String> getParams() {
            Map<String, String> params = new HashMap<>();
            try {
               // the POST parameters:
               params.put("ether_mac", ConsumoUtil.getMacAddrEther());
               params.put("wifi_mac", ConsumoUtil.getMacAddrWifi());
               params.put("ipv4", ConsumoUtil.IPV4());
               params.put("ipv6", ConsumoUtil.IPV6());
               params.put("cpu_perc", ConsumoUtil.CPUUsed2());
               params.put("mem_perc", ConsumoUtil.RamSize(context));
               params.put("disk_perc", ConsumoUtil.FreeDisk());

            } catch (SocketException e) {
               e.printStackTrace();
            }

            return params;
         }
      };

      Volley.newRequestQueue(context).add(postRequest);
   }


}
//================================================================
//GRADLE [MODULE:APP]
//================================================================

dependencies {
    implementation  'com.android.volley:volley:1.1.1'
}
//================================================================
//SEND POST
//=================================================================
package broadcast.com.br.lausherprojectsv2.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;

import broadcast.com.br.lausherprojectsv2.enums.ConfigEnum;

public class SendPOSTUtil extends AsyncTask<String, String, String> {

    private static String TAG = "SendDataLog";

    private Context context;

    public SendPOSTUtil(Context context){
        this.context = context;
    }

    @Override
    protected String doInBackground(String... strings) {

        InputStream is = null;
        String NewsData = "";

        try {

            ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("ether_mac", ConsumoUtil.getMacAddrEther()));
            nameValuePairs.add(new BasicNameValuePair("FreeDisk", ConsumoUtil.getMacAddrWifi()));
            nameValuePairs.add(new BasicNameValuePair("ipv4", ConsumoUtil.IPV4()));
            nameValuePairs.add(new BasicNameValuePair("cpu_perc", ConsumoUtil.CPUUsed()));
            nameValuePairs.add(new BasicNameValuePair("mem_perc", ConsumoUtil.RamSize(context)));
            nameValuePairs.add(new BasicNameValuePair("disk_perc", ConsumoUtil.FreeDisk()));

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(ConfigEnum.URL_POST.getConfig());
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);

            HttpEntity entity = response.getEntity();
            is = entity.getContent();

            NewsData = Stream2String(is);
            publishProgress(NewsData);
            Log.e(TAG, NewsData);
            
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String Stream2String(InputStream in) {

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line ="";
        String text ="";

        try {
            while((line=br.readLine())!=null) {
                text+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}
//================================================================
//FIM
//=================================================================

//====================================================================
//================ ATUALIZAR BACKGROUND IMAGEM VIA URL =============== 
//====================================================================
package broadcast.com.br.lausherprojectsv2.services;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.InputStream;

public class DownloadImageBackground extends AsyncTask<String, Void, Bitmap> {

   RelativeLayout relativeLayout;
   private String TAG = "DownloadImageBGLog";

   public DownloadImageBackground(RelativeLayout relativeLayout) {
      this.relativeLayout = relativeLayout;
   }

   @Override
   protected void onPreExecute() {
      super.onPreExecute();
      Log.e(TAG, "loading...");
   }

   @Override
   protected Bitmap doInBackground(String... urls) {

      String urldisplay = urls[0];
      Bitmap mIcon11 = null;

      try {
         InputStream in = new java.net.URL(urldisplay).openStream();
         mIcon11 = BitmapFactory.decodeStream(in);
      } catch (Exception e) {
         Log.e(TAG, e.getMessage());
         e.printStackTrace();
      }
      return mIcon11;
   }

   protected void onPostExecute(Bitmap result) {
      Drawable dr = new BitmapDrawable(result);
      relativeLayout.setBackgroundDrawable(dr);
      Log.e(TAG, "Sucesso !");
   }
}
//====================================================================
//MAINACTIVITY
RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);
public void changeImagemBG(int position) {
      new DownloadImageBackground(relativeLayout)
              .execute("https://divertenet.com.br/apps/images/" + 
                       backgrounds.get(position));
   
}

//====================================================================
//============================= FIM ================================== 
//====================================================================



//====================================================================
//==================== PASSAR LIST<OBJECT> POR INTENT ================ 
//====================================================================
 Intent intent = new Intent(context, MainHttpTestActivity.class);
               Bundle bundle = new Bundle();
               bundle.putSerializable("listApp", listapps);
               intent.putExtras(bundle);
               context.startActivity(intent);

//get list via intent
private ArrayList<AppsList> listsApps;
listsApps = (ArrayList<AppsList>) bundle.getSerializable("listApp");
//====================================================================
//============================== FIM ================================= 
//====================================================================



//====================================================================
//==================== EXECUTAR SCRIPT LINUX ========================= 
//====================================================================
//1-CRIAR ARQUVO .SH NO LINUX
//====================================================================
#!/bin/bash
echo "Contando as linhas ..."
sleep 5
grep 'cpu ' /proc/stat | awk '{usage=($2+$4)*100/($2+$4+$5)} END {print usage "%"}'
echo "Existem $LINHAS no arquivo."
//====================================================================
//2 - TORNAR O ARQUIVO EXECUTAVEL
//====================================================================
chmod 777 NOME_ARQUIVO.sh        
//====================================================================
//3 - CODIGO JAVA[EXECUTAR ARQUIVO]
//====================================================================
import com.sun.management.OperatingSystemMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import com.sun.management.OperatingSystemMXBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.management.MBeanServerConnection;

public class Teste {

    public static void main(String[] args) throws UnknownHostException, SocketException, IOException {
        String userHome = System.getProperty("user.dir");
        String cmd = Executar("sh "+userHome+"/src/teste/script.sh");//PATH PROJETO
        System.err.println(cmd);
        System.err.println(userHome);
    }

    public static String Executar(String cmd) {
        Process p=null;
        BufferedReader reader=null;
        
        try {

            p = Runtime.getRuntime().exec(cmd);
            reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            int read;
            char[] buffer = new char[4096];
            StringBuffer output = new StringBuffer();
            while ((read = reader.read(buffer)) > 0) {
                output.append(buffer, 0, read);
            }
            
            p.waitFor();
            return output.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }finally{
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(Teste.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
//====================================================================
//FIM
//====================================================================


//====================================================================
//INSTALLAR APP USANDO INTENT [GOOGLE PLAY]
//====================================================================
Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("market://details?id=nbtelecomtv.com.br.dev.tv"));
            startActivity(intent);

//====================================================================
//FIM
//====================================================================
//====================================================================
//COMANDO LINUX: 
//====================================================================
package broadcast.com.br.lausherprojectsv2.utils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TerminalUtil {

   public static String TAG = "TerminalLog";
   public static String Executar(String cmd) {
      try {

         Process process = Runtime.getRuntime().exec(cmd);
         BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
         int read;
         char[] buffer = new char[4096];
         StringBuffer output = new StringBuffer();
         while ((read = reader.read(buffer)) > 0) {
            output.append(buffer, 0, read);
         }
         reader.close();
         process.waitFor();
         return output.toString();
      } catch (IOException e) {
         throw new RuntimeException(e);
      } catch (InterruptedException e) {
         throw new RuntimeException(e);
      }
   }
}
//====================================================================
//FIM
//====================================================================

//====================================================================
//MAC ADDRESS: 
//====================================================================
public static String getMacAddrWifi() {

      try {
         List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
         for (NetworkInterface nif : all) {
            if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

            byte[] macBytes = nif.getHardwareAddress();
            if (macBytes == null) {
               return "";
            }

            StringBuilder res1 = new StringBuilder();
            for (byte b : macBytes) {
               String hex = Integer.toHexString(b & 0xFF);
               if (hex.length() == 1)
                  hex = "0".concat(hex);
               res1.append(hex.concat(":"));
            }

            if (res1.length() > 0) {
               res1.deleteCharAt(res1.length() - 1);
            }
            return res1.toString();
         }
      } catch (Exception ex) {
      }
      return "";
   }

   public static String getMacAddrEther() {

      try {
         List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
         for (NetworkInterface nif : all) {
            if (!nif.getName().equalsIgnoreCase("eth0")) continue;

            byte[] macBytes = nif.getHardwareAddress();
            if (macBytes == null) {
               return "";
            }

            StringBuilder res1 = new StringBuilder();
            for (byte b : macBytes) {
               String hex = Integer.toHexString(b & 0xFF);
               if (hex.length() == 1)
                  hex = "0".concat(hex);
               res1.append(hex.concat(":"));
            }

            if (res1.length() > 0) {
               res1.deleteCharAt(res1.length() - 1);
            }
            return res1.toString();
         }
      } catch (Exception ex) {
      }
      return "";
   }
//====================================================================
//FIM 
//====================================================================

//====================================================================
//CARROUSSEL 
//====================================================================
//IMPLEMENT GRADLE
//====================================================================
implementation 'com.github.devlight:infinitecycleviewpager:1.0.2'
//====================================================================         
 //MAIN XML
 <?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:background="@drawable/fundo_divertenet3"
    android:layout_height="match_parent">


    <com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:icvp_interpolator="@android:anim/accelerate_decelerate_interpolator"
        app:icvp_center_page_scale_offset="30dp"
        app:icvp_max_page_scale="0.8"
        app:icvp_medium_scaled="true"
        app:icvp_min_page_scale="0.5"
        app:icvp_min_page_scale_offset="5dp"
        android:id="@+id/infiniteHorizontalCycle"
        app:icvp_scroll_duration="500"/>
</RelativeLayout>
//====================================================================
//ADAPTER_XML
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:padding="24dp"
    android:orientation="vertical">

    <ImageView
        android:layout_centerInParent="true"
        android:src="@drawable/ic_looke"
        android:id="@+id/imageViewCard"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</RelativeLayout>

//====================================================================
//CALSS ADAPTER
//====================================================================
package broadcast.com.br.lausherprojectsv2.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

import broadcast.com.br.lausherprojectsv2.R;

public class MyAdapterCardTest extends PagerAdapter {

   List<Integer> listImage;
   Context context;
   LayoutInflater layoutInflater;


   public MyAdapterCardTest(List<Integer> listImage, Context context){

      this.listImage = listImage;
      this.context = context;
      this.layoutInflater = LayoutInflater.from(context);
   }


   @Override
   public int getCount() {
      return listImage.size();
   }

   @Override
   public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
      return view.equals(object);
   }

   @Override
   public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
      container.removeView((View)object);
   }

   @NonNull
   @Override
   public Object instantiateItem(@NonNull ViewGroup container, int position) {

      View view = layoutInflater.inflate(R.layout.card_layout, container, false);
      ImageView imageView = (ImageView) view.findViewById(R.id.imageViewCard);
      imageView.setImageResource(listImage.get(position));
      container.addView(view);
      return view;
   }
}

//====================================================================
//CLASS PRINCIPAL
//====================================================================
package broadcast.com.br.lausherprojectsv2;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.gigamole.infinitecycleviewpager.HorizontalInfiniteCycleViewPager;
import java.util.ArrayList;
import java.util.List;
import broadcast.com.br.lausherprojectsv2.adapter.MyAdapterCardTest;

public class MainCarrousselActivity extends AppCompatActivity {

   MyAdapterCardTest myAdapter;
   List<Integer>lisImage = new ArrayList<>();

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_mai_carroussel);

      initData();

      HorizontalInfiniteCycleViewPager pager = (HorizontalInfiniteCycleViewPager)
              findViewById(R.id.infiniteHorizontalCycle);

      myAdapter = new MyAdapterCardTest(lisImage, getBaseContext());
      pager.setAdapter(myAdapter);
   }


   private void initData() {
      lisImage.add(R.drawable.ic_nb_telecom_tv);
      lisImage.add(R.drawable.ic_looke);
      lisImage.add(R.drawable.ic_watch_espn);
   }
}

//====================================================================
//FIM
//====================================================================
//====================================================================
//EXECUTAR TAKS A CADA X SEGUNDOS
//====================================================================
private ScheduledExecutorService scheduleTaskExecutor;
scheduleTaskExecutor = Executors.newScheduledThreadPool(5);
scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
         public void run() {

            Log.e(TAG, "TAKS: "+ Task.getTaks());
            textCPU.setText(Task.getTaks());
            //new HttpSendData().execute("http://wiki.moebius.com.br/fibra_externo/monitorar_tvbox/insert.php");
         }
      }, 0, 3, TimeUnit.SECONDS);

//====================================================================
//ScheduledExecutorService FIM
//====================================================================
//====================================================================
//ATUALIZAR DADOS A CADA X SEGUNDO [SERVICE]
//====================================================================
package broadcast.com.br.lausherprojectsv2.services;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import broadcast.com.br.lausherprojectsv2.model.Consumo;

public class UpdateCpuAndMemoryService extends Service {

   public static final int notify = 10000;  //interval between two services(Here Service run every 5 seconds)
   int count = 0;  //number of times service is display
   private Handler mHandler = new Handler();   //run on another Thread to avoid crash
   private Timer mTimer = null;    //timer handling

   @Override
   public IBinder onBind(Intent intent) {
      return null;
   }

   @Override
   public void onCreate() {
      if (mTimer != null) // Cancel if already existed
         mTimer.cancel();
      else
         mTimer = new Timer();   //recreate new
      mTimer.scheduleAtFixedRate(new TimeDisplay(), 10, notify);   //Schedule task
   }

   @Override
   public void onDestroy() {
      super.onDestroy();
      mTimer.cancel();    //For Cancel Timer
      Toast.makeText(this, "Service is Destroyed", Toast.LENGTH_SHORT).show();
   }

   //class TimeDisplay for handling task
   class TimeDisplay extends TimerTask {
      @Override
      public void run() {
         // run on another thread
         mHandler.post(new Runnable() {
            @Override
            public void run() {
               // display toast
               
               Toast.makeText(UpdateCpuAndMemoryService.this,
                       "CPU "+ Consumo.readCPUinfo(), Toast.LENGTH_LONG).show();
            }
         });

      }

   }
}
//====================================================================
//MAINCATIVUTY
//====================================================================
//start service
startService(new Intent(this, UpdateCpuAndMemoryService.class));
//====================================================================
//MANIFEST
//====================================================================
</application>
        .........
  <service android:name=".services.UpdateCpuAndMemoryService" android:enabled="true" android:exported="true"></service>
</application>

//====================================================================
//FIM
//====================================================================

//====================================================================
//SwipeRefreshLayout
//====================================================================
<android.support.v4.widget.SwipeRefreshLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:theme="@style/AppTheme.Spinner"
        android:id="@+id/swipRefresh">

    Component......
</android.support.v4.widget.SwipeRefreshLayout>


//implemtns instercafe
public class MainActivityTest extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener 
//instancia
swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipRefresh);
swipeRefreshLayout.setOnRefreshListener(this);
swipeRefreshLayout.setColorSchemeResources(R.color.colorSelected, R.color.coloGreen, R.color.colorBlack2);

 @Override
   public void onRefresh() {
      new HttpConnectionService().execute(url);
      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
            swipeRefreshLayout.setRefreshing(false);//finish
         }
      }, 3000);
   }



//====================================================================
//SwipeRefreshLayout - FIM
//====================================================================

//====================================================================
//PERMISSAO
//====================================================================
 if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
              PackageManager.PERMISSION_DENIED) {

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
         }
      }
//====================================================================
//FIM PERMISSAO
//====================================================================
//=======================================================================
//INSTALL APP USING INTENT
//=======================================================================
package nbtelecomtv.com.br.nbtelecom_allversion.adapter.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.BuildConfig;
import android.support.v4.content.FileProvider;

import java.io.File;

public class InstallAppUtil {

    private static Context context;
      private String location = Environment.getExternalStorageDirectory() + "/Download/";
    private String nameAPK = "tv-release.apk";
    public InstallAppUtil(Context context){
        this.context = context;
    }

    public static void openIntentInstall(String location, String nameAPK) throws Exception {

        File toInstall = new File(location, nameAPK);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

            Uri fileUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", toInstall);
            Intent intent = new Intent(Intent.ACTION_VIEW, fileUri);
            intent.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true);
            intent.setDataAndType(fileUri, "application/vnd.android" + ".package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);

        } else {

            Uri apkUri = Uri.fromFile(toInstall);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }
}

//=======================================================================
//CHANGE MANIFEST
//=======================================================================
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="store.nbtelecom.com.br.nbtelecomstore">

    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission
        android:name="android.permission.INSTALL_PACKAGES"
        tools:ignore="ProtectedPermissions" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">
        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
       
       <!-- Add provider -->
       <provider
                android:name="android.support.v4.content.FileProvider"
                android:authorities="${applicationId}.provider"
                android:exported="false"
                android:grantUriPermissions="true">
            <meta-data
                    android:name="android.support.FILE_PROVIDER_PATHS"
                    android:resource="@xml/provider_paths"/>
        </provider>
        <activity android:name=".InstallActivity"></activity>
    </application>
</manifest>

//=======================================================================
//CREATE XML [RES/XML/provider_paths.xml
//=======================================================================
<?xml version="1.0" encoding="utf-8"?>
<PreferenceScreen xmlns:android="http://schemas.android.com/apk/res/android">
    <!--external-path name="external_download" path="Download"/-->

    <external-path
            name="external_file"
            path="."/>
</PreferenceScreen>

//=======================================================================
//DONWLOAD
//=======================================================================
class DownloadApp extends AsyncTask<String, Integer, Boolean> {

      @Override
      protected void onPreExecute() {
         super.onPreExecute();

         bar = new ProgressDialog(MainActivity.this);
         bar.setCancelable(false);
         bar.setTitle("Downloading...");
         bar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
         bar.setIndeterminate(true);
         bar.setCanceledOnTouchOutside(false);
         bar.show();

      }

      @Override
      protected void onProgressUpdate(Integer... progress) {
         super.onProgressUpdate(progress);
         bar.setIndeterminate(false);
         bar.setMax(100);
         bar.setProgress(progress[0]);
         String msg = "";

         if (progress[0] > 99) {
            msg = "Finishing... ";
         } else {
            msg = "Downloading... " + progress[0] + "%";
         }

         bar.setMessage(msg);
      }

      @Override
      protected void onPostExecute(Boolean result) {
         super.onPostExecute(result);

         //install app
         bar.dismiss();

         if (result) {
            startActivity(new Intent(MainActivity.this, InstallActivity.class));

         } else {
            Toast.makeText(getApplicationContext(), "Error: Try Again", Toast.LENGTH_SHORT).show();
         }
      }

      @Override
      protected Boolean doInBackground(String... strings) {

         Boolean flag = false;

         try {

            URL url = new URL(strings[0]);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            int total_size = c.getContentLength();
            c.connect();


            File file = new File(PATH);
            file.mkdirs();
            File outputFile = new File(file, nameAPK);

            if (outputFile.exists()) {
               outputFile.delete();
            }

            FileOutputStream fos = new FileOutputStream(outputFile);
            InputStream is = c.getInputStream();

            byte[] buffer = new byte[1024];
            int len1 = 0;
            int per = 0;
            int downloaded = 0;

            while ((len1 = is.read(buffer)) != -1) {
               fos.write(buffer, 0, len1);
               downloaded += len1;
               per = (int) (downloaded * 100 / total_size);
               publishProgress(per);
            }

            fos.close();
            is.close();

            flag = true;
         } catch (Exception e) {
            Log.e(TAG, "Update Error: " + e.getMessage());
            flag = false;
         }

         return flag;
      }


      private void openFile(){

         File file = new File(PATH,nameAPK);
         Uri uri = FileProvider.getUriForFile(MainActivity.this, BuildConfig.APPLICATION_ID + ".provider",
                 file);
         Intent pdfOpenintent = new Intent(Intent.ACTION_VIEW);
         pdfOpenintent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
         pdfOpenintent.setDataAndType(uri, "application/.text");

         try {
            startActivity(pdfOpenintent);
         }
         catch (ActivityNotFoundException e) {
            Log.e(TAG, "Error "+e.getMessage());
         }
      }

      private void openIntentInstall() {

         Log.e(TAG, "PATH: "+PATH);
         File file = new File(PATH, nameAPK);
         String strPath ="/storage/emulated/0/Download/";
         Intent promptInstall = new Intent(Intent.ACTION_VIEW);
         promptInstall.setDataAndType(Uri.parse("file://"+strPath+""+nameAPK),"application/vnd.android.package-archive");
         startActivity(promptInstall);
      }

      private void openIntentInstall(String location) {

         File toInstall = new File(location, nameAPK);
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(getApplicationContext(),
                    BuildConfig.APPLICATION_ID + ".provider", toInstall);
            Intent intent = new Intent(Intent.ACTION_INSTALL_PACKAGE);
            intent.setData(apkUri);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(intent);
         } else {
            Uri apkUri = Uri.fromFile(toInstall);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
         }
      }
   }
   
//====================================================================
//READ FILE DIRECTORY 
//====================================================================
File file = new File(PATH);
      File afile[] = file.listFiles();
      Log.e(TAG, "Files "+afile.length);
//====================================================================
//OPEN GOOGLE PLAY INTENT
//====================================================================

Intent intent = new Intent(Intent.ACTION_VIEW);
intent.setData(Uri.parse("market://details?id=nbtelecomtv.com.br.dev.tv"));
startActivity(intent);

//====================================================================
//DOWNLOAD USING PROGRESSDIAOLIG + THREAD
//====================================================================
package store.nbtelecom.com.br.nbtelecomstore;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

   private ImageView imageView;
   private String nameFile = "app-debug.apk";
   private String url = "http://www.divertenet.com.br/apps/" + nameFile;
   private String url2 = "http://livroandroid.com.br/imgs/livro_android.png";
   ProgressDialog progressDialog;
   private String TAG = "DownloadAppLOG";

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);
      imageView = (ImageView) findViewById(R.id.icon_app);
      imageView.setOnClickListener(this);
   }

   @Override
   protected void onResume() {
      super.onResume();

      if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
              PackageManager.PERMISSION_DENIED) {

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
         }
      }
   }

   @Override
   public void onClick(View v) {

      switch (v.getId()) {
         case R.id.icon_app:
            new DownloadApp().execute(url2);
            break;
      }
   }

   @Override
   public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
      super.onRequestPermissionsResult(requestCode, permissions, grantResults);
      if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
         Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
         //resume tasks needing this permission
      }
   }


   class DownloadApp extends AsyncTask<String, Integer, Boolean> {


      Context context;

      private String strFile = "teste_goku.jpg";


      @Override
      protected void onPreExecute() {
         super.onPreExecute();

         progressDialog = new ProgressDialog(MainActivity.this);
         progressDialog.setMessage("Download...");
         progressDialog.setIndeterminate(false);
         progressDialog.setMax(100);
         progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
         progressDialog.show();

      }

      @Override
      protected void onProgressUpdate(Integer... progress) {
         super.onProgressUpdate(progress);
         progressDialog.setProgress(progress[0]);

      }

      @Override
      protected void onPostExecute(Boolean aBoolean) {
         super.onPostExecute(aBoolean);
         progressDialog.dismiss();

         //install app
      }

      @Override
      protected Boolean doInBackground(String... strings) {

         Boolean flag = false;
         String newFile = "app-debug.apk";

         try {

            URL url = new URL(strings[0]);
            URLConnection urlConnection = url.openConnection();
            int fileLenght = urlConnection.getContentLength();
            urlConnection.connect();


            String PATH = Environment.getExternalStorageDirectory().getPath();
            InputStream in = new BufferedInputStream(url.openStream());
            OutputStream out = new FileOutputStream(PATH+ "/" +newFile);


            byte[] buffer = new byte[1024];
            long total = 0;
            int count;
            int per;

            while ((count = in.read(buffer)) != -1) {

               total += count;
               per = (int) (total * 100 / fileLenght);
               publishProgress(per);
               out.write(buffer, 0, count);
            }

            out.flush();
            out.close();
            in.close();

            //OpenNewVersion(PATH);

            flag = true;
         } catch (Exception e) {
            Log.e(TAG, "Update Error: " + e.getMessage());
            flag = false;
         }
         return flag;
      }

      private void OpenNewVersion(String location) {

         Intent intent = new Intent(Intent.ACTION_VIEW);
         intent.setDataAndType(Uri.fromFile(new File(location + "app-debug.apk")),
                 "application/vnd.android.package-archive");
         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
         startActivity(intent);
      }


   }
}
//====================================================================
//FIM
//====================================================================


//====================================================================
//SQLITE
//====================================================================
package nbtelecomtv.com.br.nbtelecom_allversion.adapter.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBase extends SQLiteOpenHelper {

   public static final String NOME_BANCO = "nbtelecom.db";
   public static final String TABELA = " users";
   public static final String ID     = " id ";
   public static final String NAME   = " name ";
   public static final String SENHA  = " senha ";
   public static final String STATUS = " status ";
   public static final int VERSAO    = 13;00

   public DataBase(Context context){
      super(context, NOME_BANCO,null, VERSAO);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {

      String sql = "CREATE TABLE "+TABELA+"("
              + ID + " integer primary key autoincrement, "
              + NAME + " text, "
              + SENHA + " text, "
              + STATUS + " text "
              +")";

      db.execSQL(sql);
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

      db.execSQL("DROP TABLE IF EXISTS " + TABELA);
      onCreate(db);
   }
}
//====================================================================
//DAO
//====================================================================
package nbtelecomtv.com.br.nbtelecom_allversion.adapter.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import nbtelecomtv.com.br.nbtelecom_allversion.adapter.interfaces.GenericDomain;
import nbtelecomtv.com.br.nbtelecom_allversion.adapter.model.User;
import nbtelecomtv.com.br.nbtelecom_allversion.adapter.sqlite.DataBase;

public class UserDao implements GenericDomain<User> {

   private SQLiteDatabase db;
   private DataBase dataBase;
   private ContentValues contentValues;
   private Long result;
   private static final String TAG = "UserDao";
   public static boolean statusUser = false;

   public UserDao(Context ctx) {
      this.dataBase = new DataBase(ctx);
   }

   //connection db
   @Override
   public void save(User user) {

      try {
         db = dataBase.getWritableDatabase();
         contentValues = new ContentValues();
         contentValues.put(DataBase.NAME, user.getNome());
         contentValues.put(DataBase.SENHA, user.getSenha());
         contentValues.put(DataBase.STATUS, user.getStatus());

         result = db.insert(DataBase.TABELA, null, contentValues);
         db.close();

         Log.e(TAG, "Sucesso ");

      } catch (Exception e) {
         Log.e(TAG, "Erro ao inserir", e);
      }
   }

   @Override
   public void update(User user) {
   }

   @Override
   public void delete(String name) {

      String where = DataBase.NAME + " = " + name;
      db = dataBase.getReadableDatabase();
      db.delete(DataBase.TABELA, where, null);
      db.close();
   }

   @Override
   public Cursor carregaDados() {
      Cursor cursor;
      String[] campos = {dataBase.ID};
      db = dataBase.getReadableDatabase();
      cursor = db.query(dataBase.TABELA, campos, null, null, null, null, null, null);

      if (cursor != null) {
         cursor.moveToFirst();
      }

      db.close();

      return cursor;
   }

   @Override
   public void EnterAppAutomatically() {

      try{
         db = dataBase.getWritableDatabase();
         String[] columns = {DataBase.NAME, DataBase.STATUS};
         Cursor cursor = db.query(DataBase.TABELA, columns, null, null, null, null, null);

         String nome;
         String status;
         User user = null;

         while (cursor.moveToNext()) {
            nome = cursor.getString(0);
            status = cursor.getString(1);

            user = new User();
            user.setNome(nome);
            user.setStatus(status);
         }

         if (user.getNome().equals("user") && user.getStatus().equals("1")) {
            statusUser = true;
            Log.e(TAG, user.getNome() + "-" + user.getStatus() + " statua user " + statusUser);
         } else {
            statusUser = false;
            Log.e(TAG, "Error ");
         }
      }catch (NullPointerException e){
         Log.e(TAG, "Error "+e.getMessage());
      }
   }

   public void logStatus() {

      db = dataBase.getWritableDatabase();
      String[] columns = {DataBase.NAME, DataBase.STATUS};
      Cursor cursor = db.query(DataBase.TABELA, columns, null, null, null, null, null);
      String nome = "";
      String status = "";
      User user = null;

      while (cursor.moveToNext()) {
         nome = cursor.getString(0);
         status = cursor.getString(1);
      }

      Log.e(TAG, nome + "-" + status);
   }

   public static boolean isExistuser(Context context) {

      try{

         DataBase dataBase = new DataBase(context);
         SQLiteDatabase db = dataBase.getWritableDatabase();
         String[] columns = {DataBase.NAME, DataBase.STATUS};
         Cursor cursor = db.query(DataBase.TABELA, columns, null, null, null, null, null);

         String nome;
         String status;
         User user = null;

         while (cursor.moveToNext()) {
            nome = cursor.getString(0);
            status = cursor.getString(1);

            user = new User();
            user.setNome(nome);
            user.setStatus(status);
         }

         if (user.getNome().equals("user") && user.getStatus().equals("1")) {
            return  true;
         }
      }catch (NullPointerException e){
         Log.e(TAG, "Error "+e.getMessage());
      }
      return false;
   }


}

//====================================================================
//GENERIC
//====================================================================
package nbtelecomtv.com.br.nbtelecom_allversion.adapter.interfaces;

import android.database.Cursor;

import java.util.List;

public interface GenericDomain<T> {

   public void save(T t);
   public void update(T t);
   public void delete(String t);
   public Cursor carregaDados();
   public void EnterAppAutomatically();

}
//====================================================================
//CLASS LOGIN : [INSERT, UPDATE, DELETE]
//====================================================================
package nbtelecomtv.com.br.nbtelecom_allversion.adapter;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nbtelecomtv.com.br.nbtelecom_allversion.R;
import nbtelecomtv.com.br.nbtelecom_allversion.adapter.dao.UserDao;
import nbtelecomtv.com.br.nbtelecom_allversion.adapter.model.User;
import nbtelecomtv.com.br.nbtelecom_allversion.adapter.sqlite.DataBase;
import nbtelecomtv.com.br.nbtelecom_allversion.adapter.test.MainTestActivity;
import nbtelecomtv.com.br.nbtelecom_allversion.adapter.test.MainTestNavListView;
import nbtelecomtv.com.br.nbtelecom_allversion.adapter.test.MainTestUsingListViewAcitvity;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

   private static final String TAG = "LoginActivityLog";
   private Button btn_entrar;
   private EditText editTextLogin, editTextsenha;

   private UserDao userDao;
   private User user;
   private Cursor cursor;
   private List itemIds;


   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.login_activity);

      btn_entrar = (Button) findViewById(R.id.btn_entrar);
      btn_entrar.setOnClickListener(this);

      editTextLogin = (EditText) findViewById(R.id.editLogin);
      editTextsenha = (EditText) findViewById(R.id.editTSenha);

      user = new User();
      userDao = new UserDao(this);

   }

   @Override
   protected void onResume() {
      super.onResume();
      //read users DB
      userDao.EnterAppAutomatically();

      //Start app automatically
      if(userDao.statusUser){
         Intent intent = new Intent(getApplicationContext(), MainTestNavListView.class);
         startActivity(intent);
      }
   }

   @Override
   public void onClick(View v) {

      switch (v.getId()) {
         case R.id.btn_entrar:
            btn_entrar.setBackgroundColor(getResources().getColor(R.color.colorGreen2));
            new Handler().postDelayed(new Runnable() {
               @Override
               public void run() {
                  logar(editTextLogin.getText().toString(), editTextsenha.getText().toString(), "0");
               }
            }, 400);

            break;
      }
   }

   private void logar(String nome, String senha, String status) {

      String str_nome = nome.toLowerCase();
      String str_senha = senha.toLowerCase();

      if (str_nome.trim().equals("user") && str_senha.trim().equals("passnb1")) {

         //grava no banco
         user.setNome(str_nome);
         user.setSenha(str_senha);
         user.setStatus(status);//false
         userDao.save(user);

         Intent intent = new Intent(this, MainTestNavListView.class);
         startActivity(intent);

      } else {

         Toast.makeText(this, "Usario ou senha invalida", Toast.LENGTH_LONG).show();

         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               btn_entrar.setBackgroundColor(getResources().getColor(R.color.colorBlack2));
            }
         }, 900);
      }
   }

   public void fullScreen(){
      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(uiOptions);
         }
      }, 300);

   }
}
//====================================================================
//FIM SQLITE
//====================================================================
//====================================================================
//WEBSERVICE
//====================================================================
package broadcast.com.br.lausherprojectsv2;

import android.os.AsyncTask;
import android.util.Log;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class WebServices extends AsyncTask<String, String, String> {


   private String TAG="WebServiceLog";
   private List lista;

   @Override
   protected void onPreExecute() {
      super.onPreExecute();

      Log.e(TAG, "Loading...");
   }

   //ensere os dados
   @Override
   protected void onPostExecute(String s) {
      super.onPostExecute(s);

      if(lista.size()>0){
         for(int i=0;i<lista.size(); i++)
            Log.e(TAG, "List "+lista.get(i).toString());
      }else{
         Log.e(TAG, "Empty list ");
      }
   }

   @Override
   protected String doInBackground(String... strings) {

      JSONParser parser = new JSONParser();
      lista = new ArrayList();

      try{

         URL url = new URL(strings[0]);
         URLConnection urlConnection = url.openConnection();

         if(urlConnection.getURL()!= null){

            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;

            //read json get url
            while((inputLine = in.readLine()) != null) {

               Log.e(TAG, "Entrou no while" );

               JSONArray arrayJson = (JSONArray) parser.parse(inputLine);

               for(int i= 0;i < arrayJson.size(); i++) {

                  JSONObject jsonObject = (JSONObject) arrayJson.get(i);
                  String name  = (String) jsonObject.get("name");
                  lista.add(name);
               }
            }
         }

      }catch (Exception e){
         Log.e(TAG, "Error "+e.getMessage());
      }
      return null;
   }

   @Override
   protected void onProgressUpdate(String... values) {
      super.onProgressUpdate(values);
   }
}

//====================================================================
//GREDLE
//====================================================================
apply plugin: 'com.android.application'

android {
    compileSdkVersion 26

    defaultConfig {
        applicationId "nbtelecomtv.com.br.dev.tv"
        minSdkVersion 19
        targetSdkVersion 26
        versionCode 5
        versionName "1.1.3"
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
    implementation 'com.android.support:leanback-v17:26.+'
    implementation 'com.android.support:appcompat-v7:26.+'
    implementation 'com.android.support.constraint:constraint-layout:1.1.2'

    //noinspection GradleCompatible
    implementation 'com.android.support:cardview-v7:26.+'

    //design
    implementation 'com.android.support:design:26.+'

    implementation group: 'com.googlecode.json-simple', name: 'json-simple', version: '1.1'//ADD LIBS
}
//====================================================================
// FIM
//====================================================================

//OBS: intent-filter e responsavel por  iniciar a activity
//====================================================================
//                          BUTTON
//====================================================================
//ID DO BUTTON
//====================================================================
if(vewi.getId() == R.id.btn1)
    Log.i("LOG", "BUttin -1");
if(view.getId()==R.id.btn2)
    Log.i("LOG", "BUttin - 2")


//====================================================================
//                          ACTIVITY
//====================================================================
//CADA ACIVITY CRIADA DEVE SER OBRIGATORIOAMENE 
//ADD NO ANDROIDMANIFEST.XML
//EX: <activity android:name = ".MainActivity"/>
//====================================================================
//MENU
//====================================================================
//CRIAR ARQUIVO DO DORETORIO RES/MENU/MENU_MAIN.XML
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item android:id="@+id/action_search"
        android:icon="@drawable/ic_launcher_background"
        app:showAsAction="always"
        android:title="Menu-serach" />
</menu>
//====================================================================
//MAINACTIVITY ADD METHODOS - onCreateOptionsMenu
//====================================================================

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}

/*
always = indica que o botao semrep deve icar visivel
=======================================================================================
ifRoom = Mostra o botão na action bar se existir espaço, ou move ele automaticamente
para o menu action overflow caso não tenha. Muitas vezes essa é a
configuração adequada para manter a compatibilidade com diversos tipos
de dispositivos e também com telas na vertical e horizontal.
=======================================================================================
whitText = Mostra o título do botão ao lado do ícone, caso tenha espaço disponível
na action bar. Por exemplo, na horizontal existe mais espaço na action bar,
portanto é possível exibir o título opcionalmente.
=======================================================================================
co11apseActíonVíew = Esse atributo indica que uma view que geralmente é grande deve ser
contraída para exibir apenas o botão. Esse é 0 caso do botão de busca do
Android, que fica contraído, mas ao ser clicado se expande para o usuário
digitar o texto.
*/
//====================================================================
//MENU
//====================================================================
package com.example.ednei.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_serach){
            Toast.makeText(this, "Sraech", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_menu){
            Toast.makeText(this, "Menu", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_string){
            Toast.makeText(this, "String", Toast.LENGTH_LONG).show();
            return true;
        }

        if (id == R.id.action_refresh){
            Toast.makeText(this, "Refresh", Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

//====================================================================
//XML - MENU/MENU_XML
//====================================================================

<?xml version="1.0" encoding="utf-8"?>

<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item android:id="@+id/action_serach"
          android:title="Search"
          app:showAsAction="always"//sempre visivel
          app:actionViewClass="android.widget.SearchView"/>

    <item android:id="@+id/action_menu"
          android:title="Menu"
          app:showAsAction="always"/>

    <item android:id="@+id/action_string"
          android:title="String"
          app:showAsAction="always"/>

    <item android:id="@+id/action_refresh"
          android:icon="@mipmap/ic_launcher"
          android:title="Menu"
          app:showAsAction="never"/>
</menu>

//====================================================================
//SearchView
//ACTION BAR COM SERACH 
//====================================================================
<?xml version="1.0" encoding="utf-8"?>

<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item android:id="@+id/action_refresh"
          android:icon="@mipmap/ic_launcher"
          android:title="Menu"
          app:showAsAction="never"/>
</menu>

//====================================================================
package com.example.ednei.myapplication;

import android.app.ActionBar;
import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_serach);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(onSearch());
        return true;
    }

    private SearchView.OnQueryTextListener onSearch() {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //usuari faz a busca
                Toast.makeText(getApplicationContext(), "Texto "+ query, Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //mudou o texto
                return false;
            }
        };
    }
}


//====================================================================
//TAB VIEW(DEPORECIADO)
//====================================================================
package com.example.ednei.myapplication;

import android.provider.SearchRecentSuggestions;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;
import android.support.v7.app.ActionBar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       
       setContentView(R.layout.activity_main);
        ActionBar actionBar= getSupportActionBar();

        actionBar.setTitle("ActionBar Compat");

        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
        //cria a tab
        actionBar.addTab(actionBar.newTab().setText("TAb1 ")
            .setTabListener(new MyLabLinstener(this, 1)));
        actionBar.addTab(actionBar.newTab().setText("TAb 2")
                .setTabListener(new MyLabLinstener(this, 2)));
        actionBar.addTab(actionBar.newTab().setText("TAb 3")
                .setTabListener(new MyLabLinstener(this, 3)));
    }
}

//====================================================================

package com.example.ednei.myapplication;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

public class MyTabLinstener implements ActionBar.TabListener {

    private Context context;
    private int tabIndex;

    public MyLabLinstener(Context context, int indexTab) {
        this.context = context;
        this.tabIndex = indexTab;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        Toast.makeText(context, "Selecionou a tab"+ tabIndex, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}


//====================================================================
//PROGRESS DIALOG
//====================================================================

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String URLIMAGE = "http://livroandroid.com.br/imgs/livro_android.png";
    ProgressDialog dialog;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = (Button) findViewById(R.id.btnDowload);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dialog = ProgressDialog.show(this, "Exemplo", "Buscando imagem...",
                false, true);
        //download
        downloadImage(URLIMAGE);
    }

    //Thread para iniciar p dowload
    private void downloadImage(final String urlImage) {
        new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(urlImage);
                    InputStream in = url.openStream();
                    final Bitmap image = BitmapFactory.decodeStream(in);
                    in.close();
                    //atualizar
                    atualizarImage(image);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error ao dowload", Toast.LENGTH_LONG).show();
                }
            }
        }.start();
    }

    private void atualizarImage(final Bitmap image) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //fecha a janela de progress
                dialog.dismiss();
                ImageView imgView = (ImageView) findViewById(R.id.img);
                imgView.setImageBitmap(image);
            }
        });
    }

}

//====================================================================
//PROGRESS - BAR
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout 
xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.progressbar.MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:orientation="vertical"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="Progess Bar" />

        <ProgressBar
            android:id="@+id/barraDeProgresso"
            style="?android:attr/progressBarStyleHorizontal"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <Button
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Simular Tarefa"
            android:id="@+id/btnOK"/>

    </LinearLayout>
</android.support.constraint.ConstraintLayout>

//====================================================================

public class MainActivity extends AppCompatActivity {

    Button btn;
    ProgressBar progressBar;
    private final String TAG="LIvro";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar  = (ProgressBar) findViewById(R.id.barraDeProgresso);
        btn = (Button)findViewById(R.id.btnOK);

        //evento click
        btn.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        for(int i=0;i<100;i++){
                            final int progress = i;

                            //Atualiza a barra de progresso
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Log.i(TAG, ">>Progress: "+progress);
                                    progressBar.setProgress(progress);
                                }
                            });

                            try{
                                Thread.sleep(200);
                            }catch (Exception e){
                                Log.i(TAG, "Fim");
                            }
                        }
                    }
                }).start();
            }
        });
    }
}

//====================================================================
//LISTVIEW
//====================================================================
//1- CRIAR A LISTVIEW NO XML
//====================================================================

/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout 
xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.listview.MainActivity">

    <ListView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/listView"/>

    <View
        android:layout_width="match_parent"
        android:layout_height="1dp"
        android:background="@color/colorAccent"/>
</android.support.constraint.ConstraintLayout>
*/

//====================================================================
//2- CRIAR UM TEXTVIEW COM ID PARA INSERIR NA LISTVIEW
//OBS: CRIAR OUTRO ARQUIVO XML COM NOME: ADAPTER_SIMPLE.XML
//====================================================================

/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:orientation="vertical"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">

        <TextView
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/text"/>
    </LinearLayout>
</android.support.constraint.ConstraintLayout>*/

//====================================================================
//CLASS ADAPTER PARA CONTROLAR A LISTA - GERENCIAR A LISTA
//====================================================================
public class SimpleAdapter extends BaseAdapter {

    public String[] planetas = new String[]{"Marte","Jupter", "Venus", "Terra"};
    public Context context;

    public SimpleAdapter(Context context){
        super();
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.planetas.length;
    }

    @Override
    public Object getItem(int position) {
        return this.planetas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String planeta = planetas[position];
        View view = LayoutInflater.from(context).inflate(R.layout.adapter_simples, parent, false);
        TextView t = (TextView)view.findViewById(R.id.text);
        t.setText(planeta);
        return view;
    }
}

//====================================================================
//MAIN PRINCIPAL - EXECUTAR A LISTA
//====================================================================

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener  {

    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(new SimpleAdapter(this));
        listView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String s = (String)parent.getAdapter().getItem(position);
        Toast.makeText(getApplicationContext(), "Texto Selecionado: "+s+" pocisao: "+position, Toast.LENGTH_LONG).show();
    }
}


//====================================================================
//              ARRAY ADAPTER CUSTOMIZADO
//====================================================================
//1 - DENTRO DA ACTIVITY PRINCIPAL ADD LISTVIEW
//====================================================================
/*
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.adaptercustomizado.MainActivity">

    <ListView
        android:id="@+id/listView"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
</android.support.constraint.ConstraintLayout>
*/
//====================================================================
//2 - CRIAR CLASS MODEL PARA INSERCAO DOS DADOS
//====================================================================

public class Planeta {

    public String nome;
    public int img;

    public Planeta(String nome, int img){
        this.nome=nome;
        this.img =img;
    }

    public static List<Planeta> getPlanetas(){

        List<Planeta>planetas = new ArrayList<>();

        planetas.add(new Planeta("Terra", R.drawable.ic_terra));
        planetas.add(new Planeta("Marte", R.drawable.ic_marte));
        planetas.add(new Planeta("Jupter", R.drawable.ic_jupter));
        planetas.add(new Planeta("Venus", R.drawable.ic_venus));
        return planetas;
    }
}

//====================================================================
//3 - CRIAR CLASS ADPTER
//====================================================================

public class PlanetaAdapter extends BaseAdapter {

    private final List<Planeta> planetas;
    private final Context ctx;

    public PlanetaAdapter(Context ctx, List<Planeta> planetas){
        this.ctx = ctx;
        this.planetas = planetas;
    }

    @Override
    public int getCount() {
        return planetas !=null ? planetas.size(): 0;
    }

    @Override
    public Object getItem(int position) {
        return planetas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        View view = LayoutInflater.from(ctx).inflate(R.layout.adapter_planeta/*ACTIVITY COM OS DADOS */, parent, false);
        TextView t = (TextView) view.findViewById(R.id.tNomePlaneta);//ID DA ACTIVITY
        ImageView img = (ImageView) view.findViewById(R.id.imgPlaneta);//ID DA ACTIVITY

        //update values
        Planeta p =     planetas.get(position);
        t.setText(p.nome);
        img.setImageResource(p.img);
        return view ;
    }
}

//====================================================================
//4 - ACTYVITY PARA MANIPULAR A CLASS ADPTER
//====================================================================

/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="match_parent">
    <ImageView
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:id="@+id/imgPlaneta"/>
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/tNomePlaneta"/>
</android.support.constraint.ConstraintLayout>
*/

//====================================================================
//5 - CLASS PRINCIPAL PARA EXIBRI A LISTA CUSTOMIZADA
//====================================================================

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected static final String TAG="Livro";
    private ListView listView;
    private List<Planeta> planetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listView);
        planetas = Planeta.getPlanetas();
        listView.setAdapter(new PlanetaAdapter(this, planetas));
    }

    @Override
    public void onClick(View v) {

    }
}

//====================================================================
//                  VIEW PAGE
//====================================================================
//1 - CRIAR ACTIVITY COM VIEW PAGE ADICIONADO
//====================================================================

/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.viewpage.MainActivity">

    <android.support.v4.view.ViewPager
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/viewPage"/>

</android.support.constraint.ConstraintLayout>
*/

//====================================================================
//ACTIVITY COM IMA IMAGEM PARA SER PASSADO OS DADOS ÁRA CLASS ADAPTER
//====================================================================
/*
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/img"/>
</android.support.constraint.ConstraintLayout>
*/
//====================================================================
//CRIAR ADAPTER PARA RECUPERAR OS DADOS DA PAGINA
//====================================================================

public class AdapterImageViewPage extends PagerAdapter {
    private Context ctx;
    private int[] imagens;

    public AdapterImageViewPage(Context ctx, int[]imagens){
        this.ctx = ctx;
        this.imagens = imagens;
    }

    @Override
    public int getCount() {
        return imagens != null ? imagens.length : 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(this.ctx).inflate(R.layout.adapter_imagem, container, false);
        ImageView img = (ImageView) view.findViewById(R.id.img);
        img.setImageResource(imagens[position]);
        ((ViewGroup)container).addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object view) {
        super.destroyItem(container, position, view);
        ((ViewGroup)container).removeView((View)view);
    }
}

//====================================================================
//MAIN ACTIVITY PARA EXBIR OS DADOS
//====================================================================

public class MainActivity extends AppCompatActivity {

    private int[] imagens = {R.drawable.ic_jupter, R.drawable.ic_marte, R.drawable.ic_terra};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp = (ViewPager)findViewById(R.id.viewPage);
        vp.setAdapter(new AdapterImageViewPage(this, imagens));

        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Toast.makeText(getBaseContext(), "Imagem "+position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

//====================================================================
//          VERIFICAR INTERNET
//====================================================================
//DECLRAR NO ON RESUME
//====================================================================

IntentFilter intentFilter = new IntentFilter();
 intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
 registerReceiver(networkChangeReceiver, intentFilter);
   
//====================================================================
//FORA DO ONRESUME
//====================================================================
   private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
         Log.d(TAG, "Network connectivity change");

         if (ConnectionManager.checkInternetConnection(getApplicationContext()) == ConnectionManager.NETWORK_STATUS_NOT_CONNECTED) {

            Dialogs.showDialogConnectionNetwork(MainTestActivity.this, "Erro de Conexão",
                    "Sem conexão, ative sua internet");
            videoView.pause();
         }
      }
   };
//====================================================================
//CLASS DE CONNECTION
//====================================================================
public class ConnectionManager {

   public static int NETWORK_STATUS_NOT_CONNECTED = 0;
   public static int NETWORK_STATUS_CONNECTED = 1;

   public static int checkInternetConnection(Context context) {
      ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

      if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
              && cm.getActiveNetworkInfo().isConnected()) {
         return NETWORK_STATUS_CONNECTED;

      } else {
         return NETWORK_STATUS_NOT_CONNECTED;
      }
   }
}
//====================================================================
//CLASS DE DIALOG [ACESS WI FI SETTINGS]
//====================================================================
public static void showDialogConnectionNetwork(final Context ctx, String title, String msg){

      AlertDialog.Builder alertDialog  = new AlertDialog.Builder(ctx);
      alertDialog.setTitle(title);
      alertDialog.setMessage(msg);

      alertDialog.setPositiveButton("Ativar internet", new DialogInterface.OnClickListener() {

         public void onClick(DialogInterface dialog, int which) {
            Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
            ctx.startActivity(intent);
         }
      });

      AlertDialog alert = alertDialog.create();
      alert.show();
   }
//====================================================================
//          FRAGMENTS
//====================================================================
//1 - MAINACTIVITY -- EXTENDAR A FRAGMENTACTIVITY
//====================================================================
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fm = getSupportFragmentManager();
    }
}
//====================================================================
//3- CRIAR CLASS QUE EXTENDA A FRAGMENT
//====================================================================

public class Fragment1 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_1, container, false);
        return view;
    }
}

//====================================================================
//4- CRIAR LAYOUT COM OS DADOS DO FRAGMENT_1
//====================================================================
/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:tools="http://schemas.android.com/tools">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Fragment - 1"/>

</android.support.constraint.ConstraintLayout>*/
//====================================================================
//CRAIR LAYOUT COM TAG FRAGMENT
//====================================================================
/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.fragments.MainActivity">

   <fragment
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:id="@+id/frag"
       class="com.example.freta.fragments.Fragment1"
       tools:layout="@layout/layout_fragment_1"/>
</android.support.constraint.ConstraintLayout>*/

//====================================================================
//FRAGMENT DINAMICAMENTE
//====================================================================
//1 - EXTENDA A CLASS PRINCIPAL A FragmentActivity
//====================================================================
public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //add fragment dinamicamnete
        if(savedInstanceState ==null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            Fragment1 fm1 = new Fragment1();
            ft.add(R.id.layoutFrag,fm1, "Fragment1");
            ft.commit();
        }
    }
}
//====================================================================
//2 - CRIAR A CLASS FRAGEMNT1 EXTENDIDA A FRAGMENT
//====================================================================
public class Fragment1 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.layout_fragment_1, container, false);
        return view;
    }
}

//====================================================================
//3 - CRIAR O LAYOUT[layout_fragment_1] PARA ARMAZENAR OS DADOS DA FRAGEMTN
//====================================================================
/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    xmlns:tools="http://schemas.android.com/tools">
    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Fragment - 1"/>
</android.support.constraint.ConstraintLayout>*/

//====================================================================
//4 - CRIAR O LAYOUT PRINCIPAL ONDE OS TEXTVIEW SERA ADIONADO DENTRO 
//DO FRAMELAYOUT    
//====================================================================
/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.fragments.MainActivity">

   <FrameLayout
       android:layout_width="match_parent"
       android:layout_height="wrap_content"
       android:id="@+id/layoutFrag"><-- id usado, na class principal
   </FrameLayout>
    
</android.support.constraint.ConstraintLayout>*/


//=============================================================================
//          FRAGMENT + TABS + VIEW PAGE
//=============================================================================
//1- CRIAR A CLASS PRIMCIPAL ONDE OS DDOS SERA EXIDOS
//=============================================================================

public class MainActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ID VIEW PAGE [LAYOUT_PRICIPAL]
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        viewPager.setAdapter(new TabasAdapter(getSupportFragmentManager()));
        final ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(android.app.ActionBar.NAVIGATION_MODE_TABS);
        
        actionBar.addTab(actionBar.newTab().setText("Frag 1").setTabListener(new MytabListener(viewPager, 0)));
        actionBar.addTab(actionBar.newTab().setText("Frag 2").setTabListener(new MytabListener(viewPager, 1)));
        actionBar.addTab(actionBar.newTab().setText("Frag 3").setTabListener(new MytabListener(viewPager, 2)));

        //se o valor treocar de pagina atualiza a tab
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int idx) {
                actionBar.setSelectedNavigationItem(idx);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

//=============================================================================
//2 -CRIAR CLASS [MytabListener] RESPONSAVEL POR GERENCIAR A VIEW PAGER
//=============================================================================

public class MytabListener implements ActionBar.TabListener {
    private Context ctx;
    private int id;

    public MytabListener(ViewPager viewPager, int id) {
        this.ctx = ctx;
        this.id  = id;
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}

//=============================================================================
//3 - CRIAR A CLASS [TabasAdapter] RESPONSAVEL POR GERENCIAR OS FRAGMENTS 
//=============================================================================

public class TabasAdapter extends FragmentPagerAdapter {

    public TabasAdapter(FragmentManager supportFragmentManager) {
        super(supportFragmentManager);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new Fragment1();

            case 1:
                return new Fragment2();

            default:
                return new Fragment3();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}

//=============================================================================
// 4 - CRIAR OS FRAGEMTNS COM RESPECTIVOS LAYOUT COM  SEUS DADOS 
//=============================================================================

public class Fragment2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_fragment2, container, false);
        return view;
    }
}

//=============================================================================
//5 - LAYOUTS FRAGMENTS [activity_fragment2]
//=============================================================================
/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="match_parent">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="Fragment- 2"/>
</android.support.constraint.ConstraintLayout>*/

//=============================================================================
//6 - POR FIM A ACTIVITY PRIMCIPA ONDE GERENCIAR TOADAS ACTIVITYS
//=============================================================================

/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.fragmenttabspageview.MainActivity">

    <FrameLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <!-- VIEW PAGER -->
        <android.support.v4.view.ViewPager
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:id="@+id/viewPager"/>
    </FrameLayout>
</android.support.constraint.ConstraintLayout>*/


//=============================================================================
//      SALAVAR DADOS DE FRGMENTS AO ROTACIONAR CELULAR
//=============================================================================
//1 - MAINPRICNIPAL 
//=============================================================================

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(savedInstanceState==null){
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            //CLASS FRAGEMTNS
            Fragment1 frag1 = new Fragment1();
            ft.add(R.id.layout_fragment/*ID CRIADO NO LAYOUT PRINCIPAL DENTRO DA TAG FRAGMENT*/, frag1, "Fragement1");
        }
    }
}

//=============================================================================
//2  - CLASS FRAGMENT
//=============================================================================

public class Fragment1 extends Fragment {
    private int count;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_1,container, false );
        //recuera o estado da activity

        setRetainInstance(true);
        if(savedInstanceState!=null){
            count= savedInstanceState.getInt("count");
        }

        Button btn = (Button) view.findViewById(R.id.btnOK);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                Toast.makeText(getContext(), "Count: "+count, Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    //Salva o status
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Count", count);
    }
}

//=============================================================================
//3   - LAYOUT FRAGMENT
//=============================================================================

/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="match_parent">

<Button
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:id="@+id/btnOK"
    android:text="Button"/>
</android.support.constraint.ConstraintLayout>
*/

//=============================================================================
//4   - LAYOUT PRINCIPAL
//=============================================================================

/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.slavardadosfragments.MainActivity">

    <fragment
        android:id="@+id/layout_fragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        class="com.example.freta.slavardadosfragments.Fragment1"
        tools:layout="@layout/fragment_1"/>
</android.support.constraint.ConstraintLayout>*/

//=============================================================================
//                          THREADS
//=============================================================================
//  ENVIAR MESSAGE
//=============================================================================

public class MainActivity extends AppCompatActivity {

    protected static final int MENSSAGEM = 1;
    private Handler handler = new TesteHandler();
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btnEnviar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Message msg = new Message();
                msg.what = MENSSAGEM;
                handler.sendMessageDelayed(msg, 3000);
            }
        });
    }

    private class TesteHandler extends Handler{

            @Override
        public void handleMessage(Message msg) {
            //super.handleMessage(msg);
            switch (msg.what){
                case MENSSAGEM:
                    Toast.makeText(getBaseContext(), "Message chegou", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }
}

//=============================================================================
//ENVIAR MESSAGE - V2
//=============================================================================

public class MainActivity extends AppCompatActivity {

    protected static final int MENSSAGEM = 1;
    //private Handler handler = new TesteHandler();
    private Handler handler = new Handler();
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btnEnviar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplication(), "Menssagem chegou", Toast.LENGTH_LONG).show();
                    }
                }, 3000);
            }
        });
    }
}


//=============================================================================
//DOWLOAD IMG E ATUALIZAR VIEW
//=============================================================================
//CLASS PRICIPAL 
//=============================================================================

public class MainActivity extends AppCompatActivity {

    private static final String URL = "http://www.topimagens.com.br/imagens/imagens-mais-novas.jpg";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn = (Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
                dowloadImagem(URL);
            }
        });
    }

    //TREHAD PARA REALIZAR O LOWLOAD
    private void dowloadImagem(final String url) {
        new Thread() {
            @Override
            public void run() {
                try {
                    final Bitmap img = Download.downloadBitnap(url);
                    ataulizarImagem(img);
                } catch (Exception e) {
                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }.start();
    }

    //THREAD PARA ATUALIZAR VIEW
    private void ataulizarImagem(final Bitmap imagem) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.INVISIBLE);
                ImageView imgView = (ImageView) findViewById(R.id.img);
                imgView.setImageBitmap(imagem);
            }
        });
    }
}

//=============================================================================
//2- CLASS PARA MANIPTLAR DOWLOAD
//=============================================================================

public class Download {
    public static Bitmap downloadBitnap(String url) throws IOException {

        Bitmap bitmap = null;
        InputStream in = new URL(url).openStream();
        //converte inpoutStream do java
        bitmap = BitmapFactory.decodeStream(in);
        in.close();
        return bitmap;
    }
}

//=============================================================================
//3- XML - COM DADOS DA IMAGEM E PROGRESS BAR
//=============================================================================

/*
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.atualizarviewthread.MainActivity">

<FrameLayout
    android:layout_width="match_parent"
    android:layout_height="wrap_content">

    <Button
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/btn"
        android:text="Download"/>

    <ImageView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/img"/>

    <ProgressBar
        android:id="@+id/progressBar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="center"/>
</FrameLayout>

</android.support.constraint.ConstraintLayout>
*/
//=============================================================================
//4-ADD PERMISSAO AO MANIFEST
//=============================================================================
/*<uses-permission android:name="android.permission.INTERNET" />*/


//=============================================================================
//                      SPLASH SCREEN
//=============================================================================
//1 - CRIAR CLASS PARA MANIPURAR SPLASH SCREEN
//=============================================================================

public class SpIashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splashe_screen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getBaseContext(), MainActivity.class));
                //fecha activity
                finish();
            }
        }, 3000/*3 SEG DURACAO*/);
    }
}

//=============================================================================
//2 - LAYOUT SPLASH SCREEN
//=============================================================================

/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="match_parent"
    android:layout_height="match_parent">
    <ImageView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:id="@+id/img"
        android:src="@drawable/ic_bg"/>
</android.support.constraint.ConstraintLayout>*/

//=============================================================================
//3 - TROCA INCIALIZACAO DE ACTIVITY NO MANIFEST
//=============================================================================
/*<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.freta.splashsreen">

    <uses-permission android:name="android.permission.INTERNET" />
    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <!-- SpIashScreenActivity VAI CARREGR POR PRIMEIRO -->
        <activity android:name=".SpIashScreenActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <!-- ADD CLASS ACTIVITY -->
        <activity android:name=".MainActivity" android:label="@string/app_name" />
    </application>
</manifest>*/

//=============================================================================
//ASYNCtRHERAS
//=============================================================================
//1 - PRINCIPAL 
//=============================================================================

public class MainActivity extends AppCompatActivity {

    ImageView imageView;
    ProgressBar progressBar;
    Bitmap bitmap;

    private static final String URL = "http://www.topimagens.com.br/imagens/imagens-mais-novas.jpg";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.img);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

        //faz dlowload
        downloadImagem();
    }

    private void    downloadImagem() {
        DownloadFilesTask task = new DownloadFilesTask();
        task.execute();
    }


    private class DownloadFilesTask extends AsyncTask<Void, Void, Bitmap>
    {
        @Override
        protected Bitmap doInBackground(Void...params) {

            // Executa em segundo plano (background)
            // 0 retorno do tipo "BITMAP" é passado ao método onPostExecute()
            try{
                bitmap = Dowload.downLoadFile(URL);
            }catch (Exception e){
                Log.i("oInBackground", "Error "+e.getMessage());
            }
            return bitmap;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //executs na tread pricipal
            //util para mostrar progressDilog ou progressBAr
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            // Recebe o resultado do método doInBackground()
            // Executa na UI Thread e pode atualizar a view
            if(bitmap!=null){
                imageView.setImageBitmap(bitmap);
                progressBar.setVisibility(View.INVISIBLE);
            }
        }
    }

    public Context getContext(){return this;}
}

//=============================================================================
//2 - CLASS DOWLOAD RESPONSAVEL POR TRATAR O DOWLOAD 
//=============================================================================

public class Dowload {
    public static Bitmap downLoadFile(String url) throws IOException {

        Bitmap bitmap = null;
        InputStream in = new URL(url).openStream();
        //converte inpoutStream do java
        bitmap = BitmapFactory.decodeStream(in);
        in.close();
        return bitmap;
    }
}


//=============================================================================
//3 - LAYOUT ONDE SERA COLOCADO A IMAGEM 
//=============================================================================

/*
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.ansytaskesthread.MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical">

        <ImageView
            android:id="@+id/img"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />

        <ProgressBar
            android:id="@+id/progressBar"
            android:layout_width="wrap_content"
            android:layout_height="match_parent" />

    </LinearLayout>

</android.support.constraint.ConstraintLayout>
*/



//=============================================================================
//ERROR APPCOMPACT
//=============================================================================
//build.gradle
//=============================================================================
// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.1'

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        jcenter()

        /*ADD LINHA*/
        maven {
            url "https://maven.google.com"
        }
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}

//
//=============================================================================
//ERROR APPCOMPACTI 
//=============================================================================
apply plugin: 'com.android.application'

android {
    compileSdkVersion 27
    buildToolsVersion "27.0.3"
    defaultConfig {
        applicationId "acelerometro.br.com.dowloads"
        minSdkVersion 14
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
    compile fileTree(dir: 'libs', include: ['*.jar'])
    androidTestCompile('com.android.support.test.espresso:espresso-core:2.2.2', {
        exclude group: 'com.android.support', module: 'support-annotations'
    })
    /*ADD LINHA*/
    compile 'com.android.support:appcompat-v7:27.0.0'
    compile 'com.android.support.constraint:constraint-layout:1.0.2'
    testCompile 'junit:junit:4.12'
}
//=============================================================================
//GOOGLE MAP
//===================================================================================
//1-CRIAR PROJETO COM MENU
//===================================================================================
//2-DETRO DO PACONTE [ex:br.com.nomedoprojeto] PRINCIPAL ADD ACTIVITY GOOGLE MAPS
//===================================================================================
//VAI SER GERADO UM ATTIVITY GOOGLE MAPS
//===================================================================================

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

//===================================================================================
//SUBISTITUI OS SEGUINTES LINHAS
//===================================================================================

public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }
    
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}

//===================================================================================
//REMOVA O acitivity_maps.xml
//===================================================================================
//ADD FRAME LAYOUT NO ARQUIVO CONTENT_MAIN.XML
//===================================================================================
/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:layout_behavior="@string/appbar_scrolling_view_behavior"
    tools:context="localizadornb.br.com.localizadornb.MainActivity"
    tools:showIn="@layout/app_bar_main">

    <!-- ADD FrameLayout -->
    <FrameLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</android.support.constraint.ConstraintLayout>*/

//===================================================================================
//GERENCIAR OS FRAGAMNTOS DETNTRO DA APLICACAO
//===================================================================================
//DENTRO DA MAINACTIVITY ADD 
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager fragmentManager;
    .... 
} 
//===================================================================================
//DENTRO DO ONCREATE ADD O FRAGAMNET
//===================================================================================

//=========================================================================================
//ADD FRAGMENT
//=========================================================================================
//recuoera o fragment do masp
fragmentManager = getSupportFragmentManager();

//inicia a trancasao para add fragment ao activity
FragmentTransaction  transaction = fragmentManager.beginTransaction();

//add po fragament a minha activity
transaction.add(R.id.container /*ID DO CONTAINER.MAIN.XML*/, new MapsFragment()/*instancia da class*/, "MapsFragment");
        
//confirma a altercao
transaction.commitAllowingStateLoss();

//===================================================================================
//INSERIR OUTRO FRAGENT
//===================================================================================
//DENTRO DO PACOTE PRINCIPAL ADD NEW FRSGMENT [OBS:DESMARQUE AS OPCOES]
//COPEI O CODIGO DO MAPACTIVITY E MUNDE O NOME DA CLASS
//===================================================================================

public class ExemploProviderFragmentV1 extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener {

    private GoogleMap mMap;
    //localizar o provider
    private LocationManager locationManager;
    ... RESTANTE DO CODIGO
}

//===================================================================================
// ONFIGURAR PROJETOS
//===================================================================================
//CTRL+ALT=SHFIT +S
//atulizr gogle-play-service
//===================================================================================


//===================================================================================
//ADD MELHOR PROVIVER
//===================================================================================
//DENTRO  DO METODDO onMapReady DA CLASS ExemploProviderFragmentV1
//===================================================================================
public void onMapReady(GoogleMap googleMap) {

        try {
            mMap = googleMap;
            mMap.setOnMapClickListener(this);
            locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            //buscar melhor provider [wifi ativo com antena, e o gps]
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);


            mMap.setMyLocationEnabled(true);

            // Add a marker in Sydney and move the camera
            LatLng latlng = new LatLng(-22.9085345, -43.22107042);
            MarkerOptions marker = new MarkerOptions();
            marker.position(latlng);
            marker.title("Titulo");
            mMap.addMarker(marker);

            //move a camera ate o marker
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latlng));
        } catch (SecurityException ex) {
            Log.i(TAG, ex.getMessage());
        }
    }

//===================================================================================
//ADD EXIBRI FRAGEMTEN NA LISTA
//===================================================================================
//DENTRO DA PASTA MENU/ ACTITIVITY_MAIN_DRAWER.MXL, ADD MAS UM ITEM E SEU ID
//===================================================================================
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <group android:checkableBehavior="single">
        <item
            android:id="@+id/nav_exemplo_basico"
            android:icon="@drawable/ic_menu_camera"
            android:title="Import" />

        <item
            android:id="@+id/nav_exemplo_providerv1"
            android:icon="@drawable/ic_menu_camera"
            android:title="Import" />
    </group>
</menu>

//===================================================================================
//INSEIRIR FRAGEMTE DENTRO DA CLASSE PRINCIEPAL
//===================================================================================
//DENTRO DO MENTODO onNavigationItemSelected
//===================================================================================

@SuppressWarnings("StatementWithEmptyBody")
@Override
public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    switch (id) {
        case R.id.nav_exemplo_basico:
            showFragment(new MapsFragment(), "MapsFragment");
        break;
        
        case R.id.nav_exemplo_providerv1:
            showFragment(new ExemploProviderFragmentV1(), "ExemploProviderFragmentV1");
            break;
    }

    //feche o menu
    DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    drawer.closeDrawer(GravityCompat.START);
    return true;
}

//===================================================================================
//CHAMA OS FRAGEMTS 
//===================================================================================

public void showFragment(Fragment fragement, String name){
    //inicia a trancasao para add fragment ao activity
    FragmentTransaction transaction = fragmentManager.beginTransaction();
    //add po fragament a minha activity
    transaction.replace(R.id.container/*ID DO CONTAINER.MAIN.XML*/,fragement, name);
    //confirma a altercao
    transaction.commit();
}


//===================================================================================
//ENVIAR DADOS VIA GET
//===================================================================================

public class ServiceHttpPost extends AsyncTask<String , String, String>{

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    
    @Override
    protected String doInBackground(String... params) {
        
        String NewsData="";
        
        //
        try {

            URL url = new URL(params[0]);
            HttpClient httpCliente = new DefaultHttpClient();
            HttpResponse httpResponse = httpCliente.execute(new HttpGet(params[0]));
            
            InputStream in = httpResponse.getEntity().getContent();
            NewsData = Stream2String(in);
            
            publishProgress(NewsData);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
    
    
    @Override
    protected void onProgressUpdate(String... progress) {
        super.onProgressUpdate(progress);
        Log.i(TAG, "onProgressUpdate");
    }
    
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.i(TAG, "onPostExecute");
    }
    
    private String Stream2String(InputStream in) {
        
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String line ="";
        String text ="";
        
        try {
            while((line=br.readLine())!=null) {
                text+=line;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text;
    }
}

//===================================================================================
//LOCATION - MARCA LOCALIZACAO
//===================================================================================

//===================================================================================
//PERMISSAO
//===================================================================================

//<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
//<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
//<uses-permission android:name="android.permission.INTERNET" />
//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
//===================================================================================


public class MyLocationListener implements LocationListener{

    TextView txtLat, txtLng;
    Context ctx;
    
    public MyLocationListener(Context ctx, TextView txtLat, TextView txtLng) {
        
        this.ctx = ctx;
        this.txtLat = txtLat;
        this.txtLng = txtLng;
    }
    
    
    @Override
    public void onLocationChanged(Location location) {
        
        txtLat.setText("Lat: "+location.getLatitude());
        txtLng.setText("Lng: "+location.getLongitude());
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(this.ctx, "Mudou status "+provider.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {
        
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}

//===================================================================================
//ENVIAR LOCALIZACAO PA BANCO VIA JSON 
//===================================================================================

public class MyLocationListener implements LocationListener{

    TextView txtLat, txtLng;
    Context ctx;
    
    public MyLocationListener(Context ctx, TextView txtLat, TextView txtLng) {
        
        this.ctx = ctx;
        this.txtLat = txtLat;
        this.txtLng = txtLng;
    }
    
    
    @Override
    public void onLocationChanged(Location location) {
        
        String latStr = Double.toString(location.getLatitude());
        String lngStr = Double.toString(location.getLongitude());
        
        txtLat.setText(latStr);
        txtLng.setText(lngStr);
        
        try {
            lat = URLEncoder.encode(txtLat.getText().toString(), "utf-8");
            lng = URLEncoder.encode(txtLng.getText().toString(),"utf-8");
            new ServiceHttpPost().execute("http://wiki.moebius.com.br/fibra_externo/inserDadosPost.php?lat="+lat+"&lng="+lng);
            System.out.println("Sucesso");
        } catch (UnsupportedEncodingException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Toast.makeText(this.ctx, "Mudou status "+provider, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(String provider) {        
    }

    @Override
    public void onProviderDisabled(String provider) {
    }
}


//========================================================================
//NotifiactionHandler
//========================================================================
public class NotifiactionHandler {

    private Context ctx;

    public NotifiactionHandler(Context ctx){
        this.ctx = ctx;
    }

    public void notification(String title, String texto){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
        builder.setSmallIcon(R.drawable.ic_localizador);
        builder.setContentTitle(title)
                .setContentText(texto)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}

//========================================================================
//PROJTO LOCALIZADOR
//========================================================================


public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onPause() {
        super.onPause();
        new NotifiactionHandler(this.getContext());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMinZoomPreference(14);
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

    }

    @Override
    public void onLocationChanged(Location location) {

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


public class NotifiactionHandler {

    private Context ctx;

    public NotifiactionHandler(Context ctx){
        this.ctx = ctx;
    }

    public void notification(String title, String texto){

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx);
        builder.setSmallIcon(R.drawable.ic_localizador);
        builder.setContentTitle(title)
                .setContentText(texto)
                .setAutoCancel(true);

        NotificationManager manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

}


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transatation = fragmentManager.beginTransaction();
        transatation.add(R.id.map_container, new MapsActivity(), "MapsActivity");
        transatation.commit();
    }
}

<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="localizadornb.br.com.localizadorbnv2.MainActivity">

    <FrameLayout
        android:id="@+id/map_container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"></FrameLayout>
</android.support.constraint.ConstraintLayout>


//======================================================================================
//BATERIA
//=======================================================================================

public float  getMyBateriaStatus(){

    Intent batteryIntent = getActivity().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

    return ((float)level /(float) scale) * 100.0f;
}
//=======================================================================================
//CAOERA POSITION 
//=======================================================================================
package localizadornb.br.com.jogomaps;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        LatLng sydney = new LatLng(-34, 151);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        CameraPosition cameraBuilder = new CameraPosition.Builder().tilt(90).bearing(0).target(sydney).zoom(12).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraBuilder);
        mMap.moveCamera(cameraUpdate);
        
        // Add a marker in Sydney and move the camera
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}


//=======================================================================================
//VELOCIMETRO
//=======================================================================================
package localizadornb.br.com.velocimetro;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    private LocationManager locationManager;
    private TextView velocimetro, emVelocidade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        velocimetro = (TextView) findViewById(R.id.textVecimetro);
        velocimetro.setText("0.0 KM/H");
        emVelocidade = (TextView) findViewById(R.id.emVelocidade);
        emVelocidade.setText("0.0 KM/H");
    }

    @Override
    protected void onResume() {
        super.onResume();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {

        int speed = (int) ((location.getSpeed()*3600)/1000);

        if(speed > 0 )
            velocimetro.setText("Velocidade > 0 KM/H: "+String.valueOf(speed)+" km/h");
        if(speed > 5 )
            velocimetro.setText("Velocidade > 5 KM/H: "+String.valueOf(speed)+" km/h");
        if(speed > 10 )
            velocimetro.setText("Velocidade > 10 KM/H: "+String.valueOf(speed)+" km/h");
        else
            velocimetro.setText("Velocidade: 0.0 KM/H");

        //se estiver em velocidade
       if(location.hasSpeed())
           emVelocidade.setText("Velocidade: "+String.valueOf(speed)+" km/h");
       else
           emVelocidade.setText("Velocidade: 0.0 KM/H");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}


package novolocalizador.br.com.novolocalizadorv2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.BatteryManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.UnsupportedEncodingException;
import java.net.InterfaceAddress;
import java.net.URLEncoder;
import java.util.Locale;

import Threads.AsynctaskService;
import services.NotificationServices;

public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private static final String TAG = "MapsActivity";
    private String myNumberTepelephony="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMinZoomPreference(12);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LatLng rj = new LatLng(-22.9132525, -43.7261822);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(rj));

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.getActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
    }

    @Override
    public void onResume() {
        super.onResume();

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 5, this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onLocationChanged(Location location) {

        int speed = (int) ((location.getSpeed()*3600)/1000);

        //verifica se esta em movimento
        if(location.hasSpeed()) {
            if (speed > 5) {
                // Add a marker in Sydney and move the camera
                LatLng sydney = new LatLng(location.getLatitude(), location.getLongitude());
                mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

                try {

                    int level = (int) getMyBateriaStatus();
                    getMyTelephony();
                    String lat = Double.toString(location.getLatitude());
                    String lng = Double.toString(location.getLongitude());

                    String battery = URLEncoder.encode(Integer.toString(level) + "%", "utf-8");
                    lat = URLEncoder.encode(lat, "utf-8");
                    lng = URLEncoder.encode(lng, "utf-8");
                    String nome = URLEncoder.encode(getMyNumberTepelephony(), "utf-8");

                    String url = "http://wiki.moebius.com.br/fibra_externo/inserDadosPost.php?lat=" + lat + "&lng=" + lng + "&nome=" + nome + "&bateria=" + battery;
                    new AsynctaskService().execute(url);

                } catch (UnsupportedEncodingException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    public void  getMyTelephony(){
        TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.getActivity(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        setMyNumberTepelephony(telephonyManager.getLine1Number());
    }

    public void setMyNumberTepelephony(String myNumberTepelephony){
        this.myNumberTepelephony = myNumberTepelephony;
    }

    public String getMyNumberTepelephony(){
        return  this.myNumberTepelephony;
    }

    public float  getMyBateriaStatus(){

        Intent batteryIntent = getActivity().registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        return ((float)level /(float) scale) * 100.0f;
    }
}


//===========================================================================
//MEDIARRECORDING
//===========================================================================
package acelerometro.br.com.multmidia;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private Button btnPlay, btnStop, btnRecoding;
    private MediaRecorder mediaRecorder;
    private String pathSalve;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = (Button) findViewById(R.id.play);
        btnStop = (Button) findViewById(R.id.stop);
        btnRecoding = (Button) findViewById(R.id.recording);
        btnPlay.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        btnRecoding.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.play:
                Playng();
                break;
            case R.id.stop:
                mediaRecorder.stop();
                break;
            case R.id.recording:
                gravarAudio();
                break;
        }
    }

    private void gravarAudio() {
        pathSalve = Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ UUID.randomUUID().toString()+"audio_recording.3gp";
        setupMediaRecording();
        try{
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this, "Gravando...", Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Log.e(TAG, e.getMessage(), e);
        }
    }

    private void setupMediaRecording() {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(pathSalve);
    }

    public void Playng(){
        File f = Environment.getExternalStorageDirectory().getAbsoluteFile();
        try {

            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(pathSalve);
            mediaPlayer.prepare();

        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage(), e);
        }

        mediaPlayer.start();
        Toast.makeText(this, "Playing...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}


//===========================================================================
//BOTTOM NAVIGATION BAR
//===========================================================================
package fibras.nbtelecom.br.com.sistemafibras;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class BottomNavigation extends AppCompatActivity {

    private TextView mTextMessage;

        private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
                = new BottomNavigationView.OnNavigationItemSelectedListener() {

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        mTextMessage.setText(R.string.title_home);
                        return true;
                    case R.id.navigation_dashboard:
                        mTextMessage.setText(R.string.title_dashboard);
                        return true;
                    case R.id.navigation_notifications:
                        mTextMessage.setText(R.string.title_notifications);
                        return true;
                }
                return false;
            }
        };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_navigation);

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
}


//===========================================================================
//ADD DENTRO NO MAIN_LAYOUT
//===========================================================================


    <android.support.design.widget.BottomNavigationView
            android:id="@+id/navigation"
            android:layout_width="0dp"
            android:layout_height="wrap_content"
            android:layout_marginEnd="0dp"
            android:layout_marginStart="0dp"
            android:background="?android:attr/windowBackground"
            app:layout_constraintBottom_toBottomOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:menu="@menu/navigation"/>

//===========================================================================
//CRIAR MENU [@menu/navigation]
//===========================================================================

<?xml version="1.0" encoding="utf-8"?>

<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item android:id="@+id/action_serach"
          android:title="Search"/>

    <item android:id="@+id/action_menu"
          android:title="Menu"/>

    <item android:id="@+id/action_string"
          android:title="String"/>
</menu>

//===========================================================================
//ADD DEPENDECIA
//===========================================================================
compile 'com.android.support:design:26.1.0'

//===========================================================================
//Float BUtton
//===========================================================================

FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
fab.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
});

<android.support.design.widget.FloatingActionButton
    android:id="@+id/fab"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_gravity="bottom|end"
    android:layout_margin="@dimen/fab_margin"
    app:srcCompat="@android:drawable/ic_dialog_email"/>


//===========================================================================
//TOOLBAR --
//===========================================================================
//REMOVE ACTION BAR DO APP
//===========================================================================

<resources>
    <style name="AppTheme.NoActionBar" parent="Theme.AppCompat.Light.NoActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
    </style>
</resources>

//===========================================================================
//MUDAT THEMA PARA REMOVER ACTION BAR
//===========================================================================

<application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme.NoActionBar">
        <activity android:name=".MainActivity" android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

//===========================================================================
//DENTRO DO MAYNACTVITY    
//===========================================================================

toolbar = (Toolbar) findViewById(R.id.my_toolbar);
//add menu a toolbar
toolbar.inflateMenu(R.menu.menu_toolbar);
setSupportActionBar(toolbar);

//===========================================================================
<android.support.v7.widget.Toolbar
        android:id="@+id/my_toolbar"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:background="@color/colorPrimary"
        android:elevation="4dp"
        android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
        app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>

//===========================================================================
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item android:id="@+id/action_search"
        android:icon="@drawable/ic_refresh_black_24dp"
        app:showAsAction="always"
        android:title="" />
</menu>



//===========================================================================
//ADD BORDER EDITEXT
//===========================================================================
<?xml version="1.0" encoding="utf-8"?>
 <selector xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_height="wrap_content"
    android:layout_width="wrap_content">
    <item>
        <shape android:shape="rectangle">
            <solid android:color="#ffffff"/>
            <corners android:radius="5dp" />
            <stroke
                android:width="2dp"
                android:color="#949494"
                />
        </shape>
    </item>
</selector>

<EditText
 android:id="@+id/editText1"
 android:background="@layout/my_edit_text_border">
 
  //===========================================================================
//BUTTON + ICON LEFT
//===========================================================================
  <Button
    android:id="@+id/bSearch"
    android:layout_width="fill_parent"
    android:layout_height="wrap_content"
    android:padding="16dp"
    android:text="Search"
    android:drawableLeft="@android:drawable/ic_menu_search"
    android:textSize="24sp"/>
    
        
 //===========================================================================
//NAVEGACAO USANDO FRAGEMTNS
//===========================================================================

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fragments.FragmentMenu;
import fragments.NotificationFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener
{

    private FragmentManager fragmentManager, fragmentManagerMenu, fragmentManagerNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.container_map, new MapsActivity(), "MapsActivity");
        transaction.commitAllowingStateLoss();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_home:
                fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.container_map, new MapsActivity(), "MapsActivity");
                transaction.commitAllowingStateLoss();
                return true;

            case R.id.action_menu:
                fragmentManagerMenu = getSupportFragmentManager();
                FragmentTransaction t2 = fragmentManagerMenu.beginTransaction();
                t2.replace(R.id.container_map, new FragmentMenu(), "FragmentMenu");
                t2.commitAllowingStateLoss();
                return true;

            case R.id.action_notification:
                fragmentManagerNotification = getSupportFragmentManager();
                FragmentTransaction t3 = fragmentManagerMenu.beginTransaction();
                t3.replace(R.id.container_map, new NotificationFragment(), "NotificationFragment");
                t3.commitAllowingStateLoss();
                return true;
        }
        return false;
    }
}


//XML DO FRAGEMT
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="fragments.FragmentMenu">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:padding="12dp">

        <!-- TODO: Update blank fragment layout -->
        <TextView
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:text="@string/hello_blank_fragment" />

    </LinearLayout>
</FrameLayout>


//===========================================================================
//ADD MARKER VIA JSON MAP
//===========================================================================
package algrimsoromano.com.br.sistemafibras;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CameraPosition.Builder;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import intents.FormEditarElementoActivity;
import models.Markers;
import models.Polylines;
import utils.GenerateJSONURL;

public class MapsActivity extends SupportMapFragment implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private GoogleMap mMap;
    private String TAG ="MapsActivity";
    private List<Markers> listMarkers;
    private List<Polylines> listPolylines;
    private Markers markers;
    private String urlStr   = "https://appeste.000webhostapp.com/Map-Android/selectElem.php";
    private String urlPolys = "http://wiki.moebius.com.br/fibra_externo/selectCabo.php";
    private Marker marker;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(
            new CameraPosition.Builder()
            .target(new LatLng(-22.9132525,-43.7261822))
            .zoom(10)
            .bearing(0)
            .tilt(90)
            .build()));

        mMap.setOnMapLongClickListener(this);

        new ReaderJsonHttp().execute(urlStr);
        new ReaderPolylinesHttp().execute(urlPolys);
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("novo").draggable(true));
    }


    class ReaderJsonHttp extends AsyncTask<String, String, String> {

        @Override
        protected void onProgressUpdate(String... progress) {
            super.onProgressUpdate(progress);
            Log.i(TAG, "download....");
        }

        //evernto ocorre apos a execussao 
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            LatLng []latLng = new LatLng[listMarkers.size()];
            if(listMarkers.size() > 0){
                for(int i=0;i<listMarkers.size();i++){
                    latLng[i] = new LatLng(listMarkers.get(i).getLatitude(), listMarkers.get(i).getLongitude());
                    drawMarkers(latLng[i],listMarkers.get(i).getNome(),listMarkers.get(i).getCodigo(),  mMap);
                }
            }
        }


        private void drawMarkers(LatLng latLng, final String nome, final String codigo, final GoogleMap googleMap) {

            Marker marker = googleMap.addMarker(
                new MarkerOptions()
                .position(latLng)
                .title(codigo+"-"+nome)
                .draggable(true)
                );


            //evento clink no martker
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {

                    String lat = String.valueOf(marker.getPosition().latitude);
                    String lng = String.valueOf(marker.getPosition().longitude);
                    Intent intent = new Intent(getActivity(), FormEditarElementoActivity.class);
                    intent.putExtra("nome", marker.getTitle());
                    intent.putExtra("latitude", lat);
                    intent.putExtra("longitude",lng);
                    startActivity(intent);
                    return false;
                }
            });


            //updade marker
            googleMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
                @Override
                public void onMarkerDragStart(Marker marker) {
                        //Log.i(TAG, "onMarkerDragStart: "+marker.getPosition().latitude);
                }

                @Override
                public void onMarkerDrag(Marker marker) {

                    //Log.i(TAG, "onMarkerDrag: "+marker.getPosition().latitude);
                }

                @Override
                public void onMarkerDragEnd(Marker marker) {
                    //Log.i(TAG, "onMarkerDragEnd: "+marker.getPosition().latitude);
                }
            });
        }

        @Override
        protected String doInBackground(String... string) {

            listMarkers = new ArrayList<>();
            JSONParser parser = new JSONParser();

            try {

                URL url = new URL(string[0]);
                URLConnection urlConnection = url.openConnection();

                if(urlConnection.getURL()!=null){

                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;

                    //read json get url
                    while((inputLine = in.readLine())!=null) {

                        JSONArray arrayJson = (JSONArray) parser.parse(inputLine);

                        for(int i=0;i<arrayJson.size();i++) {

                            JSONObject jsonObject = (JSONObject) arrayJson.get(i);
                            String codigo  = (String) jsonObject.get("codigo");
                            String latitude  = (String) jsonObject.get("latitude");
                            String longitude = (String) jsonObject.get("longitude");
                            String nome = (String) jsonObject.get("nome");

                            listMarkers.add(new Markers(Double.parseDouble(latitude), Double.parseDouble(longitude), nome,
                                codigo));
                        }
                    }
                }

            }catch (MalformedURLException e) {
                Log.e(TAG, "MalformedURLException: " + e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }  catch (ParseException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
}

//===========================================================================
//POLYLINES 
//===========================================================================
PolylineOptions options = new PolylineOptions();
options.add(new LatLng(-22.94098140882,-43.243558108807), new LatLng(-22.73126084722601, -43.751180581748486));
options.width(5);
options.color(Color.RED);
options.clickable(true);

Polyline polyline = mMap.addPolyline(options);

//===========================================================================
//READER JSON [OBS: IMPORTAR JAR ORG.JSON.SIMPLE]
//===========================================================================
/*
    
    [
        {
            "codigo":"1",
            "points":[
                {"latitude":-22.861623719624,"longitude":-43.243558108807},
                {"latitude":-22.861623719624,"longitude":-43.582891412079},
                {"latitude":-22.849586931423,"longitude":-43.436354622245}
            ]
        },
        {
            "codigo":"2",
            "points":[
                {"latitude":-23.154048490005913,"longitude":-43.64713165909052},
                {"latitude":-22.73126084722601,"longitude":-43.751180581748486},
                {"latitude":-23.46134275226831,"longitude":-42.51584925008842}
            ]
        }
    ]
*/
//===========================================================================
// CLASS LER JSON CITADO ACIMA
//===========================================================================

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ReaderJSON {

    private static JSONParser parser = new JSONParser();

    public static JSONArray readJSON(String urlStr) throws IOException, ParseException {

        URL url = new URL(urlStr);
        URLConnection urlConnection = url.openConnection();
        String inputLine;

        if(urlConnection.getURL()!=null){
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            while((inputLine = in.readLine())!=null) {
                JSONArray arrayJson = (JSONArray) parser.parse(inputLine);
                return arrayJson;
            }
        }

        return null;
    }
}

//===========================================================================
// EXECUTA NO MAIN
//===========================================================================

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

public class Main {

    public static void main(String[] agrs) {

        JSONParser parser = new JSONParser();
        String url  = "https://appeste.000webhostapp.com/Map-Android/selectRotasCabos.php";

        try {

            JSONArray jsonArray = ReaderJSON.readJSON(url);
            for(Object obj : jsonArray){
                JSONObject objects = (JSONObject) obj;
                String codigo = (String) objects.get("codigo");
                JSONArray arr = (JSONArray) objects.get("points");

                for(Object objPoints : arr){

                    JSONObject objectPoints = (JSONObject) objPoints;
                    Double lat = (Double) objectPoints.get("latitude");
                    Double lng = (Double) objectPoints.get("longitude");
                    System.out.println(codigo+"["+lat+" , "+lng+"]");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}

//===========================================================================
// QRCODE
//===========================================================================




// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    
    repositories {
        google()
        jcenter()

    }
    dependencies {
        classpath 'com.android.tools.build:gradle:3.1.2'
        classpath 'com.google.gms:google-services:3.2.1'
        

        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        maven(){
            url "https://maven.google.com" //add linha
        }
    }
}

task clean(type: Delete) {
    delete rootProject.buildDir
}


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

    packagingOptions {
        exclude 'META-INF/DEPENDENCIES'
        exclude 'META-INF/LICENSE'
        exclude 'META-INF/LICENSE.txt'
        exclude 'META-INF/license.txt'
        exclude 'META-INF/NOTICE'
        exclude 'META-INF/NOTICE.txt'
        exclude 'META-INF/notice.txt'
        exclude 'META-INF/ASL2.0'
    }
    compileOptions {
        targetCompatibility 1.8
        sourceCompatibility 1.8
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

    //noinspection DuplicatePlatformClasses
    compile group: 'org.apache.httpcomponents', name: 'httpclient', version: '4.3.5'

    compile 'me.drakeet.materialdialog:library:1.2.2'
    compile 'com.squareup.picasso:picasso:2.5.2'

    compile 'com.google.zxing:core:3.2.1'
    compile 'com.journeyapps:zxing-android-embedded:3.2.0@aar'
    compile 'com.google.android.gms:play-services-vision:9.4.0+'//add linha


    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}
package intents;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.Result;

import algrimsoromano.com.br.sistemafibras.MainActivity;
import algrimsoromano.com.br.sistemafibras.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeActivity extends AppCompatActivity implements  ZXingScannerView.ResultHandler{

    private Toolbar toolbar;
    private ZXingScannerView zXingScannerView;

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrcode);


        toolbar = (Toolbar) findViewById(R.id.my_toolbar_qrcode);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        zXingScannerView = new ZXingScannerView(this);
        setContentView(zXingScannerView);

    }

    @Override
    public void onResume() {
        super.onResume();
        zXingScannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void handleResult(Result result) {

        zXingScannerView.resumeCameraPreview(this);

        Toast.makeText(getApplicationContext(),
                "Resultao "+result.getText(), Toast.LENGTH_SHORT).show();
    }
}
XML
<?xml version="1.0" encoding="utf-8"?>

<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:orientation="vertical"
    android:layout_width="fill_parent"
    android:layout_height="fill_parent">

    <android.support.v7.widget.Toolbar
        android:id="@+id/my_toolbar_qrcode"
        android:layout_width="match_parent"
        android:layout_height="60dp"
        android:background="@color/colorPrimary"
        android:elevation="4dp"
        android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
        app:popupTheme="@style/ThemeOverlay.AppCompat.Light"/>


</LinearLayout>

//=============================================================================
//Navigation View
//=============================================================================

//instalar dependencias no gradle
//apply plugin: 'com.android.application'

android {
    compileSdkVersion 26
    defaultConfig {
        applicationId "tv.nbtelecom.com.br.nbtelecom_tv"
        minSdkVersion 15
        targetSdkVersion 26
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
    implementation 'com.android.support:appcompat-v7:26.0.0-beta1'//add
    implementation 'com.android.support:design:26.0.0-beta1'//add
    implementation 'com.android.support.constraint:constraint-layout:1.1.2'
    testImplementation 'junit:junit:4.12'
    androidTestImplementation 'com.android.support.test:runner:1.0.2'
    androidTestImplementation 'com.android.support.test.espresso:espresso-core:3.0.2'
}

//=============================================================================
//ADD MENU
//=============================================================================
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">

    <item android:title="Opt - 1"
        android:id="@+id/nav_1"/>

    <item android:title="Opt - 2"
        android:id="@+id/nav_2"/>

    <item android:title="Opt - 3"
        android:id="@+id/nav_3"/>
</menu>

//=============================================================================
//LAYOUT
//=============================================================================
<?xml version="1.0" encoding="utf-8"?>
<android.support.v4.widget.DrawerLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:id="@+id/draw_layer" [identificador]
    tools:context=".MainActivity">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
        
            <!-- my toolbar -->
        <android.support.v7.widget.Toolbar
            android:id="@+id/my_toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/colorPrimary"
            android:elevation="4dp"
            android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
            app:popupTheme="@style/ThemeOverlay.AppCompat.Light" />
    </LinearLayout>

    <!-- NAVGATION VIEW [ficar fora fo layout] -->
    <android.support.design.widget.NavigationView
        android:id="@+id/navigation_view"
        android:layout_width="wrap_content"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        app:menu="@menu/menu_navigation_draw" />
</android.support.v4.widget.DrawerLayout>

package tv.nbtelecom.com.br.nbtelecom_tv;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

   package com.minhacasa.appminhacasa;

import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private ActionBarDrawerToggle mToggle;
    public NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.inflateMenu(R.menu.menu_toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layer);

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected( MenuItem item) {

        switch(item.getItemId()){
            case R.id.nav_casa:
                Toast.makeText(MainActivity.this, "MInha casa", Toast.LENGTH_LONG).show();
                break;

        }
        //close on click item
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {

    }
}


//===============================================
//EVENTO PRESSIONAR BUTAN BACK
//==============================================
  @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("canal", canal);
        intent.putExtra("user", user);
        intent.putExtra("senha", senha);
        Toast.makeText(this, "Canal: "+canal + "\nUser " + user + "\nSenha " + senha, Toast.LENGTH_LONG).show();
        //startActivity(intent);
    }

//===============================================
//EXIBIR OLHO NO INPUT
//==============================================
 <android.support.design.widget.TextInputLayout
            android:id="@+id/password_til"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:passwordToggleEnabled="true"
            app:passwordToggleTint="@color/colorPrimary">

        <EditText
            android:id="@+id/editTSenha"
            android:inputType="textPassword"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@drawable/border_input"
            android:paddingBottom="12dp"
            android:paddingLeft="5dp"
            android:paddingTop="12dp" />

        </android.support.design.widget.TextInputLayout>
        
//===============================================
//ORIENTATION 
//==============================================
                
                   @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.e(TAG,"LANDSCAPE");
        }
        else{
            Log.e(TAG,"PORTRAIT");
        }
    }

//manifest 
 <activity
            android:name=".ActivityTeste"
            android:configChanges="orientation|screenSize|keyboardHidden">

            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        
                   
//============================================================
//TELA LIGADA
//============================================================
 getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);     
    
    
//============================================================
//ABRIAR OUTRO APLICATIVOS DENTRO DE UMA INTENT
//============================================================
private void showSiteNb(String url){

    Uri uri = Uri.parse(url);
    Intent intent = new Intent(Intent.ACTION_SEND, uri);
    startActivity(intent);
}

//options aplicativos
public void showOprionApp(){
    Intent intent = new Intent(Intent.ACTION_CALL);
    intent.setData(Uri.parse("tel:02137220100" ));
    startActivity(intent);
}            
                    
Uri uriVideo = Uri.parse("http://www.youtube.com/watch?v=skCNLsrrtUw&feature=related");
Intent intent = new Intent(Intent.ACTION_VIEW, uriVideo);
startActivity(intent);
    
    //======================================================================
    //READ HTML 
    //====================================================================
    package com.example.ednei.teste;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import factory.ConnectionFactory;

public class LedActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_led1, btn_led2, btn_led3;
    private TextView textView;

    ConnectivityManager manager;
    NetworkInfo info;
    private String url = "http://192.168.0.16:8090";
    private final String TAG="LedActivity";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_led);


        btn_led1 = (Button) findViewById(R.id.btn_led1);
        btn_led2 = (Button) findViewById(R.id.btn_led2);
        btn_led3 = (Button) findViewById(R.id.btn_led3);

        btn_led1.setOnClickListener(this);
        btn_led2.setOnClickListener(this);

        solicita(url);

    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {

            case R.id.btn_led1:
                solicita(url + "/led1");
                break;

            case R.id.btn_led2:
                solicita(url + "/led2");
                break;

            case R.id.btn_led3:
                solicita(url + "/led3");
                break;
        }
    }

    public void solicita(String url) {

        manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        info = manager.getActiveNetworkInfo();

        if (info != null && info.isConnected()) {
            new DownloadWebPageTask().execute(url);
            Log.i("Result", "Sucesso!");
        } else {
            Log.i("Result", "Sem conexao");
        }

        Log.i("Solicita", url);

    }

    private class DownloadWebPageTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LedActivity.this);
            progressDialog.setMessage("Carregando...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            ConnectionFactory conexao = new ConnectionFactory();
            return conexao.GetArduino(strings[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (result != null) {
                if (result.contains("Led 1 - Ligado")){
                    btn_led1.setBackgroundResource(R.drawable.toogle_on);
                }

                if (result.contains("Led 1 - Desligado")){
                    btn_led1.setBackgroundResource(R.drawable.toogle_off);
                }

                if (result.contains("Led 2 - Ligado")){
                    btn_led2.setBackgroundResource(R.drawable.toogle_on);
                }

                if (result.contains("Led 2 - Desligado")){
                    btn_led2.setBackgroundResource(R.drawable.toogle_off);
                }

                if (result.contains("Led 3 - Ligado")){
                    btn_led3.setBackgroundResource(R.drawable.toogle_on);
                }

                if (result.contains("Led 3 - Desligado")){
                    btn_led3.setBackgroundResource(R.drawable.toogle_off);
                }

                Log.i("Result", result);
                progressDialog.dismiss();

            } else
                Log.e("LedActivity", "onPostExecute Erro !");

        }
    }
}
//=========================================================
//CODIGO ARDUINO
//=========================================================
    #include <SPI.h>
//#include <String.h>
#include <Ethernet.h>

byte mac[] = { 0x90, 0xA2, 0xDA, 0x00, 0x9B, 0x36 };
byte ip[] = { 192, 168, 0, 16 };
EthernetServer server(8090);

int led1 = 5;
int led2 = 6;
int led3 = 8;

String readString = String(30);

String statusLed;

void setup() {
  Ethernet.begin(mac, ip);
  pinMode(led1, OUTPUT);
  pinMode(led2, OUTPUT);
  pinMode(led3, OUTPUT);
}

void loop() {
  
  EthernetClient client = server.available();
  
  if(client) 
  {
    while(client.connected())
    {
      if(client.available()) 
      {
        char c = client.read();
        
        if(readString.length() < 30) {
          readString += (c);
        }
        
        if(c == '\n')
        {
          
          if(readString.indexOf("led1") >= 0) {
            digitalWrite(led1, !digitalRead(led1));
          }
          
          if(readString.indexOf("led2") >= 0) {
            digitalWrite(led2, !digitalRead(led2));
          }
          
          if(readString.indexOf("led3") >= 0) {
            digitalWrite(led3, !digitalRead(led3));
          }
          
          // cabeçalho http padrão
          client.println("HTTP/1.1 200 OK");
          client.println("Content-Type: text/html");
          client.println();
         
          client.println("<!doctype html>");
          client.println("<html>");
          client.println("<head>");
          client.println("<title>Tutorial</title>");
          client.println("<meta name=\"viewport\" content=\"width=320\">");
          client.println("<meta name=\"viewport\" content=\"width=device-width\">");
          client.println("<meta charset=\"utf-8\">");
          client.println("<meta name=\"viewport\" content=\"initial-scale=1.0, user-scalable=no\">");
          client.println("</head>");
          client.println("<body>");
          client.println("<center>");
          
          client.println("<font size=\"5\" face=\"verdana\" color=\"green\">Android</font>");
          client.println("<font size=\"3\" face=\"verdana\" color=\"red\"> & </font>");
          client.println("<font size=\"5\" face=\"verdana\" color=\"blue\">Arduino</font><br />");
          
          if(digitalRead(led1)) {
            statusLed = "Ligado";
          } else {
            statusLed = "Desligado";
          }
          client.println("<form action=\"led1\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Led 1 - "+statusLed+"</button>");
          client.println("</form> <br />");
          
          if(digitalRead(led2)) {
            statusLed = "Ligado";
          } else {
            statusLed = "Desligado";
          }
          client.println("<form action=\"led2\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Led 2 - "+statusLed+"</button>");
          client.println("</form> <br />");
          
          if(digitalRead(led3)) {
            statusLed = "Ligado";
          } else {
            statusLed = "Desligado";
          }
          client.println("<form action=\"led3\" method=\"get\">");
          client.println("<button type=submit style=\"width:200px;\">Led 3 - "+statusLed+"</button>");
          client.println("</form> <br />");
          
          client.println("</center>");
          client.println("</body>");
          client.println("</html>");
          
          readString = "";
          
          client.stop();
        }
      }
      
    }
  }
  
}
    
// TAMANHO DA TELA
//MUDAR TAMNHO DO VIDEO
Display display = windowManager.getDefaultDisplay();
      DisplayMetrics metrics = new DisplayMetrics();
      display.getMetrics(metrics);
      screenWidht = metrics.widthPixels;
      screenHeight = metrics.heightPixels;

      videoView.getLayoutParams().width  = screenWidht/2;
      videoView.getLayoutParams().height = screenHeight;
      videoView.requestLayout();
                    
if(count <= 6){
            if (navigationView.getChildCount() > 0) {
               View childView = navigationView.getChildAt(0);
               try {
                  Method scroll = childView.getClass().getMethod("smoothScrollToPosition", int.class);
                  scroll.invoke(childView, 0);
               } catch (NoSuchMethodException |
                       SecurityException |
                       IllegalAccessException |
                       InvocationTargetException e) {
                  Log.d(TAG, "smoothScrollToPosition", e);
               }
            }
         }
    
    
    //exibe navigation view
   
   @Override
   public boolean onOptionsItemSelected(MenuItem item) {
      if(mToggle.onOptionsItemSelected(item)){
         return true;
      }
      return super.onOptionsItemSelected(item);
   }

//full
    public void fullScreen(){
      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(uiOptions);
         }
      }, 300);

   }
    
    //hide teclado automatic
    <activity
            android:name=".adapter.LoginActivity"
            android:windowSoftInputMode="stateHidden"
            android:screenOrientation="landscape" />
                
                
    //
        navigation view
                
         //lock DRAWLAYER
         drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
         
         //unlock
         mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    
   //full screen
      public void fullScreen() {
      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            int uiOptions = View.SYSTEM_UI_FLAG_IMMERSIVE | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(uiOptions);
         }
      }, 300);

   }

   private void hideSystemUI() {

      if (!ishideSystemUI) {
         View decorView = getWindow().getDecorView();
         decorView.setSystemUiVisibility(
                 View.SYSTEM_UI_FLAG_IMMERSIVE
                         | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                         | SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                         | SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                         // Hide the nav bar and status bar
                         | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                         | View.SYSTEM_UI_FLAG_FULLSCREEN);
         ishideSystemUI = true;
      }
   }

   private void showSystemUI() {

      View decorView = getWindow().getDecorView();
      if (ishideSystemUI) {
         decorView.setSystemUiVisibility(
                 View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                         | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                         | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
         ishideSystemUI = false;
      }
   }
    
    //change orientation
    
    //manisfest
    android:configChanges="orientation|screenSize|keyboardHidden"
    // java
    @Override
   public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);

      if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               actionBar.hide();
               isladscape = true;
               ajustScreenVideo(screenWidth, screenHeight);
               displayFullScreen();
               hideNavigationBar();

            }
         }, 300);

      } else {
         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               actionBar.show();
               isladscape = false;
               ajustScreenVideo(screenWidth / 2, screenHeight / 2);
               displayFullScreen();
            }
         }, 400);
      }
   }
    
    //change size video
   
    Display display = windowManager.getDefaultDisplay();
    DisplayMetrics metrics = new DisplayMetrics();
    display.getMetrics(metrics);
    screenWidth = metrics.widthPixels;
    screenHeight = metrics.heightPixels;

    public void ajustScreenVideo(final int width, final int height) {
        
      videoView.getLayoutParams().width = width;
      videoView.getLayoutParams().height = height;
      videoView.requestLayout();
   }
    
   //size view [RelativeLayout.Layoutparams]
    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) videoView.getLayoutParams();
         params.removeRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
         params.removeRule(RelativeLayout.ALIGN_PARENT_TOP);
    
//===========================================================================
//CLASS PRINCIPAL
//===========================================================================
package broadcast.com.br.broadcastproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BroadcastServices extends BroadcastReceiver {

   @Override
   public void onReceive(Context context, Intent intent) {
      Toast.makeText(context, "Broadicast", Toast.LENGTH_SHORT).show();
   }
}
//===========================================================================
//CLASS RECEIVER
//===========================================================================    
package broadcast.com.br.broadcastproject;

import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

   private Button btn;
   private static final String TAG="MainActivityLog";
   BroadcastServices receiver;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_main);

      btn = (Button) findViewById(R.id.btn);
      btn.setOnClickListener(this);

   }


   @Override
   public void onClick(View v) {

      Intent intent = new Intent(this, BroadcastServices.class);
      sendBroadcast(intent);
   }


}
    
//===========================================================================
//MANIFEST
//===========================================================================
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="broadcast.com.br.broadcastproject">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <activity android:name=".MainActivity">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

        <receiver android:name=".BroadcastServices"/>
    </application>

</manifest>


            
//BUFFER VIDEO VIEW
package nbtelecomtv.com.br.nbtelecom_allversion.adapter.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;
import android.widget.VideoView;


public class Video {


   private static Context ctx;
   private static  ProgressDialog progressDialog, dialog;
   private static  String TAG = "VideoLog";


   public Video(Context ctx){
      this.ctx = ctx;
   }

   public static void loaderVideo(final Context ctx){
      dialog = new ProgressDialog(ctx);
      dialog.setMessage("Carregando video...");
      dialog.setCancelable(true);
      dialog.show();
   }


   public static void prepareVideo(final String url, final VideoView videoView) {

      //trhead para atualizar
      progressDialog = new ProgressDialog(ctx);
      progressDialog.setMessage("Carregando video...");
      progressDialog.setCancelable(false);

      progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancelar", new DialogInterface.OnClickListener() {

         @Override
         public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
         }
      });

      progressDialog.show();
      playVideo(url, videoView);
   }

    
   public static void playVideo(final String url, final VideoView videoView) {

      try {

         videoView.setBackground(null);
         videoView.setVideoURI(Uri.parse(url));
         videoView.requestFocus();//starta o video
         videoView.start();

      } catch (Exception e) {
         Log.i(TAG, e.getMessage());
      }

      videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

         @Override
         public void onPrepared(MediaPlayer mp) {
            progressDialog.dismiss();
            Log.e(TAG, "Prepare video");

            mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
               @Override
               public boolean onInfo(MediaPlayer mp, int what, int extra) {
                  if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                     Log.e(TAG, "MEDIA_INFO_BUFFERING_START");
                     loaderVideo(ctx);
                  }


                  if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
                     Log.e(TAG, "MEDIA_INFO_BUFFERING_END");
                     dialog.dismiss();
                  }

                  return false;
               }
            });

            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
               @Override
               public boolean onError(MediaPlayer mp, int what, int extra) {
                  return false;
               }
            });
         }
      });
   }
}
//====================================================================
//                          LAUNCHER ANDROID
//====================================================================
//MANIFEST
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"

    package="broadcast.com.br.lausherprojectsv2">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher_nb"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <activity
            android:icon="@mipmap/ic_launcher_nb"
            android:label="@string/app_name"
            android:screenOrientation="landscape"
            android:name=".MainActivity"
            android:banner="@mipmap/ic_launcher_nb"
            android:launchMode="singleTask"
            android:stateNotNeeded="true">

            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.HOME" />
            </intent-filter>
        </activity>
    </application>
</manifest>

//====================================================================
//MAIN_ACTIVITY
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"

    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@color/colorBlack"
    android:padding="12dp"
    tools:context=".MainActivity">

    <GridView
        android:choiceMode="singleChoice"
        android:layout_centerInParent="true"
        android:horizontalSpacing="12dp"
        android:verticalSpacing="12dp"
        android:numColumns="4"
        android:id="@+id/gridview"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</RelativeLayout>

//====================================================================
//MY_SELECT
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<selector xmlns:android="http://schemas.android.com/apk/res/android" android:layout_width="wrap_content" android:layout_height="wrap_content">


    <item android:state_activated="false">
        <shape android:shape="rectangle">
            <solid android:color="@color/colorBlack2" />
            <corners android:radius="5dp" />
            <stroke android:width="2dp" android:color="@color/colorWhite" />
        </shape>
    </item>

    <item android:state_pressed="true">
        <shape android:shape="rectangle">
            <solid android:color="@color/colorBlack2" />
            <corners android:radius="5dp" />
            <stroke android:width="2dp" android:color="#949494" />
        </shape>
    </item>

    <item android:state_activated="true">
        <shape android:shape="rectangle">
            <solid android:color="@color/colorSelected" />
            <corners android:radius="5dp" />
            <stroke android:width="2dp" android:color="#949494" />
        </shape>
    </item>
</selector>


//====================================================================
//MAINACTIVITY
//====================================================================

package broadcast.com.br.lausherprojectsv2;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnKeyListener {

    private PackageManager manager;
    private List<AppDetail> apps;
    private GridView gridView;
    private int count = 0;
    private static String TAG = "MainActivityLog";
    private int[] chanels;
    private int positionItemBottom = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gridView = (GridView) findViewById(R.id.gridview);
        gridView.setOnKeyListener(this);

        apps = new ArrayList<AppDetail>();

        loadApps();
        loadListView();
        addClickListener();
    }

    private void loadApps() {
        manager = getPackageManager();
        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        for (ResolveInfo ri : availableActivities) {
            AppDetail app = new AppDetail();
            app.label = ri.loadLabel(manager);
            app.name = ri.activityInfo.packageName;
            app.icon = ri.activityInfo.loadIcon(manager);
            apps.add(app);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        chanels = new int[apps.size()];
    }

    private void loadListView() {

        ArrayAdapter<AppDetail> adapter = new ArrayAdapter<AppDetail>(this, R.layout.item_list, apps) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {

                if (convertView == null) {
                    convertView = getLayoutInflater().inflate(R.layout.item_list, null);
                }

                ImageView appIcon = (ImageView) convertView.findViewById(R.id.item_app_icon);
                appIcon.setImageDrawable(apps.get(position).icon);

                TextView appLabel = (TextView) convertView.findViewById(R.id.item_app_label);
                appLabel.setText(apps.get(position).label);

                return convertView;
            }
        };

        gridView.setAdapter(adapter);
        gridView.setItemChecked(0, true);
    }

    private void addClickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos, long id) {
                Intent i = manager.getLaunchIntentForPackage(apps.get(pos).name.toString());
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (event.getAction() == event.ACTION_DOWN) {
            //Right
            if (event.getKeyCode() == 22) {

                if (count >= 13)
                    return false;

                ++count;
                positionItemBottom = count;
                UtilKey.keyDownEvent(gridView, count);
                Log.e(TAG, "[RIGHT] Count: " + count);
            }

            //Bottom
            if(event.getKeyCode() == 20){

                if(positionItemBottom >=12)
                    return false;

                positionItemBottom += 4;
                count = positionItemBottom;
                UtilKey.keyDownEvent(gridView, positionItemBottom);
                Log.e(TAG, "[BOTTOM] positionItemBottom: " + positionItemBottom+" Count: "+count);
            }
        }

        if (event.getAction() == event.ACTION_UP) {

            if (event.getKeyCode() == 21) {
                if (count <= 0)
                    return false;

                --count;
                positionItemBottom = count;
                UtilKey.keyDownEvent(gridView, count);
                Log.e(TAG, "[UP] Count: " + count);
            }

            if(event.getKeyCode() == 19){

                if(positionItemBottom <=0)
                    return false;

                positionItemBottom -= 4;
                count = positionItemBottom;
                UtilKey.keyDownEvent(gridView, positionItemBottom);
                Log.e(TAG, "[BOTTOM] positionItemBottom: " + positionItemBottom+ " Count "+count);
            }
        }

        return false;
    }
}

//====================================================================
//                  FIM
//====================================================================
    
//====================================================================
// NAVIGATION REMOTE CONTROL [RIGHT-LEFT-TOP-BOTTOM]
//====================================================================

@Override
public boolean onKey(View v, int keyCode, KeyEvent event) {

        if (event.getAction() == event.ACTION_DOWN) {
            //Right
            if (event.getKeyCode() == 22) {

                if (count >= 13)
                    return false;

                ++count;
                positionItemBottom = count;
                UtilKey.keyDownEvent(gridView, count);
                Log.e(TAG, "[RIGHT] Count: " + count);
            }

            //Bottom
            if(event.getKeyCode() == 20){

                if(positionItemBottom >=12)
                    return false;

                positionItemBottom += 4;
                count = positionItemBottom;
                UtilKey.keyDownEvent(gridView, positionItemBottom);
                Log.e(TAG, "[BOTTOM] positionItemBottom: " + positionItemBottom+" Count: "+count);
            }
        }

        if (event.getAction() == event.ACTION_UP) {

            if (event.getKeyCode() == 21) {
                if (count <= 0)
                    return false;

                --count;
                positionItemBottom = count;
                UtilKey.keyDownEvent(gridView, count);
                Log.e(TAG, "[UP] Count: " + count);
            }

            if(event.getKeyCode() == 19){

                if(positionItemBottom <=0)
                    return false;

                positionItemBottom -= 4;
                count = positionItemBottom;
                UtilKey.keyDownEvent(gridView, positionItemBottom);
                Log.e(TAG, "[BOTTOM] positionItemBottom: " + positionItemBottom+ " Count "+count);
            }
        }

        return false;
}

//====================================================================
//                  FIM
//====================================================================
  
//====================================================================
//     VIDEO VIEW + LIST VIEW _ PROGRRESS BAR USING REMOTE CONTROL
//====================================================================
//====================================================================
//MAIN ACTIVITY XML
//==================================================================== 
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/draw_layer"
    android:orientation="vertical"
    android:layout_width="match_parent"
    android:layout_height="match_parent">


    <RelativeLayout
        android:id="@+id/relativeLayout"
        android:background="@color/colorBlack"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content">

        <android.support.v7.widget.Toolbar
            android:id="@+id/my_toolbar"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="@color/colorGreen2"
            android:elevation="4dp"
            android:theme="@style/ThemeOverlay.AppCompat.ActionBar"
            app:popupTheme="@style/ThemeOverlay.AppCompat.Light" />

        <VideoView
            android:layout_centerInParent="true"
            android:id="@+id/videoView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"/>

        <ListView
            android:choiceMode="singleChoice"
            android:background="@color/colorBlack2"
            android:id="@+id/listview_"
            android:layout_width="250dp"
            android:layout_height="match_parent"/>

        <ProgressBar
            android:layout_centerInParent="true"
            android:id="@+id/progressBar"
            android:theme="@style/AppTheme.Spinner"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerHorizontal="true" />
    </RelativeLayout>
</LinearLayout>
//====================================================================
//ADAPTER_LIST
//====================================================================  
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <LinearLayout
        android:background="@drawable/my_selector"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        android:layout_marginBottom="1dp">


        <LinearLayout

            android:id="@+id/layout_list"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:orientation="horizontal"
            android:layout_marginBottom="1dp">

            <ImageView
                android:background="@color/colorWhite"
                android:id="@+id/ic_chanel"
                android:layout_width="40dp"
                android:layout_height="40dp"
                android:padding="4dp"
                android:src="@drawable/ic_band" />

            <TextView
                android:layout_marginTop="4dp"
                android:id="@+id/text_chanel"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginLeft="12dp"
                android:text="Band"
                android:textColor="@color/colorWhite"
                android:textSize="14dp" />
        </LinearLayout>

    </LinearLayout>
</android.support.constraint.ConstraintLayout>                
                

                    
//====================================================================
//MAIN ACTIVITY JAVA
//====================================================================                    
                    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_test_using_listview);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //============================================================================================
        listView = (ListView) findViewById(R.id.listview);
        chanels = Chanel.getPlanetas();
        listView.setAdapter(new ChanelAdapter(this));
        listView.setOnItemClickListener(this);
        listView.setSelection(0);
        //============================================================================================
        videoView = (VideoView) findViewById(R.id.videoView);

        //============================================================================================
        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layer);
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);

        toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        toolbar.inflateMenu(R.menu.toolbar);
        setSupportActionBar(toolbar);
        //============================================================================================
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);

        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layer);
        drawerLayout.setOnTouchListener(this);

        mToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.setDrawerListener(mToggle);
        mToggle.syncState();
        drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                if (isladscape) {
                    UtilConfigVideo.configSizeVideoLadscape(videoView);
                    ajustSiseVideo(screenWidth, screenHeight);
                }
            }

            @Override
            public void onDrawerStateChanged(int newState) {
            }
        });

        //============================================================================================
        spinner = (ProgressBar) findViewById(R.id.progressBar);
    }
    
//====================================================================
//CLASS ADPTER
//==================================================================== 
package nbtelecomtv.com.br.nbtelecom_allversion.adapter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import nbtelecomtv.com.br.nbtelecom_allversion.R;

public class ChanelAdapterTest extends BaseAdapter {

   private final String[] chanels;
   private final Context ctx;
   int[]imagens = {
           R.drawable.ic_person,
           R.mipmap.ic_logo_list,
           R.mipmap.ic_cnt_list,
           R.mipmap.ic_rede_tv,
           R.mipmap.ic_nbr,
           R.mipmap.ic_tv_saude,
           R.mipmap.ic_sbt,
           R.mipmap.ic_tv_brasil,
           R.mipmap.ic_tv_escola,
           R.mipmap.ic_record_news,
           R.mipmap.ic_globo_list,
           R.mipmap.ic_record2_list,
           R.mipmap.ic_band_list,
           R.mipmap.ic_cine_brasil,
           R.mipmap.ic_blender,
           R.mipmap.ic_sempre_um_papo,
           R.mipmap.ic_vasco_tv,
   };

   public ChanelAdapterTest(Context ctx){
      this.ctx = ctx;
       //ARRAY DEFINIDO NO XML
      this.chanels = ctx.getResources().getStringArray(R.array.chanels);
   }

   @Override
   public int getCount() {

      return chanels !=null ? chanels.length:  0;
   }

   @Override
   public Object getItem(int position) {
      return chanels[position];
   }

   @Override
   public long getItemId(int position) {
      return position;
   }

   @Override
   public View getView(int position, View convertView, ViewGroup parent) {

      View view = null;
      if(view == null){

         LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
         view = inflater.inflate(R.layout.main_adapter_listview,parent, false);

      }else{
         view = convertView;
      }

      //update values

      TextView textView = (TextView) view.findViewById(R.id.text_chanel);
      ImageView img = (ImageView) view.findViewById(R.id.ic_chanel);
      textView.setText(chanels[position]);
      img.setImageResource(imagens[position]);

      return view;
   }
}
//====================================================================    
    //KEY EVENT
//====================================================================    
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        //control right[show navigation view]
        if (event.getKeyCode() == 22) {
            listView.setVisibility(View.VISIBLE);
        }

        //control left [hide navigation view]
        if (event.getKeyCode() == 21) {
            listView.setVisibility(View.GONE);
        }

        //control down
        if (event.getKeyCode() == 20) {

            //para instrucao
            if (count >= 16)
                return false;
            else
                UtilHandler.handlerDwoChanel(this, listView, count);
            ++count;
        }

        if (event.getKeyCode() == 19) {

            //para instrucao
            if (count <= 0)
                return false;
            else
                UtilHandler.handlerUpChanel(this, listView, count);
            --count;
            Log.e(TAG, "[UP]Chanel Selecionado "+UtilHandler.getChanel()+"-"+count);
        }

        //control enter
        if (event.getKeyCode() == 66 || event.getKeyCode() == 23) {

            if (!UtilHandler.getChanel().equals("user")) {

               listView.setVisibility(View.GONE);
                if (!isladscape) {
                    UtilConfigVideo.configSizeVideoPortrait(videoView);
                    ajustSiseVideo(screenWidth, screenHeight);
                }

                new Video(this).prepareVideo(URl + "user=user&pass=passnb1&token=1530019742&s=stream" +
                        UtilHandler.getChanel() + ".m3u8", videoView, spinner);

                fullScreen();
            } else {
                Intent intent = new Intent(getApplicationContext(), UsuarioActivity.class);
                startActivity(intent);
            }
        }

        if(event.getKeyCode() == 67 || event.getKeyCode()== 4){
            finishAffinity();
        }

        return false;
    }
    
//ARRAY XML
 <string-array name="chanels">
        <item>Usuário</item>
        <item>NB Telecom</item>
        <item>Rede CNT</item>
        <item>Rede TV</item>
        <item>NBR</item>
        <item>TV Saúde</item>
        <item>SBT</item>
        <item>TV Brasil</item>
        <item>TV Escola</item>
        <item>Record News</item>
        <item>Globo</item>
        <item>Record</item>
        <item>BAND</item>
        <item>Cine Brasil</item>
        <item>Blender</item>
        <item>Sempre um Papo</item>
        <item>Vasco TV</item>
    </string-array>
     
 //CLASS CONTROL CHANGE SELECT ITEM LIST
 package nbtelecomtv.com.br.nbtelecom_allversion.adapter.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import nbtelecomtv.com.br.nbtelecom_allversion.R;
import nbtelecomtv.com.br.nbtelecom_allversion.adapter.model.Video;
import nbtelecomtv.com.br.nbtelecom_allversion.adapter.test.UsuarioActivity;

public class UtilHandler {


   private static int delay = 100;
   public static String itemChecado;
   public static int positionChanel;
   private static final String TAG = "UtilHandler";
   private static String chanel;

   //==========================================================================================
   //ListView
   //==========================================================================================

   public static void handlerDwoChanel(final Context context, final ListView listView, final int position) {

      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {

            listView.setItemChecked(position+1, true);//position 1
            listView.setSelection(position+1);//position 1

            itemChecado = listView.getSelectedItem().toString();

            //insertChanel(itemChecado);
             insertChanel2(position+1);
            Log.e(TAG, "Item [Down]: " + listView.getSelectedItem() + " Item checado "+getChanel());
         }
      }, delay);
   }

   public static void handlerUpChanel(final Context context, final ListView listView, final int position) {

      final View view = new View(context);
      new Handler().postDelayed(new Runnable() {
         @Override
         public void run() {
            listView.setItemChecked(position-1, true);//position 1
            listView.setSelection(position-1);//position 1

            if (position <= 8) {
               listView.setSelection(2);
               listView.smoothScrollToPosition(2);

               if(position<=4){
                  listView.setSelection(0);
                  listView.smoothScrollToPosition(0);
               }
            }

            itemChecado = listView.getSelectedItem().toString();
            String p = String.valueOf(position-1);
             insertChanel2(position-1);
            //insertChanel(itemChecado);
            Log.e(TAG, "Item [Down]: " + listView.getSelectedItem() + " Position: " +p+ " Item checado: "+getChanel());
         }
      }, delay);
   }    
}
//===================================================   
//PERMISSOA EM TEMPO DE EXECUSSAO
//===================================================   
 
 @Override
   protected void onResume() {
      super.onResume();

      if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==
              PackageManager.PERMISSION_DENIED){

         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
         }
      }
   }   
     
     

