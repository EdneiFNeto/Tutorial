
//====================================================================
// 							ANDROID
//====================================================================
//OBS: intent-filter e responsavel por  iniciar a activity
//====================================================================
//							BUTTON
//====================================================================
//ID DO BUTTON
//====================================================================
if(vewi.getId() == R.id.btn1)
	Log.i("LOG", "BUttin -1");
if(view.getId()==R.id.btn2)
	Log.i("LOG", "BUttin - 2")


//====================================================================
//							ACTIVITY
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
//SERACH 
//SO BRECREVER METHDO DENTRO DA MAINACTIVITY  
//====================================================================
private SearchView.OnQueryTextListener onSerach(){
        return new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getApplicationContext(), "Toast search", Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        };
    }
//====================================================================
//DENTRO DO MENU_MAIN.XML ADD ITEM 
//====================================================================
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto">

    <item android:id="@+id/action_search"
        android:icon="@drawable/ic_launcher_foreground"
        app:showAsAction="always"
        android:title="Menu-serach"
        app:actionViewClass="android.widget.SearchView" />
</menu>

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
//				ARRAY ADAPTER CUSTOMIZADO
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
//					VIEW PAGE
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
//			FRAGMENTS
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
//			FRAGMENT + TABS + VIEW PAGE
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
//		SALAVAR DADOS DE FRGMENTS AO ROTACIONAR CELULAR
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
//							THREADS
//=============================================================================
//	ENVIAR MESSAGE
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
//						SPLASH SCREEN
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
//DOWNLOAD FILES  
//=============================================================================

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView imgView;
    private ProgressBar progressBar;
    private ProgressDialog progressDialog;
    private static final int DIALOG_DOWLOAD_PROGRESS=1;

    private static final String TAG = "Download";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView txt = (TextView) findViewById(R.id.textFiles);
        Button btn = (Button) findViewById(R.id.btnFile);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        imgView = (ImageView) findViewById(R.id.imgView);
        btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        Toast.makeText(getApplication(), "Clicou", Toast.LENGTH_SHORT).show();
        startDownload();
    }

    private void startDownload() {

        String url = "http://www.topimagens.com.br/imagens/imagens-mais-novas.jpg";
        EditText editText = (EditText) findViewById(R.id.textFiles);
        new DowloadFiles().execute(editText.getText().toString());
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id){

            case DIALOG_DOWLOAD_PROGRESS:
                progressDialog = new ProgressDialog(this);
                progressDialog.setMessage("Dowloading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setCancelable(true);
                progressDialog.show();
                return progressDialog;
            default:
                return null;
        }
    }

    class DowloadFiles extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... params) {
            int count;
            try {

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
            }catch (Exception e){
                Log.e(TAG, e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i(TAG, "Carregou!");
            dismissDialog(DIALOG_DOWLOAD_PROGRESS);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.i(TAG, "Carregando....");
            showDialog(DIALOG_DOWLOAD_PROGRESS);
        }

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);
            Log.i(TAG, values[0]);
            progressDialog.setProgress(Integer.parseInt(values[0]));
        }
    }
}

//====================================================================
//                          TOOLBAR
//====================================================================
//1- CRIAR LAYOUT TOOBAR
//====================================================================
/*
<?xml version="1.0" encoding="utf-8"?>
<android.support.v7.widget.Toolbar xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/toolbar"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="?attr/colorPrimary">

    <TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Hellow Toolbar"/>
</android.support.v7.widget.Toolbar>
*/
//====================================================================
//2 - MAIN ACTIVITY
//====================================================================

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TRANSFRMA TOOLBAR EM LAYOUT
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}

//====================================================================
//3 - ICLUDE TOOLBAR LAYOUT 
//====================================================================

/*
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.toolbar.MainActivity">

    <!-- onclude toolbar -->
    <include layout="@layout/include_toolbar" />

    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Hello World!"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent" />
</android.support.constraint.ConstraintLayout>
*/

//====================================================================
//3 -TOOLBAR BOTTOM
//====================================================================

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        toolbar.setNavigationIcon(R.drawable.ic_back);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //SAI O AP
                finish();F
            }
        });

        toolbar.inflateMenu(R.menu.menu_main);
    }

    //criar menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }
}



//====================================================================
//GOOGLE MAPS
//====================================================================
//1- NEW-->CRIAR GOOGLEMAPS ACTIVIT
//====================================================================
//2-- ARQIVOS GERADOS:
//====================================================================

/*<resources>
    <!--
    TODO: Before you run your application, you need a Google Maps API key.

    To get one, follow this link, follow the directions and press "Create" at the end:

    https://console.developers.google.com/flows/enableapi?apiid=maps_android_backend&keyType=CLIENT_SIDE_ANDROID&r=B0:3F:CD:5C:6A:62:88:14:F0:BA:C2:F1:B3:6D:D8:74:CD:19:9F:7E%3Bcom.example.freta.googlemaps

    You can also add your credentials to an existing key, using these values:

    Package name:
    B0:3F:CD:5C:6A:62:88:14:F0:BA:C2:F1:B3:6D:D8:74:CD:19:9F:7E

    SHA-1 certificate fingerprint:
    B0:3F:CD:5C:6A:62:88:14:F0:BA:C2:F1:B3:6D:D8:74:CD:19:9F:7E

    Alternatively, follow the directions here:
    https://developers.google.com/maps/documentation/android/start#get-key

    Once you have your key (it starts with "AIza"), replace the "google_maps_key"
    string in this file.
    -->
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">AIzaSyDpw8uYh-rez9iutDgny5nCkw1QwvOMmt4</string>
</resources>*/

//====================================================================
//3 - MANIFEST ADD OS CAMPOS
//====================================================================


/*<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.freta.googlemaps">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />

    <!-- OPEN GL -->
    <uses-feature
        android:glEsVersion="0x00020000"
        android:required="true" />

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
        
        <!-- CAHVE-->
        <meta-data
            android:name="com.google.android.geo.API_KEY"
            android:value="@string/google_maps_key" />

        <activity
            android:name=".MapsActivity"
            android:label="@string/title_activity_maps"></activity>
    </application>
</manifest>*/
//====================================================================
//4 - ACTIVITY
//====================================================================

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

//====================================================================
//5 - ADD FRAMELAYOUT COM ID NA LAYOUT PRINCIPAL  
//====================================================================

/*<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.freta.googlemaps.MainActivity">

    <!-- Map sera adionado aqui-->
    <FrameLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</android.support.constraint.ConstraintLayout>
*/
//====================================================================
//6 - ADD FRAGMENT MAP NO ACTIVITY PRINCIPAL[MAINACTOIVITY]  
//====================================================================
public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            fragmentManager =  getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.add(R.id.container/*id definido no layout pricipal*/, new MapsActivity(), "MapsActivity");
            ft.commit();
    }
}


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

public class VideosonverteMP3 extends Application {
    
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

//========================================================================
//IO
//========================================================================
//LER ARQUIVO
//========================================================================
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



//====================================================================
//                          JOGOS ANDROID
//====================================================================
package jogoandroid.com.br.jogoandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by freta on 09/02/2018.
 */

public class Game extends SurfaceView implements Runnable {

    private boolean isRunning = true;
    private final SurfaceHolder holder = getHolder();

    public Game(Context context) {
        super(context);
    }

    @Override
    public void run() {
        while (isRunning) {

            if (!holder.getSurface().isValid()) {
                //look canvas
                Canvas canvas = holder.lockCanvas();

                //draw game
                holder.unlockCanvasAndPost(canvas);
            }
        }
    }

    public void cancela() {
        this.isRunning = false;
    }


    public void pause() {
        this.isRunning = false;
    }

    public void iniciar() {
        this.isRunning = true;
    }
}


public class MainActivity extends AppCompatActivity {

    private Game game;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.container);
        game = new Game(this);

        //add game ao layout
        frameLayout.addView(game);
    }

    //resume game

    @Override
    protected void onResume() {
        super.onResume();
        game.iniciar();
    }

    //cancel thread when stage pause
    @Override
    protected void onPause() {
        super.onPause();
        game.pause();

    }
}

<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="jogoandroid.com.br.jogoandroid.MainActivity">

    <FrameLayout
        android:id="@+id/container"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
</android.support.constraint.ConstraintLayout>

<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="jogoandroid.com.br.jogoandroid">

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/AppTheme">

        <activity android:name=".MainActivity" android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>

//==================================================================================    
//SENSOR LUMINOSISADE
//==================================================================================    
public class LuminosidadeActivity extends AppCompatActivity implements SensorEventListener{

    private static final int TIPO_SENSOR = Sensor.TYPE_LIGHT;
    private SensorManager sensorManager;
    private Sensor sensor;
    private SeekBar progress;
    private TextView tValor;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_seekbar);

        progress = (SeekBar) findViewById(R.id.progress);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(TIPO_SENSOR);
        tValor = (TextView) findViewById(R.id.tValor);



        if(sensor != null){

            float max = sensor.getMaximumRange();
            progress.setMax((int) max);
        }

        else{
            Toast.makeText(getApplicationContext(), "Sensor nao disonivel", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(sensor!=null)
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        float valor = event.values[0];
        tValor.setText("Luz: "+valor);
        //((TextView) findViewById(R.id.tValor)).setText("Luz: "+valor);
        progress.setProgress((int) valor);
        Toast.makeText(getApplicationContext(), "Valor: "+valor, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

//====================================================================
//PERMISSAO EN TEMPO DE EXECUSAO
//====================================================================

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnGPS;
    private TextView textLat, textLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnGPS = (Button) findViewById(R.id.btn);
        btnGPS.setOnClickListener(this);
        textLat = (TextView) findViewById(R.id.latitude);
        textLng = (TextView) findViewById(R.id.longitude);
    }

    @Override
    public void onClick(View v) {
        pedirPermissao();
    }

    @Override
    protected void onResume() {
        super.onResume();
        pedirPermissao();
    }

    private void pedirPermissao() {

        //verifica se ja existe permissao
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }else
            configurarServio();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 1:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    configurarServio();
                }else{
                    Toast.makeText(this, "Nao vai funcionar", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    public void atualizar(Location location){

        Double lat = location.getAltitude();
        Double lng = location.getLongitude();
        textLat.setText(lat.toString());
        textLng.setText(lng.toString());
    }

    private void configurarServio() {

        try{
            LocationManager locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    atualizar(location);
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
            };

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        }catch (SecurityException  e){
            Log.e("LocationManager", e.getMessage(), e);
        }
    }
}

//=============================================================================
//ERROR APPCOMPACTI 
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
//INEROR OUTRO FRAGENT
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
//SEND GET ANDROID
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

//======================================================================================
//GRAVAR AUDIO
//======================================================================================
package gravaraudio.com.br.gravaraudio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private Button btnPlayRecorde, btnStopRecord, btnPlay, btnStop;
    private String pathSalve;
    private static final int REQUEST_PERMISSION_CODE = 1000;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!checkPermissionFromDev())
            requestPermissao();

        //init vew
        btnPlay = (Button) findViewById(R.id.btnPlay);
        btnStop = (Button) findViewById(R.id.btnStop);
        btnPlayRecorde = (Button) findViewById(R.id.btnStartRecorde);
        btnStopRecord = (Button) findViewById(R.id.btnStopRecorde);


        btnPlayRecorde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checkPermissionFromDev()) {

                    pathSalve = Environment.getExternalStorageDirectory()
                            .getAbsolutePath() + "/" + UUID.randomUUID().toString() + "audio_recorde.3gp";
                    setupMediaRecorder();
                    try {
                        mediaRecorder.prepare();
                        mediaRecorder.start();
                    } catch (IOException e) {
                        Log.e(TAG, e.getMessage(), e);
                    }

                    btnPlay.setEnabled(false);
                    btnStop.setEnabled(false);
                    Toast.makeText(MainActivity.this, "Gravando...", Toast.LENGTH_LONG).show();
                } else {
                    requestPermissao();
                }
            }
        });

        btnStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaRecorder.stop();
                btnStopRecord.setEnabled(false);
                btnPlay.setEnabled(true);
                btnPlayRecorde.setEnabled(true);
                btnStop.setEnabled(true);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnStop.setEnabled(true);
                btnStopRecord.setEnabled(false);
                btnPlayRecorde.setEnabled(false);

                mediaPlayer = new MediaPlayer();

                try {
                    mediaPlayer.setDataSource(pathSalve);
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
                Toast.makeText(MainActivity.this, "Play...", Toast.LENGTH_LONG).show();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                btnStopRecord.setEnabled(false);
                btnPlayRecorde.setEnabled(true);
                btnStop.setEnabled(false);
                btnPlay.setEnabled(true);

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    setupMediaRecorder();
                }
            }
        });
    }

    private void setupMediaRecorder() {

        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setOutputFile(pathSalve);


    }

    private void requestPermissao() {
        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.RECORD_AUDIO}, REQUEST_PERMISSION_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permissao granded", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permissao danied", Toast.LENGTH_SHORT).show();

                }
                break;
        }
    }

    private boolean checkPermissionFromDev() {

        int write_external_storage_result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int recorder_audio_result = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);

        return write_external_storage_result == PackageManager.PERMISSION_GRANTED &&
                recorder_audio_result == PackageManager.PERMISSION_GRANTED;
    }
}
