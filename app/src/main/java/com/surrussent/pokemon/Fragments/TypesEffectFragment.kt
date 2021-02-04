package com.surrussent.pokemon.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.surrussent.pokemon.API.HttpManager
import com.surrussent.pokemon.Adapter.TypeNAdapter
import com.surrussent.pokemon.Adapter.TypeSx1Adapter
import com.surrussent.pokemon.Adapter.TypeWx1Adapter
import com.surrussent.pokemon.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class TypesEffectFragment : Fragment(), AdapterView.OnItemSelectedListener {

    var type1 = arrayOf(
        "Normal",
        "Fighting",
        "Flying",
        "Poison",
        "Ground",
        "Rock",
        "Bug",
        "Ghost",
        "Steel",
        "Fire",
        "Water",
        "Grass",
        "Electric",
        "Psychic",
        "Ice",
        "Dragon",
        "Fairy",
        "Dark"
    ).sorted()
    var type2 = arrayOf(
        "",
        "Normal",
        "Fighting",
        "Flying",
        "Poison",
        "Ground",
        "Rock",
        "Bug",
        "Ghost",
        "Steel",
        "Fire",
        "Water",
        "Grass",
        "Electric",
        "Psychic",
        "Ice",
        "Dragon",
        "Fairy",
        "Dark"
    ).sorted()

    var type_color = JSONObject("""{"bug":"#A8BB20", "dark":"#705746", "dragon":"#7038F8", "electric":"#F8D030", "fairy":"#EE99AC", "fighting":"#C03028", "fire":"#F08030", "flying":"#A98FF3", "ghost":"#705898", "grass":"#78C850", "ground":"#E0C068", "ice":"#98D8D8", "normal":"#A8A878", "poison":"#A040A0", "psychic":"#F85888", "rock":"#B8A038", "steel":"#B7B7CE", "water":"#6890F0"}""")

    // Initialize Recyclerview to show coins object
    private lateinit var recycler_type_w_x1: RecyclerView
    private lateinit var recycler_type_n: RecyclerView
    private lateinit var recycler_type_s_x1: RecyclerView
    private lateinit var typeWx1Adapter: TypeWx1Adapter
    private lateinit var typeNAdapter: TypeNAdapter
    private lateinit var typeSx1Adapter: TypeSx1Adapter

    // Initialize List of coins to empty
    private var mType_Weak_x1 = JSONArray()
    private var mType_Strong_x1 = JSONArray()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_types_effect, container, false)

        val spin_1 = view.findViewById<View>(R.id.type_1) as Spinner
        val spin_2 = view.findViewById<View>(R.id.type_2) as Spinner

        recycler_type_w_x1 = view.findViewById(R.id.rec_1_1)
        recycler_type_n = view.findViewById(R.id.rec_2)
        recycler_type_s_x1 = view.findViewById(R.id.rec_3_1)

        // Set Recyclerview is fit screen size.
        recycler_type_w_x1?.setHasFixedSize(true)
        recycler_type_n?.setHasFixedSize(true)
        recycler_type_s_x1?.setHasFixedSize(true)

        recycler_type_w_x1?.setLayoutManager(LinearLayoutManager(requireActivity().applicationContext))
        recycler_type_n?.setLayoutManager(LinearLayoutManager(requireActivity().applicationContext))
        recycler_type_s_x1?.setLayoutManager(LinearLayoutManager(requireActivity().applicationContext))

        spin_1.onItemSelectedListener = this
        spin_2.onItemSelectedListener = this

        val adapter_type1: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireActivity(), android.R.layout.simple_spinner_item, type1)
        adapter_type1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val adapter_type2: ArrayAdapter<*> =
            ArrayAdapter<Any?>(requireActivity(), android.R.layout.simple_spinner_item, type2)
        adapter_type2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spin_1.adapter = adapter_type1
        spin_2.adapter = adapter_type2

        return view
    }


    private fun loadData(name: String) {
        // Call API by okhttp and retrofit with Method GET
        val call: Call<Map<*, *>> = HttpManager.getInstance().getService().getType(name)
        call.enqueue(object : Callback<Map<*, *>?> {
            override fun onResponse(call: Call<Map<*, *>?>, response: Response<Map<*, *>?>) {
                if (response.isSuccessful) {
                    val obj_data = JSONObject(response.body())
                    try {
                        mType_Weak_x1 = obj_data.getJSONObject("damage_relations").getJSONArray("double_damage_from")
                        mType_Strong_x1 = obj_data.getJSONObject("damage_relations").getJSONArray("half_damage_from")

                        val mType_Nature = JSONArray("""[{"name":"bug"}, {"name":"dark"}, {"name":"dragon"}, {"name":"electric"}, {"name":"fairy"}, {"name":"fighting"}, {"name":"fire"}, {"name":"flying"}, {"name":"ghost"}, {"name":"grass"}, {"name":"ground"}, {"name":"ice"}, {"name":"normal"}, {"name":"poison"}, {"name":"psychic"}, {"name":"rock"}, {"name":"steel"}, {"name":"water"}]""")

                        typeWx1Adapter = TypeWx1Adapter(requireActivity().applicationContext, mType_Weak_x1, type_color)
                        typeNAdapter = TypeNAdapter(requireActivity().applicationContext, mType_Nature, mType_Weak_x1, mType_Strong_x1, type_color)
                        typeSx1Adapter = TypeSx1Adapter(requireActivity().applicationContext, mType_Strong_x1, type_color)

                        // Display coin to recyclerview
                        recycler_type_w_x1.setAdapter(typeWx1Adapter)
                        recycler_type_n.setAdapter(typeNAdapter)
                        recycler_type_s_x1.setAdapter(typeSx1Adapter)
                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call<Map<*, *>?>, t: Throwable) {
                // Show exception error
                Toast.makeText(
                    requireContext(), t.toString(), Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onItemSelected(arg0: AdapterView<*>?, arg1: View?, position: Int, id: Long) {
        loadData(type1.get(position).toLowerCase(Locale.ROOT))
    }

    override fun onNothingSelected(arg0: AdapterView<*>?) {
        // TODO Auto-generated method stub
    }
}