package com.example.uielement2

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView

class AlbumAdapter(var context: Context, var arrayList: ArrayList<Album>): BaseAdapter() {
    override fun getItem(position: Int): Any {
        return arrayList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return arrayList.size
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = View.inflate(context, R.layout.grid_item_list, null)

        val icons:ImageView = view.findViewById(R.id.icons)
        val names:TextView = view.findViewById(R.id.name_text_view)

        val album: Album = arrayList.get(position)

        icons.setImageResource(album.icons!!)
        names.text = album.name

        return view
    }

}