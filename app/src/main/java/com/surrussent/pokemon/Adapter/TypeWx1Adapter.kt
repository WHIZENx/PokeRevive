package com.surrussent.pokemon.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.surrussent.pokemon.Adapter.TypeWx1Adapter.MyViewHolder
import com.surrussent.pokemon.R
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class TypeWx1Adapter(// Initialize Adapter variable
    private val mContext: Context, private val mData: JSONArray, private val mColors: JSONObject
) :
    RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Set recyclerview layout
        val view = LayoutInflater.from(mContext).inflate(R.layout.recycler_weak_x1, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val jObj = mData.optJSONObject(position)
        try {
            val name = jObj.getString("name")
            val color_code = mColors.getString(name)
            holder.type_name.setText(name.capitalize())
            holder.type_color.setBackgroundColor(Color.parseColor(color_code))
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return mData.length()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Initialize View Object in recyclerview
        var type_name: TextView
        var type_color: RelativeLayout

        init {
            type_name = itemView.findViewById(R.id.text_type)
            type_color = itemView.findViewById(R.id.rev)
        }
    }
}