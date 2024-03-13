package com.lpg.disney_api

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class Fragment01 : Fragment() {

    lateinit var listaPersonajes:TextView
    lateinit var root: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_fragmen01, container, false)

        // Inflate the layout for this fragment
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listaPersonajes= root.findViewById(R.id.listaPersonajes)

        pedirPersonajes()
    }

    fun getRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.disneyapi.dev/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun pedirPersonajes() {

        CoroutineScope(Dispatchers.IO).launch {
           val llamada = getRetrofit().create(ServicioApi::class.java).conseguirPersonaje()
            if (llamada.isSuccessful) {
                Log.i("PEPE:", "OK")
                var llama = llamada.body()?.data
                listarPersonajes(llama)
            } else {
                Log.i("PEPE:", "NO")
            }
        }
    }

    private fun listarPersonajes(nameList: List<Data>?) {
        //stringBuilder es un método que te crea un String con los valores que le demos
        //val stringBuilder = StringBuilder()
                if(nameList != null) {
            //Si se quiere poner un scrollview, se necesita utilizar el runOnUiThread
            //Si no, se puede mandar al Stringbuilder, y lanzarlo desde otra función
            activity?.runOnUiThread {
                listaPersonajes.text="Fragment 1 \n"
                nameList.forEach {
                    listaPersonajes.append("Personaje: ${it.name} \n")
                }
            }
            //Esta sería la forma de hacerlo con stringbuilder
            /*
            nameList.forEach {
                    stringBuilder.append("Personaje: ${it.name} \n")
                }
            activity?.runOnUiThread {
                listaPersonajes.text= stringBuilder.toString()

            } */
        }
    }

}