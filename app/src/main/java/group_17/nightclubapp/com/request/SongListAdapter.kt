package group_17.nightclubapp.com.request

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import group_17.nightclubapp.com.R
import java.util.concurrent.Executors

class SongListAdapter(private val context: Activity, private val songRequest: List<Song>?): ArrayAdapter<Song>(context, R.layout.songlist_layout,
    songRequest as MutableList<Song>
) {
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.songlist_layout, null, true)
        println("debug: adapter 1does this run?")

        val imageView = rowView.findViewById(R.id.iv_song) as ImageView
        val tvTitle = rowView.findViewById(R.id.tv_song_title) as TextView
        val tvArtist = rowView.findViewById(R.id.tv_song_artist) as TextView

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())
        var image: Bitmap? = null

        executor.execute {
            // Image URL
            val imageURL = songRequest?.get(position)?.album?.cover ?: "https://t3.ftcdn.net/jpg/03/35/13/14/360_F_335131435_DrHIQjlOKlu3GCXtpFkIG1v0cGgM9vJC.jpg"

            // Tries to get the image and post it in the ImageView
            // with the help of Handler
            try {
                val `in` = java.net.URL(imageURL).openStream()
                image = BitmapFactory.decodeStream(`in`)

                // Only for making changes in UI
                handler.post {
                    imageView.setImageBitmap(image)
                }
            }

            // If the URL doesnot point to
            // image or any other kind of failure
            catch (e: Exception) {
                e.printStackTrace()
            }
        }

        tvTitle.text = songRequest?.get(position)?.title ?: ""
        tvArtist.text = songRequest?.get(position)?.artist?.name ?: ""

        return rowView
    }
}