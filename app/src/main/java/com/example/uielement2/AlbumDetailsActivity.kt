package com.example.uielement2

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar
import org.w3c.dom.Text

class AlbumDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_album_details)

        val bundle: Bundle = intent.extras!!
        val icon: Int = bundle.getInt("icons")
        val albumName = intent.getStringExtra("albums")
        val songs = intent.getStringArrayListExtra( "songs")

        val adapter =
            songs?.let { ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, it) }
        val songList = findViewById<ListView>(R.id.songList)
        songList.adapter = adapter
        registerForContextMenu(songList)


        val albumCover = findViewById<ImageView>(R.id.imageView)
        albumCover.setImageResource(icon)
        findViewById<TextView>(R.id.albumName).text = albumName
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        super.onCreateContextMenu(menu, v, menuInfo)
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.delete_song, menu)
    }
    override fun onContextItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.queue_option -> {
                val dialogBuilder = AlertDialog.Builder(this)

                dialogBuilder.setMessage("Remove this song from the album?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", DialogInterface.OnClickListener{
                            dialog, which ->
                        val bundle: Bundle = intent.extras!!
                        val icon: Int = bundle.getInt("icons")
                        val songs = intent.getStringArrayListExtra( "songs")
                        val albumName = intent.getStringExtra("albums")

                        val info = item.menuInfo as AdapterView.AdapterContextMenuInfo
                        val listPosition = info.position
                        val song = songs?.get(listPosition)
                        songs!!.remove(song)

                        Toast.makeText(applicationContext, "$song was removed from album: $albumName", Toast.LENGTH_SHORT).show()

                        val intent = Intent(applicationContext, AlbumDetailsActivity::class.java)
                        intent.putExtra("icons", icon)
                        intent.putExtra("songs", songs)
                        intent.putExtra("albums", albumName)
                        startActivity(intent)

                    }).setNegativeButton("No", DialogInterface.OnClickListener{
                            dialog, which ->
                        dialog.cancel()
                    })

                val alert = dialogBuilder.create()
                alert.setTitle("Confirmation")
                alert.show()
                /*
                */

                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.go_to_albums_page -> {
                startActivity(Intent(this, AlbumsActivity::class.java))
                true
            }R.id.go_to_queue_page -> {
                val intent = Intent(this, QueueActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}

}