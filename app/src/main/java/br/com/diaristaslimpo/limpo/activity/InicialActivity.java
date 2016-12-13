package br.com.diaristaslimpo.limpo.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;

import br.com.diaristaslimpo.limpo.R;
import br.com.diaristaslimpo.limpo.task.CalcularNotaDiaristaTask;
import br.com.diaristaslimpo.limpo.task.DownloadImageTask;
import br.com.diaristaslimpo.limpo.task.UploadImageTask;

public class InicialActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private Button foto;
    public static final int CODIGO_CAMERA = 567;
    private String idDiarista;
    private String caminhoFoto;
    private ImageView campoFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);

        campoFoto = (ImageView) findViewById(R.id.fotoDiarista);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        try {
            idDiarista = (String) bundle.get("idDiarista");
        }catch (RuntimeException e){
            e.printStackTrace();
        }

        new DownloadImageTask(this, (ImageView) findViewById(R.id.fotoDiarista)).execute(idDiarista);
        new CalcularNotaDiaristaTask(this, (TextView) findViewById(R.id.inicial_nota),
                (TextView) findViewById(R.id.inicial_numeroavaliacoes)).execute(idDiarista);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        findViewById(R.id.bt_solicitar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this,MinhasSolicitacoesActivity.class);
                intent.putExtra("idDiarista",idDiarista);
                startActivity(intent);
            }
        });

        findViewById(R.id.inicial_proximas_diarias).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InicialActivity.this,ProximasDiariasActivity.class);
                intent.putExtra("idDiarista",idDiarista);
                startActivity(intent);
            }
        });

        foto = (Button)findViewById(R.id.foto);
        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                caminhoFoto = getExternalFilesDir(null) + "/" + System.currentTimeMillis() +".jpg";
                File arquivoFoto = new File(caminhoFoto);
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(arquivoFoto));
                startActivityForResult(it, CODIGO_CAMERA);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CODIGO_CAMERA) {
                carregaImagem(caminhoFoto);
            }
        }
    }

    public void carregaImagem(String caminhoFoto) {
        if (caminhoFoto != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(caminhoFoto);
            ByteArrayOutputStream baos1 = new ByteArrayOutputStream();
            if(bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos1);
                byte[] b1 = baos1.toByteArray();

                String imagem = Base64.encodeToString(b1, Base64.DEFAULT);

                campoFoto.setImageBitmap(bitmap);
//                Bitmap bitmapReduzido = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
//                campoFoto.setImageBitmap(bitmapReduzido);
//                campoFoto.setScaleType(ImageView.ScaleType.FIT_XY);
//                campoFoto.setTag(caminhoFoto);

                new UploadImageTask(InicialActivity.this).execute(idDiarista, imagem);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_solicitacao) {
            Intent it = new Intent(this, MinhasSolicitacoesActivity.class);
            it.putExtra("idDiarista",idDiarista);
            startActivity(it);

        } else if (id == R.id.nav_proximas_diarias) {
            Intent it = new Intent(this, ProximasDiariasActivity.class);
            it.putExtra("idDiarista",idDiarista);
            startActivity(it);

        } else if (id == R.id.nav_dados) {
            Intent it = new Intent(this, FormularioDiaristaActivity.class);
            it.putExtra("idDiarista",idDiarista);
            startActivity(it);

        } else if(id == R.id.nav_endereco){
            Intent it = new Intent(this, CadastroEnderecoActivity.class);
            it.putExtra("idDiarista",idDiarista);
            startActivity(it);

        } else if(id == R.id.nav_servicos){
            Intent it = new Intent(this, SelecionarServicoActivity.class);
            it.putExtra("idDiarista",idDiarista);
            startActivity(it);

        } else if (id == R.id.nav_historico) {
            Intent it = new Intent(this, ListaHistoricoActivity.class);
            it.putExtra("idDiarista",idDiarista);
            startActivity(it);

        } else if (id == R.id.nav_configuracao) {
            Intent it = new Intent(this, ConfiguracaoActivity.class);
            it.putExtra("idDiarista",idDiarista);
            startActivity(it);

        } else if (id == R.id.nav_sair) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}