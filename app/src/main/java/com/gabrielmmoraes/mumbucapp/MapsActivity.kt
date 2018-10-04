package com.gabrielmmoraes.mumbucapp

import android.animation.Animator
import android.animation.ObjectAnimator
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.mapa) as SupportMapFragment
        mapFragment.getMapAsync(this)

        esconderAjuda()
        definirEventosBotoes()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Inicializando com foco em Maricá
        val marica = LatLng(-22.9163,-42.822)
        mMap.moveCamera(CameraUpdateFactory.newLatLng(marica))

        // Definindo Zoom máximo e mínimo do Maps
        mMap.setMinZoomPreference(12.0f);
        mMap.setMaxZoomPreference(16.0f);

        // Definindo Listener para esconder botões quando há interação com o mapa
        mMap.setOnCameraMoveStartedListener { reason: Int ->
            if(reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE)
                esconderBotoes()
        }

        // Defindo listener para mostrar botões assim que o usuário tira o dedo do mapa
        mMap.setOnCameraIdleListener {
            mostrarBotoes()
        }
    }

    // Registra listeners nos botões para marcar estabelecimentos
    private fun definirEventosBotoes() {
        // Marca estabelecimentos alimentícios
        botaoAlimenticio.setOnClickListener {
            //MarcarAlimenticio()
        }

        // Marca estabelecimentos relacionados à fármacos
        botaoFarmacos.setOnClickListener {
            //MarcarFarmacos()
        }

        // Marca estabelecimentos de comércio em geral
        botaoComercio.setOnClickListener {
            //MarcarComercio()
        }

        // Chama função de para tratar o toque no botão de ajuda
        botaoAjuda.setOnTouchListener {v: View, m: MotionEvent ->
            tratarAçao(m)
            true
        }
    }

    // Função que trata ação de toque no botão de ajuda
    private fun tratarAçao(m: MotionEvent){
        // Variável que armazena o tipo de interação registrada
        val action = m.actionMasked

        // Switch que executa funções de acordo com a ação registrada
        when (action){
            // Ao apertar o botão, mostra a ajuda
            MotionEvent.ACTION_DOWN -> mostrarAjuda()

            // Ao soltar o botão, esconde a ajuda
            MotionEvent.ACTION_UP -> esconderAjuda()
        }
    }

    // Função que torna a descrição (ajuda) dos botões de marcadores visíveis
    private fun mostrarAjuda(){
        descAlimenticios.visibility = View.VISIBLE
        descFarmacos.visibility = View.VISIBLE
        descComercio.visibility = View.VISIBLE
    }

    // Função que torna a descrição (ajuda) dos botões de marcadores ocultas
    private fun esconderAjuda(){
        descAlimenticios.visibility = View.INVISIBLE
        descFarmacos.visibility = View.INVISIBLE
        descComercio.visibility = View.INVISIBLE
    }

    // Função que esconde botões de marcadores com animação de Fade Out
    private fun esconderBotoes(){
        botaoAlimenticio.fadeOut(TEMPO_ANIMACAO, {})
        botaoFarmacos.fadeOut(TEMPO_ANIMACAO, {})
        botaoComercio.fadeOut(TEMPO_ANIMACAO, {})
    }

    // Função que torna botões de marcadores visíveis com animação de Fade In
    private fun mostrarBotoes(){
        botaoAlimenticio.fadeIn(TEMPO_ANIMACAO, {})
        botaoFarmacos.fadeIn(TEMPO_ANIMACAO, {})
        botaoComercio.fadeIn(TEMPO_ANIMACAO, {})
    }

    // Função de Fade In
    private fun View.fadeIn(duration: Long, f:() -> Unit) {
        ObjectAnimator.ofFloat(this, "alpha", 0f, 1f).apply {
            setDuration(duration)
            onStart { f() }
            start()
        }
    }

    // Função de Fade Out
    private fun View.fadeOut(duration: Long, f:() -> Unit) {
        ObjectAnimator.ofFloat(this, "alpha", 1f, 0f).apply {
            setDuration(duration)
            onEnd { f() }
            start()
        }
    }

    // Override na função onAnimationStart do AnimatorListener
    private inline fun ObjectAnimator.onStart(crossinline func: () -> Unit) {
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) {}
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) { func() }
        })
    }

    // Override na função onAnimationEnd do AnimatorListener
    private inline fun ObjectAnimator.onEnd(crossinline func: () -> Unit) {
        addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {}
            override fun onAnimationEnd(animation: Animator?) { func() }
            override fun onAnimationCancel(animation: Animator?) {}
            override fun onAnimationStart(animation: Animator?) {}
        })
    }
}
