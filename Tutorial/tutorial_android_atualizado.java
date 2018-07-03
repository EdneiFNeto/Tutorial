
//====================================================================
//                          ANDROID
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

    private ProgressDialog progressDialog;

    private final String videoURL = "http://189.45.13.225/stream.php.m3u8?user=user&pass=passnb1&" +
            "token=1530019742&s=stream50.m3u8";

    private VideoView videoView;
    private  ImageView imgImageView;
    private ImageButton btn_play, btn_pause, btn_full;
    private LinearLayout layout;
    private boolean removeImagem  = false;
    public NavigationView navigationView;
    private DrawerLayout drawerLayout;
    public String canalAtual="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        drawerLayout = (DrawerLayout) findViewById(R.id.draw_layer);

        navigationView.setNavigationItemSelectedListener(this);

        layout = (LinearLayout) findViewById(R.id.relative_layout_video);
        imgImageView = new ImageView(this);
        imgImageView.setImageResource(R.drawable.fundo_player);
        imgImageView.setBackgroundColor(getResources().getColor(R.color.colorBlack));

        LinearLayout.LayoutParams  params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        videoView = new VideoView(this);
        videoView.setPadding(0, 12, 0, 0);
        videoView.setLayoutParams(params);

        layout.addView(imgImageView);

        btn_play  = (ImageButton) findViewById(R.id.btn_play) ;
        btn_pause = (ImageButton) findViewById(R.id.btn_pause) ;
        btn_full = (ImageButton) findViewById(R.id.btn_full) ;

        btn_pause.setEnabled(false);
        btn_full.setEnabled(false);

        btn_play.setOnClickListener(this);
        btn_pause.setOnClickListener(this);
        btn_full.setOnClickListener(this);

        canalAtual = "rede_globo";

    }



    /* Play  video */
    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){

            case R.id.btn_play:
                playerVideo();
                break;
            case R.id.btn_pause:
                pauseVideo();
                break;

            case R.id.btn_full:
                startActivitys("full");
                break;
        }

    }

    /*
        Show videos click item list navigationView
     */
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_rede_globo:
                //startActivitys("rede_globo");
                canalAtual = "rede_globo";
                playerVideo();
                break;
            case R.id.nav_sbt:

                canalAtual = "sbt";

                //startActivitys("sbt");
                playerVideo();
                break;

        }

        //close on click item
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startActivitys(String extras){
        Intent intent = new Intent(getApplicationContext(), VideoFullScreenActivity.class);
        intent.putExtra("canais", extras );
        startActivity(intent);
    }


    private void pauseVideo() {
        videoView.pause();
        btn_play.setImageResource(R.drawable.ic_play);
        btn_pause.setImageResource(R.drawable.ic_pause_circle_green);
    }

    public void playerVideo(){

        if(!removeImagem){
            layout.removeView(imgImageView);
            removeImagem = true;
        }

        if(removeImagem)
            layout.removeView(videoView);

        layout.addView(videoView);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Carregando...");
        progressDialog.setCancelable(false);
        progressDialog.show();

        try{
            videoView.setVideoURI(Uri.parse(videoURL));
            videoView.requestFocus();//starta o video
            videoView.start();

        }
        catch (Exception e){
            Log.i("TestAppTv", e.getMessage());
        }

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                progressDialog.dismiss();
                btn_play.setImageResource(R.drawable.ic_play_circle_playng);
                btn_pause.setImageResource(R.drawable.ic_pause);
                btn_pause.setEnabled(true);
                btn_full.setEnabled(true);

                //define o canal atual
                Toast.makeText(getApplicationContext(), "Canal Atual "+canalAtual, Toast.LENGTH_LONG).show();
            }
        });
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


